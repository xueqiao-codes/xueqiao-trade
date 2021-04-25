/*
 * ctp_upside_entry_handler.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_UPSIDE_ENTRY_HANDLER_H_
#define SRC_SUBPROCESS_CTP_CTP_UPSIDE_ENTRY_HANDLER_H_

#include <atomic>
#include <memory>
#include "hosting_upside_entry_handler.h"
#include "ThostFtdcTraderApi.h"
#include "ctp_request_dispatcher.h"
#include "order_mapping_manager.h"
#include "contract_mapping.h"
#include "ctp_login_manager.h"
#include "ctp_data_manager.h"
#include "ctp_deal_manager.h"
#include "ctp_position_manager.h"
#include "ctp_rate_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CTPUpsideEntryHandler : public HostingUpsideEntryHandler {
public:
	CTPUpsideEntryHandler(const std::shared_ptr<HostingTradeAccount> trade_account);
	virtual ~CTPUpsideEntryHandler();

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
	void onCtpPositionsCallback(soldier::base::SyncCall* sync_call
	        , std::vector<TNetPositionSummary>* _return
	        , ::platform::comm::ErrorInfo* error_info
	        , const std::shared_ptr<QryPositionResp>& rsp);

	std::shared_ptr<HostingTradeAccount> trade_account_;
	std::shared_ptr<ctpext::framework::CTPRequestDispatcher> dispatcher_;

	std::shared_ptr<CTPLoginManager> login_manager_;
	std::shared_ptr<CTPDataManager> data_manager_;
	std::shared_ptr<CTPPositionManager> position_manager_;
	std::shared_ptr<CTPDealManager> deal_manager_;
	std::shared_ptr<CTPRateManager> rate_manager_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_SUBPROCESS_CTP_CTP_UPSIDE_ENTRY_HANDLER_H_ */
