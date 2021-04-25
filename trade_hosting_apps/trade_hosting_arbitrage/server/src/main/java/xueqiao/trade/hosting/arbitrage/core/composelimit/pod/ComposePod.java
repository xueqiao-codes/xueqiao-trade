package xueqiao.trade.hosting.arbitrage.core.composelimit.pod;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.handicap.HandicapManager;
import xueqiao.trade.hosting.arbitrage.core.composelimit.handicap.IHandicapRequester;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotCalculator;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotData;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeOrderLimitLegSendOrderExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 *  购买的调度单元
 */
public abstract class ComposePod implements IHandicapRequester {

    private int mPodIndex;
    private XQOrderComposeLimitExecutor mExecutor;
    private int mTargetVolume;
    private String mDescription;

    private ComposeQuotCalculator mQuotCalculator;

    private List<XQTradeWithRelatedItem> mTrades;  // 已经打包好的套利成交
    private Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> mUnRelatedTradeLegInfos; // 部分成交，还未配对成交

    protected ComposePod(
            int podIndex
            , XQOrderComposeLimitExecutor executor
            , int targetVolume) {
        this.mPodIndex = podIndex;
        this.mExecutor = executor;
        this.mTargetVolume = targetVolume;
        this.mQuotCalculator = new ComposeQuotCalculator(mExecutor.getComposeGraph(), targetVolume);

        StringBuilder descBuilder = new StringBuilder(64);
        descBuilder.append(getClass().getSimpleName()).append("(")
                   .append("xqOrderId=").append(mExecutor.getOrder().getOrderId())
                   .append(", podIndex=").append(mPodIndex)
                   .append(", targetVolume=").append(mTargetVolume)
                   .append(")");
        mDescription = descBuilder.toString();
    }

    @Override
    public String description() {
        return mDescription;
    }

    public int getPodIndex() {
        return mPodIndex;
    }

    public int getTargetVolume() {
        return mTargetVolume;
    }

    public HostingComposeGraph getComposeGraph() {
        return mExecutor.getComposeGraph();
    }

    public HostingXQOrder getOrder() {
        return mExecutor.getOrder();
    }

    public XQOrderComposeLimitExecutor getExecutor() {
        return mExecutor;
    }

    public ComposeQuotCalculator getQuotCalculator() {
        return mQuotCalculator;
    }

    public HostingXQComposeLimitOrderDetail getOrderDetail() {
        return mExecutor.getOrder().getOrderDetail().getComposeLimitOrderDetail();
    }

    public HostingXQTradeDirection getDirection() {
        return getOrderDetail().getDirection();
    }

    public double getLimitPrice() {
        return getOrderDetail().getLimitPrice();
    }

    public HostingExecOrderTradeDirection getOrderTradeDirection(HostingComposeLeg composeLeg) {
        HostingExecOrderTradeDirection tradeDirection = HostingExecOrderTradeDirection.ORDER_BUY;
        if (getDirection() == HostingXQTradeDirection.XQ_BUY) {
            if(composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_SELL) {
                tradeDirection = HostingExecOrderTradeDirection.ORDER_SELL;
            }
        } else {
            if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
                tradeDirection = HostingExecOrderTradeDirection.ORDER_SELL;
            }
        }
        return tradeDirection;
    }

    public void onReceivedQuotationItem(QuotationItem quotationItem) throws ErrorInfo {
        if (!mQuotCalculator.updateQuotationItem(quotationItem)) {
            return ;
        }

        handleQuotationItem(quotationItem);
    }

    public void updateTradeInfo(List<XQTradeWithRelatedItem> trades
            , Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfos) {
        this.mTrades = trades;
        this.mUnRelatedTradeLegInfos = unRelatedTradeLegInfos;
        this.mQuotCalculator.updateTradeInfo(trades, unRelatedTradeLegInfos);
    }

    public List<XQTradeWithRelatedItem> getTrades() {
        return mTrades;
    }

    public Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> getUnRelatedTradeLegInfos() {
        return mUnRelatedTradeLegInfos;
    }

    public List<QuotationItem> getLastestQuotationItems() {
        return mQuotCalculator.getLastestQuotationItems();
    }

    public void initQuotationItems(List<QuotationItem> initialQuotationItems) {
        if (initialQuotationItems != null) {
            for (QuotationItem quotationItem : initialQuotationItems) {
                this.mQuotCalculator.updateQuotationItem(quotationItem);
            }
        }
    }

    public HostingComposeLeg getComposeLeg(long sledContractId) {
        ComposeQuotData quotData = mQuotCalculator.getQuotData(sledContractId);
        if (quotData == null) {
            return null;
        }
        return quotData.getComposeLeg();
    }

    /**
     * 获取某条腿已经成交的数目
     */
    public int getTradedVolume(XQOrderSubExecutor subExecutor) {
        HostingComposeLeg composeLeg = mQuotCalculator
                .getQuotData(subExecutor.getSubSledContractId()).getComposeLeg();
        Preconditions.checkNotNull(composeLeg);
        int tradedVolume = Math.max(subExecutor.getTradeListTotalVolume(), subExecutor.getUpsideTotalVolume())
                - mPodIndex * composeLeg.getQuantity();

//        if (AppLog.infoEnabled()) {
//            AppLog.i("xqOrderId=" + getOrder().getOrderId()
//                    + ", podIndex=" + mPodIndex
//                    + ", upsideTotalVolume=" + subExecutor.getUpsideTotalVolume()
//                    + ", composeLeg=" + composeLeg
//                    + ", tradedVolume=" + tradedVolume);
//        }
        Preconditions.checkArgument(tradedVolume >= 0);
        return tradedVolume;
    }

    /**
     *  获取某条腿所缺的成交数目
     */
    public int getRestVolume(XQOrderSubExecutor subExecutor) {
        HostingComposeLeg composeLeg = mQuotCalculator
                .getQuotData(subExecutor.getSubSledContractId()).getComposeLeg();
        Preconditions.checkNotNull(composeLeg);
        int restVolume = composeLeg.getQuantity() * mTargetVolume - getTradedVolume(subExecutor);
//        if (AppLog.infoEnabled()) {
//            AppLog.i("xqOrderId=" + getOrder().getOrderId()
//                    + ", podIndex=" + mPodIndex
//                    + ", composeLeg=" + composeLeg
//                    + ", resetVolume=" + restVolume);
//        }
        return restVolume;
    }

    public void lanuchOrder(XQOrderSubExecutor subExecutor
            , HostingExecOrderTradeDirection tradeDirection
            , double limitPrice
            , int quantity) throws ErrorInfo {
        Map<String, String> extraParams = new HashMap<>();
        extraParams.put(ComposePodConstant.PODINDEX_KEY, String.valueOf(mPodIndex));
        subExecutor.getOrderMarketer().lanuchOrder(tradeDirection, limitPrice, quantity, extraParams);
    }

    public void errorSuspend(int failedErrorCode) {
        mExecutor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(mExecutor) {
            @Override
            protected void onRun() throws Exception {
                getExecutor().suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS, failedErrorCode);
            }
        });
    }

    public void errorCancel(int cancelErrorCode) {
        mExecutor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(mExecutor) {
            @Override
            protected void onRun() throws Exception {
                getExecutor().cancel(cancelErrorCode);
            }
        });
    }

    public abstract HostingXQComposeLimitOrderLegChaseParam getLegChaseParam(long sledContractId);

    public abstract HostingXQComposeOrderLimitLegSendOrderExtraParam getSendOrderExtraParam(long sledContractId);

    /**
     * 子执行器是否满足发出一定订单量的基础条件
     */
    protected boolean canSend(XQOrderSubExecutor subExecutor, int sendVolume) {
        ComposeQuotData quotData = getQuotCalculator().getQuotData(subExecutor.getSubSledContractId());
        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(quotData.getComposeLeg());
        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
            if (quotData.getAskPrice1() == null) {
                return false;
            }
        } else {
            if (quotData.getBidPrice1() == null) {
                return false;
            }
        }

        HostingXQComposeOrderLimitLegSendOrderExtraParam sendOrderExtraParam
                = getSendOrderExtraParam(subExecutor.getSubSledContractId());

        // 盘口系数检查
        if (sendOrderExtraParam != null && sendOrderExtraParam.isSetQuantityRatio()
                && sendOrderExtraParam.getQuantityRatio() > 0.0) {
            int totalCount = HandicapManager.Global().getTotal(this
                    , subExecutor.getSubSledContractId()
                    , orderTradeDirection);

            double needQty = sendOrderExtraParam.getQuantityRatio() * (totalCount + sendVolume);

            if (AppLog.infoEnabled()) {
                AppLog.i("shouldSend xqOrderId=" + getOrder().getOrderId()
                        + ", composeLeg=" + quotData.getComposeLeg().getVariableName()
                        + ", lockedCount=" + totalCount
                        + ", sendVolume=" + sendVolume
                        + ", tradeDirection=" + orderTradeDirection
                        + ", needQty=" + needQty
                        + ", askQty1=" + quotData.getAskQty1()
                        + ", bidQty1=" + quotData.getBidQty1());
            }

            if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                if (quotData.getAskQty1() < needQty) {
                    return false;
                }
            } else {
                if (quotData.getBidQty1() < needQty) {
                    return false;
                }
            }
        } else {
            AppLog.w("xqOrderId=" + getOrder().getOrderId()
                    + ", composeGraphId=" + getComposeGraph().getComposeGraphId()
                    + ", can not get sendOrderExtraParam quantityRatio for sledContractId="
                    + subExecutor.getSubSledContractId());
        }

        return true;
    }

    /**
     * 判断是否全部满足发单条件
     */
    public boolean canAllSend() {
        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            int restVolume = getRestVolume(subExecutor);
            if (restVolume <= 0) {
                continue;
            }

            if (!canSend(subExecutor, restVolume)) {
                return false;
            }
        }

        return true;
    }

    public boolean isGapPriceTriggered() {
        Double gapPrice = getQuotCalculator().getGapPrice(getDirection());
        if (gapPrice == null) {
            return false;
        }

        boolean triggered;
        if (getDirection() == HostingXQTradeDirection.XQ_BUY) {
            triggered = (Double.compare(gapPrice, getLimitPrice()) <= 0);
        } else {
            triggered = (Double.compare(gapPrice, getLimitPrice()) >= 0);
        }

        if (AppLog.debugEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("isGapPriceTriggered xqOrderId=").append(getOrder().getOrderId())
                    .append(", podIndex=").append(getPodIndex())
                    .append(", targetVolume=").append(getTargetVolume())
                    .append(", direction=").append(getDirection())
                    .append(", grapPrice=").append(gapPrice)
                    .append(", limitPrice=").append(getLimitPrice())
                    .append(", triggered=").append(triggered);
            if (triggered && AppLog.infoEnabled()) {
                AppLog.i(logBuilder.toString());
            } else {
                AppLog.d(logBuilder.toString());
            }
        }

        return triggered;
    }

    public void ajustHandicap(XQOrderSubExecutor subExecutor, HostingExecOrder latestOrder) {
        if (latestOrder.isSetTradeSummary()) {
            HandicapManager.Global().requireOpLock(this
                    , new HashSet<>(Arrays.asList(subExecutor.getSubSledContractId())));
            try {
                HandicapManager.Global().set(this
                        , subExecutor.getSubSledContractId()
                        , getOrderTradeDirection(getComposeLeg(subExecutor.getSubSledContractId()))
                        , latestOrder.getOrderDetail().getQuantity()
                                - latestOrder.getTradeSummary().getUpsideTradeTotalVolume());
            } finally {
                HandicapManager.Global().releaseOpLock(this);
            }
        }
    }

    public void clearHandicap(XQOrderSubExecutor subExecutor) {
        // 某条腿全部购买完成，释放所需要的库存
        HandicapManager.Global().requireOpLock(this
                , new HashSet<>(Arrays.asList(subExecutor.getSubSledContractId())));
        try {
            HandicapManager.Global().clear(this, subExecutor.getSubSledContractId());
        } finally {
            HandicapManager.Global().releaseOpLock(this);
        }
    }

    public void clearAllHandicap() {
        HandicapManager.Global().requireOpLock(this, getExecutor().getSubExecutors().keySet());
        try {
            for (long sledContractId : getExecutor().getSubExecutors().keySet()) {
                HandicapManager.Global().clear(this, sledContractId);
            }
        } finally {
            HandicapManager.Global().releaseOpLock(this);
        }
    }

    public void checkPodState(boolean stateExpression) {
        if (stateExpression) {
            return ;
        }

        AppLog.e(new IllegalStateException("xqOrderId=" + getOrder().getOrderId()
                + ", podIndex=" + getPodIndex()
                + ", targetVolume=" + getTargetVolume()));
        errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
    }

    public double calOrderPrice(HostingExecOrderTradeDirection orderTradeDirection
            , double targetPrice
            , double tickSize) {
        BigDecimal tickSizeBigDecimal = new BigDecimal(String.valueOf(tickSize));

        BigDecimal targetTicksBD = new BigDecimal(String.valueOf(targetPrice)).divide(tickSizeBigDecimal
                , 16
                , BigDecimal.ROUND_HALF_UP);
        long targetTicks = targetTicksBD.longValue();
        if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_SELL) {
            // 对卖价进行修正，对于不能整除的金额，按照百分之一进行分割
            if ((targetTicksBD.doubleValue() - targetTicks) >= 0.01) {
                targetTicks = targetTicks + 1;
            }
        }

        return new BigDecimal(targetTicks).multiply(tickSizeBigDecimal).doubleValue();
    }

    protected abstract void handleQuotationItem(QuotationItem updatedQuotationItem) throws ErrorInfo;

    public abstract void onStart(HostingXQOrderResumeMode resumeMode) throws ErrorInfo;
    public abstract void onDestroy() throws ErrorInfo;

    public abstract void onComposeLegOrderRunning(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder);
    public abstract void onComposeLegOrderCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo;
    public abstract void onComposeLegOrderInnerCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo;

    public abstract void onComposeLegOrderFinished(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo;


}
