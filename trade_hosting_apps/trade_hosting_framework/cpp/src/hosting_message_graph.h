/*
 * hosting_message_graph.h
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#ifndef SRC_COMMON_HOSTING_MESSAGE_GRAPH_H_
#define SRC_COMMON_HOSTING_MESSAGE_GRAPH_H_

#include "message_bus/interface/message_graph_holder.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace framework {


class HostingMessageGraph {
public:
	~HostingMessageGraph() = default;

	static const std::shared_ptr<soldier::message_bus::MessageGraphHolder>&  messageGraphHolder();

private:
	static HostingMessageGraph& Global();

	HostingMessageGraph();

	std::shared_ptr<soldier::message_bus::MessageGraphHolder> holder_;
};



} // end namespace framework
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao




#endif /* SRC_COMMON_HOSTING_MESSAGE_GRAPH_H_ */
