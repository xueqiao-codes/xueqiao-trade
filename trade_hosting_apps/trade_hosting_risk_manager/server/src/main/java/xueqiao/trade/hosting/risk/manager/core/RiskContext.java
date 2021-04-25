package xueqiao.trade.hosting.risk.manager.core;

import xueqiao.trade.hosting.framework.thread.TaskThread;

public class RiskContext {
    private long mSubAccountId;
    private TaskThread mWorkThread;

    public RiskContext(long subAccountId, TaskThread workThread) {
        this.mSubAccountId = subAccountId;
        this.mWorkThread = workThread;
    }

    public long getSubAccountId() {
        return mSubAccountId;
    }

    public TaskThread getWorkThread() {
        return mWorkThread;
    }
}
