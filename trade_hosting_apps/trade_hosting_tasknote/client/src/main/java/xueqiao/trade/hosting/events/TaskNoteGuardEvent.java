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

public class TaskNoteGuardEvent implements org.apache.thrift.TBase<TaskNoteGuardEvent, TaskNoteGuardEvent._Fields>, java.io.Serializable, Cloneable, Comparable<TaskNoteGuardEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TaskNoteGuardEvent");

  private static final org.apache.thrift.protocol.TField GUARD_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("guardType", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField NOTE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("noteType", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField NOTE_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("noteKey", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TaskNoteGuardEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TaskNoteGuardEventTupleSchemeFactory());
  }

  /**
   * 
   * @see TaskNoteGuardEventType
   */
  public TaskNoteGuardEventType guardType; // optional
  /**
   * 
   * @see xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType
   */
  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType noteType; // optional
  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey noteKey; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see TaskNoteGuardEventType
     */
    GUARD_TYPE((short)1, "guardType"),
    /**
     * 
     * @see xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType
     */
    NOTE_TYPE((short)2, "noteType"),
    NOTE_KEY((short)3, "noteKey");

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
        case 1: // GUARD_TYPE
          return GUARD_TYPE;
        case 2: // NOTE_TYPE
          return NOTE_TYPE;
        case 3: // NOTE_KEY
          return NOTE_KEY;
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
  private _Fields optionals[] = {_Fields.GUARD_TYPE,_Fields.NOTE_TYPE,_Fields.NOTE_KEY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GUARD_TYPE, new org.apache.thrift.meta_data.FieldMetaData("guardType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TaskNoteGuardEventType.class)));
    tmpMap.put(_Fields.NOTE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("noteType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType.class)));
    tmpMap.put(_Fields.NOTE_KEY, new org.apache.thrift.meta_data.FieldMetaData("noteKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TaskNoteGuardEvent.class, metaDataMap);
  }

  public TaskNoteGuardEvent() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TaskNoteGuardEvent(TaskNoteGuardEvent other) {
    if (other.isSetGuardType()) {
      this.guardType = other.guardType;
    }
    if (other.isSetNoteType()) {
      this.noteType = other.noteType;
    }
    if (other.isSetNoteKey()) {
      this.noteKey = new xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey(other.noteKey);
    }
  }

  public TaskNoteGuardEvent deepCopy() {
    return new TaskNoteGuardEvent(this);
  }

  @Override
  public void clear() {
    this.guardType = null;
    this.noteType = null;
    this.noteKey = null;
  }

  /**
   * 
   * @see TaskNoteGuardEventType
   */
  public TaskNoteGuardEventType getGuardType() {
    return this.guardType;
  }

  /**
   * 
   * @see TaskNoteGuardEventType
   */
  public TaskNoteGuardEvent setGuardType(TaskNoteGuardEventType guardType) {
    this.guardType = guardType;
    return this;
  }

  public void unsetGuardType() {
    this.guardType = null;
  }

  /** Returns true if field guardType is set (has been assigned a value) and false otherwise */
  public boolean isSetGuardType() {
    return this.guardType != null;
  }

  public void setGuardTypeIsSet(boolean value) {
    if (!value) {
      this.guardType = null;
    }
  }

  /**
   * 
   * @see xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType
   */
  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType getNoteType() {
    return this.noteType;
  }

  /**
   * 
   * @see xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType
   */
  public TaskNoteGuardEvent setNoteType(xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType noteType) {
    this.noteType = noteType;
    return this;
  }

  public void unsetNoteType() {
    this.noteType = null;
  }

  /** Returns true if field noteType is set (has been assigned a value) and false otherwise */
  public boolean isSetNoteType() {
    return this.noteType != null;
  }

  public void setNoteTypeIsSet(boolean value) {
    if (!value) {
      this.noteType = null;
    }
  }

  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey getNoteKey() {
    return this.noteKey;
  }

  public TaskNoteGuardEvent setNoteKey(xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey noteKey) {
    this.noteKey = noteKey;
    return this;
  }

  public void unsetNoteKey() {
    this.noteKey = null;
  }

  /** Returns true if field noteKey is set (has been assigned a value) and false otherwise */
  public boolean isSetNoteKey() {
    return this.noteKey != null;
  }

  public void setNoteKeyIsSet(boolean value) {
    if (!value) {
      this.noteKey = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GUARD_TYPE:
      if (value == null) {
        unsetGuardType();
      } else {
        setGuardType((TaskNoteGuardEventType)value);
      }
      break;

    case NOTE_TYPE:
      if (value == null) {
        unsetNoteType();
      } else {
        setNoteType((xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType)value);
      }
      break;

    case NOTE_KEY:
      if (value == null) {
        unsetNoteKey();
      } else {
        setNoteKey((xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GUARD_TYPE:
      return getGuardType();

    case NOTE_TYPE:
      return getNoteType();

    case NOTE_KEY:
      return getNoteKey();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GUARD_TYPE:
      return isSetGuardType();
    case NOTE_TYPE:
      return isSetNoteType();
    case NOTE_KEY:
      return isSetNoteKey();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TaskNoteGuardEvent)
      return this.equals((TaskNoteGuardEvent)that);
    return false;
  }

  public boolean equals(TaskNoteGuardEvent that) {
    if (that == null)
      return false;

    boolean this_present_guardType = true && this.isSetGuardType();
    boolean that_present_guardType = true && that.isSetGuardType();
    if (this_present_guardType || that_present_guardType) {
      if (!(this_present_guardType && that_present_guardType))
        return false;
      if (!this.guardType.equals(that.guardType))
        return false;
    }

    boolean this_present_noteType = true && this.isSetNoteType();
    boolean that_present_noteType = true && that.isSetNoteType();
    if (this_present_noteType || that_present_noteType) {
      if (!(this_present_noteType && that_present_noteType))
        return false;
      if (!this.noteType.equals(that.noteType))
        return false;
    }

    boolean this_present_noteKey = true && this.isSetNoteKey();
    boolean that_present_noteKey = true && that.isSetNoteKey();
    if (this_present_noteKey || that_present_noteKey) {
      if (!(this_present_noteKey && that_present_noteKey))
        return false;
      if (!this.noteKey.equals(that.noteKey))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TaskNoteGuardEvent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGuardType()).compareTo(other.isSetGuardType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGuardType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.guardType, other.guardType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNoteType()).compareTo(other.isSetNoteType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNoteType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.noteType, other.noteType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNoteKey()).compareTo(other.isSetNoteKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNoteKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.noteKey, other.noteKey);
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
    StringBuilder sb = new StringBuilder("TaskNoteGuardEvent(");
    boolean first = true;

    if (isSetGuardType()) {
      sb.append("guardType:");
      if (this.guardType == null) {
        sb.append("null");
      } else {
        sb.append(this.guardType);
      }
      first = false;
    }
    if (isSetNoteType()) {
      if (!first) sb.append(", ");
      sb.append("noteType:");
      if (this.noteType == null) {
        sb.append("null");
      } else {
        sb.append(this.noteType);
      }
      first = false;
    }
    if (isSetNoteKey()) {
      if (!first) sb.append(", ");
      sb.append("noteKey:");
      if (this.noteKey == null) {
        sb.append("null");
      } else {
        sb.append(this.noteKey);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (noteKey != null) {
      noteKey.validate();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TaskNoteGuardEventStandardSchemeFactory implements SchemeFactory {
    public TaskNoteGuardEventStandardScheme getScheme() {
      return new TaskNoteGuardEventStandardScheme();
    }
  }

  private static class TaskNoteGuardEventStandardScheme extends StandardScheme<TaskNoteGuardEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TaskNoteGuardEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GUARD_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.guardType = TaskNoteGuardEventType.findByValue(iprot.readI32());
              struct.setGuardTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NOTE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.noteType = xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType.findByValue(iprot.readI32());
              struct.setNoteTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // NOTE_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.noteKey = new xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey();
              struct.noteKey.read(iprot);
              struct.setNoteKeyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TaskNoteGuardEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.guardType != null) {
        if (struct.isSetGuardType()) {
          oprot.writeFieldBegin(GUARD_TYPE_FIELD_DESC);
          oprot.writeI32(struct.guardType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.noteType != null) {
        if (struct.isSetNoteType()) {
          oprot.writeFieldBegin(NOTE_TYPE_FIELD_DESC);
          oprot.writeI32(struct.noteType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.noteKey != null) {
        if (struct.isSetNoteKey()) {
          oprot.writeFieldBegin(NOTE_KEY_FIELD_DESC);
          struct.noteKey.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TaskNoteGuardEventTupleSchemeFactory implements SchemeFactory {
    public TaskNoteGuardEventTupleScheme getScheme() {
      return new TaskNoteGuardEventTupleScheme();
    }
  }

  private static class TaskNoteGuardEventTupleScheme extends TupleScheme<TaskNoteGuardEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TaskNoteGuardEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGuardType()) {
        optionals.set(0);
      }
      if (struct.isSetNoteType()) {
        optionals.set(1);
      }
      if (struct.isSetNoteKey()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetGuardType()) {
        oprot.writeI32(struct.guardType.getValue());
      }
      if (struct.isSetNoteType()) {
        oprot.writeI32(struct.noteType.getValue());
      }
      if (struct.isSetNoteKey()) {
        struct.noteKey.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TaskNoteGuardEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.guardType = TaskNoteGuardEventType.findByValue(iprot.readI32());
        struct.setGuardTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.noteType = xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteType.findByValue(iprot.readI32());
        struct.setNoteTypeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.noteKey = new xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNoteKey();
        struct.noteKey.read(iprot);
        struct.setNoteKeyIsSet(true);
      }
    }
  }

}

