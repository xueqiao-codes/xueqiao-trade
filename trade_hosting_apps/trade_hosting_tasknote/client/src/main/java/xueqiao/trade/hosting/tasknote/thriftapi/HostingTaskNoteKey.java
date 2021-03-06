/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.tasknote.thriftapi;

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
 * 对应的一个Key结构
 */
public class HostingTaskNoteKey implements org.apache.thrift.TBase<HostingTaskNoteKey, HostingTaskNoteKey._Fields>, java.io.Serializable, Cloneable, Comparable<HostingTaskNoteKey> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingTaskNoteKey");

  private static final org.apache.thrift.protocol.TField KEY1_FIELD_DESC = new org.apache.thrift.protocol.TField("key1", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField KEY2_FIELD_DESC = new org.apache.thrift.protocol.TField("key2", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField KEY3_FIELD_DESC = new org.apache.thrift.protocol.TField("key3", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingTaskNoteKeyStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingTaskNoteKeyTupleSchemeFactory());
  }

  public long key1; // optional
  public long key2; // optional
  public String key3; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    KEY1((short)1, "key1"),
    KEY2((short)2, "key2"),
    KEY3((short)3, "key3");

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
        case 1: // KEY1
          return KEY1;
        case 2: // KEY2
          return KEY2;
        case 3: // KEY3
          return KEY3;
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
  private static final int __KEY1_ISSET_ID = 0;
  private static final int __KEY2_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.KEY1,_Fields.KEY2,_Fields.KEY3};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.KEY1, new org.apache.thrift.meta_data.FieldMetaData("key1", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.KEY2, new org.apache.thrift.meta_data.FieldMetaData("key2", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.KEY3, new org.apache.thrift.meta_data.FieldMetaData("key3", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingTaskNoteKey.class, metaDataMap);
  }

  public HostingTaskNoteKey() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingTaskNoteKey(HostingTaskNoteKey other) {
    __isset_bitfield = other.__isset_bitfield;
    this.key1 = other.key1;
    this.key2 = other.key2;
    if (other.isSetKey3()) {
      this.key3 = other.key3;
    }
  }

  public HostingTaskNoteKey deepCopy() {
    return new HostingTaskNoteKey(this);
  }

  @Override
  public void clear() {
    setKey1IsSet(false);
    this.key1 = 0;
    setKey2IsSet(false);
    this.key2 = 0;
    this.key3 = null;
  }

  public long getKey1() {
    return this.key1;
  }

  public HostingTaskNoteKey setKey1(long key1) {
    this.key1 = key1;
    setKey1IsSet(true);
    return this;
  }

  public void unsetKey1() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __KEY1_ISSET_ID);
  }

  /** Returns true if field key1 is set (has been assigned a value) and false otherwise */
  public boolean isSetKey1() {
    return EncodingUtils.testBit(__isset_bitfield, __KEY1_ISSET_ID);
  }

  public void setKey1IsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __KEY1_ISSET_ID, value);
  }

  public long getKey2() {
    return this.key2;
  }

  public HostingTaskNoteKey setKey2(long key2) {
    this.key2 = key2;
    setKey2IsSet(true);
    return this;
  }

  public void unsetKey2() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __KEY2_ISSET_ID);
  }

  /** Returns true if field key2 is set (has been assigned a value) and false otherwise */
  public boolean isSetKey2() {
    return EncodingUtils.testBit(__isset_bitfield, __KEY2_ISSET_ID);
  }

  public void setKey2IsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __KEY2_ISSET_ID, value);
  }

  public String getKey3() {
    return this.key3;
  }

  public HostingTaskNoteKey setKey3(String key3) {
    this.key3 = key3;
    return this;
  }

  public void unsetKey3() {
    this.key3 = null;
  }

  /** Returns true if field key3 is set (has been assigned a value) and false otherwise */
  public boolean isSetKey3() {
    return this.key3 != null;
  }

  public void setKey3IsSet(boolean value) {
    if (!value) {
      this.key3 = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case KEY1:
      if (value == null) {
        unsetKey1();
      } else {
        setKey1((Long)value);
      }
      break;

    case KEY2:
      if (value == null) {
        unsetKey2();
      } else {
        setKey2((Long)value);
      }
      break;

    case KEY3:
      if (value == null) {
        unsetKey3();
      } else {
        setKey3((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case KEY1:
      return Long.valueOf(getKey1());

    case KEY2:
      return Long.valueOf(getKey2());

    case KEY3:
      return getKey3();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case KEY1:
      return isSetKey1();
    case KEY2:
      return isSetKey2();
    case KEY3:
      return isSetKey3();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingTaskNoteKey)
      return this.equals((HostingTaskNoteKey)that);
    return false;
  }

  public boolean equals(HostingTaskNoteKey that) {
    if (that == null)
      return false;

    boolean this_present_key1 = true && this.isSetKey1();
    boolean that_present_key1 = true && that.isSetKey1();
    if (this_present_key1 || that_present_key1) {
      if (!(this_present_key1 && that_present_key1))
        return false;
      if (this.key1 != that.key1)
        return false;
    }

    boolean this_present_key2 = true && this.isSetKey2();
    boolean that_present_key2 = true && that.isSetKey2();
    if (this_present_key2 || that_present_key2) {
      if (!(this_present_key2 && that_present_key2))
        return false;
      if (this.key2 != that.key2)
        return false;
    }

    boolean this_present_key3 = true && this.isSetKey3();
    boolean that_present_key3 = true && that.isSetKey3();
    if (this_present_key3 || that_present_key3) {
      if (!(this_present_key3 && that_present_key3))
        return false;
      if (!this.key3.equals(that.key3))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingTaskNoteKey other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetKey1()).compareTo(other.isSetKey1());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKey1()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key1, other.key1);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetKey2()).compareTo(other.isSetKey2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKey2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key2, other.key2);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetKey3()).compareTo(other.isSetKey3());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKey3()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.key3, other.key3);
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
    StringBuilder sb = new StringBuilder("HostingTaskNoteKey(");
    boolean first = true;

    if (isSetKey1()) {
      sb.append("key1:");
      sb.append(this.key1);
      first = false;
    }
    if (isSetKey2()) {
      if (!first) sb.append(", ");
      sb.append("key2:");
      sb.append(this.key2);
      first = false;
    }
    if (isSetKey3()) {
      if (!first) sb.append(", ");
      sb.append("key3:");
      if (this.key3 == null) {
        sb.append("null");
      } else {
        sb.append(this.key3);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
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

  private static class HostingTaskNoteKeyStandardSchemeFactory implements SchemeFactory {
    public HostingTaskNoteKeyStandardScheme getScheme() {
      return new HostingTaskNoteKeyStandardScheme();
    }
  }

  private static class HostingTaskNoteKeyStandardScheme extends StandardScheme<HostingTaskNoteKey> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingTaskNoteKey struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // KEY1
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.key1 = iprot.readI64();
              struct.setKey1IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // KEY2
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.key2 = iprot.readI64();
              struct.setKey2IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // KEY3
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.key3 = iprot.readString();
              struct.setKey3IsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingTaskNoteKey struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetKey1()) {
        oprot.writeFieldBegin(KEY1_FIELD_DESC);
        oprot.writeI64(struct.key1);
        oprot.writeFieldEnd();
      }
      if (struct.isSetKey2()) {
        oprot.writeFieldBegin(KEY2_FIELD_DESC);
        oprot.writeI64(struct.key2);
        oprot.writeFieldEnd();
      }
      if (struct.key3 != null) {
        if (struct.isSetKey3()) {
          oprot.writeFieldBegin(KEY3_FIELD_DESC);
          oprot.writeString(struct.key3);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingTaskNoteKeyTupleSchemeFactory implements SchemeFactory {
    public HostingTaskNoteKeyTupleScheme getScheme() {
      return new HostingTaskNoteKeyTupleScheme();
    }
  }

  private static class HostingTaskNoteKeyTupleScheme extends TupleScheme<HostingTaskNoteKey> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingTaskNoteKey struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetKey1()) {
        optionals.set(0);
      }
      if (struct.isSetKey2()) {
        optionals.set(1);
      }
      if (struct.isSetKey3()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetKey1()) {
        oprot.writeI64(struct.key1);
      }
      if (struct.isSetKey2()) {
        oprot.writeI64(struct.key2);
      }
      if (struct.isSetKey3()) {
        oprot.writeString(struct.key3);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingTaskNoteKey struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.key1 = iprot.readI64();
        struct.setKey1IsSet(true);
      }
      if (incoming.get(1)) {
        struct.key2 = iprot.readI64();
        struct.setKey2IsSet(true);
      }
      if (incoming.get(2)) {
        struct.key3 = iprot.readString();
        struct.setKey3IsSet(true);
      }
    }
  }

}

