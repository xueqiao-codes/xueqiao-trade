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

public class PositionAssignHistory implements org.apache.thrift.TBase<PositionAssignHistory, PositionAssignHistory._Fields>, java.io.Serializable, Cloneable, Comparable<PositionAssignHistory> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PositionAssignHistory");

  private static final org.apache.thrift.protocol.TField ASSIGN_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("assignId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CONTENT_FIELD_DESC = new org.apache.thrift.protocol.TField("content", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestampMs", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField LAST_MODIFY_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("lastModifyTimestampMs", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PositionAssignHistoryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PositionAssignHistoryTupleSchemeFactory());
  }

  public long assignId; // optional
  public String content; // optional
  public long createTimestampMs; // optional
  public long lastModifyTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ASSIGN_ID((short)1, "assignId"),
    CONTENT((short)2, "content"),
    CREATE_TIMESTAMP_MS((short)3, "createTimestampMs"),
    LAST_MODIFY_TIMESTAMP_MS((short)4, "lastModifyTimestampMs");

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
        case 1: // ASSIGN_ID
          return ASSIGN_ID;
        case 2: // CONTENT
          return CONTENT;
        case 3: // CREATE_TIMESTAMP_MS
          return CREATE_TIMESTAMP_MS;
        case 4: // LAST_MODIFY_TIMESTAMP_MS
          return LAST_MODIFY_TIMESTAMP_MS;
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
  private static final int __ASSIGNID_ISSET_ID = 0;
  private static final int __CREATETIMESTAMPMS_ISSET_ID = 1;
  private static final int __LASTMODIFYTIMESTAMPMS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.ASSIGN_ID,_Fields.CONTENT,_Fields.CREATE_TIMESTAMP_MS,_Fields.LAST_MODIFY_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ASSIGN_ID, new org.apache.thrift.meta_data.FieldMetaData("assignId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CONTENT, new org.apache.thrift.meta_data.FieldMetaData("content", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("createTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LAST_MODIFY_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("lastModifyTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PositionAssignHistory.class, metaDataMap);
  }

  public PositionAssignHistory() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PositionAssignHistory(PositionAssignHistory other) {
    __isset_bitfield = other.__isset_bitfield;
    this.assignId = other.assignId;
    if (other.isSetContent()) {
      this.content = other.content;
    }
    this.createTimestampMs = other.createTimestampMs;
    this.lastModifyTimestampMs = other.lastModifyTimestampMs;
  }

  public PositionAssignHistory deepCopy() {
    return new PositionAssignHistory(this);
  }

  @Override
  public void clear() {
    setAssignIdIsSet(false);
    this.assignId = 0;
    this.content = null;
    setCreateTimestampMsIsSet(false);
    this.createTimestampMs = 0;
    setLastModifyTimestampMsIsSet(false);
    this.lastModifyTimestampMs = 0;
  }

  public long getAssignId() {
    return this.assignId;
  }

  public PositionAssignHistory setAssignId(long assignId) {
    this.assignId = assignId;
    setAssignIdIsSet(true);
    return this;
  }

  public void unsetAssignId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ASSIGNID_ISSET_ID);
  }

  /** Returns true if field assignId is set (has been assigned a value) and false otherwise */
  public boolean isSetAssignId() {
    return EncodingUtils.testBit(__isset_bitfield, __ASSIGNID_ISSET_ID);
  }

  public void setAssignIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ASSIGNID_ISSET_ID, value);
  }

  public String getContent() {
    return this.content;
  }

  public PositionAssignHistory setContent(String content) {
    this.content = content;
    return this;
  }

  public void unsetContent() {
    this.content = null;
  }

  /** Returns true if field content is set (has been assigned a value) and false otherwise */
  public boolean isSetContent() {
    return this.content != null;
  }

  public void setContentIsSet(boolean value) {
    if (!value) {
      this.content = null;
    }
  }

  public long getCreateTimestampMs() {
    return this.createTimestampMs;
  }

  public PositionAssignHistory setCreateTimestampMs(long createTimestampMs) {
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

  public long getLastModifyTimestampMs() {
    return this.lastModifyTimestampMs;
  }

  public PositionAssignHistory setLastModifyTimestampMs(long lastModifyTimestampMs) {
    this.lastModifyTimestampMs = lastModifyTimestampMs;
    setLastModifyTimestampMsIsSet(true);
    return this;
  }

  public void unsetLastModifyTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field lastModifyTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetLastModifyTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID);
  }

  public void setLastModifyTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ASSIGN_ID:
      if (value == null) {
        unsetAssignId();
      } else {
        setAssignId((Long)value);
      }
      break;

    case CONTENT:
      if (value == null) {
        unsetContent();
      } else {
        setContent((String)value);
      }
      break;

    case CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetCreateTimestampMs();
      } else {
        setCreateTimestampMs((Long)value);
      }
      break;

    case LAST_MODIFY_TIMESTAMP_MS:
      if (value == null) {
        unsetLastModifyTimestampMs();
      } else {
        setLastModifyTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ASSIGN_ID:
      return Long.valueOf(getAssignId());

    case CONTENT:
      return getContent();

    case CREATE_TIMESTAMP_MS:
      return Long.valueOf(getCreateTimestampMs());

    case LAST_MODIFY_TIMESTAMP_MS:
      return Long.valueOf(getLastModifyTimestampMs());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ASSIGN_ID:
      return isSetAssignId();
    case CONTENT:
      return isSetContent();
    case CREATE_TIMESTAMP_MS:
      return isSetCreateTimestampMs();
    case LAST_MODIFY_TIMESTAMP_MS:
      return isSetLastModifyTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PositionAssignHistory)
      return this.equals((PositionAssignHistory)that);
    return false;
  }

  public boolean equals(PositionAssignHistory that) {
    if (that == null)
      return false;

    boolean this_present_assignId = true && this.isSetAssignId();
    boolean that_present_assignId = true && that.isSetAssignId();
    if (this_present_assignId || that_present_assignId) {
      if (!(this_present_assignId && that_present_assignId))
        return false;
      if (this.assignId != that.assignId)
        return false;
    }

    boolean this_present_content = true && this.isSetContent();
    boolean that_present_content = true && that.isSetContent();
    if (this_present_content || that_present_content) {
      if (!(this_present_content && that_present_content))
        return false;
      if (!this.content.equals(that.content))
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

    boolean this_present_lastModifyTimestampMs = true && this.isSetLastModifyTimestampMs();
    boolean that_present_lastModifyTimestampMs = true && that.isSetLastModifyTimestampMs();
    if (this_present_lastModifyTimestampMs || that_present_lastModifyTimestampMs) {
      if (!(this_present_lastModifyTimestampMs && that_present_lastModifyTimestampMs))
        return false;
      if (this.lastModifyTimestampMs != that.lastModifyTimestampMs)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(PositionAssignHistory other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetAssignId()).compareTo(other.isSetAssignId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAssignId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.assignId, other.assignId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContent()).compareTo(other.isSetContent());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContent()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.content, other.content);
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
    lastComparison = Boolean.valueOf(isSetLastModifyTimestampMs()).compareTo(other.isSetLastModifyTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastModifyTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastModifyTimestampMs, other.lastModifyTimestampMs);
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
    StringBuilder sb = new StringBuilder("PositionAssignHistory(");
    boolean first = true;

    if (isSetAssignId()) {
      sb.append("assignId:");
      sb.append(this.assignId);
      first = false;
    }
    if (isSetContent()) {
      if (!first) sb.append(", ");
      sb.append("content:");
      if (this.content == null) {
        sb.append("null");
      } else {
        sb.append(this.content);
      }
      first = false;
    }
    if (isSetCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("createTimestampMs:");
      sb.append(this.createTimestampMs);
      first = false;
    }
    if (isSetLastModifyTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("lastModifyTimestampMs:");
      sb.append(this.lastModifyTimestampMs);
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

  private static class PositionAssignHistoryStandardSchemeFactory implements SchemeFactory {
    public PositionAssignHistoryStandardScheme getScheme() {
      return new PositionAssignHistoryStandardScheme();
    }
  }

  private static class PositionAssignHistoryStandardScheme extends StandardScheme<PositionAssignHistory> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PositionAssignHistory struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ASSIGN_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.assignId = iprot.readI64();
              struct.setAssignIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CONTENT
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.content = iprot.readString();
              struct.setContentIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CREATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTimestampMs = iprot.readI64();
              struct.setCreateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LAST_MODIFY_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lastModifyTimestampMs = iprot.readI64();
              struct.setLastModifyTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PositionAssignHistory struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetAssignId()) {
        oprot.writeFieldBegin(ASSIGN_ID_FIELD_DESC);
        oprot.writeI64(struct.assignId);
        oprot.writeFieldEnd();
      }
      if (struct.content != null) {
        if (struct.isSetContent()) {
          oprot.writeFieldBegin(CONTENT_FIELD_DESC);
          oprot.writeString(struct.content);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.createTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLastModifyTimestampMs()) {
        oprot.writeFieldBegin(LAST_MODIFY_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.lastModifyTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PositionAssignHistoryTupleSchemeFactory implements SchemeFactory {
    public PositionAssignHistoryTupleScheme getScheme() {
      return new PositionAssignHistoryTupleScheme();
    }
  }

  private static class PositionAssignHistoryTupleScheme extends TupleScheme<PositionAssignHistory> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PositionAssignHistory struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetAssignId()) {
        optionals.set(0);
      }
      if (struct.isSetContent()) {
        optionals.set(1);
      }
      if (struct.isSetCreateTimestampMs()) {
        optionals.set(2);
      }
      if (struct.isSetLastModifyTimestampMs()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetAssignId()) {
        oprot.writeI64(struct.assignId);
      }
      if (struct.isSetContent()) {
        oprot.writeString(struct.content);
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeI64(struct.createTimestampMs);
      }
      if (struct.isSetLastModifyTimestampMs()) {
        oprot.writeI64(struct.lastModifyTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PositionAssignHistory struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.assignId = iprot.readI64();
        struct.setAssignIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.content = iprot.readString();
        struct.setContentIsSet(true);
      }
      if (incoming.get(2)) {
        struct.createTimestampMs = iprot.readI64();
        struct.setCreateTimestampMsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.lastModifyTimestampMs = iprot.readI64();
        struct.setLastModifyTimestampMsIsSet(true);
      }
    }
  }

}

