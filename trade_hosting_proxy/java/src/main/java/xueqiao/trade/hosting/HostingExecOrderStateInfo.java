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

public class HostingExecOrderStateInfo implements org.apache.thrift.TBase<HostingExecOrderStateInfo, HostingExecOrderStateInfo._Fields>, java.io.Serializable, Cloneable, Comparable<HostingExecOrderStateInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("HostingExecOrderStateInfo");

  private static final org.apache.thrift.protocol.TField CURRENT_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("currentState", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField HISTORY_STATES_FIELD_DESC = new org.apache.thrift.protocol.TField("historyStates", org.apache.thrift.protocol.TType.LIST, (short)2);
  private static final org.apache.thrift.protocol.TField STATUS_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("statusMsg", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField FAILED_ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("failedErrorCode", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField UPSIDE_ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("upsideErrorCode", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField UPSIDE_USEFUL_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("upsideUsefulMsg", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new HostingExecOrderStateInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new HostingExecOrderStateInfoTupleSchemeFactory());
  }

  public HostingExecOrderState currentState; // optional
  public List<HostingExecOrderState> historyStates; // optional
  public String statusMsg; // optional
  public int failedErrorCode; // optional
  public int upsideErrorCode; // optional
  public String upsideUsefulMsg; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    CURRENT_STATE((short)1, "currentState"),
    HISTORY_STATES((short)2, "historyStates"),
    STATUS_MSG((short)3, "statusMsg"),
    FAILED_ERROR_CODE((short)4, "failedErrorCode"),
    UPSIDE_ERROR_CODE((short)5, "upsideErrorCode"),
    UPSIDE_USEFUL_MSG((short)6, "upsideUsefulMsg");

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
        case 1: // CURRENT_STATE
          return CURRENT_STATE;
        case 2: // HISTORY_STATES
          return HISTORY_STATES;
        case 3: // STATUS_MSG
          return STATUS_MSG;
        case 4: // FAILED_ERROR_CODE
          return FAILED_ERROR_CODE;
        case 5: // UPSIDE_ERROR_CODE
          return UPSIDE_ERROR_CODE;
        case 6: // UPSIDE_USEFUL_MSG
          return UPSIDE_USEFUL_MSG;
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
  private static final int __FAILEDERRORCODE_ISSET_ID = 0;
  private static final int __UPSIDEERRORCODE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.CURRENT_STATE,_Fields.HISTORY_STATES,_Fields.STATUS_MSG,_Fields.FAILED_ERROR_CODE,_Fields.UPSIDE_ERROR_CODE,_Fields.UPSIDE_USEFUL_MSG};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.CURRENT_STATE, new org.apache.thrift.meta_data.FieldMetaData("currentState", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingExecOrderState.class)));
    tmpMap.put(_Fields.HISTORY_STATES, new org.apache.thrift.meta_data.FieldMetaData("historyStates", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, HostingExecOrderState.class))));
    tmpMap.put(_Fields.STATUS_MSG, new org.apache.thrift.meta_data.FieldMetaData("statusMsg", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.FAILED_ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("failedErrorCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.UPSIDE_ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("upsideErrorCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.UPSIDE_USEFUL_MSG, new org.apache.thrift.meta_data.FieldMetaData("upsideUsefulMsg", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(HostingExecOrderStateInfo.class, metaDataMap);
  }

  public HostingExecOrderStateInfo() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public HostingExecOrderStateInfo(HostingExecOrderStateInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetCurrentState()) {
      this.currentState = new HostingExecOrderState(other.currentState);
    }
    if (other.isSetHistoryStates()) {
      List<HostingExecOrderState> __this__historyStates = new ArrayList<HostingExecOrderState>(other.historyStates.size());
      for (HostingExecOrderState other_element : other.historyStates) {
        __this__historyStates.add(new HostingExecOrderState(other_element));
      }
      this.historyStates = __this__historyStates;
    }
    if (other.isSetStatusMsg()) {
      this.statusMsg = other.statusMsg;
    }
    this.failedErrorCode = other.failedErrorCode;
    this.upsideErrorCode = other.upsideErrorCode;
    if (other.isSetUpsideUsefulMsg()) {
      this.upsideUsefulMsg = other.upsideUsefulMsg;
    }
  }

  public HostingExecOrderStateInfo deepCopy() {
    return new HostingExecOrderStateInfo(this);
  }

  @Override
  public void clear() {
    this.currentState = null;
    this.historyStates = null;
    this.statusMsg = null;
    setFailedErrorCodeIsSet(false);
    this.failedErrorCode = 0;
    setUpsideErrorCodeIsSet(false);
    this.upsideErrorCode = 0;
    this.upsideUsefulMsg = null;
  }

  public HostingExecOrderState getCurrentState() {
    return this.currentState;
  }

  public HostingExecOrderStateInfo setCurrentState(HostingExecOrderState currentState) {
    this.currentState = currentState;
    return this;
  }

  public void unsetCurrentState() {
    this.currentState = null;
  }

  /** Returns true if field currentState is set (has been assigned a value) and false otherwise */
  public boolean isSetCurrentState() {
    return this.currentState != null;
  }

  public void setCurrentStateIsSet(boolean value) {
    if (!value) {
      this.currentState = null;
    }
  }

  public int getHistoryStatesSize() {
    return (this.historyStates == null) ? 0 : this.historyStates.size();
  }

  public java.util.Iterator<HostingExecOrderState> getHistoryStatesIterator() {
    return (this.historyStates == null) ? null : this.historyStates.iterator();
  }

  public void addToHistoryStates(HostingExecOrderState elem) {
    if (this.historyStates == null) {
      this.historyStates = new ArrayList<HostingExecOrderState>();
    }
    this.historyStates.add(elem);
  }

  public List<HostingExecOrderState> getHistoryStates() {
    return this.historyStates;
  }

  public HostingExecOrderStateInfo setHistoryStates(List<HostingExecOrderState> historyStates) {
    this.historyStates = historyStates;
    return this;
  }

  public void unsetHistoryStates() {
    this.historyStates = null;
  }

  /** Returns true if field historyStates is set (has been assigned a value) and false otherwise */
  public boolean isSetHistoryStates() {
    return this.historyStates != null;
  }

  public void setHistoryStatesIsSet(boolean value) {
    if (!value) {
      this.historyStates = null;
    }
  }

  public String getStatusMsg() {
    return this.statusMsg;
  }

  public HostingExecOrderStateInfo setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
    return this;
  }

  public void unsetStatusMsg() {
    this.statusMsg = null;
  }

  /** Returns true if field statusMsg is set (has been assigned a value) and false otherwise */
  public boolean isSetStatusMsg() {
    return this.statusMsg != null;
  }

  public void setStatusMsgIsSet(boolean value) {
    if (!value) {
      this.statusMsg = null;
    }
  }

  public int getFailedErrorCode() {
    return this.failedErrorCode;
  }

  public HostingExecOrderStateInfo setFailedErrorCode(int failedErrorCode) {
    this.failedErrorCode = failedErrorCode;
    setFailedErrorCodeIsSet(true);
    return this;
  }

  public void unsetFailedErrorCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FAILEDERRORCODE_ISSET_ID);
  }

  /** Returns true if field failedErrorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetFailedErrorCode() {
    return EncodingUtils.testBit(__isset_bitfield, __FAILEDERRORCODE_ISSET_ID);
  }

  public void setFailedErrorCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FAILEDERRORCODE_ISSET_ID, value);
  }

  public int getUpsideErrorCode() {
    return this.upsideErrorCode;
  }

  public HostingExecOrderStateInfo setUpsideErrorCode(int upsideErrorCode) {
    this.upsideErrorCode = upsideErrorCode;
    setUpsideErrorCodeIsSet(true);
    return this;
  }

  public void unsetUpsideErrorCode() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __UPSIDEERRORCODE_ISSET_ID);
  }

  /** Returns true if field upsideErrorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetUpsideErrorCode() {
    return EncodingUtils.testBit(__isset_bitfield, __UPSIDEERRORCODE_ISSET_ID);
  }

  public void setUpsideErrorCodeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __UPSIDEERRORCODE_ISSET_ID, value);
  }

  public String getUpsideUsefulMsg() {
    return this.upsideUsefulMsg;
  }

  public HostingExecOrderStateInfo setUpsideUsefulMsg(String upsideUsefulMsg) {
    this.upsideUsefulMsg = upsideUsefulMsg;
    return this;
  }

  public void unsetUpsideUsefulMsg() {
    this.upsideUsefulMsg = null;
  }

  /** Returns true if field upsideUsefulMsg is set (has been assigned a value) and false otherwise */
  public boolean isSetUpsideUsefulMsg() {
    return this.upsideUsefulMsg != null;
  }

  public void setUpsideUsefulMsgIsSet(boolean value) {
    if (!value) {
      this.upsideUsefulMsg = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case CURRENT_STATE:
      if (value == null) {
        unsetCurrentState();
      } else {
        setCurrentState((HostingExecOrderState)value);
      }
      break;

    case HISTORY_STATES:
      if (value == null) {
        unsetHistoryStates();
      } else {
        setHistoryStates((List<HostingExecOrderState>)value);
      }
      break;

    case STATUS_MSG:
      if (value == null) {
        unsetStatusMsg();
      } else {
        setStatusMsg((String)value);
      }
      break;

    case FAILED_ERROR_CODE:
      if (value == null) {
        unsetFailedErrorCode();
      } else {
        setFailedErrorCode((Integer)value);
      }
      break;

    case UPSIDE_ERROR_CODE:
      if (value == null) {
        unsetUpsideErrorCode();
      } else {
        setUpsideErrorCode((Integer)value);
      }
      break;

    case UPSIDE_USEFUL_MSG:
      if (value == null) {
        unsetUpsideUsefulMsg();
      } else {
        setUpsideUsefulMsg((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case CURRENT_STATE:
      return getCurrentState();

    case HISTORY_STATES:
      return getHistoryStates();

    case STATUS_MSG:
      return getStatusMsg();

    case FAILED_ERROR_CODE:
      return Integer.valueOf(getFailedErrorCode());

    case UPSIDE_ERROR_CODE:
      return Integer.valueOf(getUpsideErrorCode());

    case UPSIDE_USEFUL_MSG:
      return getUpsideUsefulMsg();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case CURRENT_STATE:
      return isSetCurrentState();
    case HISTORY_STATES:
      return isSetHistoryStates();
    case STATUS_MSG:
      return isSetStatusMsg();
    case FAILED_ERROR_CODE:
      return isSetFailedErrorCode();
    case UPSIDE_ERROR_CODE:
      return isSetUpsideErrorCode();
    case UPSIDE_USEFUL_MSG:
      return isSetUpsideUsefulMsg();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof HostingExecOrderStateInfo)
      return this.equals((HostingExecOrderStateInfo)that);
    return false;
  }

  public boolean equals(HostingExecOrderStateInfo that) {
    if (that == null)
      return false;

    boolean this_present_currentState = true && this.isSetCurrentState();
    boolean that_present_currentState = true && that.isSetCurrentState();
    if (this_present_currentState || that_present_currentState) {
      if (!(this_present_currentState && that_present_currentState))
        return false;
      if (!this.currentState.equals(that.currentState))
        return false;
    }

    boolean this_present_historyStates = true && this.isSetHistoryStates();
    boolean that_present_historyStates = true && that.isSetHistoryStates();
    if (this_present_historyStates || that_present_historyStates) {
      if (!(this_present_historyStates && that_present_historyStates))
        return false;
      if (!this.historyStates.equals(that.historyStates))
        return false;
    }

    boolean this_present_statusMsg = true && this.isSetStatusMsg();
    boolean that_present_statusMsg = true && that.isSetStatusMsg();
    if (this_present_statusMsg || that_present_statusMsg) {
      if (!(this_present_statusMsg && that_present_statusMsg))
        return false;
      if (!this.statusMsg.equals(that.statusMsg))
        return false;
    }

    boolean this_present_failedErrorCode = true && this.isSetFailedErrorCode();
    boolean that_present_failedErrorCode = true && that.isSetFailedErrorCode();
    if (this_present_failedErrorCode || that_present_failedErrorCode) {
      if (!(this_present_failedErrorCode && that_present_failedErrorCode))
        return false;
      if (this.failedErrorCode != that.failedErrorCode)
        return false;
    }

    boolean this_present_upsideErrorCode = true && this.isSetUpsideErrorCode();
    boolean that_present_upsideErrorCode = true && that.isSetUpsideErrorCode();
    if (this_present_upsideErrorCode || that_present_upsideErrorCode) {
      if (!(this_present_upsideErrorCode && that_present_upsideErrorCode))
        return false;
      if (this.upsideErrorCode != that.upsideErrorCode)
        return false;
    }

    boolean this_present_upsideUsefulMsg = true && this.isSetUpsideUsefulMsg();
    boolean that_present_upsideUsefulMsg = true && that.isSetUpsideUsefulMsg();
    if (this_present_upsideUsefulMsg || that_present_upsideUsefulMsg) {
      if (!(this_present_upsideUsefulMsg && that_present_upsideUsefulMsg))
        return false;
      if (!this.upsideUsefulMsg.equals(that.upsideUsefulMsg))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(HostingExecOrderStateInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCurrentState()).compareTo(other.isSetCurrentState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCurrentState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.currentState, other.currentState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHistoryStates()).compareTo(other.isSetHistoryStates());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHistoryStates()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.historyStates, other.historyStates);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStatusMsg()).compareTo(other.isSetStatusMsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStatusMsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.statusMsg, other.statusMsg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFailedErrorCode()).compareTo(other.isSetFailedErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFailedErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.failedErrorCode, other.failedErrorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpsideErrorCode()).compareTo(other.isSetUpsideErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpsideErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.upsideErrorCode, other.upsideErrorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUpsideUsefulMsg()).compareTo(other.isSetUpsideUsefulMsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUpsideUsefulMsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.upsideUsefulMsg, other.upsideUsefulMsg);
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
    StringBuilder sb = new StringBuilder("HostingExecOrderStateInfo(");
    boolean first = true;

    if (isSetCurrentState()) {
      sb.append("currentState:");
      if (this.currentState == null) {
        sb.append("null");
      } else {
        sb.append(this.currentState);
      }
      first = false;
    }
    if (isSetHistoryStates()) {
      if (!first) sb.append(", ");
      sb.append("historyStates:");
      if (this.historyStates == null) {
        sb.append("null");
      } else {
        sb.append(this.historyStates);
      }
      first = false;
    }
    if (isSetStatusMsg()) {
      if (!first) sb.append(", ");
      sb.append("statusMsg:");
      if (this.statusMsg == null) {
        sb.append("null");
      } else {
        sb.append(this.statusMsg);
      }
      first = false;
    }
    if (isSetFailedErrorCode()) {
      if (!first) sb.append(", ");
      sb.append("failedErrorCode:");
      sb.append(this.failedErrorCode);
      first = false;
    }
    if (isSetUpsideErrorCode()) {
      if (!first) sb.append(", ");
      sb.append("upsideErrorCode:");
      sb.append(this.upsideErrorCode);
      first = false;
    }
    if (isSetUpsideUsefulMsg()) {
      if (!first) sb.append(", ");
      sb.append("upsideUsefulMsg:");
      if (this.upsideUsefulMsg == null) {
        sb.append("null");
      } else {
        sb.append(this.upsideUsefulMsg);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (currentState != null) {
      currentState.validate();
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

  private static class HostingExecOrderStateInfoStandardSchemeFactory implements SchemeFactory {
    public HostingExecOrderStateInfoStandardScheme getScheme() {
      return new HostingExecOrderStateInfoStandardScheme();
    }
  }

  private static class HostingExecOrderStateInfoStandardScheme extends StandardScheme<HostingExecOrderStateInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, HostingExecOrderStateInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // CURRENT_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.currentState = new HostingExecOrderState();
              struct.currentState.read(iprot);
              struct.setCurrentStateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HISTORY_STATES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list132 = iprot.readListBegin();
                struct.historyStates = new ArrayList<HostingExecOrderState>(_list132.size);
                for (int _i133 = 0; _i133 < _list132.size; ++_i133)
                {
                  HostingExecOrderState _elem134;
                  _elem134 = new HostingExecOrderState();
                  _elem134.read(iprot);
                  struct.historyStates.add(_elem134);
                }
                iprot.readListEnd();
              }
              struct.setHistoryStatesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // STATUS_MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.statusMsg = iprot.readString();
              struct.setStatusMsgIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // FAILED_ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.failedErrorCode = iprot.readI32();
              struct.setFailedErrorCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // UPSIDE_ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.upsideErrorCode = iprot.readI32();
              struct.setUpsideErrorCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // UPSIDE_USEFUL_MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.upsideUsefulMsg = iprot.readString();
              struct.setUpsideUsefulMsgIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, HostingExecOrderStateInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.currentState != null) {
        if (struct.isSetCurrentState()) {
          oprot.writeFieldBegin(CURRENT_STATE_FIELD_DESC);
          struct.currentState.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.historyStates != null) {
        if (struct.isSetHistoryStates()) {
          oprot.writeFieldBegin(HISTORY_STATES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.historyStates.size()));
            for (HostingExecOrderState _iter135 : struct.historyStates)
            {
              _iter135.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.statusMsg != null) {
        if (struct.isSetStatusMsg()) {
          oprot.writeFieldBegin(STATUS_MSG_FIELD_DESC);
          oprot.writeString(struct.statusMsg);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetFailedErrorCode()) {
        oprot.writeFieldBegin(FAILED_ERROR_CODE_FIELD_DESC);
        oprot.writeI32(struct.failedErrorCode);
        oprot.writeFieldEnd();
      }
      if (struct.isSetUpsideErrorCode()) {
        oprot.writeFieldBegin(UPSIDE_ERROR_CODE_FIELD_DESC);
        oprot.writeI32(struct.upsideErrorCode);
        oprot.writeFieldEnd();
      }
      if (struct.upsideUsefulMsg != null) {
        if (struct.isSetUpsideUsefulMsg()) {
          oprot.writeFieldBegin(UPSIDE_USEFUL_MSG_FIELD_DESC);
          oprot.writeString(struct.upsideUsefulMsg);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class HostingExecOrderStateInfoTupleSchemeFactory implements SchemeFactory {
    public HostingExecOrderStateInfoTupleScheme getScheme() {
      return new HostingExecOrderStateInfoTupleScheme();
    }
  }

  private static class HostingExecOrderStateInfoTupleScheme extends TupleScheme<HostingExecOrderStateInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, HostingExecOrderStateInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCurrentState()) {
        optionals.set(0);
      }
      if (struct.isSetHistoryStates()) {
        optionals.set(1);
      }
      if (struct.isSetStatusMsg()) {
        optionals.set(2);
      }
      if (struct.isSetFailedErrorCode()) {
        optionals.set(3);
      }
      if (struct.isSetUpsideErrorCode()) {
        optionals.set(4);
      }
      if (struct.isSetUpsideUsefulMsg()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetCurrentState()) {
        struct.currentState.write(oprot);
      }
      if (struct.isSetHistoryStates()) {
        {
          oprot.writeI32(struct.historyStates.size());
          for (HostingExecOrderState _iter136 : struct.historyStates)
          {
            _iter136.write(oprot);
          }
        }
      }
      if (struct.isSetStatusMsg()) {
        oprot.writeString(struct.statusMsg);
      }
      if (struct.isSetFailedErrorCode()) {
        oprot.writeI32(struct.failedErrorCode);
      }
      if (struct.isSetUpsideErrorCode()) {
        oprot.writeI32(struct.upsideErrorCode);
      }
      if (struct.isSetUpsideUsefulMsg()) {
        oprot.writeString(struct.upsideUsefulMsg);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, HostingExecOrderStateInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.currentState = new HostingExecOrderState();
        struct.currentState.read(iprot);
        struct.setCurrentStateIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list137 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.historyStates = new ArrayList<HostingExecOrderState>(_list137.size);
          for (int _i138 = 0; _i138 < _list137.size; ++_i138)
          {
            HostingExecOrderState _elem139;
            _elem139 = new HostingExecOrderState();
            _elem139.read(iprot);
            struct.historyStates.add(_elem139);
          }
        }
        struct.setHistoryStatesIsSet(true);
      }
      if (incoming.get(2)) {
        struct.statusMsg = iprot.readString();
        struct.setStatusMsgIsSet(true);
      }
      if (incoming.get(3)) {
        struct.failedErrorCode = iprot.readI32();
        struct.setFailedErrorCodeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.upsideErrorCode = iprot.readI32();
        struct.setUpsideErrorCodeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.upsideUsefulMsg = iprot.readString();
        struct.setUpsideUsefulMsgIsSet(true);
      }
    }
  }

}

