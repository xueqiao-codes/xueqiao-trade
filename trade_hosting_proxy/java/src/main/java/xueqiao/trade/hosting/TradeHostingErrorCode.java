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

public enum TradeHostingErrorCode implements org.apache.thrift.TEnum {
  MACHINEID_ERROR(1000),
  HOSTING_HAS_ALREADY_INITED(1001),
  HOSTING_HAS_NOT_INITED(1002),
  HOSTING_MACHINE_NOT_FOUND(1003),
  ERROR_USER_ALREADY_EXISTED(1500),
  ERROR_USER_NOT_EXISTED(1501),
  ERROR_USER_SESSION(1502),
  ERROR_USER_PASSWORD(1503),
  USER_FORBIDDEN_ERROR(2000),
  ERROR_PARAMETER(2001),
  DUPLICATE_COMPOSE_GRAPH_NAME(3000),
  COMPOSE_UPDATE_FIELDS_FORBIDDEN(3001),
  COMPOSE_STATE_OPERATION_FORBIDDEN(3002),
  COMPOSE_GRAPH_CONTAINS_INVALID_CONTRACT(3003),
  COMPOSE_GRAPH_INVALID(3004),
  ERROR_COMPOSE_NOT_EXISTED(3005),
  ERROR_COMPOSE_NOT_OWNER(3006),
  ERROR_COMPOSE_SUBSCRIBE_TOO_MANY(3007),
  ERROR_DUPLICATE_TRADE_ACCOUNT(3500),
  ERROR_TRADE_ACCOUNT_NOT_EXISTED(3501),
  ERROR_BROKER_ACCESS_CHECK_FAILED(3502),
  ERROR_TRADE_ACCOUNT_STATE_OPERATION_FORBIDDEN(3503),
  ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED(3504),
  ERROR_TRADE_ACCOUNT_INVALID_OTHER(3599),
  ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR(3600),
  ERROR_TRADE_ACCOUNT_INVALID_NOT_IN_TRADE_TIME(3601),
  ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED(3602),
  ERROR_DEALING_UNKNOWN_ORDER_STATE(4000),
  ERROR_DEALING_ORDER_STATE_HANDLE_EVENT_FAIL(4001),
  ERROR_DEALING_ORDER_STATE_HANDLE_EXECUTION_FAIL(4002),
  ERROR_DEALING_PROCESS_MANAGEMENT_INIT_FAIL(4080),
  ERROR_DEALING_PROCESS_MANAGEMENT_GET_PROCESS_INFO_FAIL(4081),
  ERROR_DEALING_PROCESS_MANAGEMENT_NEW_PROCESS_INFO_FAIL(4082),
  ERROR_DEALING_PROCESS_MANAGEMENT_REMOVE_PROCESS_INFO_FAIL(4083),
  ERROR_DEALING_PROCESS_MANAGEMENT_START_PROCESS_INFO_FAIL(4084),
  ERROR_DEALING_PROCESS_MANAGEMENT_RESTART_PROCESS_INFO_FAIL(4085),
  ERROR_DEALING_PROCESS_MANAGEMENT_IS_INITIATING(4086),
  ERROR_DEALING_PROCESS_MANAGEMENT_HANDLE_TRADE_ACCOUNT_EVENT_FAIL(4087),
  ERROR_DEALING_CTP_SERVICE_INIT_FAIL(4101),
  ERROR_DEALING_CTP_SERVICE_IS_LOGGING_NOW(4102),
  ERROR_DEALING_CTP_SERVICE_LOGIN_FAIL(4103),
  ERROR_DEALING_CTP_SERVICE_DISCONNECTED(4104),
  ERROR_DEALING_CTP_SERVICE_INPUT_ORDER_FAIL(4105),
  ERROR_DEALING_CTP_SERVICE_INPUT_ORDER_ACTION_FAIL(4106),
  ERROR_NOT_SUPPORTED(9000),
  ERROR_SERVER_INNER(10500);

  private final int value;

  private TradeHostingErrorCode(int value) {
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
  public static TradeHostingErrorCode findByValue(int value) { 
    switch (value) {
      case 1000:
        return MACHINEID_ERROR;
      case 1001:
        return HOSTING_HAS_ALREADY_INITED;
      case 1002:
        return HOSTING_HAS_NOT_INITED;
      case 1003:
        return HOSTING_MACHINE_NOT_FOUND;
      case 1500:
        return ERROR_USER_ALREADY_EXISTED;
      case 1501:
        return ERROR_USER_NOT_EXISTED;
      case 1502:
        return ERROR_USER_SESSION;
      case 1503:
        return ERROR_USER_PASSWORD;
      case 2000:
        return USER_FORBIDDEN_ERROR;
      case 2001:
        return ERROR_PARAMETER;
      case 3000:
        return DUPLICATE_COMPOSE_GRAPH_NAME;
      case 3001:
        return COMPOSE_UPDATE_FIELDS_FORBIDDEN;
      case 3002:
        return COMPOSE_STATE_OPERATION_FORBIDDEN;
      case 3003:
        return COMPOSE_GRAPH_CONTAINS_INVALID_CONTRACT;
      case 3004:
        return COMPOSE_GRAPH_INVALID;
      case 3005:
        return ERROR_COMPOSE_NOT_EXISTED;
      case 3006:
        return ERROR_COMPOSE_NOT_OWNER;
      case 3007:
        return ERROR_COMPOSE_SUBSCRIBE_TOO_MANY;
      case 3500:
        return ERROR_DUPLICATE_TRADE_ACCOUNT;
      case 3501:
        return ERROR_TRADE_ACCOUNT_NOT_EXISTED;
      case 3502:
        return ERROR_BROKER_ACCESS_CHECK_FAILED;
      case 3503:
        return ERROR_TRADE_ACCOUNT_STATE_OPERATION_FORBIDDEN;
      case 3504:
        return ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED;
      case 3599:
        return ERROR_TRADE_ACCOUNT_INVALID_OTHER;
      case 3600:
        return ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
      case 3601:
        return ERROR_TRADE_ACCOUNT_INVALID_NOT_IN_TRADE_TIME;
      case 3602:
        return ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
      case 4000:
        return ERROR_DEALING_UNKNOWN_ORDER_STATE;
      case 4001:
        return ERROR_DEALING_ORDER_STATE_HANDLE_EVENT_FAIL;
      case 4002:
        return ERROR_DEALING_ORDER_STATE_HANDLE_EXECUTION_FAIL;
      case 4080:
        return ERROR_DEALING_PROCESS_MANAGEMENT_INIT_FAIL;
      case 4081:
        return ERROR_DEALING_PROCESS_MANAGEMENT_GET_PROCESS_INFO_FAIL;
      case 4082:
        return ERROR_DEALING_PROCESS_MANAGEMENT_NEW_PROCESS_INFO_FAIL;
      case 4083:
        return ERROR_DEALING_PROCESS_MANAGEMENT_REMOVE_PROCESS_INFO_FAIL;
      case 4084:
        return ERROR_DEALING_PROCESS_MANAGEMENT_START_PROCESS_INFO_FAIL;
      case 4085:
        return ERROR_DEALING_PROCESS_MANAGEMENT_RESTART_PROCESS_INFO_FAIL;
      case 4086:
        return ERROR_DEALING_PROCESS_MANAGEMENT_IS_INITIATING;
      case 4087:
        return ERROR_DEALING_PROCESS_MANAGEMENT_HANDLE_TRADE_ACCOUNT_EVENT_FAIL;
      case 4101:
        return ERROR_DEALING_CTP_SERVICE_INIT_FAIL;
      case 4102:
        return ERROR_DEALING_CTP_SERVICE_IS_LOGGING_NOW;
      case 4103:
        return ERROR_DEALING_CTP_SERVICE_LOGIN_FAIL;
      case 4104:
        return ERROR_DEALING_CTP_SERVICE_DISCONNECTED;
      case 4105:
        return ERROR_DEALING_CTP_SERVICE_INPUT_ORDER_FAIL;
      case 4106:
        return ERROR_DEALING_CTP_SERVICE_INPUT_ORDER_ACTION_FAIL;
      case 9000:
        return ERROR_NOT_SUPPORTED;
      case 10500:
        return ERROR_SERVER_INNER;
      default:
        return null;
    }
  }
}
