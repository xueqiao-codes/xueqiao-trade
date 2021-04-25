package xueqiao.trade.hosting.position.statis.core.calculator.position.summary;

import com.antiy.error_code.ErrorCodeInner;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraphManager;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuoCombCacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.AbstractTargetCalculator;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatComposePositionDynamicInfoCalculator;
import xueqiao.trade.hosting.position.statis.service.bean.PositionSummaryData;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionSummaryHandler;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatComposePositionSummaryCalculator extends AbstractTargetCalculator<CachePositionSummary, Boolean> {

    public StatComposePositionSummaryCalculator(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
    }

    @Override
    public CachePositionSummary onCalculate(Boolean isCalculateDynamicInfo) throws ErrorInfo {
        CachePositionSummary cachePositionSummary = null;

        PositionSummaryData positionSummaryData = StatPositionSummaryHandler.queryPositionSummaryData(subAccountId, targetKey, targetType);
        List<StatPositionItem> statPositionItemList = positionSummaryData.getPositionItemList();

        cachePositionSummary = new CachePositionSummary();
        cachePositionSummary.setSubAccountId(subAccountId);
        cachePositionSummary.setTargetKey(targetKey);
        cachePositionSummary.setTargetType(targetType);

        /*
         * 因为数据兼容性问题，有可能查询不到早期的小单元,这里直接不做计算
         * */
        if ((statPositionItemList == null || statPositionItemList.size() < 1 || positionSummaryData.getPositionUnitList() == null || positionSummaryData.getPositionUnitList().size() < 1) &&
                (positionSummaryData.getClosedPositionSummaryList() == null || positionSummaryData.getClosedPositionSummaryList().size() < 1)) {
            cachePositionSummary.setNetPosition(0);
            cachePositionSummary.setClosedProfit(0);
            cachePositionSummary.setPositionTotalPriceMap(null);
        } else {
            // 计算多头仓量 和 空头仓量
            long netPosition = 0;
            if (statPositionItemList == null || statPositionItemList.size() < 1) {
                netPosition = 0;
            } else {
                long longPosition = 0;
                long shortPosition = 0;
                for (StatPositionItem item : statPositionItemList) {
                    if (item.getDirection() == StatDirection.STAT_BUY) {
                        longPosition += item.getQuantity();
                    } else {
                        shortPosition += item.getQuantity();
                    }
                }
                netPosition = longPosition - shortPosition;
            }
            cachePositionSummary.setNetPosition(netPosition);
            cachePositionSummary.setClosedProfit(calculateClosedProfit(positionSummaryData.getClosedPositionSummaryList()));
            cachePositionSummary.setPositionTotalPriceMap(calculatePositionTotalPrice(positionSummaryData.getPositionUnitList()));
        }
        /*
         * 更新缓存
         * */
        StatPositionCacheManager.getInstance().putStatPositionSummary(cachePositionSummary);

        /*
         * 获取行情，若行情有缓存，则计算持仓动态信息
         * */
        if (isCalculateDynamicInfo != null && isCalculateDynamicInfo) {
            StatQuoCombCache quoCombCache = StatQuoCombCacheManager.getInstance().getLatestQuotation(targetKey);
            if (quoCombCache != null) {
                new StatComposePositionDynamicInfoCalculator(subAccountId, targetKey, targetType).addTask(quoCombCache);
            }
        }
        return cachePositionSummary;
    }

    /**
     * 计算各腿持仓均价
     */
    private Map<Long, Double> calculatePositionTotalPrice(List<StatPositionUnit> positionUnitList) throws ErrorInfo {
        /*
         * 卖出总额 - 买入总额
         * */
        Map<Long, Double> positionTotalPriceMap = new HashMap<>();
        Map<Long, BigDecimal> tempBuyPositionTotalPriceMap = new HashMap<>();
        Map<Long, BigDecimal> tempSellPositionTotalPriceMap = new HashMap<>();

        BigDecimal tempBuyPositionTotalPrice = null;
        BigDecimal tempSellPositionTotalPrice = null;
        BigDecimal tempPositionPrice = null;
        if (positionUnitList != null && positionUnitList.size() > 0) {
            for (StatPositionUnit positionUnit : positionUnitList) {
                tempPositionPrice = new BigDecimal(new Double(positionUnit.getUnitPrice()).toString());
                tempPositionPrice = tempPositionPrice.multiply(new BigDecimal(positionUnit.getUnitQuantity()));

                if (positionUnit.getDirection() == StatDirection.STAT_BUY) {
                    tempBuyPositionTotalPrice = tempBuyPositionTotalPriceMap.get(positionUnit.getSledContractId());
                    if (tempBuyPositionTotalPrice == null) {
                        tempBuyPositionTotalPrice = new BigDecimal(0);
                    }
                    tempBuyPositionTotalPrice = tempBuyPositionTotalPrice.add(tempPositionPrice);
                    tempBuyPositionTotalPriceMap.put(positionUnit.getSledContractId(), tempBuyPositionTotalPrice);
                } else if (positionUnit.getDirection() == StatDirection.STAT_SELL) {
                    tempSellPositionTotalPrice = tempSellPositionTotalPriceMap.get(positionUnit.getSledContractId());
                    if (tempSellPositionTotalPrice == null) {
                        tempSellPositionTotalPrice = new BigDecimal(0);
                    }
                    tempSellPositionTotalPrice = tempSellPositionTotalPrice.add(tempPositionPrice);
                    tempSellPositionTotalPriceMap.put(positionUnit.getSledContractId(), tempSellPositionTotalPrice);
                } else {
                    throw StatErrorCode.errorInvalidStatDirection;
                }
            }
            Set<Long> contractIdSet = StatComposeGraphManager.getInstance().getComposeGraph(targetKey).getComposeSledContractIdSet();
            if (contractIdSet != null) {
                for (long contractId : contractIdSet) {
                    tempBuyPositionTotalPrice = tempBuyPositionTotalPriceMap.get(contractId);
                    tempSellPositionTotalPrice = tempSellPositionTotalPriceMap.get(contractId);
                    if (tempBuyPositionTotalPrice == null) {
                        tempBuyPositionTotalPrice = new BigDecimal(0);
                    }
                    if (tempSellPositionTotalPrice == null) {
                        tempSellPositionTotalPrice = new BigDecimal(0);
                    }
                    tempPositionPrice = tempSellPositionTotalPrice.subtract(tempBuyPositionTotalPrice);
                    positionTotalPriceMap.put(contractId, tempPositionPrice.doubleValue());
                }
            }
        }
        return positionTotalPriceMap;
    }

    /**
     * 计算平仓收益
     */
    private double calculateClosedProfit(List<StatClosedPositionSummary> closedPositionSummaryList) throws ErrorInfo {
        if (closedPositionSummaryList == null || closedPositionSummaryList.size() < 1) {
            return 0.0;
        }

        BigDecimal tempClosedProfit = null;
        double totalClosedProfit = 0.0;
        for (StatClosedPositionSummary closedPositionSummary : closedPositionSummaryList) {
            for (ClosedProfit closedProfit : closedPositionSummary.getClosedProfits()) {
                tempClosedProfit = new BigDecimal(closedProfit.getClosedProfitValue());
                Double currencyRate = CNYRateHelper.getInstance().getBaseCurrencyExchangeRate(closedProfit.getTradeCurrency());
                if (currencyRate == null) {
                    throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "get currency rate fail : " + closedProfit.getTradeCurrency());
                }
                tempClosedProfit = tempClosedProfit.multiply(new BigDecimal(currencyRate));
                totalClosedProfit += tempClosedProfit.doubleValue();
            }
        }
        return totalClosedProfit;
    }
}
