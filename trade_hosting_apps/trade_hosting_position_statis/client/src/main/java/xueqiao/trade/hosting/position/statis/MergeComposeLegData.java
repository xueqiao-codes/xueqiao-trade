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

public class MergeComposeLegData implements org.apache.thrift.TBase<MergeComposeLegData, MergeComposeLegData._Fields>, java.io.Serializable, Cloneable, Comparable<MergeComposeLegData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MergeComposeLegData");

  private static final org.apache.thrift.protocol.TField POSITION_ITEM_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("positionItemId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField QUANTITY_FIELD_DESC = new org.apache.thrift.protocol.TField("quantity", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("price", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField DIRETION_FIELD_DESC = new org.apache.thrift.protocol.TField("diretion", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MergeComposeLegDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MergeComposeLegDataTupleSchemeFactory());
  }

  public long positionItemId; // optional
  public long sledContractId; // optional
  public int quantity; // optional
  public double price; // optional
  /**
   * 
   * @see StatDirection
   */
  public StatDirection diretion; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    POSITION_ITEM_ID((short)1, "positionItemId"),
    SLED_CONTRACT_ID((short)2, "sledContractId"),
    QUANTITY((short)3, "quantity"),
    PRICE((short)4, "price"),
    /**
     * 
     * @see StatDirection
     */
    DIRETION((short)5, "diretion");

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
        case 2: // SLED_CONTRACT_ID
          return SLED_CONTRACT_ID;
        case 3: // QUANTITY
          return QUANTITY;
        case 4: // PRICE
          return PRICE;
        case 5: // DIRETION
          return DIRETION;
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
  private static final int __SLEDCONTRACTID_ISSET_ID = 1;
  private static final int __QUANTITY_ISSET_ID = 2;
  private static final int __PRICE_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.POSITION_ITEM_ID,_Fields.SLED_CONTRACT_ID,_Fields.QUANTITY,_Fields.PRICE,_Fields.DIRETION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.POSITION_ITEM_ID, new org.apache.thrift.meta_data.FieldMetaData("positionItemId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_CONTRACT_ID, new org.apache.thrift.meta_data.FieldMetaData("sledContractId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.QUANTITY, new org.apache.thrift.meta_data.FieldMetaData("quantity", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.PRICE, new org.apache.thrift.meta_data.FieldMetaData("price", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.DIRETION, new org.apache.thrift.meta_data.FieldMetaData("diretion", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, StatDirection.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MergeComposeLegData.class, metaDataMap);
  }

  public MergeComposeLegData() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MergeComposeLegData(MergeComposeLegData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.positionItemId = other.positionItemId;
    this.sledContractId = other.sledContractId;
    this.quantity = other.quantity;
    this.price = other.price;
    if (other.isSetDiretion()) {
      this.diretion = other.diretion;
    }
  }

  public MergeComposeLegData deepCopy() {
    return new MergeComposeLegData(this);
  }

  @Override
  public void clear() {
    setPositionItemIdIsSet(false);
    this.positionItemId = 0;
    setSledContractIdIsSet(false);
    this.sledContractId = 0;
    setQuantityIsSet(false);
    this.quantity = 0;
    setPriceIsSet(false);
    this.price = 0.0;
    this.diretion = null;
  }

  public long getPositionItemId() {
    return this.positionItemId;
  }

  public MergeComposeLegData setPositionItemId(long positionItemId) {
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

  public long getSledContractId() {
    return this.sledContractId;
  }

  public MergeComposeLegData setSledContractId(long sledContractId) {
    this.sledContractId = sledContractId;
    setSledContractIdIsSet(true);
    return this;
  }

  public void unsetSledContractId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SLEDCONTRACTID_ISSET_ID);
  }

  /** Returns true if field sledContractId is set (has been assigned a value) and false otherwise */
  public boolean isSetSledContractId() {
    return EncodingUtils.testBit(__isset_bitfield, __SLEDCONTRACTID_ISSET_ID);
  }

  public void setSledContractIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SLEDCONTRACTID_ISSET_ID, value);
  }

  public int getQuantity() {
    return this.quantity;
  }

  public MergeComposeLegData setQuantity(int quantity) {
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

  public double getPrice() {
    return this.price;
  }

  public MergeComposeLegData setPrice(double price) {
    this.price = price;
    setPriceIsSet(true);
    return this;
  }

  public void unsetPrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  /** Returns true if field price is set (has been assigned a value) and false otherwise */
  public boolean isSetPrice() {
    return EncodingUtils.testBit(__isset_bitfield, __PRICE_ISSET_ID);
  }

  public void setPriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PRICE_ISSET_ID, value);
  }

  /**
   * 
   * @see StatDirection
   */
  public StatDirection getDiretion() {
    return this.diretion;
  }

  /**
   * 
   * @see StatDirection
   */
  public MergeComposeLegData setDiretion(StatDirection diretion) {
    this.diretion = diretion;
    return this;
  }

  public void unsetDiretion() {
    this.diretion = null;
  }

  /** Returns true if field diretion is set (has been assigned a value) and false otherwise */
  public boolean isSetDiretion() {
    return this.diretion != null;
  }

  public void setDiretionIsSet(boolean value) {
    if (!value) {
      this.diretion = null;
    }
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

    case SLED_CONTRACT_ID:
      if (value == null) {
        unsetSledContractId();
      } else {
        setSledContractId((Long)value);
      }
      break;

    case QUANTITY:
      if (value == null) {
        unsetQuantity();
      } else {
        setQuantity((Integer)value);
      }
      break;

    case PRICE:
      if (value == null) {
        unsetPrice();
      } else {
        setPrice((Double)value);
      }
      break;

    case DIRETION:
      if (value == null) {
        unsetDiretion();
      } else {
        setDiretion((StatDirection)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case POSITION_ITEM_ID:
      return Long.valueOf(getPositionItemId());

    case SLED_CONTRACT_ID:
      return Long.valueOf(getSledContractId());

    case QUANTITY:
      return Integer.valueOf(getQuantity());

    case PRICE:
      return Double.valueOf(getPrice());

    case DIRETION:
      return getDiretion();

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
    case SLED_CONTRACT_ID:
      return isSetSledContractId();
    case QUANTITY:
      return isSetQuantity();
    case PRICE:
      return isSetPrice();
    case DIRETION:
      return isSetDiretion();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MergeComposeLegData)
      return this.equals((MergeComposeLegData)that);
    return false;
  }

  public boolean equals(MergeComposeLegData that) {
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

    boolean this_present_sledContractId = true && this.isSetSledContractId();
    boolean that_present_sledContractId = true && that.isSetSledContractId();
    if (this_present_sledContractId || that_present_sledContractId) {
      if (!(this_present_sledContractId && that_present_sledContractId))
        return false;
      if (this.sledContractId != that.sledContractId)
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

    boolean this_present_price = true && this.isSetPrice();
    boolean that_present_price = true && that.isSetPrice();
    if (this_present_price || that_present_price) {
      if (!(this_present_price && that_present_price))
        return false;
      if (this.price != that.price)
        return false;
    }

    boolean this_present_diretion = true && this.isSetDiretion();
    boolean that_present_diretion = true && that.isSetDiretion();
    if (this_present_diretion || that_present_diretion) {
      if (!(this_present_diretion && that_present_diretion))
        return false;
      if (!this.diretion.equals(that.diretion))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(MergeComposeLegData other) {
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
    lastComparison = Boolean.valueOf(isSetSledContractId()).compareTo(other.isSetSledContractId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledContractId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledContractId, other.sledContractId);
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
    lastComparison = Boolean.valueOf(isSetDiretion()).compareTo(other.isSetDiretion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDiretion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.diretion, other.diretion);
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
    StringBuilder sb = new StringBuilder("MergeComposeLegData(");
    boolean first = true;

    if (isSetPositionItemId()) {
      sb.append("positionItemId:");
      sb.append(this.positionItemId);
      first = false;
    }
    if (isSetSledContractId()) {
      if (!first) sb.append(", ");
      sb.append("sledContractId:");
      sb.append(this.sledContractId);
      first = false;
    }
    if (isSetQuantity()) {
      if (!first) sb.append(", ");
      sb.append("quantity:");
      sb.append(this.quantity);
      first = false;
    }
    if (isSetPrice()) {
      if (!first) sb.append(", ");
      sb.append("price:");
      sb.append(this.price);
      first = false;
    }
    if (isSetDiretion()) {
      if (!first) sb.append(", ");
      sb.append("diretion:");
      if (this.diretion == null) {
        sb.append("null");
      } else {
        sb.append(this.diretion);
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
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MergeComposeLegDataStandardSchemeFactory implements SchemeFactory {
    public MergeComposeLegDataStandardScheme getScheme() {
      return new MergeComposeLegDataStandardScheme();
    }
  }

  private static class MergeComposeLegDataStandardScheme extends StandardScheme<MergeComposeLegData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MergeComposeLegData struct) throws org.apache.thrift.TException {
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
          case 2: // SLED_CONTRACT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.sledContractId = iprot.readI64();
              struct.setSledContractIdIsSet(true);
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
          case 4: // PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.price = iprot.readDouble();
              struct.setPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DIRETION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.diretion = StatDirection.findByValue(iprot.readI32());
              struct.setDiretionIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MergeComposeLegData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetPositionItemId()) {
        oprot.writeFieldBegin(POSITION_ITEM_ID_FIELD_DESC);
        oprot.writeI64(struct.positionItemId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSledContractId()) {
        oprot.writeFieldBegin(SLED_CONTRACT_ID_FIELD_DESC);
        oprot.writeI64(struct.sledContractId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetQuantity()) {
        oprot.writeFieldBegin(QUANTITY_FIELD_DESC);
        oprot.writeI32(struct.quantity);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPrice()) {
        oprot.writeFieldBegin(PRICE_FIELD_DESC);
        oprot.writeDouble(struct.price);
        oprot.writeFieldEnd();
      }
      if (struct.diretion != null) {
        if (struct.isSetDiretion()) {
          oprot.writeFieldBegin(DIRETION_FIELD_DESC);
          oprot.writeI32(struct.diretion.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MergeComposeLegDataTupleSchemeFactory implements SchemeFactory {
    public MergeComposeLegDataTupleScheme getScheme() {
      return new MergeComposeLegDataTupleScheme();
    }
  }

  private static class MergeComposeLegDataTupleScheme extends TupleScheme<MergeComposeLegData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MergeComposeLegData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPositionItemId()) {
        optionals.set(0);
      }
      if (struct.isSetSledContractId()) {
        optionals.set(1);
      }
      if (struct.isSetQuantity()) {
        optionals.set(2);
      }
      if (struct.isSetPrice()) {
        optionals.set(3);
      }
      if (struct.isSetDiretion()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetPositionItemId()) {
        oprot.writeI64(struct.positionItemId);
      }
      if (struct.isSetSledContractId()) {
        oprot.writeI64(struct.sledContractId);
      }
      if (struct.isSetQuantity()) {
        oprot.writeI32(struct.quantity);
      }
      if (struct.isSetPrice()) {
        oprot.writeDouble(struct.price);
      }
      if (struct.isSetDiretion()) {
        oprot.writeI32(struct.diretion.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MergeComposeLegData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.positionItemId = iprot.readI64();
        struct.setPositionItemIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.sledContractId = iprot.readI64();
        struct.setSledContractIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.quantity = iprot.readI32();
        struct.setQuantityIsSet(true);
      }
      if (incoming.get(3)) {
        struct.price = iprot.readDouble();
        struct.setPriceIsSet(true);
      }
      if (incoming.get(4)) {
        struct.diretion = StatDirection.findByValue(iprot.readI32());
        struct.setDiretionIsSet(true);
      }
    }
  }

}

