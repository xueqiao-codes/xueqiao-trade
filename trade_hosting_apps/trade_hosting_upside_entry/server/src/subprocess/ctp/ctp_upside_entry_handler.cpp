/*
 * ctp_upside_entry_handler.cpp
 *
 *  Created on: 2018年2月2日
 *      Author: wangli
 */

#include "ctp_upside_entry_handler.h"

#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>
#include <limits>
#include <memory>
#include <string.h>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/string_util.h"
#include "base/sync_call.h"
#include "base/time_helper.h"
#include "order_validation.h"
#include "thrift/protocol/TDebugProtocol.h"
#include "trade_hosting_storage_api.h"
#include "trade_hosting_basic_errors_types.h"
#include "qry_investor_handler.h"
#include "qry_trading_account_handler.h"
#include "qry_settlement_info_handler.h"
#include "ContractConvertor.h"
#include "performance_utils.h"

using namespace soldier::base;
using namespace apache::thrift;
using namespace platform::comm;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::upside::position;
using namespace xueqiao::trade::hosting::storage::api;
using namespace ctpext::framework;

CTPUpsideEntryHandler::CTPUpsideEntryHandler(const std::shared_ptr<HostingTradeAccount> trade_account)
	: trade_account_(trade_account)
	  , dispatcher_(new ctpext::framework::CTPRequestDispatcher()) {
	APPLOG_INFO("CTPUpsideEntryHandler created! trade_account={}", ThriftDebugString(*trade_account));
}

CTPUpsideEntryHandler::~CTPUpsideEntryHandler() {
}

bool CTPUpsideEntryHandler::init(const std::shared_ptr<BrokerAccessEntry>& broker_access_entry) {
	APPLOG_INFO("CTPUpsideEntryHandler init! broker_access_entry={}"
			, ThriftDebugString(*broker_access_entry));

	std::string trader_api_flow_directory("/data/trade_hosting_upside_entry/");
	trader_api_flow_directory.append(boost::lexical_cast<std::string>(trade_account_->tradeAccountId))
	                                         .append("/ctp_flow");
	if (!boost::filesystem::exists(trader_api_flow_directory)) {
	    if (!boost::filesystem::create_directories(trader_api_flow_directory)) {
	        APPLOG_ERROR("create directory {} failed!", trader_api_flow_directory);
	        return false;
	    }
	}

	if (trade_account_->loginUserName.empty()) {
	    APPLOG_INFO("trade_account_id={} login user name empty", trade_account_->tradeAccountId);
	    return false;
	}
	if (trade_account_->loginPassword.empty()) {
	    APPLOG_INFO("trade_account_id={} login password empty", trade_account_->tradeAccountId);
	    return false;
	}

	CThostFtdcTraderApi* trader_api
	        = CThostFtdcTraderApi::CreateFtdcTraderApi(trader_api_flow_directory.c_str());
	dispatcher_->setTraderApi(trader_api);
	trader_api->SubscribePrivateTopic(THOST_TERT_QUICK);
	trader_api->SubscribePublicTopic(THOST_TERT_QUICK);

	if (broker_access_entry->tradeAddresses.empty()) {
		APPLOG_ERROR("broker_id={} broker_access_id={} tradeAddresses is empty"
				, broker_access_entry->brokerId
				, broker_access_entry->entryId);
		return false;
	}
	auto it = broker_access_entry->customInfoMap.find("BROKER_ID_INFO");
	if (it == broker_access_entry->customInfoMap.end()) {
		APPLOG_ERROR("broker_id={} broker_access_id={} BROKER_ID_INFO is empty"
				, broker_access_entry->brokerId
				, broker_access_entry->entryId);
		return false;
	}
	for (auto& trade_address : broker_access_entry->tradeAddresses) {
	    std::stringstream front;
	    front << "tcp://" << trade_address.address << ":" << trade_address.port;
	    trader_api->RegisterFront((char*)front.str().c_str());
	}

	login_manager_.reset(new CTPLoginManager(dispatcher_, trade_account_, it->second));
	dispatcher_->registerNotifyCallback(login_manager_);

	data_manager_.reset(new CTPDataManager(dispatcher_, trade_account_));
	login_manager_->addListener(data_manager_);

	position_manager_.reset(new CTPPositionManager(data_manager_));
	data_manager_->addListener(position_manager_);
	dispatcher_->registerNotifyCallback(position_manager_);

	deal_manager_.reset(new CTPDealManager(dispatcher_, login_manager_, data_manager_, position_manager_));
	data_manager_->addListener(deal_manager_);
	dispatcher_->registerNotifyCallback(deal_manager_);

	rate_manager_.reset(new CTPRateManager(data_manager_, login_manager_, dispatcher_));
	data_manager_->addListener(rate_manager_);

	trader_api->Init(); // 开始流程的运转

	return true;
}

void CTPUpsideEntryHandler::allocOrderRef(HostingExecOrderRef& _return, const PlatformArgs& platformArgs) {
    login_manager_->allocOrderRef(_return);
}

void CTPUpsideEntryHandler::orderInsert(const PlatformArgs& platformArgs, const HostingExecOrder& insertOrder) {
    BLOCK_TIME_PRINT("CTPUpsideEntryHandler::orderInsert execOrderId=" + boost::lexical_cast<std::string>(insertOrder.execOrderId))
    deal_manager_->orderInsert(insertOrder);
}

void CTPUpsideEntryHandler::orderDelete(const PlatformArgs& platformArgs, const HostingExecOrder& deleteOrder) {
    BLOCK_TIME_PRINT("CTPUpsideEntryHandler::orderDelete, execOrderId=" + boost::lexical_cast<std::string>(deleteOrder.execOrderId))
    deal_manager_->orderDelete(deleteOrder);
}

void CTPUpsideEntryHandler::syncOrderState(const PlatformArgs& platformArgs, const HostingExecOrder& syncOrder) {
    APPLOG_INFO("syncOrderState syncOrder={}", syncOrder.execOrderId);
    apiQryBlockCheck();
    deal_manager_->syncOrderState(syncOrder);
}

void CTPUpsideEntryHandler::syncOrderTrades(const PlatformArgs& platformArgs, const HostingExecOrder& syncOrder) {
    APPLOG_INFO("syncOrderTrades syncOrderTrades={}", syncOrder.execOrderId);
    apiQryBlockCheck();
    deal_manager_->syncOrderTrades(syncOrder);
}

void CTPUpsideEntryHandler::syncOrderStateBatch(const PlatformArgs& platformArgs, const TSyncOrderStateBatchReq& batchReq) {
    APPLOG_INFO("syncOrderTrades batchReq={}", ThriftDebugString(batchReq));
    apiQryBlockCheck();
    deal_manager_->syncOrderStateBatch(batchReq);
}

static void QryInvestorCallback(const std::shared_ptr<QryInverstorResp>& rsp) {
    APPLOG_INFO("OnRspQryInvestor errorCode={}, errorMsg={}", rsp->getErrorCode(), rsp->getUtf8ErrorMsg());
    if (rsp->hasTimeOut() || rsp->getErrorCode() != 0 || !rsp->invertor_field) {
        return ;
    }

    APPLOG_INFO("OnRspQryInvestor InvestorID={}, BrokerID={}, InvestorGroupID={}, Telephone={}"
                , rsp->invertor_field->InvestorID, rsp->invertor_field->BrokerID
                , rsp->invertor_field->InvestorGroupID, rsp->invertor_field->Telephone);
}

void CTPUpsideEntryHandler::detectUpsideEffective(const PlatformArgs& platformArgs) {
    APPLOG_INFO("detectUpsideEffective...");
    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp = login_manager_->getLoginRsp();
    if (!login_rsp) {
        return ;
    }
    dispatcher_->sendRequest(std::shared_ptr<QryInvestorHandler>(
            new QryInvestorHandler(
                    QryInvestorHandler::CallbackFunction(std::bind(&QryInvestorCallback, std::placeholders::_1))
                    , login_rsp)));
}

void CTPUpsideEntryHandler::dumpPositionSummaries(
        std::vector<PositionSummary> & _return, const PlatformArgs& platformArgs) {
    position_manager_->dumpPositionSummaries(_return);
}

static void onCtpFundsCallback(SyncCall* sync_call
        , std::vector<TFund>* _return
        , ErrorInfo* error_info
        , const std::shared_ptr<QryTradingAccountResp>& rsp) {
    AutoSyncCallNotify auto_notify(*sync_call);
    if (rsp->getErrorCode() != 0) {
        error_info->__set_errorCode(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED);
        error_info->__set_errorMsg("qry funds failed, errorCode="
                + boost::lexical_cast<std::string>(rsp->getErrorCode())
                + ", errorMsg=" + rsp->getUtf8ErrorMsg());
        return ;
    }

    for (auto& api_fund_item : rsp->funds) {
        TFund result_fund_item;

        result_fund_item.__set_currencyNo(api_fund_item.CurrencyID);
        result_fund_item.__set_currencyChannel("DEFAULT");
        result_fund_item.__set_credit(api_fund_item.Credit);
        result_fund_item.__set_preBalance(api_fund_item.PreBalance);
        result_fund_item.__set_deposit(api_fund_item.Deposit);
        result_fund_item.__set_withdraw(api_fund_item.Withdraw);
        result_fund_item.__set_frozenMargin(api_fund_item.FrozenMargin);
        result_fund_item.__set_frozenCash(api_fund_item.FrozenCash);
        result_fund_item.__set_currMargin(api_fund_item.CurrMargin);
        result_fund_item.__set_commission(api_fund_item.Commission);
        result_fund_item.__set_closeProfit(api_fund_item.CloseProfit);
        result_fund_item.__set_positionProfit(api_fund_item.PositionProfit);
        result_fund_item.__set_available(api_fund_item.Available);

        // 上次结算保证金 + 平仓盈亏 + 持仓盈亏 - 手续费  + 入金 - 出金 -上次信用额度 - 上次质押 + 质押
        result_fund_item.__set_dynamicBenefit(
                api_fund_item.PreBalance + api_fund_item.CloseProfit + api_fund_item.PositionProfit
                - api_fund_item.Commission + api_fund_item.Deposit - api_fund_item.Withdraw
                - api_fund_item.PreCredit - api_fund_item.PreMortgage + api_fund_item.Mortgage);
        if (std::fabs(result_fund_item.dynamicBenefit) > std::numeric_limits<double>::epsilon()) {
            result_fund_item.__set_riskRate((result_fund_item.currMargin * 100)/result_fund_item.dynamicBenefit);
        }

        _return->push_back(result_fund_item);
    }
}

void CTPUpsideEntryHandler::getFunds(std::vector<TFund> & _return, const PlatformArgs& platformArgs) {
    AutoApiQryBlocking auto_blocking(this);

    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp = login_manager_->getLoginRsp();
    if (!login_rsp) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "account is invalid, not login");
    }

    int retry_count = 3;
    std::unique_ptr<SyncCall> sync_call;
    while((retry_count--) > 0) {
        sync_call.reset(new SyncCall());
        ErrorInfo error_info;
        int ret = dispatcher_->sendRequest(std::shared_ptr<QryTradingAccountHandler>(
                new QryTradingAccountHandler(
                    QryTradingAccountHandler::CallbackFunction(
                            std::bind(&onCtpFundsCallback, sync_call.get(), &_return, &error_info, std::placeholders::_1))
                , login_rsp)));
        if (ret != 0) {
            APPLOG_INFO("QryTradingAccountHandler send failed, ret = " + ret);
            std::this_thread::sleep_for(std::chrono::milliseconds(500));
            continue ;
        }
        sync_call->wait();
        if (error_info.errorCode != 0) {
            throw error_info;
        }
        return ;
    }
    ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED, "Request retries too much");
}

static void onCtpSettlementInfoCallback(
        const std::string& settlementDate
        , SyncCall* sync_call
        , TSettlementInfo* _return
        , ErrorInfo* error_info
        , const std::shared_ptr<QrySettlementInfoResp>& rsp) {
    AutoSyncCallNotify auto_notify(*sync_call);
    if (rsp->getErrorCode() != 0) {
        error_info->__set_errorCode(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED);
        error_info->__set_errorMsg("qry funds failed, errorCode="
                + boost::lexical_cast<std::string>(rsp->getErrorCode())
                + ", errorMsg=" + rsp->getUtf8ErrorMsg());
        return ;
    }

    _return->__set_settlementDate(settlementDate);
    std::string content;
    for (auto& settlement_info : rsp->settlement_infos) {
        content.append(settlement_info.Content);
    }
    if (!StringUtil::gbkToUTF8(content, _return->settlementContent)) {
        error_info->__set_errorCode(TradeHostingBasicErrorCode::ERROR_SERVER_INNER);
        error_info->__set_errorMsg("convert settlementContent to utf8 failed!");
        return;
    }
    _return->__isset.settlementContent = true;
}

void CTPUpsideEntryHandler::getSettlementInfo(
        TSettlementInfo & _return
        , const PlatformArgs& platformArgs
        , const std::string& settlementDate) {
    AutoApiQryBlocking auto_blocking(this);
    if (settlementDate.length() != 10
            || String2Timestamp(settlementDate, "%Y-%m-%d") <= 0) {
        ErrorInfoHelper::throwParamError("wrong format settlementDate");
    }

    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp = login_manager_->getLoginRsp();
    if (!login_rsp) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "account is invalid, not login");
    }

    std::string trading_day = settlementDate;
    trading_day.erase(std::remove(trading_day.begin(), trading_day.end(), '-'), trading_day.end());
    APPLOG_INFO("getSettlementInfo trading_day={}", trading_day);

    int retry_count = 3;
    std::unique_ptr<SyncCall> sync_call;
    while((retry_count--) > 0) {
        sync_call.reset(new SyncCall());
        ErrorInfo error_info;
        int ret = dispatcher_->sendRequest(std::shared_ptr<QrySettlementInfoHandler>(new QrySettlementInfoHandler(
                QrySettlementInfoHandler::CallbackFunction(
                        std::bind(&onCtpSettlementInfoCallback, settlementDate, sync_call.get(), &_return, &error_info, std::placeholders::_1))
                , login_rsp
                , trading_day)));
        if (ret != 0) {
            APPLOG_INFO("QrySettlementInfoHandler send failed, ret = " + ret);
            std::this_thread::sleep_for(std::chrono::milliseconds(500));
            continue ;
        }
        sync_call->wait();
        if (error_info.errorCode != 0) {
            throw error_info;
        }
        return ;
    }

    ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED, "Request retries too much");
}

void CTPUpsideEntryHandler::onCtpPositionsCallback(SyncCall* sync_call
        , std::vector<TNetPositionSummary>* _return
        , ::platform::comm::ErrorInfo* error_info
        , const std::shared_ptr<QryPositionResp>& rsp){
    AutoSyncCallNotify auto_notify(*sync_call);
    if (rsp->getErrorCode() != 0) {
        error_info->__set_errorCode(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED);
        error_info->__set_errorMsg("qry position failed, errorCode="
                    + boost::lexical_cast<std::string>(rsp->getErrorCode())
                    + ", errorMsg=" + rsp->getUtf8ErrorMsg());
        return ;
    }

    // 先要聚合同一合约的多头仓和空头仓
    std::map<std::string, std::vector<CThostFtdcInvestorPositionField>> instrument_positions;
    for (auto& position : rsp->positions) {
        auto it = instrument_positions.find(position.InstrumentID);
        if (it == instrument_positions.end()) {
            it = instrument_positions.insert(
                    std::pair<std::string, std::vector<CThostFtdcInvestorPositionField>>(
                            position.InstrumentID, std::vector<CThostFtdcInvestorPositionField>()
                            )).first;
        }
        CHECK(it != instrument_positions.end());
        it->second.push_back(position);
    }

    for (auto& instrument_position : instrument_positions) {
        std::shared_ptr<CThostFtdcInstrumentField> instrument_field
            = data_manager_->getInstrumentsHolder().getInstrument(instrument_position.first.c_str());
        if (!instrument_field) {
            APPLOG_ERROR("Failed to get instrument for {}", instrument_position.first); // that should not be happened
            continue;
        }

        if (instrument_field->ProductClass != THOST_FTDC_PC_Futures) {
            // 滤掉非期货类型的持仓
            continue;
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
            APPLOG_INFO("Failed to found commodity mapping for InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={}"
                    , instrument_position.first
                    , instrument_field->ExchangeID
                    , instrument_field->ProductClass
                    , instrument_field->ProductID);
            continue;
        }

        // 获取合约代码
        TechPlatformContractToSledArgs args;
        TechPlatformContractToSledResult result;
        memset(&args, 0, sizeof(TechPlatformContractToSledArgs));
        memset(&result, 0, sizeof(TechPlatformContractToSledResult));

        strcpy(args.CommonContract_.TechPlatform_Exchange_, instrument_field->ExchangeID);
        args.CommonContract_.TechPlatform_CommodityType_[0] = instrument_field->ProductClass;
        strcpy(args.CommonContract_.TechPlatform_CommodityCode_, instrument_field->ProductID);
        strcpy(args.CommonContract_.TechPlatform_ContractCode_, instrument_position.first.c_str());
        args.TechPlatform_ = TechPlatform_CTP;
        result = PlatformToSledContract(args);

        if (0 == result.MixContract_.SledContractCode_[0]) {
            APPLOG_INFO("Failed to mapping contract code for InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={}"
                    , instrument_position.first.c_str()
                    , instrument_field->ExchangeID
                    , instrument_field->ProductClass
                    , instrument_field->ProductID);
            continue;
        }

        TNetPositionSummary position_summary;
        position_summary.__set_sledExchangeCode(mapping_entry->commodity.exchangeMic);
        position_summary.__set_sledCommodityType(mapping_entry->commodity.sledCommodityType);
        position_summary.__set_sledCommodityCode(mapping_entry->commodity.sledCommodityCode);
        position_summary.__set_sledCommodityId(mapping_entry->commodity.sledCommodityId);
        position_summary.__set_sledContractCode(result.MixContract_.SledContractCode_);

        int64_t net_volume = 0;
        double position_cost = 0.0;
        for (auto& position : instrument_position.second) {
            if (position.PosiDirection == THOST_FTDC_PD_Long) {
                net_volume += position.Position;
                position_cost += position.PositionCost;
            } else if (position.PosiDirection == THOST_FTDC_PD_Short) {
                net_volume -= position.Position;
                position_cost -= position.PositionCost;
            }
        }

        position_summary.__set_netVolume(net_volume);
//        APPLOG_INFO("InstrumentID={}, position_cost={}, net_volume={}, VolumeMultiple={}",
//                instrument_position.first.c_str(), position_cost, net_volume, instrument_field->VolumeMultiple);
        if (net_volume != 0 && instrument_field->VolumeMultiple != 0) {
            position_summary.__set_averagePrice(position_cost/(net_volume * instrument_field->VolumeMultiple));
        }

        _return->push_back(position_summary);
    }
}

void CTPUpsideEntryHandler::getNetPositionSummaries(
        std::vector<TNetPositionSummary> & _return
        , const PlatformArgs& platformArgs) {
    std::shared_ptr<CThostFtdcRspUserLoginField> login_rsp = login_manager_->getLoginRsp();
    if (!login_rsp) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "account is invalid, not login");
    }

    if (data_manager_->getInstrumentsHolder().isEmpty()) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "instruments is empty, not initialized");
    }

    std::shared_ptr<CThostFtdcQryInvestorPositionField> req_field(new CThostFtdcQryInvestorPositionField());
    memset(req_field.get(), 0, sizeof(CThostFtdcQryInvestorPositionField));
    strncpy(req_field->BrokerID, login_rsp->BrokerID, sizeof(TThostFtdcBrokerIDType) - 1);
    strncpy(req_field->InvestorID, login_rsp->UserID, sizeof(TThostFtdcInvestorIDType) - 1);

    int retry_count = 3;
    std::unique_ptr<SyncCall> sync_call;
    while((retry_count--) > 0) {
        sync_call.reset(new SyncCall());
        ErrorInfo error_info;

        int ret = dispatcher_->sendRequest(std::shared_ptr<QryPositionHandler>(new QryPositionHandler(
            QryPositionHandler::CallbackFunction(
                    std::bind(&CTPUpsideEntryHandler::onCtpPositionsCallback
                            , this, sync_call.get(), &_return, &error_info, std::placeholders::_1))
            , req_field)));
        if (ret != 0) {
            APPLOG_INFO("QryPositionHandler send failed, ret = " + ret);
            std::this_thread::sleep_for(std::chrono::milliseconds(500));
            continue ;
        }
        sync_call->wait();
        if (error_info.errorCode != 0) {
            throw error_info;
        }

        return ;
    }

    ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED, "Request retries too much");
}

static void fillCommodityRate(TPositionCommodityRate& commodity_rate, const std::shared_ptr<CTPProductRate>& product_rate) {
    if (product_rate->exchange_margin || product_rate->instrument_margin) {
        commodity_rate.__isset.marginRate = true;
        commodity_rate.marginRate.__isset.ctpMarginRate = true;

        if (product_rate->exchange_margin) {
            commodity_rate.marginRate.ctpMarginRate.__isset.exchangeMarginRate = true;
            commodity_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_longMarginRatioByMoney(
                    product_rate->exchange_margin->LongMarginRatioByMoney);
            commodity_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_longMarginRatioByVolume(
                    product_rate->exchange_margin->LongMarginRatioByVolume);
            commodity_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_shortMarginRatioByMoney(
                    product_rate->exchange_margin->ShortMarginRatioByMoney);
            commodity_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_shortMarginRatioByVolume(
                    product_rate->exchange_margin->ShortMarginRatioByVolume);
        }

        if (product_rate->instrument_margin) {
            commodity_rate.marginRate.ctpMarginRate.__isset.instrumentMarginRate = true;
            commodity_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_longMarginRatioByMoney(
                    product_rate->instrument_margin->LongMarginRatioByMoney);
            commodity_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_longMarginRatioByVolume(
                    product_rate->instrument_margin->LongMarginRatioByVolume);
            commodity_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_shortMarginRatioByMoney(
                    product_rate->instrument_margin->ShortMarginRatioByMoney);
            commodity_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_shortMarginRatioByVolume(
                    product_rate->instrument_margin->ShortMarginRatioByVolume);
            commodity_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_isRelative(
                    (product_rate->instrument_margin->IsRelative != 0));
        }
    }

    if (product_rate->commission) {
        commodity_rate.__isset.commissionRate = true;
        commodity_rate.commissionRate.__isset.ctpCommissionRate = true;

        commodity_rate.commissionRate.ctpCommissionRate.__set_closeRatioByMoney(
                product_rate->commission->CloseRatioByMoney);
        commodity_rate.commissionRate.ctpCommissionRate.__set_closeRatioByVolume(
                product_rate->commission->CloseRatioByVolume);
        commodity_rate.commissionRate.ctpCommissionRate.__set_closeTodayRatioByMoney(
                product_rate->commission->CloseTodayRatioByMoney);
        commodity_rate.commissionRate.ctpCommissionRate.__set_closeTodayRatioByVolume(
                product_rate->commission->CloseTodayRatioByVolume);
        commodity_rate.commissionRate.ctpCommissionRate.__set_openRatioByMoney(
                product_rate->commission->OpenRatioByMoney);
        commodity_rate.commissionRate.ctpCommissionRate.__set_openRatioByVolume(
                product_rate->commission->OpenRatioByVolume);
    }
}

static void fillContractRate(TPositionContractRate& contract_rate, const std::shared_ptr<CTPContractRate>& ctp_contract_rate) {
    if (ctp_contract_rate->exchange_margin || ctp_contract_rate->instrument_margin) {
        contract_rate.__isset.marginRate = true;
        contract_rate.marginRate.__isset.ctpMarginRate = true;

        if (ctp_contract_rate->exchange_margin) {
            contract_rate.marginRate.ctpMarginRate.__isset.exchangeMarginRate = true;
            contract_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_longMarginRatioByMoney(
                    ctp_contract_rate->exchange_margin->LongMarginRatioByMoney);
            contract_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_longMarginRatioByVolume(
                    ctp_contract_rate->exchange_margin->LongMarginRatioByVolume);
            contract_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_shortMarginRatioByMoney(
                    ctp_contract_rate->exchange_margin->ShortMarginRatioByMoney);
            contract_rate.marginRate.ctpMarginRate.exchangeMarginRate.__set_shortMarginRatioByVolume(
                    ctp_contract_rate->exchange_margin->ShortMarginRatioByVolume);
        }

        if (ctp_contract_rate->instrument_margin) {
            contract_rate.marginRate.ctpMarginRate.__isset.instrumentMarginRate = true;
            contract_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_longMarginRatioByMoney(
                    ctp_contract_rate->instrument_margin->LongMarginRatioByMoney);
            contract_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_longMarginRatioByVolume(
                    ctp_contract_rate->instrument_margin->LongMarginRatioByVolume);
            contract_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_shortMarginRatioByMoney(
                    ctp_contract_rate->instrument_margin->ShortMarginRatioByMoney);
            contract_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_shortMarginRatioByVolume(
                    ctp_contract_rate->instrument_margin->ShortMarginRatioByVolume);
            contract_rate.marginRate.ctpMarginRate.instrumentMarginRate.__set_isRelative(
                    (ctp_contract_rate->instrument_margin->IsRelative != 0));
        }
    }

    if (ctp_contract_rate->commission) {
        contract_rate.__isset.commissionRate = true;
        contract_rate.commissionRate.__isset.ctpCommissionRate = true;

        contract_rate.commissionRate.ctpCommissionRate.__set_closeRatioByMoney(
                ctp_contract_rate->commission->CloseRatioByMoney);
        contract_rate.commissionRate.ctpCommissionRate.__set_closeRatioByVolume(
                ctp_contract_rate->commission->CloseRatioByVolume);
        contract_rate.commissionRate.ctpCommissionRate.__set_closeTodayRatioByMoney(
                ctp_contract_rate->commission->CloseTodayRatioByMoney);
        contract_rate.commissionRate.ctpCommissionRate.__set_closeTodayRatioByVolume(
                ctp_contract_rate->commission->CloseTodayRatioByVolume);
        contract_rate.commissionRate.ctpCommissionRate.__set_openRatioByMoney(
                ctp_contract_rate->commission->OpenRatioByMoney);
        contract_rate.commissionRate.ctpCommissionRate.__set_openRatioByVolume(
                ctp_contract_rate->commission->OpenRatioByVolume);
    }

}

void CTPUpsideEntryHandler::getPositionRateDetails(
        TPositionRateDetails& _return
        , const PlatformArgs& platformArgs) {
    std::shared_ptr<std::map<std::string, std::shared_ptr<CTPProductRate>>> product_rates
            = rate_manager_->getRates();
    if (!product_rates) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "Rate Details has not initialized!");
    }

    _return.__set_techPlatform(BrokerTechPlatform::TECH_CTP);
    _return.__set_tradeAccountId(login_manager_->getTradeAccount()->tradeAccountId);

    _return.__isset.commodityRates = true;

    for (auto& product_it : (*product_rates)) {
        std::shared_ptr<CTPProductRate> product_rate = product_it.second;

        CommodityMappingOption mapping_option;
        mapping_option.sledBrokerId = 0;
        mapping_option.brokerExchangeCode = product_rate->exchange_id;
        mapping_option.brokerCommodityType.append(&(product_rate->product_class), 1);
        mapping_option.brokerCommodityCode = product_rate->product_id;
        mapping_option.brokerTechPlatform = ::xueqiao::contract::standard::TechPlatform::CTP;

        std::shared_ptr<CommodityMappingEntry> mapping_entry;
        uint64_t mapping_time_ns = 0;
        {
            S_BLOCK_TIMER(mapping_time_ns);
            mapping_entry = data_manager_->getContractMapping().getCommodityMapping(mapping_option);
        }
        APPLOG_DEBUG("Get Commodity for {}, escaped time={}ns", mapping_option, mapping_time_ns);


        if (!mapping_entry) {
            APPLOG_INFO("Failed to found commodity mapping for ExchangeID={}, ProductClass={}, ProductID={}"
                    , product_rate->exchange_id
                    , product_rate->product_class
                    , product_rate->product_class);
            continue;
        }

        _return.commodityRates.push_back(TPositionCommodityRate());

        TPositionCommodityRate& commodity_rate = _return.commodityRates.back();
        commodity_rate.__set_sledExchangeMic(mapping_entry->commodity.exchangeMic);
        commodity_rate.__set_sledCommodityType(mapping_entry->commodity.sledCommodityType);
        commodity_rate.__set_sledCommodityCode(mapping_entry->commodity.sledCommodityCode);
        commodity_rate.__set_sledCommodityId(mapping_entry->commodity.sledCommodityId);
        fillCommodityRate(commodity_rate, product_rate);

        commodity_rate.__isset.contractRates = true;
        for (auto& contract_it : product_rate->contract_rates) {
            std::shared_ptr<CTPContractRate> ctp_contract_rate = contract_it.second;

            TechPlatformContractToSledArgs args;
            TechPlatformContractToSledResult result;
            memset(&args, 0, sizeof(TechPlatformContractToSledArgs));
            memset(&result, 0, sizeof(TechPlatformContractToSledResult));

            strcpy(args.CommonContract_.TechPlatform_Exchange_, product_rate->exchange_id.c_str());
            args.CommonContract_.TechPlatform_CommodityType_[0] = product_rate->product_class;
            strcpy(args.CommonContract_.TechPlatform_CommodityCode_, product_rate->product_id.c_str());
            strcpy(args.CommonContract_.TechPlatform_ContractCode_, ctp_contract_rate->instrument_id.c_str());
            args.TechPlatform_ = TechPlatform_CTP;
            result = PlatformToSledContract(args);

            if (0 == result.MixContract_.SledContractCode_[0]) {
                APPLOG_INFO("Failed to mapping contract code for InstrumentID={}, ExchangeID={}, ProductClass={}, ProductID={}"
                        , ctp_contract_rate->instrument_id
                        , product_rate->exchange_id
                        , product_rate->product_class
                        , product_rate->product_id);
                continue;
            }

            TPositionContractRate contract_rate;
            contract_rate.__set_sledContractCode(result.MixContract_.SledContractCode_);
            fillContractRate(contract_rate, ctp_contract_rate);

            commodity_rate.contractRates[contract_rate.sledContractCode] = contract_rate;
        }
    }
}
