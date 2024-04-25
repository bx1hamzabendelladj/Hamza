/*
 *   Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.alibaba.nacos.client.naming.utils;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.SystemPropertyKeyConst;
import com.alibaba.nacos.client.env.NacosClientProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InitUtilsTest {
    
    @AfterEach
    void tearDown() {
        System.clearProperty(SystemPropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING);
        System.clearProperty(SystemPropertyKeyConst.ANS_NAMESPACE);
        System.clearProperty(PropertyKeyConst.NAMESPACE);
        System.clearProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE);
        System.clearProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_ENDPOINT_URL);
        System.clearProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_ENDPOINT_PORT);
        UtilAndComs.webContext = "/nacos";
        UtilAndComs.nacosUrlBase = "/nacos/v1/ns";
        UtilAndComs.nacosUrlInstance = "/nacos/v1/ns/instance";
    }
    
    /**
     * current namespace priority 1. system.Properties 2. user.Properties 3. default value
     */
    @Test
    void initNamespaceForDefault() {
        //DEFAULT
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String actual = InitUtils.initNamespaceForNaming(properties);
        assertEquals(UtilAndComs.DEFAULT_NAMESPACE_ID, actual);
    }
    
    @Test
    void initNamespaceFromAnsWithCloudParsing() {
        String expect = "ans";
        System.setProperty(SystemPropertyKeyConst.ANS_NAMESPACE, expect);
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "true");
        String actual = InitUtils.initNamespaceForNaming(properties);
        assertEquals(expect, actual);
    }
    
    @Test
    void initNamespaceFromAliwareWithCloudParsing() {
        String expect = "aliware";
        System.setProperty(SystemPropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "true");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_NAMESPACE, expect);
        String actual = InitUtils.initNamespaceForNaming(properties);
        assertEquals(expect, actual);
    }
    
    @Test
    void initNamespaceFromJvmNamespaceWithCloudParsing() {
        String expect = "jvm_namespace";
        System.setProperty(PropertyKeyConst.NAMESPACE, expect);
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String ns = InitUtils.initNamespaceForNaming(properties);
        assertEquals(expect, ns);
    }
    
    @Test
    void initNamespaceFromPropNamespaceWithCloudParsing() {
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String expect = "ns1";
        properties.setProperty(PropertyKeyConst.NAMESPACE, expect);
        String ns = InitUtils.initNamespaceForNaming(properties);
        assertEquals(expect, ns);
    }
    
    @Test
    void initNamespaceFromDefaultNamespaceWithCloudParsing() {
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "true");
        String actual = InitUtils.initNamespaceForNaming(properties);
        assertEquals(UtilAndComs.DEFAULT_NAMESPACE_ID, actual);
    }
    
    @Test
    void initNamespaceFromJvmNamespaceWithoutCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.ANS_NAMESPACE, "ans");
        String expect = "jvm_namespace";
        System.setProperty(PropertyKeyConst.NAMESPACE, expect);
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "false");
        String ns = InitUtils.initNamespaceForNaming(properties);
        assertEquals(expect, ns);
    }
    
    @Test
    void initNamespaceFromPropNamespaceWithoutCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.ANS_NAMESPACE, "ans");
        System.setProperty(SystemPropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "false");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String expect = "ns1";
        properties.setProperty(PropertyKeyConst.NAMESPACE, expect);
        String ns = InitUtils.initNamespaceForNaming(properties);
        assertEquals(expect, ns);
    }
    
    @Test
    void initNamespaceFromDefaultNamespaceWithoutCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.ANS_NAMESPACE, "ans");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.IS_USE_CLOUD_NAMESPACE_PARSING, "false");
        String actual = InitUtils.initNamespaceForNaming(properties);
        assertEquals(UtilAndComs.DEFAULT_NAMESPACE_ID, actual);
    }
    
    @Test
    void initWebRootContext() {
        String ctx = "/aaa";
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.CONTEXT_PATH, ctx);
        InitUtils.initWebRootContext(properties);
        assertEquals(ctx, UtilAndComs.webContext);
        assertEquals(ctx + "/v1/ns", UtilAndComs.nacosUrlBase);
        assertEquals(ctx + "/v1/ns/instance", UtilAndComs.nacosUrlInstance);
    }
    
    @Test
    void initWebRootContextWithoutValue() {
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        InitUtils.initWebRootContext(properties);
        assertEquals("/nacos", UtilAndComs.webContext);
        assertEquals("/nacos/v1/ns", UtilAndComs.nacosUrlBase);
        assertEquals("/nacos/v1/ns/instance", UtilAndComs.nacosUrlInstance);
    }
    
    @Test
    void initEndpointForNullProperties() {
        assertEquals("", InitUtils.initEndpoint(null));
    }
    
    @Test
    void initEndpointFromDefaultWithoutCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "false");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String actual = InitUtils.initEndpoint(properties);
        assertEquals("", actual);
    }
    
    @Test
    void initEndpointFromPropertiesWithoutCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "false");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String endpoint = "1.1.1.1";
        String endpointPort = "1234";
        properties.setProperty(PropertyKeyConst.ENDPOINT, endpoint);
        properties.setProperty(PropertyKeyConst.ENDPOINT_PORT, endpointPort);
        String actual = InitUtils.initEndpoint(properties);
        assertEquals(endpoint + ":" + endpointPort, actual);
    }
    
    @Test
    void initEndpointFromAliwareWithoutCloudParsing() {
        String endpoint = "aliware_endpoint";
        String endpointPort = "1234";
        System.setProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "false");
        System.setProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_ENDPOINT_URL, endpoint);
        System.setProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_ENDPOINT_PORT, endpointPort);
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.ENDPOINT_PORT, endpointPort + "1");
        String actual = InitUtils.initEndpoint(properties);
        assertEquals("", actual);
    }
    
    @Test
    void initEndpointFromDefaultWithCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "true");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String actual = InitUtils.initEndpoint(properties);
        assertEquals("", actual);
    }
    
    @Test
    void initEndpointFromPropertiesWithCloudParsing() {
        System.setProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "true");
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        String endpoint = "1.1.1.1";
        String endpointPort = "1234";
        properties.setProperty(PropertyKeyConst.ENDPOINT, endpoint);
        properties.setProperty(PropertyKeyConst.ENDPOINT_PORT, endpointPort);
        String actual = InitUtils.initEndpoint(properties);
        assertEquals(endpoint + ":" + endpointPort, actual);
    }
    
    @Test
    void initEndpointFromAliwareWithCloudParsing() {
        String endpoint = "aliware_endpoint";
        String endpointPort = "1234";
        System.setProperty(SystemPropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "true");
        System.setProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_ENDPOINT_URL, endpoint);
        System.setProperty(PropertyKeyConst.SystemEnv.ALIBABA_ALIWARE_ENDPOINT_PORT, endpointPort);
        final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
        properties.setProperty(PropertyKeyConst.ENDPOINT_PORT, endpointPort + "1");
        String actual = InitUtils.initEndpoint(properties);
        assertEquals(endpoint + ":" + endpointPort, actual);
    }
    
    @Test
    void initEndpointAns() {
        try {
            System.setProperty(PropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE, "true");
            final NacosClientProperties properties = NacosClientProperties.PROTOTYPE.derive();
            String endpoint = "${key:test.com}";
            properties.setProperty(PropertyKeyConst.ENDPOINT, endpoint);
            String actual = InitUtils.initEndpoint(properties);
            //defaultEndpointPort is  "8080";
            assertEquals("test.com:8080", actual);
        } finally {
            System.clearProperty(PropertyKeyConst.IS_USE_ENDPOINT_PARSING_RULE);
        }
    }
    
}