/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.adjust.assign.thriftapi;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum PositionDirection implements org.apache.thrift.TEnum {
  POSITION_BUY(0),
  POSITION_SELL(1);

  private final int value;

  private PositionDirection(int value) {
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
  public static PositionDirection findByValue(int value) { 
    switch (value) {
      case 0:
        return POSITION_BUY;
      case 1:
        return POSITION_SELL;
      default:
        return null;
    }
  }
}
