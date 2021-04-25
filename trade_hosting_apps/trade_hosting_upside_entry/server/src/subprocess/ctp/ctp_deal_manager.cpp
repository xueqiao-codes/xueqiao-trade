/*
 * ctp_deal_manager.cpp
 *
 *  Created on: 2018年3月26日
 *      Author: wangli
 */
#include "ctp_deal_manager.h"

#include <boost/lexical_cast.hpp>
#include "base/code_defense.h"
#include "ctp_self_match_manager_backend.h"
#include "errorinfo_helper.h"
#include "hosting_message_sender.h"
#include "order_validation.h"
#include "trade_hosting_basic_errors_types.h"
#include "trade_hosting_basic_events_types.h"
#include "ContractConvertor.h"

using namespace apache::thrift;
using namespace soldier::base;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::events;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::storage::api;

#define CTP_OVER_CLOSETODAY_POSITION 50
#define CTP_OVER_CLOSEYESTERDAY_POSITION 51
#define CTP_OVER_CLOSE_POSITION 30

#define APPLOG_INFO_WARN(condition, ...) \
    if (condition) { \
        APPLOG_WARN(__VA_ARGS__);\
    } else { \
        APPLOG_INFO(__VA_ARGS__); \
    }


CTPDealManager::CTPDealManager(
        const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher
        , const std::shared_ptr<CTPLoginManager>& login_manager
        , const std::shared_ptr<CTPDataManager>& data_manager
        , const std::shared_ptr<CTPPositionManager>& position_manager)
    : dispatcher_(dispatcher)
     , login_manager_(login_manager)
     , data_manager_(data_manager)
     , position_manager_(position_manager){
    CHECK(dispatcher_);
    CHECK(login_manager_);
    CHECK(data_manager_);

    std::shared_ptr<SelfMatchManagerBackend<CThostFtdcOrderField>>
        ctp_self_match_manager_backend(new CTPSelfMatchManagerBackend());
    self_match_manager_.reset(new SelfMatchManager<CThostFtdcOrderField>(ctp_self_match_manager_backend));
}

void CTPDealManager::checkAccountSummaryValid(const HostingExecOrderTradeAccountSummary& accountSummary) throw(ErrorInfo) {
    // 账号信息匹配性检测
    CHECK_PARAM_ERRORINFO(accountSummary.tradeAccountId == login_manager_->getTradeAccount()->tradeAccountId);
    CHECK_PARAM_ERRORINFO(accountSummary.brokerId == login_manager_->getTradeAccount()->tradeBrokerId);
    CHECK_PARAM_ERRORINFO(accountSummary.techPlatform == BrokerTechPlatform::TECH_CTP);
}

void CTPDealManager::checkOrderInputExtContractSummary(const CTPOrderInputExt& ctpInputExt) throw (ErrorInfo) {
    CHECK_PARAM_ERRORINFO(ctpInputExt.__isset.contractSummary);
    CHECK_PARAM_ERRORINFO(ctpInputExt.contractSummary.__isset.ctpExchangeCode && !ctpInputExt.contractSummary.ctpExchangeCode.empty());
    CHECK_PARAM_ERRORINFO(ctpInputExt.contractSummary.__isset.ctpCommodityType && ctpInputExt.contractSummary.ctpCommodityType > 0)
    CHECK_PARAM_ERRORINFO(ctpInputExt.contractSummary.__isset.ctpCommodityCode && !ctpInputExt.contractSummary.ctpCommodityCode.empty());
    CHECK_PARAM_ERRORINFO(ctpInputExt.contractSummary.__isset.ctpContractCode && !ctpInputExt.contractSummary.ctpContractCode.empty());
}

void CTPDealManager::checkOrderInputExt(const CTPOrderInputExt& ctpInputExt) throw(ErrorInfo) {
    checkOrderInputExtContractSummary(ctpInputExt);
    if(ctpInputExt.__isset.minVolume) {
        CHECK_PARAM_ERRORINFO(ctpInputExt.minVolume > 0);
    }
    if(ctpInputExt.__isset.combHedgeFlag) {
        CHECK_PARAM_ERRORINFO(SUPPORTED_CTP_COMB_HEDGE.find(ctpInputExt.combHedgeFlag) != SUPPORTED_CTP_COMB_HEDGE.end());
    }
}

void CTPDealManager::checkOrderRef(const CTPOrderRef& ctpRef) throw(ErrorInfo) {
    CHECK_PARAM_ERRORINFO(ctpRef.__isset.frontID);
    CHECK_PARAM_ERRORINFO(ctpRef.__isset.sessionID);
    CHECK_PARAM_ERRORINFO(ctpRef.__isset.orderRef && !ctpRef.orderRef.empty());
}

std::string CTPDealManager::ctpString2Utf8(const TThostFtdcErrorMsgType& msg) {
    char buffer[sizeof(TThostFtdcErrorMsgType) + 1];
    strncpy(buffer, msg, sizeof(TThostFtdcErrorMsgType));
    buffer[sizeof(TThostFtdcErrorMsgType)] = 0;

    std::string error_msg_utf8;
    StringUtil::gbkToUTF8(buffer, error_msg_utf8);
    return error_msg_utf8;
}

std::string CTPDealManager::ctpDateTime2StandardDateTime(
        const TThostFtdcDateType& date
        , const TThostFtdcTimeType& time) {
    std::string result;
    if (strlen(date) == 8 && strlen(time) == 8) {
        char convert_date[16];
        memcpy(convert_date, date, 4);
        convert_date[4] = '-';
        memcpy(convert_date + 5,date + 4, 2);
        convert_date[7] = '-';
        memcpy(convert_date + 8, date + 6, 2);
        convert_date[10] = 0;

        result.append(convert_date).append(" ").append(time);
    }
    return result;
}

std::string CTPDealManager::getCTPUsefulMsg(const std::string& msg) {
    std::vector<std::string> msg_splits;
    StringUtil::tokenize(msg, msg_splits, ":", true);
    if(msg_splits.size() <= 1) {
        return "";
    }
    return msg_splits.back();
}

int64_t CTPDealManager::getExecOrderIdByOrderField(
        const CThostFtdcOrderField* pOrder
        , const std::string& call_function) throw(ErrorInfo) {
    int64_t exec_order_id = 0;

    if (pOrder->OrderRef[0] != 0) {
        HostingExecOrderRef order_ref;
        order_ref.ctpRef.__set_frontID(pOrder->FrontID);
        order_ref.ctpRef.__set_sessionID(pOrder->SessionID);
        order_ref.ctpRef.__set_orderRef(pOrder->OrderRef);
        order_ref.__isset.ctpRef = true;
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(order_ref);
    }

    // 然后通过订单信息查询
    if (exec_order_id <= 0) {
        HostingExecOrderDealID  deal_id;
        deal_id.ctpDealId.__set_exchangeId(pOrder->ExchangeID);
        deal_id.ctpDealId.__set_orderSysId(pOrder->OrderSysID);
        deal_id.__isset.ctpDealId = true;
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(deal_id);
    }

    APPLOG_INFO("{} BrokerID={}, InvestorID={}, InstrumentID={}, ExchangeID={}, OrderSysID={}"
            ", FrontID={}, SessionID={}, OrderRef={}, OrderType={}, VolumeTotalOriginal={}"
            ", OrderStatus={}, VolumeTraded={}, VolumeTotal={}, StatusMsg={}, UsefulStatusMsg={}, OrderSubmitStatus={}"
            ", SequenceNo={}, InsertDate={}, InsertTime={}, TradingDay={}, OrderLocalID={}, TraderID={}"
            " -> exec_order_id={}", call_function
            , pOrder->BrokerID, pOrder->InvestorID, pOrder->InstrumentID, pOrder->ExchangeID, pOrder->OrderSysID
            , pOrder->FrontID, pOrder->SessionID, pOrder->OrderRef, pOrder->OrderType, pOrder->VolumeTotalOriginal
            , pOrder->OrderStatus, pOrder->VolumeTraded, pOrder->VolumeTotal
            , ctpString2Utf8(pOrder->StatusMsg), getCTPUsefulMsg(ctpString2Utf8(pOrder->StatusMsg)), pOrder->OrderSubmitStatus
            , pOrder->SequenceNo, pOrder->InsertDate, pOrder->InsertTime, pOrder->TradingDay, pOrder->TraderID, pOrder->OrderLocalID
            , exec_order_id);

    return exec_order_id;
}

int64_t CTPDealManager::getExecOrderIdByTradeField(const CThostFtdcTradeField *pTrade, const std::string& call_function) throw(ErrorInfo) {
    int64_t exec_order_id = 0;

    HostingExecOrderDealID  deal_id;
    deal_id.ctpDealId.__set_exchangeId(pTrade->ExchangeID);
    deal_id.ctpDealId.__set_orderSysId(pTrade->OrderSysID);
    deal_id.__isset.ctpDealId = true;
    exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(deal_id);

    APPLOG_INFO_WARN(exec_order_id <= 0,
            "{} BrokerID={}, InverstorID={}, InstrumentID={}, ExchangeID={}, OrderSysID={}"
            ", TradeID={}, Volumn={}, Price={}, TradeTime={}"
            ", TradeDate={}, TradeType={} OrderRef={}, SequenceNo={}, OrderLocalID={}, TraderID={}"
            " -> exec_order_id={}", call_function
            , pTrade->BrokerID, pTrade->InvestorID, pTrade->InstrumentID, pTrade->ExchangeID, pTrade->OrderSysID
            , pTrade->TradeID, pTrade->Volume, pTrade->Price, pTrade->TradeTime
            , pTrade->TradeDate, pTrade->TradeType, pTrade->OrderRef, pTrade->SequenceNo, pTrade->TraderID, pTrade->OrderLocalID
            , exec_order_id);

    return exec_order_id;
}

int64_t CTPDealManager::getExecOrderIdByInputActionField(
        const CThostFtdcInputOrderActionField* pInputOrderAction, const std::string& call_function) throw(ErrorInfo) {
    int64_t exec_order_id = -1;
    if (pInputOrderAction->OrderRef[0] != 0) {
        HostingExecOrderRef order_ref;
        order_ref.ctpRef.__set_frontID(pInputOrderAction->FrontID);
        order_ref.ctpRef.__set_sessionID(pInputOrderAction->SessionID);
        order_ref.ctpRef.__set_orderRef(pInputOrderAction->OrderRef);
        order_ref.__isset.ctpRef = true;
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(order_ref);
    }

    if (exec_order_id <= 0
            && strlen(pInputOrderAction->ExchangeID) > 0
            && strlen(pInputOrderAction->OrderSysID) > 0) {
        HostingExecOrderDealID  deal_id;
        deal_id.ctpDealId.__set_exchangeId(pInputOrderAction->ExchangeID);
        deal_id.ctpDealId.__set_orderSysId(pInputOrderAction->OrderSysID);
        deal_id.__isset.ctpDealId = true;
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(deal_id);
    }

    APPLOG_INFO_WARN(exec_order_id <= 0
                , "{} CThostFtdcInputOrderActionField frontID={}, sessionID={}, orderRef={}, ExchangeID={}, OrderSysID={}"
                " -> exec_order_id={}"
                , call_function, pInputOrderAction->FrontID, pInputOrderAction->SessionID, pInputOrderAction->OrderRef
                , pInputOrderAction->ExchangeID, pInputOrderAction->OrderSysID
                , exec_order_id);

    return exec_order_id;
}

int64_t CTPDealManager::getExecOrderIdByActionField(
        const CThostFtdcOrderActionField *pOrderAction, const std::string& call_function) throw(ErrorInfo) {
    int64_t exec_order_id = -1;
    if (pOrderAction->OrderRef[0] != 0) {
        HostingExecOrderRef order_ref;
        order_ref.ctpRef.__set_frontID(pOrderAction->FrontID);
        order_ref.ctpRef.__set_sessionID(pOrderAction->SessionID);
        order_ref.ctpRef.__set_orderRef(pOrderAction->OrderRef);
        order_ref.__isset.ctpRef = true;
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(order_ref);
    }

    if (exec_order_id <= 0
            && strlen(pOrderAction->ExchangeID) > 0
            && strlen(pOrderAction->OrderSysID) > 0) {
        HostingExecOrderDealID  deal_id;
        deal_id.ctpDealId.__set_exchangeId(pOrderAction->ExchangeID);
        deal_id.ctpDealId.__set_orderSysId(pOrderAction->OrderSysID);
        deal_id.__isset.ctpDealId = true;
        exec_order_id = data_manager_->getOrderMappingManager().getExecOrderId(deal_id);
    }

    APPLOG_INFO_WARN(exec_order_id <= 0
            , "{} CThostFtdcOrderActionField frontID={}, sessionID={}, orderRef={}, ExchangeID={}, OrderSysID={}"
            " -> exec_order_id={}"
            , call_function, pOrderAction->FrontID, pOrderAction->SessionID, pOrderAction->OrderRef
            , pOrderAction->ExchangeID, pOrderAction->OrderSysID
            , exec_order_id);

    return exec_order_id;
}

bool CTPDealManager::order2NotifyStateInfo(
        const CThostFtdcOrderField* pOrder
        , int64_t exec_order_id
        , HostingUpsideNotifyStateInfo& state_info) {
    state_info.__isset.dealInfo = true;
    if (pOrder->OrderSysID[0] != 0 && pOrder->ExchangeID[0] != 0) {
        state_info.dealInfo.__isset.dealId = true;
        state_info.dealInfo.dealId.__isset.ctpDealId = true;
        state_info.dealInfo.dealId.ctpDealId.__set_exchangeId(pOrder->ExchangeID);
        state_info.dealInfo.dealId.ctpDealId.__set_orderSysId(pOrder->OrderSysID);
        data_manager_->getOrderMappingManager().setExecOrderId(state_info.dealInfo.dealId, exec_order_id);
    }

    std::string insert_date_time = ctpDateTime2StandardDateTime(pOrder->InsertDate, pOrder->InsertTime);
    if (!insert_date_time.empty()) {
        state_info.dealInfo.__set_orderInsertDateTime(insert_date_time);
    }

    state_info.dealInfo.__isset.ctpDealInfo = true;
    if (pOrder->Direction == THOST_FTDC_D_Buy) {
        state_info.dealInfo.ctpDealInfo.__set_tradeDirection(CTPTradeDirection::CTP_BUY);
    } else if (pOrder->Direction == THOST_FTDC_D_Sell) {
        state_info.dealInfo.ctpDealInfo.__set_tradeDirection(CTPTradeDirection::CTP_SELL);
    } else {
        APPLOG_WARN("[WARNING]order2NotifyStateInfo pOrder's Direction can not recognise!");
    }

    if(pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_Open) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_OPEN);
    } else if (pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_Close) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_ClOSE);
    } else if (pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_ForceClose) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_FORCECLOSE);
    } else if (pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_CloseToday) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_CLOSETODAY);
    } else if (pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_CloseYesterday) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_CLOSEYESTERDAY);
    } else if (pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_ForceOff) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_FORCECLOSE);
    } else if (pOrder->CombOffsetFlag[0] == THOST_FTDC_OF_LocalForceClose) {
        state_info.dealInfo.ctpDealInfo.__set_offsetFlag(CTPCombOffsetFlagType::THOST_FTDC_OF_LOCALFORCECLOSE);
    }

    state_info.__set_volumeTraded(pOrder->VolumeTraded);
    state_info.__set_volumeResting(pOrder->VolumeTotal);
    state_info.__set_statusUsefulMsg(getCTPUsefulMsg(ctpString2Utf8(pOrder->StatusMsg)));
    if (pOrder->OrderStatus == THOST_FTDC_OST_Unknown) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_UPSIDE_RECIVED);
    } else if (pOrder->OrderStatus == THOST_FTDC_OST_NotTouched) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_CONDITION_NOT_TRIGGERED);
    } else if (pOrder->OrderStatus == THOST_FTDC_OST_Touched) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_CONDITION_TRIGGERED);
    } else if (pOrder->OrderStatus == THOST_FTDC_OST_AllTraded) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_FINISHED);
    } else if (pOrder->OrderStatus == THOST_FTDC_OST_Canceled) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_CANCELLED);
    } else if (pOrder->OrderStatus == THOST_FTDC_OST_PartTradedQueueing) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_PARTFINISHED);
    } else if (pOrder->OrderStatus == THOST_FTDC_OST_NoTradeQueueing) {
        state_info.__set_state(HostingUpsideNotifyStateType::NOTIFY_ORDER_RESTING);
    } else {
        APPLOG_WARN("status={} can not mapping", pOrder->OrderStatus);
        return false;
    }
    return true;
}

bool CTPDealManager::trade2TradeLegInfo(const CThostFtdcTradeField* pTrade
        , ::xueqiao::trade::hosting::HostingExecTradeLegInfo& trade_leg_info) {
    if (pTrade->TradeID[0] == 0) {
        APPLOG_WARN("[WARNING]trade2TradeLegInfo pTrade's TradeID is empty");
        return false;
    }
    if (std::isnan(pTrade->Price) || std::isinf(pTrade->Price)) {
        APPLOG_WARN("[WARNING]trade2TradeLegInfo pTrade's price is not a valid num, TradeID={}"
                , pTrade->TradeID);
        return false;
    }
    if (pTrade->Volume <= 0) {
        APPLOG_WARN("[WARNING]trade2TradeLegInfo pTrade's Volume={} is not valid, TradeID={}"
                , pTrade->Volume, pTrade->TradeID);
        return false;
    }

    trade_leg_info.__isset.legUpsideTradeId = true;
    trade_leg_info.legUpsideTradeId.__isset.ctpTradeId = true;
    trade_leg_info.legUpsideTradeId.ctpTradeId.__set_tradeId(pTrade->TradeID);

    trade_leg_info.__set_legTradePrice(pTrade->Price);
    trade_leg_info.__set_legTradeVolume(pTrade->Volume);

    if (pTrade->Direction == THOST_FTDC_D_Buy) {
        trade_leg_info.__set_legUpsideTradeDirection(HostingExecTradeDirection::TRADE_BUY);
    } else if (pTrade->Direction == THOST_FTDC_D_Sell){
        trade_leg_info.__set_legUpsideTradeDirection(HostingExecTradeDirection::TRADE_SELL);
    } else {
        APPLOG_WARN("[WARNING]trade2TradeLegInfo pTrade's Direction can not recognise!");
        return false;
    }

    std::string trade_datetime;
    trade_datetime = ctpDateTime2StandardDateTime(pTrade->TradeDate, pTrade->TradeTime);
    if (!trade_datetime.empty()) {
        trade_leg_info.__set_legTradeDateTime(trade_datetime);
    }
    return true;
}

bool CTPDealManager::trade2TradeLegContractSummary(
        const CThostFtdcTradeField* pTrade
        , ::xueqiao::trade::hosting::HostingExecOrderLegContractSummary& leg_contract_summary) {
    std::shared_ptr<CThostFtdcInstrumentField> instrument_field
            = data_manager_->getInstrumentsHolder().getInstrument(pTrade->InstrumentID);
    if (!instrument_field) {
        APPLOG_ERROR("Failed to get instrument for {}", pTrade->InstrumentID);
        return false;
    }

    // 获取雪橇商品映射
    CommodityMappingOption mapping_option;
    mapping_option.sledBrokerId = 0;
    mapping_option.brokerExchangeCode = instrument_field->ExchangeID;
    mapping_option.brokerCommodityType.append(&(instrument_field->ProductClass), 1);
    mapping_option.brokerCommodityCode = instrument_field->ProductID;
    mapping_option.brokerTechPlatform = ::xueqiao::contract::standard::TechPlatform::CTP;

    std::shared_ptr<CommodityMappingEntry> mapping_entry;
    uint64_t mapping_time_ns = 0;
    {
        S_BLOCK_TIMER(mapping_time_ns);
        mapping_entry = data_manager_->getContractMapping().getCommodityMapping(mapping_option);
    }
    APPLOG_DEBUG("Get Commodity for {}, escaped time={}ns", mapping_option, mapping_time_ns);
    if (!mapping_entry) {
        APPLOG_ERROR("Failed to found commodity mapping for InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={}"
                , pTrade->InstrumentID
                , instrument_field->ExchangeID
                , instrument_field->ProductClass
                , instrument_field->ProductID);
        return false;
    }

    // 获取合约代码
    TechPlatformContractToSledArgs args;
    TechPlatformContractToSledResult result;
    memset(&args, 0, sizeof(TechPlatformContractToSledArgs));
    memset(&result, 0, sizeof(TechPlatformContractToSledResult));

    strcpy(args.CommonContract_.TechPlatform_Exchange_, instrument_field->ExchangeID);
    args.CommonContract_.TechPlatform_CommodityType_[0] = instrument_field->ProductClass;
    strcpy(args.CommonContract_.TechPlatform_CommodityCode_, instrument_field->ProductID);
    strcpy(args.CommonContract_.TechPlatform_ContractCode_, pTrade->InstrumentID);
    args.TechPlatform_ = TechPlatform_CTP;
    result = PlatformToSledContract(args);

    if (0 == result.MixContract_.SledContractCode_[0]) {
        APPLOG_ERROR("Failed to mapping contract code for InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={}"
                , pTrade->InstrumentID
                , instrument_field->ExchangeID
                , instrument_field->ProductClass
                , instrument_field->ProductID);
        return false;
    }

    APPLOG_INFO("Contract InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={} "
            "-> mappingID={}, sledCommodityId={}, sledExchangeCode={}"
            ", sledCommodityType={}, sledCommodityCode={}, sledContractCode={}"
            , pTrade->InstrumentID, instrument_field->ExchangeID, instrument_field->ProductClass, instrument_field->ProductID
            , mapping_entry->mapping.mappingId, mapping_entry->mapping.sledCommodityId
            , mapping_entry->commodity.exchangeMic, mapping_entry->commodity.sledCommodityType
            , mapping_entry->commodity.sledCommodityCode
            , result.MixContract_.SledContractCode_);

    leg_contract_summary.__set_legSledContractCode(result.MixContract_.SledContractCode_);
    leg_contract_summary.__set_legSledCommodityId(mapping_entry->mapping.sledCommodityId);
    leg_contract_summary.__set_legSledCommodityCode(mapping_entry->commodity.sledCommodityCode);
    leg_contract_summary.__set_legSledCommodityType(mapping_entry->commodity.sledCommodityType);
    leg_contract_summary.__set_legSledExchangeMic(mapping_entry->commodity.exchangeMic);
    return true;
}

std::shared_ptr<HostingExecOrder> CTPDealManager::processNotFoundOrder(const CThostFtdcOrderField* pOrder) throw(ErrorInfo) {
    CHECK(pOrder);
    return std::shared_ptr<HostingExecOrder>();
}

void CTPDealManager::OnErrRtnOrderAction(CThostFtdcOrderActionField *pOrderAction, CThostFtdcRspInfoField *pRspInfo) {
    if (!pRspInfo) {
        APPLOG_WARN("OnErrRtnOrderAction called, but pRspInfo is empty!");
        return ;
    }
    if (!pOrderAction) {
        APPLOG_WARN("OnErrRtnOrderAction called, but pInputOrderAction is empty!");
        return ;
    }

    try {
        std::string error_msg = ctpString2Utf8(pRspInfo->ErrorMsg);
        std::string useful_error_msg = getCTPUsefulMsg(error_msg);
        APPLOG_INFO("OnErrRtnOrderAction ErrorID={}, ErrorMsg={}, UsefuleErrorMsg={}"
                , pRspInfo->ErrorID
                , error_msg
                , useful_error_msg);
        if (pRspInfo->ErrorID == 0) {
            return ;
        }
        int64_t exec_order_id = getExecOrderIdByActionField(pOrderAction, __FUNCTION__);
        if (exec_order_id <= 0) {
            return ;
        }

        if (pOrderAction->ActionFlag != THOST_FTDC_AF_Delete) {
            APPLOG_WARN("OnErrRtnOrderAction found ActionFlag != THOST_FTDC_AF_Delete, exec_order_id={}"
                    , exec_order_id);
            return ;
        }

        sendOrderDeleteFailedEvent(exec_order_id, pRspInfo->ErrorID, useful_error_msg);
    } catch (ErrorInfo& e) {
        APPLOG_INFO("OnErrRtnOrderAction error occurs, errorCode={}, errorMsg={}"
                , e.errorCode, e.errorMsg);
    }
}

void CTPDealManager::sendOrderDeleteFailedEvent(int64_t exec_order_id
        , int32_t upside_error_code
        , const std::string& upside_error_msg) {
    SEND_MESSAGE_BEGIN(UpsideOrderDeleteFailedEvent, failed_event)
    failed_event.__set_execOrderId(exec_order_id);
    failed_event.__set_upsideErrorCode(upside_error_code);
    failed_event.__set_upsideErrorMsg(upside_error_msg);
    failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER);
    failed_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
    SEND_MESSAGE_END(failed_event)
}

void CTPDealManager::OnRtnOrder(CThostFtdcOrderField *pOrder) {
    if (!pOrder) {
        APPLOG_WARN("OnRtnOrder called, but pOrder is empty!");
        return ;
    }

    try {
        self_match_manager_->processOrder(
                std::shared_ptr<CThostFtdcOrderField>(new CThostFtdcOrderField(*pOrder)));
        onRtnOrderProcess(pOrder);
    } catch (ErrorInfo& e) {
        // TODO 异常监控细节化
        APPLOG_ERROR("onRtnOrderProcess error! errorCode={}, errorMsg={}"
                , e.errorCode, e.errorMsg);
    }
}

void CTPDealManager::onRtnOrderProcess(const CThostFtdcOrderField* pOrder) throw(ErrorInfo) {
    int64_t exec_order_id = getExecOrderIdByOrderField(pOrder, __FUNCTION__);
    if (exec_order_id <= 0) {
        processNotFoundOrder(pOrder);
        return ;
    }

    SEND_MESSAGE_BEGIN(UpsideNotifyForwardStateEvent, state_event)
    state_event.__set_execOrderId(exec_order_id);
    state_event.__set_receivedTimestampMs(NowInMilliSeconds());
    state_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
    if (order2NotifyStateInfo(pOrder, exec_order_id, state_event.forwardStateInfo)) {
        state_event.__isset.forwardStateInfo = true;
    } else {
        return ;
    }

    SEND_MESSAGE_END(state_event)
}

void CTPDealManager::OnRtnTrade(CThostFtdcTradeField *pTrade) {
    if (!pTrade) {
        APPLOG_WARN("OnRtnTrade called, but pTrade is empty");
        return ;
    }

    try {
        onRtnTradeProcess(pTrade);
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("onRtnTradeProcess error! errorCode={}, errorMsg={}", e.errorCode, e.errorMsg);
    }
}

void CTPDealManager::onDataManagerInitStart() {
    self_match_manager_->onInitStart();
}

void CTPDealManager::onQryOrdersFinished(const std::shared_ptr<QryAllOrdersResp>& all_orders_rsp) {
    syncOrders(all_orders_rsp->orders, all_orders_rsp->getRequestStartTimestampMs(), false);

    std::shared_ptr<std::vector<std::shared_ptr<CThostFtdcOrderField>>>
            all_order_fields(new std::vector<std::shared_ptr<CThostFtdcOrderField>>());
    all_order_fields->reserve(all_orders_rsp->orders.size() + 1);
    for (auto it = all_orders_rsp->orders.begin(); it != all_orders_rsp->orders.end(); ++it) {
        all_order_fields->push_back(std::shared_ptr<CThostFtdcOrderField>(new CThostFtdcOrderField(*it)));
    }
    self_match_manager_->onInitAllOrders(all_order_fields);
}

void CTPDealManager::onDataManagerInitFinished() {
    self_match_manager_->onInitFinished();
}

void CTPDealManager::onRtnTradeProcess(const CThostFtdcTradeField* pTrade) throw(ErrorInfo) {
    int64_t exec_order_id = getExecOrderIdByTradeField(pTrade, __FUNCTION__);
    if (exec_order_id <= 0) {
        return ;
    }

    SEND_MESSAGE_BEGIN(UpsideNotifyForwardTradeEvent, trade_event)
    trade_event.__set_execOrderId(exec_order_id);
    trade_event.__set_receivedTimestampMs(NowInMilliSeconds());
    trade_event.__set_eventCreateTimestampMs(NowInMilliSeconds());

    if (trade2TradeLegInfo(pTrade, trade_event.forwardTradeLegInfo)) {
        trade_event.__isset.forwardTradeLegInfo = true;
    } else {
        return ;
    }

    if (trade2TradeLegContractSummary(pTrade, trade_event.forwardTradeLegContractSummary)) {
        trade_event.__isset.forwardTradeLegContractSummary = true;
    } else {
        return ;
    }

    SEND_MESSAGE_END(trade_event)
}

void CTPDealManager::orderInsert(const HostingExecOrder& insert_order) throw (ErrorInfo) {
    OrderValidation::checkInsertOrderStandard(insert_order);
    checkAccountSummaryValid(insert_order.accountSummary);

    if (insert_order.orderDetail.orderType == HostingExecOrderType::ORDER_WITH_CONDITION) {
        CHECK_PARAM_ERRORINFO(SUPPORTED_CTP_ORDER_CONDITIONS.find(insert_order.orderDetail.condition) != SUPPORTED_CTP_ORDER_CONDITIONS.end());
    }

    // 输入扩展信息的检测
    CHECK_PARAM_ERRORINFO(insert_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(insert_order.orderInputExt.__isset.ctpInputExt);
    checkOrderInputExt(insert_order.orderInputExt.ctpInputExt);

    // 检测下单前分配的OrderRef
    CHECK_PARAM_ERRORINFO(insert_order.upsideOrderRef.__isset.ctpRef);
    checkOrderRef(insert_order.upsideOrderRef.ctpRef);

    std::shared_ptr<CThostFtdcRspUserLoginField> login_info = login_manager_->getLoginRsp();
    if (!login_info) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    if (insert_order.upsideOrderRef.ctpRef.sessionID != login_info->SessionID
            || insert_order.upsideOrderRef.ctpRef.frontID != login_info->FrontID) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_ORDERREF_EXPIRED, "orderRef expired");
    }

    std::shared_ptr<HostingExecOrder> insert_order_s(new HostingExecOrder(insert_order));

    bool has_self_match = false;
    self_match_manager_->orderInsertPrepare(insert_order_s, has_self_match);
    if (has_self_match) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_SELF_MATCH, "self match");
    }

    try {
        std::shared_ptr<OrderInsertHandler> insert_handler(new OrderInsertHandler(
            OrderInsertHandler::CallbackFunction(std::bind(&CTPDealManager::handleOrderInsertRsp, this, std::placeholders::_1))
            , login_info
            , insert_order_s
            , position_manager_));
        int ret = dispatcher_->sendRequest(insert_handler);
        if (ret != 0) {
            ErrorInfoHelper::throwError(
                    TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                    , "ReqOrderInsert ret=" + boost::lexical_cast<std::string>(ret));
        } else {
            data_manager_->getOrderMappingManager().setExecOrderId(insert_order.upsideOrderRef, insert_order.execOrderId);
        }
    } catch (ErrorInfo& e) {
        self_match_manager_->orderInsertCanceled(insert_order_s);
        throw e;
    }
}

void CTPDealManager::handleOrderInsertRsp(const std::shared_ptr<OrderInsertResp>& rsp) {
    if (rsp->hasTimeOut()) {
        CHECK(false); // should nerver happed for order insert
        return ;
    }

    if (rsp->getErrorCode() == 0) {
        return ;
    }

    self_match_manager_->orderInsertCanceled(rsp->insert_order);
    position_manager_->orderInsertCanceled(rsp->insert_order);

    SEND_MESSAGE_BEGIN(UpsideOrderInsertFailedEvent, failed_event)
    failed_event.__set_execOrderId(rsp->insert_order->execOrderId);
    failed_event.__set_upsideErrorCode(rsp->getErrorCode());
    failed_event.__set_upsideErrorMsg(getCTPUsefulMsg(rsp->getUtf8ErrorMsg()));
    if (rsp->getErrorCode() == CTP_OVER_CLOSETODAY_POSITION) {
        failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSETODAY_POSITION);
    } else if (rsp->getErrorCode() == CTP_OVER_CLOSEYESTERDAY_POSITION) {
        failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSEYESTERDAY_POSITION);
    } else if (rsp->getErrorCode() == CTP_OVER_CLOSE_POSITION) {
        failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_OVER_CLOSE_POSITION);
    } else {
        failed_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER);
    }
    failed_event.__set_eventCreateTimestampMs(NowInMilliSeconds());

    SEND_MESSAGE_END(failed_event)
}

void CTPDealManager::orderDelete(const HostingExecOrder& delete_order) throw (ErrorInfo) {
    OrderValidation::checkDeleteOrderStandard(delete_order);
    checkAccountSummaryValid(delete_order.accountSummary);

    // 检查输入信息中的合约信息
    CHECK_PARAM_ERRORINFO(delete_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(delete_order.orderInputExt.__isset.ctpInputExt);
    checkOrderInputExtContractSummary(delete_order.orderInputExt.ctpInputExt);

    std::shared_ptr<CThostFtdcRspUserLoginField> login_info = login_manager_->getLoginRsp();
    if (!login_info) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    std::shared_ptr<OrderDeleteHandler> delete_handler(new OrderDeleteHandler(
            OrderDeleteHandler::CallbackFunction(std::bind(&CTPDealManager::handleOrderDeleteRsp, this, std::placeholders::_1))
            , login_info
            , std::shared_ptr<HostingExecOrder>(new HostingExecOrder(delete_order))));
    int ret = dispatcher_->sendRequest(delete_handler);
    if (0 != ret) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                , "OrderDeleteHandler ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void CTPDealManager::handleOrderDeleteRsp(const std::shared_ptr<OrderDeleteResp>& rsp) {
    if (rsp->hasTimeOut()) {
        CHECK(false);
        return ;
    }

    if (rsp->getErrorCode() == 0 || !rsp->action_field) {
        return ;
    }

    std::string useful_error_msg = getCTPUsefulMsg(rsp->getUtf8ErrorMsg());
    APPLOG_INFO("handleOrderDeleteRsp, ErrorID={}, ErrorMsg={}, UsefuleErrorMsg={}"
                , rsp->getErrorCode()
                , rsp->getUtf8ErrorMsg()
                , useful_error_msg);

    try {
        int64_t exec_order_id = getExecOrderIdByInputActionField(rsp->action_field.get(), __FUNCTION__);
        if (exec_order_id <= 0) {
            return ;
        }

        if (rsp->action_field.get()->ActionFlag != THOST_FTDC_AF_Delete) {
            APPLOG_WARN("handleOrderDeleteRsp found ActionFlag != THOST_FTDC_AF_Delete, exec_order_id={}"
                , exec_order_id);
        return ;
        }

        sendOrderDeleteFailedEvent(exec_order_id, rsp->getErrorCode(), useful_error_msg);
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("handleOrderDeleteRsp error! errorCode={}, errorMsg={}"
                , e.errorCode, e.errorMsg);
    }
}

void CTPDealManager::syncOrderState(const HostingExecOrder& sync_order) throw (ErrorInfo) {
    OrderValidation::checkSyncOrderStateStandard(sync_order);
    checkAccountSummaryValid(sync_order.accountSummary);

    CHECK_PARAM_ERRORINFO(sync_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(sync_order.orderInputExt.__isset.ctpInputExt);
    checkOrderInputExtContractSummary(sync_order.orderInputExt.ctpInputExt);

    std::shared_ptr<CThostFtdcRspUserLoginField> login_info = login_manager_->getLoginRsp();
    if (!login_info) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    std::shared_ptr<OrderSyncStateHandler> sync_state_handler(new OrderSyncStateHandler(
            OrderSyncStateHandler::CallbackFunction(std::bind(&CTPDealManager::handleSyncOrderStateRsp, this, std::placeholders::_1))
            , login_info
            , std::shared_ptr<HostingExecOrder>(new HostingExecOrder(sync_order))));
    int ret = dispatcher_->sendRequest(sync_state_handler);
    if (ret != 0) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                , "OrderSyncStateHandler ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void CTPDealManager::handleSyncOrderStateRsp(const std::shared_ptr<OrderSyncStateResp>& rsp) {
    if (rsp->hasTimeOut() || rsp->getErrorCode() != 0) {
        return ;
    }

    try {
        bool order_founded = false;
        for (int index = 0; index < (int)rsp->resp_orders.size(); ++index) {
            CThostFtdcOrderField& order = rsp->resp_orders[index];

            self_match_manager_->processOrder(std::shared_ptr<CThostFtdcOrderField>(new CThostFtdcOrderField(order)));
            position_manager_->syncOrder(&order);

            int64_t exec_order_id = processOrderSyncState(
                    &order, rsp->getRequestStartTimestampMs(), rsp->sync_order->execOrderId);
            if (exec_order_id == rsp->sync_order->execOrderId) {
                order_founded = true;
            }
        }

        if (!rsp->sync_order_has_dealinfo && !order_founded) {
            self_match_manager_->orderInsertCanceled(rsp->sync_order);
            position_manager_->orderInsertCanceled(rsp->sync_order);

            SEND_MESSAGE_BEGIN(UpsideNotifySyncStateEvent, sync_event);
            sync_event.__set_execOrderId(rsp->sync_order->execOrderId);
            sync_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
            sync_event.__set_syncReqTimestampMs(rsp->getRequestStartTimestampMs());
            sync_event.__set_syncRespTimestampMs(NowInMilliSeconds());
            sync_event.__set_mappingErrorCode(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_ORDER_NOT_FOUND);
            sync_event.__set_upsideErrorCode(-1);
            sync_event.__set_upsideErrorMsg("订单未找到");
            SEND_MESSAGE_END(sync_event);
            return ;
        }
    } catch (ErrorInfo& e) {
        APPLOG_ERROR("handleSyncOrderStateRsp error occurs, errorCode={}, errorMsg={}"
                , e.errorCode, e.errorMsg);
    }
}

int64_t CTPDealManager::processOrderSyncState(const CThostFtdcOrderField *pOrder
        , long sync_start_timestampms
        , long send_msg_exec_order_id) throw(ErrorInfo) {
    CHECK(pOrder);

    int64_t exec_order_id = getExecOrderIdByOrderField(pOrder, __FUNCTION__);
    if (exec_order_id <= 0) {
        std::shared_ptr<HostingExecOrder> exec_order = processNotFoundOrder(pOrder);
        if (exec_order) {
            exec_order_id = exec_order->execOrderId;
        }
    }

    if (exec_order_id > 0 ) {
        if (send_msg_exec_order_id < 0
                || send_msg_exec_order_id == exec_order_id) {
            SEND_MESSAGE_BEGIN(UpsideNotifySyncStateEvent, sync_event)
                sync_event.__set_execOrderId(exec_order_id);
            sync_event.__set_eventCreateTimestampMs(NowInMilliSeconds());
            sync_event.__set_syncReqTimestampMs(sync_start_timestampms);
            sync_event.__set_syncRespTimestampMs(NowInMilliSeconds());

            if (order2NotifyStateInfo(pOrder, exec_order_id, sync_event.syncStateInfo)) {
                sync_event.__isset.syncStateInfo = true;
                SEND_MESSAGE_END(sync_event);
            }
        }
    }

    return exec_order_id;
}

void CTPDealManager::syncOrderTrades(const HostingExecOrder& sync_order) throw (ErrorInfo) {
    OrderValidation::checkSyncOrderTradesStandard(sync_order);
    checkAccountSummaryValid(sync_order.accountSummary);

    CHECK_PARAM_ERRORINFO(sync_order.__isset.orderInputExt);
    CHECK_PARAM_ERRORINFO(sync_order.orderInputExt.__isset.ctpInputExt);
    checkOrderInputExtContractSummary(sync_order.orderInputExt.ctpInputExt);

    std::shared_ptr<CThostFtdcRspUserLoginField> login_info = login_manager_->getLoginRsp();
    if (!login_info) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    std::shared_ptr<CThostFtdcInstrumentField> instrument_field
        = data_manager_->getInstrumentsHolder().getInstrument(
            sync_order.orderInputExt.ctpInputExt.contractSummary.ctpContractCode.c_str());
    if (!instrument_field) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_CTP_INSTRUMENT_NOT_FOUND, "instrument not found");
    }

    std::string qry_instrument_id = instrument_field->InstrumentID;
    if (instrument_field->ProductClass == THOST_FTDC_PC_Combination) {
        qry_instrument_id = "";
    }

    // 组合合约采用连续两次调用，会碰到CTP频率限制的问题，导致后手腿的成交查询始终失败，而采用延时堵住线程的方式会有额外的副作用
    // 这里采取小批全量同步的措施进行弥补，虽然不严格谨慎，但是也起到了弥补的作用, 因为本身次函数在正常情况下调用的频率非常低
    std::shared_ptr<OrderSyncTradesHandler> sync_trades_handler(new OrderSyncTradesHandler(
            OrderSyncTradesHandler::CallbackFunction(std::bind(&CTPDealManager::handleSyncTradesRsp, this, std::placeholders::_1))
            , login_info
            , qry_instrument_id
            , std::shared_ptr<HostingExecOrder>(new HostingExecOrder(sync_order))));
    int ret = dispatcher_->sendRequest(sync_trades_handler);
    if (ret != 0) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                , "OrderSyncTradesHandler ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void CTPDealManager::handleSyncTradesRsp(const std::shared_ptr<OrderSyncTradesResp>& rsp) {
    if (rsp->hasTimeOut() || rsp->getErrorCode() != 0) {
        return ;
    }

    for (int index = 0; index < (int)rsp->trades.size(); ++index) {
        CThostFtdcTradeField& trade = rsp->trades[index];
        try {
            processTradeSync(&trade, rsp->getRequestStartTimestampMs());
        } catch (ErrorInfo& e) {
            APPLOG_ERROR("processTradeSync error ocurrs, errorCode={}, errorMsg={}"
                            , e.errorCode, e.errorMsg);
        }
    }
}

void CTPDealManager::processTradeSync(CThostFtdcTradeField* pTrade, long sync_start_timestampms) throw(ErrorInfo) {
    CHECK(pTrade);
    position_manager_->syncTrade(pTrade);

    int64_t exec_order_id = getExecOrderIdByTradeField(pTrade, __FUNCTION__);
    if (exec_order_id <= 0) {
        return ;
    }

    SEND_MESSAGE_BEGIN(UpsideNotifySyncTradeEvent, sync_trade_event);
    sync_trade_event.__set_execOrderId(exec_order_id);
    sync_trade_event.__set_syncReqTimestampMs(sync_start_timestampms);
    sync_trade_event.__set_syncRespTimestampMs(NowInMilliSeconds());
    sync_trade_event.__set_eventCreateTimestampMs(NowInMilliSeconds());

    HostingExecTradeLegInfo trade_leg_info;
    if (trade2TradeLegInfo(pTrade, trade_leg_info)) {
        sync_trade_event.syncTradeLegInfos.push_back(trade_leg_info);
        sync_trade_event.__isset.syncTradeLegInfos = true;
    } else {
        return ;
    }

    if (trade2TradeLegContractSummary(pTrade, sync_trade_event.syncTradeLegContractSummary)) {
        sync_trade_event.__isset.syncTradeLegContractSummary = true;
    } else {
        return ;
    }

    SEND_MESSAGE_END(sync_trade_event);
}

void CTPDealManager::syncOrderStateBatch(const TSyncOrderStateBatchReq& batch_req) throw (ErrorInfo) {
    CHECK_PARAM_ERRORINFO(batch_req.__isset.accountSummary);
    OrderValidation::checkAccountSummary(batch_req.accountSummary);
    checkAccountSummaryValid(batch_req.accountSummary);

    std::shared_ptr<CThostFtdcRspUserLoginField> login_info = login_manager_->getLoginRsp();
    if (!login_info) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_REJECT_ACCOUNT_INVALID, "account is invalid");
    }

    std::shared_ptr<OrderSyncStateBatchHandler> sync_batch_handler(new OrderSyncStateBatchHandler(
            OrderSyncStateBatchHandler::CallbackFunction(
                    std::bind(&CTPDealManager::handleSyncOrderBatchStateRsp, this, std::placeholders::_1))
            , login_info
            , std::shared_ptr<TSyncOrderStateBatchReq>(new TSyncOrderStateBatchReq(batch_req))));
    int ret = dispatcher_->sendRequest(sync_batch_handler);
    if (ret != 0) {
        ErrorInfoHelper::throwError(
                TradeHostingBasicErrorCode::ERROR_EXEC_ORDER_UPSIDE_REJECT_RETCODE_OTHER
                , "OrderSyncStateBatchHandler ret=" + boost::lexical_cast<std::string>(ret));
    }
}

void CTPDealManager::handleSyncOrderBatchStateRsp(const std::shared_ptr<OrderSyncStateBatchResp>& rsp) {
    if (rsp->hasTimeOut() || rsp->getErrorCode() != 0) {
        return ;
    }

    syncOrders(rsp->resp_orders, rsp->getRequestStartTimestampMs(), true);
}

void CTPDealManager::syncOrders(const std::vector<CThostFtdcOrderField>& orders
        , long sync_start_timestampms
        , bool need_notify_position_and_self_match) {
    for (int index = 0; index < (int)orders.size(); ++index) {
        const CThostFtdcOrderField& order = orders[index];
        try {
            if (need_notify_position_and_self_match) {
                self_match_manager_->processOrder(std::shared_ptr<CThostFtdcOrderField>(new CThostFtdcOrderField(order)));
                position_manager_->syncOrder(&order);
            }
            processOrderSyncState(&order, sync_start_timestampms);
        } catch (ErrorInfo& e) {
            APPLOG_ERROR("processOrderSyncState error ocurrs, errorCode={}, errorMsg={}"
                    , e.errorCode, e.errorMsg);
        }
    }
}



