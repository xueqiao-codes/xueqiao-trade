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

public class Margin implements org.apache.thrift.TBase<Margin, Margin._Fields>, java.io.Serializable, Cloneable, Comparable<Margin> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Margin");

  private static final org.apache.thrift.protocol.TField LONG_MARGIN_RATIO_BY_MONEY_FIELD_DESC = new org.apache.thrift.protocol.TField("longMarginRatioByMoney", org.apache.thrift.protocol.TType.DOUBLE, (short)1);
  private static final org.apache.thrift.protocol.TField LONG_MARGIN_RATIO_BY_VOLUME_FIELD_DESC = new org.apache.thrift.protocol.TField("longMarginRatioByVolume", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField SHORT_MARGIN_RATIO_BY_MONEY_FIELD_DESC = new org.apache.thrift.protocol.TField("shortMarginRatioByMoney", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField SHORT_MARGIN_RATIO_BY_VOLUME_FIELD_DESC = new org.apache.thrift.protocol.TField("shortMarginRatioByVolume", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField CURRENCY_FIELD_DESC = new org.apache.thrift.protocol.TField("currency", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField CURRENCY_GROUP_FIELD_DESC = new org.apache.thrift.protocol.TField("currencyGroup", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField CURRENCY_RATE_FIELD_DESC = new org.apache.thrift.protocol.TField("currencyRate", org.apache.thrift.protocol.TType.DOUBLE, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MarginStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MarginTupleSchemeFactory());
  }

  public double longMarginRatioByMoney; // optional
  public double longMarginRatioByVolume; // optional
  public double shortMarginRatioByMoney; // optional
  public double shortMarginRatioByVolume; // optional
  public String currency; // optional
  public String currencyGroup; // optional
  public double currencyRate; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    LONG_MARGIN_RATIO_BY_MONEY((short)1, "longMarginRatioByMoney"),
    LONG_MARGIN_RATIO_BY_VOLUME((short)2, "longMarginRatioByVolume"),
    SHORT_MARGIN_RATIO_BY_MONEY((short)3, "shortMarginRatioByMoney"),
    SHORT_MARGIN_RATIO_BY_VOLUME((short)4, "shortMarginRatioByVolume"),
    CURRENCY((short)5, "currency"),
    CURRENCY_GROUP((short)6, "currencyGroup"),
    CURRENCY_RATE((short)7, "currencyRate");

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
        case 1: // LONG_MARGIN_RATIO_BY_MONEY
          return LONG_MARGIN_RATIO_BY_MONEY;
        case 2: // LONG_MARGIN_RATIO_BY_VOLUME
          return LONG_MARGIN_RATIO_BY_VOLUME;
        case 3: // SHORT_MARGIN_RATIO_BY_MONEY
          return SHORT_MARGIN_RATIO_BY_MONEY;
        case 4: // SHORT_MARGIN_RATIO_BY_VOLUME
          return SHORT_MARGIN_RATIO_BY_VOLUME;
        case 5: // CURRENCY
          return CURRENCY;
        case 6: // CURRENCY_GROUP
          return CURRENCY_GROUP;
        case 7: // CURRENCY_RATE
          return CURRENCY_RATE;
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
  private static final int __LONGMARGINRATIOBYMONEY_ISSET_ID = 0;
  private static final int __LONGMARGINRATIOBYVOLUME_ISSET_ID = 1;
  private static final int __SHORTMARGINRATIOBYMONEY_ISSET_ID = 2;
  private static final int __SHORTMARGINRATIOBYVOLUME_ISSET_ID = 3;
  private static final int __CURRENCYRATE_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.LONG_MARGIN_RATIO_BY_MONEY,_Fields.LONG_MARGIN_RATIO_BY_VOLUME,_Fields.SHORT_MARGIN_RATIO_BY_MONEY,_Fields.SHORT_MARGIN_RATIO_BY_VOLUME,_Fields.CURRENCY,_Fields.CURRENCY_GROUP,_Fields.CURRENCY_RATE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.LONG_MARGIN_RATIO_BY_MONEY, new org.apache.thrift.meta_data.FieldMetaData("longMarginRatioByMoney", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.LONG_MARGIN_RATIO_BY_VOLUME, new org.apache.thrift.meta_data.FieldMetaData("longMarginRatioByVolume", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.SHORT_MARGIN_RATIO_BY_MONEY, new org.apache.thrift.meta_data.FieldMetaData("shortMarginRatioByMoney", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.SHORT_MARGIN_RATIO_BY_VOLUME, new org.apache.thrift.meta_data.FieldMetaData("shortMarginRatioByVolume", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.CURRENCY, new org.apache.thrift.meta_data.FieldMetaData("currency", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CURRENCY_GROUP, new org.apache.thrift.meta_data.FieldMetaData("currencyGroup", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CURRENCY_RATE, new org.apache.thrift.meta_data.FieldMetaData("currencyRate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Margin.class, metaDataMap);
  }

  public Margin() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Margin(Margin other) {
    __isset_bitfield = other.__isset_bitfield;
    this.longMarginRatioByMoney = other.longMarginRatioByMoney;
    this.longMarginRatioByVolume = other.longMarginRatioByVolume;
    this.shortMarginRatioByMoney = other.shortMarginRatioByMoney;
    this.shortMarginRatioByVolume = other.shortMarginRatioByVolume;
    if (other.isSetCurrency()) {
      this.currency = other.currency;
    }
    if (other.isSetCurrencyGroup()) {
      this.currencyGroup = other.currencyGroup;
    }
    this.currencyRate = other.currencyRate;
  }

  public Margin deepCopy() {
    return new Margin(this);
  }

  @Override
  public void clear() {
    setLongMarginRatioByMoneyIsSet(false);
    this.longMarginRatioByMoney = 0.0;
    setLongMarginRatioByVolumeIsSet(false);
    this.longMarginRatioByVolume = 0.0;
    setShortMarginRatioByMoneyIsSet(false);
    this.shortMarginRatioByMoney = 0.0;
    setShortMarginRatioByVolumeIsSet(false);
    this.shortMarginRatioByVolume = 0.0;
    this.currency = null;
    this.currencyGroup = null;
    setCurrencyRateIsSet(false);
    this.currencyRate = 0.0;
  }

  public double getLongMarginRatioByMoney() {
    return this.longMarginRatioByMoney;
  }

  public Margin setLongMarginRatioByMoney(double longMarginRatioByMoney) {
    this.longMarginRatioByMoney = longMarginRatioByMoney;
    setLongMarginRatioByMoneyIsSet(true);
    return this;
  }

  public void unsetLongMarginRatioByMoney() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LONGMARGINRATIOBYMONEY_ISSET_ID);
  }

  /** Returns true if field longMarginRatioByMoney is set (has been assigned a value) and false otherwise */
  public boolean isSetLongMarginRatioByMoney() {
    return EncodingUtils.testBit(__isset_bitfield, __LONGMARGINRATIOBYMONEY_ISSET_ID);
  }

  public void setLongMarginRatioByMoneyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LONGMARGINRATIOBYMONEY_ISSET_ID, value);
  }

  public double getLongMarginRatioByVolume() {
    return this.longMarginRatioByVolume;
  }

  public Margin setLongMarginRatioByVolume(double longMarginRatioByVolume) {
    this.longMarginRatioByVolume = longMarginRatioByVolume;
    setLongMarginRatioByVolumeIsSet(true);
    return this;
  }

  public void unsetLongMarginRatioByVolume() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LONGMARGINRATIOBYVOLUME_ISSET_ID);
  }

  /** Returns true if field longMarginRatioByVolume is set (has been assigned a value) and false otherwise */
  public boolean isSetLongMarginRatioByVolume() {
    return EncodingUtils.testBit(__isset_bitfield, __LONGMARGINRATIOBYVOLUME_ISSET_ID);
  }

  public void setLongMarginRatioByVolumeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LONGMARGINRATIOBYVOLUME_ISSET_ID, value);
  }

  public double getShortMarginRatioByMoney() {
    return this.shortMarginRatioByMoney;
  }

  public Margin setShortMarginRatioByMoney(double shortMarginRatioByMoney) {
    this.shortMarginRatioByMoney = shortMarginRatioByMoney;
    setShortMarginRatioByMoneyIsSet(true);
    return this;
  }

  public void unsetShortMarginRatioByMoney() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHORTMARGINRATIOBYMONEY_ISSET_ID);
  }

  /** Returns true if field shortMarginRatioByMoney is set (has been assigned a value) and false otherwise */
  public boolean isSetShortMarginRatioByMoney() {
    return EncodingUtils.testBit(__isset_bitfield, __SHORTMARGINRATIOBYMONEY_ISSET_ID);
  }

  public void setShortMarginRatioByMoneyIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHORTMARGINRATIOBYMONEY_ISSET_ID, value);
  }

  public double getShortMarginRatioByVolume() {
    return this.shortMarginRatioByVolume;
  }

  public Margin setShortMarginRatioByVolume(double shortMarginRatioByVolume) {
    this.shortMarginRatioByVolume = shortMarginRatioByVolume;
    setShortMarginRatioByVolumeIsSet(true);
    return this;
  }

  public void unsetShortMarginRatioByVolume() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SHORTMARGINRATIOBYVOLUME_ISSET_ID);
  }

  /** Returns true if field shortMarginRatioByVolume is set (has been assigned a value) and false otherwise */
  public boolean isSetShortMarginRatioByVolume() {
    return EncodingUtils.testBit(__isset_bitfield, __SHORTMARGINRATIOBYVOLUME_ISSET_ID);
  }

  public void setShortMarginRatioByVolumeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SHORTMARGINRATIOBYVOLUME_ISSET_ID, value);
  }

  public String getCurrency() {
    return this.currency;
  }

  public Margin setCurrency(String currency) {
    this.currency = currency;
    return this;
  }

  public void unsetCurrency() {
    this.currency = null;
  }

  /** Returns true if field currency is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrency() {
    return this.currency != null;
  }

  public void setCurrencyIsSet(boolean value) {
    if (!value) {
      this.currency = null;
    }
  }

  public String getCurrencyGroup() {
    return this.currencyGroup;
  }

  public Margin setCurrencyGroup(String currencyGroup) {
    this.currencyGroup = currencyGroup;
    return this;
  }

  public void unsetCurrencyGroup() {
    this.currencyGroup = null;
  }

  /** Returns true if field currencyGroup is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrencyGroup() {
    return this.currencyGroup != null;
  }

  public void setCurrencyGroupIsSet(boolean value) {
    if (!value) {
      this.currencyGroup = null;
    }
  }

  public double getCurrencyRate() {
    return this.currencyRate;
  }

  public Margin setCurrencyRate(double currencyRate) {
    this.currencyRate = currencyRate;
    setCurrencyRateIsSet(true);
    return this;
  }

  public void unsetCurrencyRate() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CURRENCYRATE_ISSET_ID);
  }

  /** Returns true if field currencyRate is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrencyRate() {
    return EncodingUtils.testBit(__isset_bitfield, __CURRENCYRATE_ISSET_ID);
  }

  public void setCurrencyRateIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CURRENCYRATE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case LONG_MARGIN_RATIO_BY_MONEY:
      if (value == null) {
        unsetLongMarginRatioByMoney();
      } else {
        setLongMarginRatioByMoney((Double)value);
      }
      break;

    case LONG_MARGIN_RATIO_BY_VOLUME:
      if (value == null) {
        unsetLongMarginRatioByVolume();
      } else {
        setLongMarginRatioByVolume((Double)value);
      }
      break;

    case SHORT_MARGIN_RATIO_BY_MONEY:
      if (value == null) {
        unsetShortMarginRatioByMoney();
      } else {
        setShortMarginRatioByMoney((Double)value);
      }
      break;

    case SHORT_MARGIN_RATIO_BY_VOLUME:
      if (value == null) {
        unsetShortMarginRatioByVolume();
      } else {
        setShortMarginRatioByVolume((Double)value);
      }
      break;

    case CURRENCY:
      if (value == null) {
        unsetCurrency();
      } else {
        setCurrency((String)value);
      }
      break;

    case CURRENCY_GROUP:
      if (value == null) {
        unsetCurrencyGroup();
      } else {
        setCurrencyGroup((String)value);
      }
      break;

    case CURRENCY_RATE:
      if (value == null) {
        unsetCurrencyRate();
      } else {
        setCurrencyRate((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case LONG_MARGIN_RATIO_BY_MONEY:
      return Double.valueOf(getLongMarginRatioByMoney());

    case LONG_MARGIN_RATIO_BY_VOLUME:
      return Double.valueOf(getLongMarginRatioByVolume());

    case SHORT_MARGIN_RATIO_BY_MONEY:
      return Double.valueOf(getShortMarginRatioByMoney());

    case SHORT_MARGIN_RATIO_BY_VOLUME:
      return Double.valueOf(getShortMarginRatioByVolume());

    case CURRENCY:
      return getCurrency();

    case CURRENCY_GROUP:
      return getCurrencyGroup();

    case CURRENCY_RATE:
      return Double.valueOf(getCurrencyRate());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case LONG_MARGIN_RATIO_BY_MONEY:
      return isSetLongMarginRatioByMoney();
    case LONG_MARGIN_RATIO_BY_VOLUME:
      return isSetLongMarginRatioByVolume();
    case SHORT_MARGIN_RATIO_BY_MONEY:
      return isSetShortMarginRatioByMoney();
    case SHORT_MARGIN_RATIO_BY_VOLUME:
      return isSetShortMarginRatioByVolume();
    case CURRENCY:
      return isSetCurrency();
    case CURRENCY_GROUP:
      return isSetCurrencyGroup();
    case CURRENCY_RATE:
      return isSetCurrencyRate();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Margin)
      return this.equals((Margin)that);
    return false;
  }

  public boolean equals(Margin that) {
    if (that == null)
      return false;

    boolean this_present_longMarginRatioByMoney = true && this.isSetLongMarginRatioByMoney();
    boolean that_present_longMarginRatioByMoney = true && that.isSetLongMarginRatioByMoney();
    if (this_present_longMarginRatioByMoney || that_present_longMarginRatioByMoney) {
      if (!(this_present_longMarginRatioByMoney && that_present_longMarginRatioByMoney))
        return false;
      if (this.longMarginRatioByMoney != that.longMarginRatioByMoney)
        return false;
    }

    boolean this_present_longMarginRatioByVolume = true && this.isSetLongMarginRatioByVolume();
    boolean that_present_longMarginRatioByVolume = true && that.isSetLongMarginRatioByVolume();
    if (this_present_longMarginRatioByVolume || that_present_longMarginRatioByVolume) {
      if (!(this_present_longMarginRatioByVolume && that_present_longMarginRatioByVolume))
        return false;
      if (this.longMarginRatioByVolume != that.longMarginRatioByVolume)
        return false;
    }

    boolean this_present_shortMarginRatioByMoney = true && this.isSetShortMarginRatioByMoney();
    boolean that_present_shortMarginRatioByMoney = true && that.isSetShortMarginRatioByMoney();
    if (this_present_shortMarginRatioByMoney || that_present_shortMarginRatioByMoney) {
      if (!(this_present_shortMarginRatioByMoney && that_present_shortMarginRatioByMoney))
        return false;
      if (this.shortMarginRatioByMoney != that.shortMarginRatioByMoney)
        return false;
    }

    boolean this_present_shortMarginRatioByVolume = true && this.isSetShortMarginRatioByVolume();
    boolean that_present_shortMarginRatioByVolume = true && that.isSetShortMarginRatioByVolume();
    if (this_present_shortMarginRatioByVolume || that_present_shortMarginRatioByVolume) {
      if (!(this_present_shortMarginRatioByVolume && that_present_shortMarginRatioByVolume))
        return false;
      if (this.shortMarginRatioByVolume != that.shortMarginRatioByVolume)
        return false;
    }

    boolean this_present_currency = true && this.isSetCurrency();
    boolean that_present_currency = true && that.isSetCurrency();
    if (this_present_currency || that_present_currency) {
      if (!(this_present_currency && that_present_currency))
        return false;
      if (!this.currency.equals(that.currency))
        return false;
    }

    boolean this_present_currencyGroup = true && this.isSetCurrencyGroup();
    boolean that_present_currencyGroup = true && that.isSetCurrencyGroup();
    if (this_present_currencyGroup || that_present_currencyGroup) {
      if (!(this_present_currencyGroup && that_present_currencyGroup))
        return false;
      if (!this.currencyGroup.equals(that.currencyGroup))
        return false;
    }

    boolean this_present_currencyRate = true && this.isSetCurrencyRate();
    boolean that_present_currencyRate = true && that.isSetCurrencyRate();
    if (this_present_currencyRate || that_present_currencyRate) {
      if (!(this_present_currencyRate && that_present_currencyRate))
        return false;
      if (this.currencyRate != that.currencyRate)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(Margin other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetLongMarginRatioByMoney()).compareTo(other.isSetLongMarginRatioByMoney());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLongMarginRatioByMoney()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.longMarginRatioByMoney, other.longMarginRatioByMoney);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLongMarginRatioByVolume()).compareTo(other.isSetLongMarginRatioByVolume());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLongMarginRatioByVolume()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.longMarginRatioByVolume, other.longMarginRatioByVolume);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShortMarginRatioByMoney()).compareTo(other.isSetShortMarginRatioByMoney());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShortMarginRatioByMoney()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shortMarginRatioByMoney, other.shortMarginRatioByMoney);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetShortMarginRatioByVolume()).compareTo(other.isSetShortMarginRatioByVolume());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetShortMarginRatioByVolume()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.shortMarginRatioByVolume, other.shortMarginRatioByVolume);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrency()).compareTo(other.isSetCurrency());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrency()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currency, other.currency);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrencyGroup()).compareTo(other.isSetCurrencyGroup());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrencyGroup()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currencyGroup, other.currencyGroup);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrencyRate()).compareTo(other.isSetCurrencyRate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrencyRate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currencyRate, other.currencyRate);
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
    StringBuilder sb = new StringBuilder("Margin(");
    boolean first = true;

    if (isSetLongMarginRatioByMoney()) {
      sb.append("longMarginRatioByMoney:");
      sb.append(this.longMarginRatioByMoney);
      first = false;
    }
    if (isSetLongMarginRatioByVolume()) {
      if (!first) sb.append(", ");
      sb.append("longMarginRatioByVolume:");
      sb.append(this.longMarginRatioByVolume);
      first = false;
    }
    if (isSetShortMarginRatioByMoney()) {
      if (!first) sb.append(", ");
      sb.append("shortMarginRatioByMoney:");
      sb.append(this.shortMarginRatioByMoney);
      first = false;
    }
    if (isSetShortMarginRatioByVolume()) {
      if (!first) sb.append(", ");
      sb.append("shortMarginRatioByVolume:");
      sb.append(this.shortMarginRatioByVolume);
      first = false;
    }
    if (isSetCurrency()) {
      if (!first) sb.append(", ");
      sb.append("currency:");
      if (this.currency == null) {
        sb.append("null");
      } else {
        sb.append(this.currency);
      }
      first = false;
    }
    if (isSetCurrencyGroup()) {
      if (!first) sb.append(", ");
      sb.append("currencyGroup:");
      if (this.currencyGroup == null) {
        sb.append("null");
      } else {
        sb.append(this.currencyGroup);
      }
      first = false;
    }
    if (isSetCurrencyRate()) {
      if (!first) sb.append(", ");
      sb.append("currencyRate:");
      sb.append(this.currencyRate);
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

  private static class MarginStandardSchemeFactory implements SchemeFactory {
    public MarginStandardScheme getScheme() {
      return new MarginStandardScheme();
    }
  }

  private static class MarginStandardScheme extends StandardScheme<Margin> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Margin struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // LONG_MARGIN_RATIO_BY_MONEY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.longMarginRatioByMoney = iprot.readDouble();
              struct.setLongMarginRatioByMoneyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // LONG_MARGIN_RATIO_BY_VOLUME
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.longMarginRatioByVolume = iprot.readDouble();
              struct.setLongMarginRatioByVolumeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SHORT_MARGIN_RATIO_BY_MONEY
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.shortMarginRatioByMoney = iprot.readDouble();
              struct.setShortMarginRatioByMoneyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SHORT_MARGIN_RATIO_BY_VOLUME
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.shortMarginRatioByVolume = iprot.readDouble();
              struct.setShortMarginRatioByVolumeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CURRENCY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currency = iprot.readString();
              struct.setCurrencyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CURRENCY_GROUP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currencyGroup = iprot.readString();
              struct.setCurrencyGroupIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // CURRENCY_RATE
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.currencyRate = iprot.readDouble();
              struct.setCurrencyRateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Margin struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetLongMarginRatioByMoney()) {
        oprot.writeFieldBegin(LONG_MARGIN_RATIO_BY_MONEY_FIELD_DESC);
        oprot.writeDouble(struct.longMarginRatioByMoney);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLongMarginRatioByVolume()) {
        oprot.writeFieldBegin(LONG_MARGIN_RATIO_BY_VOLUME_FIELD_DESC);
        oprot.writeDouble(struct.longMarginRatioByVolume);
        oprot.writeFieldEnd();
      }
      if (struct.isSetShortMarginRatioByMoney()) {
        oprot.writeFieldBegin(SHORT_MARGIN_RATIO_BY_MONEY_FIELD_DESC);
        oprot.writeDouble(struct.shortMarginRatioByMoney);
        oprot.writeFieldEnd();
      }
      if (struct.isSetShortMarginRatioByVolume()) {
        oprot.writeFieldBegin(SHORT_MARGIN_RATIO_BY_VOLUME_FIELD_DESC);
        oprot.writeDouble(struct.shortMarginRatioByVolume);
        oprot.writeFieldEnd();
      }
      if (struct.currency != null) {
        if (struct.isSetCurrency()) {
          oprot.writeFieldBegin(CURRENCY_FIELD_DESC);
          oprot.writeString(struct.currency);
          oprot.writeFieldEnd();
        }
      }
      if (struct.currencyGroup != null) {
        if (struct.isSetCurrencyGroup()) {
          oprot.writeFieldBegin(CURRENCY_GROUP_FIELD_DESC);
          oprot.writeString(struct.currencyGroup);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetCurrencyRate()) {
        oprot.writeFieldBegin(CURRENCY_RATE_FIELD_DESC);
        oprot.writeDouble(struct.currencyRate);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MarginTupleSchemeFactory implements SchemeFactory {
    public MarginTupleScheme getScheme() {
      return new MarginTupleScheme();
    }
  }

  private static class MarginTupleScheme extends TupleScheme<Margin> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Margin struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetLongMarginRatioByMoney()) {
        optionals.set(0);
      }
      if (struct.isSetLongMarginRatioByVolume()) {
        optionals.set(1);
      }
      if (struct.isSetShortMarginRatioByMoney()) {
        optionals.set(2);
      }
      if (struct.isSetShortMarginRatioByVolume()) {
        optionals.set(3);
      }
      if (struct.isSetCurrency()) {
        optionals.set(4);
      }
      if (struct.isSetCurrencyGroup()) {
        optionals.set(5);
      }
      if (struct.isSetCurrencyRate()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetLongMarginRatioByMoney()) {
        oprot.writeDouble(struct.longMarginRatioByMoney);
      }
      if (struct.isSetLongMarginRatioByVolume()) {
        oprot.writeDouble(struct.longMarginRatioByVolume);
      }
      if (struct.isSetShortMarginRatioByMoney()) {
        oprot.writeDouble(struct.shortMarginRatioByMoney);
      }
      if (struct.isSetShortMarginRatioByVolume()) {
        oprot.writeDouble(struct.shortMarginRatioByVolume);
      }
      if (struct.isSetCurrency()) {
        oprot.writeString(struct.currency);
      }
      if (struct.isSetCurrencyGroup()) {
        oprot.writeString(struct.currencyGroup);
      }
      if (struct.isSetCurrencyRate()) {
        oprot.writeDouble(struct.currencyRate);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Margin struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.longMarginRatioByMoney = iprot.readDouble();
        struct.setLongMarginRatioByMoneyIsSet(true);
      }
      if (incoming.get(1)) {
        struct.longMarginRatioByVolume = iprot.readDouble();
        struct.setLongMarginRatioByVolumeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.shortMarginRatioByMoney = iprot.readDouble();
        struct.setShortMarginRatioByMoneyIsSet(true);
      }
      if (incoming.get(3)) {
        struct.shortMarginRatioByVolume = iprot.readDouble();
        struct.setShortMarginRatioByVolumeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.currency = iprot.readString();
        struct.setCurrencyIsSet(true);
      }
      if (incoming.get(5)) {
        struct.currencyGroup = iprot.readString();
        struct.setCurrencyGroupIsSet(true);
      }
      if (incoming.get(6)) {
        struct.currencyRate = iprot.readDouble();
        struct.setCurrencyRateIsSet(true);
      }
    }
  }

}
