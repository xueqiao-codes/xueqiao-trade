package xueqiao.trade.hosting.dealing.core;

import org.soldier.base.logger.AppLog;

public abstract class ExecOrderExecutorRunnable implements Runnable {

    private ExecOrderExecutor mExecutor;

    public ExecOrderExecutorRunnable(ExecOrderExecutor executor) {
        this.mExecutor = executor;
    }

    public abstract String getName();

    public ExecOrderExecutor getExecutor() {
        return this.mExecutor;
    }

    protected abstract void onRun() throws Exception;

    public void run() {
        while(true) {
            try {
                AppLog.i("ExecOrderExecutorRunnable run, execOrderId=" + mExecutor.getExecOrderId()
                        + ", name=" + getName());
                onRun();
                break;
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

}
