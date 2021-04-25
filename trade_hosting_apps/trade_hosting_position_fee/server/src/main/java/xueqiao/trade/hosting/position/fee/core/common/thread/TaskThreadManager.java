package xueqiao.trade.hosting.position.fee.core.common.thread;

import xueqiao.trade.hosting.framework.thread.TaskThreadPool;

import java.util.HashMap;
import java.util.Map;

public class TaskThreadManager {

    public static final long GENERAL_THREAD_KEY = 0;

    private static TaskThreadManager ourInstance;

    private Map<Long, TaskContext> taskContextMap = new HashMap<>();

    private static byte[] lock = new byte[0];

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

    private TaskThreadPool mTaskThreadPool = new TaskThreadPool("position_fee", 4);

    public TaskContext getTaskThread(long key) {
        TaskContext taskContext = taskContextMap.get(key);
        if (taskContext == null) {
            synchronized (lock) {
                taskContext = taskContextMap.get(key);
                if (taskContext == null) {
                    taskContext = new TaskContext(String.valueOf(key), mTaskThreadPool.allocTaskThread());
                    taskContextMap.put(key, taskContext);
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
