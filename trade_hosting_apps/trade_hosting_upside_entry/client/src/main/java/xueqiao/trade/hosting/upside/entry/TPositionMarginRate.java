/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.upside.entry;

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

public class TPositionMarginRate implements org.apache.thrift.TBase<TPositionMarginRate, TPositionMarginRate._Fields>, java.io.Serializable, Cloneable, Comparable<TPositionMarginRate> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TPositionMarginRate");

  private static final org.apache.thrift.protocol.TField CTP_MARGIN_RATE_FIELD_DESC = new org.apache.thrift.protocol.TField("ctpMarginRate", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField ES9_MARGIN_RATE_FIELD_DESC = new org.apache.thrift.protocol.TField("es9MarginRate", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TPositionMarginRateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TPositionMarginRateTupleSchemeFactory());
  }

  public TPositionCTPMarginRate ctpMarginRate; // optional
  public TPositionES9MarginRate es9MarginRate; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CTP_MARGIN_RATE((short)1, "ctpMarginRate"),
    ES9_MARGIN_RATE((short)2, "es9MarginRate");

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
        case 1: // CTP_MARGIN_RATE
          return CTP_MARGIN_RATE;
        case 2: // ES9_MARGIN_RATE
          return ES9_MARGIN_RATE;
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
  private _Fields optionals[] = {_Fields.CTP_MARGIN_RATE,_Fields.ES9_MARGIN_RATE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CTP_MARGIN_RATE, new org.apache.thrift.meta_data.FieldMetaData("ctpMarginRate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TPositionCTPMarginRate.class)));
    tmpMap.put(_Fields.ES9_MARGIN_RATE, new org.apache.thrift.meta_data.FieldMetaData("es9MarginRate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TPositionES9MarginRate.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TPositionMarginRate.class, metaDataMap);
  }

  public TPositionMarginRate() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TPositionMarginRate(TPositionMarginRate other) {
    if (other.isSetCtpMarginRate()) {
      this.ctpMarginRate = new TPositionCTPMarginRate(other.ctpMarginRate);
    }
    if (other.isSetEs9MarginRate()) {
      this.es9MarginRate = new TPositionES9MarginRate(other.es9MarginRate);
    }
  }

  public TPositionMarginRate deepCopy() {
    return new TPositionMarginRate(this);
  }

  @Override
  public void clear() {
    this.ctpMarginRate = null;
    this.es9MarginRate = null;
  }

  public TPositionCTPMarginRate getCtpMarginRate() {
    return this.ctpMarginRate;
  }

  public TPositionMarginRate setCtpMarginRate(TPositionCTPMarginRate ctpMarginRate) {
    this.ctpMarginRate = ctpMarginRate;
    return this;
  }

  public void unsetCtpMarginRate() {
    this.ctpMarginRate = null;
  }

  /** Returns true if field ctpMarginRate is set (has been assigned a value) and false otherwise */
  public boolean isSetCtpMarginRate() {
    return this.ctpMarginRate != null;
  }

  public void setCtpMarginRateIsSet(boolean value) {
    if (!value) {
      this.ctpMarginRate = null;
    }
  }

  public TPositionES9MarginRate getEs9MarginRate() {
    return this.es9MarginRate;
  }

  public TPositionMarginRate setEs9MarginRate(TPositionES9MarginRate es9MarginRate) {
    this.es9MarginRate = es9MarginRate;
    return this;
  }

  public void unsetEs9MarginRate() {
    this.es9MarginRate = null;
  }

  /** Returns true if field es9MarginRate is set (has been assigned a value) and false otherwise */
  public boolean isSetEs9MarginRate() {
    return this.es9MarginRate != null;
  }

  public void setEs9MarginRateIsSet(boolean value) {
    if (!value) {
      this.es9MarginRate = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CTP_MARGIN_RATE:
      if (value == null) {
        unsetCtpMarginRate();
      } else {
        setCtpMarginRate((TPositionCTPMarginRate)value);
      }
      break;

    case ES9_MARGIN_RATE:
      if (value == null) {
        unsetEs9MarginRate();
      } else {
        setEs9MarginRate((TPositionES9MarginRate)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CTP_MARGIN_RATE:
      return getCtpMarginRate();

    case ES9_MARGIN_RATE:
      return getEs9MarginRate();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CTP_MARGIN_RATE:
      return isSetCtpMarginRate();
    case ES9_MARGIN_RATE:
      return isSetEs9MarginRate();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TPositionMarginRate)
      return this.equals((TPositionMarginRate)that);
    return false;
  }

  public boolean equals(TPositionMarginRate that) {
    if (that == null)
      return false;

    boolean this_present_ctpMarginRate = true && this.isSetCtpMarginRate();
    boolean that_present_ctpMarginRate = true && that.isSetCtpMarginRate();
    if (this_present_ctpMarginRate || that_present_ctpMarginRate) {
      if (!(this_present_ctpMarginRate && that_present_ctpMarginRate))
        return false;
      if (!this.ctpMarginRate.equals(that.ctpMarginRate))
        return false;
    }

    boolean this_present_es9MarginRate = true && this.isSetEs9MarginRate();
    boolean that_present_es9MarginRate = true && that.isSetEs9MarginRate();
    if (this_present_es9MarginRate || that_present_es9MarginRate) {
      if (!(this_present_es9MarginRate && that_present_es9MarginRate))
        return false;
      if (!this.es9MarginRate.equals(that.es9MarginRate))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TPositionMarginRate other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCtpMarginRate()).compareTo(other.isSetCtpMarginRate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCtpMarginRate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ctpMarginRate, other.ctpMarginRate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEs9MarginRate()).compareTo(other.isSetEs9MarginRate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEs9MarginRate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.es9MarginRate, other.es9MarginRate);
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
    StringBuilder sb = new StringBuilder("TPositionMarginRate(");
    boolean first = true;

    if (isSetCtpMarginRate()) {
      sb.append("ctpMarginRate:");
      if (this.ctpMarginRate == null) {
        sb.append("null");
      } else {
        sb.append(this.ctpMarginRate);
      }
      first = false;
    }
    if (isSetEs9MarginRate()) {
      if (!first) sb.append(", ");
      sb.append("es9MarginRate:");
      if (this.es9MarginRate == null) {
        sb.append("null");
      } else {
        sb.append(this.es9MarginRate);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (ctpMarginRate != null) {
      ctpMarginRate.validate();
    }
    if (es9MarginRate != null) {
      es9MarginRate.validate();
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

  private static class TPositionMarginRateStandardSchemeFactory implements SchemeFactory {
    public TPositionMarginRateStandardScheme getScheme() {
      return new TPositionMarginRateStandardScheme();
    }
  }

  private static class TPositionMarginRateStandardScheme extends StandardScheme<TPositionMarginRate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TPositionMarginRate struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CTP_MARGIN_RATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.ctpMarginRate = new TPositionCTPMarginRate();
              struct.ctpMarginRate.read(iprot);
              struct.setCtpMarginRateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ES9_MARGIN_RATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.es9MarginRate = new TPositionES9MarginRate();
              struct.es9MarginRate.read(iprot);
              struct.setEs9MarginRateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TPositionMarginRate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ctpMarginRate != null) {
        if (struct.isSetCtpMarginRate()) {
          oprot.writeFieldBegin(CTP_MARGIN_RATE_FIELD_DESC);
          struct.ctpMarginRate.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.es9MarginRate != null) {
        if (struct.isSetEs9MarginRate()) {
          oprot.writeFieldBegin(ES9_MARGIN_RATE_FIELD_DESC);
          struct.es9MarginRate.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TPositionMarginRateTupleSchemeFactory implements SchemeFactory {
    public TPositionMarginRateTupleScheme getScheme() {
      return new TPositionMarginRateTupleScheme();
    }
  }

  private static class TPositionMarginRateTupleScheme extends TupleScheme<TPositionMarginRate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TPositionMarginRate struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCtpMarginRate()) {
        optionals.set(0);
      }
      if (struct.isSetEs9MarginRate()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetCtpMarginRate()) {
        struct.ctpMarginRate.write(oprot);
      }
      if (struct.isSetEs9MarginRate()) {
        struct.es9MarginRate.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TPositionMarginRate struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.ctpMarginRate = new TPositionCTPMarginRate();
        struct.ctpMarginRate.read(iprot);
        struct.setCtpMarginRateIsSet(true);
      }
      if (incoming.get(1)) {
        struct.es9MarginRate = new TPositionES9MarginRate();
        struct.es9MarginRate.read(iprot);
        struct.setEs9MarginRateIsSet(true);
      }
    }
  }

}
