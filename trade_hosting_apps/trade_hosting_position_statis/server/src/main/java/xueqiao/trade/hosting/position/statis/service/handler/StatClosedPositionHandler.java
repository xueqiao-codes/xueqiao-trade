package xueqiao.trade.hosting.position.statis.service.handler;

import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.service.bean.*;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatAssert;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.*;
import xueqiao.trade.hosting.position.statis.thriftapi.StatPositionErrorCode;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class StatClosedPositionHandler extends BaseClosedPositionHandler {

    /**
     * 创建平仓明细数据
     *
     * @param statPositionItem 持仓明细数据
     * @param closedId         平仓汇总记录id
     * @param closedQuantity   平仓量
     */
    public StatClosedPositionItem generateStatClosedPositionItem(StatPositionItem statPositionItem, long closedId, int closedQuantity) throws ErrorInfo {
        StatClosedPositionItem statClosedPositionItem = new StatClosedPositionItem();

        statClosedPositionItem.setClosedItemId(AppConfig.getStatClosedPositionItemId());
        statClosedPositionItem.setClosedId(closedId);
        statClosedPositionItem.setPositionItemId(statPositionItem.getPositionItemId());
        statClosedPositionItem.setSubAccountId(statPositionItem.getSubAccountId());
        statClosedPositionItem.setTargetKey(statPositionItem.getTargetKey());
        statClosedPositionItem.setTargetType(statPositionItem.getTargetType());
        statClosedPositionItem.setPrice(statPositionItem.getPrice());
        statClosedPositionItem.setClosedQuantity(closedQuantity);
        statClosedPositionItem.setDirection(statPositionItem.getDirection());
        statClosedPositionItem.setSource(statPositionItem.getSource());
        statClosedPositionItem.setPositionCreateTimestampMs(statPositionItem.getCreateTimestampMs());

        return statClosedPositionItem;
    }

    /**
     * 计算平仓数据
     * - 原持仓数据列表
     * - 持续持仓数据列表
     * - 平仓数据列表
     * - 平仓汇总
     */
    public ClosedData getClosedData(BatchClosedPositionReq batchClosedPositionReq, List<StatPositionItem> statPositionItemList) throws ErrorInfo {
        if (batchClosedPositionReq.getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            return getComposeClosedData(batchClosedPositionReq, statPositionItemList);
        } else {
            return getContractClosedData(batchClosedPositionReq, statPositionItemList);
        }
    }

    public ClosedData getContractClosedData(BatchClosedPositionReq batchClosedPositionReq, List<StatPositionItem> statPositionItemList) throws ErrorInfo {

        StatAssert.checkParam(batchClosedPositionReq.getTargetType() == HostingXQTargetType.CONTRACT_TARGET, "get contract close data here");

        ClosedData closedData = new ClosedData();

        /*
         * targetData是为了兼容以前的数据
         * */
        TargetData targetData = new TargetData(batchClosedPositionReq.getSubAccountId(), batchClosedPositionReq.getTargetKey(), batchClosedPositionReq.getTargetType());

        Map<Long, StatPositionItem> statPositionItemMap = new HashMap<Long, StatPositionItem>();
        for (StatPositionItem item : statPositionItemList) {
            statPositionItemMap.put(item.getPositionItemId(), item);
        }

        StatPositionHandler statPositionHandler = new StatPositionHandler();

        // 生成平仓汇总项id
        long closedId = AppConfig.getStatClosedPositionId();

        StatPositionItem tempPositionItem = null;

        int longQuantitySum = 0;
        int shortQuantitySum = 0;
        BigDecimal spreadBuy = new BigDecimal(0);
        BigDecimal spreadSell = new BigDecimal(0);
        BigDecimal tempSpread = null;

        for (ClosedPositionDetailItem item : batchClosedPositionReq.getClosedPositionDetailItems()) {
            tempPositionItem = statPositionItemMap.get(item.getPositionItemId());
            if (tempPositionItem == null) {
                AppReport.reportErr(new StringBuilder("StatClosedPositionHandler ## ")
                        .append(" getClosedData ---- ")
                        .append("position item not exist(positionItemId:")
                        .append(item.positionItemId)
                        .append(")").toString());
                throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_ITEM_NOT_EXIST.getValue(), "position item not exist");
            }

            int quantityOffset = tempPositionItem.getQuantity() - item.getClosedVolume();

            /*
             * 检查该持仓项的平仓数量是否正确
             * */
            if (quantityOffset < 0) {
                AppReport.reportErr(new StringBuilder("StatClosedPositionHandler ## ")
                        .append(" getClosedData ---- ")
                        .append("closed quantity too large(positionItemId:")
                        .append(item.positionItemId)
                        .append(")").toString());
                throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_CLOSED_QUANTITY_TOO_LARGE.getValue(), "closed quantity too large");
            }
            if (tempPositionItem.getDirection() == StatDirection.STAT_BUY) {
                longQuantitySum += item.getClosedVolume();

                tempSpread = new BigDecimal(new Double(tempPositionItem.getPrice()).toString());
                tempSpread = tempSpread.multiply(new BigDecimal(item.getClosedVolume()));
                spreadBuy = spreadBuy.add(tempSpread);
            } else {
                shortQuantitySum += item.getClosedVolume();

                tempSpread = new BigDecimal(new Double(tempPositionItem.getPrice()).toString());
                tempSpread = tempSpread.multiply(new BigDecimal(item.getClosedVolume()));
                spreadSell = spreadSell.add(tempSpread);
            }

            // 查询对应的持仓小单元
            List<StatPositionUnit> statPositionUnitList = statPositionHandler.getStatPositionUnitList(tempPositionItem.getPositionItemId());
            StatAssert.doAssert(statPositionUnitList != null && statPositionUnitList.size() > 0, "unit of position(positionItemId: " + tempPositionItem.getPositionItemId() + ") is empty");
            StatPositionUnit contractPositionUnit = statPositionUnitList.get(0);

            // 记录源持仓数据
            PositionData originalPositionData = new PositionData();
            originalPositionData.setStatPositionItem(tempPositionItem);
            originalPositionData.setStatPositionUnitList(statPositionUnitList);

            PositionData continuedPositionData;
            ClosedPositionData closedPositionData;
            if (quantityOffset == 0) {
                /* 该项全部平仓 */

                // 记录持续持仓项 及 其数量
                continuedPositionData = new PositionData();
                continuedPositionData.setStatPositionItem(getContinuedPositionData(tempPositionItem, 0));
                continuedPositionData.setStatPositionUnitList(null);

                // 记录平仓项
                closedPositionData = new ClosedPositionData();
                closedPositionData.setClosedPositionItem(generateStatClosedPositionItem(tempPositionItem, closedId, tempPositionItem.getQuantity()));
                closedPositionData.setClosedUnitList(getClosedPositionUnitList(targetData, statPositionUnitList, closedPositionData.getClosedPositionItem().closedItemId));
            } else {
                /* 该项部分平仓 */

                // 记录持续持仓数据
                continuedPositionData = new PositionData();
                continuedPositionData.setStatPositionItem(getContinuedPositionData(tempPositionItem, quantityOffset));

                // 记录平仓项
                closedPositionData = new ClosedPositionData();
                closedPositionData.setClosedPositionItem(generateStatClosedPositionItem(tempPositionItem, closedId, item.getClosedVolume()));

                List<StatPositionUnit> continuedPositionUnitList = new ArrayList<>();
                List<StatClosedUnit> closedPositionUnitList = new ArrayList<>();

                continuedPositionUnitList.add(getContinuedPositionUnit(targetData, contractPositionUnit, quantityOffset));
                closedPositionUnitList.add(getClosedPositionUnit(targetData, contractPositionUnit, closedPositionData.getClosedPositionItem().getClosedItemId(), item.getClosedVolume()));

                continuedPositionData.setStatPositionUnitList(continuedPositionUnitList);
                closedPositionData.setClosedUnitList(closedPositionUnitList);
            }
            closedData.addtoOriginalPositionDataList(originalPositionData);
            closedData.addtoContinuedPositionDataList(continuedPositionData);
            closedData.addtoClosedPositionDataList(closedPositionData);
        }

        // 否能正确平掉
        if (shortQuantitySum != longQuantitySum) {
            AppReport.reportErr(new StringBuilder("StatClosedPositionHandler ## ")
                    .append(" batchClosePosition ---- ")
                    .append("closed items not macth(subaccountId:")
                    .append(batchClosedPositionReq.getSubAccountId())
                    .append(", targetKey:")
                    .append(batchClosedPositionReq.getTargetKey())
                    .append(")")
                    .append(", batchClosedPositionReq : ")
                    .append(new Gson().toJson(batchClosedPositionReq)).toString());
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_CLOSED_ITEMS_NOT_MATCH.getValue(), "closed items not macth, longQuantitySum is not equal to shortQuantitySum");
        }
        /* 数据校验结束 */

        // 计算价差收益
        BigDecimal spreadProfit = spreadSell.subtract(spreadBuy);

        // 计算平仓收益
        List<ClosedProfit> closedProfits = calculateContractClosedProfit(batchClosedPositionReq.getTargetKey(), closedData.getClosedPositionDataList());

        // 计算平仓汇总
        StatClosedPositionSummary statClosedPositionSummary = new StatClosedPositionSummary();
        statClosedPositionSummary.setClosedId(closedId);
        statClosedPositionSummary.setSubAccountId(batchClosedPositionReq.getSubAccountId());
        statClosedPositionSummary.setTargetKey(batchClosedPositionReq.getTargetKey());
        statClosedPositionSummary.setTargetType(statPositionItemList.get(0).getTargetType());
        statClosedPositionSummary.setClosedPosition(longQuantitySum);
        statClosedPositionSummary.setClosedProfits(closedProfits);
        statClosedPositionSummary.setSpreadProfit(spreadProfit.doubleValue());

        closedData.setClosedPositionSummary(statClosedPositionSummary);

        return closedData;
    }

    public ClosedData getComposeClosedData(BatchClosedPositionReq batchClosedPositionReq, List<StatPositionItem> statPositionItemList) throws ErrorInfo {

        StatAssert.checkParam(batchClosedPositionReq.getTargetType() == HostingXQTargetType.COMPOSE_TARGET, "get compose close data here");

        ClosedData closedData = new ClosedData();
        /*
         * targetData是为了兼容以前的数据
         * */
        TargetData targetData = new TargetData(batchClosedPositionReq.getSubAccountId(), batchClosedPositionReq.getTargetKey(), batchClosedPositionReq.getTargetType());

        Map<Long, StatPositionItem> statPositionItemMap = new HashMap<Long, StatPositionItem>();
        for (StatPositionItem item : statPositionItemList) {
            statPositionItemMap.put(item.getPositionItemId(), item);
        }

        StatPositionHandler statPositionHandler = new StatPositionHandler();

        /*
         * 获取组合中各腿的配比
         * */
        Map<Long, Integer> statComposeLegQuantityMap = new HostingComposeHandler().getComposeProportioning(batchClosedPositionReq.getTargetKey());

        // 生成平仓汇总项id
        long closedId = AppConfig.getStatClosedPositionId();

        StatPositionItem tempPositionItem = null;

        int longQuantitySum = 0;
        int shortQuantitySum = 0;
        BigDecimal spreadBuy = new BigDecimal(0);
        BigDecimal spreadSell = new BigDecimal(0);
        BigDecimal tempSpread = null;

        for (ClosedPositionDetailItem item : batchClosedPositionReq.getClosedPositionDetailItems()) {
            tempPositionItem = statPositionItemMap.get(item.getPositionItemId());
            if (tempPositionItem == null) {
                AppReport.reportErr(new StringBuilder("StatClosedPositionHandler ## ")
                        .append(" getClosedData ---- ")
                        .append("position item not exist(positionItemId:")
                        .append(item.positionItemId)
                        .append(")").toString());
                throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_ITEM_NOT_EXIST.getValue(), "position item not exist");
            }

            int quantityOffset = tempPositionItem.getQuantity() - item.getClosedVolume();

            /*
             * 检查该持仓项的平仓数量是否正确
             * */
            if (quantityOffset < 0) {
                AppReport.reportErr(new StringBuilder("StatClosedPositionHandler ## ")
                        .append(" getClosedData ---- ")
                        .append("closed quantity too large(positionItemId:")
                        .append(item.positionItemId)
                        .append(")").toString());
                throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_CLOSED_QUANTITY_TOO_LARGE.getValue(), "closed quantity too large");
            }
            if (tempPositionItem.getDirection() == StatDirection.STAT_BUY) {
                longQuantitySum += item.getClosedVolume();

                tempSpread = new BigDecimal(new Double(tempPositionItem.getPrice()).toString());
//                tempSpread = tempSpread.multiply(new BigDecimal(tempPositionItem.getQuantity()));
                tempSpread = tempSpread.multiply(new BigDecimal(item.getClosedVolume()));
                spreadBuy = spreadBuy.add(tempSpread);
            } else {
                shortQuantitySum += item.getClosedVolume();

                tempSpread = new BigDecimal(new Double(tempPositionItem.getPrice()).toString());
//                tempSpread = tempSpread.multiply(new BigDecimal(tempPositionItem.getQuantity()));
                tempSpread = tempSpread.multiply(new BigDecimal(item.getClosedVolume()));
                spreadSell = spreadSell.add(tempSpread);
            }

            // 查询对应的持仓小单元
            List<StatPositionUnit> statPositionUnitList = statPositionHandler.getStatPositionUnitList(tempPositionItem.getPositionItemId());

            // 小单元排序，遵循先成交先平仓的原则
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

            // 记录源持仓数据
            PositionData originalPositionData = new PositionData();
            originalPositionData.setStatPositionItem(tempPositionItem);
            originalPositionData.setStatPositionUnitList(statPositionUnitList);

            PositionData continuedPositionData;
            ClosedPositionData closedPositionData;
            if (quantityOffset == 0) {
                /* 该项全部平仓 */

                // 记录持续持仓项 及 其数量
//				continuedPositionData = null;
                continuedPositionData = new PositionData();
                continuedPositionData.setStatPositionItem(getContinuedPositionData(tempPositionItem, 0));
                continuedPositionData.setStatPositionUnitList(new ArrayList<>());

                // 记录平仓项
                closedPositionData = new ClosedPositionData();
                closedPositionData.setClosedPositionItem(generateStatClosedPositionItem(tempPositionItem, closedId, tempPositionItem.getQuantity()));
                closedPositionData.setClosedUnitList(getClosedPositionUnitList(targetData, statPositionUnitList, closedPositionData.getClosedPositionItem().closedItemId));
            } else {
                /* 该项部分平仓 */

                // 记录持续持仓数据
                continuedPositionData = new PositionData();
                continuedPositionData.setStatPositionItem(getContinuedPositionData(tempPositionItem, quantityOffset));

                // 记录平仓项
                closedPositionData = new ClosedPositionData();
                closedPositionData.setClosedPositionItem(generateStatClosedPositionItem(tempPositionItem, closedId, item.getClosedVolume()));

                /*
                 * 计算小单元的平仓情况
                 * 一个成交订单里，可能会有多笔单腿成交
                 * */
                Integer composeLegQuantity;
                Integer alreadyClosedLegQuantity = 0;
                int closedLegQuota = 0;
                Map<Long, Integer> closedLegQuantityMap = new HashMap<>();

                List<StatPositionUnit> continuedPositionUnitList = new ArrayList<>();
                List<StatClosedUnit> closedPositionUnitList = new ArrayList<>();

                for (StatPositionUnit unit : statPositionUnitList) {
                    composeLegQuantity = statComposeLegQuantityMap.get(unit.getSledContractId());
                    StatAssert.doAssert(composeLegQuantity != null, "contract(contractId:" + unit.getSledContractId() + ") not in composeGraph");
                    closedLegQuota = composeLegQuantity * item.getClosedVolume();

                    alreadyClosedLegQuantity = closedLegQuantityMap.get(unit.getSledContractId());
                    if (alreadyClosedLegQuantity == null) {
                        alreadyClosedLegQuantity = 0;
                    }

                    int closedUnitQuantityOffset = closedLegQuota - alreadyClosedLegQuantity;
                    if (closedUnitQuantityOffset > 0) {
                        /* 未达到平仓配额，继续平 */
                        if (closedUnitQuantityOffset >= unit.getUnitQuantity()) {
                            /* 该项全部平 */
                            continuedPositionUnitList.add(getContinuedPositionUnit(targetData, unit, 0));
                            closedPositionUnitList.add(getClosedPositionUnit(targetData, unit, closedPositionData.getClosedPositionItem().getClosedItemId(), unit.getUnitQuantity()));

                            closedLegQuantityMap.put(unit.getSledContractId(), alreadyClosedLegQuantity + unit.getUnitQuantity());
                        } else {
                            /* 该项部分平 */
                            continuedPositionUnitList.add(getContinuedPositionUnit(targetData, unit, unit.getUnitQuantity() - closedUnitQuantityOffset));
                            closedPositionUnitList.add(getClosedPositionUnit(targetData, unit, closedPositionData.getClosedPositionItem().getClosedItemId(), closedUnitQuantityOffset));

                            closedLegQuantityMap.put(unit.getSledContractId(), alreadyClosedLegQuantity + closedUnitQuantityOffset);
                        }
                    } else {
                        /* 已经达到平仓配额了，不用再平了 */
                        continuedPositionUnitList.add(unit);
                    }
                }
                continuedPositionData.setStatPositionUnitList(continuedPositionUnitList);
                closedPositionData.setClosedUnitList(closedPositionUnitList);
            }
            closedData.addtoOriginalPositionDataList(originalPositionData);
            closedData.addtoContinuedPositionDataList(continuedPositionData);
            closedData.addtoClosedPositionDataList(closedPositionData);
        }

        // 否能正确平掉
        if (shortQuantitySum != longQuantitySum) {
            AppReport.reportErr(new StringBuilder("ClosePositionService ## ")
                    .append(" batchClosePosition ---- ")
                    .append("closed items not macth(subaccountId:")
                    .append(batchClosedPositionReq.getSubAccountId())
                    .append(", targetKey:")
                    .append(batchClosedPositionReq.getTargetKey())
                    .append(")")
                    .append(", batchClosedPositionReq : ")
                    .append(new Gson().toJson(batchClosedPositionReq)).toString());
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_CLOSED_ITEMS_NOT_MATCH.getValue(), "closed items not macth, longQuantitySum is not equal to shortQuantitySum");
        }
        /* 数据校验结束 */

        // 计算价差收益
        BigDecimal spreadProfit = spreadSell.subtract(spreadBuy);

        // 计算平仓收益
        List<ClosedProfit> closedProfits = calculateComposeClosedProfit(closedData.getClosedPositionDataList());

        // 计算平仓汇总
        StatClosedPositionSummary statClosedPositionSummary = new StatClosedPositionSummary();
        statClosedPositionSummary.setClosedId(closedId);
        statClosedPositionSummary.setSubAccountId(batchClosedPositionReq.getSubAccountId());
        statClosedPositionSummary.setTargetKey(batchClosedPositionReq.getTargetKey());
        statClosedPositionSummary.setTargetType(statPositionItemList.get(0).getTargetType());
        statClosedPositionSummary.setClosedPosition(longQuantitySum);
        statClosedPositionSummary.setClosedProfits(closedProfits);
        statClosedPositionSummary.setSpreadProfit(spreadProfit.doubleValue());

        closedData.setClosedPositionSummary(statClosedPositionSummary);

        /*
         * 校验数据
         * */
        checkClosedData(closedData);
        return closedData;
    }

    /**
     * 获取持续持仓项
     */
    private StatPositionItem getContinuedPositionData(StatPositionItem originalPositionItem, int continuedPositionQuantity) {
        StatPositionItem statPositionItem = new StatPositionItem();
        statPositionItem.setPositionItemId(originalPositionItem.getPositionItemId());
        statPositionItem.setSubAccountId(originalPositionItem.getSubAccountId());
        statPositionItem.setTargetKey(originalPositionItem.getTargetKey());
        statPositionItem.setTargetType(originalPositionItem.getTargetType());
        statPositionItem.setPrice(originalPositionItem.getPrice());
        statPositionItem.setQuantity(continuedPositionQuantity);
        statPositionItem.setDirection(originalPositionItem.getDirection());
        statPositionItem.setCreateTimestampMs(originalPositionItem.getCreateTimestampMs());

        return statPositionItem;
    }

    /**
     * 获取持仓小单元
     */
    private StatPositionUnit getContinuedPositionUnit(StatPositionUnit originalStatPositionUnit, int quantity) {
//        StatPositionUnit unit = new StatPositionUnit();
//        unit.setUnitId(originalStatPositionUnit.getUnitId());
//        unit.setPositionItemId(originalStatPositionUnit.getPositionItemId());
//        unit.setSledContractId(originalStatPositionUnit.getSledContractId());
//        unit.setUnitPrice(originalStatPositionUnit.getUnitPrice());
//        unit.setUnitQuantity(quantity);
//        unit.setDirection(originalStatPositionUnit.getDirection());
//        unit.setSourceDataTimestampMs(originalStatPositionUnit.getSourceDataTimestampMs());
//
//        /*
//         * more fields
//         * */
//        ExternalDataSource dataSource = new ExternalDataSource();
//        unit.setSubAccountId(originalStatPositionUnit.getSubAccountId());
//        unit.setTargetKey(originalStatPositionUnit.getTargetKey());
//        unit.setTargetType(originalStatPositionUnit.getTargetType());
//        dataSource.setSourceId(originalStatPositionUnit.getSource().getSourceId());
//        dataSource.setSourceType(originalStatPositionUnit.getSource().getSourceType());
//        unit.setSource(dataSource);
//
//        return unit;
        return getContinuedPositionUnit(null, originalStatPositionUnit, quantity);
    }

    /**
     * 获取持仓小单元(兼容版)
     */
    private StatPositionUnit getContinuedPositionUnit(TargetData targetData, StatPositionUnit originalStatPositionUnit, int quantity) {
        StatPositionUnit unit = new StatPositionUnit();
        unit.setUnitId(originalStatPositionUnit.getUnitId());
        unit.setPositionItemId(originalStatPositionUnit.getPositionItemId());
        unit.setSledContractId(originalStatPositionUnit.getSledContractId());
        unit.setUnitPrice(originalStatPositionUnit.getUnitPrice());
        unit.setUnitQuantity(quantity);
        unit.setDirection(originalStatPositionUnit.getDirection());
        unit.setSourceDataTimestampMs(originalStatPositionUnit.getSourceDataTimestampMs());

        /*
         * more fields
         * */
        ExternalDataSource dataSource = new ExternalDataSource();
        if (targetData != null && originalStatPositionUnit.getSubAccountId() == 0) {
            unit.setSubAccountId(targetData.getSubAccountId());
            unit.setTargetKey(targetData.getTargetKey());
            unit.setTargetType(targetData.getTargetType());
        } else {
            unit.setSubAccountId(originalStatPositionUnit.getSubAccountId());
            unit.setTargetKey(originalStatPositionUnit.getTargetKey());
            unit.setTargetType(originalStatPositionUnit.getTargetType());
        }
        dataSource.setSourceId(originalStatPositionUnit.getSource().getSourceId());
        dataSource.setSourceType(originalStatPositionUnit.getSource().getSourceType());
        unit.setSource(dataSource);

        return unit;
    }

    /**
     * 获取平仓小单元
     */
    protected StatClosedUnit getClosedPositionUnit(StatPositionUnit originalStatPositionUnit, long closedItemId, int quantity) throws ErrorInfo {
//        StatClosedUnit unit = new StatClosedUnit();
//        unit.setClosedUnitId(AppConfig.getStatClosedUnitId());
//        unit.setClosedItemId(closedItemId);
//        unit.setPositionUnitId(originalStatPositionUnit.getUnitId());
//        unit.setPositionItemId(originalStatPositionUnit.getPositionItemId());
//        unit.setSledContractId(originalStatPositionUnit.getSledContractId());
//        unit.setUnitPrice(originalStatPositionUnit.getUnitPrice());
//        unit.setUnitQuantity(quantity);
//        unit.setDirection(originalStatPositionUnit.getDirection());
//        unit.setSourceDataTimestampMs(originalStatPositionUnit.getSourceDataTimestampMs());
//
//        /*
//         * more fields
//         * */
//        ExternalDataSource dataSource = new ExternalDataSource();
//        unit.setSubAccountId(originalStatPositionUnit.getSubAccountId());
//        unit.setTargetKey(originalStatPositionUnit.getTargetKey());
//        unit.setTargetType(originalStatPositionUnit.getTargetType());
//        dataSource.setSourceId(originalStatPositionUnit.getSource().getSourceId());
//        dataSource.setSourceType(originalStatPositionUnit.getSource().getSourceType());
//        unit.setSource(dataSource);

        return getClosedPositionUnit(null, originalStatPositionUnit, closedItemId, quantity);
    }

    /**
     * 获取平仓小单元（兼容版）
     */
    protected StatClosedUnit getClosedPositionUnit(TargetData targetData, StatPositionUnit originalStatPositionUnit, long closedItemId, int quantity) throws ErrorInfo {
        StatClosedUnit unit = new StatClosedUnit();
        unit.setClosedUnitId(AppConfig.getStatClosedUnitId());
        unit.setClosedItemId(closedItemId);
        unit.setPositionUnitId(originalStatPositionUnit.getUnitId());
        unit.setPositionItemId(originalStatPositionUnit.getPositionItemId());
        unit.setSledContractId(originalStatPositionUnit.getSledContractId());
        unit.setUnitPrice(originalStatPositionUnit.getUnitPrice());
        unit.setUnitQuantity(quantity);
        unit.setDirection(originalStatPositionUnit.getDirection());
        unit.setSourceDataTimestampMs(originalStatPositionUnit.getSourceDataTimestampMs());

        /*
         * more fields
         * */
        ExternalDataSource dataSource = new ExternalDataSource();
        if (targetData != null && originalStatPositionUnit.getSubAccountId() == 0) {
            unit.setSubAccountId(targetData.getSubAccountId());
            unit.setTargetKey(targetData.getTargetKey());
            unit.setTargetType(targetData.getTargetType());
        } else {
            unit.setSubAccountId(originalStatPositionUnit.getSubAccountId());
            unit.setTargetKey(originalStatPositionUnit.getTargetKey());
            unit.setTargetType(originalStatPositionUnit.getTargetType());
        }
        dataSource.setSourceId(originalStatPositionUnit.getSource().getSourceId());
        dataSource.setSourceType(originalStatPositionUnit.getSource().getSourceType());
        unit.setSource(dataSource);

        return unit;
    }

    /**
     * 获取平仓小单元
     */
    protected List<StatClosedUnit> getClosedPositionUnitList(List<StatPositionUnit> positionUnitList, long closedItemId) throws ErrorInfo {
        List<StatClosedUnit> statClosedUnitList = new ArrayList<>();
        StatClosedUnit closedUnit;
        for (StatPositionUnit unit : positionUnitList) {
            closedUnit = getClosedPositionUnit(unit, closedItemId, unit.getUnitQuantity());
            statClosedUnitList.add(closedUnit);
        }
        return statClosedUnitList;
    }

    /**
     * 获取平仓小单元(兼容版)
     */
    protected List<StatClosedUnit> getClosedPositionUnitList(TargetData targetData, List<StatPositionUnit> positionUnitList, long closedItemId) throws ErrorInfo {
        List<StatClosedUnit> statClosedUnitList = new ArrayList<>();
        StatClosedUnit closedUnit;
        for (StatPositionUnit unit : positionUnitList) {
            closedUnit = getClosedPositionUnit(targetData, unit, closedItemId, unit.getUnitQuantity());
            statClosedUnitList.add(closedUnit);
        }
        return statClosedUnitList;
    }

    private void checkClosedData(ClosedData closedData) throws ErrorInfo {
        Map<Long, Integer> originalPositionUnitQuantityMap = new HashMap<>();
        Map<Long, Integer> continuedPositionUnitQuantityMap = new HashMap<>();
        Integer tempQuantity;
        /*
         * 计算原数据各腿的净仓
         * */
        for (PositionData originalPositionData : closedData.getOriginalPositionDataList()) {
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
        for (PositionData continuedPositionData : closedData.getContinuedPositionDataList()) {
            for (StatPositionUnit unit : continuedPositionData.getStatPositionUnitList()) {
                tempQuantity = continuedPositionUnitQuantityMap.get(unit.getSledContractId());
                if (tempQuantity == null) {
                    tempQuantity = 0;
                }
                if (unit.getDirection() == StatDirection.STAT_BUY) {
                    tempQuantity += unit.getUnitQuantity();
                } else {
                    tempQuantity -= unit.getUnitQuantity();
                }
                continuedPositionUnitQuantityMap.put(unit.getSledContractId(), tempQuantity);
            }
        }
        /*
         * 对比前后净仓是否一致
         * */
        for (Map.Entry<Long, Integer> originalPositionUnitQuantityEntry : originalPositionUnitQuantityMap.entrySet()) {
            tempQuantity = continuedPositionUnitQuantityMap.get(originalPositionUnitQuantityEntry.getKey());
            if (tempQuantity == null) {
                tempQuantity = 0;
            }
            StatAssert.doAssert(originalPositionUnitQuantityEntry.getValue().equals(tempQuantity), "unit quantity after close not the same as before");
        }
    }

    /**
     * 计算合约平仓收益
     * 计算公式 ：Sum（（卖价 - 买价）* 平仓量 * 合约乘数） * 计价单位
     */
    public List<ClosedProfit> calculateContractClosedProfit(String targetKey, List<ClosedPositionData> closedPositionDataList/*List<StatClosedPositionItem> statClosedPositionItemList*/) throws ErrorInfo {

        HostingContractHandler hostingContractHandler = new HostingContractHandler();
        SledContractData sledContractData = hostingContractHandler.getSledContractData(Long.parseLong(targetKey));

        List<ClosedProfit> closedProfitList = new ArrayList<ClosedProfit>();
        ClosedProfit closedProfit = new ClosedProfit();

        BigDecimal buyProfitSum = new BigDecimal(0);
        BigDecimal sellProfitSum = new BigDecimal(0);
        BigDecimal tempClosedProfit = null;
//		for (StatClosedPositionItem statClosedPositionItem : statClosedPositionItemList) {
        for (ClosedPositionData closedPositionData : closedPositionDataList) {
            if (closedPositionData.getClosedPositionItem().getDirection() == StatDirection.STAT_BUY) {
                tempClosedProfit = new BigDecimal(new Double(closedPositionData.getClosedPositionItem().getPrice()).toString());
                tempClosedProfit = tempClosedProfit.multiply(new BigDecimal(closedPositionData.getClosedPositionItem().getClosedQuantity()));
                buyProfitSum = buyProfitSum.add(tempClosedProfit);
            } else {
                tempClosedProfit = new BigDecimal(new Double(closedPositionData.getClosedPositionItem().getPrice()).toString());
                tempClosedProfit = tempClosedProfit.multiply(new BigDecimal(closedPositionData.getClosedPositionItem().getClosedQuantity()));
                sellProfitSum = sellProfitSum.add(tempClosedProfit);
            }
        }
        BigDecimal retPrice = sellProfitSum.subtract(buyProfitSum);
        retPrice = retPrice.multiply(new BigDecimal(sledContractData.getContractSize()));  // 每手乘数
        retPrice = retPrice.multiply(new BigDecimal(sledContractData.getChargeUnit()));    // 计价单位

        closedProfit.setClosedProfitValue(retPrice.doubleValue());
        closedProfit.setTradeCurrency(sledContractData.getTradeCurrency());  // 合约的币种
        closedProfitList.add(closedProfit);

        return closedProfitList;
    }

    /**
     * 计算组合平仓收益
     * 按腿的币种分别计算收益总和（这样就不涉及汇率的运算）
     * 计算公式 ：
     * 腿盈亏：Sum（（卖价 - 买价）* 平仓量 * 合约乘数） * 计价单位
     */
    public List<ClosedProfit> calculateComposeClosedProfit(List<ClosedPositionData> closedPositionDataList) throws ErrorInfo {
        HostingContractHandler hostingContractHandler = new HostingContractHandler();
        /*
         * Map<currency, closedProfitBigDecimal>
         * */
        Map<String, BigDecimal> closedProfitMap = new HashMap<>();
        SledContractData sledContractData = null;

        BigDecimal tempClosedProfit = null;
        BigDecimal buySum = null;
        BigDecimal sellSum = null;

        for (ClosedPositionData closedPositionData : closedPositionDataList) {
//			List<StatClosedUnit> closedUnitList = closedPositionData.getClosedUnitList();
            for (StatClosedUnit unit : closedPositionData.getClosedUnitList()) {
                sledContractData = hostingContractHandler.getSledContractData(unit.getSledContractId());
                final String currency = sledContractData.getTradeCurrency();

                if (AppReport.DEBUG) {
                    AppReport.debug("StatClosedPositionHandler ## calculateComposeClosedProfit -- currency : " + currency + ", sledContractData : " + new Gson().toJson(sledContractData));
                }

                if (closedProfitMap.containsKey(currency)) {
                    tempClosedProfit = closedProfitMap.get(currency);
                } else {
                    tempClosedProfit = new BigDecimal(0);
                }

                if (unit.getDirection() == StatDirection.STAT_BUY) {
                    // buy
                    buySum = new BigDecimal(new Double(unit.getUnitPrice()).toString());
                    buySum = buySum.multiply(new BigDecimal(unit.getUnitQuantity()));
                    buySum = buySum.multiply(new BigDecimal(sledContractData.getContractSize()));
                    buySum = buySum.multiply(new BigDecimal(sledContractData.getChargeUnit()));

                    tempClosedProfit = tempClosedProfit.subtract(buySum);
                } else {
                    // sell
                    sellSum = new BigDecimal(new Double(unit.getUnitPrice()).toString());
                    sellSum = sellSum.multiply(new BigDecimal(unit.getUnitQuantity()));
                    sellSum = sellSum.multiply(new BigDecimal(sledContractData.getContractSize()));
                    sellSum = sellSum.multiply(new BigDecimal(sledContractData.getChargeUnit()));

                    tempClosedProfit = tempClosedProfit.add(sellSum);
                }
                closedProfitMap.put(currency, tempClosedProfit);
            }
        }

        List<ClosedProfit> closedProfitList = new ArrayList<ClosedProfit>();
        ClosedProfit closedProfit = null;
        for (Map.Entry<String, BigDecimal> closedProfitEntry : closedProfitMap.entrySet()) {
            closedProfit = new ClosedProfit();
            closedProfit.setTradeCurrency(closedProfitEntry.getKey());
            closedProfit.setClosedProfitValue(closedProfitEntry.getValue().doubleValue());
            closedProfitList.add(closedProfit);
        }
        return closedProfitList;
    }

    /**
     * 批量平仓
     */
    public void batchClosePosition(ClosedData closedData) throws ErrorInfo {

        new DBTransactionHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                // 更新持仓
//				new StatPositionHandler().updateStatPositionItems(getConnection(), statPositionItemList);
                StatPositionHandler statPositionHandler = new StatPositionHandler();
                for (PositionData positionData : closedData.getContinuedPositionDataList()) {
                    if (positionData.getStatPositionItem().getQuantity() == 0) {
                        // 全部被平时，删除持仓数据
                        statPositionHandler.removePosition(getConnection(), positionData.getStatPositionItem().getPositionItemId());
                    } else {
                        statPositionHandler.updatePositionData(getConnection(), positionData.getStatPositionItem(), positionData.getStatPositionUnitList());
                    }
                }

                // 写入平仓明细
//				StatClosedPositionItemTable statClosedPositionItemTable = new StatClosedPositionItemTable(getConnection());
                for (ClosedPositionData closedPositionData : closedData.getClosedPositionDataList()) {
//					statClosedPositionItemTable.insert(statClosedPositionItem);
                    addClosedPositionData(getConnection(), closedPositionData.getClosedPositionItem(), closedPositionData.getClosedUnitList());
                }

                // 写入平仓汇总
                new StatClosedPositionSummaryTable(getConnection()).insert(closedData.getClosedPositionSummary());

                //写入平仓收益
                StatSummaryClosedProfitTable statSummaryClosedProfitTable = new StatSummaryClosedProfitTable(getConnection());
                for (ClosedProfit closedProfit : closedData.getClosedPositionSummary().getClosedProfits()) {
                    statSummaryClosedProfitTable.insert(closedData.getClosedPositionSummary().getClosedId(), closedProfit);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    /**
     * 添加平仓数据
     * 1 平仓明细
     * 2 平仓小单元
     */
    public void addClosedPositionData(Connection conn, StatClosedPositionItem closedPositionItem, List<StatClosedUnit> closedUnitList) throws SQLException, ErrorInfo {
        // 数据较验
        if (closedUnitList.size() < 1) {
            throw StatErrorCode.errorAddPositionWithBlankUnit;
        }
        for (StatClosedUnit unit : closedUnitList) {
            if (closedPositionItem.getClosedItemId() != unit.getClosedItemId()) {
                throw StatErrorCode.errorAddPositionItemIdNotMatch;
            }
        }

        // 1 导入持仓明细项
        new StatClosedPositionItemTable(conn).insert(closedPositionItem);

        // 2 导入持仓小单元
        StatClosedUnitTable closedUnitTable = new StatClosedUnitTable(conn);
        for (StatClosedUnit closedUnit : closedUnitList) {
            closedUnitTable.insert(closedUnit);
        }
    }
}
