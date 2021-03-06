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

public class StatPositionUnitPage implements org.apache.thrift.TBase<StatPositionUnitPage, StatPositionUnitPage._Fields>, java.io.Serializable, Cloneable, Comparable<StatPositionUnitPage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("StatPositionUnitPage");

  private static final org.apache.thrift.protocol.TField TOTAL_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("totalNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField STAT_POSITION_UNIT_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("statPositionUnitList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new StatPositionUnitPageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new StatPositionUnitPageTupleSchemeFactory());
  }

  public int totalNum; // optional
  public List<StatPositionUnit> statPositionUnitList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_NUM((short)1, "totalNum"),
    STAT_POSITION_UNIT_LIST((short)2, "statPositionUnitList");

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
        case 2: // STAT_POSITION_UNIT_LIST
          return STAT_POSITION_UNIT_LIST;
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
  private _Fields optionals[] = {_Fields.TOTAL_NUM,_Fields.STAT_POSITION_UNIT_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_NUM, new org.apache.thrift.meta_data.FieldMetaData("totalNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.STAT_POSITION_UNIT_LIST, new org.apache.thrift.meta_data.FieldMetaData("statPositionUnitList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, StatPositionUnit.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(StatPositionUnitPage.class, metaDataMap);
  }

  public StatPositionUnitPage() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public StatPositionUnitPage(StatPositionUnitPage other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalNum = other.totalNum;
    if (other.isSetStatPositionUnitList()) {
      List<StatPositionUnit> __this__statPositionUnitList = new ArrayList<StatPositionUnit>(other.statPositionUnitList.size());
      for (StatPositionUnit other_element : other.statPositionUnitList) {
        __this__statPositionUnitList.add(new StatPositionUnit(other_element));
      }
      this.statPositionUnitList = __this__statPositionUnitList;
    }
  }

  public StatPositionUnitPage deepCopy() {
    return new StatPositionUnitPage(this);
  }

  @Override
  public void clear() {
    setTotalNumIsSet(false);
    this.totalNum = 0;
    this.statPositionUnitList = null;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public StatPositionUnitPage setTotalNum(int totalNum) {
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

  public int getStatPositionUnitListSize() {
    return (this.statPositionUnitList == null) ? 0 : this.statPositionUnitList.size();
  }

  public java.util.Iterator<StatPositionUnit> getStatPositionUnitListIterator() {
    return (this.statPositionUnitList == null) ? null : this.statPositionUnitList.iterator();
  }

  public void addToStatPositionUnitList(StatPositionUnit elem) {
    if (this.statPositionUnitList == null) {
      this.statPositionUnitList = new ArrayList<StatPositionUnit>();
    }
    this.statPositionUnitList.add(elem);
  }

  public List<StatPositionUnit> getStatPositionUnitList() {
    return this.statPositionUnitList;
  }

  public StatPositionUnitPage setStatPositionUnitList(List<StatPositionUnit> statPositionUnitList) {
    this.statPositionUnitList = statPositionUnitList;
    return this;
  }

  public void unsetStatPositionUnitList() {
    this.statPositionUnitList = null;
  }

  /** Returns true if field statPositionUnitList is set (has been assigned a value) and false otherwise */
  public boolean isSetStatPositionUnitList() {
    return this.statPositionUnitList != null;
  }

  public void setStatPositionUnitListIsSet(boolean value) {
    if (!value) {
      this.statPositionUnitList = null;
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

    case STAT_POSITION_UNIT_LIST:
      if (value == null) {
        unsetStatPositionUnitList();
      } else {
        setStatPositionUnitList((List<StatPositionUnit>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_NUM:
      return Integer.valueOf(getTotalNum());

    case STAT_POSITION_UNIT_LIST:
      return getStatPositionUnitList();

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
    case STAT_POSITION_UNIT_LIST:
      return isSetStatPositionUnitList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof StatPositionUnitPage)
      return this.equals((StatPositionUnitPage)that);
    return false;
  }

  public boolean equals(StatPositionUnitPage that) {
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

    boolean this_present_statPositionUnitList = true && this.isSetStatPositionUnitList();
    boolean that_present_statPositionUnitList = true && that.isSetStatPositionUnitList();
    if (this_present_statPositionUnitList || that_present_statPositionUnitList) {
      if (!(this_present_statPositionUnitList && that_present_statPositionUnitList))
        return false;
      if (!this.statPositionUnitList.equals(that.statPositionUnitList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(StatPositionUnitPage other) {
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
    lastComparison = Boolean.valueOf(isSetStatPositionUnitList()).compareTo(other.isSetStatPositionUnitList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatPositionUnitList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statPositionUnitList, other.statPositionUnitList);
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
    StringBuilder sb = new StringBuilder("StatPositionUnitPage(");
    boolean first = true;

    if (isSetTotalNum()) {
      sb.append("totalNum:");
      sb.append(this.totalNum);
      first = false;
    }
    if (isSetStatPositionUnitList()) {
      if (!first) sb.append(", ");
      sb.append("statPositionUnitList:");
      if (this.statPositionUnitList == null) {
        sb.append("null");
      } else {
        sb.append(this.statPositionUnitList);
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

  private static class StatPositionUnitPageStandardSchemeFactory implements SchemeFactory {
    public StatPositionUnitPageStandardScheme getScheme() {
      return new StatPositionUnitPageStandardScheme();
    }
  }

  private static class StatPositionUnitPageStandardScheme extends StandardScheme<StatPositionUnitPage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, StatPositionUnitPage struct) throws org.apache.thrift.TException {
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
          case 2: // STAT_POSITION_UNIT_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list204 = iprot.readListBegin();
                struct.statPositionUnitList = new ArrayList<StatPositionUnit>(_list204.size);
                for (int _i205 = 0; _i205 < _list204.size; ++_i205)
                {
                  StatPositionUnit _elem206;
                  _elem206 = new StatPositionUnit();
                  _elem206.read(iprot);
                  struct.statPositionUnitList.add(_elem206);
                }
                iprot.readListEnd();
              }
              struct.setStatPositionUnitListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, StatPositionUnitPage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalNum()) {
        oprot.writeFieldBegin(TOTAL_NUM_FIELD_DESC);
        oprot.writeI32(struct.totalNum);
        oprot.writeFieldEnd();
      }
      if (struct.statPositionUnitList != null) {
        if (struct.isSetStatPositionUnitList()) {
          oprot.writeFieldBegin(STAT_POSITION_UNIT_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.statPositionUnitList.size()));
            for (StatPositionUnit _iter207 : struct.statPositionUnitList)
            {
              _iter207.write(oprot);
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

  private static class StatPositionUnitPageTupleSchemeFactory implements SchemeFactory {
    public StatPositionUnitPageTupleScheme getScheme() {
      return new StatPositionUnitPageTupleScheme();
    }
  }

  private static class StatPositionUnitPageTupleScheme extends TupleScheme<StatPositionUnitPage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, StatPositionUnitPage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalNum()) {
        optionals.set(0);
      }
      if (struct.isSetStatPositionUnitList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTotalNum()) {
        oprot.writeI32(struct.totalNum);
      }
      if (struct.isSetStatPositionUnitList()) {
        {
          oprot.writeI32(struct.statPositionUnitList.size());
          for (StatPositionUnit _iter208 : struct.statPositionUnitList)
          {
            _iter208.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, StatPositionUnitPage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.totalNum = iprot.readI32();
        struct.setTotalNumIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list209 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.statPositionUnitList = new ArrayList<StatPositionUnit>(_list209.size);
          for (int _i210 = 0; _i210 < _list209.size; ++_i210)
          {
            StatPositionUnit _elem211;
            _elem211 = new StatPositionUnit();
            _elem211.read(iprot);
            struct.statPositionUnitList.add(_elem211);
          }
        }
        struct.setStatPositionUnitListIsSet(true);
      }
    }
  }

}

