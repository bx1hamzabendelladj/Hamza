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

package com.alibaba.nacos.naming.pojo;

import com.alibaba.nacos.naming.core.Instance;

import java.util.List;

/**
 * InstanceOperationInfo. operation resources description
 *
 * @author horizonzy
 */
public class InstanceOperationInfo {
    
    public InstanceOperationInfo() {
    }
    
    public InstanceOperationInfo(String serviceName, String consistencyType, List<Instance> instances) {
        this.serviceName = serviceName;
        this.consistencyType = consistencyType;
        this.instances = instances;
    }
    
    /**
     * serverName.
     */
    private String serviceName;
    
    /**
     * ephemeral/persist/*.
     * <p>ephemeral = all ephemeral instances in {@link com.alibaba.nacos.naming.consistency.ephemeral.distro.DistroConsistencyServiceImpl}
     * persist = all persist instances in {@link com.alibaba.nacos.naming.consistency.persistent.raft.RaftConsistencyServiceImpl}
     * * = persist union ephemeral</p>
     */
    private String consistencyType;
    
    /**
     * instances which need operate.
     */
    private List<Instance> instances;
    
    public String getServiceName() {
        return serviceName;
    }
    
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    
    public String getConsistencyType() {
        return consistencyType;
    }
    
    public void setConsistencyType(String consistencyType) {
        this.consistencyType = consistencyType;
    }
    
    public List<Instance> getInstances() {
        return instances;
    }
    
    public void setInstances(List<Instance> instances) {
        this.instances = instances;
    }
    
}
