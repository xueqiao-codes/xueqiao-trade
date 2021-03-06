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

public class ProxyFakeLoginReq implements org.apache.thrift.TBase<ProxyFakeLoginReq, ProxyFakeLoginReq._Fields>, java.io.Serializable, Cloneable, Comparable<ProxyFakeLoginReq> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ProxyFakeLoginReq");

  private static final org.apache.thrift.protocol.TField COMPANY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("companyCode", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField USER_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("userName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField PASSWORD_MD5_FIELD_DESC = new org.apache.thrift.protocol.TField("passwordMd5", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField TRADE_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("tradeType", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField VERIFY_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("verifyCode", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField LOGIN_USER_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("loginUserType", org.apache.thrift.protocol.TType.I32, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ProxyFakeLoginReqStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ProxyFakeLoginReqTupleSchemeFactory());
  }

  public String companyCode; // optional
  public String userName; // optional
  public String passwordMd5; // optional
  /**
   * 
   * @see ProxyTradeType
   */
  public ProxyTradeType tradeType; // optional
  public String verifyCode; // optional
  /**
   * 
   * @see LoginUserType
   */
  public LoginUserType loginUserType; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMPANY_CODE((short)1, "companyCode"),
    USER_NAME((short)2, "userName"),
    PASSWORD_MD5((short)3, "passwordMd5"),
    /**
     * 
     * @see ProxyTradeType
     */
    TRADE_TYPE((short)4, "tradeType"),
    VERIFY_CODE((short)5, "verifyCode"),
    /**
     * 
     * @see LoginUserType
     */
    LOGIN_USER_TYPE((short)6, "loginUserType");

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
        case 4: // TRADE_TYPE
          return TRADE_TYPE;
        case 5: // VERIFY_CODE
          return VERIFY_CODE;
        case 6: // LOGIN_USER_TYPE
          return LOGIN_USER_TYPE;
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
  private _Fields optionals[] = {_Fields.COMPANY_CODE,_Fields.USER_NAME,_Fields.PASSWORD_MD5,_Fields.TRADE_TYPE,_Fields.VERIFY_CODE,_Fields.LOGIN_USER_TYPE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMPANY_CODE, new org.apache.thrift.meta_data.FieldMetaData("companyCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USER_NAME, new org.apache.thrift.meta_data.FieldMetaData("userName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PASSWORD_MD5, new org.apache.thrift.meta_data.FieldMetaData("passwordMd5", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TRADE_TYPE, new org.apache.thrift.meta_data.FieldMetaData("tradeType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, ProxyTradeType.class)));
    tmpMap.put(_Fields.VERIFY_CODE, new org.apache.thrift.meta_data.FieldMetaData("verifyCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.LOGIN_USER_TYPE, new org.apache.thrift.meta_data.FieldMetaData("loginUserType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LoginUserType.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ProxyFakeLoginReq.class, metaDataMap);
  }

  public ProxyFakeLoginReq() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ProxyFakeLoginReq(ProxyFakeLoginReq other) {
    if (other.isSetCompanyCode()) {
      this.companyCode = other.companyCode;
    }
    if (other.isSetUserName()) {
      this.userName = other.userName;
    }
    if (other.isSetPasswordMd5()) {
      this.passwordMd5 = other.passwordMd5;
    }
    if (other.isSetTradeType()) {
      this.tradeType = other.tradeType;
    }
    if (other.isSetVerifyCode()) {
      this.verifyCode = other.verifyCode;
    }
    if (other.isSetLoginUserType()) {
      this.loginUserType = other.loginUserType;
    }
  }

  public ProxyFakeLoginReq deepCopy() {
    return new ProxyFakeLoginReq(this);
  }

  @Override
  public void clear() {
    this.companyCode = null;
    this.userName = null;
    this.passwordMd5 = null;
    this.tradeType = null;
    this.verifyCode = null;
    this.loginUserType = null;
  }

  public String getCompanyCode() {
    return this.companyCode;
  }

  public ProxyFakeLoginReq setCompanyCode(String companyCode) {
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

  public ProxyFakeLoginReq setUserName(String userName) {
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

  public ProxyFakeLoginReq setPasswordMd5(String passwordMd5) {
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

  /**
   * 
   * @see ProxyTradeType
   */
  public ProxyTradeType getTradeType() {
    return this.tradeType;
  }

  /**
   * 
   * @see ProxyTradeType
   */
  public ProxyFakeLoginReq setTradeType(ProxyTradeType tradeType) {
    this.tradeType = tradeType;
    return this;
  }

  public void unsetTradeType() {
    this.tradeType = null;
  }

  /** Returns true if field tradeType is set (has been assigned a value) and false otherwise */
  public boolean isSetTradeType() {
    return this.tradeType != null;
  }

  public void setTradeTypeIsSet(boolean value) {
    if (!value) {
      this.tradeType = null;
    }
  }

  public String getVerifyCode() {
    return this.verifyCode;
  }

  public ProxyFakeLoginReq setVerifyCode(String verifyCode) {
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

  /**
   * 
   * @see LoginUserType
   */
  public LoginUserType getLoginUserType() {
    return this.loginUserType;
  }

  /**
   * 
   * @see LoginUserType
   */
  public ProxyFakeLoginReq setLoginUserType(LoginUserType loginUserType) {
    this.loginUserType = loginUserType;
    return this;
  }

  public void unsetLoginUserType() {
    this.loginUserType = null;
  }

  /** Returns true if field loginUserType is set (has been assigned a value) and false otherwise */
  public boolean isSetLoginUserType() {
    return this.loginUserType != null;
  }

  public void setLoginUserTypeIsSet(boolean value) {
    if (!value) {
      this.loginUserType = null;
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

    case TRADE_TYPE:
      if (value == null) {
        unsetTradeType();
      } else {
        setTradeType((ProxyTradeType)value);
      }
      break;

    case VERIFY_CODE:
      if (value == null) {
        unsetVerifyCode();
      } else {
        setVerifyCode((String)value);
      }
      break;

    case LOGIN_USER_TYPE:
      if (value == null) {
        unsetLoginUserType();
      } else {
        setLoginUserType((LoginUserType)value);
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

    case TRADE_TYPE:
      return getTradeType();

    case VERIFY_CODE:
      return getVerifyCode();

    case LOGIN_USER_TYPE:
      return getLoginUserType();

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
    case TRADE_TYPE:
      return isSetTradeType();
    case VERIFY_CODE:
      return isSetVerifyCode();
    case LOGIN_USER_TYPE:
      return isSetLoginUserType();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ProxyFakeLoginReq)
      return this.equals((ProxyFakeLoginReq)that);
    return false;
  }

  public boolean equals(ProxyFakeLoginReq that) {
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

    boolean this_present_tradeType = true && this.isSetTradeType();
    boolean that_present_tradeType = true && that.isSetTradeType();
    if (this_present_tradeType || that_present_tradeType) {
      if (!(this_present_tradeType && that_present_tradeType))
        return false;
      if (!this.tradeType.equals(that.tradeType))
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

    boolean this_present_loginUserType = true && this.isSetLoginUserType();
    boolean that_present_loginUserType = true && that.isSetLoginUserType();
    if (this_present_loginUserType || that_present_loginUserType) {
      if (!(this_present_loginUserType && that_present_loginUserType))
        return false;
      if (!this.loginUserType.equals(that.loginUserType))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public int compareTo(ProxyFakeLoginReq other) {
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
    lastComparison = Boolean.valueOf(isSetTradeType()).compareTo(other.isSetTradeType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTradeType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tradeType, other.tradeType);
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
    lastComparison = Boolean.valueOf(isSetLoginUserType()).compareTo(other.isSetLoginUserType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLoginUserType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.loginUserType, other.loginUserType);
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
    StringBuilder sb = new StringBuilder("ProxyFakeLoginReq(");
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
    if (isSetTradeType()) {
      if (!first) sb.append(", ");
      sb.append("tradeType:");
      if (this.tradeType == null) {
        sb.append("null");
      } else {
        sb.append(this.tradeType);
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
    if (isSetLoginUserType()) {
      if (!first) sb.append(", ");
      sb.append("loginUserType:");
      if (this.loginUserType == null) {
        sb.append("null");
      } else {
        sb.append(this.loginUserType);
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

  private static class ProxyFakeLoginReqStandardSchemeFactory implements SchemeFactory {
    public ProxyFakeLoginReqStandardScheme getScheme() {
      return new ProxyFakeLoginReqStandardScheme();
    }
  }

  private static class ProxyFakeLoginReqStandardScheme extends StandardScheme<ProxyFakeLoginReq> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ProxyFakeLoginReq struct) throws org.apache.thrift.TException {
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
          case 4: // TRADE_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.tradeType = ProxyTradeType.findByValue(iprot.readI32());
              struct.setTradeTypeIsSet(true);
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
          case 6: // LOGIN_USER_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.loginUserType = LoginUserType.findByValue(iprot.readI32());
              struct.setLoginUserTypeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ProxyFakeLoginReq struct) throws org.apache.thrift.TException {
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
      if (struct.tradeType != null) {
        if (struct.isSetTradeType()) {
          oprot.writeFieldBegin(TRADE_TYPE_FIELD_DESC);
          oprot.writeI32(struct.tradeType.getValue());
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
      if (struct.loginUserType != null) {
        if (struct.isSetLoginUserType()) {
          oprot.writeFieldBegin(LOGIN_USER_TYPE_FIELD_DESC);
          oprot.writeI32(struct.loginUserType.getValue());
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ProxyFakeLoginReqTupleSchemeFactory implements SchemeFactory {
    public ProxyFakeLoginReqTupleScheme getScheme() {
      return new ProxyFakeLoginReqTupleScheme();
    }
  }

  private static class ProxyFakeLoginReqTupleScheme extends TupleScheme<ProxyFakeLoginReq> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ProxyFakeLoginReq struct) throws org.apache.thrift.TException {
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
      if (struct.isSetTradeType()) {
        optionals.set(3);
      }
      if (struct.isSetVerifyCode()) {
        optionals.set(4);
      }
      if (struct.isSetLoginUserType()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetCompanyCode()) {
        oprot.writeString(struct.companyCode);
      }
      if (struct.isSetUserName()) {
        oprot.writeString(struct.userName);
      }
      if (struct.isSetPasswordMd5()) {
        oprot.writeString(struct.passwordMd5);
      }
      if (struct.isSetTradeType()) {
        oprot.writeI32(struct.tradeType.getValue());
      }
      if (struct.isSetVerifyCode()) {
        oprot.writeString(struct.verifyCode);
      }
      if (struct.isSetLoginUserType()) {
        oprot.writeI32(struct.loginUserType.getValue());
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ProxyFakeLoginReq struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
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
        struct.tradeType = ProxyTradeType.findByValue(iprot.readI32());
        struct.setTradeTypeIsSet(true);
      }
      if (incoming.get(4)) {
        struct.verifyCode = iprot.readString();
        struct.setVerifyCodeIsSet(true);
      }
      if (incoming.get(5)) {
        struct.loginUserType = LoginUserType.findByValue(iprot.readI32());
        struct.setLoginUserTypeIsSet(true);
      }
    }
  }

}

