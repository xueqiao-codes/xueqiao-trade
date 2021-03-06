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

public class StatPositionSummaryPage implements org.apache.thrift.TBase<StatPositionSummaryPage, StatPositionSummaryPage._Fields>, java.io.Serializable, Cloneable, Comparable<StatPositionSummaryPage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StatPositionSummaryPage");

  private static final org.apache.thrift.protocol.TField TOTAL_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("totalNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField STAT_POSITION_SUMMARY_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("statPositionSummaryList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StatPositionSummaryPageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StatPositionSummaryPageTupleSchemeFactory());
  }

  public int totalNum; // optional
  public List<StatPositionSummary> statPositionSummaryList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_NUM((short)1, "totalNum"),
    STAT_POSITION_SUMMARY_LIST((short)2, "statPositionSummaryList");

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
        case 1: // TOTAL_NUM
          return TOTAL_NUM;
        case 2: // STAT_POSITION_SUMMARY_LIST
          return STAT_POSITION_SUMMARY_LIST;
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
  private static final int __TOTALNUM_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TOTAL_NUM,_Fields.STAT_POSITION_SUMMARY_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_NUM, new org.apache.thrift.meta_data.FieldMetaData("totalNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STAT_POSITION_SUMMARY_LIST, new org.apache.thrift.meta_data.FieldMetaData("statPositionSummaryList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, StatPositionSummary.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatPositionSummaryPage.class, metaDataMap);
  }

  public StatPositionSummaryPage() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StatPositionSummaryPage(StatPositionSummaryPage other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalNum = other.totalNum;
    if (other.isSetStatPositionSummaryList()) {
      List<StatPositionSummary> __this__statPositionSummaryList = new ArrayList<StatPositionSummary>(other.statPositionSummaryList.size());
      for (StatPositionSummary other_element : other.statPositionSummaryList) {
        __this__statPositionSummaryList.add(new StatPositionSummary(other_element));
      }
      this.statPositionSummaryList = __this__statPositionSummaryList;
    }
  }

  public StatPositionSummaryPage deepCopy() {
    return new StatPositionSummaryPage(this);
  }

  @Override
  public void clear() {
    setTotalNumIsSet(false);
    this.totalNum = 0;
    this.statPositionSummaryList = null;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public StatPositionSummaryPage setTotalNum(int totalNum) {
    this.totalNum = totalNum;
    setTotalNumIsSet(true);
    return this;
  }

  public void unsetTotalNum() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALNUM_ISSET_ID);
  }

  /** Returns true if field totalNum is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalNum() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALNUM_ISSET_ID);
  }

  public void setTotalNumIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALNUM_ISSET_ID, value);
  }

  public int getStatPositionSummaryListSize() {
    return (this.statPositionSummaryList == null) ? 0 : this.statPositionSummaryList.size();
  }

  public java.util.Iterator<StatPositionSummary> getStatPositionSummaryListIterator() {
    return (this.statPositionSummaryList == null) ? null : this.statPositionSummaryList.iterator();
  }

  public void addToStatPositionSummaryList(StatPositionSummary elem) {
    if (this.statPositionSummaryList == null) {
      this.statPositionSummaryList = new ArrayList<StatPositionSummary>();
    }
    this.statPositionSummaryList.add(elem);
  }

  public List<StatPositionSummary> getStatPositionSummaryList() {
    return this.statPositionSummaryList;
  }

  public StatPositionSummaryPage setStatPositionSummaryList(List<StatPositionSummary> statPositionSummaryList) {
    this.statPositionSummaryList = statPositionSummaryList;
    return this;
  }

  public void unsetStatPositionSummaryList() {
    this.statPositionSummaryList = null;
  }

  /** Returns true if field statPositionSummaryList is set (has been assigned a value) and false otherwise */
  public boolean isSetStatPositionSummaryList() {
    return this.statPositionSummaryList != null;
  }

  public void setStatPositionSummaryListIsSet(boolean value) {
    if (!value) {
      this.statPositionSummaryList = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_NUM:
      if (value == null) {
        unsetTotalNum();
      } else {
        setTotalNum((Integer)value);
      }
      break;

    case STAT_POSITION_SUMMARY_LIST:
      if (value == null) {
        unsetStatPositionSummaryList();
      } else {
        setStatPositionSummaryList((List<StatPositionSummary>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_NUM:
      return Integer.valueOf(getTotalNum());

    case STAT_POSITION_SUMMARY_LIST:
      return getStatPositionSummaryList();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_NUM:
      return isSetTotalNum();
    case STAT_POSITION_SUMMARY_LIST:
      return isSetStatPositionSummaryList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StatPositionSummaryPage)
      return this.equals((StatPositionSummaryPage)that);
    return false;
  }

  public boolean equals(StatPositionSummaryPage that) {
    if (that == null)
      return false;

    boolean this_present_totalNum = true && this.isSetTotalNum();
    boolean that_present_totalNum = true && that.isSetTotalNum();
    if (this_present_totalNum || that_present_totalNum) {
      if (!(this_present_totalNum && that_present_totalNum))
        return false;
      if (this.totalNum != that.totalNum)
        return false;
    }

    boolean this_present_statPositionSummaryList = true && this.isSetStatPositionSummaryList();
    boolean that_present_statPositionSummaryList = true && that.isSetStatPositionSummaryList();
    if (this_present_statPositionSummaryList || that_present_statPositionSummaryList) {
      if (!(this_present_statPositionSummaryList && that_present_statPositionSummaryList))
        return false;
      if (!this.statPositionSummaryList.equals(that.statPositionSummaryList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StatPositionSummaryPage other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTotalNum()).compareTo(other.isSetTotalNum());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalNum()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalNum, other.totalNum);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatPositionSummaryList()).compareTo(other.isSetStatPositionSummaryList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatPositionSummaryList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statPositionSummaryList, other.statPositionSummaryList);
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
    StringBuilder sb = new StringBuilder("StatPositionSummaryPage(");
    boolean first = true;

    if (isSetTotalNum()) {
      sb.append("totalNum:");
      sb.append(this.totalNum);
      first = false;
    }
    if (isSetStatPositionSummaryList()) {
      if (!first) sb.append(", ");
      sb.append("statPositionSummaryList:");
      if (this.statPositionSummaryList == null) {
        sb.append("null");
      } else {
        sb.append(this.statPositionSummaryList);
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

  private static class StatPositionSummaryPageStandardSchemeFactory implements SchemeFactory {
    public StatPositionSummaryPageStandardScheme getScheme() {
      return new StatPositionSummaryPageStandardScheme();
    }
  }

  private static class StatPositionSummaryPageStandardScheme extends StandardScheme<StatPositionSummaryPage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StatPositionSummaryPage struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_NUM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalNum = iprot.readI32();
              struct.setTotalNumIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // STAT_POSITION_SUMMARY_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list124 = iprot.readListBegin();
                struct.statPositionSummaryList = new ArrayList<StatPositionSummary>(_list124.size);
                for (int _i125 = 0; _i125 < _list124.size; ++_i125)
                {
                  StatPositionSummary _elem126;
                  _elem126 = new StatPositionSummary();
                  _elem126.read(iprot);
                  struct.statPositionSummaryList.add(_elem126);
                }
                iprot.readListEnd();
              }
              struct.setStatPositionSummaryListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StatPositionSummaryPage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalNum()) {
        oprot.writeFieldBegin(TOTAL_NUM_FIELD_DESC);
        oprot.writeI32(struct.totalNum);
        oprot.writeFieldEnd();
      }
      if (struct.statPositionSummaryList != null) {
        if (struct.isSetStatPositionSummaryList()) {
          oprot.writeFieldBegin(STAT_POSITION_SUMMARY_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.statPositionSummaryList.size()));
            for (StatPositionSummary _iter127 : struct.statPositionSummaryList)
            {
              _iter127.write(oprot);
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

  private static class StatPositionSummaryPageTupleSchemeFactory implements SchemeFactory {
    public StatPositionSummaryPageTupleScheme getScheme() {
      return new StatPositionSummaryPageTupleScheme();
    }
  }

  private static class StatPositionSummaryPageTupleScheme extends TupleScheme<StatPositionSummaryPage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StatPositionSummaryPage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalNum()) {
        optionals.set(0);
      }
      if (struct.isSetStatPositionSummaryList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTotalNum()) {
        oprot.writeI32(struct.totalNum);
      }
      if (struct.isSetStatPositionSummaryList()) {
        {
          oprot.writeI32(struct.statPositionSummaryList.size());
          for (StatPositionSummary _iter128 : struct.statPositionSummaryList)
          {
            _iter128.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StatPositionSummaryPage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.totalNum = iprot.readI32();
        struct.setTotalNumIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list129 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.statPositionSummaryList = new ArrayList<StatPositionSummary>(_list129.size);
          for (int _i130 = 0; _i130 < _list129.size; ++_i130)
          {
            StatPositionSummary _elem131;
            _elem131 = new StatPositionSummary();
            _elem131.read(iprot);
            struct.statPositionSummaryList.add(_elem131);
          }
        }
        struct.setStatPositionSummaryListIsSet(true);
      }
    }
  }

}

