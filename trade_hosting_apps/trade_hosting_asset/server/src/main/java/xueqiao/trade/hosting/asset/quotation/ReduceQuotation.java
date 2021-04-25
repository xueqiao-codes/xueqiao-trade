package xueqiao.trade.hosting.asset.quotation;

import xueqiao.quotation.QuotationItem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReduceQuotation {
    private static ReduceQuotation ourInstance = new ReduceQuotation();

    public static ReduceQuotation getInstance() {
        return ourInstance;
    }

    private final static long TIME_SPAN = 500;

    private ReduceQuotation() {
    }

    private Map<String, Long> contractQuotationTimestampMap = new ConcurrentHashMap<>();

    public boolean isReduce(QuotationItem quotationItem) {
        String contractKey = buildContractKey(quotationItem);
        Long lastTimestamp = contractQuotationTimestampMap.get(contractKey);
        long nowTimestamp = System.currentTimeMillis();

        if (lastTimestamp == null || nowTimestamp - lastTimestamp > TIME_SPAN) {
            contractQuotationTimestampMap.put(contractKey, nowTimestamp);
            return false;
        }
        return true;
    }

    private String buildContractKey(QuotationItem quotationItem) {
        StringBuilder builder = new StringBuilder();
        builder.append(quotationItem.getSledExchangeCode());
        builder.append("|");
        builder.append(quotationItem.getSledCommodityType());
        builder.append("|");
        builder.append(quotationItem.getSledCommodityCode());
        builder.append("|");
        builder.append(quotationItem.getSledContractCode());
        return builder.toString();
    }
}
