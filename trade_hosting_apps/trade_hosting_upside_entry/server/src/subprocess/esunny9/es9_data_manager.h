/*
 * es9_data_manager.h
 *
 *  Created on: 2018年4月18日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_DATA_MANAGER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_DATA_MANAGER_H_

#include "base/thread_pool.h"
#include "contract_mapping.h"
#include "es9_login_manager.h"
#include "es9_qry_all_orders_handler.h"
#include "es9_qry_account_fee_handler.h"
#include "es9_qry_account_margin_handler.h"
#include "es9_qry_commodity_handler.h"
#include "order_mapping_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class IEs9DataManagerListener {
public:
    virtual ~IEs9DataManagerListener() = default;

    virtual void onEs9DataManagerInitStart() {}

    virtual void onEs9QryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp) {}
    virtual void onEs9QryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp) {}

    virtual void onEs9QryOrdersFinished(const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp) {}
    virtual void onEs9DataManagerInitFinished() {}

protected:
    IEs9DataManagerListener() = default;
};


class Es9DataManager : public IEs9LoginListener, public soldier::base::ListenerContainer<IEs9DataManagerListener> {
public:
    Es9DataManager(const std::shared_ptr<es9ext::framework::Es9RequestDispatcher>& request_dispatcher
            , const std::shared_ptr<::xueqiao::trade::hosting::HostingTradeAccount>& trade_account);
    virtual ~Es9DataManager() = default;

    virtual void onLoginFinished(const std::shared_ptr<LoginRspData>& login_rsp_data);

    inline OrderMappingManager& getOrderMappingManager() {
        return order_mapping_manager_;
    }

    inline ::xueqiao::trade::hosting::storage::api::ContractMapping& getContractMapping() {
        return contract_mapping_;
    }

private:
    void sendQryCommodities();
    void onQryCommoditiesFinished(const std::shared_ptr<Es9QryCommodityResp>& commodity_resp);

    void sendQryAccountFee();
    void onQryAccountFeeFinished(const std::shared_ptr<Es9QryAccountFeeResp>& account_fee_resp);

    void sendQryAllOrders();
    void onQryAllOrdersFinished(const std::shared_ptr<Es9QryAllOrdersResp>& all_orders_resp);

    void notifyInitFinished();


    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;

    OrderMappingManager order_mapping_manager_;
    ::xueqiao::trade::hosting::storage::api::ContractMapping contract_mapping_;

    std::shared_ptr<soldier::base::TaskThread> init_thread_;

    std::shared_ptr<LoginRspData> login_rsp_data_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_DATA_MANAGER_H_ */
