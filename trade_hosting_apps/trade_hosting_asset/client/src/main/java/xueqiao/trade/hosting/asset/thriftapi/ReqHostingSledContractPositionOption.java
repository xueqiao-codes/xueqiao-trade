/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.asset.thriftapi;

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

public class ReqHostingSledContractPositionOption implements org.apache.thrift.TBase<ReqHostingSledContractPositionOption, ReqHostingSledContractPositionOption._Fields>, java.io.Serializable, Cloneable, Comparable<ReqHostingSledContractPositionOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ReqHostingSledContractPositionOption");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SLED_CONTRACT_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("sledContractIds", org.apache.thrift.protocol.TType.SET, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ReqHostingSledContractPositionOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ReqHostingSledContractPositionOptionTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public Set<Long> sledContractIds; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    SLED_CONTRACT_IDS((short)2, "sledContractIds");

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
        case 2: // SLED_CONTRACT_IDS
          return SLED_CONTRACT_IDS;
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
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.SLED_CONTRACT_IDS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SLED_CONTRACT_IDS, new org.apache.thrift.meta_data.FieldMetaData("sledContractIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ReqHostingSledContractPositionOption.class, metaDataMap);
  }

  public ReqHostingSledContractPositionOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ReqHostingSledContractPositionOption(ReqHostingSledContractPositionOption other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    if (other.isSetSledContractIds()) {
      Set<Long> __this__sledContractIds = new HashSet<Long>(other.sledContractIds);
      this.sledContractIds = __this__sledContractIds;
    }
  }

  public ReqHostingSledContractPositionOption deepCopy() {
    return new ReqHostingSledContractPositionOption(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.sledContractIds = null;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public ReqHostingSledContractPositionOption setSubAccountId(long subAccountId) {
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

  public int getSledContractIdsSize() {
    return (this.sledContractIds == null) ? 0 : this.sledContractIds.size();
  }

  public java.util.Iterator<Long> getSledContractIdsIterator() {
    return (this.sledContractIds == null) ? null : this.sledContractIds.iterator();
  }

  public void addToSledContractIds(long elem) {
    if (this.sledContractIds == null) {
      this.sledContractIds = new HashSet<Long>();
    }
    this.sledContractIds.add(elem);
  }

  public Set<Long> getSledContractIds() {
    return this.sledContractIds;
  }

  public ReqHostingSledContractPositionOption setSledContractIds(Set<Long> sledContractIds) {
    this.sledContractIds = sledContractIds;
    return this;
  }

  public void unsetSledContractIds() {
    this.sledContractIds = null;
  }

  /** Returns true if field sledContractIds is set (has been assigned a value) and false otherwise */
  public boolean isSetSledContractIds() {
    return this.sledContractIds != null;
  }

  public void setSledContractIdsIsSet(boolean value) {
    if (!value) {
      this.sledContractIds = null;
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

    case SLED_CONTRACT_IDS:
      if (value == null) {
        unsetSledContractIds();
      } else {
        setSledContractIds((Set<Long>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case SLED_CONTRACT_IDS:
      return getSledContractIds();

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
    case SLED_CONTRACT_IDS:
      return isSetSledContractIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ReqHostingSledContractPositionOption)
      return this.equals((ReqHostingSledContractPositionOption)that);
    return false;
  }

  public boolean equals(ReqHostingSledContractPositionOption that) {
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

    boolean this_present_sledContractIds = true && this.isSetSledContractIds();
    boolean that_present_sledContractIds = true && that.isSetSledContractIds();
    if (this_present_sledContractIds || that_present_sledContractIds) {
      if (!(this_present_sledContractIds && that_present_sledContractIds))
        return false;
      if (!this.sledContractIds.equals(that.sledContractIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ReqHostingSledContractPositionOption other) {
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
    lastComparison = Boolean.valueOf(isSetSledContractIds()).compareTo(other.isSetSledContractIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledContractIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledContractIds, other.sledContractIds);
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
    StringBuilder sb = new StringBuilder("ReqHostingSledContractPositionOption(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetSledContractIds()) {
      if (!first) sb.append(", ");
      sb.append("sledContractIds:");
      if (this.sledContractIds == null) {
        sb.append("null");
      } else {
        sb.append(this.sledContractIds);
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

  private static class ReqHostingSledContractPositionOptionStandardSchemeFactory implements SchemeFactory {
    public ReqHostingSledContractPositionOptionStandardScheme getScheme() {
      return new ReqHostingSledContractPositionOptionStandardScheme();
    }
  }

  private static class ReqHostingSledContractPositionOptionStandardScheme extends StandardScheme<ReqHostingSledContractPositionOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ReqHostingSledContractPositionOption struct) throws org.apache.thrift.TException {
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
          case 2: // SLED_CONTRACT_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set8 = iprot.readSetBegin();
                struct.sledContractIds = new HashSet<Long>(2*_set8.size);
                for (int _i9 = 0; _i9 < _set8.size; ++_i9)
                {
                  long _elem10;
                  _elem10 = iprot.readI64();
                  struct.sledContractIds.add(_elem10);
                }
                iprot.readSetEnd();
              }
              struct.setSledContractIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ReqHostingSledContractPositionOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.sledContractIds != null) {
        if (struct.isSetSledContractIds()) {
          oprot.writeFieldBegin(SLED_CONTRACT_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, struct.sledContractIds.size()));
            for (long _iter11 : struct.sledContractIds)
            {
              oprot.writeI64(_iter11);
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

  private static class ReqHostingSledContractPositionOptionTupleSchemeFactory implements SchemeFactory {
    public ReqHostingSledContractPositionOptionTupleScheme getScheme() {
      return new ReqHostingSledContractPositionOptionTupleScheme();
    }
  }

  private static class ReqHostingSledContractPositionOptionTupleScheme extends TupleScheme<ReqHostingSledContractPositionOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ReqHostingSledContractPositionOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetSledContractIds()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetSledContractIds()) {
        {
          oprot.writeI32(struct.sledContractIds.size());
          for (long _iter12 : struct.sledContractIds)
          {
            oprot.writeI64(_iter12);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ReqHostingSledContractPositionOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TSet _set13 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.sledContractIds = new HashSet<Long>(2*_set13.size);
          for (int _i14 = 0; _i14 < _set13.size; ++_i14)
          {
            long _elem15;
            _elem15 = iprot.readI64();
            struct.sledContractIds.add(_elem15);
          }
        }
        struct.setSledContractIdsIsSet(true);
      }
    }
  }

}

