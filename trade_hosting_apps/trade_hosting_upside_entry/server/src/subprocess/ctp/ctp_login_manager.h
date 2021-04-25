/*
 * ctp_login_manager.h
 *
 *  Created on: 2018年3月24日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_LOGIN_MANAGER_H_
#define SRC_SUBPROCESS_CTP_CTP_LOGIN_MANAGER_H_

#include <memory>
#include "base/thread_pool.h"
#include "base/listener_container.h"
#include "ThostFtdcTraderApi.h"
#include "ctp_request_dispatcher.h"
#include "ctp_login_handler.h"
#include "comm_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class ILoginListener {
public:
    virtual ~ILoginListener() = default;
    virtual void onLoginSuccess(const std::shared_ptr<CThostFtdcRspUserLoginField>& login_rsp) = 0;

protected:
    ILoginListener() = default;
};

/**
 *  登录管理器
 */
class CTPLoginManager : public CThostFtdcTraderSpi, public soldier::base::ListenerContainer<ILoginListener> {
public:
    CTPLoginManager(const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account
            , const std::string& broker_id_info);
    virtual ~CTPLoginManager() = default;

    virtual void OnFrontConnected();
    virtual void OnFrontDisconnected(int nReason);

    void allocOrderRef(::xueqiao::trade::hosting::HostingExecOrderRef& _return);

    const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& getTradeAccount();

    std::shared_ptr<CThostFtdcRspUserLoginField> getLoginRsp();

private:
    std::shared_ptr<soldier::base::TaskThread> working_thread_;

    void handleFrontConnected();
    void handleFrontDisconnected(int nReason);
    void handleLoginResp(const std::shared_ptr<CTPLoginResp>& login_rsp);
    void handleAllocOrderRef(soldier::base::SyncCall* sync_call
                , HostingExecOrderRef* order_ref
                , ::platform::comm::ErrorInfo* err);

    void handleLoginTimeOut();
    void handleLoginFailed(int api_ret_code);
    void handleLoginSuccess(const std::shared_ptr<CThostFtdcRspUserLoginField>& login_rsp);

    std::shared_ptr<ctpext::framework::CTPRequestDispatcher> dispatcher_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount> trade_account_;
    std::string broker_id_info_;

    std::shared_ptr<CTPLoginHandler> login_handler_;

    int32_t max_order_ref_ = 0;
    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_CTP_CTP_LOGIN_MANAGER_H_ */
