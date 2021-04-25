package xueqiao.trade.hosting.tradeaccount.data.core;

import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.framework.thread.TaskThreadPool;
import xueqiao.trade.hosting.tradeaccount.data.job.TAJobScheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TADataSynchonizerManager {
    private static TADataSynchonizerManager sInstance;

    public static TADataSynchonizerManager Global() {
        if (sInstance == null) {
            synchronized (TADataSynchonizerManager.class) {
                if (sInstance == null) {
                    sInstance = new TADataSynchonizerManager();
                }
            }
        }
        return sInstance;
    }

    private TaskThreadPool mThreadPool = new TaskThreadPool("TADataSync", 4);
    private Map<Long, TADataSynchonizer> mSynchronizers = new HashMap<>();

    public synchronized void addSynchronizer(long tradeAccountId
            , BrokerTechPlatform techPlatform
            , long tradeAccountCreateTimestamp) {
        if (mSynchronizers.containsKey(tradeAccountId)) {
            return ;
        }

        AppLog.i("addSynchronizer for tradeAccountId=" + tradeAccountId);
        TADataSynchonizer synchonizer = new TADataSynchonizer(mThreadPool.allocTaskThread()
                , tradeAccountId, tradeAccountCreateTimestamp);
        mSynchronizers.put(tradeAccountId, synchonizer);
        synchonizer.getTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                synchonizer.onCreate();
            }
        });
        TAJobScheduler.Global().addTAJobs(tradeAccountId, techPlatform);
    }

    public synchronized void rmSynchronizer(long tradeAccountId) {
        TADataSynchonizer synchonizer = mSynchronizers.remove(tradeAccountId);
        if (synchonizer == null) {
            return ;
        }

        AppLog.i("rmSynchronizer for tradeAccountId=" + tradeAccountId);
        synchonizer.getTaskThread().postTask(new Runnable() {
            @Override
            public void run() {
                synchonizer.onDestroy();
            }
        });
        TAJobScheduler.Global().rmTAJobs(tradeAccountId);
    }

    public synchronized void clearAll() {
        AppLog.i("clearAll synchronizer");
        for (TADataSynchonizer synchonizer : mSynchronizers.values()) {
            synchonizer.getTaskThread().postTask(new Runnable() {
                @Override
                public void run() {
                    synchonizer.onDestroy();
                }
            });
            TAJobScheduler.Global().rmTAJobs(synchonizer.getTradeAccountId());
        }
        mSynchronizers.clear();
    }

    public synchronized Set<Long> getTradeAccountIds() {
        return mSynchronizers.keySet();
    }
}
