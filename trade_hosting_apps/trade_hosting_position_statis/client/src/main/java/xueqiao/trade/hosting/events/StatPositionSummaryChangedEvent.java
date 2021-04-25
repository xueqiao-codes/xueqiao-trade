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
 * 实时统计持仓  汇总  变化信息事件，使用 MessageAgent 推送
 * 推送重新计算的持仓汇总(StatPositionSummary)
 */
public class StatPositionSummaryChangedEvent implements org.apache.thrift.TBase<StatPositionSummaryChangedEvent, StatPositionSummaryChangedEvent._Fields>, java.io.Serializable, Cloneable, Comparable<StatPositionSummaryChangedEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StatPositionSummaryChangedEvent");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField STAT_POSITION_SUMMARY_FIELD_DESC = new org.apache.thrift.protocol.TField("statPositionSummary", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField EVENT_CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("eventCreateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField EVENT_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("eventType", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StatPositionSummaryChangedEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StatPositionSummaryChangedEventTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public xueqiao.trade.hosting.position.statis.StatPositionSummary statPositionSummary; // optional
  public long eventCreateTimestampMs; // optional
  /**
   * 
   * @see StatPositionEventType
   */
  public StatPositionEventType eventType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    STAT_POSITION_SUMMARY((short)2, "statPositionSummary"),
    EVENT_CREATE_TIMESTAMP_MS((short)3, "eventCreateTimestampMs"),
    /**
     * 
     * @see StatPositionEventType
     */
    EVENT_TYPE((short)4, "eventType");

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
        case 2: // STAT_POSITION_SUMMARY
          return STAT_POSITION_SUMMARY;
        case 3: // EVENT_CREATE_TIMESTAMP_MS
          return EVENT_CREATE_TIMESTAMP_MS;
        case 4: // EVENT_TYPE
          return EVENT_TYPE;
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
  private static final int __EVENTCREATETIMESTAMPMS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.STAT_POSITION_SUMMARY,_Fields.EVENT_CREATE_TIMESTAMP_MS,_Fields.EVENT_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.STAT_POSITION_SUMMARY, new org.apache.thrift.meta_data.FieldMetaData("statPositionSummary", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.position.statis.StatPositionSummary.class)));
    tmpMap.put(_Fields.EVENT_CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("eventCreateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.EVENT_TYPE, new org.apache.thrift.meta_data.FieldMetaData("eventType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, StatPositionEventType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatPositionSummaryChangedEvent.class, metaDataMap);
  }

  public StatPositionSummaryChangedEvent() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StatPositionSummaryChangedEvent(StatPositionSummaryChangedEvent other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    if (other.isSetStatPositionSummary()) {
      this.statPositionSummary = new xueqiao.trade.hosting.position.statis.StatPositionSummary(other.statPositionSummary);
    }
    this.eventCreateTimestampMs = other.eventCreateTimestampMs;
    if (other.isSetEventType()) {
      this.eventType = other.eventType;
    }
  }

  public StatPositionSummaryChangedEvent deepCopy() {
    return new StatPositionSummaryChangedEvent(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.statPositionSummary = null;
    setEventCreateTimestampMsIsSet(false);
    this.eventCreateTimestampMs = 0;
    this.eventType = null;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public StatPositionSummaryChangedEvent setSubAccountId(long subAccountId) {
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

  public xueqiao.trade.hosting.position.statis.StatPositionSummary getStatPositionSummary() {
    return this.statPositionSummary;
  }

  public StatPositionSummaryChangedEvent setStatPositionSummary(xueqiao.trade.hosting.position.statis.StatPositionSummary statPositionSummary) {
    this.statPositionSummary = statPositionSummary;
    return this;
  }

  public void unsetStatPositionSummary() {
    this.statPositionSummary = null;
  }

  /** Returns true if field statPositionSummary is set (has been assigned a value) and false otherwise */
  public boolean isSetStatPositionSummary() {
    return this.statPositionSummary != null;
  }

  public void setStatPositionSummaryIsSet(boolean value) {
    if (!value) {
      this.statPositionSummary = null;
    }
  }

  public long getEventCreateTimestampMs() {
    return this.eventCreateTimestampMs;
  }

  public StatPositionSummaryChangedEvent setEventCreateTimestampMs(long eventCreateTimestampMs) {
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

  /**
   * 
   * @see StatPositionEventType
   */
  public StatPositionEventType getEventType() {
    return this.eventType;
  }

  /**
   * 
   * @see StatPositionEventType
   */
  public StatPositionSummaryChangedEvent setEventType(StatPositionEventType eventType) {
    this.eventType = eventType;
    return this;
  }

  public void unsetEventType() {
    this.eventType = null;
  }

  /** Returns true if field eventType is set (has been assigned a value) and false otherwise */
  public boolean isSetEventType() {
    return this.eventType != null;
  }

  public void setEventTypeIsSet(boolean value) {
    if (!value) {
      this.eventType = null;
    }
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

    case STAT_POSITION_SUMMARY:
      if (value == null) {
        unsetStatPositionSummary();
      } else {
        setStatPositionSummary((xueqiao.trade.hosting.position.statis.StatPositionSummary)value);
      }
      break;

    case EVENT_CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetEventCreateTimestampMs();
      } else {
        setEventCreateTimestampMs((Long)value);
      }
      break;

    case EVENT_TYPE:
      if (value == null) {
        unsetEventType();
      } else {
        setEventType((StatPositionEventType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case STAT_POSITION_SUMMARY:
      return getStatPositionSummary();

    case EVENT_CREATE_TIMESTAMP_MS:
      return Long.valueOf(getEventCreateTimestampMs());

    case EVENT_TYPE:
      return getEventType();

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
    case STAT_POSITION_SUMMARY:
      return isSetStatPositionSummary();
    case EVENT_CREATE_TIMESTAMP_MS:
      return isSetEventCreateTimestampMs();
    case EVENT_TYPE:
      return isSetEventType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StatPositionSummaryChangedEvent)
      return this.equals((StatPositionSummaryChangedEvent)that);
    return false;
  }

  public boolean equals(StatPositionSummaryChangedEvent that) {
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

    boolean this_present_statPositionSummary = true && this.isSetStatPositionSummary();
    boolean that_present_statPositionSummary = true && that.isSetStatPositionSummary();
    if (this_present_statPositionSummary || that_present_statPositionSummary) {
      if (!(this_present_statPositionSummary && that_present_statPositionSummary))
        return false;
      if (!this.statPositionSummary.equals(that.statPositionSummary))
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

    boolean this_present_eventType = true && this.isSetEventType();
    boolean that_present_eventType = true && that.isSetEventType();
    if (this_present_eventType || that_present_eventType) {
      if (!(this_present_eventType && that_present_eventType))
        return false;
      if (!this.eventType.equals(that.eventType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StatPositionSummaryChangedEvent other) {
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
    lastComparison = Boolean.valueOf(isSetStatPositionSummary()).compareTo(other.isSetStatPositionSummary());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatPositionSummary()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statPositionSummary, other.statPositionSummary);
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
    lastComparison = Boolean.valueOf(isSetEventType()).compareTo(other.isSetEventType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEventType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.eventType, other.eventType);
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
    StringBuilder sb = new StringBuilder("StatPositionSummaryChangedEvent(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetStatPositionSummary()) {
      if (!first) sb.append(", ");
      sb.append("statPositionSummary:");
      if (this.statPositionSummary == null) {
        sb.append("null");
      } else {
        sb.append(this.statPositionSummary);
      }
      first = false;
    }
    if (isSetEventCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("eventCreateTimestampMs:");
      sb.append(this.eventCreateTimestampMs);
      first = false;
    }
    if (isSetEventType()) {
      if (!first) sb.append(", ");
      sb.append("eventType:");
      if (this.eventType == null) {
        sb.append("null");
      } else {
        sb.append(this.eventType);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (statPositionSummary != null) {
      statPositionSummary.validate();
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

  private static class StatPositionSummaryChangedEventStandardSchemeFactory implements SchemeFactory {
    public StatPositionSummaryChangedEventStandardScheme getScheme() {
      return new StatPositionSummaryChangedEventStandardScheme();
    }
  }

  private static class StatPositionSummaryChangedEventStandardScheme extends StandardScheme<StatPositionSummaryChangedEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StatPositionSummaryChangedEvent struct) throws org.apache.thrift.TException {
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
          case 2: // STAT_POSITION_SUMMARY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.statPositionSummary = new xueqiao.trade.hosting.position.statis.StatPositionSummary();
              struct.statPositionSummary.read(iprot);
              struct.setStatPositionSummaryIsSet(true);
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
          case 4: // EVENT_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.eventType = StatPositionEventType.findByValue(iprot.readI32());
              struct.setEventTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StatPositionSummaryChangedEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.statPositionSummary != null) {
        if (struct.isSetStatPositionSummary()) {
          oprot.writeFieldBegin(STAT_POSITION_SUMMARY_FIELD_DESC);
          struct.statPositionSummary.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetEventCreateTimestampMs()) {
        oprot.writeFieldBegin(EVENT_CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.eventCreateTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.eventType != null) {
        if (struct.isSetEventType()) {
          oprot.writeFieldBegin(EVENT_TYPE_FIELD_DESC);
          oprot.writeI32(struct.eventType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StatPositionSummaryChangedEventTupleSchemeFactory implements SchemeFactory {
    public StatPositionSummaryChangedEventTupleScheme getScheme() {
      return new StatPositionSummaryChangedEventTupleScheme();
    }
  }

  private static class StatPositionSummaryChangedEventTupleScheme extends TupleScheme<StatPositionSummaryChangedEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StatPositionSummaryChangedEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetStatPositionSummary()) {
        optionals.set(1);
      }
      if (struct.isSetEventCreateTimestampMs()) {
        optionals.set(2);
      }
      if (struct.isSetEventType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetStatPositionSummary()) {
        struct.statPositionSummary.write(oprot);
      }
      if (struct.isSetEventCreateTimestampMs()) {
        oprot.writeI64(struct.eventCreateTimestampMs);
      }
      if (struct.isSetEventType()) {
        oprot.writeI32(struct.eventType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StatPositionSummaryChangedEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.statPositionSummary = new xueqiao.trade.hosting.position.statis.StatPositionSummary();
        struct.statPositionSummary.read(iprot);
        struct.setStatPositionSummaryIsSet(true);
      }
      if (incoming.get(2)) {
        struct.eventCreateTimestampMs = iprot.readI64();
        struct.setEventCreateTimestampMsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.eventType = StatPositionEventType.findByValue(iprot.readI32());
        struct.setEventTypeIsSet(true);
      }
    }
  }

}
