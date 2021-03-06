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

public class ReqSettlementPositionTradeDetailOption implements org.apache.thrift.TBase<ReqSettlementPositionTradeDetailOption, ReqSettlementPositionTradeDetailOption._Fields>, java.io.Serializable, Cloneable, Comparable<ReqSettlementPositionTradeDetailOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReqSettlementPositionTradeDetailOption");

  private static final org.apache.thrift.protocol.TField SETTLEMENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("settlementId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReqSettlementPositionTradeDetailOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReqSettlementPositionTradeDetailOptionTupleSchemeFactory());
  }

  public long settlementId; // optional
  public long sledContractId; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SETTLEMENT_ID((short)1, "settlementId"),
    SLED_CONTRACT_ID((short)2, "sledContractId");

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
        case 1: // SETTLEMENT_ID
          return SETTLEMENT_ID;
        case 2: // SLED_CONTRACT_ID
          return SLED_CONTRACT_ID;
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
  private static final int __SETTLEMENTID_ISSET_ID = 0;
  private static final int __SLEDCONTRACTID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SETTLEMENT_ID,_Fields.SLED_CONTRACT_ID};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SETTLEMENT_ID, new org.apache.thrift.meta_data.FieldMetaData("settlementId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReqSettlementPositionTradeDetailOption.class, metaDataMap);
  }

  public ReqSettlementPositionTradeDetailOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReqSettlementPositionTradeDetailOption(ReqSettlementPositionTradeDetailOption other) {
    __isset_bitfield = other.__isset_bitfield;
    this.settlementId = other.settlementId;
    this.sledContractId = other.sledContractId;
  }

  public ReqSettlementPositionTradeDetailOption deepCopy() {
    return new ReqSettlementPositionTradeDetailOption(this);
  }

  @Override
  public void clear() {
    setSettlementIdIsSet(false);
    this.settlementId = 0;
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
  }

  public long getSettlementId() {
    return this.settlementId;
  }

  public ReqSettlementPositionTradeDetailOption setSettlementId(long settlementId) {
    this.settlementId = settlementId;
    setSettlementIdIsSet(true);
    return this;
  }

  public void unsetSettlementId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SETTLEMENTID_ISSET_ID);
  }

  /** Returns true if field settlementId is set (has been assigned a value) and false otherwise */
  public boolean isSetSettlementId() {
    return EncodingUtils.testBit(__isset_bitfield, __SETTLEMENTID_ISSET_ID);
  }

  public void setSettlementIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SETTLEMENTID_ISSET_ID, value);
  }

  public long getSledContractId() {
    return this.sledContractId;
  }

  public ReqSettlementPositionTradeDetailOption setSledContractId(long sledContractId) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SETTLEMENT_ID:
      if (value == null) {
        unsetSettlementId();
      } else {
        setSettlementId((Long)value);
      }
      break;

    case SLED_CONTRACT_ID:
      if (value == null) {
        unsetSledContractId();
      } else {
        setSledContractId((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SETTLEMENT_ID:
      return Long.valueOf(getSettlementId());

    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SETTLEMENT_ID:
      return isSetSettlementId();
    case SLED_CONTRACT_ID:
      return isSetSledContractId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReqSettlementPositionTradeDetailOption)
      return this.equals((ReqSettlementPositionTradeDetailOption)that);
    return false;
  }

  public boolean equals(ReqSettlementPositionTradeDetailOption that) {
    if (that == null)
      return false;

    boolean this_present_settlementId = true && this.isSetSettlementId();
    boolean that_present_settlementId = true && that.isSetSettlementId();
    if (this_present_settlementId || that_present_settlementId) {
      if (!(this_present_settlementId && that_present_settlementId))
        return false;
      if (this.settlementId != that.settlementId)
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

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ReqSettlementPositionTradeDetailOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSettlementId()).compareTo(other.isSetSettlementId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSettlementId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.settlementId, other.settlementId);
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
    StringBuilder sb = new StringBuilder("ReqSettlementPositionTradeDetailOption(");
    boolean first = true;

    if (isSetSettlementId()) {
      sb.append("settlementId:");
      sb.append(this.settlementId);
      first = false;
    }
    if (isSetSledContractId()) {
      if (!first) sb.append(", ");
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
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

  private static class ReqSettlementPositionTradeDetailOptionStandardSchemeFactory implements SchemeFactory {
    public ReqSettlementPositionTradeDetailOptionStandardScheme getScheme() {
      return new ReqSettlementPositionTradeDetailOptionStandardScheme();
    }
  }

  private static class ReqSettlementPositionTradeDetailOptionStandardScheme extends StandardScheme<ReqSettlementPositionTradeDetailOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReqSettlementPositionTradeDetailOption struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SETTLEMENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.settlementId = iprot.readI64();
              struct.setSettlementIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SLED_CONTRACT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sledContractId = iprot.readI64();
              struct.setSledContractIdIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReqSettlementPositionTradeDetailOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSettlementId()) {
        oprot.writeFieldBegin(SETTLEMENT_ID_FIELD_DESC);
        oprot.writeI64(struct.settlementId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ReqSettlementPositionTradeDetailOptionTupleSchemeFactory implements SchemeFactory {
    public ReqSettlementPositionTradeDetailOptionTupleScheme getScheme() {
      return new ReqSettlementPositionTradeDetailOptionTupleScheme();
    }
  }

  private static class ReqSettlementPositionTradeDetailOptionTupleScheme extends TupleScheme<ReqSettlementPositionTradeDetailOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReqSettlementPositionTradeDetailOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSettlementId()) {
        optionals.set(0);
      }
      if (struct.isSetSledContractId()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSettlementId()) {
        oprot.writeI64(struct.settlementId);
      }
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReqSettlementPositionTradeDetailOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.settlementId = iprot.readI64();
        struct.setSettlementIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
    }
  }

}

