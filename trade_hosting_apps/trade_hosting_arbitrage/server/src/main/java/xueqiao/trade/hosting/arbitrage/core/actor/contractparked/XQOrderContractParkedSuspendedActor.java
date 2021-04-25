package xueqiao.trade.hosting.arbitrage.core.actor.contractparked;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractParkedExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;

public class XQOrderContractParkedSuspendedActor implements IXQOrderActor {
    
    private XQOrderContractParkedExecutor mExecutor;
    
    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderContractParkedExecutor.class.cast(executor);
        
        HostingXQOrderState orderState = executor.getOrder().getOrderState();
        if (orderState.getSuspendReason() != HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
            return ;
        }
        
        TradeTimeSpan nearestSpan = executor.getTradeTimeSpanList().getNearestSpan(
                executor.getOrder().getCreateTimestampMs());
        if (nearestSpan == null) {
            // 把暂停状态改成异常暂停，让用户手工启动
            mExecutor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return ;
        }
        
        XQOrderJobScheduler.Global().addStartOrderJob(mExecutor.getOrder().getOrderId()
                , nearestSpan.getStartTimestampMs() - ParkedVariable.PRE_START_PERIOD_MS);
    }
    
    
    @Override
    public void onSuspendReasonChanged(HostingXQSuspendReason newReason) throws ErrorInfo {
        if (newReason != HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
            XQOrderJobScheduler.Global().removeStartOrderJob(mExecutor.getContext().getOrderId());
        }
    }
}
