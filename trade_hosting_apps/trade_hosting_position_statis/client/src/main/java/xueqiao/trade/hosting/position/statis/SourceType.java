/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.trade.hosting.position.statis;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum SourceType implements org.apache.thrift.TEnum {
  ST_UNKNOWN(0),
  ST_ASSIGN(1),
  ST_TRADE(2),
  ST_MERGE_TO_COMPOSE_NOT_TRADE(3);

  private final int value;

  private SourceType(int value) {
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
  public static SourceType findByValue(int value) { 
    switch (value) {
      case 0:
        return ST_UNKNOWN;
      case 1:
        return ST_ASSIGN;
      case 2:
        return ST_TRADE;
      case 3:
        return ST_MERGE_TO_COMPOSE_NOT_TRADE;
      default:
        return null;
    }
  }
}
