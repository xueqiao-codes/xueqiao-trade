package xueqiao.trade.hosting.arbitrage.core.actor.composelimit;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderActorHelper;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;

public class XQOrderComposeLimitStartingActor implements IXQOrderActor  {
    private XQOrderComposeLimitExecutor mExecutor;
    
    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderComposeLimitExecutor.class.cast(executor);
        
        XQOrderJobScheduler.Global().removeStartOrderJob(mExecutor.getContext().getOrderId());
        if (!mExecutor.getEffectDateController().handleActorStarting(mExecutor)) {
            return ;
        }

        // 将恢复模式保留
        mExecutor.startFinished(mExecutor.getOrder().getOrderState().getResumeMode());
    }

}
