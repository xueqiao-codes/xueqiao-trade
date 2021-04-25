package xueqiao.trade.hosting.arbitrage.core.actor.contractlimit;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractLimitExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;

public class XQOrderContractLimitSuspendedActor implements IXQOrderActor {
    private XQOrderContractLimitExecutor mExecutor;

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        mExecutor = XQOrderContractLimitExecutor.class.cast(executor);

        if (!mExecutor.getEffectDateController().handleActorSuspended(mExecutor)) {
            return ;
        }
    }

    @Override
    public void onSuspendReasonChanged(HostingXQSuspendReason newReason) throws ErrorInfo {
        if (newReason != HostingXQSuspendReason.SUSPENDED_FUNCTIONAL) {
            XQOrderJobScheduler.Global().removeStartOrderJob(mExecutor.getContext().getOrderId());
        }
    }
}