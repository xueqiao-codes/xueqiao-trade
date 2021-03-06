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

public class HostingXQTradeSummary implements org.apache.thrift.TBase<HostingXQTradeSummary, HostingXQTradeSummary._Fields>, java.io.Serializable, Cloneable, Comparable<HostingXQTradeSummary> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingXQTradeSummary");

  private static final org.apache.thrift.protocol.TField TOTAL_VOLUME_FIELD_DESC = new org.apache.thrift.protocol.TField("totalVolume", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField AVERAGE_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("averagePrice", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField SUB_TRADE_SUMMARIES_FIELD_DESC = new org.apache.thrift.protocol.TField("subTradeSummaries", org.apache.thrift.protocol.TType.MAP, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingXQTradeSummaryStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingXQTradeSummaryTupleSchemeFactory());
  }

  public int totalVolume; // optional
  public double averagePrice; // optional
  public Map<String,HostingXQSubTradeSummary> subTradeSummaries; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_VOLUME((short)1, "totalVolume"),
    AVERAGE_PRICE((short)2, "averagePrice"),
    SUB_TRADE_SUMMARIES((short)3, "subTradeSummaries");

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
        case 1: // TOTAL_VOLUME
          return TOTAL_VOLUME;
        case 2: // AVERAGE_PRICE
          return AVERAGE_PRICE;
        case 3: // SUB_TRADE_SUMMARIES
          return SUB_TRADE_SUMMARIES;
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
  private static final int __TOTALVOLUME_ISSET_ID = 0;
  private static final int __AVERAGEPRICE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TOTAL_VOLUME,_Fields.AVERAGE_PRICE,_Fields.SUB_TRADE_SUMMARIES};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_VOLUME, new org.apache.thrift.meta_data.FieldMetaData("totalVolume", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.AVERAGE_PRICE, new org.apache.thrift.meta_data.FieldMetaData("averagePrice", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.SUB_TRADE_SUMMARIES, new org.apache.thrift.meta_data.FieldMetaData("subTradeSummaries", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingXQSubTradeSummary.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingXQTradeSummary.class, metaDataMap);
  }

  public HostingXQTradeSummary() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingXQTradeSummary(HostingXQTradeSummary other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalVolume = other.totalVolume;
    this.averagePrice = other.averagePrice;
    if (other.isSetSubTradeSummaries()) {
      Map<String,HostingXQSubTradeSummary> __this__subTradeSummaries = new HashMap<String,HostingXQSubTradeSummary>(other.subTradeSummaries.size());
      for (Map.Entry<String, HostingXQSubTradeSummary> other_element : other.subTradeSummaries.entrySet()) {

        String other_element_key = other_element.getKey();
        HostingXQSubTradeSummary other_element_value = other_element.getValue();

        String __this__subTradeSummaries_copy_key = other_element_key;

        HostingXQSubTradeSummary __this__subTradeSummaries_copy_value = new HostingXQSubTradeSummary(other_element_value);

        __this__subTradeSummaries.put(__this__subTradeSummaries_copy_key, __this__subTradeSummaries_copy_value);
      }
      this.subTradeSummaries = __this__subTradeSummaries;
    }
  }

  public HostingXQTradeSummary deepCopy() {
    return new HostingXQTradeSummary(this);
  }

  @Override
  public void clear() {
    setTotalVolumeIsSet(false);
    this.totalVolume = 0;
    setAveragePriceIsSet(false);
    this.averagePrice = 0.0;
    this.subTradeSummaries = null;
  }

  public int getTotalVolume() {
    return this.totalVolume;
  }

  public HostingXQTradeSummary setTotalVolume(int totalVolume) {
    this.totalVolume = totalVolume;
    setTotalVolumeIsSet(true);
    return this;
  }

  public void unsetTotalVolume() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALVOLUME_ISSET_ID);
  }

  /** Returns true if field totalVolume is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalVolume() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALVOLUME_ISSET_ID);
  }

  public void setTotalVolumeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALVOLUME_ISSET_ID, value);
  }

  public double getAveragePrice() {
    return this.averagePrice;
  }

  public HostingXQTradeSummary setAveragePrice(double averagePrice) {
    this.averagePrice = averagePrice;
    setAveragePriceIsSet(true);
    return this;
  }

  public void unsetAveragePrice() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AVERAGEPRICE_ISSET_ID);
  }

  /** Returns true if field averagePrice is set (has been assigned a value) and false otherwise */
  public boolean isSetAveragePrice() {
    return EncodingUtils.testBit(__isset_bitfield, __AVERAGEPRICE_ISSET_ID);
  }

  public void setAveragePriceIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AVERAGEPRICE_ISSET_ID, value);
  }

  public int getSubTradeSummariesSize() {
    return (this.subTradeSummaries == null) ? 0 : this.subTradeSummaries.size();
  }

  public void putToSubTradeSummaries(String key, HostingXQSubTradeSummary val) {
    if (this.subTradeSummaries == null) {
      this.subTradeSummaries = new HashMap<String,HostingXQSubTradeSummary>();
    }
    this.subTradeSummaries.put(key, val);
  }

  public Map<String,HostingXQSubTradeSummary> getSubTradeSummaries() {
    return this.subTradeSummaries;
  }

  public HostingXQTradeSummary setSubTradeSummaries(Map<String,HostingXQSubTradeSummary> subTradeSummaries) {
    this.subTradeSummaries = subTradeSummaries;
    return this;
  }

  public void unsetSubTradeSummaries() {
    this.subTradeSummaries = null;
  }

  /** Returns true if field subTradeSummaries is set (has been assigned a value) and false otherwise */
  public boolean isSetSubTradeSummaries() {
    return this.subTradeSummaries != null;
  }

  public void setSubTradeSummariesIsSet(boolean value) {
    if (!value) {
      this.subTradeSummaries = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_VOLUME:
      if (value == null) {
        unsetTotalVolume();
      } else {
        setTotalVolume((Integer)value);
      }
      break;

    case AVERAGE_PRICE:
      if (value == null) {
        unsetAveragePrice();
      } else {
        setAveragePrice((Double)value);
      }
      break;

    case SUB_TRADE_SUMMARIES:
      if (value == null) {
        unsetSubTradeSummaries();
      } else {
        setSubTradeSummaries((Map<String,HostingXQSubTradeSummary>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_VOLUME:
      return Integer.valueOf(getTotalVolume());

    case AVERAGE_PRICE:
      return Double.valueOf(getAveragePrice());

    case SUB_TRADE_SUMMARIES:
      return getSubTradeSummaries();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_VOLUME:
      return isSetTotalVolume();
    case AVERAGE_PRICE:
      return isSetAveragePrice();
    case SUB_TRADE_SUMMARIES:
      return isSetSubTradeSummaries();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingXQTradeSummary)
      return this.equals((HostingXQTradeSummary)that);
    return false;
  }

  public boolean equals(HostingXQTradeSummary that) {
    if (that == null)
      return false;

    boolean this_present_totalVolume = true && this.isSetTotalVolume();
    boolean that_present_totalVolume = true && that.isSetTotalVolume();
    if (this_present_totalVolume || that_present_totalVolume) {
      if (!(this_present_totalVolume && that_present_totalVolume))
        return false;
      if (this.totalVolume != that.totalVolume)
        return false;
    }

    boolean this_present_averagePrice = true && this.isSetAveragePrice();
    boolean that_present_averagePrice = true && that.isSetAveragePrice();
    if (this_present_averagePrice || that_present_averagePrice) {
      if (!(this_present_averagePrice && that_present_averagePrice))
        return false;
      if (this.averagePrice != that.averagePrice)
        return false;
    }

    boolean this_present_subTradeSummaries = true && this.isSetSubTradeSummaries();
    boolean that_present_subTradeSummaries = true && that.isSetSubTradeSummaries();
    if (this_present_subTradeSummaries || that_present_subTradeSummaries) {
      if (!(this_present_subTradeSummaries && that_present_subTradeSummaries))
        return false;
      if (!this.subTradeSummaries.equals(that.subTradeSummaries))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingXQTradeSummary other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTotalVolume()).compareTo(other.isSetTotalVolume());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalVolume()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalVolume, other.totalVolume);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAveragePrice()).compareTo(other.isSetAveragePrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAveragePrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.averagePrice, other.averagePrice);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubTradeSummaries()).compareTo(other.isSetSubTradeSummaries());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubTradeSummaries()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subTradeSummaries, other.subTradeSummaries);
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
    StringBuilder sb = new StringBuilder("HostingXQTradeSummary(");
    boolean first = true;

    if (isSetTotalVolume()) {
      sb.append("totalVolume:");
      sb.append(this.totalVolume);
      first = false;
    }
    if (isSetAveragePrice()) {
      if (!first) sb.append(", ");
      sb.append("averagePrice:");
      sb.append(this.averagePrice);
      first = false;
    }
    if (isSetSubTradeSummaries()) {
      if (!first) sb.append(", ");
      sb.append("subTradeSummaries:");
      if (this.subTradeSummaries == null) {
        sb.append("null");
      } else {
        sb.append(this.subTradeSummaries);
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

  private static class HostingXQTradeSummaryStandardSchemeFactory implements SchemeFactory {
    public HostingXQTradeSummaryStandardScheme getScheme() {
      return new HostingXQTradeSummaryStandardScheme();
    }
  }

  private static class HostingXQTradeSummaryStandardScheme extends StandardScheme<HostingXQTradeSummary> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingXQTradeSummary struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_VOLUME
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalVolume = iprot.readI32();
              struct.setTotalVolumeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // AVERAGE_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.averagePrice = iprot.readDouble();
              struct.setAveragePriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SUB_TRADE_SUMMARIES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map146 = iprot.readMapBegin();
                struct.subTradeSummaries = new HashMap<String,HostingXQSubTradeSummary>(2*_map146.size);
                for (int _i147 = 0; _i147 < _map146.size; ++_i147)
                {
                  String _key148;
                  HostingXQSubTradeSummary _val149;
                  _key148 = iprot.readString();
                  _val149 = new HostingXQSubTradeSummary();
                  _val149.read(iprot);
                  struct.subTradeSummaries.put(_key148, _val149);
                }
                iprot.readMapEnd();
              }
              struct.setSubTradeSummariesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingXQTradeSummary struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalVolume()) {
        oprot.writeFieldBegin(TOTAL_VOLUME_FIELD_DESC);
        oprot.writeI32(struct.totalVolume);
        oprot.writeFieldEnd();
      }
      if (struct.isSetAveragePrice()) {
        oprot.writeFieldBegin(AVERAGE_PRICE_FIELD_DESC);
        oprot.writeDouble(struct.averagePrice);
        oprot.writeFieldEnd();
      }
      if (struct.subTradeSummaries != null) {
        if (struct.isSetSubTradeSummaries()) {
          oprot.writeFieldBegin(SUB_TRADE_SUMMARIES_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.subTradeSummaries.size()));
            for (Map.Entry<String, HostingXQSubTradeSummary> _iter150 : struct.subTradeSummaries.entrySet())
            {
              oprot.writeString(_iter150.getKey());
              _iter150.getValue().write(oprot);
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

  private static class HostingXQTradeSummaryTupleSchemeFactory implements SchemeFactory {
    public HostingXQTradeSummaryTupleScheme getScheme() {
      return new HostingXQTradeSummaryTupleScheme();
    }
  }

  private static class HostingXQTradeSummaryTupleScheme extends TupleScheme<HostingXQTradeSummary> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingXQTradeSummary struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalVolume()) {
        optionals.set(0);
      }
      if (struct.isSetAveragePrice()) {
        optionals.set(1);
      }
      if (struct.isSetSubTradeSummaries()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetTotalVolume()) {
        oprot.writeI32(struct.totalVolume);
      }
      if (struct.isSetAveragePrice()) {
        oprot.writeDouble(struct.averagePrice);
      }
      if (struct.isSetSubTradeSummaries()) {
        {
          oprot.writeI32(struct.subTradeSummaries.size());
          for (Map.Entry<String, HostingXQSubTradeSummary> _iter151 : struct.subTradeSummaries.entrySet())
          {
            oprot.writeString(_iter151.getKey());
            _iter151.getValue().write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingXQTradeSummary struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.totalVolume = iprot.readI32();
        struct.setTotalVolumeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.averagePrice = iprot.readDouble();
        struct.setAveragePriceIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TMap _map152 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.subTradeSummaries = new HashMap<String,HostingXQSubTradeSummary>(2*_map152.size);
          for (int _i153 = 0; _i153 < _map152.size; ++_i153)
          {
            String _key154;
            HostingXQSubTradeSummary _val155;
            _key154 = iprot.readString();
            _val155 = new HostingXQSubTradeSummary();
            _val155.read(iprot);
            struct.subTradeSummaries.put(_key154, _val155);
          }
        }
        struct.setSubTradeSummariesIsSet(true);
      }
    }
  }

}

