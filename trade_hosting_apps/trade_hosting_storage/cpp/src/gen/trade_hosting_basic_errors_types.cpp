/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#include "trade_hosting_basic_errors_types.h"

#include <algorithm>

namespace xueqiao { namespace trade { namespace hosting {

int _kTradeHostingBasicErrorCodeValues[] = {
  TradeHostingBasicErrorCode::MACHINEID_ERROR,
  TradeHostingBasicErrorCode::HOSTING_HAS_ALREADY_INITED,
  TradeHostingBasicErrorCode::HOSTING_HAS_NOT_INITED,
  TradeHostingBasicErrorCode::HOSTING_MACHINE_NOT_FOUND,
  TradeHostingBasicErrorCode::HOSTING_CLEARING,
  TradeHostingBasicErrorCode::ERROR_USER_ALREADY_EXISTED,
  TradeHostingBasicErrorCode::ERROR_USER_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_USER_SESSION,
  TradeHostingBasicErrorCode::ERROR_USER_PASSWORD,
  TradeHostingBasicErrorCode::ERROR_USER_DISABLED,
  TradeHostingBasicErrorCode::ERROR_SUB_ACCOUNT_DUPLICATE_NAME,
  TradeHostingBasicErrorCode::ERROR_SUB_ACCOUNT_OP_MONEY_DUPLICATE_TICKET,
  TradeHostingBasicErrorCode::ERROR_SUB_ACCOUNT_RELATED_ITEM_NOT_EXIST,
  TradeHostingBasicErrorCode::ERROR_SUB_ACCOUNT_NOT_EXISTED,
  TradeHostingBasicErrorCode::USER_FORBIDDEN_ERROR,
  TradeHostingBasicErrorCode::ERROR_PARAMETER,
  TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_NOT_OWNER,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_CONTAINS_INVALID_CONTRACT,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_INVALID,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_CONTAINS_EXCHANGE_COMB_CONTRACT,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_GCD_NOT_ONE,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_LEGS_SHOULD_GE_TWO,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_VIEW_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_COMPOSE_VIEW_SUBSCRIBE_TOO_MANY,
  TradeHostingBasicErrorCode::ERROR_CONFIG_VERSION_LOW,
  TradeHostingBasicErrorCode::ERROR_CONFIG_LOST,
  TradeHostingBasicErrorCode::ERROR_CONFIG_SAME_CONTENT_MD5,
  TradeHostingBasicErrorCode::ERROR_CONTRACT_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_COMMODITY_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_CONTRACT_NOT_MATCH_MACHINE_RUNNING_MODE,
  TradeHostingBasicErrorCode::ERROR_DUPLICATE_TRADE_ACCOUNT,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_BROKER_ACCESS_CHECK_FAILED,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_STATE_OPERATION_FORBIDDEN,
  TradeHostingBasicErrorCode::ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_CANNOT_MODIFY_USERNAME,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_BROKER_TECH_ENV_NOT_MATCH,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_OTHER,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_NOT_IN_TRADE_TIME,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STARTING,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STOPED,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_CONNECTING,
  TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_AUTH,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_ORDERREF_EXPIRED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_NOT_EXISTED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_ALREADY_IN_REVOKING,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_EXISTED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REVOKE_TIMEOUT,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_SELF_MATCH,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REVOKE_ACTION_UNKOWN,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_DUPLICATE_SEND,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_FAILED_USER_CANCELLED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION,
  TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_ORDER_SENDING,
  TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED,
  TradeHostingBasicErrorCode::ERROR_NOT_SUPPORTED,
  TradeHostingBasicErrorCode::ERROR_SERVER_INNER
};
const char* _kTradeHostingBasicErrorCodeNames[] = {
  "MACHINEID_ERROR",
  "HOSTING_HAS_ALREADY_INITED",
  "HOSTING_HAS_NOT_INITED",
  "HOSTING_MACHINE_NOT_FOUND",
  "HOSTING_CLEARING",
  "ERROR_USER_ALREADY_EXISTED",
  "ERROR_USER_NOT_EXISTED",
  "ERROR_USER_SESSION",
  "ERROR_USER_PASSWORD",
  "ERROR_USER_DISABLED",
  "ERROR_SUB_ACCOUNT_DUPLICATE_NAME",
  "ERROR_SUB_ACCOUNT_OP_MONEY_DUPLICATE_TICKET",
  "ERROR_SUB_ACCOUNT_RELATED_ITEM_NOT_EXIST",
  "ERROR_SUB_ACCOUNT_NOT_EXISTED",
  "USER_FORBIDDEN_ERROR",
  "ERROR_PARAMETER",
  "ERROR_OPERATION_FORBIDDEN",
  "ERROR_COMPOSE_GRAPH_NOT_OWNER",
  "ERROR_COMPOSE_GRAPH_CONTAINS_INVALID_CONTRACT",
  "ERROR_COMPOSE_GRAPH_INVALID",
  "ERROR_COMPOSE_GRAPH_NOT_EXISTED",
  "ERROR_COMPOSE_GRAPH_CONTAINS_EXCHANGE_COMB_CONTRACT",
  "ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_GCD_NOT_ONE",
  "ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_LEGS_SHOULD_GE_TWO",
  "ERROR_COMPOSE_VIEW_NOT_EXISTED",
  "ERROR_COMPOSE_VIEW_SUBSCRIBE_TOO_MANY",
  "ERROR_CONFIG_VERSION_LOW",
  "ERROR_CONFIG_LOST",
  "ERROR_CONFIG_SAME_CONTENT_MD5",
  "ERROR_CONTRACT_NOT_EXISTED",
  "ERROR_COMMODITY_NOT_EXISTED",
  "ERROR_CONTRACT_NOT_MATCH_MACHINE_RUNNING_MODE",
  "ERROR_DUPLICATE_TRADE_ACCOUNT",
  "ERROR_TRADE_ACCOUNT_NOT_EXISTED",
  "ERROR_BROKER_ACCESS_CHECK_FAILED",
  "ERROR_TRADE_ACCOUNT_STATE_OPERATION_FORBIDDEN",
  "ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED",
  "ERROR_TRADE_ACCOUNT_CANNOT_MODIFY_USERNAME",
  "ERROR_TRADE_ACCOUNT_BROKER_TECH_ENV_NOT_MATCH",
  "ERROR_TRADE_ACCOUNT_INVALID_OTHER",
  "ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR",
  "ERROR_TRADE_ACCOUNT_INVALID_NOT_IN_TRADE_TIME",
  "ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED",
  "ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STARTING",
  "ERROR_TRADE_ACCOUNT_INVALID_PROCESS_STOPED",
  "ERROR_TRADE_ACCOUNT_INVALID_CONNECTING",
  "ERROR_TRADE_ACCOUNT_INVALID_AUTH",
  "ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID",
  "ERROR_EXEC_ORDER_ORDERREF_EXPIRED",
  "ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME",
  "ERROR_EXEC_ORDER_CANNOT_DELETE_NO_DEALINFO",
  "ERROR_EXEC_ORDER_NOT_EXISTED",
  "ERROR_EXEC_ORDER_STATE_NOT_IN_WAITING_VERIFY",
  "ERROR_EXEC_ORDER_ALREADY_IN_REVOKING",
  "ERROR_EXEC_ORDER_UPSIDE_DELETED_FOR_REVOKE",
  "ERROR_EXEC_ORDER_UPSIDE_FINISHED_FOR_REVOKE",
  "ERROR_EXEC_ORDER_EXISTED",
  "ERROR_EXEC_ORDER_SYSTEM_SEND_FROM_VERIFYTIME_TOO_LONG",
  "ERROR_EXEC_ORDER_TRADE_ACCOUNT_IN_INIT",
  "ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND",
  "ERROR_EXEC_ORDER_CTP_COMB_INSTRUMENT_SLICE_FAILED",
  "ERROR_EXEC_ORDER_REVOKE_TIMEOUT",
  "ERROR_EXEC_ORDER_ESUNNY9_NO_ACCOUNT_NO",
  "ERROR_EXEC_ORDER_SELF_MATCH",
  "ERROR_EXEC_ORDER_REVOKE_ACTION_UNKOWN",
  "ERROR_EXEC_ORDER_DUPLICATE_SEND",
  "ERROR_EXEC_ORDER_FAILED_USER_CANCELLED",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_NOT_FOUND",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_NOT_SUPPORTED_ORDER_TYPE",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_ERROR",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_TOO_MUCH",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_CONTRACT_MAPPING_ERROR",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_MAPPING_CONTRACT_CODE_FAILED",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_SYSTEM_CANNOT_PROCESSS",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN",
  "ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN",
  "ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER",
  "ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND",
  "ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION",
  "ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION",
  "ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION",
  "ERROR_EXEC_ORDER_UPSIDE_REJECT_ORDER_SENDING",
  "ERROR_UPSIDE_CALL_FAILED",
  "ERROR_NOT_SUPPORTED",
  "ERROR_SERVER_INNER"
};
const std::map<int, const char*> _TradeHostingBasicErrorCode_VALUES_TO_NAMES(::apache::thrift::TEnumIterator(89, _kTradeHostingBasicErrorCodeValues, _kTradeHostingBasicErrorCodeNames), ::apache::thrift::TEnumIterator(-1, NULL, NULL));

}}} // namespace
