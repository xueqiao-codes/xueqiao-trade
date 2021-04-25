package xueqiao.trade.hosting.arbitrage.core.composelimit.engine;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubRecorder;
import xueqiao.trade.hosting.arbitrage.core.composelimit.ComposeBaseEngine;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotCalculator;
import xueqiao.trade.hosting.arbitrage.core.composelimit.quot.ComposeQuotData;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

public class SimpleComposeEngine extends ComposeBaseEngine {

    private ComposeQuotCalculator mQuotCalculator;
    private boolean mIsRunning = false;
    
    @Override
    public void onCreate(XQOrderComposeLimitExecutor executor) throws ErrorInfo {
        super.onCreate(executor);

        mQuotCalculator = new ComposeQuotCalculator(getExecutor().getComposeGraph()
            , getOrderQuantity());
        mQuotCalculator.updateTradeInfo(getExecutor().getTradeWithRelatedItems().values()
            , getExecutor().getLastestUnRelatedTradeLegInfos());

        if (getExecutor().hasSubExecutorRunning()) {
            mIsRunning = true;
        }
    }
    
    @Override
    public void onDestroy() throws ErrorInfo {
        super.onDestroy();
    }

    @Override
    protected void processQuotationItem(QuotationItem quotationItem) throws ErrorInfo {
        if (!mQuotCalculator.updateQuotationItem(quotationItem)) {
            return ;
        }

        onHandleQuotationChanged();
    }

    @Override
    public void onTradeListChanged(XQOrderSubExecutor subExecutor) {
        mQuotCalculator.updateTradeInfo(getExecutor().getTradeWithRelatedItems().values()
                , getExecutor().getLastestUnRelatedTradeLegInfos());
    }

    private void onHandleQuotationChanged() throws ErrorInfo {
//        if (AppLog.debugEnabled()) {
//            AppLog.d("onHandleQuotationChanged xqOrderId=" + getOrder().getOrderId() + ", mIsRunning=" + mIsRunning);
//        }

        if (mIsRunning) {
            return ;
        }
        
        Double gapPrice = mQuotCalculator.getGapPrice(getDirection());
        if (gapPrice == null) {
            return ;
        }
        
        boolean triggered = false;
        if (getDirection() == HostingXQTradeDirection.XQ_BUY) {
            triggered = (Double.compare(gapPrice, getLimitPrice()) <= 0);
        } else {
            triggered = (Double.compare(gapPrice, getLimitPrice()) >= 0);
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d("xqOrderId=" + getOrder().getOrderId()
                    + ", direction=" + getDirection()
                    + ", grapPrice=" + gapPrice
                    + ", limitPrice=" + getLimitPrice()
                    + ", orderQty=" + getOrderQuantity()
                    + ", triggered=" + triggered);
        }
        
        if (!triggered) {
            return ;
        }

        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            ComposeQuotData quotData = mQuotCalculator.getQuotData(subExecutor.getSubSledContractId());
            if (quotData.getComposeLeg().getQuantity() > 0) {
                continue;
            }

            HostingExecOrderTradeDirection orderTradeDirection = super.getOrderTradeDirection(quotData.getComposeLeg());
            if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                getExecutor().getSubRecorder(subExecutor.getSubSledContractId())
                        .updateLatestRecordPrice(quotData.getAskPrice1());
            } else {
                getExecutor().getSubRecorder(subExecutor.getSubSledContractId())
                        .updateLatestRecordPrice(quotData.getBidPrice1());
            }
        }

        for (XQOrderSubExecutor subExecutor : getExecutor().getSubExecutors().values()) {
            ComposeQuotData quotData = mQuotCalculator.getQuotData(subExecutor.getSubSledContractId());

            if (quotData.getComposeLeg().getQuantity() <= 0) {
                continue;
            }

            int totalVolume = quotData.getComposeLeg().getQuantity() * getOrderQuantity();
            int targetVolume = totalVolume - subExecutor.getUpsideTotalVolume();
            if (targetVolume <= 0) {
                continue ;
            }
                
            HostingExecOrderTradeDirection orderTradeDirection = super.getOrderTradeDirection(quotData.getComposeLeg());
            if (orderTradeDirection == HostingExecOrderTradeDirection.ORDER_BUY) {
                subExecutor.getOrderMarketer().lanuchOrder(orderTradeDirection
                        , quotData.getAskPrice1(), targetVolume);
            } else {
                subExecutor.getOrderMarketer().lanuchOrder(orderTradeDirection
                        , quotData.getBidPrice1(), targetVolume);
            }
        }
        
        mIsRunning = true;
    }

    @Override
    protected void onComposeLegOrderFailed(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_SEND_FAILED.getValue());
    }

    @Override
    protected void onComposeLegOrderCancelled(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        errorSuspend(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_ORDER_SEND_FAILED.getValue());
    }

    @Override
    protected void onComposeLegOrderFinished(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
    }

}
