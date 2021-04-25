package xueqiao.trade.hosting.arbitrage.core.executor;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderExecutorFactory;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.actor.condition.XQOrderConditionRunningActor;
import xueqiao.trade.hosting.arbitrage.core.actor.condition.XQOrderConditionStartingActor;
import xueqiao.trade.hosting.arbitrage.core.actor.condition.XQOrderConditionSuspendedActor;
import xueqiao.trade.hosting.arbitrage.core.data.CalculateTradesResult;
import xueqiao.trade.hosting.arbitrage.core.effectdate.EffectDateController;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQCondition;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionAction;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionOrderLabel;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionTrigger;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDate;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderPrice;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderPriceType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.trade.time.TimeUtil;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpanList;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.ext.GFDOrderCalculator;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

import java.util.concurrent.TimeUnit;

/**
 * 条件单执行器
 * @author wangli
 *
 */
public class XQOrderConditionExecutor extends XQOrderBaseExecutor {
    private static final long AJUST_START_TIMESTAMPMS = -2000;
    private static final long AJUST_END_TIMESTAMPMS = 0;

    private SledContractDetails mTargetContractDetail;
    private HostingComposeGraph mTargetComposeGraph;
    private IHostingContractApi mContractApi;
    private IHostingComposeApi mComposeApi;
    private EffectDateController mEffectDateController;

    private TradeTimeSpanList mTradeTimeSpanList;
    
    public XQOrderConditionExecutor(HostingXQOrder order) throws ErrorInfo {
        super(order);
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
    }
    
    public SledContractDetails getTargetContractDetail() {
        return mTargetContractDetail;
    }
    
    public HostingComposeGraph getTargetComposeGraph() {
        return mTargetComposeGraph;
    }
    
    public IHostingContractApi getContractApi() {
        return mContractApi;
    }
    
    @Override
    public TradeTimeSpanList getTradeTimeSpanList() {
        return mTradeTimeSpanList;
    }
    
    @Override
    protected long calCleanTimestampMs() throws ErrorInfo {
        if (mTargetComposeGraph != null) {
            TradeTimeSpan endTradeTimeSpan 
                = getTradeTimeSpanList().getNearestSpan(getOrder().getOrderState().getStateTimestampMs());
            if (endTradeTimeSpan == null) {
                return TimeUtil.fromNow24H();
            }
            
            // 组合的条件单的清理策略
            return TimeUtil.fromTimePointMs(endTradeTimeSpan.getEndTimestampMs(), 5, TimeUnit.MINUTES);
        }
        
        // 合约条件单, 寻找触发后的时间点
        TradingTimeAnalysisor analysisor = new TradingTimeAnalysisor(mContractApi);
        analysisor.setCommodityId(mTargetContractDetail.getSledCommodity().getSledCommodityId());
        analysisor.setProcessTimestampMs(getOrder().getOrderState().getStateTimestampMs());
        try {
            if (!analysisor.analysis()) {
                AppLog.e(analysisor);
                return TimeUtil.fromNow24H();
            }
            GFDOrderCalculator cal = new GFDOrderCalculator(analysisor);
            if (!cal.calculate()) {
                AppLog.e(cal);
                return TimeUtil.fromNow24H();
            }
            if (cal.isMarketClosed()) {
                return System.currentTimeMillis() + 60*1000;
            }
            return cal.getTTLTimestampMs();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        } 
        return TimeUtil.fromNow24H();
    }

    public EffectDateController getEffectDateController() {
        if (mEffectDateController == null) {
            mEffectDateController = new EffectDateController(false
                , getOrder().getOrderDetail().getConditionOrderDetail().getEffectDate());
        }
        return mEffectDateController;
    }

    @Override
    public XQOrderHelper.GFDEffectiveTimeSpan createGFDEffectiveTimeSpan(long processTimestampMs) {
        if (mTargetComposeGraph != null) {
            return null;
        }

        return XQOrderHelper.createGFDEffectiveTimeSpan(
                getTargetContractDetail().getSledCommodity().getSledCommodityId()
                , processTimestampMs
                , AJUST_START_TIMESTAMPMS
                , AJUST_END_TIMESTAMPMS
        );
    }

    @Override
    public boolean refreshTradeTimeSpanList() throws ErrorInfo {
        if (mTargetComposeGraph != null) {
            mTradeTimeSpanList = XQOrderHelper.constructTimeSpanList(mTargetComposeGraph);
        } else {
            mTradeTimeSpanList = XQOrderHelper.constructTimeSpanList(
                    mTargetContractDetail.getSledCommodity().getSledCommodityId());
        }
        
        if (mTradeTimeSpanList == null) {
            mTradeTimeSpanList = new TradeTimeSpanList();
            return false;
        }
        mTradeTimeSpanList.ajustTimeSpan(AJUST_START_TIMESTAMPMS, AJUST_END_TIMESTAMPMS);
        
        if (AppLog.debugEnabled()) {
            AppLog.d("refreshTradeTimeSpanList nearest trade time is " 
                    + mTradeTimeSpanList.getNearestSpan(System.currentTimeMillis())
                    + " for xqOrderId=" + getOrder().getOrderId()
                    + ",orderTarget=" + getOrder().getOrderTarget());
        }
        
        return true;
    }

    @Override
    protected void checkOrderTarget(HostingXQTarget target) throws ErrorInfo {
        ParameterChecker.check(target.isSetTargetType(), "targetType must be set");
        ParameterChecker.check(target.isSetTargetKey() && StringUtils.isNotEmpty(target.getTargetKey())
                , "targetKey must be set");
        try {
            NumberUtils.createLong(target.getTargetKey());
        } catch (NumberFormatException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "target's key should be number");
        }
    }
    
    @Override
    protected IXQOrderActor createActor(HostingXQOrderStateValue targetStateValue) {
        if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_STARTING) {
            return new XQOrderConditionStartingActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_RUNNING) {
            return new XQOrderConditionRunningActor();
        } else if (targetStateValue == HostingXQOrderStateValue.XQ_ORDER_SUSPENDED) {
            return new XQOrderConditionSuspendedActor();
        }
            
        return super.createActor(targetStateValue);
    }
    
    @Override
    protected void checkOrderDetail(HostingXQOrderDetail detail) throws ErrorInfo {
        ParameterChecker.check(detail.isSetConditionOrderDetail(), "conditionOrderDetail must be set");
        HostingXQConditionOrderDetail conditionOrderDetail = detail.getConditionOrderDetail();
        
        // 检测有效期的完整性
        ParameterChecker.check(conditionOrderDetail.isSetEffectDate()
                , "conditionOrderDetail's effectDate must be set");
        HostingXQEffectDate effectDate = conditionOrderDetail.getEffectDate();
        ParameterChecker.check(effectDate.isSetType(), "effectDate's type must be set");
        if (effectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_TODAY) {
            ParameterChecker.check(getOrder().getOrderTarget().getTargetType() == HostingXQTargetType.CONTRACT_TARGET
                    , "only contract target supports effect type today");
        } else if (effectDate.getType() == HostingXQEffectDateType.XQ_EFFECT_PERIOD) {
            ParameterChecker.check(effectDate.isSetStartEffectTimestampS() && effectDate.getStartEffectTimestampS() >= 0
                    , "effectDate's startEffectTimestampS should be set and >= 0");
            ParameterChecker.check(effectDate.isSetEndEffectTimestampS() && effectDate.getEndEffectTimestampS() > 0
                    , "effectDate's endEffectTimestampS should be set and > 0");
            ParameterChecker.check(effectDate.getEndEffectTimestampS() > effectDate.getStartEffectTimestampS()
                    , "effectDate's endEffectTimestampS should > startEffectTimestampS");
        }
        
        if (conditionOrderDetail.isSetLabel()) {
            if (conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_LOST_BUY
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_LOST_SELL
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_PROFIT_BUY
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_PROFIT_SELL) {
                ParameterChecker.check(conditionOrderDetail.getConditionsSize() == 1
                        , "conditionOrderDetail's conditions size should be 1");
            } else if (conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_BUY
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_SELL) {
                ParameterChecker.check(conditionOrderDetail.getConditionsSize() == 2
                        , "conditionOrderDetail's conditions size should be 2");
            }
        }
        
        
        // 检测条件的完整性
        ParameterChecker.check(conditionOrderDetail.getConditionsSize() > 0
                , "conditionOrderDetail's conditions must be set and not be empty");
        for (int index = 0; index < conditionOrderDetail.getConditions().size(); ++index) {
            HostingXQCondition condition = conditionOrderDetail.getConditions().get(index);
            ParameterChecker.check(condition.isSetConditionTrigger(), "the " + index + " condition must set conditionTrigger");
            ParameterChecker.check(condition.isSetConditionAction(), "the " + index + " condition must set conditionAction");
            
            HostingXQConditionTrigger conditionTrigger = condition.getConditionTrigger();
            ParameterChecker.check(conditionTrigger.isSetTriggerPriceType()
                    , "the " + index + " conditionTrigger must set conditionPriceType");
            ParameterChecker.check(conditionTrigger.isSetTriggerOperator()
                    , "the " + index + " conditionTrigger must set conditionOperator");
            ParameterChecker.check(conditionTrigger.isSetConditionPrice() 
                    && PriceUtils.isAppropriatePrice(conditionTrigger.getConditionPrice())
                    , "the " + index + " conditonTirgger must set appropriate conditionPrice");
            
            HostingXQConditionAction conditionAction = condition.getConditionAction();
            ParameterChecker.check(conditionAction.isSetTradeDirection()
                    , "the " + index + " conditionAction must set tradeDirection");
            ParameterChecker.check(conditionAction.isSetOrderType()
                    , "the " + index + " conditionAction must set orderType");
            ParameterChecker.check(conditionAction.isSetPrice()
                    , "the " + index + " conditionAction must set price");
            ParameterChecker.check(conditionAction.isSetQuantity() && conditionAction.getQuantity() > 0
                    , "the " + index + " conditionAction must set quantity and quantity > 0");
            
            
            if (conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_LOST_BUY
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_PROFIT_BUY
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_BUY) {
                ParameterChecker.check(conditionAction.getTradeDirection() == HostingXQTradeDirection.XQ_BUY
                        , "the " + index + " conditionAction's tradeDirection should be XQ_BUY");
            } else if (conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_LOST_SELL
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_PROFIT_SELL
                    || conditionOrderDetail.getLabel() == HostingXQConditionOrderLabel.LABEL_STOP_SELL) {
                ParameterChecker.check(conditionAction.getTradeDirection() == HostingXQTradeDirection.XQ_SELL
                        , "the " + index + " conditionAction's tradeDirection should be XQ_SELL");
            }
            
            HostingXQOrderPrice conditionActionPrice = conditionAction.getPrice();
            ParameterChecker.check(conditionActionPrice.isSetPriceType()
                    , "the " + index + " conditionAction's price must set priceType");
            if (conditionActionPrice.getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_LIMIT) {
                ParameterChecker.check(conditionActionPrice.isSetLimitPrice() 
                        && PriceUtils.isAppropriatePrice(conditionActionPrice.getLimitPrice())
                    , "the " + index + " conditionAction's price must set appropriate limitPrice");
            }
            if (conditionActionPrice.isSetChasePriceTicks()) {
                ParameterChecker.check(conditionActionPrice.getChasePriceTicks() >= 0
                    , "the " + index + " conditionAction's price of chasePriceTicks must >= 0");
            }
            if (conditionActionPrice.isSetChasePriceValue()) {
                ParameterChecker.check(conditionActionPrice.getChasePriceValue() >= 0.0
                    , "the " + index + " conditionAction's price of chasePriceValue must >= 0");
            }
            
            
            HostingXQOrder conditionOrder = new HostingXQOrder();
            //以下公共参数为假参数，并不真实验证
            conditionOrder.setOrderId("CONDITION");  
            conditionOrder.setSubAccountId(getOrder().getSubAccountId());
            conditionOrder.setSubUserId(getOrder().getSubUserId());
            //
            conditionOrder.setOrderType(conditionAction.getOrderType());
            conditionOrder.setOrderTarget(getOrder().getOrderTarget());
            HostingXQOrderDetail conditionActionOrderDetail = new HostingXQOrderDetail();
            
            if (conditionAction.getOrderType() == HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT) {
                ParameterChecker.check(getOrder().getOrderTarget().getTargetType() == HostingXQTargetType.CONTRACT_TARGET
                        , "the "  + index + " conditionAction's orderType is not matched");
                
                HostingXQContractLimitOrderDetail contractLimitOrderDetail = new HostingXQContractLimitOrderDetail();
                contractLimitOrderDetail.setDirection(conditionAction.getTradeDirection());
                contractLimitOrderDetail.setQuantity(conditionAction.getQuantity());
                contractLimitOrderDetail.setLimitPrice((double)0.0);  // mock
                contractLimitOrderDetail.setEffectDate(new HostingXQEffectDate());
                contractLimitOrderDetail.getEffectDate().setType(HostingXQEffectDateType.XQ_EFFECT_TODAY);
                
                conditionActionOrderDetail.setContractLimitOrderDetail(contractLimitOrderDetail);
                
            } else if (conditionAction.getOrderType() == HostingXQOrderType.XQ_ORDER_COMPOSE_LIMIT) {
                ParameterChecker.check(getOrder().getOrderTarget().getTargetType() == HostingXQTargetType.COMPOSE_TARGET
                        , "the " + index + " conditionAction's orderType is not matched");
                ParameterChecker.check(conditionAction.isSetExtra()
                        , "the " + index + " conditionAction's extra should be set");
                ParameterChecker.check(conditionAction.getExtra().isSetComposeLimitExecParams()
                        , "the " + index + " conditionAction's extra should set composeLimitExecParams");
                
                HostingXQComposeLimitOrderDetail composeLimitOrderDetail = new HostingXQComposeLimitOrderDetail();
                composeLimitOrderDetail.setDirection(conditionAction.getTradeDirection());
                composeLimitOrderDetail.setQuantity(conditionAction.getQuantity());
                composeLimitOrderDetail.setLimitPrice((double)0.0); // mock
                composeLimitOrderDetail.setEffectDate(new HostingXQEffectDate());
                composeLimitOrderDetail.getEffectDate().setType(HostingXQEffectDateType.XQ_EFFECT_FOREVER);
                composeLimitOrderDetail.setExecParams(conditionAction.getExtra().getComposeLimitExecParams());
                conditionActionOrderDetail.setComposeLimitOrderDetail(composeLimitOrderDetail);
                
            } else {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                        , "the " + index + "conditionAction's orderType is not supported");
            }
            conditionOrder.setOrderDetail(conditionActionOrderDetail);
            
            try {
                XQOrderExecutorFactory.createExecutor(conditionOrder).checkOrder();
            } catch (ErrorInfo e) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                        , "the " + index + " conditionAction error, " + e.getErrorMsg());
            }
        }
    }

    @Override
    protected boolean suspendSupported() {
        return true;
    }

    @Override
    protected void prepare() throws ErrorInfo {
        HostingXQTarget orderTarget = getOrder().getOrderTarget();
        if (orderTarget.getTargetType() == HostingXQTargetType.CONTRACT_TARGET) {
            mTargetContractDetail = mContractApi.getContractDetailForSure(NumberUtils.createLong(orderTarget.getTargetKey()));
        } else if (orderTarget.getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            mTargetComposeGraph = mComposeApi.getComposeGraph(NumberUtils.createLong(orderTarget.getTargetKey()));
            if (mTargetComposeGraph == null) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
                        , "composeGraph is not existed");
            }
        }
        
        refreshTradeTimeSpanList();
    }

    @Override
    protected void calculateTrades(CalculateTradesResult result) throws ErrorInfo {
    }

    @Override
    protected int getOrderTargetVolume() {
        return Integer.MAX_VALUE;
    }

}
