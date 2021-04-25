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

public class BatchClosedPositionReq implements org.apache.thrift.TBase<BatchClosedPositionReq, BatchClosedPositionReq._Fields>, java.io.Serializable, Cloneable, Comparable<BatchClosedPositionReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("BatchClosedPositionReq");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField TARGET_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("targetKey", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CLOSED_POSITION_DETAIL_ITEMS_FIELD_DESC = new org.apache.thrift.protocol.TField("closedPositionDetailItems", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField TARGET_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("targetType", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new BatchClosedPositionReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new BatchClosedPositionReqTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public String targetKey; // optional
  public List<ClosedPositionDetailItem> closedPositionDetailItems; // optional
  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    TARGET_KEY((short)2, "targetKey"),
    CLOSED_POSITION_DETAIL_ITEMS((short)3, "closedPositionDetailItems"),
    /**
     * 
     * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
     */
    TARGET_TYPE((short)4, "targetType");

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
        case 1: // SUB_ACCOUNT_ID
          return SUB_ACCOUNT_ID;
        case 2: // TARGET_KEY
          return TARGET_KEY;
        case 3: // CLOSED_POSITION_DETAIL_ITEMS
          return CLOSED_POSITION_DETAIL_ITEMS;
        case 4: // TARGET_TYPE
          return TARGET_TYPE;
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
  private static final int __SUBACCOUNTID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.TARGET_KEY,_Fields.CLOSED_POSITION_DETAIL_ITEMS,_Fields.TARGET_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TARGET_KEY, new org.apache.thrift.meta_data.FieldMetaData("targetKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CLOSED_POSITION_DETAIL_ITEMS, new org.apache.thrift.meta_data.FieldMetaData("closedPositionDetailItems", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ClosedPositionDetailItem.class))));
    tmpMap.put(_Fields.TARGET_TYPE, new org.apache.thrift.meta_data.FieldMetaData("targetType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(BatchClosedPositionReq.class, metaDataMap);
  }

  public BatchClosedPositionReq() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public BatchClosedPositionReq(BatchClosedPositionReq other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    if (other.isSetTargetKey()) {
      this.targetKey = other.targetKey;
    }
    if (other.isSetClosedPositionDetailItems()) {
      List<ClosedPositionDetailItem> __this__closedPositionDetailItems = new ArrayList<ClosedPositionDetailItem>(other.closedPositionDetailItems.size());
      for (ClosedPositionDetailItem other_element : other.closedPositionDetailItems) {
        __this__closedPositionDetailItems.add(new ClosedPositionDetailItem(other_element));
      }
      this.closedPositionDetailItems = __this__closedPositionDetailItems;
    }
    if (other.isSetTargetType()) {
      this.targetType = other.targetType;
    }
  }

  public BatchClosedPositionReq deepCopy() {
    return new BatchClosedPositionReq(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.targetKey = null;
    this.closedPositionDetailItems = null;
    this.targetType = null;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public BatchClosedPositionReq setSubAccountId(long subAccountId) {
    this.subAccountId = subAccountId;
    setSubAccountIdIsSet(true);
    return this;
  }

  public void unsetSubAccountId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SUBACCOUNTID_ISSET_ID);
  }

  /** Returns true if field subAccountId is set (has been assigned a value) and false otherwise */
  public boolean isSetSubAccountId() {
    return EncodingUtils.testBit(__isset_bitfield, __SUBACCOUNTID_ISSET_ID);
  }

  public void setSubAccountIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SUBACCOUNTID_ISSET_ID, value);
  }

  public String getTargetKey() {
    return this.targetKey;
  }

  public BatchClosedPositionReq setTargetKey(String targetKey) {
    this.targetKey = targetKey;
    return this;
  }

  public void unsetTargetKey() {
    this.targetKey = null;
  }

  /** Returns true if field targetKey is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetKey() {
    return this.targetKey != null;
  }

  public void setTargetKeyIsSet(boolean value) {
    if (!value) {
      this.targetKey = null;
    }
  }

  public int getClosedPositionDetailItemsSize() {
    return (this.closedPositionDetailItems == null) ? 0 : this.closedPositionDetailItems.size();
  }

  public java.util.Iterator<ClosedPositionDetailItem> getClosedPositionDetailItemsIterator() {
    return (this.closedPositionDetailItems == null) ? null : this.closedPositionDetailItems.iterator();
  }

  public void addToClosedPositionDetailItems(ClosedPositionDetailItem elem) {
    if (this.closedPositionDetailItems == null) {
      this.closedPositionDetailItems = new ArrayList<ClosedPositionDetailItem>();
    }
    this.closedPositionDetailItems.add(elem);
  }

  public List<ClosedPositionDetailItem> getClosedPositionDetailItems() {
    return this.closedPositionDetailItems;
  }

  public BatchClosedPositionReq setClosedPositionDetailItems(List<ClosedPositionDetailItem> closedPositionDetailItems) {
    this.closedPositionDetailItems = closedPositionDetailItems;
    return this;
  }

  public void unsetClosedPositionDetailItems() {
    this.closedPositionDetailItems = null;
  }

  /** Returns true if field closedPositionDetailItems is set (has been assigned a value) and false otherwise */
  public boolean isSetClosedPositionDetailItems() {
    return this.closedPositionDetailItems != null;
  }

  public void setClosedPositionDetailItemsIsSet(boolean value) {
    if (!value) {
      this.closedPositionDetailItems = null;
    }
  }

  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType getTargetType() {
    return this.targetType;
  }

  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public BatchClosedPositionReq setTargetType(xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) {
    this.targetType = targetType;
    return this;
  }

  public void unsetTargetType() {
    this.targetType = null;
  }

  /** Returns true if field targetType is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetType() {
    return this.targetType != null;
  }

  public void setTargetTypeIsSet(boolean value) {
    if (!value) {
      this.targetType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      if (value == null) {
        unsetSubAccountId();
      } else {
        setSubAccountId((Long)value);
      }
      break;

    case TARGET_KEY:
      if (value == null) {
        unsetTargetKey();
      } else {
        setTargetKey((String)value);
      }
      break;

    case CLOSED_POSITION_DETAIL_ITEMS:
      if (value == null) {
        unsetClosedPositionDetailItems();
      } else {
        setClosedPositionDetailItems((List<ClosedPositionDetailItem>)value);
      }
      break;

    case TARGET_TYPE:
      if (value == null) {
        unsetTargetType();
      } else {
        setTargetType((xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case TARGET_KEY:
      return getTargetKey();

    case CLOSED_POSITION_DETAIL_ITEMS:
      return getClosedPositionDetailItems();

    case TARGET_TYPE:
      return getTargetType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUB_ACCOUNT_ID:
      return isSetSubAccountId();
    case TARGET_KEY:
      return isSetTargetKey();
    case CLOSED_POSITION_DETAIL_ITEMS:
      return isSetClosedPositionDetailItems();
    case TARGET_TYPE:
      return isSetTargetType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof BatchClosedPositionReq)
      return this.equals((BatchClosedPositionReq)that);
    return false;
  }

  public boolean equals(BatchClosedPositionReq that) {
    if (that == null)
      return false;

    boolean this_present_subAccountId = true && this.isSetSubAccountId();
    boolean that_present_subAccountId = true && that.isSetSubAccountId();
    if (this_present_subAccountId || that_present_subAccountId) {
      if (!(this_present_subAccountId && that_present_subAccountId))
        return false;
      if (this.subAccountId != that.subAccountId)
        return false;
    }

    boolean this_present_targetKey = true && this.isSetTargetKey();
    boolean that_present_targetKey = true && that.isSetTargetKey();
    if (this_present_targetKey || that_present_targetKey) {
      if (!(this_present_targetKey && that_present_targetKey))
        return false;
      if (!this.targetKey.equals(that.targetKey))
        return false;
    }

    boolean this_present_closedPositionDetailItems = true && this.isSetClosedPositionDetailItems();
    boolean that_present_closedPositionDetailItems = true && that.isSetClosedPositionDetailItems();
    if (this_present_closedPositionDetailItems || that_present_closedPositionDetailItems) {
      if (!(this_present_closedPositionDetailItems && that_present_closedPositionDetailItems))
        return false;
      if (!this.closedPositionDetailItems.equals(that.closedPositionDetailItems))
        return false;
    }

    boolean this_present_targetType = true && this.isSetTargetType();
    boolean that_present_targetType = true && that.isSetTargetType();
    if (this_present_targetType || that_present_targetType) {
      if (!(this_present_targetType && that_present_targetType))
        return false;
      if (!this.targetType.equals(that.targetType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(BatchClosedPositionReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSubAccountId()).compareTo(other.isSetSubAccountId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubAccountId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subAccountId, other.subAccountId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTargetKey()).compareTo(other.isSetTargetKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetKey, other.targetKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClosedPositionDetailItems()).compareTo(other.isSetClosedPositionDetailItems());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClosedPositionDetailItems()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.closedPositionDetailItems, other.closedPositionDetailItems);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTargetType()).compareTo(other.isSetTargetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetType, other.targetType);
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
    StringBuilder sb = new StringBuilder("BatchClosedPositionReq(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetTargetKey()) {
      if (!first) sb.append(", ");
      sb.append("targetKey:");
      if (this.targetKey == null) {
        sb.append("null");
      } else {
        sb.append(this.targetKey);
      }
      first = false;
    }
    if (isSetClosedPositionDetailItems()) {
      if (!first) sb.append(", ");
      sb.append("closedPositionDetailItems:");
      if (this.closedPositionDetailItems == null) {
        sb.append("null");
      } else {
        sb.append(this.closedPositionDetailItems);
      }
      first = false;
    }
    if (isSetTargetType()) {
      if (!first) sb.append(", ");
      sb.append("targetType:");
      if (this.targetType == null) {
        sb.append("null");
      } else {
        sb.append(this.targetType);
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

  private static class BatchClosedPositionReqStandardSchemeFactory implements SchemeFactory {
    public BatchClosedPositionReqStandardScheme getScheme() {
      return new BatchClosedPositionReqStandardScheme();
    }
  }

  private static class BatchClosedPositionReqStandardScheme extends StandardScheme<BatchClosedPositionReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, BatchClosedPositionReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUB_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.subAccountId = iprot.readI64();
              struct.setSubAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TARGET_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.targetKey = iprot.readString();
              struct.setTargetKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CLOSED_POSITION_DETAIL_ITEMS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list108 = iprot.readListBegin();
                struct.closedPositionDetailItems = new ArrayList<ClosedPositionDetailItem>(_list108.size);
                for (int _i109 = 0; _i109 < _list108.size; ++_i109)
                {
                  ClosedPositionDetailItem _elem110;
                  _elem110 = new ClosedPositionDetailItem();
                  _elem110.read(iprot);
                  struct.closedPositionDetailItems.add(_elem110);
                }
                iprot.readListEnd();
              }
              struct.setClosedPositionDetailItemsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TARGET_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.targetType = xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.findByValue(iprot.readI32());
              struct.setTargetTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, BatchClosedPositionReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.targetKey != null) {
        if (struct.isSetTargetKey()) {
          oprot.writeFieldBegin(TARGET_KEY_FIELD_DESC);
          oprot.writeString(struct.targetKey);
          oprot.writeFieldEnd();
        }
      }
      if (struct.closedPositionDetailItems != null) {
        if (struct.isSetClosedPositionDetailItems()) {
          oprot.writeFieldBegin(CLOSED_POSITION_DETAIL_ITEMS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.closedPositionDetailItems.size()));
            for (ClosedPositionDetailItem _iter111 : struct.closedPositionDetailItems)
            {
              _iter111.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.targetType != null) {
        if (struct.isSetTargetType()) {
          oprot.writeFieldBegin(TARGET_TYPE_FIELD_DESC);
          oprot.writeI32(struct.targetType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class BatchClosedPositionReqTupleSchemeFactory implements SchemeFactory {
    public BatchClosedPositionReqTupleScheme getScheme() {
      return new BatchClosedPositionReqTupleScheme();
    }
  }

  private static class BatchClosedPositionReqTupleScheme extends TupleScheme<BatchClosedPositionReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, BatchClosedPositionReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetTargetKey()) {
        optionals.set(1);
      }
      if (struct.isSetClosedPositionDetailItems()) {
        optionals.set(2);
      }
      if (struct.isSetTargetType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetTargetKey()) {
        oprot.writeString(struct.targetKey);
      }
      if (struct.isSetClosedPositionDetailItems()) {
        {
          oprot.writeI32(struct.closedPositionDetailItems.size());
          for (ClosedPositionDetailItem _iter112 : struct.closedPositionDetailItems)
          {
            _iter112.write(oprot);
          }
        }
      }
      if (struct.isSetTargetType()) {
        oprot.writeI32(struct.targetType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, BatchClosedPositionReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.targetKey = iprot.readString();
        struct.setTargetKeyIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list113 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.closedPositionDetailItems = new ArrayList<ClosedPositionDetailItem>(_list113.size);
          for (int _i114 = 0; _i114 < _list113.size; ++_i114)
          {
            ClosedPositionDetailItem _elem115;
            _elem115 = new ClosedPositionDetailItem();
            _elem115.read(iprot);
            struct.closedPositionDetailItems.add(_elem115);
          }
        }
        struct.setClosedPositionDetailItemsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.targetType = xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.findByValue(iprot.readI32());
        struct.setTargetTypeIsSet(true);
      }
    }
  }

}

