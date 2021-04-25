/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.statis;

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

public class StatPositionSummaryEx implements org.apache.thrift.TBase<StatPositionSummaryEx, StatPositionSummaryEx._Fields>, java.io.Serializable, Cloneable, Comparable<StatPositionSummaryEx> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StatPositionSummaryEx");

  private static final org.apache.thrift.protocol.TField TARGET_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("targetKey", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField TARGET_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("targetType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField POSITION_SUMMARY_FIELD_DESC = new org.apache.thrift.protocol.TField("positionSummary", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField POSITION_DYNAMIC_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("positionDynamicInfo", org.apache.thrift.protocol.TType.STRUCT, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StatPositionSummaryExStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StatPositionSummaryExTupleSchemeFactory());
  }

  public String targetKey; // optional
  public long subAccountId; // optional
  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType; // optional
  public StatPositionSummary positionSummary; // optional
  public StatPositionDynamicInfo positionDynamicInfo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TARGET_KEY((short)1, "targetKey"),
    SUB_ACCOUNT_ID((short)2, "subAccountId"),
    /**
     * 
     * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
     */
    TARGET_TYPE((short)3, "targetType"),
    POSITION_SUMMARY((short)5, "positionSummary"),
    POSITION_DYNAMIC_INFO((short)6, "positionDynamicInfo");

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
        case 1: // TARGET_KEY
          return TARGET_KEY;
        case 2: // SUB_ACCOUNT_ID
          return SUB_ACCOUNT_ID;
        case 3: // TARGET_TYPE
          return TARGET_TYPE;
        case 5: // POSITION_SUMMARY
          return POSITION_SUMMARY;
        case 6: // POSITION_DYNAMIC_INFO
          return POSITION_DYNAMIC_INFO;
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
  private static final int __SUBACCOUNTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TARGET_KEY,_Fields.SUB_ACCOUNT_ID,_Fields.TARGET_TYPE,_Fields.POSITION_SUMMARY,_Fields.POSITION_DYNAMIC_INFO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TARGET_KEY, new org.apache.thrift.meta_data.FieldMetaData("targetKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TARGET_TYPE, new org.apache.thrift.meta_data.FieldMetaData("targetType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.class)));
    tmpMap.put(_Fields.POSITION_SUMMARY, new org.apache.thrift.meta_data.FieldMetaData("positionSummary", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, StatPositionSummary.class)));
    tmpMap.put(_Fields.POSITION_DYNAMIC_INFO, new org.apache.thrift.meta_data.FieldMetaData("positionDynamicInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, StatPositionDynamicInfo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatPositionSummaryEx.class, metaDataMap);
  }

  public StatPositionSummaryEx() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StatPositionSummaryEx(StatPositionSummaryEx other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTargetKey()) {
      this.targetKey = other.targetKey;
    }
    this.subAccountId = other.subAccountId;
    if (other.isSetTargetType()) {
      this.targetType = other.targetType;
    }
    if (other.isSetPositionSummary()) {
      this.positionSummary = new StatPositionSummary(other.positionSummary);
    }
    if (other.isSetPositionDynamicInfo()) {
      this.positionDynamicInfo = new StatPositionDynamicInfo(other.positionDynamicInfo);
    }
  }

  public StatPositionSummaryEx deepCopy() {
    return new StatPositionSummaryEx(this);
  }

  @Override
  public void clear() {
    this.targetKey = null;
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.targetType = null;
    this.positionSummary = null;
    this.positionDynamicInfo = null;
  }

  public String getTargetKey() {
    return this.targetKey;
  }

  public StatPositionSummaryEx setTargetKey(String targetKey) {
    this.targetKey = targetKey;
    return this;
  }

  public void unsetTargetKey() {
    this.targetKey = null;
  }

  /** Returns true if field targetKey is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetKey() {
    return this.targetKey != null;
  }

  public void setTargetKeyIsSet(boolean value) {
    if (!value) {
      this.targetKey = null;
    }
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public StatPositionSummaryEx setSubAccountId(long subAccountId) {
    this.subAccountId = subAccountId;
    setSubAccountIdIsSet(true);
    return this;
  }

  public void unsetSubAccountId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUBACCOUNTID_ISSET_ID);
  }

  /** Returns true if field subAccountId is set (has been assigned a value) and false otherwise */
  public boolean isSetSubAccountId() {
    return EncodingUtils.testBit(__isset_bitfield, __SUBACCOUNTID_ISSET_ID);
  }

  public void setSubAccountIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUBACCOUNTID_ISSET_ID, value);
  }

  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType getTargetType() {
    return this.targetType;
  }

  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public StatPositionSummaryEx setTargetType(xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) {
    this.targetType = targetType;
    return this;
  }

  public void unsetTargetType() {
    this.targetType = null;
  }

  /** Returns true if field targetType is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetType() {
    return this.targetType != null;
  }

  public void setTargetTypeIsSet(boolean value) {
    if (!value) {
      this.targetType = null;
    }
  }

  public StatPositionSummary getPositionSummary() {
    return this.positionSummary;
  }

  public StatPositionSummaryEx setPositionSummary(StatPositionSummary positionSummary) {
    this.positionSummary = positionSummary;
    return this;
  }

  public void unsetPositionSummary() {
    this.positionSummary = null;
  }

  /** Returns true if field positionSummary is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionSummary() {
    return this.positionSummary != null;
  }

  public void setPositionSummaryIsSet(boolean value) {
    if (!value) {
      this.positionSummary = null;
    }
  }

  public StatPositionDynamicInfo getPositionDynamicInfo() {
    return this.positionDynamicInfo;
  }

  public StatPositionSummaryEx setPositionDynamicInfo(StatPositionDynamicInfo positionDynamicInfo) {
    this.positionDynamicInfo = positionDynamicInfo;
    return this;
  }

  public void unsetPositionDynamicInfo() {
    this.positionDynamicInfo = null;
  }

  /** Returns true if field positionDynamicInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionDynamicInfo() {
    return this.positionDynamicInfo != null;
  }

  public void setPositionDynamicInfoIsSet(boolean value) {
    if (!value) {
      this.positionDynamicInfo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TARGET_KEY:
      if (value == null) {
        unsetTargetKey();
      } else {
        setTargetKey((String)value);
      }
      break;

    case SUB_ACCOUNT_ID:
      if (value == null) {
        unsetSubAccountId();
      } else {
        setSubAccountId((Long)value);
      }
      break;

    case TARGET_TYPE:
      if (value == null) {
        unsetTargetType();
      } else {
        setTargetType((xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType)value);
      }
      break;

    case POSITION_SUMMARY:
      if (value == null) {
        unsetPositionSummary();
      } else {
        setPositionSummary((StatPositionSummary)value);
      }
      break;

    case POSITION_DYNAMIC_INFO:
      if (value == null) {
        unsetPositionDynamicInfo();
      } else {
        setPositionDynamicInfo((StatPositionDynamicInfo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TARGET_KEY:
      return getTargetKey();

    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case TARGET_TYPE:
      return getTargetType();

    case POSITION_SUMMARY:
      return getPositionSummary();

    case POSITION_DYNAMIC_INFO:
      return getPositionDynamicInfo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TARGET_KEY:
      return isSetTargetKey();
    case SUB_ACCOUNT_ID:
      return isSetSubAccountId();
    case TARGET_TYPE:
      return isSetTargetType();
    case POSITION_SUMMARY:
      return isSetPositionSummary();
    case POSITION_DYNAMIC_INFO:
      return isSetPositionDynamicInfo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StatPositionSummaryEx)
      return this.equals((StatPositionSummaryEx)that);
    return false;
  }

  public boolean equals(StatPositionSummaryEx that) {
    if (that == null)
      return false;

    boolean this_present_targetKey = true && this.isSetTargetKey();
    boolean that_present_targetKey = true && that.isSetTargetKey();
    if (this_present_targetKey || that_present_targetKey) {
      if (!(this_present_targetKey && that_present_targetKey))
        return false;
      if (!this.targetKey.equals(that.targetKey))
        return false;
    }

    boolean this_present_subAccountId = true && this.isSetSubAccountId();
    boolean that_present_subAccountId = true && that.isSetSubAccountId();
    if (this_present_subAccountId || that_present_subAccountId) {
      if (!(this_present_subAccountId && that_present_subAccountId))
        return false;
      if (this.subAccountId != that.subAccountId)
        return false;
    }

    boolean this_present_targetType = true && this.isSetTargetType();
    boolean that_present_targetType = true && that.isSetTargetType();
    if (this_present_targetType || that_present_targetType) {
      if (!(this_present_targetType && that_present_targetType))
        return false;
      if (!this.targetType.equals(that.targetType))
        return false;
    }

    boolean this_present_positionSummary = true && this.isSetPositionSummary();
    boolean that_present_positionSummary = true && that.isSetPositionSummary();
    if (this_present_positionSummary || that_present_positionSummary) {
      if (!(this_present_positionSummary && that_present_positionSummary))
        return false;
      if (!this.positionSummary.equals(that.positionSummary))
        return false;
    }

    boolean this_present_positionDynamicInfo = true && this.isSetPositionDynamicInfo();
    boolean that_present_positionDynamicInfo = true && that.isSetPositionDynamicInfo();
    if (this_present_positionDynamicInfo || that_present_positionDynamicInfo) {
      if (!(this_present_positionDynamicInfo && that_present_positionDynamicInfo))
        return false;
      if (!this.positionDynamicInfo.equals(that.positionDynamicInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StatPositionSummaryEx other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTargetKey()).compareTo(other.isSetTargetKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetKey, other.targetKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubAccountId()).compareTo(other.isSetSubAccountId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubAccountId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subAccountId, other.subAccountId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTargetType()).compareTo(other.isSetTargetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetType, other.targetType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionSummary()).compareTo(other.isSetPositionSummary());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionSummary()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionSummary, other.positionSummary);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionDynamicInfo()).compareTo(other.isSetPositionDynamicInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionDynamicInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionDynamicInfo, other.positionDynamicInfo);
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
    StringBuilder sb = new StringBuilder("StatPositionSummaryEx(");
    boolean first = true;

    if (isSetTargetKey()) {
      sb.append("targetKey:");
      if (this.targetKey == null) {
        sb.append("null");
      } else {
        sb.append(this.targetKey);
      }
      first = false;
    }
    if (isSetSubAccountId()) {
      if (!first) sb.append(", ");
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetTargetType()) {
      if (!first) sb.append(", ");
      sb.append("targetType:");
      if (this.targetType == null) {
        sb.append("null");
      } else {
        sb.append(this.targetType);
      }
      first = false;
    }
    if (isSetPositionSummary()) {
      if (!first) sb.append(", ");
      sb.append("positionSummary:");
      if (this.positionSummary == null) {
        sb.append("null");
      } else {
        sb.append(this.positionSummary);
      }
      first = false;
    }
    if (isSetPositionDynamicInfo()) {
      if (!first) sb.append(", ");
      sb.append("positionDynamicInfo:");
      if (this.positionDynamicInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.positionDynamicInfo);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (positionSummary != null) {
      positionSummary.validate();
    }
    if (positionDynamicInfo != null) {
      positionDynamicInfo.validate();
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

  private static class StatPositionSummaryExStandardSchemeFactory implements SchemeFactory {
    public StatPositionSummaryExStandardScheme getScheme() {
      return new StatPositionSummaryExStandardScheme();
    }
  }

  private static class StatPositionSummaryExStandardScheme extends StandardScheme<StatPositionSummaryEx> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StatPositionSummaryEx struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TARGET_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.targetKey = iprot.readString();
              struct.setTargetKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SUB_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.subAccountId = iprot.readI64();
              struct.setSubAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TARGET_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.targetType = xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.findByValue(iprot.readI32());
              struct.setTargetTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // POSITION_SUMMARY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.positionSummary = new StatPositionSummary();
              struct.positionSummary.read(iprot);
              struct.setPositionSummaryIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // POSITION_DYNAMIC_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.positionDynamicInfo = new StatPositionDynamicInfo();
              struct.positionDynamicInfo.read(iprot);
              struct.setPositionDynamicInfoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StatPositionSummaryEx struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.targetKey != null) {
        if (struct.isSetTargetKey()) {
          oprot.writeFieldBegin(TARGET_KEY_FIELD_DESC);
          oprot.writeString(struct.targetKey);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.targetType != null) {
        if (struct.isSetTargetType()) {
          oprot.writeFieldBegin(TARGET_TYPE_FIELD_DESC);
          oprot.writeI32(struct.targetType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.positionSummary != null) {
        if (struct.isSetPositionSummary()) {
          oprot.writeFieldBegin(POSITION_SUMMARY_FIELD_DESC);
          struct.positionSummary.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.positionDynamicInfo != null) {
        if (struct.isSetPositionDynamicInfo()) {
          oprot.writeFieldBegin(POSITION_DYNAMIC_INFO_FIELD_DESC);
          struct.positionDynamicInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StatPositionSummaryExTupleSchemeFactory implements SchemeFactory {
    public StatPositionSummaryExTupleScheme getScheme() {
      return new StatPositionSummaryExTupleScheme();
    }
  }

  private static class StatPositionSummaryExTupleScheme extends TupleScheme<StatPositionSummaryEx> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StatPositionSummaryEx struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTargetKey()) {
        optionals.set(0);
      }
      if (struct.isSetSubAccountId()) {
        optionals.set(1);
      }
      if (struct.isSetTargetType()) {
        optionals.set(2);
      }
      if (struct.isSetPositionSummary()) {
        optionals.set(3);
      }
      if (struct.isSetPositionDynamicInfo()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTargetKey()) {
        oprot.writeString(struct.targetKey);
      }
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetTargetType()) {
        oprot.writeI32(struct.targetType.getValue());
      }
      if (struct.isSetPositionSummary()) {
        struct.positionSummary.write(oprot);
      }
      if (struct.isSetPositionDynamicInfo()) {
        struct.positionDynamicInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StatPositionSummaryEx struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.targetKey = iprot.readString();
        struct.setTargetKeyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.targetType = xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.findByValue(iprot.readI32());
        struct.setTargetTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.positionSummary = new StatPositionSummary();
        struct.positionSummary.read(iprot);
        struct.setPositionSummaryIsSet(true);
      }
      if (incoming.get(4)) {
        struct.positionDynamicInfo = new StatPositionDynamicInfo();
        struct.positionDynamicInfo.read(iprot);
        struct.setPositionDynamicInfoIsSet(true);
      }
    }
  }

}

