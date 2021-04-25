/*
 * ctp_login_manager.cpp
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#include "ctp_login_manager.h"

#include <boost/lexical_cast.hpp>
#include "base/app_log.h"
#include "effective_reporter.h"
#include "trade_hosting_storage_api.h"
#include "trade_hosting_basic_errors_types.h"
#include "thrift/protocol/TDebugProtocol.h"

#define CTP_INVALID_LOGIN 3
#define CTP_USER_NOT_ACTIVE 4
#define CTP_USER_NOT_FOUND 11
#define CTP_BROKER_NOT_FOUND 12
#define CTP_INVALID_INVESTORIDORPASSWORD 48
#define CTP_INVALID_LOGIN_IPADDRESS 49
#define CTP_AUTH_FAILED	63
#define CTP_NOT_AUTHENT	64
#define CTP_API_UNSUPPORTED_VERSION 3039
#define	CTP_API_INVALID_KEY	3040


using namespace apache::thrift;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;


CTPLoginManager::CTPLoginManager(
        const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher
        , const std::shared_ptr<HostingTradeAccount>& trade_account
        , const std::string& broker_id_info
		, const std::string& app_id_info
		, const std::string& auth_code_info)
    : dispatcher_(dispatcher)
      , trade_account_(trade_account)
      , broker_id_info_(broker_id_info)
	  , app_id_info_(app_id_info)
	  , auth_code_info_(auth_code_info){
    working_thread_.reset(new soldier::base::TaskThread());
}

const std::shared_ptr<HostingTradeAccount>& CTPLoginManager::getTradeAccount() {
    return trade_account_;
}

std::shared_ptr<CThostFtdcRspUserLoginField> CTPLoginManager::getLoginRsp() {
    return login_rsp_;
}

void CTPLoginManager::allocOrderRef(::xueqiao::trade::hosting::HostingExecOrderRef& _return) {
    SyncCall sync_call;
    ErrorInfo err;
    working_thread_->postTask(&CTPLoginManager::handleAllocOrderRef, this, &sync_call, &_return, &err);
    sync_call.wait();

    APPLOG_INFO("allocOrderRef _return={}, err.errorCode={}, err.errorMsg={}"
            , ThriftDebugString(_return), err.errorCode, err.errorMsg);
    if (err.errorCode != 0) {
        throw err;
    }
}

void CTPLoginManager::handleAllocOrderRef(
        SyncCall* sync_call
        , HostingExecOrderRef* order_ref
        , ErrorInfo* err) {
    AutoSyncCallNotify auto_notify(*sync_call);
    if (!login_rsp_) {
        err->__set_errorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID);
        err->__set_errorMsg("account is invalid");
        return ;
    }

    CTPOrderRef ctp_order_ref;
    ctp_order_ref.__set_frontID(login_rsp_->FrontID);
    ctp_order_ref.__set_sessionID(login_rsp_->SessionID);
    ctp_order_ref.__set_orderRef(boost::lexical_cast<std::string>(++max_order_ref_));
    order_ref->__set_ctpRef(ctp_order_ref);
}

void CTPLoginManager::OnFrontConnected() {
    working_thread_->postTask(&CTPLoginManager::handleFrontConnected, this);
}

void CTPLoginManager::OnFrontDisconnected(int nReason) {
    working_thread_->postTask(&CTPLoginManager::handleFrontDisconnected, this, nReason);
}

void CTPLoginManager::handleFrontConnected() {
	//穿透式监管，改成先授权认证，授权认证通过之后，再登录
	authenticate_rsp_ = std::shared_ptr<CThostFtdcRspAuthenticateField>();

	if (authenticate_handler_) {
		dispatcher_->cancel(authenticate_handler_->getRequestId());
	}

	authenticate_handler_.reset(new CTPAuthenticateHandler(
			CTPAuthenticateHandler::CallbackFunction(std::bind(&CTPLoginManager::handleAuthenticateResp, this, std::placeholders::_1))
			, trade_account_
			, broker_id_info_
			, app_id_info_
			, auth_code_info_));
	authenticate_handler_->setCallbackThread(working_thread_);

	dispatcher_->sendRequest(authenticate_handler_);
}

void CTPLoginManager::handleFrontDisconnected(int nReason) {
    login_rsp_ = std::shared_ptr<CThostFtdcRspUserLoginField>();
}

void CTPLoginManager::handleLoginResp(const std::shared_ptr<CTPLoginResp>& login_rsp) {
    APPLOG_INFO("handleLoginResp trade_account_id={}, timeout={}, ErrorID={}, ErrorMSG={}"
                , trade_account_->tradeAccountId
                , login_rsp->hasTimeOut()
                , login_rsp->getErrorCode()
                , login_rsp->getUtf8ErrorMsg());

    login_handler_.reset();
    if (login_rsp->hasTimeOut()) {
        handleLoginTimeOut();
        return ;
    }

    if (login_rsp->getErrorCode() == 0) {
        APPLOG_INFO("handleLoginResp TradingDay={}, LoginTime={}, BrokerID={}, UserID={}, SystemName={}"
                ", FrontID={}, SessionID={}, MaxOrderRef={}"
                , login_rsp->login_rsp_field->TradingDay, login_rsp->login_rsp_field->LoginTime
                , login_rsp->login_rsp_field->BrokerID, login_rsp->login_rsp_field->UserID
                , login_rsp->login_rsp_field->SystemName, login_rsp->login_rsp_field->FrontID
                , login_rsp->login_rsp_field->SessionID, login_rsp->login_rsp_field->MaxOrderRef);

        login_rsp_ = login_rsp->login_rsp_field;
        max_order_ref_ = boost::lexical_cast<int32_t>(login_rsp_->MaxOrderRef);
        handleLoginSuccess(login_rsp_);
    } else {
        handleLoginFailed(login_rsp->getErrorCode());
    }
}

void CTPLoginManager::handleLoginTimeOut() {
    HostingTradeAccountAPI::InvalidDescription description;
    description.invalid_reason = "登录超时";
    description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_CONNECTING;
    HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
}

void CTPLoginManager::handleLoginSuccess(const std::shared_ptr<CThostFtdcRspUserLoginField>& login_rsp) {
    HostingTradeAccountAPI::setTradeAccountActive(trade_account_->tradeAccountId);

    APPLOG_INFO("handleLoginSuccess SHFETime={}, DCETime={}, CZCETime={}, FFEXTime={}, INETime={}, ServerTimeStampMs={}"
            , login_rsp->SHFETime, login_rsp->DCETime, login_rsp->CZCETime
            , login_rsp->FFEXTime, login_rsp->INETime
            , soldier::base::NowInMilliSeconds());

    std::vector<std::shared_ptr<ILoginListener>> listeners;
    getListeners(listeners);
    for (auto listener : listeners) {
        listener->onLoginSuccess(login_rsp_);
    }
}

void CTPLoginManager::handleLoginFailed(int api_ret_code) {
    APPLOG_INFO("handleLoginFailed api_ret_code={}", api_ret_code);

    HostingTradeAccountAPI::InvalidDescription description;
    description.api_ret_code = api_ret_code;
    if (api_ret_code == CTP_INVALID_LOGIN || api_ret_code == CTP_INVALID_INVESTORIDORPASSWORD) {
        description.invalid_reason = "用户名密码错误";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == CTP_USER_NOT_FOUND){
        description.invalid_reason = "登陆用户不存在";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_USER_PASSWD_ERROR;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == CTP_USER_NOT_ACTIVE) {
        description.invalid_reason = "登陆用户不活跃";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    } else if (api_ret_code == CTP_INVALID_LOGIN_IPADDRESS) {
        description.invalid_reason = "不合法的登陆地址";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    } else {
        description.invalid_reason = "登陆异常";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    }
    HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
}

void CTPLoginManager::handleAuthenticateResp(const std::shared_ptr<CTPAuthenticateResp>& authenticate_rsp) {
    APPLOG_INFO("handleAuthenticateResp trade_account_id={}, timeout={}, ErrorID={}, ErrorMSG={}, AppID={}"
                , trade_account_->tradeAccountId
                , authenticate_rsp->hasTimeOut()
                , authenticate_rsp->getErrorCode()
                , authenticate_rsp->getUtf8ErrorMsg()
				, authenticate_rsp->authenticate_rsp_field->AppID);

    authenticate_handler_.reset();
    if (authenticate_rsp->hasTimeOut()) {
        handleAuthenticateTimeOut();
        return ;
    }

    if (authenticate_rsp->getErrorCode() == 0) {
        APPLOG_INFO("handleAuthenticateResp AppType={}", authenticate_rsp->authenticate_rsp_field->AppType);

        authenticate_rsp_ = authenticate_rsp->authenticate_rsp_field;

        handleAuthenticateSuccess(authenticate_rsp_);
    } else {
        handleAuthenticateFailed(authenticate_rsp->getErrorCode());
    }
}

void CTPLoginManager::handleAuthenticateTimeOut() {
    HostingTradeAccountAPI::InvalidDescription description;
    description.invalid_reason = "API授权认证超时";
    description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_CONNECTING;
    HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
}

void CTPLoginManager::handleAuthenticateSuccess(const std::shared_ptr<CThostFtdcRspAuthenticateField>& authenticate_rsp) {
	//已经认证成功，可以登录
	login_rsp_ = std::shared_ptr<CThostFtdcRspUserLoginField>();

	if (login_handler_) {
		dispatcher_->cancel(login_handler_->getRequestId());
	}

	login_handler_.reset(
			new CTPLoginHandler(
					CTPLoginHandler::CallbackFunction(
							std::bind(&CTPLoginManager::handleLoginResp, this,
									std::placeholders::_1)), trade_account_,
					broker_id_info_));
	login_handler_->setCallbackThread(working_thread_);

	dispatcher_->sendRequest(login_handler_);
}

void CTPLoginManager::handleAuthenticateFailed(int api_ret_code) {
    APPLOG_INFO("handleAuthenticateFailed api_ret_code={}", api_ret_code);

    HostingTradeAccountAPI::InvalidDescription description;
    description.api_ret_code = api_ret_code;
    if (api_ret_code == CTP_AUTH_FAILED) {
        description.invalid_reason = "CTP:客户端认证失败";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_AUTH;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == CTP_NOT_AUTHENT){
        description.invalid_reason = "CTP:客户端未认证";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_AUTH;
        EffectiveReporter::Global().setAccountInfoInvalid(api_ret_code);
    } else if (api_ret_code == CTP_API_UNSUPPORTED_VERSION) {
        description.invalid_reason = "CTP:不支持该API版本";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_AUTH;
    } else if (api_ret_code == CTP_API_INVALID_KEY) {
        description.invalid_reason = "CTP:无效的API KEY";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_AUTH;
    } else {
        description.invalid_reason = "API授权认证异常";
        description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_BROKER_REFUSED;
    }
    HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
}
