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

public enum StdOrderStateType implements org.apache.thrift.TEnum {
  SENDING(0),
  ADDING(1),
  WORKING(2),
  PART_FILLED(3),
  FILLED(4),
  CANCELED(5),
  CANCELING(6),
  REJECTED(7),
  EXPIRED(8),
  SLED_ERROR(9),
  UNTOUCHED(10),
  TOUCHED(11),
  REQUEST_CANCELING(101),
  REQUEST_INSERTING(102);

  private final int value;

  private StdOrderStateType(int value) {
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
  public static StdOrderStateType findByValue(int value) { 
    switch (value) {
      case 0:
        return SENDING;
      case 1:
        return ADDING;
      case 2:
        return WORKING;
      case 3:
        return PART_FILLED;
      case 4:
        return FILLED;
      case 5:
        return CANCELED;
      case 6:
        return CANCELING;
      case 7:
        return REJECTED;
      case 8:
        return EXPIRED;
      case 9:
        return SLED_ERROR;
      case 10:
        return UNTOUCHED;
      case 11:
        return TOUCHED;
      case 101:
        return REQUEST_CANCELING;
      case 102:
        return REQUEST_INSERTING;
      default:
        return null;
    }
  }
}
