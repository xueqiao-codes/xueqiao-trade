/*
 * es9_self_match_manager_backend.h
 *
 *  Created on: 2018年4月23日
 *      Author: 44385
 */

#ifndef SRC_SUBPROCESS_ESUNNY9_ES9_SELF_MATCH_MANAGER_BACKEND_H_
#define SRC_SUBPROCESS_ESUNNY9_ES9_SELF_MATCH_MANAGER_BACKEND_H_

#include "iTapTradeAPIDataType.h"
#include "self_match_manager.h"

namespace xueqiao {
namespace trade {
namespace hosting {
namespace upside {
namespace entry {


class Es9SelfMatchManagerBackend : public SelfMatchManagerBackend<ITapTrade::TapAPIOrderInfo> {
public:
    Es9SelfMatchManagerBackend() = default;
    virtual ~Es9SelfMatchManagerBackend() = default;

    virtual const char* orderFieldTypeName() {
        return "TapAPIOrderInfo";
    }

    virtual bool isOrderSupported(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        if (order_field->OrderType != ITapTrade::TAPI_ORDER_TYPE_LIMIT) {
            return false;
        }
        return true;
    }

    virtual HostingExecOrderTradeDirection::type getTradeDirection(
                const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        if (order_field->OrderSide == ITapTrade::TAPI_SIDE_BUY) {
            return HostingExecOrderTradeDirection::ORDER_BUY;
        } else if (order_field->OrderSide == ITapTrade::TAPI_SIDE_SELL) {
            return HostingExecOrderTradeDirection::ORDER_SELL;
        }
        CHECK(false);
        return HostingExecOrderTradeDirection::ORDER_SELL;
    }

    virtual double getPrice(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        return order_field->OrderPrice;
    }

    virtual std::string getOrderContractKey(const std::shared_ptr<xueqiao::trade::hosting::HostingExecOrder>& exec_order) {
        if (!exec_order->__isset.orderInputExt
             || !exec_order->orderInputExt.__isset.esunny9InputExt
             || !exec_order->orderInputExt.esunny9InputExt.__isset.contractSummary) {
            return "";
        }

        const ESunny9ContractSummary& contract_summary = exec_order->orderInputExt.esunny9InputExt.contractSummary;
        if (!contract_summary.__isset.esunny9ExchangeNo
                || contract_summary.esunny9ExchangeNo.empty()
                || !contract_summary.__isset.esunny9CommodityType
                || !contract_summary.__isset.esunny9CommodityNo
                || contract_summary.esunny9CommodityNo.empty()
                || !contract_summary.__isset.esunny9ContractNo
                || contract_summary.esunny9ContractNo.empty()) {
            return "";
        }

        std::stringstream contract_key_stream;
        contract_key_stream << contract_summary.esunny9ExchangeNo
                            << "|"
                            << (char)contract_summary.esunny9CommodityType
                            << "|"
                            << contract_summary.esunny9CommodityNo
                            << "|"
                            << contract_summary.esunny9ContractNo;
        if (contract_summary.__isset.esunny9ContractNo2
                && !contract_summary.esunny9ContractNo2.empty()) {
            contract_key_stream << "&" << contract_summary.esunny9ContractNo2;
        }

        return contract_key_stream.str();
    }

    virtual std::string getOrderContractKey(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        if (order_field->ExchangeNo[0] == 0
                || order_field->CommodityNo[0] == 0
                || order_field->ContractNo[0] == 0) {
            return "";
        }

        std::stringstream contract_key_stream;
        contract_key_stream << order_field->ExchangeNo
                            << "|"
                            << order_field->CommodityType
                            << "|"
                            << order_field->CommodityNo
                            << "|"
                            << order_field->ContractNo;
        if (order_field->ContractNo2[2] != 0) {
            contract_key_stream << "&" << order_field->ContractNo2;
        }
        return contract_key_stream.str();
    }

    virtual std::string getOrderRefKey(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        if (order_field->RefString[0] != 'X' || order_field->RefString[1] != 'Q') {
            return "";
        }
        int __count = 0;
        for (size_t index = 0; index < sizeof(ITapTrade::TAPISTR_50); ++index) {
            if (order_field->RefString[index] == 0) {
                break;
            }
            if (order_field->RefString[index] == '_') {
                __count += 1;
            }
        }
        if (__count != 2) {
            return "";
        }

        return order_field->RefString;
    }

    virtual std::string getOrderDealIDKey(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        if (order_field->OrderNo[0] == 0) {
            return "";
        }
        return order_field->OrderNo;
    }

    virtual bool isNewerOrderField(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field
                , const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& origin_order_field) {
        if (origin_order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_FAIL
                || origin_order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_DELETED
                || origin_order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_LEFTDELETED
                || origin_order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_CANCELED
                || origin_order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_FINISHED) {
            return false;
        }
        if (order_field->OrderMatchQty < origin_order_field->OrderMatchQty) {
            return false;
        }
        return true;
    }

    virtual bool isHangStatus(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        if (order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_FAIL
                || order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_DELETED
                || order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_LEFTDELETED
                || order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_CANCELED
                || order_field->OrderState == ITapTrade::TAPI_ORDER_STATE_FINISHED) {
            return false;
        }
        return true;
    }

    virtual std::string dumpOrderField(const std::shared_ptr<ITapTrade::TapAPIOrderInfo>& order_field) {
        std::stringstream os;
        os << "{"
           << "ExchangeNo=" << order_field->ExchangeNo
           << ", CommodityType=" << order_field->CommodityType
           << ", CommodityNo=" << order_field->CommodityNo
           << ", ContractNo=" << order_field->ContractNo
           << ", ContractNo2=" << order_field->ContractNo2
           << ", RefString=" << order_field->RefString
           << ", OrderNo=" << order_field->OrderNo
           << ", OrderSide=" << order_field->OrderSide
           << ", OrderType=" << order_field->OrderType
           << ", OrderPrice=" << order_field->OrderPrice
           << ", OrderState=" << order_field->OrderState
           << "}";
        return os.str();
    }

    virtual std::shared_ptr<ITapTrade::TapAPIOrderInfo> createDummyOrder(
                   const std::shared_ptr<::xueqiao::trade::hosting::HostingExecOrder>& exec_order) {
        std::shared_ptr<ITapTrade::TapAPIOrderInfo> order_field(new ITapTrade::TapAPIOrderInfo());
        memset(order_field.get(), 0, sizeof(ITapTrade::TapAPIOrderInfo));

        const ESunny9ContractSummary& contractSummary
                = exec_order->orderInputExt.esunny9InputExt.contractSummary;
        strncpy(order_field->ExchangeNo
                    , contractSummary.esunny9ExchangeNo.c_str()
                    , sizeof(ITapTrade::TAPISTR_10) - 1);
        order_field->CommodityType = (ITapTrade::TAPICommodityType)contractSummary.esunny9CommodityType;
        strncpy(order_field->CommodityNo
                , contractSummary.esunny9CommodityNo.c_str()
                , sizeof(ITapTrade::TAPISTR_10) - 1);
        strncpy(order_field->ContractNo
                , contractSummary.esunny9ContractNo.c_str()
                , sizeof(ITapTrade::TAPISTR_10) - 1);

        if (contractSummary.__isset.esunny9ContractNo2 && !contractSummary.esunny9ContractNo2.empty()) {
            strncpy(order_field->ContractNo2
                    , contractSummary.esunny9ContractNo2.c_str()
                    , sizeof(ITapTrade::TAPISTR_10) - 1);
        }

        strcpy(order_field->RefString, exec_order->upsideOrderRef.esunny9Ref.refString.c_str());
        if (exec_order->orderDetail.tradeDirection == HostingExecOrderTradeDirection::ORDER_SELL) {
            order_field->OrderSide = ITapTrade::TAPI_SIDE_SELL;
        } else {
            order_field->OrderSide = ITapTrade::TAPI_SIDE_BUY;
        }
        order_field->OrderQty = exec_order->orderDetail.quantity;
        order_field->OrderPrice = exec_order->orderDetail.limitPrice;
        order_field->OrderType = ITapTrade::TAPI_ORDER_TYPE_LIMIT;
        order_field->OrderState = '?';

        return order_field;
    }

};


} // end namespace entry
} // end namespace upside
} // end namespace hosting
} // end namespace trade
} // end namespace xueqiao


#endif /* SRC_SUBPROCESS_ESUNNY9_ES9_SELF_MATCH_MANAGER_BACKEND_H_ */
