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

public class ClosedProfit implements org.apache.thrift.TBase<ClosedProfit, ClosedProfit._Fields>, java.io.Serializable, Cloneable, Comparable<ClosedProfit> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ClosedProfit");

  private static final org.apache.thrift.protocol.TField TRADE_CURRENCY_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeCurrency", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CLOSED_PROFIT_VALUE_FIELD_DESC = new org.apache.thrift.protocol.TField("closedProfitValue", org.apache.thrift.protocol.TType.DOUBLE, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ClosedProfitStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ClosedProfitTupleSchemeFactory());
  }

  public String tradeCurrency; // optional
  public double closedProfitValue; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRADE_CURRENCY((short)2, "tradeCurrency"),
    CLOSED_PROFIT_VALUE((short)3, "closedProfitValue");

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
        case 2: // TRADE_CURRENCY
          return TRADE_CURRENCY;
        case 3: // CLOSED_PROFIT_VALUE
          return CLOSED_PROFIT_VALUE;
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
  private static final int __CLOSEDPROFITVALUE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TRADE_CURRENCY,_Fields.CLOSED_PROFIT_VALUE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRADE_CURRENCY, new org.apache.thrift.meta_data.FieldMetaData("tradeCurrency", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLOSED_PROFIT_VALUE, new org.apache.thrift.meta_data.FieldMetaData("closedProfitValue", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ClosedProfit.class, metaDataMap);
  }

  public ClosedProfit() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ClosedProfit(ClosedProfit other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetTradeCurrency()) {
      this.tradeCurrency = other.tradeCurrency;
    }
    this.closedProfitValue = other.closedProfitValue;
  }

  public ClosedProfit deepCopy() {
    return new ClosedProfit(this);
  }

  @Override
  public void clear() {
    this.tradeCurrency = null;
    setClosedProfitValueIsSet(false);
    this.closedProfitValue = 0.0;
  }

  public String getTradeCurrency() {
    return this.tradeCurrency;
  }

  public ClosedProfit setTradeCurrency(String tradeCurrency) {
    this.tradeCurrency = tradeCurrency;
    return this;
  }

  public void unsetTradeCurrency() {
    this.tradeCurrency = null;
  }

  /** Returns true if field tradeCurrency is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeCurrency() {
    return this.tradeCurrency != null;
  }

  public void setTradeCurrencyIsSet(boolean value) {
    if (!value) {
      this.tradeCurrency = null;
    }
  }

  public double getClosedProfitValue() {
    return this.closedProfitValue;
  }

  public ClosedProfit setClosedProfitValue(double closedProfitValue) {
    this.closedProfitValue = closedProfitValue;
    setClosedProfitValueIsSet(true);
    return this;
  }

  public void unsetClosedProfitValue() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CLOSEDPROFITVALUE_ISSET_ID);
  }

  /** Returns true if field closedProfitValue is set (has been assigned a value) and false otherwise */
  public boolean isSetClosedProfitValue() {
    return EncodingUtils.testBit(__isset_bitfield, __CLOSEDPROFITVALUE_ISSET_ID);
  }

  public void setClosedProfitValueIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CLOSEDPROFITVALUE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TRADE_CURRENCY:
      if (value == null) {
        unsetTradeCurrency();
      } else {
        setTradeCurrency((String)value);
      }
      break;

    case CLOSED_PROFIT_VALUE:
      if (value == null) {
        unsetClosedProfitValue();
      } else {
        setClosedProfitValue((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRADE_CURRENCY:
      return getTradeCurrency();

    case CLOSED_PROFIT_VALUE:
      return Double.valueOf(getClosedProfitValue());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TRADE_CURRENCY:
      return isSetTradeCurrency();
    case CLOSED_PROFIT_VALUE:
      return isSetClosedProfitValue();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ClosedProfit)
      return this.equals((ClosedProfit)that);
    return false;
  }

  public boolean equals(ClosedProfit that) {
    if (that == null)
      return false;

    boolean this_present_tradeCurrency = true && this.isSetTradeCurrency();
    boolean that_present_tradeCurrency = true && that.isSetTradeCurrency();
    if (this_present_tradeCurrency || that_present_tradeCurrency) {
      if (!(this_present_tradeCurrency && that_present_tradeCurrency))
        return false;
      if (!this.tradeCurrency.equals(that.tradeCurrency))
        return false;
    }

    boolean this_present_closedProfitValue = true && this.isSetClosedProfitValue();
    boolean that_present_closedProfitValue = true && that.isSetClosedProfitValue();
    if (this_present_closedProfitValue || that_present_closedProfitValue) {
      if (!(this_present_closedProfitValue && that_present_closedProfitValue))
        return false;
      if (this.closedProfitValue != that.closedProfitValue)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ClosedProfit other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTradeCurrency()).compareTo(other.isSetTradeCurrency());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeCurrency()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeCurrency, other.tradeCurrency);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClosedProfitValue()).compareTo(other.isSetClosedProfitValue());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClosedProfitValue()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.closedProfitValue, other.closedProfitValue);
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
    StringBuilder sb = new StringBuilder("ClosedProfit(");
    boolean first = true;

    if (isSetTradeCurrency()) {
      sb.append("tradeCurrency:");
      if (this.tradeCurrency == null) {
        sb.append("null");
      } else {
        sb.append(this.tradeCurrency);
      }
      first = false;
    }
    if (isSetClosedProfitValue()) {
      if (!first) sb.append(", ");
      sb.append("closedProfitValue:");
      sb.append(this.closedProfitValue);
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

  private static class ClosedProfitStandardSchemeFactory implements SchemeFactory {
    public ClosedProfitStandardScheme getScheme() {
      return new ClosedProfitStandardScheme();
    }
  }

  private static class ClosedProfitStandardScheme extends StandardScheme<ClosedProfit> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ClosedProfit struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 2: // TRADE_CURRENCY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tradeCurrency = iprot.readString();
              struct.setTradeCurrencyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLOSED_PROFIT_VALUE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.closedProfitValue = iprot.readDouble();
              struct.setClosedProfitValueIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ClosedProfit struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tradeCurrency != null) {
        if (struct.isSetTradeCurrency()) {
          oprot.writeFieldBegin(TRADE_CURRENCY_FIELD_DESC);
          oprot.writeString(struct.tradeCurrency);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetClosedProfitValue()) {
        oprot.writeFieldBegin(CLOSED_PROFIT_VALUE_FIELD_DESC);
        oprot.writeDouble(struct.closedProfitValue);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ClosedProfitTupleSchemeFactory implements SchemeFactory {
    public ClosedProfitTupleScheme getScheme() {
      return new ClosedProfitTupleScheme();
    }
  }

  private static class ClosedProfitTupleScheme extends TupleScheme<ClosedProfit> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ClosedProfit struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTradeCurrency()) {
        optionals.set(0);
      }
      if (struct.isSetClosedProfitValue()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTradeCurrency()) {
        oprot.writeString(struct.tradeCurrency);
      }
      if (struct.isSetClosedProfitValue()) {
        oprot.writeDouble(struct.closedProfitValue);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ClosedProfit struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.tradeCurrency = iprot.readString();
        struct.setTradeCurrencyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.closedProfitValue = iprot.readDouble();
        struct.setClosedProfitValueIsSet(true);
      }
    }
  }

}

