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
        FIRSTLEG_WAITING_TRIGGER,  // ????????????
        FIRSTLEG_RUNNING,  // ???????????????
        AFTERLEG_WAITING_TRIGGER, // ??????????????????????????????????????????
        UNFINISHLEG_CHASING, // ??????????????????
    }

    private Set<Long> mFirstLegSeletions = new HashSet<>(); // ?????????????????????
    private Map<Long, IImpactCostProvider> mImpactCostProviders
            = new HashMap<>();  // ?????????????????????

    private Status mState;   // ????????????
    private long mFirstLegSledContractId;  // ?????????????????????ID
    private int mFirstLegTradedVolume;

    private ComposeChaseEngine mChaseEngine; // ????????????

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
        // ???????????????????????????
        double firstImpactCost = Double.MIN_VALUE;
        for (long sledContractId : mImpactCostProviders.keySet()) {
            Double impactCost = mImpactCostProviders.get(sledContractId).getImpactCost();
            if (impactCost == null || Double.isNaN(impactCost) || Double.isInfinite(impactCost)) {
                // ????????????????????????, ??????????????????????????????
                impactCost = Double.MAX_VALUE;
            }

            if (Double.compare(impactCost, firstImpactCost) > 0) {
                firstImpactCost = impactCost;
                mFirstLegSledContractId = sledContractId;
            } else if (Double.compare(impactCost, firstImpactCost) == 0) {
                // ????????????????????????????????????
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
            // ????????????
            HostingXQComposeLimitOrderLegByPriceTryingParam legByPriceTryingParam
                    = getLegByPriceTryingParam(mFirstLegSledContractId);

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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
            // ????????????
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
        // ???????????????????????????????????????
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
            // ??????????????????????????????????????????
            AppLog.e("checkFirstLegTrigger called, but hasOrderRunning"
                    + ", xqOrderId=" + getOrder().getOrderId()
                    + ", podIndex=" + getPodIndex()
                    + ", targetVolume=" + getTargetVolume()
                    + ", state=" + mState);
            errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_INNER_STATE_ERROR.getValue());
            return;
        }

        if (mFirstLegTradedVolume <= 0) {
            // ?????????????????????
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

        // ??????????????????
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

        // ??????????????????????????????
        HandicapManager.Global().requireOpLock(this, getExecutor().getSubExecutors().keySet());
        try {
            if (!canFirstLegSend()) {
                return;
            }

            // ??????????????????????????????
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

        // ??????????????????, ??????????????????????????????????????????
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

        // ???????????????????????????
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
            // ??????????????????
            return ;
        }

        // ???????????????????????????????????????????????????????????????
        if (System.currentTimeMillis() - latestOrder.getCreateTimestampMs()
                < ComposePodConstant.FIRSTLEG_RUNNING_ORDER_MIN_PERIOD_MS) {
            return ;
        }

        firstLegSubExecutor.cancelOrderRunning();
    }

    private void startChaseAfterLegs() throws ErrorInfo {
        // ??????????????????????????????????????????????????????????????????????????????????????????????????????
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

            // ????????????
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
        // ???????????????????????????
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
        // ???????????????????????????
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
            // ????????????????????????????????????????????????????????????
            for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
                if (subExecutor.hasOrderRunning()) {
                    // ?????????????????????????????????????????????????????????????????????
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
                // ???????????????????????????
                // ???????????????????????????????????????
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
                // ????????????
                checkPodState(!hasOtherLegsRunning);
                if (firstHasTradedSubExecutor.hasOrderRunning()) {
                    mState = Status.FIRSTLEG_RUNNING;
                } else {
                    mState = Status.FIRSTLEG_WAITING_TRIGGER;
                }
            }

        } else {
            // ????????????????????????????????????
            mState = Status.UNFINISHLEG_CHASING;
        }

    }

    /**
     *  ??????onStart????????????????????????????????????????????????????????????
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

        // ?????????????????????????????????
        if (mState == Status.FIRSTLEG_WAITING_TRIGGER) {
            if (startMode == HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST) {
                mState = Status.UNFINISHLEG_CHASING;
            }
        } else if (mState == Status.FIRSTLEG_RUNNING) {
            // ????????????????????????????????????????????????????????????????????????????????????
            checkPodState(startMode != HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_WITHOUT_COST);
        } else if (mState == Status.UNFINISHLEG_CHASING) {
            if (!getExecutor().hasSubExecutorRunning()) {
                if (startMode == HostingXQOrderResumeMode.RESUME_MODE_COMPOSE_CHASING_BY_PRICE) {
                    // ??????????????????????????????
                    mState = Status.AFTERLEG_WAITING_TRIGGER;
                } else if (startMode == HostingXQOrderResumeMode.RESUME_MODE_NONE){
                    // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
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
                        // ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
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

        // ???????????????????????????????????????????????????????????????
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

            mFirstLegTradedVolume = getTradedVolume(subExecutor); // ???????????????????????????
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
            // ?????????????????????
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
