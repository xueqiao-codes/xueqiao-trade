package xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic;

import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.StatPositionDynamicInfo;
import xueqiao.trade.hosting.position.statis.core.cache.contract.StatContractCache;
import xueqiao.trade.hosting.position.statis.core.cache.contract.StatContractCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionDynamic;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class StatContractPositionDynamicInfoCalculator extends PositionDynamicCalculator<Void, StatQuotationCache> {

    private CachePositionSummary cachePositionSummary;
    private StatContractCache contractCache;
    private Long contractId;

    public StatContractPositionDynamicInfoCalculator(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
        contractId = Long.valueOf(targetKey);
    }

    @Override
    public Void onCalculate(StatQuotationCache quotationCache) throws ErrorInfo {

        if (quotationCache == null) {
            AppLog.e("onCalculate ---- quotationCache is null, targetKey : " + targetKey);
            return null;
        }

        StatPositionDynamicInfo positionDynamicInfo = new StatPositionDynamicInfo();
        positionDynamicInfo.setSubAccountId(subAccountId);
        positionDynamicInfo.setTargetKey(targetKey);
        positionDynamicInfo.setTargetType(targetType);

        cachePositionSummary = StatPositionCacheManager.getInstance().getStatPositionSummary(subAccountId, targetKey, targetType);
        if (cachePositionSummary == null) {
            AppLog.w("StatContractPositionDynamicInfoCalculator ## onCalculate ---- cachePositionSummary is null, subAccountId : " + subAccountId + ", targetKey : " + targetKey + ", targetType : " + targetType);
            StatQuotationHelper.unRegister(subAccountId, targetKey, targetType);
        } else {
            contractCache = StatContractCacheManager.getInstance().getContract(contractId, quotationCache.getContractSymbol());
            if (contractCache == null) {
                AppLog.w("StatContractPositionDynamicInfoCalculator ## onCalculate ---- contractCache is null, contractId : " + contractId);
            } else {
                /*
                 * 最新价
                 * */
                if (quotationCache.isSetLastPrice() || quotationCache.getLastPrice() != 0) {
                    positionDynamicInfo.setLastPrice(quotationCache.getLastPrice());
                }

                /*
                 * 持仓盈亏
                 * */
                if (quotationCache.isSetLastPrice() || quotationCache.getLastPrice() != 0) {
                    positionDynamicInfo.setPositionProfit(calculatePositionProfit(quotationCache.getLastPrice()));
                }

                /*
                 * 平仓盈亏
                 * */
                positionDynamicInfo.setClosedProfit(cachePositionSummary.getClosedProfit());

                /*
                 * 总盈亏
                 * */
                if (positionDynamicInfo.isSetPositionProfit()) {
                    positionDynamicInfo.setTotalProfit(positionDynamicInfo.getPositionProfit() + positionDynamicInfo.getClosedProfit());
                }

                /*
                 * 持仓货值
                 * */
                if (quotationCache.isSetLastPrice() || quotationCache.getLastPrice() != 0) {
                    calculatePositionValue(quotationCache, positionDynamicInfo);
                }

                /*
                 * 基于基币的杠杆
                 * */
                if (positionDynamicInfo.isSetPositionValue()) {
                    try {
                        double leverage = calculateBaseLeverage(positionDynamicInfo.getPositionValue());
                        positionDynamicInfo.setLeverage(leverage);
                    } catch (Throwable throwable) {
                        AppLog.e("calculateBaseLeverage fail, " + throwable.getMessage());
                    }
                }

                /*
                 * 当前用于计算的币种是 合约本身的币种
                 * */
                positionDynamicInfo.setCurrency(contractCache.getTradeCurrency());
            }
        }

        /*
         * 记录计算信息更新时间
         * */
        positionDynamicInfo.setModifyTimestampMs(System.currentTimeMillis());

        /*
         * 缓存计算结果
         * */
        StatPositionCacheManager.getInstance().putCachePositionDynamic(CachePositionDynamic.convertToCachePositionDynamic(positionDynamicInfo));

        /*
         * 推送到客户端
         * 不再在这里推送，统一在PositionDynamicInfoSenderJob中定时推送
         * */
//        sendPositionDynamicInfoMsg(positionDynamicInfo);
        return null;
    }

    /**
     * 计算持仓盈亏
     * 合约持仓盈亏 = (最新价 * 净仓 + 卖出总额 - 买入总额) * 合约乘数 * 计价单位
     */
    private double calculatePositionProfit(double lastPrice) {
        if (cachePositionSummary.getPositionTotalPriceMap() == null || cachePositionSummary.getPositionTotalPriceMap().size() < 1) {
            return 0;
        }
        double positionTotalPrice = cachePositionSummary.getPositionTotalPriceMap().get(contractId);
        BigDecimal positionProfit;
        if (cachePositionSummary.getNetPosition() == 0) {
            positionProfit = new BigDecimal(0);
        } else {
            positionProfit = new BigDecimal(lastPrice);
            positionProfit = positionProfit.multiply(new BigDecimal(cachePositionSummary.getNetPosition()));
        }
        positionProfit = positionProfit.add(new BigDecimal(positionTotalPrice));
        positionProfit = positionProfit.multiply(new BigDecimal(contractCache.getContractCoefficient()));
        return positionProfit.doubleValue();
    }

    /**
     * 计算持仓货值
     */
    private void calculatePositionValue(StatQuotationCache quotationCache, StatPositionDynamicInfo positionDynamicInfo) {
        BigDecimal positionValue = new BigDecimal(quotationCache.getLastPrice());
        long netPosition = cachePositionSummary.getNetPosition();
        if (netPosition < 0) {
            netPosition = -1 * netPosition;
        }
        positionValue = positionValue.multiply(new BigDecimal(netPosition));
        positionValue = positionValue.multiply(new BigDecimal(contractCache.getContractCoefficient()));

        /*
         * 记录各币种的货值
         * */
        Map<String, Double> positionValueMap = new HashMap<>();
        positionValueMap.put(contractCache.getTradeCurrency(), positionValue.doubleValue());
        positionDynamicInfo.setPositionValueMap(positionValueMap);

        /*
         * 计算货值
         * */
        positionDynamicInfo.setPositionValue(positionValue.doubleValue());
    }

    /**
     * 计算杠杆
     */
    private double calculateBaseLeverage(double positionValue) throws ErrorInfo {
        BigDecimal leverage = new BigDecimal(positionValue);
        double dynamicBenefit = getBaseCurrencyDynamicBenefit();
        if (dynamicBenefit == 0) {
            return 0;
        }
        leverage = CNYRateHelper.getInstance().exchangeRate1(contractCache.getTradeCurrency(), getBaseCurrency(), leverage);
        leverage = leverage.divide(new BigDecimal(dynamicBenefit), 16, BigDecimal.ROUND_HALF_UP);
        return leverage.doubleValue();
    }
}
