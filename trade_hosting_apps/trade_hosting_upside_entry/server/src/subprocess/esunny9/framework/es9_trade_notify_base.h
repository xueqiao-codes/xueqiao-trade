/*
 * es9_trade_notify_base.h
 *
 *  Created on: 2018年4月12日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_TRADE_NOTIFY_BASE_H_
#define SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_TRADE_NOTIFY_BASE_H_

#include "iTapTradeAPI.h"

namespace es9ext {
namespace framework {


class Es9TradeNotifyBase : public ITapTrade::ITapTradeAPINotify {
public:
    virtual ~Es9TradeNotifyBase() = default;

    virtual void TAP_CDECL OnConnect() {}
    virtual void TAP_CDECL OnRspLogin(ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPITradeLoginRspInfo *loginRspInfo){}
    virtual void TAP_CDECL OnExpriationDate(ITapTrade::TAPIDATE date, int days){}
    virtual void TAP_CDECL OnAPIReady(){}
    virtual void TAP_CDECL OnDisconnect(ITapTrade::TAPIINT32 reasonCode){}

    virtual void TAP_CDECL OnRspChangePassword(ITapTrade::TAPIUINT32 sessionID, ITapTrade::TAPIINT32 errorCode) {}
    virtual void TAP_CDECL OnRspSetReservedInfo(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode, const ITapTrade::TAPISTR_50 info) {}
    virtual void TAP_CDECL OnRspQryAccount(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIUINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountInfo *info) {}
    virtual void TAP_CDECL OnRspQryFund(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIFundData *info) {}

    virtual void TAP_CDECL OnRtnFund(const ITapTrade::TapAPIFundData *info) {}
    virtual void TAP_CDECL OnRspQryExchange(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIExchangeInfo *info) {}
    virtual void TAP_CDECL OnRspQryCommodity(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPICommodityInfo *info) {}
    virtual void TAP_CDECL OnRspQryContract(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPITradeContractInfo *info) {}
    virtual void TAP_CDECL OnRtnContract(const ITapTrade::TapAPITradeContractInfo *info) {}
    virtual void TAP_CDECL OnRspOrderAction(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderActionRsp *info) {}
    virtual void TAP_CDECL OnRtnOrder(const ITapTrade::TapAPIOrderInfoNotice *info) {}
    virtual void TAP_CDECL OnRspQryOrder(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIOrderInfo *info) {}
    virtual void TAP_CDECL OnRspQryOrderProcess(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIOrderInfo *info) {}

    virtual void TAP_CDECL OnRspQryFill(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIFillInfo *info) {}

    virtual void TAP_CDECL OnRtnFill(const ITapTrade::TapAPIFillInfo *info) {}
    virtual void TAP_CDECL OnRspQryPosition(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIPositionInfo *info) {}
    virtual void TAP_CDECL OnRtnPosition(const ITapTrade::TapAPIPositionInfo *info) {}
    virtual void TAP_CDECL OnRtnPositionProfit(const ITapTrade::TapAPIPositionProfitNotice *info) {}

    virtual void TAP_CDECL OnRspQryCurrency(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPICurrencyInfo *info) {}

    virtual void TAP_CDECL OnRspQryTradeMessage(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPITradeMessage *info) {}

    virtual void TAP_CDECL OnRtnTradeMessage(const ITapTrade::TapAPITradeMessage *info) {}

    virtual void TAP_CDECL OnRspQryHisOrder(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIHisOrderQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryHisOrderProcess(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIHisOrderProcessQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryHisMatch(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIHisMatchQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryHisPosition(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIHisPositionQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryHisDelivery(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIHisDeliveryQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryAccountCashAdjust(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountCashAdjustQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryBill(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIBillQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryAccountFeeRent(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountFeeRentQryRsp *info) {}

    virtual void TAP_CDECL OnRspQryAccountMarginRent(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , ITapTrade::TAPIYNFLAG isLast
            , const ITapTrade::TapAPIAccountMarginRentQryRsp *info) {}

    virtual void TAP_CDECL OnRspHKMarketOrderInsert(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            ,  const ITapTrade::TapAPIOrderMarketInsertRsp *info) {}

    virtual void TAP_CDECL OnRspHKMarketOrderDelete(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderMarketDeleteRsp *info) {}

    virtual void TAP_CDECL OnHKMarketQuoteNotice(const ITapTrade::TapAPIOrderQuoteMarketNotice *info) {}
    virtual void TAP_CDECL OnRspOrderLocalRemove(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderLocalRemoveRsp *info) {}

    virtual void TAP_CDECL OnRspOrderLocalInput(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderLocalInputRsp *info) {}

    virtual void TAP_CDECL OnRspOrderLocalModify(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderLocalModifyRsp *info) {}

    virtual void TAP_CDECL OnRspOrderLocalTransfer(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIOrderLocalTransferRsp *info) {}

    virtual void TAP_CDECL OnRspFillLocalInput(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIFillLocalInputRsp *info) {}

    virtual void TAP_CDECL OnRspFillLocalRemove(ITapTrade::TAPIUINT32 sessionID
            , ITapTrade::TAPIINT32 errorCode
            , const ITapTrade::TapAPIFillLocalRemoveRsp *info) {}


protected:
    Es9TradeNotifyBase() = default;
};

} // end namespace framework
} // end namespace es9ext



#endif /* SRC_SUBPROCESS_ESUNNY9_FRAMEWORK_ES9_TRADE_NOTIFY_BASE_H_ */
