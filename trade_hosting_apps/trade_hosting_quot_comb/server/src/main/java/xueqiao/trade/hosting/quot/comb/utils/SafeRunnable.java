package xueqiao.trade.hosting.quot.comb.utils;

import org.soldier.base.logger.AppLog;

public abstract class SafeRunnable implements Runnable {
    protected abstract void onRun() throws Exception;
    
    @Override
    public void run() {
        try {
            onRun();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }
    }
    
}
