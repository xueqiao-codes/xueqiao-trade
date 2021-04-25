/*
 * ctp_rate_manager.h
 *
 *  Created on: 2019年4月2日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_RATE_MANAGER_H_
#define SRC_SUBPROCESS_CTP_CTP_RATE_MANAGER_H_

#include <deque>
#include <mutex>
#include "base/thread_pool.h"
#include "ThostFtdcTraderApi.h"
#include "ctp_data_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

/**
 *  CTP合约的费率
 */
struct CTPContractRate {
    std::string instrument_id;
    std::shared_ptr<CThostFtdcExchangeMarginRateField> exchange_margin;  // 交易所保证金率
    std::shared_ptr<CThostFtdcInstrumentMarginRateField> instrument_margin; // 合约保证金率调整
    std::shared_ptr<CThostFtdcInstrumentCommissionRateField> commission; // 手续费
};

/**
 *  CTP商品的费率
 */
struct CTPProductRate {
    std::string exchange_id;
    TThostFtdcProductClassType  product_class;
    std::string product_id;
    std::shared_ptr<CThostFtdcExchangeMarginRateField> exchange_margin;
    std::shared_ptr<CThostFtdcInstrumentMarginRateField> instrument_margin;
    std::shared_ptr<CThostFtdcInstrumentCommissionRateField> commission;

    /**
     *  所包含的合约的费率
     */
    std::map<std::string, std::shared_ptr<CTPContractRate>> contract_rates;
};


/**
 *  合约保证金和收费费率管理器
 */
class CTPRateManager : public IDataManagerListener {
public:
    CTPRateManager(const std::shared_ptr<CTPDataManager>& data_manager
            , const std::shared_ptr<CTPLoginManager>& login_manager
            , const std::shared_ptr<ctpext::framework::CTPRequestDispatcher>& dispatcher);
    virtual ~CTPRateManager();

    std::shared_ptr<std::map<std::string, std::shared_ptr<CTPProductRate>>> getRates();

    virtual void onDataManagerInitStart();
    virtual void onQryAllInstrumentsFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp);

    virtual void onQryExchangeMarginRatesFinished(
            const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp);
    virtual void onQryInstrumentMarginRatesFinished(
            const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp);
    virtual void onQryInstrumentCommissionRatesFinished(
            const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp);
    virtual void onDataManagerInitFinished();

private:
    void handleInitStart();
    void handleInitFinished();

    void handleAllInstrumentFinished(const std::shared_ptr<QryInstrumentsResp>& instruments_rsp);
    void handleExchangeMarginRates(const std::shared_ptr<QryExchangeMarginRateResp>& exchange_margin_rates_rsp);
    void handleInstrumentMarginRates(const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp);
    void handleInstrumentCommissionRates(const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp);

    void scheduleTask();

    void qryInstrumentMargin();
    void onQryInstrumentMarginFinished(const std::shared_ptr<QryInstrumentMarginRateResp>& instrument_margin_rates_rsp);

    void qryInstrumentCommission();
    void onQryInstrumentCommissionFinished(const std::shared_ptr<QryInstrumentCommissionRateResp>& instrument_commission_rates_rsp);

    void collationResult();

    std::shared_ptr<CTPDataManager> data_manager_;
    std::shared_ptr<CTPLoginManager> login_manager_;
    std::shared_ptr<ctpext::framework::CTPRequestDispatcher> dispatcher_;
    std::shared_ptr<soldier::base::TaskThread> work_thread_;

    // 整理得出所有的商品编码
    std::map<std::string, std::set<std::string>> product_contracts_;

    // 等待查询的所有合约
    std::deque<std::string> waiting_instruments_;
    std::unique_ptr<std::string> qrying_instrument_id_;

    // 存放从接口出来的结果
    std::map<std::string, std::shared_ptr<CThostFtdcExchangeMarginRateField>> exchange_margins_;
    std::map<std::string, std::shared_ptr<CThostFtdcInstrumentMarginRateField>> instrument_margins_;
    std::map<std::string, std::shared_ptr<CThostFtdcInstrumentCommissionRateField>> commissions_;

    // 整理出最终的结果
    std::mutex rates_lock_;
    std::shared_ptr<std::map<std::string, std::shared_ptr<CTPProductRate>>> product_rates_;
};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace position

#endif /* SRC_SUBPROCESS_CTP_CTP_RATE_MANAGER_H_ */
