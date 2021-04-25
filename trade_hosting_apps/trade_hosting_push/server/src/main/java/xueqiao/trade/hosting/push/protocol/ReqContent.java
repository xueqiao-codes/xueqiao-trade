/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.push.protocol;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端发送请求的内容
 */
public class ReqContent implements org.apache.thrift.TBase<ReqContent, ReqContent._Fields>, java.io.Serializable, Cloneable, Comparable<ReqContent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReqContent");

  private static final org.apache.thrift.protocol.TField REQUEST_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("requestId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField REQUEST_STRUCT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("requestStructType", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField REQUEST_STRUCT_BYTES_FIELD_DESC = new org.apache.thrift.protocol.TField("requestStructBytes", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReqContentStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReqContentTupleSchemeFactory());
  }

  public long requestId; // required
  public String requestStructType; // required
  public ByteBuffer requestStructBytes; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    REQUEST_ID((short)1, "requestId"),
    REQUEST_STRUCT_TYPE((short)2, "requestStructType"),
    REQUEST_STRUCT_BYTES((short)3, "requestStructBytes");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // REQUEST_ID
          return REQUEST_ID;
        case 2: // REQUEST_STRUCT_TYPE
          return REQUEST_STRUCT_TYPE;
        case 3: // REQUEST_STRUCT_BYTES
          return REQUEST_STRUCT_BYTES;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __REQUESTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.REQUEST_STRUCT_BYTES};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.REQUEST_ID, new org.apache.thrift.meta_data.FieldMetaData("requestId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.REQUEST_STRUCT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("requestStructType", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.REQUEST_STRUCT_BYTES, new org.apache.thrift.meta_data.FieldMetaData("requestStructBytes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING        , true)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReqContent.class, metaDataMap);
  }

  public ReqContent() {
  }

  public ReqContent(
    long requestId,
    String requestStructType)
  {
    this();
    this.requestId = requestId;
    setRequestIdIsSet(true);
    this.requestStructType = requestStructType;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReqContent(ReqContent other) {
    __isset_bitfield = other.__isset_bitfield;
    this.requestId = other.requestId;
    if (other.isSetRequestStructType()) {
      this.requestStructType = other.requestStructType;
    }
    if (other.isSetRequestStructBytes()) {
      this.requestStructBytes = org.apache.thrift.TBaseHelper.copyBinary(other.requestStructBytes);
;
    }
  }

  public ReqContent deepCopy() {
    return new ReqContent(this);
  }

  @Override
  public void clear() {
    setRequestIdIsSet(false);
    this.requestId = 0;
    this.requestStructType = null;
    this.requestStructBytes = null;
  }

  public long getRequestId() {
    return this.requestId;
  }

  public ReqContent setRequestId(long requestId) {
    this.requestId = requestId;
    setRequestIdIsSet(true);
    return this;
  }

  public void unsetRequestId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REQUESTID_ISSET_ID);
  }

  /** Returns true if field requestId is set (has been assigned a value) and false otherwise */
  public boolean isSetRequestId() {
    return EncodingUtils.testBit(__isset_bitfield, __REQUESTID_ISSET_ID);
  }

  public void setRequestIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REQUESTID_ISSET_ID, value);
  }

  public String getRequestStructType() {
    return this.requestStructType;
  }

  public ReqContent setRequestStructType(String requestStructType) {
    this.requestStructType = requestStructType;
    return this;
  }

  public void unsetRequestStructType() {
    this.requestStructType = null;
  }

  /** Returns true if field requestStructType is set (has been assigned a value) and false otherwise */
  public boolean isSetRequestStructType() {
    return this.requestStructType != null;
  }

  public void setRequestStructTypeIsSet(boolean value) {
    if (!value) {
      this.requestStructType = null;
    }
  }

  public byte[] getRequestStructBytes() {
    setRequestStructBytes(org.apache.thrift.TBaseHelper.rightSize(requestStructBytes));
    return requestStructBytes == null ? null : requestStructBytes.array();
  }

  public ByteBuffer bufferForRequestStructBytes() {
    return requestStructBytes;
  }

  public ReqContent setRequestStructBytes(byte[] requestStructBytes) {
    setRequestStructBytes(requestStructBytes == null ? (ByteBuffer)null : ByteBuffer.wrap(requestStructBytes));
    return this;
  }

  public ReqContent setRequestStructBytes(ByteBuffer requestStructBytes) {
    this.requestStructBytes = requestStructBytes;
    return this;
  }

  public void unsetRequestStructBytes() {
    this.requestStructBytes = null;
  }

  /** Returns true if field requestStructBytes is set (has been assigned a value) and false otherwise */
  public boolean isSetRequestStructBytes() {
    return this.requestStructBytes != null;
  }

  public void setRequestStructBytesIsSet(boolean value) {
    if (!value) {
      this.requestStructBytes = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case REQUEST_ID:
      if (value == null) {
        unsetRequestId();
      } else {
        setRequestId((Long)value);
      }
      break;

    case REQUEST_STRUCT_TYPE:
      if (value == null) {
        unsetRequestStructType();
      } else {
        setRequestStructType((String)value);
      }
      break;

    case REQUEST_STRUCT_BYTES:
      if (value == null) {
        unsetRequestStructBytes();
      } else {
        setRequestStructBytes((ByteBuffer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case REQUEST_ID:
      return Long.valueOf(getRequestId());

    case REQUEST_STRUCT_TYPE:
      return getRequestStructType();

    case REQUEST_STRUCT_BYTES:
      return getRequestStructBytes();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case REQUEST_ID:
      return isSetRequestId();
    case REQUEST_STRUCT_TYPE:
      return isSetRequestStructType();
    case REQUEST_STRUCT_BYTES:
      return isSetRequestStructBytes();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReqContent)
      return this.equals((ReqContent)that);
    return false;
  }

  public boolean equals(ReqContent that) {
    if (that == null)
      return false;

    boolean this_present_requestId = true;
    boolean that_present_requestId = true;
    if (this_present_requestId || that_present_requestId) {
      if (!(this_present_requestId && that_present_requestId))
        return false;
      if (this.requestId != that.requestId)
        return false;
    }

    boolean this_present_requestStructType = true && this.isSetRequestStructType();
    boolean that_present_requestStructType = true && that.isSetRequestStructType();
    if (this_present_requestStructType || that_present_requestStructType) {
      if (!(this_present_requestStructType && that_present_requestStructType))
        return false;
      if (!this.requestStructType.equals(that.requestStructType))
        return false;
    }

    boolean this_present_requestStructBytes = true && this.isSetRequestStructBytes();
    boolean that_present_requestStructBytes = true && that.isSetRequestStructBytes();
    if (this_present_requestStructBytes || that_present_requestStructBytes) {
      if (!(this_present_requestStructBytes && that_present_requestStructBytes))
        return false;
      if (!this.requestStructBytes.equals(that.requestStructBytes))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ReqContent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRequestId()).compareTo(other.isSetRequestId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRequestId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.requestId, other.requestId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRequestStructType()).compareTo(other.isSetRequestStructType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRequestStructType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.requestStructType, other.requestStructType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRequestStructBytes()).compareTo(other.isSetRequestStructBytes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRequestStructBytes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.requestStructBytes, other.requestStructBytes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("ReqContent(");
    boolean first = true;

    sb.append("requestId:");
    sb.append(this.requestId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("requestStructType:");
    if (this.requestStructType == null) {
      sb.append("null");
    } else {
      sb.append(this.requestStructType);
    }
    first = false;
    if (isSetRequestStructBytes()) {
      if (!first) sb.append(", ");
      sb.append("requestStructBytes:");
      if (this.requestStructBytes == null) {
        sb.append("null");
      } else {
        org.apache.thrift.TBaseHelper.toString(this.requestStructBytes, sb);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'requestId' because it's a primitive and you chose the non-beans generator.
    if (requestStructType == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'requestStructType' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ReqContentStandardSchemeFactory implements SchemeFactory {
    public ReqContentStandardScheme getScheme() {
      return new ReqContentStandardScheme();
    }
  }

  private static class ReqContentStandardScheme extends StandardScheme<ReqContent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReqContent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // REQUEST_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.requestId = iprot.readI64();
              struct.setRequestIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // REQUEST_STRUCT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.requestStructType = iprot.readString();
              struct.setRequestStructTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // REQUEST_STRUCT_BYTES
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.requestStructBytes = iprot.readBinary();
              struct.setRequestStructBytesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetRequestId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'requestId' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReqContent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(REQUEST_ID_FIELD_DESC);
      oprot.writeI64(struct.requestId);
      oprot.writeFieldEnd();
      if (struct.requestStructType != null) {
        oprot.writeFieldBegin(REQUEST_STRUCT_TYPE_FIELD_DESC);
        oprot.writeString(struct.requestStructType);
        oprot.writeFieldEnd();
      }
      if (struct.requestStructBytes != null) {
        if (struct.isSetRequestStructBytes()) {
          oprot.writeFieldBegin(REQUEST_STRUCT_BYTES_FIELD_DESC);
          oprot.writeBinary(struct.requestStructBytes);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReqContentTupleSchemeFactory implements SchemeFactory {
    public ReqContentTupleScheme getScheme() {
      return new ReqContentTupleScheme();
    }
  }

  private static class ReqContentTupleScheme extends TupleScheme<ReqContent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReqContent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.requestId);
      oprot.writeString(struct.requestStructType);
      BitSet optionals = new BitSet();
      if (struct.isSetRequestStructBytes()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetRequestStructBytes()) {
        oprot.writeBinary(struct.requestStructBytes);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReqContent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.requestId = iprot.readI64();
      struct.setRequestIdIsSet(true);
      struct.requestStructType = iprot.readString();
      struct.setRequestStructTypeIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.requestStructBytes = iprot.readBinary();
        struct.setRequestStructBytesIsSet(true);
      }
    }
  }

}

