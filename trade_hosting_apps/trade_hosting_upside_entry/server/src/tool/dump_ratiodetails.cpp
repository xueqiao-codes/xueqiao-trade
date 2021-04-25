/*
 * dump_ratiodetails.cpp
 *
 *  Created on: 2019年4月8日
 *      Author: wangli
 */

#ifndef SRC_TOOL_DUMP_RATIODETAILS_CPP_
#define SRC_TOOL_DUMP_RATIODETAILS_CPP_

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

    TPositionRateDetails position_rate_details;

    try {
        STUB_SYNC_INVOKE(stub, getPositionRateDetails, position_rate_details);
    } catch (platform::comm::ErrorInfo& ei) {
        std::cerr << "call failed, errorCode=" << ei.errorCode
                << ", errorMsg=" << ei.errorMsg << std::endl;
    } catch (TException& e) {
        std::cerr << "call failed, " << e.what() << std::endl;
        return -1;
    }

    std::cout << ThriftDebugString(position_rate_details) << std::endl;

    return 0;
}



#endif /* SRC_TOOL_DUMP_RATIODETAILS_CPP_ */
