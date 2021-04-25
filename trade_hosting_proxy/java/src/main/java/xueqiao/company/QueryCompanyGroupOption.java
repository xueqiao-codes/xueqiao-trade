/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.company;

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

public class QueryCompanyGroupOption implements org.apache.thrift.TBase<QueryCompanyGroupOption, QueryCompanyGroupOption._Fields>, java.io.Serializable, Cloneable, Comparable<QueryCompanyGroupOption> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QueryCompanyGroupOption");

  private static final org.apache.thrift.protocol.TField COMPANY_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("companyId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField GROUP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("groupId", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField GROUP_CODE_PARTICAL_FIELD_DESC = new org.apache.thrift.protocol.TField("groupCodePartical", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField GROUP_CODE_WHOLE_FIELD_DESC = new org.apache.thrift.protocol.TField("groupCodeWhole", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField GROUP_NAME_PARTICAL_FIELD_DESC = new org.apache.thrift.protocol.TField("groupNamePartical", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField GROUP_NAME_WHOLE_FIELD_DESC = new org.apache.thrift.protocol.TField("groupNameWhole", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QueryCompanyGroupOptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QueryCompanyGroupOptionTupleSchemeFactory());
  }

  public int companyId; // optional
  public int groupId; // optional
  public String groupCodePartical; // optional
  public String groupCodeWhole; // optional
  public String groupNamePartical; // optional
  public String groupNameWhole; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMPANY_ID((short)1, "companyId"),
    GROUP_ID((short)2, "groupId"),
    GROUP_CODE_PARTICAL((short)3, "groupCodePartical"),
    GROUP_CODE_WHOLE((short)4, "groupCodeWhole"),
    GROUP_NAME_PARTICAL((short)5, "groupNamePartical"),
    GROUP_NAME_WHOLE((short)6, "groupNameWhole");

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
        case 1: // COMPANY_ID
          return COMPANY_ID;
        case 2: // GROUP_ID
          return GROUP_ID;
        case 3: // GROUP_CODE_PARTICAL
          return GROUP_CODE_PARTICAL;
        case 4: // GROUP_CODE_WHOLE
          return GROUP_CODE_WHOLE;
        case 5: // GROUP_NAME_PARTICAL
          return GROUP_NAME_PARTICAL;
        case 6: // GROUP_NAME_WHOLE
          return GROUP_NAME_WHOLE;
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
  private static final int __COMPANYID_ISSET_ID = 0;
  private static final int __GROUPID_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.COMPANY_ID,_Fields.GROUP_ID,_Fields.GROUP_CODE_PARTICAL,_Fields.GROUP_CODE_WHOLE,_Fields.GROUP_NAME_PARTICAL,_Fields.GROUP_NAME_WHOLE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMPANY_ID, new org.apache.thrift.meta_data.FieldMetaData("companyId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GROUP_ID, new org.apache.thrift.meta_data.FieldMetaData("groupId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GROUP_CODE_PARTICAL, new org.apache.thrift.meta_data.FieldMetaData("groupCodePartical", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.GROUP_CODE_WHOLE, new org.apache.thrift.meta_data.FieldMetaData("groupCodeWhole", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.GROUP_NAME_PARTICAL, new org.apache.thrift.meta_data.FieldMetaData("groupNamePartical", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.GROUP_NAME_WHOLE, new org.apache.thrift.meta_data.FieldMetaData("groupNameWhole", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QueryCompanyGroupOption.class, metaDataMap);
  }

  public QueryCompanyGroupOption() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QueryCompanyGroupOption(QueryCompanyGroupOption other) {
    __isset_bitfield = other.__isset_bitfield;
    this.companyId = other.companyId;
    this.groupId = other.groupId;
    if (other.isSetGroupCodePartical()) {
      this.groupCodePartical = other.groupCodePartical;
    }
    if (other.isSetGroupCodeWhole()) {
      this.groupCodeWhole = other.groupCodeWhole;
    }
    if (other.isSetGroupNamePartical()) {
      this.groupNamePartical = other.groupNamePartical;
    }
    if (other.isSetGroupNameWhole()) {
      this.groupNameWhole = other.groupNameWhole;
    }
  }

  public QueryCompanyGroupOption deepCopy() {
    return new QueryCompanyGroupOption(this);
  }

  @Override
  public void clear() {
    setCompanyIdIsSet(false);
    this.companyId = 0;
    setGroupIdIsSet(false);
    this.groupId = 0;
    this.groupCodePartical = null;
    this.groupCodeWhole = null;
    this.groupNamePartical = null;
    this.groupNameWhole = null;
  }

  public int getCompanyId() {
    return this.companyId;
  }

  public QueryCompanyGroupOption setCompanyId(int companyId) {
    this.companyId = companyId;
    setCompanyIdIsSet(true);
    return this;
  }

  public void unsetCompanyId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  /** Returns true if field companyId is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyId() {
    return EncodingUtils.testBit(__isset_bitfield, __COMPANYID_ISSET_ID);
  }

  public void setCompanyIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMPANYID_ISSET_ID, value);
  }

  public int getGroupId() {
    return this.groupId;
  }

  public QueryCompanyGroupOption setGroupId(int groupId) {
    this.groupId = groupId;
    setGroupIdIsSet(true);
    return this;
  }

  public void unsetGroupId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GROUPID_ISSET_ID);
  }

  /** Returns true if field groupId is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupId() {
    return EncodingUtils.testBit(__isset_bitfield, __GROUPID_ISSET_ID);
  }

  public void setGroupIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GROUPID_ISSET_ID, value);
  }

  public String getGroupCodePartical() {
    return this.groupCodePartical;
  }

  public QueryCompanyGroupOption setGroupCodePartical(String groupCodePartical) {
    this.groupCodePartical = groupCodePartical;
    return this;
  }

  public void unsetGroupCodePartical() {
    this.groupCodePartical = null;
  }

  /** Returns true if field groupCodePartical is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupCodePartical() {
    return this.groupCodePartical != null;
  }

  public void setGroupCodeParticalIsSet(boolean value) {
    if (!value) {
      this.groupCodePartical = null;
    }
  }

  public String getGroupCodeWhole() {
    return this.groupCodeWhole;
  }

  public QueryCompanyGroupOption setGroupCodeWhole(String groupCodeWhole) {
    this.groupCodeWhole = groupCodeWhole;
    return this;
  }

  public void unsetGroupCodeWhole() {
    this.groupCodeWhole = null;
  }

  /** Returns true if field groupCodeWhole is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupCodeWhole() {
    return this.groupCodeWhole != null;
  }

  public void setGroupCodeWholeIsSet(boolean value) {
    if (!value) {
      this.groupCodeWhole = null;
    }
  }

  public String getGroupNamePartical() {
    return this.groupNamePartical;
  }

  public QueryCompanyGroupOption setGroupNamePartical(String groupNamePartical) {
    this.groupNamePartical = groupNamePartical;
    return this;
  }

  public void unsetGroupNamePartical() {
    this.groupNamePartical = null;
  }

  /** Returns true if field groupNamePartical is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupNamePartical() {
    return this.groupNamePartical != null;
  }

  public void setGroupNameParticalIsSet(boolean value) {
    if (!value) {
      this.groupNamePartical = null;
    }
  }

  public String getGroupNameWhole() {
    return this.groupNameWhole;
  }

  public QueryCompanyGroupOption setGroupNameWhole(String groupNameWhole) {
    this.groupNameWhole = groupNameWhole;
    return this;
  }

  public void unsetGroupNameWhole() {
    this.groupNameWhole = null;
  }

  /** Returns true if field groupNameWhole is set (has been assigned a value) and false otherwise */
  public boolean isSetGroupNameWhole() {
    return this.groupNameWhole != null;
  }

  public void setGroupNameWholeIsSet(boolean value) {
    if (!value) {
      this.groupNameWhole = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COMPANY_ID:
      if (value == null) {
        unsetCompanyId();
      } else {
        setCompanyId((Integer)value);
      }
      break;

    case GROUP_ID:
      if (value == null) {
        unsetGroupId();
      } else {
        setGroupId((Integer)value);
      }
      break;

    case GROUP_CODE_PARTICAL:
      if (value == null) {
        unsetGroupCodePartical();
      } else {
        setGroupCodePartical((String)value);
      }
      break;

    case GROUP_CODE_WHOLE:
      if (value == null) {
        unsetGroupCodeWhole();
      } else {
        setGroupCodeWhole((String)value);
      }
      break;

    case GROUP_NAME_PARTICAL:
      if (value == null) {
        unsetGroupNamePartical();
      } else {
        setGroupNamePartical((String)value);
      }
      break;

    case GROUP_NAME_WHOLE:
      if (value == null) {
        unsetGroupNameWhole();
      } else {
        setGroupNameWhole((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COMPANY_ID:
      return Integer.valueOf(getCompanyId());

    case GROUP_ID:
      return Integer.valueOf(getGroupId());

    case GROUP_CODE_PARTICAL:
      return getGroupCodePartical();

    case GROUP_CODE_WHOLE:
      return getGroupCodeWhole();

    case GROUP_NAME_PARTICAL:
      return getGroupNamePartical();

    case GROUP_NAME_WHOLE:
      return getGroupNameWhole();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COMPANY_ID:
      return isSetCompanyId();
    case GROUP_ID:
      return isSetGroupId();
    case GROUP_CODE_PARTICAL:
      return isSetGroupCodePartical();
    case GROUP_CODE_WHOLE:
      return isSetGroupCodeWhole();
    case GROUP_NAME_PARTICAL:
      return isSetGroupNamePartical();
    case GROUP_NAME_WHOLE:
      return isSetGroupNameWhole();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QueryCompanyGroupOption)
      return this.equals((QueryCompanyGroupOption)that);
    return false;
  }

  public boolean equals(QueryCompanyGroupOption that) {
    if (that == null)
      return false;

    boolean this_present_companyId = true && this.isSetCompanyId();
    boolean that_present_companyId = true && that.isSetCompanyId();
    if (this_present_companyId || that_present_companyId) {
      if (!(this_present_companyId && that_present_companyId))
        return false;
      if (this.companyId != that.companyId)
        return false;
    }

    boolean this_present_groupId = true && this.isSetGroupId();
    boolean that_present_groupId = true && that.isSetGroupId();
    if (this_present_groupId || that_present_groupId) {
      if (!(this_present_groupId && that_present_groupId))
        return false;
      if (this.groupId != that.groupId)
        return false;
    }

    boolean this_present_groupCodePartical = true && this.isSetGroupCodePartical();
    boolean that_present_groupCodePartical = true && that.isSetGroupCodePartical();
    if (this_present_groupCodePartical || that_present_groupCodePartical) {
      if (!(this_present_groupCodePartical && that_present_groupCodePartical))
        return false;
      if (!this.groupCodePartical.equals(that.groupCodePartical))
        return false;
    }

    boolean this_present_groupCodeWhole = true && this.isSetGroupCodeWhole();
    boolean that_present_groupCodeWhole = true && that.isSetGroupCodeWhole();
    if (this_present_groupCodeWhole || that_present_groupCodeWhole) {
      if (!(this_present_groupCodeWhole && that_present_groupCodeWhole))
        return false;
      if (!this.groupCodeWhole.equals(that.groupCodeWhole))
        return false;
    }

    boolean this_present_groupNamePartical = true && this.isSetGroupNamePartical();
    boolean that_present_groupNamePartical = true && that.isSetGroupNamePartical();
    if (this_present_groupNamePartical || that_present_groupNamePartical) {
      if (!(this_present_groupNamePartical && that_present_groupNamePartical))
        return false;
      if (!this.groupNamePartical.equals(that.groupNamePartical))
        return false;
    }

    boolean this_present_groupNameWhole = true && this.isSetGroupNameWhole();
    boolean that_present_groupNameWhole = true && that.isSetGroupNameWhole();
    if (this_present_groupNameWhole || that_present_groupNameWhole) {
      if (!(this_present_groupNameWhole && that_present_groupNameWhole))
        return false;
      if (!this.groupNameWhole.equals(that.groupNameWhole))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QueryCompanyGroupOption other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCompanyId()).compareTo(other.isSetCompanyId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyId, other.companyId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupId()).compareTo(other.isSetGroupId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupId, other.groupId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupCodePartical()).compareTo(other.isSetGroupCodePartical());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupCodePartical()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupCodePartical, other.groupCodePartical);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupCodeWhole()).compareTo(other.isSetGroupCodeWhole());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupCodeWhole()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupCodeWhole, other.groupCodeWhole);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupNamePartical()).compareTo(other.isSetGroupNamePartical());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupNamePartical()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupNamePartical, other.groupNamePartical);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGroupNameWhole()).compareTo(other.isSetGroupNameWhole());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGroupNameWhole()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.groupNameWhole, other.groupNameWhole);
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
    StringBuilder sb = new StringBuilder("QueryCompanyGroupOption(");
    boolean first = true;

    if (isSetCompanyId()) {
      sb.append("companyId:");
      sb.append(this.companyId);
      first = false;
    }
    if (isSetGroupId()) {
      if (!first) sb.append(", ");
      sb.append("groupId:");
      sb.append(this.groupId);
      first = false;
    }
    if (isSetGroupCodePartical()) {
      if (!first) sb.append(", ");
      sb.append("groupCodePartical:");
      if (this.groupCodePartical == null) {
        sb.append("null");
      } else {
        sb.append(this.groupCodePartical);
      }
      first = false;
    }
    if (isSetGroupCodeWhole()) {
      if (!first) sb.append(", ");
      sb.append("groupCodeWhole:");
      if (this.groupCodeWhole == null) {
        sb.append("null");
      } else {
        sb.append(this.groupCodeWhole);
      }
      first = false;
    }
    if (isSetGroupNamePartical()) {
      if (!first) sb.append(", ");
      sb.append("groupNamePartical:");
      if (this.groupNamePartical == null) {
        sb.append("null");
      } else {
        sb.append(this.groupNamePartical);
      }
      first = false;
    }
    if (isSetGroupNameWhole()) {
      if (!first) sb.append(", ");
      sb.append("groupNameWhole:");
      if (this.groupNameWhole == null) {
        sb.append("null");
      } else {
        sb.append(this.groupNameWhole);
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

  private static class QueryCompanyGroupOptionStandardSchemeFactory implements SchemeFactory {
    public QueryCompanyGroupOptionStandardScheme getScheme() {
      return new QueryCompanyGroupOptionStandardScheme();
    }
  }

  private static class QueryCompanyGroupOptionStandardScheme extends StandardScheme<QueryCompanyGroupOption> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QueryCompanyGroupOption struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMPANY_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.companyId = iprot.readI32();
              struct.setCompanyIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GROUP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.groupId = iprot.readI32();
              struct.setGroupIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // GROUP_CODE_PARTICAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupCodePartical = iprot.readString();
              struct.setGroupCodeParticalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // GROUP_CODE_WHOLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupCodeWhole = iprot.readString();
              struct.setGroupCodeWholeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // GROUP_NAME_PARTICAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupNamePartical = iprot.readString();
              struct.setGroupNameParticalIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // GROUP_NAME_WHOLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.groupNameWhole = iprot.readString();
              struct.setGroupNameWholeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QueryCompanyGroupOption struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetCompanyId()) {
        oprot.writeFieldBegin(COMPANY_ID_FIELD_DESC);
        oprot.writeI32(struct.companyId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetGroupId()) {
        oprot.writeFieldBegin(GROUP_ID_FIELD_DESC);
        oprot.writeI32(struct.groupId);
        oprot.writeFieldEnd();
      }
      if (struct.groupCodePartical != null) {
        if (struct.isSetGroupCodePartical()) {
          oprot.writeFieldBegin(GROUP_CODE_PARTICAL_FIELD_DESC);
          oprot.writeString(struct.groupCodePartical);
          oprot.writeFieldEnd();
        }
      }
      if (struct.groupCodeWhole != null) {
        if (struct.isSetGroupCodeWhole()) {
          oprot.writeFieldBegin(GROUP_CODE_WHOLE_FIELD_DESC);
          oprot.writeString(struct.groupCodeWhole);
          oprot.writeFieldEnd();
        }
      }
      if (struct.groupNamePartical != null) {
        if (struct.isSetGroupNamePartical()) {
          oprot.writeFieldBegin(GROUP_NAME_PARTICAL_FIELD_DESC);
          oprot.writeString(struct.groupNamePartical);
          oprot.writeFieldEnd();
        }
      }
      if (struct.groupNameWhole != null) {
        if (struct.isSetGroupNameWhole()) {
          oprot.writeFieldBegin(GROUP_NAME_WHOLE_FIELD_DESC);
          oprot.writeString(struct.groupNameWhole);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QueryCompanyGroupOptionTupleSchemeFactory implements SchemeFactory {
    public QueryCompanyGroupOptionTupleScheme getScheme() {
      return new QueryCompanyGroupOptionTupleScheme();
    }
  }

  private static class QueryCompanyGroupOptionTupleScheme extends TupleScheme<QueryCompanyGroupOption> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QueryCompanyGroupOption struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCompanyId()) {
        optionals.set(0);
      }
      if (struct.isSetGroupId()) {
        optionals.set(1);
      }
      if (struct.isSetGroupCodePartical()) {
        optionals.set(2);
      }
      if (struct.isSetGroupCodeWhole()) {
        optionals.set(3);
      }
      if (struct.isSetGroupNamePartical()) {
        optionals.set(4);
      }
      if (struct.isSetGroupNameWhole()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetCompanyId()) {
        oprot.writeI32(struct.companyId);
      }
      if (struct.isSetGroupId()) {
        oprot.writeI32(struct.groupId);
      }
      if (struct.isSetGroupCodePartical()) {
        oprot.writeString(struct.groupCodePartical);
      }
      if (struct.isSetGroupCodeWhole()) {
        oprot.writeString(struct.groupCodeWhole);
      }
      if (struct.isSetGroupNamePartical()) {
        oprot.writeString(struct.groupNamePartical);
      }
      if (struct.isSetGroupNameWhole()) {
        oprot.writeString(struct.groupNameWhole);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QueryCompanyGroupOption struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.companyId = iprot.readI32();
        struct.setCompanyIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.groupId = iprot.readI32();
        struct.setGroupIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.groupCodePartical = iprot.readString();
        struct.setGroupCodeParticalIsSet(true);
      }
      if (incoming.get(3)) {
        struct.groupCodeWhole = iprot.readString();
        struct.setGroupCodeWholeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.groupNamePartical = iprot.readString();
        struct.setGroupNameParticalIsSet(true);
      }
      if (incoming.get(5)) {
        struct.groupNameWhole = iprot.readString();
        struct.setGroupNameWholeIsSet(true);
      }
    }
  }

}

