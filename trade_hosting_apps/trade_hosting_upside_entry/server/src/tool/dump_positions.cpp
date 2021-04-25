/*
 * dump_positions.cpp
 *
 *  Created on: 2018年3月28日
 *      Author: wangli
 */
#include <boost/lexical_cast.hpp>
#include <iostream>
#include "thrift/protocol/TDebugProtocol.h"
#include "trade_hosting_upside_entry_stub.h"

using namespace apache::thrift;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::upside::position;

int main(int argc, char* argv[]) {
    if (argc < 2) {
        std::cout << "please input trade account id" << std::endl;
        return -1;
    }

    int64_t trade_account_Id = boost::lexical_cast<int64_t>(argv[1]);
    TradeHostingUpsideEntryStub stub;
    std::string process_socket_file;
    process_socket_file.append("/data/trade_hosting_upside_entry/run/")
                       .append(boost::lexical_cast<std::string>(trade_account_Id))
                       .append(".sock");
    stub.setSocketFile(process_socket_file);

    std::vector<PositionSummary> position_summaries;
    try {
        STUB_SYNC_INVOKE(stub, dumpPositionSummaries, position_summaries);
    } catch (TException& e) {
        std::cerr << "call failed, " << e.what() << std::endl;
        return -1;
    }

    std::cout << "======================POSITION SUMMARY BEGIN==========================\n";
    for (auto position_summary : position_summaries) {
        std::cout << ThriftDebugString(position_summary) << std::endl;
    }
    std::cout << "======================POSITION SUMMARY END============================";
    std::cout << std::endl;
    return 0;
}
