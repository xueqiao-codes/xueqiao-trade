package xueqiao.trade.hosting.arbitrage.core.actor.contractlimit;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.OrderExecutorRunnable;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractLimitExecutor;
import xueqiao.trade.hosting.arbitrage.quotation.ABTQuotationListener;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;
import xueqiao.trade.hosting.storage.dealing.HostingDealingHelper;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

public class XQOrderContractLimitRunningActor extends ABTQuotationListener implements IXQOrderActor {

    private XQOrderContractLimitExecutor mExecutor;
    private boolean mIsQuotationSubscribed = false;
    private boolean mIsDestroyed = false;

    private void subscribeQuotation() {
        super.attachQuotationListener();
        try {
            QuotationDispatcher.Global().registerListener(XQOrderHelper.getQuotationPlatform(mExecutor.getTargetContractDetail())
                    , XQOrderHelper.getQuotationContractSymbol(mExecutor.getTargetContractDetail())
                    , this);
        } catch (UnsupportedEncodingException e) {
            AppLog.f(e.getMessage(), e);
        }
        mIsQuotationSubscribed = true;
    }

    private void unSubscribeQuotation() {
        if (!mIsQuotationSubscribed) {
            return ;
        }

        try {
            QuotationDispatcher.Global().unRegisterListener(XQOrderHelper.getQuotationPlatform(mExecutor.getTargetContractDetail())
                    , XQOrderHelper.getQuotationContractSymbol(mExecutor.getTargetContractDetail()), this);
        } catch (UnsupportedEncodingException e) {
            AppLog.e(e.getMessage(), e);
        }
        super.detachQuotationListener();

        mIsQuotationSubscribed = false;
    }

    private void sendOrder() throws ErrorInfo {
        unSubscribeQuotation();

        if (mExecutor.hasSubExecutorRunning()) {
            return ;
        }

        XQOrderSubExecutor subExecutor = mExecutor.getSubExecutor(mExecutor.getSledContractId());

        double limitPrice = mExecutor.getOrder().getOrderDetail().getContractLimitOrderDetail().getLimitPrice();
        int quantity = mExecutor.getOrder().getOrderDetail().getContractLimitOrderDetail().getQuantity();
        HostingExecOrderTradeDirection tradeDirection = HostingExecOrderTradeDirection.ORDER_BUY;
        if (mExecutor.getOrder().getOrderDetail().getContractLimitOrderDetail().getDirection()
                == HostingXQTradeDirection.XQ_SELL) {
            tradeDirection = HostingExecOrderTradeDirection.ORDER_SELL;
        }

        int tradedVolume = Math.max(subExecutor.getTradeListTotalVolume(), subExecutor.getUpsideTotalVolume());
        Preconditions.checkArgument(tradedVolume >= 0);
        if (quantity <= tradedVolume) {
            mExecutor.finish();
            return ;
        }

        subExecutor.getOrderMarketer().lanuchOrder(tradeDirection
                , limitPrice
                , quantity - tradedVolume);
    }

    private void startNextTimeTick() {
        mExecutor.getContext().getWorkThread().postTaskDelay(new OrderExecutorRunnable(mExecutor) {
            @Override
            protected void onRun() throws Exception {
                onTimeTick();
            }
        }, 1, TimeUnit.SECONDS);
    }

    private void onTimeTick() throws ErrorInfo {
        if (mIsDestroyed || mExecutor.hasSubExecutorRunning()) {
            return ;
        }
        XQOrderSubExecutor subExecutor = mExecutor.getSubExecutor(mExecutor.getSledContractId());
        int quantity = mExecutor.getOrder().getOrderDetail().getContractLimitOrderDetail().getQuantity();
        int tradedVolume = Math.max(subExecutor.getTradeListTotalVolume(), subExecutor.getUpsideTotalVolume());
        if (quantity <= tradedVolume) {
            return ;
        }

        long currentTimestampMs = System.currentTimeMillis();
        if (currentTimestampMs >= mExecutor.getEffectDateController().getRunningTimeSpan().getStartTimestampMs()
                + CLOVariable.MAX_CLO_LIMIT_WAITING_QUOTATION_TIMEMS) {
            sendOrder();
            return ;
        }
        startNextTimeTick();
    }

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderContractLimitExecutor.class.cast(executor);

        if (!mExecutor.getEffectDateController().handleActorRunning(mExecutor)) {
            return ;
        }

        if (mExecutor.hasSubExecutorRunning()) {
            // 说明已经有挂单在外, 正常运行，不用驱动发送订单
            return ;
        }

        if (System.currentTimeMillis() <
                mExecutor.getEffectDateController().getRunningTimeSpan().getStartTimestampMs()
                + CLOVariable.MAX_CLO_LIMIT_WAITING_QUOTATION_TIMEMS) {
            // 利用行情驱动下单
            subscribeQuotation();
            startNextTimeTick();
        } else {
            sendOrder();
        }
    }

    @Override
    public void onDestroy() {
        unSubscribeQuotation();
        mIsDestroyed = true;
    }

    @Override
    public TaskThread getTaskThread() {
        return mExecutor.getContext().getWorkThread();
    }

    @Override
    public void onHandleQuotationItem(QuotationItem quotationItem) throws Exception {
        sendOrder();
    }

    @Override
    public void onLastestOrderChanged(XQOrderSubExecutor subExecutor, HostingExecOrder latestOrder) throws ErrorInfo {
        handleRelatedExecOrderChanged(latestOrder);
    }

    private String getUsefulMsg(HostingExecOrderStateInfo stateInfo) {
        if (!StringUtils.isEmpty(stateInfo.getUpsideUsefulMsg())) {
            return stateInfo.getUpsideUsefulMsg();
        }
        if (stateInfo.getFailedErrorCode() != 0) {
            return HostingDealingHelper.getErrorMsg(stateInfo.getFailedErrorCode());
        }
        return "";
    }

    private void handleRelatedExecOrderChanged(HostingExecOrder relatedExecOrder) throws ErrorInfo {
        if (relatedExecOrder == null) {
            return ;
        }
        HostingExecOrderState relatedExecOrderState = relatedExecOrder.getStateInfo().getCurrentState();
        if (relatedExecOrderState == null) {
            return ;
        }
        if (relatedExecOrderState.getValue() == HostingExecOrderStateValue.ORDER_VERIFY_FAILED) {
            mExecutor.suspend(
                    HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_VERIFY_FAILED.getValue()
                    , getUsefulMsg(relatedExecOrder.getStateInfo()));
            return ;
        }
        if (relatedExecOrderState.getValue() == HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED) {
            mExecutor.suspend(
                    HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_SEND_FAILED.getValue()
                    , getUsefulMsg(relatedExecOrder.getStateInfo()));
            return ;
        }
        if (relatedExecOrderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_REJECTED) {
            mExecutor.suspend(
                    HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_REJECTED.getValue()
                    , getUsefulMsg(relatedExecOrder.getStateInfo()));
            return ;
        }

        if (relatedExecOrderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_DELETED){
            mExecutor.suspend(
                    HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_EXEC_ORDER_UPSIDE_CANCELLED.getValue()
                    , getUsefulMsg(relatedExecOrder.getStateInfo()));
            return;
        }

    }

}
