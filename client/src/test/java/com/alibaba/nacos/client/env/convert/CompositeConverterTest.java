/*
 * Copyright 1999-2023 Alibaba Group Holding Ltd.
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

package com.alibaba.nacos.client.env.convert;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.MissingFormatArgumentException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeConverterTest {
    
    CompositeConverter compositeConverter;
    
    @BeforeEach
    void setUp() throws Exception {
        compositeConverter = new CompositeConverter();
    }
    
    @AfterEach
    void tearDown() throws Exception {
    }
    
    @Test
    void convertNotSupportType() {
        assertThrows(MissingFormatArgumentException.class, () -> {
            compositeConverter.convert("test", CompositeConverter.class);
        });
    }
    
    @Test
    void convertBooleanForEmptyProperty() {
        assertNull(compositeConverter.convert(null, Boolean.class));
    }
    
    @Test
    void convertBooleanTrue() {
        assertTrue(compositeConverter.convert("true", Boolean.class));
        assertTrue(compositeConverter.convert("on", Boolean.class));
        assertTrue(compositeConverter.convert("yes", Boolean.class));
        assertTrue(compositeConverter.convert("1", Boolean.class));
    }
    
    @Test
    void convertBooleanFalse() {
        assertFalse(compositeConverter.convert("false", Boolean.class));
        assertFalse(compositeConverter.convert("off", Boolean.class));
        assertFalse(compositeConverter.convert("no", Boolean.class));
        assertFalse(compositeConverter.convert("0", Boolean.class));
    }
    
    @Test
    void convertBooleanIllegal() {
        assertThrows(IllegalArgumentException.class, () -> {
            compositeConverter.convert("aaa", Boolean.class);
        });
    }
    
    @Test
    void convertIntegerForEmptyProperty() {
        assertNull(compositeConverter.convert(null, Integer.class));
    }
    
    @Test
    void convertInteger() {
        assertEquals(100, (int) compositeConverter.convert("100", Integer.class));
        assertEquals(Integer.MAX_VALUE,
                (int) compositeConverter.convert(String.valueOf(Integer.MAX_VALUE), Integer.class));
        assertEquals(Integer.MIN_VALUE,
                (int) compositeConverter.convert(String.valueOf(Integer.MIN_VALUE), Integer.class));
    }
    
    @Test
    void convertIntegerIllegal() {
        assertThrows(IllegalArgumentException.class, () -> {
            compositeConverter.convert("aaa", Integer.class);
        });
    }
    
    @Test
    void convertLongForEmptyProperty() {
        assertNull(compositeConverter.convert(null, Long.class));
    }
    
    @Test
    void convertLong() {
        assertEquals(100L, (long) compositeConverter.convert("100", Long.class));
        assertEquals(Long.MAX_VALUE, (long) compositeConverter.convert(String.valueOf(Long.MAX_VALUE), Long.class));
        assertEquals(Long.MIN_VALUE, (long) compositeConverter.convert(String.valueOf(Long.MIN_VALUE), Long.class));
    }
    
    @Test
    void convertLongIllegal() {
        assertThrows(IllegalArgumentException.class, () -> {
            compositeConverter.convert("aaa", Long.class);
        });
    }
}