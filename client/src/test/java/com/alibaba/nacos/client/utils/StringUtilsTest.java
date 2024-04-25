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

package com.alibaba.nacos.client.utils;

import com.alibaba.nacos.common.utils.StringUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StringUtilsTest {
    
    @Test
    void isNotBlank() {
        assertTrue(StringUtils.isNotBlank("foo"));
        
        assertFalse(StringUtils.isNotBlank(" "));
        assertFalse(StringUtils.isNotBlank(null));
    }
    
    @Test
    void isNotEmpty() {
        assertFalse(StringUtils.isNotEmpty(""));
        
        assertTrue(StringUtils.isNotEmpty("foo"));
    }
    
    @Test
    void defaultIfEmpty() {
        assertEquals("foo", StringUtils.defaultIfEmpty("", "foo"));
        assertEquals("bar", StringUtils.defaultIfEmpty("bar", "foo"));
    }
    
    @Test
    void equals() {
        assertTrue(StringUtils.equals("foo", "foo"));
        
        assertFalse(StringUtils.equals("bar", "foo"));
        assertFalse(StringUtils.equals(" ", "foo"));
        assertFalse(StringUtils.equals("foo", null));
    }
    
    @Test
    void substringBetween() {
        assertNull(StringUtils.substringBetween(null, null, null));
        assertNull(StringUtils.substringBetween("", "foo", ""));
        assertNull(StringUtils.substringBetween("foo", "bar", "baz"));
        
        assertEquals("", StringUtils.substringBetween("foo", "foo", ""));
    }
    
    @Test
    void join() {
        assertNull(StringUtils.join(null, ""));
        
        Collection collection = new ArrayList();
        collection.add("foo");
        collection.add("bar");
        assertEquals("foo,bar", StringUtils.join(collection, ","));
    }
}
