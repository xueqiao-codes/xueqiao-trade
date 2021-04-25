/*
 * get_settlement_info.cpp
 *
 *  Created on: 2018年8月11日
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
    if (argc < 3) {
        std::cout << "please input trade account id and settlementDate" << std::endl;
        return -1;
    }

    int64_t trade_account_Id = boost::lexical_cast<int64_t>(argv[1]);
    std::string settlement_date = argv[2];

    TradeHostingUpsideEntryStub stub;
    std::string process_socket_file;
    process_socket_file.append("/data/trade_hosting_upside_entry/run/")
                       .append(boost::lexical_cast<std::string>(trade_account_Id))
                       .append(".sock");
    stub.setSocketFile(process_socket_file);

    TSettlementInfo settlement_info;
    try {
        STUB_SYNC_INVOKE(stub, getSettlementInfo, settlement_info, settlement_date);
    } catch (platform::comm::ErrorInfo& ei) {
        std::cerr << "call failed, errorCode=" << ei.errorCode
                << ", errorMsg=" << ei.errorMsg << std::endl;
    } catch (TException& e) {
        std::cerr << "call failed, " << e.what() << std::endl;
           return -1;
    }

    std::cout << "======================SETTLEMENTINFO BEGIN==========================\n";

    std::cout << settlement_info.settlementDate<< std::endl
              << settlement_info.settlementContent
              << std::endl;

    std::cout << "======================SETTLEMENTINFO END============================";
    std::cout << std::endl;

    return 0;
}



