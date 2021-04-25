package xueqiao.trade.hosting.position.statis.service.thread;

import xueqiao.trade.hosting.framework.thread.TaskThreadPool;
import xueqiao.trade.hosting.position.statis.core.thread.TaskContext;
//import xueqiao.trade.hosting.position.statis.core.thread.TaskThreadPool;

import java.util.HashMap;
import java.util.Map;

public class StatWorkerThreadManager {

    public static String THREAD_KEY_SINGLE_WORKER = "single_worker";

    private static StatWorkerThreadManager ourInstance;

    private Map<String, TaskContext> taskContextMap = new HashMap<>();

    private static byte[] lock = new byte[0];

    public static StatWorkerThreadManager getInstance() {
        if (ourInstance == null) {
            synchronized (lock) {
                if (ourInstance == null) {
                    ourInstance = new StatWorkerThreadManager();
                }
            }
        }
        return ourInstance;
    }

    private StatWorkerThreadManager() {
    }

    private TaskThreadPool mTaskThreadPool = new TaskThreadPool("position_statis_single_operator", 1);

    public TaskContext getTaskThread(String key) {
        TaskContext taskContext = taskContextMap.get(key);
        if (taskContext == null) {
            synchronized (lock) {
                taskContext = taskContextMap.get(key);
                if (taskContext == null) {
                    taskContext = new TaskContext(key, mTaskThreadPool.allocTaskThread());
                    taskContextMap.put(key, taskContext);
                }
            }
        }
        return taskContext;
    }
}
