/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package xueqiao.company.service.maintenance;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * 维护状态
 */
public enum MaintenanceState implements org.apache.thrift.TEnum {
  EMPTY(0),
  SCHEDULED(1);

  private final int value;

  private MaintenanceState(int value) {
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
  public static MaintenanceState findByValue(int value) { 
    switch (value) {
      case 0:
        return EMPTY;
      case 1:
        return SCHEDULED;
      default:
        return null;
    }
  }
}
