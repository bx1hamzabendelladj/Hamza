package com.alibaba.nacos.plugin.control.configs;

import com.alibaba.nacos.sys.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ControlConfigs {
    
    private static ControlConfigs INSTANCE = null;
    
    public static ControlConfigs getInstance() {
        if (INSTANCE == null) {
            INSTANCE = ApplicationUtils.getBean(ControlConfigs.class);
        }
        return INSTANCE;
    }
    
    public static void setINSTANCE(ControlConfigs INSTANCE) {
        ControlConfigs.INSTANCE = INSTANCE;
    }
    
    @Value("${nacos.plugin.control.tps.barrier.creator:simplecount}")
    private String tpsBarrierCreator = "localsimplecount";
    
    @Value("${nacos.plugin.control.rule.persist.activator:internalconfigcenter}")
    private String rulePersistActivator = "internalconfigcenter";
    
    public String getTpsBarrierCreator() {
        return tpsBarrierCreator;
    }
    
    public void setTpsBarrierCreator(String tpsBarrierCreator) {
        this.tpsBarrierCreator = tpsBarrierCreator;
    }
    
    public String getRulePersistActivator() {
        return rulePersistActivator;
    }
    
    public void setRulePersistActivator(String rulePersistActivator) {
        this.rulePersistActivator = rulePersistActivator;
    }
}
