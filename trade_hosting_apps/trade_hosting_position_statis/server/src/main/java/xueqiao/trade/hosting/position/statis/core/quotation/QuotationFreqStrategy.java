package xueqiao.trade.hosting.position.statis.core.quotation;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuotationFreqStrategy {
    private static QuotationFreqStrategy ourInstance = new QuotationFreqStrategy();

    public static QuotationFreqStrategy getInstance() {
        return ourInstance;
    }

    private final static long TIME_SPAN = 500;

    private QuotationFreqStrategy() {
    }

    private Map<String, Long> targetQuotationTimestampMap = new ConcurrentHashMap<>();

    public boolean isReduce(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        String globalTargetKey = getGlobalTargetKey(subAccountId, targetKey, targetType);
        Long lastTimestamp = targetQuotationTimestampMap.get(globalTargetKey);
        long nowTimestamp = System.currentTimeMillis();

        if (lastTimestamp == null || nowTimestamp - lastTimestamp > TIME_SPAN) {
            targetQuotationTimestampMap.put(globalTargetKey, nowTimestamp);
            return false;
        }
        return true;
    }

    private String getGlobalTargetKey(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        StringBuilder builder = new StringBuilder();
        builder.append(subAccountId);
        builder.append("|");
        builder.append(targetKey);
        builder.append("|");
        builder.append(targetType.getValue());
        return builder.toString();
    }
}
