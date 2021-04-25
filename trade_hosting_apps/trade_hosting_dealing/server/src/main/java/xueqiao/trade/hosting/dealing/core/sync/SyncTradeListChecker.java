package xueqiao.trade.hosting.dealing.core.sync;


import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.framework.thread.TaskThread;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SyncTradeListChecker extends TaskThread {
	private static SyncTradeListChecker sInstance;
	public static SyncTradeListChecker Global() {
		if (sInstance == null) {
			synchronized (SyncTradeListChecker.class) {
				if (sInstance == null) {
					sInstance = new SyncTradeListChecker();
				}
			}
		}
		return sInstance;
	}

	private Set<Long> needSyncExecOrderIds = new HashSet<>();

	private SyncTradeListChecker() {
		setName("TradeListChekcer");
		checkOnce();
	}

	public void addCheck(long execOrderId) {
//		AppLog.i("SyncTradeListChecker addCheck, execOrderId=" + execOrderId);

		postTask(new Runnable() {
			@Override
			public void run() {
				needSyncExecOrderIds.add(execOrderId);
			}
		});
	}

	public void rmCheck(long execOrderId) {
//		AppLog.i("SyncTradeListChecker rmCheck, execOrderId=" + execOrderId);

		postTask(new Runnable() {
			@Override
			public void run() {
				needSyncExecOrderIds.remove(execOrderId);
			}
		});
	}

	public void checkOnce() {
		try {
			AppLog.i("[NOTICE]SyncTradeListCheckTask started...");
			long startTimestampMs = System.currentTimeMillis();
			for (Long execOrderId : needSyncExecOrderIds) {
				SyncOrderTaskManager.Global().addTask(new SyncOrderTradeListTask(execOrderId), 0);
			}
			AppLog.i("[NOTICE]SyncTradeListCheckTask finished, time escaped="
				        + (System.currentTimeMillis() - startTimestampMs) + "ms");
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		} finally {
			this.postTaskDelay(new Runnable() {
				@Override
				public void run() {
					checkOnce();
				}
			}, 30, TimeUnit.SECONDS);
		}
	}

}
