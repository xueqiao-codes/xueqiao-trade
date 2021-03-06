/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.proxy;

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

public class ProxyLoginReq implements org.apache.thrift.TBase<ProxyLoginReq, ProxyLoginReq._Fields>, java.io.Serializable, Cloneable, Comparable<ProxyLoginReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProxyLoginReq");

  private static final org.apache.thrift.protocol.TField COMPANY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("companyCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField USER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("userName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PASSWORD_MD5_FIELD_DESC = new org.apache.thrift.protocol.TField("passwordMd5", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField COMPANY_GROUP_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("companyGroupCode", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField VERIFY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("verifyCode", org.apache.thrift.protocol.TType.STRING, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProxyLoginReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProxyLoginReqTupleSchemeFactory());
  }

  public String companyCode; // optional
  public String userName; // optional
  public String passwordMd5; // optional
  public String companyGroupCode; // optional
  public String verifyCode; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMPANY_CODE((short)1, "companyCode"),
    USER_NAME((short)2, "userName"),
    PASSWORD_MD5((short)3, "passwordMd5"),
    COMPANY_GROUP_CODE((short)4, "companyGroupCode"),
    VERIFY_CODE((short)5, "verifyCode");

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
        case 1: // COMPANY_CODE
          return COMPANY_CODE;
        case 2: // USER_NAME
          return USER_NAME;
        case 3: // PASSWORD_MD5
          return PASSWORD_MD5;
        case 4: // COMPANY_GROUP_CODE
          return COMPANY_GROUP_CODE;
        case 5: // VERIFY_CODE
          return VERIFY_CODE;
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
  private _Fields optionals[] = {_Fields.COMPANY_CODE,_Fields.USER_NAME,_Fields.PASSWORD_MD5,_Fields.COMPANY_GROUP_CODE,_Fields.VERIFY_CODE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMPANY_CODE, new org.apache.thrift.meta_data.FieldMetaData("companyCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_NAME, new org.apache.thrift.meta_data.FieldMetaData("userName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PASSWORD_MD5, new org.apache.thrift.meta_data.FieldMetaData("passwordMd5", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.COMPANY_GROUP_CODE, new org.apache.thrift.meta_data.FieldMetaData("companyGroupCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.VERIFY_CODE, new org.apache.thrift.meta_data.FieldMetaData("verifyCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProxyLoginReq.class, metaDataMap);
  }

  public ProxyLoginReq() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProxyLoginReq(ProxyLoginReq other) {
    if (other.isSetCompanyCode()) {
      this.companyCode = other.companyCode;
    }
    if (other.isSetUserName()) {
      this.userName = other.userName;
    }
    if (other.isSetPasswordMd5()) {
      this.passwordMd5 = other.passwordMd5;
    }
    if (other.isSetCompanyGroupCode()) {
      this.companyGroupCode = other.companyGroupCode;
    }
    if (other.isSetVerifyCode()) {
      this.verifyCode = other.verifyCode;
    }
  }

  public ProxyLoginReq deepCopy() {
    return new ProxyLoginReq(this);
  }

  @Override
  public void clear() {
    this.companyCode = null;
    this.userName = null;
    this.passwordMd5 = null;
    this.companyGroupCode = null;
    this.verifyCode = null;
  }

  public String getCompanyCode() {
    return this.companyCode;
  }

  public ProxyLoginReq setCompanyCode(String companyCode) {
    this.companyCode = companyCode;
    return this;
  }

  public void unsetCompanyCode() {
    this.companyCode = null;
  }

  /** Returns true if field companyCode is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyCode() {
    return this.companyCode != null;
  }

  public void setCompanyCodeIsSet(boolean value) {
    if (!value) {
      this.companyCode = null;
    }
  }

  public String getUserName() {
    return this.userName;
  }

  public ProxyLoginReq setUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public void unsetUserName() {
    this.userName = null;
  }

  /** Returns true if field userName is set (has been assigned a value) and false otherwise */
  public boolean isSetUserName() {
    return this.userName != null;
  }

  public void setUserNameIsSet(boolean value) {
    if (!value) {
      this.userName = null;
    }
  }

  public String getPasswordMd5() {
    return this.passwordMd5;
  }

  public ProxyLoginReq setPasswordMd5(String passwordMd5) {
    this.passwordMd5 = passwordMd5;
    return this;
  }

  public void unsetPasswordMd5() {
    this.passwordMd5 = null;
  }

  /** Returns true if field passwordMd5 is set (has been assigned a value) and false otherwise */
  public boolean isSetPasswordMd5() {
    return this.passwordMd5 != null;
  }

  public void setPasswordMd5IsSet(boolean value) {
    if (!value) {
      this.passwordMd5 = null;
    }
  }

  public String getCompanyGroupCode() {
    return this.companyGroupCode;
  }

  public ProxyLoginReq setCompanyGroupCode(String companyGroupCode) {
    this.companyGroupCode = companyGroupCode;
    return this;
  }

  public void unsetCompanyGroupCode() {
    this.companyGroupCode = null;
  }

  /** Returns true if field companyGroupCode is set (has been assigned a value) and false otherwise */
  public boolean isSetCompanyGroupCode() {
    return this.companyGroupCode != null;
  }

  public void setCompanyGroupCodeIsSet(boolean value) {
    if (!value) {
      this.companyGroupCode = null;
    }
  }

  public String getVerifyCode() {
    return this.verifyCode;
  }

  public ProxyLoginReq setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
    return this;
  }

  public void unsetVerifyCode() {
    this.verifyCode = null;
  }

  /** Returns true if field verifyCode is set (has been assigned a value) and false otherwise */
  public boolean isSetVerifyCode() {
    return this.verifyCode != null;
  }

  public void setVerifyCodeIsSet(boolean value) {
    if (!value) {
      this.verifyCode = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COMPANY_CODE:
      if (value == null) {
        unsetCompanyCode();
      } else {
        setCompanyCode((String)value);
      }
      break;

    case USER_NAME:
      if (value == null) {
        unsetUserName();
      } else {
        setUserName((String)value);
      }
      break;

    case PASSWORD_MD5:
      if (value == null) {
        unsetPasswordMd5();
      } else {
        setPasswordMd5((String)value);
      }
      break;

    case COMPANY_GROUP_CODE:
      if (value == null) {
        unsetCompanyGroupCode();
      } else {
        setCompanyGroupCode((String)value);
      }
      break;

    case VERIFY_CODE:
      if (value == null) {
        unsetVerifyCode();
      } else {
        setVerifyCode((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COMPANY_CODE:
      return getCompanyCode();

    case USER_NAME:
      return getUserName();

    case PASSWORD_MD5:
      return getPasswordMd5();

    case COMPANY_GROUP_CODE:
      return getCompanyGroupCode();

    case VERIFY_CODE:
      return getVerifyCode();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COMPANY_CODE:
      return isSetCompanyCode();
    case USER_NAME:
      return isSetUserName();
    case PASSWORD_MD5:
      return isSetPasswordMd5();
    case COMPANY_GROUP_CODE:
      return isSetCompanyGroupCode();
    case VERIFY_CODE:
      return isSetVerifyCode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProxyLoginReq)
      return this.equals((ProxyLoginReq)that);
    return false;
  }

  public boolean equals(ProxyLoginReq that) {
    if (that == null)
      return false;

    boolean this_present_companyCode = true && this.isSetCompanyCode();
    boolean that_present_companyCode = true && that.isSetCompanyCode();
    if (this_present_companyCode || that_present_companyCode) {
      if (!(this_present_companyCode && that_present_companyCode))
        return false;
      if (!this.companyCode.equals(that.companyCode))
        return false;
    }

    boolean this_present_userName = true && this.isSetUserName();
    boolean that_present_userName = true && that.isSetUserName();
    if (this_present_userName || that_present_userName) {
      if (!(this_present_userName && that_present_userName))
        return false;
      if (!this.userName.equals(that.userName))
        return false;
    }

    boolean this_present_passwordMd5 = true && this.isSetPasswordMd5();
    boolean that_present_passwordMd5 = true && that.isSetPasswordMd5();
    if (this_present_passwordMd5 || that_present_passwordMd5) {
      if (!(this_present_passwordMd5 && that_present_passwordMd5))
        return false;
      if (!this.passwordMd5.equals(that.passwordMd5))
        return false;
    }

    boolean this_present_companyGroupCode = true && this.isSetCompanyGroupCode();
    boolean that_present_companyGroupCode = true && that.isSetCompanyGroupCode();
    if (this_present_companyGroupCode || that_present_companyGroupCode) {
      if (!(this_present_companyGroupCode && that_present_companyGroupCode))
        return false;
      if (!this.companyGroupCode.equals(that.companyGroupCode))
        return false;
    }

    boolean this_present_verifyCode = true && this.isSetVerifyCode();
    boolean that_present_verifyCode = true && that.isSetVerifyCode();
    if (this_present_verifyCode || that_present_verifyCode) {
      if (!(this_present_verifyCode && that_present_verifyCode))
        return false;
      if (!this.verifyCode.equals(that.verifyCode))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ProxyLoginReq other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCompanyCode()).compareTo(other.isSetCompanyCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyCode, other.companyCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUserName()).compareTo(other.isSetUserName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUserName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.userName, other.userName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPasswordMd5()).compareTo(other.isSetPasswordMd5());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPasswordMd5()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.passwordMd5, other.passwordMd5);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCompanyGroupCode()).compareTo(other.isSetCompanyGroupCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompanyGroupCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.companyGroupCode, other.companyGroupCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVerifyCode()).compareTo(other.isSetVerifyCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVerifyCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.verifyCode, other.verifyCode);
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
    StringBuilder sb = new StringBuilder("ProxyLoginReq(");
    boolean first = true;

    if (isSetCompanyCode()) {
      sb.append("companyCode:");
      if (this.companyCode == null) {
        sb.append("null");
      } else {
        sb.append(this.companyCode);
      }
      first = false;
    }
    if (isSetUserName()) {
      if (!first) sb.append(", ");
      sb.append("userName:");
      if (this.userName == null) {
        sb.append("null");
      } else {
        sb.append(this.userName);
      }
      first = false;
    }
    if (isSetPasswordMd5()) {
      if (!first) sb.append(", ");
      sb.append("passwordMd5:");
      if (this.passwordMd5 == null) {
        sb.append("null");
      } else {
        sb.append(this.passwordMd5);
      }
      first = false;
    }
    if (isSetCompanyGroupCode()) {
      if (!first) sb.append(", ");
      sb.append("companyGroupCode:");
      if (this.companyGroupCode == null) {
        sb.append("null");
      } else {
        sb.append(this.companyGroupCode);
      }
      first = false;
    }
    if (isSetVerifyCode()) {
      if (!first) sb.append(", ");
      sb.append("verifyCode:");
      if (this.verifyCode == null) {
        sb.append("null");
      } else {
        sb.append(this.verifyCode);
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class ProxyLoginReqStandardSchemeFactory implements SchemeFactory {
    public ProxyLoginReqStandardScheme getScheme() {
      return new ProxyLoginReqStandardScheme();
    }
  }

  private static class ProxyLoginReqStandardScheme extends StandardScheme<ProxyLoginReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProxyLoginReq struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMPANY_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.companyCode = iprot.readString();
              struct.setCompanyCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // USER_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.userName = iprot.readString();
              struct.setUserNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // PASSWORD_MD5
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.passwordMd5 = iprot.readString();
              struct.setPasswordMd5IsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // COMPANY_GROUP_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.companyGroupCode = iprot.readString();
              struct.setCompanyGroupCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // VERIFY_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.verifyCode = iprot.readString();
              struct.setVerifyCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProxyLoginReq struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.companyCode != null) {
        if (struct.isSetCompanyCode()) {
          oprot.writeFieldBegin(COMPANY_CODE_FIELD_DESC);
          oprot.writeString(struct.companyCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.userName != null) {
        if (struct.isSetUserName()) {
          oprot.writeFieldBegin(USER_NAME_FIELD_DESC);
          oprot.writeString(struct.userName);
          oprot.writeFieldEnd();
        }
      }
      if (struct.passwordMd5 != null) {
        if (struct.isSetPasswordMd5()) {
          oprot.writeFieldBegin(PASSWORD_MD5_FIELD_DESC);
          oprot.writeString(struct.passwordMd5);
          oprot.writeFieldEnd();
        }
      }
      if (struct.companyGroupCode != null) {
        if (struct.isSetCompanyGroupCode()) {
          oprot.writeFieldBegin(COMPANY_GROUP_CODE_FIELD_DESC);
          oprot.writeString(struct.companyGroupCode);
          oprot.writeFieldEnd();
        }
      }
      if (struct.verifyCode != null) {
        if (struct.isSetVerifyCode()) {
          oprot.writeFieldBegin(VERIFY_CODE_FIELD_DESC);
          oprot.writeString(struct.verifyCode);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProxyLoginReqTupleSchemeFactory implements SchemeFactory {
    public ProxyLoginReqTupleScheme getScheme() {
      return new ProxyLoginReqTupleScheme();
    }
  }

  private static class ProxyLoginReqTupleScheme extends TupleScheme<ProxyLoginReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProxyLoginReq struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCompanyCode()) {
        optionals.set(0);
      }
      if (struct.isSetUserName()) {
        optionals.set(1);
      }
      if (struct.isSetPasswordMd5()) {
        optionals.set(2);
      }
      if (struct.isSetCompanyGroupCode()) {
        optionals.set(3);
      }
      if (struct.isSetVerifyCode()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetCompanyCode()) {
        oprot.writeString(struct.companyCode);
      }
      if (struct.isSetUserName()) {
        oprot.writeString(struct.userName);
      }
      if (struct.isSetPasswordMd5()) {
        oprot.writeString(struct.passwordMd5);
      }
      if (struct.isSetCompanyGroupCode()) {
        oprot.writeString(struct.companyGroupCode);
      }
      if (struct.isSetVerifyCode()) {
        oprot.writeString(struct.verifyCode);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProxyLoginReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.companyCode = iprot.readString();
        struct.setCompanyCodeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.userName = iprot.readString();
        struct.setUserNameIsSet(true);
      }
      if (incoming.get(2)) {
        struct.passwordMd5 = iprot.readString();
        struct.setPasswordMd5IsSet(true);
      }
      if (incoming.get(3)) {
        struct.companyGroupCode = iprot.readString();
        struct.setCompanyGroupCodeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.verifyCode = iprot.readString();
        struct.setVerifyCodeIsSet(true);
      }
    }
  }

}

