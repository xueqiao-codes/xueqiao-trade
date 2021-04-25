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

public class XQSpecCommissionSettingPage implements org.apache.thrift.TBase<XQSpecCommissionSettingPage, XQSpecCommissionSettingPage._Fields>, java.io.Serializable, Cloneable, Comparable<XQSpecCommissionSettingPage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("XQSpecCommissionSettingPage");

  private static final org.apache.thrift.protocol.TField TOTAL_NUM_FIELD_DESC = new org.apache.thrift.protocol.TField("totalNum", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField XQSPEC_COMMISSION_SETTING_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("XQSpecCommissionSettingList", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new XQSpecCommissionSettingPageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new XQSpecCommissionSettingPageTupleSchemeFactory());
  }

  public int totalNum; // optional
  public List<XQSpecCommissionSettings> XQSpecCommissionSettingList; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_NUM((short)1, "totalNum"),
    XQSPEC_COMMISSION_SETTING_LIST((short)2, "XQSpecCommissionSettingList");

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
        case 2: // XQSPEC_COMMISSION_SETTING_LIST
          return XQSPEC_COMMISSION_SETTING_LIST;
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
  private _Fields optionals[] = {_Fields.TOTAL_NUM,_Fields.XQSPEC_COMMISSION_SETTING_LIST};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_NUM, new org.apache.thrift.meta_data.FieldMetaData("totalNum", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.XQSPEC_COMMISSION_SETTING_LIST, new org.apache.thrift.meta_data.FieldMetaData("XQSpecCommissionSettingList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, XQSpecCommissionSettings.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(XQSpecCommissionSettingPage.class, metaDataMap);
  }

  public XQSpecCommissionSettingPage() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public XQSpecCommissionSettingPage(XQSpecCommissionSettingPage other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalNum = other.totalNum;
    if (other.isSetXQSpecCommissionSettingList()) {
      List<XQSpecCommissionSettings> __this__XQSpecCommissionSettingList = new ArrayList<XQSpecCommissionSettings>(other.XQSpecCommissionSettingList.size());
      for (XQSpecCommissionSettings other_element : other.XQSpecCommissionSettingList) {
        __this__XQSpecCommissionSettingList.add(new XQSpecCommissionSettings(other_element));
      }
      this.XQSpecCommissionSettingList = __this__XQSpecCommissionSettingList;
    }
  }

  public XQSpecCommissionSettingPage deepCopy() {
    return new XQSpecCommissionSettingPage(this);
  }

  @Override
  public void clear() {
    setTotalNumIsSet(false);
    this.totalNum = 0;
    this.XQSpecCommissionSettingList = null;
  }

  public int getTotalNum() {
    return this.totalNum;
  }

  public XQSpecCommissionSettingPage setTotalNum(int totalNum) {
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

  public int getXQSpecCommissionSettingListSize() {
    return (this.XQSpecCommissionSettingList == null) ? 0 : this.XQSpecCommissionSettingList.size();
  }

  public java.util.Iterator<XQSpecCommissionSettings> getXQSpecCommissionSettingListIterator() {
    return (this.XQSpecCommissionSettingList == null) ? null : this.XQSpecCommissionSettingList.iterator();
  }

  public void addToXQSpecCommissionSettingList(XQSpecCommissionSettings elem) {
    if (this.XQSpecCommissionSettingList == null) {
      this.XQSpecCommissionSettingList = new ArrayList<XQSpecCommissionSettings>();
    }
    this.XQSpecCommissionSettingList.add(elem);
  }

  public List<XQSpecCommissionSettings> getXQSpecCommissionSettingList() {
    return this.XQSpecCommissionSettingList;
  }

  public XQSpecCommissionSettingPage setXQSpecCommissionSettingList(List<XQSpecCommissionSettings> XQSpecCommissionSettingList) {
    this.XQSpecCommissionSettingList = XQSpecCommissionSettingList;
    return this;
  }

  public void unsetXQSpecCommissionSettingList() {
    this.XQSpecCommissionSettingList = null;
  }

  /** Returns true if field XQSpecCommissionSettingList is set (has been assigned a value) and false otherwise */
  public boolean isSetXQSpecCommissionSettingList() {
    return this.XQSpecCommissionSettingList != null;
  }

  public void setXQSpecCommissionSettingListIsSet(boolean value) {
    if (!value) {
      this.XQSpecCommissionSettingList = null;
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

    case XQSPEC_COMMISSION_SETTING_LIST:
      if (value == null) {
        unsetXQSpecCommissionSettingList();
      } else {
        setXQSpecCommissionSettingList((List<XQSpecCommissionSettings>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_NUM:
      return Integer.valueOf(getTotalNum());

    case XQSPEC_COMMISSION_SETTING_LIST:
      return getXQSpecCommissionSettingList();

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
    case XQSPEC_COMMISSION_SETTING_LIST:
      return isSetXQSpecCommissionSettingList();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof XQSpecCommissionSettingPage)
      return this.equals((XQSpecCommissionSettingPage)that);
    return false;
  }

  public boolean equals(XQSpecCommissionSettingPage that) {
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

    boolean this_present_XQSpecCommissionSettingList = true && this.isSetXQSpecCommissionSettingList();
    boolean that_present_XQSpecCommissionSettingList = true && that.isSetXQSpecCommissionSettingList();
    if (this_present_XQSpecCommissionSettingList || that_present_XQSpecCommissionSettingList) {
      if (!(this_present_XQSpecCommissionSettingList && that_present_XQSpecCommissionSettingList))
        return false;
      if (!this.XQSpecCommissionSettingList.equals(that.XQSpecCommissionSettingList))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(XQSpecCommissionSettingPage other) {
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
    lastComparison = Boolean.valueOf(isSetXQSpecCommissionSettingList()).compareTo(other.isSetXQSpecCommissionSettingList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetXQSpecCommissionSettingList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.XQSpecCommissionSettingList, other.XQSpecCommissionSettingList);
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
    StringBuilder sb = new StringBuilder("XQSpecCommissionSettingPage(");
    boolean first = true;

    if (isSetTotalNum()) {
      sb.append("totalNum:");
      sb.append(this.totalNum);
      first = false;
    }
    if (isSetXQSpecCommissionSettingList()) {
      if (!first) sb.append(", ");
      sb.append("XQSpecCommissionSettingList:");
      if (this.XQSpecCommissionSettingList == null) {
        sb.append("null");
      } else {
        sb.append(this.XQSpecCommissionSettingList);
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

  private static class XQSpecCommissionSettingPageStandardSchemeFactory implements SchemeFactory {
    public XQSpecCommissionSettingPageStandardScheme getScheme() {
      return new XQSpecCommissionSettingPageStandardScheme();
    }
  }

  private static class XQSpecCommissionSettingPageStandardScheme extends StandardScheme<XQSpecCommissionSettingPage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, XQSpecCommissionSettingPage struct) throws org.apache.thrift.TException {
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
          case 2: // XQSPEC_COMMISSION_SETTING_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list24 = iprot.readListBegin();
                struct.XQSpecCommissionSettingList = new ArrayList<XQSpecCommissionSettings>(_list24.size);
                for (int _i25 = 0; _i25 < _list24.size; ++_i25)
                {
                  XQSpecCommissionSettings _elem26;
                  _elem26 = new XQSpecCommissionSettings();
                  _elem26.read(iprot);
                  struct.XQSpecCommissionSettingList.add(_elem26);
                }
                iprot.readListEnd();
              }
              struct.setXQSpecCommissionSettingListIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, XQSpecCommissionSettingPage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalNum()) {
        oprot.writeFieldBegin(TOTAL_NUM_FIELD_DESC);
        oprot.writeI32(struct.totalNum);
        oprot.writeFieldEnd();
      }
      if (struct.XQSpecCommissionSettingList != null) {
        if (struct.isSetXQSpecCommissionSettingList()) {
          oprot.writeFieldBegin(XQSPEC_COMMISSION_SETTING_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.XQSpecCommissionSettingList.size()));
            for (XQSpecCommissionSettings _iter27 : struct.XQSpecCommissionSettingList)
            {
              _iter27.write(oprot);
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

  private static class XQSpecCommissionSettingPageTupleSchemeFactory implements SchemeFactory {
    public XQSpecCommissionSettingPageTupleScheme getScheme() {
      return new XQSpecCommissionSettingPageTupleScheme();
    }
  }

  private static class XQSpecCommissionSettingPageTupleScheme extends TupleScheme<XQSpecCommissionSettingPage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, XQSpecCommissionSettingPage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalNum()) {
        optionals.set(0);
      }
      if (struct.isSetXQSpecCommissionSettingList()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTotalNum()) {
        oprot.writeI32(struct.totalNum);
      }
      if (struct.isSetXQSpecCommissionSettingList()) {
        {
          oprot.writeI32(struct.XQSpecCommissionSettingList.size());
          for (XQSpecCommissionSettings _iter28 : struct.XQSpecCommissionSettingList)
          {
            _iter28.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, XQSpecCommissionSettingPage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.totalNum = iprot.readI32();
        struct.setTotalNumIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list29 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.XQSpecCommissionSettingList = new ArrayList<XQSpecCommissionSettings>(_list29.size);
          for (int _i30 = 0; _i30 < _list29.size; ++_i30)
          {
            XQSpecCommissionSettings _elem31;
            _elem31 = new XQSpecCommissionSettings();
            _elem31.read(iprot);
            struct.XQSpecCommissionSettingList.add(_elem31);
          }
        }
        struct.setXQSpecCommissionSettingListIsSet(true);
      }
    }
  }

}

