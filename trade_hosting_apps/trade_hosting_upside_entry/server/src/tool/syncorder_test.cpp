/*
 * syncorder_test.cpp
 *
 *  Created on: 2018年2月8日
 *      Author: wangli
 */

#include "trade_hosting_upside_entry_stub.h"

#include <chrono>
#include <iostream>
#include <boost/lexical_cast.hpp>
#include "errorinfo_helper.h"
#include "test_define.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

void syncOneTest(TradeHostingUpsideEntryStub& stub) {
    HostingExecOrder sync_order;

    sync_order.__set_execOrderId(1);

    HostingExecOrderTradeAccountSummary trade_account_summary;
    trade_account_summary.__set_tradeAccountId(TEST_TRADE_ACCOUNT_ID);
    trade_account_summary.__set_techPlatform(TEST_BROKER_TECH_PLATFORM);
    trade_account_summary.__set_brokerId(TEST_BROKER_ID);
    sync_order.__set_accountSummary(trade_account_summary);

    HostingExecOrderInputExt orderInputExt;
    CTPOrderInputExt ctpInputExt;
    CTPContractSummary ctpContractSummary;
    ctpContractSummary.__set_ctpExchangeCode("SHFE");
    ctpContractSummary.__set_ctpContractCode("1806");
    ctpContractSummary.__set_ctpCommodityCode("cu");
    ctpContractSummary.__set_ctpCommodityType(1);
    ctpInputExt.__set_contractSummary(ctpContractSummary);

    orderInputExt.__set_ctpInputExt(ctpInputExt);
    sync_order.__set_orderInputExt(orderInputExt);

    HostingExecOrderDealInfo deal_info;
    deal_info.__isset.dealId = true;
    deal_info.dealId.__isset.ctpDealId = true;
    //        deal_info.dealId.ctpDealId.__set_exchangeId("SHFE");
    //        deal_info.dealId.ctpDealId.__set_orderSysId("        3124");
    deal_info.__set_orderInsertDateTime("2018-02-06 18:47:59");

    sync_order.__set_dealInfo(deal_info);

    STUB_SYNC_INVOKE_ERRORINFO(stub, syncOrderState, sync_order);
}

void syncBatchTest(TradeHostingUpsideEntryStub& stub) {
    TSyncOrderStateBatchReq batch_req;

    HostingExecOrderTradeAccountSummary trade_account_summary;
    trade_account_summary.__set_tradeAccountId(TEST_TRADE_ACCOUNT_ID);
    trade_account_summary.__set_techPlatform(TEST_BROKER_TECH_PLATFORM);
    trade_account_summary.__set_brokerId(TEST_BROKER_ID);
    batch_req.__set_accountSummary(trade_account_summary);

    CTPContractSummary ctpContractSummary;
    ctpContractSummary.__set_ctpExchangeCode("SHFE");
    ctpContractSummary.__set_ctpContractCode("1806");
    ctpContractSummary.__set_ctpCommodityCode("au");
    ctpContractSummary.__set_ctpCommodityType(1);
    batch_req.__set_ctpContractSummary(ctpContractSummary);

    STUB_SYNC_INVOKE_ERRORINFO(stub, syncOrderStateBatch, batch_req);
}

void syncTradeTest(TradeHostingUpsideEntryStub& stub) {
    HostingExecOrder sync_order;

    sync_order.__set_execOrderId(100);

    HostingExecOrderTradeAccountSummary trade_account_summary;
    trade_account_summary.__set_tradeAccountId(TEST_TRADE_ACCOUNT_ID);
    trade_account_summary.__set_techPlatform(TEST_BROKER_TECH_PLATFORM);
    trade_account_summary.__set_brokerId(TEST_BROKER_ID);
    sync_order.__set_accountSummary(trade_account_summary);

    HostingExecOrderInputExt orderInputExt;
    CTPOrderInputExt ctpInputExt;
    CTPContractSummary ctpContractSummary;
    ctpContractSummary.__set_ctpExchangeCode("SHFE");
    ctpContractSummary.__set_ctpContractCode("1806");
    ctpContractSummary.__set_ctpCommodityCode("cu");
    ctpContractSummary.__set_ctpCommodityType(1);
    ctpInputExt.__set_contractSummary(ctpContractSummary);
    orderInputExt.__set_ctpInputExt(ctpInputExt);
    sync_order.__set_orderInputExt(orderInputExt);

    HostingExecOrderDealInfo deal_info;
    deal_info.__set_orderInsertDateTime("2018-02-09 16:08:15");
//    deal_info.__isset.dealId = true;
//    deal_info.dealId.__isset.ctpDealId = true;
//    deal_info.dealId.ctpDealId.__set_exchangeId("SHFE");
//    deal_info.dealId.ctpDealId.__set_orderSysId("        3124");
    sync_order.__set_dealInfo(deal_info);

    STUB_SYNC_INVOKE_ERRORINFO(stub, syncOrderTrades, sync_order);
}

int main(int argc, char* argv[]) {
    std::stringstream socket_file;
    socket_file << "/data/trade_hosting_upside_entry/run/"
            << boost::lexical_cast<std::string>(TEST_TRADE_ACCOUNT_ID) << ".sock";

    TradeHostingUpsideEntryStub stub;
    stub.setSocketFile(socket_file.str());

    try {
//        syncBatchTest(stub);
        syncTradeTest(stub);
    } catch (ErrorInfo& err) {
        std::cout << "error occurs, errorCode=" << err.errorCode
                << ", msg=" << err.errorMsg
                << std::endl;
    }
    return 0;
}


