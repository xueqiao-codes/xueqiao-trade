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

public class HostingXQOrderWithTradeList implements org.apache.thrift.TBase<HostingXQOrderWithTradeList, HostingXQOrderWithTradeList._Fields>, java.io.Serializable, Cloneable, Comparable<HostingXQOrderWithTradeList> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingXQOrderWithTradeList");

  private static final org.apache.thrift.protocol.TField ORDER_FIELD_DESC = new org.apache.thrift.protocol.TField("order", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField TRADE_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingXQOrderWithTradeListStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingXQOrderWithTradeListTupleSchemeFactory());
  }

  public HostingXQOrder order; // optional
  public List<HostingXQTrade> tradeList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ORDER((short)1, "order"),
    TRADE_LIST((short)2, "tradeList");

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
        case 1: // ORDER
          return ORDER;
        case 2: // TRADE_LIST
          return TRADE_LIST;
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
  private _Fields optionals[] = {_Fields.ORDER,_Fields.TRADE_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ORDER, new org.apache.thrift.meta_data.FieldMetaData("order", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingXQOrder.class)));
    tmpMap.put(_Fields.TRADE_LIST, new org.apache.thrift.meta_data.FieldMetaData("tradeList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingXQTrade.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingXQOrderWithTradeList.class, metaDataMap);
  }

  public HostingXQOrderWithTradeList() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingXQOrderWithTradeList(HostingXQOrderWithTradeList other) {
    if (other.isSetOrder()) {
      this.order = new HostingXQOrder(other.order);
    }
    if (other.isSetTradeList()) {
      List<HostingXQTrade> __this__tradeList = new ArrayList<HostingXQTrade>(other.tradeList.size());
      for (HostingXQTrade other_element : other.tradeList) {
        __this__tradeList.add(new HostingXQTrade(other_element));
      }
      this.tradeList = __this__tradeList;
    }
  }

  public HostingXQOrderWithTradeList deepCopy() {
    return new HostingXQOrderWithTradeList(this);
  }

  @Override
  public void clear() {
    this.order = null;
    this.tradeList = null;
  }

  public HostingXQOrder getOrder() {
    return this.order;
  }

  public HostingXQOrderWithTradeList setOrder(HostingXQOrder order) {
    this.order = order;
    return this;
  }

  public void unsetOrder() {
    this.order = null;
  }

  /** Returns true if field order is set (has been assigned a value) and false otherwise */
  public boolean isSetOrder() {
    return this.order != null;
  }

  public void setOrderIsSet(boolean value) {
    if (!value) {
      this.order = null;
    }
  }

  public int getTradeListSize() {
    return (this.tradeList == null) ? 0 : this.tradeList.size();
  }

  public java.util.Iterator<HostingXQTrade> getTradeListIterator() {
    return (this.tradeList == null) ? null : this.tradeList.iterator();
  }

  public void addToTradeList(HostingXQTrade elem) {
    if (this.tradeList == null) {
      this.tradeList = new ArrayList<HostingXQTrade>();
    }
    this.tradeList.add(elem);
  }

  public List<HostingXQTrade> getTradeList() {
    return this.tradeList;
  }

  public HostingXQOrderWithTradeList setTradeList(List<HostingXQTrade> tradeList) {
    this.tradeList = tradeList;
    return this;
  }

  public void unsetTradeList() {
    this.tradeList = null;
  }

  /** Returns true if field tradeList is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeList() {
    return this.tradeList != null;
  }

  public void setTradeListIsSet(boolean value) {
    if (!value) {
      this.tradeList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ORDER:
      if (value == null) {
        unsetOrder();
      } else {
        setOrder((HostingXQOrder)value);
      }
      break;

    case TRADE_LIST:
      if (value == null) {
        unsetTradeList();
      } else {
        setTradeList((List<HostingXQTrade>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ORDER:
      return getOrder();

    case TRADE_LIST:
      return getTradeList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ORDER:
      return isSetOrder();
    case TRADE_LIST:
      return isSetTradeList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingXQOrderWithTradeList)
      return this.equals((HostingXQOrderWithTradeList)that);
    return false;
  }

  public boolean equals(HostingXQOrderWithTradeList that) {
    if (that == null)
      return false;

    boolean this_present_order = true && this.isSetOrder();
    boolean that_present_order = true && that.isSetOrder();
    if (this_present_order || that_present_order) {
      if (!(this_present_order && that_present_order))
        return false;
      if (!this.order.equals(that.order))
        return false;
    }

    boolean this_present_tradeList = true && this.isSetTradeList();
    boolean that_present_tradeList = true && that.isSetTradeList();
    if (this_present_tradeList || that_present_tradeList) {
      if (!(this_present_tradeList && that_present_tradeList))
        return false;
      if (!this.tradeList.equals(that.tradeList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingXQOrderWithTradeList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetOrder()).compareTo(other.isSetOrder());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrder()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.order, other.order);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTradeList()).compareTo(other.isSetTradeList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeList, other.tradeList);
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
    StringBuilder sb = new StringBuilder("HostingXQOrderWithTradeList(");
    boolean first = true;

    if (isSetOrder()) {
      sb.append("order:");
      if (this.order == null) {
        sb.append("null");
      } else {
        sb.append(this.order);
      }
      first = false;
    }
    if (isSetTradeList()) {
      if (!first) sb.append(", ");
      sb.append("tradeList:");
      if (this.tradeList == null) {
        sb.append("null");
      } else {
        sb.append(this.tradeList);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (order != null) {
      order.validate();
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

  private static class HostingXQOrderWithTradeListStandardSchemeFactory implements SchemeFactory {
    public HostingXQOrderWithTradeListStandardScheme getScheme() {
      return new HostingXQOrderWithTradeListStandardScheme();
    }
  }

  private static class HostingXQOrderWithTradeListStandardScheme extends StandardScheme<HostingXQOrderWithTradeList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingXQOrderWithTradeList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ORDER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.order = new HostingXQOrder();
              struct.order.read(iprot);
              struct.setOrderIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TRADE_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list312 = iprot.readListBegin();
                struct.tradeList = new ArrayList<HostingXQTrade>(_list312.size);
                for (int _i313 = 0; _i313 < _list312.size; ++_i313)
                {
                  HostingXQTrade _elem314;
                  _elem314 = new HostingXQTrade();
                  _elem314.read(iprot);
                  struct.tradeList.add(_elem314);
                }
                iprot.readListEnd();
              }
              struct.setTradeListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingXQOrderWithTradeList struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.order != null) {
        if (struct.isSetOrder()) {
          oprot.writeFieldBegin(ORDER_FIELD_DESC);
          struct.order.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.tradeList != null) {
        if (struct.isSetTradeList()) {
          oprot.writeFieldBegin(TRADE_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.tradeList.size()));
            for (HostingXQTrade _iter315 : struct.tradeList)
            {
              _iter315.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingXQOrderWithTradeListTupleSchemeFactory implements SchemeFactory {
    public HostingXQOrderWithTradeListTupleScheme getScheme() {
      return new HostingXQOrderWithTradeListTupleScheme();
    }
  }

  private static class HostingXQOrderWithTradeListTupleScheme extends TupleScheme<HostingXQOrderWithTradeList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingXQOrderWithTradeList struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetOrder()) {
        optionals.set(0);
      }
      if (struct.isSetTradeList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetOrder()) {
        struct.order.write(oprot);
      }
      if (struct.isSetTradeList()) {
        {
          oprot.writeI32(struct.tradeList.size());
          for (HostingXQTrade _iter316 : struct.tradeList)
          {
            _iter316.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingXQOrderWithTradeList struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.order = new HostingXQOrder();
        struct.order.read(iprot);
        struct.setOrderIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list317 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.tradeList = new ArrayList<HostingXQTrade>(_list317.size);
          for (int _i318 = 0; _i318 < _list317.size; ++_i318)
          {
            HostingXQTrade _elem319;
            _elem319 = new HostingXQTrade();
            _elem319.read(iprot);
            struct.tradeList.add(_elem319);
          }
        }
        struct.setTradeListIsSet(true);
      }
    }
  }

}

