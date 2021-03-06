/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.statis;

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

public class StatDataSource implements org.apache.thrift.TBase<StatDataSource, StatDataSource._Fields>, java.io.Serializable, Cloneable, Comparable<StatDataSource> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StatDataSource");

  private static final org.apache.thrift.protocol.TField SOURCE_DATA_CHANNEL_FIELD_DESC = new org.apache.thrift.protocol.TField("sourceDataChannel", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField SOURCE_DATA_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("sourceDataTimestampMs", org.apache.thrift.protocol.TType.I64, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StatDataSourceStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StatDataSourceTupleSchemeFactory());
  }

  /**
   * 
   * @see DataSourceChannel
   */
  public DataSourceChannel sourceDataChannel; // optional
  public long sourceDataTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see DataSourceChannel
     */
    SOURCE_DATA_CHANNEL((short)1, "sourceDataChannel"),
    SOURCE_DATA_TIMESTAMP_MS((short)2, "sourceDataTimestampMs");

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
        case 1: // SOURCE_DATA_CHANNEL
          return SOURCE_DATA_CHANNEL;
        case 2: // SOURCE_DATA_TIMESTAMP_MS
          return SOURCE_DATA_TIMESTAMP_MS;
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
  private static final int __SOURCEDATATIMESTAMPMS_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SOURCE_DATA_CHANNEL,_Fields.SOURCE_DATA_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SOURCE_DATA_CHANNEL, new org.apache.thrift.meta_data.FieldMetaData("sourceDataChannel", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, DataSourceChannel.class)));
    tmpMap.put(_Fields.SOURCE_DATA_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("sourceDataTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatDataSource.class, metaDataMap);
  }

  public StatDataSource() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StatDataSource(StatDataSource other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetSourceDataChannel()) {
      this.sourceDataChannel = other.sourceDataChannel;
    }
    this.sourceDataTimestampMs = other.sourceDataTimestampMs;
  }

  public StatDataSource deepCopy() {
    return new StatDataSource(this);
  }

  @Override
  public void clear() {
    this.sourceDataChannel = null;
    setSourceDataTimestampMsIsSet(false);
    this.sourceDataTimestampMs = 0;
  }

  /**
   * 
   * @see DataSourceChannel
   */
  public DataSourceChannel getSourceDataChannel() {
    return this.sourceDataChannel;
  }

  /**
   * 
   * @see DataSourceChannel
   */
  public StatDataSource setSourceDataChannel(DataSourceChannel sourceDataChannel) {
    this.sourceDataChannel = sourceDataChannel;
    return this;
  }

  public void unsetSourceDataChannel() {
    this.sourceDataChannel = null;
  }

  /** Returns true if field sourceDataChannel is set (has been assigned a value) and false otherwise */
  public boolean isSetSourceDataChannel() {
    return this.sourceDataChannel != null;
  }

  public void setSourceDataChannelIsSet(boolean value) {
    if (!value) {
      this.sourceDataChannel = null;
    }
  }

  public long getSourceDataTimestampMs() {
    return this.sourceDataTimestampMs;
  }

  public StatDataSource setSourceDataTimestampMs(long sourceDataTimestampMs) {
    this.sourceDataTimestampMs = sourceDataTimestampMs;
    setSourceDataTimestampMsIsSet(true);
    return this;
  }

  public void unsetSourceDataTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SOURCEDATATIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field sourceDataTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetSourceDataTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __SOURCEDATATIMESTAMPMS_ISSET_ID);
  }

  public void setSourceDataTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SOURCEDATATIMESTAMPMS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SOURCE_DATA_CHANNEL:
      if (value == null) {
        unsetSourceDataChannel();
      } else {
        setSourceDataChannel((DataSourceChannel)value);
      }
      break;

    case SOURCE_DATA_TIMESTAMP_MS:
      if (value == null) {
        unsetSourceDataTimestampMs();
      } else {
        setSourceDataTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SOURCE_DATA_CHANNEL:
      return getSourceDataChannel();

    case SOURCE_DATA_TIMESTAMP_MS:
      return Long.valueOf(getSourceDataTimestampMs());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SOURCE_DATA_CHANNEL:
      return isSetSourceDataChannel();
    case SOURCE_DATA_TIMESTAMP_MS:
      return isSetSourceDataTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StatDataSource)
      return this.equals((StatDataSource)that);
    return false;
  }

  public boolean equals(StatDataSource that) {
    if (that == null)
      return false;

    boolean this_present_sourceDataChannel = true && this.isSetSourceDataChannel();
    boolean that_present_sourceDataChannel = true && that.isSetSourceDataChannel();
    if (this_present_sourceDataChannel || that_present_sourceDataChannel) {
      if (!(this_present_sourceDataChannel && that_present_sourceDataChannel))
        return false;
      if (!this.sourceDataChannel.equals(that.sourceDataChannel))
        return false;
    }

    boolean this_present_sourceDataTimestampMs = true && this.isSetSourceDataTimestampMs();
    boolean that_present_sourceDataTimestampMs = true && that.isSetSourceDataTimestampMs();
    if (this_present_sourceDataTimestampMs || that_present_sourceDataTimestampMs) {
      if (!(this_present_sourceDataTimestampMs && that_present_sourceDataTimestampMs))
        return false;
      if (this.sourceDataTimestampMs != that.sourceDataTimestampMs)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StatDataSource other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSourceDataChannel()).compareTo(other.isSetSourceDataChannel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSourceDataChannel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sourceDataChannel, other.sourceDataChannel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSourceDataTimestampMs()).compareTo(other.isSetSourceDataTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSourceDataTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sourceDataTimestampMs, other.sourceDataTimestampMs);
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
    StringBuilder sb = new StringBuilder("StatDataSource(");
    boolean first = true;

    if (isSetSourceDataChannel()) {
      sb.append("sourceDataChannel:");
      if (this.sourceDataChannel == null) {
        sb.append("null");
      } else {
        sb.append(this.sourceDataChannel);
      }
      first = false;
    }
    if (isSetSourceDataTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("sourceDataTimestampMs:");
      sb.append(this.sourceDataTimestampMs);
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

  private static class StatDataSourceStandardSchemeFactory implements SchemeFactory {
    public StatDataSourceStandardScheme getScheme() {
      return new StatDataSourceStandardScheme();
    }
  }

  private static class StatDataSourceStandardScheme extends StandardScheme<StatDataSource> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StatDataSource struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SOURCE_DATA_CHANNEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.sourceDataChannel = DataSourceChannel.findByValue(iprot.readI32());
              struct.setSourceDataChannelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SOURCE_DATA_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sourceDataTimestampMs = iprot.readI64();
              struct.setSourceDataTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StatDataSource struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sourceDataChannel != null) {
        if (struct.isSetSourceDataChannel()) {
          oprot.writeFieldBegin(SOURCE_DATA_CHANNEL_FIELD_DESC);
          oprot.writeI32(struct.sourceDataChannel.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetSourceDataTimestampMs()) {
        oprot.writeFieldBegin(SOURCE_DATA_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.sourceDataTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class StatDataSourceTupleSchemeFactory implements SchemeFactory {
    public StatDataSourceTupleScheme getScheme() {
      return new StatDataSourceTupleScheme();
    }
  }

  private static class StatDataSourceTupleScheme extends TupleScheme<StatDataSource> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StatDataSource struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSourceDataChannel()) {
        optionals.set(0);
      }
      if (struct.isSetSourceDataTimestampMs()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSourceDataChannel()) {
        oprot.writeI32(struct.sourceDataChannel.getValue());
      }
      if (struct.isSetSourceDataTimestampMs()) {
        oprot.writeI64(struct.sourceDataTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StatDataSource struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.sourceDataChannel = DataSourceChannel.findByValue(iprot.readI32());
        struct.setSourceDataChannelIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sourceDataTimestampMs = iprot.readI64();
        struct.setSourceDataTimestampMsIsSet(true);
      }
    }
  }

}

