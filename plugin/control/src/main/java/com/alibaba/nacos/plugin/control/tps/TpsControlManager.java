package com.alibaba.nacos.plugin.control.tps;

import com.alibaba.nacos.common.executor.ExecutorFactory;
import com.alibaba.nacos.common.utils.StringUtils;
import com.alibaba.nacos.plugin.control.Loggers;
import com.alibaba.nacos.plugin.control.ruleactivator.RuleParserProxy;
import com.alibaba.nacos.plugin.control.ruleactivator.RuleStorageProxy;
import com.alibaba.nacos.plugin.control.tps.request.TpsCheckRequest;
import com.alibaba.nacos.plugin.control.tps.response.TpsCheckResponse;
import com.alibaba.nacos.plugin.control.tps.response.TpsResultCode;
import com.alibaba.nacos.plugin.control.tps.rule.TpsControlRule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * abstract tps control manager.
 */
public class TpsControlManager {
    
    /**
     * point name -> tps barrier.
     */
    protected final Map<String, TpsBarrier> points = new ConcurrentHashMap<>(16);
    
    /**
     * point name -> tps control rule.
     */
    protected final Map<String, TpsControlRule> rules = new ConcurrentHashMap<>(16);
    
    protected ScheduledExecutorService executorService;
    
    public TpsControlManager() {
        
        executorService = ExecutorFactory.newSingleScheduledExecutorService(r -> {
            Thread thread = new Thread(r, "nacos.plugin.tps.control.reporter");
            thread.setDaemon(true);
            return thread;
        });
        
        startTpsReport();
    }
    
    protected void startTpsReport() {
        executorService.scheduleWithFixedDelay(new TpsMetricsReporter(), 0, 900, TimeUnit.MILLISECONDS);
    }
    
    /**
     * apple tps rule.
     *
     * @param pointName pointName.
     */
    public synchronized void registerTpsPoint(String pointName) {
        if (!points.containsKey(pointName)) {
            points.put(pointName, TpsBarrierCreatorProxy.getTpsBarrierCreator().createTpsBarrier(pointName));
            if (rules.containsKey(pointName)) {
                points.get(pointName).applyRule(rules.get(pointName));
            } else {
                initTpsRule(pointName);
            }
        }
    }
    
    protected void initTpsRule(String pointName) {
        RuleStorageProxy ruleStorageProxy = RuleStorageProxy.getInstance();
        
        String localRuleContent = ruleStorageProxy.getLocalDiskStorage().getTpsRule(pointName);
        if (StringUtils.isNotBlank(localRuleContent)) {
            Loggers.CONTROL.info("Found local disk tps control rule of {},content ={}", pointName, localRuleContent);
        } else if (ruleStorageProxy.getExternalStorage() != null
                && ruleStorageProxy.getExternalStorage().getTpsRule(pointName) != null) {
            localRuleContent = ruleStorageProxy.getExternalStorage().getTpsRule(pointName);
            if (StringUtils.isNotBlank(localRuleContent)) {
                Loggers.CONTROL.info("Found external  tps control rule of {},content ={}", pointName, localRuleContent);
            }
        }
        
        if (StringUtils.isNotBlank(localRuleContent)) {
            TpsControlRule tpsLimitRule = RuleParserProxy.getInstance().parseTpsRule(localRuleContent);
            this.applyTpsRule(pointName, tpsLimitRule);
        } else {
            Loggers.CONTROL.info("No tps control rule of {} found  ", pointName, localRuleContent);
        }
    }
    
    /**
     * apple tps rule.
     *
     * @param pointName pointName.
     * @param rule      rule.
     */
    public synchronized void applyTpsRule(String pointName, TpsControlRule rule) {
        if (rule == null) {
            rules.remove(pointName);
        } else {
            rules.put(pointName, rule);
        }
        if (points.containsKey(pointName)) {
            points.get(pointName).applyRule(rule);
        }
    }
    
    public Map<String, TpsBarrier> getPoints() {
        return points;
    }
    
    public Map<String, TpsControlRule> getRules() {
        return rules;
    }
    
    /**
     * check tps result.
     *
     * @param tpsRequest TpsRequest.
     * @return check current tps is allowed.
     */
    public TpsCheckResponse check(TpsCheckRequest tpsRequest) {
       
        if (points.containsKey(tpsRequest.getPointName())) {
            try {
                return points.get(tpsRequest.getPointName()).applyTps(tpsRequest);
            } catch (Throwable throwable) {
                Loggers.TPS.warn("[{}]apply tps error,error={}", tpsRequest.getPointName(), throwable);
            }
        }
        return new TpsCheckResponse(true, TpsResultCode.CHECK_SKIP, "skip");
        
    }
    
    class TpsMetricsReporter implements Runnable {
        
        long lastReportSecond = 0L;
        
        long lastReportMinutes = 0L;
        
        long lastReportHours = 0L;
        
        /**
         * get format string "2021-01-16 17:20:21" of timestamp.
         *
         * @param timeStamp timestamp milliseconds.
         * @return
         */
        public String getTimeFormatOfSecond(long timeStamp) {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp));
            return format;
        }
        
        @Override
        public void run() {
            try {
                long now = System.currentTimeMillis();
                StringBuilder stringBuilder = new StringBuilder();
                Set<Map.Entry<String, TpsBarrier>> entries = points.entrySet();
                
                long tempSecond = 0L;
                long tempMinutes = 0L;
                long tempHours = 0L;
                
                long metricsTime = now - 1000L;
                String formatString = getTimeFormatOfSecond(metricsTime);
                for (Map.Entry<String, TpsBarrier> entry : entries) {
                    TpsBarrier tpsBarrier = entry.getValue();
                    String pointName = entry.getKey();
                    TpsMetrics metrics = tpsBarrier.getPointBarrier().getMetrics(metricsTime);
                    if (metrics != null) {
                        //already reported.
                        if (lastReportSecond != 0L && lastReportSecond == metrics.getTimeStamp()) {
                            continue;
                        }
                        tempSecond = metrics.getTimeStamp();
                        
                        stringBuilder.append(pointName).append("|").append("point").append("|")
                                .append(metrics.getPeriod()).append("|").append(formatString).append("|")
                                .append(metrics.getCounter().getPassCount()).append("|")
                                .append(metrics.getCounter().getDeniedCount()).append("|").append("\n");
                    }
                }
                
                if (tempSecond > 0) {
                    lastReportSecond = tempSecond;
                }
                if (tempMinutes > 0) {
                    lastReportMinutes = tempMinutes;
                }
                if (tempHours > 0) {
                    lastReportHours = tempHours;
                }
                
                if (stringBuilder.length() > 0) {
                    Loggers.TPS.info("Tps reporting...\n" + stringBuilder.toString());
                }
            } catch (Throwable throwable) {
                Loggers.TPS.error("Tps reporting error", throwable);
            }
            
        }
    }
}
