package xueqiao.trade.hosting.arbitrage.core;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;
import com.longsheng.xueqiao.contract.thriftapi.ReqSledTradeTimeOption;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTime;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTimePage;
import com.longsheng.xueqiao.contract.thriftapi.TTimeSpan;
import com.longsheng.xueqiao.contract.thriftapi.TimeSpanState;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.quotation.QuotationItemUtil;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.*;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.ext.GFDOrderCalculator;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import static xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode.*;

public class XQOrderHelper {
    private static Map<HostingXQOrderStateValue, String> STATUS_DESCRIPTIONS = 
            new HashMap<HostingXQOrderStateValue, String>();
    
    private static Map<Integer, String> ERROR_DESCRIPTIONS = new HashMap<Integer, String>();
    
    static {
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_CREATED, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_STARTING, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_CANCELLING, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_CANCELLED, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_FINISHED, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_FINISHING, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_RUNNING, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_SUSPENDING, "?????????");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_SUSPENDED, "?????????");
    }
    
    static {
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue(), "?????????????????????????????????");
        
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue(), "??????????????????,???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_BEFORE_EFFECT_TIME_PERIOD.getValue(), "????????????,?????????????????????");

        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_VERIFY_FAILED.getValue(), "???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_SEND_FAILED.getValue(), "?????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_CANCELLED_TOO_MANY.getValue(), "???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_PRICE_PROTECTED.getValue(), "???????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_LEAK_BUT_TRADE_CLOSE.getValue(), "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue(), "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_INNER_EXCEPTION_OCCURS.getValue(), "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_OPERATION_INTENT_UNKOWN.getValue(), "?????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_VERIFY_FAILED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_SEND_FAILED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_REJECTED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_CANCELLED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_EXPIRED.getValue(), "??????????????????");

        ERROR_DESCRIPTIONS.put(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue(), "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_STARTED_BUT_AFTER_EFFECT_TIME_PERIOD.getValue()
                , "???????????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue(), "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_BY_USER.getValue(), "????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_VERIFY_FAILED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_SEND_FAILED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_UPSIDE_REJECTED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_UPSIDE_CANCELLED.getValue(), "????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_MARKET_CLOSED.getValue(), "?????????,????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_ANALYSIS_TRADETIME_FAILED.getValue(), "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_MARKET_OPENDED_FOR_PARKED.getValue(), "????????????,???????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_STARTING_TOO_LONG_FOR_PARKED.getValue()
                , "??????????????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_TRIGGER_RUNNING_TOO_LONG_FOR_PARKED.getValue()
                , "???????????????????????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_COMPOSE_EXEC_TYPE_NOT_SUPPORTED.getValue()
                , "??????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_COMPOSE_NO_ACCEPTABLE_FIRSTLEG.getValue()
                , "????????????????????????,?????????????????????????????????");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_INNER_EXCEPTION_OCCURS.getValue()
                , "??????????????????????????????");
    }
    
    public static HostingXQOrderState generateStateMsg(HostingXQOrderState orderState) {
        StringBuilder stateMsgBuilder = new StringBuilder(128);
        
        String statusDescription = STATUS_DESCRIPTIONS.get(orderState.getStateValue());
        if (StringUtils.isEmpty(statusDescription)) {
            stateMsgBuilder.append("????????????");
        } else {
            stateMsgBuilder.append(statusDescription);
        }
        
        if (orderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_SUSPENDING
                || orderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            stateMsgBuilder.append(",");
            if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_BY_PERSON) {
                stateMsgBuilder.append("????????????");
            } else if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
                stateMsgBuilder.append("???????????????");
            } else if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS) {
                stateMsgBuilder.append("????????????");
            } else {
                stateMsgBuilder.append("????????????????????????");
            }
            
            if (orderState.getSuspendedErrorCode() != 0) {
                stateMsgBuilder.append("(");
                String errorDescription = ERROR_DESCRIPTIONS.get(orderState.getSuspendedErrorCode());
                if (StringUtils.isEmpty(errorDescription)) {
                    stateMsgBuilder.append("????????????");
                } else {
                    stateMsgBuilder.append(errorDescription);
                }
                stateMsgBuilder.append(")");
            }
        }
        
        if (orderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CANCELLING
            || orderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_CANCELLED) {
            if (orderState.getCancelledErrorCode() != 0) {
                String errorDescription = ERROR_DESCRIPTIONS.get(orderState.getCancelledErrorCode());
                stateMsgBuilder.append(",");
                if (StringUtils.isEmpty(errorDescription)) {
                    stateMsgBuilder.append("????????????");
                } else {
                    stateMsgBuilder.append(errorDescription);
                }
            }
        }
        
        if (!StringUtils.isEmpty(orderState.getExecUsefulMsg())) {
            stateMsgBuilder.append(",").append(orderState.getExecUsefulMsg());
        }
        
        orderState.setStateMsg(stateMsgBuilder.toString());
        return orderState;
    }
    
    public static String generateTargetUniqueKey(HostingXQTarget target) {
        StringBuilder keyBuilder = new StringBuilder(64);
        keyBuilder.append(target.getTargetType().getValue()).append("_")
                  .append(target.getTargetKey());
        return keyBuilder.toString();
    }
    
    public static HostingXQOrderState createOrderState(HostingXQOrderStateValue stateValue) {
        HostingXQOrderState orderState = new HostingXQOrderState();
        orderState.setStateValue(stateValue);
        orderState.setStateTimestampMs(System.currentTimeMillis());
        orderState.setSuspendedErrorCode(0);
        orderState.setCancelledErrorCode(0);
        orderState.setSuspendReason(HostingXQSuspendReason.SUSPENDED_REASON_NONE);
        orderState.setResumeMode(HostingXQOrderResumeMode.RESUME_MODE_NONE);
        return orderState;
    }
    
    public static HostingXQOrderState createAndGenerateStateMsg(HostingXQOrderStateValue stateValue) {
        return generateStateMsg(createOrderState(stateValue));
    }
    
    public static String getQuotationPlatform(SledContractDetails contractDetail) {
        if (contractDetail.getSledContract().getPlatformEnv()
                == TechPlatformEnv.SIM) {
            return "SXQ";
        } 
        return "XQ";
    }
    
    public static String getQuotationContractSymbol(SledContractDetails contractDetail) throws UnsupportedEncodingException {
        StringBuilder contractSymbolBuilder = new StringBuilder(64);
        contractSymbolBuilder.append(contractDetail.getSledCommodity().getExchangeMic())
                             .append("|").append(contractDetail.getSledCommodity().getSledCommodityType().getValue())
                             .append("|").append(contractDetail.getSledCommodity().getSledCommodityCode())
                             .append("|").append(contractDetail.getSledContract().getSledContractCode());
        return URLEncoder.encode(contractSymbolBuilder.toString(), "UTF-8");
    }
    
    private static SledTradeTime getCommodityTradeTime(long sledCommodityId) throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<SledTradeTime>() {
            @Override
            public SledTradeTime call() throws Exception {
                ReqSledTradeTimeOption option = new ReqSledTradeTimeOption();
                option.setSledCommodityIds(Arrays.asList((int)sledCommodityId));
                SledTradeTimePage resultPage = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class)
                        .getContractOnlineStub().reqSledTradeTime(option, 0, 1);
                if (resultPage.getPageSize() <= 0) {
                    return null;
                }
                return resultPage.getPage().get(0);
            }
        });
    }

    /**
     *  ????????????ID???????????????????????????
     */
    public static TradeTimeSpanList constructTimeSpanList(long sledCommodityId) throws ErrorInfo {
        TradeTimeSpanList resultTimeSpanList = new TradeTimeSpanList();
        if (!resultTimeSpanList.construct(getCommodityTradeTime(sledCommodityId))) {
            return null;
        }
        return resultTimeSpanList;
    }

    /**
     *   ???????????????????????????????????????
     * @param composeGraph
     */
    public static TradeTimeSpanList constructTimeSpanList(HostingComposeGraph composeGraph) throws ErrorInfo {
        TradeTimeSpanList resultTimeSpanList = null;
        for (HostingComposeLeg composeLeg : composeGraph.getLegs().values()) {
            SledTradeTime legTradeTime = getCommodityTradeTime(composeLeg.getSledCommodityId());
            if (legTradeTime == null) {
                return null;
            }
            
            TradeTimeSpanList toMergeSpanList = new TradeTimeSpanList();
            if (!toMergeSpanList.construct(legTradeTime)) {
                return null;
            }
            
            if (resultTimeSpanList == null) {
                resultTimeSpanList = toMergeSpanList;
                continue;
            }
            resultTimeSpanList = resultTimeSpanList.merge(toMergeSpanList);
        }
        return resultTimeSpanList;
    }

    public static class GFDEffectiveTimeSpan {
        private long startTimestampMs;
        private long endTimestampMs;
        private long ttlTimestampMs;

        public long getStartTimestampMs() {
            return startTimestampMs;
        }

        public void setStartTimestampMs(long startTimestampMs) {
            this.startTimestampMs = startTimestampMs;
        }

        public long getEndTimestampMs() {
            return endTimestampMs;
        }

        public void setEndTimestampMs(long endTimestampMs) {
            this.endTimestampMs = endTimestampMs;
        }

        public long getTtlTimestampMs() {
            return ttlTimestampMs;
        }

        public void setTtlTimestampMs(long ttlTimestampMs) {
            this.ttlTimestampMs = ttlTimestampMs;
        }
    }

    /**
     * ??????????????????????????????(??????????????????,?????????????????????)
     *
     * ajustStartTimestampMs???ajustEndTimeStampMs, ???????????????
     *
     */
    public static GFDEffectiveTimeSpan createGFDEffectiveTimeSpan(long sledCommodityId
            , long processTimestampMs
            , long ajustStartTimestampMs
            , long ajustEndTimestampMs) {
        GFDEffectiveTimeSpan resultTimeSpan = new GFDEffectiveTimeSpan();

        // ?????????????????????
        try {
            TradeTimeSpanList tradeTimeSpanList = constructTimeSpanList(sledCommodityId);
            if (tradeTimeSpanList == null) {
                return null;
            }
            tradeTimeSpanList.ajustTimeSpan(ajustStartTimestampMs, ajustEndTimestampMs);

            TradeTimeSpan nearestTimeSpan = tradeTimeSpanList.getNearestSpan(processTimestampMs);
            if (nearestTimeSpan == null) {
                return null;
            }
            resultTimeSpan.setStartTimestampMs(nearestTimeSpan.getStartTimestampMs());

        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            return null;
        }

        // ?????????????????????
        try {
            TradingTimeAnalysisor analysisorEnd = new TradingTimeAnalysisor(
                    Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class));
            analysisorEnd.setCommodityId(sledCommodityId);
            analysisorEnd.setProcessTimestampMs(resultTimeSpan.getStartTimestampMs());

            if (!analysisorEnd.analysis()) {
                AppLog.e(analysisorEnd);
                return null;
            }

            GFDOrderCalculator cal = new GFDOrderCalculator(analysisorEnd);
            if (!cal.calculate()) {
                AppLog.e(cal);
                return null;
            }

            resultTimeSpan.setEndTimestampMs(cal.getTradingEndDate().getTime() + ajustEndTimestampMs);
            resultTimeSpan.setTtlTimestampMs(cal.getTTLTimestampMs());
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            return null;
        }

        return resultTimeSpan;
    }
    
    public static Double calculateOrderPrice(QuotationItem quotItem
            , HostingXQTarget tradeTarget
            , HostingXQTradeDirection tradeDirection
            , HostingXQOrderPrice orderPrice
            , SledContractDetails contractDetail) {
        if (orderPrice.getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_LIMIT) {
            return orderPrice.getLimitPrice();
        }
        
        Double resultPrice = null;
        if (orderPrice.getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_LATEST) {
            resultPrice = QuotationItemUtil.getLastPrice(quotItem);
        } else if (orderPrice.getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_IN_LINE) {
            if (tradeDirection == HostingXQTradeDirection.XQ_BUY) {
                resultPrice = QuotationItemUtil.getBid1Price(quotItem);
            } else {
                resultPrice = QuotationItemUtil.getAsk1Price(quotItem);
            }
        } else if (orderPrice.getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_OPPONENT) {
            if (tradeDirection == HostingXQTradeDirection.XQ_BUY) {
                resultPrice = QuotationItemUtil.getAsk1Price(quotItem);
            } else {
                resultPrice = QuotationItemUtil.getBid1Price(quotItem);
            }
        }
        if (resultPrice == null) {
            return resultPrice;
        }
        
        // ????????????
        BigDecimal chaseValue = null;
        if (tradeTarget.getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            if (orderPrice.isSetChasePriceValue()) {
                chaseValue = new BigDecimal(String.valueOf(orderPrice.chasePriceValue));
            }
        } else {
            if (orderPrice.isSetChasePriceTicks()) {
                BigDecimal tickSize = new BigDecimal(String.valueOf(contractDetail.getSledCommodity().getTickSize()));
                chaseValue = tickSize.multiply(new BigDecimal(orderPrice.getChasePriceTicks()));
            }
        }
        if (chaseValue == null) {
            return resultPrice;
        }
        
        // ???????????????+??? ???????????????-
        if (tradeDirection == HostingXQTradeDirection.XQ_BUY) {
            return new BigDecimal(resultPrice.toString()).add(chaseValue).doubleValue();
        }
        return new BigDecimal(resultPrice.toString()).subtract(chaseValue).doubleValue();
    }

    public static HostingXQTradeRelatedItem dbRelatedItem2Hosting(XQTradeRelatedItem dbRelatedItem) {
        HostingXQTradeRelatedItem resultItem = new HostingXQTradeRelatedItem();

        resultItem.setOrderId(dbRelatedItem.getOrderId());
        resultItem.setTradeId(dbRelatedItem.getTradeId());
        resultItem.setExecOrderId(dbRelatedItem.getExecOrderId());
        resultItem.setExecTradeId(dbRelatedItem.getExecTradeId());
        resultItem.setExecTradeLegId(dbRelatedItem.getExecTradeLegId());
        resultItem.setExecTradeLegDirection(dbRelatedItem.getExecTradeLegDirection());
        resultItem.setExecTradeLegVolume(dbRelatedItem.getExecTradeLegVolume());
        resultItem.setExecTradeLegPrice(dbRelatedItem.getExecTradeLegPrice());
        resultItem.setRelatedTradeVolume(dbRelatedItem.getRelatedTradeVolume());
        resultItem.setSledContractId(dbRelatedItem.getSledContractId());
        resultItem.setCreateTimestampMs(dbRelatedItem.getCreateTimestampMs());

        return resultItem;
    }
    
}
