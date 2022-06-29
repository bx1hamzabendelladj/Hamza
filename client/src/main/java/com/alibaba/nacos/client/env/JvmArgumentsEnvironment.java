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

import java.util.Properties;

/**
 * jvm arguments environment.
 * @author onewe
 */
class JvmArgumentsEnvironment extends AbstractNacosEnvironment {
    
    private static final JvmArgumentsEnvironment INSTANCE = new JvmArgumentsEnvironment();
    
    private final Properties envs = System.getProperties();
    
    private JvmArgumentsEnvironment() {}
    
    public static JvmArgumentsEnvironment getInstance() {
        return INSTANCE;
    }
    
    @Override
    public EnvType getType() {
        return EnvType.JVM_ARGS;
    }
    
    @Override
    public String getProperty(String key) {
        return envs.getProperty(key);
    }
    
    @Override
    public void setProperty(String key, String value) {
        throw new UnsupportedOperationException("Unsupported set property operation!");
    }
    
    @Override
    public void addProperties(Properties properties) {
        throw new UnsupportedOperationException("Unsupported add properties operation!");
    }
    
    @Override
    public void removeProperty(String key) {
        throw new UnsupportedOperationException("Unsupported remove property operation!");
    }
    
    @Override
    public void clean() {
        throw new UnsupportedOperationException("Unsupported clean operation!");
    }
    
}
