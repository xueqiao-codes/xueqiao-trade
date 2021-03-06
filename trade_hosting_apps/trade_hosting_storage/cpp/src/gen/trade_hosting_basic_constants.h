/**
 * Autogenerated by Thrift Compiler (0.9.1)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
#ifndef trade_hosting_basic_CONSTANTS_H
#define trade_hosting_basic_CONSTANTS_H

#include "trade_hosting_basic_types.h"

namespace xueqiao { namespace trade { namespace hosting {

class trade_hosting_basicConstants {
 public:
  trade_hosting_basicConstants();

  int32_t MAX_LOGIN_NAME_LENGTH;
  int32_t MAX_LOGIN_PASSWD_LENGTH;
  int32_t MAX_PHONE_LENGTH;
  int32_t MAX_NICKNAME_LENGTH;
  int32_t MAX_EMAIL_LENGTH;
  int32_t MAX_VARIABLE_LENGTH;
  int32_t MAX_FORMULAR_LENGTH;
  int32_t MAX_SUBSCRIBE_COMPOSE_NUMBER;
  int32_t MAX_COMPOSE_LEG_COUNT;
  int32_t MAX_TRADE_ACCOUNT_LOGIN_USER_NAME_LENGTH;
  int32_t MAX_TRADE_ACCOUNT_LOGIN_PASSWD_LENGTH;
  int32_t MAX_TRADE_ACCOUNT_INVALID_REASON_LENGTH;
  std::string ESUNNY3_APPID_PROPKEY;
  std::string ESUNNY3_CERTINFO_PROPKEY;
  std::string ESUNNY9_AUTHCODE_PROPKEY;
};

extern const trade_hosting_basicConstants g_trade_hosting_basic_constants;

}}} // namespace

#endif
