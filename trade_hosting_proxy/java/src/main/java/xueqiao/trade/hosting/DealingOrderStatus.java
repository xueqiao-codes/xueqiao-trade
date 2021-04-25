/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting;

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

public class DealingOrderStatus implements org.apache.thrift.TBase<DealingOrderStatus, DealingOrderStatus._Fields>, java.io.Serializable, Cloneable, Comparable<DealingOrderStatus> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("DealingOrderStatus");

  private static final org.apache.thrift.protocol.TField SLED_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DEALING_ORDER_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("dealingOrderState", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField STD_ORDER_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("stdOrderState", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new DealingOrderStatusStandardSchemeFactory());
    schemes.put(TupleScheme.class, new DealingOrderStatusTupleSchemeFactory());
  }

  public String sledId; // optional
  /**
   * 
   * @see DealingOrderStateType
   */
  public DealingOrderStateType dealingOrderState; // optional
  /**
   * 
   * @see StdOrderStateType
   */
  public StdOrderStateType stdOrderState; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLED_ID((short)1, "sledId"),
    /**
     * 
     * @see DealingOrderStateType
     */
    DEALING_ORDER_STATE((short)2, "dealingOrderState"),
    /**
     * 
     * @see StdOrderStateType
     */
    STD_ORDER_STATE((short)3, "stdOrderState");

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
        case 1: // SLED_ID
          return SLED_ID;
        case 2: // DEALING_ORDER_STATE
          return DEALING_ORDER_STATE;
        case 3: // STD_ORDER_STATE
          return STD_ORDER_STATE;
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
  private _Fields optionals[] = {_Fields.SLED_ID,_Fields.DEALING_ORDER_STATE,_Fields.STD_ORDER_STATE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLED_ID, new org.apache.thrift.meta_data.FieldMetaData("sledId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DEALING_ORDER_STATE, new org.apache.thrift.meta_data.FieldMetaData("dealingOrderState", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, DealingOrderStateType.class)));
    tmpMap.put(_Fields.STD_ORDER_STATE, new org.apache.thrift.meta_data.FieldMetaData("stdOrderState", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, StdOrderStateType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(DealingOrderStatus.class, metaDataMap);
  }

  public DealingOrderStatus() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public DealingOrderStatus(DealingOrderStatus other) {
    if (other.isSetSledId()) {
      this.sledId = other.sledId;
    }
    if (other.isSetDealingOrderState()) {
      this.dealingOrderState = other.dealingOrderState;
    }
    if (other.isSetStdOrderState()) {
      this.stdOrderState = other.stdOrderState;
    }
  }

  public DealingOrderStatus deepCopy() {
    return new DealingOrderStatus(this);
  }

  @Override
  public void clear() {
    this.sledId = null;
    this.dealingOrderState = null;
    this.stdOrderState = null;
  }

  public String getSledId() {
    return this.sledId;
  }

  public DealingOrderStatus setSledId(String sledId) {
    this.sledId = sledId;
    return this;
  }

  public void unsetSledId() {
    this.sledId = null;
  }

  /** Returns true if field sledId is set (has been assigned a value) and false otherwise */
  public boolean isSetSledId() {
    return this.sledId != null;
  }

  public void setSledIdIsSet(boolean value) {
    if (!value) {
      this.sledId = null;
    }
  }

  /**
   * 
   * @see DealingOrderStateType
   */
  public DealingOrderStateType getDealingOrderState() {
    return this.dealingOrderState;
  }

  /**
   * 
   * @see DealingOrderStateType
   */
  public DealingOrderStatus setDealingOrderState(DealingOrderStateType dealingOrderState) {
    this.dealingOrderState = dealingOrderState;
    return this;
  }

  public void unsetDealingOrderState() {
    this.dealingOrderState = null;
  }

  /** Returns true if field dealingOrderState is set (has been assigned a value) and false otherwise */
  public boolean isSetDealingOrderState() {
    return this.dealingOrderState != null;
  }

  public void setDealingOrderStateIsSet(boolean value) {
    if (!value) {
      this.dealingOrderState = null;
    }
  }

  /**
   * 
   * @see StdOrderStateType
   */
  public StdOrderStateType getStdOrderState() {
    return this.stdOrderState;
  }

  /**
   * 
   * @see StdOrderStateType
   */
  public DealingOrderStatus setStdOrderState(StdOrderStateType stdOrderState) {
    this.stdOrderState = stdOrderState;
    return this;
  }

  public void unsetStdOrderState() {
    this.stdOrderState = null;
  }

  /** Returns true if field stdOrderState is set (has been assigned a value) and false otherwise */
  public boolean isSetStdOrderState() {
    return this.stdOrderState != null;
  }

  public void setStdOrderStateIsSet(boolean value) {
    if (!value) {
      this.stdOrderState = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SLED_ID:
      if (value == null) {
        unsetSledId();
      } else {
        setSledId((String)value);
      }
      break;

    case DEALING_ORDER_STATE:
      if (value == null) {
        unsetDealingOrderState();
      } else {
        setDealingOrderState((DealingOrderStateType)value);
      }
      break;

    case STD_ORDER_STATE:
      if (value == null) {
        unsetStdOrderState();
      } else {
        setStdOrderState((StdOrderStateType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLED_ID:
      return getSledId();

    case DEALING_ORDER_STATE:
      return getDealingOrderState();

    case STD_ORDER_STATE:
      return getStdOrderState();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SLED_ID:
      return isSetSledId();
    case DEALING_ORDER_STATE:
      return isSetDealingOrderState();
    case STD_ORDER_STATE:
      return isSetStdOrderState();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof DealingOrderStatus)
      return this.equals((DealingOrderStatus)that);
    return false;
  }

  public boolean equals(DealingOrderStatus that) {
    if (that == null)
      return false;

    boolean this_present_sledId = true && this.isSetSledId();
    boolean that_present_sledId = true && that.isSetSledId();
    if (this_present_sledId || that_present_sledId) {
      if (!(this_present_sledId && that_present_sledId))
        return false;
      if (!this.sledId.equals(that.sledId))
        return false;
    }

    boolean this_present_dealingOrderState = true && this.isSetDealingOrderState();
    boolean that_present_dealingOrderState = true && that.isSetDealingOrderState();
    if (this_present_dealingOrderState || that_present_dealingOrderState) {
      if (!(this_present_dealingOrderState && that_present_dealingOrderState))
        return false;
      if (!this.dealingOrderState.equals(that.dealingOrderState))
        return false;
    }

    boolean this_present_stdOrderState = true && this.isSetStdOrderState();
    boolean that_present_stdOrderState = true && that.isSetStdOrderState();
    if (this_present_stdOrderState || that_present_stdOrderState) {
      if (!(this_present_stdOrderState && that_present_stdOrderState))
        return false;
      if (!this.stdOrderState.equals(that.stdOrderState))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(DealingOrderStatus other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSledId()).compareTo(other.isSetSledId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledId, other.sledId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDealingOrderState()).compareTo(other.isSetDealingOrderState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDealingOrderState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dealingOrderState, other.dealingOrderState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStdOrderState()).compareTo(other.isSetStdOrderState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStdOrderState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stdOrderState, other.stdOrderState);
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
    StringBuilder sb = new StringBuilder("DealingOrderStatus(");
    boolean first = true;

    if (isSetSledId()) {
      sb.append("sledId:");
      if (this.sledId == null) {
        sb.append("null");
      } else {
        sb.append(this.sledId);
      }
      first = false;
    }
    if (isSetDealingOrderState()) {
      if (!first) sb.append(", ");
      sb.append("dealingOrderState:");
      if (this.dealingOrderState == null) {
        sb.append("null");
      } else {
        sb.append(this.dealingOrderState);
      }
      first = false;
    }
    if (isSetStdOrderState()) {
      if (!first) sb.append(", ");
      sb.append("stdOrderState:");
      if (this.stdOrderState == null) {
        sb.append("null");
      } else {
        sb.append(this.stdOrderState);
      }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class DealingOrderStatusStandardSchemeFactory implements SchemeFactory {
    public DealingOrderStatusStandardScheme getScheme() {
      return new DealingOrderStatusStandardScheme();
    }
  }

  private static class DealingOrderStatusStandardScheme extends StandardScheme<DealingOrderStatus> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, DealingOrderStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SLED_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sledId = iprot.readString();
              struct.setSledIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DEALING_ORDER_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.dealingOrderState = DealingOrderStateType.findByValue(iprot.readI32());
              struct.setDealingOrderStateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // STD_ORDER_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.stdOrderState = StdOrderStateType.findByValue(iprot.readI32());
              struct.setStdOrderStateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, DealingOrderStatus struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sledId != null) {
        if (struct.isSetSledId()) {
          oprot.writeFieldBegin(SLED_ID_FIELD_DESC);
          oprot.writeString(struct.sledId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.dealingOrderState != null) {
        if (struct.isSetDealingOrderState()) {
          oprot.writeFieldBegin(DEALING_ORDER_STATE_FIELD_DESC);
          oprot.writeI32(struct.dealingOrderState.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.stdOrderState != null) {
        if (struct.isSetStdOrderState()) {
          oprot.writeFieldBegin(STD_ORDER_STATE_FIELD_DESC);
          oprot.writeI32(struct.stdOrderState.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class DealingOrderStatusTupleSchemeFactory implements SchemeFactory {
    public DealingOrderStatusTupleScheme getScheme() {
      return new DealingOrderStatusTupleScheme();
    }
  }

  private static class DealingOrderStatusTupleScheme extends TupleScheme<DealingOrderStatus> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, DealingOrderStatus struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSledId()) {
        optionals.set(0);
      }
      if (struct.isSetDealingOrderState()) {
        optionals.set(1);
      }
      if (struct.isSetStdOrderState()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetSledId()) {
        oprot.writeString(struct.sledId);
      }
      if (struct.isSetDealingOrderState()) {
        oprot.writeI32(struct.dealingOrderState.getValue());
      }
      if (struct.isSetStdOrderState()) {
        oprot.writeI32(struct.stdOrderState.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, DealingOrderStatus struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.sledId = iprot.readString();
        struct.setSledIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.dealingOrderState = DealingOrderStateType.findByValue(iprot.readI32());
        struct.setDealingOrderStateIsSet(true);
      }
      if (incoming.get(2)) {
        struct.stdOrderState = StdOrderStateType.findByValue(iprot.readI32());
        struct.setStdOrderStateIsSet(true);
      }
    }
  }

}

