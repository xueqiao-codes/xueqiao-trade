package xueqiao.trade.hosting.arbitrage.core.actor.common;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;

public class XQOrderFinishingActor implements IXQOrderActor {

    private XQOrderBaseExecutor mExecutor;

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = executor;

        if (!mExecutor.hasSubExecutorRunning()) {
            mExecutor.finishCompleted();
        }
    }

    @Override
    public void onLastestOrderChanged(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (!mExecutor.hasSubExecutorRunning()) {
            mExecutor.finishCompleted();
        }
    }
}
