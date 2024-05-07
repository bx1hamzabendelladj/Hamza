/*
 * Copyright 1999-2021 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.client.auth.impl;

import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.common.http.HttpRestResult;
import com.alibaba.nacos.common.http.client.NacosRestTemplate;
import com.alibaba.nacos.common.http.param.Header;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NacosClientAuthServiceImplTest {
    
    @Test
    void loginSuccess() throws Exception {
        //given
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"accessToken\":\"ttttttttttttttttt\",\"tokenTtl\":1000}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        //when
        boolean ret = nacosClientAuthService.login(properties);
        //then
        assertTrue(ret);
    }
    
    @Test
    void testLoginFailCode() throws Exception {
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setCode(400);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        boolean ret = nacosClientAuthService.login(properties);
        assertFalse(ret);
    }
    
    @Test
    void testLoginFailHttp() throws Exception {
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenThrow(new Exception());
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        boolean ret = nacosClientAuthService.login(properties);
        assertFalse(ret);
        
    }
    
    @Test
    void testLoginServerListSuccess() throws Exception {
        //given
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"accessToken\":\"ttttttttttttttttt\",\"tokenTtl\":1000}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        boolean ret = nacosClientAuthService.login(properties);
        assertTrue(ret);
    }
    
    @Test
    void testLoginServerListLoginInWindow() throws Exception {
        //given
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"accessToken\":\"ttttttttttttttttt\",\"tokenTtl\":1000}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        //when
        nacosClientAuthService.login(properties);
        //then
        boolean ret = nacosClientAuthService.login(properties);
        assertTrue(ret);
        
    }
    
    @Test
    void getAccessToken() throws Exception {
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"accessToken\":\"abc\",\"tokenTtl\":1000}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        //when
        assertTrue(nacosClientAuthService.login(properties));
        //then
        assertEquals("abc",
                nacosClientAuthService.getLoginIdentityContext(null).getParameter(NacosAuthLoginConstant.ACCESSTOKEN));
    }
    
    @Test
    void getAccessEmptyToken() throws Exception {
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"accessToken\":\"\",\"tokenTtl\":1000}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        //when
        assertTrue(nacosClientAuthService.login(properties));
        //then
        assertEquals("",
                nacosClientAuthService.getLoginIdentityContext(null).getParameter(NacosAuthLoginConstant.ACCESSTOKEN));
    }
    
    @Test
    void getAccessTokenWithoutToken() throws Exception {
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"tokenTtl\":1000}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        //when
        assertTrue(nacosClientAuthService.login(properties));
        //then
        assertNull(
                nacosClientAuthService.getLoginIdentityContext(null).getParameter(NacosAuthLoginConstant.ACCESSTOKEN));
    }
    
    @Test
    void getAccessTokenWithInvalidTtl() throws Exception {
        NacosRestTemplate nacosRestTemplate = mock(NacosRestTemplate.class);
        HttpRestResult<Object> result = new HttpRestResult<>();
        result.setData("{\"accessToken\":\"abc\",\"tokenTtl\":\"abc\"}");
        result.setCode(200);
        when(nacosRestTemplate.postForm(any(), (Header) any(), any(), any(), any())).thenReturn(result);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.USERNAME, "aaa");
        properties.setProperty(PropertyKeyConst.PASSWORD, "123456");
        
        List<String> serverList = new ArrayList<>();
        serverList.add("localhost");
        
        NacosClientAuthServiceImpl nacosClientAuthService = new NacosClientAuthServiceImpl();
        nacosClientAuthService.setServerList(serverList);
        nacosClientAuthService.setNacosRestTemplate(nacosRestTemplate);
        //when
        assertFalse(nacosClientAuthService.login(properties));
    }
}
