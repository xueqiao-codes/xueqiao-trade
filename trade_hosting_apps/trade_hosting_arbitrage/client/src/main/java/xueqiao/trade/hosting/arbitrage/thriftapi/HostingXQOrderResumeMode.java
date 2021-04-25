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

/**
 * 订单恢复运行的模式
 */
public enum HostingXQOrderResumeMode implements org.apache.thrift.TEnum {
  RESUME_MODE_NONE(0),
  RESUME_MODE_COMPOSE_CHASING_BY_PRICE(1),
  RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST(2);

  private final int value;

  private HostingXQOrderResumeMode(int value) {
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
  public static HostingXQOrderResumeMode findByValue(int value) { 
    switch (value) {
      case 0:
        return RESUME_MODE_NONE;
      case 1:
        return RESUME_MODE_COMPOSE_CHASING_BY_PRICE;
      case 2:
        return RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST;
      default:
        return null;
    }
  }
}