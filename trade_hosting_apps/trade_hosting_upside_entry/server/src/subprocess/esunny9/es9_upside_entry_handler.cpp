/*
 * es9_upside_entry_handler.cpp
 *
 *  Created on: 2018年4月12日
 *      Author: 44385
 */
#include "es9_upside_entry_handler.h"
#include <boost/filesystem.hpp>
#include <boost/lexical_cast.hpp>
#include <string.h>
#include <memory>
#include "base/app_log.h"
#include "base/code_defense.h"
#include "base/string_util.h"
#include "iTapAPIError.h"
#include "order_validation.h"
#include "thrift/protocol/TDebugProtocol.h"
#include "trade_hosting_storage_api.h"
#include "trade_hosting_basic_constants.h"
#include "errorinfo_helper.h"
#include "es9_qry_account_handler.h"
#include "es9_qry_bill_handler.h"
#include "es9_qry_fund_handler.h"
#include "ContractConvertor.h"
#include "performance_utils.h"

using namespace soldier::base;
using namespace apache::thrift;
using namespace platform::comm;
using namespace es9ext::framework;
using namespace xueqiao::trade::hosting;
using namespace xueqiao::trade::hosting::upside::entry;
using namespace xueqiao::trade::hosting::upside::position;
using namespace xueqiao::trade::hosting::storage::api;


Es9UpsideEntryHandler::Es9UpsideEntryHandler(const std::shared_ptr<HostingTradeAccount> trade_account)
    : trade_account_(trade_account)
      , request_dispatcher_(new Es9RequestDispatcher()){
    APPLOG_INFO("Es9UpsideEntryHandler created! trade_account={}", ThriftDebugString(*trade_account));
}

Es9UpsideEntryHandler::~Es9UpsideEntryHandler() {

}

bool Es9UpsideEntryHandler::init(const std::shared_ptr<BrokerAccessEntry>& broker_access_entry) {
    APPLOG_INFO("Es9UpsideEntryHandler init! broker_access_entry={}", ThriftDebugString(*broker_access_entry));

    HostingTradeAccountAPI::InvalidDescription description;
    description.api_ret_code = 0;
    description.invalid_error_code = TradeHostingBasicErrorCode::ERROR_TRADE_ACCOUNT_INVALID_OTHER;

    std::string auth_code = trade_account_->accountProperties[g_trade_hosting_basic_constants.ESUNNY9_AUTHCODE_PROPKEY];
    if (auth_code.empty()) {
        APPLOG_ERROR("account's auth code is empty");
        description.invalid_reason = "授权码为空";
        HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
        return false;
    }

    if (auth_code.length() >= sizeof(ITapTrade::TAPIAUTHCODE)) {
        APPLOG_ERROR("account's auth code is too long");
        description.invalid_reason = "授权码过长";
        HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
        return false;
    }

    ITapTrade::TapAPIApplicationInfo app_info;
    memset(&app_info, 0, sizeof(ITapTrade::TapAPIApplicationInfo));
    strcpy(app_info.AuthCode, auth_code.c_str());

    std::stringstream operation_log_path;
    operation_log_path << "/data/applog/apps/trade_hosting_upside_entry/subprocess/"
                       << trade_account_->tradeAccountId
                       << "/es9_log";
    strcpy(app_info.KeyOperationLogPath, operation_log_path.str().c_str());

    ITapTrade::TAPIINT32 ret = 0;
    ITapTrade::ITapTradeAPI * trader_api = CreateITapTradeAPI(&app_info, ret);
    if (ret != ITapTrade::TAPIERROR_SUCCEED) {
        APPLOG_ERROR("failed to create ITapTradeAPI instance, ret={}, errorMsg={}", ret, es9ErrorMsg(ret));
        description.invalid_reason = "初始化API实例失败," + es9ErrorMsg(ret);
        HostingTradeAccountAPI::setTradeAccountInvalid(trade_account_->tradeAccountId, description);
        return false;
    }

    request_dispatcher_->setTraderApi(trader_api);

    if (broker_access_entry->tradeAddresses.empty()) {
        APPLOG_ERROR("trade address not config for brokerId={}, brokerAccessId={}, name={}"
                , broker_access_entry->brokerId, broker_access_entry->entryId
                , broker_access_entry->accessName);
        return false;
    }

    login_manager_.reset(new Es9LoginManager(request_dispatcher_, trade_account_));
    request_dispatcher_->registerNotifyCallback(login_manager_);

    data_manager_.reset(new Es9DataManager(request_dispatcher_, trade_account_));
    login_manager_->addListener(data_manager_);

    deal_manager_.reset(new Es9DealManager(request_dispatcher_, login_manager_, data_manager_));
    request_dispatcher_->registerNotifyCallback(deal_manager_);
    data_manager_->addListener(deal_manager_);

    rate_manager_.reset(new Es9RateManager(login_manager_, request_dispatcher_));
    data_manager_->addListener(rate_manager_);


    // TODO 选择地址，选取一个最优的地址
    int choosed_index = 0;

    std::string addr = broker_access_entry->tradeAddresses[choosed_index].address.c_str();
    int16_t port = (int16_t)broker_access_entry->tradeAddresses[choosed_index].port;

    APPLOG_INFO("Es9 trader api using addr={}, port={}", addr, port);
    trader_api->SetHostAddress(addr.c_str(), port);

    login_manager_->startLogin();

    logger_cleaner_.reset(new Es9LoggerCleaner(operation_log_path.str(), "SocketBin_"));

    return true;
}

void Es9UpsideEntryHandler::allocOrderRef(
        HostingExecOrderRef& _return, const PlatformArgs& platformArgs) {
    deal_manager_->allocOrderRef(_return);
}

void Es9UpsideEntryHandler::orderInsert(
        const PlatformArgs& platformArgs, const HostingExecOrder& insertOrder) {
    BLOCK_TIME_PRINT("Es9UpsideEntryHandler::orderInsert execOrderId=" + boost::lexical_cast<std::string>(insertOrder.execOrderId))
    deal_manager_->orderInsert(insertOrder);

}

void Es9UpsideEntryHandler::orderDelete(
        const PlatformArgs& platformArgs, const HostingExecOrder& deleteOrder) {
    BLOCK_TIME_PRINT("Es9UpsideEntryHandler::orderDelete execOrderId=" + boost::lexical_cast<std::string>(deleteOrder.execOrderId))
    deal_manager_->orderDelete(deleteOrder);
}

void Es9UpsideEntryHandler::syncOrderState(
        const PlatformArgs& platformArgs, const HostingExecOrder& syncOrder) {
    APPLOG_INFO("syncOrderState syncOrder={}", syncOrder.execOrderId);
    apiQryBlockCheck();
    deal_manager_->syncOrderState(syncOrder);
}

void Es9UpsideEntryHandler::syncOrderTrades(
        const PlatformArgs& platformArgs, const HostingExecOrder& syncOrder) {
    APPLOG_INFO("syncOrderTrades syncOrderTrades={}", syncOrder.execOrderId);
    apiQryBlockCheck();
    deal_manager_->syncOrderTrades(syncOrder);
}

void Es9UpsideEntryHandler::syncOrderStateBatch(
        const PlatformArgs& platformArgs, const TSyncOrderStateBatchReq& batchReq) {
    APPLOG_INFO("syncOrderTrades batchReq={}", ThriftDebugString(batchReq));
    apiQryBlockCheck();
    deal_manager_->syncOrderStateBatch(batchReq);
}

static void detectUpsideEffectiveResp(const std::shared_ptr<Es9QryAccountResp>& qry_account_resp) {
    APPLOG_INFO("detectUpsideEffectiveResp errorCode={}, errorMsg={}"
            , qry_account_resp->getErrorCode(), qry_account_resp->getUtf8ErrorMsg());
}

void Es9UpsideEntryHandler::detectUpsideEffective(const PlatformArgs& platformArgs) {
    APPLOG_INFO("detectUpsideEffective...");
    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        return ;
    }

    request_dispatcher_->sendRequest(std::shared_ptr<Es9QryAccountHandler>(
            new Es9QryAccountHandler(std::bind(&detectUpsideEffectiveResp, std::placeholders::_1))
            ));
}

void Es9UpsideEntryHandler::dumpPositionSummaries(
        std::vector<PositionSummary> & _return, const PlatformArgs& platformArgs) {
    ErrorInfoHelper::throwNotSupportedError("not supported for es9");
}

static void onEs9FundsCallback(SyncCall* sync_call
        , std::vector<TFund>* _return
        , ErrorInfo* error_info
        , const std::shared_ptr<Es9QryFundResp>& rsp) {
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

        if (0 == api_fund_item.CurrencyNo[0]) {
            // 币种代码
            continue;
        }
        result_fund_item.__set_currencyNo(api_fund_item.CurrencyNo);
        result_fund_item.__set_currencyChannel(api_fund_item.CurrencyGroupNo);

        result_fund_item.__set_credit(api_fund_item.AuthMoney);
        result_fund_item.__set_preBalance(api_fund_item.PreEquity);
        result_fund_item.__set_deposit(api_fund_item.CashInValue);
        result_fund_item.__set_withdraw(api_fund_item.CashOutValue);
        result_fund_item.__set_frozenMargin(api_fund_item.FrozenDeposit);
        result_fund_item.__set_frozenCash(api_fund_item.FrozenFee);
        result_fund_item.__set_currMargin(api_fund_item.AccountIntialMargin);
        result_fund_item.__set_commission(api_fund_item.AccountFee);
        result_fund_item.__set_closeProfit(api_fund_item.CloseProfit);
        result_fund_item.__set_positionProfit(api_fund_item.PositionProfit);
        result_fund_item.__set_available(api_fund_item.Available);

        result_fund_item.__set_dynamicBenefit(api_fund_item.Equity);
        if (std::fabs(result_fund_item.dynamicBenefit) > std::numeric_limits<double>::epsilon()) {
            result_fund_item.__set_riskRate((result_fund_item.currMargin * 100)/result_fund_item.dynamicBenefit);
        }

        _return->push_back(result_fund_item);
    }
}


void Es9UpsideEntryHandler::getFunds(std::vector<TFund> & _return
        , const PlatformArgs& platformArgs) {
    AutoApiQryBlocking auto_blocking(this);
    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "account is invalid, not login");
    }

    if (login_rsp_data->account_infos.empty()) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "no account number");
    }

    int retry_count = 3;
    std::unique_ptr<SyncCall> sync_call;
    while((retry_count--) > 0) {
        sync_call.reset(new SyncCall());
        ErrorInfo error_info;

        int ret = request_dispatcher_->sendRequest(std::shared_ptr<Es9QryFundHandler>(
                new Es9QryFundHandler(
                        Es9QryFundHandler::CallbackFunction(
                                std::bind(&onEs9FundsCallback, sync_call.get(), &_return, &error_info, std::placeholders::_1))
                        , login_rsp_data->account_infos[0].AccountNo
                )));
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

static void onEs9BillCallback(
        const std::string& settlementDate
        , SyncCall* sync_call
        , TSettlementInfo* _return
        , ErrorInfo* error_info
        , const std::shared_ptr<Es9QryBillResp>& rsp) {
    AutoSyncCallNotify auto_notify(*sync_call);
    if (rsp->getErrorCode() != 0) {
        error_info->__set_errorCode(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED);
        error_info->__set_errorMsg("qry bill failed, errorCode="
                + boost::lexical_cast<std::string>(rsp->getErrorCode())
                + ", errorMsg=" + rsp->getUtf8ErrorMsg());
        return ;
    }

    _return->__set_settlementDate(settlementDate);
    _return->__set_settlementContent(rsp->bill_content);
}

void Es9UpsideEntryHandler::getSettlementInfo(TSettlementInfo & _return
        , const PlatformArgs& platformArgs
        , const std::string& settlementDate) {
    AutoApiQryBlocking auto_blocking(this);
    if (settlementDate.length() != 10
            || String2Timestamp(settlementDate, "%Y-%m-%d") <= 0) {
        ErrorInfoHelper::throwParamError("wrong format settlementDate");
    }

    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "account is invalid, not login");
    }

    if (login_rsp_data->account_infos.empty()) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "no account number");
    }

    int retry_count = 3;
    std::unique_ptr<SyncCall> sync_call;
    while((retry_count--) > 0) {
        sync_call.reset(new SyncCall());
        ErrorInfo error_info;
        int ret = request_dispatcher_->sendRequest(std::shared_ptr<Es9QryBillHandler>(new Es9QryBillHandler(
                Es9QryBillHandler::CallbackFunction(
                            std::bind(&onEs9BillCallback, settlementDate, sync_call.get(), &_return, &error_info, std::placeholders::_1))
                    , login_rsp_data->login_rsp_info.UserNo
                    , settlementDate)));
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

void Es9UpsideEntryHandler::onEs9PositionCallback(
        soldier::base::SyncCall* sync_call
        , std::vector<TNetPositionSummary>* _return
        , ::platform::comm::ErrorInfo* error_info
        , const std::shared_ptr<Es9QryPositionResp>& rsp) {
    AutoSyncCallNotify auto_notify(*sync_call);
    if (rsp->getErrorCode() != 0) {
        error_info->__set_errorCode(TradeHostingBasicErrorCode::ERROR_UPSIDE_CALL_FAILED);
        error_info->__set_errorMsg("qry bill failed, errorCode="
                + boost::lexical_cast<std::string>(rsp->getErrorCode())
                + ", errorMsg=" + rsp->getUtf8ErrorMsg());
        return ;
    }

    // 按照合约进行持仓的聚类
    std::map<std::string, std::vector<ITapTrade::TapAPIPositionInfo>> contract_positions;
    for (auto& position : rsp->positions) {
        std::string contract_key;
        contract_key.append(position.ExchangeNo)
                    .append("|").append(1, position.CommodityType)
                    .append("|").append(position.CommodityNo)
                    .append("|").append(position.ContractNo);

        auto it = contract_positions.find(contract_key);
        if (it == contract_positions.end()) {
            it = contract_positions.insert(
                    std::pair<std::string, std::vector<ITapTrade::TapAPIPositionInfo>>(
                            contract_key, std::vector<ITapTrade::TapAPIPositionInfo>()
                    )).first;
        }
        CHECK(it != contract_positions.end());
        it->second.push_back(position);
    }

    for (auto& contract_position : contract_positions) {
        CHECK(!contract_position.second.empty());
        ITapTrade::TapAPIPositionInfo& position_represent = contract_position.second[0];

        CommodityMappingOption mapping_option;
        mapping_option.sledBrokerId = 0;
        mapping_option.brokerExchangeCode = position_represent.ExchangeNo;
        mapping_option.brokerCommodityType.append(1, position_represent.CommodityType);
        mapping_option.brokerCommodityCode = position_represent.CommodityNo;
        mapping_option.brokerTechPlatform = ::xueqiao::contract::standard::TechPlatform::ESUNNY;

        std::shared_ptr<CommodityMappingEntry> mapping_entry;
        mapping_entry = data_manager_->getContractMapping().getCommodityMapping(mapping_option);
        if (!mapping_entry) {
            APPLOG_INFO("Failed to found commodity mapping for ExchangeNo={}, CommodityType={}, CommodityNo={}"
                    , position_represent.ExchangeNo
                    , position_represent.CommodityType
                    , position_represent.CommodityNo);
            continue;
        }

        // 获取合约代码
        TechPlatformContractToSledArgs args;
        TechPlatformContractToSledResult result;
        memset(&args, 0, sizeof(TechPlatformContractToSledArgs));
        memset(&result, 0, sizeof(TechPlatformContractToSledResult));

        strcpy(args.CommonContract_.TechPlatform_Exchange_, position_represent.ExchangeNo);
        args.CommonContract_.TechPlatform_CommodityType_[0] = position_represent.CommodityType;
        strcpy(args.CommonContract_.TechPlatform_CommodityCode_, position_represent.CommodityNo);
        strcpy(args.CommonContract_.TechPlatform_ContractCode_, position_represent.ContractNo);
        args.TechPlatform_ = TechPlatform_ESUNNY;
        result = PlatformToSledContract(args);

        if (0 == result.MixContract_.SledContractCode_[0]) {
            APPLOG_WARN("[WARNING]Failed to mapping contract code for ExchangeNo={}, CommodityType={}, CommodityNo={}, ContractNo={}"
                    , position_represent.ExchangeNo, position_represent.CommodityType
                    , position_represent.CommodityNo, position_represent.ContractNo);
            continue;
        }

        TNetPositionSummary position_summary;
        position_summary.__set_sledExchangeCode(mapping_entry->commodity.exchangeMic);
        position_summary.__set_sledCommodityType(mapping_entry->commodity.sledCommodityType);
        position_summary.__set_sledCommodityCode(mapping_entry->commodity.sledCommodityCode);
        position_summary.__set_sledCommodityId(mapping_entry->commodity.sledCommodityId);
        position_summary.__set_sledContractCode(result.MixContract_.SledContractCode_);

        int64_t net_volume = 0;
        double total_fee = 0.0;

        for (auto& position : contract_position.second) {
            if (position.PositionQty <= 0) {
                continue;
            }

            if (position.MatchSide == ITapTrade::TAPI_SIDE_BUY) {
                net_volume += position.PositionQty;
                total_fee += (position.PositionQty * position.PositionPrice);
            } else if (position.MatchSide == ITapTrade::TAPI_SIDE_SELL) {
                net_volume -= position.PositionQty;
                total_fee -= (position.PositionQty * position.PositionPrice);
            }
        }

        position_summary.__set_netVolume(net_volume);
        position_summary.__set_averagePrice(total_fee/net_volume);

        _return->push_back(position_summary);
    }
}

void Es9UpsideEntryHandler::getNetPositionSummaries(
        std::vector<TNetPositionSummary> & _return
        , const PlatformArgs& platformArgs) {
    AutoApiQryBlocking auto_blocking(this);
    std::shared_ptr<LoginRspData> login_rsp_data = login_manager_->getLoginRspData();
    if (!login_rsp_data) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "account is invalid, not login");
    }

    if (login_rsp_data->account_infos.empty()) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "no account number");
    }

    int retry_count = 3;
    std::unique_ptr<SyncCall> sync_call;
    while((retry_count--) > 0) {
        sync_call.reset(new SyncCall());
        ErrorInfo error_info;

        int ret = request_dispatcher_->sendRequest(std::shared_ptr<Es9QryPositionHandler>(new Es9QryPositionHandler(
                Es9QryPositionHandler::CallbackFunction(
                        std::bind(&Es9UpsideEntryHandler::onEs9PositionCallback, this
                                , sync_call.get(), &_return, &error_info, std::placeholders::_1) )
                , login_rsp_data->account_infos[0].AccountNo)));
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

static void fillCommodityRate(TPositionCommodityRate& commodity_rate
        , const std::shared_ptr<Es9CommodityRate>& es9_commodity_rate) {
    if (es9_commodity_rate->account_fee) {
        commodity_rate.__isset.commissionRate = true;
        commodity_rate.commissionRate.__isset.es9CommissionRate = true;

        commodity_rate.commissionRate.es9CommissionRate.__set_calculateMode(
                es9_commodity_rate->account_fee->CalculateMode);
        commodity_rate.commissionRate.es9CommissionRate.__set_closeTodayFee(
                es9_commodity_rate->account_fee->CloseTodayFee);
        commodity_rate.commissionRate.es9CommissionRate.__set_currencyGroupNo(
                es9_commodity_rate->account_fee->CurrencyGroupNo);
        commodity_rate.commissionRate.es9CommissionRate.__set_currencyNo(
                es9_commodity_rate->account_fee->CurrencyNo);
        commodity_rate.commissionRate.es9CommissionRate.__set_openCloseFee(
                es9_commodity_rate->account_fee->OpenCloseFee);
    }

    if (es9_commodity_rate->account_margin) {
        commodity_rate.__isset.marginRate = true;
        commodity_rate.marginRate.__isset.es9MarginRate = true;

        commodity_rate.marginRate.es9MarginRate.__set_calculateMode(
                es9_commodity_rate->account_margin->CalculateMode);
        commodity_rate.marginRate.es9MarginRate.__set_currencyGroupNo(
                es9_commodity_rate->account_margin->CurrencyGroupNo);
        commodity_rate.marginRate.es9MarginRate.__set_currencyNo(
                es9_commodity_rate->account_margin->CurrencyNo);
        commodity_rate.marginRate.es9MarginRate.__set_initialMargin(
                es9_commodity_rate->account_margin->InitialMargin);
        commodity_rate.marginRate.es9MarginRate.__set_lockMargin(
                es9_commodity_rate->account_margin->LockMargin);
        commodity_rate.marginRate.es9MarginRate.__set_maintenanceMargin(
                es9_commodity_rate->account_margin->MaintenanceMargin);
        commodity_rate.marginRate.es9MarginRate.__set_sellInitialMargin(
                es9_commodity_rate->account_margin->SellInitialMargin);
        commodity_rate.marginRate.es9MarginRate.__set_sellMaintenanceMargin(
                es9_commodity_rate->account_margin->SellMaintenanceMargin);
    }
}

static void fillContractRate(TPositionContractRate& contract_rate
        , const Es9ContractRate& es9_contract_rate) {
    if (es9_contract_rate.account_margin) {
        contract_rate.__isset.marginRate = true;
        contract_rate.marginRate.__isset.es9MarginRate = true;

        contract_rate.marginRate.es9MarginRate.__set_calculateMode(
                es9_contract_rate.account_margin->CalculateMode);
        contract_rate.marginRate.es9MarginRate.__set_currencyGroupNo(
                es9_contract_rate.account_margin->CurrencyGroupNo);
        contract_rate.marginRate.es9MarginRate.__set_currencyNo(
                es9_contract_rate.account_margin->CurrencyNo);
        contract_rate.marginRate.es9MarginRate.__set_initialMargin(
                es9_contract_rate.account_margin->InitialMargin);
        contract_rate.marginRate.es9MarginRate.__set_lockMargin(
                es9_contract_rate.account_margin->LockMargin);
        contract_rate.marginRate.es9MarginRate.__set_maintenanceMargin(
                es9_contract_rate.account_margin->MaintenanceMargin);
        contract_rate.marginRate.es9MarginRate.__set_sellInitialMargin(
                es9_contract_rate.account_margin->SellInitialMargin);
        contract_rate.marginRate.es9MarginRate.__set_sellMaintenanceMargin(
                es9_contract_rate.account_margin->SellMaintenanceMargin);
    }
}

void Es9UpsideEntryHandler::getPositionRateDetails(
        TPositionRateDetails& _return
        , const PlatformArgs& platformArgs) {
    std::shared_ptr<std::map<std::string, std::shared_ptr<Es9CommodityRate>>> commodity_rates
        = rate_manager_->getRates();
    if (!commodity_rates) {
        ErrorInfoHelper::throwError(TradeHostingBasicErrorCode::ERROR_OPERATION_FORBIDDEN, "Rate Details has not initialized!");
    }

    _return.__set_techPlatform(BrokerTechPlatform::TECH_ESUNNY_9);
    _return.__set_tradeAccountId(login_manager_->getTradeAccount()->tradeAccountId);

    _return.__isset.commodityRates = true;

    for (auto& product_it : (*commodity_rates)) {
        std::shared_ptr<Es9CommodityRate> es9_commodity_rate = product_it.second;

        CommodityMappingOption mapping_option;
        mapping_option.sledBrokerId = 0;
        mapping_option.brokerExchangeCode = es9_commodity_rate->exchange_no;
        mapping_option.brokerCommodityType.append(1, es9_commodity_rate->commodity_type);
        mapping_option.brokerCommodityCode = es9_commodity_rate->commodity_no;
        mapping_option.brokerTechPlatform = ::xueqiao::contract::standard::TechPlatform::ESUNNY;

        std::shared_ptr<CommodityMappingEntry> mapping_entry;
        uint64_t mapping_time_ns = 0;
        {
            S_BLOCK_TIMER(mapping_time_ns);
            mapping_entry = data_manager_->getContractMapping().getCommodityMapping(mapping_option);
        }
        APPLOG_DEBUG("Get Commodity for {}, escaped time={}ns", mapping_option, mapping_time_ns);

        if (!mapping_entry) {
            APPLOG_INFO("Failed to found commodity mapping for ExchangeNo={}, CommodityType={}, CommodityNo={}"
                    , es9_commodity_rate->exchange_no
                    , es9_commodity_rate->commodity_type
                    , es9_commodity_rate->commodity_no);
            continue;
        }

        _return.commodityRates.push_back(TPositionCommodityRate());

        TPositionCommodityRate& commodity_rate = _return.commodityRates.back();
        commodity_rate.__set_sledExchangeMic(mapping_entry->commodity.exchangeMic);
        commodity_rate.__set_sledCommodityType(mapping_entry->commodity.sledCommodityType);
        commodity_rate.__set_sledCommodityCode(mapping_entry->commodity.sledCommodityCode);
        commodity_rate.__set_sledCommodityId(mapping_entry->commodity.sledCommodityId);
        fillCommodityRate(commodity_rate, es9_commodity_rate);

        commodity_rate.__isset.contractRates = true;
        for (auto& es9_contract_rate : es9_commodity_rate->contract_rates) {

            TechPlatformContractToSledArgs args;
            TechPlatformContractToSledResult result;
            memset(&args, 0, sizeof(TechPlatformContractToSledArgs));
            memset(&result, 0, sizeof(TechPlatformContractToSledResult));

            strcpy(args.CommonContract_.TechPlatform_Exchange_, es9_commodity_rate->exchange_no.c_str());
            args.CommonContract_.TechPlatform_CommodityType_[0] = es9_commodity_rate->commodity_type;
            strcpy(args.CommonContract_.TechPlatform_CommodityCode_, es9_commodity_rate->commodity_no.c_str());
            strcpy(args.CommonContract_.TechPlatform_ContractCode_, es9_contract_rate.contract_no.c_str());
            args.TechPlatform_ = TechPlatform_ESUNNY;
            result = PlatformToSledContract(args);

            if (0 == result.MixContract_.SledContractCode_[0]) {
                APPLOG_INFO("Failed to mapping contract code for ContractNo={}, ExchangeNo={}, CommodityType={}, CommodityNo={}"
                        , es9_contract_rate.contract_no
                        , es9_commodity_rate->exchange_no
                        , es9_commodity_rate->commodity_type
                        , es9_commodity_rate->commodity_no);
                continue;
            }

            TPositionContractRate contract_rate;
            contract_rate.__set_sledContractCode(result.MixContract_.SledContractCode_);
            fillContractRate(contract_rate, es9_contract_rate);

            commodity_rate.contractRates[contract_rate.sledContractCode] = contract_rate;
        }
    }
}
