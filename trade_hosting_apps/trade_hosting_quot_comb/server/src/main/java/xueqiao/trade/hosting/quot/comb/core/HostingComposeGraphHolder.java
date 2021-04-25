package xueqiao.trade.hosting.quot.comb.core;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.container.LRULinkedHashMap;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;

import java.util.concurrent.locks.ReentrantLock;

public class HostingComposeGraphHolder {
    private static HostingComposeGraphHolder sInstance;
    public static HostingComposeGraphHolder Global() {
        if (sInstance == null) {
            synchronized (HostingComposeGraphHolder.class) {
                if (sInstance == null) {
                    sInstance = new HostingComposeGraphHolder();
                }
            }
        }
        return sInstance;
    }

    private IHostingComposeApi mComposeApi;
    private ReentrantLock mLock = new ReentrantLock();
    private LRULinkedHashMap<Long, HostingComposeGraph> mCache = new LRULinkedHashMap<>(500);

    private HostingComposeGraphHolder() {
        mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
    }

    public HostingComposeGraph getComposeGraph(long composeGraphId) throws ErrorInfo {
        mLock.lock();
        HostingComposeGraph composeGraph = mCache.get(composeGraphId);
        mLock.unlock();

        if (composeGraph != null) {
            return composeGraph;
        }

        composeGraph = mComposeApi.getComposeGraph(composeGraphId);
        if (composeGraph == null) {
            return null;
        }

        mLock.lock();
        mCache.put(composeGraph.getComposeGraphId(), composeGraph);
        mLock.unlock();

        return composeGraph;
    }
}
