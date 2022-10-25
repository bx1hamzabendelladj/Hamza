/*
 * Copyright 1999-2021 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.plugin.environment.impl;

import com.alibaba.nacos.plugin.environment.spi.CustomEnvironmentPluginService;

import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Nacos default auth plugin service implementation.
 *
 * @author huangtianhui
 */

public class NacosCustomEnvPluginService implements CustomEnvironmentPluginService {

    @Override
    public Map<String, Object> customValue(Map<String, Object> property) {
        String pwd = (String) property.get("db.password.0");
        byte[] decode = Base64.getDecoder().decode(pwd);
        property.put("db.password.0", new String(decode));
        return property;
    }

    @Override
    public Set<String> propertyKey() {
        Set<String> propertyKey = new HashSet<>();
        propertyKey.add("db.password.0");
        return propertyKey;
    }

    @Override
    public Integer order() {
        return 1;
    }

    @Override
    public String pluginName() {
        return "testCustomEnvironmentPluginService";
    }
}
