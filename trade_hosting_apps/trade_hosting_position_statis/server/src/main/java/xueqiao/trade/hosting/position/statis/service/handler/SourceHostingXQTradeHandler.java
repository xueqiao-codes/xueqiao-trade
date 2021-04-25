package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeRelatedItem;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.app.Constant;
import xueqiao.trade.hosting.position.statis.service.bean.ImportUnitData;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceHostingXQTrade;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceHostingXQTradeRelatedItem;
import xueqiao.trade.hosting.position.statis.storage.table.SourceHostingXQTradeRelatedItemTable;
import xueqiao.trade.hosting.position.statis.storage.table.SourceHostingXQTradeTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SourceHostingXQTradeHandler {

    /**
     * 判断成交记录是否已存在
     */
    public boolean isHostingXQTradeExist(String orderId, long tradeId) throws ErrorInfo {
        SourceHostingXQTrade sourceHostingXQTrade = new DBQueryHelper<SourceHostingXQTrade>(PositionStatisDB.Global()) {
            @Override
            protected SourceHostingXQTrade onQuery(Connection connection) throws Exception {
                return new SourceHostingXQTradeTable(connection).getSourceHostingXQTrade(orderId, tradeId);
            }
        }.query();
        if (sourceHostingXQTrade != null) {
            return true;
        }
        return false;
    }

    /**
     * 将交易信息转为持仓明细信息
     */
    public StatPositionItem transferToStatPositionItem(HostingXQTrade hostingXQTrade) throws ErrorInfo {
        StatPositionItem statPositionItem = new StatPositionItem();

        statPositionItem.setPositionItemId(AppConfig.getStatPositionItemId());
        statPositionItem.setTargetKey(hostingXQTrade.getTradeTarget().getTargetKey());
        statPositionItem.setSubAccountId(hostingXQTrade.getSubAccountId());
        statPositionItem.setTargetType(hostingXQTrade.getTradeTarget().getTargetType());
        statPositionItem.setPrice(hostingXQTrade.getTradePrice());
        statPositionItem.setQuantity(hostingXQTrade.getTradeVolume());
        if (hostingXQTrade.getTradeDiretion() == HostingXQTradeDirection.XQ_BUY) {
            statPositionItem.setDirection(StatDirection.STAT_BUY);
        } else {
            statPositionItem.setDirection(StatDirection.STAT_SELL);
        }
        StatDataSource statDataSource = new StatDataSource();
        statDataSource.setSourceDataTimestampMs(hostingXQTrade.getCreateTimestampMs());
        if (hostingXQTrade.getTradeTarget().getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            statDataSource.setSourceDataChannel(DataSourceChannel.FROM_XQ_COMPOSE_TRADE);
        } else if (hostingXQTrade.getTradeTarget().getTargetType() == HostingXQTargetType.CONTRACT_TARGET
                && hostingXQTrade.getSourceOrderTarget().getTargetType() == HostingXQTargetType.COMPOSE_TARGET) {
            statDataSource.setSourceDataChannel(DataSourceChannel.FROM_XQ_PARTIAL_COMPOSE_TRADE);
        } else {
            statDataSource.setSourceDataChannel(DataSourceChannel.FROM_XQ_CONTRACT_TRADE);
        }
        statPositionItem.setSource(statDataSource);

        return statPositionItem;
    }

    /**
     * 将交易关联信息转为持仓小单元列表
     */
    public /*List<StatPositionUnit>*/ImportUnitData transferToStatPositionUnitList(StatPositionItem statPositionItem, List<HostingXQTradeRelatedItem> hostingXQTradeRelatedItemList) throws ErrorInfo {
        ImportUnitData importUnitData = new ImportUnitData();
//        List<StatPositionUnit> statPositionUnitList = new ArrayList<StatPositionUnit>();
//        List<SourceHostingXQTradeRelatedItem> relatedItemList;

        for (HostingXQTradeRelatedItem hostingXQTradeRelatedItem : hostingXQTradeRelatedItemList) {
            /*
             * related item
             * */
            SourceHostingXQTradeRelatedItem relatedItem = new SourceHostingXQTradeRelatedItem();
            relatedItem.setSourceId(AppConfig.getSourceHostingXQTradeRelatedItemSourceId());
            relatedItem.setSubAccountId(statPositionItem.getSubAccountId());
            relatedItem.setCompleted(false);
            relatedItem.setOrderId(hostingXQTradeRelatedItem.getOrderId());
            relatedItem.setTradeId(hostingXQTradeRelatedItem.getTradeId());
            relatedItem.setExecOrderId(hostingXQTradeRelatedItem.getExecOrderId());
            relatedItem.setExecTradeId(hostingXQTradeRelatedItem.getExecTradeId());
            relatedItem.setExecTradeLegId(hostingXQTradeRelatedItem.getExecTradeLegId());
            relatedItem.setExecTradeLegDirection(hostingXQTradeRelatedItem.getExecTradeLegDirection());
            relatedItem.setExecTradeLegPrice(hostingXQTradeRelatedItem.getExecTradeLegPrice());
            relatedItem.setRelatedTradeVolume(hostingXQTradeRelatedItem.getRelatedTradeVolume());
            relatedItem.setSledContractId(hostingXQTradeRelatedItem.getSledContractId());
            relatedItem.setCreateTimestampMs(hostingXQTradeRelatedItem.getCreateTimestampMs());

            importUnitData.addToRelatedItemList(relatedItem);

            /*
             * position unit
             * */
            StatPositionUnit statPositionUnit = new StatPositionUnit();

            statPositionUnit.setUnitId(AppConfig.getStatPositionUnitId());
            statPositionUnit.setPositionItemId(statPositionItem.getPositionItemId());
            statPositionUnit.setSledContractId(hostingXQTradeRelatedItem.getSledContractId());
            statPositionUnit.setUnitPrice(hostingXQTradeRelatedItem.getExecTradeLegPrice());
            statPositionUnit.setUnitQuantity(hostingXQTradeRelatedItem.getRelatedTradeVolume());
            /*
             * 关联信息里给的方向是最终买卖的方向，并非与组合里腿的方向一致
             * 对应的，后面需要拆组合时，直接按小单元的方向拆
             * */
            if (hostingXQTradeRelatedItem.getExecTradeLegDirection() == HostingExecTradeDirection.TRADE_BUY) {
                statPositionUnit.setDirection(StatDirection.STAT_BUY);
            } else {
                statPositionUnit.setDirection(StatDirection.STAT_SELL);
            }
            statPositionUnit.setSourceDataTimestampMs(hostingXQTradeRelatedItem.getCreateTimestampMs());

            /*
             * more fields
             * */
            ExternalDataSource dataSource = new ExternalDataSource();
            statPositionUnit.setSubAccountId(statPositionItem.getSubAccountId());
            statPositionUnit.setTargetKey(statPositionItem.getTargetKey());
            statPositionUnit.setTargetType(statPositionItem.getTargetType());
            dataSource.setSourceType(SourceType.ST_TRADE);
            dataSource.setSourceId(relatedItem.getSourceId());
            statPositionUnit.setSource(dataSource);

            importUnitData.addToPositionUnitList(statPositionUnit);
        }
        return importUnitData;
    }

    /**
     * 成交数据入库
     */
    public void importHostingXQTradeData(HostingXQTrade hostingXQTrade, StatPositionItem statPositionItem, ImportUnitData importUnitData) throws ErrorInfo {
        new DBTransactionHelper(PositionStatisDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                // 1 写入成交数据备份数据（保留一个月的数据）
                new SourceHostingXQTradeTable(getConnection()).addSourceXQTradeItem(hostingXQTrade);

                // 2 写入成交关联数据
                SourceHostingXQTradeRelatedItemTable sourceHostingXQTradeRelatedItemTable = new SourceHostingXQTradeRelatedItemTable(getConnection());
                for (SourceHostingXQTradeRelatedItem item : importUnitData.getRelatedItemList()) {
                    sourceHostingXQTradeRelatedItemTable.insert(item);
                }

                // 3 写入持仓数据
                new StatPositionHandler().addPositionData(getConnection(), statPositionItem, importUnitData.getPositionUnitList());
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    /**
     * 删除一个月前的数据
     */
    public void deleteHostingXQTradeOneMonthBefore() throws ErrorInfo {
        new DBStepHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                long millisOneMonthBefore = System.currentTimeMillis() - Constant.MOLLIS_30_DAY;
                new SourceHostingXQTradeTable(getConnection()).delete(millisOneMonthBefore);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
