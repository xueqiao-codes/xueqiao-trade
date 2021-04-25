package xueqiao.trade.hosting.dealing.core.sync;

public interface ISyncTask extends Runnable {
	public String type();        // 任务的类型描述
	public String key();         // 任务的key，主要用于同时提交相同任务时，排重
}
