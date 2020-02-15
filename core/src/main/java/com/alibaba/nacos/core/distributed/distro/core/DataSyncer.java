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

package com.alibaba.nacos.core.distributed.distro.core;

import com.alibaba.nacos.core.cluster.Node;
import com.alibaba.nacos.core.cluster.NodeManager;
import com.alibaba.nacos.core.distributed.distro.AbstractDistroKVStore;
import com.alibaba.nacos.core.executor.ExecutorFactory;
import com.alibaba.nacos.core.executor.NameThreadFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 */
public class DataSyncer {

    private final NodeManager nodeManager;

    private final Map<String, AbstractDistroKVStore> distroStores;

    private final DistroClient distroClient;

    private final Map<String, String> taskMap = new ConcurrentHashMap<>();

    private ScheduledExecutorService dataSyncExecutor;

    public DataSyncer(NodeManager nodeManager,
                      Map<String, AbstractDistroKVStore> distroStores,
                      DistroClient distroClient) {
        this.nodeManager = nodeManager;
        this.distroStores = distroStores;
        this.distroClient = distroClient;
    }

    public void start() {
        this.dataSyncExecutor = ExecutorFactory.newScheduledExecutorService(
                DataSyncer.class.getCanonicalName(),
                Runtime.getRuntime().availableProcessors(),
                new NameThreadFactory("com.alibaba.nacos.naming.distro.data.syncer"));

    }

    public void submit(SyncTask task, long delay) {
        // If it's a new task:
        if (task.getRetryCount() == 0) {
            // associated key already exist:
            // TODO log debug
            task.getKeys().removeIf(key -> StringUtils.isNotBlank(taskMap.putIfAbsent(buildKey(key, task.getTargetServer()), key)));
        }

        if (task.getKeys().isEmpty()) {
            // all keys are removed:
            return;
        }

        distroStores.forEach((s, distroStore) -> dataSyncExecutor.schedule(() -> {
            // 1. check the server
            if (getServers() == null || getServers().isEmpty()) {
                return;
            }

            List<String> keys = task.getKeys();


            // 2. get the datums by keys and check the datum is empty or not
            Map<String, byte[]> datumMap = distroStore.batchGet(keys);
            if (datumMap.isEmpty()) {
                // clear all flags of this task:
                for (String key : keys) {
                    taskMap.remove(buildKey(key, task.getTargetServer()));
                }
                return;
            }

            long timestamp = System.currentTimeMillis();
            boolean success = distroClient.syncData(datumMap, task.getTargetServer());
            if (success) {
                // clear all flags of this task:
                for (String key : task.getKeys()) {
                    taskMap.remove(buildKey(key, task.getTargetServer()));
                }
            } else {
                SyncTask syncTask = new SyncTask();
                syncTask.setBizInfo(distroStore.biz());
                syncTask.setKeys(task.getKeys());
                syncTask.setRetryCount(task.getRetryCount() + 1);
                syncTask.setLastExecuteTime(timestamp);
                syncTask.setTargetServer(task.getTargetServer());
                retrySync(syncTask);
            }
        }, delay, TimeUnit.MILLISECONDS));
    }

    public void retrySync(SyncTask syncTask) {

        if (!nodeManager.hasNode(syncTask.getTargetServer())) {
            // if server is no longer in healthy server list, ignore this task:
            //fix #1665 remove existing tasks
            if (syncTask.getKeys() != null) {
                for (String key : syncTask.getKeys()) {
                    taskMap.remove(buildKey(key, syncTask.getTargetServer()));
                }
            }
            return;
        }

        // TODO may choose other retry policy.
        submit(syncTask, 2000);
    }

    public List<Node> getServers() {
        return nodeManager.allNodes();
    }

    public void shutdown() {

    }

    private String buildKey(String key, String targetServer) {
        return key + "@@@@" + targetServer;
    }

}
