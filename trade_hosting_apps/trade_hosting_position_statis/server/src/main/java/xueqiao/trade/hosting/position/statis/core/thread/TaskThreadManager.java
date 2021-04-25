package xueqiao.trade.hosting.position.statis.core.thread;

import xueqiao.trade.hosting.framework.thread.TaskThreadPool;

import java.util.HashMap;
import java.util.Map;

public class TaskThreadManager {
    private static TaskThreadManager ourInstance;

    private Map<Long, TaskContext> taskContextMap = new HashMap<>();

    private static byte[] lock = new byte[0];

    public static final long THREAD_KEY_POSITION_DYNAMIC_INFO_SENDER = 0;

    public static TaskThreadManager getInstance() {
        if (ourInstance == null) {
            synchronized (lock) {
                if (ourInstance == null) {
                    ourInstance = new TaskThreadManager();
                }
            }
        }
        return ourInstance;
    }

    private TaskThreadManager() {
    }

    private TaskThreadPool mTaskThreadPool = new TaskThreadPool("position_statis", 4);

    public TaskContext getTaskThread(long accountId) {
        TaskContext taskContext = taskContextMap.get(accountId);
        if (taskContext == null) {
            synchronized (lock) {
                taskContext = taskContextMap.get(accountId);
                if (taskContext == null) {
                    taskContext = new TaskContext(accountId, mTaskThreadPool.allocTaskThread());
                    taskContextMap.put(accountId, taskContext);
                }
            }
        }
        return taskContext;
    }

    /**
     * 重置线程池
     */
    public void reset() {
        synchronized (lock) {
            taskContextMap.clear();
        }
    }
}
