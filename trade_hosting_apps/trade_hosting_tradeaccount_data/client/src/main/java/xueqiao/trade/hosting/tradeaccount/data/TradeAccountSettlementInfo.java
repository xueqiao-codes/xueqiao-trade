/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.tradeaccount.data;

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

public class TradeAccountSettlementInfo implements org.apache.thrift.TBase<TradeAccountSettlementInfo, TradeAccountSettlementInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TradeAccountSettlementInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TradeAccountSettlementInfo");

  private static final org.apache.thrift.protocol.TField TRADE_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SETTLEMENT_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("settlementDate", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SETTLEMENT_CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("settlementContent", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestampMs", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TradeAccountSettlementInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TradeAccountSettlementInfoTupleSchemeFactory());
  }

  public long tradeAccountId; // optional
  public String settlementDate; // optional
  public String settlementContent; // optional
  public long createTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRADE_ACCOUNT_ID((short)1, "tradeAccountId"),
    SETTLEMENT_DATE((short)2, "settlementDate"),
    SETTLEMENT_CONTENT((short)3, "settlementContent"),
    CREATE_TIMESTAMP_MS((short)4, "createTimestampMs");

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
        case 1: // TRADE_ACCOUNT_ID
          return TRADE_ACCOUNT_ID;
        case 2: // SETTLEMENT_DATE
          return SETTLEMENT_DATE;
        case 3: // SETTLEMENT_CONTENT
          return SETTLEMENT_CONTENT;
        case 4: // CREATE_TIMESTAMP_MS
          return CREATE_TIMESTAMP_MS;
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
  private static final int __TRADEACCOUNTID_ISSET_ID = 0;
  private static final int __CREATETIMESTAMPMS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TRADE_ACCOUNT_ID,_Fields.SETTLEMENT_DATE,_Fields.SETTLEMENT_CONTENT,_Fields.CREATE_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRADE_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("tradeAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SETTLEMENT_DATE, new org.apache.thrift.meta_data.FieldMetaData("settlementDate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SETTLEMENT_CONTENT, new org.apache.thrift.meta_data.FieldMetaData("settlementContent", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("createTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TradeAccountSettlementInfo.class, metaDataMap);
  }

  public TradeAccountSettlementInfo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TradeAccountSettlementInfo(TradeAccountSettlementInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.tradeAccountId = other.tradeAccountId;
    if (other.isSetSettlementDate()) {
      this.settlementDate = other.settlementDate;
    }
    if (other.isSetSettlementContent()) {
      this.settlementContent = other.settlementContent;
    }
    this.createTimestampMs = other.createTimestampMs;
  }

  public TradeAccountSettlementInfo deepCopy() {
    return new TradeAccountSettlementInfo(this);
  }

  @Override
  public void clear() {
    setTradeAccountIdIsSet(false);
    this.tradeAccountId = 0;
    this.settlementDate = null;
    this.settlementContent = null;
    setCreateTimestampMsIsSet(false);
    this.createTimestampMs = 0;
  }

  public long getTradeAccountId() {
    return this.tradeAccountId;
  }

  public TradeAccountSettlementInfo setTradeAccountId(long tradeAccountId) {
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

  public String getSettlementDate() {
    return this.settlementDate;
  }

  public TradeAccountSettlementInfo setSettlementDate(String settlementDate) {
    this.settlementDate = settlementDate;
    return this;
  }

  public void unsetSettlementDate() {
    this.settlementDate = null;
  }

  /** Returns true if field settlementDate is set (has been assigned a value) and false otherwise */
  public boolean isSetSettlementDate() {
    return this.settlementDate != null;
  }

  public void setSettlementDateIsSet(boolean value) {
    if (!value) {
      this.settlementDate = null;
    }
  }

  public String getSettlementContent() {
    return this.settlementContent;
  }

  public TradeAccountSettlementInfo setSettlementContent(String settlementContent) {
    this.settlementContent = settlementContent;
    return this;
  }

  public void unsetSettlementContent() {
    this.settlementContent = null;
  }

  /** Returns true if field settlementContent is set (has been assigned a value) and false otherwise */
  public boolean isSetSettlementContent() {
    return this.settlementContent != null;
  }

  public void setSettlementContentIsSet(boolean value) {
    if (!value) {
      this.settlementContent = null;
    }
  }

  public long getCreateTimestampMs() {
    return this.createTimestampMs;
  }

  public TradeAccountSettlementInfo setCreateTimestampMs(long createTimestampMs) {
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

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TRADE_ACCOUNT_ID:
      if (value == null) {
        unsetTradeAccountId();
      } else {
        setTradeAccountId((Long)value);
      }
      break;

    case SETTLEMENT_DATE:
      if (value == null) {
        unsetSettlementDate();
      } else {
        setSettlementDate((String)value);
      }
      break;

    case SETTLEMENT_CONTENT:
      if (value == null) {
        unsetSettlementContent();
      } else {
        setSettlementContent((String)value);
      }
      break;

    case CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetCreateTimestampMs();
      } else {
        setCreateTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRADE_ACCOUNT_ID:
      return Long.valueOf(getTradeAccountId());

    case SETTLEMENT_DATE:
      return getSettlementDate();

    case SETTLEMENT_CONTENT:
      return getSettlementContent();

    case CREATE_TIMESTAMP_MS:
      return Long.valueOf(getCreateTimestampMs());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TRADE_ACCOUNT_ID:
      return isSetTradeAccountId();
    case SETTLEMENT_DATE:
      return isSetSettlementDate();
    case SETTLEMENT_CONTENT:
      return isSetSettlementContent();
    case CREATE_TIMESTAMP_MS:
      return isSetCreateTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TradeAccountSettlementInfo)
      return this.equals((TradeAccountSettlementInfo)that);
    return false;
  }

  public boolean equals(TradeAccountSettlementInfo that) {
    if (that == null)
      return false;

    boolean this_present_tradeAccountId = true && this.isSetTradeAccountId();
    boolean that_present_tradeAccountId = true && that.isSetTradeAccountId();
    if (this_present_tradeAccountId || that_present_tradeAccountId) {
      if (!(this_present_tradeAccountId && that_present_tradeAccountId))
        return false;
      if (this.tradeAccountId != that.tradeAccountId)
        return false;
    }

    boolean this_present_settlementDate = true && this.isSetSettlementDate();
    boolean that_present_settlementDate = true && that.isSetSettlementDate();
    if (this_present_settlementDate || that_present_settlementDate) {
      if (!(this_present_settlementDate && that_present_settlementDate))
        return false;
      if (!this.settlementDate.equals(that.settlementDate))
        return false;
    }

    boolean this_present_settlementContent = true && this.isSetSettlementContent();
    boolean that_present_settlementContent = true && that.isSetSettlementContent();
    if (this_present_settlementContent || that_present_settlementContent) {
      if (!(this_present_settlementContent && that_present_settlementContent))
        return false;
      if (!this.settlementContent.equals(that.settlementContent))
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

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TradeAccountSettlementInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetSettlementDate()).compareTo(other.isSetSettlementDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSettlementDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.settlementDate, other.settlementDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSettlementContent()).compareTo(other.isSetSettlementContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSettlementContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.settlementContent, other.settlementContent);
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
    StringBuilder sb = new StringBuilder("TradeAccountSettlementInfo(");
    boolean first = true;

    if (isSetTradeAccountId()) {
      sb.append("tradeAccountId:");
      sb.append(this.tradeAccountId);
      first = false;
    }
    if (isSetSettlementDate()) {
      if (!first) sb.append(", ");
      sb.append("settlementDate:");
      if (this.settlementDate == null) {
        sb.append("null");
      } else {
        sb.append(this.settlementDate);
      }
      first = false;
    }
    if (isSetSettlementContent()) {
      if (!first) sb.append(", ");
      sb.append("settlementContent:");
      if (this.settlementContent == null) {
        sb.append("null");
      } else {
        sb.append(this.settlementContent);
      }
      first = false;
    }
    if (isSetCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("createTimestampMs:");
      sb.append(this.createTimestampMs);
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

  private static class TradeAccountSettlementInfoStandardSchemeFactory implements SchemeFactory {
    public TradeAccountSettlementInfoStandardScheme getScheme() {
      return new TradeAccountSettlementInfoStandardScheme();
    }
  }

  private static class TradeAccountSettlementInfoStandardScheme extends StandardScheme<TradeAccountSettlementInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TradeAccountSettlementInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TRADE_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.tradeAccountId = iprot.readI64();
              struct.setTradeAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SETTLEMENT_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.settlementDate = iprot.readString();
              struct.setSettlementDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SETTLEMENT_CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.settlementContent = iprot.readString();
              struct.setSettlementContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CREATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTimestampMs = iprot.readI64();
              struct.setCreateTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TradeAccountSettlementInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTradeAccountId()) {
        oprot.writeFieldBegin(TRADE_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.tradeAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.settlementDate != null) {
        if (struct.isSetSettlementDate()) {
          oprot.writeFieldBegin(SETTLEMENT_DATE_FIELD_DESC);
          oprot.writeString(struct.settlementDate);
          oprot.writeFieldEnd();
        }
      }
      if (struct.settlementContent != null) {
        if (struct.isSetSettlementContent()) {
          oprot.writeFieldBegin(SETTLEMENT_CONTENT_FIELD_DESC);
          oprot.writeString(struct.settlementContent);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.createTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TradeAccountSettlementInfoTupleSchemeFactory implements SchemeFactory {
    public TradeAccountSettlementInfoTupleScheme getScheme() {
      return new TradeAccountSettlementInfoTupleScheme();
    }
  }

  private static class TradeAccountSettlementInfoTupleScheme extends TupleScheme<TradeAccountSettlementInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TradeAccountSettlementInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTradeAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetSettlementDate()) {
        optionals.set(1);
      }
      if (struct.isSetSettlementContent()) {
        optionals.set(2);
      }
      if (struct.isSetCreateTimestampMs()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTradeAccountId()) {
        oprot.writeI64(struct.tradeAccountId);
      }
      if (struct.isSetSettlementDate()) {
        oprot.writeString(struct.settlementDate);
      }
      if (struct.isSetSettlementContent()) {
        oprot.writeString(struct.settlementContent);
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeI64(struct.createTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TradeAccountSettlementInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.tradeAccountId = iprot.readI64();
        struct.setTradeAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.settlementDate = iprot.readString();
        struct.setSettlementDateIsSet(true);
      }
      if (incoming.get(2)) {
        struct.settlementContent = iprot.readString();
        struct.setSettlementContentIsSet(true);
      }
      if (incoming.get(3)) {
        struct.createTimestampMs = iprot.readI64();
        struct.setCreateTimestampMsIsSet(true);
      }
    }
  }

}

