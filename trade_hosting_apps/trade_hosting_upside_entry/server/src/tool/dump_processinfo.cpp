/*
 * dump_processinfo.cpp
 *
 *  Created on: 2018年2月3日
 *      Author: wangli
 */

#include <iostream>
#include "base/time_helper.h"
#include "trade_hosting_upside_entry_stub.h"

using namespace soldier::base;
using namespace apache::thrift;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

int main(int argc, char* argv[]) {
	std::vector<TSubProcessInfo> subprocess_infos;

	TradeHostingUpsideEntryStub stub;
	stub.setPeerAddr("127.0.0.1");

	try {
		STUB_SYNC_INVOKE(stub, getSubProcessInfos, subprocess_infos);
	} catch (TException& e) {
		std::cerr << "call failed, " << e.what() << std::endl;
		return -1;
	}

	std::cout << "======================SUBPROCESS INFOS BEGIN==========================\n";
	for (auto info : subprocess_infos) {
		std::cout << info.tradeAccountId << " -> " << info.pid << "\n";

		for (auto time_info : info.timeInfos) {
			std::cout << "  start:" << Timestamp2String(time_info.startTimestamp, "%Y-%m-%d %H:%M:%S");
			if (time_info.exitedTimestamp > 0) {
				std::cout << ", exited:" << Timestamp2String(time_info.exitedTimestamp, "%Y-%m-%d %H:%M:%S");
			}
			std::cout << "\n";
		}
	}
	std::cout << "======================SUBPROCESS INFOS END============================";
	std::cout << std::endl;
	return 0;
}

