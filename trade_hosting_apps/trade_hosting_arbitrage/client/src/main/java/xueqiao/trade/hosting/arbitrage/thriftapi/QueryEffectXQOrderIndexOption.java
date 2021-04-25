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

public class QueryEffectXQOrderIndexOption implements org.apache.thrift.TBase<QueryEffectXQOrderIndexOption, QueryEffectXQOrderIndexOption._Fields>, java.io.Serializable, Cloneable, Comparable<QueryEffectXQOrderIndexOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryEffectXQOrderIndexOption");

  private static final org.apache.thrift.protocol.TField SUB_USER_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("subUserIds", org.apache.thrift.protocol.TType.SET, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountIds", org.apache.thrift.protocol.TType.SET, (short)2);
  private static final org.apache.thrift.protocol.TField ORDER_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("orderIds", org.apache.thrift.protocol.TType.SET, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryEffectXQOrderIndexOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryEffectXQOrderIndexOptionTupleSchemeFactory());
  }

  public Set<Integer> subUserIds; // optional
  public Set<Long> subAccountIds; // optional
  public Set<String> orderIds; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_USER_IDS((short)1, "subUserIds"),
    SUB_ACCOUNT_IDS((short)2, "subAccountIds"),
    ORDER_IDS((short)3, "orderIds");

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
        case 1: // SUB_USER_IDS
          return SUB_USER_IDS;
        case 2: // SUB_ACCOUNT_IDS
          return SUB_ACCOUNT_IDS;
        case 3: // ORDER_IDS
          return ORDER_IDS;
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
  private _Fields optionals[] = {_Fields.SUB_USER_IDS,_Fields.SUB_ACCOUNT_IDS,_Fields.ORDER_IDS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_USER_IDS, new org.apache.thrift.meta_data.FieldMetaData("subUserIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    tmpMap.put(_Fields.SUB_ACCOUNT_IDS, new org.apache.thrift.meta_data.FieldMetaData("subAccountIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.ORDER_IDS, new org.apache.thrift.meta_data.FieldMetaData("orderIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryEffectXQOrderIndexOption.class, metaDataMap);
  }

  public QueryEffectXQOrderIndexOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryEffectXQOrderIndexOption(QueryEffectXQOrderIndexOption other) {
    if (other.isSetSubUserIds()) {
      Set<Integer> __this__subUserIds = new HashSet<Integer>(other.subUserIds);
      this.subUserIds = __this__subUserIds;
    }
    if (other.isSetSubAccountIds()) {
      Set<Long> __this__subAccountIds = new HashSet<Long>(other.subAccountIds);
      this.subAccountIds = __this__subAccountIds;
    }
    if (other.isSetOrderIds()) {
      Set<String> __this__orderIds = new HashSet<String>(other.orderIds);
      this.orderIds = __this__orderIds;
    }
  }

  public QueryEffectXQOrderIndexOption deepCopy() {
    return new QueryEffectXQOrderIndexOption(this);
  }

  @Override
  public void clear() {
    this.subUserIds = null;
    this.subAccountIds = null;
    this.orderIds = null;
  }

  public int getSubUserIdsSize() {
    return (this.subUserIds == null) ? 0 : this.subUserIds.size();
  }

  public java.util.Iterator<Integer> getSubUserIdsIterator() {
    return (this.subUserIds == null) ? null : this.subUserIds.iterator();
  }

  public void addToSubUserIds(int elem) {
    if (this.subUserIds == null) {
      this.subUserIds = new HashSet<Integer>();
    }
    this.subUserIds.add(elem);
  }

  public Set<Integer> getSubUserIds() {
    return this.subUserIds;
  }

  public QueryEffectXQOrderIndexOption setSubUserIds(Set<Integer> subUserIds) {
    this.subUserIds = subUserIds;
    return this;
  }

  public void unsetSubUserIds() {
    this.subUserIds = null;
  }

  /** Returns true if field subUserIds is set (has been assigned a value) and false otherwise */
  public boolean isSetSubUserIds() {
    return this.subUserIds != null;
  }

  public void setSubUserIdsIsSet(boolean value) {
    if (!value) {
      this.subUserIds = null;
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

  public QueryEffectXQOrderIndexOption setSubAccountIds(Set<Long> subAccountIds) {
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

  public int getOrderIdsSize() {
    return (this.orderIds == null) ? 0 : this.orderIds.size();
  }

  public java.util.Iterator<String> getOrderIdsIterator() {
    return (this.orderIds == null) ? null : this.orderIds.iterator();
  }

  public void addToOrderIds(String elem) {
    if (this.orderIds == null) {
      this.orderIds = new HashSet<String>();
    }
    this.orderIds.add(elem);
  }

  public Set<String> getOrderIds() {
    return this.orderIds;
  }

  public QueryEffectXQOrderIndexOption setOrderIds(Set<String> orderIds) {
    this.orderIds = orderIds;
    return this;
  }

  public void unsetOrderIds() {
    this.orderIds = null;
  }

  /** Returns true if field orderIds is set (has been assigned a value) and false otherwise */
  public boolean isSetOrderIds() {
    return this.orderIds != null;
  }

  public void setOrderIdsIsSet(boolean value) {
    if (!value) {
      this.orderIds = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUB_USER_IDS:
      if (value == null) {
        unsetSubUserIds();
      } else {
        setSubUserIds((Set<Integer>)value);
      }
      break;

    case SUB_ACCOUNT_IDS:
      if (value == null) {
        unsetSubAccountIds();
      } else {
        setSubAccountIds((Set<Long>)value);
      }
      break;

    case ORDER_IDS:
      if (value == null) {
        unsetOrderIds();
      } else {
        setOrderIds((Set<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_USER_IDS:
      return getSubUserIds();

    case SUB_ACCOUNT_IDS:
      return getSubAccountIds();

    case ORDER_IDS:
      return getOrderIds();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUB_USER_IDS:
      return isSetSubUserIds();
    case SUB_ACCOUNT_IDS:
      return isSetSubAccountIds();
    case ORDER_IDS:
      return isSetOrderIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryEffectXQOrderIndexOption)
      return this.equals((QueryEffectXQOrderIndexOption)that);
    return false;
  }

  public boolean equals(QueryEffectXQOrderIndexOption that) {
    if (that == null)
      return false;

    boolean this_present_subUserIds = true && this.isSetSubUserIds();
    boolean that_present_subUserIds = true && that.isSetSubUserIds();
    if (this_present_subUserIds || that_present_subUserIds) {
      if (!(this_present_subUserIds && that_present_subUserIds))
        return false;
      if (!this.subUserIds.equals(that.subUserIds))
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

    boolean this_present_orderIds = true && this.isSetOrderIds();
    boolean that_present_orderIds = true && that.isSetOrderIds();
    if (this_present_orderIds || that_present_orderIds) {
      if (!(this_present_orderIds && that_present_orderIds))
        return false;
      if (!this.orderIds.equals(that.orderIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryEffectXQOrderIndexOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSubUserIds()).compareTo(other.isSetSubUserIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubUserIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subUserIds, other.subUserIds);
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
    lastComparison = Boolean.valueOf(isSetOrderIds()).compareTo(other.isSetOrderIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOrderIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orderIds, other.orderIds);
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
    StringBuilder sb = new StringBuilder("QueryEffectXQOrderIndexOption(");
    boolean first = true;

    if (isSetSubUserIds()) {
      sb.append("subUserIds:");
      if (this.subUserIds == null) {
        sb.append("null");
      } else {
        sb.append(this.subUserIds);
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
    if (isSetOrderIds()) {
      if (!first) sb.append(", ");
      sb.append("orderIds:");
      if (this.orderIds == null) {
        sb.append("null");
      } else {
        sb.append(this.orderIds);
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

  private static class QueryEffectXQOrderIndexOptionStandardSchemeFactory implements SchemeFactory {
    public QueryEffectXQOrderIndexOptionStandardScheme getScheme() {
      return new QueryEffectXQOrderIndexOptionStandardScheme();
    }
  }

  private static class QueryEffectXQOrderIndexOptionStandardScheme extends StandardScheme<QueryEffectXQOrderIndexOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryEffectXQOrderIndexOption struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUB_USER_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set180 = iprot.readSetBegin();
                struct.subUserIds = new HashSet<Integer>(2*_set180.size);
                for (int _i181 = 0; _i181 < _set180.size; ++_i181)
                {
                  int _elem182;
                  _elem182 = iprot.readI32();
                  struct.subUserIds.add(_elem182);
                }
                iprot.readSetEnd();
              }
              struct.setSubUserIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SUB_ACCOUNT_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set183 = iprot.readSetBegin();
                struct.subAccountIds = new HashSet<Long>(2*_set183.size);
                for (int _i184 = 0; _i184 < _set183.size; ++_i184)
                {
                  long _elem185;
                  _elem185 = iprot.readI64();
                  struct.subAccountIds.add(_elem185);
                }
                iprot.readSetEnd();
              }
              struct.setSubAccountIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ORDER_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set186 = iprot.readSetBegin();
                struct.orderIds = new HashSet<String>(2*_set186.size);
                for (int _i187 = 0; _i187 < _set186.size; ++_i187)
                {
                  String _elem188;
                  _elem188 = iprot.readString();
                  struct.orderIds.add(_elem188);
                }
                iprot.readSetEnd();
              }
              struct.setOrderIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryEffectXQOrderIndexOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.subUserIds != null) {
        if (struct.isSetSubUserIds()) {
          oprot.writeFieldBegin(SUB_USER_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I32, struct.subUserIds.size()));
            for (int _iter189 : struct.subUserIds)
            {
              oprot.writeI32(_iter189);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.subAccountIds != null) {
        if (struct.isSetSubAccountIds()) {
          oprot.writeFieldBegin(SUB_ACCOUNT_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, struct.subAccountIds.size()));
            for (long _iter190 : struct.subAccountIds)
            {
              oprot.writeI64(_iter190);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.orderIds != null) {
        if (struct.isSetOrderIds()) {
          oprot.writeFieldBegin(ORDER_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, struct.orderIds.size()));
            for (String _iter191 : struct.orderIds)
            {
              oprot.writeString(_iter191);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryEffectXQOrderIndexOptionTupleSchemeFactory implements SchemeFactory {
    public QueryEffectXQOrderIndexOptionTupleScheme getScheme() {
      return new QueryEffectXQOrderIndexOptionTupleScheme();
    }
  }

  private static class QueryEffectXQOrderIndexOptionTupleScheme extends TupleScheme<QueryEffectXQOrderIndexOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryEffectXQOrderIndexOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubUserIds()) {
        optionals.set(0);
      }
      if (struct.isSetSubAccountIds()) {
        optionals.set(1);
      }
      if (struct.isSetOrderIds()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetSubUserIds()) {
        {
          oprot.writeI32(struct.subUserIds.size());
          for (int _iter192 : struct.subUserIds)
          {
            oprot.writeI32(_iter192);
          }
        }
      }
      if (struct.isSetSubAccountIds()) {
        {
          oprot.writeI32(struct.subAccountIds.size());
          for (long _iter193 : struct.subAccountIds)
          {
            oprot.writeI64(_iter193);
          }
        }
      }
      if (struct.isSetOrderIds()) {
        {
          oprot.writeI32(struct.orderIds.size());
          for (String _iter194 : struct.orderIds)
          {
            oprot.writeString(_iter194);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryEffectXQOrderIndexOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TSet _set195 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.subUserIds = new HashSet<Integer>(2*_set195.size);
          for (int _i196 = 0; _i196 < _set195.size; ++_i196)
          {
            int _elem197;
            _elem197 = iprot.readI32();
            struct.subUserIds.add(_elem197);
          }
        }
        struct.setSubUserIdsIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TSet _set198 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.subAccountIds = new HashSet<Long>(2*_set198.size);
          for (int _i199 = 0; _i199 < _set198.size; ++_i199)
          {
            long _elem200;
            _elem200 = iprot.readI64();
            struct.subAccountIds.add(_elem200);
          }
        }
        struct.setSubAccountIdsIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TSet _set201 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.orderIds = new HashSet<String>(2*_set201.size);
          for (int _i202 = 0; _i202 < _set201.size; ++_i202)
          {
            String _elem203;
            _elem203 = iprot.readString();
            struct.orderIds.add(_elem203);
          }
        }
        struct.setOrderIdsIsSet(true);
      }
    }
  }

}

