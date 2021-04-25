package xueqiao.trade.hosting.position.statis.core.cache.position;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.core.cache.ICacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.position.summary.StatComposePositionSummaryCalculator;
import xueqiao.trade.hosting.position.statis.core.calculator.position.summary.StatContractPositionSummaryCalculator;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatPositionCacheManager implements ICacheManager {
    private static StatPositionCacheManager ourInstance = new StatPositionCacheManager();

    public static StatPositionCacheManager getInstance() {
        return ourInstance;
    }

    private StatPositionCacheManager() {
    }

    private static byte[] lock = new byte[0];

    /**
     * Map<key, StatPositionSummary>
     */
    private Map<String, CachePositionSummary> statPositionSummaryMap = new ConcurrentHashMap<>();

    /**
     * Map<key, CachePositionDynamic>
     */
    private Map<String, CachePositionDynamic> statPositionDynamicMap = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取持仓汇总信息
     */
    public CachePositionSummary getStatPositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        return getStatPositionSummary(subAccountId, targetKey, targetType, false);
    }

    /**
     * 从缓存中获取持仓汇总信息
     */
    public CachePositionSummary getStatPositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType, boolean isCalculateDynamicInfo) throws ErrorInfo {
        String key = getKey(subAccountId, targetKey, targetType);
        CachePositionSummary positionSummary = statPositionSummaryMap.get(key);
        if (positionSummary == null) {
            synchronized (lock) {
                positionSummary = statPositionSummaryMap.get(key);
                if (positionSummary == null) {
                    positionSummary = queryCachePositionSummary(subAccountId, targetKey, targetType, isCalculateDynamicInfo);
                }
            }
        }
        /*
         * 小单元的数据库有更改，里面添加了subAccountId, targetKey, targetType等字段，旧的数据这几个字段是无有效值的，
         * 因而通过这几个值查询不到小单元数据，因而会导致positionSummary为空，这是旧数据的兼容性问题.
         * */
        return positionSummary;
    }

    /**
     * 从缓存中获取持仓动态信息
     */
    public CachePositionDynamic getCachePositionDynamic(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        String key = getKey(subAccountId, targetKey, targetType);
        return statPositionDynamicMap.get(key);
    }

    /**
     * 从缓存中获取所有持仓动态信息
     */
    public Collection<CachePositionDynamic> getCachePositionDynamicList() {
        return statPositionDynamicMap.values();
    }

    /**
     * 从缓存中获取所有持仓动态信息
     */
    public Collection<CachePositionSummary> getCachePositionSummaryList() {
        return statPositionSummaryMap.values();
    }

    /**
     * 更新缓存中的持仓汇总信息
     */
    public void putStatPositionSummary(CachePositionSummary positionSummary) {
        String key = getKey(positionSummary.getSubAccountId(), positionSummary.getTargetKey(), positionSummary.getTargetType());
        statPositionSummaryMap.put(key, positionSummary);
    }

    /**
     * 更新缓存中的持仓动态信息
     */
    public void putCachePositionDynamic(CachePositionDynamic positionDynamic) {
        String key = getKey(positionDynamic.getSubAccountId(), positionDynamic.getTargetKey(), positionDynamic.getTargetType());
        statPositionDynamicMap.put(key, positionDynamic);
    }

    /**
     * 从缓存中删除持仓动态信息
     */
//    public void removeStatPositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType) {
//        String key = getKey(subAccountId, targetKey, targetType);
//        statPositionSummaryMap.remove(key);
//    }

    public void removeStatPositionSummary(String key) {
        statPositionSummaryMap.remove(key);
    }

    /**
     * 从缓存中删除持仓动态信息
     */
//    public void removeCachePositionDynamic(long subAccountId, String targetKey, HostingXQTargetType targetType) {
//        String key = getKey(subAccountId, targetKey, targetType);
//        statPositionDynamicMap.remove(key);
//    }

    /**
     * 从缓存中删除持仓动态信息
     */
//    public void removeCachePositionDynamic(CachePositionDynamic positionDynamic) {
//        String key = getKey(positionDynamic.getSubAccountId(), positionDynamic.getTargetKey(), positionDynamic.getTargetType());
//        statPositionDynamicMap.remove(key);
//    }

    public void removeCachePositionDynamic(String key) {
        statPositionDynamicMap.remove(key);
    }

    private CachePositionSummary queryCachePositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType, boolean isCalculateDynamicInfo) throws ErrorInfo {
        if (targetType == HostingXQTargetType.COMPOSE_TARGET) {
            /*
             * 组合
             * */
            return new StatComposePositionSummaryCalculator(subAccountId, targetKey, targetType).onCalculate(isCalculateDynamicInfo);
        } else if (targetType == HostingXQTargetType.CONTRACT_TARGET) {
            /*
             * 合约
             * */
            return new StatContractPositionSummaryCalculator(subAccountId, targetKey, targetType).onCalculate(isCalculateDynamicInfo);
        } else {
            throw StatErrorCode.errorInvalidTargetType;
        }
    }

    public static String getKey(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        StringBuilder builder = new StringBuilder();
        builder.append(subAccountId);
        builder.append("|");
        builder.append(targetKey);
        builder.append("|");
        builder.append(targetType.getValue());
        return builder.toString();
    }

    @Override
    public void clear() {
        statPositionSummaryMap.clear();
        statPositionDynamicMap.clear();
    }
}
