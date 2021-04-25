package xueqiao.trade.hosting.position.statis.service;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.service.bean.ImportUnitData;
import xueqiao.trade.hosting.position.statis.service.handler.SourceAssignPositionHandler;
import xueqiao.trade.hosting.position.statis.service.handler.SourceHostingXQTradeHandler;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.thread.StatSingleWorkerThread;

import java.util.List;

public class ImportDataService {

    /**
     * 从消息总线导入数据
     * 数据源类型有：
     * 1 合约成交
     * 2 组合成交
     * 3 瘸腿成交
     */
    public void importHostingXQTradeData(HostingXQTrade hostingXQTrade) throws TException {
        new StatSingleWorkerThread<Void>() {

            @Override
            protected Void onCall() throws Exception {
                _importHostingXQTradeData(hostingXQTrade);
                return null;
            }
        }.get();
    }

    private void _importHostingXQTradeData(HostingXQTrade hostingXQTrade) throws TException {

        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), "importHostingXQTradeData -- hostingXQTrade : " + hostingXQTrade.toString());
        }

        SourceHostingXQTradeHandler sourceHostingXQTradeHandler = new SourceHostingXQTradeHandler();
        // 数据校验,但不抛异常，只是返回
        if (hostingXQTrade == null) {
            AppReport.warning("hostingXQTrade has received, but hostingXQTrade is null");
            return;
        }
        if (!hostingXQTrade.isSetOrderId() || StringUtils.isBlank(hostingXQTrade.getOrderId())) {
            AppReport.warning("hostingXQTrade has received, but orderId is blank");
            return;
        }
        if (!hostingXQTrade.isSetTradeId() || hostingXQTrade.getTradeId() < 0) {
            AppReport.warning("hostingXQTrade has received, but tradeId is not set or less than 0");
            return;
        }
        if (!hostingXQTrade.isSetSubAccountId() || hostingXQTrade.getSubAccountId() < 0) {
            AppReport.warning("hostingXQTrade has received, but subAccountId is not set or less than 0");
            return;
        }

        // 判断数据是否重复
        if (sourceHostingXQTradeHandler.isHostingXQTradeExist(hostingXQTrade.getOrderId(), hostingXQTrade.getTradeId())) {
            return;
        }

        // 读取成交腿数据
        List<HostingXQTradeRelatedItem> hostingXQTradeRelatedItemList = ArbitrageStubFactory.getStub().getXQTradeRelatedItems(hostingXQTrade.getOrderId(), hostingXQTrade.getTradeId());
        if (hostingXQTradeRelatedItemList != null && hostingXQTradeRelatedItemList.size() < 1) {
            AppReport.warning(new StringBuilder("ImportDataService ## ")
                    .append(" importHostingXQTradeData -- ")
                    .append("hostingXQTradeRelatedItemList is empty(orderId:")
                    .append(hostingXQTrade.getOrderId())
                    .append(",tradeId:")
                    .append(hostingXQTrade.getTradeId())
                    .append(")").toString());
            return;
        }

        if (AppReport.DEBUG) {
            // todo: 后面关注进来的成交数据（组合， 合约）
            AppReport.info("ImportDataService -----  hostingXQTrade : " + hostingXQTrade.toString() + ", hostingXQTradeRelatedItemList : " + new Gson().toJson(hostingXQTradeRelatedItemList));
        }

        // 做入库前数据运算 ---- 持仓明细
        StatPositionItem statPositionItem = sourceHostingXQTradeHandler.transferToStatPositionItem(hostingXQTrade);

        // 做入库前数据运算 ---- 持仓小单元
//		List<StatPositionUnit> statPositionUnitList = sourceHostingXQTradeHandler.transferToStatPositionUnitList(statPositionItem, hostingXQTradeRelatedItemList);
        ImportUnitData importUnitData = sourceHostingXQTradeHandler.transferToStatPositionUnitList(statPositionItem, hostingXQTradeRelatedItemList);

        // 事务中导入持仓数据
        sourceHostingXQTradeHandler.importHostingXQTradeData(hostingXQTrade, statPositionItem, importUnitData);

        // 重新计算持仓汇总
        new StatPositionHandler().calculateAndUpdateStatPositionSummary(statPositionItem.getSubAccountId(), statPositionItem.getTargetKey(), statPositionItem.getTargetType());
    }

    /**
     * 其他模块导入分配数据
     * 数据源类型有：
     * 1 合约分配
     */
    public void importAssignTradeData(PositionAssigned positionAssigned) throws ErrorInfo {
        new StatSingleWorkerThread<Void>() {

            @Override
            protected Void onCall() throws Exception {
                _importAssignTradeData(positionAssigned);
                return null;
            }
        }.get();
    }

    private void _importAssignTradeData(PositionAssigned positionAssigned) throws ErrorInfo {

        if (AppReport.TRACE) {
            AppReport.trace(this.getClass(), "importAssignTradeData -- positionAssigned : " + positionAssigned.toString());
        }

        SourceAssignPositionHandler sourceAssignPositionHandler = new SourceAssignPositionHandler();
        // 数据校验
        sourceAssignPositionHandler.verify(positionAssigned);

        // 判断数据是否重复
        if (sourceAssignPositionHandler.isAssignPositionExist(positionAssigned.getAssignId())) {
            return;
        }

        // 做入库前数据运算 ---- 持仓明细
        StatPositionItem statPositionItem = sourceAssignPositionHandler.transferToStatPositionItem(positionAssigned);

        // 做入库前数据运算 ---- 持仓小单元
        List<StatPositionUnit> statPositionUnitList = sourceAssignPositionHandler.transferToStatPositionUnitList(statPositionItem, positionAssigned);

        /* 在事务中写入分配持仓数据 */
        sourceAssignPositionHandler.importAssignPositionData(positionAssigned, statPositionItem, statPositionUnitList);

        // 重新计算持仓汇总
        new StatPositionHandler().calculateAndUpdateStatPositionSummary(statPositionItem.getSubAccountId(), statPositionItem.getTargetKey(), statPositionItem.getTargetType());
    }

}
