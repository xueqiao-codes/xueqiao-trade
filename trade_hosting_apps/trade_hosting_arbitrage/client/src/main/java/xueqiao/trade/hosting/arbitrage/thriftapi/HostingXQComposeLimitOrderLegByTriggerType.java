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

public enum HostingXQComposeLimitOrderLegByTriggerType implements org.apache.thrift.TEnum {
  PRICE_BEST(1),
  PRICE_TRYING(2);

  private final int value;

  private HostingXQComposeLimitOrderLegByTriggerType(int value) {
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
  public static HostingXQComposeLimitOrderLegByTriggerType findByValue(int value) { 
    switch (value) {
      case 1:
        return PRICE_BEST;
      case 2:
        return PRICE_TRYING;
      default:
        return null;
    }
  }
}
