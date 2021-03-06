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

public class QueryStatArchiveItemOption implements org.apache.thrift.TBase<QueryStatArchiveItemOption, QueryStatArchiveItemOption._Fields>, java.io.Serializable, Cloneable, Comparable<QueryStatArchiveItemOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryStatArchiveItemOption");

  private static final org.apache.thrift.protocol.TField CLOSED_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("closedId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_ACCOUNT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("subAccountId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField TARGET_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("targetKey", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField TARGET_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("targetType", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField ARCHIVE_START_DATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("archiveStartDateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)10);
  private static final org.apache.thrift.protocol.TField ARCHIVE_END_DATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("archiveEndDateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)11);
  private static final org.apache.thrift.protocol.TField ARCHIVED_DATE_TIMESTAMP_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("archivedDateTimestampMs", org.apache.thrift.protocol.TType.I64, (short)12);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryStatArchiveItemOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryStatArchiveItemOptionTupleSchemeFactory());
  }

  public long closedId; // optional
  public long subAccountId; // optional
  public String targetKey; // optional
  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType; // optional
  public long archiveStartDateTimestampMs; // optional
  public long archiveEndDateTimestampMs; // optional
  public long archivedDateTimestampMs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CLOSED_ID((short)1, "closedId"),
    SUB_ACCOUNT_ID((short)3, "subAccountId"),
    TARGET_KEY((short)4, "targetKey"),
    /**
     * 
     * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
     */
    TARGET_TYPE((short)5, "targetType"),
    ARCHIVE_START_DATE_TIMESTAMP_MS((short)10, "archiveStartDateTimestampMs"),
    ARCHIVE_END_DATE_TIMESTAMP_MS((short)11, "archiveEndDateTimestampMs"),
    ARCHIVED_DATE_TIMESTAMP_MS((short)12, "archivedDateTimestampMs");

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
        case 1: // CLOSED_ID
          return CLOSED_ID;
        case 3: // SUB_ACCOUNT_ID
          return SUB_ACCOUNT_ID;
        case 4: // TARGET_KEY
          return TARGET_KEY;
        case 5: // TARGET_TYPE
          return TARGET_TYPE;
        case 10: // ARCHIVE_START_DATE_TIMESTAMP_MS
          return ARCHIVE_START_DATE_TIMESTAMP_MS;
        case 11: // ARCHIVE_END_DATE_TIMESTAMP_MS
          return ARCHIVE_END_DATE_TIMESTAMP_MS;
        case 12: // ARCHIVED_DATE_TIMESTAMP_MS
          return ARCHIVED_DATE_TIMESTAMP_MS;
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
  private static final int __CLOSEDID_ISSET_ID = 0;
  private static final int __SUBACCOUNTID_ISSET_ID = 1;
  private static final int __ARCHIVESTARTDATETIMESTAMPMS_ISSET_ID = 2;
  private static final int __ARCHIVEENDDATETIMESTAMPMS_ISSET_ID = 3;
  private static final int __ARCHIVEDDATETIMESTAMPMS_ISSET_ID = 4;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.CLOSED_ID,_Fields.SUB_ACCOUNT_ID,_Fields.TARGET_KEY,_Fields.TARGET_TYPE,_Fields.ARCHIVE_START_DATE_TIMESTAMP_MS,_Fields.ARCHIVE_END_DATE_TIMESTAMP_MS,_Fields.ARCHIVED_DATE_TIMESTAMP_MS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CLOSED_ID, new org.apache.thrift.meta_data.FieldMetaData("closedId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SUB_ACCOUNT_ID, new org.apache.thrift.meta_data.FieldMetaData("subAccountId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TARGET_KEY, new org.apache.thrift.meta_data.FieldMetaData("targetKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TARGET_TYPE, new org.apache.thrift.meta_data.FieldMetaData("targetType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.class)));
    tmpMap.put(_Fields.ARCHIVE_START_DATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("archiveStartDateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ARCHIVE_END_DATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("archiveEndDateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.ARCHIVED_DATE_TIMESTAMP_MS, new org.apache.thrift.meta_data.FieldMetaData("archivedDateTimestampMs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryStatArchiveItemOption.class, metaDataMap);
  }

  public QueryStatArchiveItemOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryStatArchiveItemOption(QueryStatArchiveItemOption other) {
    __isset_bitfield = other.__isset_bitfield;
    this.closedId = other.closedId;
    this.subAccountId = other.subAccountId;
    if (other.isSetTargetKey()) {
      this.targetKey = other.targetKey;
    }
    if (other.isSetTargetType()) {
      this.targetType = other.targetType;
    }
    this.archiveStartDateTimestampMs = other.archiveStartDateTimestampMs;
    this.archiveEndDateTimestampMs = other.archiveEndDateTimestampMs;
    this.archivedDateTimestampMs = other.archivedDateTimestampMs;
  }

  public QueryStatArchiveItemOption deepCopy() {
    return new QueryStatArchiveItemOption(this);
  }

  @Override
  public void clear() {
    setClosedIdIsSet(false);
    this.closedId = 0;
    setSubAccountIdIsSet(false);
    this.subAccountId = 0;
    this.targetKey = null;
    this.targetType = null;
    setArchiveStartDateTimestampMsIsSet(false);
    this.archiveStartDateTimestampMs = 0;
    setArchiveEndDateTimestampMsIsSet(false);
    this.archiveEndDateTimestampMs = 0;
    setArchivedDateTimestampMsIsSet(false);
    this.archivedDateTimestampMs = 0;
  }

  public long getClosedId() {
    return this.closedId;
  }

  public QueryStatArchiveItemOption setClosedId(long closedId) {
    this.closedId = closedId;
    setClosedIdIsSet(true);
    return this;
  }

  public void unsetClosedId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CLOSEDID_ISSET_ID);
  }

  /** Returns true if field closedId is set (has been assigned a value) and false otherwise */
  public boolean isSetClosedId() {
    return EncodingUtils.testBit(__isset_bitfield, __CLOSEDID_ISSET_ID);
  }

  public void setClosedIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CLOSEDID_ISSET_ID, value);
  }

  public long getSubAccountId() {
    return this.subAccountId;
  }

  public QueryStatArchiveItemOption setSubAccountId(long subAccountId) {
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

  public String getTargetKey() {
    return this.targetKey;
  }

  public QueryStatArchiveItemOption setTargetKey(String targetKey) {
    this.targetKey = targetKey;
    return this;
  }

  public void unsetTargetKey() {
    this.targetKey = null;
  }

  /** Returns true if field targetKey is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetKey() {
    return this.targetKey != null;
  }

  public void setTargetKeyIsSet(boolean value) {
    if (!value) {
      this.targetKey = null;
    }
  }

  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType getTargetType() {
    return this.targetType;
  }

  /**
   * 
   * @see xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType
   */
  public QueryStatArchiveItemOption setTargetType(xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType) {
    this.targetType = targetType;
    return this;
  }

  public void unsetTargetType() {
    this.targetType = null;
  }

  /** Returns true if field targetType is set (has been assigned a value) and false otherwise */
  public boolean isSetTargetType() {
    return this.targetType != null;
  }

  public void setTargetTypeIsSet(boolean value) {
    if (!value) {
      this.targetType = null;
    }
  }

  public long getArchiveStartDateTimestampMs() {
    return this.archiveStartDateTimestampMs;
  }

  public QueryStatArchiveItemOption setArchiveStartDateTimestampMs(long archiveStartDateTimestampMs) {
    this.archiveStartDateTimestampMs = archiveStartDateTimestampMs;
    setArchiveStartDateTimestampMsIsSet(true);
    return this;
  }

  public void unsetArchiveStartDateTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ARCHIVESTARTDATETIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field archiveStartDateTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetArchiveStartDateTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __ARCHIVESTARTDATETIMESTAMPMS_ISSET_ID);
  }

  public void setArchiveStartDateTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ARCHIVESTARTDATETIMESTAMPMS_ISSET_ID, value);
  }

  public long getArchiveEndDateTimestampMs() {
    return this.archiveEndDateTimestampMs;
  }

  public QueryStatArchiveItemOption setArchiveEndDateTimestampMs(long archiveEndDateTimestampMs) {
    this.archiveEndDateTimestampMs = archiveEndDateTimestampMs;
    setArchiveEndDateTimestampMsIsSet(true);
    return this;
  }

  public void unsetArchiveEndDateTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ARCHIVEENDDATETIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field archiveEndDateTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetArchiveEndDateTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __ARCHIVEENDDATETIMESTAMPMS_ISSET_ID);
  }

  public void setArchiveEndDateTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ARCHIVEENDDATETIMESTAMPMS_ISSET_ID, value);
  }

  public long getArchivedDateTimestampMs() {
    return this.archivedDateTimestampMs;
  }

  public QueryStatArchiveItemOption setArchivedDateTimestampMs(long archivedDateTimestampMs) {
    this.archivedDateTimestampMs = archivedDateTimestampMs;
    setArchivedDateTimestampMsIsSet(true);
    return this;
  }

  public void unsetArchivedDateTimestampMs() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ARCHIVEDDATETIMESTAMPMS_ISSET_ID);
  }

  /** Returns true if field archivedDateTimestampMs is set (has been assigned a value) and false otherwise */
  public boolean isSetArchivedDateTimestampMs() {
    return EncodingUtils.testBit(__isset_bitfield, __ARCHIVEDDATETIMESTAMPMS_ISSET_ID);
  }

  public void setArchivedDateTimestampMsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ARCHIVEDDATETIMESTAMPMS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CLOSED_ID:
      if (value == null) {
        unsetClosedId();
      } else {
        setClosedId((Long)value);
      }
      break;

    case SUB_ACCOUNT_ID:
      if (value == null) {
        unsetSubAccountId();
      } else {
        setSubAccountId((Long)value);
      }
      break;

    case TARGET_KEY:
      if (value == null) {
        unsetTargetKey();
      } else {
        setTargetKey((String)value);
      }
      break;

    case TARGET_TYPE:
      if (value == null) {
        unsetTargetType();
      } else {
        setTargetType((xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType)value);
      }
      break;

    case ARCHIVE_START_DATE_TIMESTAMP_MS:
      if (value == null) {
        unsetArchiveStartDateTimestampMs();
      } else {
        setArchiveStartDateTimestampMs((Long)value);
      }
      break;

    case ARCHIVE_END_DATE_TIMESTAMP_MS:
      if (value == null) {
        unsetArchiveEndDateTimestampMs();
      } else {
        setArchiveEndDateTimestampMs((Long)value);
      }
      break;

    case ARCHIVED_DATE_TIMESTAMP_MS:
      if (value == null) {
        unsetArchivedDateTimestampMs();
      } else {
        setArchivedDateTimestampMs((Long)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CLOSED_ID:
      return Long.valueOf(getClosedId());

    case SUB_ACCOUNT_ID:
      return Long.valueOf(getSubAccountId());

    case TARGET_KEY:
      return getTargetKey();

    case TARGET_TYPE:
      return getTargetType();

    case ARCHIVE_START_DATE_TIMESTAMP_MS:
      return Long.valueOf(getArchiveStartDateTimestampMs());

    case ARCHIVE_END_DATE_TIMESTAMP_MS:
      return Long.valueOf(getArchiveEndDateTimestampMs());

    case ARCHIVED_DATE_TIMESTAMP_MS:
      return Long.valueOf(getArchivedDateTimestampMs());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CLOSED_ID:
      return isSetClosedId();
    case SUB_ACCOUNT_ID:
      return isSetSubAccountId();
    case TARGET_KEY:
      return isSetTargetKey();
    case TARGET_TYPE:
      return isSetTargetType();
    case ARCHIVE_START_DATE_TIMESTAMP_MS:
      return isSetArchiveStartDateTimestampMs();
    case ARCHIVE_END_DATE_TIMESTAMP_MS:
      return isSetArchiveEndDateTimestampMs();
    case ARCHIVED_DATE_TIMESTAMP_MS:
      return isSetArchivedDateTimestampMs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryStatArchiveItemOption)
      return this.equals((QueryStatArchiveItemOption)that);
    return false;
  }

  public boolean equals(QueryStatArchiveItemOption that) {
    if (that == null)
      return false;

    boolean this_present_closedId = true && this.isSetClosedId();
    boolean that_present_closedId = true && that.isSetClosedId();
    if (this_present_closedId || that_present_closedId) {
      if (!(this_present_closedId && that_present_closedId))
        return false;
      if (this.closedId != that.closedId)
        return false;
    }

    boolean this_present_subAccountId = true && this.isSetSubAccountId();
    boolean that_present_subAccountId = true && that.isSetSubAccountId();
    if (this_present_subAccountId || that_present_subAccountId) {
      if (!(this_present_subAccountId && that_present_subAccountId))
        return false;
      if (this.subAccountId != that.subAccountId)
        return false;
    }

    boolean this_present_targetKey = true && this.isSetTargetKey();
    boolean that_present_targetKey = true && that.isSetTargetKey();
    if (this_present_targetKey || that_present_targetKey) {
      if (!(this_present_targetKey && that_present_targetKey))
        return false;
      if (!this.targetKey.equals(that.targetKey))
        return false;
    }

    boolean this_present_targetType = true && this.isSetTargetType();
    boolean that_present_targetType = true && that.isSetTargetType();
    if (this_present_targetType || that_present_targetType) {
      if (!(this_present_targetType && that_present_targetType))
        return false;
      if (!this.targetType.equals(that.targetType))
        return false;
    }

    boolean this_present_archiveStartDateTimestampMs = true && this.isSetArchiveStartDateTimestampMs();
    boolean that_present_archiveStartDateTimestampMs = true && that.isSetArchiveStartDateTimestampMs();
    if (this_present_archiveStartDateTimestampMs || that_present_archiveStartDateTimestampMs) {
      if (!(this_present_archiveStartDateTimestampMs && that_present_archiveStartDateTimestampMs))
        return false;
      if (this.archiveStartDateTimestampMs != that.archiveStartDateTimestampMs)
        return false;
    }

    boolean this_present_archiveEndDateTimestampMs = true && this.isSetArchiveEndDateTimestampMs();
    boolean that_present_archiveEndDateTimestampMs = true && that.isSetArchiveEndDateTimestampMs();
    if (this_present_archiveEndDateTimestampMs || that_present_archiveEndDateTimestampMs) {
      if (!(this_present_archiveEndDateTimestampMs && that_present_archiveEndDateTimestampMs))
        return false;
      if (this.archiveEndDateTimestampMs != that.archiveEndDateTimestampMs)
        return false;
    }

    boolean this_present_archivedDateTimestampMs = true && this.isSetArchivedDateTimestampMs();
    boolean that_present_archivedDateTimestampMs = true && that.isSetArchivedDateTimestampMs();
    if (this_present_archivedDateTimestampMs || that_present_archivedDateTimestampMs) {
      if (!(this_present_archivedDateTimestampMs && that_present_archivedDateTimestampMs))
        return false;
      if (this.archivedDateTimestampMs != that.archivedDateTimestampMs)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryStatArchiveItemOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetClosedId()).compareTo(other.isSetClosedId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClosedId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.closedId, other.closedId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
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
    lastComparison = Boolean.valueOf(isSetTargetKey()).compareTo(other.isSetTargetKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetKey, other.targetKey);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTargetType()).compareTo(other.isSetTargetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTargetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.targetType, other.targetType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetArchiveStartDateTimestampMs()).compareTo(other.isSetArchiveStartDateTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArchiveStartDateTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.archiveStartDateTimestampMs, other.archiveStartDateTimestampMs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetArchiveEndDateTimestampMs()).compareTo(other.isSetArchiveEndDateTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArchiveEndDateTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.archiveEndDateTimestampMs, other.archiveEndDateTimestampMs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetArchivedDateTimestampMs()).compareTo(other.isSetArchivedDateTimestampMs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArchivedDateTimestampMs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.archivedDateTimestampMs, other.archivedDateTimestampMs);
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
    StringBuilder sb = new StringBuilder("QueryStatArchiveItemOption(");
    boolean first = true;

    if (isSetClosedId()) {
      sb.append("closedId:");
      sb.append(this.closedId);
      first = false;
    }
    if (isSetSubAccountId()) {
      if (!first) sb.append(", ");
      sb.append("subAccountId:");
      sb.append(this.subAccountId);
      first = false;
    }
    if (isSetTargetKey()) {
      if (!first) sb.append(", ");
      sb.append("targetKey:");
      if (this.targetKey == null) {
        sb.append("null");
      } else {
        sb.append(this.targetKey);
      }
      first = false;
    }
    if (isSetTargetType()) {
      if (!first) sb.append(", ");
      sb.append("targetType:");
      if (this.targetType == null) {
        sb.append("null");
      } else {
        sb.append(this.targetType);
      }
      first = false;
    }
    if (isSetArchiveStartDateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("archiveStartDateTimestampMs:");
      sb.append(this.archiveStartDateTimestampMs);
      first = false;
    }
    if (isSetArchiveEndDateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("archiveEndDateTimestampMs:");
      sb.append(this.archiveEndDateTimestampMs);
      first = false;
    }
    if (isSetArchivedDateTimestampMs()) {
      if (!first) sb.append(", ");
      sb.append("archivedDateTimestampMs:");
      sb.append(this.archivedDateTimestampMs);
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

  private static class QueryStatArchiveItemOptionStandardSchemeFactory implements SchemeFactory {
    public QueryStatArchiveItemOptionStandardScheme getScheme() {
      return new QueryStatArchiveItemOptionStandardScheme();
    }
  }

  private static class QueryStatArchiveItemOptionStandardScheme extends StandardScheme<QueryStatArchiveItemOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryStatArchiveItemOption struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CLOSED_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.closedId = iprot.readI64();
              struct.setClosedIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SUB_ACCOUNT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.subAccountId = iprot.readI64();
              struct.setSubAccountIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TARGET_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.targetKey = iprot.readString();
              struct.setTargetKeyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // TARGET_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.targetType = xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.findByValue(iprot.readI32());
              struct.setTargetTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // ARCHIVE_START_DATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.archiveStartDateTimestampMs = iprot.readI64();
              struct.setArchiveStartDateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 11: // ARCHIVE_END_DATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.archiveEndDateTimestampMs = iprot.readI64();
              struct.setArchiveEndDateTimestampMsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 12: // ARCHIVED_DATE_TIMESTAMP_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.archivedDateTimestampMs = iprot.readI64();
              struct.setArchivedDateTimestampMsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryStatArchiveItemOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetClosedId()) {
        oprot.writeFieldBegin(CLOSED_ID_FIELD_DESC);
        oprot.writeI64(struct.closedId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetSubAccountId()) {
        oprot.writeFieldBegin(SUB_ACCOUNT_ID_FIELD_DESC);
        oprot.writeI64(struct.subAccountId);
        oprot.writeFieldEnd();
      }
      if (struct.targetKey != null) {
        if (struct.isSetTargetKey()) {
          oprot.writeFieldBegin(TARGET_KEY_FIELD_DESC);
          oprot.writeString(struct.targetKey);
          oprot.writeFieldEnd();
        }
      }
      if (struct.targetType != null) {
        if (struct.isSetTargetType()) {
          oprot.writeFieldBegin(TARGET_TYPE_FIELD_DESC);
          oprot.writeI32(struct.targetType.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetArchiveStartDateTimestampMs()) {
        oprot.writeFieldBegin(ARCHIVE_START_DATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.archiveStartDateTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.isSetArchiveEndDateTimestampMs()) {
        oprot.writeFieldBegin(ARCHIVE_END_DATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.archiveEndDateTimestampMs);
        oprot.writeFieldEnd();
      }
      if (struct.isSetArchivedDateTimestampMs()) {
        oprot.writeFieldBegin(ARCHIVED_DATE_TIMESTAMP_MS_FIELD_DESC);
        oprot.writeI64(struct.archivedDateTimestampMs);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryStatArchiveItemOptionTupleSchemeFactory implements SchemeFactory {
    public QueryStatArchiveItemOptionTupleScheme getScheme() {
      return new QueryStatArchiveItemOptionTupleScheme();
    }
  }

  private static class QueryStatArchiveItemOptionTupleScheme extends TupleScheme<QueryStatArchiveItemOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryStatArchiveItemOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetClosedId()) {
        optionals.set(0);
      }
      if (struct.isSetSubAccountId()) {
        optionals.set(1);
      }
      if (struct.isSetTargetKey()) {
        optionals.set(2);
      }
      if (struct.isSetTargetType()) {
        optionals.set(3);
      }
      if (struct.isSetArchiveStartDateTimestampMs()) {
        optionals.set(4);
      }
      if (struct.isSetArchiveEndDateTimestampMs()) {
        optionals.set(5);
      }
      if (struct.isSetArchivedDateTimestampMs()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetClosedId()) {
        oprot.writeI64(struct.closedId);
      }
      if (struct.isSetSubAccountId()) {
        oprot.writeI64(struct.subAccountId);
      }
      if (struct.isSetTargetKey()) {
        oprot.writeString(struct.targetKey);
      }
      if (struct.isSetTargetType()) {
        oprot.writeI32(struct.targetType.getValue());
      }
      if (struct.isSetArchiveStartDateTimestampMs()) {
        oprot.writeI64(struct.archiveStartDateTimestampMs);
      }
      if (struct.isSetArchiveEndDateTimestampMs()) {
        oprot.writeI64(struct.archiveEndDateTimestampMs);
      }
      if (struct.isSetArchivedDateTimestampMs()) {
        oprot.writeI64(struct.archivedDateTimestampMs);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryStatArchiveItemOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.closedId = iprot.readI64();
        struct.setClosedIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.subAccountId = iprot.readI64();
        struct.setSubAccountIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.targetKey = iprot.readString();
        struct.setTargetKeyIsSet(true);
      }
      if (incoming.get(3)) {
        struct.targetType = xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType.findByValue(iprot.readI32());
        struct.setTargetTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.archiveStartDateTimestampMs = iprot.readI64();
        struct.setArchiveStartDateTimestampMsIsSet(true);
      }
      if (incoming.get(5)) {
        struct.archiveEndDateTimestampMs = iprot.readI64();
        struct.setArchiveEndDateTimestampMsIsSet(true);
      }
      if (incoming.get(6)) {
        struct.archivedDateTimestampMs = iprot.readI64();
        struct.setArchivedDateTimestampMsIsSet(true);
      }
    }
  }

}

