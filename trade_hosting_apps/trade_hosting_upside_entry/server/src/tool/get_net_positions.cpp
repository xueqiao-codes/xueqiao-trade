/*
 * get_net_positions.cpp
 *
 *  Created on: 2018年8月14日
 *      Author: wangli
 */

#include <boost/lexical_cast.hpp>
#include <iostream>
#include "thrift/protocol/TDebugProtocol.h"
#include "trade_hosting_upside_entry_stub.h"

using namespace apache::thrift;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

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

    std::vector<TNetPositionSummary> net_positions;

    try {
        STUB_SYNC_INVOKE(stub, getNetPositionSummaries, net_positions);
    } catch (platform::comm::ErrorInfo& ei) {
        std::cerr << "call failed, errorCode=" << ei.errorCode
                  << ", errorMsg=" << ei.errorMsg << std::endl;
    } catch (TException& e) {
        std::cerr << "call failed, " << e.what() << std::endl;
        return -1;
    }

    std::cout << "======================NETPOSITION BEGIN==========================\n";
    for (auto& net_position : net_positions) {
        std::cout << ThriftDebugString(net_position) << std::endl;
    }
    std::cout << "======================NETPOSITION END============================";
    std::cout << std::endl;

    return 0;
}


