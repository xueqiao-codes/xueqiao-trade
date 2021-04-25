/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.push.protocol;

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

public class QuotationSubscribeReq implements org.apache.thrift.TBase<QuotationSubscribeReq, QuotationSubscribeReq._Fields>, java.io.Serializable, Cloneable, Comparable<QuotationSubscribeReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QuotationSubscribeReq");

  private static final org.apache.thrift.protocol.TField TOPICS_FIELD_DESC = new org.apache.thrift.protocol.TField("topics", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QuotationSubscribeReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QuotationSubscribeReqTupleSchemeFactory());
  }

  public List<QuotationTopic> topics; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOPICS((short)1, "topics");

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
        case 1: // TOPICS
          return TOPICS;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOPICS, new org.apache.thrift.meta_data.FieldMetaData("topics", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, QuotationTopic.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QuotationSubscribeReq.class, metaDataMap);
  }

  public QuotationSubscribeReq() {
  }

  public QuotationSubscribeReq(
    List<QuotationTopic> topics)
  {
    this();
    this.topics = topics;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QuotationSubscribeReq(QuotationSubscribeReq other) {
    if (other.isSetTopics()) {
      List<QuotationTopic> __this__topics = new ArrayList<QuotationTopic>(other.topics.size());
      for (QuotationTopic other_element : other.topics) {
        __this__topics.add(new QuotationTopic(other_element));
      }
      this.topics = __this__topics;
    }
  }

  public QuotationSubscribeReq deepCopy() {
    return new QuotationSubscribeReq(this);
  }

  @Override
  public void clear() {
    this.topics = null;
  }

  public int getTopicsSize() {
    return (this.topics == null) ? 0 : this.topics.size();
  }

  public java.util.Iterator<QuotationTopic> getTopicsIterator() {
    return (this.topics == null) ? null : this.topics.iterator();
  }

  public void addToTopics(QuotationTopic elem) {
    if (this.topics == null) {
      this.topics = new ArrayList<QuotationTopic>();
    }
    this.topics.add(elem);
  }

  public List<QuotationTopic> getTopics() {
    return this.topics;
  }

  public QuotationSubscribeReq setTopics(List<QuotationTopic> topics) {
    this.topics = topics;
    return this;
  }

  public void unsetTopics() {
    this.topics = null;
  }

  /** Returns true if field topics is set (has been assigned a value) and false otherwise */
  public boolean isSetTopics() {
    return this.topics != null;
  }

  public void setTopicsIsSet(boolean value) {
    if (!value) {
      this.topics = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOPICS:
      if (value == null) {
        unsetTopics();
      } else {
        setTopics((List<QuotationTopic>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOPICS:
      return getTopics();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOPICS:
      return isSetTopics();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QuotationSubscribeReq)
      return this.equals((QuotationSubscribeReq)that);
    return false;
  }

  public boolean equals(QuotationSubscribeReq that) {
    if (that == null)
      return false;

    boolean this_present_topics = true && this.isSetTopics();
    boolean that_present_topics = true && that.isSetTopics();
    if (this_present_topics || that_present_topics) {
      if (!(this_present_topics && that_present_topics))
        return false;
      if (!this.topics.equals(that.topics))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QuotationSubscribeReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTopics()).compareTo(other.isSetTopics());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTopics()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.topics, other.topics);
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
    StringBuilder sb = new StringBuilder("QuotationSubscribeReq(");
    boolean first = true;

    sb.append("topics:");
    if (this.topics == null) {
      sb.append("null");
    } else {
      sb.append(this.topics);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (topics == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'topics' was not present! Struct: " + toString());
    }
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

  private static class QuotationSubscribeReqStandardSchemeFactory implements SchemeFactory {
    public QuotationSubscribeReqStandardScheme getScheme() {
      return new QuotationSubscribeReqStandardScheme();
    }
  }

  private static class QuotationSubscribeReqStandardScheme extends StandardScheme<QuotationSubscribeReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QuotationSubscribeReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOPICS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.topics = new ArrayList<QuotationTopic>(_list8.size);
                for (int _i9 = 0; _i9 < _list8.size; ++_i9)
                {
                  QuotationTopic _elem10;
                  _elem10 = new QuotationTopic();
                  _elem10.read(iprot);
                  struct.topics.add(_elem10);
                }
                iprot.readListEnd();
              }
              struct.setTopicsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QuotationSubscribeReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.topics != null) {
        oprot.writeFieldBegin(TOPICS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.topics.size()));
          for (QuotationTopic _iter11 : struct.topics)
          {
            _iter11.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QuotationSubscribeReqTupleSchemeFactory implements SchemeFactory {
    public QuotationSubscribeReqTupleScheme getScheme() {
      return new QuotationSubscribeReqTupleScheme();
    }
  }

  private static class QuotationSubscribeReqTupleScheme extends TupleScheme<QuotationSubscribeReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QuotationSubscribeReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.topics.size());
        for (QuotationTopic _iter12 : struct.topics)
        {
          _iter12.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QuotationSubscribeReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list13 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.topics = new ArrayList<QuotationTopic>(_list13.size);
        for (int _i14 = 0; _i14 < _list13.size; ++_i14)
        {
          QuotationTopic _elem15;
          _elem15 = new QuotationTopic();
          _elem15.read(iprot);
          struct.topics.add(_elem15);
        }
      }
      struct.setTopicsIsSet(true);
    }
  }

}

