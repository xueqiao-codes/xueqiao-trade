/*
 * order_validation.cpp
 *
 *  Created on: 2018年2月6日
 *      Author: wangli
 */
#include "order_validation.h"

#include <cmath>
#include "errorinfo_helper.h"

using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::storage::api;
using namespace xueqiao::trade::hosting::upside::entry;

void OrderValidation::checkInsertOrderStandard(const HostingExecOrder& insertOrder) throw(ErrorInfo) {
	CHECK_PARAM_ERRORINFO(insertOrder.__isset.execOrderId && insertOrder.execOrderId > 0)
	CHECK_PARAM_ERRORINFO(insertOrder.__isset.subUserId && insertOrder.subUserId > 0)

	// 标准雪橇执行订单中订单详情的检测
	CHECK_PARAM_ERRORINFO(insertOrder.__isset.orderDetail)  // 订单详情
	CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.orderType) // 订单类型
	CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.quantity && insertOrder.orderDetail.quantity > 0) // 订单手数
	CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.tradeDirection) // 买卖方向
	CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_BUY
			|| insertOrder.orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_SELL);
	CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.orderMode)  // 订单模式

	// 目前只支持GFD模式订单
	if (insertOrder.orderDetail.orderMode != HostingExecOrderMode::ORDER_GFD) {
		ErrorInfoHelper::throwNotSupportedError("Only support GFD mode order");
	}

	CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.orderCreatorType) // 订单创建者类型

	if (insertOrder.orderDetail.orderType == HostingExecOrderType::ORDER_LIMIT_PRICE) {
	    CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.limitPrice)
	    CHECK_PARAM_ERRORINFO(!std::isnan(insertOrder.orderDetail.limitPrice))
	    CHECK_PARAM_ERRORINFO(!std::isinf(insertOrder.orderDetail.limitPrice))
	} else if (insertOrder.orderDetail.orderType == HostingExecOrderType::ORDER_WITH_CONDITION) {
	    CHECK_PARAM_ERRORINFO(!std::isnan(insertOrder.orderDetail.limitPrice))
	    CHECK_PARAM_ERRORINFO(!std::isinf(insertOrder.orderDetail.limitPrice))
		CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.condition);
		CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.condition >= HostingExecOrderCondition::LASTEST_PRICE_GT
				&& insertOrder.orderDetail.condition <= HostingExecOrderCondition::BUYONE_PRICE_LE);
		CHECK_PARAM_ERRORINFO(insertOrder.orderDetail.__isset.conditionPrice)
		CHECK_PARAM_ERRORINFO(!std::isnan(insertOrder.orderDetail.conditionPrice))
		CHECK_PARAM_ERRORINFO(!std::isinf(insertOrder.orderDetail.conditionPrice))
	} else {
		ErrorInfoHelper::throwParamError("unkown order type!");
	}

	// 账号信息的检测
	CHECK_PARAM_ERRORINFO(insertOrder.__isset.accountSummary);
	checkAccountSummary(insertOrder.accountSummary);

	// 下单前分配OrderRef
	CHECK_PARAM_ERRORINFO(insertOrder.__isset.upsideOrderRef);
}

void OrderValidation::checkDeleteOrderStandard(const HostingExecOrder& deleteOrder) throw(ErrorInfo)  {
	CHECK_PARAM_ERRORINFO(deleteOrder.__isset.execOrderId && deleteOrder.execOrderId > 0);  // 必须有明确的订单号

	// 携带订单引用信息或者订单信息
	CHECK_PARAM_ERRORINFO(deleteOrder.__isset.upsideOrderRef);
	CHECK_PARAM_ERRORINFO(deleteOrder.__isset.dealInfo);

	// 账号信息检测
	CHECK_PARAM_ERRORINFO(deleteOrder.__isset.accountSummary);
	checkAccountSummary(deleteOrder.accountSummary);
}

void OrderValidation::checkSyncOrderStateStandard(const HostingExecOrder& syncOrder) throw(ErrorInfo) {
    CHECK_PARAM_ERRORINFO(syncOrder.__isset.execOrderId && syncOrder.execOrderId > 0);

    CHECK_PARAM_ERRORINFO(syncOrder.__isset.accountSummary);
    checkAccountSummary(syncOrder.accountSummary);
}

void OrderValidation::checkSyncOrderTradesStandard(const ::xueqiao::trade::hosting::HostingExecOrder& syncOrder) {
    CHECK_PARAM_ERRORINFO(syncOrder.__isset.execOrderId && syncOrder.execOrderId > 0);
    CHECK_PARAM_ERRORINFO(syncOrder.__isset.accountSummary);
    checkAccountSummary(syncOrder.accountSummary);
}

void OrderValidation::checkAccountSummary(const HostingExecOrderTradeAccountSummary& accountSummary) throw(ErrorInfo) {
	CHECK_PARAM_ERRORINFO(accountSummary.__isset.tradeAccountId && accountSummary.tradeAccountId > 0);
	CHECK_PARAM_ERRORINFO(accountSummary.__isset.brokerId && accountSummary.brokerId > 0);
	CHECK_PARAM_ERRORINFO(accountSummary.__isset.techPlatform);
}
