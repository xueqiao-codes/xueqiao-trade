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

public class StatComposeLeg implements org.apache.thrift.TBase<StatComposeLeg, StatComposeLeg._Fields>, java.io.Serializable, Cloneable, Comparable<StatComposeLeg> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StatComposeLeg");

  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField LEG_TRADE_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("legTradePrice", org.apache.thrift.protocol.TType.DOUBLE, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StatComposeLegStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StatComposeLegTupleSchemeFactory());
  }

  public long sledContractId; // optional
  public double legTradePrice; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLED_CONTRACT_ID((short)1, "sledContractId"),
    LEG_TRADE_PRICE((short)3, "legTradePrice");

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
        case 3: // LEG_TRADE_PRICE
          return LEG_TRADE_PRICE;
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
  private static final int __LEGTRADEPRICE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SLED_CONTRACT_ID,_Fields.LEG_TRADE_PRICE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LEG_TRADE_PRICE, new org.apache.thrift.meta_data.FieldMetaData("legTradePrice", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatComposeLeg.class, metaDataMap);
  }

  public StatComposeLeg() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StatComposeLeg(StatComposeLeg other) {
    __isset_bitfield = other.__isset_bitfield;
    this.sledContractId = other.sledContractId;
    this.legTradePrice = other.legTradePrice;
  }

  public StatComposeLeg deepCopy() {
    return new StatComposeLeg(this);
  }

  @Override
  public void clear() {
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
    setLegTradePriceIsSet(false);
    this.legTradePrice = 0.0;
  }

  public long getSledContractId() {
    return this.sledContractId;
  }

  public StatComposeLeg setSledContractId(long sledContractId) {
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

  public double getLegTradePrice() {
    return this.legTradePrice;
  }

  public StatComposeLeg setLegTradePrice(double legTradePrice) {
    this.legTradePrice = legTradePrice;
    setLegTradePriceIsSet(true);
    return this;
  }

  public void unsetLegTradePrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LEGTRADEPRICE_ISSET_ID);
  }

  /** Returns true if field legTradePrice is set (has been assigned a value) and false otherwise */
  public boolean isSetLegTradePrice() {
    return EncodingUtils.testBit(__isset_bitfield, __LEGTRADEPRICE_ISSET_ID);
  }

  public void setLegTradePriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LEGTRADEPRICE_ISSET_ID, value);
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

    case LEG_TRADE_PRICE:
      if (value == null) {
        unsetLegTradePrice();
      } else {
        setLegTradePrice((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    case LEG_TRADE_PRICE:
      return Double.valueOf(getLegTradePrice());

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
    case LEG_TRADE_PRICE:
      return isSetLegTradePrice();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StatComposeLeg)
      return this.equals((StatComposeLeg)that);
    return false;
  }

  public boolean equals(StatComposeLeg that) {
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

    boolean this_present_legTradePrice = true && this.isSetLegTradePrice();
    boolean that_present_legTradePrice = true && that.isSetLegTradePrice();
    if (this_present_legTradePrice || that_present_legTradePrice) {
      if (!(this_present_legTradePrice && that_present_legTradePrice))
        return false;
      if (this.legTradePrice != that.legTradePrice)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StatComposeLeg other) {
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
    lastComparison = Boolean.valueOf(isSetLegTradePrice()).compareTo(other.isSetLegTradePrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLegTradePrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.legTradePrice, other.legTradePrice);
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
    StringBuilder sb = new StringBuilder("StatComposeLeg(");
    boolean first = true;

    if (isSetSledContractId()) {
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
      first = false;
    }
    if (isSetLegTradePrice()) {
      if (!first) sb.append(", ");
      sb.append("legTradePrice:");
      sb.append(this.legTradePrice);
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

  private static class StatComposeLegStandardSchemeFactory implements SchemeFactory {
    public StatComposeLegStandardScheme getScheme() {
      return new StatComposeLegStandardScheme();
    }
  }

  private static class StatComposeLegStandardScheme extends StandardScheme<StatComposeLeg> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StatComposeLeg struct) throws org.apache.thrift.TException {
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
          case 3: // LEG_TRADE_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.legTradePrice = iprot.readDouble();
              struct.setLegTradePriceIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StatComposeLeg struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLegTradePrice()) {
        oprot.writeFieldBegin(LEG_TRADE_PRICE_FIELD_DESC);
        oprot.writeDouble(struct.legTradePrice);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StatComposeLegTupleSchemeFactory implements SchemeFactory {
    public StatComposeLegTupleScheme getScheme() {
      return new StatComposeLegTupleScheme();
    }
  }

  private static class StatComposeLegTupleScheme extends TupleScheme<StatComposeLeg> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StatComposeLeg struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSledContractId()) {
        optionals.set(0);
      }
      if (struct.isSetLegTradePrice()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
      if (struct.isSetLegTradePrice()) {
        oprot.writeDouble(struct.legTradePrice);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StatComposeLeg struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.legTradePrice = iprot.readDouble();
        struct.setLegTradePriceIsSet(true);
      }
    }
  }

}

