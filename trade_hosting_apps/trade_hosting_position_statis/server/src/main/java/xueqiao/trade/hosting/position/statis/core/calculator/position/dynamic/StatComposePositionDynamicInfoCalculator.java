package xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic;

import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.StatDirection;
import xueqiao.trade.hosting.position.statis.StatPositionDynamicInfo;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraph;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraphManager;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeLeg;
import xueqiao.trade.hosting.position.statis.core.cache.contract.StatContractCache;
import xueqiao.trade.hosting.position.statis.core.cache.contract.StatContractCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionDynamic;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StatComposePositionDynamicInfoCalculator extends PositionDynamicCalculator<Void, StatQuoCombCache> {

    private CachePositionSummary cachePositionSummary;
    private StatComposeGraph composeGraph;

    /*
     * Map<contractSymbol, StatQuotationCache>
     * */
    private Map<String, StatQuotationCache> statQuotationCacheMap = new HashMap<>();

    public StatComposePositionDynamicInfoCalculator(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        super(subAccountId, targetKey, targetType);
        StatComposeGraph tempComposeGraph = StatComposeGraphManager.getInstance().getComposeGraph(targetKey);
        /*
         * ????????????
         * */
        if (tempComposeGraph != null) {
            Gson gson = new Gson();
            composeGraph = gson.fromJson(gson.toJson(tempComposeGraph), StatComposeGraph.class);
        }
    }

    @Override
    public Void onCalculate(StatQuoCombCache quoCombCache) throws ErrorInfo {

        if (quoCombCache == null) {
            AppLog.e("onCalculate ---- quoCombCache is null, targetKey : " + targetKey);
        }

        if (composeGraph == null) {
            AppLog.e("StatComposePositionDynamicInfoCalculator ### onCalculate  -------- composeGraph is null, targetKey : " + targetKey);
            return null;
        }

        StatPositionDynamicInfo positionDynamicInfo = new StatPositionDynamicInfo();
        positionDynamicInfo.setSubAccountId(subAccountId);
        positionDynamicInfo.setTargetKey(targetKey);
        positionDynamicInfo.setTargetType(targetType);

        cachePositionSummary = StatPositionCacheManager.getInstance().getStatPositionSummary(subAccountId, targetKey, targetType);
        if (cachePositionSummary == null) {
            AppLog.w("StatComposePositionDynamicInfoCalculator ## onCalculate ---- cachePositionSummary is null, subAccountId : " + subAccountId + ", targetKey : " + targetKey + ", targetType : " + targetType);
            StatQuotationHelper.unRegister(subAccountId, targetKey, targetType);
//            return null;
        } else {
            convertQuoCombToMap(quoCombCache);

            /*
             * ?????????
             * */
            if (quoCombCache.getCombItem().isSetLastPrice() || quoCombCache.getCombItem().getLastPrice() != 0) {
                positionDynamicInfo.setLastPrice(quoCombCache.getCombItem().getLastPrice());
            }

            /*
             * ????????????
             * */
            Double positionProfit = calculatePositionProfit();
            if (positionProfit != null) {
                positionDynamicInfo.setPositionProfit(positionProfit);
            }

            /*
             * ????????????
             * */
            positionDynamicInfo.setClosedProfit(cachePositionSummary.getClosedProfit());

            /*
             * ?????????
             * */
            if (positionDynamicInfo.isSetPositionProfit()) {
                positionDynamicInfo.setTotalProfit(positionDynamicInfo.getPositionProfit() + positionDynamicInfo.getClosedProfit());
            } else {
                positionDynamicInfo.setTotalProfit(positionDynamicInfo.getClosedProfit());
            }

            /*
             * ????????????
             * */
            calculatePositionValue(positionDynamicInfo);

            /*
             * ??????
             * */
            if (positionDynamicInfo.isSetPositionValue()) {
                positionDynamicInfo.setLeverage(calculateLeverage(positionDynamicInfo.getPositionValue()));
            }

            /*
             * ?????????????????????????????? CNY
             * */
            positionDynamicInfo.setCurrency(getBaseCurrency());
        }

        /*
         * ??????????????????????????????
         * */
        positionDynamicInfo.setModifyTimestampMs(System.currentTimeMillis());

        /*
         * ??????????????????
         * */
        StatPositionCacheManager.getInstance().putCachePositionDynamic(CachePositionDynamic.convertToCachePositionDynamic(positionDynamicInfo));

        /*
         * ??????????????????
         * ?????????????????????????????????PositionDynamicInfoSenderJob???????????????
         * */
//        sendPositionDynamicInfoMsg(positionDynamicInfo);
        return null;
    }

    private void convertQuoCombToMap(StatQuoCombCache quoCombCache) throws ErrorInfo {
        statQuotationCacheMap.clear();
        for (StatQuotationCache quotationCache : quoCombCache.getLegItems()) {
            statQuotationCacheMap.put(quotationCache.getContractSymbol(), quotationCache);
        }
    }

    /**
     * ??????????????????
     * ?????????????????? = (????????? * ?????? + ???????????? - ????????????) * ???????????? * ????????????
     */
    private Double calculatePositionProfit() throws ErrorInfo {
        if (cachePositionSummary.getPositionTotalPriceMap() == null || cachePositionSummary.getPositionTotalPriceMap().size() < 1) {
            return null;
        }
        BigDecimal positionProfit = new BigDecimal(0);
        BigDecimal tempPositionProfit;
        /*
         * ???????????????????????????id
         * */
        Collection<StatComposeLeg> statComposeLegCollection = composeGraph.getStatComposeLegCollection();
        StatQuotationCache quotationCache;
        StatContractCache contractCache;
        StatComposeLeg composeLeg;
        double positionTotalPrice;
        for (StatComposeLeg statComposeLeg : statComposeLegCollection) {
            quotationCache = statQuotationCacheMap.get(statComposeLeg.getContractSymbol());

            if (!quotationCache.isSetLastPrice() && quotationCache.getLastPrice() == 0) {
                // ????????????????????????????????????????????????????????????
                return null;
            }

            positionTotalPrice = cachePositionSummary.getPositionTotalPriceMap().get(statComposeLeg.getSledContractId());
            contractCache = StatContractCacheManager.getInstance().getContract(statComposeLeg.getSledContractId(), statComposeLeg.getContractSymbol());
            composeLeg = composeGraph.getComposeLegQuantityMap().get(statComposeLeg.getSledContractId());

            if (cachePositionSummary.getNetPosition() == 0) {
                tempPositionProfit = new BigDecimal(0);
            } else {
                /*
                 * ????????? ?????????????????????????????????????????????0??? ??????????????????????????????????????????????????????????????????
                 * */
                int composeLegQuantity;
                if (composeLeg.getDirection() == StatDirection.STAT_BUY) {
                    composeLegQuantity = composeLeg.getQuantity();
                } else {
                    composeLegQuantity = -1 * composeLeg.getQuantity();
                }
                long legNetQuantity = cachePositionSummary.getNetPosition() * composeLegQuantity;

                tempPositionProfit = new BigDecimal(quotationCache.getLastPrice());
                tempPositionProfit = tempPositionProfit.multiply(new BigDecimal(legNetQuantity));
            }

            tempPositionProfit = tempPositionProfit.add(new BigDecimal(positionTotalPrice));
            tempPositionProfit = tempPositionProfit.multiply(new BigDecimal(contractCache.getContractBaseCurrencyCoefficient()));

            positionProfit = positionProfit.add(tempPositionProfit);
        }
        return positionProfit.doubleValue();
    }

    /**
     * ??????????????????
     */
    private void calculatePositionValue(StatPositionDynamicInfo positionDynamicInfo) throws ErrorInfo {
        StatQuotationCache quotationCache;
        StatContractCache contractCache;
        StatComposeLeg composeLeg;
        BigDecimal buyPositionValue = new BigDecimal(0);
        BigDecimal sellPositionValue = new BigDecimal(0);
        BigDecimal tempPositionValue;

        /*
         * ????????????????????????
         * */
        Map<String, Double> positionValueMap = new HashMap<>();

        for (Map.Entry<Long, StatComposeLeg> entry : composeGraph.getComposeLegQuantityMap().entrySet()) {
            composeLeg = entry.getValue();
            quotationCache = statQuotationCacheMap.get(composeLeg.getContractSymbol());

            if (!quotationCache.isSetLastPrice() && quotationCache.getLastPrice() == 0) {
                // ????????????????????????????????????????????????????????????
                return;
            }

            contractCache = StatContractCacheManager.getInstance().getContract(composeLeg.getSledContractId(), composeLeg.getContractSymbol());

            if (cachePositionSummary.getNetPosition() > 0) {
                if (composeLeg.getDirection() == StatDirection.STAT_BUY) {
                    tempPositionValue = new BigDecimal(quotationCache.getLastPrice());
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(cachePositionSummary.getNetPosition()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(composeLeg.getQuantity()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(contractCache.getContractCoefficient()));

                    positionValueMap.put(contractCache.getTradeCurrency(), tempPositionValue.doubleValue());

                    tempPositionValue = CNYRateHelper.getInstance().exchangeRate1(contractCache.getTradeCurrency(), getBaseCurrency(), tempPositionValue);
                    buyPositionValue = buyPositionValue.add(tempPositionValue);
                } else {
                    tempPositionValue = new BigDecimal(quotationCache.getLastPrice());
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(cachePositionSummary.getNetPosition()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(composeLeg.getQuantity()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(contractCache.getContractCoefficient()));

                    /*
                     * ?????????????????????
                     * */
                    positionValueMap.put(contractCache.getTradeCurrency(), tempPositionValue.doubleValue() * -1);

                    tempPositionValue = CNYRateHelper.getInstance().exchangeRate1(contractCache.getTradeCurrency(), getBaseCurrency(), tempPositionValue);
                    sellPositionValue = sellPositionValue.add(tempPositionValue);
                }
            } else if (cachePositionSummary.getNetPosition() < 0) {
                long absNetPosition = -1 * cachePositionSummary.getNetPosition();
                if (composeLeg.getDirection() == StatDirection.STAT_BUY) {
                    tempPositionValue = new BigDecimal(quotationCache.getLastPrice());
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(cachePositionSummary.getNetPosition()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(composeLeg.getQuantity()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(contractCache.getContractCoefficient()));

                    /*
                     * ?????????????????????
                     * */
                    positionValueMap.put(contractCache.getTradeCurrency(), tempPositionValue.doubleValue() * -1);

                    tempPositionValue = CNYRateHelper.getInstance().exchangeRate1(contractCache.getTradeCurrency(), getBaseCurrency(), tempPositionValue);
                    sellPositionValue = sellPositionValue.add(tempPositionValue);
                } else {
                    tempPositionValue = new BigDecimal(quotationCache.getLastPrice());
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(absNetPosition));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(composeLeg.getQuantity()));
                    tempPositionValue = tempPositionValue.multiply(new BigDecimal(contractCache.getContractCoefficient()));

                    positionValueMap.put(contractCache.getTradeCurrency(), tempPositionValue.doubleValue());

                    tempPositionValue = CNYRateHelper.getInstance().exchangeRate1(contractCache.getTradeCurrency(), getBaseCurrency(), tempPositionValue);
                    buyPositionValue = buyPositionValue.add(tempPositionValue);
                }
            }
        }
        positionDynamicInfo.setPositionValueMap(positionValueMap);
        if (buyPositionValue.doubleValue() > sellPositionValue.doubleValue()) {
            positionDynamicInfo.setPositionValue(buyPositionValue.doubleValue());
        } else {
            positionDynamicInfo.setPositionValue(sellPositionValue.doubleValue());
        }
    }

    /**
     * ????????????
     */
    protected double calculateLeverage(double positionValue) throws ErrorInfo {
        BigDecimal leverage = new BigDecimal(positionValue);
        double dynamicBenefit = getBaseCurrencyDynamicBenefit();
        if (dynamicBenefit == 0) {
            return 0;
        }
        leverage = leverage.divide(new BigDecimal(dynamicBenefit), 16, BigDecimal.ROUND_HALF_UP);
        return leverage.doubleValue();
    }
}
