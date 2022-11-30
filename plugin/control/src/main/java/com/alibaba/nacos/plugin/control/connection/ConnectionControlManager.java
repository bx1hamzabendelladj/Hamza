package com.alibaba.nacos.plugin.control.connection;

import com.alibaba.nacos.common.executor.ExecutorFactory;
import com.alibaba.nacos.common.spi.NacosServiceLoader;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.control.Loggers;
import com.alibaba.nacos.plugin.control.connection.request.ConnectionCheckRequest;
import com.alibaba.nacos.plugin.control.connection.response.ConnectionCheckResponse;
import com.alibaba.nacos.plugin.control.connection.rule.ConnectionControlRule;
import com.alibaba.nacos.plugin.control.ruleactivator.RuleParserProxy;
import com.alibaba.nacos.plugin.control.ruleactivator.RuleStorageProxy;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * connection control manager.
 */
public abstract class ConnectionControlManager {
    
    protected ConnectionControlRule connectionControlRule;
    
    protected Collection<ConnectionMetricsCollector> metricsCollectorList;
    
    public abstract String getName();
    
    private ScheduledExecutorService executorService;
    
    public ConnectionControlManager() {
        metricsCollectorList = NacosServiceLoader.load(ConnectionMetricsCollector.class);
        Loggers.CONTROL.info("Load connection metrics collector,size={},{}", metricsCollectorList.size(),
                metricsCollectorList);
        initConnectionRule();
        if (!metricsCollectorList.isEmpty()) {
            initExecuteService();
            startConnectionMetricsReport();
        }
        
    }
    
    private void initExecuteService() {
        executorService = ExecutorFactory.newSingleScheduledExecutorService(r -> {
            Thread thread = new Thread(r, "nacos.plugin.control.connection.reporter");
            thread.setDaemon(true);
            return thread;
        });
    }
    
    private void startConnectionMetricsReport() {
        executorService.scheduleWithFixedDelay(new ConnectionMetricsReporter(), 0, 3000, TimeUnit.MILLISECONDS);
    }
    
    private void initConnectionRule() {
        RuleStorageProxy ruleStorageProxy = RuleStorageProxy.getInstance();
        String localRuleContent = ruleStorageProxy.getLocalDiskStorage().getConnectionRule();
        if (StringUtils.isNotBlank(localRuleContent)) {
            Loggers.CONTROL.info("Found local disk connection rule content on start up,value  ={}", localRuleContent);
        } else if (ruleStorageProxy.getExternalStorage() != null
                && ruleStorageProxy.getExternalStorage().getConnectionRule() != null) {
            localRuleContent = ruleStorageProxy.getExternalStorage().getConnectionRule();
            if (StringUtils.isNotBlank(localRuleContent)) {
                Loggers.CONTROL
                        .info("Found persist disk connection rule content on start up ,value  ={}", localRuleContent);
            }
        }
        
        if (StringUtils.isNotBlank(localRuleContent)) {
            connectionControlRule = RuleParserProxy.getInstance().parseConnectionRule(localRuleContent);
            Loggers.CONTROL.info("init connection rule end");
            
        } else {
            Loggers.CONTROL.info("No connection rule content found ,use default empty rule ");
            connectionControlRule = new ConnectionControlRule();
        }
    }
    
    public ConnectionControlRule getConnectionLimitRule() {
        return connectionControlRule;
    }
    
    public abstract void applyConnectionLimitRule(ConnectionControlRule connectionControlRule);
    
    /**
     * check connection allowed.
     *
     * @param connectionCheckRequest connectionCheckRequest.
     * @return
     */
    public abstract ConnectionCheckResponse check(ConnectionCheckRequest connectionCheckRequest);
    
    class ConnectionMetricsReporter implements Runnable {
        
        @Override
        public void run() {
            Map<String, Integer> metricsTotalCount = metricsCollectorList.stream().collect(
                    Collectors.toMap(ConnectionMetricsCollector::getName, ConnectionMetricsCollector::getTotalCount));
            int totalCount = metricsTotalCount.values().stream().mapToInt(Integer::intValue).sum();
            
            Loggers.CONNECTION.info(String.format("ConnectionMetrics, totalCount = %s, detail = %s", totalCount,
                    metricsTotalCount.toString()));
            
        }
    }
}
