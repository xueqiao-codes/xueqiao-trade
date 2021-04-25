package xueqiao.trade.hosting.dealing.core.sync;

public interface ISyncPolicy {
	public long getDelayMs();
	public long getPeriodMs();
	
	public ISyncTask getSyncTask(long execOrderId);
	
	public BaseTaskManager getTaskManager();
}
