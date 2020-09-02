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

package com.alibaba.nacos.api.remote.response;

/**
 * PlainBodyResponse.
 *
 * @author liuzunfei
 * @version $Id: PlainBodyResponse.java, v 0.1 2020年07月15日 1:37 PM liuzunfei Exp $
 */
public class PlainBodyResponse extends Response {
    
    private String bodyString;
    
    public PlainBodyResponse() {
    
    }
    
    public PlainBodyResponse(String bodyString) {
        this.bodyString = bodyString;
    }
    
    /**
     * Getter method for property <tt>bodyString</tt>.
     *
     * @return property value of bodyString
     */
    public String getBodyString() {
        return bodyString;
    }
    
    /**
     * Setter method for property <tt>bodyString</tt>.
     *
     * @param bodyString value to be assigned to property bodyString
     */
    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }
}
