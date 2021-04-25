package xueqiao.trade.hosting.arbitrage.core.composelimit;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

import java.util.Iterator;

public abstract class ComposeBaseEngine implements IComposeEngine {
    
    private XQOrderComposeLimitExecutor mExecutor;

    public HostingComposeGraph getComposeGraph() {
        return mExecutor.getComposeGraph();
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
    
    public int getOrderQuantity() {
        return getOrderDetail().getQuantity();
    }
    
    public XQOrderComposeLimitExecutor getExecutor() {
        return mExecutor;
    }
    
    public TaskThread getWorkThread() {
        return mExecutor.getContext().getWorkThread();
    }
    
    public HostingXQOrder getOrder() {
        return mExecutor.getOrder(); 
    }
    
    public void errorSuspend(int failedErrorCode) {
        getWorkThread().postTask(new OrderExecutorRunnable(mExecutor) {
            @Override
            protected void onRun() throws Exception {
                mExecutor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS, failedErrorCode);
            }
        });
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
        if (quotationItem == null) {
            return ;
        }

        processQuotationItem(quotationItem);
    }

    protected abstract void processQuotationItem(QuotationItem quotationItem) throws ErrorInfo;
    
    @Override
    public void onCreate(XQOrderComposeLimitExecutor executor) throws ErrorInfo {
        this.mExecutor = executor;
    }

    @Override
    public void onDestroy() throws ErrorInfo {
    }
    
    @Override
    public void onLastestOrderChanged(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (latestOrder.isSetTradeSummary()) {
            if (latestOrder.getTradeSummary().getUpsideTradeTotalVolume()
                    != latestOrder.getTradeSummary().getTradeListTotalVolume()) {
                // 目前的订单状态不是完全确保成交数值上的准确性，丢弃掉
                return ;
            }
        }

        HostingExecOrderState orderState = latestOrder.getStateInfo().getCurrentState();
        if (orderState == null) {
            // 发送至执行订单系统中
            onComposeLegOrderRunning(subExecutor, latestOrder);
            return ;
        }
        
        if (orderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_FINISHED) {
            onComposeLegOrderFinished(subExecutor, latestOrder);
        } else if (orderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_DELETED) {
            onComposeLegOrderCancelled(subExecutor, latestOrder);
        } else if (orderState.getValue() == HostingExecOrderStateValue.ORDER_VERIFY_FAILED
                || orderState.getValue() == HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED
                || orderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_REJECTED) {
            onComposeLegOrderFailed(subExecutor, latestOrder);
        } else if (orderState.getValue() == HostingExecOrderStateValue.ORDER_EXPIRED) {
            onComposeLegOrderExpired(subExecutor, latestOrder);
        } else {
            onComposeLegOrderRunning(subExecutor, latestOrder);
        }
    }


    protected void onComposeLegOrderRunning(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) {
    }
    
    protected abstract void onComposeLegOrderFailed(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo;
    
    protected abstract void onComposeLegOrderCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo;
    
    protected abstract void onComposeLegOrderFinished(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder)  throws ErrorInfo;
    
    protected void onComposeLegOrderExpired(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) {
    }

}
