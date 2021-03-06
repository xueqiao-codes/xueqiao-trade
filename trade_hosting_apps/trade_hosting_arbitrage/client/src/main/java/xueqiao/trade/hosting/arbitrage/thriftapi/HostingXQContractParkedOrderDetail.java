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

public class HostingXQContractParkedOrderDetail implements org.apache.thrift.TBase<HostingXQContractParkedOrderDetail, HostingXQContractParkedOrderDetail._Fields>, java.io.Serializable, Cloneable, Comparable<HostingXQContractParkedOrderDetail> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingXQContractParkedOrderDetail");

  private static final org.apache.thrift.protocol.TField DIRECTION_FIELD_DESC = new org.apache.thrift.protocol.TField("direction", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("price", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("quantity", org.apache.thrift.protocol.TType.I32, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingXQContractParkedOrderDetailStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingXQContractParkedOrderDetailTupleSchemeFactory());
  }

  /**
   * 
   * @see HostingXQTradeDirection
   */
  public HostingXQTradeDirection direction; // optional
  public HostingXQOrderPrice price; // optional
  public int quantity; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see HostingXQTradeDirection
     */
    DIRECTION((short)1, "direction"),
    PRICE((short)2, "price"),
    QUANTITY((short)3, "quantity");

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
        case 1: // DIRECTION
          return DIRECTION;
        case 2: // PRICE
          return PRICE;
        case 3: // QUANTITY
          return QUANTITY;
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
  private static final int __QUANTITY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.DIRECTION,_Fields.PRICE,_Fields.QUANTITY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DIRECTION, new org.apache.thrift.meta_data.FieldMetaData("direction", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, HostingXQTradeDirection.class)));
    tmpMap.put(_Fields.PRICE, new org.apache.thrift.meta_data.FieldMetaData("price", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingXQOrderPrice.class)));
    tmpMap.put(_Fields.QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("quantity", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingXQContractParkedOrderDetail.class, metaDataMap);
  }

  public HostingXQContractParkedOrderDetail() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingXQContractParkedOrderDetail(HostingXQContractParkedOrderDetail other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDirection()) {
      this.direction = other.direction;
    }
    if (other.isSetPrice()) {
      this.price = new HostingXQOrderPrice(other.price);
    }
    this.quantity = other.quantity;
  }

  public HostingXQContractParkedOrderDetail deepCopy() {
    return new HostingXQContractParkedOrderDetail(this);
  }

  @Override
  public void clear() {
    this.direction = null;
    this.price = null;
    setQuantityIsSet(false);
    this.quantity = 0;
  }

  /**
   * 
   * @see HostingXQTradeDirection
   */
  public HostingXQTradeDirection getDirection() {
    return this.direction;
  }

  /**
   * 
   * @see HostingXQTradeDirection
   */
  public HostingXQContractParkedOrderDetail setDirection(HostingXQTradeDirection direction) {
    this.direction = direction;
    return this;
  }

  public void unsetDirection() {
    this.direction = null;
  }

  /** Returns true if field direction is set (has been assigned a value) and false otherwise */
  public boolean isSetDirection() {
    return this.direction != null;
  }

  public void setDirectionIsSet(boolean value) {
    if (!value) {
      this.direction = null;
    }
  }

  public HostingXQOrderPrice getPrice() {
    return this.price;
  }

  public HostingXQContractParkedOrderDetail setPrice(HostingXQOrderPrice price) {
    this.price = price;
    return this;
  }

  public void unsetPrice() {
    this.price = null;
  }

  /** Returns true if field price is set (has been assigned a value) and false otherwise */
  public boolean isSetPrice() {
    return this.price != null;
  }

  public void setPriceIsSet(boolean value) {
    if (!value) {
      this.price = null;
    }
  }

  public int getQuantity() {
    return this.quantity;
  }

  public HostingXQContractParkedOrderDetail setQuantity(int quantity) {
    this.quantity = quantity;
    setQuantityIsSet(true);
    return this;
  }

  public void unsetQuantity() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __QUANTITY_ISSET_ID);
  }

  /** Returns true if field quantity is set (has been assigned a value) and false otherwise */
  public boolean isSetQuantity() {
    return EncodingUtils.testBit(__isset_bitfield, __QUANTITY_ISSET_ID);
  }

  public void setQuantityIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __QUANTITY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DIRECTION:
      if (value == null) {
        unsetDirection();
      } else {
        setDirection((HostingXQTradeDirection)value);
      }
      break;

    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((HostingXQOrderPrice)value);
      }
      break;

    case QUANTITY:
      if (value == null) {
        unsetQuantity();
      } else {
        setQuantity((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DIRECTION:
      return getDirection();

    case PRICE:
      return getPrice();

    case QUANTITY:
      return Integer.valueOf(getQuantity());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DIRECTION:
      return isSetDirection();
    case PRICE:
      return isSetPrice();
    case QUANTITY:
      return isSetQuantity();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingXQContractParkedOrderDetail)
      return this.equals((HostingXQContractParkedOrderDetail)that);
    return false;
  }

  public boolean equals(HostingXQContractParkedOrderDetail that) {
    if (that == null)
      return false;

    boolean this_present_direction = true && this.isSetDirection();
    boolean that_present_direction = true && that.isSetDirection();
    if (this_present_direction || that_present_direction) {
      if (!(this_present_direction && that_present_direction))
        return false;
      if (!this.direction.equals(that.direction))
        return false;
    }

    boolean this_present_price = true && this.isSetPrice();
    boolean that_present_price = true && that.isSetPrice();
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (!this.price.equals(that.price))
        return false;
    }

    boolean this_present_quantity = true && this.isSetQuantity();
    boolean that_present_quantity = true && that.isSetQuantity();
    if (this_present_quantity || that_present_quantity) {
      if (!(this_present_quantity && that_present_quantity))
        return false;
      if (this.quantity != that.quantity)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingXQContractParkedOrderDetail other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDirection()).compareTo(other.isSetDirection());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDirection()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.direction, other.direction);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPrice()).compareTo(other.isSetPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.price, other.price);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetQuantity()).compareTo(other.isSetQuantity());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetQuantity()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.quantity, other.quantity);
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
    StringBuilder sb = new StringBuilder("HostingXQContractParkedOrderDetail(");
    boolean first = true;

    if (isSetDirection()) {
      sb.append("direction:");
      if (this.direction == null) {
        sb.append("null");
      } else {
        sb.append(this.direction);
      }
      first = false;
    }
    if (isSetPrice()) {
      if (!first) sb.append(", ");
      sb.append("price:");
      if (this.price == null) {
        sb.append("null");
      } else {
        sb.append(this.price);
      }
      first = false;
    }
    if (isSetQuantity()) {
      if (!first) sb.append(", ");
      sb.append("quantity:");
      sb.append(this.quantity);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (price != null) {
      price.validate();
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

  private static class HostingXQContractParkedOrderDetailStandardSchemeFactory implements SchemeFactory {
    public HostingXQContractParkedOrderDetailStandardScheme getScheme() {
      return new HostingXQContractParkedOrderDetailStandardScheme();
    }
  }

  private static class HostingXQContractParkedOrderDetailStandardScheme extends StandardScheme<HostingXQContractParkedOrderDetail> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingXQContractParkedOrderDetail struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DIRECTION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.direction = HostingXQTradeDirection.findByValue(iprot.readI32());
              struct.setDirectionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.price = new HostingXQOrderPrice();
              struct.price.read(iprot);
              struct.setPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // QUANTITY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.quantity = iprot.readI32();
              struct.setQuantityIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingXQContractParkedOrderDetail struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.direction != null) {
        if (struct.isSetDirection()) {
          oprot.writeFieldBegin(DIRECTION_FIELD_DESC);
          oprot.writeI32(struct.direction.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.price != null) {
        if (struct.isSetPrice()) {
          oprot.writeFieldBegin(PRICE_FIELD_DESC);
          struct.price.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetQuantity()) {
        oprot.writeFieldBegin(QUANTITY_FIELD_DESC);
        oprot.writeI32(struct.quantity);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingXQContractParkedOrderDetailTupleSchemeFactory implements SchemeFactory {
    public HostingXQContractParkedOrderDetailTupleScheme getScheme() {
      return new HostingXQContractParkedOrderDetailTupleScheme();
    }
  }

  private static class HostingXQContractParkedOrderDetailTupleScheme extends TupleScheme<HostingXQContractParkedOrderDetail> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingXQContractParkedOrderDetail struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetDirection()) {
        optionals.set(0);
      }
      if (struct.isSetPrice()) {
        optionals.set(1);
      }
      if (struct.isSetQuantity()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetDirection()) {
        oprot.writeI32(struct.direction.getValue());
      }
      if (struct.isSetPrice()) {
        struct.price.write(oprot);
      }
      if (struct.isSetQuantity()) {
        oprot.writeI32(struct.quantity);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingXQContractParkedOrderDetail struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.direction = HostingXQTradeDirection.findByValue(iprot.readI32());
        struct.setDirectionIsSet(true);
      }
      if (incoming.get(1)) {
        struct.price = new HostingXQOrderPrice();
        struct.price.read(iprot);
        struct.setPriceIsSet(true);
      }
      if (incoming.get(2)) {
        struct.quantity = iprot.readI32();
        struct.setQuantityIsSet(true);
      }
    }
  }

}

