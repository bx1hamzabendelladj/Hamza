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

// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: nacos_grpc_service.proto

package com.alibaba.nacos.api.grpc;

public interface GrpcResponseOrBuilder extends
        // @@protoc_insertion_point(interface_extends:GrpcResponse)
        com.google.protobuf.MessageOrBuilder {
  
  /**
   * <code>int32 code = 1;</code>
   */
  int getCode();
  
  /**
   * <pre>
   * reponse body
   * </pre>
   *
   * <code>.google.protobuf.Any body = 2;</code>
   */
  boolean hasBody();
  
  /**
   * <pre>
   * reponse body
   * </pre>
   *
   * <code>.google.protobuf.Any body = 2;</code>
   */
  com.google.protobuf.Any getBody();
  
  /**
   * <pre>
   * reponse body
   * </pre>
   *
   * <code>.google.protobuf.Any body = 2;</code>
   */
  com.google.protobuf.AnyOrBuilder getBodyOrBuilder();
  
  /**
   * <code>string type = 3;</code>
   */
  String getType();
  
  /**
   * <code>string type = 3;</code>
   */
  com.google.protobuf.ByteString getTypeBytes();
}
