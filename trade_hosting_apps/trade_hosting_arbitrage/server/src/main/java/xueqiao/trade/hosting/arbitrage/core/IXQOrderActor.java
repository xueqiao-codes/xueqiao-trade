package xueqiao.trade.hosting.arbitrage.core;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;

public interface IXQOrderActor {
    void onCreate(XQOrderBaseExecutor executor) throws ErrorInfo;
    default void onDestroy() throws ErrorInfo {
    }
    
    default void onLastestOrderChanged(XQOrderSubExecutor subExecutor
            , HostingExecOrder lastestOrder) throws ErrorInfo {
    }
    
    default void onTradeListChanged(XQOrderSubExecutor subExecutor) throws ErrorInfo {
    }
    
    default void onSuspendReasonChanged(HostingXQSuspendReason newReason) throws ErrorInfo {
    }
}
