package xueqiao.trade.hosting.position.statis.core.cache.quotation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import xueqiao.quotation.QuotationItem;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatQuotationCacheManager implements IQuoCacheManager<QuotationItem, StatQuotationCache> {
    private static StatQuotationCacheManager ourInstance = new StatQuotationCacheManager();

    public static StatQuotationCacheManager getInstance() {
        return ourInstance;
    }

    private StatQuotationCacheManager() {
    }

    /**
     * Map<sledContractId, StatQuotationCache>
     */
    private Map<Long, StatQuotationCache> statQuotationCacheMap = new ConcurrentHashMap<>();

    public StatQuotationCache getLatestQuotation(String targetKey) {
        long sledContractId = Long.valueOf(targetKey);
        return statQuotationCacheMap.get(sledContractId);
    }

    @Override
    public StatQuotationCache getLatestQuotation(long sledContractId) {
        return statQuotationCacheMap.get(sledContractId);
    }

    public void removeQuotation(long sledContractId) {
        statQuotationCacheMap.remove(sledContractId);
    }

    @Override
    public StatQuotationCache putLatestQuotation(long sledContractId, QuotationItem quotation) {
        StatQuotationCache quotationCache = StatQuotationCache.getStatQuotationCache(quotation);
        quotationCache.setContractId(sledContractId);
        this.statQuotationCacheMap.put(sledContractId, quotationCache);
        return quotationCache;
    }

    /**
     * 获取合约行情缓存列表
     */
    public Collection<StatQuotationCache> getContractQuotationCacheList() {
        return statQuotationCacheMap.values();
    }

    /**
     * 获取缓存map的序列化字符串
     */
    public String getCacheJson() {
        return new Gson().toJson(statQuotationCacheMap);
    }

    /**
     * 将数据恢复到内存
     */
    public void recoveryCacheFromJson(String cacheJson) {
        statQuotationCacheMap = new Gson().fromJson(cacheJson, new TypeToken<ConcurrentHashMap<Long, StatQuotationCache>>() {
        }.getType());
    }

    @Override
    public void clear() {
        statQuotationCacheMap.clear();
    }
}
