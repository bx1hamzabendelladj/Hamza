/*
 * Copyright 1999-2022 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.plugin.config.configuration;

import com.alibaba.nacos.plugin.config.ConfigChangePluginManager;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Config cahgne apsect EnableCondition.
 *
 * @author liyunfei
 */
public class AspectEnableCondition implements Condition {
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return ConfigChangePluginManager.findPluginServiceQueueByPointcut("import") != null
                || ConfigChangePluginManager.findPluginServiceQueueByPointcut("publish") != null
                || ConfigChangePluginManager.findPluginServiceQueueByPointcut("update") != null
                || ConfigChangePluginManager.findPluginServiceQueueByPointcut("remove") != null;
    }
}