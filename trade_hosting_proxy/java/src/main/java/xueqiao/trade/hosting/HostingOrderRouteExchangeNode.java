/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting;

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

public class HostingOrderRouteExchangeNode implements org.apache.thrift.TBase<HostingOrderRouteExchangeNode, HostingOrderRouteExchangeNode._Fields>, java.io.Serializable, Cloneable, Comparable<HostingOrderRouteExchangeNode> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingOrderRouteExchangeNode");

  private static final org.apache.thrift.protocol.TField SLED_EXCHANGE_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("sledExchangeCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField SUB_COMMODITY_TYPE_NODES_FIELD_DESC = new org.apache.thrift.protocol.TField("subCommodityTypeNodes", org.apache.thrift.protocol.TType.MAP, (short)2);
  private static final org.apache.thrift.protocol.TField RELATED_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("relatedInfo", org.apache.thrift.protocol.TType.STRUCT, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingOrderRouteExchangeNodeStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingOrderRouteExchangeNodeTupleSchemeFactory());
  }

  public String sledExchangeCode; // optional
  public Map<Short,HostingOrderRouteCommodityTypeNode> subCommodityTypeNodes; // optional
  public HostingOrderRouteRelatedInfo relatedInfo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SLED_EXCHANGE_CODE((short)1, "sledExchangeCode"),
    SUB_COMMODITY_TYPE_NODES((short)2, "subCommodityTypeNodes"),
    RELATED_INFO((short)3, "relatedInfo");

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
        case 1: // SLED_EXCHANGE_CODE
          return SLED_EXCHANGE_CODE;
        case 2: // SUB_COMMODITY_TYPE_NODES
          return SUB_COMMODITY_TYPE_NODES;
        case 3: // RELATED_INFO
          return RELATED_INFO;
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
  private _Fields optionals[] = {_Fields.SLED_EXCHANGE_CODE,_Fields.SUB_COMMODITY_TYPE_NODES,_Fields.RELATED_INFO};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SLED_EXCHANGE_CODE, new org.apache.thrift.meta_data.FieldMetaData("sledExchangeCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUB_COMMODITY_TYPE_NODES, new org.apache.thrift.meta_data.FieldMetaData("subCommodityTypeNodes", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingOrderRouteCommodityTypeNode.class))));
    tmpMap.put(_Fields.RELATED_INFO, new org.apache.thrift.meta_data.FieldMetaData("relatedInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingOrderRouteRelatedInfo.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingOrderRouteExchangeNode.class, metaDataMap);
  }

  public HostingOrderRouteExchangeNode() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingOrderRouteExchangeNode(HostingOrderRouteExchangeNode other) {
    if (other.isSetSledExchangeCode()) {
      this.sledExchangeCode = other.sledExchangeCode;
    }
    if (other.isSetSubCommodityTypeNodes()) {
      Map<Short,HostingOrderRouteCommodityTypeNode> __this__subCommodityTypeNodes = new HashMap<Short,HostingOrderRouteCommodityTypeNode>(other.subCommodityTypeNodes.size());
      for (Map.Entry<Short, HostingOrderRouteCommodityTypeNode> other_element : other.subCommodityTypeNodes.entrySet()) {

        Short other_element_key = other_element.getKey();
        HostingOrderRouteCommodityTypeNode other_element_value = other_element.getValue();

        Short __this__subCommodityTypeNodes_copy_key = other_element_key;

        HostingOrderRouteCommodityTypeNode __this__subCommodityTypeNodes_copy_value = new HostingOrderRouteCommodityTypeNode(other_element_value);

        __this__subCommodityTypeNodes.put(__this__subCommodityTypeNodes_copy_key, __this__subCommodityTypeNodes_copy_value);
      }
      this.subCommodityTypeNodes = __this__subCommodityTypeNodes;
    }
    if (other.isSetRelatedInfo()) {
      this.relatedInfo = new HostingOrderRouteRelatedInfo(other.relatedInfo);
    }
  }

  public HostingOrderRouteExchangeNode deepCopy() {
    return new HostingOrderRouteExchangeNode(this);
  }

  @Override
  public void clear() {
    this.sledExchangeCode = null;
    this.subCommodityTypeNodes = null;
    this.relatedInfo = null;
  }

  public String getSledExchangeCode() {
    return this.sledExchangeCode;
  }

  public HostingOrderRouteExchangeNode setSledExchangeCode(String sledExchangeCode) {
    this.sledExchangeCode = sledExchangeCode;
    return this;
  }

  public void unsetSledExchangeCode() {
    this.sledExchangeCode = null;
  }

  /** Returns true if field sledExchangeCode is set (has been assigned a value) and false otherwise */
  public boolean isSetSledExchangeCode() {
    return this.sledExchangeCode != null;
  }

  public void setSledExchangeCodeIsSet(boolean value) {
    if (!value) {
      this.sledExchangeCode = null;
    }
  }

  public int getSubCommodityTypeNodesSize() {
    return (this.subCommodityTypeNodes == null) ? 0 : this.subCommodityTypeNodes.size();
  }

  public void putToSubCommodityTypeNodes(short key, HostingOrderRouteCommodityTypeNode val) {
    if (this.subCommodityTypeNodes == null) {
      this.subCommodityTypeNodes = new HashMap<Short,HostingOrderRouteCommodityTypeNode>();
    }
    this.subCommodityTypeNodes.put(key, val);
  }

  public Map<Short,HostingOrderRouteCommodityTypeNode> getSubCommodityTypeNodes() {
    return this.subCommodityTypeNodes;
  }

  public HostingOrderRouteExchangeNode setSubCommodityTypeNodes(Map<Short,HostingOrderRouteCommodityTypeNode> subCommodityTypeNodes) {
    this.subCommodityTypeNodes = subCommodityTypeNodes;
    return this;
  }

  public void unsetSubCommodityTypeNodes() {
    this.subCommodityTypeNodes = null;
  }

  /** Returns true if field subCommodityTypeNodes is set (has been assigned a value) and false otherwise */
  public boolean isSetSubCommodityTypeNodes() {
    return this.subCommodityTypeNodes != null;
  }

  public void setSubCommodityTypeNodesIsSet(boolean value) {
    if (!value) {
      this.subCommodityTypeNodes = null;
    }
  }

  public HostingOrderRouteRelatedInfo getRelatedInfo() {
    return this.relatedInfo;
  }

  public HostingOrderRouteExchangeNode setRelatedInfo(HostingOrderRouteRelatedInfo relatedInfo) {
    this.relatedInfo = relatedInfo;
    return this;
  }

  public void unsetRelatedInfo() {
    this.relatedInfo = null;
  }

  /** Returns true if field relatedInfo is set (has been assigned a value) and false otherwise */
  public boolean isSetRelatedInfo() {
    return this.relatedInfo != null;
  }

  public void setRelatedInfoIsSet(boolean value) {
    if (!value) {
      this.relatedInfo = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SLED_EXCHANGE_CODE:
      if (value == null) {
        unsetSledExchangeCode();
      } else {
        setSledExchangeCode((String)value);
      }
      break;

    case SUB_COMMODITY_TYPE_NODES:
      if (value == null) {
        unsetSubCommodityTypeNodes();
      } else {
        setSubCommodityTypeNodes((Map<Short,HostingOrderRouteCommodityTypeNode>)value);
      }
      break;

    case RELATED_INFO:
      if (value == null) {
        unsetRelatedInfo();
      } else {
        setRelatedInfo((HostingOrderRouteRelatedInfo)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SLED_EXCHANGE_CODE:
      return getSledExchangeCode();

    case SUB_COMMODITY_TYPE_NODES:
      return getSubCommodityTypeNodes();

    case RELATED_INFO:
      return getRelatedInfo();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SLED_EXCHANGE_CODE:
      return isSetSledExchangeCode();
    case SUB_COMMODITY_TYPE_NODES:
      return isSetSubCommodityTypeNodes();
    case RELATED_INFO:
      return isSetRelatedInfo();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingOrderRouteExchangeNode)
      return this.equals((HostingOrderRouteExchangeNode)that);
    return false;
  }

  public boolean equals(HostingOrderRouteExchangeNode that) {
    if (that == null)
      return false;

    boolean this_present_sledExchangeCode = true && this.isSetSledExchangeCode();
    boolean that_present_sledExchangeCode = true && that.isSetSledExchangeCode();
    if (this_present_sledExchangeCode || that_present_sledExchangeCode) {
      if (!(this_present_sledExchangeCode && that_present_sledExchangeCode))
        return false;
      if (!this.sledExchangeCode.equals(that.sledExchangeCode))
        return false;
    }

    boolean this_present_subCommodityTypeNodes = true && this.isSetSubCommodityTypeNodes();
    boolean that_present_subCommodityTypeNodes = true && that.isSetSubCommodityTypeNodes();
    if (this_present_subCommodityTypeNodes || that_present_subCommodityTypeNodes) {
      if (!(this_present_subCommodityTypeNodes && that_present_subCommodityTypeNodes))
        return false;
      if (!this.subCommodityTypeNodes.equals(that.subCommodityTypeNodes))
        return false;
    }

    boolean this_present_relatedInfo = true && this.isSetRelatedInfo();
    boolean that_present_relatedInfo = true && that.isSetRelatedInfo();
    if (this_present_relatedInfo || that_present_relatedInfo) {
      if (!(this_present_relatedInfo && that_present_relatedInfo))
        return false;
      if (!this.relatedInfo.equals(that.relatedInfo))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingOrderRouteExchangeNode other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSledExchangeCode()).compareTo(other.isSetSledExchangeCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSledExchangeCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sledExchangeCode, other.sledExchangeCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubCommodityTypeNodes()).compareTo(other.isSetSubCommodityTypeNodes());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubCommodityTypeNodes()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subCommodityTypeNodes, other.subCommodityTypeNodes);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRelatedInfo()).compareTo(other.isSetRelatedInfo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRelatedInfo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.relatedInfo, other.relatedInfo);
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
    StringBuilder sb = new StringBuilder("HostingOrderRouteExchangeNode(");
    boolean first = true;

    if (isSetSledExchangeCode()) {
      sb.append("sledExchangeCode:");
      if (this.sledExchangeCode == null) {
        sb.append("null");
      } else {
        sb.append(this.sledExchangeCode);
      }
      first = false;
    }
    if (isSetSubCommodityTypeNodes()) {
      if (!first) sb.append(", ");
      sb.append("subCommodityTypeNodes:");
      if (this.subCommodityTypeNodes == null) {
        sb.append("null");
      } else {
        sb.append(this.subCommodityTypeNodes);
      }
      first = false;
    }
    if (isSetRelatedInfo()) {
      if (!first) sb.append(", ");
      sb.append("relatedInfo:");
      if (this.relatedInfo == null) {
        sb.append("null");
      } else {
        sb.append(this.relatedInfo);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (relatedInfo != null) {
      relatedInfo.validate();
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class HostingOrderRouteExchangeNodeStandardSchemeFactory implements SchemeFactory {
    public HostingOrderRouteExchangeNodeStandardScheme getScheme() {
      return new HostingOrderRouteExchangeNodeStandardScheme();
    }
  }

  private static class HostingOrderRouteExchangeNodeStandardScheme extends StandardScheme<HostingOrderRouteExchangeNode> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingOrderRouteExchangeNode struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SLED_EXCHANGE_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.sledExchangeCode = iprot.readString();
              struct.setSledExchangeCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SUB_COMMODITY_TYPE_NODES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map76 = iprot.readMapBegin();
                struct.subCommodityTypeNodes = new HashMap<Short,HostingOrderRouteCommodityTypeNode>(2*_map76.size);
                for (int _i77 = 0; _i77 < _map76.size; ++_i77)
                {
                  short _key78;
                  HostingOrderRouteCommodityTypeNode _val79;
                  _key78 = iprot.readI16();
                  _val79 = new HostingOrderRouteCommodityTypeNode();
                  _val79.read(iprot);
                  struct.subCommodityTypeNodes.put(_key78, _val79);
                }
                iprot.readMapEnd();
              }
              struct.setSubCommodityTypeNodesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RELATED_INFO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.relatedInfo = new HostingOrderRouteRelatedInfo();
              struct.relatedInfo.read(iprot);
              struct.setRelatedInfoIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingOrderRouteExchangeNode struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.sledExchangeCode != null) {
        if (struct.isSetSledExchangeCode()) {
          oprot.writeFieldBegin(SLED_EXCHANGE_CODE_FIELD_DESC);
          oprot.writeString(struct.sledExchangeCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.subCommodityTypeNodes != null) {
        if (struct.isSetSubCommodityTypeNodes()) {
          oprot.writeFieldBegin(SUB_COMMODITY_TYPE_NODES_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I16, org.apache.thrift.protocol.TType.STRUCT, struct.subCommodityTypeNodes.size()));
            for (Map.Entry<Short, HostingOrderRouteCommodityTypeNode> _iter80 : struct.subCommodityTypeNodes.entrySet())
            {
              oprot.writeI16(_iter80.getKey());
              _iter80.getValue().write(oprot);
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.relatedInfo != null) {
        if (struct.isSetRelatedInfo()) {
          oprot.writeFieldBegin(RELATED_INFO_FIELD_DESC);
          struct.relatedInfo.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingOrderRouteExchangeNodeTupleSchemeFactory implements SchemeFactory {
    public HostingOrderRouteExchangeNodeTupleScheme getScheme() {
      return new HostingOrderRouteExchangeNodeTupleScheme();
    }
  }

  private static class HostingOrderRouteExchangeNodeTupleScheme extends TupleScheme<HostingOrderRouteExchangeNode> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingOrderRouteExchangeNode struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetSledExchangeCode()) {
        optionals.set(0);
      }
      if (struct.isSetSubCommodityTypeNodes()) {
        optionals.set(1);
      }
      if (struct.isSetRelatedInfo()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetSledExchangeCode()) {
        oprot.writeString(struct.sledExchangeCode);
      }
      if (struct.isSetSubCommodityTypeNodes()) {
        {
          oprot.writeI32(struct.subCommodityTypeNodes.size());
          for (Map.Entry<Short, HostingOrderRouteCommodityTypeNode> _iter81 : struct.subCommodityTypeNodes.entrySet())
          {
            oprot.writeI16(_iter81.getKey());
            _iter81.getValue().write(oprot);
          }
        }
      }
      if (struct.isSetRelatedInfo()) {
        struct.relatedInfo.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingOrderRouteExchangeNode struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.sledExchangeCode = iprot.readString();
        struct.setSledExchangeCodeIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TMap _map82 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I16, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.subCommodityTypeNodes = new HashMap<Short,HostingOrderRouteCommodityTypeNode>(2*_map82.size);
          for (int _i83 = 0; _i83 < _map82.size; ++_i83)
          {
            short _key84;
            HostingOrderRouteCommodityTypeNode _val85;
            _key84 = iprot.readI16();
            _val85 = new HostingOrderRouteCommodityTypeNode();
            _val85.read(iprot);
            struct.subCommodityTypeNodes.put(_key84, _val85);
          }
        }
        struct.setSubCommodityTypeNodesIsSet(true);
      }
      if (incoming.get(2)) {
        struct.relatedInfo = new HostingOrderRouteRelatedInfo();
        struct.relatedInfo.read(iprot);
        struct.setRelatedInfoIsSet(true);
      }
    }
  }

}

