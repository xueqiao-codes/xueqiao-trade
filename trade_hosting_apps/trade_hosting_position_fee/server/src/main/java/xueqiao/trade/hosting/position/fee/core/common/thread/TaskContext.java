package xueqiao.trade.hosting.position.fee.core.common.thread;

import xueqiao.trade.hosting.framework.thread.TaskThread;

public class TaskContext {
    private TaskThread mTaskThread;
    private String mKey;

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
}
