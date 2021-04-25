/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.events;

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
 * 操作账号过期合约删除事件，使用 PushApi 推送
 */
public class ExpiredContractDeleteEvent implements org.apache.thrift.TBase<ExpiredContractDeleteEvent, ExpiredContractDeleteEvent._Fields>, java.io.Serializable, Cloneable, Comparable<ExpiredContractDeleteEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ExpiredContractDeleteEvent");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField EVENT_CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("eventCreateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ExpiredContractDeleteEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ExpiredContractDeleteEventTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public long sledContractId; // optional
  public long eventCreateTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    SLED_CONTRACT_ID((short)2, "sledContractId"),
    EVENT_CREATE_TIMESTAMP_MS((short)3, "eventCreateTimestampMs");

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
        case 2: // SLED_CONTRACT_ID
          return SLED_CONTRACT_ID;
        case 3: // EVENT_CREATE_TIMESTAMP_MS
          return EVENT_CREATE_TIMESTAMP_MS;
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
  private static final int __SLEDCONTRACTID_ISSET_ID = 1;
  private static final int __EVENTCREATETIMESTAMPMS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.SLED_CONTRACT_ID,_Fields.EVENT_CREATE_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.EVENT_CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("eventCreateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ExpiredContractDeleteEvent.class, metaDataMap);
  }

  public ExpiredContractDeleteEvent() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ExpiredContractDeleteEvent(ExpiredContractDeleteEvent other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    this.sledContractId = other.sledContractId;
    this.eventCreateTimestampMs = other.eventCreateTimestampMs;
  }

  public ExpiredContractDeleteEvent deepCopy() {
    return new ExpiredContractDeleteEvent(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
    setEventCreateTimestampMsIsSet(false);
    this.eventCreateTimestampMs = 0;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public ExpiredContractDeleteEvent setSubAccountId(long subAccountId) {
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

  public long getSledContractId() {
    return this.sledContractId;
  }

  public ExpiredContractDeleteEvent setSledContractId(long sledContractId) {
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

  public long getEventCreateTimestampMs() {
    return this.eventCreateTimestampMs;
  }

  public ExpiredContractDeleteEvent setEventCreateTimestampMs(long eventCreateTimestampMs) {
    this.eventCreateTimestampMs = eventCreateTimestampMs;
    setEventCreateTimestampMsIsSet(true);
    return this;
  }

  public void unsetEventCreateTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __EVENTCREATETIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field eventCreateTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetEventCreateTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __EVENTCREATETIMESTAMPMS_ISSET_ID);
  }

  public void setEventCreateTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __EVENTCREATETIMESTAMPMS_ISSET_ID, value);
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

    case SLED_CONTRACT_ID:
      if (value == null) {
        unsetSledContractId();
      } else {
        setSledContractId((Long)value);
      }
      break;

    case EVENT_CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetEventCreateTimestampMs();
      } else {
        setEventCreateTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    case EVENT_CREATE_TIMESTAMP_MS:
      return Long.valueOf(getEventCreateTimestampMs());

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
    case SLED_CONTRACT_ID:
      return isSetSledContractId();
    case EVENT_CREATE_TIMESTAMP_MS:
      return isSetEventCreateTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ExpiredContractDeleteEvent)
      return this.equals((ExpiredContractDeleteEvent)that);
    return false;
  }

  public boolean equals(ExpiredContractDeleteEvent that) {
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

    boolean this_present_sledContractId = true && this.isSetSledContractId();
    boolean that_present_sledContractId = true && that.isSetSledContractId();
    if (this_present_sledContractId || that_present_sledContractId) {
      if (!(this_present_sledContractId && that_present_sledContractId))
        return false;
      if (this.sledContractId != that.sledContractId)
        return false;
    }

    boolean this_present_eventCreateTimestampMs = true && this.isSetEventCreateTimestampMs();
    boolean that_present_eventCreateTimestampMs = true && that.isSetEventCreateTimestampMs();
    if (this_present_eventCreateTimestampMs || that_present_eventCreateTimestampMs) {
      if (!(this_present_eventCreateTimestampMs && that_present_eventCreateTimestampMs))
        return false;
      if (this.eventCreateTimestampMs != that.eventCreateTimestampMs)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ExpiredContractDeleteEvent other) {
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
    lastComparison = Boolean.valueOf(isSetEventCreateTimestampMs()).compareTo(other.isSetEventCreateTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEventCreateTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.eventCreateTimestampMs, other.eventCreateTimestampMs);
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
    StringBuilder sb = new StringBuilder("ExpiredContractDeleteEvent(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetSledContractId()) {
      if (!first) sb.append(", ");
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
      first = false;
    }
    if (isSetEventCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("eventCreateTimestampMs:");
      sb.append(this.eventCreateTimestampMs);
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

  private static class ExpiredContractDeleteEventStandardSchemeFactory implements SchemeFactory {
    public ExpiredContractDeleteEventStandardScheme getScheme() {
      return new ExpiredContractDeleteEventStandardScheme();
    }
  }

  private static class ExpiredContractDeleteEventStandardScheme extends StandardScheme<ExpiredContractDeleteEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ExpiredContractDeleteEvent struct) throws org.apache.thrift.TException {
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
          case 2: // SLED_CONTRACT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sledContractId = iprot.readI64();
              struct.setSledContractIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // EVENT_CREATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.eventCreateTimestampMs = iprot.readI64();
              struct.setEventCreateTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ExpiredContractDeleteEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetEventCreateTimestampMs()) {
        oprot.writeFieldBegin(EVENT_CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.eventCreateTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ExpiredContractDeleteEventTupleSchemeFactory implements SchemeFactory {
    public ExpiredContractDeleteEventTupleScheme getScheme() {
      return new ExpiredContractDeleteEventTupleScheme();
    }
  }

  private static class ExpiredContractDeleteEventTupleScheme extends TupleScheme<ExpiredContractDeleteEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ExpiredContractDeleteEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetSledContractId()) {
        optionals.set(1);
      }
      if (struct.isSetEventCreateTimestampMs()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
      if (struct.isSetEventCreateTimestampMs()) {
        oprot.writeI64(struct.eventCreateTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ExpiredContractDeleteEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.eventCreateTimestampMs = iprot.readI64();
        struct.setEventCreateTimestampMsIsSet(true);
      }
    }
  }

}

