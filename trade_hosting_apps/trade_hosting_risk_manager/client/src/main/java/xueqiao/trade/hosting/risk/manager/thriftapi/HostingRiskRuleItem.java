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

public class HostingRiskRuleItem implements org.apache.thrift.TBase<HostingRiskRuleItem, HostingRiskRuleItem._Fields>, java.io.Serializable, Cloneable, Comparable<HostingRiskRuleItem> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingRiskRuleItem");

  private static final org.apache.thrift.protocol.TField RULE_ENABLED_FIELD_DESC = new org.apache.thrift.protocol.TField("ruleEnabled", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField ALARM_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("alarmValue", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField FORBIDDEN_OPEN_POSITION_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("forbiddenOpenPositionValue", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingRiskRuleItemStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingRiskRuleItemTupleSchemeFactory());
  }

  public boolean ruleEnabled; // optional
  public HostingRiskRuleItemValue alarmValue; // optional
  public HostingRiskRuleItemValue forbiddenOpenPositionValue; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    RULE_ENABLED((short)1, "ruleEnabled"),
    ALARM_VALUE((short)2, "alarmValue"),
    FORBIDDEN_OPEN_POSITION_VALUE((short)3, "forbiddenOpenPositionValue");

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
        case 1: // RULE_ENABLED
          return RULE_ENABLED;
        case 2: // ALARM_VALUE
          return ALARM_VALUE;
        case 3: // FORBIDDEN_OPEN_POSITION_VALUE
          return FORBIDDEN_OPEN_POSITION_VALUE;
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
  private static final int __RULEENABLED_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.RULE_ENABLED,_Fields.ALARM_VALUE,_Fields.FORBIDDEN_OPEN_POSITION_VALUE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.RULE_ENABLED, new org.apache.thrift.meta_data.FieldMetaData("ruleEnabled", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.ALARM_VALUE, new org.apache.thrift.meta_data.FieldMetaData("alarmValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingRiskRuleItemValue.class)));
    tmpMap.put(_Fields.FORBIDDEN_OPEN_POSITION_VALUE, new org.apache.thrift.meta_data.FieldMetaData("forbiddenOpenPositionValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingRiskRuleItemValue.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingRiskRuleItem.class, metaDataMap);
  }

  public HostingRiskRuleItem() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingRiskRuleItem(HostingRiskRuleItem other) {
    __isset_bitfield = other.__isset_bitfield;
    this.ruleEnabled = other.ruleEnabled;
    if (other.isSetAlarmValue()) {
      this.alarmValue = new HostingRiskRuleItemValue(other.alarmValue);
    }
    if (other.isSetForbiddenOpenPositionValue()) {
      this.forbiddenOpenPositionValue = new HostingRiskRuleItemValue(other.forbiddenOpenPositionValue);
    }
  }

  public HostingRiskRuleItem deepCopy() {
    return new HostingRiskRuleItem(this);
  }

  @Override
  public void clear() {
    setRuleEnabledIsSet(false);
    this.ruleEnabled = false;
    this.alarmValue = null;
    this.forbiddenOpenPositionValue = null;
  }

  public boolean isRuleEnabled() {
    return this.ruleEnabled;
  }

  public HostingRiskRuleItem setRuleEnabled(boolean ruleEnabled) {
    this.ruleEnabled = ruleEnabled;
    setRuleEnabledIsSet(true);
    return this;
  }

  public void unsetRuleEnabled() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __RULEENABLED_ISSET_ID);
  }

  /** Returns true if field ruleEnabled is set (has been assigned a value) and false otherwise */
  public boolean isSetRuleEnabled() {
    return EncodingUtils.testBit(__isset_bitfield, __RULEENABLED_ISSET_ID);
  }

  public void setRuleEnabledIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __RULEENABLED_ISSET_ID, value);
  }

  public HostingRiskRuleItemValue getAlarmValue() {
    return this.alarmValue;
  }

  public HostingRiskRuleItem setAlarmValue(HostingRiskRuleItemValue alarmValue) {
    this.alarmValue = alarmValue;
    return this;
  }

  public void unsetAlarmValue() {
    this.alarmValue = null;
  }

  /** Returns true if field alarmValue is set (has been assigned a value) and false otherwise */
  public boolean isSetAlarmValue() {
    return this.alarmValue != null;
  }

  public void setAlarmValueIsSet(boolean value) {
    if (!value) {
      this.alarmValue = null;
    }
  }

  public HostingRiskRuleItemValue getForbiddenOpenPositionValue() {
    return this.forbiddenOpenPositionValue;
  }

  public HostingRiskRuleItem setForbiddenOpenPositionValue(HostingRiskRuleItemValue forbiddenOpenPositionValue) {
    this.forbiddenOpenPositionValue = forbiddenOpenPositionValue;
    return this;
  }

  public void unsetForbiddenOpenPositionValue() {
    this.forbiddenOpenPositionValue = null;
  }

  /** Returns true if field forbiddenOpenPositionValue is set (has been assigned a value) and false otherwise */
  public boolean isSetForbiddenOpenPositionValue() {
    return this.forbiddenOpenPositionValue != null;
  }

  public void setForbiddenOpenPositionValueIsSet(boolean value) {
    if (!value) {
      this.forbiddenOpenPositionValue = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case RULE_ENABLED:
      if (value == null) {
        unsetRuleEnabled();
      } else {
        setRuleEnabled((Boolean)value);
      }
      break;

    case ALARM_VALUE:
      if (value == null) {
        unsetAlarmValue();
      } else {
        setAlarmValue((HostingRiskRuleItemValue)value);
      }
      break;

    case FORBIDDEN_OPEN_POSITION_VALUE:
      if (value == null) {
        unsetForbiddenOpenPositionValue();
      } else {
        setForbiddenOpenPositionValue((HostingRiskRuleItemValue)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case RULE_ENABLED:
      return Boolean.valueOf(isRuleEnabled());

    case ALARM_VALUE:
      return getAlarmValue();

    case FORBIDDEN_OPEN_POSITION_VALUE:
      return getForbiddenOpenPositionValue();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case RULE_ENABLED:
      return isSetRuleEnabled();
    case ALARM_VALUE:
      return isSetAlarmValue();
    case FORBIDDEN_OPEN_POSITION_VALUE:
      return isSetForbiddenOpenPositionValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingRiskRuleItem)
      return this.equals((HostingRiskRuleItem)that);
    return false;
  }

  public boolean equals(HostingRiskRuleItem that) {
    if (that == null)
      return false;

    boolean this_present_ruleEnabled = true && this.isSetRuleEnabled();
    boolean that_present_ruleEnabled = true && that.isSetRuleEnabled();
    if (this_present_ruleEnabled || that_present_ruleEnabled) {
      if (!(this_present_ruleEnabled && that_present_ruleEnabled))
        return false;
      if (this.ruleEnabled != that.ruleEnabled)
        return false;
    }

    boolean this_present_alarmValue = true && this.isSetAlarmValue();
    boolean that_present_alarmValue = true && that.isSetAlarmValue();
    if (this_present_alarmValue || that_present_alarmValue) {
      if (!(this_present_alarmValue && that_present_alarmValue))
        return false;
      if (!this.alarmValue.equals(that.alarmValue))
        return false;
    }

    boolean this_present_forbiddenOpenPositionValue = true && this.isSetForbiddenOpenPositionValue();
    boolean that_present_forbiddenOpenPositionValue = true && that.isSetForbiddenOpenPositionValue();
    if (this_present_forbiddenOpenPositionValue || that_present_forbiddenOpenPositionValue) {
      if (!(this_present_forbiddenOpenPositionValue && that_present_forbiddenOpenPositionValue))
        return false;
      if (!this.forbiddenOpenPositionValue.equals(that.forbiddenOpenPositionValue))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingRiskRuleItem other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetRuleEnabled()).compareTo(other.isSetRuleEnabled());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRuleEnabled()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ruleEnabled, other.ruleEnabled);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAlarmValue()).compareTo(other.isSetAlarmValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlarmValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.alarmValue, other.alarmValue);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetForbiddenOpenPositionValue()).compareTo(other.isSetForbiddenOpenPositionValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetForbiddenOpenPositionValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.forbiddenOpenPositionValue, other.forbiddenOpenPositionValue);
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
    StringBuilder sb = new StringBuilder("HostingRiskRuleItem(");
    boolean first = true;

    if (isSetRuleEnabled()) {
      sb.append("ruleEnabled:");
      sb.append(this.ruleEnabled);
      first = false;
    }
    if (isSetAlarmValue()) {
      if (!first) sb.append(", ");
      sb.append("alarmValue:");
      if (this.alarmValue == null) {
        sb.append("null");
      } else {
        sb.append(this.alarmValue);
      }
      first = false;
    }
    if (isSetForbiddenOpenPositionValue()) {
      if (!first) sb.append(", ");
      sb.append("forbiddenOpenPositionValue:");
      if (this.forbiddenOpenPositionValue == null) {
        sb.append("null");
      } else {
        sb.append(this.forbiddenOpenPositionValue);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (alarmValue != null) {
      alarmValue.validate();
    }
    if (forbiddenOpenPositionValue != null) {
      forbiddenOpenPositionValue.validate();
    }
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

  private static class HostingRiskRuleItemStandardSchemeFactory implements SchemeFactory {
    public HostingRiskRuleItemStandardScheme getScheme() {
      return new HostingRiskRuleItemStandardScheme();
    }
  }

  private static class HostingRiskRuleItemStandardScheme extends StandardScheme<HostingRiskRuleItem> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingRiskRuleItem struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // RULE_ENABLED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.ruleEnabled = iprot.readBool();
              struct.setRuleEnabledIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ALARM_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.alarmValue = new HostingRiskRuleItemValue();
              struct.alarmValue.read(iprot);
              struct.setAlarmValueIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FORBIDDEN_OPEN_POSITION_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.forbiddenOpenPositionValue = new HostingRiskRuleItemValue();
              struct.forbiddenOpenPositionValue.read(iprot);
              struct.setForbiddenOpenPositionValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingRiskRuleItem struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetRuleEnabled()) {
        oprot.writeFieldBegin(RULE_ENABLED_FIELD_DESC);
        oprot.writeBool(struct.ruleEnabled);
        oprot.writeFieldEnd();
      }
      if (struct.alarmValue != null) {
        if (struct.isSetAlarmValue()) {
          oprot.writeFieldBegin(ALARM_VALUE_FIELD_DESC);
          struct.alarmValue.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.forbiddenOpenPositionValue != null) {
        if (struct.isSetForbiddenOpenPositionValue()) {
          oprot.writeFieldBegin(FORBIDDEN_OPEN_POSITION_VALUE_FIELD_DESC);
          struct.forbiddenOpenPositionValue.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingRiskRuleItemTupleSchemeFactory implements SchemeFactory {
    public HostingRiskRuleItemTupleScheme getScheme() {
      return new HostingRiskRuleItemTupleScheme();
    }
  }

  private static class HostingRiskRuleItemTupleScheme extends TupleScheme<HostingRiskRuleItem> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingRiskRuleItem struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetRuleEnabled()) {
        optionals.set(0);
      }
      if (struct.isSetAlarmValue()) {
        optionals.set(1);
      }
      if (struct.isSetForbiddenOpenPositionValue()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetRuleEnabled()) {
        oprot.writeBool(struct.ruleEnabled);
      }
      if (struct.isSetAlarmValue()) {
        struct.alarmValue.write(oprot);
      }
      if (struct.isSetForbiddenOpenPositionValue()) {
        struct.forbiddenOpenPositionValue.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingRiskRuleItem struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.ruleEnabled = iprot.readBool();
        struct.setRuleEnabledIsSet(true);
      }
      if (incoming.get(1)) {
        struct.alarmValue = new HostingRiskRuleItemValue();
        struct.alarmValue.read(iprot);
        struct.setAlarmValueIsSet(true);
      }
      if (incoming.get(2)) {
        struct.forbiddenOpenPositionValue = new HostingRiskRuleItemValue();
        struct.forbiddenOpenPositionValue.read(iprot);
        struct.setForbiddenOpenPositionValueIsSet(true);
      }
    }
  }

}

