/*
 * addorder_test.cpp
 *
 *  Created on: 2018年2月6日
 *      Author: wangli
 */

#include "trade_hosting_upside_entry_stub.h"

#include <boost/format.hpp>
#include <chrono>
#include <iostream>
#include <boost/lexical_cast.hpp>
#include "errorinfo_helper.h"
#include "test_define.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;

void addOrderTest(TradeHostingUpsideEntryStub& stub) {
    HostingExecOrderRef order_ref;
    STUB_SYNC_INVOKE_ERRORINFO(stub, allocOrderRef, order_ref);

    HostingExecOrder request_order;
    request_order.__set_upsideOrderRef(order_ref);
    request_order.__set_execOrderId(
            std::chrono::duration_cast<std::chrono::nanoseconds>(std::chrono::system_clock::now().time_since_epoch()).count());
    request_order.__set_subUserId(2);

    HostingExecOrderDetail order_detail;
    order_detail.__set_orderCreatorType(HostingExecOrderCreatorType::ORDER_ARTIFICAL);
    order_detail.__set_orderType(HostingExecOrderType::ORDER_LIMIT_PRICE);
    order_detail.__set_tradeDirection(HostingExecOrderTradeDirection::ORDER_BUY);
    order_detail.__set_limitPrice(52150);
    order_detail.__set_quantity(1);
    order_detail.__set_orderMode(HostingExecOrderMode::ORDER_GFD);
    request_order.__set_orderDetail(order_detail);

    HostingExecOrderTradeAccountSummary trade_account_summary;
    trade_account_summary.__set_tradeAccountId(TEST_TRADE_ACCOUNT_ID);
    trade_account_summary.__set_techPlatform(TEST_BROKER_TECH_PLATFORM);
    trade_account_summary.__set_brokerId(TEST_BROKER_ID);
    request_order.__set_accountSummary(trade_account_summary);

    HostingExecOrderInputExt orderInputExt;
    CTPOrderInputExt ctpInputExt;
    ctpInputExt.__set_minVolume(1);
    ctpInputExt.__set_combOffsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_OPEN);
    //      ctpInputExt.__set_combOffsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_CLOSETODAY);

    CTPContractSummary ctpContractSummary;
    ctpContractSummary.__set_ctpExchangeCode("SHFE");
    ctpContractSummary.__set_ctpContractCode("1806");
    ctpContractSummary.__set_ctpCommodityCode("cu");
    ctpContractSummary.__set_ctpCommodityType(1);
    ctpInputExt.__set_contractSummary(ctpContractSummary);

    orderInputExt.__set_ctpInputExt(ctpInputExt);
    request_order.__set_orderInputExt(orderInputExt);

    STUB_SYNC_INVOKE_ERRORINFO(stub, orderInsert, request_order);
}

void deleteOrderTest(TradeHostingUpsideEntryStub& stub) {
    HostingExecOrder delete_order;

    delete_order.__set_execOrderId(1000);

    HostingExecOrderRef order_ref;
    order_ref.__isset.ctpRef = true;
    order_ref.ctpRef.__set_frontID(1);
    order_ref.ctpRef.__set_sessionID(1267008061);
    order_ref.ctpRef.__set_orderRef("8");
    delete_order.__set_upsideOrderRef(order_ref);

    HostingExecOrderTradeAccountSummary trade_account_summary;
    trade_account_summary.__set_tradeAccountId(TEST_TRADE_ACCOUNT_ID);
    trade_account_summary.__set_techPlatform(TEST_BROKER_TECH_PLATFORM);
    trade_account_summary.__set_brokerId(TEST_BROKER_ID);
    delete_order.__set_accountSummary(trade_account_summary);

    HostingExecOrderInputExt orderInputExt;
    CTPOrderInputExt ctpInputExt;
//    ctpInputExt.__set_minVolume(1);
//        ctpInputExt.__set_combOffsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_OPEN);
        //      ctpInputExt.__set_combOffsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_CLOSETODAY);

    CTPContractSummary ctpContractSummary;
    ctpContractSummary.__set_ctpExchangeCode("SHFE");
    ctpContractSummary.__set_ctpContractCode("1806");
    ctpContractSummary.__set_ctpCommodityCode("cu");
    ctpContractSummary.__set_ctpCommodityType(1);
    ctpInputExt.__set_contractSummary(ctpContractSummary);

    orderInputExt.__set_ctpInputExt(ctpInputExt);
    delete_order.__set_orderInputExt(orderInputExt);

    delete_order.__isset.dealInfo = true;

    STUB_SYNC_INVOKE_ERRORINFO(stub, orderDelete, delete_order);
}


int main(int argc, char* argv[]) {
	std::stringstream socket_file;
	socket_file << "/data/trade_hosting_upside_entry/run/"
	        << boost::lexical_cast<std::string>(TEST_TRADE_ACCOUNT_ID) << ".sock";

	TradeHostingUpsideEntryStub stub;
	stub.setSocketFile(socket_file.str());

	try {
	    addOrderTest(stub);
//	    deleteOrderTest(stub);
	} catch (ErrorInfo& err) {
		std::cout << "error occurs, errorCode=" << err.errorCode
				  << ", msg=" << err.errorMsg
				  << std::endl;
	}


}

