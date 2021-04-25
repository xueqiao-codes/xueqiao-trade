/*
 * hosting_message_graph.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include "hosting_message_graph.h"

#include <memory>
#include <mutex>
#include "base/code_defense.h"

using namespace soldier::message_bus;
using namespace xueqiao::trade::hosting::framework;

static std::unique_ptr<HostingMessageGraph> s_instance;

const std::shared_ptr<MessageGraphHolder>& HostingMessageGraph::messageGraphHolder() {
	return Global().holder_;
}

HostingMessageGraph& HostingMessageGraph::Global() {
	if (!s_instance) {
		static std::mutex lock;
		lock.lock();
		if (!s_instance) {
			s_instance.reset(new HostingMessageGraph());
		}
		lock.unlock();
	}
	return *s_instance;
}

HostingMessageGraph::HostingMessageGraph() {
	holder_ = MessageGraphHolder::create("/data/configs/qconf/xueqiao/trade/hosting/message_graph.json");
	CHECK(holder_);
}


