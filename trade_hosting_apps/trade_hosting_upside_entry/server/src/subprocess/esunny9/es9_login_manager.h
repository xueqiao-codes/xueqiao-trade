/*
 * es9_login_manager.h
 *
 *  Created on: 2018年4月13日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_LOGIN_MANAGER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_LOGIN_MANAGER_H_

#include <memory>
#include "base/thread_pool.h"
#include "base/listener_container.h"
#include "es9_request_dispatcher.h"
#include "es9_qry_account_handler.h"
#include "trade_hosting_basic_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

struct LoginRspData{
    ITapTrade::TapAPITradeLoginRspInfo login_rsp_info;
    std::vector<ITapTrade::TapAPIAccountInfo> account_infos;

    LoginRspData() {
        memset(&login_rsp_info, 0, sizeof(ITapTrade::TapAPITradeLoginRspInfo));
    }
};

class IEs9LoginListener {
public:
    virtual ~IEs9LoginListener() = default;

    virtual void onLoginFinished(const std::shared_ptr<LoginRspData>& login_rsp_data) = 0;

protected:
    IEs9LoginListener() = default;
};

class Es9LoginManager : public es9ext::framework::Es9TradeNotifyBase, public soldier::base::ListenerContainer<IEs9LoginListener> {
public:
    Es9LoginManager(
            const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account);
    virtual ~Es9LoginManager();

    void startLogin();

    virtual void OnRspLogin(ITapTrade::TAPIINT32 errorCode
                , const ITapTrade::TapAPITradeLoginRspInfo *loginRspInfo);
    virtual void OnAPIReady();
    virtual void OnDisconnect(ITapTrade::TAPIINT32 reasonCode);

    std::shared_ptr<LoginRspData> getLoginRspData() {
        return login_rsp_data_;
    }
    const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& getTradeAccount() {
        return trade_account_;
    }

private:
    void sendQryAccount();

    void handleStartLogin();
    void handleDisconnect(ITapTrade::TAPIINT32 reasonCode);

    void handleOnRspLogin(ITapTrade::TAPIINT32 errorCode
            , const std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo>& login_rsp_info);
    void handleAPIReady();
    void handleLoginFailed(int api_ret_code);
    void handleLoginSuccess(const std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo>& login_rsp_info);
    void handleQryAccountFinished(const std::shared_ptr<Es9QryAccountResp>& qry_account_resp);

    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;
    std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount> trade_account_;

    std::shared_ptr<soldier::base::TaskThread> working_thread_;

    std::shared_ptr<ITapTrade::TapAPITradeLoginRspInfo> login_rsp_info_;
    std::shared_ptr<LoginRspData> login_rsp_data_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_LOGIN_MANAGER_H_ */
