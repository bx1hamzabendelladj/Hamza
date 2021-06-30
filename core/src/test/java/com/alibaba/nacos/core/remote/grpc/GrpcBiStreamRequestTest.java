/*
 *  Copyright 1999-2021 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.alibaba.nacos.core.remote.grpc;

import com.alibaba.nacos.api.grpc.auto.BiRequestStreamGrpc;
import com.alibaba.nacos.api.grpc.auto.Payload;
import com.alibaba.nacos.api.remote.RemoteConstants;
import com.alibaba.nacos.api.remote.request.ConnectResetRequest;
import com.alibaba.nacos.api.remote.request.ConnectionSetupRequest;
import com.alibaba.nacos.api.remote.request.RequestMeta;
import com.alibaba.nacos.api.remote.response.ConnectResetResponse;
import com.alibaba.nacos.api.remote.response.Response;
import com.alibaba.nacos.common.remote.client.grpc.GrpcUtils;
import com.alibaba.nacos.core.remote.Connection;
import com.alibaba.nacos.core.remote.ConnectionManager;
import com.alibaba.nacos.sys.utils.ApplicationUtils;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;
import io.grpc.testing.GrpcCleanupRule;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.alibaba.nacos.core.remote.grpc.BaseGrpcServer.CONTEXT_KEY_CONN_ID;
import static com.alibaba.nacos.core.remote.grpc.BaseGrpcServer.CONTEXT_KEY_CONN_LOCAL_PORT;
import static com.alibaba.nacos.core.remote.grpc.BaseGrpcServer.CONTEXT_KEY_CONN_REMOTE_IP;
import static com.alibaba.nacos.core.remote.grpc.BaseGrpcServer.CONTEXT_KEY_CONN_REMOTE_PORT;

/**
 * @author chenglu
 * @date 2021-06-30 17:11
 */
@RunWith(MockitoJUnitRunner.class)
public class GrpcBiStreamRequestTest {
    @Rule
    public GrpcCleanupRule grpcCleanupRule = new GrpcCleanupRule();
    
    public BiRequestStreamGrpc.BiRequestStreamStub streamStub;
    
//    @Mock
    private ConnectionManager connectionManager = new ConnectionManager();
    
    private StreamObserver<Payload> payloadStreamObserver;
    
    @Before
    public void setUp() throws IOException {
        String serverName = InProcessServerBuilder.generateName();
        String remoteIp = "127.0.0.1";
        // Create a server, add service, start, and register for automatic graceful shutdown.
        grpcCleanupRule.register(InProcessServerBuilder
                .forName(serverName).directExecutor().addService(new GrpcBiStreamRequestAcceptor(connectionManager))
                .intercept(new ServerInterceptor() {
                    @Override
                    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata,
                            ServerCallHandler<ReqT, RespT> serverCallHandler) {
                        Context ctx = Context.current().withValue(CONTEXT_KEY_CONN_ID, UUID.randomUUID().toString())
                                .withValue(CONTEXT_KEY_CONN_LOCAL_PORT, 1234)
                                .withValue(CONTEXT_KEY_CONN_REMOTE_PORT, 8948)
                                .withValue(CONTEXT_KEY_CONN_REMOTE_IP, remoteIp);
                        return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
                    }
                })
                .build().start());
        streamStub = BiRequestStreamGrpc.newStub(
                grpcCleanupRule.register(InProcessChannelBuilder.forName(serverName).directExecutor().build()));
//        Mockito.doReturn(true).when(connectionManager).traced(remoteIp);
//        Mockito.when(connectionManager.register(Mockito.anyString(), Mockito.any(Connection.class))).thenReturn(true);
    }
    
    @After
    public void tearDown() {
        ApplicationUtils.setStarted(false);
    }
    
    @Test
    public void testConnectionSetupRequest() {
        
        String connectId = UUID.randomUUID().toString();
        String requestId = UUID.randomUUID().toString();
        
        StreamObserver<Payload> streamObserver = new StreamObserver<Payload>() {
            @Override
            public void onNext(Payload payload) {
                System.out.println("Receive data from server, data: " + payload);
                Assert.assertNotNull(payload);
                ConnectResetRequest connectResetRequest = (ConnectResetRequest) GrpcUtils.parse(payload);
                Response response = new ConnectResetResponse();
                response.setRequestId(connectResetRequest.getRequestId());
                Payload res = GrpcUtils.convert(response);
                payloadStreamObserver.onNext(res);
            }
    
            @Override
            public void onError(Throwable throwable) {
            }
    
            @Override
            public void onCompleted() {
            }
        };
        payloadStreamObserver = streamStub.requestBiStream(streamObserver);
        RequestMeta metadata = new RequestMeta();
        metadata.setClientIp("127.0.0.1");
        metadata.setConnectionId(connectId);

        ConnectionSetupRequest connectionSetupRequest = new ConnectionSetupRequest();
        connectionSetupRequest.setRequestId(requestId);
        connectionSetupRequest.setClientVersion("2.0.3");
        Payload payload = GrpcUtils.convert(connectionSetupRequest, metadata);
        payloadStreamObserver.onNext(payload);
    }
}
