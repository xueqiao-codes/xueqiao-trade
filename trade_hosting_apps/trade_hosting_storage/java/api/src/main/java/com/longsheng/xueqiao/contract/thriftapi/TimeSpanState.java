/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.longsheng.xueqiao.contract.thriftapi;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum TimeSpanState implements org.apache.thrift.TEnum {
  T_OPEN(0),
  REST(1),
  CLOSED(2),
  T_PLUS_ONE_OPEN(3);

  private final int value;

  private TimeSpanState(int value) {
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
  public static TimeSpanState findByValue(int value) { 
    switch (value) {
      case 0:
        return T_OPEN;
      case 1:
        return REST;
      case 2:
        return CLOSED;
      case 3:
        return T_PLUS_ONE_OPEN;
      default:
        return null;
    }
  }
}
