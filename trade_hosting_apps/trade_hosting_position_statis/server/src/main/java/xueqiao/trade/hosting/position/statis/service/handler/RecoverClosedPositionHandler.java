package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.service.bean.*;
import xueqiao.trade.hosting.position.statis.service.report.StatAssert;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecoverClosedPositionHandler extends BaseClosedPositionHandler {

    public RecoveryClosedData getRecoveryClosedData(long closedId) throws ErrorInfo {
        StatPositionHandler statPositionHandler = new StatPositionHandler();

        RecoveryClosedData recoveryClosedData = new RecoveryClosedData();
        recoveryClosedData.setClosedId(closedId);

        List<StatClosedPositionItem> statClosedPositionItemList = queryStatClosedPositionItemList(closedId);

        ClosedPositionData closedPositionData;
        List<StatClosedUnit> closedUnitList;
        RecoveryPositionData recoveryPositionData;
        List<StatPositionUnit> positionUnitList;
        PositionData positionData;
        for (StatClosedPositionItem closedPositionItem : statClosedPositionItemList) {

            // 获取待恢复的平仓数据
            closedUnitList = queryClosedUnitList(closedPositionItem.getClosedItemId());

            closedPositionData = new ClosedPositionData();
            closedPositionData.setClosedPositionItem(closedPositionItem);
            closedPositionData.setClosedUnitList(closedUnitList);

            recoveryClosedData.addtoClosedPositionDataList(closedPositionData);

            // 计算恢复后的持仓数据
            recoveryPositionData = new RecoveryPositionData();

            // 原持仓数据
            StatPositionItem positionItem = statPositionHandler.getStatPositionItem(closedPositionItem.getPositionItemId());
//            List<StatPositionUnit> currentPositionUnitList = statPositionHandler.getStatPositionUnitList(positionItem.getPositionItemId());

            positionData = new PositionData();
            positionData.setStatPositionItem(positionItem);
//            positionData.setStatPositionUnitList(currentPositionUnitList);
            recoveryClosedData.addtoOriginalPositionDataList(positionData);

            if (positionItem == null) {
                /* 没有对应的原持仓，直接恢复 */
                // 持仓项为空，小单元也有空( 给一个空list，避免下面流程出现空指针 )
                positionData.setStatPositionUnitList(new ArrayList<>());

                positionItem  = getStatPositionItem(closedPositionItem);
                positionUnitList = getStatPositionUnitList(closedUnitList);

                recoveryPositionData.setStatPositionItem(positionItem);
                recoveryPositionData.setStatPositionUnitList(positionUnitList);
                recoveryPositionData.setNeedCombine(false);
            } else {
                /* 有对应的原持仓，与原持仓合并 */
                // 原持仓的小单元
                List<StatPositionUnit> currentPositionUnitList = statPositionHandler.getStatPositionUnitList(positionItem.getPositionItemId());
                positionData.setStatPositionUnitList(currentPositionUnitList);

                positionItem  = getStatPositionItem(closedPositionItem, positionItem.getQuantity());
                positionUnitList = getStatPositionUnitList(closedUnitList, currentPositionUnitList);

                recoveryPositionData.setStatPositionItem(positionItem);
                recoveryPositionData.setStatPositionUnitList(positionUnitList);
                recoveryPositionData.setNeedCombine(true);
            }
            recoveryClosedData.addtoPositionDataList(recoveryPositionData);
        }

        /*
        * 校验数据
        * */
        checkRecoveryClosedData(recoveryClosedData);

        return recoveryClosedData;
    }

    /**
     * 恢复平仓
     */
    public void recoverPosition(RecoveryClosedData recoveryClosedData) throws ErrorInfo {

        new DBTransactionHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                // 恢复持仓
                for (RecoveryPositionData item : recoveryClosedData.getPositionDataList()) {
                    recoverClosedPosition(getConnection(), item);
                }

                // 清除平仓明细
                new StatClosedPositionItemTable(getConnection()).delete(recoveryClosedData.getClosedId());

                // 清除平仓小单元
                StatClosedUnitTable closedUnitTable = new StatClosedUnitTable(getConnection());
                for (ClosedPositionData closedPositionData : recoveryClosedData.getClosedPositionDataList()) {
                    closedUnitTable.deleteStatClosedUnit(closedPositionData.getClosedPositionItem().getClosedItemId());
                }

                // 清除平仓汇总
                new StatClosedPositionSummaryTable(getConnection()).delete(recoveryClosedData.getClosedId());

                // 清除平仓收益
                new StatSummaryClosedProfitTable(getConnection()).delete(recoveryClosedData.getClosedId());
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    /**
     * 恢复平仓
     */
    public void recoverClosedPosition(Connection conn, RecoveryPositionData recoveryPositionData) throws SQLException {
        StatPositionItemTable statPositionItemTable = new StatPositionItemTable(conn);
        StatPositionUnitTable statPositionUnitTable = new StatPositionUnitTable(conn);
        if (recoveryPositionData.isNeedCombine()) {
            statPositionItemTable.updateStatPositionItemQuantity(recoveryPositionData.getStatPositionItem().getPositionItemId(), recoveryPositionData.getStatPositionItem().getQuantity());
            for (StatPositionUnit unit : recoveryPositionData.getStatPositionUnitList()) {
                statPositionUnitTable.updateStatPositionUnitQuantity(unit.getUnitId(), unit.getUnitQuantity());
            }
        } else {
            statPositionItemTable.insert(recoveryPositionData.getStatPositionItem());
            for (StatPositionUnit unit : recoveryPositionData.getStatPositionUnitList()) {
                statPositionUnitTable.insert(unit);
            }
        }
    }

    private void checkRecoveryClosedData(RecoveryClosedData recoveryClosedData) throws ErrorInfo {
        /*
        * 验证平仓的数据是否平的
        * */
        Map<String, Integer> closedPositionQuantityMap = new HashMap<>();
        Map<Long, Integer> closedUnitQuantityMap = new HashMap<>();
        Integer tempClosedPositionQuantity;
        Integer tempClosedUnitQuantity;
        for (ClosedPositionData closedPositionData : recoveryClosedData.getClosedPositionDataList()) {
            /*
            * 计算平仓项的数量
            * */
            tempClosedPositionQuantity = closedPositionQuantityMap.get(closedPositionData.getClosedPositionItem().getTargetKey());
            if (tempClosedPositionQuantity == null) {
                tempClosedPositionQuantity = 0;
            }
            if (closedPositionData.getClosedPositionItem().getDirection() == StatDirection.STAT_BUY) {
                tempClosedPositionQuantity += closedPositionData.getClosedPositionItem().getClosedQuantity();
            } else {
                tempClosedPositionQuantity -= closedPositionData.getClosedPositionItem().getClosedQuantity();
            }
            closedPositionQuantityMap.put(closedPositionData.getClosedPositionItem().getTargetKey(), tempClosedPositionQuantity);

            /*
            * 计算平仓小单位的数量
            * */
            for (StatClosedUnit closedUnit : closedPositionData.getClosedUnitList()) {
                tempClosedUnitQuantity = closedUnitQuantityMap.get(closedUnit.getSledContractId());
                if (tempClosedUnitQuantity == null) {
                    tempClosedUnitQuantity = 0;
                }
                if (closedUnit.getDirection() == StatDirection.STAT_BUY) {
                    tempClosedUnitQuantity += closedUnit.getUnitQuantity();
                } else {
                    tempClosedUnitQuantity -= closedUnit.getUnitQuantity();
                }
                closedUnitQuantityMap.put(closedUnit.getSledContractId(), tempClosedUnitQuantity);
            }
        }
        for (Map.Entry<String, Integer> closedPositionQuantityEntry : closedPositionQuantityMap.entrySet()) {
            StatAssert.doAssert(closedPositionQuantityEntry.getValue() == 0, "net position is not zero");
        }
        for (Map.Entry<Long, Integer> closedUnitQuantityEntry : closedUnitQuantityMap.entrySet()) {
            StatAssert.doAssert(closedUnitQuantityEntry.getValue() == 0, "net unit quantity is not zero");
        }

        /**
         * 对比原持仓数据 与 恢复后的持仓数据
         * */
        Map<Long, Integer> originalPositionUnitQuantityMap = new HashMap<>();
        Map<Long, Integer> recoveryPositionUnitQuantityMap = new HashMap<>();
        Integer tempQuantity;
        /*
         * 计算原数据各腿的净仓
         * */
        for (PositionData originalPositionData : recoveryClosedData.getOriginalPositionDataList()) {
            for (StatPositionUnit unit : originalPositionData.getStatPositionUnitList()) {
                tempQuantity = originalPositionUnitQuantityMap.get(unit.getSledContractId());
                if (tempQuantity == null) {
                    tempQuantity = 0;
                }
                if (unit.getDirection() == StatDirection.STAT_BUY) {
                    tempQuantity += unit.getUnitQuantity();
                } else {
                    tempQuantity -= unit.getUnitQuantity();
                }
                originalPositionUnitQuantityMap.put(unit.getSledContractId(), tempQuantity);
            }
        }
        /*
         * 计算持续持仓数据各腿的净仓
         * */
        for (PositionData continuedPositionData : recoveryClosedData.getPositionDataList()) {
            for (StatPositionUnit unit : continuedPositionData.getStatPositionUnitList()) {
                tempQuantity = recoveryPositionUnitQuantityMap.get(unit.getSledContractId());
                if (tempQuantity == null) {
                    tempQuantity = 0;
                }
                if (unit.getDirection() == StatDirection.STAT_BUY) {
                    tempQuantity += unit.getUnitQuantity();
                } else {
                    tempQuantity -= unit.getUnitQuantity();
                }
                recoveryPositionUnitQuantityMap.put(unit.getSledContractId(), tempQuantity);
            }
        }
        /*
         * 对比前后净仓是否一致
         * */
        for (Map.Entry<Long, Integer> originalPositionUnitQuantityEntry : originalPositionUnitQuantityMap.entrySet()) {
            tempQuantity = recoveryPositionUnitQuantityMap.get(originalPositionUnitQuantityEntry.getKey());
            if (tempQuantity == null) {
                tempQuantity = 0;
            }
            StatAssert.doAssert(tempQuantity != null && originalPositionUnitQuantityEntry.getValue().equals(tempQuantity), "unit quantity after recover not the same as before");
        }
    }

    private StatPositionItem getStatPositionItem(StatClosedPositionItem closedPositionItem) {
        StatPositionItem positionItem = new StatPositionItem();

        positionItem.setPositionItemId(closedPositionItem.getPositionItemId());
        positionItem.setSubAccountId(closedPositionItem.getSubAccountId());
        positionItem.setTargetKey(closedPositionItem.getTargetKey());
        positionItem.setTargetType(closedPositionItem.getTargetType());
        positionItem.setPrice(closedPositionItem.getPrice());
        positionItem.setQuantity(closedPositionItem.getClosedQuantity());
        positionItem.setDirection(closedPositionItem.getDirection());
        positionItem.setSource(closedPositionItem.getSource());
        positionItem.setCreateTimestampMs(closedPositionItem.getPositionCreateTimestampMs());

        return positionItem;
    }

    private StatPositionItem getStatPositionItem(StatClosedPositionItem closedPositionItem, int currentPositionItemQuantity) {
        StatPositionItem positionItem = new StatPositionItem();

        positionItem.setPositionItemId(closedPositionItem.getPositionItemId());
        positionItem.setSubAccountId(closedPositionItem.getSubAccountId());
        positionItem.setTargetKey(closedPositionItem.getTargetKey());
        positionItem.setTargetType(closedPositionItem.getTargetType());
        positionItem.setPrice(closedPositionItem.getPrice());
        positionItem.setQuantity(closedPositionItem.getClosedQuantity() + currentPositionItemQuantity);
        positionItem.setDirection(closedPositionItem.getDirection());
        positionItem.setSource(closedPositionItem.getSource());
        positionItem.setCreateTimestampMs(closedPositionItem.getPositionCreateTimestampMs());

        return positionItem;
    }

    private StatPositionUnit getStatPositionUnit(StatClosedUnit closedUnit) {
        StatPositionUnit positionUnit = new StatPositionUnit();
        positionUnit.setUnitId(closedUnit.getPositionUnitId());
        positionUnit.setPositionItemId(closedUnit.getPositionItemId());
        positionUnit.setSledContractId(closedUnit.getSledContractId());
        positionUnit.setUnitPrice(closedUnit.getUnitPrice());
        positionUnit.setUnitQuantity(closedUnit.getUnitQuantity());
        positionUnit.setDirection(closedUnit.getDirection());
        positionUnit.setSourceDataTimestampMs(closedUnit.getSourceDataTimestampMs());

        /*
        * more fields
        * */
        ExternalDataSource dataSource = new ExternalDataSource();
        positionUnit.setSubAccountId(closedUnit.getSubAccountId());
        positionUnit.setTargetKey(closedUnit.getTargetKey());
        positionUnit.setTargetType(closedUnit.getTargetType());
        dataSource.setSourceId(closedUnit.getSource().getSourceId());
        dataSource.setSourceType(closedUnit.getSource().getSourceType());
        positionUnit.setSource(dataSource);

        return positionUnit;
    }

    private List<StatPositionUnit> getStatPositionUnitList(List<StatClosedUnit> closedUnitList) {
        List<StatPositionUnit> positionUnitList = new ArrayList<>();
        for (StatClosedUnit closedUnit : closedUnitList) {
            positionUnitList.add(getStatPositionUnit(closedUnit));
        }
        return positionUnitList;
    }

    private StatPositionUnit getStatPositionUnit(StatClosedUnit closedUnit, int currentPositionUnitQuantity) {
        StatPositionUnit positionUnit = new StatPositionUnit();

        positionUnit.setUnitId(closedUnit.getPositionUnitId());
        positionUnit.setPositionItemId(closedUnit.getPositionItemId());
        positionUnit.setSledContractId(closedUnit.getSledContractId());
        positionUnit.setUnitPrice(closedUnit.getUnitPrice());
        positionUnit.setUnitQuantity(closedUnit.getUnitQuantity() + currentPositionUnitQuantity);
        positionUnit.setDirection(closedUnit.getDirection());
        positionUnit.setSourceDataTimestampMs(closedUnit.getSourceDataTimestampMs());

        /*
         * more fields
         * */
        ExternalDataSource dataSource = new ExternalDataSource();
        positionUnit.setSubAccountId(closedUnit.getSubAccountId());
        positionUnit.setTargetKey(closedUnit.getTargetKey());
        positionUnit.setTargetType(closedUnit.getTargetType());
        dataSource.setSourceId(closedUnit.getSource().getSourceId());
        dataSource.setSourceType(closedUnit.getSource().getSourceType());
        positionUnit.setSource(dataSource);

        return positionUnit;
    }

    private List<StatPositionUnit> getStatPositionUnitList(List<StatClosedUnit> closedUnitList, List<StatPositionUnit> CuurentPositionUnitList) {
        List<StatPositionUnit> positionUnitList = new ArrayList<>();
        Map<Long, StatPositionUnit> positionUnitMap = new HashMap<>();
        for (StatPositionUnit unit : CuurentPositionUnitList) {
            positionUnitMap.put(unit.getUnitId(), unit);
        }
        StatPositionUnit tempPositionUnit;
        StatPositionUnit positionUnit;
        for (StatClosedUnit closedUnit : closedUnitList) {
            tempPositionUnit = positionUnitMap.get(closedUnit.getPositionUnitId());
            if (tempPositionUnit != null) {
                positionUnit = getStatPositionUnit(closedUnit, tempPositionUnit.getUnitQuantity());
            } else {
                positionUnit = getStatPositionUnit(closedUnit);
            }
            positionUnitList.add(positionUnit);
        }

        return positionUnitList;
    }

}
