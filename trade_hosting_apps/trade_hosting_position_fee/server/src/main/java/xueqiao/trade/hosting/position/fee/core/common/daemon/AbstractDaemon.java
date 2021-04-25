package xueqiao.trade.hosting.position.fee.core.common.daemon;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public abstract class AbstractDaemon extends Thread {

    private boolean isDaemonRunning = false;
    private long startDelayedMillis = 0;

    /**
     * 判断是否已满足任务执行的时间条件
     */
    public abstract boolean isTaskTime();

    /**
     * 任务的主体
     */
    public abstract void doTask() throws ErrorInfo, Throwable;

    /**
     * 获取检查是否到任务执行时间的周期
     * 单位是：毫秒
     */
    public abstract long getSleepPeriod();

    /**
     * 判断daemon是否在运行中
     */
    public boolean isRunning() {
        return isDaemonRunning;
    }

    /**
     * 延时启动
     */
    public void startDelayed(long millis) {
        this.startDelayedMillis = millis;
        start();
    }

    @Override
    public synchronized void start() {
        super.start();
        isDaemonRunning = true;
    }

    @Override
    public void run() {
        AppLog.i(Thread.currentThread().getName() + "start..., startDelayedMillis : " + startDelayedMillis);
        /*
         * 推迟启动工作
         * */
        if (startDelayedMillis > 0) {
            sleep(startDelayedMillis);
        }
        while (true) {
            if (isTaskTime()) {
                try {
                    doTask();
                } catch (ErrorInfo errorInfo) {
                    errorInfo.printStackTrace();
                    AppLog.e(this.getClass().getSimpleName() + " ---- errorInfo : " + errorInfo.toString());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                    AppLog.e(this.getClass().getSimpleName() + " ---- throwable : " + throwable.toString());
                }
            }
            sleep(getSleepPeriod());
        }
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
