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

public class UpsideContractCommission implements org.apache.thrift.TBase<UpsideContractCommission, UpsideContractCommission._Fields>, java.io.Serializable, Cloneable, Comparable<UpsideContractCommission> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UpsideContractCommission");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CONTRACT_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("contractInfo", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField COMMISSION_FIELD_DESC = new org.apache.thrift.protocol.TField("commission", org.apache.thrift.protocol.TType.STRUCT, (short)10);
  private static final org.apache.thrift.protocol.TField DATA_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("dataType", org.apache.thrift.protocol.TType.I32, (short)11);
  private static final org.apache.thrift.protocol.TField IS_SYNC_FIELD_DESC = new org.apache.thrift.protocol.TField("isSync", org.apache.thrift.protocol.TType.BOOL, (short)12);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestampMs", org.apache.thrift.protocol.TType.I64, (short)21);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UpsideContractCommissionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UpsideContractCommissionTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public ContractInfo contractInfo; // optional
  public CommissionInfo commission; // optional
  /**
   * 
   * @see UpsideDataType
   */
  public UpsideDataType dataType; // optional
  public boolean isSync; // optional
  public long lastmodifyTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    CONTRACT_INFO((short)2, "contractInfo"),
    COMMISSION((short)10, "commission"),
    /**
     * 
     * @see UpsideDataType
     */
    DATA_TYPE((short)11, "dataType"),
    IS_SYNC((short)12, "isSync"),
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
        case 2: // CONTRACT_INFO
          return CONTRACT_INFO;
        case 10: // COMMISSION
          return COMMISSION;
        case 11: // DATA_TYPE
          return DATA_TYPE;
        case 12: // IS_SYNC
          return IS_SYNC;
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
  private static final int __LASTMODIFYTIMESTAMPMS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.CONTRACT_INFO,_Fields.COMMISSION,_Fields.DATA_TYPE,_Fields.IS_SYNC,_Fields.LASTMODIFY_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CONTRACT_INFO, new org.apache.thrift.meta_data.FieldMetaData("contractInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ContractInfo.class)));
    tmpMap.put(_Fields.COMMISSION, new org.apache.thrift.meta_data.FieldMetaData("commission", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, CommissionInfo.class)));
    tmpMap.put(_Fields.DATA_TYPE, new org.apache.thrift.meta_data.FieldMetaData("dataType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, UpsideDataType.class)));
    tmpMap.put(_Fields.IS_SYNC, new org.apache.thrift.meta_data.FieldMetaData("isSync", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UpsideContractCommission.class, metaDataMap);
  }

  public UpsideContractCommission() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UpsideContractCommission(UpsideContractCommission other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    if (other.isSetContractInfo()) {
      this.contractInfo = new ContractInfo(other.contractInfo);
    }
    if (other.isSetCommission()) {
      this.commission = new CommissionInfo(other.commission);
    }
    if (other.isSetDataType()) {
      this.dataType = other.dataType;
    }
    this.isSync = other.isSync;
    this.lastmodifyTimestampMs = other.lastmodifyTimestampMs;
  }

  public UpsideContractCommission deepCopy() {
    return new UpsideContractCommission(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.contractInfo = null;
    this.commission = null;
    this.dataType = null;
    setIsSyncIsSet(false);
    this.isSync = false;
    setLastmodifyTimestampMsIsSet(false);
    this.lastmodifyTimestampMs = 0;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public UpsideContractCommission setSubAccountId(long subAccountId) {
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

  public ContractInfo getContractInfo() {
    return this.contractInfo;
  }

  public UpsideContractCommission setContractInfo(ContractInfo contractInfo) {
    this.contractInfo = contractInfo;
    return this;
  }

  public void unsetContractInfo() {
    this.contractInfo = null;
  }

  /** Returns true if field contractInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetContractInfo() {
    return this.contractInfo != null;
  }

  public void setContractInfoIsSet(boolean value) {
    if (!value) {
      this.contractInfo = null;
    }
  }

  public CommissionInfo getCommission() {
    return this.commission;
  }

  public UpsideContractCommission setCommission(CommissionInfo commission) {
    this.commission = commission;
    return this;
  }

  public void unsetCommission() {
    this.commission = null;
  }

  /** Returns true if field commission is set (has been assigned a value) and false otherwise */
  public boolean isSetCommission() {
    return this.commission != null;
  }

  public void setCommissionIsSet(boolean value) {
    if (!value) {
      this.commission = null;
    }
  }

  /**
   * 
   * @see UpsideDataType
   */
  public UpsideDataType getDataType() {
    return this.dataType;
  }

  /**
   * 
   * @see UpsideDataType
   */
  public UpsideContractCommission setDataType(UpsideDataType dataType) {
    this.dataType = dataType;
    return this;
  }

  public void unsetDataType() {
    this.dataType = null;
  }

  /** Returns true if field dataType is set (has been assigned a value) and false otherwise */
  public boolean isSetDataType() {
    return this.dataType != null;
  }

  public void setDataTypeIsSet(boolean value) {
    if (!value) {
      this.dataType = null;
    }
  }

  public boolean isIsSync() {
    return this.isSync;
  }

  public UpsideContractCommission setIsSync(boolean isSync) {
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

  public long getLastmodifyTimestampMs() {
    return this.lastmodifyTimestampMs;
  }

  public UpsideContractCommission setLastmodifyTimestampMs(long lastmodifyTimestampMs) {
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

    case CONTRACT_INFO:
      if (value == null) {
        unsetContractInfo();
      } else {
        setContractInfo((ContractInfo)value);
      }
      break;

    case COMMISSION:
      if (value == null) {
        unsetCommission();
      } else {
        setCommission((CommissionInfo)value);
      }
      break;

    case DATA_TYPE:
      if (value == null) {
        unsetDataType();
      } else {
        setDataType((UpsideDataType)value);
      }
      break;

    case IS_SYNC:
      if (value == null) {
        unsetIsSync();
      } else {
        setIsSync((Boolean)value);
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

    case CONTRACT_INFO:
      return getContractInfo();

    case COMMISSION:
      return getCommission();

    case DATA_TYPE:
      return getDataType();

    case IS_SYNC:
      return Boolean.valueOf(isIsSync());

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
    case CONTRACT_INFO:
      return isSetContractInfo();
    case COMMISSION:
      return isSetCommission();
    case DATA_TYPE:
      return isSetDataType();
    case IS_SYNC:
      return isSetIsSync();
    case LASTMODIFY_TIMESTAMP_MS:
      return isSetLastmodifyTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UpsideContractCommission)
      return this.equals((UpsideContractCommission)that);
    return false;
  }

  public boolean equals(UpsideContractCommission that) {
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

    boolean this_present_contractInfo = true && this.isSetContractInfo();
    boolean that_present_contractInfo = true && that.isSetContractInfo();
    if (this_present_contractInfo || that_present_contractInfo) {
      if (!(this_present_contractInfo && that_present_contractInfo))
        return false;
      if (!this.contractInfo.equals(that.contractInfo))
        return false;
    }

    boolean this_present_commission = true && this.isSetCommission();
    boolean that_present_commission = true && that.isSetCommission();
    if (this_present_commission || that_present_commission) {
      if (!(this_present_commission && that_present_commission))
        return false;
      if (!this.commission.equals(that.commission))
        return false;
    }

    boolean this_present_dataType = true && this.isSetDataType();
    boolean that_present_dataType = true && that.isSetDataType();
    if (this_present_dataType || that_present_dataType) {
      if (!(this_present_dataType && that_present_dataType))
        return false;
      if (!this.dataType.equals(that.dataType))
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
  public int compareTo(UpsideContractCommission other) {
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
    lastComparison = Boolean.valueOf(isSetContractInfo()).compareTo(other.isSetContractInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContractInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.contractInfo, other.contractInfo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommission()).compareTo(other.isSetCommission());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommission()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commission, other.commission);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDataType()).compareTo(other.isSetDataType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDataType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dataType, other.dataType);
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
    StringBuilder sb = new StringBuilder("UpsideContractCommission(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetContractInfo()) {
      if (!first) sb.append(", ");
      sb.append("contractInfo:");
      if (this.contractInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.contractInfo);
      }
      first = false;
    }
    if (isSetCommission()) {
      if (!first) sb.append(", ");
      sb.append("commission:");
      if (this.commission == null) {
        sb.append("null");
      } else {
        sb.append(this.commission);
      }
      first = false;
    }
    if (isSetDataType()) {
      if (!first) sb.append(", ");
      sb.append("dataType:");
      if (this.dataType == null) {
        sb.append("null");
      } else {
        sb.append(this.dataType);
      }
      first = false;
    }
    if (isSetIsSync()) {
      if (!first) sb.append(", ");
      sb.append("isSync:");
      sb.append(this.isSync);
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
    if (contractInfo != null) {
      contractInfo.validate();
    }
    if (commission != null) {
      commission.validate();
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

  private static class UpsideContractCommissionStandardSchemeFactory implements SchemeFactory {
    public UpsideContractCommissionStandardScheme getScheme() {
      return new UpsideContractCommissionStandardScheme();
    }
  }

  private static class UpsideContractCommissionStandardScheme extends StandardScheme<UpsideContractCommission> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UpsideContractCommission struct) throws org.apache.thrift.TException {
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
          case 2: // CONTRACT_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.contractInfo = new ContractInfo();
              struct.contractInfo.read(iprot);
              struct.setContractInfoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // COMMISSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.commission = new CommissionInfo();
              struct.commission.read(iprot);
              struct.setCommissionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 11: // DATA_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dataType = UpsideDataType.findByValue(iprot.readI32());
              struct.setDataTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UpsideContractCommission struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.contractInfo != null) {
        if (struct.isSetContractInfo()) {
          oprot.writeFieldBegin(CONTRACT_INFO_FIELD_DESC);
          struct.contractInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.commission != null) {
        if (struct.isSetCommission()) {
          oprot.writeFieldBegin(COMMISSION_FIELD_DESC);
          struct.commission.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.dataType != null) {
        if (struct.isSetDataType()) {
          oprot.writeFieldBegin(DATA_TYPE_FIELD_DESC);
          oprot.writeI32(struct.dataType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetIsSync()) {
        oprot.writeFieldBegin(IS_SYNC_FIELD_DESC);
        oprot.writeBool(struct.isSync);
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

  private static class UpsideContractCommissionTupleSchemeFactory implements SchemeFactory {
    public UpsideContractCommissionTupleScheme getScheme() {
      return new UpsideContractCommissionTupleScheme();
    }
  }

  private static class UpsideContractCommissionTupleScheme extends TupleScheme<UpsideContractCommission> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UpsideContractCommission struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetContractInfo()) {
        optionals.set(1);
      }
      if (struct.isSetCommission()) {
        optionals.set(2);
      }
      if (struct.isSetDataType()) {
        optionals.set(3);
      }
      if (struct.isSetIsSync()) {
        optionals.set(4);
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetContractInfo()) {
        struct.contractInfo.write(oprot);
      }
      if (struct.isSetCommission()) {
        struct.commission.write(oprot);
      }
      if (struct.isSetDataType()) {
        oprot.writeI32(struct.dataType.getValue());
      }
      if (struct.isSetIsSync()) {
        oprot.writeBool(struct.isSync);
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        oprot.writeI64(struct.lastmodifyTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UpsideContractCommission struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.contractInfo = new ContractInfo();
        struct.contractInfo.read(iprot);
        struct.setContractInfoIsSet(true);
      }
      if (incoming.get(2)) {
        struct.commission = new CommissionInfo();
        struct.commission.read(iprot);
        struct.setCommissionIsSet(true);
      }
      if (incoming.get(3)) {
        struct.dataType = UpsideDataType.findByValue(iprot.readI32());
        struct.setDataTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.isSync = iprot.readBool();
        struct.setIsSyncIsSet(true);
      }
      if (incoming.get(5)) {
        struct.lastmodifyTimestampMs = iprot.readI64();
        struct.setLastmodifyTimestampMsIsSet(true);
      }
    }
  }

}

