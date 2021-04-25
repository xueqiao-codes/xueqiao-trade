package xueqiao.trade.hosting.arbitrage.core.composelimit.pod.impl;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.handicap.HandicapManager;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePod;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotData;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeOrderLimitLegSendOrderExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

/**
 *  并发执行实现
 */
public class ComposeParallelPod extends ComposePod  {
    private enum Status {
        WAITING_TRIGGER, // 等待触发
        ORDER_CHASING,  // 追单中
    }

    /**
     * 当前的状态
     */
    private Status mState;

    /**
     *  追单引擎
     */
    private ComposeChaseEngine mChaseEngine;

    public ComposeParallelPod(int podIndex
            , XQOrderComposeLimitExecutor executor
            , int targetVolume) {
        super(podIndex, executor, targetVolume);
    }

    @Override
    public HostingXQComposeOrderLimitLegSendOrderExtraParam getSendOrderExtraParam(long sledContractId) {
        return getOrderDetail().getExecParams()
                .getExecParallelParams()
                .getLegSendOrderExtraParam()
                .get(sledContractId);
    }

    public HostingXQComposeLimitOrderLegChaseParam getLegChaseParam(long sledContractId) {
        return getOrderDetail().getExecParams()
                 .getExecParallelParams()
                 .getLegChaseParams()
                 .get(sledContractId);
    }


    private void checkTrigger() throws ErrorInfo {
        if (getExecutor().hasSubExecutorRunning()) {
            // 可能是发单异常引发的状态不对
            AppLog.e("checkTrigger called, but hasOrderRunning"
                    + ", xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", state=" + mState);
            errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
            return;
        }

        if (!isGapPriceTriggered()) {
            return;
        }

        HandicapManager.Global().requireOpLock(this, getExecutor().getSubExecutors().keySet());
        try {
            if (!canAllSend()) {
                return;
            }

            // 先锁盘口
            for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                int restVolume = getRestVolume(subExecutor);
                if (restVolume <= 0) {
                    continue;
                }

                HandicapManager.Global().set(this
                        , subExecutor.getSubSledContractId()
                        , getOrderTradeDirection(getComposeLeg(subExecutor.getSubSledContractId()))
                        , restVolume);
            }

        } finally {
            HandicapManager.Global().releaseOpLock(this);
        }

        // 优先记录开始追单的情况，如果这段代码出现异常，不会导致追单状态不完整
        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            ComposeQuotData quotData = getQuotCalculator().getQuotData(subExecutor.getSubSledContractId());
            if (quotData.getComposeLeg().getQuantity() > 0) {
                continue;
            }

            HostingExecOrderTradeDirection orderTradeDirection
                    = getOrderTradeDirection(getComposeLeg(subExecutor.getSubSledContractId()));
            if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                getExecutor().getSubRecorder(subExecutor.getSubSledContractId())
                             .updateLatestRecordPrice(quotData.getAskPrice1());
            } else {
                getExecutor().getSubRecorder(subExecutor.getSubSledContractId())
                             .updateLatestRecordPrice(quotData.getBidPrice1());
            }
        }

        mState = Status.ORDER_CHASING;
        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            ComposeQuotData quotData = getQuotCalculator().getQuotData(subExecutor.getSubSledContractId());
            if (quotData.getComposeLeg().getQuantity() <= 0) {
                continue;
            }

            mChaseEngine.startChaseOrder(subExecutor);
        }
    }

    @Override
    protected void handleQuotationItem(QuotationItem updatedQuotationItem) throws ErrorInfo {
        if (AppLog.traceEnabled()) {
            AppLog.t("handleQuotationItem xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", mState=" + mState
                    + ", updatedQuotationItem=" + updatedQuotationItem);
        }

        if (mState == null) {
            return ;
        }

        if (mState == Status.WAITING_TRIGGER) {
            checkTrigger();
        } else {
            mChaseEngine.checkChase(updatedQuotationItem);
        }
    }

    private void restoreState() throws ErrorInfo {
        if (getExecutor().hasSubExecutorRunning()) {
            mState =  Status.ORDER_CHASING;
            return ;
        }
        mState =  Status.WAITING_TRIGGER;
    }

    @Override
    public void onStart(HostingXQOrderResumeMode startMode) throws ErrorInfo {
        mChaseEngine = new ComposeChaseEngine(this);
        restoreState();
        Preconditions.checkNotNull(mState);

        // 瘸腿的情况下才准强制追单
        if (mState == Status.WAITING_TRIGGER
                && startMode == HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST
                && !getUnRelatedTradeLegInfos().isEmpty()) {
            mState = Status.ORDER_CHASING;
            return;
        }

         if (mState == Status.ORDER_CHASING) {
             HandicapManager.Global().requireOpLock(this, getExecutor().getSubExecutors().keySet());
             try {
                 for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                     int restVolume = getRestVolume(subExecutor);
                     if (restVolume > 0) {
                         mChaseEngine.addWatchLeg(subExecutor.getSubSledContractId());
                         HandicapManager.Global().set(this
                                 , subExecutor.getSubSledContractId()
                                 , getOrderTradeDirection(getComposeLeg(subExecutor.getSubSledContractId()))
                                 , restVolume);
                     }

                     if (subExecutor.hasOrderRunning()) {
                         // 对于恢复的订单，因为失去了原有的现场，这里采用保守的追价保护，不允许订单变更价格
                         mChaseEngine.setTopChasePrice(subExecutor.getSubSledContractId()
                                 , subExecutor.getOrderMarketer().getLastestOrder().getOrderDetail().getLimitPrice());
                     }

                 }
             } finally {
                 HandicapManager.Global().releaseOpLock(this);
             }
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("ComposeParallelPod onStart! xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", composeLegs=" + getComposeGraph().getLegs()
                    + ", mState=" + mState
                    + ", topChasePrices=" + mChaseEngine.getTopChasePrices()
                    + ", watchLegs=" + mChaseEngine.getWatchLegs());
        }
    }

    @Override
    public void onDestroy() {
        if (AppLog.infoEnabled()) {
            AppLog.i("ComposeParallelPod onDestroy! xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume());
        }

        clearAllHandicap();
    }

    @Override
    public void onComposeLegOrderRunning(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) {
        if (mState != Status.ORDER_CHASING) {
            AppLog.w("onComposeLegOrderRunning, xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", but mState not in ORDER_CHASE");
            return ;
        }

        ajustHandicap(subExecutor, latestOrder);
    }

    @Override
    public void onComposeLegOrderCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (mState != Status.ORDER_CHASING) {
            AppLog.w("onComposeLegOrderCancelled, xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", but mState not in ORDER_CHASING");
            return ;
        }

        // 解锁库存
        ajustHandicap(subExecutor, latestOrder);

        int restVolume = getRestVolume(subExecutor);
        if (restVolume <= 0) {
            return ;
        }

        mChaseEngine.tryChaseLeg(subExecutor, restVolume);
    }

    @Override
    public void onComposeLegOrderInnerCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        onComposeLegOrderCancelled(subExecutor, latestOrder);
    }

    @Override
    public void onComposeLegOrderFinished(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        checkPodState(getRestVolume(subExecutor) <= 0);
        clearHandicap(subExecutor);
    }
}
