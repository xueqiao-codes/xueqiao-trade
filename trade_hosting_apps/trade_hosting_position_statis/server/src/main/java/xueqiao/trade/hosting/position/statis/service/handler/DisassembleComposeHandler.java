package xueqiao.trade.hosting.position.statis.service.handler;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraph;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraphManager;
import xueqiao.trade.hosting.position.statis.service.bean.ComposeGraphLegData;
import xueqiao.trade.hosting.position.statis.service.bean.DisassembledData;
import xueqiao.trade.hosting.position.statis.service.bean.PositionData;
import xueqiao.trade.hosting.position.statis.service.report.StatAssert;
import xueqiao.trade.hosting.position.statis.service.util.ComposeUtil;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;

import java.math.BigDecimal;
import java.util.*;

public class DisassembleComposeHandler {

    public void verify(DisassembleComposePositionReq disassembleComposePositionReq) throws ErrorInfo {
        ParameterChecker.checkNotNull(disassembleComposePositionReq, "disassembleComposePositionReq");
        ParameterChecker.check(disassembleComposePositionReq.getSubAccountId() > 0, "subAccountId should more than zero");
        ParameterChecker.check(StringUtils.isNotBlank(disassembleComposePositionReq.getTargetKey()), "TargetKey should not be blank");
        ParameterChecker.check(disassembleComposePositionReq.isSetTargetType(), "targetType should be set");
        ParameterChecker.check(disassembleComposePositionReq.getPositionItemDataListSize() > 0, "positionItemDataList should not be null or empty");
        for (PositionItemData positionItemData : disassembleComposePositionReq.getPositionItemDataList()) {
            ParameterChecker.check(positionItemData.getPositionItemId() > 0, "positionItemId should more than zero");
            ParameterChecker.check(positionItemData.getQuantity() > 0, "quantity should more than zero");
        }
    }

    /**
     * ???????????????????????? (???????????????????????? ??? ?????????????????????)
     *
     * @param statPositionItem     ????????????????????????
     * @param disassembledQuantity ?????????????????????????????????
     */
    public DisassembledData getDisassemblePositionDataList(StatPositionItem statPositionItem, int disassembledQuantity) throws ErrorInfo {

        StatAssert.checkParam(statPositionItem.getTargetType() == HostingXQTargetType.COMPOSE_TARGET, "can not disassemble contract");
        StatAssert.checkParam(statPositionItem.getQuantity() >= disassembledQuantity, "disassembledQuantity can not large than position quantity");

        // ??????????????????????????????
        List<StatPositionUnit> statPositionUnitList = new StatPositionHandler().getStatPositionUnitList(statPositionItem.getPositionItemId());
        StatAssert.doAssert(statPositionUnitList != null && statPositionUnitList.size() > 0, "unit list is empty.  positionItemId : " + statPositionItem.getPositionItemId());

        // ??????????????????????????????????????????????????????????????????
        statPositionUnitList.sort(new Comparator<StatPositionUnit>() {
            @Override
            public int compare(StatPositionUnit o1, StatPositionUnit o2) {
                if (o1.getSourceDataTimestampMs() > o2.getSourceDataTimestampMs()) {
                    return 1;
                } else if (o1.getSourceDataTimestampMs() < o2.getSourceDataTimestampMs()) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        /*
         * ??????????????????????????????
         * */
        Map<Long, ComposeGraphLegData> statComposeGraphMap = new HostingComposeHandler().getComposeGraphMap(statPositionItem.getTargetKey());

        DisassembledData disassembledData = new DisassembledData();
        List<PositionData> positionDataList = new ArrayList<>();

        /*
         * ???????????????????????????
         * */
        PositionData originalComposeData = new PositionData();
        originalComposeData.setStatPositionItem(statPositionItem);
        originalComposeData.setStatPositionUnitList(statPositionUnitList);
        disassembledData.setOriginalComposeData(originalComposeData);

        if (disassembledQuantity == statPositionItem.getQuantity()) {
            /* ???????????? */
            disassembledData.setDisassembleComposeData(null);

            for (StatPositionUnit statPositionUnit : statPositionUnitList) {
                PositionData positionData = convertUnitToPosition(statPositionItem, statPositionUnit, statPositionUnit.getUnitQuantity());
                positionDataList.add(positionData);
            }
        } else {
            /* ???????????? */
            PositionData disassembleComposeData = new PositionData();
            StatPositionItem disassembleComposePositionItem = getDisassembledStatPositionItem(statPositionItem, statPositionItem.getQuantity() - disassembledQuantity);
            List<StatPositionUnit> disassembleComposePositionUnitList = new ArrayList<>();

            /*
             * ?????????????????????????????????map
             * */
            Map<Long, Integer> disassembledLegQuantityMap = new HashMap<>();

            Integer alreadyDisassembledLegQuantity = 0;
            Integer composeLegQuantity = 0;
            int disassembledLegQuota = 0;
            for (StatPositionUnit statPositionUnit : statPositionUnitList) {
                /*
                 * ??????????????????????????????????????????????????????
                 * */
                composeLegQuantity = statComposeGraphMap.get(statPositionUnit.getSledContractId()).getQuantity();
                StatAssert.doAssert(composeLegQuantity != null, "contract (contractId: " + statPositionUnit.getSledContractId() + ") not found in composeGraph");

                disassembledLegQuota = composeLegQuantity * disassembledQuantity;

                /*
                 * ????????????????????????????????????
                 * */
                alreadyDisassembledLegQuantity = disassembledLegQuantityMap.get(statPositionUnit.getSledContractId());
                if (alreadyDisassembledLegQuantity == null) {
                    alreadyDisassembledLegQuantity = 0;
                }

                int disassembledLegQuantityOffset = disassembledLegQuota - alreadyDisassembledLegQuantity;
                if (disassembledLegQuantityOffset > 0) {
                    /* ?????????????????????????????????????????????????????????????????? */
                    if (disassembledLegQuantityOffset >= statPositionUnit.getUnitQuantity()) {
                        /* ???????????????  ??????  ????????? */

                        // ?????????????????????
                        alreadyDisassembledLegQuantity += statPositionUnit.getUnitQuantity();
                        disassembledLegQuantityMap.put(statPositionUnit.getSledContractId(), alreadyDisassembledLegQuantity);

                        // ???????????????????????????
                        PositionData positionData = convertUnitToPosition(statPositionItem, statPositionUnit, statPositionUnit.getUnitQuantity());
                        positionDataList.add(positionData);

                        StatPositionUnit disassembledComposeStatPositionUnit = getDisassembledStatPositionUnit(statPositionUnit, 0);
                        disassembleComposePositionUnitList.add(disassembledComposeStatPositionUnit);

                    } else {
                        /* ???????????????  ??????  ????????? */
                        // ?????????????????????
                        alreadyDisassembledLegQuantity += disassembledLegQuantityOffset;
                        disassembledLegQuantityMap.put(statPositionUnit.getSledContractId(), alreadyDisassembledLegQuantity);

                        // ???????????????????????????
                        PositionData positionData = convertUnitToPosition(statPositionItem, statPositionUnit, disassembledLegQuantityOffset);
                        positionDataList.add(positionData);

                        // ?????????????????????????????? ??? ????????????????????????????????????????????????
                        StatPositionUnit disassembledComposeStatPositionUnit = getDisassembledStatPositionUnit(statPositionUnit, statPositionUnit.getUnitQuantity() - disassembledLegQuantityOffset);
                        disassembleComposePositionUnitList.add(disassembledComposeStatPositionUnit);
                    }

                } else {
                    /* ????????????????????????????????????????????????????????? */
                    disassembleComposePositionUnitList.add(statPositionUnit);
                }
            }

            /*
             *  20190315
             *  ????????????????????????????????????
             * */
            Map<Long, Double> composeLegTotalPriceMap = new HashMap<>();
            if (disassembleComposePositionUnitList != null) {
                Double tempLegTotalPrice;
                BigDecimal tempLegPriceBigDecimal;
                for (StatPositionUnit positionUnit : disassembleComposePositionUnitList) {
                    tempLegTotalPrice = composeLegTotalPriceMap.get(positionUnit.getSledContractId());
                    if (tempLegTotalPrice == null) {
                        tempLegTotalPrice = new Double(0);
                    }
                    tempLegPriceBigDecimal = new BigDecimal(positionUnit.getUnitPrice());
                    tempLegPriceBigDecimal = tempLegPriceBigDecimal.multiply(new BigDecimal(positionUnit.getUnitQuantity()));
                    tempLegTotalPrice += tempLegPriceBigDecimal.doubleValue();
                    composeLegTotalPriceMap.put(positionUnit.getSledContractId(), tempLegTotalPrice);
                }
            }
            // ??????????????????
            StatComposeGraph composeGraph = StatComposeGraphManager.getInstance().getComposeGraphDirect(Long.valueOf(statPositionItem.getTargetKey()));
            Double composePrice = ComposeUtil.calculateComposePrice(composeGraph, composeLegTotalPriceMap, disassembleComposePositionItem.getQuantity());
            disassembleComposePositionItem.setPrice(composePrice);
            /*
             * 20190315 fix end
             * */
            // ??????????????????????????????
            disassembleComposeData.setStatPositionItem(disassembleComposePositionItem);
            disassembleComposeData.setStatPositionUnitList(disassembleComposePositionUnitList);
            disassembledData.setDisassembleComposeData(disassembleComposeData);
        }
        // ??????????????????????????????
        disassembledData.setDisassembleContractDataList(positionDataList);

        checkDisassembledData(disassembledData, statComposeGraphMap);
        return disassembledData;
    }

    /**
     * ????????????????????????
     */
    private PositionData convertUnitToPosition(StatPositionItem statPositionItem, StatPositionUnit statPositionUnit, int quantity) throws ErrorInfo {

        StatAssert.checkParam(quantity <= statPositionUnit.getUnitQuantity(), "quantity should not larger than quantity of unit");

        PositionData positionData = new PositionData();
        StatPositionItem positionItem = new StatPositionItem();
        List<StatPositionUnit> positionUnitList = new ArrayList<>();

        // ????????????
        positionItem.setPositionItemId(AppConfig.getStatPositionItemId());
        positionItem.setSubAccountId(statPositionItem.getSubAccountId());
        positionItem.setTargetKey(String.valueOf(statPositionUnit.getSledContractId()));
        positionItem.setTargetType(HostingXQTargetType.CONTRACT_TARGET);
        positionItem.setPrice(statPositionUnit.getUnitPrice());
        /*
         * ??????????????????????????????????????????????????????
         * */
        positionItem.setQuantity(quantity);

        /*
         * ???????????????????????????????????????????????????
         * ???????????????????????????????????????????????????????????????
         * */
        positionItem.setDirection(statPositionUnit.getDirection());

        StatDataSource StatDataSource = new StatDataSource();
        StatDataSource.setSourceDataTimestampMs(statPositionUnit.getSourceDataTimestampMs());
        StatDataSource.setSourceDataChannel(DataSourceChannel.FROM_COMPOSE_DISASSEMBLY);
//        if (statPositionItem.getSource().getSourceDataChannel() == DataSourceChannel.FROM_XQ_COMPOSE_TRADE) {
//            StatDataSource.setSourceDataChannel(DataSourceChannel.FROM_COMPOSE_TRADE_DISASSEMBLY);
//        } else if (statPositionItem.getSource().getSourceDataChannel() == DataSourceChannel.FROM_COMPOSE_CONSTRUCTION) {
//            StatDataSource.setSourceDataChannel(DataSourceChannel.FROM_COMPOSE_CONSTRUCTION_DISASSEMBLY);
//        } else {
//
//        }
        positionItem.setSource(StatDataSource);

        // ???????????????
        StatPositionUnit positionUnit = new StatPositionUnit();
        positionUnit.setUnitId(AppConfig.getStatPositionUnitId());
        positionUnit.setPositionItemId(positionItem.getPositionItemId());
        positionUnit.setSledContractId(statPositionUnit.sledContractId);
        positionUnit.setUnitPrice(statPositionUnit.getUnitPrice());
        /*
         * ??????????????????
         * */
        positionUnit.setUnitQuantity(quantity);
        /*
         * ?????????????????????item??????????????????????????????????????????????????????????????????
         * */
        positionUnit.setDirection(positionItem.getDirection());
        positionUnit.setSourceDataTimestampMs(statPositionUnit.getSourceDataTimestampMs());

        /*
        * unit more fields
        * */
        ExternalDataSource dataSource = new ExternalDataSource();
        positionUnit.setSubAccountId(positionItem.getSubAccountId());
        positionUnit.setTargetKey(positionItem.getTargetKey());
        positionUnit.setTargetType(positionItem.getTargetType());
        dataSource.setSourceId(statPositionUnit.getSource().getSourceId());
        dataSource.setSourceType(statPositionUnit.getSource().getSourceType());
        positionUnit.setSource(dataSource);

        positionUnitList.add(positionUnit);

        positionData.setStatPositionItem(positionItem);
        positionData.setStatPositionUnitList(positionUnitList);

        return positionData;
    }

    /**
     * ?????????????????????????????????????????????????????????
     *
     * @param originalStatPositionItem    ??????????????????
     * @param disassembledComposeQuantity ???????????????????????????
     * @return ??????????????????
     */
    private StatPositionItem getDisassembledStatPositionItem(StatPositionItem originalStatPositionItem, int disassembledComposeQuantity) {
        StatPositionItem disassembledComposePositionItem = new StatPositionItem();
        disassembledComposePositionItem.setPositionItemId(originalStatPositionItem.getPositionItemId());
        disassembledComposePositionItem.setSubAccountId(originalStatPositionItem.getSubAccountId());
        disassembledComposePositionItem.setTargetKey(originalStatPositionItem.getTargetKey());
        disassembledComposePositionItem.setTargetType(originalStatPositionItem.getTargetType());
        disassembledComposePositionItem.setPrice(originalStatPositionItem.getPrice());
        disassembledComposePositionItem.setQuantity(disassembledComposeQuantity);
        disassembledComposePositionItem.setDirection(originalStatPositionItem.getDirection());
        disassembledComposePositionItem.setCreateTimestampMs(originalStatPositionItem.getCreateTimestampMs());

        return disassembledComposePositionItem;
    }

    /**
     * ?????????????????????????????????
     *
     * @param originalStatPositionUnit        ?????????????????????
     * @param disassembledComposeUnitQuantity ??????????????????????????????
     * @return ??????????????????????????????
     */
    private StatPositionUnit getDisassembledStatPositionUnit(StatPositionUnit originalStatPositionUnit, int disassembledComposeUnitQuantity) {
        StatPositionUnit disassembledComposeStatPositionUnit = new StatPositionUnit();
        disassembledComposeStatPositionUnit.setUnitId(originalStatPositionUnit.getUnitId());
        disassembledComposeStatPositionUnit.setPositionItemId(originalStatPositionUnit.getPositionItemId());
        disassembledComposeStatPositionUnit.setSledContractId(originalStatPositionUnit.getSledContractId());
        disassembledComposeStatPositionUnit.setUnitPrice(originalStatPositionUnit.getUnitPrice());
        disassembledComposeStatPositionUnit.setUnitQuantity(disassembledComposeUnitQuantity);
        disassembledComposeStatPositionUnit.setDirection(originalStatPositionUnit.getDirection());
        disassembledComposeStatPositionUnit.setSourceDataTimestampMs(originalStatPositionUnit.getSourceDataTimestampMs());

        /*
        * more fields
        * */
        ExternalDataSource dataSource = new ExternalDataSource();
        disassembledComposeStatPositionUnit.setSubAccountId(originalStatPositionUnit.getSubAccountId());
        disassembledComposeStatPositionUnit.setTargetKey(originalStatPositionUnit.getTargetKey());
        disassembledComposeStatPositionUnit.setTargetType(originalStatPositionUnit.getTargetType());
        dataSource.setSourceId(originalStatPositionUnit.getSource().getSourceId());
        dataSource.setSourceType(originalStatPositionUnit.getSource().getSourceType());
        disassembledComposeStatPositionUnit.setSource(dataSource);

        return disassembledComposeStatPositionUnit;
    }

    /**
     * ??????????????????????????????????????????
     * ???????????????????????????????????????????????????
     * ?????????????????????????????????????????????
     *
     * @param disassembledData          ????????????
     * @param composeGraphLegDataMap ?????????????????????????????????
     */
    private void checkDisassembledData(DisassembledData disassembledData, Map<Long, ComposeGraphLegData> composeGraphLegDataMap) throws ErrorInfo {
        Map<Long, Integer> originalUnitQuantityMap = new HashMap<>();
        Map<Long, Integer> disassembledUnitQuantityMap = new HashMap<>();
        /*
         * ??????????????????
         * */
        if (disassembledData.getDisassembleComposeData() != null) {
            Map<Long, Integer> composeUnitQuantityMap = new HashMap<>();
            Integer unitQuantity;
            for (StatPositionUnit unit : disassembledData.getDisassembleComposeData().getStatPositionUnitList()) {
                unitQuantity = composeUnitQuantityMap.get(unit.getSledContractId());
                if (unitQuantity == null) {
                    unitQuantity = 0;
                }
                if (unit.getDirection() == StatDirection.STAT_BUY) {
                    unitQuantity += unit.getUnitQuantity();
                } else {
                    unitQuantity -= unit.getUnitQuantity();
                }
                composeUnitQuantityMap.put(unit.getSledContractId(), unitQuantity);
                disassembledUnitQuantityMap.put(unit.getSledContractId(), unitQuantity);
            }
            for (Map.Entry<Long, Integer> composeUnitQuantityEntry : composeUnitQuantityMap.entrySet()) {
                ComposeGraphLegData composeGraphLegData = composeGraphLegDataMap.get(composeUnitQuantityEntry.getKey());
                int expectedCompoaseUnitQuantity = disassembledData.getDisassembleComposeData().getStatPositionItem().getQuantity() * composeGraphLegData.getQuantity();
                if (disassembledData.getDisassembleComposeData().getStatPositionItem().getDirection() != composeGraphLegData.getDirection()) {
                    expectedCompoaseUnitQuantity = -1 * expectedCompoaseUnitQuantity;
                }
//                AppLog.i("DiassembleComposeHandler ---- checkDisassembledData ---- statComposeLegQuantityMap : " + new Gson().toJson(composeGraphLegData) + ", composeUnitQuantityEntry.getKey() : " + composeUnitQuantityEntry.getKey());
//                AppLog.i("DiassembleComposeHandler ---- checkDisassembledData ---- positionItem.quantity : " + disassembledData.getDisassembleComposeData().getStatPositionItem().getQuantity()
//                        + ", composeLegQuantity : " + composeGraphLegDataMap.get(composeUnitQuantityEntry.getKey()).toString()
//                        + ", direction: " + disassembledData.getDisassembleComposeData().getStatPositionItem().getDirection().name()
//                        + ", composeUnitQuantityEntry.getKey() : " + composeUnitQuantityEntry.getKey()
//                        + ", expectedCompoaseUnitQuantity : " + expectedCompoaseUnitQuantity
//                        + ", composeUnitQuantityEntry.getValue() : " + composeUnitQuantityEntry.getValue());
                StatAssert.doAssert(expectedCompoaseUnitQuantity == composeUnitQuantityEntry.getValue(), "unit quantity not match with compose");
            }
        }

        /*
         * ???????????????????????????
         * */
        Integer unitQuantity;
        for (PositionData positionData : disassembledData.getDisassembleContractDataList()) {
            long sledContractId = Long.valueOf(positionData.getStatPositionItem().getTargetKey());
            unitQuantity = disassembledUnitQuantityMap.get(sledContractId);
            if (unitQuantity == null) {
                unitQuantity = 0;
            }
            if (positionData.getStatPositionItem().getDirection() == StatDirection.STAT_BUY) {
                unitQuantity += positionData.getStatPositionItem().getQuantity();
            } else {
                unitQuantity -= positionData.getStatPositionItem().getQuantity();
            }
            disassembledUnitQuantityMap.put(sledContractId, unitQuantity);
        }
        for (StatPositionUnit unit : disassembledData.getOriginalComposeData().getStatPositionUnitList()) {
            unitQuantity = originalUnitQuantityMap.get(unit.getSledContractId());
            if (unitQuantity == null) {
                unitQuantity = 0;
            }
            if (unit.getDirection() == StatDirection.STAT_BUY) {
                unitQuantity += unit.getUnitQuantity();
            } else {
                unitQuantity -= unit.getUnitQuantity();
            }
            originalUnitQuantityMap.put(unit.getSledContractId(), unitQuantity);
        }
        for (Map.Entry<Long, Integer> originalUnitQuantityEntry : originalUnitQuantityMap.entrySet()) {
            Integer quantity = disassembledUnitQuantityMap.get(originalUnitQuantityEntry.getKey());
            StatAssert.doAssert(quantity != null && originalUnitQuantityEntry.getValue() == quantity, "unit quantity after disassembly not the same as before");
        }
    }

    /**
     * ??????????????????
     * ?????????????????????null ??? quantity == 0
     */
    public void disassembleComposePosition(DisassembledData disassembledData) throws ErrorInfo {

        new DBTransactionHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                StatPositionHandler statPositionHandler = new StatPositionHandler();
                // ?????????????????????
                if (disassembledData.getDisassembleComposeData() != null) {
                    /*
                    *  ????????????????????????
                    *  ????????????????????????0???????????????????????????
                    * */
                    statPositionHandler.updatePositionData(getConnection(), disassembledData.getDisassembleComposeData().getStatPositionItem(), disassembledData.getDisassembleComposeData().getStatPositionUnitList());
                } else {
                    // ????????????????????????????????????????????????
                    statPositionHandler.removePosition(getConnection(), disassembledData.getOriginalComposeData().getStatPositionItem().getPositionItemId());
                }

                // ????????????????????????
                for (PositionData positionData : disassembledData.getDisassembleContractDataList()) {
                    statPositionHandler.addPositionData(getConnection(), positionData.getStatPositionItem(), positionData.getStatPositionUnitList());
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
