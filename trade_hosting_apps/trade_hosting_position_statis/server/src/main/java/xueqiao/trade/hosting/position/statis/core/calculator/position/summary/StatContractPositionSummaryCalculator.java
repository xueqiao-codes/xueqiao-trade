package xueqiao.trade.hosting.position.statis.core.calculator.position.summary;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.StatClosedPositionSummary;
import xueqiao.trade.hosting.position.statis.StatDirection;
import xueqiao.trade.hosting.position.statis.StatPositionItem;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionSummary;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCache;
import xueqiao.trade.hosting.position.statis.core.cache.quotation.StatQuotationCacheManager;
import xueqiao.trade.hosting.position.statis.core.calculator.AbstractTargetCalculator;
import xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic.StatContractPositionDynamicInfoCalculator;
import xueqiao.trade.hosting.position.statis.service.bean.PositionSummaryData;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionSummaryHandler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计算 合约标的 的持仓汇总信息
 */
public class StatContractPositionSummaryCalculator extends AbstractTargetCalculator<CachePositionSummary, Boolean> {

    public StatContractPositionSummaryCalculator(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        super(subAccountId, targetKey, targetType);
    }

    @Override
    public CachePositionSummary onCalculate(Boolean isCalculateDynamicInfo) throws ErrorInfo {
        CachePositionSummary cachePositionSummary = null;

        PositionSummaryData positionSummaryData = StatPositionSummaryHandler.queryPositionSummaryData(subAccountId, targetKey, targetType);
        List<StatPositionItem> statPositionItemList = positionSummaryData.getPositionItemList();//StatPositionHandler.queryStatPositionItemList(subAccountId, targetKey, targetType);

        if ((positionSummaryData.getPositionItemList() == null || positionSummaryData.getPositionItemList().size() < 1 ||
                positionSummaryData.getPositionUnitList() == null || positionSummaryData.getPositionUnitList().size() < 1) &&
                (positionSummaryData.getClosedPositionSummaryList() == null || positionSummaryData.getClosedPositionSummaryList().size() < 1)) {
            /*
             * 从缓存中删除
             * 不在这里删除，在一个固定频率的CleanCacheJob中清理
             * */
//            StatPositionCacheManager.getInstance().removeStatPositionSummary(subAccountId, targetKey, targetType);
        } else {
            Map<Long, Double> positionTotalPriceMap = new HashMap<>();
            long contractId = Long.valueOf(targetKey);
            long netPosition = 0;
            cachePositionSummary = new CachePositionSummary();

            // 计算多头仓量 和 空头仓量
            if (statPositionItemList == null || statPositionItemList.size() < 1) {
                netPosition = 0;
                positionTotalPriceMap.put(contractId, new Double(0));
            } else {
                long longPosition = 0;
                long shortPosition = 0;
                BigDecimal buyPriceSum = new BigDecimal(0);
                BigDecimal sellPriceSum = new BigDecimal(0);
                BigDecimal tradePrice = null;
                for (StatPositionItem item : statPositionItemList) {
                    if (item.getDirection() == StatDirection.STAT_BUY) {
                        longPosition += item.getQuantity();
                        tradePrice = new BigDecimal(new Double(item.getPrice()).toString());
                        buyPriceSum = buyPriceSum.add(tradePrice.multiply(new BigDecimal(item.getQuantity())));
                    } else {
                        shortPosition += item.getQuantity();
                        tradePrice = new BigDecimal(new Double(item.getPrice()).toString());
                        sellPriceSum = sellPriceSum.add(tradePrice.multiply(new BigDecimal(item.getQuantity())));
                    }
                }
                netPosition = longPosition - shortPosition;
                /*
                 * 卖出总额 - 买入总额
                 * */
                double positionTotalPrice = sellPriceSum.subtract(buyPriceSum).doubleValue();
                positionTotalPriceMap.put(contractId, positionTotalPrice);
            }
            cachePositionSummary.setSubAccountId(subAccountId);
            cachePositionSummary.setTargetKey(targetKey);
            cachePositionSummary.setTargetType(targetType);
            cachePositionSummary.setNetPosition(netPosition);
            cachePositionSummary.setClosedProfit(calculateClosedProfit(positionSummaryData.getClosedPositionSummaryList()));
            cachePositionSummary.setPositionTotalPriceMap(positionTotalPriceMap);

            /*
             * 更新缓存
             * */
            StatPositionCacheManager.getInstance().putStatPositionSummary(cachePositionSummary);
        }

        /*
         * 获取行情，若行情有缓存，则计算持仓动态信息
         * */
        if (isCalculateDynamicInfo != null && isCalculateDynamicInfo) {
            StatQuotationCache quotationCache = StatQuotationCacheManager.getInstance().getLatestQuotation(targetKey);
            if (quotationCache != null) {
                new StatContractPositionDynamicInfoCalculator(subAccountId, targetKey, targetType).addTask(quotationCache);
            }
        }
        return cachePositionSummary;
    }

    private double calculateClosedProfit(List<StatClosedPositionSummary> closedPositionSummaryList) {
        if (closedPositionSummaryList == null || closedPositionSummaryList.size() < 1) {
            return 0.0;
        }

        double closedProfit = 0.0;
        for (StatClosedPositionSummary closedPositionSummary : closedPositionSummaryList) {
            closedProfit += closedPositionSummary.getClosedProfits().get(0).getClosedProfitValue();
        }
        return closedProfit;
    }
}
