package xueqiao.trade.hosting.position.statis.daemon;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;

public abstract class AbstractDaemon extends Thread {

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

	@Override
	public void run() {
		while (true) {
			if (isTaskTime()) {
				try {
					doTask();
				} catch (ErrorInfo errorInfo) {
					errorInfo.printStackTrace();
					AppReport.reportErr(this.getClass().getSimpleName() + " ---- errorInfo : " + errorInfo.toString());
				} catch (Throwable throwable) {
					throwable.printStackTrace();
					AppReport.reportErr(this.getClass().getSimpleName() + " ---- throwable : " + throwable.toString());
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
