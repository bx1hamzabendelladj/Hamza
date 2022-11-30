package com.alibaba.nacos.plugin.control.configs;

import com.alibaba.nacos.plugin.control.Loggers;
import com.alibaba.nacos.sys.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ControlConfigs {
    
    private static ControlConfigs instance = null;
    
    public static ControlConfigs getInstance() {
        if (instance == null) {
            try {
                instance = ApplicationUtils.getBean(ControlConfigs.class);
            } catch (Throwable throwable) {
                Loggers.CONTROL
                        .warn("Fail to get control configs bean from spring context,use default constructor instance");
                instance = new ControlConfigs();
            }
        }
        return instance;
    }
    
    public static void setInstance(ControlConfigs instance) {
        ControlConfigs.instance = instance;
    }
    
    @Value("${nacos.plugin.control.tps.enabled:false}")
    private boolean tpsEnabled = false;
    
    @Value("${nacos.plugin.control.tps.barrier.creator:mse}")
    private String tpsBarrierCreator = "mse";
    
    @Value("${nacos.plugin.control.tps.barrier.rule.creator:mse}")
    private String tpsRuleBarrierCreator = "mse";

    @Value("${nacos.plugin.control.connection.runtime.ejector:nacos}")
    private String connectionRuntimeEjector = "nacos";
    
    @Value("${nacos.plugin.control.connection.manager:nacos}")
    private String connectionManager = "nacos";
    
    @Value("${nacos.plugin.control.rule.external.storage:internalconfigcenter}")
    private String ruleExternalStorage = "internalconfigcenter";
    
    @Value("${nacos.plugin.control.rule.parser:nacos}")
    private String ruleParser = "nacos";
    
    public void setTpsEnabled(boolean tpsEnabled) {
        this.tpsEnabled = tpsEnabled;
    }
    
    public String getTpsBarrierCreator() {
        return tpsBarrierCreator;
    }
    
    public void setTpsBarrierCreator(String tpsBarrierCreator) {
        this.tpsBarrierCreator = tpsBarrierCreator;
    }
    
    public String getTpsRuleBarrierCreator() {
        return tpsRuleBarrierCreator;
    }
    
    public void setTpsRuleBarrierCreator(String tpsRuleBarrierCreator) {
        this.tpsRuleBarrierCreator = tpsRuleBarrierCreator;
    }
    
    public String getRuleExternalStorage() {
        return ruleExternalStorage;
    }
    
    public void setRuleExternalStorage(String ruleExternalStorage) {
        this.ruleExternalStorage = ruleExternalStorage;
    }
    
    public String getRuleParser() {
        return ruleParser;
    }
    
    public void setRuleParser(String ruleParser) {
        this.ruleParser = ruleParser;
    }
    
    public String getConnectionManager() {
        return connectionManager;
    }
    
    public void setConnectionManager(String connectionManager) {
        this.connectionManager = connectionManager;
    }
    
    public String getConnectionRuntimeEjector() {
        return connectionRuntimeEjector;
    }
    
    public void setConnectionRuntimeEjector(String connectionRuntimeEjector) {
        this.connectionRuntimeEjector = connectionRuntimeEjector;
    }
}
