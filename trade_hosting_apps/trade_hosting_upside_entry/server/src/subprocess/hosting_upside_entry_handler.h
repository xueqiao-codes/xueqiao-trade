/*
 * hosting_upside_entry_handler.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_SUBPROCESS_HOSTING_UPSIDE_ENTRY_HANDLER_H_
#define SRC_SUBPROCESS_HOSTING_UPSIDE_ENTRY_HANDLER_H_

#include <atomic>
#include "base/app_log.h"
#include "base/time_helper.h"
#include "broker_types.h"
#include "TradeHostingUpsideEntry.h"
#include "trade_hosting_basic_errors_types.h"
#include "errorinfo_helper.h"
#include "effective_reporter.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class HostingUpsideEntryHandler : public TradeHostingUpsideEntryIf {
public:
	virtual ~HostingUpsideEntryHandler() = default;

	/**
	 *  进行初始化操作
	 */
	virtual bool init(const std::shared_ptr<BrokerAccessEntry>& broker_access_entry) = 0;
	virtual void getSubProcessInfos(
			std::vector<TSubProcessInfo> & _return, const  ::platform::comm::PlatformArgs& platformArgs) {
		::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwNotSupportedError("in subprocess");
	}

	virtual void restartSubProcess(const ::platform::comm::PlatformArgs& platformArgs, const int64_t trade_account_id) {
		::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwNotSupportedError("in subprocess");
	}

	virtual int64_t getLastUpsideEffectiveTimestamp(const ::platform::comm::PlatformArgs& platformArgs) {
	    return EffectiveReporter::Global().getLastEffetiveTimestamp();
	}

	virtual void sendUpsideHeartBeat(const ::platform::comm::PlatformArgs& platformArgs) {
	    apiQryBlockCheck();
	    if (EffectiveReporter::Global().isAccountInfoInvalid()) {
	        APPLOG_INFO("sendUpsideHeartBeat account info invalid, heartbeat success!");
	        EffectiveReporter::Global().setEffective();
	        return ;
	    }
	    detectUpsideEffective(platformArgs);
	}

	virtual void getPositionInfos(std::vector<TPositionInfo> & _return
	            , const  ::platform::comm::PlatformArgs& platformArgs) {
	    ::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwNotSupportedError("Not Implemented");
	}

	virtual void detectUpsideEffective(const ::platform::comm::PlatformArgs& platformArgs) = 0;

	void blockApiQry() {
	    api_block_count_ += 1;
	}

	void unBlockApiQry() {
	    api_block_count_ -= 1;
	}

protected:
	void apiQryBlockCheck() {
	    if (api_block_count_ > 0) {
	        ::xueqiao::trade::hosting::storage::api::ErrorInfoHelper::throwError(
	                ::xueqiao::trade::hosting::TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN
	                , "api qry is blocked");
	    }
	}

private:
	std::atomic_int api_block_count_ = {0};
};

class AutoApiQryBlocking {
private:
    HostingUpsideEntryHandler* handler_;

public:
    AutoApiQryBlocking(HostingUpsideEntryHandler* handler)
        : handler_(handler) {
        handler->blockApiQry();
    }

    ~AutoApiQryBlocking() {
        handler_->unBlockApiQry();
    }
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_HOSTING_UPSIDE_ENTRY_HANDLER_H_ */
