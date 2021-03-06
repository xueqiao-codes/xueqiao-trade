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

public enum TradeHostingBasicErrorCode implements org.apache.thrift.TEnum {
  /**
   * 托管机操作相关的错误
   */
  MACHINEID_ERROR(1000),
  HOSTING_HAS_ALREADY_INITED(1001),
  HOSTING_HAS_NOT_INITED(1002),
  HOSTING_MACHINE_NOT_FOUND(1003),
  HOSTING_CLEARING(1004),
  /**
   * 用户相关的错误
   */
  ERROR_USER_ALREADY_EXISTED(1500),
  ERROR_USER_NOT_EXISTED(1501),
  ERROR_USER_SESSION(1502),
  ERROR_USER_PASSWORD(1503),
  ERROR_USER_DISABLED(1504),
  /**
   * 子账户相关错误
   */
  ERROR_SUB_ACCOUNT_DUPLICATE_NAME(1701),
  ERROR_SUB_ACCOUNT_OP_MONEY_DUPLICATE_TICKET(1702),
  ERROR_SUB_ACCOUNT_RELATED_ITEM_NOT_EXIST(1703),
  ERROR_SUB_ACCOUNT_NOT_EXISTED(1704),
  /**
   * 通用错误
   */
  USER_FORBIDDEN_ERROR(2000),
  ERROR_PARAMETER(2001),
  ERROR_OPERATION_FORBIDDEN(2002),
  /**
   * 组合图相关错误的定义
   */
  ERROR_COMPOSE_GRAPH_NOT_OWNER(3000),
  ERROR_COMPOSE_GRAPH_CONTAINS_INVALID_CONTRACT(3001),
  ERROR_COMPOSE_GRAPH_INVALID(3002),
  ERROR_COMPOSE_GRAPH_NOT_EXISTED(3003),
  ERROR_COMPOSE_GRAPH_CONTAINS_EXCHANGE_COMB_CONTRACT(3004),
  ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_GCD_NOT_ONE(3005),
  ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_LEGS_SHOULD_GE_TWO(3006),
  ERROR_COMPOSE_VIEW_NOT_EXISTED(3050),
  ERROR_COMPOSE_VIEW_SUBSCRIBE_TOO_MANY(3056),
  /**
   * 配置系统的错误定义
   */
  ERROR_CONFIG_VERSION_LOW(3100),
  ERROR_CONFIG_LOST(3101),
  ERROR_CONFIG_SAME_CONTENT_MD5(3102),
  /**
   * 合约信息相关的错误
   */
  ERROR_CONTRACT_NOT_EXISTED(3200),
  ERROR_COMMODITY_NOT_EXISTED(3201),
  ERROR_CONTRACT_NOT_MATCH_MACHINE_RUNNING_MODE(3202),
  /**
   * 以下为账号相关的错误
   */
  ERROR_DUPLICATE_TRADE_ACCOUNT(3500),
  ERROR_TRADE_ACCOUNT_NOT_EXISTED(3501),
  ERROR_BROKER_ACCESS_CHECK_FAILED(3502),
  ERROR_TRADE_ACCOUNT_STATE_OPERATION_FORBIDDEN(3503),
  ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED(3504),
  ERROR_TRADE_ACCOUNT_CANNOT_MODIFY_USERNAME(3505),
  ERROR_TRADE_ACCOUNT_BROKER_TECH_ENV_NOT_MATCH(3506),
  ERROR_TRADE_ACCOUNT_INVALID_OTHER(3599),
  ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR(3600),
  ERROR_TRADE_ACCOUNT_INVALID_NOT_IN_TRADE_TIME(3601),
  ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED(3602),
  ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STARTING(3603),
  ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STOPED(3604),
  ERROR_TRADE_ACCOUNT_INVALID_CONNECTING(3605),
  /**
   * 订单执行相关的错误
   */
  ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID(3800),
  ERROR_EXEC_ORDER_ORDERREF_EXPIRED(3801),
  ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME(3802),
  ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO(3803),
  ERROR_EXEC_ORDER_NOT_EXISTED(3804),
  ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY(3805),
  ERROR_EXEC_ORDER_ALREADY_IN_REVOKING(3806),
  ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE(3807),
  ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE(3808),
  ERROR_EXEC_ORDER_EXISTED(3809),
  ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG(3810),
  ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT(3811),
  ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND(3812),
  ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED(3813),
  ERROR_EXEC_ORDER_REVOKE_TIMEOUT(3814),
  ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO(3815),
  ERROR_EXEC_ORDER_SELF_MATCH(3816),
  ERROR_EXEC_ORDER_REVOKE_ACTION_UNKOWN(3817),
  ERROR_EXEC_ORDER_DUPLICATE_SEND(3818),
  ERROR_EXEC_ORDER_FAILED_USER_CANCELLED(3850),
  ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT(3851),
  ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND(3852),
  ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE(3853),
  ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR(3854),
  ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED(3855),
  ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED(3856),
  ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH(3857),
  ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR(3858),
  ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED(3859),
  ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS(3860),
  ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED(3861),
  ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN(3862),
  ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN(3863),
  ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER(3900),
  ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND(3901),
  ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION(3902),
  ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION(3903),
  ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION(3904),
  ERROR_EXEC_ORDER_UPSIDE_REJECT_ORDER_SENDING(3905),
  ERROR_UPSIDE_CALL_FAILED(3920),
  /**
   * 通用错误， 与平台对接
   */
  ERROR_NOT_SUPPORTED(9000),
  ERROR_SERVER_INNER(10500);

  private final int value;

  private TradeHostingBasicErrorCode(int value) {
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
  public static TradeHostingBasicErrorCode findByValue(int value) { 
    switch (value) {
      case 1000:
        return MACHINEID_ERROR;
      case 1001:
        return HOSTING_HAS_ALREADY_INITED;
      case 1002:
        return HOSTING_HAS_NOT_INITED;
      case 1003:
        return HOSTING_MACHINE_NOT_FOUND;
      case 1004:
        return HOSTING_CLEARING;
      case 1500:
        return ERROR_USER_ALREADY_EXISTED;
      case 1501:
        return ERROR_USER_NOT_EXISTED;
      case 1502:
        return ERROR_USER_SESSION;
      case 1503:
        return ERROR_USER_PASSWORD;
      case 1504:
        return ERROR_USER_DISABLED;
      case 1701:
        return ERROR_SUB_ACCOUNT_DUPLICATE_NAME;
      case 1702:
        return ERROR_SUB_ACCOUNT_OP_MONEY_DUPLICATE_TICKET;
      case 1703:
        return ERROR_SUB_ACCOUNT_RELATED_ITEM_NOT_EXIST;
      case 1704:
        return ERROR_SUB_ACCOUNT_NOT_EXISTED;
      case 2000:
        return USER_FORBIDDEN_ERROR;
      case 2001:
        return ERROR_PARAMETER;
      case 2002:
        return ERROR_OPERATION_FORBIDDEN;
      case 3000:
        return ERROR_COMPOSE_GRAPH_NOT_OWNER;
      case 3001:
        return ERROR_COMPOSE_GRAPH_CONTAINS_INVALID_CONTRACT;
      case 3002:
        return ERROR_COMPOSE_GRAPH_INVALID;
      case 3003:
        return ERROR_COMPOSE_GRAPH_NOT_EXISTED;
      case 3004:
        return ERROR_COMPOSE_GRAPH_CONTAINS_EXCHANGE_COMB_CONTRACT;
      case 3005:
        return ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_GCD_NOT_ONE;
      case 3006:
        return ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_LEGS_SHOULD_GE_TWO;
      case 3050:
        return ERROR_COMPOSE_VIEW_NOT_EXISTED;
      case 3056:
        return ERROR_COMPOSE_VIEW_SUBSCRIBE_TOO_MANY;
      case 3100:
        return ERROR_CONFIG_VERSION_LOW;
      case 3101:
        return ERROR_CONFIG_LOST;
      case 3102:
        return ERROR_CONFIG_SAME_CONTENT_MD5;
      case 3200:
        return ERROR_CONTRACT_NOT_EXISTED;
      case 3201:
        return ERROR_COMMODITY_NOT_EXISTED;
      case 3202:
        return ERROR_CONTRACT_NOT_MATCH_MACHINE_RUNNING_MODE;
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
      case 3505:
        return ERROR_TRADE_ACCOUNT_CANNOT_MODIFY_USERNAME;
      case 3506:
        return ERROR_TRADE_ACCOUNT_BROKER_TECH_ENV_NOT_MATCH;
      case 3599:
        return ERROR_TRADE_ACCOUNT_INVALID_OTHER;
      case 3600:
        return ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
      case 3601:
        return ERROR_TRADE_ACCOUNT_INVALID_NOT_IN_TRADE_TIME;
      case 3602:
        return ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
      case 3603:
        return ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STARTING;
      case 3604:
        return ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STOPED;
      case 3605:
        return ERROR_TRADE_ACCOUNT_INVALID_CONNECTING;
      case 3800:
        return ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID;
      case 3801:
        return ERROR_EXEC_ORDER_ORDERREF_EXPIRED;
      case 3802:
        return ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME;
      case 3803:
        return ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO;
      case 3804:
        return ERROR_EXEC_ORDER_NOT_EXISTED;
      case 3805:
        return ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY;
      case 3806:
        return ERROR_EXEC_ORDER_ALREADY_IN_REVOKING;
      case 3807:
        return ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE;
      case 3808:
        return ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE;
      case 3809:
        return ERROR_EXEC_ORDER_EXISTED;
      case 3810:
        return ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG;
      case 3811:
        return ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT;
      case 3812:
        return ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND;
      case 3813:
        return ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED;
      case 3814:
        return ERROR_EXEC_ORDER_REVOKE_TIMEOUT;
      case 3815:
        return ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO;
      case 3816:
        return ERROR_EXEC_ORDER_SELF_MATCH;
      case 3817:
        return ERROR_EXEC_ORDER_REVOKE_ACTION_UNKOWN;
      case 3818:
        return ERROR_EXEC_ORDER_DUPLICATE_SEND;
      case 3850:
        return ERROR_EXEC_ORDER_FAILED_USER_CANCELLED;
      case 3851:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT;
      case 3852:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND;
      case 3853:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE;
      case 3854:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR;
      case 3855:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED;
      case 3856:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED;
      case 3857:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH;
      case 3858:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR;
      case 3859:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED;
      case 3860:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS;
      case 3861:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED;
      case 3862:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN;
      case 3863:
        return ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN;
      case 3900:
        return ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER;
      case 3901:
        return ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND;
      case 3902:
        return ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION;
      case 3903:
        return ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION;
      case 3904:
        return ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION;
      case 3905:
        return ERROR_EXEC_ORDER_UPSIDE_REJECT_ORDER_SENDING;
      case 3920:
        return ERROR_UPSIDE_CALL_FAILED;
      case 9000:
        return ERROR_NOT_SUPPORTED;
      case 10500:
        return ERROR_SERVER_INNER;
      default:
        return null;
    }
  }
}
