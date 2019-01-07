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
package com.alibaba.nacos.api.naming.loadbalancer;



import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.pojo.ServiceInfo;


/**
 *
 * abstract User-defined implementation of LoadBalancer
 * @author XCXCXCXCX
 */
public interface LoadBalancer{

    /**
     * User implementation method is required
     * to complete the load balancing selection algorithm
     *
     * @param serviceInfo   It provides the user with the service information,
     *                       including the service name, current service list, etc.
     * @return One instance
     */
    Instance doChoose(ServiceInfo serviceInfo);

}
