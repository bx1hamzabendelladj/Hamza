package com.alibaba.nacos.client.monitor;

import com.alibaba.nacos.common.http.param.Header;
import com.alibaba.nacos.common.utils.VersionUtils;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.propagation.TextMapSetter;

import java.util.Map;

public class TraceMonitor {
    
    private static final String NACOS_CLIENT_NAME = "Nacos-Java-Client";
    
    private static OpenTelemetry nacosOpenTelemetry = GlobalOpenTelemetry.get();
    
    private static Tracer tracer = nacosOpenTelemetry.getTracer(NACOS_CLIENT_NAME, VersionUtils.version);
    
    private static final TextMapSetter<Header> HTTP_CONTEXT_SETTER = (carrier, key, value) -> {
        assert carrier != null;
        carrier.addParam(key, value);
    };
    
    private static final TextMapSetter<Map<String, String>> RPC_CONTEXT_SETTER = (carrier, key, value) -> {
        assert carrier != null;
        carrier.put(key, value);
    };
    
    public static TextMapSetter<Header> getHttpContextSetter() {
        return HTTP_CONTEXT_SETTER;
    }
    
    public static TextMapSetter<Map<String, String>> getRpcContextSetter() {
        return RPC_CONTEXT_SETTER;
    }
    
    /**
     * Get the Nacos client OpenTelemetry tracer.
     *
     * @return the OpenTelemetry tracer
     */
    public static Tracer getTracer() {
        return tracer;
    }
    
    /**
     * Get the Nacos client OpenTelemetry instance.
     *
     * @return the OpenTelemetry instance
     */
    public static OpenTelemetry getOpenTelemetry() {
        return nacosOpenTelemetry;
    }
    
    /**
     * Set the Nacos client OpenTelemetry tracer with new OpenTelemetry instance.
     *
     * @param openTelemetry the OpenTelemetry instance
     */
    public static Tracer setTracer(OpenTelemetry openTelemetry) {
        return setTracer(openTelemetry, NACOS_CLIENT_NAME, VersionUtils.version);
    }
    
    /**
     * Set the Nacos client OpenTelemetry tracer with new OpenTelemetry instance, tracer name and version.
     *
     * @param openTelemetry the OpenTelemetry instance
     * @param name          the tracer name
     * @param version       the tracer version
     */
    public static Tracer setTracer(OpenTelemetry openTelemetry, String name, String version) {
        nacosOpenTelemetry = openTelemetry;
        tracer = setTracer(name, version);
        return tracer;
    }
    
    /**
     * Set the Nacos client OpenTelemetry tracer with new tracer name and version.
     *
     * @param name    the tracer name
     * @param version the tracer version
     */
    public static Tracer setTracer(String name, String version) {
        tracer = nacosOpenTelemetry.getTracer(name, version);
        return tracer;
    }
    
    /**
     * Set the Nacos client OpenTelemetry instance.
     *
     * @param openTelemetry the OpenTelemetry instance
     */
    public static void setOpenTelemetry(OpenTelemetry openTelemetry) {
        setTracer(openTelemetry);
    }
    
    /**
     * Reset the Nacos client OpenTelemetry instance to global. Then reset the tracer.
     */
    public static void resetOpenTelemetryAndTrace() {
        nacosOpenTelemetry = GlobalOpenTelemetry.get();
        tracer = nacosOpenTelemetry.getTracer(NACOS_CLIENT_NAME, VersionUtils.version);
    }
}