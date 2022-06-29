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

package com.alibaba.nacos.client.env;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * user customizable env.
 *
 * @author onewe
 */
class UserCustomizableEnvironment extends AbstractNacosEnvironment {
    
    private static final UserCustomizableEnvironment INSTANCE = new UserCustomizableEnvironment();
    
    private final Map<String, String> envs = new ConcurrentHashMap<>();
    
    private UserCustomizableEnvironment() {
    }
    
    public static UserCustomizableEnvironment getInstance() {
        return INSTANCE;
    }
    
    @Override
    public EnvType getType() {
        return EnvType.USER_CUSTOMIZABLE;
    }
    
    @Override
    public String getProperty(String key) {
        return envs.get(key);
    }
    
    @Override
    public void setProperty(String key, String value) {
        envs.put(key, value);
    }
    
    @Override
    public void addProperties(Properties properties) {
        if (properties == null) {
            return;
        }
        
        for (String key : properties.stringPropertyNames()) {
            this.envs.put(key, properties.getProperty(key));
        }
        
    }
    
    @Override
    public void removeProperty(String key) {
        this.envs.remove(key);
    }
    
    @Override
    public void clean() {
        this.envs.clear();
    }
}
