/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.company;

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

public class InitHostingMachineReq implements org.apache.thrift.TBase<InitHostingMachineReq, InitHostingMachineReq._Fields>, java.io.Serializable, Cloneable, Comparable<InitHostingMachineReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("InitHostingMachineReq");

  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("companyId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField GROUP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("groupId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField INIT_HOSTING_TASK_FIELD_DESC = new org.apache.thrift.protocol.TField("initHostingTask", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new InitHostingMachineReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new InitHostingMachineReqTupleSchemeFactory());
  }

  public int companyId; // optional
  public int groupId; // optional
  public xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMPANY_ID((short)1, "companyId"),
    GROUP_ID((short)2, "groupId"),
    INIT_HOSTING_TASK((short)3, "initHostingTask");

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
        case 1: // COMPANY_ID
          return COMPANY_ID;
        case 2: // GROUP_ID
          return GROUP_ID;
        case 3: // INIT_HOSTING_TASK
          return INIT_HOSTING_TASK;
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
  private static final int __COMPANYID_ISSET_ID = 0;
  private static final int __GROUPID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.COMPANY_ID,_Fields.GROUP_ID,_Fields.INIT_HOSTING_TASK};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("companyId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GROUP_ID, new org.apache.thrift.meta_data.FieldMetaData("groupId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.INIT_HOSTING_TASK, new org.apache.thrift.meta_data.FieldMetaData("initHostingTask", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.hosting.taskqueue.SyncInitHostingTask.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(InitHostingMachineReq.class, metaDataMap);
  }

  public InitHostingMachineReq() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public InitHostingMachineReq(InitHostingMachineReq other) {
    __isset_bitfield = other.__isset_bitfield;
    this.companyId = other.companyId;
    this.groupId = other.groupId;
    if (other.isSetInitHostingTask()) {
      this.initHostingTask = new xueqiao.hosting.taskqueue.SyncInitHostingTask(other.initHostingTask);
    }
  }

  public InitHostingMachineReq deepCopy() {
    return new InitHostingMachineReq(this);
  }

  @Override
  public void clear() {
    setCompanyIdIsSet(false);
    this.companyId = 0;
    setGroupIdIsSet(false);
    this.groupId = 0;
    this.initHostingTask = null;
  }

  public int getCompanyId() {
    return this.companyId;
  }

  public InitHostingMachineReq setCompanyId(int companyId) {
    this.companyId = companyId;
    setCompanyIdIsSet(true);
    return this;
  }

  public void unsetCompanyId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  /** Returns true if field companyId is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyId() {
    return EncodingUtils.testBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  public void setCompanyIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMPANYID_ISSET_ID, value);
  }

  public int getGroupId() {
    return this.groupId;
  }

  public InitHostingMachineReq setGroupId(int groupId) {
    this.groupId = groupId;
    setGroupIdIsSet(true);
    return this;
  }

  public void unsetGroupId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GROUPID_ISSET_ID);
  }

  /** Returns true if field groupId is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupId() {
    return EncodingUtils.testBit(__isset_bitfield, __GROUPID_ISSET_ID);
  }

  public void setGroupIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GROUPID_ISSET_ID, value);
  }

  public xueqiao.hosting.taskqueue.SyncInitHostingTask getInitHostingTask() {
    return this.initHostingTask;
  }

  public InitHostingMachineReq setInitHostingTask(xueqiao.hosting.taskqueue.SyncInitHostingTask initHostingTask) {
    this.initHostingTask = initHostingTask;
    return this;
  }

  public void unsetInitHostingTask() {
    this.initHostingTask = null;
  }

  /** Returns true if field initHostingTask is set (has been assigned a value) and false otherwise */
  public boolean isSetInitHostingTask() {
    return this.initHostingTask != null;
  }

  public void setInitHostingTaskIsSet(boolean value) {
    if (!value) {
      this.initHostingTask = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COMPANY_ID:
      if (value == null) {
        unsetCompanyId();
      } else {
        setCompanyId((Integer)value);
      }
      break;

    case GROUP_ID:
      if (value == null) {
        unsetGroupId();
      } else {
        setGroupId((Integer)value);
      }
      break;

    case INIT_HOSTING_TASK:
      if (value == null) {
        unsetInitHostingTask();
      } else {
        setInitHostingTask((xueqiao.hosting.taskqueue.SyncInitHostingTask)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COMPANY_ID:
      return Integer.valueOf(getCompanyId());

    case GROUP_ID:
      return Integer.valueOf(getGroupId());

    case INIT_HOSTING_TASK:
      return getInitHostingTask();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COMPANY_ID:
      return isSetCompanyId();
    case GROUP_ID:
      return isSetGroupId();
    case INIT_HOSTING_TASK:
      return isSetInitHostingTask();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof InitHostingMachineReq)
      return this.equals((InitHostingMachineReq)that);
    return false;
  }

  public boolean equals(InitHostingMachineReq that) {
    if (that == null)
      return false;

    boolean this_present_companyId = true && this.isSetCompanyId();
    boolean that_present_companyId = true && that.isSetCompanyId();
    if (this_present_companyId || that_present_companyId) {
      if (!(this_present_companyId && that_present_companyId))
        return false;
      if (this.companyId != that.companyId)
        return false;
    }

    boolean this_present_groupId = true && this.isSetGroupId();
    boolean that_present_groupId = true && that.isSetGroupId();
    if (this_present_groupId || that_present_groupId) {
      if (!(this_present_groupId && that_present_groupId))
        return false;
      if (this.groupId != that.groupId)
        return false;
    }

    boolean this_present_initHostingTask = true && this.isSetInitHostingTask();
    boolean that_present_initHostingTask = true && that.isSetInitHostingTask();
    if (this_present_initHostingTask || that_present_initHostingTask) {
      if (!(this_present_initHostingTask && that_present_initHostingTask))
        return false;
      if (!this.initHostingTask.equals(that.initHostingTask))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(InitHostingMachineReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCompanyId()).compareTo(other.isSetCompanyId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyId, other.companyId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupId()).compareTo(other.isSetGroupId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupId, other.groupId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInitHostingTask()).compareTo(other.isSetInitHostingTask());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInitHostingTask()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.initHostingTask, other.initHostingTask);
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
    StringBuilder sb = new StringBuilder("InitHostingMachineReq(");
    boolean first = true;

    if (isSetCompanyId()) {
      sb.append("companyId:");
      sb.append(this.companyId);
      first = false;
    }
    if (isSetGroupId()) {
      if (!first) sb.append(", ");
      sb.append("groupId:");
      sb.append(this.groupId);
      first = false;
    }
    if (isSetInitHostingTask()) {
      if (!first) sb.append(", ");
      sb.append("initHostingTask:");
      if (this.initHostingTask == null) {
        sb.append("null");
      } else {
        sb.append(this.initHostingTask);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (initHostingTask != null) {
      initHostingTask.validate();
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

  private static class InitHostingMachineReqStandardSchemeFactory implements SchemeFactory {
    public InitHostingMachineReqStandardScheme getScheme() {
      return new InitHostingMachineReqStandardScheme();
    }
  }

  private static class InitHostingMachineReqStandardScheme extends StandardScheme<InitHostingMachineReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, InitHostingMachineReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.companyId = iprot.readI32();
              struct.setCompanyIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GROUP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.groupId = iprot.readI32();
              struct.setGroupIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // INIT_HOSTING_TASK
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.initHostingTask = new xueqiao.hosting.taskqueue.SyncInitHostingTask();
              struct.initHostingTask.read(iprot);
              struct.setInitHostingTaskIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, InitHostingMachineReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetCompanyId()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.companyId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetGroupId()) {
        oprot.writeFieldBegin(GROUP_ID_FIELD_DESC);
        oprot.writeI32(struct.groupId);
        oprot.writeFieldEnd();
      }
      if (struct.initHostingTask != null) {
        if (struct.isSetInitHostingTask()) {
          oprot.writeFieldBegin(INIT_HOSTING_TASK_FIELD_DESC);
          struct.initHostingTask.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class InitHostingMachineReqTupleSchemeFactory implements SchemeFactory {
    public InitHostingMachineReqTupleScheme getScheme() {
      return new InitHostingMachineReqTupleScheme();
    }
  }

  private static class InitHostingMachineReqTupleScheme extends TupleScheme<InitHostingMachineReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, InitHostingMachineReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCompanyId()) {
        optionals.set(0);
      }
      if (struct.isSetGroupId()) {
        optionals.set(1);
      }
      if (struct.isSetInitHostingTask()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetCompanyId()) {
        oprot.writeI32(struct.companyId);
      }
      if (struct.isSetGroupId()) {
        oprot.writeI32(struct.groupId);
      }
      if (struct.isSetInitHostingTask()) {
        struct.initHostingTask.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, InitHostingMachineReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.companyId = iprot.readI32();
        struct.setCompanyIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.groupId = iprot.readI32();
        struct.setGroupIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.initHostingTask = new xueqiao.hosting.taskqueue.SyncInitHostingTask();
        struct.initHostingTask.read(iprot);
        struct.setInitHostingTaskIsSet(true);
      }
    }
  }

}
