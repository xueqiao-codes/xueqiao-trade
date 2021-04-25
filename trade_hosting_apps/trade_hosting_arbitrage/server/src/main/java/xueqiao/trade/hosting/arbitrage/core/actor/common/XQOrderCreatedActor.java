package xueqiao.trade.hosting.arbitrage.core.actor.common;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.arbitrage.core.IXQOrderActor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;

public class XQOrderCreatedActor implements IXQOrderActor {

    @Override
    public void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo {
        executor.start();
    }

}
