/*
 * upside_entry_mainprocess.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_MAINPROCESS_UPSIDE_ENTRY_MAINPROCESS_H_
#define SRC_MAINPROCESS_UPSIDE_ENTRY_MAINPROCESS_H_

#include <thread>
#include "message_bus/interface/message_agent.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class UpsideEntryMainProcess {
public:
	UpsideEntryMainProcess();
	~UpsideEntryMainProcess() = default;

	bool runLoop();

private:
	void serve();

	std::shared_ptr<soldier::message_bus::MessageAgent> mainprocess_message_agent_;
};



} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao



#endif /* SRC_MAINPROCESS_UPSIDE_ENTRY_MAINPROCESS_H_ */
