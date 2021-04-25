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
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_CREATED, "已创建");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_STARTING, "启动中");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_CANCELLING, "撤单中");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_CANCELLED, "已撤单");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_FINISHED, "已完成");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_FINISHING, "完成中");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_RUNNING, "进行中");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_SUSPENDING, "暂停中");
        STATUS_DESCRIPTIONS.put(HostingXQOrderStateValue.XQ_ORDER_SUSPENDED, "已暂停");
    }
    
    static {
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue(), "构建交易时间失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue(), "近期无任何可交易时间段");
        
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue(), "非交易时间段,交易开始后自动启动");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_BEFORE_EFFECT_TIME_PERIOD.getValue(), "还未生效,生效后自动启动");

        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_VERIFY_FAILED.getValue(), "组合腿订单审核失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_SEND_FAILED.getValue(), "组合腿发送执行订单失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_CANCELLED_TOO_MANY.getValue(), "组合腿撤单次数过多");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_PRICE_PROTECTED.getValue(), "组合腿触发价格保护");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_LEG_LEAK_BUT_TRADE_CLOSE.getValue(), "市场停止交易导致瘸腿");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue(), "内部状态异常导致暂停");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_INNER_EXCEPTION_OCCURS.getValue(), "系统内部异常导致暂停");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_OPERATION_INTENT_UNKOWN.getValue(), "操作意图不明确导致暂停");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_VERIFY_FAILED.getValue(), "执行订单审核失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_SEND_FAILED.getValue(), "执行订单发送失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_REJECTED.getValue(), "执行订单上手拒绝");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_CANCELLED.getValue(), "执行订单上手撤单");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_EXPIRED.getValue(), "执行订单过期");

        ERROR_DESCRIPTIONS.put(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue(), "系统异常");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_STARTED_BUT_AFTER_EFFECT_TIME_PERIOD.getValue()
                , "启动订单时检测到订单已过期");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_AFTER_EFFECT_TIME_PERIOD.getValue(), "订单过期");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_BY_USER.getValue(), "主动撤单");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_VERIFY_FAILED.getValue(), "执行订单审核失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_SEND_FAILED.getValue(), "执行订单发送失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_UPSIDE_REJECTED.getValue(), "执行订单上手拒绝");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_EXEC_ORDER_UPSIDE_CANCELLED.getValue(), "执行订单上手撤单");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_MARKET_CLOSED.getValue(), "已闭市,当日有效订单无效");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_ANALYSIS_TRADETIME_FAILED.getValue(), "系统分析交易时间失败");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_MARKET_OPENDED_FOR_PARKED.getValue(), "开市阶段,预埋单无效");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_STARTING_TOO_LONG_FOR_PARKED.getValue()
                , "系统异常导致启动时间偏离过长");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_TRIGGER_RUNNING_TOO_LONG_FOR_PARKED.getValue()
                , "系统异常导致启动触发器运行时间过长");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_COMPOSE_EXEC_TYPE_NOT_SUPPORTED.getValue()
                , "不支持的组合执行方式");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_COMPOSE_NO_ACCEPTABLE_FIRSTLEG.getValue()
                , "订单无可用先手腿,组合执行参数选择不合理");
        ERROR_DESCRIPTIONS.put(ERROR_XQ_ORDER_CANCELLED_INNER_EXCEPTION_OCCURS.getValue()
                , "系统内部异常导致撤单");
    }
    
    public static HostingXQOrderState generateStateMsg(HostingXQOrderState orderState) {
        StringBuilder stateMsgBuilder = new StringBuilder(128);
        
        String statusDescription = STATUS_DESCRIPTIONS.get(orderState.getStateValue());
        if (StringUtils.isEmpty(statusDescription)) {
            stateMsgBuilder.append("未知状态");
        } else {
            stateMsgBuilder.append(statusDescription);
        }
        
        if (orderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_SUSPENDING
                || orderState.getStateValue() == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            stateMsgBuilder.append(",");
            if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_BY_PERSON) {
                stateMsgBuilder.append("手工暂停");
            } else if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
                stateMsgBuilder.append("功能性暂停");
            } else if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS) {
                stateMsgBuilder.append("异常暂停");
            } else {
                stateMsgBuilder.append("系统原因导致暂停");
            }
            
            if (orderState.getSuspendedErrorCode() != 0) {
                stateMsgBuilder.append("(");
                String errorDescription = ERROR_DESCRIPTIONS.get(orderState.getSuspendedErrorCode());
                if (StringUtils.isEmpty(errorDescription)) {
                    stateMsgBuilder.append("未知原因");
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
                    stateMsgBuilder.append("原因未知");
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
     *  根据商品ID，构建可交易时间段
     */
    public static TradeTimeSpanList constructTimeSpanList(long sledCommodityId) throws ErrorInfo {
        TradeTimeSpanList resultTimeSpanList = new TradeTimeSpanList();
        if (!resultTimeSpanList.construct(getCommodityTradeTime(sledCommodityId))) {
            return null;
        }
        return resultTimeSpanList;
    }

    /**
     *   构建组合重叠的可交易时间段
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
     * 构建当日有效的时间段(从开市到闭市,包含中间的休市)
     *
     * ajustStartTimestampMs和ajustEndTimeStampMs, 调整时间段
     *
     */
    public static GFDEffectiveTimeSpan createGFDEffectiveTimeSpan(long sledCommodityId
            , long processTimestampMs
            , long ajustStartTimestampMs
            , long ajustEndTimestampMs) {
        GFDEffectiveTimeSpan resultTimeSpan = new GFDEffectiveTimeSpan();

        // 分析有效期开始
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

        // 分析有效期结束
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
        
        // 追价计算
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
        
        // 买时追价为+， 卖时追价为-
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
