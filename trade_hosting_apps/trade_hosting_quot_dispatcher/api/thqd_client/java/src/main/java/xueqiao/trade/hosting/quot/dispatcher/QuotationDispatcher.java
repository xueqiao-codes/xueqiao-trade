package xueqiao.trade.hosting.quot.dispatcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.quot.dispatcher.client.IQuotationCallback;
import xueqiao.trade.hosting.quot.dispatcher.client.THQDClient;

public class QuotationDispatcher implements IQuotationCallback {
    private static QuotationDispatcher sInstance;
    
    public static void init(String clientKey) {
        if (sInstance == null) {
            synchronized(QuotationDispatcher.class) {
                if (sInstance == null) {
                    sInstance = new QuotationDispatcher(clientKey);
                }
            }
        }
    }
    
    public static QuotationDispatcher Global() {
        return sInstance;
    }
    
    private Lock mReadLock;
    private Lock mWriteLock;
    private ReentrantReadWriteLock mRWLock;
    
    private Map<String, Map<String, Set<IQuotationListener>>> mListeners 
        = new HashMap<String, Map<String, Set<IQuotationListener>>>();
    
    private QuotationDispatcher(String clientKey) {
        mRWLock = new ReentrantReadWriteLock();
        mReadLock = mRWLock.readLock();
        mWriteLock = mRWLock.writeLock();
        
        THQDClient.init(clientKey);
        THQDClient.Global().setQuotationCallback(this);
    }
    
    public void registerListener(String platform
            , String contractSymbol
            , IQuotationListener listener) {
        if (StringUtils.isEmpty(platform)
                || StringUtils.isEmpty(contractSymbol)
                || listener == null) {
            return ;
        }
        
        try {
            mWriteLock.lock();
            Map<String, Set<IQuotationListener>> contractSymbolListeners = mListeners.get(platform);
            if (contractSymbolListeners == null) {
                contractSymbolListeners = new HashMap<String, Set<IQuotationListener>>();
                mListeners.put(platform, contractSymbolListeners);
            }
        
            Set<IQuotationListener> quotationListeners = contractSymbolListeners.get(contractSymbol);
            if (quotationListeners == null) {
                quotationListeners = new HashSet<IQuotationListener>();
                contractSymbolListeners.put(contractSymbol, quotationListeners);
            }
            if (quotationListeners.isEmpty()) {
                THQDClient.Global().addTopic(platform, contractSymbol);
            }
            quotationListeners.add(listener);
        } finally {
            mWriteLock.unlock();
        }
    }
    
    public void unRegisterListener(String platform
            , String contractSymbol
            , IQuotationListener listener) {
        if (StringUtils.isEmpty(platform)
                || StringUtils.isEmpty(contractSymbol)
                || listener == null) {
            return ;
        }
        
        try {
            mWriteLock.lock();
            
            Map<String, Set<IQuotationListener>> contractSymbolListeners = mListeners.get(platform);
            if (contractSymbolListeners == null) {
                return ;
            }
            Set<IQuotationListener> quotationListeners = contractSymbolListeners.get(contractSymbol);
            if (quotationListeners == null) {
                return ;
            }
            quotationListeners.remove(listener);
            if (quotationListeners.isEmpty()) {
                THQDClient.Global().removeTopic(platform, contractSymbol);
            }
        } finally {
            mWriteLock.unlock();
        }
    }
    
    @Override
    public void onReceivedQuotationItem(String platform, String contractSymbol, QuotationItem item) {
        Set<IQuotationListener> listenerSet = null;
        try {
            mReadLock.lock();
            Map<String, Set<IQuotationListener>> contractSymbolListeners = mListeners.get(platform);
            if (contractSymbolListeners != null) {
                Set<IQuotationListener> quotationListeners = contractSymbolListeners.get(contractSymbol);
                if (quotationListeners != null && !quotationListeners.isEmpty()) {
                    listenerSet = new HashSet<IQuotationListener>(quotationListeners);
                }
            }
        } finally {
            mReadLock.unlock();
        }

        if (listenerSet == null) {
            return ;
        }
        
        for (IQuotationListener listener : listenerSet) {
            try {
                listener.onReceivedQuotationItem(item);
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }
}
