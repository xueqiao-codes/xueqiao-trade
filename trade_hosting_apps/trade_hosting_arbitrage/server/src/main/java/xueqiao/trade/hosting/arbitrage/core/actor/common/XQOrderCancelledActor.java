package xueqiao.trade.hosting.arbitrage.core.actor.common;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderManager;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;

public class XQOrderCancelledActor implements IXQOrderActor {

    private XQOrderBaseExecutor mExecutor;

    private void waitProcessTradeListComplete() throws ErrorInfo {
        if (!mExecutor.isTradeListComplete()) {
            return ;
        }

        XQOrderActorHelper.constructUnRelatedTrades(mExecutor);
        XQOrderManager.Global().rmExecutor(mExecutor.getContext().getOrderId());
    }

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = executor;
        mExecutor.updateCleanTimestampMs();

        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderCancelledActor onCreate, xqOrderId=" + mExecutor.getOrder().getOrderId());
        }

        waitProcessTradeListComplete();
    }

    @Override
    public void onDestroy() throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderCancelledActor onDestory, xqOrderId=" + mExecutor.getOrder().getOrderId());
        }

        XQOrderActorHelper.constructUnRelatedTrades(mExecutor);
    }

    @Override
    public void onTradeListChanged(XQOrderSubExecutor subExecutor) throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderCancelledActor onTradeListChanged, xqOrderId=" + mExecutor.getOrder().getOrderId());
        }
        waitProcessTradeListComplete();
    }

}
