/*
 * hosting_message_sender.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include <mutex>
#include "base/code_defense.h"
#include "hosting_message_graph.h"
#include "hosting_message_sender.h"

using namespace soldier::message_bus;
using namespace xueqiao::trade::hosting::framework;

std::unique_ptr<HostingMessageSender> s_instance;

thread_local boost::shared_ptr<apache::thrift::transport::TMemoryBuffer> HostingMessageSender::write_buffer_;

HostingMessageSender& HostingMessageSender::Global() {
	if (!s_instance) {
		static std::mutex lock;
		lock.lock();
		if (!s_instance) {
			s_instance.reset(new HostingMessageSender());
		}
		lock.unlock();
	}
	return *s_instance;
}

HostingMessageSender::HostingMessageSender() {
	message_agent_ = MessageAgent::create("empty"
			, std::shared_ptr<IMessageConsumer>(new EmptyMessageConsumer())
			, HostingMessageGraph::messageGraphHolder());
	CHECK(message_agent_);
}
