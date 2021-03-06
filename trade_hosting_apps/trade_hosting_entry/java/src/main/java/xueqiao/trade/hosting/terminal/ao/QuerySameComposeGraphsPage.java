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

public class QuerySameComposeGraphsPage implements org.apache.thrift.TBase<QuerySameComposeGraphsPage, QuerySameComposeGraphsPage._Fields>, java.io.Serializable, Cloneable, Comparable<QuerySameComposeGraphsPage> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("QuerySameComposeGraphsPage");

  private static final org.apache.thrift.protocol.TField TOTAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("totalCount", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField GRAPHS_FIELD_DESC = new org.apache.thrift.protocol.TField("graphs", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new QuerySameComposeGraphsPageStandardSchemeFactory());
    schemes.put(TupleScheme.class, new QuerySameComposeGraphsPageTupleSchemeFactory());
  }

  public int totalCount; // optional
  public List<xueqiao.trade.hosting.HostingComposeGraph> graphs; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TOTAL_COUNT((short)1, "totalCount"),
    GRAPHS((short)2, "graphs");

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
        case 1: // TOTAL_COUNT
          return TOTAL_COUNT;
        case 2: // GRAPHS
          return GRAPHS;
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
  private static final int __TOTALCOUNT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.TOTAL_COUNT,_Fields.GRAPHS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.TOTAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("totalCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GRAPHS, new org.apache.thrift.meta_data.FieldMetaData("graphs", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, xueqiao.trade.hosting.HostingComposeGraph.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(QuerySameComposeGraphsPage.class, metaDataMap);
  }

  public QuerySameComposeGraphsPage() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public QuerySameComposeGraphsPage(QuerySameComposeGraphsPage other) {
    __isset_bitfield = other.__isset_bitfield;
    this.totalCount = other.totalCount;
    if (other.isSetGraphs()) {
      List<xueqiao.trade.hosting.HostingComposeGraph> __this__graphs = new ArrayList<xueqiao.trade.hosting.HostingComposeGraph>(other.graphs.size());
      for (xueqiao.trade.hosting.HostingComposeGraph other_element : other.graphs) {
        __this__graphs.add(new xueqiao.trade.hosting.HostingComposeGraph(other_element));
      }
      this.graphs = __this__graphs;
    }
  }

  public QuerySameComposeGraphsPage deepCopy() {
    return new QuerySameComposeGraphsPage(this);
  }

  @Override
  public void clear() {
    setTotalCountIsSet(false);
    this.totalCount = 0;
    this.graphs = null;
  }

  public int getTotalCount() {
    return this.totalCount;
  }

  public QuerySameComposeGraphsPage setTotalCount(int totalCount) {
    this.totalCount = totalCount;
    setTotalCountIsSet(true);
    return this;
  }

  public void unsetTotalCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  /** Returns true if field totalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetTotalCount() {
    return EncodingUtils.testBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID);
  }

  public void setTotalCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTALCOUNT_ISSET_ID, value);
  }

  public int getGraphsSize() {
    return (this.graphs == null) ? 0 : this.graphs.size();
  }

  public java.util.Iterator<xueqiao.trade.hosting.HostingComposeGraph> getGraphsIterator() {
    return (this.graphs == null) ? null : this.graphs.iterator();
  }

  public void addToGraphs(xueqiao.trade.hosting.HostingComposeGraph elem) {
    if (this.graphs == null) {
      this.graphs = new ArrayList<xueqiao.trade.hosting.HostingComposeGraph>();
    }
    this.graphs.add(elem);
  }

  public List<xueqiao.trade.hosting.HostingComposeGraph> getGraphs() {
    return this.graphs;
  }

  public QuerySameComposeGraphsPage setGraphs(List<xueqiao.trade.hosting.HostingComposeGraph> graphs) {
    this.graphs = graphs;
    return this;
  }

  public void unsetGraphs() {
    this.graphs = null;
  }

  /** Returns true if field graphs is set (has been assigned a value) and false otherwise */
  public boolean isSetGraphs() {
    return this.graphs != null;
  }

  public void setGraphsIsSet(boolean value) {
    if (!value) {
      this.graphs = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TOTAL_COUNT:
      if (value == null) {
        unsetTotalCount();
      } else {
        setTotalCount((Integer)value);
      }
      break;

    case GRAPHS:
      if (value == null) {
        unsetGraphs();
      } else {
        setGraphs((List<xueqiao.trade.hosting.HostingComposeGraph>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TOTAL_COUNT:
      return Integer.valueOf(getTotalCount());

    case GRAPHS:
      return getGraphs();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TOTAL_COUNT:
      return isSetTotalCount();
    case GRAPHS:
      return isSetGraphs();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof QuerySameComposeGraphsPage)
      return this.equals((QuerySameComposeGraphsPage)that);
    return false;
  }

  public boolean equals(QuerySameComposeGraphsPage that) {
    if (that == null)
      return false;

    boolean this_present_totalCount = true && this.isSetTotalCount();
    boolean that_present_totalCount = true && that.isSetTotalCount();
    if (this_present_totalCount || that_present_totalCount) {
      if (!(this_present_totalCount && that_present_totalCount))
        return false;
      if (this.totalCount != that.totalCount)
        return false;
    }

    boolean this_present_graphs = true && this.isSetGraphs();
    boolean that_present_graphs = true && that.isSetGraphs();
    if (this_present_graphs || that_present_graphs) {
      if (!(this_present_graphs && that_present_graphs))
        return false;
      if (!this.graphs.equals(that.graphs))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(QuerySameComposeGraphsPage other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTotalCount()).compareTo(other.isSetTotalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTotalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.totalCount, other.totalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGraphs()).compareTo(other.isSetGraphs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGraphs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.graphs, other.graphs);
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
    StringBuilder sb = new StringBuilder("QuerySameComposeGraphsPage(");
    boolean first = true;

    if (isSetTotalCount()) {
      sb.append("totalCount:");
      sb.append(this.totalCount);
      first = false;
    }
    if (isSetGraphs()) {
      if (!first) sb.append(", ");
      sb.append("graphs:");
      if (this.graphs == null) {
        sb.append("null");
      } else {
        sb.append(this.graphs);
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

  private static class QuerySameComposeGraphsPageStandardSchemeFactory implements SchemeFactory {
    public QuerySameComposeGraphsPageStandardScheme getScheme() {
      return new QuerySameComposeGraphsPageStandardScheme();
    }
  }

  private static class QuerySameComposeGraphsPageStandardScheme extends StandardScheme<QuerySameComposeGraphsPage> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, QuerySameComposeGraphsPage struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TOTAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.totalCount = iprot.readI32();
              struct.setTotalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GRAPHS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.graphs = new ArrayList<xueqiao.trade.hosting.HostingComposeGraph>(_list16.size);
                for (int _i17 = 0; _i17 < _list16.size; ++_i17)
                {
                  xueqiao.trade.hosting.HostingComposeGraph _elem18;
                  _elem18 = new xueqiao.trade.hosting.HostingComposeGraph();
                  _elem18.read(iprot);
                  struct.graphs.add(_elem18);
                }
                iprot.readListEnd();
              }
              struct.setGraphsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, QuerySameComposeGraphsPage struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetTotalCount()) {
        oprot.writeFieldBegin(TOTAL_COUNT_FIELD_DESC);
        oprot.writeI32(struct.totalCount);
        oprot.writeFieldEnd();
      }
      if (struct.graphs != null) {
        if (struct.isSetGraphs()) {
          oprot.writeFieldBegin(GRAPHS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.graphs.size()));
            for (xueqiao.trade.hosting.HostingComposeGraph _iter19 : struct.graphs)
            {
              _iter19.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class QuerySameComposeGraphsPageTupleSchemeFactory implements SchemeFactory {
    public QuerySameComposeGraphsPageTupleScheme getScheme() {
      return new QuerySameComposeGraphsPageTupleScheme();
    }
  }

  private static class QuerySameComposeGraphsPageTupleScheme extends TupleScheme<QuerySameComposeGraphsPage> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, QuerySameComposeGraphsPage struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetTotalCount()) {
        optionals.set(0);
      }
      if (struct.isSetGraphs()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetTotalCount()) {
        oprot.writeI32(struct.totalCount);
      }
      if (struct.isSetGraphs()) {
        {
          oprot.writeI32(struct.graphs.size());
          for (xueqiao.trade.hosting.HostingComposeGraph _iter20 : struct.graphs)
          {
            _iter20.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, QuerySameComposeGraphsPage struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.totalCount = iprot.readI32();
        struct.setTotalCountIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.graphs = new ArrayList<xueqiao.trade.hosting.HostingComposeGraph>(_list21.size);
          for (int _i22 = 0; _i22 < _list21.size; ++_i22)
          {
            xueqiao.trade.hosting.HostingComposeGraph _elem23;
            _elem23 = new xueqiao.trade.hosting.HostingComposeGraph();
            _elem23.read(iprot);
            struct.graphs.add(_elem23);
          }
        }
        struct.setGraphsIsSet(true);
      }
    }
  }

}

