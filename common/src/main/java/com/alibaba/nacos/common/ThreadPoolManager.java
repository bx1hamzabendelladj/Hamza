/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.nacos.common;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * For unified management of thread pool resources, the consumer can simply call
 * the register method to {@link ThreadPoolManager#register(String, String, ExecutorService)}
 * the thread pool that needs to be included in the life cycle management of the resource
 *
 * @author <a href="mailto:liaochuntao@live.com">liaochuntao</a>
 */
public final class ThreadPoolManager {

    private Map<String, Map<String, Set<ExecutorService>>> resourcesManager;

    private Map<String, Object> lockers = new ConcurrentHashMap<String, Object>(8);

    private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();

    static {
        INSTANCE.init();
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                INSTANCE.destroyAll();
            }
        }));
    }

    public static ThreadPoolManager getInstance() {
        return INSTANCE;
    }

    private ThreadPoolManager() {}

    private void init() {
        resourcesManager = new ConcurrentHashMap<String, Map<String, Set<ExecutorService>>>(8);
    }

	/**
	 * Register the thread pool resources with the resource manager
	 *
     * @param biz business name
	 * @param resourceName resource name
	 * @param executor {@link ExecutorService}
	 */
	public synchronized void register(String biz, String resourceName, ExecutorService executor) {
        if (!resourcesManager.containsKey(biz)) {
            resourcesManager.put(biz, new ConcurrentHashMap<String, Set<ExecutorService>>(8));
            lockers.put(biz, new Object());
        }
        resourcesManager.get(biz).get(resourceName).add(executor);
    }

	/**
	 * Cancel the uniform lifecycle management for all threads under this resource
	 *
     * @param biz business name
	 * @param resourceName resource name
	 */
	public synchronized void deregister(String biz, String resourceName) {
        if (resourcesManager.containsKey(biz)) {
            resourcesManager.get(biz).remove(resourceName);
        }
    }

	/**
	 * Undoing the uniform lifecycle management of {@link ExecutorService} under this resource
	 *
     * @param biz business name
	 * @param resourceName resource name
	 * @param executor {@link ExecutorService}
	 */
	public synchronized void deregister(String biz, String resourceName, ExecutorService executor) {
        if (resourcesManager.containsKey(biz)) {
            final Map<String, Set<ExecutorService>> subResourceMap = resourcesManager.get(biz);
            if (subResourceMap.containsKey(resourceName)) {
                subResourceMap.get(resourceName).remove(executor);
            }
        }
    }

    public synchronized void destroy(String biz) {
	    final Object monitor = lockers.get(biz);
	    if (monitor == null) {
	        return;
        }
	    synchronized (monitor) {
	        Map<String, Set<ExecutorService>> subResource = resourcesManager.get(biz);
	        for (Map.Entry<String, Set<ExecutorService>> entry : subResource.entrySet()) {
	            for (ExecutorService executor : entry.getValue()) {
	                executor.shutdown();
	                int retry = 3;
	                while (retry > 0) {
	                    retry --;
	                    try {
	                        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
	                            executor.shutdownNow();
                            }
                        } catch (Exception e) {
                            executor.shutdownNow();
                        }
                    }
                }
            }
        }
    }

    private void destroyAll() {
	    Set<String> bizs = resourcesManager.keySet();
	    for (String biz : bizs) {
	        destroy(biz);
        }
    }
}
