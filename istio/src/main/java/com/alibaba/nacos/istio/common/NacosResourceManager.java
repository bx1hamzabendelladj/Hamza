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

package com.alibaba.nacos.istio.common;

import com.alibaba.nacos.istio.misc.IstioConfig;
import com.alibaba.nacos.istio.model.IstioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * NacosResourceManager.
 *
 * @author special.fy
 */
@Component
public class NacosResourceManager {

    private ResourceSnapshot resourceSnapshot;

    @Autowired
    NacosServiceInfoResourceWatcher serviceInfoResourceWatcher;

    @Autowired
    private IstioConfig istioConfig;

    public NacosResourceManager() {
        resourceSnapshot = new ResourceSnapshot(istioConfig);
    }

    public Map<String, IstioService> services() {
        return serviceInfoResourceWatcher.snapshot();
    }

    public IstioConfig getIstioConfig() {
        return istioConfig;
    }

    public synchronized ResourceSnapshot getResourceSnapshot() {
        return resourceSnapshot;
    }

    public synchronized void setResourceSnapshot(ResourceSnapshot resourceSnapshot) {
        this.resourceSnapshot = resourceSnapshot;
    }

    public void initResourceSnapshot() {
        ResourceSnapshot resourceSnapshot = getResourceSnapshot();
        resourceSnapshot.initResourceSnapshot(this);
    }

    /**
     * description:snapshot.
     * @param: []
     * @return: com.alibaba.nacos.istio.common.ResourceSnapshot
     */
    public ResourceSnapshot createResourceSnapshot(Set<String> removedHostName, Set<String> removedClusterName,
            Set<String> updateService, Set<String> updateInstance) {
        ResourceSnapshot resourceSnapshot = new ResourceSnapshot(removedHostName, removedClusterName, updateService, updateInstance, istioConfig);
        resourceSnapshot.initResourceSnapshot(this);
        setResourceSnapshot(resourceSnapshot);
        return resourceSnapshot;
    }
}
