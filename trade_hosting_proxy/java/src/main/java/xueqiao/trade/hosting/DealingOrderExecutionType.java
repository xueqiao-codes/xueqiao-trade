/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum DealingOrderExecutionType implements org.apache.thrift.TEnum {
  VERIFY(0),
  INPUT(1),
  ACTION_CANCEL(2),
  SLED_CLOSE(3);

  private final int value;

  private DealingOrderExecutionType(int value) {
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
  public static DealingOrderExecutionType findByValue(int value) { 
    switch (value) {
      case 0:
        return VERIFY;
      case 1:
        return INPUT;
      case 2:
        return ACTION_CANCEL;
      case 3:
        return SLED_CLOSE;
      default:
        return null;
    }
  }
}