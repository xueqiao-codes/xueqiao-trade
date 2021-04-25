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
 * 持仓分配时，需要添加同步调用任务，用于持仓分配影响到其他服务时，确保成功调用
 */
public class PositionAssignTask implements org.apache.thrift.TBase<PositionAssignTask, PositionAssignTask._Fields>, java.io.Serializable, Cloneable, Comparable<PositionAssignTask> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PositionAssignTask");

  private static final org.apache.thrift.protocol.TField TASK_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("taskId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField POSITION_ASSIGNED_FIELD_DESC = new org.apache.thrift.protocol.TField("positionAssigned", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField AO_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("aoType", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField CREATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("createTimestampMs", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField LASTMODIFY_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("lastmodifyTimestampMs", org.apache.thrift.protocol.TType.I64, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PositionAssignTaskStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PositionAssignTaskTupleSchemeFactory());
  }

  public long taskId; // optional
  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned; // optional
  /**
   * 
   * @see AoType
   */
  public AoType aoType; // optional
  public long createTimestampMs; // optional
  public long lastmodifyTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TASK_ID((short)1, "taskId"),
    POSITION_ASSIGNED((short)2, "positionAssigned"),
    /**
     * 
     * @see AoType
     */
    AO_TYPE((short)3, "aoType"),
    CREATE_TIMESTAMP_MS((short)5, "createTimestampMs"),
    LASTMODIFY_TIMESTAMP_MS((short)6, "lastmodifyTimestampMs");

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
        case 1: // TASK_ID
          return TASK_ID;
        case 2: // POSITION_ASSIGNED
          return POSITION_ASSIGNED;
        case 3: // AO_TYPE
          return AO_TYPE;
        case 5: // CREATE_TIMESTAMP_MS
          return CREATE_TIMESTAMP_MS;
        case 6: // LASTMODIFY_TIMESTAMP_MS
          return LASTMODIFY_TIMESTAMP_MS;
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
  private static final int __TASKID_ISSET_ID = 0;
  private static final int __CREATETIMESTAMPMS_ISSET_ID = 1;
  private static final int __LASTMODIFYTIMESTAMPMS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TASK_ID,_Fields.POSITION_ASSIGNED,_Fields.AO_TYPE,_Fields.CREATE_TIMESTAMP_MS,_Fields.LASTMODIFY_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TASK_ID, new org.apache.thrift.meta_data.FieldMetaData("taskId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.POSITION_ASSIGNED, new org.apache.thrift.meta_data.FieldMetaData("positionAssigned", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned.class)));
    tmpMap.put(_Fields.AO_TYPE, new org.apache.thrift.meta_data.FieldMetaData("aoType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, AoType.class)));
    tmpMap.put(_Fields.CREATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("createTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LASTMODIFY_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("lastmodifyTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PositionAssignTask.class, metaDataMap);
  }

  public PositionAssignTask() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PositionAssignTask(PositionAssignTask other) {
    __isset_bitfield = other.__isset_bitfield;
    this.taskId = other.taskId;
    if (other.isSetPositionAssigned()) {
      this.positionAssigned = new xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned(other.positionAssigned);
    }
    if (other.isSetAoType()) {
      this.aoType = other.aoType;
    }
    this.createTimestampMs = other.createTimestampMs;
    this.lastmodifyTimestampMs = other.lastmodifyTimestampMs;
  }

  public PositionAssignTask deepCopy() {
    return new PositionAssignTask(this);
  }

  @Override
  public void clear() {
    setTaskIdIsSet(false);
    this.taskId = 0;
    this.positionAssigned = null;
    this.aoType = null;
    setCreateTimestampMsIsSet(false);
    this.createTimestampMs = 0;
    setLastmodifyTimestampMsIsSet(false);
    this.lastmodifyTimestampMs = 0;
  }

  public long getTaskId() {
    return this.taskId;
  }

  public PositionAssignTask setTaskId(long taskId) {
    this.taskId = taskId;
    setTaskIdIsSet(true);
    return this;
  }

  public void unsetTaskId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TASKID_ISSET_ID);
  }

  /** Returns true if field taskId is set (has been assigned a value) and false otherwise */
  public boolean isSetTaskId() {
    return EncodingUtils.testBit(__isset_bitfield, __TASKID_ISSET_ID);
  }

  public void setTaskIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TASKID_ISSET_ID, value);
  }

  public xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned getPositionAssigned() {
    return this.positionAssigned;
  }

  public PositionAssignTask setPositionAssigned(xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned positionAssigned) {
    this.positionAssigned = positionAssigned;
    return this;
  }

  public void unsetPositionAssigned() {
    this.positionAssigned = null;
  }

  /** Returns true if field positionAssigned is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionAssigned() {
    return this.positionAssigned != null;
  }

  public void setPositionAssignedIsSet(boolean value) {
    if (!value) {
      this.positionAssigned = null;
    }
  }

  /**
   * 
   * @see AoType
   */
  public AoType getAoType() {
    return this.aoType;
  }

  /**
   * 
   * @see AoType
   */
  public PositionAssignTask setAoType(AoType aoType) {
    this.aoType = aoType;
    return this;
  }

  public void unsetAoType() {
    this.aoType = null;
  }

  /** Returns true if field aoType is set (has been assigned a value) and false otherwise */
  public boolean isSetAoType() {
    return this.aoType != null;
  }

  public void setAoTypeIsSet(boolean value) {
    if (!value) {
      this.aoType = null;
    }
  }

  public long getCreateTimestampMs() {
    return this.createTimestampMs;
  }

  public PositionAssignTask setCreateTimestampMs(long createTimestampMs) {
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

  public long getLastmodifyTimestampMs() {
    return this.lastmodifyTimestampMs;
  }

  public PositionAssignTask setLastmodifyTimestampMs(long lastmodifyTimestampMs) {
    this.lastmodifyTimestampMs = lastmodifyTimestampMs;
    setLastmodifyTimestampMsIsSet(true);
    return this;
  }

  public void unsetLastmodifyTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field lastmodifyTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetLastmodifyTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID);
  }

  public void setLastmodifyTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTMODIFYTIMESTAMPMS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TASK_ID:
      if (value == null) {
        unsetTaskId();
      } else {
        setTaskId((Long)value);
      }
      break;

    case POSITION_ASSIGNED:
      if (value == null) {
        unsetPositionAssigned();
      } else {
        setPositionAssigned((xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned)value);
      }
      break;

    case AO_TYPE:
      if (value == null) {
        unsetAoType();
      } else {
        setAoType((AoType)value);
      }
      break;

    case CREATE_TIMESTAMP_MS:
      if (value == null) {
        unsetCreateTimestampMs();
      } else {
        setCreateTimestampMs((Long)value);
      }
      break;

    case LASTMODIFY_TIMESTAMP_MS:
      if (value == null) {
        unsetLastmodifyTimestampMs();
      } else {
        setLastmodifyTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TASK_ID:
      return Long.valueOf(getTaskId());

    case POSITION_ASSIGNED:
      return getPositionAssigned();

    case AO_TYPE:
      return getAoType();

    case CREATE_TIMESTAMP_MS:
      return Long.valueOf(getCreateTimestampMs());

    case LASTMODIFY_TIMESTAMP_MS:
      return Long.valueOf(getLastmodifyTimestampMs());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TASK_ID:
      return isSetTaskId();
    case POSITION_ASSIGNED:
      return isSetPositionAssigned();
    case AO_TYPE:
      return isSetAoType();
    case CREATE_TIMESTAMP_MS:
      return isSetCreateTimestampMs();
    case LASTMODIFY_TIMESTAMP_MS:
      return isSetLastmodifyTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PositionAssignTask)
      return this.equals((PositionAssignTask)that);
    return false;
  }

  public boolean equals(PositionAssignTask that) {
    if (that == null)
      return false;

    boolean this_present_taskId = true && this.isSetTaskId();
    boolean that_present_taskId = true && that.isSetTaskId();
    if (this_present_taskId || that_present_taskId) {
      if (!(this_present_taskId && that_present_taskId))
        return false;
      if (this.taskId != that.taskId)
        return false;
    }

    boolean this_present_positionAssigned = true && this.isSetPositionAssigned();
    boolean that_present_positionAssigned = true && that.isSetPositionAssigned();
    if (this_present_positionAssigned || that_present_positionAssigned) {
      if (!(this_present_positionAssigned && that_present_positionAssigned))
        return false;
      if (!this.positionAssigned.equals(that.positionAssigned))
        return false;
    }

    boolean this_present_aoType = true && this.isSetAoType();
    boolean that_present_aoType = true && that.isSetAoType();
    if (this_present_aoType || that_present_aoType) {
      if (!(this_present_aoType && that_present_aoType))
        return false;
      if (!this.aoType.equals(that.aoType))
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

    boolean this_present_lastmodifyTimestampMs = true && this.isSetLastmodifyTimestampMs();
    boolean that_present_lastmodifyTimestampMs = true && that.isSetLastmodifyTimestampMs();
    if (this_present_lastmodifyTimestampMs || that_present_lastmodifyTimestampMs) {
      if (!(this_present_lastmodifyTimestampMs && that_present_lastmodifyTimestampMs))
        return false;
      if (this.lastmodifyTimestampMs != that.lastmodifyTimestampMs)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(PositionAssignTask other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTaskId()).compareTo(other.isSetTaskId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTaskId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.taskId, other.taskId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPositionAssigned()).compareTo(other.isSetPositionAssigned());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionAssigned()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionAssigned, other.positionAssigned);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAoType()).compareTo(other.isSetAoType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAoType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.aoType, other.aoType);
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
    lastComparison = Boolean.valueOf(isSetLastmodifyTimestampMs()).compareTo(other.isSetLastmodifyTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLastmodifyTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastmodifyTimestampMs, other.lastmodifyTimestampMs);
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
    StringBuilder sb = new StringBuilder("PositionAssignTask(");
    boolean first = true;

    if (isSetTaskId()) {
      sb.append("taskId:");
      sb.append(this.taskId);
      first = false;
    }
    if (isSetPositionAssigned()) {
      if (!first) sb.append(", ");
      sb.append("positionAssigned:");
      if (this.positionAssigned == null) {
        sb.append("null");
      } else {
        sb.append(this.positionAssigned);
      }
      first = false;
    }
    if (isSetAoType()) {
      if (!first) sb.append(", ");
      sb.append("aoType:");
      if (this.aoType == null) {
        sb.append("null");
      } else {
        sb.append(this.aoType);
      }
      first = false;
    }
    if (isSetCreateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("createTimestampMs:");
      sb.append(this.createTimestampMs);
      first = false;
    }
    if (isSetLastmodifyTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("lastmodifyTimestampMs:");
      sb.append(this.lastmodifyTimestampMs);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (positionAssigned != null) {
      positionAssigned.validate();
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

  private static class PositionAssignTaskStandardSchemeFactory implements SchemeFactory {
    public PositionAssignTaskStandardScheme getScheme() {
      return new PositionAssignTaskStandardScheme();
    }
  }

  private static class PositionAssignTaskStandardScheme extends StandardScheme<PositionAssignTask> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PositionAssignTask struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TASK_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.taskId = iprot.readI64();
              struct.setTaskIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // POSITION_ASSIGNED
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.positionAssigned = new xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned();
              struct.positionAssigned.read(iprot);
              struct.setPositionAssignedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // AO_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.aoType = AoType.findByValue(iprot.readI32());
              struct.setAoTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CREATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.createTimestampMs = iprot.readI64();
              struct.setCreateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // LASTMODIFY_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.lastmodifyTimestampMs = iprot.readI64();
              struct.setLastmodifyTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PositionAssignTask struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTaskId()) {
        oprot.writeFieldBegin(TASK_ID_FIELD_DESC);
        oprot.writeI64(struct.taskId);
        oprot.writeFieldEnd();
      }
      if (struct.positionAssigned != null) {
        if (struct.isSetPositionAssigned()) {
          oprot.writeFieldBegin(POSITION_ASSIGNED_FIELD_DESC);
          struct.positionAssigned.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.aoType != null) {
        if (struct.isSetAoType()) {
          oprot.writeFieldBegin(AO_TYPE_FIELD_DESC);
          oprot.writeI32(struct.aoType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeFieldBegin(CREATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.createTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        oprot.writeFieldBegin(LASTMODIFY_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.lastmodifyTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PositionAssignTaskTupleSchemeFactory implements SchemeFactory {
    public PositionAssignTaskTupleScheme getScheme() {
      return new PositionAssignTaskTupleScheme();
    }
  }

  private static class PositionAssignTaskTupleScheme extends TupleScheme<PositionAssignTask> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PositionAssignTask struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTaskId()) {
        optionals.set(0);
      }
      if (struct.isSetPositionAssigned()) {
        optionals.set(1);
      }
      if (struct.isSetAoType()) {
        optionals.set(2);
      }
      if (struct.isSetCreateTimestampMs()) {
        optionals.set(3);
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetTaskId()) {
        oprot.writeI64(struct.taskId);
      }
      if (struct.isSetPositionAssigned()) {
        struct.positionAssigned.write(oprot);
      }
      if (struct.isSetAoType()) {
        oprot.writeI32(struct.aoType.getValue());
      }
      if (struct.isSetCreateTimestampMs()) {
        oprot.writeI64(struct.createTimestampMs);
      }
      if (struct.isSetLastmodifyTimestampMs()) {
        oprot.writeI64(struct.lastmodifyTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PositionAssignTask struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.taskId = iprot.readI64();
        struct.setTaskIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.positionAssigned = new xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned();
        struct.positionAssigned.read(iprot);
        struct.setPositionAssignedIsSet(true);
      }
      if (incoming.get(2)) {
        struct.aoType = AoType.findByValue(iprot.readI32());
        struct.setAoTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.createTimestampMs = iprot.readI64();
        struct.setCreateTimestampMsIsSet(true);
      }
      if (incoming.get(4)) {
        struct.lastmodifyTimestampMs = iprot.readI64();
        struct.setLastmodifyTimestampMsIsSet(true);
      }
    }
  }

}
