package xueqiao.trade.hosting.arbitrage.core.actor.contractlimit;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderContractLimitExecutor;
import xueqiao.trade.hosting.arbitrage.job.XQOrderJobScheduler;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQContractLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQEffectDateType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.trade.time.TradeTimeSpan;

public class XQOrderContractLimitStartingActor implements IXQOrderActor {

    private XQOrderContractLimitExecutor mExecutor;

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        this.mExecutor = XQOrderContractLimitExecutor.class.cast(executor);

        XQOrderJobScheduler.Global().removeStartOrderJob(mExecutor.getContext().getOrderId());
        if (!mExecutor.getEffectDateController().handleActorStarting(mExecutor)) {
            return ;
        }

        mExecutor.startFinished();
    }
}
