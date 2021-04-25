package xueqiao.trade.hosting.position.statis.service.handler;

import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.events.StatPositionEventType;
import xueqiao.trade.hosting.events.StatPositionSummaryChangedEvent;
import xueqiao.trade.hosting.events.StatPositionSummaryChangedGuardEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.core.calculator.position.summary.StatComposePositionSummaryCalculator;
import xueqiao.trade.hosting.position.statis.core.calculator.position.summary.StatContractPositionSummaryCalculator;
import xueqiao.trade.hosting.position.statis.core.quotation.StatQuotationHelper;
import xueqiao.trade.hosting.position.statis.service.bean.CheckNetPositionReqData;
import xueqiao.trade.hosting.position.statis.service.bean.PositionTargetData;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;
import xueqiao.trade.hosting.position.statis.service.util.BigDecimalUtil;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionItemTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionSummaryTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionUnitTable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class StatPositionHandler {

    /**
     * 根据targetkey查询持仓明细
     */
    public static List<StatPositionItem> queryStatPositionItemList(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        return new DBQueryHelper<List<StatPositionItem>>(PositionStatisDB.Global()) {
            @Override
            protected List<StatPositionItem> onQuery(Connection connection) throws Exception {
                return new StatPositionItemTable(connection).queryStatPositionItemList(subAccountId, targetKey, targetType);
            }
        }.query();
    }

    public Map<Long, Long> calculateNetPositionForContract(CheckNetPositionReqData data) throws ErrorInfo {
        Map<Long, Long> contractNetPositionMap = new HashMap<>();
        Map<Long, Long> tempNetPositionMap = new HashMap<>();

        for (PositionTargetData targetData : data.getPositionTargetDataList()) {
            tempNetPositionMap = calculateNetPositionForContract(data.getSubAccountId(), targetData.getTargetKey(), targetData.getTargetType());
            for (Map.Entry<Long, Long> entrySet : tempNetPositionMap.entrySet()) {
                if (contractNetPositionMap.containsKey(entrySet.getKey())) {
                    contractNetPositionMap.put(entrySet.getKey(), contractNetPositionMap.get(entrySet.getKey()) + entrySet.getValue());
                } else {
                    contractNetPositionMap.put(entrySet.getKey(), entrySet.getValue());
                }
            }
        }
        return contractNetPositionMap;
    }

    /**
     * 计算合约层面的净仓
     * 如果是组合，则计算组合里面的合约的净仓
     * return 净仓
     */
    public Map<Long, Long> calculateNetPositionForContract(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
//		long netPosition = 0;
        Map<Long, Long> contractNetPositionMap = new HashMap<>();

        // 读取库中信息持仓明细信息
        List<StatPositionItem> statPositionItemList = queryStatPositionItemList(subAccountId, targetKey, targetType);

        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), new StringBuilder("calculateNetPositionForContract subAccountId : ")
                    .append(subAccountId).append(", targetKey : ").append(targetKey).toString());
//					.append(", statPositionItemList : ").append(new Gson().toJson(statPositionItemList)).toString());
        }

        if (statPositionItemList != null && statPositionItemList.size() > 0) {
            List<StatPositionUnit> tempPositionUnitList;
            Long tempNetPosition;

            for (StatPositionItem positionItem : statPositionItemList) {
                tempPositionUnitList = getStatPositionUnitList(positionItem.getPositionItemId());

                for (StatPositionUnit positionUnit : tempPositionUnitList) {
                    tempNetPosition = contractNetPositionMap.get(positionUnit.getSledContractId());
                    if (tempNetPosition == null) {
                        tempNetPosition = 0l;
                    }
                    if (positionUnit.getDirection() == StatDirection.STAT_BUY) {
                        tempNetPosition += positionUnit.getUnitQuantity();
                    } else {
                        tempNetPosition -= positionUnit.getUnitQuantity();
                    }
                    contractNetPositionMap.put(positionUnit.getSledContractId(), tempNetPosition);
                }
            }
        }
        return contractNetPositionMap;
    }

    /**
     * 对比两次的争仓
     */
    public void compareNetPositionForContract(String operation, Map<Long, Long> netPositionMap1, Map<Long, Long> netPositionMap2) throws ErrorInfo {

        StringBuilder builder = new StringBuilder();
        if (netPositionMap1.isEmpty() && netPositionMap2.isEmpty()) {
            builder.append("################ compareNetPositionForContract ---- ")
                    .append(operation)
                    .append(" ---- netPositionMap1 and netPositionMap2 is empty ---- OK YYYYYYY ##############");
            AppReport.debug(builder.toString());
            return;
        }
        Set<Long> contractIdList = new HashSet<>();
        if (!netPositionMap1.isEmpty()) {
            for (Map.Entry<Long, Long> netPositionEntry1 : netPositionMap1.entrySet()) {
                contractIdList.add(netPositionEntry1.getKey());
            }
        }
        if (!netPositionMap2.isEmpty()) {
            for (Map.Entry<Long, Long> netPositionEntry2 : netPositionMap2.entrySet()) {
                contractIdList.add(netPositionEntry2.getKey());
            }
        }
        for (long contractId : contractIdList) {
            Long netPosition1 = netPositionMap1.get(contractId);
            Long netPosition2 = netPositionMap2.get(contractId);
            if (netPosition1 == null) {
                netPosition1 = 0l;
            }
            if (netPosition2 == null) {
                netPosition2 = 0l;
            }
            if (!netPosition1.equals(netPosition2)) {
                builder.append("################ compareNetPositionForContract ---- ")
                        .append(operation)
                        .append(" ---- net position not match ---- ERROR NNNNNNNNNNNNNNNNNNNNN");
                AppReport.reportErr(builder.toString());
                throw StatErrorCode.errorCheckNetPosition;
            }
        }

        builder.append("################ compareNetPositionForContract ---- ")
                .append(operation)
                .append(" OK YYYYYYY ##############");
        AppReport.debug(builder.toString());
    }

    /**
     * 计算并更新持仓汇总信息
     * return 净仓
     */
    boolean isSetQuotationListener = false;

    public long calculateAndUpdateStatPositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        // 读取库中信息持仓明细信息
        List<StatPositionItem> statPositionItemList = queryStatPositionItemList(subAccountId, targetKey, targetType);

        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), new StringBuilder("calculateAndUpdateStatPositionSummary subAccountId : ")
                    .append(subAccountId).append(", targetKey : ").append(targetKey).toString());
//				.append(", statPositionItemList : ").append(new Gson().toJson(statPositionItemList)).toString());
        }

        StatPositionSummary statPositionSummary = new StatPositionSummary();

        if (statPositionItemList == null || statPositionItemList.size() < 1) {
            if (AppReport.DEBUG) {
                AppReport.debug(new StringBuilder("StatPositionHandler ## calculateAndUpdateStatPositionSummary -- statPositionItemList is blank(subAccountId: ")
                        .append(subAccountId).append(", targetKey : ")
                        .append(targetKey).append(")").toString());
            }

            statPositionSummary.setTargetKey(targetKey);
            statPositionSummary.setTargetType(targetType);
            statPositionSummary.setSubAccountId(subAccountId);
            statPositionSummary.setLongPosition(0);
            statPositionSummary.setShortPosition(0);
            statPositionSummary.setNetPosition(0);
            statPositionSummary.setPositionAvgPrice(0);
        } else {
            // 计算多头仓量 和 空头仓量
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
            long netPosition = longPosition - shortPosition;
            if (longPosition != 0 || shortPosition != 0) {
                isSetQuotationListener = true;
            }
            BigDecimal totalFee = buyPriceSum.subtract(sellPriceSum);

            if (netPosition != 0) {
                double positionAvgPrice = totalFee.divide(new BigDecimal(netPosition), BigDecimalUtil.BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).doubleValue();
                statPositionSummary.setPositionAvgPrice(positionAvgPrice);
            } else {
                /*
                 * 当净仓为0时，无法按公式计算持仓均价，该情况下，持仓均价无效
                 * 这里以0入库
                 * */
                statPositionSummary.setPositionAvgPrice(0);
            }
            statPositionSummary.setTargetKey(targetKey);
            statPositionSummary.setSubAccountId(subAccountId);
            statPositionSummary.setTargetType(statPositionItemList.get(0).getTargetType());
            statPositionSummary.setLongPosition(longPosition);
            statPositionSummary.setShortPosition(shortPosition);
            statPositionSummary.setNetPosition(netPosition);
        }


        // 写入数据库
        new DBTransactionHelperWithMsg<Void>(PositionStatisDB.Global()) {

            StatPositionSummary statPositionSummaryRet = null;
            StatPositionSummaryTable positionSummaryTable = null;
            StatPositionEventType statPositionEventType = null;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                positionSummaryTable = new StatPositionSummaryTable(getConnection());
                statPositionSummaryRet = positionSummaryTable.queryStatPositionSummary(statPositionSummary.getTargetKey(), statPositionSummary.getTargetType(), statPositionSummary.getSubAccountId());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                /*
                 * 当汇总计算后持仓量（长仓和短仓）为0时，不做删除，依然保存持仓量为0的记录
                 * 到归档时再删除
                 * */
                long currentMillis = System.currentTimeMillis();

                if (statPositionSummaryRet == null) {
                    statPositionEventType = StatPositionEventType.STAT_ITEM_CREATED;
                    statPositionSummary.setCreateTimestampMs(currentMillis);
                    statPositionSummary.setLastmodifyTimestampMs(currentMillis);
                    positionSummaryTable.insert(statPositionSummary);
                } else {
                    statPositionEventType = StatPositionEventType.STAT_ITEM_UPDATED;
                    statPositionSummary.setCreateTimestampMs(statPositionSummaryRet.getCreateTimestampMs());
                    statPositionSummary.setLastmodifyTimestampMs(currentMillis);
//                    if (statPositionSummary.getLongPosition() == 0 && statPositionSummary.getShortPosition() == 0) {
//                        positionSummaryTable.delete(statPositionSummary.getTargetKey(), statPositionSummary.getTargetType(), statPositionSummary.getSubAccountId());
//                    } else {
//                        positionSummaryTable.update(statPositionSummary);
//                    }
                    positionSummaryTable.update(statPositionSummary);
                }

                // 监听新来数据的行情
                if (isSetQuotationListener) {
                    StatQuotationHelper.register(subAccountId, targetKey, targetType);
                } else {
                    StatQuotationHelper.unRegister(subAccountId, targetKey, targetType);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                StatPositionSummaryChangedGuardEvent event = new StatPositionSummaryChangedGuardEvent();
                event.setSubAccountId(statPositionSummary.getSubAccountId());
                event.setTargetKey(statPositionSummary.getTargetKey());
                event.setTargetType(statPositionSummary.getTargetType());
                event.setEventType(statPositionEventType);
                return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(3));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

            @Override
            protected TBase prepareNotifyMessage() {
                StatPositionSummaryChangedEvent event = new StatPositionSummaryChangedEvent();
                event.setSubAccountId(statPositionSummary.getSubAccountId());
                if (statPositionSummary.getNetPosition() == 0) {
                    /*
                     * 净仓为0时，持仓均价无效。
                     * 给到客户端则不设置
                     * */
                    statPositionSummary.unsetPositionAvgPrice();
                }
                event.setStatPositionSummary(statPositionSummary);
                event.setEventCreateTimestampMs(System.currentTimeMillis());
                event.setEventType(statPositionEventType);
                return event;
            }
        }.execute();

        calculatePositionSummaryCache(statPositionSummary.getSubAccountId(), statPositionSummary.getTargetKey(), statPositionSummary.getTargetType());
        // 返回净仓
        return statPositionSummary.getNetPosition();
    }

    private void calculatePositionSummaryCache(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        if (targetType == HostingXQTargetType.COMPOSE_TARGET) {
            new StatComposePositionSummaryCalculator(subAccountId, targetKey, targetType).onCalculate(true);
        } else if (targetType == HostingXQTargetType.CONTRACT_TARGET) {
            new StatContractPositionSummaryCalculator(subAccountId, targetKey, targetType).onCalculate(true);
        } else {
            throw StatErrorCode.errorInvalidTargetType;
        }
    }

    /**
     * 查询持仓汇总信息
     */
    public static StatPositionSummary queryStatPositionSummary(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        return new DBQueryHelper<StatPositionSummary>(PositionStatisDB.Global()) {

            @Override
            protected StatPositionSummary onQuery(Connection connection) throws Exception {
                return new StatPositionSummaryTable(connection).queryStatPositionSummary(targetKey, targetType, subAccountId);
            }
        }.query();
    }

    /**
     * 查询所有持仓汇总信息
     */
    public static List<StatPositionSummary> queryAllStatPositionSummary() throws ErrorInfo {
        return new DBQueryHelper<List<StatPositionSummary>>(PositionStatisDB.Global()) {

            @Override
            protected List<StatPositionSummary> onQuery(Connection connection) throws Exception {
                return new StatPositionSummaryTable(connection).queryAllStatPositionSummary();
            }
        }.query();
    }

    /**
     * 根据ID查询持仓明细项
     */
    public StatPositionItem getStatPositionItem(long positionItemId) throws ErrorInfo {
        return new DBQueryHelper<StatPositionItem>(PositionStatisDB.Global()) {

            @Override
            protected StatPositionItem onQuery(Connection connection) throws Exception {
                return new StatPositionItemTable(connection).queryStatPositionItem(positionItemId);
            }
        }.query();
    }

    /**
     * 添加持仓数据
     * 1 持仓明细
     * 2 持仓小单元
     */
    public static void addPositionData(Connection conn, StatPositionItem statPositionItem, List<StatPositionUnit> statPositionUnitList) throws SQLException, ErrorInfo {
        // 数据较验
        if (statPositionUnitList.size() < 1) {
            throw StatErrorCode.errorAddPositionWithBlankUnit;
        }
        for (StatPositionUnit unit : statPositionUnitList) {
            if (statPositionItem.getPositionItemId() != unit.getPositionItemId()) {
                throw StatErrorCode.errorAddPositionItemIdNotMatch;
            }
        }

        // 1 导入持仓明细项
        new StatPositionItemTable(conn).insert(statPositionItem);

        // 2 导入持仓小单元
        StatPositionUnitTable statPositionUnitTable = new StatPositionUnitTable(conn);
        for (StatPositionUnit statPositionUnit : statPositionUnitList) {
            statPositionUnitTable.insert(statPositionUnit);
        }
    }

    /**
     * 更新持仓数据
     * 1 持仓明细
     * 2 持仓小单元: 如果小单元的数量为0，则删除
     */
    public static void updatePositionData(Connection conn, StatPositionItem statPositionItem, List<StatPositionUnit> statPositionUnitList) throws ErrorInfo, SQLException {
        // 数据较验
        if (statPositionUnitList.size() < 1) {
            throw StatErrorCode.errorAddPositionWithBlankUnit;
        }
        for (StatPositionUnit unit : statPositionUnitList) {
            if (statPositionItem.getPositionItemId() != unit.getPositionItemId()) {
                throw StatErrorCode.errorAddPositionItemIdNotMatch;
            }
        }

        // 1 导入持仓明细项(更新数量及价格)
//        new StatPositionItemTable(conn).updateStatPositionItemQuantity(statPositionItem.getPositionItemId(), statPositionItem.getQuantity());
        new StatPositionItemTable(conn).updateStatPositionItem(statPositionItem.getPositionItemId(), statPositionItem.getQuantity(), statPositionItem.getPrice());

        // 2 导入持仓小单元
        StatPositionUnitTable statPositionUnitTable = new StatPositionUnitTable(conn);
        for (StatPositionUnit statPositionUnit : statPositionUnitList) {
            if (statPositionUnit.getUnitQuantity() == 0) {
                /* 如果数量为0，则删除 */
                statPositionUnitTable.deleteOneUnit(statPositionUnit.getUnitId());
            } else {
                /* 如果数量不为0，则更新 */
                statPositionUnitTable.updateStatPositionUnitQuantity(statPositionUnit.getUnitId(), statPositionUnit.getUnitQuantity());
            }
        }
    }

    /**
     * 更新持仓明细的持仓手数
     */
    public void updateStatPositionItemQuantity(Connection conn, long positionItemId, int updateQuantity) throws SQLException {
        new StatPositionItemTable(conn).updateStatPositionItemQuantity(positionItemId, updateQuantity);
    }

    /**
     * 删除持仓数据
     * 1 持仓明细
     * 2 持仓小单元
     */
    public static void removePosition(Connection conn, long positionItemId) throws SQLException {
        // 删除持仓明细
        new StatPositionItemTable(conn).deleteStatPositionItem(positionItemId);
        // 删除持仓小单元
        new StatPositionUnitTable(conn).deleteStatPositionUnit(positionItemId);
    }

    /**
     * 根据subAccountId, targetKey 和 targetType来删除持仓数据
     * 1 持仓汇总
     * 2 持仓明细
     * 3 持仓小单元
     */
    public static void removePosition(Connection conn, long subAccountId, String targetKey, HostingXQTargetType targetType) throws SQLException {
        // 持仓汇总
        new StatPositionSummaryTable(conn).delete(targetKey, targetType, subAccountId);
        // 删除持仓明细
        new StatPositionItemTable(conn).deleteStatPositionItem(subAccountId, targetKey, targetType);
        // 删除持仓小单元
        new StatPositionUnitTable(conn).deleteStatPositionUnit(subAccountId, targetKey, targetType);
    }

    /**
     * 查询持仓明细项对应的持仓小单元
     */
    List<StatPositionUnit> getStatPositionUnitList(long positionItemId) throws ErrorInfo {
        return new DBQueryHelper<List<StatPositionUnit>>(PositionStatisDB.Global()) {
            @Override
            protected List<StatPositionUnit> onQuery(Connection connection) throws Exception {
                return new StatPositionUnitTable(connection).queryStatPositionUnitList(positionItemId);
            }
        }.query();
    }

    /**
     * 更新持仓明细
     */
    public void updateStatPositionItems(Connection conn, List<StatPositionItem> statPositionItemList) throws SQLException {
        StatPositionItemTable statPositionItemTable = new StatPositionItemTable(conn);
        for (StatPositionItem statPositionItem : statPositionItemList) {
            if (statPositionItem.getQuantity() > 0) {
                statPositionItemTable.updateStatPositionItemQuantity(statPositionItem.getPositionItemId(), statPositionItem.getQuantity());
            } else {
                statPositionItemTable.deleteStatPositionItem(statPositionItem.getPositionItemId());
            }
        }
    }
}
