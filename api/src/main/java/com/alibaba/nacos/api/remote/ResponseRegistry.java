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

package com.alibaba.nacos.api.remote;

import com.alibaba.nacos.api.config.remote.response.ConfigChangeListenResponse;
import com.alibaba.nacos.api.config.remote.response.ConfigChangeNotifyResponse;
import com.alibaba.nacos.api.config.remote.response.ConfigQueryResponse;
import com.alibaba.nacos.api.config.remote.response.ConfigResponseTypeConstants;
import com.alibaba.nacos.api.naming.remote.NamingRequestTypeConstants;
import com.alibaba.nacos.api.remote.response.ConnectResetResponse;
import com.alibaba.nacos.api.remote.response.HeartBeatResponse;
import com.alibaba.nacos.api.remote.response.ResponseTypeConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * ResponseRegistry.
 *
 * @author liuzunfei
 * @version $Id: ResponseRegistry.java, v 0.1 2020年07月15日 12:43 PM liuzunfei Exp $
 */
public class ResponseRegistry {
    
    private static final Map<String, Class> REGISTRY_RESPONSES = new HashMap<String, Class>();
    
    static {
        
        //internal response regitry
        REGISTRY_RESPONSES.put(ResponseTypeConstants.HEART_BEAT, HeartBeatResponse.class);
        REGISTRY_RESPONSES.put(ResponseTypeConstants.CONNECT_SWITCH, ConnectResetResponse.class);
        
        //config response registry
        REGISTRY_RESPONSES.put(ConfigResponseTypeConstants.CONFIG_CHANGE, ConfigChangeListenResponse.class);
        REGISTRY_RESPONSES.put(ConfigResponseTypeConstants.CONFIG_CHANGE_NOTIFY, ConfigChangeNotifyResponse.class);
        REGISTRY_RESPONSES.put(ConfigResponseTypeConstants.CONFIG_QUERY, ConfigQueryResponse.class);
        
        //naming response registry
        //REGISTRY_RESPONSES.put(NamingRequestTypeConstants.SERVICE_INSTANCE_CHANGE, ServiceI.class);
    }
    
    public static Class getClassByType(String type) {
        return REGISTRY_RESPONSES.get(type);
    }
    
}