/*
 * es9_login_manager.cpp
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */
#include "es9_login_manager.h"

#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/string_util.h"
#include "effective_reporter.h"
#include "es9_login_handler.h"
#include "es9_error_mapping.h"

#include "iTapAPIError.h"
#include "trade_hosting_basic_errors_types.h"
#include "trade_hosting_storage_api.h"

using namespace apache::thrift;
using namespace es9ext::framework;
using namespace platform::comm;
using namespace soldier::base;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


Es9LoginManager::Es9LoginManager(
        const std::shared_ptr<Es9RequestDispatcher>& request_dispatcher
        , const std::shared_ptr<HostingTradeAccount>& trade_account)
    : request_dispatcher_(request_dispatcher)
      , trade_account_(trade_account)
      , working_thread_(new soldier::base::TaskThread()){
}

Es9LoginManager::~Es9LoginManager() {

}

void Es9LoginManager::startLogin() {
    working_thread_->postTask(&Es9LoginManager::handleStartLogin, this);
}

void Es9LoginManager::handleStartLogin() {
    std::shared_ptr<Es9LoginHandler> login_handler(
            new Es9LoginHandler(trade_account_));
    int ret = request_dispatcher_->sendRequest(login_handler);
    if (ret != 0) {
        APPLOG_ERROR("start login failed, ret={}", ret);
   }
}

void Es9LoginManager::OnRspLogin(
        ITapTrade::TAPIINT32 errorCode
        , const ITapTrade::TapAPITradeLoginRspInfo *loginRspInfo) {
    std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo> login_rsp_info;
    if (loginRspInfo) {
        login_rsp_info.reset(new ITapTrade::TapAPITradeLoginRspInfo(*loginRspInfo));
    }

    working_thread_->postTask(&Es9LoginManager::handleOnRspLogin
            , this, errorCode, login_rsp_info);
}

void Es9LoginManager::OnAPIReady() {
    working_thread_->postTask(&Es9LoginManager::handleAPIReady, this);
}

void Es9LoginManager::OnDisconnect(ITapTrade::TAPIINT32 reasonCode) {
    working_thread_->postTask(&Es9LoginManager::handleDisconnect, this, reasonCode);
}

void Es9LoginManager::handleDisconnect(ITapTrade::TAPIINT32 reasonCode) {
    login_rsp_info_ = std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo>();
    login_rsp_data_ = std::shared_ptr<LoginRspData>();
}

void Es9LoginManager::handleOnRspLogin(
        ITapTrade::TAPIINT32 errorCode
        , const std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo>& login_rsp_info) {
    if (errorCode == ITapTrade::TAPIERROR_SUCCEED) {
        CHECK(login_rsp_info);
        handleLoginSuccess(login_rsp_info);
    } else {
        handleLoginFailed(errorCode);
    }
}

void Es9LoginManager::sendQryAccount() {
    int ret = 0;
    do {
        std::shared_ptr<Es9QryAccountHandler> qry_account_handler(new Es9QryAccountHandler(
                Es9QryAccountHandler::CallbackFunction(std::bind(&Es9LoginManager::handleQryAccountFinished, this, std::placeholders::_1))));
        qry_account_handler->setCallbackThread(working_thread_);
        ret = request_dispatcher_->sendRequest(qry_account_handler);
        if (ret != 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(300));
        }
    } while(ret != 0);
}

void Es9LoginManager::handleAPIReady() {
    APPLOG_INFO("handleAPIReady...");
    sendQryAccount();
}

void Es9LoginManager::handleQryAccountFinished(const std::shared_ptr<Es9QryAccountResp>& qry_account_resp) {
    APPLOG_INFO("handleQryAccountFinished errorCode={}, errorMsg={}", qry_account_resp->getErrorCode()
            , qry_account_resp->getUtf8ErrorMsg());
    if (qry_account_resp->getErrorCode() != ITapTrade::TAPIERROR_SUCCEED) {
        sendQryAccount();
        return ;
    }

    for (const auto& account_info : qry_account_resp->account_infos) {
        APPLOG_INFO("AccountNo={}, AccountType={}, AccountState={}, AccountTradeRight={}, CommodityGroupNo={}"
                , account_info.AccountNo, account_info.AccountType, account_info.AccountState
                , account_info.AccountTradeRight, account_info.CommodityGroupNo);
    }

    std::shared_ptr<LoginRspData> tmp_login_rsp_data(new LoginRspData());
    CHECK(login_rsp_info_);
    tmp_login_rsp_data->login_rsp_info = *login_rsp_info_;
    tmp_login_rsp_data->account_infos = qry_account_resp->account_infos;

    // 原子交换数据
    login_rsp_data_ = tmp_login_rsp_data;

    std::vector<std::shared_ptr<IEs9LoginListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onLoginFinished(login_rsp_data_);
    }
}

void Es9LoginManager::handleLoginFailed(int api_ret_code) {
    std::string api_error_msg = es9ErrorMsg(api_ret_code);
    APPLOG_INFO("handleLoginFailed api_ret_code={}, api_error_msg={}", api_ret_code, api_error_msg);

    HostingTradeAccountAPI::InvalidDescription description;
    description.api_ret_code = api_ret_code;
    if (api_ret_code == ITapTrade::TAPIERROR_LOGIN_PASS) {
        description.invalid_reason = "用户名密码错误";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == ITapTrade::TAPIERROR_LOGIN_USER){
        description.invalid_reason = "登陆用户不存在";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == ITapTrade::TAPIERROR_LOGIN_FORCE) {
        description.invalid_reason = "需要强制修改密码";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == ITapTrade::TAPIERROR_LOGIN_STATE) {
        description.invalid_reason = "登录状态禁止登陆";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    } else if (api_ret_code == ITapTrade::TAPIERROR_LOGIN_DDA) {
        description.invalid_reason = "登录需要验证码";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    } else {
        description.invalid_reason = "登陆异常," + api_error_msg;
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    }
    HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
}

void Es9LoginManager::handleLoginSuccess(const std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo>& login_rsp_info) {
    APPLOG_INFO("handleLoginSuccess, UserNo={}, UserType={}, ReservedInfo={}"
            ", TradeDate={}, LastSettleTime={}, StartTime={}, InitTime={}"
            , login_rsp_info->UserNo, login_rsp_info->UserType
            , login_rsp_info->ReservedInfo
            , login_rsp_info->TradeDate, login_rsp_info->LastSettleTime
            , login_rsp_info->StartTime, login_rsp_info->InitTime);

    HostingTradeAccountAPI::setTradeAccountActive(trade_account_->tradeAccountId);
    login_rsp_info_ = login_rsp_info;
}

