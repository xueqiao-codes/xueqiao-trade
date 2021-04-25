/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.upside.position;

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

public class CTPOpenTDInfo implements org.apache.thrift.TBase<CTPOpenTDInfo, CTPOpenTDInfo._Fields>, java.io.Serializable, Cloneable, Comparable<CTPOpenTDInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CTPOpenTDInfo");

  private static final org.apache.thrift.protocol.TField OPEN_TDVOLUME_FIELD_DESC = new org.apache.thrift.protocol.TField("openTDVolume", org.apache.thrift.protocol.TType.I32, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CTPOpenTDInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CTPOpenTDInfoTupleSchemeFactory());
  }

  public int openTDVolume; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    OPEN_TDVOLUME((short)1, "openTDVolume");

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
        case 1: // OPEN_TDVOLUME
          return OPEN_TDVOLUME;
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
  private static final int __OPENTDVOLUME_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.OPEN_TDVOLUME};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.OPEN_TDVOLUME, new org.apache.thrift.meta_data.FieldMetaData("openTDVolume", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CTPOpenTDInfo.class, metaDataMap);
  }

  public CTPOpenTDInfo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CTPOpenTDInfo(CTPOpenTDInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.openTDVolume = other.openTDVolume;
  }

  public CTPOpenTDInfo deepCopy() {
    return new CTPOpenTDInfo(this);
  }

  @Override
  public void clear() {
    setOpenTDVolumeIsSet(false);
    this.openTDVolume = 0;
  }

  public int getOpenTDVolume() {
    return this.openTDVolume;
  }

  public CTPOpenTDInfo setOpenTDVolume(int openTDVolume) {
    this.openTDVolume = openTDVolume;
    setOpenTDVolumeIsSet(true);
    return this;
  }

  public void unsetOpenTDVolume() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OPENTDVOLUME_ISSET_ID);
  }

  /** Returns true if field openTDVolume is set (has been assigned a value) and false otherwise */
  public boolean isSetOpenTDVolume() {
    return EncodingUtils.testBit(__isset_bitfield, __OPENTDVOLUME_ISSET_ID);
  }

  public void setOpenTDVolumeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OPENTDVOLUME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case OPEN_TDVOLUME:
      if (value == null) {
        unsetOpenTDVolume();
      } else {
        setOpenTDVolume((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case OPEN_TDVOLUME:
      return Integer.valueOf(getOpenTDVolume());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case OPEN_TDVOLUME:
      return isSetOpenTDVolume();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CTPOpenTDInfo)
      return this.equals((CTPOpenTDInfo)that);
    return false;
  }

  public boolean equals(CTPOpenTDInfo that) {
    if (that == null)
      return false;

    boolean this_present_openTDVolume = true && this.isSetOpenTDVolume();
    boolean that_present_openTDVolume = true && that.isSetOpenTDVolume();
    if (this_present_openTDVolume || that_present_openTDVolume) {
      if (!(this_present_openTDVolume && that_present_openTDVolume))
        return false;
      if (this.openTDVolume != that.openTDVolume)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(CTPOpenTDInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOpenTDVolume()).compareTo(other.isSetOpenTDVolume());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOpenTDVolume()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.openTDVolume, other.openTDVolume);
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
    StringBuilder sb = new StringBuilder("CTPOpenTDInfo(");
    boolean first = true;

    if (isSetOpenTDVolume()) {
      sb.append("openTDVolume:");
      sb.append(this.openTDVolume);
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

  private static class CTPOpenTDInfoStandardSchemeFactory implements SchemeFactory {
    public CTPOpenTDInfoStandardScheme getScheme() {
      return new CTPOpenTDInfoStandardScheme();
    }
  }

  private static class CTPOpenTDInfoStandardScheme extends StandardScheme<CTPOpenTDInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CTPOpenTDInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // OPEN_TDVOLUME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.openTDVolume = iprot.readI32();
              struct.setOpenTDVolumeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CTPOpenTDInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetOpenTDVolume()) {
        oprot.writeFieldBegin(OPEN_TDVOLUME_FIELD_DESC);
        oprot.writeI32(struct.openTDVolume);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CTPOpenTDInfoTupleSchemeFactory implements SchemeFactory {
    public CTPOpenTDInfoTupleScheme getScheme() {
      return new CTPOpenTDInfoTupleScheme();
    }
  }

  private static class CTPOpenTDInfoTupleScheme extends TupleScheme<CTPOpenTDInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CTPOpenTDInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOpenTDVolume()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetOpenTDVolume()) {
        oprot.writeI32(struct.openTDVolume);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CTPOpenTDInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.openTDVolume = iprot.readI32();
        struct.setOpenTDVolumeIsSet(true);
      }
    }
  }

}

