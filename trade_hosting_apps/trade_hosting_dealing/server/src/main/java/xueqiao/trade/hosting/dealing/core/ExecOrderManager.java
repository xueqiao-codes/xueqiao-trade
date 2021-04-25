package xueqiao.trade.hosting.dealing.core;

import com.google.common.base.Preconditions;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.framework.thread.TaskThread;

import java.util.HashMap;
import java.util.Map;

public class ExecOrderManager {
    private static ExecOrderManager sInstance;
    public static ExecOrderManager Global() {
        if (sInstance == null) {
            synchronized (ExecOrderManager.class) {
                if (sInstance == null) {
                    sInstance = new ExecOrderManager();
                }
            }
        }
        return sInstance;
    }

    private TaskThread mTaskThreadNT;
    private Map<Long, TaskThread> mTaskThreads = new HashMap<>();
    private HashMap<Long, ExecOrderExecutor> mOrderExecutors = new HashMap<>();

    private ExecOrderManager() {
        mTaskThreadNT = new TaskThread();
        mTaskThreadNT.setName("ExecOrder-NT");
    }

    public synchronized void addExecutor(ExecOrderExecutor executor) {
        if (mOrderExecutors.containsKey(executor.getExecOrderId())) {
            return ;
        }
        initNewExecutor(executor);
    }

    private void initNewExecutor(ExecOrderExecutor newExecutor) {
        TaskThread allocTaskThread;

        HostingExecOrder execOrder = newExecutor.getExecOrder();
        if (execOrder.getAccountSummary() == null
                || execOrder.getAccountSummary().getTradeAccountId() <= 0) {
            allocTaskThread = mTaskThreadNT;
        } else {
            allocTaskThread = mTaskThreads.get(execOrder.getAccountSummary().getTradeAccountId());
            if (allocTaskThread == null) {
                allocTaskThread = new TaskThread();
                allocTaskThread.setName("ExecOrder-" + String.valueOf(execOrder.getAccountSummary().getTradeAccountId()));
                mTaskThreads.put(execOrder.getAccountSummary().getTradeAccountId(), allocTaskThread);
            }
        }
        Preconditions.checkNotNull(allocTaskThread);

        mOrderExecutors.put(newExecutor.getExecOrderId(), newExecutor);
        newExecutor.setWorkThread(allocTaskThread);
        newExecutor.getWorkThread().postTask(new ExecOrderExecutorRunnable(newExecutor) {
            @Override
            public String getName() {
                return "initialize";
            }

            @Override
            public void onRun() throws ErrorInfo {
                getExecutor().initialize();
            }
        });
    }

    public synchronized ExecOrderExecutor getExecutor(long execOrderId) {
        return mOrderExecutors.get(execOrderId);
    }

    public synchronized void rmExecutor(long orderId) {
        ExecOrderExecutor executor = mOrderExecutors.remove(orderId);
        if (executor == null) {
            return ;
        }
        executor.getWorkThread().postTask(new ExecOrderExecutorRunnable(executor) {
            @Override
            public String getName() {
                return "destroy";
            }

            @Override
            protected void onRun() throws Exception {
                getExecutor().destroy();
            }
        });
    }

//    public synchronized List<ExecOrderExecutor> getAllExecutors() {
//        return new ArrayList<ExecOrderExecutor>(mOrderExecutors.values());
//    }

    public synchronized void destroyAll() {
        for (ExecOrderExecutor executor : mOrderExecutors.values()) {
            executor.getWorkThread().postTask(new ExecOrderExecutorRunnable(executor) {
                @Override
                public String getName() {
                    return "destroy";
                }

                @Override
                protected void onRun() throws Exception {
                    getExecutor().destroy();
                }
            });
        }
        mOrderExecutors.clear();
    }

}
