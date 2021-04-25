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

public class UpsideOrderDeleteFailedEvent implements org.apache.thrift.TBase<UpsideOrderDeleteFailedEvent, UpsideOrderDeleteFailedEvent._Fields>, java.io.Serializable, Cloneable, Comparable<UpsideOrderDeleteFailedEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UpsideOrderDeleteFailedEvent");

  private static final org.apache.thrift.protocol.TField EXEC_ORDER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("execOrderId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField UPSIDE_ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("upsideErrorCode", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField UPSIDE_ERROR_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("upsideErrorMsg", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField EVENT_CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("eventCreateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField MAPPING_ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("mappingErrorCode", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UpsideOrderDeleteFailedEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UpsideOrderDeleteFailedEventTupleSchemeFactory());
  }

  public long execOrderId; // optional
  public int upsideErrorCode; // optional
  public String upsideErrorMsg; // optional
  public long eventCreateTimestampMs; // optional
  public int mappingErrorCode; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    EXEC_ORDER_ID((short)1, "execOrderId"),
    UPSIDE_ERROR_CODE((short)2, "upsideErrorCode"),
    UPSIDE_ERROR_MSG((short)3, "upsideErrorMsg"),
    EVENT_CREATE_TIMESTAMP_MS((short)4, "eventCreateTimestampMs"),
    MAPPING_ERROR_CODE((short)5, "mappingErrorCode");

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
        case 1: // EXEC_ORDER_ID
          return EXEC_ORDER_ID;
        case 2: // UPSIDE_ERROR_CODE
          return UPSIDE_ERROR_CODE;
        case 3: // UPSIDE_ERROR_MSG
          return UPSIDE_ERROR_MSG;
        case 4: // EVENT_CREATE_TIMESTAMP_MS
          return EVENT_CREATE_TIMESTAMP_MS;
        case 5: // MAPPING_ERROR_CODE
          return MAPPING_ERROR_CODE;
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
  private static final int __EXECORDERID_ISSET_ID = 0;
  private static final int __UPSIDEERRORCODE_ISSET_ID = 1;
  private static final int __EVENTCREATETIMESTAMPMS_ISSET_ID = 2;
  private static final int __MAPPINGERRORCODE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.EXEC_ORDER_ID,_Fields.UPSIDE_ERROR_CODE,_Fields.UPSIDE_ERROR_MSG,_Fields.EVENT_CREATE_TIMESTAMP_MS,_Fields.MAPPING_ERROR_CODE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EXEC_ORDER_ID, new org.apache.thrift.meta_data.FieldMetaData("execOrderId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.UPSIDE_ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("upsideErrorCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.UPSIDE_ERROR_MSG, new org.apache.thrift.meta_data.FieldMetaData("upsideErrorMsg", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EVENT_CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("eventCreateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MAPPING_ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("mappingErrorCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UpsideOrderDeleteFailedEvent.class, metaDataMap);
  }

  public UpsideOrderDeleteFailedEvent() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UpsideOrderDeleteFailedEvent(UpsideOrderDeleteFailedEvent other) {
    __isset_bitfield = other.__isset_bitfield;
    this.execOrderId = other.execOrderId;
    this.upsideErrorCode = other.upsideErrorCode;
    if (other.isSetUpsideErrorMsg()) {
      this.upsideErrorMsg = other.upsideErrorMsg;
    }
    this.eventCreateTimestampMs = other.eventCreateTimestampMs;
    this.mappingErrorCode = other.mappingErrorCode;
  }

  public UpsideOrderDeleteFailedEvent deepCopy() {
    return new UpsideOrderDeleteFailedEvent(this);
  }

  @Override
  public void clear() {
    setExecOrderIdIsSet(false);
    this.execOrderId = 0;
    setUpsideErrorCodeIsSet(false);
    this.upsideErrorCode = 0;
    this.upsideErrorMsg = null;
    setEventCreateTimestampMsIsSet(false);
    this.eventCreateTimestampMs = 0;
    setMappingErrorCodeIsSet(false);
    this.mappingErrorCode = 0;
  }

  public long getExecOrderId() {
    return this.execOrderId;
  }

  public UpsideOrderDeleteFailedEvent setExecOrderId(long execOrderId) {
    this.execOrderId = execOrderId;
    setExecOrderIdIsSet(true);
    return this;
  }

  public void unsetExecOrderId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __EXECORDERID_ISSET_ID);
  }

  /** Returns true if field execOrderId is set (has been assigned a value) and false otherwise */
  public boolean isSetExecOrderId() {
    return EncodingUtils.testBit(__isset_bitfield, __EXECORDERID_ISSET_ID);
  }

  public void setExecOrderIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __EXECORDERID_ISSET_ID, value);
  }

  public int getUpsideErrorCode() {
    return this.upsideErrorCode;
  }

  public UpsideOrderDeleteFailedEvent setUpsideErrorCode(int upsideErrorCode) {
    this.upsideErrorCode = upsideErrorCode;
    setUpsideErrorCodeIsSet(true);
    return this;
  }

  public void unsetUpsideErrorCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UPSIDEERRORCODE_ISSET_ID);
  }

  /** Returns true if field upsideErrorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetUpsideErrorCode() {
    return EncodingUtils.testBit(__isset_bitfield, __UPSIDEERRORCODE_ISSET_ID);
  }

  public void setUpsideErrorCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UPSIDEERRORCODE_ISSET_ID, value);
  }

  public String getUpsideErrorMsg() {
    return this.upsideErrorMsg;
  }

  public UpsideOrderDeleteFailedEvent setUpsideErrorMsg(String upsideErrorMsg) {
    this.upsideErrorMsg = upsideErrorMsg;
    return this;
  }

  public void unsetUpsideErrorMsg() {
    this.upsideErrorMsg = null;
  }

  /** Returns true if field upsideErrorMsg is set (has been assigned a value) and false otherwise */
  public boolean isSetUpsideErrorMsg() {
    return this.upsideErrorMsg != null;
  }

  public void setUpsideErrorMsgIsSet(boolean value) {
    if (!value) {
      this.upsideErrorMsg = null;
    }
  }

  public long getEventCreateTimestampMs() {
    return this.eventCreateTimestampMs;
  }

  public UpsideOrderDeleteFailedEvent setEventCreateTimestampMs(long eventCreateTimestampMs) {
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

  public int getMappingErrorCode() {
    return this.mappingErrorCode;
  }

  public UpsideOrderDeleteFailedEvent setMappingErrorCode(int mappingErrorCode) {
    this.mappingErrorCode = mappingErrorCode;
    setMappingErrorCodeIsSet(true);
    return this;
  }

  public void unsetMappingErrorCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MAPPINGERRORCODE_ISSET_ID);
  }

  /** Returns true if field mappingErrorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetMappingErrorCode() {
    return EncodingUtils.testBit(__isset_bitfield, __MAPPINGERRORCODE_ISSET_ID);
  }

  public void setMappingErrorCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MAPPINGERRORCODE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case EXEC_ORDER_ID:
      if (value == null) {
        unsetExecOrderId();
      } else {
        setExecOrderId((Long)value);
      }
      break;

    case UPSIDE_ERROR_CODE:
      if (value == null) {
        unsetUpsideErrorCode();
      } else {
        setUpsideErrorCode((Integer)value);
      }
      break;

    case UPSIDE_ERROR_MSG:
      if (value == null) {
        unsetUpsideErrorMsg();
      } else {
        setUpsideErrorMsg((String)value);
      }
      break;

    case EVENT_CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetEventCreateTimestampMs();
      } else {
        setEventCreateTimestampMs((Long)value);
      }
      break;

    case MAPPING_ERROR_CODE:
      if (value == null) {
        unsetMappingErrorCode();
      } else {
        setMappingErrorCode((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case EXEC_ORDER_ID:
      return Long.valueOf(getExecOrderId());

    case UPSIDE_ERROR_CODE:
      return Integer.valueOf(getUpsideErrorCode());

    case UPSIDE_ERROR_MSG:
      return getUpsideErrorMsg();

    case EVENT_CREATE_TIMESTAMP_MS:
      return Long.valueOf(getEventCreateTimestampMs());

    case MAPPING_ERROR_CODE:
      return Integer.valueOf(getMappingErrorCode());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case EXEC_ORDER_ID:
      return isSetExecOrderId();
    case UPSIDE_ERROR_CODE:
      return isSetUpsideErrorCode();
    case UPSIDE_ERROR_MSG:
      return isSetUpsideErrorMsg();
    case EVENT_CREATE_TIMESTAMP_MS:
      return isSetEventCreateTimestampMs();
    case MAPPING_ERROR_CODE:
      return isSetMappingErrorCode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UpsideOrderDeleteFailedEvent)
      return this.equals((UpsideOrderDeleteFailedEvent)that);
    return false;
  }

  public boolean equals(UpsideOrderDeleteFailedEvent that) {
    if (that == null)
      return false;

    boolean this_present_execOrderId = true && this.isSetExecOrderId();
    boolean that_present_execOrderId = true && that.isSetExecOrderId();
    if (this_present_execOrderId || that_present_execOrderId) {
      if (!(this_present_execOrderId && that_present_execOrderId))
        return false;
      if (this.execOrderId != that.execOrderId)
        return false;
    }

    boolean this_present_upsideErrorCode = true && this.isSetUpsideErrorCode();
    boolean that_present_upsideErrorCode = true && that.isSetUpsideErrorCode();
    if (this_present_upsideErrorCode || that_present_upsideErrorCode) {
      if (!(this_present_upsideErrorCode && that_present_upsideErrorCode))
        return false;
      if (this.upsideErrorCode != that.upsideErrorCode)
        return false;
    }

    boolean this_present_upsideErrorMsg = true && this.isSetUpsideErrorMsg();
    boolean that_present_upsideErrorMsg = true && that.isSetUpsideErrorMsg();
    if (this_present_upsideErrorMsg || that_present_upsideErrorMsg) {
      if (!(this_present_upsideErrorMsg && that_present_upsideErrorMsg))
        return false;
      if (!this.upsideErrorMsg.equals(that.upsideErrorMsg))
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

    boolean this_present_mappingErrorCode = true && this.isSetMappingErrorCode();
    boolean that_present_mappingErrorCode = true && that.isSetMappingErrorCode();
    if (this_present_mappingErrorCode || that_present_mappingErrorCode) {
      if (!(this_present_mappingErrorCode && that_present_mappingErrorCode))
        return false;
      if (this.mappingErrorCode != that.mappingErrorCode)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(UpsideOrderDeleteFailedEvent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetExecOrderId()).compareTo(other.isSetExecOrderId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExecOrderId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.execOrderId, other.execOrderId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpsideErrorCode()).compareTo(other.isSetUpsideErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpsideErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.upsideErrorCode, other.upsideErrorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpsideErrorMsg()).compareTo(other.isSetUpsideErrorMsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpsideErrorMsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.upsideErrorMsg, other.upsideErrorMsg);
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
    lastComparison = Boolean.valueOf(isSetMappingErrorCode()).compareTo(other.isSetMappingErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMappingErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mappingErrorCode, other.mappingErrorCode);
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
    StringBuilder sb = new StringBuilder("UpsideOrderDeleteFailedEvent(");
    boolean first = true;

    if (isSetExecOrderId()) {
      sb.append("execOrderId:");
      sb.append(this.execOrderId);
      first = false;
    }
    if (isSetUpsideErrorCode()) {
      if (!first) sb.append(", ");
      sb.append("upsideErrorCode:");
      sb.append(this.upsideErrorCode);
      first = false;
    }
    if (isSetUpsideErrorMsg()) {
      if (!first) sb.append(", ");
      sb.append("upsideErrorMsg:");
      if (this.upsideErrorMsg == null) {
        sb.append("null");
      } else {
        sb.append(this.upsideErrorMsg);
      }
      first = false;
    }
    if (isSetEventCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("eventCreateTimestampMs:");
      sb.append(this.eventCreateTimestampMs);
      first = false;
    }
    if (isSetMappingErrorCode()) {
      if (!first) sb.append(", ");
      sb.append("mappingErrorCode:");
      sb.append(this.mappingErrorCode);
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

  private static class UpsideOrderDeleteFailedEventStandardSchemeFactory implements SchemeFactory {
    public UpsideOrderDeleteFailedEventStandardScheme getScheme() {
      return new UpsideOrderDeleteFailedEventStandardScheme();
    }
  }

  private static class UpsideOrderDeleteFailedEventStandardScheme extends StandardScheme<UpsideOrderDeleteFailedEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UpsideOrderDeleteFailedEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // EXEC_ORDER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.execOrderId = iprot.readI64();
              struct.setExecOrderIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // UPSIDE_ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.upsideErrorCode = iprot.readI32();
              struct.setUpsideErrorCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // UPSIDE_ERROR_MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.upsideErrorMsg = iprot.readString();
              struct.setUpsideErrorMsgIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // EVENT_CREATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.eventCreateTimestampMs = iprot.readI64();
              struct.setEventCreateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MAPPING_ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.mappingErrorCode = iprot.readI32();
              struct.setMappingErrorCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UpsideOrderDeleteFailedEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetExecOrderId()) {
        oprot.writeFieldBegin(EXEC_ORDER_ID_FIELD_DESC);
        oprot.writeI64(struct.execOrderId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUpsideErrorCode()) {
        oprot.writeFieldBegin(UPSIDE_ERROR_CODE_FIELD_DESC);
        oprot.writeI32(struct.upsideErrorCode);
        oprot.writeFieldEnd();
      }
      if (struct.upsideErrorMsg != null) {
        if (struct.isSetUpsideErrorMsg()) {
          oprot.writeFieldBegin(UPSIDE_ERROR_MSG_FIELD_DESC);
          oprot.writeString(struct.upsideErrorMsg);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetEventCreateTimestampMs()) {
        oprot.writeFieldBegin(EVENT_CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.eventCreateTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.isSetMappingErrorCode()) {
        oprot.writeFieldBegin(MAPPING_ERROR_CODE_FIELD_DESC);
        oprot.writeI32(struct.mappingErrorCode);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UpsideOrderDeleteFailedEventTupleSchemeFactory implements SchemeFactory {
    public UpsideOrderDeleteFailedEventTupleScheme getScheme() {
      return new UpsideOrderDeleteFailedEventTupleScheme();
    }
  }

  private static class UpsideOrderDeleteFailedEventTupleScheme extends TupleScheme<UpsideOrderDeleteFailedEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UpsideOrderDeleteFailedEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetExecOrderId()) {
        optionals.set(0);
      }
      if (struct.isSetUpsideErrorCode()) {
        optionals.set(1);
      }
      if (struct.isSetUpsideErrorMsg()) {
        optionals.set(2);
      }
      if (struct.isSetEventCreateTimestampMs()) {
        optionals.set(3);
      }
      if (struct.isSetMappingErrorCode()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetExecOrderId()) {
        oprot.writeI64(struct.execOrderId);
      }
      if (struct.isSetUpsideErrorCode()) {
        oprot.writeI32(struct.upsideErrorCode);
      }
      if (struct.isSetUpsideErrorMsg()) {
        oprot.writeString(struct.upsideErrorMsg);
      }
      if (struct.isSetEventCreateTimestampMs()) {
        oprot.writeI64(struct.eventCreateTimestampMs);
      }
      if (struct.isSetMappingErrorCode()) {
        oprot.writeI32(struct.mappingErrorCode);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UpsideOrderDeleteFailedEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.execOrderId = iprot.readI64();
        struct.setExecOrderIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.upsideErrorCode = iprot.readI32();
        struct.setUpsideErrorCodeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.upsideErrorMsg = iprot.readString();
        struct.setUpsideErrorMsgIsSet(true);
      }
      if (incoming.get(3)) {
        struct.eventCreateTimestampMs = iprot.readI64();
        struct.setEventCreateTimestampMsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.mappingErrorCode = iprot.readI32();
        struct.setMappingErrorCodeIsSet(true);
      }
    }
  }

}

