/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.events;

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

public class ComposeViewEvent implements org.apache.thrift.TBase<ComposeViewEvent, ComposeViewEvent._Fields>, java.io.Serializable, Cloneable, Comparable<ComposeViewEvent> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ComposeViewEvent");

  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField COMPOSE_GRAPH_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("composeGraphId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField SUB_USER_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("subUserIds", org.apache.thrift.protocol.TType.SET, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ComposeViewEventStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ComposeViewEventTupleSchemeFactory());
  }

  /**
   * 
   * @see ComposeViewEventType
   */
  public ComposeViewEventType type; // optional
  public long composeGraphId; // optional
  public Set<Integer> subUserIds; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 
     * @see ComposeViewEventType
     */
    TYPE((short)1, "type"),
    COMPOSE_GRAPH_ID((short)2, "composeGraphId"),
    SUB_USER_IDS((short)3, "subUserIds");

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
        case 1: // TYPE
          return TYPE;
        case 2: // COMPOSE_GRAPH_ID
          return COMPOSE_GRAPH_ID;
        case 3: // SUB_USER_IDS
          return SUB_USER_IDS;
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
  private static final int __COMPOSEGRAPHID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TYPE,_Fields.COMPOSE_GRAPH_ID,_Fields.SUB_USER_IDS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ComposeViewEventType.class)));
    tmpMap.put(_Fields.COMPOSE_GRAPH_ID, new org.apache.thrift.meta_data.FieldMetaData("composeGraphId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.SUB_USER_IDS, new org.apache.thrift.meta_data.FieldMetaData("subUserIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ComposeViewEvent.class, metaDataMap);
  }

  public ComposeViewEvent() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ComposeViewEvent(ComposeViewEvent other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetType()) {
      this.type = other.type;
    }
    this.composeGraphId = other.composeGraphId;
    if (other.isSetSubUserIds()) {
      Set<Integer> __this__subUserIds = new HashSet<Integer>(other.subUserIds);
      this.subUserIds = __this__subUserIds;
    }
  }

  public ComposeViewEvent deepCopy() {
    return new ComposeViewEvent(this);
  }

  @Override
  public void clear() {
    this.type = null;
    setComposeGraphIdIsSet(false);
    this.composeGraphId = 0;
    this.subUserIds = null;
  }

  /**
   * 
   * @see ComposeViewEventType
   */
  public ComposeViewEventType getType() {
    return this.type;
  }

  /**
   * 
   * @see ComposeViewEventType
   */
  public ComposeViewEvent setType(ComposeViewEventType type) {
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

  public long getComposeGraphId() {
    return this.composeGraphId;
  }

  public ComposeViewEvent setComposeGraphId(long composeGraphId) {
    this.composeGraphId = composeGraphId;
    setComposeGraphIdIsSet(true);
    return this;
  }

  public void unsetComposeGraphId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMPOSEGRAPHID_ISSET_ID);
  }

  /** Returns true if field composeGraphId is set (has been assigned a value) and false otherwise */
  public boolean isSetComposeGraphId() {
    return EncodingUtils.testBit(__isset_bitfield, __COMPOSEGRAPHID_ISSET_ID);
  }

  public void setComposeGraphIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMPOSEGRAPHID_ISSET_ID, value);
  }

  public int getSubUserIdsSize() {
    return (this.subUserIds == null) ? 0 : this.subUserIds.size();
  }

  public java.util.Iterator<Integer> getSubUserIdsIterator() {
    return (this.subUserIds == null) ? null : this.subUserIds.iterator();
  }

  public void addToSubUserIds(int elem) {
    if (this.subUserIds == null) {
      this.subUserIds = new HashSet<Integer>();
    }
    this.subUserIds.add(elem);
  }

  public Set<Integer> getSubUserIds() {
    return this.subUserIds;
  }

  public ComposeViewEvent setSubUserIds(Set<Integer> subUserIds) {
    this.subUserIds = subUserIds;
    return this;
  }

  public void unsetSubUserIds() {
    this.subUserIds = null;
  }

  /** Returns true if field subUserIds is set (has been assigned a value) and false otherwise */
  public boolean isSetSubUserIds() {
    return this.subUserIds != null;
  }

  public void setSubUserIdsIsSet(boolean value) {
    if (!value) {
      this.subUserIds = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((ComposeViewEventType)value);
      }
      break;

    case COMPOSE_GRAPH_ID:
      if (value == null) {
        unsetComposeGraphId();
      } else {
        setComposeGraphId((Long)value);
      }
      break;

    case SUB_USER_IDS:
      if (value == null) {
        unsetSubUserIds();
      } else {
        setSubUserIds((Set<Integer>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TYPE:
      return getType();

    case COMPOSE_GRAPH_ID:
      return Long.valueOf(getComposeGraphId());

    case SUB_USER_IDS:
      return getSubUserIds();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TYPE:
      return isSetType();
    case COMPOSE_GRAPH_ID:
      return isSetComposeGraphId();
    case SUB_USER_IDS:
      return isSetSubUserIds();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ComposeViewEvent)
      return this.equals((ComposeViewEvent)that);
    return false;
  }

  public boolean equals(ComposeViewEvent that) {
    if (that == null)
      return false;

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_composeGraphId = true && this.isSetComposeGraphId();
    boolean that_present_composeGraphId = true && that.isSetComposeGraphId();
    if (this_present_composeGraphId || that_present_composeGraphId) {
      if (!(this_present_composeGraphId && that_present_composeGraphId))
        return false;
      if (this.composeGraphId != that.composeGraphId)
        return false;
    }

    boolean this_present_subUserIds = true && this.isSetSubUserIds();
    boolean that_present_subUserIds = true && that.isSetSubUserIds();
    if (this_present_subUserIds || that_present_subUserIds) {
      if (!(this_present_subUserIds && that_present_subUserIds))
        return false;
      if (!this.subUserIds.equals(that.subUserIds))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ComposeViewEvent other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

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
    lastComparison = Boolean.valueOf(isSetComposeGraphId()).compareTo(other.isSetComposeGraphId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetComposeGraphId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.composeGraphId, other.composeGraphId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubUserIds()).compareTo(other.isSetSubUserIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubUserIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subUserIds, other.subUserIds);
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
    StringBuilder sb = new StringBuilder("ComposeViewEvent(");
    boolean first = true;

    if (isSetType()) {
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
      }
      first = false;
    }
    if (isSetComposeGraphId()) {
      if (!first) sb.append(", ");
      sb.append("composeGraphId:");
      sb.append(this.composeGraphId);
      first = false;
    }
    if (isSetSubUserIds()) {
      if (!first) sb.append(", ");
      sb.append("subUserIds:");
      if (this.subUserIds == null) {
        sb.append("null");
      } else {
        sb.append(this.subUserIds);
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

  private static class ComposeViewEventStandardSchemeFactory implements SchemeFactory {
    public ComposeViewEventStandardScheme getScheme() {
      return new ComposeViewEventStandardScheme();
    }
  }

  private static class ComposeViewEventStandardScheme extends StandardScheme<ComposeViewEvent> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ComposeViewEvent struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = ComposeViewEventType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMPOSE_GRAPH_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.composeGraphId = iprot.readI64();
              struct.setComposeGraphIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SUB_USER_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set8 = iprot.readSetBegin();
                struct.subUserIds = new HashSet<Integer>(2*_set8.size);
                for (int _i9 = 0; _i9 < _set8.size; ++_i9)
                {
                  int _elem10;
                  _elem10 = iprot.readI32();
                  struct.subUserIds.add(_elem10);
                }
                iprot.readSetEnd();
              }
              struct.setSubUserIdsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ComposeViewEvent struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeI32(struct.type.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetComposeGraphId()) {
        oprot.writeFieldBegin(COMPOSE_GRAPH_ID_FIELD_DESC);
        oprot.writeI64(struct.composeGraphId);
        oprot.writeFieldEnd();
      }
      if (struct.subUserIds != null) {
        if (struct.isSetSubUserIds()) {
          oprot.writeFieldBegin(SUB_USER_IDS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I32, struct.subUserIds.size()));
            for (int _iter11 : struct.subUserIds)
            {
              oprot.writeI32(_iter11);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ComposeViewEventTupleSchemeFactory implements SchemeFactory {
    public ComposeViewEventTupleScheme getScheme() {
      return new ComposeViewEventTupleScheme();
    }
  }

  private static class ComposeViewEventTupleScheme extends TupleScheme<ComposeViewEvent> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ComposeViewEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetType()) {
        optionals.set(0);
      }
      if (struct.isSetComposeGraphId()) {
        optionals.set(1);
      }
      if (struct.isSetSubUserIds()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetComposeGraphId()) {
        oprot.writeI64(struct.composeGraphId);
      }
      if (struct.isSetSubUserIds()) {
        {
          oprot.writeI32(struct.subUserIds.size());
          for (int _iter12 : struct.subUserIds)
          {
            oprot.writeI32(_iter12);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ComposeViewEvent struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.type = ComposeViewEventType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.composeGraphId = iprot.readI64();
        struct.setComposeGraphIdIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TSet _set13 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.I32, iprot.readI32());
          struct.subUserIds = new HashSet<Integer>(2*_set13.size);
          for (int _i14 = 0; _i14 < _set13.size; ++_i14)
          {
            int _elem15;
            _elem15 = iprot.readI32();
            struct.subUserIds.add(_elem15);
          }
        }
        struct.setSubUserIdsIsSet(true);
      }
    }
  }

}
