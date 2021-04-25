package xueqiao.trade.hosting.arbitrage.core.actor.composelimit;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.actor.common.XQOrderActorHelper;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

import java.util.Map;
import java.util.TreeSet;

public class XQOrderComposeLimitSuspendedActor implements IXQOrderActor {
    
    private XQOrderComposeLimitExecutor mExecutor;
    
    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderComposeLimitExecutor.class.cast(executor);
        
        if (!mExecutor.getEffectDateController().handleActorSuspended(mExecutor)) {
            return ;
        }

        // 功能性暂停，检查是否具备未关联成交，如果存在未关联成交
        // 如果自动重启，则会导致订单执行间隔太大，导致不确定性产生
        // 将订单置为异常暂停
        HostingXQOrderState orderState = executor.getOrder().getOrderState();
        if (orderState.getSuspendReason() == HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
            Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegs
                    = mExecutor.generateUnRelatedExecTradeLegInfos();
            if (!unRelatedTradeLegs.isEmpty()) {
                mExecutor.suspend(HostingXQSuspendReason.SUSPENDED_ERROR_OCCURS
                        , TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_SUSPENDED_LEG_LEAK_BUT_TRADE_CLOSE.getValue());
            }
        }
    }
    
    @Override
    public void onSuspendReasonChanged(HostingXQSuspendReason newReason) throws ErrorInfo {
        if (newReason != HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
            XQOrderJobScheduler.Global().removeStartOrderJob(mExecutor.getContext().getOrderId());
        }
    }
    
}
