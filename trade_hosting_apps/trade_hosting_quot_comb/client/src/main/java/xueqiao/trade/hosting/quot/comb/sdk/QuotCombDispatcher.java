package xueqiao.trade.hosting.quot.comb.sdk;


import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class QuotCombDispatcher implements QuotCombClient.IQuotCombClientCallback {

    private static QuotCombDispatcher sInstance;
    public static void init(String consumerKey) {
        if (sInstance == null) {
            synchronized (QuotCombDispatcher.class) {
                if (sInstance == null) {
                   sInstance = new QuotCombDispatcher(consumerKey);
                }
            }
        }
    }

    public static QuotCombDispatcher Global() {
        return sInstance;
    }

    private Lock mReadLock;
    private Lock mWriteLock;
    private ReentrantReadWriteLock mRWLock;

    private Map<Long, Set<IQuotCombListener>> mListenerMap = new HashMap<>();

    private QuotCombClient mCombClient;

    private QuotCombDispatcher(String consumerKey) {
        mRWLock = new ReentrantReadWriteLock();
        mReadLock = mRWLock.readLock();
        mWriteLock = mRWLock.writeLock();

        mCombClient = new QuotCombClient(consumerKey, this);
        mCombClient.startWork();
    }

    public void registerListener(long composeGraphId, IQuotCombListener listener) {
        if (composeGraphId <= 0 || listener == null) {
            return ;
        }

        mWriteLock.lock();
        try {
            Set<IQuotCombListener> quotationListeners = mListenerMap.get(composeGraphId);
            if (quotationListeners == null) {
                quotationListeners = new HashSet<>();
                mListenerMap.put(composeGraphId, quotationListeners);
            }
            if (quotationListeners.isEmpty()) {
                mCombClient.addComposeGraphId(composeGraphId);
            }
            quotationListeners.add(listener);
        } finally {
            mWriteLock.unlock();
        }
    }

    public void unRegisterListener(long composeGraphId, IQuotCombListener listener) {
        if (composeGraphId <= 0 || listener == null) {
            return ;
        }

        mWriteLock.lock();
        try {
            Set<IQuotCombListener> quotationListeners = mListenerMap.get(composeGraphId);
            if (quotationListeners == null) {
                return;
            }
            quotationListeners.remove(listener);
            if (quotationListeners.isEmpty()) {
                mCombClient.removeComposeGraphId(composeGraphId);
            }
        } finally {
            mWriteLock.unlock();
        }
    }

    @Override
    public void onReceiveQuotationComb(HostingQuotationComb quotationComb) {
        Set<IQuotCombListener> cpListenerSet = null;
        try {
            mReadLock.lock();
            Set<IQuotCombListener> listenerSet = mListenerMap.get(quotationComb.getComposeGraphId());
            if (listenerSet != null) {
               cpListenerSet = new HashSet<>(listenerSet);
            }
        } finally {
            mReadLock.unlock();
        }

        if (cpListenerSet == null) {
            return ;
        }

        for (IQuotCombListener listener : cpListenerSet) {
            try {
                listener.onReceivedQuotationComb(quotationComb);
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }
}

