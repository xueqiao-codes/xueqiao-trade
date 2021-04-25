package xueqiao.trade.hosting.arbitrage.core.actor.contractparked;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractParkedExecutor;
import xueqiao.trade.hosting.arbitrage.quotation.ABTQuotationListener;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractParkedOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDate;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderPrice;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderPriceType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class XQOrderContractParkedRunningActor extends ABTQuotationListener implements IXQOrderActor {
    
    private XQOrderContractParkedExecutor mExecutor;
    private TradeTimeSpan mTriggerTimeSpan;
    private boolean mIsDestroyed = false;
    
    public HostingXQContractParkedOrderDetail getContractParkedOrderDetail() {
        return mExecutor.getOrder().getOrderDetail().getContractParkedOrderDetail();
    }
    
    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderContractParkedExecutor.class.cast(executor);
        
        if (StringUtils.isNotEmpty(mExecutor.getOrder().getActionOrderId())) {
            mExecutor.finish();
            return ;
        }
        
        mTriggerTimeSpan
            = mExecutor.getTradeTimeSpanList().getNearestSpan(mExecutor.getOrder().getCreateTimestampMs());
        if (mTriggerTimeSpan == null) {
            mExecutor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return ;
        }
        
        long currentTimestampMs = System.currentTimeMillis();
        if (currentTimestampMs > mTriggerTimeSpan.getStartTimestampMs() + ParkedVariable.MAX_PARKED_RUNNING_PERIOD_MS) {
            // 启动时间过久
            mExecutor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_TRIGGER_RUNNING_TOO_LONG_FOR_PARKED.getValue());
            return ;
        }
        
        // 订阅行情
        super.attachQuotationListener();
        try {
            QuotationDispatcher.Global().registerListener(XQOrderHelper.getQuotationPlatform(mExecutor.getTargetContractDetail())
                    , XQOrderHelper.getQuotationContractSymbol(mExecutor.getTargetContractDetail())
                    , this);
        } catch (UnsupportedEncodingException e) {
            AppLog.f(e.getMessage(), e);
        }
        
        startNextTimeTick();
    }
    
    private void startNextTimeTick() {
        mExecutor.getContext().getWorkThread().postTaskDelay(new OrderExecutorRunnable(mExecutor) {
            @Override
            protected void onRun() throws Exception {
                onTimeTick();
            }
        }, 1, TimeUnit.SECONDS);
    }
    
    @Override
    public void onDestroy() {
        try {
            QuotationDispatcher.Global().unRegisterListener(XQOrderHelper.getQuotationPlatform(mExecutor.getTargetContractDetail())
                    , XQOrderHelper.getQuotationContractSymbol(mExecutor.getTargetContractDetail()), this);
        } catch (UnsupportedEncodingException e) {
            AppLog.e(e.getMessage(), e);
        }
        super.detachQuotationListener();

        mIsDestroyed = true;
    }
    
    public void onTimeTick() throws ErrorInfo {
        if (mIsDestroyed || StringUtils.isNotEmpty(mExecutor.getOrder().getActionOrderId())) {
            return ;
        }
        
        long currentTimestampMs = System.currentTimeMillis();
        if (currentTimestampMs > mTriggerTimeSpan.getStartTimestampMs() + ParkedVariable.MAX_PARKED_RUNNING_PERIOD_MS) {
            // 启动时间过久
            mExecutor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_TRIGGER_RUNNING_TOO_LONG_FOR_PARKED.getValue());
            return ;
        }
        
        if (getContractParkedOrderDetail().getPrice().getPriceType() == HostingXQOrderPriceType.ACTION_PRICE_LIMIT) {
            // 对于指定限价单, 超过延时则立即发单
            if (currentTimestampMs >= mTriggerTimeSpan.getStartTimestampMs() + ParkedVariable.MAX_PARKED_LIMIT_WAITING_QUOATION_TIMEMS) {
                triggerNewOrder(getContractParkedOrderDetail().getPrice().getLimitPrice());
                return ;
            }
        }
        
        startNextTimeTick();
    }
    
    // 触发下单
    private void triggerNewOrder(double price) throws ErrorInfo {
        HostingXQOrder newOrder = new HostingXQOrder();
        newOrder.setOrderId(mExecutor.generateActionOrderId());
        newOrder.setOrderType(HostingXQOrderType.XQ_ORDER_CONTRACT_LIMIT);
        newOrder.setOrderTarget(mExecutor.getOrder().getOrderTarget());
        newOrder.setOrderDetail(new HostingXQOrderDetail());
        
        HostingXQContractLimitOrderDetail contractLimitOrderDetail = new HostingXQContractLimitOrderDetail();
        contractLimitOrderDetail.setDirection(getContractParkedOrderDetail().getDirection());
        contractLimitOrderDetail.setQuantity(getContractParkedOrderDetail().getQuantity());
        contractLimitOrderDetail.setLimitPrice(price);
        contractLimitOrderDetail.setEffectDate(new HostingXQEffectDate());
        contractLimitOrderDetail.getEffectDate().setType(HostingXQEffectDateType.XQ_EFFECT_TODAY);
        
        newOrder.getOrderDetail().setContractLimitOrderDetail(contractLimitOrderDetail);
        
        mExecutor.createNewOrderForTrigger(newOrder);
        mExecutor.finish();
    }
    
    @Override
    public TaskThread getTaskThread() {
        return mExecutor.getContext().getWorkThread();
    }

    @Override
    public void onHandleQuotationItem(QuotationItem quotationItem) throws Exception {
        if (StringUtils.isNotEmpty(mExecutor.getOrder().getActionOrderId())) {
            return ;
        }
        
        // 行情到来，说明开市情况
        HostingXQOrderPrice orderPrice = getContractParkedOrderDetail().getPrice();
        Double triggerPrice = XQOrderHelper.calculateOrderPrice(quotationItem
                , mExecutor.getOrder().getOrderTarget()
                , getContractParkedOrderDetail().getDirection()
                , orderPrice
                , mExecutor.getTargetContractDetail());
        if (triggerPrice == null) {
            return ;
        }
        triggerNewOrder(triggerPrice);
    }

}
