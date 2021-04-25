/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.risk.manager.thriftapi;

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

public class HostingRiskRuleItemValue implements org.apache.thrift.TBase<HostingRiskRuleItemValue, HostingRiskRuleItemValue._Fields>, java.io.Serializable, Cloneable, Comparable<HostingRiskRuleItemValue> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingRiskRuleItemValue");

  private static final org.apache.thrift.protocol.TField LONG_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("longValue", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField DOUBE_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("doubeValue", org.apache.thrift.protocol.TType.DOUBLE, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingRiskRuleItemValueStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingRiskRuleItemValueTupleSchemeFactory());
  }

  public long longValue; // optional
  public double doubeValue; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LONG_VALUE((short)1, "longValue"),
    DOUBE_VALUE((short)2, "doubeValue");

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
        case 1: // LONG_VALUE
          return LONG_VALUE;
        case 2: // DOUBE_VALUE
          return DOUBE_VALUE;
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
  private static final int __LONGVALUE_ISSET_ID = 0;
  private static final int __DOUBEVALUE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.LONG_VALUE,_Fields.DOUBE_VALUE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LONG_VALUE, new org.apache.thrift.meta_data.FieldMetaData("longValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DOUBE_VALUE, new org.apache.thrift.meta_data.FieldMetaData("doubeValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingRiskRuleItemValue.class, metaDataMap);
  }

  public HostingRiskRuleItemValue() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingRiskRuleItemValue(HostingRiskRuleItemValue other) {
    __isset_bitfield = other.__isset_bitfield;
    this.longValue = other.longValue;
    this.doubeValue = other.doubeValue;
  }

  public HostingRiskRuleItemValue deepCopy() {
    return new HostingRiskRuleItemValue(this);
  }

  @Override
  public void clear() {
    setLongValueIsSet(false);
    this.longValue = 0;
    setDoubeValueIsSet(false);
    this.doubeValue = 0.0;
  }

  public long getLongValue() {
    return this.longValue;
  }

  public HostingRiskRuleItemValue setLongValue(long longValue) {
    this.longValue = longValue;
    setLongValueIsSet(true);
    return this;
  }

  public void unsetLongValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LONGVALUE_ISSET_ID);
  }

  /** Returns true if field longValue is set (has been assigned a value) and false otherwise */
  public boolean isSetLongValue() {
    return EncodingUtils.testBit(__isset_bitfield, __LONGVALUE_ISSET_ID);
  }

  public void setLongValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LONGVALUE_ISSET_ID, value);
  }

  public double getDoubeValue() {
    return this.doubeValue;
  }

  public HostingRiskRuleItemValue setDoubeValue(double doubeValue) {
    this.doubeValue = doubeValue;
    setDoubeValueIsSet(true);
    return this;
  }

  public void unsetDoubeValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DOUBEVALUE_ISSET_ID);
  }

  /** Returns true if field doubeValue is set (has been assigned a value) and false otherwise */
  public boolean isSetDoubeValue() {
    return EncodingUtils.testBit(__isset_bitfield, __DOUBEVALUE_ISSET_ID);
  }

  public void setDoubeValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DOUBEVALUE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LONG_VALUE:
      if (value == null) {
        unsetLongValue();
      } else {
        setLongValue((Long)value);
      }
      break;

    case DOUBE_VALUE:
      if (value == null) {
        unsetDoubeValue();
      } else {
        setDoubeValue((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LONG_VALUE:
      return Long.valueOf(getLongValue());

    case DOUBE_VALUE:
      return Double.valueOf(getDoubeValue());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LONG_VALUE:
      return isSetLongValue();
    case DOUBE_VALUE:
      return isSetDoubeValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingRiskRuleItemValue)
      return this.equals((HostingRiskRuleItemValue)that);
    return false;
  }

  public boolean equals(HostingRiskRuleItemValue that) {
    if (that == null)
      return false;

    boolean this_present_longValue = true && this.isSetLongValue();
    boolean that_present_longValue = true && that.isSetLongValue();
    if (this_present_longValue || that_present_longValue) {
      if (!(this_present_longValue && that_present_longValue))
        return false;
      if (this.longValue != that.longValue)
        return false;
    }

    boolean this_present_doubeValue = true && this.isSetDoubeValue();
    boolean that_present_doubeValue = true && that.isSetDoubeValue();
    if (this_present_doubeValue || that_present_doubeValue) {
      if (!(this_present_doubeValue && that_present_doubeValue))
        return false;
      if (this.doubeValue != that.doubeValue)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingRiskRuleItemValue other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetLongValue()).compareTo(other.isSetLongValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLongValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.longValue, other.longValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDoubeValue()).compareTo(other.isSetDoubeValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDoubeValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.doubeValue, other.doubeValue);
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
    StringBuilder sb = new StringBuilder("HostingRiskRuleItemValue(");
    boolean first = true;

    if (isSetLongValue()) {
      sb.append("longValue:");
      sb.append(this.longValue);
      first = false;
    }
    if (isSetDoubeValue()) {
      if (!first) sb.append(", ");
      sb.append("doubeValue:");
      sb.append(this.doubeValue);
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

  private static class HostingRiskRuleItemValueStandardSchemeFactory implements SchemeFactory {
    public HostingRiskRuleItemValueStandardScheme getScheme() {
      return new HostingRiskRuleItemValueStandardScheme();
    }
  }

  private static class HostingRiskRuleItemValueStandardScheme extends StandardScheme<HostingRiskRuleItemValue> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingRiskRuleItemValue struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LONG_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.longValue = iprot.readI64();
              struct.setLongValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DOUBE_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.doubeValue = iprot.readDouble();
              struct.setDoubeValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingRiskRuleItemValue struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetLongValue()) {
        oprot.writeFieldBegin(LONG_VALUE_FIELD_DESC);
        oprot.writeI64(struct.longValue);
        oprot.writeFieldEnd();
      }
      if (struct.isSetDoubeValue()) {
        oprot.writeFieldBegin(DOUBE_VALUE_FIELD_DESC);
        oprot.writeDouble(struct.doubeValue);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingRiskRuleItemValueTupleSchemeFactory implements SchemeFactory {
    public HostingRiskRuleItemValueTupleScheme getScheme() {
      return new HostingRiskRuleItemValueTupleScheme();
    }
  }

  private static class HostingRiskRuleItemValueTupleScheme extends TupleScheme<HostingRiskRuleItemValue> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingRiskRuleItemValue struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLongValue()) {
        optionals.set(0);
      }
      if (struct.isSetDoubeValue()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetLongValue()) {
        oprot.writeI64(struct.longValue);
      }
      if (struct.isSetDoubeValue()) {
        oprot.writeDouble(struct.doubeValue);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingRiskRuleItemValue struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.longValue = iprot.readI64();
        struct.setLongValueIsSet(true);
      }
      if (incoming.get(1)) {
        struct.doubeValue = iprot.readDouble();
        struct.setDoubeValueIsSet(true);
      }
    }
  }

}
