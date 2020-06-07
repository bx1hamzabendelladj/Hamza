// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Data.proto

package com.alibaba.nacos.consistency.entity;

/**
 * Protobuf type {@code Response}
 */
public  final class Response extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:Response)
    ResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use Response.newBuilder() to construct.
  private Response(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private Response() {
    data_ = com.google.protobuf.ByteString.EMPTY;
    errMsg_ = "";
  }

  @Override
  @SuppressWarnings({"unused"})
  protected Object newInstance(
      UnusedPrivateParameter unused) {
    return new Response();
  }

  @Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private Response(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {

            data_ = input.readBytes();
            break;
          }
          case 18: {
            String s = input.readStringRequireUtf8();

            errMsg_ = s;
            break;
          }
          case 24: {

            success_ = input.readBool();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return Data.internal_static_Response_descriptor;
  }

  @Override
  protected FieldAccessorTable
      internalGetFieldAccessorTable() {
    return Data.internal_static_Response_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            Response.class, Response.Builder.class);
  }

  public static final int DATA_FIELD_NUMBER = 1;
  private com.google.protobuf.ByteString data_;
  /**
   * <code>bytes data = 1;</code>
   */
  public com.google.protobuf.ByteString getData() {
    return data_;
  }

  public static final int ERRMSG_FIELD_NUMBER = 2;
  private volatile Object errMsg_;
  /**
   * <code>string errMsg = 2;</code>
   */
  public String getErrMsg() {
    Object ref = errMsg_;
    if (ref instanceof String) {
      return (String) ref;
    } else {
      com.google.protobuf.ByteString bs =
          (com.google.protobuf.ByteString) ref;
      String s = bs.toStringUtf8();
      errMsg_ = s;
      return s;
    }
  }
  /**
   * <code>string errMsg = 2;</code>
   */
  public com.google.protobuf.ByteString
      getErrMsgBytes() {
    Object ref = errMsg_;
    if (ref instanceof String) {
      com.google.protobuf.ByteString b =
          com.google.protobuf.ByteString.copyFromUtf8(
              (String) ref);
      errMsg_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int SUCCESS_FIELD_NUMBER = 3;
  private boolean success_;
  /**
   * <code>bool success = 3;</code>
   */
  public boolean getSuccess() {
    return success_;
  }

  private byte memoizedIsInitialized = -1;
  @Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!data_.isEmpty()) {
      output.writeBytes(1, data_);
    }
    if (!getErrMsgBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, errMsg_);
    }
    if (success_ != false) {
      output.writeBool(3, success_);
    }
    unknownFields.writeTo(output);
  }

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!data_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(1, data_);
    }
    if (!getErrMsgBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, errMsg_);
    }
    if (success_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(3, success_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof Response)) {
      return super.equals(obj);
    }
    Response other = (Response) obj;

    if (!getData()
        .equals(other.getData())) return false;
    if (!getErrMsg()
        .equals(other.getErrMsg())) return false;
    if (getSuccess()
        != other.getSuccess()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + DATA_FIELD_NUMBER;
    hash = (53 * hash) + getData().hashCode();
    hash = (37 * hash) + ERRMSG_FIELD_NUMBER;
    hash = (53 * hash) + getErrMsg().hashCode();
    hash = (37 * hash) + SUCCESS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getSuccess());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static Response parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Response parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Response parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Response parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Response parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static Response parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static Response parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static Response parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static Response parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static Response parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static Response parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static Response parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(Response prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @Override
  protected Builder newBuilderForType(
      BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code Response}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:Response)
      ResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return Data.internal_static_Response_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return Data.internal_static_Response_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Response.class, Response.Builder.class);
    }

    // Construct using com.alibaba.nacos.consistency.entity.Response.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @Override
    public Builder clear() {
      super.clear();
      data_ = com.google.protobuf.ByteString.EMPTY;

      errMsg_ = "";

      success_ = false;

      return this;
    }

    @Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return Data.internal_static_Response_descriptor;
    }

    @Override
    public Response getDefaultInstanceForType() {
      return Response.getDefaultInstance();
    }

    @Override
    public Response build() {
      Response result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @Override
    public Response buildPartial() {
      Response result = new Response(this);
      result.data_ = data_;
      result.errMsg_ = errMsg_;
      result.success_ = success_;
      onBuilt();
      return result;
    }

    @Override
    public Builder clone() {
      return super.clone();
    }
    @Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.setField(field, value);
    }
    @Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        Object value) {
      return super.addRepeatedField(field, value);
    }
    @Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof Response) {
        return mergeFrom((Response)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(Response other) {
      if (other == Response.getDefaultInstance()) return this;
      if (other.getData() != com.google.protobuf.ByteString.EMPTY) {
        setData(other.getData());
      }
      if (!other.getErrMsg().isEmpty()) {
        errMsg_ = other.errMsg_;
        onChanged();
      }
      if (other.getSuccess() != false) {
        setSuccess(other.getSuccess());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @Override
    public final boolean isInitialized() {
      return true;
    }

    @Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Response parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (Response) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.google.protobuf.ByteString data_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes data = 1;</code>
     */
    public com.google.protobuf.ByteString getData() {
      return data_;
    }
    /**
     * <code>bytes data = 1;</code>
     */
    public Builder setData(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }

      data_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes data = 1;</code>
     */
    public Builder clearData() {

      data_ = getDefaultInstance().getData();
      onChanged();
      return this;
    }

    private Object errMsg_ = "";
    /**
     * <code>string errMsg = 2;</code>
     */
    public String getErrMsg() {
      Object ref = errMsg_;
      if (!(ref instanceof String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        errMsg_ = s;
        return s;
      } else {
        return (String) ref;
      }
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public com.google.protobuf.ByteString
        getErrMsgBytes() {
      Object ref = errMsg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b =
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        errMsg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public Builder setErrMsg(
        String value) {
      if (value == null) {
    throw new NullPointerException();
  }

      errMsg_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public Builder clearErrMsg() {

      errMsg_ = getDefaultInstance().getErrMsg();
      onChanged();
      return this;
    }
    /**
     * <code>string errMsg = 2;</code>
     */
    public Builder setErrMsgBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);

      errMsg_ = value;
      onChanged();
      return this;
    }

    private boolean success_ ;
    /**
     * <code>bool success = 3;</code>
     */
    public boolean getSuccess() {
      return success_;
    }
    /**
     * <code>bool success = 3;</code>
     */
    public Builder setSuccess(boolean value) {

      success_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool success = 3;</code>
     */
    public Builder clearSuccess() {

      success_ = false;
      onChanged();
      return this;
    }
    @Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:Response)
  }

  // @@protoc_insertion_point(class_scope:Response)
  private static final Response DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new Response();
  }

  public static Response getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<Response>
      PARSER = new com.google.protobuf.AbstractParser<Response>() {
    @Override
    public Response parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new Response(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<Response> parser() {
    return PARSER;
  }

  @Override
  public com.google.protobuf.Parser<Response> getParserForType() {
    return PARSER;
  }

  @Override
  public Response getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

