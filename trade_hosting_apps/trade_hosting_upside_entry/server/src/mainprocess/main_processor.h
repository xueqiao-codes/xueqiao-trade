/*
 * main_processor.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_MAINPROCESS_MAIN_PROCESSOR_H_
#define SRC_MAINPROCESS_MAIN_PROCESSOR_H_

#include "message_bus/interface/message_consumer.h"
#include "subprocess_manager.h"

#include "trade_hosting_basic_events_types.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class MainProcessor: public soldier::message_bus::IMessageConsumer
	, public ISubProcessListener{
public:
	MainProcessor();
	virtual ~MainProcessor();

	virtual StartUpMode onStartUp();

	virtual ConsumeResult onConsumeMessage(
	            const std::string& topic
	            , const std::string& message);

	ConsumeResult onInit();

	IMessageConsumer::ConsumeResult
		onHandleTradeAccountEvent(const xueqiao::trade::hosting::events::TradeAccountEvent& event);
	void doTradeAccountStateAction(const std::shared_ptr<HostingTradeAccount>& trade_account);

	virtual void onStartSubProcessBegin(int64_t trade_account_id);
	virtual void onStartSubProcessFinished(int64_t trade_account_id, int pid);

	virtual void onStopSubProcessBegin(int64_t trade_account_id, int pid);
	virtual void onStopSubProcessFinished(int64_t trade_account_id);

	virtual void onRestartSubProcessBegin(int64_t trade_account_id);
	virtual void onRestartSubProcessFinished(int64_t trade_account_id, int pid);

	virtual void onSubProcessExited(int64_t trade_account_id);
};

} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_MAINPROCESS_MAIN_PROCESSOR_H_ */
