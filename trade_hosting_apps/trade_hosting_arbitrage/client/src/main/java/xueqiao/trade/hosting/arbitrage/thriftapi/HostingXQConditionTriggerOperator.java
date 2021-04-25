/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.arbitrage.thriftapi;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum HostingXQConditionTriggerOperator implements org.apache.thrift.TEnum {
  XQ_OP_LT(1),
  XQ_OP_LE(2),
  XQ_OP_EQ(3),
  XQ_OP_NE(4),
  XQ_OP_GT(5),
  XQ_OP_GE(6);

  private final int value;

  private HostingXQConditionTriggerOperator(int value) {
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
  public static HostingXQConditionTriggerOperator findByValue(int value) { 
    switch (value) {
      case 1:
        return XQ_OP_LT;
      case 2:
        return XQ_OP_LE;
      case 3:
        return XQ_OP_EQ;
      case 4:
        return XQ_OP_NE;
      case 5:
        return XQ_OP_GT;
      case 6:
        return XQ_OP_GE;
      default:
        return null;
    }
  }
}
