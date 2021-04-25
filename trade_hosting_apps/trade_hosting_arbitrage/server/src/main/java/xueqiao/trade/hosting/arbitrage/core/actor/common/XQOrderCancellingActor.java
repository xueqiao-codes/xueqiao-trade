package xueqiao.trade.hosting.arbitrage.core.actor.common;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;

import java.util.Map.Entry;

public class XQOrderCancellingActor implements IXQOrderActor {
    
    private XQOrderBaseExecutor mExecutor;
    
    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = executor;
        XQOrderJobScheduler.Global().removeAllOrderJob(mExecutor.getContext().getOrderId());
        
        for (Entry<Long, XQOrderSubExecutor> subExecutorEntry : mExecutor.getSubExecutors().entrySet()) {
            subExecutorEntry.getValue().cancelOrderRunning();
        }
        if (!mExecutor.hasSubExecutorRunning()) {
            mExecutor.cancelFinished();
        }
    }
    
    @Override
    public void onLastestOrderChanged(XQOrderSubExecutor subExecutor
            , HostingExecOrder latestOrder) throws ErrorInfo {
        if (!mExecutor.hasSubExecutorRunning()) {
            mExecutor.cancelFinished();
        }
    }
    
}
