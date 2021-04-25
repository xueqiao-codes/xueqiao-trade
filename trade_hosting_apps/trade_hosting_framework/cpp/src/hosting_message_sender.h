/*
 * hosting_message_sender.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_COMMON_HOSTING_MESSAGE_SENDER_H_
#define SRC_COMMON_HOSTING_MESSAGE_SENDER_H_

#include <memory>
#include <boost/shared_ptr.hpp>
#include "message_bus/interface/message_agent.h"
#include "thrift/protocol/TCompactProtocol.h"
#include "thrift/protocol/TDebugProtocol.h"
#include "thrift/transport/TBufferTransports.h"


namespace xueqiao {
namespace trade {
namespace hosting {
namespace framework {

class EmptyMessageConsumer : public soldier::message_bus::IMessageConsumer {
public:
	virtual soldier::message_bus::IMessageConsumer::StartUpMode onStartUp() {
		return soldier::message_bus::IMessageConsumer::StartUpMode::CLEAR_QUEUE_INIT;
	}

	virtual soldier::message_bus::IMessageConsumer::ConsumeResult onConsumeMessage(
	            const std::string& topic
	            , const std::string& message) {
		return soldier::message_bus::IMessageConsumer::ConsumeResult::CONSUME_OK;
	}
};

class HostingMessageSender {
public:
	static HostingMessageSender& Global();
	~HostingMessageSender() = default;

	inline soldier::message_bus::MessageAgent& Agent() {
		return *message_agent_;
	}

	template<class TBase>
	void constructTMessage(soldier::message_bus::TMessage& t_msg
			, const std::string& msgStructType
			, const TBase& msgStruct) {
		if (!write_buffer_) {
			write_buffer_.reset(new ::apache::thrift::transport::TMemoryBuffer(1024));
		} else {
		    write_buffer_->resetBuffer();
		}

		apache::thrift::protocol::TCompactProtocolT<apache::thrift::transport::TMemoryBuffer>
			protocol(write_buffer_);
		msgStruct.write(&protocol);

		uint8_t* buf = NULL;
		uint32_t size = 0;
		write_buffer_->getBuffer(&buf, &size);

		if (t_msg.from.empty()) {
			t_msg.from = "hosting_message_sendor";
		}
		t_msg.topic = msgStructType;
		t_msg.message_data = buf;
		t_msg.message_length = size;
	}

	template<class TBase>
	void sendTMessage(const std::string& msgStructType, const TBase& msgStruct) {
	    soldier::message_bus::TMessage t_msg;
	    constructTMessage<TBase>(t_msg, msgStructType, msgStruct);
	    message_agent_->sendMessageDirectly(t_msg);
	}

private:
	HostingMessageSender();
	static thread_local boost::shared_ptr<apache::thrift::transport::TMemoryBuffer> write_buffer_;
	std::shared_ptr<soldier::message_bus::MessageAgent> message_agent_;
};


} // end namespace framework
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#define SEND_MESSAGE_BEGIN(msg_struct_name, msg_name) \
    std::string msg_name##_topic(#msg_struct_name); \
    msg_struct_name msg_name;


#define SEND_MESSAGE_END(msg_name) \
    APPLOG_INFO("sendTMessage {}", ::apache::thrift::ThriftDebugString(msg_name)); \
    ::xueqiao::trade::hosting::framework::HostingMessageSender::Global().sendTMessage(msg_name##_topic, msg_name);

#endif /* SRC_COMMON_HOSTING_MESSAGE_SENDER_H_ */
