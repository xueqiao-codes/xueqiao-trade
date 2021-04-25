package xueqiao.trade.hosting.arbitrage.thread;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.TradeHostingBasicErrorCode;

public abstract class RepeatRunnable implements Runnable {

    @Override
    public void run() {
        while(true) {
            try {
                onRun();
                break;
            } catch (ErrorInfo e) {
                AppLog.e(e.getMessage(), e);
                if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {}
                }
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);

            }
        }
    }
    
    protected abstract void onRun() throws Exception;
}
