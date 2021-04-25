package xueqiao.trade.hosting.arbitrage.core;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;

public abstract class OrderExecutorRunnable implements Runnable {
    
    private XQOrderBaseExecutor mExecutor;
    
    public OrderExecutorRunnable(XQOrderBaseExecutor executor) {
        this.mExecutor = executor;
    }


    @Override
    public void run()  {
        try {
            if (getExecutor().isDetroyed()) {
                AppLog.i("executor destoryed, drop the old task for xqOrderId="
                        + getExecutor().getOrder().getOrderId());
                return ;
            }
            onRun();
        } catch (ErrorInfo e) {
            AppLog.e(e.getMessage(), e);
            if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()) {
                resetExecutor();
            } else {
                getExecutor().innerErrorAction();
            }
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
            resetExecutor();
        } catch (Throwable e) {
            // 内部出现未知异常，通常重置也未必能解决，比较麻烦，
            AppLog.e(e.getMessage(), e);
            getExecutor().innerErrorAction();
        }
    }
    
    private void resetExecutor() {
        // 这里极大可能是可恢复异常
        try {
            AppLog.i("reset executor for xqOrderId=" + getExecutor().getOrder().getOrderId());
            Thread.sleep(500);
            XQOrderManager.Global().reset(getExecutor());
        } catch (Throwable e) {
            AppLog.f(e.getMessage(), e);
        }
    }
    
    public XQOrderBaseExecutor getExecutor() {
        return mExecutor;
    }
    
//    public OrderExecutorRunnable setExecutor(XQOrderBaseExecutor executor) {
//        this.mExecutor = executor;
//        return this;
//    }
    
    protected abstract void onRun() throws Exception;

}
