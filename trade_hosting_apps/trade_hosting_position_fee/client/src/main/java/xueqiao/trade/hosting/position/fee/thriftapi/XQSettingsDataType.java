/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.fee.thriftapi;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum XQSettingsDataType implements org.apache.thrift.TEnum {
  SDT_NO_DATA(0),
  SDT_GENERAL(1),
  SDT_COMMODITY(2);

  private final int value;

  private XQSettingsDataType(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static XQSettingsDataType findByValue(int value) { 
    switch (value) {
      case 0:
        return SDT_NO_DATA;
      case 1:
        return SDT_GENERAL;
      case 2:
        return SDT_COMMODITY;
      default:
        return null;
    }
  }
}
