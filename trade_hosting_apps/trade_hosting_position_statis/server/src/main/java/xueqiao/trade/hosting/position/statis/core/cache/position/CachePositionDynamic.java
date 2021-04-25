package xueqiao.trade.hosting.position.statis.core.cache.position;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.StatPositionDynamicInfo;

import java.util.HashMap;
import java.util.Map;

public class CachePositionDynamic {
    private String targetKey;
    private long subAccountId;
    private xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType targetType;
    private double lastPrice;
    private boolean isSetLastPrice;
    private double positionProfit;
    private boolean isSetPositionProfit;
    private double closedProfit;
    private boolean isSetClosedProfit;
    private double totalProfit;
    private boolean isSetTotalProfit;
    private double positionValue;
    private boolean isSetPositionValue;
    private double leverage;
    private boolean isSetLeverage;
    private Map<String, Double> positionValueMap;
    private boolean isSetPositionValueMap;
    private String currency;
    private boolean isSetCurrency;
    private long modifyTimestampMs;

    public CachePositionDynamic() {
        isSetLastPrice = false;
        isSetPositionProfit = false;
        isSetClosedProfit = false;
        isSetTotalProfit = false;
        isSetPositionValue = false;
        isSetLeverage = false;
        isSetPositionValueMap = false;
        isSetCurrency = false;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public HostingXQTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(HostingXQTargetType targetType) {
        this.targetType = targetType;
    }

    public double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(double lastPrice) {
        this.lastPrice = lastPrice;
        isSetLastPrice = true;
    }

    public double getPositionProfit() {
        return positionProfit;
    }

    public void setPositionProfit(double positionProfit) {
        this.positionProfit = positionProfit;
        isSetPositionProfit = true;
    }

    public double getClosedProfit() {
        return closedProfit;
    }

    public void setClosedProfit(double closedProfit) {
        this.closedProfit = closedProfit;
        isSetClosedProfit = true;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
        isSetTotalProfit = true;
    }

    public double getPositionValue() {
        return positionValue;
    }

    public void setPositionValue(double positionValue) {
        this.positionValue = positionValue;
        isSetPositionValue = true;
    }

    public double getLeverage() {
        return leverage;
    }

    public void setLeverage(double leverage) {
        this.leverage = leverage;
        isSetLeverage = true;
    }

    public Map<String, Double> getPositionValueMap() {
        return positionValueMap;
    }

    public void setPositionValueMap(Map<String, Double> positionValueMap) {
        this.positionValueMap = positionValueMap;
        isSetPositionValueMap = true;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        isSetCurrency = true;
    }

    public long getModifyTimestampMs() {
        return modifyTimestampMs;
    }

    public void setModifyTimestampMs(long modifyTimestampMs) {
        this.modifyTimestampMs = modifyTimestampMs;
    }

    public boolean isSetLastPrice() {
        return isSetLastPrice;
    }

    public void unsetLastPrice() {
        isSetLastPrice = false;
    }

    public boolean isSetPositionProfit() {
        return isSetPositionProfit;
    }

    public void unsetPositionProfit() {
        isSetPositionProfit = false;
    }

    public boolean isSetClosedProfit() {
        return isSetClosedProfit;
    }

    public void unsetClosedProfit() {
        isSetClosedProfit = false;
    }

    public boolean isSetTotalProfit() {
        return isSetTotalProfit;
    }

    public void unsetTotalProfit() {
        isSetTotalProfit = false;
    }

    public boolean isSetPositionValue() {
        return isSetPositionValue;
    }

    public void unsetPositionValue() {
        isSetPositionValue = false;
    }

    public boolean isSetLeverage() {
        return isSetLeverage;
    }

    public void unsetLeverage() {
        isSetLeverage = false;
    }

    public boolean isSetPositionValueMap() {
        return isSetPositionValueMap;
    }

    public void unsetPositionValueMap() {
        isSetPositionValueMap = false;
    }

    public boolean isSetCurrency() {
        return isSetCurrency;
    }

    public void unsetCurrency() {
        isSetCurrency = false;
    }

    public static CachePositionDynamic convertToCachePositionDynamic(StatPositionDynamicInfo positionDynamicInfo) {
        CachePositionDynamic cachePositionDynamic = new CachePositionDynamic();
        Map<String, Double> positionValueMap = new HashMap<>();
        if (positionDynamicInfo.getPositionValueMap() != null && !positionDynamicInfo.getPositionValueMap().isEmpty()) {
            for (Map.Entry<String, Double> entry : positionDynamicInfo.getPositionValueMap().entrySet()) {
                positionValueMap.put(entry.getKey(), entry.getValue());
            }
        }

        cachePositionDynamic.setTargetKey(positionDynamicInfo.getTargetKey());
        cachePositionDynamic.setSubAccountId(positionDynamicInfo.getSubAccountId());
        cachePositionDynamic.setTargetType(positionDynamicInfo.getTargetType());
        if (positionDynamicInfo.isSetLastPrice()) {
            cachePositionDynamic.setLastPrice(positionDynamicInfo.getLastPrice());
        }
        if (positionDynamicInfo.isSetPositionProfit()) {
            cachePositionDynamic.setPositionProfit(positionDynamicInfo.getPositionProfit());
        }
        if (positionDynamicInfo.isSetClosedProfit()) {
            cachePositionDynamic.setClosedProfit(positionDynamicInfo.getClosedProfit());
        }
        if (positionDynamicInfo.isSetTotalProfit()) {
            cachePositionDynamic.setTotalProfit(positionDynamicInfo.getTotalProfit());
        }
        if (positionDynamicInfo.isSetPositionValue()) {
            cachePositionDynamic.setPositionValue(positionDynamicInfo.getPositionValue());
        }
        if (positionDynamicInfo.isSetLeverage()) {
            cachePositionDynamic.setLeverage(positionDynamicInfo.getLeverage());
        }
        cachePositionDynamic.setPositionValueMap(positionValueMap);
        if (positionDynamicInfo.isSetCurrency()) {
            cachePositionDynamic.setCurrency(positionDynamicInfo.getCurrency());
        }
        cachePositionDynamic.setModifyTimestampMs(positionDynamicInfo.getModifyTimestampMs());

        return cachePositionDynamic;
    }

    public static StatPositionDynamicInfo convertToPositionDynamicInfo(CachePositionDynamic cachePositionDynamic) {
        StatPositionDynamicInfo positionDynamicInfo = new StatPositionDynamicInfo();

        Map<String, Double> positionValueMap = new HashMap<>();
        if (!cachePositionDynamic.getPositionValueMap().isEmpty()) {
            for (Map.Entry<String, Double> entry : cachePositionDynamic.getPositionValueMap().entrySet()) {
                positionValueMap.put(entry.getKey(), entry.getValue());
            }
        }

        positionDynamicInfo.setTargetKey(cachePositionDynamic.getTargetKey());
        positionDynamicInfo.setSubAccountId(cachePositionDynamic.getSubAccountId());
        positionDynamicInfo.setTargetType(cachePositionDynamic.getTargetType());
        if (cachePositionDynamic.isSetLastPrice) {
            positionDynamicInfo.setLastPrice(cachePositionDynamic.getLastPrice());
        }
        if (cachePositionDynamic.isSetPositionProfit()) {
            positionDynamicInfo.setPositionProfit(cachePositionDynamic.getPositionProfit());
        }
        if (cachePositionDynamic.isSetClosedProfit()) {
            positionDynamicInfo.setClosedProfit(cachePositionDynamic.getClosedProfit());
        }
        if (cachePositionDynamic.isSetTotalProfit()) {
            positionDynamicInfo.setTotalProfit(cachePositionDynamic.getTotalProfit());
        }
        if (cachePositionDynamic.isSetPositionValue()) {
            positionDynamicInfo.setPositionValue(cachePositionDynamic.getPositionValue());
        }
        if (cachePositionDynamic.isSetLeverage()) {
            positionDynamicInfo.setLeverage(cachePositionDynamic.getLeverage());
        }
        positionDynamicInfo.setPositionValueMap(positionValueMap);
        if (cachePositionDynamic.isSetCurrency()) {
            positionDynamicInfo.setCurrency(cachePositionDynamic.getCurrency());
        }
        positionDynamicInfo.setModifyTimestampMs(cachePositionDynamic.getModifyTimestampMs());

        return positionDynamicInfo;
    }

}
