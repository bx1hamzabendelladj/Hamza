/*
 * Copyright 1999-2020 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.naming.core.v2.client.manager.impl;

import com.alibaba.nacos.naming.core.v2.client.Client;
import com.alibaba.nacos.naming.core.v2.client.impl.IpPortBasedClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersistentIpPortClientManagerTest {
    
    private PersistentIpPortClientManager persistentIpPortClientManager;
    
    @Mock
    private IpPortBasedClient client;
    
    @Mock
    private IpPortBasedClient snapshotClient;
    
    private final String persistentIpPortId = "127.0.0.1:80#true";
    
    @Before
    public void setUp() throws Exception {
        persistentIpPortClientManager = new PersistentIpPortClientManager();
        when(client.getClientId()).thenReturn(persistentIpPortId);
        persistentIpPortClientManager.clientConnected(client);
    }
    
    @Test
    public void testGetClient() {
        Client fetchedClient = persistentIpPortClientManager.getClient(persistentIpPortId);
        assertEquals(fetchedClient, client);
    }
    
    @Test
    public void testAllClientId() {
        Collection<String> allClientIds = persistentIpPortClientManager.allClientId();
        assertEquals(1, allClientIds.size());
        assertTrue(allClientIds.contains(persistentIpPortId));
    }
    
    @Test
    public void testContainsPersistentIpPortId() {
        assertTrue(persistentIpPortClientManager.contains(persistentIpPortId));
        String unUsedClientId = "127.0.0.1:8888#true";
        assertFalse(persistentIpPortClientManager.contains(unUsedClientId));
    }
    
    @Test
    public void testLoadFromSnapshot() {
        String snapshotPersistentIpPortId = "127.0.0.1:8080#true";
        ConcurrentMap<String, IpPortBasedClient> snapshotClients = new ConcurrentHashMap<>();
        snapshotClients.put(snapshotPersistentIpPortId, snapshotClient);
        persistentIpPortClientManager.loadFromSnapshot(snapshotClients);
        Collection<String> allClientIds = persistentIpPortClientManager.allClientId();
        assertEquals(allClientIds.size(), 1);
        assertTrue(allClientIds.contains(snapshotPersistentIpPortId));
        assertFalse(allClientIds.contains(persistentIpPortId));
    }
}