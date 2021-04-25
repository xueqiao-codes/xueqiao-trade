/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.arbitrage.thriftapi;

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

public class HostingXQComposeOrderLimitLegSendOrderExtraParam implements org.apache.thrift.TBase<HostingXQComposeOrderLimitLegSendOrderExtraParam, HostingXQComposeOrderLimitLegSendOrderExtraParam._Fields>, java.io.Serializable, Cloneable, Comparable<HostingXQComposeOrderLimitLegSendOrderExtraParam> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingXQComposeOrderLimitLegSendOrderExtraParam");

  private static final org.apache.thrift.protocol.TField QUANTITY_RATIO_FIELD_DESC = new org.apache.thrift.protocol.TField("quantityRatio", org.apache.thrift.protocol.TType.DOUBLE, (short)1);
  private static final org.apache.thrift.protocol.TField IMPACT_COST_FIELD_DESC = new org.apache.thrift.protocol.TField("impactCost", org.apache.thrift.protocol.TType.DOUBLE, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingXQComposeOrderLimitLegSendOrderExtraParamStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingXQComposeOrderLimitLegSendOrderExtraParamTupleSchemeFactory());
  }

  public double quantityRatio; // optional
  public double impactCost; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    QUANTITY_RATIO((short)1, "quantityRatio"),
    IMPACT_COST((short)2, "impactCost");

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
        case 1: // QUANTITY_RATIO
          return QUANTITY_RATIO;
        case 2: // IMPACT_COST
          return IMPACT_COST;
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
  private static final int __QUANTITYRATIO_ISSET_ID = 0;
  private static final int __IMPACTCOST_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.QUANTITY_RATIO,_Fields.IMPACT_COST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.QUANTITY_RATIO, new org.apache.thrift.meta_data.FieldMetaData("quantityRatio", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.IMPACT_COST, new org.apache.thrift.meta_data.FieldMetaData("impactCost", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingXQComposeOrderLimitLegSendOrderExtraParam.class, metaDataMap);
  }

  public HostingXQComposeOrderLimitLegSendOrderExtraParam() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingXQComposeOrderLimitLegSendOrderExtraParam(HostingXQComposeOrderLimitLegSendOrderExtraParam other) {
    __isset_bitfield = other.__isset_bitfield;
    this.quantityRatio = other.quantityRatio;
    this.impactCost = other.impactCost;
  }

  public HostingXQComposeOrderLimitLegSendOrderExtraParam deepCopy() {
    return new HostingXQComposeOrderLimitLegSendOrderExtraParam(this);
  }

  @Override
  public void clear() {
    setQuantityRatioIsSet(false);
    this.quantityRatio = 0.0;
    setImpactCostIsSet(false);
    this.impactCost = 0.0;
  }

  public double getQuantityRatio() {
    return this.quantityRatio;
  }

  public HostingXQComposeOrderLimitLegSendOrderExtraParam setQuantityRatio(double quantityRatio) {
    this.quantityRatio = quantityRatio;
    setQuantityRatioIsSet(true);
    return this;
  }

  public void unsetQuantityRatio() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __QUANTITYRATIO_ISSET_ID);
  }

  /** Returns true if field quantityRatio is set (has been assigned a value) and false otherwise */
  public boolean isSetQuantityRatio() {
    return EncodingUtils.testBit(__isset_bitfield, __QUANTITYRATIO_ISSET_ID);
  }

  public void setQuantityRatioIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __QUANTITYRATIO_ISSET_ID, value);
  }

  public double getImpactCost() {
    return this.impactCost;
  }

  public HostingXQComposeOrderLimitLegSendOrderExtraParam setImpactCost(double impactCost) {
    this.impactCost = impactCost;
    setImpactCostIsSet(true);
    return this;
  }

  public void unsetImpactCost() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IMPACTCOST_ISSET_ID);
  }

  /** Returns true if field impactCost is set (has been assigned a value) and false otherwise */
  public boolean isSetImpactCost() {
    return EncodingUtils.testBit(__isset_bitfield, __IMPACTCOST_ISSET_ID);
  }

  public void setImpactCostIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IMPACTCOST_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case QUANTITY_RATIO:
      if (value == null) {
        unsetQuantityRatio();
      } else {
        setQuantityRatio((Double)value);
      }
      break;

    case IMPACT_COST:
      if (value == null) {
        unsetImpactCost();
      } else {
        setImpactCost((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case QUANTITY_RATIO:
      return Double.valueOf(getQuantityRatio());

    case IMPACT_COST:
      return Double.valueOf(getImpactCost());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case QUANTITY_RATIO:
      return isSetQuantityRatio();
    case IMPACT_COST:
      return isSetImpactCost();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingXQComposeOrderLimitLegSendOrderExtraParam)
      return this.equals((HostingXQComposeOrderLimitLegSendOrderExtraParam)that);
    return false;
  }

  public boolean equals(HostingXQComposeOrderLimitLegSendOrderExtraParam that) {
    if (that == null)
      return false;

    boolean this_present_quantityRatio = true && this.isSetQuantityRatio();
    boolean that_present_quantityRatio = true && that.isSetQuantityRatio();
    if (this_present_quantityRatio || that_present_quantityRatio) {
      if (!(this_present_quantityRatio && that_present_quantityRatio))
        return false;
      if (this.quantityRatio != that.quantityRatio)
        return false;
    }

    boolean this_present_impactCost = true && this.isSetImpactCost();
    boolean that_present_impactCost = true && that.isSetImpactCost();
    if (this_present_impactCost || that_present_impactCost) {
      if (!(this_present_impactCost && that_present_impactCost))
        return false;
      if (this.impactCost != that.impactCost)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingXQComposeOrderLimitLegSendOrderExtraParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetQuantityRatio()).compareTo(other.isSetQuantityRatio());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuantityRatio()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.quantityRatio, other.quantityRatio);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetImpactCost()).compareTo(other.isSetImpactCost());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetImpactCost()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.impactCost, other.impactCost);
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
    StringBuilder sb = new StringBuilder("HostingXQComposeOrderLimitLegSendOrderExtraParam(");
    boolean first = true;

    if (isSetQuantityRatio()) {
      sb.append("quantityRatio:");
      sb.append(this.quantityRatio);
      first = false;
    }
    if (isSetImpactCost()) {
      if (!first) sb.append(", ");
      sb.append("impactCost:");
      sb.append(this.impactCost);
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

  private static class HostingXQComposeOrderLimitLegSendOrderExtraParamStandardSchemeFactory implements SchemeFactory {
    public HostingXQComposeOrderLimitLegSendOrderExtraParamStandardScheme getScheme() {
      return new HostingXQComposeOrderLimitLegSendOrderExtraParamStandardScheme();
    }
  }

  private static class HostingXQComposeOrderLimitLegSendOrderExtraParamStandardScheme extends StandardScheme<HostingXQComposeOrderLimitLegSendOrderExtraParam> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingXQComposeOrderLimitLegSendOrderExtraParam struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // QUANTITY_RATIO
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.quantityRatio = iprot.readDouble();
              struct.setQuantityRatioIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // IMPACT_COST
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.impactCost = iprot.readDouble();
              struct.setImpactCostIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingXQComposeOrderLimitLegSendOrderExtraParam struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetQuantityRatio()) {
        oprot.writeFieldBegin(QUANTITY_RATIO_FIELD_DESC);
        oprot.writeDouble(struct.quantityRatio);
        oprot.writeFieldEnd();
      }
      if (struct.isSetImpactCost()) {
        oprot.writeFieldBegin(IMPACT_COST_FIELD_DESC);
        oprot.writeDouble(struct.impactCost);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingXQComposeOrderLimitLegSendOrderExtraParamTupleSchemeFactory implements SchemeFactory {
    public HostingXQComposeOrderLimitLegSendOrderExtraParamTupleScheme getScheme() {
      return new HostingXQComposeOrderLimitLegSendOrderExtraParamTupleScheme();
    }
  }

  private static class HostingXQComposeOrderLimitLegSendOrderExtraParamTupleScheme extends TupleScheme<HostingXQComposeOrderLimitLegSendOrderExtraParam> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingXQComposeOrderLimitLegSendOrderExtraParam struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetQuantityRatio()) {
        optionals.set(0);
      }
      if (struct.isSetImpactCost()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetQuantityRatio()) {
        oprot.writeDouble(struct.quantityRatio);
      }
      if (struct.isSetImpactCost()) {
        oprot.writeDouble(struct.impactCost);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingXQComposeOrderLimitLegSendOrderExtraParam struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.quantityRatio = iprot.readDouble();
        struct.setQuantityRatioIsSet(true);
      }
      if (incoming.get(1)) {
        struct.impactCost = iprot.readDouble();
        struct.setImpactCostIsSet(true);
      }
    }
  }

}
