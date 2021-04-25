/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.risk.manager.thriftapi;

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

/**
 * 切面数据聚合
 */
public class HostingRiskFrameDataInfo implements org.apache.thrift.TBase<HostingRiskFrameDataInfo, HostingRiskFrameDataInfo._Fields>, java.io.Serializable, Cloneable, Comparable<HostingRiskFrameDataInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingRiskFrameDataInfo");

  private static final org.apache.thrift.protocol.TField GLOBAL_DATA_INFOS_FIELD_DESC = new org.apache.thrift.protocol.TField("globalDataInfos", org.apache.thrift.protocol.TType.LIST, (short)1);
  private static final org.apache.thrift.protocol.TField COMMODITY_DATA_INFOS_FIELD_DESC = new org.apache.thrift.protocol.TField("commodityDataInfos", org.apache.thrift.protocol.TType.MAP, (short)2);
  private static final org.apache.thrift.protocol.TField CONTRACT_DATA_INFOS_FIELD_DESC = new org.apache.thrift.protocol.TField("contractDataInfos", org.apache.thrift.protocol.TType.MAP, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingRiskFrameDataInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingRiskFrameDataInfoTupleSchemeFactory());
  }

  public List<HostingRiskItemDataInfo> globalDataInfos; // optional
  public Map<Long,List<HostingRiskItemDataInfo>> commodityDataInfos; // optional
  public Map<Long,Map<Long,List<HostingRiskItemDataInfo>>> contractDataInfos; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GLOBAL_DATA_INFOS((short)1, "globalDataInfos"),
    COMMODITY_DATA_INFOS((short)2, "commodityDataInfos"),
    CONTRACT_DATA_INFOS((short)3, "contractDataInfos");

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
        case 1: // GLOBAL_DATA_INFOS
          return GLOBAL_DATA_INFOS;
        case 2: // COMMODITY_DATA_INFOS
          return COMMODITY_DATA_INFOS;
        case 3: // CONTRACT_DATA_INFOS
          return CONTRACT_DATA_INFOS;
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
  private _Fields optionals[] = {_Fields.GLOBAL_DATA_INFOS,_Fields.COMMODITY_DATA_INFOS,_Fields.CONTRACT_DATA_INFOS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GLOBAL_DATA_INFOS, new org.apache.thrift.meta_data.FieldMetaData("globalDataInfos", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingRiskItemDataInfo.class))));
    tmpMap.put(_Fields.COMMODITY_DATA_INFOS, new org.apache.thrift.meta_data.FieldMetaData("commodityDataInfos", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
            new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingRiskItemDataInfo.class)))));
    tmpMap.put(_Fields.CONTRACT_DATA_INFOS, new org.apache.thrift.meta_data.FieldMetaData("contractDataInfos", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
            new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
                new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
                    new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingRiskItemDataInfo.class))))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingRiskFrameDataInfo.class, metaDataMap);
  }

  public HostingRiskFrameDataInfo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingRiskFrameDataInfo(HostingRiskFrameDataInfo other) {
    if (other.isSetGlobalDataInfos()) {
      List<HostingRiskItemDataInfo> __this__globalDataInfos = new ArrayList<HostingRiskItemDataInfo>(other.globalDataInfos.size());
      for (HostingRiskItemDataInfo other_element : other.globalDataInfos) {
        __this__globalDataInfos.add(new HostingRiskItemDataInfo(other_element));
      }
      this.globalDataInfos = __this__globalDataInfos;
    }
    if (other.isSetCommodityDataInfos()) {
      Map<Long,List<HostingRiskItemDataInfo>> __this__commodityDataInfos = new HashMap<Long,List<HostingRiskItemDataInfo>>(other.commodityDataInfos.size());
      for (Map.Entry<Long, List<HostingRiskItemDataInfo>> other_element : other.commodityDataInfos.entrySet()) {

        Long other_element_key = other_element.getKey();
        List<HostingRiskItemDataInfo> other_element_value = other_element.getValue();

        Long __this__commodityDataInfos_copy_key = other_element_key;

        List<HostingRiskItemDataInfo> __this__commodityDataInfos_copy_value = new ArrayList<HostingRiskItemDataInfo>(other_element_value.size());
        for (HostingRiskItemDataInfo other_element_value_element : other_element_value) {
          __this__commodityDataInfos_copy_value.add(new HostingRiskItemDataInfo(other_element_value_element));
        }

        __this__commodityDataInfos.put(__this__commodityDataInfos_copy_key, __this__commodityDataInfos_copy_value);
      }
      this.commodityDataInfos = __this__commodityDataInfos;
    }
    if (other.isSetContractDataInfos()) {
      Map<Long,Map<Long,List<HostingRiskItemDataInfo>>> __this__contractDataInfos = new HashMap<Long,Map<Long,List<HostingRiskItemDataInfo>>>(other.contractDataInfos.size());
      for (Map.Entry<Long, Map<Long,List<HostingRiskItemDataInfo>>> other_element : other.contractDataInfos.entrySet()) {

        Long other_element_key = other_element.getKey();
        Map<Long,List<HostingRiskItemDataInfo>> other_element_value = other_element.getValue();

        Long __this__contractDataInfos_copy_key = other_element_key;

        Map<Long,List<HostingRiskItemDataInfo>> __this__contractDataInfos_copy_value = new HashMap<Long,List<HostingRiskItemDataInfo>>(other_element_value.size());
        for (Map.Entry<Long, List<HostingRiskItemDataInfo>> other_element_value_element : other_element_value.entrySet()) {

          Long other_element_value_element_key = other_element_value_element.getKey();
          List<HostingRiskItemDataInfo> other_element_value_element_value = other_element_value_element.getValue();

          Long __this__contractDataInfos_copy_value_copy_key = other_element_value_element_key;

          List<HostingRiskItemDataInfo> __this__contractDataInfos_copy_value_copy_value = new ArrayList<HostingRiskItemDataInfo>(other_element_value_element_value.size());
          for (HostingRiskItemDataInfo other_element_value_element_value_element : other_element_value_element_value) {
            __this__contractDataInfos_copy_value_copy_value.add(new HostingRiskItemDataInfo(other_element_value_element_value_element));
          }

          __this__contractDataInfos_copy_value.put(__this__contractDataInfos_copy_value_copy_key, __this__contractDataInfos_copy_value_copy_value);
        }

        __this__contractDataInfos.put(__this__contractDataInfos_copy_key, __this__contractDataInfos_copy_value);
      }
      this.contractDataInfos = __this__contractDataInfos;
    }
  }

  public HostingRiskFrameDataInfo deepCopy() {
    return new HostingRiskFrameDataInfo(this);
  }

  @Override
  public void clear() {
    this.globalDataInfos = null;
    this.commodityDataInfos = null;
    this.contractDataInfos = null;
  }

  public int getGlobalDataInfosSize() {
    return (this.globalDataInfos == null) ? 0 : this.globalDataInfos.size();
  }

  public java.util.Iterator<HostingRiskItemDataInfo> getGlobalDataInfosIterator() {
    return (this.globalDataInfos == null) ? null : this.globalDataInfos.iterator();
  }

  public void addToGlobalDataInfos(HostingRiskItemDataInfo elem) {
    if (this.globalDataInfos == null) {
      this.globalDataInfos = new ArrayList<HostingRiskItemDataInfo>();
    }
    this.globalDataInfos.add(elem);
  }

  public List<HostingRiskItemDataInfo> getGlobalDataInfos() {
    return this.globalDataInfos;
  }

  public HostingRiskFrameDataInfo setGlobalDataInfos(List<HostingRiskItemDataInfo> globalDataInfos) {
    this.globalDataInfos = globalDataInfos;
    return this;
  }

  public void unsetGlobalDataInfos() {
    this.globalDataInfos = null;
  }

  /** Returns true if field globalDataInfos is set (has been assigned a value) and false otherwise */
  public boolean isSetGlobalDataInfos() {
    return this.globalDataInfos != null;
  }

  public void setGlobalDataInfosIsSet(boolean value) {
    if (!value) {
      this.globalDataInfos = null;
    }
  }

  public int getCommodityDataInfosSize() {
    return (this.commodityDataInfos == null) ? 0 : this.commodityDataInfos.size();
  }

  public void putToCommodityDataInfos(long key, List<HostingRiskItemDataInfo> val) {
    if (this.commodityDataInfos == null) {
      this.commodityDataInfos = new HashMap<Long,List<HostingRiskItemDataInfo>>();
    }
    this.commodityDataInfos.put(key, val);
  }

  public Map<Long,List<HostingRiskItemDataInfo>> getCommodityDataInfos() {
    return this.commodityDataInfos;
  }

  public HostingRiskFrameDataInfo setCommodityDataInfos(Map<Long,List<HostingRiskItemDataInfo>> commodityDataInfos) {
    this.commodityDataInfos = commodityDataInfos;
    return this;
  }

  public void unsetCommodityDataInfos() {
    this.commodityDataInfos = null;
  }

  /** Returns true if field commodityDataInfos is set (has been assigned a value) and false otherwise */
  public boolean isSetCommodityDataInfos() {
    return this.commodityDataInfos != null;
  }

  public void setCommodityDataInfosIsSet(boolean value) {
    if (!value) {
      this.commodityDataInfos = null;
    }
  }

  public int getContractDataInfosSize() {
    return (this.contractDataInfos == null) ? 0 : this.contractDataInfos.size();
  }

  public void putToContractDataInfos(long key, Map<Long,List<HostingRiskItemDataInfo>> val) {
    if (this.contractDataInfos == null) {
      this.contractDataInfos = new HashMap<Long,Map<Long,List<HostingRiskItemDataInfo>>>();
    }
    this.contractDataInfos.put(key, val);
  }

  public Map<Long,Map<Long,List<HostingRiskItemDataInfo>>> getContractDataInfos() {
    return this.contractDataInfos;
  }

  public HostingRiskFrameDataInfo setContractDataInfos(Map<Long,Map<Long,List<HostingRiskItemDataInfo>>> contractDataInfos) {
    this.contractDataInfos = contractDataInfos;
    return this;
  }

  public void unsetContractDataInfos() {
    this.contractDataInfos = null;
  }

  /** Returns true if field contractDataInfos is set (has been assigned a value) and false otherwise */
  public boolean isSetContractDataInfos() {
    return this.contractDataInfos != null;
  }

  public void setContractDataInfosIsSet(boolean value) {
    if (!value) {
      this.contractDataInfos = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GLOBAL_DATA_INFOS:
      if (value == null) {
        unsetGlobalDataInfos();
      } else {
        setGlobalDataInfos((List<HostingRiskItemDataInfo>)value);
      }
      break;

    case COMMODITY_DATA_INFOS:
      if (value == null) {
        unsetCommodityDataInfos();
      } else {
        setCommodityDataInfos((Map<Long,List<HostingRiskItemDataInfo>>)value);
      }
      break;

    case CONTRACT_DATA_INFOS:
      if (value == null) {
        unsetContractDataInfos();
      } else {
        setContractDataInfos((Map<Long,Map<Long,List<HostingRiskItemDataInfo>>>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GLOBAL_DATA_INFOS:
      return getGlobalDataInfos();

    case COMMODITY_DATA_INFOS:
      return getCommodityDataInfos();

    case CONTRACT_DATA_INFOS:
      return getContractDataInfos();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GLOBAL_DATA_INFOS:
      return isSetGlobalDataInfos();
    case COMMODITY_DATA_INFOS:
      return isSetCommodityDataInfos();
    case CONTRACT_DATA_INFOS:
      return isSetContractDataInfos();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingRiskFrameDataInfo)
      return this.equals((HostingRiskFrameDataInfo)that);
    return false;
  }

  public boolean equals(HostingRiskFrameDataInfo that) {
    if (that == null)
      return false;

    boolean this_present_globalDataInfos = true && this.isSetGlobalDataInfos();
    boolean that_present_globalDataInfos = true && that.isSetGlobalDataInfos();
    if (this_present_globalDataInfos || that_present_globalDataInfos) {
      if (!(this_present_globalDataInfos && that_present_globalDataInfos))
        return false;
      if (!this.globalDataInfos.equals(that.globalDataInfos))
        return false;
    }

    boolean this_present_commodityDataInfos = true && this.isSetCommodityDataInfos();
    boolean that_present_commodityDataInfos = true && that.isSetCommodityDataInfos();
    if (this_present_commodityDataInfos || that_present_commodityDataInfos) {
      if (!(this_present_commodityDataInfos && that_present_commodityDataInfos))
        return false;
      if (!this.commodityDataInfos.equals(that.commodityDataInfos))
        return false;
    }

    boolean this_present_contractDataInfos = true && this.isSetContractDataInfos();
    boolean that_present_contractDataInfos = true && that.isSetContractDataInfos();
    if (this_present_contractDataInfos || that_present_contractDataInfos) {
      if (!(this_present_contractDataInfos && that_present_contractDataInfos))
        return false;
      if (!this.contractDataInfos.equals(that.contractDataInfos))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingRiskFrameDataInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGlobalDataInfos()).compareTo(other.isSetGlobalDataInfos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGlobalDataInfos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.globalDataInfos, other.globalDataInfos);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommodityDataInfos()).compareTo(other.isSetCommodityDataInfos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommodityDataInfos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commodityDataInfos, other.commodityDataInfos);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContractDataInfos()).compareTo(other.isSetContractDataInfos());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContractDataInfos()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.contractDataInfos, other.contractDataInfos);
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
    StringBuilder sb = new StringBuilder("HostingRiskFrameDataInfo(");
    boolean first = true;

    if (isSetGlobalDataInfos()) {
      sb.append("globalDataInfos:");
      if (this.globalDataInfos == null) {
        sb.append("null");
      } else {
        sb.append(this.globalDataInfos);
      }
      first = false;
    }
    if (isSetCommodityDataInfos()) {
      if (!first) sb.append(", ");
      sb.append("commodityDataInfos:");
      if (this.commodityDataInfos == null) {
        sb.append("null");
      } else {
        sb.append(this.commodityDataInfos);
      }
      first = false;
    }
    if (isSetContractDataInfos()) {
      if (!first) sb.append(", ");
      sb.append("contractDataInfos:");
      if (this.contractDataInfos == null) {
        sb.append("null");
      } else {
        sb.append(this.contractDataInfos);
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

  private static class HostingRiskFrameDataInfoStandardSchemeFactory implements SchemeFactory {
    public HostingRiskFrameDataInfoStandardScheme getScheme() {
      return new HostingRiskFrameDataInfoStandardScheme();
    }
  }

  private static class HostingRiskFrameDataInfoStandardScheme extends StandardScheme<HostingRiskFrameDataInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingRiskFrameDataInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GLOBAL_DATA_INFOS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list162 = iprot.readListBegin();
                struct.globalDataInfos = new ArrayList<HostingRiskItemDataInfo>(_list162.size);
                for (int _i163 = 0; _i163 < _list162.size; ++_i163)
                {
                  HostingRiskItemDataInfo _elem164;
                  _elem164 = new HostingRiskItemDataInfo();
                  _elem164.read(iprot);
                  struct.globalDataInfos.add(_elem164);
                }
                iprot.readListEnd();
              }
              struct.setGlobalDataInfosIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMMODITY_DATA_INFOS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map165 = iprot.readMapBegin();
                struct.commodityDataInfos = new HashMap<Long,List<HostingRiskItemDataInfo>>(2*_map165.size);
                for (int _i166 = 0; _i166 < _map165.size; ++_i166)
                {
                  long _key167;
                  List<HostingRiskItemDataInfo> _val168;
                  _key167 = iprot.readI64();
                  {
                    org.apache.thrift.protocol.TList _list169 = iprot.readListBegin();
                    _val168 = new ArrayList<HostingRiskItemDataInfo>(_list169.size);
                    for (int _i170 = 0; _i170 < _list169.size; ++_i170)
                    {
                      HostingRiskItemDataInfo _elem171;
                      _elem171 = new HostingRiskItemDataInfo();
                      _elem171.read(iprot);
                      _val168.add(_elem171);
                    }
                    iprot.readListEnd();
                  }
                  struct.commodityDataInfos.put(_key167, _val168);
                }
                iprot.readMapEnd();
              }
              struct.setCommodityDataInfosIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CONTRACT_DATA_INFOS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map172 = iprot.readMapBegin();
                struct.contractDataInfos = new HashMap<Long,Map<Long,List<HostingRiskItemDataInfo>>>(2*_map172.size);
                for (int _i173 = 0; _i173 < _map172.size; ++_i173)
                {
                  long _key174;
                  Map<Long,List<HostingRiskItemDataInfo>> _val175;
                  _key174 = iprot.readI64();
                  {
                    org.apache.thrift.protocol.TMap _map176 = iprot.readMapBegin();
                    _val175 = new HashMap<Long,List<HostingRiskItemDataInfo>>(2*_map176.size);
                    for (int _i177 = 0; _i177 < _map176.size; ++_i177)
                    {
                      long _key178;
                      List<HostingRiskItemDataInfo> _val179;
                      _key178 = iprot.readI64();
                      {
                        org.apache.thrift.protocol.TList _list180 = iprot.readListBegin();
                        _val179 = new ArrayList<HostingRiskItemDataInfo>(_list180.size);
                        for (int _i181 = 0; _i181 < _list180.size; ++_i181)
                        {
                          HostingRiskItemDataInfo _elem182;
                          _elem182 = new HostingRiskItemDataInfo();
                          _elem182.read(iprot);
                          _val179.add(_elem182);
                        }
                        iprot.readListEnd();
                      }
                      _val175.put(_key178, _val179);
                    }
                    iprot.readMapEnd();
                  }
                  struct.contractDataInfos.put(_key174, _val175);
                }
                iprot.readMapEnd();
              }
              struct.setContractDataInfosIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingRiskFrameDataInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.globalDataInfos != null) {
        if (struct.isSetGlobalDataInfos()) {
          oprot.writeFieldBegin(GLOBAL_DATA_INFOS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.globalDataInfos.size()));
            for (HostingRiskItemDataInfo _iter183 : struct.globalDataInfos)
            {
              _iter183.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.commodityDataInfos != null) {
        if (struct.isSetCommodityDataInfos()) {
          oprot.writeFieldBegin(COMMODITY_DATA_INFOS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.LIST, struct.commodityDataInfos.size()));
            for (Map.Entry<Long, List<HostingRiskItemDataInfo>> _iter184 : struct.commodityDataInfos.entrySet())
            {
              oprot.writeI64(_iter184.getKey());
              {
                oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, _iter184.getValue().size()));
                for (HostingRiskItemDataInfo _iter185 : _iter184.getValue())
                {
                  _iter185.write(oprot);
                }
                oprot.writeListEnd();
              }
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.contractDataInfos != null) {
        if (struct.isSetContractDataInfos()) {
          oprot.writeFieldBegin(CONTRACT_DATA_INFOS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.MAP, struct.contractDataInfos.size()));
            for (Map.Entry<Long, Map<Long,List<HostingRiskItemDataInfo>>> _iter186 : struct.contractDataInfos.entrySet())
            {
              oprot.writeI64(_iter186.getKey());
              {
                oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.LIST, _iter186.getValue().size()));
                for (Map.Entry<Long, List<HostingRiskItemDataInfo>> _iter187 : _iter186.getValue().entrySet())
                {
                  oprot.writeI64(_iter187.getKey());
                  {
                    oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, _iter187.getValue().size()));
                    for (HostingRiskItemDataInfo _iter188 : _iter187.getValue())
                    {
                      _iter188.write(oprot);
                    }
                    oprot.writeListEnd();
                  }
                }
                oprot.writeMapEnd();
              }
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingRiskFrameDataInfoTupleSchemeFactory implements SchemeFactory {
    public HostingRiskFrameDataInfoTupleScheme getScheme() {
      return new HostingRiskFrameDataInfoTupleScheme();
    }
  }

  private static class HostingRiskFrameDataInfoTupleScheme extends TupleScheme<HostingRiskFrameDataInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingRiskFrameDataInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGlobalDataInfos()) {
        optionals.set(0);
      }
      if (struct.isSetCommodityDataInfos()) {
        optionals.set(1);
      }
      if (struct.isSetContractDataInfos()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetGlobalDataInfos()) {
        {
          oprot.writeI32(struct.globalDataInfos.size());
          for (HostingRiskItemDataInfo _iter189 : struct.globalDataInfos)
          {
            _iter189.write(oprot);
          }
        }
      }
      if (struct.isSetCommodityDataInfos()) {
        {
          oprot.writeI32(struct.commodityDataInfos.size());
          for (Map.Entry<Long, List<HostingRiskItemDataInfo>> _iter190 : struct.commodityDataInfos.entrySet())
          {
            oprot.writeI64(_iter190.getKey());
            {
              oprot.writeI32(_iter190.getValue().size());
              for (HostingRiskItemDataInfo _iter191 : _iter190.getValue())
              {
                _iter191.write(oprot);
              }
            }
          }
        }
      }
      if (struct.isSetContractDataInfos()) {
        {
          oprot.writeI32(struct.contractDataInfos.size());
          for (Map.Entry<Long, Map<Long,List<HostingRiskItemDataInfo>>> _iter192 : struct.contractDataInfos.entrySet())
          {
            oprot.writeI64(_iter192.getKey());
            {
              oprot.writeI32(_iter192.getValue().size());
              for (Map.Entry<Long, List<HostingRiskItemDataInfo>> _iter193 : _iter192.getValue().entrySet())
              {
                oprot.writeI64(_iter193.getKey());
                {
                  oprot.writeI32(_iter193.getValue().size());
                  for (HostingRiskItemDataInfo _iter194 : _iter193.getValue())
                  {
                    _iter194.write(oprot);
                  }
                }
              }
            }
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingRiskFrameDataInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list195 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.globalDataInfos = new ArrayList<HostingRiskItemDataInfo>(_list195.size);
          for (int _i196 = 0; _i196 < _list195.size; ++_i196)
          {
            HostingRiskItemDataInfo _elem197;
            _elem197 = new HostingRiskItemDataInfo();
            _elem197.read(iprot);
            struct.globalDataInfos.add(_elem197);
          }
        }
        struct.setGlobalDataInfosIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map198 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.LIST, iprot.readI32());
          struct.commodityDataInfos = new HashMap<Long,List<HostingRiskItemDataInfo>>(2*_map198.size);
          for (int _i199 = 0; _i199 < _map198.size; ++_i199)
          {
            long _key200;
            List<HostingRiskItemDataInfo> _val201;
            _key200 = iprot.readI64();
            {
              org.apache.thrift.protocol.TList _list202 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
              _val201 = new ArrayList<HostingRiskItemDataInfo>(_list202.size);
              for (int _i203 = 0; _i203 < _list202.size; ++_i203)
              {
                HostingRiskItemDataInfo _elem204;
                _elem204 = new HostingRiskItemDataInfo();
                _elem204.read(iprot);
                _val201.add(_elem204);
              }
            }
            struct.commodityDataInfos.put(_key200, _val201);
          }
        }
        struct.setCommodityDataInfosIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TMap _map205 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.MAP, iprot.readI32());
          struct.contractDataInfos = new HashMap<Long,Map<Long,List<HostingRiskItemDataInfo>>>(2*_map205.size);
          for (int _i206 = 0; _i206 < _map205.size; ++_i206)
          {
            long _key207;
            Map<Long,List<HostingRiskItemDataInfo>> _val208;
            _key207 = iprot.readI64();
            {
              org.apache.thrift.protocol.TMap _map209 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.LIST, iprot.readI32());
              _val208 = new HashMap<Long,List<HostingRiskItemDataInfo>>(2*_map209.size);
              for (int _i210 = 0; _i210 < _map209.size; ++_i210)
              {
                long _key211;
                List<HostingRiskItemDataInfo> _val212;
                _key211 = iprot.readI64();
                {
                  org.apache.thrift.protocol.TList _list213 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
                  _val212 = new ArrayList<HostingRiskItemDataInfo>(_list213.size);
                  for (int _i214 = 0; _i214 < _list213.size; ++_i214)
                  {
                    HostingRiskItemDataInfo _elem215;
                    _elem215 = new HostingRiskItemDataInfo();
                    _elem215.read(iprot);
                    _val212.add(_elem215);
                  }
                }
                _val208.put(_key211, _val212);
              }
            }
            struct.contractDataInfos.put(_key207, _val208);
          }
        }
        struct.setContractDataInfosIsSet(true);
      }
    }
  }

}
