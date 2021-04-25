/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting;

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

public class ESunny9ContractSummary implements org.apache.thrift.TBase<ESunny9ContractSummary, ESunny9ContractSummary._Fields>, java.io.Serializable, Cloneable, Comparable<ESunny9ContractSummary> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ESunny9ContractSummary");

  private static final org.apache.thrift.protocol.TField ESUNNY9_EXCHANGE_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("esunny9ExchangeNo", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField ESUNNY9_COMMODITY_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("esunny9CommodityType", org.apache.thrift.protocol.TType.I16, (short)2);
  private static final org.apache.thrift.protocol.TField ESUNNY9_COMMODITY_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("esunny9CommodityNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ESUNNY9_CONTRACT_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("esunny9ContractNo", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField ESUNNY9_CONTRACT_NO2_FIELD_DESC = new org.apache.thrift.protocol.TField("esunny9ContractNo2", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ESunny9ContractSummaryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ESunny9ContractSummaryTupleSchemeFactory());
  }

  public String esunny9ExchangeNo; // optional
  public short esunny9CommodityType; // optional
  public String esunny9CommodityNo; // optional
  public String esunny9ContractNo; // optional
  public String esunny9ContractNo2; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ESUNNY9_EXCHANGE_NO((short)1, "esunny9ExchangeNo"),
    ESUNNY9_COMMODITY_TYPE((short)2, "esunny9CommodityType"),
    ESUNNY9_COMMODITY_NO((short)3, "esunny9CommodityNo"),
    ESUNNY9_CONTRACT_NO((short)4, "esunny9ContractNo"),
    ESUNNY9_CONTRACT_NO2((short)5, "esunny9ContractNo2");

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
        case 1: // ESUNNY9_EXCHANGE_NO
          return ESUNNY9_EXCHANGE_NO;
        case 2: // ESUNNY9_COMMODITY_TYPE
          return ESUNNY9_COMMODITY_TYPE;
        case 3: // ESUNNY9_COMMODITY_NO
          return ESUNNY9_COMMODITY_NO;
        case 4: // ESUNNY9_CONTRACT_NO
          return ESUNNY9_CONTRACT_NO;
        case 5: // ESUNNY9_CONTRACT_NO2
          return ESUNNY9_CONTRACT_NO2;
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
  private static final int __ESUNNY9COMMODITYTYPE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ESUNNY9_EXCHANGE_NO,_Fields.ESUNNY9_COMMODITY_TYPE,_Fields.ESUNNY9_COMMODITY_NO,_Fields.ESUNNY9_CONTRACT_NO,_Fields.ESUNNY9_CONTRACT_NO2};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ESUNNY9_EXCHANGE_NO, new org.apache.thrift.meta_data.FieldMetaData("esunny9ExchangeNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ESUNNY9_COMMODITY_TYPE, new org.apache.thrift.meta_data.FieldMetaData("esunny9CommodityType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.ESUNNY9_COMMODITY_NO, new org.apache.thrift.meta_data.FieldMetaData("esunny9CommodityNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ESUNNY9_CONTRACT_NO, new org.apache.thrift.meta_data.FieldMetaData("esunny9ContractNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ESUNNY9_CONTRACT_NO2, new org.apache.thrift.meta_data.FieldMetaData("esunny9ContractNo2", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ESunny9ContractSummary.class, metaDataMap);
  }

  public ESunny9ContractSummary() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ESunny9ContractSummary(ESunny9ContractSummary other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetEsunny9ExchangeNo()) {
      this.esunny9ExchangeNo = other.esunny9ExchangeNo;
    }
    this.esunny9CommodityType = other.esunny9CommodityType;
    if (other.isSetEsunny9CommodityNo()) {
      this.esunny9CommodityNo = other.esunny9CommodityNo;
    }
    if (other.isSetEsunny9ContractNo()) {
      this.esunny9ContractNo = other.esunny9ContractNo;
    }
    if (other.isSetEsunny9ContractNo2()) {
      this.esunny9ContractNo2 = other.esunny9ContractNo2;
    }
  }

  public ESunny9ContractSummary deepCopy() {
    return new ESunny9ContractSummary(this);
  }

  @Override
  public void clear() {
    this.esunny9ExchangeNo = null;
    setEsunny9CommodityTypeIsSet(false);
    this.esunny9CommodityType = 0;
    this.esunny9CommodityNo = null;
    this.esunny9ContractNo = null;
    this.esunny9ContractNo2 = null;
  }

  public String getEsunny9ExchangeNo() {
    return this.esunny9ExchangeNo;
  }

  public ESunny9ContractSummary setEsunny9ExchangeNo(String esunny9ExchangeNo) {
    this.esunny9ExchangeNo = esunny9ExchangeNo;
    return this;
  }

  public void unsetEsunny9ExchangeNo() {
    this.esunny9ExchangeNo = null;
  }

  /** Returns true if field esunny9ExchangeNo is set (has been assigned a value) and false otherwise */
  public boolean isSetEsunny9ExchangeNo() {
    return this.esunny9ExchangeNo != null;
  }

  public void setEsunny9ExchangeNoIsSet(boolean value) {
    if (!value) {
      this.esunny9ExchangeNo = null;
    }
  }

  public short getEsunny9CommodityType() {
    return this.esunny9CommodityType;
  }

  public ESunny9ContractSummary setEsunny9CommodityType(short esunny9CommodityType) {
    this.esunny9CommodityType = esunny9CommodityType;
    setEsunny9CommodityTypeIsSet(true);
    return this;
  }

  public void unsetEsunny9CommodityType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ESUNNY9COMMODITYTYPE_ISSET_ID);
  }

  /** Returns true if field esunny9CommodityType is set (has been assigned a value) and false otherwise */
  public boolean isSetEsunny9CommodityType() {
    return EncodingUtils.testBit(__isset_bitfield, __ESUNNY9COMMODITYTYPE_ISSET_ID);
  }

  public void setEsunny9CommodityTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ESUNNY9COMMODITYTYPE_ISSET_ID, value);
  }

  public String getEsunny9CommodityNo() {
    return this.esunny9CommodityNo;
  }

  public ESunny9ContractSummary setEsunny9CommodityNo(String esunny9CommodityNo) {
    this.esunny9CommodityNo = esunny9CommodityNo;
    return this;
  }

  public void unsetEsunny9CommodityNo() {
    this.esunny9CommodityNo = null;
  }

  /** Returns true if field esunny9CommodityNo is set (has been assigned a value) and false otherwise */
  public boolean isSetEsunny9CommodityNo() {
    return this.esunny9CommodityNo != null;
  }

  public void setEsunny9CommodityNoIsSet(boolean value) {
    if (!value) {
      this.esunny9CommodityNo = null;
    }
  }

  public String getEsunny9ContractNo() {
    return this.esunny9ContractNo;
  }

  public ESunny9ContractSummary setEsunny9ContractNo(String esunny9ContractNo) {
    this.esunny9ContractNo = esunny9ContractNo;
    return this;
  }

  public void unsetEsunny9ContractNo() {
    this.esunny9ContractNo = null;
  }

  /** Returns true if field esunny9ContractNo is set (has been assigned a value) and false otherwise */
  public boolean isSetEsunny9ContractNo() {
    return this.esunny9ContractNo != null;
  }

  public void setEsunny9ContractNoIsSet(boolean value) {
    if (!value) {
      this.esunny9ContractNo = null;
    }
  }

  public String getEsunny9ContractNo2() {
    return this.esunny9ContractNo2;
  }

  public ESunny9ContractSummary setEsunny9ContractNo2(String esunny9ContractNo2) {
    this.esunny9ContractNo2 = esunny9ContractNo2;
    return this;
  }

  public void unsetEsunny9ContractNo2() {
    this.esunny9ContractNo2 = null;
  }

  /** Returns true if field esunny9ContractNo2 is set (has been assigned a value) and false otherwise */
  public boolean isSetEsunny9ContractNo2() {
    return this.esunny9ContractNo2 != null;
  }

  public void setEsunny9ContractNo2IsSet(boolean value) {
    if (!value) {
      this.esunny9ContractNo2 = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ESUNNY9_EXCHANGE_NO:
      if (value == null) {
        unsetEsunny9ExchangeNo();
      } else {
        setEsunny9ExchangeNo((String)value);
      }
      break;

    case ESUNNY9_COMMODITY_TYPE:
      if (value == null) {
        unsetEsunny9CommodityType();
      } else {
        setEsunny9CommodityType((Short)value);
      }
      break;

    case ESUNNY9_COMMODITY_NO:
      if (value == null) {
        unsetEsunny9CommodityNo();
      } else {
        setEsunny9CommodityNo((String)value);
      }
      break;

    case ESUNNY9_CONTRACT_NO:
      if (value == null) {
        unsetEsunny9ContractNo();
      } else {
        setEsunny9ContractNo((String)value);
      }
      break;

    case ESUNNY9_CONTRACT_NO2:
      if (value == null) {
        unsetEsunny9ContractNo2();
      } else {
        setEsunny9ContractNo2((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ESUNNY9_EXCHANGE_NO:
      return getEsunny9ExchangeNo();

    case ESUNNY9_COMMODITY_TYPE:
      return Short.valueOf(getEsunny9CommodityType());

    case ESUNNY9_COMMODITY_NO:
      return getEsunny9CommodityNo();

    case ESUNNY9_CONTRACT_NO:
      return getEsunny9ContractNo();

    case ESUNNY9_CONTRACT_NO2:
      return getEsunny9ContractNo2();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ESUNNY9_EXCHANGE_NO:
      return isSetEsunny9ExchangeNo();
    case ESUNNY9_COMMODITY_TYPE:
      return isSetEsunny9CommodityType();
    case ESUNNY9_COMMODITY_NO:
      return isSetEsunny9CommodityNo();
    case ESUNNY9_CONTRACT_NO:
      return isSetEsunny9ContractNo();
    case ESUNNY9_CONTRACT_NO2:
      return isSetEsunny9ContractNo2();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ESunny9ContractSummary)
      return this.equals((ESunny9ContractSummary)that);
    return false;
  }

  public boolean equals(ESunny9ContractSummary that) {
    if (that == null)
      return false;

    boolean this_present_esunny9ExchangeNo = true && this.isSetEsunny9ExchangeNo();
    boolean that_present_esunny9ExchangeNo = true && that.isSetEsunny9ExchangeNo();
    if (this_present_esunny9ExchangeNo || that_present_esunny9ExchangeNo) {
      if (!(this_present_esunny9ExchangeNo && that_present_esunny9ExchangeNo))
        return false;
      if (!this.esunny9ExchangeNo.equals(that.esunny9ExchangeNo))
        return false;
    }

    boolean this_present_esunny9CommodityType = true && this.isSetEsunny9CommodityType();
    boolean that_present_esunny9CommodityType = true && that.isSetEsunny9CommodityType();
    if (this_present_esunny9CommodityType || that_present_esunny9CommodityType) {
      if (!(this_present_esunny9CommodityType && that_present_esunny9CommodityType))
        return false;
      if (this.esunny9CommodityType != that.esunny9CommodityType)
        return false;
    }

    boolean this_present_esunny9CommodityNo = true && this.isSetEsunny9CommodityNo();
    boolean that_present_esunny9CommodityNo = true && that.isSetEsunny9CommodityNo();
    if (this_present_esunny9CommodityNo || that_present_esunny9CommodityNo) {
      if (!(this_present_esunny9CommodityNo && that_present_esunny9CommodityNo))
        return false;
      if (!this.esunny9CommodityNo.equals(that.esunny9CommodityNo))
        return false;
    }

    boolean this_present_esunny9ContractNo = true && this.isSetEsunny9ContractNo();
    boolean that_present_esunny9ContractNo = true && that.isSetEsunny9ContractNo();
    if (this_present_esunny9ContractNo || that_present_esunny9ContractNo) {
      if (!(this_present_esunny9ContractNo && that_present_esunny9ContractNo))
        return false;
      if (!this.esunny9ContractNo.equals(that.esunny9ContractNo))
        return false;
    }

    boolean this_present_esunny9ContractNo2 = true && this.isSetEsunny9ContractNo2();
    boolean that_present_esunny9ContractNo2 = true && that.isSetEsunny9ContractNo2();
    if (this_present_esunny9ContractNo2 || that_present_esunny9ContractNo2) {
      if (!(this_present_esunny9ContractNo2 && that_present_esunny9ContractNo2))
        return false;
      if (!this.esunny9ContractNo2.equals(that.esunny9ContractNo2))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ESunny9ContractSummary other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetEsunny9ExchangeNo()).compareTo(other.isSetEsunny9ExchangeNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEsunny9ExchangeNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.esunny9ExchangeNo, other.esunny9ExchangeNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEsunny9CommodityType()).compareTo(other.isSetEsunny9CommodityType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEsunny9CommodityType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.esunny9CommodityType, other.esunny9CommodityType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEsunny9CommodityNo()).compareTo(other.isSetEsunny9CommodityNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEsunny9CommodityNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.esunny9CommodityNo, other.esunny9CommodityNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEsunny9ContractNo()).compareTo(other.isSetEsunny9ContractNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEsunny9ContractNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.esunny9ContractNo, other.esunny9ContractNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEsunny9ContractNo2()).compareTo(other.isSetEsunny9ContractNo2());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEsunny9ContractNo2()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.esunny9ContractNo2, other.esunny9ContractNo2);
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
    StringBuilder sb = new StringBuilder("ESunny9ContractSummary(");
    boolean first = true;

    if (isSetEsunny9ExchangeNo()) {
      sb.append("esunny9ExchangeNo:");
      if (this.esunny9ExchangeNo == null) {
        sb.append("null");
      } else {
        sb.append(this.esunny9ExchangeNo);
      }
      first = false;
    }
    if (isSetEsunny9CommodityType()) {
      if (!first) sb.append(", ");
      sb.append("esunny9CommodityType:");
      sb.append(this.esunny9CommodityType);
      first = false;
    }
    if (isSetEsunny9CommodityNo()) {
      if (!first) sb.append(", ");
      sb.append("esunny9CommodityNo:");
      if (this.esunny9CommodityNo == null) {
        sb.append("null");
      } else {
        sb.append(this.esunny9CommodityNo);
      }
      first = false;
    }
    if (isSetEsunny9ContractNo()) {
      if (!first) sb.append(", ");
      sb.append("esunny9ContractNo:");
      if (this.esunny9ContractNo == null) {
        sb.append("null");
      } else {
        sb.append(this.esunny9ContractNo);
      }
      first = false;
    }
    if (isSetEsunny9ContractNo2()) {
      if (!first) sb.append(", ");
      sb.append("esunny9ContractNo2:");
      if (this.esunny9ContractNo2 == null) {
        sb.append("null");
      } else {
        sb.append(this.esunny9ContractNo2);
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

  private static class ESunny9ContractSummaryStandardSchemeFactory implements SchemeFactory {
    public ESunny9ContractSummaryStandardScheme getScheme() {
      return new ESunny9ContractSummaryStandardScheme();
    }
  }

  private static class ESunny9ContractSummaryStandardScheme extends StandardScheme<ESunny9ContractSummary> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ESunny9ContractSummary struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ESUNNY9_EXCHANGE_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.esunny9ExchangeNo = iprot.readString();
              struct.setEsunny9ExchangeNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ESUNNY9_COMMODITY_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.esunny9CommodityType = iprot.readI16();
              struct.setEsunny9CommodityTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ESUNNY9_COMMODITY_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.esunny9CommodityNo = iprot.readString();
              struct.setEsunny9CommodityNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ESUNNY9_CONTRACT_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.esunny9ContractNo = iprot.readString();
              struct.setEsunny9ContractNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // ESUNNY9_CONTRACT_NO2
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.esunny9ContractNo2 = iprot.readString();
              struct.setEsunny9ContractNo2IsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ESunny9ContractSummary struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.esunny9ExchangeNo != null) {
        if (struct.isSetEsunny9ExchangeNo()) {
          oprot.writeFieldBegin(ESUNNY9_EXCHANGE_NO_FIELD_DESC);
          oprot.writeString(struct.esunny9ExchangeNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetEsunny9CommodityType()) {
        oprot.writeFieldBegin(ESUNNY9_COMMODITY_TYPE_FIELD_DESC);
        oprot.writeI16(struct.esunny9CommodityType);
        oprot.writeFieldEnd();
      }
      if (struct.esunny9CommodityNo != null) {
        if (struct.isSetEsunny9CommodityNo()) {
          oprot.writeFieldBegin(ESUNNY9_COMMODITY_NO_FIELD_DESC);
          oprot.writeString(struct.esunny9CommodityNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.esunny9ContractNo != null) {
        if (struct.isSetEsunny9ContractNo()) {
          oprot.writeFieldBegin(ESUNNY9_CONTRACT_NO_FIELD_DESC);
          oprot.writeString(struct.esunny9ContractNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.esunny9ContractNo2 != null) {
        if (struct.isSetEsunny9ContractNo2()) {
          oprot.writeFieldBegin(ESUNNY9_CONTRACT_NO2_FIELD_DESC);
          oprot.writeString(struct.esunny9ContractNo2);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ESunny9ContractSummaryTupleSchemeFactory implements SchemeFactory {
    public ESunny9ContractSummaryTupleScheme getScheme() {
      return new ESunny9ContractSummaryTupleScheme();
    }
  }

  private static class ESunny9ContractSummaryTupleScheme extends TupleScheme<ESunny9ContractSummary> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ESunny9ContractSummary struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetEsunny9ExchangeNo()) {
        optionals.set(0);
      }
      if (struct.isSetEsunny9CommodityType()) {
        optionals.set(1);
      }
      if (struct.isSetEsunny9CommodityNo()) {
        optionals.set(2);
      }
      if (struct.isSetEsunny9ContractNo()) {
        optionals.set(3);
      }
      if (struct.isSetEsunny9ContractNo2()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetEsunny9ExchangeNo()) {
        oprot.writeString(struct.esunny9ExchangeNo);
      }
      if (struct.isSetEsunny9CommodityType()) {
        oprot.writeI16(struct.esunny9CommodityType);
      }
      if (struct.isSetEsunny9CommodityNo()) {
        oprot.writeString(struct.esunny9CommodityNo);
      }
      if (struct.isSetEsunny9ContractNo()) {
        oprot.writeString(struct.esunny9ContractNo);
      }
      if (struct.isSetEsunny9ContractNo2()) {
        oprot.writeString(struct.esunny9ContractNo2);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ESunny9ContractSummary struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.esunny9ExchangeNo = iprot.readString();
        struct.setEsunny9ExchangeNoIsSet(true);
      }
      if (incoming.get(1)) {
        struct.esunny9CommodityType = iprot.readI16();
        struct.setEsunny9CommodityTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.esunny9CommodityNo = iprot.readString();
        struct.setEsunny9CommodityNoIsSet(true);
      }
      if (incoming.get(3)) {
        struct.esunny9ContractNo = iprot.readString();
        struct.setEsunny9ContractNoIsSet(true);
      }
      if (incoming.get(4)) {
        struct.esunny9ContractNo2 = iprot.readString();
        struct.setEsunny9ContractNo2IsSet(true);
      }
    }
  }

}

