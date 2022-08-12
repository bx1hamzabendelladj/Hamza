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

package com.alibaba.nacos.config.server.service;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.config.server.model.ConfigInfo;
import com.alibaba.nacos.config.server.model.vo.ConfigRequestInfoVo;
import com.alibaba.nacos.config.server.model.vo.ConfigVo;
import com.alibaba.nacos.config.server.service.repository.PersistService;
import com.alibaba.nacos.sys.env.EnvUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.StandardEnvironment;

import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

/**
 * ConfigServiceTest.
 *
 * @author dongyafei
 * @date 2022/8/11
 */

@RunWith(MockitoJUnitRunner.class)
public class ConfigServiceTest {
    
    private ConfigService configService;
    
    @Mock
    private PersistService persistService;
    
    @Before
    public void setUp() throws Exception {
        EnvUtil.setEnvironment(new StandardEnvironment());
        this.configService = new ConfigService(persistService);
    }
    
    @Test
    public void testPublishConfig() throws NacosException {
        ConfigVo configVo = new ConfigVo();
        configVo.setDataId("test");
        configVo.setGroup("test");
        configVo.setContent("test content");
        
        ConfigRequestInfoVo configRequestInfoVo = new ConfigRequestInfoVo();
        
        // if betaIps is blank and tag is blank
        Boolean aResult = configService.publishConfig(configVo, configRequestInfoVo, null, "", true);
        verify(persistService)
                .insertOrUpdate(any(), any(), any(ConfigInfo.class), any(Timestamp.class), any(), anyBoolean());
        Assert.assertEquals(true, aResult);
        
        // if betaIps is blank and tag is not blank
        configVo.setTag("test tag");
        Boolean bResult = configService.publishConfig(configVo, configRequestInfoVo, null, "", true);
        verify(persistService)
                .insertOrUpdateTag(any(ConfigInfo.class), eq("test tag"), any(), any(), any(Timestamp.class),
                        anyBoolean());
        Assert.assertEquals(true, bResult);
        
        // if betaIps is not blank
        configRequestInfoVo.setBetaIps("test-betaIps");
        Boolean cResult = configService.publishConfig(configVo, configRequestInfoVo, null, "", true);
        verify(persistService)
                .insertOrUpdateBeta(any(ConfigInfo.class), eq("test-betaIps"), any(), any(), any(Timestamp.class),
                        anyBoolean());
        Assert.assertEquals(true, cResult);
    }
    
    @Test
    public void testDeleteConfig() {
        
        // if tag is blank
        Boolean aResult = configService.deleteConfig("test", "test", "", "", "1.1.1.1", "test");
        verify(persistService).removeConfigInfo(eq("test"), eq("test"), eq(""), any(), any());
        Assert.assertEquals(true, aResult);
    
        // if tag is not blank
        Boolean bResult = configService.deleteConfig("test", "test", "", "test", "1.1.1.1", "test");
        verify(persistService).removeConfigInfoTag(eq("test"), eq("test"), eq(""), eq("test"), any(), any());
        Assert.assertEquals(true, bResult);
    }
}
