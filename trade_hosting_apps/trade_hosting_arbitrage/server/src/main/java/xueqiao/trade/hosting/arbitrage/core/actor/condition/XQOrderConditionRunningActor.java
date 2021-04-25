package xueqiao.trade.hosting.arbitrage.core.actor.condition;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderConditionExecutor;
import xueqiao.trade.hosting.arbitrage.quotation.ABTQuotationListener;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQCondition;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionAction;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionTrigger;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionTriggerOperator;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQConditionTriggerPriceType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDate;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

import java.io.UnsupportedEncodingException;
import java.util.Set;

public class XQOrderConditionRunningActor extends ABTQuotationListener implements IXQOrderActor{
    
    private XQOrderConditionExecutor mExecutor;
    private QuotCombCalculator mCombCalculator;
    
    private HostingXQTargetType getTargetType() {
        return mExecutor.getOrder().getOrderTarget().getTargetType();
    }
    
    private HostingXQConditionOrderDetail getConditionOrderDetail() {
        return mExecutor.getOrder().getOrderDetail().getConditionOrderDetail();
    }

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderConditionExecutor.class.cast(executor);
        
        // 检测是否已经触发
        if (StringUtils.isNotEmpty(mExecutor.getOrder().getActionOrderId())) {
            mExecutor.finish();
            return ;
        }

        if (!mExecutor.getEffectDateController().handleActorRunning(mExecutor)) {
            return ;
        }
        
        // 订阅所需行情
        super.attachQuotationListener();
        if (getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            try {
                mCombCalculator = new QuotCombCalculator(mExecutor.getTargetComposeGraph());
            } catch (UnsupportedEncodingException e) {
                AppLog.f(e.getMessage(), e);
            }
            
            Set<String> relatedContractSymbols = mCombCalculator.getRelatedContractSymbols();
            for (String contractSymbol : relatedContractSymbols) {
                QuotationDispatcher.Global().registerListener(mCombCalculator.getPlatform()
                        , contractSymbol, this);
            }
        } else {
            try {
                QuotationDispatcher.Global().registerListener(XQOrderHelper.getQuotationPlatform(mExecutor.getTargetContractDetail())
                        , XQOrderHelper.getQuotationContractSymbol(mExecutor.getTargetContractDetail())
                        , this);
            } catch (UnsupportedEncodingException e) {
                AppLog.f(e.getMessage(), e);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            Set<String> relatedContractSymbols = mCombCalculator.getRelatedContractSymbols();
            for (String contractSymbol : relatedContractSymbols) {
                QuotationDispatcher.Global().unRegisterListener(mCombCalculator.getPlatform()
                        , contractSymbol, this);
            }
        } else {
            try {
                QuotationDispatcher.Global().unRegisterListener(XQOrderHelper.getQuotationPlatform(mExecutor.getTargetContractDetail())
                        , XQOrderHelper.getQuotationContractSymbol(mExecutor.getTargetContractDetail()), this);
            } catch (UnsupportedEncodingException e) {
                AppLog.f(e.getMessage(), e);
            }
        }
        super.detachQuotationListener();
    }
    
    @Override
    public TaskThread getTaskThread() {
        return mExecutor.getContext().getWorkThread();
    }
    
    @Override
    public void onHandleQuotationItem(QuotationItem quotationItem) throws Exception {
        QuotationItem outputQuotationItem = quotationItem;
        if (getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            if (!mCombCalculator.onReceivedQuotationItem(quotationItem)) {
                return ;
            }
            outputQuotationItem = mCombCalculator.getCombQuotationItem();
        }
        
        for (HostingXQCondition condition : getConditionOrderDetail().getConditions()) {
            HostingXQConditionTrigger conditionTrigger = condition.getConditionTrigger();
            double triggerPrice = Double.NaN;
            if (conditionTrigger.getTriggerPriceType() == HostingXQConditionTriggerPriceType.XQ_ASK_PRICE) {
                if (outputQuotationItem.getAskPriceSize() <= 0) {
                    continue;
                }
                triggerPrice = outputQuotationItem.getAskPrice().get(0);
            } else if (conditionTrigger.getTriggerPriceType() == HostingXQConditionTriggerPriceType.XQ_BID_PRICE) {
                if (outputQuotationItem.getBidPriceSize() <= 0) {
                    continue;
                }
                triggerPrice = outputQuotationItem.getBidPrice().get(0);
            } else if (conditionTrigger.getTriggerPriceType() == HostingXQConditionTriggerPriceType.XQ_LASTEST_PRICE) {
                triggerPrice = outputQuotationItem.getLastPrice();
            }
            if (!PriceUtils.isAppropriatePrice(triggerPrice)) {
                continue;
            }
            
            if (AppLog.debugEnabled()) {
                AppLog.d("xqOrderId=" + mExecutor.getOrder().getOrderId() + ", triggerPrice=" + triggerPrice
                        + ", conditionTrigger=" + conditionTrigger);
            }
            if (isTriggered(triggerPrice, conditionTrigger)) {
                Double actionPrice = XQOrderHelper.calculateOrderPrice(outputQuotationItem
                        , mExecutor.getOrder().getOrderTarget()
                        , condition.getConditionAction().getTradeDirection()
                        , condition.getConditionAction().getPrice()
                        , mExecutor.getTargetContractDetail());
                if (actionPrice == null) {
                    return ;
                }
                triggerAction(condition.getConditionAction(), actionPrice);
                return ;
            }
        }
    }
    
    private boolean isTriggered(double triggerPrice, HostingXQConditionTrigger conditionTrigger) {
        if (conditionTrigger.getTriggerOperator() == HostingXQConditionTriggerOperator.XQ_OP_LT) {
            return triggerPrice < conditionTrigger.getConditionPrice();
        } else if (conditionTrigger.getTriggerOperator() == HostingXQConditionTriggerOperator.XQ_OP_LE) {
            return triggerPrice <= conditionTrigger.getConditionPrice();
        } else if (conditionTrigger.getTriggerOperator() == HostingXQConditionTriggerOperator.XQ_OP_EQ) {
            return triggerPrice == conditionTrigger.getConditionPrice();
        } else if (conditionTrigger.getTriggerOperator() == HostingXQConditionTriggerOperator.XQ_OP_NE) {
            return triggerPrice != conditionTrigger.getConditionPrice();
        } else if (conditionTrigger.getTriggerOperator() == HostingXQConditionTriggerOperator.XQ_OP_GT) {
            return triggerPrice > conditionTrigger.getConditionPrice();
        } else if (conditionTrigger.getTriggerOperator() == HostingXQConditionTriggerOperator.XQ_OP_GE) {
            return triggerPrice >= conditionTrigger.getConditionPrice();
        }
        return false;
    }
    
    private void triggerAction(HostingXQConditionAction action, Double actionPrice) throws ErrorInfo {
        HostingXQOrder newOrder = new HostingXQOrder();
        newOrder.setOrderId(mExecutor.generateActionOrderId());
        newOrder.setOrderType(action.getOrderType());
        newOrder.setOrderTarget(mExecutor.getOrder().getOrderTarget());
        newOrder.setOrderDetail(new HostingXQOrderDetail());
        if (action.getOrderType() == HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT) {
            HostingXQContractLimitOrderDetail contractLimitOrderDetail = new HostingXQContractLimitOrderDetail();
            contractLimitOrderDetail.setDirection(action.getTradeDirection());
            contractLimitOrderDetail.setQuantity(action.getQuantity());
            contractLimitOrderDetail.setLimitPrice(actionPrice);
            contractLimitOrderDetail.setEffectDate(new HostingXQEffectDate());
            contractLimitOrderDetail.getEffectDate().setType(HostingXQEffectDateType.XQ_EFFECT_TODAY);
            newOrder.getOrderDetail().setContractLimitOrderDetail(contractLimitOrderDetail);
        } else if (action.getOrderType() == HostingXQOrderType.XQ_ORDER_COMPOSE_LIMIT) {
            HostingXQComposeLimitOrderDetail composeLimitOrderDetail = new HostingXQComposeLimitOrderDetail();
            composeLimitOrderDetail.setDirection(action.getTradeDirection());
            composeLimitOrderDetail.setQuantity(action.getQuantity());
            composeLimitOrderDetail.setLimitPrice(actionPrice);
            composeLimitOrderDetail.setEffectDate(new HostingXQEffectDate());
            composeLimitOrderDetail.getEffectDate().setType(HostingXQEffectDateType.XQ_EFFECT_FOREVER);
            composeLimitOrderDetail.setExecParams(action.getExtra().getComposeLimitExecParams());
            newOrder.getOrderDetail().setComposeLimitOrderDetail(composeLimitOrderDetail);
        } else {
            AppLog.w("[WARNING]triggerAction for xqOrderId=" + mExecutor.getOrder().getOrderId()
                    + ", but orderType is not supported for " + action.getOrderType());
            return ;
        }
        mExecutor.createNewOrderForTrigger(newOrder);
        mExecutor.finish();
    }
    

}
