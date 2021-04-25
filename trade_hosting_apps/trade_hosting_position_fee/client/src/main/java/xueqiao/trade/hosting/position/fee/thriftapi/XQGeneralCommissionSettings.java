/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.fee.thriftapi;

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

public class XQGeneralCommissionSettings implements org.apache.thrift.TBase<XQGeneralCommissionSettings, XQGeneralCommissionSettings._Fields>, java.io.Serializable, Cloneable, Comparable<XQGeneralCommissionSettings> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("XQGeneralCommissionSettings");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField COMMISSION_DELTA_FIELD_DESC = new org.apache.thrift.protocol.TField("commissionDelta", org.apache.thrift.protocol.TType.STRUCT, (short)10);
  private static final org.apache.thrift.protocol.TField IS_SYNC_FIELD_DESC = new org.apache.thrift.protocol.TField("isSync", org.apache.thrift.protocol.TType.BOOL, (short)12);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestampMs", org.apache.thrift.protocol.TType.I64, (short)20);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestampMs", org.apache.thrift.protocol.TType.I64, (short)21);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new XQGeneralCommissionSettingsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new XQGeneralCommissionSettingsTupleSchemeFactory());
  }

  public long subAccountId; // optional
  /**
   * 
   * @see FeeCalculateType
   */
  public FeeCalculateType type; // optional
  public CommissionInfo commissionDelta; // optional
  public boolean isSync; // optional
  public long createTimestampMs; // optional
  public long lastmodifyTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    /**
     * 
     * @see FeeCalculateType
     */
    TYPE((short)4, "type"),
    COMMISSION_DELTA((short)10, "commissionDelta"),
    IS_SYNC((short)12, "isSync"),
    CREATE_TIMESTAMP_MS((short)20, "createTimestampMs"),
    LASTMODIFY_TIMESTAMP_MS((short)21, "lastmodifyTimestampMs");

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
        case 1: // SUB_ACCOUNT_ID
          return SUB_ACCOUNT_ID;
        case 4: // TYPE
          return TYPE;
        case 10: // COMMISSION_DELTA
          return COMMISSION_DELTA;
        case 12: // IS_SYNC
          return IS_SYNC;
        case 20: // CREATE_TIMESTAMP_MS
          return CREATE_TIMESTAMP_MS;
        case 21: // LASTMODIFY_TIMESTAMP_MS
          return LASTMODIFY_TIMESTAMP_MS;
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
  private static final int __ISSYNC_ISSET_ID = 1;
  private static final int __CREATETIMESTAMPMS_ISSET_ID = 2;
  private static final int __LASTMODIFYTIMESTAMPMS_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.TYPE,_Fields.COMMISSION_DELTA,_Fields.IS_SYNC,_Fields.CREATE_TIMESTAMP_MS,_Fields.LASTMODIFY_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, FeeCalculateType.class)));
    tmpMap.put(_Fields.COMMISSION_DELTA, new org.apache.thrift.meta_data.FieldMetaData("commissionDelta", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CommissionInfo.class)));
    tmpMap.put(_Fields.IS_SYNC, new org.apache.thrift.meta_data.FieldMetaData("isSync", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("createTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(XQGeneralCommissionSettings.class, metaDataMap);
  }

  public XQGeneralCommissionSettings() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public XQGeneralCommissionSettings(XQGeneralCommissionSettings other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetCommissionDelta()) {
      this.commissionDelta = new CommissionInfo(other.commissionDelta);
    }
    this.isSync = other.isSync;
    this.createTimestampMs = other.createTimestampMs;
    this.lastmodifyTimestampMs = other.lastmodifyTimestampMs;
  }

  public XQGeneralCommissionSettings deepCopy() {
    return new XQGeneralCommissionSettings(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.type = null;
    this.commissionDelta = null;
    setIsSyncIsSet(false);
    this.isSync = false;
    setCreateTimestampMsIsSet(false);
    this.createTimestampMs = 0;
    setLastmodifyTimestampMsIsSet(false);
    this.lastmodifyTimestampMs = 0;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public XQGeneralCommissionSettings setSubAccountId(long subAccountId) {
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
   * @see FeeCalculateType
   */
  public FeeCalculateType getType() {
    return this.type;
  }

  /**
   * 
   * @see FeeCalculateType
   */
  public XQGeneralCommissionSettings setType(FeeCalculateType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public CommissionInfo getCommissionDelta() {
    return this.commissionDelta;
  }

  public XQGeneralCommissionSettings setCommissionDelta(CommissionInfo commissionDelta) {
    this.commissionDelta = commissionDelta;
    return this;
  }

  public void unsetCommissionDelta() {
    this.commissionDelta = null;
  }

  /** Returns true if field commissionDelta is set (has been assigned a value) and false otherwise */
  public boolean isSetCommissionDelta() {
    return this.commissionDelta != null;
  }

  public void setCommissionDeltaIsSet(boolean value) {
    if (!value) {
      this.commissionDelta = null;
    }
  }

  public boolean isIsSync() {
    return this.isSync;
  }

  public XQGeneralCommissionSettings setIsSync(boolean isSync) {
    this.isSync = isSync;
    setIsSyncIsSet(true);
    return this;
  }

  public void unsetIsSync() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ISSYNC_ISSET_ID);
  }

  /** Returns true if field isSync is set (has been assigned a value) and false otherwise */
  public boolean isSetIsSync() {
    return EncodingUtils.testBit(__isset_bitfield, __ISSYNC_ISSET_ID);
  }

  public void setIsSyncIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ISSYNC_ISSET_ID, value);
  }

  public long getCreateTimestampMs() {
    return this.createTimestampMs;
  }

  public XQGeneralCommissionSettings setCreateTimestampMs(long createTimestampMs) {
    this.createTimestampMs = createTimestampMs;
    setCreateTimestampMsIsSet(true);
    return this;
  }

  public void unsetCreateTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CREATETIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field createTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetCreateTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __CREATETIMESTAMPMS_ISSET_ID);
  }

  public void setCreateTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CREATETIMESTAMPMS_ISSET_ID, value);
  }

  public long getLastmodifyTimestampMs() {
    return this.lastmodifyTimestampMs;
  }

  public XQGeneralCommissionSettings setLastmodifyTimestampMs(long lastmodifyTimestampMs) {
    this.lastmodifyTimestampMs = lastmodifyTimestampMs;
    setLastmodifyTimestampMsIsSet(true);
    return this;
  }

  public void unsetLastmodifyTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field lastmodifyTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetLastmodifyTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID);
  }

  public void setLastmodifyTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      if (value == null) {
        unsetSubAccountId();
      } else {
        setSubAccountId((Long)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((FeeCalculateType)value);
      }
      break;

    case COMMISSION_DELTA:
      if (value == null) {
        unsetCommissionDelta();
      } else {
        setCommissionDelta((CommissionInfo)value);
      }
      break;

    case IS_SYNC:
      if (value == null) {
        unsetIsSync();
      } else {
        setIsSync((Boolean)value);
      }
      break;

    case CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetCreateTimestampMs();
      } else {
        setCreateTimestampMs((Long)value);
      }
      break;

    case LASTMODIFY_TIMESTAMP_MS:
      if (value == null) {
        unsetLastmodifyTimestampMs();
      } else {
        setLastmodifyTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case TYPE:
      return getType();

    case COMMISSION_DELTA:
      return getCommissionDelta();

    case IS_SYNC:
      return Boolean.valueOf(isIsSync());

    case CREATE_TIMESTAMP_MS:
      return Long.valueOf(getCreateTimestampMs());

    case LASTMODIFY_TIMESTAMP_MS:
      return Long.valueOf(getLastmodifyTimestampMs());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUB_ACCOUNT_ID:
      return isSetSubAccountId();
    case TYPE:
      return isSetType();
    case COMMISSION_DELTA:
      return isSetCommissionDelta();
    case IS_SYNC:
      return isSetIsSync();
    case CREATE_TIMESTAMP_MS:
      return isSetCreateTimestampMs();
    case LASTMODIFY_TIMESTAMP_MS:
      return isSetLastmodifyTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof XQGeneralCommissionSettings)
      return this.equals((XQGeneralCommissionSettings)that);
    return false;
  }

  public boolean equals(XQGeneralCommissionSettings that) {
    if (that == null)
      return false;

    boolean this_present_subAccountId = true && this.isSetSubAccountId();
    boolean that_present_subAccountId = true && that.isSetSubAccountId();
    if (this_present_subAccountId || that_present_subAccountId) {
      if (!(this_present_subAccountId && that_present_subAccountId))
        return false;
      if (this.subAccountId != that.subAccountId)
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_commissionDelta = true && this.isSetCommissionDelta();
    boolean that_present_commissionDelta = true && that.isSetCommissionDelta();
    if (this_present_commissionDelta || that_present_commissionDelta) {
      if (!(this_present_commissionDelta && that_present_commissionDelta))
        return false;
      if (!this.commissionDelta.equals(that.commissionDelta))
        return false;
    }

    boolean this_present_isSync = true && this.isSetIsSync();
    boolean that_present_isSync = true && that.isSetIsSync();
    if (this_present_isSync || that_present_isSync) {
      if (!(this_present_isSync && that_present_isSync))
        return false;
      if (this.isSync != that.isSync)
        return false;
    }

    boolean this_present_createTimestampMs = true && this.isSetCreateTimestampMs();
    boolean that_present_createTimestampMs = true && that.isSetCreateTimestampMs();
    if (this_present_createTimestampMs || that_present_createTimestampMs) {
      if (!(this_present_createTimestampMs && that_present_createTimestampMs))
        return false;
      if (this.createTimestampMs != that.createTimestampMs)
        return false;
    }

    boolean this_present_lastmodifyTimestampMs = true && this.isSetLastmodifyTimestampMs();
    boolean that_present_lastmodifyTimestampMs = true && that.isSetLastmodifyTimestampMs();
    if (this_present_lastmodifyTimestampMs || that_present_lastmodifyTimestampMs) {
      if (!(this_present_lastmodifyTimestampMs && that_present_lastmodifyTimestampMs))
        return false;
      if (this.lastmodifyTimestampMs != that.lastmodifyTimestampMs)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(XQGeneralCommissionSettings other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommissionDelta()).compareTo(other.isSetCommissionDelta());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommissionDelta()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commissionDelta, other.commissionDelta);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIsSync()).compareTo(other.isSetIsSync());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsSync()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isSync, other.isSync);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCreateTimestampMs()).compareTo(other.isSetCreateTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreateTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createTimestampMs, other.createTimestampMs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLastmodifyTimestampMs()).compareTo(other.isSetLastmodifyTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastmodifyTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastmodifyTimestampMs, other.lastmodifyTimestampMs);
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
    StringBuilder sb = new StringBuilder("XQGeneralCommissionSettings(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
      }
      first = false;
    }
    if (isSetCommissionDelta()) {
      if (!first) sb.append(", ");
      sb.append("commissionDelta:");
      if (this.commissionDelta == null) {
        sb.append("null");
      } else {
        sb.append(this.commissionDelta);
      }
      first = false;
    }
    if (isSetIsSync()) {
      if (!first) sb.append(", ");
      sb.append("isSync:");
      sb.append(this.isSync);
      first = false;
    }
    if (isSetCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("createTimestampMs:");
      sb.append(this.createTimestampMs);
      first = false;
    }
    if (isSetLastmodifyTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("lastmodifyTimestampMs:");
      sb.append(this.lastmodifyTimestampMs);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (commissionDelta != null) {
      commissionDelta.validate();
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

  private static class XQGeneralCommissionSettingsStandardSchemeFactory implements SchemeFactory {
    public XQGeneralCommissionSettingsStandardScheme getScheme() {
      return new XQGeneralCommissionSettingsStandardScheme();
    }
  }

  private static class XQGeneralCommissionSettingsStandardScheme extends StandardScheme<XQGeneralCommissionSettings> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, XQGeneralCommissionSettings struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUB_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.subAccountId = iprot.readI64();
              struct.setSubAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = FeeCalculateType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // COMMISSION_DELTA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.commissionDelta = new CommissionInfo();
              struct.commissionDelta.read(iprot);
              struct.setCommissionDeltaIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 12: // IS_SYNC
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.isSync = iprot.readBool();
              struct.setIsSyncIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 20: // CREATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTimestampMs = iprot.readI64();
              struct.setCreateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 21: // LASTMODIFY_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lastmodifyTimestampMs = iprot.readI64();
              struct.setLastmodifyTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, XQGeneralCommissionSettings struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeI32(struct.type.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.commissionDelta != null) {
        if (struct.isSetCommissionDelta()) {
          oprot.writeFieldBegin(COMMISSION_DELTA_FIELD_DESC);
          struct.commissionDelta.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetIsSync()) {
        oprot.writeFieldBegin(IS_SYNC_FIELD_DESC);
        oprot.writeBool(struct.isSync);
        oprot.writeFieldEnd();
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.createTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        oprot.writeFieldBegin(LASTMODIFY_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.lastmodifyTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class XQGeneralCommissionSettingsTupleSchemeFactory implements SchemeFactory {
    public XQGeneralCommissionSettingsTupleScheme getScheme() {
      return new XQGeneralCommissionSettingsTupleScheme();
    }
  }

  private static class XQGeneralCommissionSettingsTupleScheme extends TupleScheme<XQGeneralCommissionSettings> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, XQGeneralCommissionSettings struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetType()) {
        optionals.set(1);
      }
      if (struct.isSetCommissionDelta()) {
        optionals.set(2);
      }
      if (struct.isSetIsSync()) {
        optionals.set(3);
      }
      if (struct.isSetCreateTimestampMs()) {
        optionals.set(4);
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetCommissionDelta()) {
        struct.commissionDelta.write(oprot);
      }
      if (struct.isSetIsSync()) {
        oprot.writeBool(struct.isSync);
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeI64(struct.createTimestampMs);
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        oprot.writeI64(struct.lastmodifyTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, XQGeneralCommissionSettings struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.type = FeeCalculateType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.commissionDelta = new CommissionInfo();
        struct.commissionDelta.read(iprot);
        struct.setCommissionDeltaIsSet(true);
      }
      if (incoming.get(3)) {
        struct.isSync = iprot.readBool();
        struct.setIsSyncIsSet(true);
      }
      if (incoming.get(4)) {
        struct.createTimestampMs = iprot.readI64();
        struct.setCreateTimestampMsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.lastmodifyTimestampMs = iprot.readI64();
        struct.setLastmodifyTimestampMsIsSet(true);
      }
    }
  }

}

