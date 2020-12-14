/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.naming.remote.udp;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.remote.response.PushCallBack;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.alibaba.nacos.naming.misc.GlobalExecutor;
import com.alibaba.nacos.naming.misc.Loggers;
import com.alibaba.nacos.naming.push.AckEntry;
import com.alibaba.nacos.naming.push.AckPacket;
import com.alibaba.nacos.naming.push.PushService;
import com.alibaba.nacos.naming.utils.Constants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Udp socket connector to send upd data and listen ack if necessary.
 *
 * @author xiweng.yy
 */
@Component
public class UdpConnector {
    
    private final ConcurrentMap<String, AckEntry> ackMap;
    
    private final ConcurrentMap<String, PushCallBack> callbackMap;
    
    private final DatagramSocket udpSocket;
    
    private final AtomicLong pushCount;
    
    private final AtomicLong pushFailed;
    
    public UdpConnector() throws SocketException {
        this.ackMap = new ConcurrentHashMap<>();
        this.callbackMap = new ConcurrentHashMap<>();
        this.udpSocket = new DatagramSocket();
        this.pushCount = new AtomicLong();
        this.pushFailed = new AtomicLong();
    }
    
    public boolean containAck(String ackId) {
        return ackMap.containsKey(ackId);
    }
    
    /**
     * Sync send data once.
     *
     * @param ackEntry ack entry
     * @throws NacosException nacos exception during sending
     */
    public void sendData(AckEntry ackEntry) throws NacosException {
        try {
            pushCount.incrementAndGet();
            doSend(ackEntry.getOrigin());
        } catch (IOException e) {
            pushFailed.incrementAndGet();
            throw new NacosException(NacosException.SERVER_ERROR, "[NACOS-PUSH] push data with exception: ", e);
        }
    }
    
    /**
     * Async send data without callback. If send failed will retry {@link Constants#UDP_MAX_RETRY_TIMES} times.
     *
     * @param ackEntry ack entry
     */
    public void sendDataWithoutCallback(AckEntry ackEntry) {
        if (isInvalidData(ackEntry)) {
            return;
        }
        try {
            if (!ackMap.containsKey(ackEntry.getKey())) {
                pushCount.incrementAndGet();
            }
            ackMap.put(ackEntry.getKey(), ackEntry);
            Loggers.PUSH.info("send udp packet: " + ackEntry.getKey());
            ackEntry.increaseRetryTime();
            udpSocket.send(ackEntry.getOrigin());
            GlobalExecutor.scheduleRetransmitter(new UdpRetrySender(ackEntry, this), Constants.ACK_TIMEOUT_NANOS,
                    TimeUnit.NANOSECONDS);
        } catch (Exception e) {
            Loggers.PUSH.error("[NACOS-PUSH] failed to push data: {} to client: {}, error: {}", ackEntry.getData(),
                    ackEntry.getOrigin().getAddress().getHostAddress(), e);
            ackMap.remove(ackEntry.getKey());
            pushFailed.incrementAndGet();
        }
    }
    
    /**
     * Send Data with {@link PushCallBack}.
     *
     * @param ackEntry     ack entry
     * @param pushCallBack push callback
     */
    public void sendDataWithCallback(AckEntry ackEntry, PushCallBack pushCallBack) {
        if (isInvalidData(ackEntry)) {
            return;
        }
        try {
            if (!ackMap.containsKey(ackEntry.getKey())) {
                pushCount.incrementAndGet();
            }
            ackMap.put(ackEntry.getKey(), ackEntry);
            callbackMap.put(ackEntry.getKey(), pushCallBack);
            Loggers.PUSH.info("send udp packet with callback: " + ackEntry.getKey());
            ackEntry.increaseRetryTime();
            udpSocket.send(ackEntry.getOrigin());
            GlobalExecutor.scheduleRetransmitter(new PushService.Retransmitter(ackEntry), pushCallBack.getTimeout(),
                    TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            callbackMap.remove(ackEntry.getKey());
            ackMap.remove(ackEntry.getKey());
            pushCallBack.onFail(e);
            pushFailed.incrementAndGet();
        }
    }
    
    private boolean isInvalidData(AckEntry ackEntry) {
        return null == ackEntry || isRetryMaxTime(ackEntry);
    }
    
    private boolean isRetryMaxTime(AckEntry ackEntry) {
        if (ackEntry.getRetryTimes() > Constants.UDP_MAX_RETRY_TIMES) {
            Loggers.PUSH.warn("max re-push times reached, retry times {}, key: {}", ackEntry.getRetryTimes(),
                    ackEntry.getKey());
            ackMap.remove(ackEntry.getKey());
            pushFailed.incrementAndGet();
            return true;
        }
        return false;
    }
    
    private void doSend(DatagramPacket packet) throws IOException {
        if (udpSocket.isConnected() && !udpSocket.isClosed()) {
            udpSocket.send(packet);
        }
    }
    
    private class UdpReceiver implements Runnable {
        
        @Override
        public void run() {
            while (true) {
                byte[] buffer = new byte[1024 * 64];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                try {
                    udpSocket.receive(packet);
                    String json = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();
                    AckPacket ackPacket = JacksonUtils.toObj(json, AckPacket.class);
                    InetSocketAddress socketAddress = (InetSocketAddress) packet.getSocketAddress();
                    String ip = socketAddress.getAddress().getHostAddress();
                    int port = socketAddress.getPort();
                    if (System.nanoTime() - ackPacket.lastRefTime > Constants.ACK_TIMEOUT_NANOS) {
                        Loggers.PUSH.warn("ack takes too long from {} ack json: {}", packet.getSocketAddress(), json);
                    }
                    String ackKey = AckEntry.getAckKey(ip, port, ackPacket.lastRefTime);
                    AckEntry ackEntry = ackMap.remove(ackKey);
                    if (ackEntry == null) {
                        throw new IllegalStateException(
                                "unable to find ackEntry for key: " + ackKey + ", ack json: " + json);
                    }
                    PushCallBack callBack = callbackMap.remove(ackKey);
                    callBack.onSuccess();
                } catch (Throwable e) {
                    Loggers.PUSH.error("[NACOS-PUSH] error while receiving ack data", e);
                }
            }
        }
        
    }
}
