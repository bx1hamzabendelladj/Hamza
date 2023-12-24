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

package com.alibaba.nacos.common.remote.client;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * RpcConstants.
 *
 * @author githubcheng2978.
 */
public class RpcConstants {

    public static final String NACOS_CLIENT_RPC = "nacos.remote.client.rpc";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_ENABLE = NACOS_CLIENT_RPC + ".tls.enable";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_PROVIDER = NACOS_CLIENT_RPC + ".tls.provider";

    @RpcConfigLabel
    public static final String RPC_CLIENT_MUTUAL_AUTH = NACOS_CLIENT_RPC + ".tls.mutualAuth";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_PROTOCOLS = NACOS_CLIENT_RPC + ".tls.protocols";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_CIPHERS = NACOS_CLIENT_RPC + ".tls.ciphers";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_CERT_CHAIN_PATH = NACOS_CLIENT_RPC + ".tls.certChainFile";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_CERT_KEY = NACOS_CLIENT_RPC + ".tls.certPrivateKey";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_TRUST_PWD = NACOS_CLIENT_RPC + ".tls.certPrivateKeyPassword";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_TRUST_COLLECTION_CHAIN_PATH =
            NACOS_CLIENT_RPC + ".tls.trustCollectionChainPath";

    @RpcConfigLabel
    public static final String RPC_CLIENT_TLS_TRUST_ALL = NACOS_CLIENT_RPC + ".tls.trustAll";

    private static final Set<String> CONFIG_NAMES = new HashSet<>();

    public static final String NACOS_CLUSTER_CLIENT_RPC = "nacos.remote.client.rpc";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_ENABLE = NACOS_CLUSTER_CLIENT_RPC + ".tls.enable";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_PROVIDER = NACOS_CLUSTER_CLIENT_RPC + ".tls.provider";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_MUTUAL_AUTH = NACOS_CLUSTER_CLIENT_RPC + ".tls.mutualAuth";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_PROTOCOLS = NACOS_CLUSTER_CLIENT_RPC + ".tls.protocols";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_CIPHERS = NACOS_CLUSTER_CLIENT_RPC + ".tls.ciphers";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_CERT_CHAIN_PATH = NACOS_CLUSTER_CLIENT_RPC + ".tls.certChainFile";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_CERT_KEY = NACOS_CLUSTER_CLIENT_RPC + ".tls.certPrivateKey";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_TRUST_PWD =
            NACOS_CLUSTER_CLIENT_RPC + ".tls.certPrivateKeyPassword";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_TRUST_COLLECTION_CHAIN_PATH =
            NACOS_CLUSTER_CLIENT_RPC + ".tls.trustCollectionChainPath";

    @RpcClusterConfigLabel
    public static final String RPC_CLUSTER_CLIENT_TLS_TRUST_ALL = NACOS_CLUSTER_CLIENT_RPC + ".tls.trustAll";

    private static final Set<String> CLUSTER_CONFIG_NAMES = new HashSet<>();

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface RpcConfigLabel {

    }

    static {
        Field[] declaredFields = RpcConstants.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.getType().equals(String.class) && null != declaredField.getAnnotation(
                    RpcConfigLabel.class)) {
                try {
                    CONFIG_NAMES.add((String) declaredField.get(null));
                } catch (IllegalAccessException ignored) {
                }
            }
        }

        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            if (declaredField.getType().equals(String.class) && null != declaredField.getAnnotation(
                    RpcClusterConfigLabel.class)) {
                try {
                    CLUSTER_CONFIG_NAMES.add((String) declaredField.get(null));
                } catch (IllegalAccessException ignored) {
                }
            }
        }
    }

    public static Set<String> getClusterRpcParams() {
        return Collections.unmodifiableSet(CONFIG_NAMES);
    }

    public static Set<String> getRpcParams() {
        return Collections.unmodifiableSet(CONFIG_NAMES);
    }

    @Documented
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    protected @interface RpcClusterConfigLabel {

    }
}
