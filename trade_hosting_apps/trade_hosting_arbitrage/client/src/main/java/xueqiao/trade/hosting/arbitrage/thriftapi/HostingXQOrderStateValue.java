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

public enum HostingXQOrderStateValue implements org.apache.thrift.TEnum {
  XQ_ORDER_CREATED(1),
  XQ_ORDER_CANCELLED(2),
  XQ_ORDER_CANCELLING(3),
  XQ_ORDER_SUSPENDED(4),
  XQ_ORDER_SUSPENDING(5),
  XQ_ORDER_RUNNING(6),
  XQ_ORDER_STARTING(7),
  XQ_ORDER_FINISHED(8),
  XQ_ORDER_FINISHING(9);

  private final int value;

  private HostingXQOrderStateValue(int value) {
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
  public static HostingXQOrderStateValue findByValue(int value) { 
    switch (value) {
      case 1:
        return XQ_ORDER_CREATED;
      case 2:
        return XQ_ORDER_CANCELLED;
      case 3:
        return XQ_ORDER_CANCELLING;
      case 4:
        return XQ_ORDER_SUSPENDED;
      case 5:
        return XQ_ORDER_SUSPENDING;
      case 6:
        return XQ_ORDER_RUNNING;
      case 7:
        return XQ_ORDER_STARTING;
      case 8:
        return XQ_ORDER_FINISHED;
      case 9:
        return XQ_ORDER_FINISHING;
      default:
        return null;
    }
  }
}
