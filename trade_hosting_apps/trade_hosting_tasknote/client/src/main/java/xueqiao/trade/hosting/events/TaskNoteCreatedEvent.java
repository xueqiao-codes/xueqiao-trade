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

public class TaskNoteCreatedEvent implements org.apache.thrift.TBase<TaskNoteCreatedEvent, TaskNoteCreatedEvent._Fields>, java.io.Serializable, Cloneable, Comparable<TaskNoteCreatedEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TaskNoteCreatedEvent");

  private static final org.apache.thrift.protocol.TField CREATED_TASK_NOTE_FIELD_DESC = new org.apache.thrift.protocol.TField("createdTaskNote", org.apache.thrift.protocol.TType.STRUCT, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TaskNoteCreatedEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TaskNoteCreatedEventTupleSchemeFactory());
  }

  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote createdTaskNote; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CREATED_TASK_NOTE((short)1, "createdTaskNote");

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
        case 1: // CREATED_TASK_NOTE
          return CREATED_TASK_NOTE;
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
  private _Fields optionals[] = {_Fields.CREATED_TASK_NOTE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CREATED_TASK_NOTE, new org.apache.thrift.meta_data.FieldMetaData("createdTaskNote", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TaskNoteCreatedEvent.class, metaDataMap);
  }

  public TaskNoteCreatedEvent() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TaskNoteCreatedEvent(TaskNoteCreatedEvent other) {
    if (other.isSetCreatedTaskNote()) {
      this.createdTaskNote = new xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote(other.createdTaskNote);
    }
  }

  public TaskNoteCreatedEvent deepCopy() {
    return new TaskNoteCreatedEvent(this);
  }

  @Override
  public void clear() {
    this.createdTaskNote = null;
  }

  public xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote getCreatedTaskNote() {
    return this.createdTaskNote;
  }

  public TaskNoteCreatedEvent setCreatedTaskNote(xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote createdTaskNote) {
    this.createdTaskNote = createdTaskNote;
    return this;
  }

  public void unsetCreatedTaskNote() {
    this.createdTaskNote = null;
  }

  /** Returns true if field createdTaskNote is set (has been assigned a value) and false otherwise */
  public boolean isSetCreatedTaskNote() {
    return this.createdTaskNote != null;
  }

  public void setCreatedTaskNoteIsSet(boolean value) {
    if (!value) {
      this.createdTaskNote = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CREATED_TASK_NOTE:
      if (value == null) {
        unsetCreatedTaskNote();
      } else {
        setCreatedTaskNote((xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CREATED_TASK_NOTE:
      return getCreatedTaskNote();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CREATED_TASK_NOTE:
      return isSetCreatedTaskNote();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TaskNoteCreatedEvent)
      return this.equals((TaskNoteCreatedEvent)that);
    return false;
  }

  public boolean equals(TaskNoteCreatedEvent that) {
    if (that == null)
      return false;

    boolean this_present_createdTaskNote = true && this.isSetCreatedTaskNote();
    boolean that_present_createdTaskNote = true && that.isSetCreatedTaskNote();
    if (this_present_createdTaskNote || that_present_createdTaskNote) {
      if (!(this_present_createdTaskNote && that_present_createdTaskNote))
        return false;
      if (!this.createdTaskNote.equals(that.createdTaskNote))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TaskNoteCreatedEvent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCreatedTaskNote()).compareTo(other.isSetCreatedTaskNote());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCreatedTaskNote()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.createdTaskNote, other.createdTaskNote);
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
    StringBuilder sb = new StringBuilder("TaskNoteCreatedEvent(");
    boolean first = true;

    if (isSetCreatedTaskNote()) {
      sb.append("createdTaskNote:");
      if (this.createdTaskNote == null) {
        sb.append("null");
      } else {
        sb.append(this.createdTaskNote);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (createdTaskNote != null) {
      createdTaskNote.validate();
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

  private static class TaskNoteCreatedEventStandardSchemeFactory implements SchemeFactory {
    public TaskNoteCreatedEventStandardScheme getScheme() {
      return new TaskNoteCreatedEventStandardScheme();
    }
  }

  private static class TaskNoteCreatedEventStandardScheme extends StandardScheme<TaskNoteCreatedEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TaskNoteCreatedEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CREATED_TASK_NOTE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.createdTaskNote = new xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote();
              struct.createdTaskNote.read(iprot);
              struct.setCreatedTaskNoteIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TaskNoteCreatedEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.createdTaskNote != null) {
        if (struct.isSetCreatedTaskNote()) {
          oprot.writeFieldBegin(CREATED_TASK_NOTE_FIELD_DESC);
          struct.createdTaskNote.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TaskNoteCreatedEventTupleSchemeFactory implements SchemeFactory {
    public TaskNoteCreatedEventTupleScheme getScheme() {
      return new TaskNoteCreatedEventTupleScheme();
    }
  }

  private static class TaskNoteCreatedEventTupleScheme extends TupleScheme<TaskNoteCreatedEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TaskNoteCreatedEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCreatedTaskNote()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetCreatedTaskNote()) {
        struct.createdTaskNote.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TaskNoteCreatedEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.createdTaskNote = new xueqiao.trade.hosting.tasknote.thriftapi.HostingTaskNote();
        struct.createdTaskNote.read(iprot);
        struct.setCreatedTaskNoteIsSet(true);
      }
    }
  }

}
