package xueqiao.trade.hosting.position.statis.core.thread;

import xueqiao.trade.hosting.framework.thread.TaskThread;

public class TaskContext {
    private TaskThread mTaskThread;
    private String mKey;
    private long mAccountId;

    public TaskContext(long accountId, TaskThread taskThread) {
        this.mKey = Long.toString(accountId);
        this.mAccountId = accountId;
        this.mTaskThread = taskThread;
    }

    public TaskContext(String key, TaskThread taskThread) {
        this.mKey = key;
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
