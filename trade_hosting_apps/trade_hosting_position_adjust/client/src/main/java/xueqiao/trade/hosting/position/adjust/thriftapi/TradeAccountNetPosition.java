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
 * 资金账户的合约净持仓
 */
public class TradeAccountNetPosition implements org.apache.thrift.TBase<TradeAccountNetPosition, TradeAccountNetPosition._Fields>, java.io.Serializable, Cloneable, Comparable<TradeAccountNetPosition> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TradeAccountNetPosition");

  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLED_NET_POSITION_FIELD_DESC = new org.apache.thrift.protocol.TField("sledNetPosition", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField UPSIDE_NET_POSITION_FIELD_DESC = new org.apache.thrift.protocol.TField("upsideNetPosition", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TradeAccountNetPositionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TradeAccountNetPositionTupleSchemeFactory());
  }

  public long sledContractId; // optional
  public int sledNetPosition; // optional
  public int upsideNetPosition; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLED_CONTRACT_ID((short)1, "sledContractId"),
    SLED_NET_POSITION((short)2, "sledNetPosition"),
    UPSIDE_NET_POSITION((short)3, "upsideNetPosition");

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
        case 2: // SLED_NET_POSITION
          return SLED_NET_POSITION;
        case 3: // UPSIDE_NET_POSITION
          return UPSIDE_NET_POSITION;
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
  private static final int __SLEDNETPOSITION_ISSET_ID = 1;
  private static final int __UPSIDENETPOSITION_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SLED_CONTRACT_ID,_Fields.SLED_NET_POSITION,_Fields.UPSIDE_NET_POSITION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_NET_POSITION, new org.apache.thrift.meta_data.FieldMetaData("sledNetPosition", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.UPSIDE_NET_POSITION, new org.apache.thrift.meta_data.FieldMetaData("upsideNetPosition", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TradeAccountNetPosition.class, metaDataMap);
  }

  public TradeAccountNetPosition() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TradeAccountNetPosition(TradeAccountNetPosition other) {
    __isset_bitfield = other.__isset_bitfield;
    this.sledContractId = other.sledContractId;
    this.sledNetPosition = other.sledNetPosition;
    this.upsideNetPosition = other.upsideNetPosition;
  }

  public TradeAccountNetPosition deepCopy() {
    return new TradeAccountNetPosition(this);
  }

  @Override
  public void clear() {
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
    setSledNetPositionIsSet(false);
    this.sledNetPosition = 0;
    setUpsideNetPositionIsSet(false);
    this.upsideNetPosition = 0;
  }

  public long getSledContractId() {
    return this.sledContractId;
  }

  public TradeAccountNetPosition setSledContractId(long sledContractId) {
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

  public int getSledNetPosition() {
    return this.sledNetPosition;
  }

  public TradeAccountNetPosition setSledNetPosition(int sledNetPosition) {
    this.sledNetPosition = sledNetPosition;
    setSledNetPositionIsSet(true);
    return this;
  }

  public void unsetSledNetPosition() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLEDNETPOSITION_ISSET_ID);
  }

  /** Returns true if field sledNetPosition is set (has been assigned a value) and false otherwise */
  public boolean isSetSledNetPosition() {
    return EncodingUtils.testBit(__isset_bitfield, __SLEDNETPOSITION_ISSET_ID);
  }

  public void setSledNetPositionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLEDNETPOSITION_ISSET_ID, value);
  }

  public int getUpsideNetPosition() {
    return this.upsideNetPosition;
  }

  public TradeAccountNetPosition setUpsideNetPosition(int upsideNetPosition) {
    this.upsideNetPosition = upsideNetPosition;
    setUpsideNetPositionIsSet(true);
    return this;
  }

  public void unsetUpsideNetPosition() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UPSIDENETPOSITION_ISSET_ID);
  }

  /** Returns true if field upsideNetPosition is set (has been assigned a value) and false otherwise */
  public boolean isSetUpsideNetPosition() {
    return EncodingUtils.testBit(__isset_bitfield, __UPSIDENETPOSITION_ISSET_ID);
  }

  public void setUpsideNetPositionIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UPSIDENETPOSITION_ISSET_ID, value);
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

    case SLED_NET_POSITION:
      if (value == null) {
        unsetSledNetPosition();
      } else {
        setSledNetPosition((Integer)value);
      }
      break;

    case UPSIDE_NET_POSITION:
      if (value == null) {
        unsetUpsideNetPosition();
      } else {
        setUpsideNetPosition((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    case SLED_NET_POSITION:
      return Integer.valueOf(getSledNetPosition());

    case UPSIDE_NET_POSITION:
      return Integer.valueOf(getUpsideNetPosition());

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
    case SLED_NET_POSITION:
      return isSetSledNetPosition();
    case UPSIDE_NET_POSITION:
      return isSetUpsideNetPosition();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TradeAccountNetPosition)
      return this.equals((TradeAccountNetPosition)that);
    return false;
  }

  public boolean equals(TradeAccountNetPosition that) {
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

    boolean this_present_sledNetPosition = true && this.isSetSledNetPosition();
    boolean that_present_sledNetPosition = true && that.isSetSledNetPosition();
    if (this_present_sledNetPosition || that_present_sledNetPosition) {
      if (!(this_present_sledNetPosition && that_present_sledNetPosition))
        return false;
      if (this.sledNetPosition != that.sledNetPosition)
        return false;
    }

    boolean this_present_upsideNetPosition = true && this.isSetUpsideNetPosition();
    boolean that_present_upsideNetPosition = true && that.isSetUpsideNetPosition();
    if (this_present_upsideNetPosition || that_present_upsideNetPosition) {
      if (!(this_present_upsideNetPosition && that_present_upsideNetPosition))
        return false;
      if (this.upsideNetPosition != that.upsideNetPosition)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TradeAccountNetPosition other) {
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
    lastComparison = Boolean.valueOf(isSetSledNetPosition()).compareTo(other.isSetSledNetPosition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledNetPosition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledNetPosition, other.sledNetPosition);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpsideNetPosition()).compareTo(other.isSetUpsideNetPosition());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpsideNetPosition()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.upsideNetPosition, other.upsideNetPosition);
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
    StringBuilder sb = new StringBuilder("TradeAccountNetPosition(");
    boolean first = true;

    if (isSetSledContractId()) {
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
      first = false;
    }
    if (isSetSledNetPosition()) {
      if (!first) sb.append(", ");
      sb.append("sledNetPosition:");
      sb.append(this.sledNetPosition);
      first = false;
    }
    if (isSetUpsideNetPosition()) {
      if (!first) sb.append(", ");
      sb.append("upsideNetPosition:");
      sb.append(this.upsideNetPosition);
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

  private static class TradeAccountNetPositionStandardSchemeFactory implements SchemeFactory {
    public TradeAccountNetPositionStandardScheme getScheme() {
      return new TradeAccountNetPositionStandardScheme();
    }
  }

  private static class TradeAccountNetPositionStandardScheme extends StandardScheme<TradeAccountNetPosition> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TradeAccountNetPosition struct) throws org.apache.thrift.TException {
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
          case 2: // SLED_NET_POSITION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sledNetPosition = iprot.readI32();
              struct.setSledNetPositionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UPSIDE_NET_POSITION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.upsideNetPosition = iprot.readI32();
              struct.setUpsideNetPositionIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TradeAccountNetPosition struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSledNetPosition()) {
        oprot.writeFieldBegin(SLED_NET_POSITION_FIELD_DESC);
        oprot.writeI32(struct.sledNetPosition);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUpsideNetPosition()) {
        oprot.writeFieldBegin(UPSIDE_NET_POSITION_FIELD_DESC);
        oprot.writeI32(struct.upsideNetPosition);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TradeAccountNetPositionTupleSchemeFactory implements SchemeFactory {
    public TradeAccountNetPositionTupleScheme getScheme() {
      return new TradeAccountNetPositionTupleScheme();
    }
  }

  private static class TradeAccountNetPositionTupleScheme extends TupleScheme<TradeAccountNetPosition> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TradeAccountNetPosition struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSledContractId()) {
        optionals.set(0);
      }
      if (struct.isSetSledNetPosition()) {
        optionals.set(1);
      }
      if (struct.isSetUpsideNetPosition()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
      if (struct.isSetSledNetPosition()) {
        oprot.writeI32(struct.sledNetPosition);
      }
      if (struct.isSetUpsideNetPosition()) {
        oprot.writeI32(struct.upsideNetPosition);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TradeAccountNetPosition struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sledNetPosition = iprot.readI32();
        struct.setSledNetPositionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.upsideNetPosition = iprot.readI32();
        struct.setUpsideNetPositionIsSet(true);
      }
    }
  }

}

