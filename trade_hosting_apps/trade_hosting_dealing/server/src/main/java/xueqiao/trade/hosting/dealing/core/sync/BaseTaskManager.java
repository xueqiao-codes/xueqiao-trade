package xueqiao.trade.hosting.dealing.core.sync;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

import com.google.common.base.Preconditions;

public abstract class BaseTaskManager {
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private class TaskEntry implements Runnable {
		public ScheduledFuture<?> future;
		public long delayMs;
		public long periodMs;
		
		public ISyncTask syncTask;
		
		private int freequencySleepMs = 0;
		
		public TaskEntry(int freequencySleepMs) {
			this.freequencySleepMs = freequencySleepMs;
		}

		@Override
		public void run() {
			try {
				syncTask.run();
				if (periodMs <= 0) {
					removeTaskEntry(this);
				} 
				// 降频策略
				if (freequencySleepMs > 0) {
					try {
						Thread.sleep(freequencySleepMs);
					} catch (InterruptedException e) {
					}
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
		
	}
	
	private Map<String, Map<String, TaskEntry>> taskMapping = new HashMap<String, Map<String, TaskEntry>>();
	
	protected BaseTaskManager() {
	}
	
	protected abstract int freequencySleepMs();
	
	/**
	 *  一定延时后执行的任务
	 */
	public void addTask(ISyncTask task, long delayMs) {
		addTask(task, delayMs, 0);
	}
	
	public synchronized boolean addTask(ISyncTask task, long delayMs, long periodMs) {
		Preconditions.checkNotNull(task);
		Preconditions.checkArgument(StringUtils.isNotEmpty(task.type()));
		Preconditions.checkArgument(StringUtils.isNotEmpty(task.key()));
		Preconditions.checkArgument(delayMs >= 0);
		Preconditions.checkArgument(periodMs >= 0);
		
		Map<String, TaskEntry> keyTaskMap = taskMapping.get(task.type());
		if (keyTaskMap == null) {
			keyTaskMap = new HashMap<String, TaskEntry>();
			taskMapping.put(task.type(), keyTaskMap);
		}
		TaskEntry taskEntry = keyTaskMap.get(task.key());
		if (taskEntry != null) {
			// 相同任务已经存在
			return false;
		}
		
		taskEntry = new TaskEntry(freequencySleepMs());
		taskEntry.delayMs = delayMs;
		taskEntry.periodMs = periodMs;
		taskEntry.syncTask = task;
		
		if (AppLog.infoEnabled()) {
			AppLog.i(getClass().getSimpleName() + " addTask class=" + task.getClass().getSimpleName()
					+ ", type=" + task.type() + ", key=" + task.key() 
					+ ", delayMs=" + delayMs + ", periodMs=" + periodMs);
		}
		if (periodMs <= 0) {
			taskEntry.future = scheduler.schedule(taskEntry, taskEntry.delayMs, TimeUnit.MILLISECONDS);
		} else {
			taskEntry.future = scheduler.scheduleWithFixedDelay(taskEntry, taskEntry.delayMs
					, taskEntry.periodMs, TimeUnit.MILLISECONDS);
		}
		keyTaskMap.put(task.key(), taskEntry);
		return true;
	}
	
	public synchronized void cancelTask(ISyncTask task) {
		Preconditions.checkNotNull(task);
		Preconditions.checkArgument(StringUtils.isNotEmpty(task.type()));
		Preconditions.checkArgument(StringUtils.isNotEmpty(task.key()));
		
		Map<String, TaskEntry> keyTaskMap = taskMapping.get(task.type());
		if (keyTaskMap == null) {
			return ;
		}
		TaskEntry entry = keyTaskMap.get(task.key());
		if (entry == null) {
			return ;
		}
		entry.future.cancel(false);
		entry.delayMs = -1;
		entry.periodMs = -1;
		keyTaskMap.remove(task.key());
		
		if (AppLog.infoEnabled()) {
			AppLog.i(getClass().getSimpleName() + " removeTask class=" 
					+ task.getClass().getSimpleName()
					+ ", type=" + task.type() + ", key=" + task.key());
		}
	}
	
	private synchronized void removeTaskEntry(TaskEntry entry) {
		Map<String, TaskEntry> keyTaskMap = taskMapping.get(entry.syncTask.type());
		if (keyTaskMap == null) {
			AppLog.w(getClass().getSimpleName() + "Failed to find keyTaskMap for syncTask type=" + entry.syncTask.type());
			return;
		}
		TaskEntry mappingEntry = keyTaskMap.get(entry.syncTask.key());
		if (entry == mappingEntry) {
			keyTaskMap.remove(entry.syncTask.key());
		}
	}
}
