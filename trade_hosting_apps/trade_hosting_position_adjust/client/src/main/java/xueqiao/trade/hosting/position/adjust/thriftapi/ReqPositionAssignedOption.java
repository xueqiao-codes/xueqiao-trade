/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.adjust.thriftapi;

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
 * 查询持仓分配明细
 */
public class ReqPositionAssignedOption implements org.apache.thrift.TBase<ReqPositionAssignedOption, ReqPositionAssignedOption._Fields>, java.io.Serializable, Cloneable, Comparable<ReqPositionAssignedOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReqPositionAssignedOption");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField INPUT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("inputId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField TRADE_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeAccountId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField SUB_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subUserId", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField ASSIGN_START_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("assignStartTimestamp", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField ASSIGN_END_TIMESTAMP_FIELD_DESC = new org.apache.thrift.protocol.TField("assignEndTimestamp", org.apache.thrift.protocol.TType.I64, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReqPositionAssignedOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReqPositionAssignedOptionTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public long inputId; // optional
  public long tradeAccountId; // optional
  public long subUserId; // optional
  public long sledContractId; // optional
  public long assignStartTimestamp; // optional
  public long assignEndTimestamp; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    INPUT_ID((short)2, "inputId"),
    TRADE_ACCOUNT_ID((short)3, "tradeAccountId"),
    SUB_USER_ID((short)4, "subUserId"),
    SLED_CONTRACT_ID((short)5, "sledContractId"),
    ASSIGN_START_TIMESTAMP((short)6, "assignStartTimestamp"),
    ASSIGN_END_TIMESTAMP((short)7, "assignEndTimestamp");

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
        case 2: // INPUT_ID
          return INPUT_ID;
        case 3: // TRADE_ACCOUNT_ID
          return TRADE_ACCOUNT_ID;
        case 4: // SUB_USER_ID
          return SUB_USER_ID;
        case 5: // SLED_CONTRACT_ID
          return SLED_CONTRACT_ID;
        case 6: // ASSIGN_START_TIMESTAMP
          return ASSIGN_START_TIMESTAMP;
        case 7: // ASSIGN_END_TIMESTAMP
          return ASSIGN_END_TIMESTAMP;
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
  private static final int __INPUTID_ISSET_ID = 1;
  private static final int __TRADEACCOUNTID_ISSET_ID = 2;
  private static final int __SUBUSERID_ISSET_ID = 3;
  private static final int __SLEDCONTRACTID_ISSET_ID = 4;
  private static final int __ASSIGNSTARTTIMESTAMP_ISSET_ID = 5;
  private static final int __ASSIGNENDTIMESTAMP_ISSET_ID = 6;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.INPUT_ID,_Fields.TRADE_ACCOUNT_ID,_Fields.SUB_USER_ID,_Fields.SLED_CONTRACT_ID,_Fields.ASSIGN_START_TIMESTAMP,_Fields.ASSIGN_END_TIMESTAMP};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.INPUT_ID, new org.apache.thrift.meta_data.FieldMetaData("inputId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TRADE_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("tradeAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SUB_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("subUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ASSIGN_START_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("assignStartTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ASSIGN_END_TIMESTAMP, new org.apache.thrift.meta_data.FieldMetaData("assignEndTimestamp", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReqPositionAssignedOption.class, metaDataMap);
  }

  public ReqPositionAssignedOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReqPositionAssignedOption(ReqPositionAssignedOption other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    this.inputId = other.inputId;
    this.tradeAccountId = other.tradeAccountId;
    this.subUserId = other.subUserId;
    this.sledContractId = other.sledContractId;
    this.assignStartTimestamp = other.assignStartTimestamp;
    this.assignEndTimestamp = other.assignEndTimestamp;
  }

  public ReqPositionAssignedOption deepCopy() {
    return new ReqPositionAssignedOption(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    setInputIdIsSet(false);
    this.inputId = 0;
    setTradeAccountIdIsSet(false);
    this.tradeAccountId = 0;
    setSubUserIdIsSet(false);
    this.subUserId = 0;
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
    setAssignStartTimestampIsSet(false);
    this.assignStartTimestamp = 0;
    setAssignEndTimestampIsSet(false);
    this.assignEndTimestamp = 0;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public ReqPositionAssignedOption setSubAccountId(long subAccountId) {
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

  public long getInputId() {
    return this.inputId;
  }

  public ReqPositionAssignedOption setInputId(long inputId) {
    this.inputId = inputId;
    setInputIdIsSet(true);
    return this;
  }

  public void unsetInputId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __INPUTID_ISSET_ID);
  }

  /** Returns true if field inputId is set (has been assigned a value) and false otherwise */
  public boolean isSetInputId() {
    return EncodingUtils.testBit(__isset_bitfield, __INPUTID_ISSET_ID);
  }

  public void setInputIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __INPUTID_ISSET_ID, value);
  }

  public long getTradeAccountId() {
    return this.tradeAccountId;
  }

  public ReqPositionAssignedOption setTradeAccountId(long tradeAccountId) {
    this.tradeAccountId = tradeAccountId;
    setTradeAccountIdIsSet(true);
    return this;
  }

  public void unsetTradeAccountId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TRADEACCOUNTID_ISSET_ID);
  }

  /** Returns true if field tradeAccountId is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeAccountId() {
    return EncodingUtils.testBit(__isset_bitfield, __TRADEACCOUNTID_ISSET_ID);
  }

  public void setTradeAccountIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TRADEACCOUNTID_ISSET_ID, value);
  }

  public long getSubUserId() {
    return this.subUserId;
  }

  public ReqPositionAssignedOption setSubUserId(long subUserId) {
    this.subUserId = subUserId;
    setSubUserIdIsSet(true);
    return this;
  }

  public void unsetSubUserId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUBUSERID_ISSET_ID);
  }

  /** Returns true if field subUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetSubUserId() {
    return EncodingUtils.testBit(__isset_bitfield, __SUBUSERID_ISSET_ID);
  }

  public void setSubUserIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUBUSERID_ISSET_ID, value);
  }

  public long getSledContractId() {
    return this.sledContractId;
  }

  public ReqPositionAssignedOption setSledContractId(long sledContractId) {
    this.sledContractId = sledContractId;
    setSledContractIdIsSet(true);
    return this;
  }

  public void unsetSledContractId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLEDCONTRACTID_ISSET_ID);
  }

  /** Returns true if field sledContractId is set (has been assigned a value) and false otherwise */
  public boolean isSetSledContractId() {
    return EncodingUtils.testBit(__isset_bitfield, __SLEDCONTRACTID_ISSET_ID);
  }

  public void setSledContractIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLEDCONTRACTID_ISSET_ID, value);
  }

  public long getAssignStartTimestamp() {
    return this.assignStartTimestamp;
  }

  public ReqPositionAssignedOption setAssignStartTimestamp(long assignStartTimestamp) {
    this.assignStartTimestamp = assignStartTimestamp;
    setAssignStartTimestampIsSet(true);
    return this;
  }

  public void unsetAssignStartTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ASSIGNSTARTTIMESTAMP_ISSET_ID);
  }

  /** Returns true if field assignStartTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetAssignStartTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __ASSIGNSTARTTIMESTAMP_ISSET_ID);
  }

  public void setAssignStartTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ASSIGNSTARTTIMESTAMP_ISSET_ID, value);
  }

  public long getAssignEndTimestamp() {
    return this.assignEndTimestamp;
  }

  public ReqPositionAssignedOption setAssignEndTimestamp(long assignEndTimestamp) {
    this.assignEndTimestamp = assignEndTimestamp;
    setAssignEndTimestampIsSet(true);
    return this;
  }

  public void unsetAssignEndTimestamp() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ASSIGNENDTIMESTAMP_ISSET_ID);
  }

  /** Returns true if field assignEndTimestamp is set (has been assigned a value) and false otherwise */
  public boolean isSetAssignEndTimestamp() {
    return EncodingUtils.testBit(__isset_bitfield, __ASSIGNENDTIMESTAMP_ISSET_ID);
  }

  public void setAssignEndTimestampIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ASSIGNENDTIMESTAMP_ISSET_ID, value);
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

    case INPUT_ID:
      if (value == null) {
        unsetInputId();
      } else {
        setInputId((Long)value);
      }
      break;

    case TRADE_ACCOUNT_ID:
      if (value == null) {
        unsetTradeAccountId();
      } else {
        setTradeAccountId((Long)value);
      }
      break;

    case SUB_USER_ID:
      if (value == null) {
        unsetSubUserId();
      } else {
        setSubUserId((Long)value);
      }
      break;

    case SLED_CONTRACT_ID:
      if (value == null) {
        unsetSledContractId();
      } else {
        setSledContractId((Long)value);
      }
      break;

    case ASSIGN_START_TIMESTAMP:
      if (value == null) {
        unsetAssignStartTimestamp();
      } else {
        setAssignStartTimestamp((Long)value);
      }
      break;

    case ASSIGN_END_TIMESTAMP:
      if (value == null) {
        unsetAssignEndTimestamp();
      } else {
        setAssignEndTimestamp((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case INPUT_ID:
      return Long.valueOf(getInputId());

    case TRADE_ACCOUNT_ID:
      return Long.valueOf(getTradeAccountId());

    case SUB_USER_ID:
      return Long.valueOf(getSubUserId());

    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    case ASSIGN_START_TIMESTAMP:
      return Long.valueOf(getAssignStartTimestamp());

    case ASSIGN_END_TIMESTAMP:
      return Long.valueOf(getAssignEndTimestamp());

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
    case INPUT_ID:
      return isSetInputId();
    case TRADE_ACCOUNT_ID:
      return isSetTradeAccountId();
    case SUB_USER_ID:
      return isSetSubUserId();
    case SLED_CONTRACT_ID:
      return isSetSledContractId();
    case ASSIGN_START_TIMESTAMP:
      return isSetAssignStartTimestamp();
    case ASSIGN_END_TIMESTAMP:
      return isSetAssignEndTimestamp();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReqPositionAssignedOption)
      return this.equals((ReqPositionAssignedOption)that);
    return false;
  }

  public boolean equals(ReqPositionAssignedOption that) {
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

    boolean this_present_inputId = true && this.isSetInputId();
    boolean that_present_inputId = true && that.isSetInputId();
    if (this_present_inputId || that_present_inputId) {
      if (!(this_present_inputId && that_present_inputId))
        return false;
      if (this.inputId != that.inputId)
        return false;
    }

    boolean this_present_tradeAccountId = true && this.isSetTradeAccountId();
    boolean that_present_tradeAccountId = true && that.isSetTradeAccountId();
    if (this_present_tradeAccountId || that_present_tradeAccountId) {
      if (!(this_present_tradeAccountId && that_present_tradeAccountId))
        return false;
      if (this.tradeAccountId != that.tradeAccountId)
        return false;
    }

    boolean this_present_subUserId = true && this.isSetSubUserId();
    boolean that_present_subUserId = true && that.isSetSubUserId();
    if (this_present_subUserId || that_present_subUserId) {
      if (!(this_present_subUserId && that_present_subUserId))
        return false;
      if (this.subUserId != that.subUserId)
        return false;
    }

    boolean this_present_sledContractId = true && this.isSetSledContractId();
    boolean that_present_sledContractId = true && that.isSetSledContractId();
    if (this_present_sledContractId || that_present_sledContractId) {
      if (!(this_present_sledContractId && that_present_sledContractId))
        return false;
      if (this.sledContractId != that.sledContractId)
        return false;
    }

    boolean this_present_assignStartTimestamp = true && this.isSetAssignStartTimestamp();
    boolean that_present_assignStartTimestamp = true && that.isSetAssignStartTimestamp();
    if (this_present_assignStartTimestamp || that_present_assignStartTimestamp) {
      if (!(this_present_assignStartTimestamp && that_present_assignStartTimestamp))
        return false;
      if (this.assignStartTimestamp != that.assignStartTimestamp)
        return false;
    }

    boolean this_present_assignEndTimestamp = true && this.isSetAssignEndTimestamp();
    boolean that_present_assignEndTimestamp = true && that.isSetAssignEndTimestamp();
    if (this_present_assignEndTimestamp || that_present_assignEndTimestamp) {
      if (!(this_present_assignEndTimestamp && that_present_assignEndTimestamp))
        return false;
      if (this.assignEndTimestamp != that.assignEndTimestamp)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ReqPositionAssignedOption other) {
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
    lastComparison = Boolean.valueOf(isSetInputId()).compareTo(other.isSetInputId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInputId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.inputId, other.inputId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTradeAccountId()).compareTo(other.isSetTradeAccountId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeAccountId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeAccountId, other.tradeAccountId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubUserId()).compareTo(other.isSetSubUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subUserId, other.subUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSledContractId()).compareTo(other.isSetSledContractId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledContractId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledContractId, other.sledContractId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAssignStartTimestamp()).compareTo(other.isSetAssignStartTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAssignStartTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.assignStartTimestamp, other.assignStartTimestamp);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAssignEndTimestamp()).compareTo(other.isSetAssignEndTimestamp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAssignEndTimestamp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.assignEndTimestamp, other.assignEndTimestamp);
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
    StringBuilder sb = new StringBuilder("ReqPositionAssignedOption(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetInputId()) {
      if (!first) sb.append(", ");
      sb.append("inputId:");
      sb.append(this.inputId);
      first = false;
    }
    if (isSetTradeAccountId()) {
      if (!first) sb.append(", ");
      sb.append("tradeAccountId:");
      sb.append(this.tradeAccountId);
      first = false;
    }
    if (isSetSubUserId()) {
      if (!first) sb.append(", ");
      sb.append("subUserId:");
      sb.append(this.subUserId);
      first = false;
    }
    if (isSetSledContractId()) {
      if (!first) sb.append(", ");
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
      first = false;
    }
    if (isSetAssignStartTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("assignStartTimestamp:");
      sb.append(this.assignStartTimestamp);
      first = false;
    }
    if (isSetAssignEndTimestamp()) {
      if (!first) sb.append(", ");
      sb.append("assignEndTimestamp:");
      sb.append(this.assignEndTimestamp);
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

  private static class ReqPositionAssignedOptionStandardSchemeFactory implements SchemeFactory {
    public ReqPositionAssignedOptionStandardScheme getScheme() {
      return new ReqPositionAssignedOptionStandardScheme();
    }
  }

  private static class ReqPositionAssignedOptionStandardScheme extends StandardScheme<ReqPositionAssignedOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReqPositionAssignedOption struct) throws org.apache.thrift.TException {
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
          case 2: // INPUT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.inputId = iprot.readI64();
              struct.setInputIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TRADE_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.tradeAccountId = iprot.readI64();
              struct.setTradeAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SUB_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.subUserId = iprot.readI64();
              struct.setSubUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SLED_CONTRACT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sledContractId = iprot.readI64();
              struct.setSledContractIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ASSIGN_START_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.assignStartTimestamp = iprot.readI64();
              struct.setAssignStartTimestampIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // ASSIGN_END_TIMESTAMP
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.assignEndTimestamp = iprot.readI64();
              struct.setAssignEndTimestampIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReqPositionAssignedOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetInputId()) {
        oprot.writeFieldBegin(INPUT_ID_FIELD_DESC);
        oprot.writeI64(struct.inputId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetTradeAccountId()) {
        oprot.writeFieldBegin(TRADE_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.tradeAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSubUserId()) {
        oprot.writeFieldBegin(SUB_USER_ID_FIELD_DESC);
        oprot.writeI64(struct.subUserId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAssignStartTimestamp()) {
        oprot.writeFieldBegin(ASSIGN_START_TIMESTAMP_FIELD_DESC);
        oprot.writeI64(struct.assignStartTimestamp);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAssignEndTimestamp()) {
        oprot.writeFieldBegin(ASSIGN_END_TIMESTAMP_FIELD_DESC);
        oprot.writeI64(struct.assignEndTimestamp);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReqPositionAssignedOptionTupleSchemeFactory implements SchemeFactory {
    public ReqPositionAssignedOptionTupleScheme getScheme() {
      return new ReqPositionAssignedOptionTupleScheme();
    }
  }

  private static class ReqPositionAssignedOptionTupleScheme extends TupleScheme<ReqPositionAssignedOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReqPositionAssignedOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetInputId()) {
        optionals.set(1);
      }
      if (struct.isSetTradeAccountId()) {
        optionals.set(2);
      }
      if (struct.isSetSubUserId()) {
        optionals.set(3);
      }
      if (struct.isSetSledContractId()) {
        optionals.set(4);
      }
      if (struct.isSetAssignStartTimestamp()) {
        optionals.set(5);
      }
      if (struct.isSetAssignEndTimestamp()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetInputId()) {
        oprot.writeI64(struct.inputId);
      }
      if (struct.isSetTradeAccountId()) {
        oprot.writeI64(struct.tradeAccountId);
      }
      if (struct.isSetSubUserId()) {
        oprot.writeI64(struct.subUserId);
      }
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
      if (struct.isSetAssignStartTimestamp()) {
        oprot.writeI64(struct.assignStartTimestamp);
      }
      if (struct.isSetAssignEndTimestamp()) {
        oprot.writeI64(struct.assignEndTimestamp);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReqPositionAssignedOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.inputId = iprot.readI64();
        struct.setInputIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.tradeAccountId = iprot.readI64();
        struct.setTradeAccountIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.subUserId = iprot.readI64();
        struct.setSubUserIdIsSet(true);
      }
      if (incoming.get(4)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
      if (incoming.get(5)) {
        struct.assignStartTimestamp = iprot.readI64();
        struct.setAssignStartTimestampIsSet(true);
      }
      if (incoming.get(6)) {
        struct.assignEndTimestamp = iprot.readI64();
        struct.setAssignEndTimestampIsSet(true);
      }
    }
  }

}

