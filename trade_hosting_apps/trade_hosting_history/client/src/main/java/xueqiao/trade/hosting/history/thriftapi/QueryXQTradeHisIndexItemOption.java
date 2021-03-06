/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.history.thriftapi;

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

public class QueryXQTradeHisIndexItemOption implements org.apache.thrift.TBase<QueryXQTradeHisIndexItemOption, QueryXQTradeHisIndexItemOption._Fields>, java.io.Serializable, Cloneable, Comparable<QueryXQTradeHisIndexItemOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryXQTradeHisIndexItemOption");

  private static final org.apache.thrift.protocol.TField TRADE_CREATE_TIME_PERIOD_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeCreateTimePeriod", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountIds", org.apache.thrift.protocol.TType.SET, (short)2);
  private static final org.apache.thrift.protocol.TField TRADE_TARGETS_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeTargets", org.apache.thrift.protocol.TType.SET, (short)3);
  private static final org.apache.thrift.protocol.TField ITEM_ORDER_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("itemOrderType", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryXQTradeHisIndexItemOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryXQTradeHisIndexItemOptionTupleSchemeFactory());
  }

  public QueryTimePeriod tradeCreateTimePeriod; // optional
  public Set<Long> subAccountIds; // optional
  public Set<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget> tradeTargets; // optional
  /**
   * 
   * @see QueryXQTradeHisIndexItemOrderType
   */
  public QueryXQTradeHisIndexItemOrderType itemOrderType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRADE_CREATE_TIME_PERIOD((short)1, "tradeCreateTimePeriod"),
    SUB_ACCOUNT_IDS((short)2, "subAccountIds"),
    TRADE_TARGETS((short)3, "tradeTargets"),
    /**
     * 
     * @see QueryXQTradeHisIndexItemOrderType
     */
    ITEM_ORDER_TYPE((short)4, "itemOrderType");

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
        case 1: // TRADE_CREATE_TIME_PERIOD
          return TRADE_CREATE_TIME_PERIOD;
        case 2: // SUB_ACCOUNT_IDS
          return SUB_ACCOUNT_IDS;
        case 3: // TRADE_TARGETS
          return TRADE_TARGETS;
        case 4: // ITEM_ORDER_TYPE
          return ITEM_ORDER_TYPE;
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
  private _Fields optionals[] = {_Fields.TRADE_CREATE_TIME_PERIOD,_Fields.SUB_ACCOUNT_IDS,_Fields.TRADE_TARGETS,_Fields.ITEM_ORDER_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRADE_CREATE_TIME_PERIOD, new org.apache.thrift.meta_data.FieldMetaData("tradeCreateTimePeriod", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, QueryTimePeriod.class)));
    tmpMap.put(_Fields.SUB_ACCOUNT_IDS, new org.apache.thrift.meta_data.FieldMetaData("subAccountIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.TRADE_TARGETS, new org.apache.thrift.meta_data.FieldMetaData("tradeTargets", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget.class))));
    tmpMap.put(_Fields.ITEM_ORDER_TYPE, new org.apache.thrift.meta_data.FieldMetaData("itemOrderType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, QueryXQTradeHisIndexItemOrderType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryXQTradeHisIndexItemOption.class, metaDataMap);
  }

  public QueryXQTradeHisIndexItemOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryXQTradeHisIndexItemOption(QueryXQTradeHisIndexItemOption other) {
    if (other.isSetTradeCreateTimePeriod()) {
      this.tradeCreateTimePeriod = new QueryTimePeriod(other.tradeCreateTimePeriod);
    }
    if (other.isSetSubAccountIds()) {
      Set<Long> __this__subAccountIds = new HashSet<Long>(other.subAccountIds);
      this.subAccountIds = __this__subAccountIds;
    }
    if (other.isSetTradeTargets()) {
      Set<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget> __this__tradeTargets = new HashSet<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget>(other.tradeTargets.size());
      for (xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget other_element : other.tradeTargets) {
        __this__tradeTargets.add(new xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget(other_element));
      }
      this.tradeTargets = __this__tradeTargets;
    }
    if (other.isSetItemOrderType()) {
      this.itemOrderType = other.itemOrderType;
    }
  }

  public QueryXQTradeHisIndexItemOption deepCopy() {
    return new QueryXQTradeHisIndexItemOption(this);
  }

  @Override
  public void clear() {
    this.tradeCreateTimePeriod = null;
    this.subAccountIds = null;
    this.tradeTargets = null;
    this.itemOrderType = null;
  }

  public QueryTimePeriod getTradeCreateTimePeriod() {
    return this.tradeCreateTimePeriod;
  }

  public QueryXQTradeHisIndexItemOption setTradeCreateTimePeriod(QueryTimePeriod tradeCreateTimePeriod) {
    this.tradeCreateTimePeriod = tradeCreateTimePeriod;
    return this;
  }

  public void unsetTradeCreateTimePeriod() {
    this.tradeCreateTimePeriod = null;
  }

  /** Returns true if field tradeCreateTimePeriod is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeCreateTimePeriod() {
    return this.tradeCreateTimePeriod != null;
  }

  public void setTradeCreateTimePeriodIsSet(boolean value) {
    if (!value) {
      this.tradeCreateTimePeriod = null;
    }
  }

  public int getSubAccountIdsSize() {
    return (this.subAccountIds == null) ? 0 : this.subAccountIds.size();
  }

  public java.util.Iterator<Long> getSubAccountIdsIterator() {
    return (this.subAccountIds == null) ? null : this.subAccountIds.iterator();
  }

  public void addToSubAccountIds(long elem) {
    if (this.subAccountIds == null) {
      this.subAccountIds = new HashSet<Long>();
    }
    this.subAccountIds.add(elem);
  }

  public Set<Long> getSubAccountIds() {
    return this.subAccountIds;
  }

  public QueryXQTradeHisIndexItemOption setSubAccountIds(Set<Long> subAccountIds) {
    this.subAccountIds = subAccountIds;
    return this;
  }

  public void unsetSubAccountIds() {
    this.subAccountIds = null;
  }

  /** Returns true if field subAccountIds is set (has been assigned a value) and false otherwise */
  public boolean isSetSubAccountIds() {
    return this.subAccountIds != null;
  }

  public void setSubAccountIdsIsSet(boolean value) {
    if (!value) {
      this.subAccountIds = null;
    }
  }

  public int getTradeTargetsSize() {
    return (this.tradeTargets == null) ? 0 : this.tradeTargets.size();
  }

  public java.util.Iterator<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget> getTradeTargetsIterator() {
    return (this.tradeTargets == null) ? null : this.tradeTargets.iterator();
  }

  public void addToTradeTargets(xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget elem) {
    if (this.tradeTargets == null) {
      this.tradeTargets = new HashSet<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget>();
    }
    this.tradeTargets.add(elem);
  }

  public Set<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget> getTradeTargets() {
    return this.tradeTargets;
  }

  public QueryXQTradeHisIndexItemOption setTradeTargets(Set<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget> tradeTargets) {
    this.tradeTargets = tradeTargets;
    return this;
  }

  public void unsetTradeTargets() {
    this.tradeTargets = null;
  }

  /** Returns true if field tradeTargets is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeTargets() {
    return this.tradeTargets != null;
  }

  public void setTradeTargetsIsSet(boolean value) {
    if (!value) {
      this.tradeTargets = null;
    }
  }

  /**
   * 
   * @see QueryXQTradeHisIndexItemOrderType
   */
  public QueryXQTradeHisIndexItemOrderType getItemOrderType() {
    return this.itemOrderType;
  }

  /**
   * 
   * @see QueryXQTradeHisIndexItemOrderType
   */
  public QueryXQTradeHisIndexItemOption setItemOrderType(QueryXQTradeHisIndexItemOrderType itemOrderType) {
    this.itemOrderType = itemOrderType;
    return this;
  }

  public void unsetItemOrderType() {
    this.itemOrderType = null;
  }

  /** Returns true if field itemOrderType is set (has been assigned a value) and false otherwise */
  public boolean isSetItemOrderType() {
    return this.itemOrderType != null;
  }

  public void setItemOrderTypeIsSet(boolean value) {
    if (!value) {
      this.itemOrderType = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TRADE_CREATE_TIME_PERIOD:
      if (value == null) {
        unsetTradeCreateTimePeriod();
      } else {
        setTradeCreateTimePeriod((QueryTimePeriod)value);
      }
      break;

    case SUB_ACCOUNT_IDS:
      if (value == null) {
        unsetSubAccountIds();
      } else {
        setSubAccountIds((Set<Long>)value);
      }
      break;

    case TRADE_TARGETS:
      if (value == null) {
        unsetTradeTargets();
      } else {
        setTradeTargets((Set<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget>)value);
      }
      break;

    case ITEM_ORDER_TYPE:
      if (value == null) {
        unsetItemOrderType();
      } else {
        setItemOrderType((QueryXQTradeHisIndexItemOrderType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRADE_CREATE_TIME_PERIOD:
      return getTradeCreateTimePeriod();

    case SUB_ACCOUNT_IDS:
      return getSubAccountIds();

    case TRADE_TARGETS:
      return getTradeTargets();

    case ITEM_ORDER_TYPE:
      return getItemOrderType();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TRADE_CREATE_TIME_PERIOD:
      return isSetTradeCreateTimePeriod();
    case SUB_ACCOUNT_IDS:
      return isSetSubAccountIds();
    case TRADE_TARGETS:
      return isSetTradeTargets();
    case ITEM_ORDER_TYPE:
      return isSetItemOrderType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryXQTradeHisIndexItemOption)
      return this.equals((QueryXQTradeHisIndexItemOption)that);
    return false;
  }

  public boolean equals(QueryXQTradeHisIndexItemOption that) {
    if (that == null)
      return false;

    boolean this_present_tradeCreateTimePeriod = true && this.isSetTradeCreateTimePeriod();
    boolean that_present_tradeCreateTimePeriod = true && that.isSetTradeCreateTimePeriod();
    if (this_present_tradeCreateTimePeriod || that_present_tradeCreateTimePeriod) {
      if (!(this_present_tradeCreateTimePeriod && that_present_tradeCreateTimePeriod))
        return false;
      if (!this.tradeCreateTimePeriod.equals(that.tradeCreateTimePeriod))
        return false;
    }

    boolean this_present_subAccountIds = true && this.isSetSubAccountIds();
    boolean that_present_subAccountIds = true && that.isSetSubAccountIds();
    if (this_present_subAccountIds || that_present_subAccountIds) {
      if (!(this_present_subAccountIds && that_present_subAccountIds))
        return false;
      if (!this.subAccountIds.equals(that.subAccountIds))
        return false;
    }

    boolean this_present_tradeTargets = true && this.isSetTradeTargets();
    boolean that_present_tradeTargets = true && that.isSetTradeTargets();
    if (this_present_tradeTargets || that_present_tradeTargets) {
      if (!(this_present_tradeTargets && that_present_tradeTargets))
        return false;
      if (!this.tradeTargets.equals(that.tradeTargets))
        return false;
    }

    boolean this_present_itemOrderType = true && this.isSetItemOrderType();
    boolean that_present_itemOrderType = true && that.isSetItemOrderType();
    if (this_present_itemOrderType || that_present_itemOrderType) {
      if (!(this_present_itemOrderType && that_present_itemOrderType))
        return false;
      if (!this.itemOrderType.equals(that.itemOrderType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryXQTradeHisIndexItemOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTradeCreateTimePeriod()).compareTo(other.isSetTradeCreateTimePeriod());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeCreateTimePeriod()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeCreateTimePeriod, other.tradeCreateTimePeriod);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubAccountIds()).compareTo(other.isSetSubAccountIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubAccountIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subAccountIds, other.subAccountIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTradeTargets()).compareTo(other.isSetTradeTargets());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeTargets()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeTargets, other.tradeTargets);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetItemOrderType()).compareTo(other.isSetItemOrderType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetItemOrderType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.itemOrderType, other.itemOrderType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("QueryXQTradeHisIndexItemOption(");
    boolean first = true;

    if (isSetTradeCreateTimePeriod()) {
      sb.append("tradeCreateTimePeriod:");
      if (this.tradeCreateTimePeriod == null) {
        sb.append("null");
      } else {
        sb.append(this.tradeCreateTimePeriod);
      }
      first = false;
    }
    if (isSetSubAccountIds()) {
      if (!first) sb.append(", ");
      sb.append("subAccountIds:");
      if (this.subAccountIds == null) {
        sb.append("null");
      } else {
        sb.append(this.subAccountIds);
      }
      first = false;
    }
    if (isSetTradeTargets()) {
      if (!first) sb.append(", ");
      sb.append("tradeTargets:");
      if (this.tradeTargets == null) {
        sb.append("null");
      } else {
        sb.append(this.tradeTargets);
      }
      first = false;
    }
    if (isSetItemOrderType()) {
      if (!first) sb.append(", ");
      sb.append("itemOrderType:");
      if (this.itemOrderType == null) {
        sb.append("null");
      } else {
        sb.append(this.itemOrderType);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
    // check for sub-struct validity
    if (tradeCreateTimePeriod != null) {
      tradeCreateTimePeriod.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class QueryXQTradeHisIndexItemOptionStandardSchemeFactory implements SchemeFactory {
    public QueryXQTradeHisIndexItemOptionStandardScheme getScheme() {
      return new QueryXQTradeHisIndexItemOptionStandardScheme();
    }
  }

  private static class QueryXQTradeHisIndexItemOptionStandardScheme extends StandardScheme<QueryXQTradeHisIndexItemOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryXQTradeHisIndexItemOption struct) throws TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TRADE_CREATE_TIME_PERIOD
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.tradeCreateTimePeriod = new QueryTimePeriod();
              struct.tradeCreateTimePeriod.read(iprot);
              struct.setTradeCreateTimePeriodIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SUB_ACCOUNT_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set64 = iprot.readSetBegin();
                struct.subAccountIds = new HashSet<Long>(2*_set64.size);
                for (int _i65 = 0; _i65 < _set64.size; ++_i65)
                {
                  long _elem66;
                  _elem66 = iprot.readI64();
                  struct.subAccountIds.add(_elem66);
                }
                iprot.readSetEnd();
              }
              struct.setSubAccountIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TRADE_TARGETS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set67 = iprot.readSetBegin();
                struct.tradeTargets = new HashSet<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget>(2*_set67.size);
                for (int _i68 = 0; _i68 < _set67.size; ++_i68)
                {
                  xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget _elem69;
                  _elem69 = new xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget();
                  _elem69.read(iprot);
                  struct.tradeTargets.add(_elem69);
                }
                iprot.readSetEnd();
              }
              struct.setTradeTargetsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ITEM_ORDER_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.itemOrderType = QueryXQTradeHisIndexItemOrderType.findByValue(iprot.readI32());
              struct.setItemOrderTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryXQTradeHisIndexItemOption struct) throws TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tradeCreateTimePeriod != null) {
        if (struct.isSetTradeCreateTimePeriod()) {
          oprot.writeFieldBegin(TRADE_CREATE_TIME_PERIOD_FIELD_DESC);
          struct.tradeCreateTimePeriod.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.subAccountIds != null) {
        if (struct.isSetSubAccountIds()) {
          oprot.writeFieldBegin(SUB_ACCOUNT_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, struct.subAccountIds.size()));
            for (long _iter70 : struct.subAccountIds)
            {
              oprot.writeI64(_iter70);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.tradeTargets != null) {
        if (struct.isSetTradeTargets()) {
          oprot.writeFieldBegin(TRADE_TARGETS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, struct.tradeTargets.size()));
            for (xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget _iter71 : struct.tradeTargets)
            {
              _iter71.write(oprot);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.itemOrderType != null) {
        if (struct.isSetItemOrderType()) {
          oprot.writeFieldBegin(ITEM_ORDER_TYPE_FIELD_DESC);
          oprot.writeI32(struct.itemOrderType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryXQTradeHisIndexItemOptionTupleSchemeFactory implements SchemeFactory {
    public QueryXQTradeHisIndexItemOptionTupleScheme getScheme() {
      return new QueryXQTradeHisIndexItemOptionTupleScheme();
    }
  }

  private static class QueryXQTradeHisIndexItemOptionTupleScheme extends TupleScheme<QueryXQTradeHisIndexItemOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryXQTradeHisIndexItemOption struct) throws TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTradeCreateTimePeriod()) {
        optionals.set(0);
      }
      if (struct.isSetSubAccountIds()) {
        optionals.set(1);
      }
      if (struct.isSetTradeTargets()) {
        optionals.set(2);
      }
      if (struct.isSetItemOrderType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTradeCreateTimePeriod()) {
        struct.tradeCreateTimePeriod.write(oprot);
      }
      if (struct.isSetSubAccountIds()) {
        {
          oprot.writeI32(struct.subAccountIds.size());
          for (long _iter72 : struct.subAccountIds)
          {
            oprot.writeI64(_iter72);
          }
        }
      }
      if (struct.isSetTradeTargets()) {
        {
          oprot.writeI32(struct.tradeTargets.size());
          for (xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget _iter73 : struct.tradeTargets)
          {
            _iter73.write(oprot);
          }
        }
      }
      if (struct.isSetItemOrderType()) {
        oprot.writeI32(struct.itemOrderType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryXQTradeHisIndexItemOption struct) throws TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.tradeCreateTimePeriod = new QueryTimePeriod();
        struct.tradeCreateTimePeriod.read(iprot);
        struct.setTradeCreateTimePeriodIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TSet _set74 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.subAccountIds = new HashSet<Long>(2*_set74.size);
          for (int _i75 = 0; _i75 < _set74.size; ++_i75)
          {
            long _elem76;
            _elem76 = iprot.readI64();
            struct.subAccountIds.add(_elem76);
          }
        }
        struct.setSubAccountIdsIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TSet _set77 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.tradeTargets = new HashSet<xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget>(2*_set77.size);
          for (int _i78 = 0; _i78 < _set77.size; ++_i78)
          {
            xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget _elem79;
            _elem79 = new xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget();
            _elem79.read(iprot);
            struct.tradeTargets.add(_elem79);
          }
        }
        struct.setTradeTargetsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.itemOrderType = QueryXQTradeHisIndexItemOrderType.findByValue(iprot.readI32());
        struct.setItemOrderTypeIsSet(true);
      }
    }
  }

}

