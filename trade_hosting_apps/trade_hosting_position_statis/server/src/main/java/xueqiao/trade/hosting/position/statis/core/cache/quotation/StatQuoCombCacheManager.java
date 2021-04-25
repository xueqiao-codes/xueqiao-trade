package xueqiao.trade.hosting.position.statis.core.cache.quotation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatQuoCombCacheManager implements IQuoCacheManager<HostingQuotationComb, StatQuoCombCache> {
    private static StatQuoCombCacheManager ourInstance = new StatQuoCombCacheManager();

    public static StatQuoCombCacheManager getInstance() {
        return ourInstance;
    }

    private StatQuoCombCacheManager() {
    }

    /**
     * Map<composeGraphId, StatQuoCombCache>
     */
    private Map<Long, StatQuoCombCache> statQuoCombCacheMap = new ConcurrentHashMap<>();

    public StatQuoCombCache getLatestQuotation(String targetKey) {
        long composeGraphId = Long.valueOf(targetKey);
        return statQuoCombCacheMap.get(composeGraphId);
    }

    @Override
    public StatQuoCombCache getLatestQuotation(long composeGraphId) {
        return statQuoCombCacheMap.get(composeGraphId);
    }

    @Override
    public StatQuoCombCache putLatestQuotation(long composeGraphId, HostingQuotationComb quotation) {
        StatQuoCombCache statQuoCombCache = StatQuoCombCache.getStatQuoCombCache(quotation);
        this.statQuoCombCacheMap.put(composeGraphId, statQuoCombCache);
        return statQuoCombCache;
    }

    public void removeQuotation(long composeGraphId) {
        statQuoCombCacheMap.remove(composeGraphId);
    }

    /**
     * 获取组合行情缓存列表
     */
    public Collection<StatQuoCombCache> getComposeQuotationCacheList() {
        return statQuoCombCacheMap.values();
    }

    /**
     * 获取缓存map的序列化字符串
     */
    public String getCacheJson() {
        return new Gson().toJson(statQuoCombCacheMap);
    }

    /**
     * 将数据恢复到内存
     */
    public void recoveryCacheFromJson(String cacheJson) {
        statQuoCombCacheMap = new Gson().fromJson(cacheJson, new TypeToken<ConcurrentHashMap<Long, StatQuoCombCache>>() {
        }.getType());
    }

    @Override
    public void clear() {
        statQuoCombCacheMap.clear();
    }
}
