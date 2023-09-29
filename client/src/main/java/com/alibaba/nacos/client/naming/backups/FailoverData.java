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
package com.alibaba.nacos.client.naming.backups;

/**
 * Failover Data.
 *
 * @author zongkang.guo
 */
public class FailoverData {

    /**
     * failover type,naming or config.
     */
    private DataType dataType;

    /**
     * failover data.
     */
    private Object data;

    public FailoverData(DataType dataType, Object data) {
        this.data = data;
        this.dataType = dataType;
    }

    public enum DataType {
        /**
         * naming.
         */
        naming,
        /**
         * config.
         */
        config
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
