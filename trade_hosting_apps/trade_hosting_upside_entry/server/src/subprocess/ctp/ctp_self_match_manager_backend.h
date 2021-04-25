/*
 * ctp_self_match_manager_backend.h
 *
 *  Created on: 2018年4月23日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_CTP_CTP_SELF_MATCH_MANAGER_BACKEND_H_
#define SRC_SUBPROCESS_CTP_CTP_SELF_MATCH_MANAGER_BACKEND_H_

#include "ThostFtdcTraderApi.h"
#include "self_match_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {

class CTPSelfMatchManagerBackend : public SelfMatchManagerBackend<CThostFtdcOrderField> {
public:
    CTPSelfMatchManagerBackend() = default;
    virtual ~CTPSelfMatchManagerBackend() = default;

    virtual const char* orderFieldTypeName() { return "CThostFtdcOrderField"; }

    virtual bool isOrderSupported(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        if (order_field->OrderPriceType != THOST_FTDC_OPT_LimitPrice) {
            return false;
        }
        return true;
    }

    virtual double getPrice(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        return order_field->LimitPrice;
    }

    virtual HostingExecOrderTradeDirection::type getTradeDirection(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        if (order_field->Direction == THOST_FTDC_D_Buy) {
            return HostingExecOrderTradeDirection::ORDER_BUY;
        } else if (order_field->Direction == THOST_FTDC_D_Sell) {
            return HostingExecOrderTradeDirection::ORDER_SELL;
        }
        CHECK(false);
        return HostingExecOrderTradeDirection::ORDER_SELL;
    }

    virtual std::string getOrderContractKey(const std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder>& exec_order) {
        if (exec_order->__isset.orderInputExt
                && exec_order->orderInputExt.__isset.ctpInputExt
                && exec_order->orderInputExt.ctpInputExt.__isset.contractSummary
                && exec_order->orderInputExt.ctpInputExt.contractSummary.__isset.ctpContractCode
                && !exec_order->orderInputExt.ctpInputExt.contractSummary.ctpContractCode.empty()) {
            return exec_order->orderInputExt.ctpInputExt.contractSummary.ctpContractCode;
        }

        return "";
    }

    virtual std::string getOrderContractKey(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        return order_field->InstrumentID;
    }

    virtual std::string getOrderRefKey(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        if (order_field->OrderRef[0] == 0) {
            return "";
        }

        std::stringstream order_ref_stream;
        order_ref_stream << order_field->FrontID
                         << "_" << order_field->SessionID
                         << "_" << order_field->OrderRef;
        return order_ref_stream.str();
    }

    virtual std::string getOrderDealIDKey(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        if (order_field->ExchangeID[0] == 0 || order_field->OrderSysID[0] == 0) {
            return "";
        }

        std::stringstream order_dealid_stream;
        order_dealid_stream << order_field->ExchangeID
                            << "_" << order_field->OrderSysID;
        return order_dealid_stream.str();
    }

    virtual bool isNewerOrderField(const std::shared_ptr<CThostFtdcOrderField>& order_field
                , const std::shared_ptr<CThostFtdcOrderField>& origin_order_field) {
        if (origin_order_field->OrderStatus == THOST_FTDC_OST_AllTraded
                || origin_order_field->OrderStatus == THOST_FTDC_OST_Canceled
                || origin_order_field->OrderStatus == THOST_FTDC_OST_Touched) {
            return false;
        }

        if (order_field->VolumeTraded < origin_order_field->VolumeTraded) {
            return false;
        }

        return true;
    }

    virtual bool isHangStatus(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        if (order_field->OrderStatus == THOST_FTDC_OST_AllTraded
                || order_field->OrderStatus == THOST_FTDC_OST_Canceled
                || order_field->OrderStatus == THOST_FTDC_OST_Touched) {
            return false;
        }
        return true;
    }

    virtual std::string dumpOrderField(const std::shared_ptr<CThostFtdcOrderField>& order_field) {
        std::stringstream os;
        os << "{"
           << "InstrumentID=" << order_field->InstrumentID
           << ", FrontID=" << order_field->FrontID
           << ", SessionID=" << order_field->SessionID
           << ", OrderRef=" << order_field->OrderRef
           << ", ExchangeID=" << order_field->ExchangeID
           << ", OrderSysID=" << order_field->OrderSysID
           << ", Direction=" << order_field->Direction
           << ", OrderPriceType=" << order_field->OrderPriceType
           << ", LimitPrice=" << order_field->LimitPrice
           << ", OrderStatus=" << order_field->OrderStatus
           << "}";
        return os.str();
    }

    virtual std::shared_ptr<CThostFtdcOrderField> createDummyOrder(
                const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& exec_order) {
        std::shared_ptr<CThostFtdcOrderField> order_field(new CThostFtdcOrderField());
        memset(order_field.get(), 0, sizeof(CThostFtdcOrderField));
        strncpy(order_field->InstrumentID
                , exec_order->orderInputExt.ctpInputExt.contractSummary.ctpContractCode.c_str()
                , sizeof(TThostFtdcInstrumentIDType) - 1);
        order_field->FrontID = exec_order->upsideOrderRef.ctpRef.frontID;
        order_field->SessionID = exec_order->upsideOrderRef.ctpRef.sessionID;
        strncpy(order_field->OrderRef, exec_order->upsideOrderRef.ctpRef.orderRef.c_str(), sizeof(TThostFtdcOrderRefType) - 1);
        order_field->OrderStatus = '?';
        order_field->VolumeTotalOriginal = exec_order->orderDetail.quantity;
        order_field->VolumeTotal = order_field->VolumeTotalOriginal;
        order_field->VolumeTraded = 0;
        if (exec_order->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_BUY) {
            order_field->Direction = THOST_FTDC_D_Buy;
        } else {
            order_field->Direction = THOST_FTDC_D_Sell;
        }
        order_field->LimitPrice = exec_order->orderDetail.limitPrice;
        order_field->OrderPriceType = THOST_FTDC_OPT_LimitPrice;

        return order_field;
    }

};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao

#endif /* SRC_SUBPROCESS_CTP_CTP_SELF_MATCH_MANAGER_BACKEND_H_ */
