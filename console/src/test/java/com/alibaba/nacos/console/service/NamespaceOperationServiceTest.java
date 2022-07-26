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

package com.alibaba.nacos.console.service;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.config.server.model.TenantInfo;
import com.alibaba.nacos.config.server.service.repository.PersistService;
import com.alibaba.nacos.console.model.Namespace;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * NamespaceOperationServiceTest.
 * @author dongyafei
 * @date 2022/8/16
 */
@RunWith(MockitoJUnitRunner.class)
public class NamespaceOperationServiceTest {
    
    private NamespaceOperationService namespaceOperationService;
    
    @Mock
    private PersistService persistService;
    
    private static final String TEST_NAMESPACE_ID = "testId";
    
    private static final String TEST_NAMESPACE_NAME = "testName";
    
    private static final String TEST_NAMESPACE_DESC = "testDesc";
    
    private static final String DEFAULT_NAMESPACE = "public";
    
    private static final int DEFAULT_QUOTA = 200;
    
    private static final String DEFAULT_KP = "1";
    
    @Before
    public void setUp() throws Exception {
        namespaceOperationService = new NamespaceOperationService(persistService);
    }
    
    @Test
    public void testGetNamespaceList() {
        TenantInfo tenantInfo = new TenantInfo();
        tenantInfo.setTenantId(TEST_NAMESPACE_ID);
        tenantInfo.setTenantName(TEST_NAMESPACE_NAME);
        tenantInfo.setTenantDesc(TEST_NAMESPACE_DESC);
        when(persistService.findTenantByKp(DEFAULT_KP)).thenReturn(Collections.singletonList(tenantInfo));
        when(persistService.configInfoCount(anyString())).thenReturn(1);
        
        List<Namespace> list = namespaceOperationService.getNamespaceList();
        assertEquals(2, list.size());
        Namespace namespaceA = list.get(0);
        assertEquals("", namespaceA.getNamespace());
        assertEquals(DEFAULT_NAMESPACE, namespaceA.getNamespaceShowName());
        assertEquals(DEFAULT_QUOTA, namespaceA.getQuota());
        assertEquals(1, namespaceA.getConfigCount());
        
        Namespace namespaceB = list.get(1);
        assertEquals(TEST_NAMESPACE_ID, namespaceB.getNamespace());
        assertEquals(TEST_NAMESPACE_NAME, namespaceB.getNamespaceShowName());
        assertEquals(1, namespaceB.getConfigCount());
    }
    
    @Test
    public void testCreateNamespace() throws NacosException {
        when(persistService.tenantInfoCountByTenantId(anyString())).thenReturn(0);
        namespaceOperationService.createNamespace(TEST_NAMESPACE_ID, TEST_NAMESPACE_NAME, TEST_NAMESPACE_DESC, true);
        verify(persistService)
                .insertTenantInfoAtomic(eq(DEFAULT_KP), eq(TEST_NAMESPACE_ID), eq(TEST_NAMESPACE_NAME), eq(TEST_NAMESPACE_DESC),
                        any(), anyLong());
    }
    
    @Test
    public void testEditNamespace() {
        namespaceOperationService.editNamespace(TEST_NAMESPACE_ID, TEST_NAMESPACE_NAME, TEST_NAMESPACE_DESC);
        verify(persistService).updateTenantNameAtomic(DEFAULT_KP, TEST_NAMESPACE_ID, TEST_NAMESPACE_NAME, TEST_NAMESPACE_DESC);
    }
    
    @Test
    public void testRemoveNamespace() {
        namespaceOperationService.removeNamespace(TEST_NAMESPACE_ID);
        verify(persistService).removeTenantInfoAtomic(DEFAULT_KP, TEST_NAMESPACE_ID);
    }
}
