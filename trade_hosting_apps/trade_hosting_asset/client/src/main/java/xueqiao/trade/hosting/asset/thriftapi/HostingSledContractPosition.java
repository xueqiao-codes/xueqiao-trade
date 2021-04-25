/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.asset.thriftapi;

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
 * 实时某一时刻雪橇合约持仓
 */
public class HostingSledContractPosition implements org.apache.thrift.TBase<HostingSledContractPosition, HostingSledContractPosition._Fields>, java.io.Serializable, Cloneable, Comparable<HostingSledContractPosition> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingSledContractPosition");

  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField SLED_COMMODITY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledCommodityId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField CURRENCY_FIELD_DESC = new org.apache.thrift.protocol.TField("currency", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField POSITION_VOLUME_FIELD_DESC = new org.apache.thrift.protocol.TField("positionVolume", org.apache.thrift.protocol.TType.STRUCT, (short)5);
  private static final org.apache.thrift.protocol.TField POSITION_FUND_FIELD_DESC = new org.apache.thrift.protocol.TField("positionFund", org.apache.thrift.protocol.TType.STRUCT, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingSledContractPositionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingSledContractPositionTupleSchemeFactory());
  }

  public long sledContractId; // optional
  public long subAccountId; // optional
  public long sledCommodityId; // optional
  public String currency; // optional
  public HostingPositionVolume positionVolume; // optional
  public HostingPositionFund positionFund; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLED_CONTRACT_ID((short)1, "sledContractId"),
    SUB_ACCOUNT_ID((short)2, "subAccountId"),
    SLED_COMMODITY_ID((short)3, "sledCommodityId"),
    CURRENCY((short)4, "currency"),
    POSITION_VOLUME((short)5, "positionVolume"),
    POSITION_FUND((short)6, "positionFund");

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
        case 1: // SLED_CONTRACT_ID
          return SLED_CONTRACT_ID;
        case 2: // SUB_ACCOUNT_ID
          return SUB_ACCOUNT_ID;
        case 3: // SLED_COMMODITY_ID
          return SLED_COMMODITY_ID;
        case 4: // CURRENCY
          return CURRENCY;
        case 5: // POSITION_VOLUME
          return POSITION_VOLUME;
        case 6: // POSITION_FUND
          return POSITION_FUND;
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
  private static final int __SLEDCONTRACTID_ISSET_ID = 0;
  private static final int __SUBACCOUNTID_ISSET_ID = 1;
  private static final int __SLEDCOMMODITYID_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SLED_CONTRACT_ID,_Fields.SUB_ACCOUNT_ID,_Fields.SLED_COMMODITY_ID,_Fields.CURRENCY,_Fields.POSITION_VOLUME,_Fields.POSITION_FUND};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_COMMODITY_ID, new org.apache.thrift.meta_data.FieldMetaData("sledCommodityId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CURRENCY, new org.apache.thrift.meta_data.FieldMetaData("currency", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.POSITION_VOLUME, new org.apache.thrift.meta_data.FieldMetaData("positionVolume", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingPositionVolume.class)));
    tmpMap.put(_Fields.POSITION_FUND, new org.apache.thrift.meta_data.FieldMetaData("positionFund", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingPositionFund.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingSledContractPosition.class, metaDataMap);
  }

  public HostingSledContractPosition() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingSledContractPosition(HostingSledContractPosition other) {
    __isset_bitfield = other.__isset_bitfield;
    this.sledContractId = other.sledContractId;
    this.subAccountId = other.subAccountId;
    this.sledCommodityId = other.sledCommodityId;
    if (other.isSetCurrency()) {
      this.currency = other.currency;
    }
    if (other.isSetPositionVolume()) {
      this.positionVolume = new HostingPositionVolume(other.positionVolume);
    }
    if (other.isSetPositionFund()) {
      this.positionFund = new HostingPositionFund(other.positionFund);
    }
  }

  public HostingSledContractPosition deepCopy() {
    return new HostingSledContractPosition(this);
  }

  @Override
  public void clear() {
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    setSledCommodityIdIsSet(false);
    this.sledCommodityId = 0;
    this.currency = null;
    this.positionVolume = null;
    this.positionFund = null;
  }

  public long getSledContractId() {
    return this.sledContractId;
  }

  public HostingSledContractPosition setSledContractId(long sledContractId) {
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

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public HostingSledContractPosition setSubAccountId(long subAccountId) {
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

  public long getSledCommodityId() {
    return this.sledCommodityId;
  }

  public HostingSledContractPosition setSledCommodityId(long sledCommodityId) {
    this.sledCommodityId = sledCommodityId;
    setSledCommodityIdIsSet(true);
    return this;
  }

  public void unsetSledCommodityId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLEDCOMMODITYID_ISSET_ID);
  }

  /** Returns true if field sledCommodityId is set (has been assigned a value) and false otherwise */
  public boolean isSetSledCommodityId() {
    return EncodingUtils.testBit(__isset_bitfield, __SLEDCOMMODITYID_ISSET_ID);
  }

  public void setSledCommodityIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLEDCOMMODITYID_ISSET_ID, value);
  }

  public String getCurrency() {
    return this.currency;
  }

  public HostingSledContractPosition setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public void unsetCurrency() {
    this.currency = null;
  }

  /** Returns true if field currency is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrency() {
    return this.currency != null;
  }

  public void setCurrencyIsSet(boolean value) {
    if (!value) {
      this.currency = null;
    }
  }

  public HostingPositionVolume getPositionVolume() {
    return this.positionVolume;
  }

  public HostingSledContractPosition setPositionVolume(HostingPositionVolume positionVolume) {
    this.positionVolume = positionVolume;
    return this;
  }

  public void unsetPositionVolume() {
    this.positionVolume = null;
  }

  /** Returns true if field positionVolume is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionVolume() {
    return this.positionVolume != null;
  }

  public void setPositionVolumeIsSet(boolean value) {
    if (!value) {
      this.positionVolume = null;
    }
  }

  public HostingPositionFund getPositionFund() {
    return this.positionFund;
  }

  public HostingSledContractPosition setPositionFund(HostingPositionFund positionFund) {
    this.positionFund = positionFund;
    return this;
  }

  public void unsetPositionFund() {
    this.positionFund = null;
  }

  /** Returns true if field positionFund is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionFund() {
    return this.positionFund != null;
  }

  public void setPositionFundIsSet(boolean value) {
    if (!value) {
      this.positionFund = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SLED_CONTRACT_ID:
      if (value == null) {
        unsetSledContractId();
      } else {
        setSledContractId((Long)value);
      }
      break;

    case SUB_ACCOUNT_ID:
      if (value == null) {
        unsetSubAccountId();
      } else {
        setSubAccountId((Long)value);
      }
      break;

    case SLED_COMMODITY_ID:
      if (value == null) {
        unsetSledCommodityId();
      } else {
        setSledCommodityId((Long)value);
      }
      break;

    case CURRENCY:
      if (value == null) {
        unsetCurrency();
      } else {
        setCurrency((String)value);
      }
      break;

    case POSITION_VOLUME:
      if (value == null) {
        unsetPositionVolume();
      } else {
        setPositionVolume((HostingPositionVolume)value);
      }
      break;

    case POSITION_FUND:
      if (value == null) {
        unsetPositionFund();
      } else {
        setPositionFund((HostingPositionFund)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case SLED_COMMODITY_ID:
      return Long.valueOf(getSledCommodityId());

    case CURRENCY:
      return getCurrency();

    case POSITION_VOLUME:
      return getPositionVolume();

    case POSITION_FUND:
      return getPositionFund();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SLED_CONTRACT_ID:
      return isSetSledContractId();
    case SUB_ACCOUNT_ID:
      return isSetSubAccountId();
    case SLED_COMMODITY_ID:
      return isSetSledCommodityId();
    case CURRENCY:
      return isSetCurrency();
    case POSITION_VOLUME:
      return isSetPositionVolume();
    case POSITION_FUND:
      return isSetPositionFund();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingSledContractPosition)
      return this.equals((HostingSledContractPosition)that);
    return false;
  }

  public boolean equals(HostingSledContractPosition that) {
    if (that == null)
      return false;

    boolean this_present_sledContractId = true && this.isSetSledContractId();
    boolean that_present_sledContractId = true && that.isSetSledContractId();
    if (this_present_sledContractId || that_present_sledContractId) {
      if (!(this_present_sledContractId && that_present_sledContractId))
        return false;
      if (this.sledContractId != that.sledContractId)
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

    boolean this_present_sledCommodityId = true && this.isSetSledCommodityId();
    boolean that_present_sledCommodityId = true && that.isSetSledCommodityId();
    if (this_present_sledCommodityId || that_present_sledCommodityId) {
      if (!(this_present_sledCommodityId && that_present_sledCommodityId))
        return false;
      if (this.sledCommodityId != that.sledCommodityId)
        return false;
    }

    boolean this_present_currency = true && this.isSetCurrency();
    boolean that_present_currency = true && that.isSetCurrency();
    if (this_present_currency || that_present_currency) {
      if (!(this_present_currency && that_present_currency))
        return false;
      if (!this.currency.equals(that.currency))
        return false;
    }

    boolean this_present_positionVolume = true && this.isSetPositionVolume();
    boolean that_present_positionVolume = true && that.isSetPositionVolume();
    if (this_present_positionVolume || that_present_positionVolume) {
      if (!(this_present_positionVolume && that_present_positionVolume))
        return false;
      if (!this.positionVolume.equals(that.positionVolume))
        return false;
    }

    boolean this_present_positionFund = true && this.isSetPositionFund();
    boolean that_present_positionFund = true && that.isSetPositionFund();
    if (this_present_positionFund || that_present_positionFund) {
      if (!(this_present_positionFund && that_present_positionFund))
        return false;
      if (!this.positionFund.equals(that.positionFund))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingSledContractPosition other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetSledCommodityId()).compareTo(other.isSetSledCommodityId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledCommodityId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledCommodityId, other.sledCommodityId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrency()).compareTo(other.isSetCurrency());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrency()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currency, other.currency);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionVolume()).compareTo(other.isSetPositionVolume());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionVolume()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionVolume, other.positionVolume);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionFund()).compareTo(other.isSetPositionFund());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionFund()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionFund, other.positionFund);
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
    StringBuilder sb = new StringBuilder("HostingSledContractPosition(");
    boolean first = true;

    if (isSetSledContractId()) {
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
      first = false;
    }
    if (isSetSubAccountId()) {
      if (!first) sb.append(", ");
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetSledCommodityId()) {
      if (!first) sb.append(", ");
      sb.append("sledCommodityId:");
      sb.append(this.sledCommodityId);
      first = false;
    }
    if (isSetCurrency()) {
      if (!first) sb.append(", ");
      sb.append("currency:");
      if (this.currency == null) {
        sb.append("null");
      } else {
        sb.append(this.currency);
      }
      first = false;
    }
    if (isSetPositionVolume()) {
      if (!first) sb.append(", ");
      sb.append("positionVolume:");
      if (this.positionVolume == null) {
        sb.append("null");
      } else {
        sb.append(this.positionVolume);
      }
      first = false;
    }
    if (isSetPositionFund()) {
      if (!first) sb.append(", ");
      sb.append("positionFund:");
      if (this.positionFund == null) {
        sb.append("null");
      } else {
        sb.append(this.positionFund);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (positionVolume != null) {
      positionVolume.validate();
    }
    if (positionFund != null) {
      positionFund.validate();
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

  private static class HostingSledContractPositionStandardSchemeFactory implements SchemeFactory {
    public HostingSledContractPositionStandardScheme getScheme() {
      return new HostingSledContractPositionStandardScheme();
    }
  }

  private static class HostingSledContractPositionStandardScheme extends StandardScheme<HostingSledContractPosition> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingSledContractPosition struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SLED_CONTRACT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sledContractId = iprot.readI64();
              struct.setSledContractIdIsSet(true);
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
          case 3: // SLED_COMMODITY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sledCommodityId = iprot.readI64();
              struct.setSledCommodityIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CURRENCY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currency = iprot.readString();
              struct.setCurrencyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // POSITION_VOLUME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.positionVolume = new HostingPositionVolume();
              struct.positionVolume.read(iprot);
              struct.setPositionVolumeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // POSITION_FUND
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.positionFund = new HostingPositionFund();
              struct.positionFund.read(iprot);
              struct.setPositionFundIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingSledContractPosition struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSledCommodityId()) {
        oprot.writeFieldBegin(SLED_COMMODITY_ID_FIELD_DESC);
        oprot.writeI64(struct.sledCommodityId);
        oprot.writeFieldEnd();
      }
      if (struct.currency != null) {
        if (struct.isSetCurrency()) {
          oprot.writeFieldBegin(CURRENCY_FIELD_DESC);
          oprot.writeString(struct.currency);
          oprot.writeFieldEnd();
        }
      }
      if (struct.positionVolume != null) {
        if (struct.isSetPositionVolume()) {
          oprot.writeFieldBegin(POSITION_VOLUME_FIELD_DESC);
          struct.positionVolume.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.positionFund != null) {
        if (struct.isSetPositionFund()) {
          oprot.writeFieldBegin(POSITION_FUND_FIELD_DESC);
          struct.positionFund.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingSledContractPositionTupleSchemeFactory implements SchemeFactory {
    public HostingSledContractPositionTupleScheme getScheme() {
      return new HostingSledContractPositionTupleScheme();
    }
  }

  private static class HostingSledContractPositionTupleScheme extends TupleScheme<HostingSledContractPosition> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingSledContractPosition struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSledContractId()) {
        optionals.set(0);
      }
      if (struct.isSetSubAccountId()) {
        optionals.set(1);
      }
      if (struct.isSetSledCommodityId()) {
        optionals.set(2);
      }
      if (struct.isSetCurrency()) {
        optionals.set(3);
      }
      if (struct.isSetPositionVolume()) {
        optionals.set(4);
      }
      if (struct.isSetPositionFund()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetSledCommodityId()) {
        oprot.writeI64(struct.sledCommodityId);
      }
      if (struct.isSetCurrency()) {
        oprot.writeString(struct.currency);
      }
      if (struct.isSetPositionVolume()) {
        struct.positionVolume.write(oprot);
      }
      if (struct.isSetPositionFund()) {
        struct.positionFund.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingSledContractPosition struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.sledCommodityId = iprot.readI64();
        struct.setSledCommodityIdIsSet(true);
      }
      if (incoming.get(3)) {
        struct.currency = iprot.readString();
        struct.setCurrencyIsSet(true);
      }
      if (incoming.get(4)) {
        struct.positionVolume = new HostingPositionVolume();
        struct.positionVolume.read(iprot);
        struct.setPositionVolumeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.positionFund = new HostingPositionFund();
        struct.positionFund.read(iprot);
        struct.setPositionFundIsSet(true);
      }
    }
  }

}
