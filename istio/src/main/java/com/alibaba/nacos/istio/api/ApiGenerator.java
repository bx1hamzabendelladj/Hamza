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

package com.alibaba.nacos.istio.api;

import com.alibaba.nacos.istio.model.PushContext;
import io.envoyproxy.envoy.service.discovery.v3.Resource;

import java.util.List;
import java.util.Set;

/**
 * This interface is used to generator mcp resources or xds data.
 *
 * @author special.fy
 */
public interface ApiGenerator<T> {

    /**
     * Generate data based on resource snapshot.
     *
     * @param pushContext Push Context
     * @return data
     */
    List<T> generate(PushContext pushContext);
    
    List<Resource> deltaGenerate(PushContext pushContext, Set<String> removed);
}
