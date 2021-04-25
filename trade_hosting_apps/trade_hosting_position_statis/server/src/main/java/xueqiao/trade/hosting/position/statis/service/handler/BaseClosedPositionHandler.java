package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.service.bean.ArchiveItemData;
import xueqiao.trade.hosting.position.statis.service.bean.ClosedPositionData;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.StatClosedPositionItemTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatClosedPositionSummaryTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatClosedUnitTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatSummaryClosedProfitTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseClosedPositionHandler {

    /**
     * 获取平仓明细列表
     */
    public List<StatClosedPositionItem> queryStatClosedPositionItemList(long closedId) throws ErrorInfo {
        return new DBQueryHelper<List<StatClosedPositionItem>>(PositionStatisDB.Global()) {
            @Override
            protected List<StatClosedPositionItem> onQuery(Connection connection) throws Exception {
                return new StatClosedPositionItemTable(connection).queryStatClosedPositionItemList(closedId);
            }
        }.query();
    }

    /**
     * 获取平仓小单元列表
     */
    public List<StatClosedUnit> queryClosedUnitList(long closedItemId) throws ErrorInfo {
        return new DBQueryHelper<List<StatClosedUnit>>(PositionStatisDB.Global()) {
            @Override
            protected List<StatClosedUnit> onQuery(Connection connection) throws Exception {
                return new StatClosedUnitTable(connection).queryStatClosedUnitList(closedItemId);
            }
        }.query();
    }

    /**
     * 计算出（未归档的）平仓天汇总
     */
    public static StatClosedPositionDateSummary getDateSummary(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        StatClosedPositionHandler statClosedPositionHandler = new StatClosedPositionHandler();

        List<StatClosedPositionSummary> statClosedPositionSummaryList = statClosedPositionHandler.queryStatClosedPositionSummary(subAccountId, targetKey, targetType);
        if (statClosedPositionSummaryList == null || statClosedPositionSummaryList.isEmpty()) {
            return null;
        }

        StatClosedPositionDateSummary statClosedPositionDateSummary = new StatClosedPositionDateSummary();
        statClosedPositionDateSummary.setSubAccountId(subAccountId);
        statClosedPositionDateSummary.setTargetKey(targetKey);
        statClosedPositionDateSummary.setTargetType(targetType);
        statClosedPositionDateSummary.setArchivedDateTimestampMs(0);

        long closePosition = 0;
        double tempCloseProfit = 0.0;
        double spreadProfit = 0.0;

        Map<String, Double> closedProfitMap = new HashMap<>();

        for (StatClosedPositionSummary statClosedPositionSummary : statClosedPositionSummaryList) {
            closePosition += statClosedPositionSummary.getClosedPosition();
            spreadProfit += statClosedPositionSummary.getSpreadProfit();

            // 按币种累加平仓收益
            for (ClosedProfit closedProfit : statClosedPositionSummary.getClosedProfits()) {
                if (closedProfitMap.containsKey(closedProfit.getTradeCurrency())) {
                    tempCloseProfit = closedProfitMap.get(closedProfit.getTradeCurrency());
                } else {
                    tempCloseProfit = 0.0;
                }
                tempCloseProfit += closedProfit.getClosedProfitValue();
                closedProfitMap.put(closedProfit.getTradeCurrency(), tempCloseProfit);
            }
        }

        List<ClosedProfit> closedProfitList = new ArrayList<ClosedProfit>();
        ClosedProfit closedProfit = null;
        double totalClosedProfit = 0.0;
        for (String currency : closedProfitMap.keySet()) {
            closedProfit = new ClosedProfit();
            closedProfit.setTradeCurrency(currency);
            /*
             * 按每种币种计算总收益
             * 数据里存储的是各币种下的收益
             * 但计算各币种的总收益返回
             * */
            totalClosedProfit = CNYRateHelper.getInstance().calculateTotalValue(currency, closedProfitMap);
            closedProfit.setClosedProfitValue(totalClosedProfit/*closedProfitMap.get(currency).doubleValue()*/);
            closedProfitList.add(closedProfit);
        }

        statClosedPositionDateSummary.setClosedPosition(closePosition);
        statClosedPositionDateSummary.setSpreadProfit(spreadProfit);
        statClosedPositionDateSummary.setClosedProfits(closedProfitList);

        return statClosedPositionDateSummary;
    }

    /**
     * 查询
     */
    public List<StatClosedPositionSummary> queryStatClosedPositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        return new DBQueryHelper<List<StatClosedPositionSummary>>(PositionStatisDB.Global()) {
            @Override
            protected List<StatClosedPositionSummary> onQuery(Connection connection) throws Exception {
                List<StatClosedPositionSummary> statClosedPositionSummaryList = new StatClosedPositionSummaryTable(connection).query(subAccountId, targetKey, targetType);

                // 读取平仓收益
                StatSummaryClosedProfitTable statSummaryClosedProfitTable = new StatSummaryClosedProfitTable(connection);
                for (StatClosedPositionSummary statClosedPositionSummary : statClosedPositionSummaryList) {
                    List<ClosedProfit> closedProfitList = statSummaryClosedProfitTable.query(statClosedPositionSummary.getClosedId());
                    statClosedPositionSummary.setClosedProfits(closedProfitList);
                }

                return statClosedPositionSummaryList;
            }
        }.query();
    }

    /**
     * 查找平仓汇总表中第一项
     */
    public StatClosedPositionSummary findFirstStatClosedPositionSummary() throws ErrorInfo {
        return new DBQueryHelper<StatClosedPositionSummary>(PositionStatisDB.Global()) {
            @Override
            protected StatClosedPositionSummary onQuery(Connection connection) throws Exception {
                return new StatClosedPositionSummaryTable(connection).queryFirstItem();
            }
        }.query();
    }

    /**
     * 删除平仓数据（归档时调用）
     * 1 平仓汇总
     * 2 平仓明细
     */
    public void removeClosedPosition(Connection conn, ArchiveItemData archiveItemData) throws SQLException {
        // 删除平仓汇总
        new StatClosedPositionSummaryTable(conn).delete(archiveItemData.getStatClosedPositionSummary().getClosedId());
        // 删除平仓明细
        new StatClosedPositionItemTable(conn).delete(archiveItemData.getStatClosedPositionSummary().getClosedId());
        //  删除平仓小单元
        StatClosedUnitTable statClosedUnitTable = new StatClosedUnitTable(conn);
        for (ClosedPositionData closedPositionData : archiveItemData.getClosedPositionDataList()) {
            statClosedUnitTable.deleteStatClosedUnit(closedPositionData.getClosedPositionItem().getClosedItemId());
        }
    }
}
