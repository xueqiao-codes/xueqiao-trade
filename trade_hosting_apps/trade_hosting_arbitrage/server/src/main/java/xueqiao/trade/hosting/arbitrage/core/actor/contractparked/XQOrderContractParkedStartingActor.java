package xueqiao.trade.hosting.arbitrage.core.actor.contractparked;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;

public class XQOrderContractParkedStartingActor implements IXQOrderActor {

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        XQOrderJobScheduler.Global().removeStartOrderJob(executor.getContext().getOrderId());
        
        if (!executor.refreshTradeTimeSpanList()) {
            executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_CONSTRUCT_FAILED.getValue());
            return ;
        }
        
        TradeTimeSpan nearestSpan 
            = executor.getTradeTimeSpanList().getNearestSpan(executor.getOrder().getCreateTimestampMs());
        if (nearestSpan == null) {
            executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_TRADETIME_NO_RECENT.getValue());
            return ;
        }
        
        
        long currentTimestampMs = System.currentTimeMillis();
        if (currentTimestampMs > nearestSpan.getStartTimestampMs() + ParkedVariable.MAX_PARKED_STARTING_PERIOD_MS) {
            // 启动时间过久
            executor.cancel(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_CANCELLED_STARTING_TOO_LONG_FOR_PARKED.getValue());
            return ;
        } else if (currentTimestampMs < nearestSpan.getStartTimestampMs() - ParkedVariable.PRE_START_PERIOD_MS) {
            executor.suspend(HostingXQSuspendReason.SUSPENDED_FUNCTIONAL
                    , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_NOTIN_TRADETIME.getValue());
            return ;
        }
        
        executor.startFinished();
    }

}
