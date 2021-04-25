package xueqiao.trade.hosting.arbitrage.core.actor.common;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderManager;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;

public class XQOrderFinishedActor implements IXQOrderActor {

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

        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderFinishedActor onCreate, xqOrderId=" + mExecutor.getOrder().getOrderId());
        }

        XQOrderJobScheduler.Global().removeAllOrderJob(executor.getContext().getOrderId());
        executor.updateCleanTimestampMs();

        waitProcessTradeListComplete();
    }

    @Override
    public void onDestroy() throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderFinishedActor onDestory, xqOrderId=" + mExecutor.getOrder().getOrderId());
        }

        XQOrderActorHelper.constructUnRelatedTrades(mExecutor);
    }

    @Override
    public void onTradeListChanged(XQOrderSubExecutor subExecutor) throws ErrorInfo {
        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderFinishedActor onTradeListChanged, xqOrderId=" + mExecutor.getOrder().getOrderId());
        }

        waitProcessTradeListComplete();
    }

}
