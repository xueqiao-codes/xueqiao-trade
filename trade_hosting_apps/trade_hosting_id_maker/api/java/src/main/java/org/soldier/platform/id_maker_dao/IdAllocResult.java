/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.id_maker_dao;

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

public class IdAllocResult implements org.apache.thrift.TBase<IdAllocResult, IdAllocResult._Fields>, java.io.Serializable, Cloneable, Comparable<IdAllocResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("IdAllocResult");

  private static final org.apache.thrift.protocol.TField BEGIN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("beginId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField ALLOC_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("allocSize", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new IdAllocResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new IdAllocResultTupleSchemeFactory());
  }

  public long beginId; // required
  public int allocSize; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BEGIN_ID((short)1, "beginId"),
    ALLOC_SIZE((short)2, "allocSize");

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
        case 1: // BEGIN_ID
          return BEGIN_ID;
        case 2: // ALLOC_SIZE
          return ALLOC_SIZE;
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
  private static final int __BEGINID_ISSET_ID = 0;
  private static final int __ALLOCSIZE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BEGIN_ID, new org.apache.thrift.meta_data.FieldMetaData("beginId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ALLOC_SIZE, new org.apache.thrift.meta_data.FieldMetaData("allocSize", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(IdAllocResult.class, metaDataMap);
  }

  public IdAllocResult() {
  }

  public IdAllocResult(
    long beginId,
    int allocSize)
  {
    this();
    this.beginId = beginId;
    setBeginIdIsSet(true);
    this.allocSize = allocSize;
    setAllocSizeIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public IdAllocResult(IdAllocResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.beginId = other.beginId;
    this.allocSize = other.allocSize;
  }

  public IdAllocResult deepCopy() {
    return new IdAllocResult(this);
  }

  @Override
  public void clear() {
    setBeginIdIsSet(false);
    this.beginId = 0;
    setAllocSizeIsSet(false);
    this.allocSize = 0;
  }

  public long getBeginId() {
    return this.beginId;
  }

  public IdAllocResult setBeginId(long beginId) {
    this.beginId = beginId;
    setBeginIdIsSet(true);
    return this;
  }

  public void unsetBeginId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BEGINID_ISSET_ID);
  }

  /** Returns true if field beginId is set (has been assigned a value) and false otherwise */
  public boolean isSetBeginId() {
    return EncodingUtils.testBit(__isset_bitfield, __BEGINID_ISSET_ID);
  }

  public void setBeginIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BEGINID_ISSET_ID, value);
  }

  public int getAllocSize() {
    return this.allocSize;
  }

  public IdAllocResult setAllocSize(int allocSize) {
    this.allocSize = allocSize;
    setAllocSizeIsSet(true);
    return this;
  }

  public void unsetAllocSize() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ALLOCSIZE_ISSET_ID);
  }

  /** Returns true if field allocSize is set (has been assigned a value) and false otherwise */
  public boolean isSetAllocSize() {
    return EncodingUtils.testBit(__isset_bitfield, __ALLOCSIZE_ISSET_ID);
  }

  public void setAllocSizeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ALLOCSIZE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BEGIN_ID:
      if (value == null) {
        unsetBeginId();
      } else {
        setBeginId((Long)value);
      }
      break;

    case ALLOC_SIZE:
      if (value == null) {
        unsetAllocSize();
      } else {
        setAllocSize((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BEGIN_ID:
      return Long.valueOf(getBeginId());

    case ALLOC_SIZE:
      return Integer.valueOf(getAllocSize());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BEGIN_ID:
      return isSetBeginId();
    case ALLOC_SIZE:
      return isSetAllocSize();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof IdAllocResult)
      return this.equals((IdAllocResult)that);
    return false;
  }

  public boolean equals(IdAllocResult that) {
    if (that == null)
      return false;

    boolean this_present_beginId = true;
    boolean that_present_beginId = true;
    if (this_present_beginId || that_present_beginId) {
      if (!(this_present_beginId && that_present_beginId))
        return false;
      if (this.beginId != that.beginId)
        return false;
    }

    boolean this_present_allocSize = true;
    boolean that_present_allocSize = true;
    if (this_present_allocSize || that_present_allocSize) {
      if (!(this_present_allocSize && that_present_allocSize))
        return false;
      if (this.allocSize != that.allocSize)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(IdAllocResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetBeginId()).compareTo(other.isSetBeginId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeginId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beginId, other.beginId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAllocSize()).compareTo(other.isSetAllocSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAllocSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.allocSize, other.allocSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("IdAllocResult(");
    boolean first = true;

    sb.append("beginId:");
    sb.append(this.beginId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("allocSize:");
    sb.append(this.allocSize);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // alas, we cannot check 'beginId' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'allocSize' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class IdAllocResultStandardSchemeFactory implements SchemeFactory {
    public IdAllocResultStandardScheme getScheme() {
      return new IdAllocResultStandardScheme();
    }
  }

  private static class IdAllocResultStandardScheme extends StandardScheme<IdAllocResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, IdAllocResult struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BEGIN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.beginId = iprot.readI64();
              struct.setBeginIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ALLOC_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.allocSize = iprot.readI32();
              struct.setAllocSizeIsSet(true);
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
      if (!struct.isSetBeginId()) {
        throw new TProtocolException("Required field 'beginId' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetAllocSize()) {
        throw new TProtocolException("Required field 'allocSize' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, IdAllocResult struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BEGIN_ID_FIELD_DESC);
      oprot.writeI64(struct.beginId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(ALLOC_SIZE_FIELD_DESC);
      oprot.writeI32(struct.allocSize);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class IdAllocResultTupleSchemeFactory implements SchemeFactory {
    public IdAllocResultTupleScheme getScheme() {
      return new IdAllocResultTupleScheme();
    }
  }

  private static class IdAllocResultTupleScheme extends TupleScheme<IdAllocResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, IdAllocResult struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.beginId);
      oprot.writeI32(struct.allocSize);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, IdAllocResult struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.beginId = iprot.readI64();
      struct.setBeginIdIsSet(true);
      struct.allocSize = iprot.readI32();
      struct.setAllocSizeIsSet(true);
    }
  }

}

