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

public enum TaskNoteGuardEventType implements org.apache.thrift.TEnum {
  GUARD_TASK_NOTE_CREATED(1),
  GUARD_TASK_NOTE_DELETED(2);

  private final int value;

  private TaskNoteGuardEventType(int value) {
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
  public static TaskNoteGuardEventType findByValue(int value) { 
    switch (value) {
      case 1:
        return GUARD_TASK_NOTE_CREATED;
      case 2:
        return GUARD_TASK_NOTE_DELETED;
      default:
        return null;
    }
  }
}
