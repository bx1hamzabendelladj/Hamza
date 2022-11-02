package com.alibaba.nacos.plugin.control.tps.nacos;

import com.alibaba.nacos.plugin.control.tps.RuleBarrier;
import com.alibaba.nacos.plugin.control.tps.RuleBarrierCreator;

import java.util.concurrent.TimeUnit;

public class LocalSimpleCountBarrierCreator implements RuleBarrierCreator {
    
    private static LocalSimpleCountBarrierCreator INSTANCE = new LocalSimpleCountBarrierCreator();
    
    public LocalSimpleCountBarrierCreator() {
    }
    
    public static final LocalSimpleCountBarrierCreator getInstance() {
        return INSTANCE;
    }
    
    @Override
    public RuleBarrier createRuleBarrier(String name, String pattern, TimeUnit period, String model) {
        return new LocalSimpleCountRuleBarrier(name, pattern, period, model);
    }
    
    @Override
    public String name() {
        return "localsimplecount";
    }
}
