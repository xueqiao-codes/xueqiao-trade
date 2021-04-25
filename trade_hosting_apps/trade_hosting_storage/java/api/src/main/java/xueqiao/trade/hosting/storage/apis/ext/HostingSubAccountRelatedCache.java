package xueqiao.trade.hosting.storage.apis.ext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer.ConsumeResult;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingSubAccountRelatedItem;
import xueqiao.trade.hosting.events.SubAccountRelatedInfoChangedEvent;
import xueqiao.trade.hosting.events.SubAccountRelatedInfoChangedEventType;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;

/**
 * 子账户关联信息缓存
 * @author wangli
 *
 */
public class HostingSubAccountRelatedCache {
    
    private IHostingSubAccountApi mSubAccountApi;
    private Map<Long, Set<Integer>> mSubAccountRelatedSubUserIds = new HashMap<Long, Set<Integer>>();
    private ReentrantReadWriteLock mRWLock = new ReentrantReadWriteLock();
    private Lock mReadLock;
    private Lock mWriteLock;
    
    public HostingSubAccountRelatedCache(IHostingSubAccountApi subAccountApi) {
        mSubAccountApi = subAccountApi;
        mReadLock = mRWLock.readLock();
        mWriteLock = mRWLock.writeLock();
    }
    
    public void sync() throws ErrorInfo {
        try {
            mWriteLock.lock();
            syncImpl();
        } finally {
            mWriteLock.unlock();
        }
    }
    
    private void syncImpl() throws ErrorInfo {
        mSubAccountRelatedSubUserIds.clear();
        addRelatedItems(mSubAccountApi.getAllSubAccountRelatedItems());
    }
    
    private void addRelatedItems(List<HostingSubAccountRelatedItem> relatedItems) {
        if (relatedItems == null || relatedItems.isEmpty()) {
            return ;
        }
        
        for (HostingSubAccountRelatedItem relatedItem : relatedItems) {
            Set<Integer> subUserIds = mSubAccountRelatedSubUserIds.get(relatedItem.getSubAccountId());
            if (subUserIds == null) {
                subUserIds = new HashSet<Integer>();
                mSubAccountRelatedSubUserIds.put(relatedItem.getSubAccountId(), subUserIds);
            }
            subUserIds.add(relatedItem.getSubUserId());
        }
    }
    
    public void subAccountRelatedInfoChanged(long subAccountId) throws ErrorInfo {
        try {
            mWriteLock.lock();
            subAccountRelatedInfoChangedImpl(subAccountId);
        } finally {
            mWriteLock.unlock();
        }
    }
    
    private void subAccountRelatedInfoChangedImpl(long subAccountId) throws ErrorInfo {
        mSubAccountRelatedSubUserIds.remove(subAccountId);
        
        Map<Long, List<HostingSubAccountRelatedItem>> 
                relatedItemsMapping = mSubAccountApi.getSubAccountRelatedItemsBySubAccountIds(
                        new HashSet<Long>(Arrays.asList(subAccountId)));
        if (relatedItemsMapping == null || relatedItemsMapping.isEmpty()) {
            return ;
        }
        
        addRelatedItems(relatedItemsMapping.get(subAccountId));
    }
    
    public boolean hasRelated(long subAccountId, int subUserId) {
        try {
            mReadLock.lock();
            
            Set<Integer> relatedSubUserIds = mSubAccountRelatedSubUserIds.get(subAccountId);
            if (relatedSubUserIds == null) {
                return false;
            }
            return relatedSubUserIds.contains(subUserId);
        } finally {
            mReadLock.unlock();
        }
    }
    
    public Set<Integer> getRelatedSubUserIds(long subAccountId) {
        try {
            mReadLock.lock();
            
            Set<Integer> relatedSubUserIds = mSubAccountRelatedSubUserIds.get(subAccountId);
            if (relatedSubUserIds == null) {
                return null;
            }
            return new HashSet<Integer>(relatedSubUserIds);
        } finally {
            mReadLock.unlock();
        }
    }
    
    public ConsumeResult handleSubAccountRelatedInfoChangedEvent(SubAccountRelatedInfoChangedEvent event) throws ErrorInfo {
        if (!event.isSetType()) {
            AppLog.w("received SubAccountRelatedInfoChangedEvent, but no type");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        if (event.getType() == SubAccountRelatedInfoChangedEventType.RELATED_INFO_ALL_CLEARED) {
            sync();
        } else {
            if (event.getSubAccountId() <= 0) {
                AppLog.w("received SubAccountRelatedInfoChangedEvent(" + event.getType() +"), but subAccountId<=0");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }
            subAccountRelatedInfoChanged(event.getSubAccountId());
        }
        return ConsumeResult.CONSUME_OK;
    }
    
}
