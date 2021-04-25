package xueqiao.trade.hosting.arbitrage.core;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.thread.TaskThreadPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XQOrderManager {
    private static XQOrderManager sInstance;
    public static XQOrderManager Global() {
        if (sInstance == null) {
            synchronized(XQOrderManager.class) {
                if (sInstance == null) {
                    sInstance = new XQOrderManager();
                }
            }
        }
        return sInstance;
    }
    
    private TaskThreadPool mWorkThreadPool = new TaskThreadPool("XQOrderExec");
    private HashMap<String, XQOrderBaseExecutor> mOrderExecutors = new HashMap<String, XQOrderBaseExecutor>();
    
    private XQOrderManager() {
    }
    
    public synchronized void addExecutor(XQOrderBaseExecutor executor) {
        if (mOrderExecutors.containsKey(executor.getContext().getOrderId())) {
            return ;
        }
        initNewExecutor(executor);
    }
    
    private void initNewExecutor(XQOrderBaseExecutor newExecutor) {
        newExecutor.getContext().setWorkThread(mWorkThreadPool.allocTaskThread());
        mOrderExecutors.put(newExecutor.getContext().getOrderId(), newExecutor);
        newExecutor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(newExecutor) {
            @Override
            public void onRun() throws ErrorInfo {
                getExecutor().initialize();
            }
        });
    }
    
    public synchronized XQOrderBaseExecutor getExecutor(String orderId) {
        return mOrderExecutors.get(orderId);
    }
    
    public synchronized void rmExecutor(String orderId) {
        XQOrderBaseExecutor executor = mOrderExecutors.remove(orderId);
        if (executor == null) {
            return ;
        }
        executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
            @Override
            protected void onRun() throws Exception {
                getExecutor().destroy();
            }
        });
    }
    
    public synchronized void reset(XQOrderBaseExecutor executor) throws ErrorInfo {
        executor.destroy();
        initNewExecutor(XQOrderExecutorFactory.createExecutor(executor.getOrder()));
    }
    
    public synchronized List<XQOrderBaseExecutor> getAllExecutors() {
        return new ArrayList<XQOrderBaseExecutor>(mOrderExecutors.values());
    }

    public synchronized void destroyAll() {
        for (XQOrderBaseExecutor executor : mOrderExecutors.values()) {
            executor.getContext().getWorkThread().postTask(new OrderExecutorRunnable(executor) {
                @Override
                protected void onRun() throws Exception {
                    getExecutor().destroy();
                }
            });
        }
        mOrderExecutors.clear();
    }
    
}
