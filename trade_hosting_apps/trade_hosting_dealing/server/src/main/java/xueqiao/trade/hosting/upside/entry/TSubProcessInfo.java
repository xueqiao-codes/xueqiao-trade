/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.upside.entry;

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

public class TSubProcessInfo implements org.apache.thrift.TBase<TSubProcessInfo, TSubProcessInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TSubProcessInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSubProcessInfo");

  private static final org.apache.thrift.protocol.TField TRADE_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField PID_FIELD_DESC = new org.apache.thrift.protocol.TField("pid", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TIME_INFOS_FIELD_DESC = new org.apache.thrift.protocol.TField("timeInfos", org.apache.thrift.protocol.TType.LIST, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TSubProcessInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TSubProcessInfoTupleSchemeFactory());
  }

  public long tradeAccountId; // optional
  public int pid; // optional
  public List<TSubProcessTimeInfo> timeInfos; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRADE_ACCOUNT_ID((short)1, "tradeAccountId"),
    PID((short)2, "pid"),
    TIME_INFOS((short)3, "timeInfos");

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
        case 1: // TRADE_ACCOUNT_ID
          return TRADE_ACCOUNT_ID;
        case 2: // PID
          return PID;
        case 3: // TIME_INFOS
          return TIME_INFOS;
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
  private static final int __TRADEACCOUNTID_ISSET_ID = 0;
  private static final int __PID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TRADE_ACCOUNT_ID,_Fields.PID,_Fields.TIME_INFOS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRADE_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("tradeAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.PID, new org.apache.thrift.meta_data.FieldMetaData("pid", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TIME_INFOS, new org.apache.thrift.meta_data.FieldMetaData("timeInfos", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TSubProcessTimeInfo.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSubProcessInfo.class, metaDataMap);
  }

  public TSubProcessInfo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSubProcessInfo(TSubProcessInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.tradeAccountId = other.tradeAccountId;
    this.pid = other.pid;
    if (other.isSetTimeInfos()) {
      List<TSubProcessTimeInfo> __this__timeInfos = new ArrayList<TSubProcessTimeInfo>(other.timeInfos.size());
      for (TSubProcessTimeInfo other_element : other.timeInfos) {
        __this__timeInfos.add(new TSubProcessTimeInfo(other_element));
      }
      this.timeInfos = __this__timeInfos;
    }
  }

  public TSubProcessInfo deepCopy() {
    return new TSubProcessInfo(this);
  }

  @Override
  public void clear() {
    setTradeAccountIdIsSet(false);
    this.tradeAccountId = 0;
    setPidIsSet(false);
    this.pid = 0;
    this.timeInfos = null;
  }

  public long getTradeAccountId() {
    return this.tradeAccountId;
  }

  public TSubProcessInfo setTradeAccountId(long tradeAccountId) {
    this.tradeAccountId = tradeAccountId;
    setTradeAccountIdIsSet(true);
    return this;
  }

  public void unsetTradeAccountId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TRADEACCOUNTID_ISSET_ID);
  }

  /** Returns true if field tradeAccountId is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeAccountId() {
    return EncodingUtils.testBit(__isset_bitfield, __TRADEACCOUNTID_ISSET_ID);
  }

  public void setTradeAccountIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TRADEACCOUNTID_ISSET_ID, value);
  }

  public int getPid() {
    return this.pid;
  }

  public TSubProcessInfo setPid(int pid) {
    this.pid = pid;
    setPidIsSet(true);
    return this;
  }

  public void unsetPid() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PID_ISSET_ID);
  }

  /** Returns true if field pid is set (has been assigned a value) and false otherwise */
  public boolean isSetPid() {
    return EncodingUtils.testBit(__isset_bitfield, __PID_ISSET_ID);
  }

  public void setPidIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PID_ISSET_ID, value);
  }

  public int getTimeInfosSize() {
    return (this.timeInfos == null) ? 0 : this.timeInfos.size();
  }

  public java.util.Iterator<TSubProcessTimeInfo> getTimeInfosIterator() {
    return (this.timeInfos == null) ? null : this.timeInfos.iterator();
  }

  public void addToTimeInfos(TSubProcessTimeInfo elem) {
    if (this.timeInfos == null) {
      this.timeInfos = new ArrayList<TSubProcessTimeInfo>();
    }
    this.timeInfos.add(elem);
  }

  public List<TSubProcessTimeInfo> getTimeInfos() {
    return this.timeInfos;
  }

  public TSubProcessInfo setTimeInfos(List<TSubProcessTimeInfo> timeInfos) {
    this.timeInfos = timeInfos;
    return this;
  }

  public void unsetTimeInfos() {
    this.timeInfos = null;
  }

  /** Returns true if field timeInfos is set (has been assigned a value) and false otherwise */
  public boolean isSetTimeInfos() {
    return this.timeInfos != null;
  }

  public void setTimeInfosIsSet(boolean value) {
    if (!value) {
      this.timeInfos = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TRADE_ACCOUNT_ID:
      if (value == null) {
        unsetTradeAccountId();
      } else {
        setTradeAccountId((Long)value);
      }
      break;

    case PID:
      if (value == null) {
        unsetPid();
      } else {
        setPid((Integer)value);
      }
      break;

    case TIME_INFOS:
      if (value == null) {
        unsetTimeInfos();
      } else {
        setTimeInfos((List<TSubProcessTimeInfo>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRADE_ACCOUNT_ID:
      return Long.valueOf(getTradeAccountId());

    case PID:
      return Integer.valueOf(getPid());

    case TIME_INFOS:
      return getTimeInfos();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TRADE_ACCOUNT_ID:
      return isSetTradeAccountId();
    case PID:
      return isSetPid();
    case TIME_INFOS:
      return isSetTimeInfos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TSubProcessInfo)
      return this.equals((TSubProcessInfo)that);
    return false;
  }

  public boolean equals(TSubProcessInfo that) {
    if (that == null)
      return false;

    boolean this_present_tradeAccountId = true && this.isSetTradeAccountId();
    boolean that_present_tradeAccountId = true && that.isSetTradeAccountId();
    if (this_present_tradeAccountId || that_present_tradeAccountId) {
      if (!(this_present_tradeAccountId && that_present_tradeAccountId))
        return false;
      if (this.tradeAccountId != that.tradeAccountId)
        return false;
    }

    boolean this_present_pid = true && this.isSetPid();
    boolean that_present_pid = true && that.isSetPid();
    if (this_present_pid || that_present_pid) {
      if (!(this_present_pid && that_present_pid))
        return false;
      if (this.pid != that.pid)
        return false;
    }

    boolean this_present_timeInfos = true && this.isSetTimeInfos();
    boolean that_present_timeInfos = true && that.isSetTimeInfos();
    if (this_present_timeInfos || that_present_timeInfos) {
      if (!(this_present_timeInfos && that_present_timeInfos))
        return false;
      if (!this.timeInfos.equals(that.timeInfos))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TSubProcessInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTradeAccountId()).compareTo(other.isSetTradeAccountId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeAccountId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeAccountId, other.tradeAccountId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPid()).compareTo(other.isSetPid());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPid()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pid, other.pid);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTimeInfos()).compareTo(other.isSetTimeInfos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimeInfos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timeInfos, other.timeInfos);
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
    StringBuilder sb = new StringBuilder("TSubProcessInfo(");
    boolean first = true;

    if (isSetTradeAccountId()) {
      sb.append("tradeAccountId:");
      sb.append(this.tradeAccountId);
      first = false;
    }
    if (isSetPid()) {
      if (!first) sb.append(", ");
      sb.append("pid:");
      sb.append(this.pid);
      first = false;
    }
    if (isSetTimeInfos()) {
      if (!first) sb.append(", ");
      sb.append("timeInfos:");
      if (this.timeInfos == null) {
        sb.append("null");
      } else {
        sb.append(this.timeInfos);
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

  private static class TSubProcessInfoStandardSchemeFactory implements SchemeFactory {
    public TSubProcessInfoStandardScheme getScheme() {
      return new TSubProcessInfoStandardScheme();
    }
  }

  private static class TSubProcessInfoStandardScheme extends StandardScheme<TSubProcessInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSubProcessInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TRADE_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.tradeAccountId = iprot.readI64();
              struct.setTradeAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.pid = iprot.readI32();
              struct.setPidIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TIME_INFOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.timeInfos = new ArrayList<TSubProcessTimeInfo>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  TSubProcessTimeInfo _elem10;
                  _elem10 = new TSubProcessTimeInfo();
                  _elem10.read(iprot);
                  struct.timeInfos.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setTimeInfosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSubProcessInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTradeAccountId()) {
        oprot.writeFieldBegin(TRADE_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.tradeAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetPid()) {
        oprot.writeFieldBegin(PID_FIELD_DESC);
        oprot.writeI32(struct.pid);
        oprot.writeFieldEnd();
      }
      if (struct.timeInfos != null) {
        if (struct.isSetTimeInfos()) {
          oprot.writeFieldBegin(TIME_INFOS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.timeInfos.size()));
            for (TSubProcessTimeInfo _iter11 : struct.timeInfos)
            {
              _iter11.write(oprot);
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

  private static class TSubProcessInfoTupleSchemeFactory implements SchemeFactory {
    public TSubProcessInfoTupleScheme getScheme() {
      return new TSubProcessInfoTupleScheme();
    }
  }

  private static class TSubProcessInfoTupleScheme extends TupleScheme<TSubProcessInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSubProcessInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTradeAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetPid()) {
        optionals.set(1);
      }
      if (struct.isSetTimeInfos()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTradeAccountId()) {
        oprot.writeI64(struct.tradeAccountId);
      }
      if (struct.isSetPid()) {
        oprot.writeI32(struct.pid);
      }
      if (struct.isSetTimeInfos()) {
        {
          oprot.writeI32(struct.timeInfos.size());
          for (TSubProcessTimeInfo _iter12 : struct.timeInfos)
          {
            _iter12.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSubProcessInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.tradeAccountId = iprot.readI64();
        struct.setTradeAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.pid = iprot.readI32();
        struct.setPidIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.timeInfos = new ArrayList<TSubProcessTimeInfo>(_list13.size);
          for (int _i14 = 0; _i14 < _list13.size; ++_i14)
          {
            TSubProcessTimeInfo _elem15;
            _elem15 = new TSubProcessTimeInfo();
            _elem15.read(iprot);
            struct.timeInfos.add(_elem15);
          }
        }
        struct.setTimeInfosIsSet(true);
      }
    }
  }

}

