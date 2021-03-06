/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.soldier.platform.app.manager.thriftapi;

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

public class VersionNum implements org.apache.thrift.TBase<VersionNum, VersionNum._Fields>, java.io.Serializable, Cloneable, Comparable<VersionNum> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VersionNum");

  private static final org.apache.thrift.protocol.TField MAJOR_VERSION_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("majorVersionNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField MINOR_VERSION_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("minorVersionNum", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField BUILD_VERSION_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("buildVersionNum", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField REVERSION_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("reversionNum", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new VersionNumStandardSchemeFactory());
    schemes.put(TupleScheme.class, new VersionNumTupleSchemeFactory());
  }

  public int majorVersionNum; // optional
  public int minorVersionNum; // optional
  public int buildVersionNum; // optional
  public int reversionNum; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MAJOR_VERSION_NUM((short)1, "majorVersionNum"),
    MINOR_VERSION_NUM((short)2, "minorVersionNum"),
    BUILD_VERSION_NUM((short)3, "buildVersionNum"),
    REVERSION_NUM((short)4, "reversionNum");

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
        case 1: // MAJOR_VERSION_NUM
          return MAJOR_VERSION_NUM;
        case 2: // MINOR_VERSION_NUM
          return MINOR_VERSION_NUM;
        case 3: // BUILD_VERSION_NUM
          return BUILD_VERSION_NUM;
        case 4: // REVERSION_NUM
          return REVERSION_NUM;
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
  private static final int __MAJORVERSIONNUM_ISSET_ID = 0;
  private static final int __MINORVERSIONNUM_ISSET_ID = 1;
  private static final int __BUILDVERSIONNUM_ISSET_ID = 2;
  private static final int __REVERSIONNUM_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.MAJOR_VERSION_NUM,_Fields.MINOR_VERSION_NUM,_Fields.BUILD_VERSION_NUM,_Fields.REVERSION_NUM};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MAJOR_VERSION_NUM, new org.apache.thrift.meta_data.FieldMetaData("majorVersionNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MINOR_VERSION_NUM, new org.apache.thrift.meta_data.FieldMetaData("minorVersionNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.BUILD_VERSION_NUM, new org.apache.thrift.meta_data.FieldMetaData("buildVersionNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.REVERSION_NUM, new org.apache.thrift.meta_data.FieldMetaData("reversionNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VersionNum.class, metaDataMap);
  }

  public VersionNum() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VersionNum(VersionNum other) {
    __isset_bitfield = other.__isset_bitfield;
    this.majorVersionNum = other.majorVersionNum;
    this.minorVersionNum = other.minorVersionNum;
    this.buildVersionNum = other.buildVersionNum;
    this.reversionNum = other.reversionNum;
  }

  public VersionNum deepCopy() {
    return new VersionNum(this);
  }

  @Override
  public void clear() {
    setMajorVersionNumIsSet(false);
    this.majorVersionNum = 0;
    setMinorVersionNumIsSet(false);
    this.minorVersionNum = 0;
    setBuildVersionNumIsSet(false);
    this.buildVersionNum = 0;
    setReversionNumIsSet(false);
    this.reversionNum = 0;
  }

  public int getMajorVersionNum() {
    return this.majorVersionNum;
  }

  public VersionNum setMajorVersionNum(int majorVersionNum) {
    this.majorVersionNum = majorVersionNum;
    setMajorVersionNumIsSet(true);
    return this;
  }

  public void unsetMajorVersionNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MAJORVERSIONNUM_ISSET_ID);
  }

  /** Returns true if field majorVersionNum is set (has been assigned a value) and false otherwise */
  public boolean isSetMajorVersionNum() {
    return EncodingUtils.testBit(__isset_bitfield, __MAJORVERSIONNUM_ISSET_ID);
  }

  public void setMajorVersionNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MAJORVERSIONNUM_ISSET_ID, value);
  }

  public int getMinorVersionNum() {
    return this.minorVersionNum;
  }

  public VersionNum setMinorVersionNum(int minorVersionNum) {
    this.minorVersionNum = minorVersionNum;
    setMinorVersionNumIsSet(true);
    return this;
  }

  public void unsetMinorVersionNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MINORVERSIONNUM_ISSET_ID);
  }

  /** Returns true if field minorVersionNum is set (has been assigned a value) and false otherwise */
  public boolean isSetMinorVersionNum() {
    return EncodingUtils.testBit(__isset_bitfield, __MINORVERSIONNUM_ISSET_ID);
  }

  public void setMinorVersionNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MINORVERSIONNUM_ISSET_ID, value);
  }

  public int getBuildVersionNum() {
    return this.buildVersionNum;
  }

  public VersionNum setBuildVersionNum(int buildVersionNum) {
    this.buildVersionNum = buildVersionNum;
    setBuildVersionNumIsSet(true);
    return this;
  }

  public void unsetBuildVersionNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BUILDVERSIONNUM_ISSET_ID);
  }

  /** Returns true if field buildVersionNum is set (has been assigned a value) and false otherwise */
  public boolean isSetBuildVersionNum() {
    return EncodingUtils.testBit(__isset_bitfield, __BUILDVERSIONNUM_ISSET_ID);
  }

  public void setBuildVersionNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BUILDVERSIONNUM_ISSET_ID, value);
  }

  public int getReversionNum() {
    return this.reversionNum;
  }

  public VersionNum setReversionNum(int reversionNum) {
    this.reversionNum = reversionNum;
    setReversionNumIsSet(true);
    return this;
  }

  public void unsetReversionNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __REVERSIONNUM_ISSET_ID);
  }

  /** Returns true if field reversionNum is set (has been assigned a value) and false otherwise */
  public boolean isSetReversionNum() {
    return EncodingUtils.testBit(__isset_bitfield, __REVERSIONNUM_ISSET_ID);
  }

  public void setReversionNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __REVERSIONNUM_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MAJOR_VERSION_NUM:
      if (value == null) {
        unsetMajorVersionNum();
      } else {
        setMajorVersionNum((Integer)value);
      }
      break;

    case MINOR_VERSION_NUM:
      if (value == null) {
        unsetMinorVersionNum();
      } else {
        setMinorVersionNum((Integer)value);
      }
      break;

    case BUILD_VERSION_NUM:
      if (value == null) {
        unsetBuildVersionNum();
      } else {
        setBuildVersionNum((Integer)value);
      }
      break;

    case REVERSION_NUM:
      if (value == null) {
        unsetReversionNum();
      } else {
        setReversionNum((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MAJOR_VERSION_NUM:
      return Integer.valueOf(getMajorVersionNum());

    case MINOR_VERSION_NUM:
      return Integer.valueOf(getMinorVersionNum());

    case BUILD_VERSION_NUM:
      return Integer.valueOf(getBuildVersionNum());

    case REVERSION_NUM:
      return Integer.valueOf(getReversionNum());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MAJOR_VERSION_NUM:
      return isSetMajorVersionNum();
    case MINOR_VERSION_NUM:
      return isSetMinorVersionNum();
    case BUILD_VERSION_NUM:
      return isSetBuildVersionNum();
    case REVERSION_NUM:
      return isSetReversionNum();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof VersionNum)
      return this.equals((VersionNum)that);
    return false;
  }

  public boolean equals(VersionNum that) {
    if (that == null)
      return false;

    boolean this_present_majorVersionNum = true && this.isSetMajorVersionNum();
    boolean that_present_majorVersionNum = true && that.isSetMajorVersionNum();
    if (this_present_majorVersionNum || that_present_majorVersionNum) {
      if (!(this_present_majorVersionNum && that_present_majorVersionNum))
        return false;
      if (this.majorVersionNum != that.majorVersionNum)
        return false;
    }

    boolean this_present_minorVersionNum = true && this.isSetMinorVersionNum();
    boolean that_present_minorVersionNum = true && that.isSetMinorVersionNum();
    if (this_present_minorVersionNum || that_present_minorVersionNum) {
      if (!(this_present_minorVersionNum && that_present_minorVersionNum))
        return false;
      if (this.minorVersionNum != that.minorVersionNum)
        return false;
    }

    boolean this_present_buildVersionNum = true && this.isSetBuildVersionNum();
    boolean that_present_buildVersionNum = true && that.isSetBuildVersionNum();
    if (this_present_buildVersionNum || that_present_buildVersionNum) {
      if (!(this_present_buildVersionNum && that_present_buildVersionNum))
        return false;
      if (this.buildVersionNum != that.buildVersionNum)
        return false;
    }

    boolean this_present_reversionNum = true && this.isSetReversionNum();
    boolean that_present_reversionNum = true && that.isSetReversionNum();
    if (this_present_reversionNum || that_present_reversionNum) {
      if (!(this_present_reversionNum && that_present_reversionNum))
        return false;
      if (this.reversionNum != that.reversionNum)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(VersionNum other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetMajorVersionNum()).compareTo(other.isSetMajorVersionNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMajorVersionNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.majorVersionNum, other.majorVersionNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMinorVersionNum()).compareTo(other.isSetMinorVersionNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMinorVersionNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.minorVersionNum, other.minorVersionNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBuildVersionNum()).compareTo(other.isSetBuildVersionNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBuildVersionNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.buildVersionNum, other.buildVersionNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReversionNum()).compareTo(other.isSetReversionNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReversionNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.reversionNum, other.reversionNum);
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
    StringBuilder sb = new StringBuilder("VersionNum(");
    boolean first = true;

    if (isSetMajorVersionNum()) {
      sb.append("majorVersionNum:");
      sb.append(this.majorVersionNum);
      first = false;
    }
    if (isSetMinorVersionNum()) {
      if (!first) sb.append(", ");
      sb.append("minorVersionNum:");
      sb.append(this.minorVersionNum);
      first = false;
    }
    if (isSetBuildVersionNum()) {
      if (!first) sb.append(", ");
      sb.append("buildVersionNum:");
      sb.append(this.buildVersionNum);
      first = false;
    }
    if (isSetReversionNum()) {
      if (!first) sb.append(", ");
      sb.append("reversionNum:");
      sb.append(this.reversionNum);
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

  private static class VersionNumStandardSchemeFactory implements SchemeFactory {
    public VersionNumStandardScheme getScheme() {
      return new VersionNumStandardScheme();
    }
  }

  private static class VersionNumStandardScheme extends StandardScheme<VersionNum> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VersionNum struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MAJOR_VERSION_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.majorVersionNum = iprot.readI32();
              struct.setMajorVersionNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MINOR_VERSION_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.minorVersionNum = iprot.readI32();
              struct.setMinorVersionNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // BUILD_VERSION_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.buildVersionNum = iprot.readI32();
              struct.setBuildVersionNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REVERSION_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.reversionNum = iprot.readI32();
              struct.setReversionNumIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, VersionNum struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetMajorVersionNum()) {
        oprot.writeFieldBegin(MAJOR_VERSION_NUM_FIELD_DESC);
        oprot.writeI32(struct.majorVersionNum);
        oprot.writeFieldEnd();
      }
      if (struct.isSetMinorVersionNum()) {
        oprot.writeFieldBegin(MINOR_VERSION_NUM_FIELD_DESC);
        oprot.writeI32(struct.minorVersionNum);
        oprot.writeFieldEnd();
      }
      if (struct.isSetBuildVersionNum()) {
        oprot.writeFieldBegin(BUILD_VERSION_NUM_FIELD_DESC);
        oprot.writeI32(struct.buildVersionNum);
        oprot.writeFieldEnd();
      }
      if (struct.isSetReversionNum()) {
        oprot.writeFieldBegin(REVERSION_NUM_FIELD_DESC);
        oprot.writeI32(struct.reversionNum);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VersionNumTupleSchemeFactory implements SchemeFactory {
    public VersionNumTupleScheme getScheme() {
      return new VersionNumTupleScheme();
    }
  }

  private static class VersionNumTupleScheme extends TupleScheme<VersionNum> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VersionNum struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMajorVersionNum()) {
        optionals.set(0);
      }
      if (struct.isSetMinorVersionNum()) {
        optionals.set(1);
      }
      if (struct.isSetBuildVersionNum()) {
        optionals.set(2);
      }
      if (struct.isSetReversionNum()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetMajorVersionNum()) {
        oprot.writeI32(struct.majorVersionNum);
      }
      if (struct.isSetMinorVersionNum()) {
        oprot.writeI32(struct.minorVersionNum);
      }
      if (struct.isSetBuildVersionNum()) {
        oprot.writeI32(struct.buildVersionNum);
      }
      if (struct.isSetReversionNum()) {
        oprot.writeI32(struct.reversionNum);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VersionNum struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.majorVersionNum = iprot.readI32();
        struct.setMajorVersionNumIsSet(true);
      }
      if (incoming.get(1)) {
        struct.minorVersionNum = iprot.readI32();
        struct.setMinorVersionNumIsSet(true);
      }
      if (incoming.get(2)) {
        struct.buildVersionNum = iprot.readI32();
        struct.setBuildVersionNumIsSet(true);
      }
      if (incoming.get(3)) {
        struct.reversionNum = iprot.readI32();
        struct.setReversionNumIsSet(true);
      }
    }
  }

}

