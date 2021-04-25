package xueqiao.trade.hosting.arbitrage.core.executor;

import com.google.common.base.Preconditions;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.core.IDGenerator;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubRecorder;
import xueqiao.trade.hosting.arbitrage.core.actor.composelimit.XQOrderComposeLimitRunningActor;
import xueqiao.trade.hosting.arbitrage.core.actor.composelimit.XQOrderComposeLimitStartingActor;
import xueqiao.trade.hosting.arbitrage.core.actor.composelimit.XQOrderComposeLimitSuspendedActor;
import xueqiao.trade.hosting.arbitrage.core.composelimit.ComposeSettingsProvider;
import xueqiao.trade.hosting.arbitrage.core.data.CalculateTradesResult;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.core.effectdate.EffectDateController;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderFirstLegChooseMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderFirstLegChooseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByFirstLegExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByPriceTryingParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByTriggerType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderParallelParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderSettings;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeOrderLimitLegSendOrderExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeSummary;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.thriftapi.trade_hosting_arbitrageConstants;
import xueqiao.trade.hosting.arbitrage.trade.time.TimeUtil;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class XQOrderComposeLimitExecutor extends XQOrderBaseExecutor {
    private IHostingDealingApi mDealingApi;
    private HostingComposeGraph mComposeGraph;
    private Expression mTradePriceExpression;
    private EffectDateController mEffectDateController;
    
    private TradeTimeSpanList mTradeTimeSpanList = new TradeTimeSpanList();
    
    public XQOrderComposeLimitExecutor(HostingXQOrder order) throws ErrorInfo {
        super(order);

        mDealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
    }
    
    public TradeTimeSpanList getTradeTimeSpanList() {
        return mTradeTimeSpanList;
    }
    
    public HostingComposeGraph getComposeGraph() {
        return mComposeGraph;
    }

    public EffectDateController getEffectDateController() {
        if (mEffectDateController == null) {
            mEffectDateController = new EffectDateController(false
                    , getOrder().getOrderDetail().getComposeLimitOrderDetail().getEffectDate());
        }
        return mEffectDateController;
    }

    @Override
    public boolean refreshTradeTimeSpanList() throws ErrorInfo {
        mTradeTimeSpanList = XQOrderHelper.constructTimeSpanList(mComposeGraph);
        if (mTradeTimeSpanList == null) {
            mTradeTimeSpanList = new TradeTimeSpanList();
            return false;
        }

        HostingXQComposeLimitOrderExecParams execParams
                = getOrder().getOrderDetail().getComposeLimitOrderDetail().getExecParams();
        if (execParams.isSetEarlySuspendedForMarketSeconds() && execParams.getEarlySuspendedForMarketSeconds() > 0) {
            mTradeTimeSpanList.ajustTimeSpan(-2000
                    , -1000* execParams.getEarlySuspendedForMarketSeconds());
        } else {
            mTradeTimeSpanList.ajustTimeSpan(-2000, -5000);
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d("refreshTradeTimeSpanList nearest trade time is " 
                    + mTradeTimeSpanList.getNearestSpan(System.currentTimeMillis())
                    + " for xqOrderId=" + getOrder().getOrderId()
                    + ",orderTarget=" + getOrder().getOrderTarget());
        }
        
        return true;
    }

    @Override
    protected long calCleanTimestampMs() throws ErrorInfo {
        long currentTimestampMs = System.currentTimeMillis();
        
        TradeTimeSpan nearestSpan = mTradeTimeSpanList.getNearestSpan(currentTimestampMs);
        if (nearestSpan == null) {
            return TimeUtil.fromNow24H();  // 交易时间无法获取，保留24小时有效期为安全有效期
        }
        
        if (nearestSpan.getStartTimestampMs() > currentTimestampMs) {
            return TimeUtil.fromNow1Minute(); // 非交易时间清理, 保留1分钟有效期
        }
        
        return TimeUtil.fromTimePointMs(nearestSpan.getEndTimestampMs(), 5, TimeUnit.MINUTES);
    }

    @Override
    protected void checkOrderTarget(HostingXQTarget target) throws ErrorInfo {
        ParameterChecker.check(target.isSetTargetType() && target.getTargetType() == HostingXQTargetType.COMPOSE_TARGET
                , "target's type should be set to compose target");
        ParameterChecker.check(target.isSetTargetKey() && StringUtils.isNotEmpty(target.getTargetKey())
                , "target's key should be set and not be empty");

        long composeGraphId = 0;
        try {
            composeGraphId = NumberUtils.createLong(target.getTargetKey());
        } catch (NumberFormatException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "target's key should be number");
        }

        mComposeGraph = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class)
                .getComposeGraph(composeGraphId);
        if (mComposeGraph == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
                    , "composeGraph is not existed");
        }
    }

    @Override
    protected void checkOrderDetail(HostingXQOrderDetail detail) throws ErrorInfo {
        ParameterChecker.check(detail.isSetComposeLimitOrderDetail(), "composeLimitOrderDetail should be set");
        HostingXQComposeLimitOrderDetail composeLimitOrderDetail = detail.getComposeLimitOrderDetail();
        ParameterChecker.check(composeLimitOrderDetail.isSetDirection()
                , "composeLimitOrderDetail's direction should be set");
        ParameterChecker.check(composeLimitOrderDetail.isSetLimitPrice()
                && PriceUtils.isAppropriatePrice(composeLimitOrderDetail.getLimitPrice())
                , "composeLimitOrderDetail's limitPrice should be set and price should be appropriate");
        ParameterChecker.check(composeLimitOrderDetail.isSetQuantity() && composeLimitOrderDetail.getQuantity() > 0
                , "composeLimitOrderDetails's quantiy should be set and > 0");
        ParameterChecker.check(composeLimitOrderDetail.isSetEffectDate()
                , "composeLimitOrderDetails's effectDate should be set");
        ParameterChecker.check(composeLimitOrderDetail.getEffectDate().isSetType()
                , "composeLimitOrderDetails's type of effectDate should be set");
        if (composeLimitOrderDetail.getEffectDate().getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD) {
            ParameterChecker.check(composeLimitOrderDetail.getEffectDate().isSetStartEffectTimestampS()
                            && composeLimitOrderDetail.getEffectDate().getStartEffectTimestampS() >= 0
                    , "effectDate's startEffectTimestampS should be set and >= 0");
            ParameterChecker.check(composeLimitOrderDetail.getEffectDate().isSetEndEffectTimestampS()
                            && composeLimitOrderDetail.getEffectDate().getEndEffectTimestampS() > 0
                    , "effectDate's endEffectTimestampS should be set and > 0");
            ParameterChecker.check(composeLimitOrderDetail.getEffectDate().getEndEffectTimestampS() >
                            composeLimitOrderDetail.getEffectDate().getStartEffectTimestampS()
                    , "effectDate's endEffectTimestampS should > startEffectTimestampS");
        }

        if (composeLimitOrderDetail.getEffectDate().getType() == HostingXQEffectDateType.XQ_EFFECT_TODAY) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_EFFECT_DATE_TYPE_NOT_SUPPORTED.getValue()
                    , "effectDate's type not supported");
        }
        ParameterChecker.check(composeLimitOrderDetail.isSetExecParams()
                , "composeLimitOrderDetail's execParams should be set");

        HostingXQComposeLimitOrderExecParams execParams = composeLimitOrderDetail.getExecParams();
        if (execParams.isSetExecEveryQty()) {
            ParameterChecker.check(execParams.getExecEveryQty() > 0
                    && execParams.getExecEveryQty() <= composeLimitOrderDetail.getQuantity()
                , "execParams's execEveryQty shoud > 0 and < " + composeLimitOrderDetail.getQuantity());
        }

        if (execParams.isSetExecType()) {
            HostingXQComposeLimitOrderExecType execType = execParams.getExecType();
            if (execType == HostingXQComposeLimitOrderExecType.PARALLEL_LEG) {
                ParameterChecker.check(execParams.isSetExecParallelParams(), "execParam's execParallelParams should be set");
                checkParallelParams(execParams.getExecParallelParams());

            } else if (execType == HostingXQComposeLimitOrderExecType.LEG_BY_LEG) {
                ParameterChecker.check(execParams.isSetExecLegByParams(), "execParam's execLegByParams should be set");
                checkLegByParams(execParams.getExecLegByParams());

            } else {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                        , "execParam's type is unkown");
            }

            if (execParams.isSetEarlySuspendedForMarketSeconds()) {
                ParameterChecker.check(execParams.getEarlySuspendedForMarketSeconds() > 0
                        , "execParam's earlySuspendedForMarketSeconds should > 0");
            }
        }
    }

    private void checkLegChaseParam(String paramDesc
            , HostingComposeLeg composeLeg
            , HostingXQComposeLimitOrderLegChaseParam legChaseParam
            , HostingXQComposeLimitOrderSettings settings) throws ErrorInfo {
        if (legChaseParam == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , paramDesc + "'s legChaseParams should contains key for sledContractId="
                    + composeLeg.getSledContractId());
        }

        if (legChaseParam.isSetTicks() && legChaseParam.getTicks() < 0) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "legChaseParams's ticks of " + paramDesc + " for sledContractId=" + composeLeg.getSledContractId()
                    + " should not < 0");
        }
        if (legChaseParam.getTicks() > settings.getMaxChaseTicks()) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_PARAM_ILLEGAL_TO_SYSTEM_SETTINGS.getValue()
                    , "legChaseParams's ticks of " + paramDesc + " for sledContractId=" + composeLeg.getSledContractId()
                    + " should not > " + settings.getMaxChaseTicks());
        }

        if (legChaseParam.isSetTimes() && legChaseParam.getTimes() < 0) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "legChaseParams's times of " + paramDesc + " for sledContractId=" + composeLeg.getSledContractId()
                    + " should not < 0");
        }

        if (Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class)
                .isInvolExchange(composeLeg.getSledExchangeMic())) {
            if (legChaseParam.getTimes() <= 0 || legChaseParam.getTimes() > settings.getMaxInvolRevokeLimitNum()) {
                throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_PARAM_ILLEGAL_TO_SYSTEM_SETTINGS.getValue()
                        , "legChaseParams's times of " + paramDesc + " for sledContractId=" + composeLeg.getSledContractId()
                        + " should not > 0 and <=" + settings.getMaxInvolRevokeLimitNum());
            }
        }

        if (!legChaseParam.isSetProtectPriceRatio() || legChaseParam.getProtectPriceRatio() <= 0.0) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "legChaseParams's protectPriceRatio of " + paramDesc + " for sledContractId="
                    + composeLeg.getSledContractId() + " should be set and > 0.0");
        }
        if (legChaseParam.getProtectPriceRatio() > settings.getMaxPriceProtectRatio()) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_PARAM_ILLEGAL_TO_SYSTEM_SETTINGS.getValue()
                    , "legChaseParams's protectPriceRatio of " + paramDesc + "for sledContractId=" + composeLeg.getSledContractId()
                    + " should <= " + settings.getMaxPriceProtectRatio());
        }
    }

    private void checkLegSendOrderExtraParam(String paramDesc
            , HostingComposeLeg composeLeg
            , HostingXQComposeOrderLimitLegSendOrderExtraParam sendOrderExtraParam
            , HostingXQComposeLimitOrderSettings settings) throws ErrorInfo {
        if (sendOrderExtraParam == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , paramDesc + "'s legSendOrderExtraParam should contains key for sledContractId="
                    + composeLeg.getSledContractId());
        }
        if (!sendOrderExtraParam.isSetQuantityRatio() || sendOrderExtraParam.getQuantityRatio() <= 0.0) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "sendOrderExtraParam's quantityRatio of " +paramDesc + " for sledContractId="
                    + composeLeg.getSledContractId() + " should be set and > 0.0");
        }
        if (sendOrderExtraParam.getQuantityRatio() < settings.getMinQuantityRatio()) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_PARAM_ILLEGAL_TO_SYSTEM_SETTINGS.getValue()
                    , "sendOrderExtraParam's quantityRatio of " + paramDesc + " for sledContractId="
                    + composeLeg.getSledContractId() + " should >= " + settings.getMinQuantityRatio());
        }
    }

    private void checkParallelParams(HostingXQComposeLimitOrderParallelParams parallelParams) throws ErrorInfo {
        ParameterChecker.check(parallelParams.isSetLegChaseParams()
                , "parallelParams's legChaseParams should be set");
        ParameterChecker.check(parallelParams.isSetLegSendOrderExtraParam()
                , "parallelParams's legSendOrderExtraParam should be set");

        HostingXQComposeLimitOrderSettings settings
                = ComposeSettingsProvider.get(trade_hosting_arbitrageConstants.SYSTEM_SETTING_KEY).getSettings();
        for (HostingComposeLeg composeLeg : mComposeGraph.getLegs().values()) {
            HostingXQComposeLimitOrderLegChaseParam legChaseParam
                    = parallelParams.getLegChaseParams().get(composeLeg.getSledContractId());
            checkLegChaseParam("parallelParams", composeLeg, legChaseParam, settings);

            HostingXQComposeOrderLimitLegSendOrderExtraParam sendOrderExtraParam
                    = parallelParams.getLegSendOrderExtraParam().get(composeLeg.getSledContractId());
            checkLegSendOrderExtraParam("parallelParams", composeLeg, sendOrderExtraParam, settings);
        }
    }

    private void checkLegByParams(HostingXQComposeLimitOrderLegByParams legByParams) throws ErrorInfo {
        ParameterChecker.check(legByParams.isSetLegByTriggerType(), "legByParams's legByTriggerType should be set");
        ParameterChecker.check(legByParams.isSetFirstLegChooseParam(), "legByParams's firstLegChooseParam should be set");

        HostingXQComposeLimitOrderFirstLegChooseParam firstLegChooseParam = legByParams.getFirstLegChooseParam();
        ParameterChecker.check(firstLegChooseParam.isSetMode(), "firstLegChooseParam's mode should be set");

        IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        Set<Long> firstLegSet = new HashSet<>();
        if (firstLegChooseParam.getMode() == HostingXQComposeLimitOrderFirstLegChooseMode.FIRST_LEG_CHOOSE_MODE_APPOINT) {
            ParameterChecker.check(firstLegChooseParam.isSetAppointSledContractId() && firstLegChooseParam.getAppointSledContractId() > 0
                    , "firstLegChooseParam's appointSledContractId should be set and > 0 for FIRST_LEG_CHOOSE_MODE_APPOINT mode");
            firstLegSet.add(firstLegChooseParam.getAppointSledContractId());
        } else  {
            for (HostingComposeLeg composeLeg : mComposeGraph.getLegs().values()) {
                if (composeLeg.getQuantity() <= 0) {
                    // 不参与交易腿不能成为先手腿
                    continue;
                }

                if (firstLegChooseParam.getMode() == HostingXQComposeLimitOrderFirstLegChooseMode.FIRST_LEG_CHOOSE_MODE_OUTER_DISC) {
                    if (!contractApi.isInvolExchange(composeLeg.getSledExchangeMic())) {
                        firstLegSet.add(composeLeg.getSledContractId());
                    }
                } else {
                    firstLegSet.add(composeLeg.getSledContractId());
                }
            }
        }

        if (legByParams.getLegByTriggerType() == HostingXQComposeLimitOrderLegByTriggerType.PRICE_TRYING) {
            ParameterChecker.check(legByParams.isSetFirstLegTryingParams(), "legByParams's firstLegTryingParams should be set");
        }

        ParameterChecker.check(legByParams.isSetLegChaseParams(), "legByParams's legChaseParams should be set");
        ParameterChecker.check(legByParams.isSetLegSendOrderExtraParam()
                , "legByParams's legSendOrderExtraParams should be set");
        ParameterChecker.check(legByParams.isSetFirstLegExtraParams()
                , "legByParams's firstLegExtraParams should be set");


        HostingXQComposeLimitOrderSettings settings
                = ComposeSettingsProvider.get(trade_hosting_arbitrageConstants.SYSTEM_SETTING_KEY).getSettings();
        for (HostingComposeLeg composeLeg : mComposeGraph.getLegs().values()) {
            HostingXQComposeLimitOrderLegChaseParam legChaseParam
                    = legByParams.getLegChaseParams().get(composeLeg.getSledContractId());
            checkLegChaseParam("legByParams", composeLeg, legChaseParam, settings);

            HostingXQComposeOrderLimitLegSendOrderExtraParam sendOrderExtraParam
                    = legByParams.getLegSendOrderExtraParam().get(composeLeg.getSledContractId());
            checkLegSendOrderExtraParam("legByParams", composeLeg, sendOrderExtraParam, settings);

            if (firstLegSet.contains(composeLeg.getSledContractId())) {
                HostingXQComposeLimitOrderLegByFirstLegExtraParam firstLegExtraParam
                        = legByParams.getFirstLegExtraParams().get(composeLeg.getSledContractId());
                ParameterChecker.check(firstLegExtraParam != null
                        , "firstLegExtraParam of legByParams should contains for sledContractId=" + composeLeg.getSledContractId());
                ParameterChecker.check(firstLegExtraParam.isSetRevokeDeviatePriceTicks() && firstLegExtraParam.getRevokeDeviatePriceTicks() > 0
                        , "firstLegExtraParam's revokeDeviatePriceTicks of legByParams for sledContractId=" + composeLeg.getSledContractId()
                                + " should set and > 0");
            }


            // 价格尝试类型时，对于先手退
            if (legByParams.getLegByTriggerType() == HostingXQComposeLimitOrderLegByTriggerType.PRICE_TRYING
                    && firstLegSet.contains(composeLeg.getSledContractId())) {
                HostingXQComposeLimitOrderLegByPriceTryingParam legByPriceTryingParam
                        = legByParams.getFirstLegTryingParams().get(composeLeg.getSledContractId());
                ParameterChecker.check(legByPriceTryingParam != null
                        , "firstLegTryingParams of legByParams should contains for sledContractId=" + composeLeg.getSledContractId());
                ParameterChecker.check(legByPriceTryingParam.isSetBeyondInPriceTicks() && legByPriceTryingParam.getBeyondInPriceTicks() >= 0
                        , "firstLegTryingParams's beyondInPriceTicks of legByParams for sledContractId=" + composeLeg.getSledContractId()
                            + " should set and > 0");
            }
        }

    }

    @Override
    protected boolean suspendSupported() {
        return true;
    }

    @Override
    protected void prepare() throws ErrorInfo {
        if (mComposeGraph == null) {
            mComposeGraph
                    = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class)
                    .getComposeGraph(NumberUtils.createLong(getOrder().getOrderTarget().getTargetKey()));
        }
        if (mComposeGraph == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
                    , "composeGraph is not existed");
        }
        
        for (HostingComposeLeg composeLeg : mComposeGraph.getLegs().values()) {
            mSubExecutors.put(composeLeg.getSledContractId()
                    , new XQOrderSubExecutor(getContext(), composeLeg.getSledContractId()));
        }
        
        mTradePriceExpression = new ExpressionBuilder(mComposeGraph.getFormular())
                .buildinUseBigDecimal(true)
                .variables(mComposeGraph.getLegs().keySet()).build();
        
        refreshTradeTimeSpanList();
    }
    
    @Override
    protected IXQOrderActor createActor(HostingXQOrderStateValue targetStateValue) {
        if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_STARTING) {
            return new XQOrderComposeLimitStartingActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            return new XQOrderComposeLimitSuspendedActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_RUNNING) {
            return new XQOrderComposeLimitRunningActor();
        }
        return super.createActor(targetStateValue);
    }
    
    private int calTotalVolume(TreeSet<UnRelatedExecTradeLegInfo> tradeLegInfoList) {
        int totalVolume = 0;
        for (UnRelatedExecTradeLegInfo tradeLegInfo : tradeLegInfoList) {
            totalVolume += tradeLegInfo.getLeftVolume();
        }
        return totalVolume;
    }
    
    @Override
    protected void calculateTrades(CalculateTradesResult result) throws ErrorInfo {
        Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfos = super.generateUnRelatedExecTradeLegInfos();
        if (unRelatedTradeLegInfos.isEmpty() ) {
            return ;
        }
        
        HostingXQTradeSummary orderTradeSummary = new HostingXQTradeSummary(getOrder().getOrderTradeSummary());
        orderTradeSummary.setSubTradeSummaries(generateSubTradeSummaries());
        result.setOrderTradeSummary(orderTradeSummary);
        
        // 计算成交总量
        int tradeVolume = Integer.MAX_VALUE;
        for (HostingComposeLeg composeLeg : mComposeGraph.getLegs().values()) {
            if (composeLeg.getQuantity() <= 0) {
                // 不参与交易的腿
                continue;
            }
            
            if (!unRelatedTradeLegInfos.containsKey(composeLeg.getSledContractId())) {
                return ;
            }
            
            int composeLegTradeVolume = calTotalVolume(unRelatedTradeLegInfos.get(composeLeg.getSledContractId()))
                    /composeLeg.getQuantity();
            tradeVolume = Math.min(tradeVolume, composeLegTradeVolume);
            if (tradeVolume <= 0) {
                return ;
            }
        }
            
        // 构建一手雪橇组合成交
        XQTradeWithRelatedItem tradeWithRelatedItem = new XQTradeWithRelatedItem();
            
        HostingXQTrade newTrade = new HostingXQTrade();
        newTrade.setOrderId(getOrder().getOrderId());
        newTrade.setTradeId(IDGenerator.Global().createTradeId());
        newTrade.setSubAccountId(getOrder().getSubAccountId());
        newTrade.setSubUserId(getOrder().getSubUserId());
        HostingXQTarget target = new HostingXQTarget();
        target.setTargetType(HostingXQTargetType.COMPOSE_TARGET);
        target.setTargetKey(String.valueOf(mComposeGraph.getComposeGraphId()));
        newTrade.setTradeTarget(target);
        newTrade.setTradeDiretion(getOrder().getOrderDetail().getComposeLimitOrderDetail().getDirection());
        newTrade.setTradeVolume(tradeVolume);
            
        for (HostingComposeLeg composeLeg : mComposeGraph.getLegs().values()) {
            if (composeLeg.getQuantity() == 0) {
                // 不参与交易, 但是参与计算, 获取其开始追单的价格作为参考价
                XQOrderSubRecorder subRecorder = getSubRecorder(composeLeg.getSledContractId());
                if (subRecorder.getLatestUsedPrice() == null) {
                    // 无关联成交价，这里理论上是有BUG
                    AppLog.w("xqOrderId=" + getOrder().getOrderId()
                            + ", sledContractId=" + composeLeg.getSledContractId()
                            + ", composeLeg quantity is 0, but can not get latestUsedPrice!!!");
                    return ;
                }

                XQTradeRelatedItem tradeRelatedItem = new XQTradeRelatedItem();
                tradeRelatedItem.setOrderId(newTrade.getOrderId());
                tradeRelatedItem.setTradeId(newTrade.getTradeId());
                tradeRelatedItem.setExecOrderId(mDealingApi.createOrderId());
                tradeRelatedItem.setExecTradeId(mDealingApi.createTradeId());
                tradeRelatedItem.setExecTradeLegId(mDealingApi.createTradeLegId());
                tradeRelatedItem.setSledContractId(composeLeg.getSledContractId());
                HostingXQComposeLimitOrderDetail orderDetail = getOrder().getOrderDetail().getComposeLimitOrderDetail();
                tradeRelatedItem.setExecTradeLegDirection(HostingExecTradeDirection.TRADE_BUY);
                if (orderDetail.getDirection() == HostingXQTradeDirection.XQ_BUY) {
                    if(composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_SELL) {
                        tradeRelatedItem.setExecTradeLegDirection(HostingExecTradeDirection.TRADE_SELL);
                    }
                } else {
                    if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
                        tradeRelatedItem.setExecTradeLegDirection(HostingExecTradeDirection.TRADE_SELL);
                    }
                }
                tradeRelatedItem.setExecTradeLegPrice(subRecorder.getLatestUsedPrice());
                tradeRelatedItem.setExecTradeLegVolume(0);
                tradeRelatedItem.setRelatedTradeVolume(0);

                tradeWithRelatedItem.addRelatedItem(tradeRelatedItem);

                mTradePriceExpression.setVariable(composeLeg.getVariableName()
                        , subRecorder.getLatestUsedPrice());

            } else {
                TreeSet<UnRelatedExecTradeLegInfo> contractTradeLegInfos
                        = unRelatedTradeLegInfos.get(composeLeg.getSledContractId());

                int needVolume = composeLeg.getQuantity() * tradeVolume;
                BigDecimal tradeQuantity = new BigDecimal(needVolume);
                BigDecimal totalFee = new BigDecimal(0);

                Iterator<UnRelatedExecTradeLegInfo> it = contractTradeLegInfos.iterator();
                while (needVolume > 0) {
                    Preconditions.checkArgument(it.hasNext());

                    UnRelatedExecTradeLegInfo matchTradeLegInfo = it.next();
                    int usedVolume = 0;
                    if (matchTradeLegInfo.getLeftVolume() > needVolume) {
                        usedVolume = needVolume;
                        matchTradeLegInfo.setLeftVolume(matchTradeLegInfo.getLeftVolume() - needVolume);
                    } else {
                        usedVolume = matchTradeLegInfo.getLeftVolume();
                        it.remove();
                    }

                    totalFee = totalFee.add(new BigDecimal(usedVolume).multiply(
                            new BigDecimal(String.valueOf(matchTradeLegInfo.getTradeLeg().getTradeLegInfo().getLegTradePrice()))));

                    tradeWithRelatedItem.addRelatedItem(XQTradeRelatedItem.from(newTrade
                            , matchTradeLegInfo.getTradeLeg(), usedVolume));

                    needVolume -= usedVolume;
                }

                // 计算腿的成交均价
                mTradePriceExpression.setVariable(composeLeg.getVariableName()
                        , totalFee.divide(tradeQuantity, 16, BigDecimal.ROUND_HALF_UP).doubleValue());
            }
                
        }
        
        newTrade.setTradePrice(mTradePriceExpression.evaluate());
        tradeWithRelatedItem.setTrade(newTrade);
        result.getTradeWithRelatedItemsList().add(tradeWithRelatedItem);
        
        if (result.getTradeWithRelatedItemsList().isEmpty()) {
            return ;
        }
        
        // 有成交，需要重新计算成交均价和成交量
        int tradeTotalVolume = 0;
        BigDecimal tradeTotalFee = new BigDecimal(0);
        
        for (XQTradeWithRelatedItem originRelateItem : super.getTradeWithRelatedItems().values()) {
            tradeTotalFee = tradeTotalFee.add(
                    new BigDecimal(String.valueOf(originRelateItem.getTrade().getTradePrice()))
                            .multiply(new BigDecimal(originRelateItem.getTrade().getTradeVolume())));
            tradeTotalVolume += originRelateItem.getTrade().getTradeVolume();
        }
        for (XQTradeWithRelatedItem newRelatedItem : result.getTradeWithRelatedItemsList()) {
            tradeTotalFee = tradeTotalFee.add(
                    new BigDecimal(String.valueOf(newRelatedItem.getTrade().getTradePrice()))
                            .multiply(new BigDecimal(newRelatedItem.getTrade().getTradeVolume())));
            tradeTotalVolume += newRelatedItem.getTrade().getTradeVolume();
        }
        
        orderTradeSummary.setTotalVolume(tradeTotalVolume);
        if (tradeTotalVolume > 0) {
            orderTradeSummary.setAveragePrice(tradeTotalFee.divide(
                    new BigDecimal(tradeTotalVolume), 16, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        
    }

    @Override
    protected int getOrderTargetVolume() {
        return getOrder().getOrderDetail().getComposeLimitOrderDetail().getQuantity();
    }

}
