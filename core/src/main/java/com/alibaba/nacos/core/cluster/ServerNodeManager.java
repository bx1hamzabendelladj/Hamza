/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.core.cluster;

import com.alibaba.nacos.consistency.Config;
import com.alibaba.nacos.consistency.ConsistencyProtocol;
import com.alibaba.nacos.consistency.LogProcessor;
import com.alibaba.nacos.consistency.ProtocolMetaData;
import com.alibaba.nacos.consistency.ap.APProtocol;
import com.alibaba.nacos.consistency.ap.LogProcessor4AP;
import com.alibaba.nacos.consistency.cp.CPProtocol;
import com.alibaba.nacos.consistency.cp.LogProcessor4CP;
import com.alibaba.nacos.core.cluster.task.NodeStateReportTask;
import com.alibaba.nacos.core.cluster.task.SyncNodeTask;
import com.alibaba.nacos.core.distributed.id.DistributeIDManager;
import com.alibaba.nacos.core.notify.NotifyManager;
import com.alibaba.nacos.core.utils.Constants;
import com.alibaba.nacos.core.utils.InetUtils;
import com.alibaba.nacos.core.utils.Loggers;
import com.alibaba.nacos.core.utils.PropertyUtil;
import com.alibaba.nacos.core.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 *
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 */
@Component(value = "serverNodeManager")
@SuppressWarnings("all")
public class ServerNodeManager implements ApplicationListener<WebServerInitializedEvent>, NodeManager {

    private final NodeTaskManager taskManager = new NodeTaskManager(this);

    private Map<String, Node> serverListHealth = new ConcurrentSkipListMap<>();

    private Set<Node> serverListUnHealth = new CopyOnWriteArraySet<>();

    private volatile List<Node> nodeView = new ArrayList<>();

    private volatile boolean isInIpList = true;

    private volatile boolean isAddressServerHealth = true;

    public String domainName;

    public String addressPort;

    public String addressUrl;

    public String envIdUrl;

    public String addressServerUrl;

    private boolean isHealthCheck = true;

    private String contextPath = "";

    @Value("${server.port:8848}")
    private int port;

    private String localAddress;

    @Value("${useAddressServer:false}")
    private boolean isUseAddressServer;

    private final ServletContext servletContext;

    private Node self;

    public ServerNodeManager(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @PostConstruct
    @Override
    public void init() {
        this.localAddress = InetUtils.getSelfIp() + ":" + port;

        // register NodeChangeEvent publisher to NotifyManager

        NotifyManager.registerPublisher(NodeChangeEvent::new, NodeChangeEvent.class);

        // init nacos core sys

        initSys();

        SyncNodeTask task = new SyncNodeTask(servletContext);
        taskManager.execute(task);

        // Consistency protocol module initialization

        initAPProtocol();
        initCPProtocol();

        // To initialize the distributed ID generator, need to wait
        // for the cluster node information to be initialized.

        DistributeIDManager.init(this);
    }

    @Override
    public void update(Node newNode) {

        long lastRefTime = Long.parseLong(String.valueOf(newNode.extendVal(Node.LAST_REF_TIME)));

        // If this node updates itself, ignore the health judgment

        if (lastRefTime < System.currentTimeMillis() && !isSelf(newNode)) {
            newNode.setState(NodeState.DOWN);
            serverListHealth.remove(newNode.address());
            serverListUnHealth.add(newNode);
        } else {
            serverListHealth.put(newNode.address(), newNode);
        }

        // reset node view to lazy update

        nodeView = null;

    }

    @Override
    public int indexOf(String address) {
        int index = 1;
        for (Map.Entry<String, Node> entry : serverListHealth.entrySet()) {
            if (Objects.equals(entry.getKey(), address)) {
                return index;
            }
            index ++;
        }
        return index;
    }

    @Override
    public boolean hasNode(String address) {
        boolean result = serverListHealth.containsKey(address);
        if (!result) {
            for (Map.Entry<String, Node> entry : serverListHealth.entrySet()) {
                if (StringUtils.contains(entry.getKey(), address)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public Node self() {
        if (Objects.isNull(self)) {
            self = serverListHealth.get(localAddress);
        }
        return self;
    }

    @Override
    public List<Node> allNodes() {
        if (CollectionUtils.isEmpty(nodeView)) {
            synchronized (this) {
                if (CollectionUtils.isEmpty(nodeView)) {
                    nodeView = new ArrayList<>(serverListHealth.values());
                }
            }
        }
        return nodeView;
    }

    @Override
    public synchronized void nodeJoin(Collection<Node> nodes) {

        for (Node node : nodes) {
            serverListHealth.put(node.address(), node);
        }

        // r eset node view

        nodeView = null;

        NotifyManager.publishEvent(NodeChangeEvent.class, NodeChangeEvent.builder()
                .kind("join")
                .changeNodes(nodes)
                .allNodes(allNodes())
                .build());

    }

    @Override
    public synchronized void nodeLeave(Collection<Node> nodes) {
        for (Node node : nodes) {
            serverListHealth.remove(node.address());
        }

        // reset node view

        nodeView = null;

        NotifyManager.publishEvent(NodeChangeEvent.class, NodeChangeEvent.builder()
                .kind("leave")
                .changeNodes(nodes)
                .allNodes(allNodes())
                .build());
    }

    @Override
    public void subscribe(NodeChangeListener listener) {
        NotifyManager.registerSubscribe(listener);
    }

    @Override
    public void unSubscribe(NodeChangeListener listener) {
        NotifyManager.deregisterSubscribe(listener);
    }

    @Override
    public String getContextPath() {
        if (StringUtils.isBlank(contextPath)) {
            String contextPath = PropertyUtil.getProperty(Constants.WEB_CONTEXT_PATH);

            // If you can't find it, check it from Sping Environment

            if (StringUtils.isBlank(contextPath)) {
                contextPath = SpringUtils.getProperty(Constants.WEB_CONTEXT_PATH);
            }
            if (Constants.ROOT_WEB_CONTEXT_PATH.equals(contextPath)) {
                return StringUtils.EMPTY;
            } else {
                return contextPath;
            }
        }
        return contextPath;
    }

    @Override
    public boolean isFirstIp() {
        return 1 == indexOf(localAddress);
    }

    @Override
    public void clean() {
    }

    @PreDestroy
    @Override
    public void shutdown() {
        Map<String, ConsistencyProtocol> protocolMap = SpringUtils.getBeansOfType(ConsistencyProtocol.class);
        for (ConsistencyProtocol protocol : protocolMap.values()) {
            protocol.shutdown();
        }
    }

    public boolean isSelf(Node node) {
        return Objects.equals(node.address(), self.address());
    }

    private void initSys() {
        String envDomainName = System.getenv("address_server_domain");
        if (StringUtils.isBlank(envDomainName)) {
            domainName = System.getProperty("address.server.domain", "jmenv.tbsite.net");
        } else {
            domainName = envDomainName;
        }
        String envAddressPort = System.getenv("address_server_port");
        if (StringUtils.isBlank(envAddressPort)) {
            addressPort = System.getProperty("address.server.port", "8080");
        } else {
            addressPort = envAddressPort;
        }
        addressUrl = System.getProperty("address.server.url",
                servletContext.getContextPath() + "/" + "serverlist");
        addressServerUrl = "http://" + domainName + ":" + addressPort + addressUrl;
        envIdUrl = "http://" + domainName + ":" + addressPort + "/env";

        Loggers.CORE.info("ServerListService address-server port:" + addressPort);
        Loggers.CORE.info("ADDRESS_SERVER_URL:" + addressServerUrl);

        isHealthCheck = Boolean.parseBoolean(SpringUtils.getProperty("isHealthCheck", "true"));
    }

    private void initAPProtocol() {
        CPProtocol protocol = SpringUtils.getBean(CPProtocol.class);
        Config config = (Config) SpringUtils.getBean(protocol.configType());
        config.addLogProcessors(loadProcessorAndInjectProtocol(LogProcessor4AP.class, protocol).toArray(new LogProcessor[0]));
        protocol.init((config));

        injectClusterInfo(protocol.protocolMetaData());

        subscribe(event -> injectClusterInfo(protocol.protocolMetaData()));
    }

    private void initCPProtocol() {
        APProtocol protocol = SpringUtils.getBean(APProtocol.class);
        Config config = (Config) SpringUtils.getBean(protocol.configType());
        config.addLogProcessors(loadProcessorAndInjectProtocol(LogProcessor4CP.class, protocol).toArray(new LogProcessor[0]));
        protocol.init((config));

        injectClusterInfo(protocol.protocolMetaData());

        subscribe(event -> injectClusterInfo(protocol.protocolMetaData()));
    }

    private void injectClusterInfo(ProtocolMetaData metaData) {

        Map<String, Map<String, Object>> defaultMetaData = new HashMap<>();
        Map<String, Object> sub = new HashMap<>(8);

        defaultMetaData.put(ProtocolMetaData.GLOBAL, sub);

        sub.put(ProtocolMetaData.CLUSTER_INFO, allNodes().stream().map(Node::address).collect(Collectors.toList()));
        sub.put(ProtocolMetaData.SELF, self.address());

        metaData.load(defaultMetaData);

    }

    @SuppressWarnings("all")
    private List<LogProcessor> loadProcessorAndInjectProtocol(Class cls, ConsistencyProtocol protocol) {
        Map<String, LogProcessor> beans = (Map<String, LogProcessor>) SpringUtils.getBeansOfType(cls);

        final List<LogProcessor> result = new ArrayList<>(beans.values());

        ServiceLoader<LogProcessor> loader = ServiceLoader.load(cls);
        for (LogProcessor t : loader) {
            t.injectProtocol(protocol);
            result.add(t);
        }
        return result;
    }


    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        taskManager.execute(new NodeStateReportTask());
    }

    public List<Node> getServerListHealth() {
        return nodeView == null ? Collections.emptyList() : nodeView;
    }

    public Set<Node> getServerListUnHealth() {
        return serverListUnHealth;
    }

    public boolean isUseAddressServer() {
        return isUseAddressServer;
    }

    public boolean isInIpList() {
        return isInIpList;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getAddressPort() {
        return addressPort;
    }

    public String getAddressUrl() {
        return addressUrl;
    }

    public String getEnvIdUrl() {
        return envIdUrl;
    }

    public boolean isHealthCheck() {
        return isHealthCheck;
    }

    public boolean isAddressServerHealth() {
        return isAddressServerHealth;
    }

    public String getAddressServerUrl() {
        return addressServerUrl;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public boolean getUseAddressServer() {
        return isUseAddressServer;
    }

    public void setUseAddressServer(boolean useAddressServer) {
        isUseAddressServer = useAddressServer;
    }

    public int getPort() {
        return port;
    }

    public void setAddressServerHealth(boolean addressServerHealth) {
        isAddressServerHealth = addressServerHealth;
    }
}
