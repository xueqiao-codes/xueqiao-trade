/*
 * es9_upside_entry_handler.h
 *
 *  Created on: 2018年4月12日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_UPSIDE_ENTRY_HANDLER_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_UPSIDE_ENTRY_HANDLER_H_

#include <atomic>
#include "hosting_upside_entry_handler.h"
#include "es9_request_dispatcher.h"
#include "es9_login_manager.h"
#include "es9_data_manager.h"
#include "es9_deal_manager.h"
#include "es9_qry_position_handler.h"
#include "es9_logger_cleaner.h"
#include "es9_rate_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class Es9UpsideEntryHandler : public HostingUpsideEntryHandler {
public:
    Es9UpsideEntryHandler(const std::shared_ptr<HostingTradeAccount> trade_account);
    virtual ~Es9UpsideEntryHandler();

    virtual bool init(const std::shared_ptr<BrokerAccessEntry>& broker_access_entry);
    virtual void allocOrderRef(::xueqiao::trade::hosting::HostingExecOrderRef& _return
            , const  ::platform::comm::PlatformArgs& platformArgs);
    virtual void orderInsert(const ::platform::comm::PlatformArgs& platformArgs
            , const ::xueqiao::trade::hosting::HostingExecOrder& insertOrder);
    virtual void orderDelete(const ::platform::comm::PlatformArgs& platformArgs
            , const ::xueqiao::trade::hosting::HostingExecOrder& deleteOrder);

    virtual void syncOrderState(const ::platform::comm::PlatformArgs& platformArgs
            , const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder);
    virtual void syncOrderTrades(const  ::platform::comm::PlatformArgs& platformArgs
            , const  ::xueqiao::trade::hosting::HostingExecOrder& syncOrder);

    virtual void syncOrderStateBatch(const ::platform::comm::PlatformArgs& platformArgs
            , const TSyncOrderStateBatchReq& batchReq);

    virtual void detectUpsideEffective(const ::platform::comm::PlatformArgs& platformArgs);

    virtual void dumpPositionSummaries(std::vector<::xueqiao::trade::hosting::upside::position::PositionSummary> & _return
            , const  ::platform::comm::PlatformArgs& platformArgs);

    virtual void getFunds(std::vector<TFund> & _return
            , const  ::platform::comm::PlatformArgs& platformArgs);

    virtual void getSettlementInfo(TSettlementInfo & _return
            , const  ::platform::comm::PlatformArgs& platformArgs
            , const std::string& settlementDate);

    virtual void getNetPositionSummaries(std::vector<TNetPositionSummary> & _return
            , const  ::platform::comm::PlatformArgs& platformArgs);

    virtual void getPositionRateDetails(TPositionRateDetails& _return
            , const  ::platform::comm::PlatformArgs& platformArgs);

private:
    void onEs9PositionCallback(soldier::base::SyncCall* sync_call
            , std::vector<TNetPositionSummary>* _return
            , ::platform::comm::ErrorInfo* error_info
            , const std::shared_ptr<Es9QryPositionResp>& rsp);

    std::shared_ptr<HostingTradeAccount> trade_account_;
    std::shared_ptr<es9ext::framework::Es9RequestDispatcher> request_dispatcher_;

    std::shared_ptr<Es9LoginManager> login_manager_;
    std::shared_ptr<Es9DataManager> data_manager_;
    std::shared_ptr<Es9DealManager> deal_manager_;

    std::shared_ptr<Es9LoggerCleaner> logger_cleaner_;

    std::shared_ptr<Es9RateManager> rate_manager_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_UPSIDE_ENTRY_HANDLER_H_ */
