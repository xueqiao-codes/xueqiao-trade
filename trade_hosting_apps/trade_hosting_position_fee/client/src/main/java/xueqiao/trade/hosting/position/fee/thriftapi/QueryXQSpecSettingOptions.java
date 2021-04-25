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

public class QueryXQSpecSettingOptions implements org.apache.thrift.TBase<QueryXQSpecSettingOptions, QueryXQSpecSettingOptions._Fields>, java.io.Serializable, Cloneable, Comparable<QueryXQSpecSettingOptions> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryXQSpecSettingOptions");

  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField EXCHANGE_MIC_FIELD_DESC = new org.apache.thrift.protocol.TField("exchangeMic", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField COMMODITY_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("commodityIds", org.apache.thrift.protocol.TType.SET, (short)3);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryXQSpecSettingOptionsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryXQSpecSettingOptionsTupleSchemeFactory());
  }

  public long subAccountId; // optional
  public String exchangeMic; // optional
  public Set<Long> commodityIds; // optional
  /**
   * 
   * @see FeeCalculateType
   */
  public FeeCalculateType type; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUB_ACCOUNT_ID((short)1, "subAccountId"),
    EXCHANGE_MIC((short)2, "exchangeMic"),
    COMMODITY_IDS((short)3, "commodityIds"),
    /**
     * 
     * @see FeeCalculateType
     */
    TYPE((short)4, "type");

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
        case 2: // EXCHANGE_MIC
          return EXCHANGE_MIC;
        case 3: // COMMODITY_IDS
          return COMMODITY_IDS;
        case 4: // TYPE
          return TYPE;
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
  private _Fields optionals[] = {_Fields.SUB_ACCOUNT_ID,_Fields.EXCHANGE_MIC,_Fields.COMMODITY_IDS,_Fields.TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.EXCHANGE_MIC, new org.apache.thrift.meta_data.FieldMetaData("exchangeMic", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMMODITY_IDS, new org.apache.thrift.meta_data.FieldMetaData("commodityIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, FeeCalculateType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryXQSpecSettingOptions.class, metaDataMap);
  }

  public QueryXQSpecSettingOptions() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryXQSpecSettingOptions(QueryXQSpecSettingOptions other) {
    __isset_bitfield = other.__isset_bitfield;
    this.subAccountId = other.subAccountId;
    if (other.isSetExchangeMic()) {
      this.exchangeMic = other.exchangeMic;
    }
    if (other.isSetCommodityIds()) {
      Set<Long> __this__commodityIds = new HashSet<Long>(other.commodityIds);
      this.commodityIds = __this__commodityIds;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
  }

  public QueryXQSpecSettingOptions deepCopy() {
    return new QueryXQSpecSettingOptions(this);
  }

  @Override
  public void clear() {
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.exchangeMic = null;
    this.commodityIds = null;
    this.type = null;
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public QueryXQSpecSettingOptions setSubAccountId(long subAccountId) {
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

  public String getExchangeMic() {
    return this.exchangeMic;
  }

  public QueryXQSpecSettingOptions setExchangeMic(String exchangeMic) {
    this.exchangeMic = exchangeMic;
    return this;
  }

  public void unsetExchangeMic() {
    this.exchangeMic = null;
  }

  /** Returns true if field exchangeMic is set (has been assigned a value) and false otherwise */
  public boolean isSetExchangeMic() {
    return this.exchangeMic != null;
  }

  public void setExchangeMicIsSet(boolean value) {
    if (!value) {
      this.exchangeMic = null;
    }
  }

  public int getCommodityIdsSize() {
    return (this.commodityIds == null) ? 0 : this.commodityIds.size();
  }

  public java.util.Iterator<Long> getCommodityIdsIterator() {
    return (this.commodityIds == null) ? null : this.commodityIds.iterator();
  }

  public void addToCommodityIds(long elem) {
    if (this.commodityIds == null) {
      this.commodityIds = new HashSet<Long>();
    }
    this.commodityIds.add(elem);
  }

  public Set<Long> getCommodityIds() {
    return this.commodityIds;
  }

  public QueryXQSpecSettingOptions setCommodityIds(Set<Long> commodityIds) {
    this.commodityIds = commodityIds;
    return this;
  }

  public void unsetCommodityIds() {
    this.commodityIds = null;
  }

  /** Returns true if field commodityIds is set (has been assigned a value) and false otherwise */
  public boolean isSetCommodityIds() {
    return this.commodityIds != null;
  }

  public void setCommodityIdsIsSet(boolean value) {
    if (!value) {
      this.commodityIds = null;
    }
  }

  /**
   * 
   * @see FeeCalculateType
   */
  public FeeCalculateType getType() {
    return this.type;
  }

  /**
   * 
   * @see FeeCalculateType
   */
  public QueryXQSpecSettingOptions setType(FeeCalculateType type) {
    this.type = type;
    return this;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
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

    case EXCHANGE_MIC:
      if (value == null) {
        unsetExchangeMic();
      } else {
        setExchangeMic((String)value);
      }
      break;

    case COMMODITY_IDS:
      if (value == null) {
        unsetCommodityIds();
      } else {
        setCommodityIds((Set<Long>)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((FeeCalculateType)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case EXCHANGE_MIC:
      return getExchangeMic();

    case COMMODITY_IDS:
      return getCommodityIds();

    case TYPE:
      return getType();

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
    case EXCHANGE_MIC:
      return isSetExchangeMic();
    case COMMODITY_IDS:
      return isSetCommodityIds();
    case TYPE:
      return isSetType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryXQSpecSettingOptions)
      return this.equals((QueryXQSpecSettingOptions)that);
    return false;
  }

  public boolean equals(QueryXQSpecSettingOptions that) {
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

    boolean this_present_exchangeMic = true && this.isSetExchangeMic();
    boolean that_present_exchangeMic = true && that.isSetExchangeMic();
    if (this_present_exchangeMic || that_present_exchangeMic) {
      if (!(this_present_exchangeMic && that_present_exchangeMic))
        return false;
      if (!this.exchangeMic.equals(that.exchangeMic))
        return false;
    }

    boolean this_present_commodityIds = true && this.isSetCommodityIds();
    boolean that_present_commodityIds = true && that.isSetCommodityIds();
    if (this_present_commodityIds || that_present_commodityIds) {
      if (!(this_present_commodityIds && that_present_commodityIds))
        return false;
      if (!this.commodityIds.equals(that.commodityIds))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryXQSpecSettingOptions other) {
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
    lastComparison = Boolean.valueOf(isSetExchangeMic()).compareTo(other.isSetExchangeMic());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetExchangeMic()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.exchangeMic, other.exchangeMic);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCommodityIds()).compareTo(other.isSetCommodityIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCommodityIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.commodityIds, other.commodityIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
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
    StringBuilder sb = new StringBuilder("QueryXQSpecSettingOptions(");
    boolean first = true;

    if (isSetSubAccountId()) {
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetExchangeMic()) {
      if (!first) sb.append(", ");
      sb.append("exchangeMic:");
      if (this.exchangeMic == null) {
        sb.append("null");
      } else {
        sb.append(this.exchangeMic);
      }
      first = false;
    }
    if (isSetCommodityIds()) {
      if (!first) sb.append(", ");
      sb.append("commodityIds:");
      if (this.commodityIds == null) {
        sb.append("null");
      } else {
        sb.append(this.commodityIds);
      }
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
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

  private static class QueryXQSpecSettingOptionsStandardSchemeFactory implements SchemeFactory {
    public QueryXQSpecSettingOptionsStandardScheme getScheme() {
      return new QueryXQSpecSettingOptionsStandardScheme();
    }
  }

  private static class QueryXQSpecSettingOptionsStandardScheme extends StandardScheme<QueryXQSpecSettingOptions> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryXQSpecSettingOptions struct) throws org.apache.thrift.TException {
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
          case 2: // EXCHANGE_MIC
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.exchangeMic = iprot.readString();
              struct.setExchangeMicIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // COMMODITY_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set104 = iprot.readSetBegin();
                struct.commodityIds = new HashSet<Long>(2*_set104.size);
                for (int _i105 = 0; _i105 < _set104.size; ++_i105)
                {
                  long _elem106;
                  _elem106 = iprot.readI64();
                  struct.commodityIds.add(_elem106);
                }
                iprot.readSetEnd();
              }
              struct.setCommodityIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = FeeCalculateType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryXQSpecSettingOptions struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.exchangeMic != null) {
        if (struct.isSetExchangeMic()) {
          oprot.writeFieldBegin(EXCHANGE_MIC_FIELD_DESC);
          oprot.writeString(struct.exchangeMic);
          oprot.writeFieldEnd();
        }
      }
      if (struct.commodityIds != null) {
        if (struct.isSetCommodityIds()) {
          oprot.writeFieldBegin(COMMODITY_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, struct.commodityIds.size()));
            for (long _iter107 : struct.commodityIds)
            {
              oprot.writeI64(_iter107);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeI32(struct.type.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryXQSpecSettingOptionsTupleSchemeFactory implements SchemeFactory {
    public QueryXQSpecSettingOptionsTupleScheme getScheme() {
      return new QueryXQSpecSettingOptionsTupleScheme();
    }
  }

  private static class QueryXQSpecSettingOptionsTupleScheme extends TupleScheme<QueryXQSpecSettingOptions> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryXQSpecSettingOptions struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSubAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetExchangeMic()) {
        optionals.set(1);
      }
      if (struct.isSetCommodityIds()) {
        optionals.set(2);
      }
      if (struct.isSetType()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetExchangeMic()) {
        oprot.writeString(struct.exchangeMic);
      }
      if (struct.isSetCommodityIds()) {
        {
          oprot.writeI32(struct.commodityIds.size());
          for (long _iter108 : struct.commodityIds)
          {
            oprot.writeI64(_iter108);
          }
        }
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryXQSpecSettingOptions struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.exchangeMic = iprot.readString();
        struct.setExchangeMicIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TSet _set109 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.commodityIds = new HashSet<Long>(2*_set109.size);
          for (int _i110 = 0; _i110 < _set109.size; ++_i110)
          {
            long _elem111;
            _elem111 = iprot.readI64();
            struct.commodityIds.add(_elem111);
          }
        }
        struct.setCommodityIdsIsSet(true);
      }
      if (incoming.get(3)) {
        struct.type = FeeCalculateType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
    }
  }

}

