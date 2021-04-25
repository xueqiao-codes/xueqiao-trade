package xueqiao.trade.hosting.arbitrage.core.composelimit.pod.impl;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.apache.commons.lang.math.RandomUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.cost.IImpactCostProvider;
import xueqiao.trade.hosting.arbitrage.core.composelimit.cost.ImpactCostProviderFactory;
import xueqiao.trade.hosting.arbitrage.core.composelimit.handicap.HandicapManager;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePod;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.ComposePodConstant;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotData;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderFirstLegChooseMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderFirstLegChooseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByFirstLegExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByPriceTryingParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByTriggerType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeOrderLimitLegSendOrderExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ComposeLegByPod extends ComposePod {
    private enum Status {
        FIRSTLEG_WAITING_TRIGGER,  // 等待触发
        FIRSTLEG_RUNNING,  // 先手腿挂单
        AFTERLEG_WAITING_TRIGGER, // 后手腿按照价差追单，等待触发
        UNFINISHLEG_CHASING, // 未完成腿追单
    }

    private Set<Long> mFirstLegSeletions = new HashSet<>(); // 先手腿可选集合
    private Map<Long, IImpactCostProvider> mImpactCostProviders
            = new HashMap<>();  // 冲击成本计算器

    private Status mState;   // 当前状态
    private long mFirstLegSledContractId;  // 当前先手腿合约ID
    private int mFirstLegTradedVolume;

    private ComposeChaseEngine mChaseEngine; // 追单引擎

    public ComposeLegByPod(int podIndex, XQOrderComposeLimitExecutor executor, int targetVolume) {
        super(podIndex, executor, targetVolume);
    }

    @Override
    public HostingXQComposeLimitOrderLegChaseParam getLegChaseParam(long sledContractId) {
        return getOrderDetail().getExecParams()
                .getExecLegByParams()
                .getLegChaseParams()
                .get(sledContractId);
    }

    @Override
    public HostingXQComposeOrderLimitLegSendOrderExtraParam getSendOrderExtraParam(long sledContractId) {
        return getOrderDetail().getExecParams()
                .getExecLegByParams()
                .getLegSendOrderExtraParam()
                .get(sledContractId);
    }

    private HostingXQComposeLimitOrderLegByPriceTryingParam getLegByPriceTryingParam(long sledContractId) {
        return getOrderDetail().getExecParams()
                .getExecLegByParams()
                .getFirstLegTryingParams()
                .get(sledContractId);
    }

    private HostingXQComposeLimitOrderLegByFirstLegExtraParam getLegByFirstLegExtraParam(long sledContractId) {
        return getOrderDetail().getExecParams()
                .getExecLegByParams()
                .getFirstLegExtraParams()
                .get(sledContractId);
    }

    private HostingXQComposeLimitOrderLegByTriggerType getLegByTriggerType() {
        return getOrderDetail().getExecParams().getExecLegByParams().getLegByTriggerType();
    }

    private boolean chooseFirstLeg() {
        // 冲击成本高为先手腿
        double firstImpactCost = Double.MIN_VALUE;
        for (long sledContractId : mImpactCostProviders.keySet()) {
            Double impactCost = mImpactCostProviders.get(sledContractId).getImpactCost();
            if (impactCost == null || Double.isNaN(impactCost) || Double.isInfinite(impactCost)) {
                // 无法获取冲击成本, 认为冲击成本为最高值
                impactCost = Double.MAX_VALUE;
            }

            if (Double.compare(impactCost, firstImpactCost) > 0) {
                firstImpactCost = impactCost;
                mFirstLegSledContractId = sledContractId;
            } else if (Double.compare(impactCost, firstImpactCost) == 0) {
                // 冲击成本相同，则随机切换
                if (RandomUtils.nextBoolean()) {
                    mFirstLegSledContractId = sledContractId;
                }
            }
        }

        return true;
    }

    private boolean isFirstLegTargetPriceTriggered(Double firstLegTargetPrice
            , HostingXQComposeLimitOrderLegByTriggerType legByTriggerType
            , HostingExecOrderTradeDirection orderTradeDirection
            , ComposeQuotData quotData) {
        if (legByTriggerType == HostingXQComposeLimitOrderLegByTriggerType.PRICE_TRYING) {
            // 挂单触发
            HostingXQComposeLimitOrderLegByPriceTryingParam legByPriceTryingParam
                    = getLegByPriceTryingParam(mFirstLegSledContractId);

            // 理论上这些参数应该在下单前检测到，这里再次做检查，如果出现则属于代码有一定问题
            if (legByPriceTryingParam == null) {
                AppLog.w("isFirstLegTargetPriceTriggered, xqOrderId=" + getOrder().getOrderId()
                        + ", sledContractId=" + mFirstLegSledContractId
                        + ", legByPriceTryingParam is null! BUG???");
                return false;
            }

            if (!legByPriceTryingParam.isSetBeyondInPriceTicks() || legByPriceTryingParam.getBeyondInPriceTicks() < 0) {
                AppLog.w("isFirstLegTargetPriceTriggered, xqOrderId=" + getOrder().getOrderId()
                        + ", sledContractId=" + mFirstLegSledContractId
                        + ", legByPriceTryingParam's beyondInPriceTicks is not set or < 0! BUG???");
                return false;
            }


            BigDecimal ticksValue = null;
            if (legByPriceTryingParam.getBeyondInPriceTicks() > 0) {
                SledContractDetails contractDetails = getExecutor().getSubExecutor(mFirstLegSledContractId).getContractDetails();
                ticksValue = new BigDecimal(String.valueOf(contractDetails.getSledCommodity().getTickSize()))
                        .multiply(new BigDecimal(legByPriceTryingParam.getBeyondInPriceTicks()));
            }

            BigDecimal comparePrice = new BigDecimal(String.valueOf(firstLegTargetPrice));

            if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                if (quotData.getBidPrice1() == null) {
                    return false;
                }

                if (ticksValue != null) {
                    comparePrice = comparePrice.subtract(ticksValue);
                }

                if (Double.compare(comparePrice.doubleValue(), quotData.getBidPrice1()) >= 0) {
                    return true;
                }

            } else {
                if (quotData.getAskPrice1() == null) {
                    return false;
                }

                if (ticksValue != null) {
                    comparePrice = comparePrice.add(ticksValue);
                }

                if (Double.compare(comparePrice.doubleValue(), quotData.getAskPrice1()) <= 0) {
                    return true;
                }
            }

        } else {
            // 到价触发
            if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                if (quotData.getAskPrice1() == null) {
                    return false;
                }

                if (Double.compare(firstLegTargetPrice, quotData.getAskPrice1()) >= 0) {
                    return true;
                }

            } else {
                if (quotData.getBidPrice1() == null) {
                    return false;
                }

                if (Double.compare(firstLegTargetPrice, quotData.getBidPrice1()) <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean canFirstLegSend() {
        // 主要判断后手腿是否能够发单
        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            if (subExecutor.getSubSledContractId() == mFirstLegSledContractId) {
                continue;
            }

            int restVolume = getRestVolume(subExecutor);
            checkPodState(restVolume > 0);

            if (!canSend(subExecutor, restVolume)) {
                return false;
            }
        }
        return true;
    }

    private void checkFirstLegTrigger() throws ErrorInfo {
        if (getExecutor().hasSubExecutorRunning()) {
            // 可能是发单异常引发的状态不对
            AppLog.e("checkFirstLegTrigger called, but hasOrderRunning"
                    + ", xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", state=" + mState);
            errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
            return;
        }

        if (mFirstLegTradedVolume <= 0) {
            // 重新选择先手腿
            if (!chooseFirstLeg()) {
                return ;
            }
        }

        Preconditions.checkState(mFirstLegSledContractId > 0);

        XQOrderSubExecutor firstLegSubExecutor = getExecutor().getSubExecutor(mFirstLegSledContractId);
        int firstLegRestVolume = getRestVolume(firstLegSubExecutor);
        checkPodState(firstLegRestVolume > 0);

        Double firstLegTargetPrice = getQuotCalculator().getTargetPrice(mFirstLegSledContractId, getDirection(), getLimitPrice());
        if (firstLegTargetPrice == null) {
            return ;
        }

        ComposeQuotData quotData = getQuotCalculator().getQuotData(mFirstLegSledContractId);
        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(quotData.getComposeLeg());

        // 区分挂单触发
        HostingXQComposeLimitOrderLegByTriggerType legByTriggerType = getLegByTriggerType();
        boolean triggered = isFirstLegTargetPriceTriggered(firstLegTargetPrice, legByTriggerType, orderTradeDirection, quotData);

        if (AppLog.debugEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("isFirstLegTargetPriceTriggered xqOrderId=").append(getOrder().getOrderId())
                    .append(", podIndex=").append(getPodIndex())
                    .append(", targetVolume=").append(getTargetVolume())
                    .append(", direction=").append(getDirection())
                    .append(", firstLegVariableName=").append(quotData.getComposeLeg().getVariableName())
                    .append(", legByTriggerType=").append(legByTriggerType)
                    .append(", firstLegTargetPrice=").append(firstLegTargetPrice)
                    .append(", bidPrice1=").append(quotData.getBidPrice1())
                    .append(", askPrice1=").append(quotData.getAskPrice1())
                    .append(", triggered=").append(triggered);
            if (triggered && AppLog.infoEnabled()) {
                AppLog.i(logBuilder.toString());
            } else {
                AppLog.d(logBuilder.toString());
            }
        }

        if (!triggered) {
            return ;
        }

        // 判断后手是否限制罚单
        HandicapManager.Global().requireOpLock(this, getExecutor().getSubExecutors().keySet());
        try {
            if (!canFirstLegSend()) {
                return;
            }

            // 锁住后手腿需要的盘口
            for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                if (subExecutor.getSubSledContractId() == mFirstLegSledContractId) {
                    continue;
                }

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

        lanuchOrder(firstLegSubExecutor
                , orderTradeDirection
                , calOrderPrice(orderTradeDirection
                        , firstLegTargetPrice
                        , firstLegSubExecutor.getContractDetails().getSledCommodity().getTickSize())
                , firstLegRestVolume);

        mState = Status.FIRSTLEG_RUNNING;
    }

    private void checkFirstLegRunning(QuotationItem updateQuotationItem) {
        ComposeQuotData quotData = getQuotCalculator().getQuotData(updateQuotationItem.getContractSymbol());
        if (quotData == null) {
            return ;
        }
        if (quotData.getComposeLeg().getSledContractId() != mFirstLegSledContractId) {
            return ;
        }

        XQOrderSubExecutor firstLegSubExecutor = getExecutor().getSubExecutor(mFirstLegSledContractId);
        if (!firstLegSubExecutor.hasOrderRunning()) {
            AppLog.e("checkFirstLegRunning xqOrderId=" + getOrder().getOrderId()
                    + ", mFirstLegSledContractId=" + mFirstLegSledContractId
                    + ", but no order running!");
            errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
            return ;
        }

        // 正在撤销订单, 应该等先手腿撤销订单后再处理
        if (firstLegSubExecutor.getOrderMarketer().isCancelling()) {
            return ;
        }

        Double firstLegTargetPrice = getQuotCalculator().getTargetPrice(mFirstLegSledContractId, getDirection(), getLimitPrice());
        if (firstLegTargetPrice == null) {
            return ;
        }

        HostingXQComposeLimitOrderLegByFirstLegExtraParam firstLegExtraParam
                = getLegByFirstLegExtraParam(mFirstLegSledContractId);
        HostingExecOrderTradeDirection orderTradeDirection = getOrderTradeDirection(quotData.getComposeLeg());

        HostingExecOrder latestOrder = firstLegSubExecutor.getOrderMarketer().getLastestOrder();

        double firstLegTargetOrderPrice = calOrderPrice(orderTradeDirection
                , firstLegTargetPrice
                , firstLegSubExecutor.getContractDetails().getSledCommodity().getTickSize());
        double firstLegOrderPrice = latestOrder.getOrderDetail().getLimitPrice();

        BigDecimal tickSizeBigDecimal = new BigDecimal(
                firstLegSubExecutor.getContractDetails().getSledCommodity().getTickSize());

        // 计算两者之间的点差
        long targetOrderTicks = new BigDecimal(String.valueOf(firstLegTargetOrderPrice))
                .divide(tickSizeBigDecimal, 16, BigDecimal.ROUND_HALF_UP).longValue();
        long orderTicks = new BigDecimal(String.valueOf(firstLegOrderPrice))
                .divide(tickSizeBigDecimal, 16, BigDecimal.ROUND_HALF_UP).longValue();

        long tickDistance = Math.abs(targetOrderTicks - orderTicks);

        if (AppLog.infoEnabled()) {
            AppLog.i("checkFirstLegRunning xqOrderId=" + getOrder().getOrderId()
                     + ", podIndex=" + getPodIndex()
                     + ", targetVolume=" + getTargetVolume()
                     + ", direction=" + getDirection()
                     + ", firstLegVariableName=" + quotData.getComposeLeg().getVariableName()
                     + ", firstLegTargetPrice=" + firstLegTargetOrderPrice
                     + ", firstLegOrderPrice=" + firstLegOrderPrice
                     + ", targetOrderTicks=" + targetOrderTicks
                     + ", orderTicks=" +  orderTicks
                     + ", tickDistance=" + tickDistance
                     + ", revokeDeviatePriceTicks=" + firstLegExtraParam.getRevokeDeviatePriceTicks());
        }


        if (tickDistance < firstLegExtraParam.getRevokeDeviatePriceTicks()) {
            // 没有偏离过大
            return ;
        }

        // 保留一个订单最少停留一定间隔，避免撤单过多
        if (System.currentTimeMillis() - latestOrder.getCreateTimestampMs()
                < ComposePodConstant.FIRSTLEG_RUNNING_ORDER_MIN_PERIOD_MS) {
            return ;
        }

        firstLegSubExecutor.cancelOrderRunning();
    }

    private void startChaseAfterLegs() throws ErrorInfo {
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

        mState = Status.UNFINISHLEG_CHASING;
        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            ComposeQuotData quotData = getQuotCalculator().getQuotData(subExecutor.getSubSledContractId());
            if (quotData.getComposeLeg().getQuantity() <= 0) {
                continue;
            }

            mChaseEngine.startChaseOrder(subExecutor);
        }
    }

    private void checkAfterLegTrigger() throws ErrorInfo {
        if (getExecutor().hasSubExecutorRunning()) {
            AppLog.e("checkAfterLegTrigger called, but hasOrderRunning"
                    + ", xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", state=" + mState);
            errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
            return ;
        }

        if (!isGapPriceTriggered()) {
            return ;
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

                mChaseEngine.addWatchLeg(subExecutor.getSubSledContractId());
            }

        } finally {
            HandicapManager.Global().releaseOpLock(this);
        }

        startChaseAfterLegs();
    }

    @Override
    protected void handleQuotationItem(QuotationItem updatedQuotationItem) throws ErrorInfo {
        if (AppLog.traceEnabled()) {
            AppLog.t("handleQuotationItem xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", mState=" + mState
                    + ", mFirstSledContractId=" + mFirstLegSledContractId
                    + ", updatedQuotationItem=" + updatedQuotationItem);
        }

        if (mState == null) {
            return ;
        }

        if (mState == Status.FIRSTLEG_WAITING_TRIGGER) {
            checkFirstLegTrigger();
        } else if (mState == Status.FIRSTLEG_RUNNING) {
            checkFirstLegRunning(updatedQuotationItem);
        } else if (mState == Status.AFTERLEG_WAITING_TRIGGER) {
            checkAfterLegTrigger();
        } else {
            mChaseEngine.checkChase(updatedQuotationItem);
        }
    }

    private void calFirstLegSelections() {
        // 计算先手腿可选集合
        HostingXQComposeLimitOrderFirstLegChooseParam firstLegChooseParam
                = getOrderDetail().getExecParams().getExecLegByParams().getFirstLegChooseParam();
        if (firstLegChooseParam.getMode()
                == HostingXQComposeLimitOrderFirstLegChooseMode.FIRST_LEG_CHOOSE_MODE_DEFAULT) {
            for (HostingComposeLeg composeLeg : getComposeGraph().getLegs().values()) {
                if (composeLeg.getQuantity() <= 0 || !getQuotCalculator().canCalTargetPrice(composeLeg.getSledContractId())) {
                    continue;
                }
                mFirstLegSeletions.add(composeLeg.getSledContractId());
            }
        } else if (firstLegChooseParam.getMode()
                == HostingXQComposeLimitOrderFirstLegChooseMode.FIRST_LEG_CHOOSE_MODE_APPOINT) {
            ComposeQuotData quotData = getQuotCalculator().getQuotData(firstLegChooseParam.getAppointSledContractId());
            if (quotData != null
                    && quotData.getComposeLeg().getQuantity() > 0
                    && getQuotCalculator().canCalTargetPrice(firstLegChooseParam.getAppointSledContractId())) {
                mFirstLegSeletions.add(firstLegChooseParam.getAppointSledContractId());
            }
        } else if (firstLegChooseParam.getMode()
                == HostingXQComposeLimitOrderFirstLegChooseMode.FIRST_LEG_CHOOSE_MODE_OUTER_DISC) {
            IHostingContractApi contractApi
                    = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            for (HostingComposeLeg composeLeg : getComposeGraph().getLegs().values()) {
                if (composeLeg.getQuantity() <= 0 || !getQuotCalculator().canCalTargetPrice(composeLeg.getSledContractId())) {
                    continue;
                }
                if (contractApi.isInvolExchange(composeLeg.getSledExchangeMic())) {
                    continue;
                }

                mFirstLegSeletions.add(composeLeg.getSledContractId());
            }
        }
    }

    private void constructImpactProviders() {
        for (Long sledContractId : mFirstLegSeletions) {
            mImpactCostProviders.put(sledContractId
                , ImpactCostProviderFactory.Global().getProvider(
                       getExecutor().getSubExecutors().get(sledContractId).getContractDetails()));
        }
    }

    private void restoreState() throws ErrorInfo {
        // 先确定启动时的状态
        int hasTradedLegsCount = 0;
        long firstHasTradedLegSledContractId = 0;
        for (HostingComposeLeg composeLeg : getComposeGraph().getLegs().values()) {
            if (composeLeg.getQuantity() <= 0) {
                continue ;
            }

            int tradedVolume = getTradedVolume(getExecutor().getSubExecutors().get(composeLeg.getSledContractId()));
            if (tradedVolume > 0) {
                hasTradedLegsCount += 1;
            }

            if (firstHasTradedLegSledContractId == 0) {
                firstHasTradedLegSledContractId = composeLeg.getSledContractId();
            }
        }

        if (hasTradedLegsCount == 0) {
            // 说明这个时候处于等待触发或者先手挂单状态
            for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                if (subExecutor.hasOrderRunning()) {
                    // 这里做一个扫描性质的状态监测，确保状态的正确性
                    checkPodState(mState != Status.FIRSTLEG_RUNNING);
                    mState = Status.FIRSTLEG_RUNNING;
                    mFirstLegSledContractId = subExecutor.getSubSledContractId();
                    checkPodState(subExecutor.getTradeListTotalVolume() == 0);
                    checkPodState(mFirstLegSeletions.contains(subExecutor.getSubSledContractId()));
                }
            }
            if (mState != Status.FIRSTLEG_RUNNING) {
                mState = Status.FIRSTLEG_WAITING_TRIGGER;
            }

            return ;
        } else if (hasTradedLegsCount == 1) {
            checkPodState(firstHasTradedLegSledContractId > 0);

            XQOrderSubExecutor firstHasTradedSubExecutor = getExecutor().getSubExecutor(firstHasTradedLegSledContractId);
            mFirstLegSledContractId = firstHasTradedLegSledContractId;
            mFirstLegTradedVolume = getTradedVolume(firstHasTradedSubExecutor);

            boolean hasOtherLegsRunning = false;
            for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                if (subExecutor.getSubSledContractId() == firstHasTradedLegSledContractId) {
                    continue;
                }

                if (subExecutor.hasOrderRunning()) {
                    hasOtherLegsRunning = true;
                    break;
                }
            }

            int firstLegRestVolume = getRestVolume(firstHasTradedSubExecutor);
            if (firstLegRestVolume <= 0) {
                // 先手腿已经完全成交
                // 查看是否具有其他腿订单在跑
                if (!hasOtherLegsRunning) {
                    if (firstHasTradedSubExecutor.hasOrderRunning()) {
                        mState = Status.FIRSTLEG_RUNNING;
                        return ;
                    } else {
                        mState = Status.UNFINISHLEG_CHASING;
                    }
                } else {
                    checkPodState(!firstHasTradedSubExecutor.hasOrderRunning());
                    mState = Status.UNFINISHLEG_CHASING;
                }

            } else {
                // 部分成交
                checkPodState(!hasOtherLegsRunning);
                if (firstHasTradedSubExecutor.hasOrderRunning()) {
                    mState = Status.FIRSTLEG_RUNNING;
                } else {
                    mState = Status.FIRSTLEG_WAITING_TRIGGER;
                }
            }

        } else {
            // 说明这个时候处于追单状态
            mState = Status.UNFINISHLEG_CHASING;
        }

    }

    /**
     *  处理onStart时可能有两种情况，正常调度和程序重启导致
     */
    @Override
    public void onStart(HostingXQOrderResumeMode startMode) throws ErrorInfo {
        calFirstLegSelections();
        if (mFirstLegSeletions.isEmpty()) {
            errorCancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_COMPOSE_NO_ACCEPTABLE_FIRSTLEG.getValue());
            return ;
        }
        constructImpactProviders();

        restoreState();
        Preconditions.checkNotNull(mState);

        mChaseEngine = new ComposeChaseEngine(this);

        // 根据模式信息，进行修正
        if (mState == Status.FIRSTLEG_WAITING_TRIGGER) {
            if (startMode == HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST) {
                mState = Status.UNFINISHLEG_CHASING;
            }
        } else if (mState == Status.FIRSTLEG_RUNNING) {
            // 先手追单的状态理论上不可能被打断，不存在外界传入强制模式
            checkPodState(startMode != HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST);
        } else if (mState == Status.UNFINISHLEG_CHASING) {
            if (!getExecutor().hasSubExecutorRunning()) {
                if (startMode == HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_BY_PRICE) {
                    // 用户指明按照价差追单
                    mState = Status.AFTERLEG_WAITING_TRIGGER;
                } else if (startMode == HostingXQOrderResumeMode.RESUME_MODE_NONE){
                    // 不明确的追单意图，系统恢复导致，这个时候进行追单问题严重，交给客户处理
                    errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_OPERATION_INTENT_UNKOWN.getValue());
                    return ;
                }
            }
        } else {
            checkPodState(false);
        }

        if (mState == Status.UNFINISHLEG_CHASING) {
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
            AppLog.i("ComposeLegByPod onStart! xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", mState=" + mState
                    + ", composeLegs=" + getComposeGraph().getLegs()
                    + ", mFirstLegSelections=" + mFirstLegSeletions
                    + ", mFirstLegSledContractId=" + mFirstLegSledContractId
                    + ", topChasePrices=" + mChaseEngine.getTopChasePrices()
                    + ", watchLegs=" + mChaseEngine.getWatchLegs());
        }

    }

    @Override
    public void onDestroy() throws ErrorInfo {
        for (IImpactCostProvider provider : mImpactCostProviders.values()) {
            provider.release();
        }

        clearAllHandicap();
    }

    private boolean checkFirstLegRunning(String callMethod, XQOrderSubExecutor subExecutor) {
        if (subExecutor.getSubSledContractId() == mFirstLegSledContractId) {
            return true;
        }

        // 先手褪挂单，但是不是跑的先手腿，肯定有问题
        AppLog.e(callMethod + ", xqOrderId=" + getOrder().getOrderId()
                + ", podIndex=" + getPodIndex()
                + ", targetVolume=" + getTargetVolume()
                + ", mState=" + mState
                + ", mFirstLegSledContractId=" + mFirstLegSledContractId
                + ", subExecutor's subSledContractId=" + subExecutor.getSubSledContractId()
                + ", not first leg running!!!");
        errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
        return false;
    }

    @Override
    public void onComposeLegOrderRunning(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) {
        if (mState == Status.UNFINISHLEG_CHASING) {
            ajustHandicap(subExecutor, latestOrder);
            return ;
        } else if (mState == Status.FIRSTLEG_RUNNING) {
            if (!checkFirstLegRunning("onComposeLegOrderRunning", subExecutor)) {
                return;
            }

            mFirstLegTradedVolume = getTradedVolume(subExecutor); // 增加先手腿的成交量
            return ;
        }
    }

    @Override
    public void onComposeLegOrderCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (AppLog.infoEnabled()) {
            AppLog.i("onComposeLegOrderCancelled xqOrderId=" + getOrder().getOrderId()
                + ", subExecutor.subSledContractId=" + subExecutor.getSubSledContractId()
                + ", mFirstLegSledContractId=" + mFirstLegSledContractId
                + ", mState=" + mState);
        }

        if (mState == Status.UNFINISHLEG_CHASING) {
            ajustHandicap(subExecutor, latestOrder);

            int restVolume = getRestVolume(subExecutor);
            if (restVolume <= 0) {
                return ;
            }

            mChaseEngine.tryChaseLeg(subExecutor, restVolume);
            return ;
        } else if (mState == Status.FIRSTLEG_RUNNING) {
            // 先手腿已经撤单
            if (!checkFirstLegRunning("onComposeLegOrderCancelled", subExecutor)) {
                return;
            }

            mFirstLegTradedVolume = getTradedVolume(subExecutor);
            checkPodState(getRestVolume(subExecutor) > 0);

            clearAllHandicap();
            mState = Status.FIRSTLEG_WAITING_TRIGGER;

            if (mFirstLegTradedVolume <= 0) {
                mFirstLegSledContractId = 0;
            }

            return ;
        }
    }

    @Override
    public void onComposeLegOrderInnerCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        onComposeLegOrderCancelled(subExecutor, latestOrder);
    }

    @Override
    public void onComposeLegOrderFinished(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (AppLog.infoEnabled()) {
            AppLog.i("onComposeLegOrderFinished xqOrderId=" + getOrder().getOrderId()
                    + ", subExecutor.subSledContractId=" + subExecutor.getSubSledContractId()
                    + ", mFirstLegSledContractId=" + mFirstLegSledContractId
                    + ", mState=" + mState);
        }

        if (mState == Status.UNFINISHLEG_CHASING) {
            checkPodState(getRestVolume(subExecutor) <= 0);
            clearHandicap(subExecutor);
            return ;
        } else if (mState == Status.FIRSTLEG_RUNNING){
            if (!checkFirstLegRunning("onComposeLegOrderFinished", subExecutor)) {
                return;
            }

            mFirstLegTradedVolume = getTradedVolume(subExecutor);
            checkPodState(getRestVolume(subExecutor) <= 0);

            startChaseAfterLegs();
            return ;
        }

    }

}
