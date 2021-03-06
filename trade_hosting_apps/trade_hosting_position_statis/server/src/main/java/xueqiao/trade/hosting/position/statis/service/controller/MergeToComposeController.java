package xueqiao.trade.hosting.position.statis.service.controller;

import com.antiy.error_code.ErrorCodeInner;
import com.antiy.error_code.ErrorCodeOuter;
import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraph;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeGraphManager;
import xueqiao.trade.hosting.position.statis.core.cache.compose.StatComposeLeg;
import xueqiao.trade.hosting.position.statis.service.bean.MergeToComposeData;
import xueqiao.trade.hosting.position.statis.service.bean.PositionData;
import xueqiao.trade.hosting.position.statis.service.bean.TargetData;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatAssert;
import xueqiao.trade.hosting.position.statis.service.util.ComposeUtil;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionItemTable;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionUnitTable;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class MergeToComposeController {

    private static final double COMPOSE_PRICE_ERROR_VIATION_TOLERANCE = 0.000001;

    private StatMergeToComposeReq mergeToComposeReq;
    private MergeToComposeData mergeToComposeData;

    /*
     * Map<positionItemId, positionData>
     * */
    private Map<Long, PositionData> originalContractPositionDataMap;
    private Map<Long, Integer> composeLegQuantityMap;
    private StatComposeGraph composeGraph;
    Map<Long, Double> composeLegTotalPriceMap;

    public MergeToComposeController(StatMergeToComposeReq mergeToComposeReq) throws ErrorInfo {
        this.mergeToComposeReq = mergeToComposeReq;
        mergeToComposeData = new MergeToComposeData();
        originalContractPositionDataMap = new HashMap<>();
        composeLegQuantityMap = new HashMap<>();
        composeLegTotalPriceMap = new HashMap<>();

        composeGraph = StatComposeGraphManager.getInstance().getComposeGraphDirect(mergeToComposeReq.getComposeGraphId());
    }

    /**
     * ????????????????????????
     */
    public void validateParams() throws ErrorInfo {
        ParameterChecker.check(mergeToComposeReq != null, "mergeToComposeReq should not be empty");
        ParameterChecker.check(mergeToComposeReq.getSubAccountId() > 0, "invalid subAccountId");
        ParameterChecker.check(mergeToComposeReq.getComposeGraphId() > 0, "invalid subAccountId");
        ParameterChecker.check(mergeToComposeReq.isSetComposePrice(), "composePrice should be set");
        ParameterChecker.check(mergeToComposeReq.getVolume() > 0, "volume should be larger than 0");
        ParameterChecker.check(mergeToComposeReq.isSetDiretion(), "compose direction should be set");
        ParameterChecker.check(mergeToComposeReq.getMergeComposeLegDataList() != null && mergeToComposeReq.getMergeComposeLegDataListSize() > 0, "mergeComposeLegDataList should not be blank");
        for (MergeComposeLegData legData : mergeToComposeReq.getMergeComposeLegDataList()) {
            ParameterChecker.check(legData.isSetSledContractId(), "sledContractId should be set");
            ParameterChecker.check(legData.getQuantity() >= 0, "quantity should be equal to or larger than 0");
            if (legData.getQuantity() > 0) {
                ParameterChecker.check(legData.getPositionItemId() > 0, "positionItemId should be larger than 0 when quantity is larger than 0");
            }
        }

        Set<Long> contractIdSet = composeGraph.getComposeSledContractIdSet();
        StatAssert.doAssert(contractIdSet != null && !contractIdSet.isEmpty(), "fail to fetch compose graph, composeGraphId : " + mergeToComposeReq.getComposeGraphId());

        Map<Long, Integer> contractQuantityMap = new HashMap<>();
        Integer tempQuantity = null;
        StatComposeLeg tempStatComposeLeg;
        for (MergeComposeLegData legData : mergeToComposeReq.getMergeComposeLegDataList()) {

            /*
             * ??????????????????
             * */
            tempStatComposeLeg = composeGraph.getComposeLegQuantityMap().get(legData.getSledContractId());
            if (mergeToComposeReq.getDiretion() == StatDirection.STAT_BUY) {
                ParameterChecker.check(tempStatComposeLeg.getDirection() == legData.getDiretion(), "leg data direction is not match with compose graph");
            } else {
                ParameterChecker.check(tempStatComposeLeg.getDirection() != legData.getDiretion(), "leg data direction is not match with compose graph");
            }

            /*
             * ??????????????????
             * */
            tempQuantity = contractQuantityMap.get(legData.getSledContractId());
            if (tempQuantity == null) {
                tempQuantity = new Integer(0);
            }
            tempQuantity += legData.getQuantity();
            contractQuantityMap.put(legData.getSledContractId(), tempQuantity);
        }

        /*
         * ???????????????????????????????????????????????????
         * */
        for (long contractId : contractIdSet) {
            int composeLegQuantity = getComposeLegQuantity(contractId);
            Integer contractQuantity = contractQuantityMap.get(contractId);
            ParameterChecker.check(contractQuantity != null & contractQuantity == composeLegQuantity, "legData is not match with composeGraph");
        }
    }

    private Integer getComposeLegQuantity(long contractId) {
        Integer composeLegQuantity = composeLegQuantityMap.get(contractId);
        if (composeLegQuantity == null) {
            composeLegQuantity = composeGraph.getComposeLegQuantityMap().get(contractId).getQuantity();
            composeLegQuantity *= mergeToComposeReq.getVolume();
            composeLegQuantityMap.put(contractId, composeLegQuantity);
        }
        return composeLegQuantity;
    }

    /**
     * ???????????????
     */
    private void fetchOriginalContractPositionDataList(Connection conn) throws SQLException, ErrorInfo {
        StatPositionItemTable positionItemTable = new StatPositionItemTable(conn);
        StatPositionUnitTable positionUnitTable = new StatPositionUnitTable(conn);

        Set<Long> positionItemIdSet = new HashSet<>();
        Set<Long> positionUnitIdSet = new HashSet<>();
        PositionData tempPositionData = null;

        /*
         * ????????????????????????????????????
         * */
        for (MergeComposeLegData legData : mergeToComposeReq.getMergeComposeLegDataList()) {
            /*
             * ????????????0???????????????????????????
             * */
            if (legData.getQuantity() > 0) {
                positionItemIdSet.add(legData.getPositionItemId());
            }
        }
        List<StatPositionItem> positionItemList = positionItemTable.queryStatPositionItems(positionItemIdSet, true);
        StatAssert.doAssert(positionItemList != null && positionItemList.size() > 0, "fail to fetch position data from system");
        for (StatPositionItem positionItem : positionItemList) {
            positionUnitIdSet.add(positionItem.getPositionItemId());
            tempPositionData = originalContractPositionDataMap.get(positionItem.getPositionItemId());
            if (tempPositionData == null) {
                tempPositionData = new PositionData();
            }
            tempPositionData.setStatPositionItem(positionItem);
            originalContractPositionDataMap.put(positionItem.getPositionItemId(), tempPositionData);
        }
        List<StatPositionUnit> positionUnitList = positionUnitTable.queryStatPositionUnitList(positionUnitIdSet, true);
        StatAssert.doAssert(positionUnitList != null && positionUnitList.size() > 0, "fail to fetch position unit data from system");
        for (StatPositionUnit positionUnit : positionUnitList) {
            /*
             * ???????????????????????????
             * */
            tempPositionData = originalContractPositionDataMap.get(positionUnit.getPositionItemId());
            StatAssert.doAssert(tempPositionData != null, "fetch original position data error");
            tempPositionData.addToStatPositionUnitList(positionUnit);
            originalContractPositionDataMap.put(positionUnit.getPositionItemId(), tempPositionData);
        }

        for (PositionData positionDataItem : originalContractPositionDataMap.values()) {
            mergeToComposeData.addToOriginalContractPositionDataList(positionDataItem);
        }
    }

    /**
     * ????????????
     */
    public void prepareData(Connection conn) throws ErrorInfo, SQLException {
        fetchOriginalContractPositionDataList(conn);

        PositionData postMergeComposePositionData = new PositionData();
        /*
         * ??????????????????????????????
         * */
        postMergeComposePositionData.setStatPositionItem(getPostMergeComposePositionItem());

        PositionData tempOriginalContractPositionData = null;
        PositionData tempPostMergeContractPositionData = null;

//        Map<Long, Integer> legMergeQuantityQuotaMap = new HashMap<>();
        Integer legMergeQuantityQuota = 0;
        for (MergeComposeLegData legData : mergeToComposeReq.getMergeComposeLegDataList()) {

            /*
             * ??????????????????????????????
             * */
//            legMergeQuantityQuota = legMergeQuantityQuotaMap.get(legData.getSledContractId());
//            AppLog.e("prepareData ########################### legMergeQuantityQuota is null : " + (legMergeQuantityQuota == null));
//            if (legMergeQuantityQuota == null) {
//                legMergeQuantityQuota = composeGraph.getComposeLegQuantityMap().get(legData.getSledContractId()).getQuantity();
//                legMergeQuantityQuota *= mergeToComposeReq.getVolume();
//            }
            legMergeQuantityQuota = legData.getQuantity();
            if (legData.getQuantity() == 0) {
                /*
                 * ????????????????????? (?????????0)
                 * */
                postMergeComposePositionData.addToStatPositionUnitList(getNotTradingPostMergeComposePositionUnit(postMergeComposePositionData.getStatPositionItem(), legData));
            } else {
                tempOriginalContractPositionData = originalContractPositionDataMap.get(legData.getPositionItemId());
                if (legData.getQuantity() == tempOriginalContractPositionData.getStatPositionItem().getQuantity()) {
                    /*
                     * ?????????????????????
                     * */
                    /* ??????????????????????????? */
                    tempPostMergeContractPositionData = new PositionData();
                    tempPostMergeContractPositionData.setStatPositionItem(getPostMergeContractPosition(tempOriginalContractPositionData, legData, true));
                    tempPostMergeContractPositionData.setStatPositionUnitList(new ArrayList<>());

                    /* ??????????????????????????? */
                    for (StatPositionUnit positionUnit : tempOriginalContractPositionData.getStatPositionUnitList()) {
                        postMergeComposePositionData.addToStatPositionUnitList(getPostMergeComposePositionUnit(postMergeComposePositionData.getStatPositionItem(), positionUnit, positionUnit.getUnitQuantity()));
                    }
                    legMergeQuantityQuota -= legData.getQuantity();

                } else if (legData.getQuantity() < tempOriginalContractPositionData.getStatPositionItem().getQuantity()) {
                    /*
                     * ?????????????????????
                     * */
                    /* ??????????????????????????? */
                    tempPostMergeContractPositionData = new PositionData();
                    tempPostMergeContractPositionData.setStatPositionItem(getPostMergeContractPosition(tempOriginalContractPositionData, legData, false));

                    /* ??????????????????????????? */
//                    int legMergeQuantityQuota = targetComposeLegUnitQuantity;
                    for (StatPositionUnit positionUnit : tempOriginalContractPositionData.getStatPositionUnitList()) {
                        TargetData targetData = new TargetData(mergeToComposeReq.getSubAccountId(), String.valueOf(positionUnit.getSledContractId()), HostingXQTargetType.CONTRACT_TARGET);
                        if (legMergeQuantityQuota > 0) {
                            if (positionUnit.getUnitQuantity() <= legMergeQuantityQuota) {
                                /* ???????????????????????? */
                                postMergeComposePositionData.addToStatPositionUnitList(getPostMergeComposePositionUnit(postMergeComposePositionData.getStatPositionItem(), positionUnit, positionUnit.getUnitQuantity()));
                                legMergeQuantityQuota -= positionUnit.getUnitQuantity();
                                AppLog.e("#################################### legMergeQuantityQuota : " + legMergeQuantityQuota + ", positionUnit.getUnitQuantity() : " + positionUnit.getUnitQuantity());
                            } else {
                                /* ???????????????????????? */
                                postMergeComposePositionData.addToStatPositionUnitList(getPostMergeComposePositionUnit(postMergeComposePositionData.getStatPositionItem(), positionUnit, legMergeQuantityQuota));

                                /* ????????????????????????????????? */
                                tempPostMergeContractPositionData.addToStatPositionUnitList(getPostMergeContractPositionUnit(targetData, positionUnit, positionUnit.getUnitQuantity() - legMergeQuantityQuota));

                                legMergeQuantityQuota = 0;
                            }
                        } else {
                            /* ?????????????????????????????????????????????????????? */
                            tempPostMergeContractPositionData.addToStatPositionUnitList(getPostMergeContractPositionUnit(targetData, positionUnit, positionUnit.getUnitQuantity()));
                        }
                    }
                } else {
                    /*
                     * ?????????????????????????????????????????????????????????
                     * */
                    AppReport.reportErr("prepareMergeToComposeData # invalid leg data quantity, should be larger than existing position quantity : " + new Gson().toJson(legData));
                    throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid leg data quantity, should be larger than existing position quantity");
                }
            }
            mergeToComposeData.addToPostMergeContractPositionDataList(tempPostMergeContractPositionData);
        }
        /*
         *  20190315
         *  ???????????????????????????????????????
         * */
        if (postMergeComposePositionData.getStatPositionUnitList() != null) {
            Double tempLegTotalPrice;
            BigDecimal tempLegPriceBigDecimal;
            for (StatPositionUnit positionUnit : postMergeComposePositionData.getStatPositionUnitList()) {
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
        setPostMergeComposePositionComposePrice(postMergeComposePositionData.getStatPositionItem());
        /*
         * 20190315 fix end
         * */
        mergeToComposeData.setPostMergeComposePositionData(postMergeComposePositionData);
    }

    /**
     * ???????????????????????????????????????
     */
    public void validateData() throws ErrorInfo {

        /*
         * 1 ????????????
         * ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * */
//        Double composePrice = calculateComposePrice();
//        if (composePrice == null) {
//            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "fail to calculate compose price");
//        }
//        double composePriceErrorViation = 0;
//        if (mergeToComposeReq.getComposePrice() > composePrice) {
//            composePriceErrorViation = mergeToComposeReq.getComposePrice() - composePrice;
//        } else {
//            composePriceErrorViation = composePrice - mergeToComposeReq.getComposePrice();
//        }
//        if (composePriceErrorViation > COMPOSE_PRICE_ERROR_VIATION_TOLERANCE) {
//            AppLog.e("MergeToComposeController # validateData ---- composePrice : " + composePrice + ", mergeToComposeReq.getComposePrice() : " + mergeToComposeReq.getComposePrice() + ", composePriceErrorViation : " + composePriceErrorViation);
//            throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "compose price errorviation is larger than 0.000001, please check");
//        }

        /*
         * 2 ????????????
         * */
        StatComposeLeg tempStatComposeLeg;

        Map<Long, Integer> postMergeLegQuantityMap = new HashMap<>();
        Map<Long, Integer> originalContractQuantityMap = new HashMap<>();
        Integer tempQuantity = null;

        for (StatPositionUnit positionUnit : mergeToComposeData.getPostMergeComposePositionData().getStatPositionUnitList()) {
            tempStatComposeLeg = composeGraph.getComposeLegQuantityMap().get(positionUnit.getSledContractId());

            /* 2.1 ???????????? */
            if (mergeToComposeData.getPostMergeComposePositionData().getStatPositionItem().getDirection() == StatDirection.STAT_BUY) {
                StatAssert.doAssert(tempStatComposeLeg.getDirection() == positionUnit.getDirection(), "merge fail, compose leg direction is not match with composeGraph");
            } else {
                StatAssert.doAssert(tempStatComposeLeg.getDirection() != positionUnit.getDirection(), "merge fail, compose leg direction is not match with composeGraph");
            }

            /*
             * ??????????????????
             * */
            tempQuantity = postMergeLegQuantityMap.get(positionUnit.getSledContractId());
            if (tempQuantity == null) {
                tempQuantity = new Integer(0);
            }
            tempQuantity += positionUnit.getUnitQuantity();
            postMergeLegQuantityMap.put(positionUnit.getSledContractId(), tempQuantity);
        }

        /* 2.2 ???????????? */
        int composeLegQuantity;
        for (long contractId : composeGraph.getComposeSledContractIdSet()) {
            composeLegQuantity = getComposeLegQuantity(contractId);
            Integer contractQuantity = postMergeLegQuantityMap.get(contractId);
//            ParameterChecker.check(contractQuantity != null & contractQuantity == composeLegQuantity, "merge fail,compose leg quantity is not match with composeGraph");
            if (contractQuantity == null || contractQuantity != composeLegQuantity) {
                AppReport.reportErr("validateData ---- merge fail, composeLegQuantity : " + composeLegQuantity + ", contractQuantity : " + contractQuantity + ", contractId : " + contractId);
                throw new ErrorInfo(ErrorCodeInner.PARAM_ERROR.getErrorCode(), "merge fail,compose leg quantity is not match with composeGraph");
            }
        }

        /*
         * 3 ????????????
         * */
        int tempTotalUnitQuantity = 0;
        for (PositionData positionData : mergeToComposeData.getPostMergeContractPositionDataList()) {
            long contractId = 0;
            tempTotalUnitQuantity = 0;
            if (positionData.getStatPositionUnitList() == null || positionData.getStatPositionUnitList().size() < 1) {
                continue;
            }
            for (StatPositionUnit positionUnit : positionData.getStatPositionUnitList()) {
                contractId = positionUnit.getSledContractId();
                ParameterChecker.check(positionData.getStatPositionItem().getDirection() == positionUnit.getDirection(), "merge fail, postMergeContractPosition direction error");
                tempTotalUnitQuantity += positionUnit.getUnitQuantity();
            }
            ParameterChecker.check(positionData.getStatPositionItem().getQuantity() == tempTotalUnitQuantity, "merge fail, postMergeContractPosition quantity not match with unit");

            tempQuantity = postMergeLegQuantityMap.get(contractId);
            if (tempQuantity == null) {
                tempQuantity = new Integer(0);
            }
            tempQuantity += tempTotalUnitQuantity;
            postMergeLegQuantityMap.put(contractId, tempQuantity);
        }

        /*
         * 4 ?????????????????????????????????
         * */
        for (PositionData positionData : mergeToComposeData.getOriginalContractPositionDataList()) {
            for (StatPositionUnit positionUnit : positionData.getStatPositionUnitList()) {
                tempQuantity = originalContractQuantityMap.get(positionUnit.getSledContractId());
                if (tempQuantity == null) {
                    tempQuantity = new Integer(0);
                }
                tempQuantity += positionUnit.getUnitQuantity();
                originalContractQuantityMap.put(positionUnit.getSledContractId(), tempQuantity);
            }
        }
//        AppLog.e("validateData #### originalContractQuantityMap.size() :" + originalContractQuantityMap.size() + ", postMergeLegQuantityMap.size() " + postMergeLegQuantityMap.size());
//        AppLog.e("validateData #### originalContractQuantityMap :" + new Gson().toJson(originalContractQuantityMap) + ", postMergeLegQuantityMap : " + new Gson().toJson(postMergeLegQuantityMap));

        ParameterChecker.check(originalContractQuantityMap.size() == postMergeLegQuantityMap.size(), "merge fail, originalContractQuantity is not equal to postMergeLegQuantity");
        Integer originalContractQuantity;
        for (Map.Entry<Long, Integer> postMergeLegQuantityEntry : postMergeLegQuantityMap.entrySet()) {
            originalContractQuantity = originalContractQuantityMap.get(postMergeLegQuantityEntry.getKey());
            AppLog.d("validateData #### postMergeLegQuantityEntry.getKey() :" + postMergeLegQuantityEntry.getKey() + ", originalContractQuantity " + originalContractQuantity + ", postMergeLegQuantityEntry.getValue() : " + postMergeLegQuantityEntry.getValue());
            ParameterChecker.check(originalContractQuantity != null, "merge fail, originalContractQuantity is null, not match with postMergeLegQuantity");
            ParameterChecker.check(postMergeLegQuantityEntry.getValue() == originalContractQuantity, "merge fail, originalContractQuantity is not match with postMergeLegQuantity");
        }
    }

    /**
     * ??????
     */
    public void store(Connection conn) throws SQLException, ErrorInfo {
        /*
         * ?????????????????????
         * */
        for (PositionData positionData : mergeToComposeData.getPostMergeContractPositionDataList()) {
            if (positionData.getStatPositionItem().getQuantity() == 0) {
                StatPositionHandler.removePosition(conn, positionData.getStatPositionItem().getPositionItemId());
            } else {
                StatPositionHandler.updatePositionData(conn, positionData.getStatPositionItem(), positionData.getStatPositionUnitList());
            }
        }

        /*
         * ?????????????????????
         * */
        StatPositionHandler.addPositionData(conn, mergeToComposeData.getPostMergeComposePositionData().getStatPositionItem(), mergeToComposeData.getPostMergeComposePositionData().getStatPositionUnitList());
    }

    public MergeToComposeData getMergeToComposeData() {
        return mergeToComposeData;
    }

    /**
     * ????????????????????????
     */
    private StatPositionItem getPostMergeComposePositionItem() throws ErrorInfo {
        StatPositionItem postMergeComposePositionItem = new StatPositionItem();

        StatDataSource dataSource = new StatDataSource();
        dataSource.setSourceDataChannel(DataSourceChannel.FROM_CONTRACT_MERGE);
        dataSource.setSourceDataTimestampMs(System.currentTimeMillis());

        postMergeComposePositionItem.setPositionItemId(AppConfig.getStatPositionItemId());
        postMergeComposePositionItem.setSubAccountId(mergeToComposeReq.getSubAccountId());
        postMergeComposePositionItem.setTargetKey(String.valueOf(mergeToComposeReq.getComposeGraphId()));
        postMergeComposePositionItem.setTargetType(HostingXQTargetType.COMPOSE_TARGET);
//        postMergeComposePositionItem.setPrice(/*mergeToComposeReq.getComposePrice()*/composePrice);
        postMergeComposePositionItem.setQuantity(mergeToComposeReq.getVolume());
        postMergeComposePositionItem.setDirection(mergeToComposeReq.getDiretion());
        postMergeComposePositionItem.setSource(dataSource);

        return postMergeComposePositionItem;
    }

    private void setPostMergeComposePositionComposePrice(StatPositionItem postMergeComposePositionItem) throws ErrorInfo {
        Double composePrice = ComposeUtil.calculateComposePrice(composeGraph, composeLegTotalPriceMap, mergeToComposeReq.getVolume());
        if (composePrice == null) {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "fail to calculate compose price");
        }
        postMergeComposePositionItem.setPrice(composePrice);
    }

    /**
     * ??????????????? ????????? ?????????????????????
     */
    private StatPositionUnit getNotTradingPostMergeComposePositionUnit(StatPositionItem postMergeComposePosition, MergeComposeLegData legData) throws ErrorInfo {
        StatPositionUnit tempPostMergeComposePositionUnit = new StatPositionUnit();
        ExternalDataSource dataSource = new ExternalDataSource();
        dataSource.setSourceType(SourceType.ST_MERGE_TO_COMPOSE_NOT_TRADE);
        dataSource.setSourceId(0);  // ?????????????????????????????????????????????????????????

        tempPostMergeComposePositionUnit.setUnitId(AppConfig.getStatPositionUnitId());
        tempPostMergeComposePositionUnit.setPositionItemId(postMergeComposePosition.getPositionItemId());
        tempPostMergeComposePositionUnit.setSledContractId(legData.getSledContractId());
        tempPostMergeComposePositionUnit.setUnitPrice(legData.getPrice());
        tempPostMergeComposePositionUnit.setUnitQuantity(0);
        tempPostMergeComposePositionUnit.setDirection(legData.getDiretion());
        tempPostMergeComposePositionUnit.setSourceDataTimestampMs(System.currentTimeMillis());
        tempPostMergeComposePositionUnit.setSubAccountId(postMergeComposePosition.getSubAccountId());
        tempPostMergeComposePositionUnit.setTargetKey(postMergeComposePosition.getTargetKey());
        tempPostMergeComposePositionUnit.setTargetType(postMergeComposePosition.getTargetType());
        tempPostMergeComposePositionUnit.setSource(dataSource);

        return tempPostMergeComposePositionUnit;
    }

    /**
     * ??????????????????????????????????????????
     */
    private StatPositionUnit getPostMergeComposePositionUnit(StatPositionItem postMergeComposePosition, StatPositionUnit originalContractPositionUnit, int mergeQuantity) throws ErrorInfo {
        StatPositionUnit tempPostMergeComposePositionUnit = new StatPositionUnit();
        ExternalDataSource dataSource = new ExternalDataSource();

        dataSource.setSourceType(originalContractPositionUnit.getSource().getSourceType());
        dataSource.setSourceId(originalContractPositionUnit.getSource().getSourceId());

        tempPostMergeComposePositionUnit.setUnitId(AppConfig.getStatPositionUnitId());
        tempPostMergeComposePositionUnit.setPositionItemId(postMergeComposePosition.getPositionItemId());
        tempPostMergeComposePositionUnit.setSledContractId(originalContractPositionUnit.getSledContractId());
        tempPostMergeComposePositionUnit.setUnitPrice(originalContractPositionUnit.getUnitPrice());
        tempPostMergeComposePositionUnit.setDirection(originalContractPositionUnit.getDirection());
        tempPostMergeComposePositionUnit.setUnitQuantity(mergeQuantity);
        tempPostMergeComposePositionUnit.setSourceDataTimestampMs(originalContractPositionUnit.getSourceDataTimestampMs());
        tempPostMergeComposePositionUnit.setSubAccountId(postMergeComposePosition.getSubAccountId());
        tempPostMergeComposePositionUnit.setTargetKey(postMergeComposePosition.getTargetKey());
        tempPostMergeComposePositionUnit.setTargetType(postMergeComposePosition.getTargetType());
        tempPostMergeComposePositionUnit.setSource(dataSource);

        return tempPostMergeComposePositionUnit;
    }

    /**
     * ??????????????????????????????????????????
     */
    private StatPositionUnit getPostMergeContractPositionUnit(StatPositionUnit originalContractPositionUnit, int contractUnitQuantity) throws ErrorInfo {
        return getPostMergeContractPositionUnit(null, originalContractPositionUnit, contractUnitQuantity);
    }

    /**
     * ??????????????????????????????????????????(?????????)
     */
    private StatPositionUnit getPostMergeContractPositionUnit(TargetData targetData, StatPositionUnit originalContractPositionUnit, int contractUnitQuantity) throws ErrorInfo {
        StatPositionUnit tempPostMergeContractPositionUnit = new StatPositionUnit();
        ExternalDataSource dataSource = new ExternalDataSource();

        dataSource.setSourceType(originalContractPositionUnit.getSource().getSourceType());
        dataSource.setSourceId(originalContractPositionUnit.getSource().getSourceId());

        tempPostMergeContractPositionUnit.setUnitId(originalContractPositionUnit.getUnitId());
        tempPostMergeContractPositionUnit.setPositionItemId(originalContractPositionUnit.getPositionItemId());
        tempPostMergeContractPositionUnit.setSledContractId(originalContractPositionUnit.getSledContractId());
        tempPostMergeContractPositionUnit.setUnitPrice(originalContractPositionUnit.getUnitPrice());
        tempPostMergeContractPositionUnit.setDirection(originalContractPositionUnit.getDirection());
        tempPostMergeContractPositionUnit.setUnitQuantity(contractUnitQuantity);
        tempPostMergeContractPositionUnit.setSourceDataTimestampMs(originalContractPositionUnit.getSourceDataTimestampMs());
        if (targetData != null && originalContractPositionUnit.getSubAccountId() == 0) {
            tempPostMergeContractPositionUnit.setSubAccountId(targetData.getSubAccountId());
            tempPostMergeContractPositionUnit.setTargetKey(targetData.getTargetKey());
            tempPostMergeContractPositionUnit.setTargetType(targetData.getTargetType());
        } else {
            tempPostMergeContractPositionUnit.setSubAccountId(originalContractPositionUnit.getSubAccountId());
            tempPostMergeContractPositionUnit.setTargetKey(originalContractPositionUnit.getTargetKey());
            tempPostMergeContractPositionUnit.setTargetType(originalContractPositionUnit.getTargetType());
        }

        tempPostMergeContractPositionUnit.setSource(dataSource);

        return tempPostMergeContractPositionUnit;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     */
    private StatPositionItem getPostMergeContractPosition(PositionData originalContractPositionData, MergeComposeLegData legData, boolean isTotallyMerge) {
        StatPositionItem postMergeContractPosition = new StatPositionItem();

        StatDataSource dataSource = new StatDataSource();
        dataSource.setSourceDataChannel(originalContractPositionData.getStatPositionItem().getSource().getSourceDataChannel());
        dataSource.setSourceDataTimestampMs(originalContractPositionData.getStatPositionItem().getSource().getSourceDataTimestampMs());

        postMergeContractPosition.setPositionItemId(originalContractPositionData.getStatPositionItem().getPositionItemId());
        postMergeContractPosition.setSubAccountId(originalContractPositionData.getStatPositionItem().getSubAccountId());
        postMergeContractPosition.setTargetKey(originalContractPositionData.getStatPositionItem().getTargetKey());
        postMergeContractPosition.setTargetType(originalContractPositionData.getStatPositionItem().getTargetType());
        postMergeContractPosition.setPrice(originalContractPositionData.getStatPositionItem().getPrice());
        postMergeContractPosition.setDirection(originalContractPositionData.getStatPositionItem().getDirection());
        postMergeContractPosition.setSource(dataSource);
        postMergeContractPosition.setCreateTimestampMs(originalContractPositionData.getStatPositionItem().getCreateTimestampMs());

        if (isTotallyMerge) {
            postMergeContractPosition.setQuantity(0);  // ???????????????????????????????????????????????????0
        } else {
            postMergeContractPosition.setQuantity(originalContractPositionData.getStatPositionItem().getQuantity() - legData.getQuantity());
        }
        return postMergeContractPosition;
    }
}
