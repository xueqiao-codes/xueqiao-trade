package xueqiao.trade.hosting.dealing.core.cleaner;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.dealing.core.ExecOrderExecutor;
import xueqiao.trade.hosting.dealing.core.ExecOrderManager;
import xueqiao.trade.hosting.framework.thread.SyncResult;
import xueqiao.trade.hosting.framework.thread.TaskThread;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class ExecOrderInDealingCleaner extends TaskThread {
    private static ExecOrderInDealingCleaner sInstance;
    public static ExecOrderInDealingCleaner Global() {
        if (sInstance == null) {
            synchronized (ExecOrderInDealingCleaner.class) {
                if (sInstance == null) {
                    sInstance = new ExecOrderInDealingCleaner();
                }
            }
        }
        return sInstance;
    }

    private TreeMap<Long, Set<Long>> cleanIndexes = new TreeMap<>();

    public ExecOrderInDealingCleaner() {
        scheduleNext();
    }

    private void scheduleNext() {
        postTaskDelay(new Runnable() {
            @Override
            public void run() {
                runOnce();
            }
        }, 5, TimeUnit.MINUTES);
    }

    public void addCleanIndex(long ttlTimetampMs, long execOrderId)  {
        if (AppLog.infoEnabled()){
            AppLog.i("addCleanIndex execOrderId=" + execOrderId
                    + ", ttlTimestampMs=" + ttlTimetampMs);
        }

        postTask(new Runnable() {
            @Override
            public void run() {
                Set<Long> cleanExecOrderIds = cleanIndexes.get(ttlTimetampMs);
                if (cleanExecOrderIds == null) {
                    cleanExecOrderIds = new HashSet<>();
                    cleanIndexes.put(ttlTimetampMs, cleanExecOrderIds);
                }
                cleanExecOrderIds.add(execOrderId);
            }
        });
    }

    private void doCleanExecOrder(long execOrderId) throws ErrorInfo {
        ExecOrderExecutor executor = ExecOrderManager.Global().getExecutor(execOrderId);
        if (executor == null) {
            return ;
        }

        new SyncResult<Void>(executor.getWorkThread()) {
            @Override
            protected Void onCall() throws Exception {
                return executor.clean();
            }
        }.get();

        ExecOrderManager.Global().rmExecutor(execOrderId);
    }

    private void runOnce() {
        try {
            long startTimestampMs = System.currentTimeMillis();

            while(!cleanIndexes.isEmpty()) {
                Iterator<Map.Entry<Long, Set<Long>>> headIterator = cleanIndexes.entrySet().iterator();
                Map.Entry<Long, Set<Long>> headEntry = headIterator.next();
                if (headEntry.getKey() >= startTimestampMs) {
                    break;
                }

                if (headEntry.getKey() > 0) {
                    for (Long execOrderId : headEntry.getValue()) {
                        doCleanExecOrder(execOrderId);
                    }
                }

                headIterator.remove();
            }

        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        } finally {
            scheduleNext();
        }

    }

}
