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
 *
 */

package com.alibaba.nacos.istio.xds;

import com.alibaba.nacos.istio.api.ApiGenerator;
import com.alibaba.nacos.istio.common.ResourceSnapshot;
import com.alibaba.nacos.istio.model.ServiceEntryWrapper;
import com.google.protobuf.Any;
import io.envoyproxy.envoy.config.cluster.v3.Cluster;
import io.envoyproxy.envoy.config.core.v3.AggregatedConfigSource;
import io.envoyproxy.envoy.config.core.v3.ConfigSource;
import io.envoyproxy.envoy.config.core.v3.Http1ProtocolOptions;
import io.envoyproxy.envoy.config.core.v3.Http2ProtocolOptions;
import io.envoyproxy.envoy.config.core.v3.TrafficDirection;
import istio.networking.v1alpha3.ServiceEntryOuterClass;

import java.util.ArrayList;
import java.util.List;

import static com.alibaba.nacos.istio.api.ApiConstants.CLUSTER_V3_TYPE;
import static com.alibaba.nacos.istio.util.IstioCrdUtil.buildClusterName;
import static io.envoyproxy.envoy.config.core.v3.ApiVersion.V2_VALUE;

/**
 * @author RocketEngine26
 * @date 2022/8/17 下午8:09
 */
public final class CdsV3Generator implements ApiGenerator<Any> {
    
    private static volatile CdsV3Generator singleton = null;
    
    public static CdsV3Generator getInstance() {
        if (singleton == null) {
            synchronized (ServiceEntryXdsGenerator.class) {
                if (singleton == null) {
                    singleton = new CdsV3Generator();
                }
            }
        }
        return singleton;
    }
    
    @Override
    public List<Any> generate(ResourceSnapshot resourceSnapshot) {
        List<Any> result = new ArrayList<>();
        List<ServiceEntryWrapper> serviceEntries = resourceSnapshot.getServiceEntries();
        
        for (ServiceEntryWrapper serviceEntryWrapper : serviceEntries) {
            ServiceEntryOuterClass.ServiceEntry serviceEntry = serviceEntryWrapper.getServiceEntry();
            
            int port = serviceEntry.getPorts(0).getNumber();
            boolean protocolFlag = serviceEntry.getEndpointsList().get(0).getPortsMap().containsKey("grpc");
            String name = buildClusterName(TrafficDirection.OUTBOUND, "", serviceEntry.getHosts(0), port);
            
            Cluster.Builder cluster = Cluster.newBuilder().setName(name).setType(Cluster.DiscoveryType.EDS)
                    .setEdsClusterConfig(Cluster.EdsClusterConfig.newBuilder().setServiceName(name).setEdsConfig(
                            ConfigSource.newBuilder().setAds(AggregatedConfigSource.newBuilder())
                                    .setResourceApiVersionValue(V2_VALUE).build()).build());
            if (protocolFlag) {
                cluster.setHttp2ProtocolOptions(Http2ProtocolOptions.newBuilder().build());
            } else {
                cluster.setHttpProtocolOptions(Http1ProtocolOptions.newBuilder().build());
            }
            
            result.add(Any.newBuilder().setValue(cluster.build().toByteString()).setTypeUrl(CLUSTER_V3_TYPE).build());
        }
        
        return result;
    }
}