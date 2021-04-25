package xueqiao.trade.hosting.asset.calculator;


import xueqiao.trade.hosting.framework.thread.TaskThread;

public class AssetContext {

    private TaskThread mTaskThread;
    private String mKey;
    private long mAccountId;

    public AssetContext(long accountId, TaskThread taskThread) {
        this.mKey = Long.toString(accountId);
        this.mAccountId = accountId;
        this.mTaskThread = taskThread;
    }

    public TaskThread getmTaskThread() {
        return mTaskThread;
    }

    public String getKey() {
        return mKey;
    }

    public long getAccountId() {
        return mAccountId;
    }
}
