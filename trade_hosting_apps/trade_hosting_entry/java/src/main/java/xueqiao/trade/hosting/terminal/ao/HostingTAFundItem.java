/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.terminal.ao;

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

public class HostingTAFundItem implements org.apache.thrift.TBase<HostingTAFundItem, HostingTAFundItem._Fields>, java.io.Serializable, Cloneable, Comparable<HostingTAFundItem> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingTAFundItem");

  private static final org.apache.thrift.protocol.TField TRADE_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeAccountId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField UPDATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("updateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField TOTAL_FUND_FIELD_DESC = new org.apache.thrift.protocol.TField("totalFund", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField GROUP_FUNDS_FIELD_DESC = new org.apache.thrift.protocol.TField("groupFunds", org.apache.thrift.protocol.TType.MAP, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingTAFundItemStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingTAFundItemTupleSchemeFactory());
  }

  public long tradeAccountId; // optional
  public long updateTimestampMs; // optional
  public xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund totalFund; // optional
  public Map<String,HostingTAFundCurrencyGroup> groupFunds; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TRADE_ACCOUNT_ID((short)1, "tradeAccountId"),
    UPDATE_TIMESTAMP_MS((short)2, "updateTimestampMs"),
    TOTAL_FUND((short)3, "totalFund"),
    GROUP_FUNDS((short)4, "groupFunds");

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
        case 2: // UPDATE_TIMESTAMP_MS
          return UPDATE_TIMESTAMP_MS;
        case 3: // TOTAL_FUND
          return TOTAL_FUND;
        case 4: // GROUP_FUNDS
          return GROUP_FUNDS;
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
  private static final int __UPDATETIMESTAMPMS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TRADE_ACCOUNT_ID,_Fields.UPDATE_TIMESTAMP_MS,_Fields.TOTAL_FUND,_Fields.GROUP_FUNDS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TRADE_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("tradeAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.UPDATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("updateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TOTAL_FUND, new org.apache.thrift.meta_data.FieldMetaData("totalFund", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund.class)));
    tmpMap.put(_Fields.GROUP_FUNDS, new org.apache.thrift.meta_data.FieldMetaData("groupFunds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingTAFundCurrencyGroup.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingTAFundItem.class, metaDataMap);
  }

  public HostingTAFundItem() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingTAFundItem(HostingTAFundItem other) {
    __isset_bitfield = other.__isset_bitfield;
    this.tradeAccountId = other.tradeAccountId;
    this.updateTimestampMs = other.updateTimestampMs;
    if (other.isSetTotalFund()) {
      this.totalFund = new xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund(other.totalFund);
    }
    if (other.isSetGroupFunds()) {
      Map<String,HostingTAFundCurrencyGroup> __this__groupFunds = new HashMap<String,HostingTAFundCurrencyGroup>(other.groupFunds.size());
      for (Map.Entry<String, HostingTAFundCurrencyGroup> other_element : other.groupFunds.entrySet()) {

        String other_element_key = other_element.getKey();
        HostingTAFundCurrencyGroup other_element_value = other_element.getValue();

        String __this__groupFunds_copy_key = other_element_key;

        HostingTAFundCurrencyGroup __this__groupFunds_copy_value = new HostingTAFundCurrencyGroup(other_element_value);

        __this__groupFunds.put(__this__groupFunds_copy_key, __this__groupFunds_copy_value);
      }
      this.groupFunds = __this__groupFunds;
    }
  }

  public HostingTAFundItem deepCopy() {
    return new HostingTAFundItem(this);
  }

  @Override
  public void clear() {
    setTradeAccountIdIsSet(false);
    this.tradeAccountId = 0;
    setUpdateTimestampMsIsSet(false);
    this.updateTimestampMs = 0;
    this.totalFund = null;
    this.groupFunds = null;
  }

  public long getTradeAccountId() {
    return this.tradeAccountId;
  }

  public HostingTAFundItem setTradeAccountId(long tradeAccountId) {
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

  public long getUpdateTimestampMs() {
    return this.updateTimestampMs;
  }

  public HostingTAFundItem setUpdateTimestampMs(long updateTimestampMs) {
    this.updateTimestampMs = updateTimestampMs;
    setUpdateTimestampMsIsSet(true);
    return this;
  }

  public void unsetUpdateTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UPDATETIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field updateTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetUpdateTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __UPDATETIMESTAMPMS_ISSET_ID);
  }

  public void setUpdateTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UPDATETIMESTAMPMS_ISSET_ID, value);
  }

  public xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund getTotalFund() {
    return this.totalFund;
  }

  public HostingTAFundItem setTotalFund(xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund totalFund) {
    this.totalFund = totalFund;
    return this;
  }

  public void unsetTotalFund() {
    this.totalFund = null;
  }

  /** Returns true if field totalFund is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalFund() {
    return this.totalFund != null;
  }

  public void setTotalFundIsSet(boolean value) {
    if (!value) {
      this.totalFund = null;
    }
  }

  public int getGroupFundsSize() {
    return (this.groupFunds == null) ? 0 : this.groupFunds.size();
  }

  public void putToGroupFunds(String key, HostingTAFundCurrencyGroup val) {
    if (this.groupFunds == null) {
      this.groupFunds = new HashMap<String,HostingTAFundCurrencyGroup>();
    }
    this.groupFunds.put(key, val);
  }

  public Map<String,HostingTAFundCurrencyGroup> getGroupFunds() {
    return this.groupFunds;
  }

  public HostingTAFundItem setGroupFunds(Map<String,HostingTAFundCurrencyGroup> groupFunds) {
    this.groupFunds = groupFunds;
    return this;
  }

  public void unsetGroupFunds() {
    this.groupFunds = null;
  }

  /** Returns true if field groupFunds is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupFunds() {
    return this.groupFunds != null;
  }

  public void setGroupFundsIsSet(boolean value) {
    if (!value) {
      this.groupFunds = null;
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

    case UPDATE_TIMESTAMP_MS:
      if (value == null) {
        unsetUpdateTimestampMs();
      } else {
        setUpdateTimestampMs((Long)value);
      }
      break;

    case TOTAL_FUND:
      if (value == null) {
        unsetTotalFund();
      } else {
        setTotalFund((xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund)value);
      }
      break;

    case GROUP_FUNDS:
      if (value == null) {
        unsetGroupFunds();
      } else {
        setGroupFunds((Map<String,HostingTAFundCurrencyGroup>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TRADE_ACCOUNT_ID:
      return Long.valueOf(getTradeAccountId());

    case UPDATE_TIMESTAMP_MS:
      return Long.valueOf(getUpdateTimestampMs());

    case TOTAL_FUND:
      return getTotalFund();

    case GROUP_FUNDS:
      return getGroupFunds();

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
    case UPDATE_TIMESTAMP_MS:
      return isSetUpdateTimestampMs();
    case TOTAL_FUND:
      return isSetTotalFund();
    case GROUP_FUNDS:
      return isSetGroupFunds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingTAFundItem)
      return this.equals((HostingTAFundItem)that);
    return false;
  }

  public boolean equals(HostingTAFundItem that) {
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

    boolean this_present_updateTimestampMs = true && this.isSetUpdateTimestampMs();
    boolean that_present_updateTimestampMs = true && that.isSetUpdateTimestampMs();
    if (this_present_updateTimestampMs || that_present_updateTimestampMs) {
      if (!(this_present_updateTimestampMs && that_present_updateTimestampMs))
        return false;
      if (this.updateTimestampMs != that.updateTimestampMs)
        return false;
    }

    boolean this_present_totalFund = true && this.isSetTotalFund();
    boolean that_present_totalFund = true && that.isSetTotalFund();
    if (this_present_totalFund || that_present_totalFund) {
      if (!(this_present_totalFund && that_present_totalFund))
        return false;
      if (!this.totalFund.equals(that.totalFund))
        return false;
    }

    boolean this_present_groupFunds = true && this.isSetGroupFunds();
    boolean that_present_groupFunds = true && that.isSetGroupFunds();
    if (this_present_groupFunds || that_present_groupFunds) {
      if (!(this_present_groupFunds && that_present_groupFunds))
        return false;
      if (!this.groupFunds.equals(that.groupFunds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingTAFundItem other) {
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
    lastComparison = Boolean.valueOf(isSetUpdateTimestampMs()).compareTo(other.isSetUpdateTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpdateTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.updateTimestampMs, other.updateTimestampMs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTotalFund()).compareTo(other.isSetTotalFund());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalFund()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalFund, other.totalFund);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupFunds()).compareTo(other.isSetGroupFunds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupFunds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupFunds, other.groupFunds);
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
    StringBuilder sb = new StringBuilder("HostingTAFundItem(");
    boolean first = true;

    if (isSetTradeAccountId()) {
      sb.append("tradeAccountId:");
      sb.append(this.tradeAccountId);
      first = false;
    }
    if (isSetUpdateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("updateTimestampMs:");
      sb.append(this.updateTimestampMs);
      first = false;
    }
    if (isSetTotalFund()) {
      if (!first) sb.append(", ");
      sb.append("totalFund:");
      if (this.totalFund == null) {
        sb.append("null");
      } else {
        sb.append(this.totalFund);
      }
      first = false;
    }
    if (isSetGroupFunds()) {
      if (!first) sb.append(", ");
      sb.append("groupFunds:");
      if (this.groupFunds == null) {
        sb.append("null");
      } else {
        sb.append(this.groupFunds);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (totalFund != null) {
      totalFund.validate();
    }
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

  private static class HostingTAFundItemStandardSchemeFactory implements SchemeFactory {
    public HostingTAFundItemStandardScheme getScheme() {
      return new HostingTAFundItemStandardScheme();
    }
  }

  private static class HostingTAFundItemStandardScheme extends StandardScheme<HostingTAFundItem> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingTAFundItem struct) throws org.apache.thrift.TException {
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
          case 2: // UPDATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.updateTimestampMs = iprot.readI64();
              struct.setUpdateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TOTAL_FUND
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.totalFund = new xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund();
              struct.totalFund.read(iprot);
              struct.setTotalFundIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // GROUP_FUNDS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map176 = iprot.readMapBegin();
                struct.groupFunds = new HashMap<String,HostingTAFundCurrencyGroup>(2*_map176.size);
                for (int _i177 = 0; _i177 < _map176.size; ++_i177)
                {
                  String _key178;
                  HostingTAFundCurrencyGroup _val179;
                  _key178 = iprot.readString();
                  _val179 = new HostingTAFundCurrencyGroup();
                  _val179.read(iprot);
                  struct.groupFunds.put(_key178, _val179);
                }
                iprot.readMapEnd();
              }
              struct.setGroupFundsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingTAFundItem struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTradeAccountId()) {
        oprot.writeFieldBegin(TRADE_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.tradeAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUpdateTimestampMs()) {
        oprot.writeFieldBegin(UPDATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.updateTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.totalFund != null) {
        if (struct.isSetTotalFund()) {
          oprot.writeFieldBegin(TOTAL_FUND_FIELD_DESC);
          struct.totalFund.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.groupFunds != null) {
        if (struct.isSetGroupFunds()) {
          oprot.writeFieldBegin(GROUP_FUNDS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, struct.groupFunds.size()));
            for (Map.Entry<String, HostingTAFundCurrencyGroup> _iter180 : struct.groupFunds.entrySet())
            {
              oprot.writeString(_iter180.getKey());
              _iter180.getValue().write(oprot);
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

  private static class HostingTAFundItemTupleSchemeFactory implements SchemeFactory {
    public HostingTAFundItemTupleScheme getScheme() {
      return new HostingTAFundItemTupleScheme();
    }
  }

  private static class HostingTAFundItemTupleScheme extends TupleScheme<HostingTAFundItem> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingTAFundItem struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTradeAccountId()) {
        optionals.set(0);
      }
      if (struct.isSetUpdateTimestampMs()) {
        optionals.set(1);
      }
      if (struct.isSetTotalFund()) {
        optionals.set(2);
      }
      if (struct.isSetGroupFunds()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTradeAccountId()) {
        oprot.writeI64(struct.tradeAccountId);
      }
      if (struct.isSetUpdateTimestampMs()) {
        oprot.writeI64(struct.updateTimestampMs);
      }
      if (struct.isSetTotalFund()) {
        struct.totalFund.write(oprot);
      }
      if (struct.isSetGroupFunds()) {
        {
          oprot.writeI32(struct.groupFunds.size());
          for (Map.Entry<String, HostingTAFundCurrencyGroup> _iter181 : struct.groupFunds.entrySet())
          {
            oprot.writeString(_iter181.getKey());
            _iter181.getValue().write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingTAFundItem struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.tradeAccountId = iprot.readI64();
        struct.setTradeAccountIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.updateTimestampMs = iprot.readI64();
        struct.setUpdateTimestampMsIsSet(true);
      }
      if (incoming.get(2)) {
        struct.totalFund = new xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund();
        struct.totalFund.read(iprot);
        struct.setTotalFundIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TMap _map182 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.groupFunds = new HashMap<String,HostingTAFundCurrencyGroup>(2*_map182.size);
          for (int _i183 = 0; _i183 < _map182.size; ++_i183)
          {
            String _key184;
            HostingTAFundCurrencyGroup _val185;
            _key184 = iprot.readString();
            _val185 = new HostingTAFundCurrencyGroup();
            _val185.read(iprot);
            struct.groupFunds.put(_key184, _val185);
          }
        }
        struct.setGroupFundsIsSet(true);
      }
    }
  }

}

