/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.fee.thriftapi;

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

public class UpsideContractMarginPage implements org.apache.thrift.TBase<UpsideContractMarginPage, UpsideContractMarginPage._Fields>, java.io.Serializable, Cloneable, Comparable<UpsideContractMarginPage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("UpsideContractMarginPage");

  private static final org.apache.thrift.protocol.TField TOTAL_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("totalNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField UPSIDE_CONTRACT_MARGIN_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("upsideContractMarginList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UpsideContractMarginPageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UpsideContractMarginPageTupleSchemeFactory());
  }

  public int totalNum; // optional
  public List<UpsideContractMargin> upsideContractMarginList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_NUM((short)1, "totalNum"),
    UPSIDE_CONTRACT_MARGIN_LIST((short)2, "upsideContractMarginList");

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
        case 2: // UPSIDE_CONTRACT_MARGIN_LIST
          return UPSIDE_CONTRACT_MARGIN_LIST;
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
  private _Fields optionals[] = {_Fields.TOTAL_NUM,_Fields.UPSIDE_CONTRACT_MARGIN_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_NUM, new org.apache.thrift.meta_data.FieldMetaData("totalNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.UPSIDE_CONTRACT_MARGIN_LIST, new org.apache.thrift.meta_data.FieldMetaData("upsideContractMarginList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, UpsideContractMargin.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(UpsideContractMarginPage.class, metaDataMap);
  }

  public UpsideContractMarginPage() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public UpsideContractMarginPage(UpsideContractMarginPage other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalNum = other.totalNum;
    if (other.isSetUpsideContractMarginList()) {
      List<UpsideContractMargin> __this__upsideContractMarginList = new ArrayList<UpsideContractMargin>(other.upsideContractMarginList.size());
      for (UpsideContractMargin other_element : other.upsideContractMarginList) {
        __this__upsideContractMarginList.add(new UpsideContractMargin(other_element));
      }
      this.upsideContractMarginList = __this__upsideContractMarginList;
    }
  }

  public UpsideContractMarginPage deepCopy() {
    return new UpsideContractMarginPage(this);
  }

  @Override
  public void clear() {
    setTotalNumIsSet(false);
    this.totalNum = 0;
    this.upsideContractMarginList = null;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public UpsideContractMarginPage setTotalNum(int totalNum) {
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

  public int getUpsideContractMarginListSize() {
    return (this.upsideContractMarginList == null) ? 0 : this.upsideContractMarginList.size();
  }

  public java.util.Iterator<UpsideContractMargin> getUpsideContractMarginListIterator() {
    return (this.upsideContractMarginList == null) ? null : this.upsideContractMarginList.iterator();
  }

  public void addToUpsideContractMarginList(UpsideContractMargin elem) {
    if (this.upsideContractMarginList == null) {
      this.upsideContractMarginList = new ArrayList<UpsideContractMargin>();
    }
    this.upsideContractMarginList.add(elem);
  }

  public List<UpsideContractMargin> getUpsideContractMarginList() {
    return this.upsideContractMarginList;
  }

  public UpsideContractMarginPage setUpsideContractMarginList(List<UpsideContractMargin> upsideContractMarginList) {
    this.upsideContractMarginList = upsideContractMarginList;
    return this;
  }

  public void unsetUpsideContractMarginList() {
    this.upsideContractMarginList = null;
  }

  /** Returns true if field upsideContractMarginList is set (has been assigned a value) and false otherwise */
  public boolean isSetUpsideContractMarginList() {
    return this.upsideContractMarginList != null;
  }

  public void setUpsideContractMarginListIsSet(boolean value) {
    if (!value) {
      this.upsideContractMarginList = null;
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

    case UPSIDE_CONTRACT_MARGIN_LIST:
      if (value == null) {
        unsetUpsideContractMarginList();
      } else {
        setUpsideContractMarginList((List<UpsideContractMargin>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_NUM:
      return Integer.valueOf(getTotalNum());

    case UPSIDE_CONTRACT_MARGIN_LIST:
      return getUpsideContractMarginList();

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
    case UPSIDE_CONTRACT_MARGIN_LIST:
      return isSetUpsideContractMarginList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof UpsideContractMarginPage)
      return this.equals((UpsideContractMarginPage)that);
    return false;
  }

  public boolean equals(UpsideContractMarginPage that) {
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

    boolean this_present_upsideContractMarginList = true && this.isSetUpsideContractMarginList();
    boolean that_present_upsideContractMarginList = true && that.isSetUpsideContractMarginList();
    if (this_present_upsideContractMarginList || that_present_upsideContractMarginList) {
      if (!(this_present_upsideContractMarginList && that_present_upsideContractMarginList))
        return false;
      if (!this.upsideContractMarginList.equals(that.upsideContractMarginList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(UpsideContractMarginPage other) {
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
    lastComparison = Boolean.valueOf(isSetUpsideContractMarginList()).compareTo(other.isSetUpsideContractMarginList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpsideContractMarginList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.upsideContractMarginList, other.upsideContractMarginList);
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
    StringBuilder sb = new StringBuilder("UpsideContractMarginPage(");
    boolean first = true;

    if (isSetTotalNum()) {
      sb.append("totalNum:");
      sb.append(this.totalNum);
      first = false;
    }
    if (isSetUpsideContractMarginList()) {
      if (!first) sb.append(", ");
      sb.append("upsideContractMarginList:");
      if (this.upsideContractMarginList == null) {
        sb.append("null");
      } else {
        sb.append(this.upsideContractMarginList);
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

  private static class UpsideContractMarginPageStandardSchemeFactory implements SchemeFactory {
    public UpsideContractMarginPageStandardScheme getScheme() {
      return new UpsideContractMarginPageStandardScheme();
    }
  }

  private static class UpsideContractMarginPageStandardScheme extends StandardScheme<UpsideContractMarginPage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, UpsideContractMarginPage struct) throws org.apache.thrift.TException {
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
          case 2: // UPSIDE_CONTRACT_MARGIN_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list40 = iprot.readListBegin();
                struct.upsideContractMarginList = new ArrayList<UpsideContractMargin>(_list40.size);
                for (int _i41 = 0; _i41 < _list40.size; ++_i41)
                {
                  UpsideContractMargin _elem42;
                  _elem42 = new UpsideContractMargin();
                  _elem42.read(iprot);
                  struct.upsideContractMarginList.add(_elem42);
                }
                iprot.readListEnd();
              }
              struct.setUpsideContractMarginListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, UpsideContractMarginPage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalNum()) {
        oprot.writeFieldBegin(TOTAL_NUM_FIELD_DESC);
        oprot.writeI32(struct.totalNum);
        oprot.writeFieldEnd();
      }
      if (struct.upsideContractMarginList != null) {
        if (struct.isSetUpsideContractMarginList()) {
          oprot.writeFieldBegin(UPSIDE_CONTRACT_MARGIN_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.upsideContractMarginList.size()));
            for (UpsideContractMargin _iter43 : struct.upsideContractMarginList)
            {
              _iter43.write(oprot);
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

  private static class UpsideContractMarginPageTupleSchemeFactory implements SchemeFactory {
    public UpsideContractMarginPageTupleScheme getScheme() {
      return new UpsideContractMarginPageTupleScheme();
    }
  }

  private static class UpsideContractMarginPageTupleScheme extends TupleScheme<UpsideContractMarginPage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, UpsideContractMarginPage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalNum()) {
        optionals.set(0);
      }
      if (struct.isSetUpsideContractMarginList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTotalNum()) {
        oprot.writeI32(struct.totalNum);
      }
      if (struct.isSetUpsideContractMarginList()) {
        {
          oprot.writeI32(struct.upsideContractMarginList.size());
          for (UpsideContractMargin _iter44 : struct.upsideContractMarginList)
          {
            _iter44.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, UpsideContractMarginPage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.totalNum = iprot.readI32();
        struct.setTotalNumIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list45 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.upsideContractMarginList = new ArrayList<UpsideContractMargin>(_list45.size);
          for (int _i46 = 0; _i46 < _list45.size; ++_i46)
          {
            UpsideContractMargin _elem47;
            _elem47 = new UpsideContractMargin();
            _elem47.read(iprot);
            struct.upsideContractMarginList.add(_elem47);
          }
        }
        struct.setUpsideContractMarginListIsSet(true);
      }
    }
  }

}
