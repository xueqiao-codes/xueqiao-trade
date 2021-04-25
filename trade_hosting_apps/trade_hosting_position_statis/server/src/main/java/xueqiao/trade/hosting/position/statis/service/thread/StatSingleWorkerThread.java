package xueqiao.trade.hosting.position.statis.service.thread;

import xueqiao.trade.hosting.framework.thread.SyncResult;

public abstract class StatSingleWorkerThread<T> extends SyncResult<T> {
    public StatSingleWorkerThread() {
        super(StatWorkerThreadManager.getInstance().getTaskThread(StatWorkerThreadManager.THREAD_KEY_SINGLE_WORKER).getmTaskThread());
    }
}
