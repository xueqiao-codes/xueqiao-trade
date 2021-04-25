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

public class TPositionES9MarginRate implements org.apache.thrift.TBase<TPositionES9MarginRate, TPositionES9MarginRate._Fields>, java.io.Serializable, Cloneable, Comparable<TPositionES9MarginRate> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TPositionES9MarginRate");

  private static final org.apache.thrift.protocol.TField CALCULATE_MODE_FIELD_DESC = new org.apache.thrift.protocol.TField("calculateMode", org.apache.thrift.protocol.TType.I16, (short)1);
  private static final org.apache.thrift.protocol.TField CURRENCY_GROUP_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("currencyGroupNo", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField CURRENCY_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("currencyNo", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField INITIAL_MARGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("initialMargin", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField MAINTENANCE_MARGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("maintenanceMargin", org.apache.thrift.protocol.TType.DOUBLE, (short)5);
  private static final org.apache.thrift.protocol.TField SELL_INITIAL_MARGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("sellInitialMargin", org.apache.thrift.protocol.TType.DOUBLE, (short)6);
  private static final org.apache.thrift.protocol.TField SELL_MAINTENANCE_MARGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("sellMaintenanceMargin", org.apache.thrift.protocol.TType.DOUBLE, (short)7);
  private static final org.apache.thrift.protocol.TField LOCK_MARGIN_FIELD_DESC = new org.apache.thrift.protocol.TField("lockMargin", org.apache.thrift.protocol.TType.DOUBLE, (short)8);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TPositionES9MarginRateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TPositionES9MarginRateTupleSchemeFactory());
  }

  public short calculateMode; // optional
  public String currencyGroupNo; // optional
  public String currencyNo; // optional
  public double initialMargin; // optional
  public double maintenanceMargin; // optional
  public double sellInitialMargin; // optional
  public double sellMaintenanceMargin; // optional
  public double lockMargin; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CALCULATE_MODE((short)1, "calculateMode"),
    CURRENCY_GROUP_NO((short)2, "currencyGroupNo"),
    CURRENCY_NO((short)3, "currencyNo"),
    INITIAL_MARGIN((short)4, "initialMargin"),
    MAINTENANCE_MARGIN((short)5, "maintenanceMargin"),
    SELL_INITIAL_MARGIN((short)6, "sellInitialMargin"),
    SELL_MAINTENANCE_MARGIN((short)7, "sellMaintenanceMargin"),
    LOCK_MARGIN((short)8, "lockMargin");

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
        case 1: // CALCULATE_MODE
          return CALCULATE_MODE;
        case 2: // CURRENCY_GROUP_NO
          return CURRENCY_GROUP_NO;
        case 3: // CURRENCY_NO
          return CURRENCY_NO;
        case 4: // INITIAL_MARGIN
          return INITIAL_MARGIN;
        case 5: // MAINTENANCE_MARGIN
          return MAINTENANCE_MARGIN;
        case 6: // SELL_INITIAL_MARGIN
          return SELL_INITIAL_MARGIN;
        case 7: // SELL_MAINTENANCE_MARGIN
          return SELL_MAINTENANCE_MARGIN;
        case 8: // LOCK_MARGIN
          return LOCK_MARGIN;
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
  private static final int __CALCULATEMODE_ISSET_ID = 0;
  private static final int __INITIALMARGIN_ISSET_ID = 1;
  private static final int __MAINTENANCEMARGIN_ISSET_ID = 2;
  private static final int __SELLINITIALMARGIN_ISSET_ID = 3;
  private static final int __SELLMAINTENANCEMARGIN_ISSET_ID = 4;
  private static final int __LOCKMARGIN_ISSET_ID = 5;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.CALCULATE_MODE,_Fields.CURRENCY_GROUP_NO,_Fields.CURRENCY_NO,_Fields.INITIAL_MARGIN,_Fields.MAINTENANCE_MARGIN,_Fields.SELL_INITIAL_MARGIN,_Fields.SELL_MAINTENANCE_MARGIN,_Fields.LOCK_MARGIN};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CALCULATE_MODE, new org.apache.thrift.meta_data.FieldMetaData("calculateMode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16)));
    tmpMap.put(_Fields.CURRENCY_GROUP_NO, new org.apache.thrift.meta_data.FieldMetaData("currencyGroupNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CURRENCY_NO, new org.apache.thrift.meta_data.FieldMetaData("currencyNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INITIAL_MARGIN, new org.apache.thrift.meta_data.FieldMetaData("initialMargin", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.MAINTENANCE_MARGIN, new org.apache.thrift.meta_data.FieldMetaData("maintenanceMargin", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.SELL_INITIAL_MARGIN, new org.apache.thrift.meta_data.FieldMetaData("sellInitialMargin", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.SELL_MAINTENANCE_MARGIN, new org.apache.thrift.meta_data.FieldMetaData("sellMaintenanceMargin", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.LOCK_MARGIN, new org.apache.thrift.meta_data.FieldMetaData("lockMargin", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TPositionES9MarginRate.class, metaDataMap);
  }

  public TPositionES9MarginRate() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TPositionES9MarginRate(TPositionES9MarginRate other) {
    __isset_bitfield = other.__isset_bitfield;
    this.calculateMode = other.calculateMode;
    if (other.isSetCurrencyGroupNo()) {
      this.currencyGroupNo = other.currencyGroupNo;
    }
    if (other.isSetCurrencyNo()) {
      this.currencyNo = other.currencyNo;
    }
    this.initialMargin = other.initialMargin;
    this.maintenanceMargin = other.maintenanceMargin;
    this.sellInitialMargin = other.sellInitialMargin;
    this.sellMaintenanceMargin = other.sellMaintenanceMargin;
    this.lockMargin = other.lockMargin;
  }

  public TPositionES9MarginRate deepCopy() {
    return new TPositionES9MarginRate(this);
  }

  @Override
  public void clear() {
    setCalculateModeIsSet(false);
    this.calculateMode = 0;
    this.currencyGroupNo = null;
    this.currencyNo = null;
    setInitialMarginIsSet(false);
    this.initialMargin = 0.0;
    setMaintenanceMarginIsSet(false);
    this.maintenanceMargin = 0.0;
    setSellInitialMarginIsSet(false);
    this.sellInitialMargin = 0.0;
    setSellMaintenanceMarginIsSet(false);
    this.sellMaintenanceMargin = 0.0;
    setLockMarginIsSet(false);
    this.lockMargin = 0.0;
  }

  public short getCalculateMode() {
    return this.calculateMode;
  }

  public TPositionES9MarginRate setCalculateMode(short calculateMode) {
    this.calculateMode = calculateMode;
    setCalculateModeIsSet(true);
    return this;
  }

  public void unsetCalculateMode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CALCULATEMODE_ISSET_ID);
  }

  /** Returns true if field calculateMode is set (has been assigned a value) and false otherwise */
  public boolean isSetCalculateMode() {
    return EncodingUtils.testBit(__isset_bitfield, __CALCULATEMODE_ISSET_ID);
  }

  public void setCalculateModeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CALCULATEMODE_ISSET_ID, value);
  }

  public String getCurrencyGroupNo() {
    return this.currencyGroupNo;
  }

  public TPositionES9MarginRate setCurrencyGroupNo(String currencyGroupNo) {
    this.currencyGroupNo = currencyGroupNo;
    return this;
  }

  public void unsetCurrencyGroupNo() {
    this.currencyGroupNo = null;
  }

  /** Returns true if field currencyGroupNo is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrencyGroupNo() {
    return this.currencyGroupNo != null;
  }

  public void setCurrencyGroupNoIsSet(boolean value) {
    if (!value) {
      this.currencyGroupNo = null;
    }
  }

  public String getCurrencyNo() {
    return this.currencyNo;
  }

  public TPositionES9MarginRate setCurrencyNo(String currencyNo) {
    this.currencyNo = currencyNo;
    return this;
  }

  public void unsetCurrencyNo() {
    this.currencyNo = null;
  }

  /** Returns true if field currencyNo is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrencyNo() {
    return this.currencyNo != null;
  }

  public void setCurrencyNoIsSet(boolean value) {
    if (!value) {
      this.currencyNo = null;
    }
  }

  public double getInitialMargin() {
    return this.initialMargin;
  }

  public TPositionES9MarginRate setInitialMargin(double initialMargin) {
    this.initialMargin = initialMargin;
    setInitialMarginIsSet(true);
    return this;
  }

  public void unsetInitialMargin() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __INITIALMARGIN_ISSET_ID);
  }

  /** Returns true if field initialMargin is set (has been assigned a value) and false otherwise */
  public boolean isSetInitialMargin() {
    return EncodingUtils.testBit(__isset_bitfield, __INITIALMARGIN_ISSET_ID);
  }

  public void setInitialMarginIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __INITIALMARGIN_ISSET_ID, value);
  }

  public double getMaintenanceMargin() {
    return this.maintenanceMargin;
  }

  public TPositionES9MarginRate setMaintenanceMargin(double maintenanceMargin) {
    this.maintenanceMargin = maintenanceMargin;
    setMaintenanceMarginIsSet(true);
    return this;
  }

  public void unsetMaintenanceMargin() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MAINTENANCEMARGIN_ISSET_ID);
  }

  /** Returns true if field maintenanceMargin is set (has been assigned a value) and false otherwise */
  public boolean isSetMaintenanceMargin() {
    return EncodingUtils.testBit(__isset_bitfield, __MAINTENANCEMARGIN_ISSET_ID);
  }

  public void setMaintenanceMarginIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MAINTENANCEMARGIN_ISSET_ID, value);
  }

  public double getSellInitialMargin() {
    return this.sellInitialMargin;
  }

  public TPositionES9MarginRate setSellInitialMargin(double sellInitialMargin) {
    this.sellInitialMargin = sellInitialMargin;
    setSellInitialMarginIsSet(true);
    return this;
  }

  public void unsetSellInitialMargin() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SELLINITIALMARGIN_ISSET_ID);
  }

  /** Returns true if field sellInitialMargin is set (has been assigned a value) and false otherwise */
  public boolean isSetSellInitialMargin() {
    return EncodingUtils.testBit(__isset_bitfield, __SELLINITIALMARGIN_ISSET_ID);
  }

  public void setSellInitialMarginIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SELLINITIALMARGIN_ISSET_ID, value);
  }

  public double getSellMaintenanceMargin() {
    return this.sellMaintenanceMargin;
  }

  public TPositionES9MarginRate setSellMaintenanceMargin(double sellMaintenanceMargin) {
    this.sellMaintenanceMargin = sellMaintenanceMargin;
    setSellMaintenanceMarginIsSet(true);
    return this;
  }

  public void unsetSellMaintenanceMargin() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SELLMAINTENANCEMARGIN_ISSET_ID);
  }

  /** Returns true if field sellMaintenanceMargin is set (has been assigned a value) and false otherwise */
  public boolean isSetSellMaintenanceMargin() {
    return EncodingUtils.testBit(__isset_bitfield, __SELLMAINTENANCEMARGIN_ISSET_ID);
  }

  public void setSellMaintenanceMarginIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SELLMAINTENANCEMARGIN_ISSET_ID, value);
  }

  public double getLockMargin() {
    return this.lockMargin;
  }

  public TPositionES9MarginRate setLockMargin(double lockMargin) {
    this.lockMargin = lockMargin;
    setLockMarginIsSet(true);
    return this;
  }

  public void unsetLockMargin() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LOCKMARGIN_ISSET_ID);
  }

  /** Returns true if field lockMargin is set (has been assigned a value) and false otherwise */
  public boolean isSetLockMargin() {
    return EncodingUtils.testBit(__isset_bitfield, __LOCKMARGIN_ISSET_ID);
  }

  public void setLockMarginIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LOCKMARGIN_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CALCULATE_MODE:
      if (value == null) {
        unsetCalculateMode();
      } else {
        setCalculateMode((Short)value);
      }
      break;

    case CURRENCY_GROUP_NO:
      if (value == null) {
        unsetCurrencyGroupNo();
      } else {
        setCurrencyGroupNo((String)value);
      }
      break;

    case CURRENCY_NO:
      if (value == null) {
        unsetCurrencyNo();
      } else {
        setCurrencyNo((String)value);
      }
      break;

    case INITIAL_MARGIN:
      if (value == null) {
        unsetInitialMargin();
      } else {
        setInitialMargin((Double)value);
      }
      break;

    case MAINTENANCE_MARGIN:
      if (value == null) {
        unsetMaintenanceMargin();
      } else {
        setMaintenanceMargin((Double)value);
      }
      break;

    case SELL_INITIAL_MARGIN:
      if (value == null) {
        unsetSellInitialMargin();
      } else {
        setSellInitialMargin((Double)value);
      }
      break;

    case SELL_MAINTENANCE_MARGIN:
      if (value == null) {
        unsetSellMaintenanceMargin();
      } else {
        setSellMaintenanceMargin((Double)value);
      }
      break;

    case LOCK_MARGIN:
      if (value == null) {
        unsetLockMargin();
      } else {
        setLockMargin((Double)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CALCULATE_MODE:
      return Short.valueOf(getCalculateMode());

    case CURRENCY_GROUP_NO:
      return getCurrencyGroupNo();

    case CURRENCY_NO:
      return getCurrencyNo();

    case INITIAL_MARGIN:
      return Double.valueOf(getInitialMargin());

    case MAINTENANCE_MARGIN:
      return Double.valueOf(getMaintenanceMargin());

    case SELL_INITIAL_MARGIN:
      return Double.valueOf(getSellInitialMargin());

    case SELL_MAINTENANCE_MARGIN:
      return Double.valueOf(getSellMaintenanceMargin());

    case LOCK_MARGIN:
      return Double.valueOf(getLockMargin());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CALCULATE_MODE:
      return isSetCalculateMode();
    case CURRENCY_GROUP_NO:
      return isSetCurrencyGroupNo();
    case CURRENCY_NO:
      return isSetCurrencyNo();
    case INITIAL_MARGIN:
      return isSetInitialMargin();
    case MAINTENANCE_MARGIN:
      return isSetMaintenanceMargin();
    case SELL_INITIAL_MARGIN:
      return isSetSellInitialMargin();
    case SELL_MAINTENANCE_MARGIN:
      return isSetSellMaintenanceMargin();
    case LOCK_MARGIN:
      return isSetLockMargin();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TPositionES9MarginRate)
      return this.equals((TPositionES9MarginRate)that);
    return false;
  }

  public boolean equals(TPositionES9MarginRate that) {
    if (that == null)
      return false;

    boolean this_present_calculateMode = true && this.isSetCalculateMode();
    boolean that_present_calculateMode = true && that.isSetCalculateMode();
    if (this_present_calculateMode || that_present_calculateMode) {
      if (!(this_present_calculateMode && that_present_calculateMode))
        return false;
      if (this.calculateMode != that.calculateMode)
        return false;
    }

    boolean this_present_currencyGroupNo = true && this.isSetCurrencyGroupNo();
    boolean that_present_currencyGroupNo = true && that.isSetCurrencyGroupNo();
    if (this_present_currencyGroupNo || that_present_currencyGroupNo) {
      if (!(this_present_currencyGroupNo && that_present_currencyGroupNo))
        return false;
      if (!this.currencyGroupNo.equals(that.currencyGroupNo))
        return false;
    }

    boolean this_present_currencyNo = true && this.isSetCurrencyNo();
    boolean that_present_currencyNo = true && that.isSetCurrencyNo();
    if (this_present_currencyNo || that_present_currencyNo) {
      if (!(this_present_currencyNo && that_present_currencyNo))
        return false;
      if (!this.currencyNo.equals(that.currencyNo))
        return false;
    }

    boolean this_present_initialMargin = true && this.isSetInitialMargin();
    boolean that_present_initialMargin = true && that.isSetInitialMargin();
    if (this_present_initialMargin || that_present_initialMargin) {
      if (!(this_present_initialMargin && that_present_initialMargin))
        return false;
      if (this.initialMargin != that.initialMargin)
        return false;
    }

    boolean this_present_maintenanceMargin = true && this.isSetMaintenanceMargin();
    boolean that_present_maintenanceMargin = true && that.isSetMaintenanceMargin();
    if (this_present_maintenanceMargin || that_present_maintenanceMargin) {
      if (!(this_present_maintenanceMargin && that_present_maintenanceMargin))
        return false;
      if (this.maintenanceMargin != that.maintenanceMargin)
        return false;
    }

    boolean this_present_sellInitialMargin = true && this.isSetSellInitialMargin();
    boolean that_present_sellInitialMargin = true && that.isSetSellInitialMargin();
    if (this_present_sellInitialMargin || that_present_sellInitialMargin) {
      if (!(this_present_sellInitialMargin && that_present_sellInitialMargin))
        return false;
      if (this.sellInitialMargin != that.sellInitialMargin)
        return false;
    }

    boolean this_present_sellMaintenanceMargin = true && this.isSetSellMaintenanceMargin();
    boolean that_present_sellMaintenanceMargin = true && that.isSetSellMaintenanceMargin();
    if (this_present_sellMaintenanceMargin || that_present_sellMaintenanceMargin) {
      if (!(this_present_sellMaintenanceMargin && that_present_sellMaintenanceMargin))
        return false;
      if (this.sellMaintenanceMargin != that.sellMaintenanceMargin)
        return false;
    }

    boolean this_present_lockMargin = true && this.isSetLockMargin();
    boolean that_present_lockMargin = true && that.isSetLockMargin();
    if (this_present_lockMargin || that_present_lockMargin) {
      if (!(this_present_lockMargin && that_present_lockMargin))
        return false;
      if (this.lockMargin != that.lockMargin)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(TPositionES9MarginRate other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCalculateMode()).compareTo(other.isSetCalculateMode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCalculateMode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.calculateMode, other.calculateMode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrencyGroupNo()).compareTo(other.isSetCurrencyGroupNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrencyGroupNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currencyGroupNo, other.currencyGroupNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCurrencyNo()).compareTo(other.isSetCurrencyNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrencyNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currencyNo, other.currencyNo);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInitialMargin()).compareTo(other.isSetInitialMargin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInitialMargin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.initialMargin, other.initialMargin);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMaintenanceMargin()).compareTo(other.isSetMaintenanceMargin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMaintenanceMargin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.maintenanceMargin, other.maintenanceMargin);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSellInitialMargin()).compareTo(other.isSetSellInitialMargin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellInitialMargin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sellInitialMargin, other.sellInitialMargin);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSellMaintenanceMargin()).compareTo(other.isSetSellMaintenanceMargin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSellMaintenanceMargin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sellMaintenanceMargin, other.sellMaintenanceMargin);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLockMargin()).compareTo(other.isSetLockMargin());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLockMargin()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lockMargin, other.lockMargin);
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
    StringBuilder sb = new StringBuilder("TPositionES9MarginRate(");
    boolean first = true;

    if (isSetCalculateMode()) {
      sb.append("calculateMode:");
      sb.append(this.calculateMode);
      first = false;
    }
    if (isSetCurrencyGroupNo()) {
      if (!first) sb.append(", ");
      sb.append("currencyGroupNo:");
      if (this.currencyGroupNo == null) {
        sb.append("null");
      } else {
        sb.append(this.currencyGroupNo);
      }
      first = false;
    }
    if (isSetCurrencyNo()) {
      if (!first) sb.append(", ");
      sb.append("currencyNo:");
      if (this.currencyNo == null) {
        sb.append("null");
      } else {
        sb.append(this.currencyNo);
      }
      first = false;
    }
    if (isSetInitialMargin()) {
      if (!first) sb.append(", ");
      sb.append("initialMargin:");
      sb.append(this.initialMargin);
      first = false;
    }
    if (isSetMaintenanceMargin()) {
      if (!first) sb.append(", ");
      sb.append("maintenanceMargin:");
      sb.append(this.maintenanceMargin);
      first = false;
    }
    if (isSetSellInitialMargin()) {
      if (!first) sb.append(", ");
      sb.append("sellInitialMargin:");
      sb.append(this.sellInitialMargin);
      first = false;
    }
    if (isSetSellMaintenanceMargin()) {
      if (!first) sb.append(", ");
      sb.append("sellMaintenanceMargin:");
      sb.append(this.sellMaintenanceMargin);
      first = false;
    }
    if (isSetLockMargin()) {
      if (!first) sb.append(", ");
      sb.append("lockMargin:");
      sb.append(this.lockMargin);
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

  private static class TPositionES9MarginRateStandardSchemeFactory implements SchemeFactory {
    public TPositionES9MarginRateStandardScheme getScheme() {
      return new TPositionES9MarginRateStandardScheme();
    }
  }

  private static class TPositionES9MarginRateStandardScheme extends StandardScheme<TPositionES9MarginRate> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TPositionES9MarginRate struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CALCULATE_MODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.calculateMode = iprot.readI16();
              struct.setCalculateModeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CURRENCY_GROUP_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currencyGroupNo = iprot.readString();
              struct.setCurrencyGroupNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // CURRENCY_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.currencyNo = iprot.readString();
              struct.setCurrencyNoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // INITIAL_MARGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.initialMargin = iprot.readDouble();
              struct.setInitialMarginIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MAINTENANCE_MARGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.maintenanceMargin = iprot.readDouble();
              struct.setMaintenanceMarginIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // SELL_INITIAL_MARGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.sellInitialMargin = iprot.readDouble();
              struct.setSellInitialMarginIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // SELL_MAINTENANCE_MARGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.sellMaintenanceMargin = iprot.readDouble();
              struct.setSellMaintenanceMarginIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // LOCK_MARGIN
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.lockMargin = iprot.readDouble();
              struct.setLockMarginIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TPositionES9MarginRate struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetCalculateMode()) {
        oprot.writeFieldBegin(CALCULATE_MODE_FIELD_DESC);
        oprot.writeI16(struct.calculateMode);
        oprot.writeFieldEnd();
      }
      if (struct.currencyGroupNo != null) {
        if (struct.isSetCurrencyGroupNo()) {
          oprot.writeFieldBegin(CURRENCY_GROUP_NO_FIELD_DESC);
          oprot.writeString(struct.currencyGroupNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.currencyNo != null) {
        if (struct.isSetCurrencyNo()) {
          oprot.writeFieldBegin(CURRENCY_NO_FIELD_DESC);
          oprot.writeString(struct.currencyNo);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetInitialMargin()) {
        oprot.writeFieldBegin(INITIAL_MARGIN_FIELD_DESC);
        oprot.writeDouble(struct.initialMargin);
        oprot.writeFieldEnd();
      }
      if (struct.isSetMaintenanceMargin()) {
        oprot.writeFieldBegin(MAINTENANCE_MARGIN_FIELD_DESC);
        oprot.writeDouble(struct.maintenanceMargin);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSellInitialMargin()) {
        oprot.writeFieldBegin(SELL_INITIAL_MARGIN_FIELD_DESC);
        oprot.writeDouble(struct.sellInitialMargin);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSellMaintenanceMargin()) {
        oprot.writeFieldBegin(SELL_MAINTENANCE_MARGIN_FIELD_DESC);
        oprot.writeDouble(struct.sellMaintenanceMargin);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLockMargin()) {
        oprot.writeFieldBegin(LOCK_MARGIN_FIELD_DESC);
        oprot.writeDouble(struct.lockMargin);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TPositionES9MarginRateTupleSchemeFactory implements SchemeFactory {
    public TPositionES9MarginRateTupleScheme getScheme() {
      return new TPositionES9MarginRateTupleScheme();
    }
  }

  private static class TPositionES9MarginRateTupleScheme extends TupleScheme<TPositionES9MarginRate> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TPositionES9MarginRate struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCalculateMode()) {
        optionals.set(0);
      }
      if (struct.isSetCurrencyGroupNo()) {
        optionals.set(1);
      }
      if (struct.isSetCurrencyNo()) {
        optionals.set(2);
      }
      if (struct.isSetInitialMargin()) {
        optionals.set(3);
      }
      if (struct.isSetMaintenanceMargin()) {
        optionals.set(4);
      }
      if (struct.isSetSellInitialMargin()) {
        optionals.set(5);
      }
      if (struct.isSetSellMaintenanceMargin()) {
        optionals.set(6);
      }
      if (struct.isSetLockMargin()) {
        optionals.set(7);
      }
      oprot.writeBitSet(optionals, 8);
      if (struct.isSetCalculateMode()) {
        oprot.writeI16(struct.calculateMode);
      }
      if (struct.isSetCurrencyGroupNo()) {
        oprot.writeString(struct.currencyGroupNo);
      }
      if (struct.isSetCurrencyNo()) {
        oprot.writeString(struct.currencyNo);
      }
      if (struct.isSetInitialMargin()) {
        oprot.writeDouble(struct.initialMargin);
      }
      if (struct.isSetMaintenanceMargin()) {
        oprot.writeDouble(struct.maintenanceMargin);
      }
      if (struct.isSetSellInitialMargin()) {
        oprot.writeDouble(struct.sellInitialMargin);
      }
      if (struct.isSetSellMaintenanceMargin()) {
        oprot.writeDouble(struct.sellMaintenanceMargin);
      }
      if (struct.isSetLockMargin()) {
        oprot.writeDouble(struct.lockMargin);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TPositionES9MarginRate struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(8);
      if (incoming.get(0)) {
        struct.calculateMode = iprot.readI16();
        struct.setCalculateModeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.currencyGroupNo = iprot.readString();
        struct.setCurrencyGroupNoIsSet(true);
      }
      if (incoming.get(2)) {
        struct.currencyNo = iprot.readString();
        struct.setCurrencyNoIsSet(true);
      }
      if (incoming.get(3)) {
        struct.initialMargin = iprot.readDouble();
        struct.setInitialMarginIsSet(true);
      }
      if (incoming.get(4)) {
        struct.maintenanceMargin = iprot.readDouble();
        struct.setMaintenanceMarginIsSet(true);
      }
      if (incoming.get(5)) {
        struct.sellInitialMargin = iprot.readDouble();
        struct.setSellInitialMarginIsSet(true);
      }
      if (incoming.get(6)) {
        struct.sellMaintenanceMargin = iprot.readDouble();
        struct.setSellMaintenanceMarginIsSet(true);
      }
      if (incoming.get(7)) {
        struct.lockMargin = iprot.readDouble();
        struct.setLockMarginIsSet(true);
      }
    }
  }

}

