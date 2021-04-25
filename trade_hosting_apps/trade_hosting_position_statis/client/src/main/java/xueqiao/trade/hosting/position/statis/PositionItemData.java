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

public class PositionItemData implements org.apache.thrift.TBase<PositionItemData, PositionItemData._Fields>, java.io.Serializable, Cloneable, Comparable<PositionItemData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PositionItemData");

  private static final org.apache.thrift.protocol.TField POSITION_ITEM_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("positionItemId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("quantity", org.apache.thrift.protocol.TType.I32, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PositionItemDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PositionItemDataTupleSchemeFactory());
  }

  public long positionItemId; // optional
  public int quantity; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POSITION_ITEM_ID((short)1, "positionItemId"),
    QUANTITY((short)2, "quantity");

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
        case 1: // POSITION_ITEM_ID
          return POSITION_ITEM_ID;
        case 2: // QUANTITY
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
  private static final int __POSITIONITEMID_ISSET_ID = 0;
  private static final int __QUANTITY_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.POSITION_ITEM_ID,_Fields.QUANTITY};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POSITION_ITEM_ID, new org.apache.thrift.meta_data.FieldMetaData("positionItemId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("quantity", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PositionItemData.class, metaDataMap);
  }

  public PositionItemData() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PositionItemData(PositionItemData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.positionItemId = other.positionItemId;
    this.quantity = other.quantity;
  }

  public PositionItemData deepCopy() {
    return new PositionItemData(this);
  }

  @Override
  public void clear() {
    setPositionItemIdIsSet(false);
    this.positionItemId = 0;
    setQuantityIsSet(false);
    this.quantity = 0;
  }

  public long getPositionItemId() {
    return this.positionItemId;
  }

  public PositionItemData setPositionItemId(long positionItemId) {
    this.positionItemId = positionItemId;
    setPositionItemIdIsSet(true);
    return this;
  }

  public void unsetPositionItemId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __POSITIONITEMID_ISSET_ID);
  }

  /** Returns true if field positionItemId is set (has been assigned a value) and false otherwise */
  public boolean isSetPositionItemId() {
    return EncodingUtils.testBit(__isset_bitfield, __POSITIONITEMID_ISSET_ID);
  }

  public void setPositionItemIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __POSITIONITEMID_ISSET_ID, value);
  }

  public int getQuantity() {
    return this.quantity;
  }

  public PositionItemData setQuantity(int quantity) {
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
    case POSITION_ITEM_ID:
      if (value == null) {
        unsetPositionItemId();
      } else {
        setPositionItemId((Long)value);
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
    case POSITION_ITEM_ID:
      return Long.valueOf(getPositionItemId());

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
    case POSITION_ITEM_ID:
      return isSetPositionItemId();
    case QUANTITY:
      return isSetQuantity();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PositionItemData)
      return this.equals((PositionItemData)that);
    return false;
  }

  public boolean equals(PositionItemData that) {
    if (that == null)
      return false;

    boolean this_present_positionItemId = true && this.isSetPositionItemId();
    boolean that_present_positionItemId = true && that.isSetPositionItemId();
    if (this_present_positionItemId || that_present_positionItemId) {
      if (!(this_present_positionItemId && that_present_positionItemId))
        return false;
      if (this.positionItemId != that.positionItemId)
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
  public int compareTo(PositionItemData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPositionItemId()).compareTo(other.isSetPositionItemId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPositionItemId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.positionItemId, other.positionItemId);
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
    StringBuilder sb = new StringBuilder("PositionItemData(");
    boolean first = true;

    if (isSetPositionItemId()) {
      sb.append("positionItemId:");
      sb.append(this.positionItemId);
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

  private static class PositionItemDataStandardSchemeFactory implements SchemeFactory {
    public PositionItemDataStandardScheme getScheme() {
      return new PositionItemDataStandardScheme();
    }
  }

  private static class PositionItemDataStandardScheme extends StandardScheme<PositionItemData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PositionItemData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // POSITION_ITEM_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.positionItemId = iprot.readI64();
              struct.setPositionItemIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // QUANTITY
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, PositionItemData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetPositionItemId()) {
        oprot.writeFieldBegin(POSITION_ITEM_ID_FIELD_DESC);
        oprot.writeI64(struct.positionItemId);
        oprot.writeFieldEnd();
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

  private static class PositionItemDataTupleSchemeFactory implements SchemeFactory {
    public PositionItemDataTupleScheme getScheme() {
      return new PositionItemDataTupleScheme();
    }
  }

  private static class PositionItemDataTupleScheme extends TupleScheme<PositionItemData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PositionItemData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPositionItemId()) {
        optionals.set(0);
      }
      if (struct.isSetQuantity()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetPositionItemId()) {
        oprot.writeI64(struct.positionItemId);
      }
      if (struct.isSetQuantity()) {
        oprot.writeI32(struct.quantity);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PositionItemData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.positionItemId = iprot.readI64();
        struct.setPositionItemIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.quantity = iprot.readI32();
        struct.setQuantityIsSet(true);
      }
    }
  }

}
