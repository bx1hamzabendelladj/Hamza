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

package com.alibaba.nacos.client.ability;

import com.alibaba.nacos.api.ability.constant.AbilityKey;
import com.alibaba.nacos.api.ability.constant.AbilityStatus;
import com.alibaba.nacos.api.ability.entity.AbilityTable;
import com.alibaba.nacos.api.ability.register.impl.ClientAbilities;
import com.alibaba.nacos.common.ability.AbstractAbilityControlManager;
import com.alibaba.nacos.common.ability.DefaultAbilityControlManager;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**.
 * @author Daydreamer
 * @description {@link AbstractAbilityControlManager} for nacos-client.
 * @date 2022/7/13 13:38
 **/
public class ClientAbilityControlManager extends DefaultAbilityControlManager {
    
    public ClientAbilityControlManager() {
    }
    
    @Override
    protected Map<AbilityKey, Boolean> getCurrentNodeSupportAbility() {
        return ClientAbilities.getStaticAbilities();
    }
    
    @Override
    public AbilityStatus isSupport(String connectionId, AbilityKey abilityKey) {
        AbilityTable abilityTable = nodeAbilityTable.get(connectionId);
        if (abilityTable == null) {
            return AbilityStatus.UNKNOWN;
        }
        Boolean isSupport = Optional.ofNullable(abilityTable.getAbility())
                .orElse(Collections.emptyMap())
                .getOrDefault(abilityKey, false);
        return isSupport ? AbilityStatus.SUPPORTED : AbilityStatus.NOT_SUPPORTED;
    }
    
    @Override
    protected void add(AbilityTable table) {
        // nothing to do
    }
    
    @Override
    protected void remove(String connectionId) {
        // nothing to do
    }

}