package xueqiao.trade.hosting.position.statis.core.calculator.cleancache;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionDynamic;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CleanCacheCalculator extends AbstractCalculator<Void, Void> {
    private static final long THREAD_KEY = 0;

    public CleanCacheCalculator() {
        super(THREAD_KEY);
    }

    private Map<String, Object> keyMap = new HashMap<>();
    private Map<Long, Object> contractIdMap = new HashMap<>();
    private Map<Long, Object> composeGraphIdMap = new HashMap<>();

    @Override
    public Void onCalculate(Void param) throws ErrorInfo {
        AppLog.i("CleanCacheCalculator # onCalculate");
        preparePositionSummaryData();
        cleanPositionSummaryData();
        return null;
    }

    private void preparePositionSummaryData() throws ErrorInfo {
        keyMap.clear();
        contractIdMap.clear();
        composeGraphIdMap.clear();
        String key;
        long targetId;

        Object emptyObject = new Object();

        List<StatPositionSummary> positionSummaryList = StatPositionHandler.queryAllStatPositionSummary();
        if (positionSummaryList == null || positionSummaryList.size() < 1) {
            return;
        }
        for (StatPositionSummary positionSummary : positionSummaryList) {
            key = StatPositionCacheManager.getKey(positionSummary.getSubAccountId(), positionSummary.getTargetKey(), positionSummary.getTargetType());
            keyMap.put(key, emptyObject);

            targetId = Long.valueOf(positionSummary.getTargetKey());
            if (positionSummary.getTargetType() == HostingXQTargetType.CONTRACT_TARGET) {
                contractIdMap.put(targetId, emptyObject);
            } else {
                composeGraphIdMap.put(targetId, emptyObject);
            }
        }
    }

    private void cleanPositionSummaryData() {
        String key;
        Object emptyObject;

        // clean dynamic
        Collection<CachePositionDynamic> cachePositionDynamicCollection = StatPositionCacheManager.getInstance().getCachePositionDynamicList();
        for (CachePositionDynamic cachePositionDynamic : cachePositionDynamicCollection) {
            key = StatPositionCacheManager.getKey(cachePositionDynamic.getSubAccountId(), cachePositionDynamic.getTargetKey(), cachePositionDynamic.getTargetType());
            emptyObject = keyMap.get(key);
            if (emptyObject == null) {
                StatPositionCacheManager.getInstance().removeCachePositionDynamic(key);
            }
        }

        // clean summary
        Collection<CachePositionSummary> cachePositionSummaryCollection = StatPositionCacheManager.getInstance().getCachePositionSummaryList();
        for (CachePositionSummary cachePositionSummary : cachePositionSummaryCollection) {
            key = StatPositionCacheManager.getKey(cachePositionSummary.getSubAccountId(), cachePositionSummary.getTargetKey(), cachePositionSummary.getTargetType());
            emptyObject = keyMap.get(key);
            if (emptyObject == null) {
                StatPositionCacheManager.getInstance().removeStatPositionSummary(key);
            }
        }

        // clean contract quotation
        Collection<StatQuotationCache> statQuotationCacheCollection = StatQuotationCacheManager.getInstance().getContractQuotationCacheList();
        for (StatQuotationCache statQuotationCache : statQuotationCacheCollection) {
            emptyObject = contractIdMap.get(statQuotationCache.getContractId());
            if (emptyObject == null) {
                StatQuotationCacheManager.getInstance().removeQuotation(statQuotationCache.getContractId());
            }
        }

        // clean compose quotation
        Collection<StatQuoCombCache> statQuoCombCacheCollection = StatQuoCombCacheManager.getInstance().getComposeQuotationCacheList();
        for (StatQuoCombCache statQuoCombCache : statQuoCombCacheCollection) {
            emptyObject = composeGraphIdMap.get(statQuoCombCache.getComposeGraphId());
            if (emptyObject == null) {
                StatQuoCombCacheManager.getInstance().removeQuotation(statQuoCombCache.getComposeGraphId());
            }
        }
    }
}
