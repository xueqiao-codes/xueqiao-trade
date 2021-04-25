/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.events;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * 组合视图事件定义
 */
public enum ComposeViewEventType implements org.apache.thrift.TEnum {
  COMPOSE_VIEW_ADDED(1),
  COMPOSE_VIEW_SUBSCRIBED(2),
  COMPOSE_VIEW_UNSUBSCRIBD(3),
  COMPOSE_VIEW_DELETED(4),
  COMPOSE_VIEW_ALL_CLEARED(5);

  private final int value;

  private ComposeViewEventType(int value) {
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
  public static ComposeViewEventType findByValue(int value) { 
    switch (value) {
      case 1:
        return COMPOSE_VIEW_ADDED;
      case 2:
        return COMPOSE_VIEW_SUBSCRIBED;
      case 3:
        return COMPOSE_VIEW_UNSUBSCRIBD;
      case 4:
        return COMPOSE_VIEW_DELETED;
      case 5:
        return COMPOSE_VIEW_ALL_CLEARED;
      default:
        return null;
    }
  }
}
