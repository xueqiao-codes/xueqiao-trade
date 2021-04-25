package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.app.Constant;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceAssignedPosition;
import xueqiao.trade.hosting.position.statis.storage.table.SourceAssignPositionTable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SourceAssignPositionHandler {

    /**
     * 检查参数的合法性
     */
    public void verify(PositionAssigned positionAssigned) throws ErrorInfo {
        ParameterChecker.checkNotNull(positionAssigned, "positionAssigned");
        ParameterChecker.check(positionAssigned.getAssignId() > 0, "assignId should be larger than zero");
        ParameterChecker.check(positionAssigned.getSubAccountId() > 0, "subAccountId should be larger than zero");
        ParameterChecker.check(positionAssigned.getSledContractId() > 0, "sledContractId should be larger than zero");
    }

    /**
     * 判断分配持仓记录是否已存在
     */
    public boolean isAssignPositionExist(long assignId) throws ErrorInfo {
        SourceAssignedPosition sourceAssignedPosition = new DBQueryHelper<SourceAssignedPosition>(PositionStatisDB.Global()) {
            @Override
            protected SourceAssignedPosition onQuery(Connection connection) throws Exception {
                return new SourceAssignPositionTable(connection).getSourceAssignPositionItem(assignId);
            }
        }.query();
        if (sourceAssignedPosition != null) {
            return true;
        }
        return false;
    }

    /**
     * 将分配持仓信息转为持仓明细信息
     */
    public StatPositionItem transferToStatPositionItem(PositionAssigned positionAssigned) throws ErrorInfo {
        StatPositionItem statPositionItem = new StatPositionItem();

        statPositionItem.setPositionItemId(AppConfig.getStatPositionItemId());
        statPositionItem.setTargetKey(String.valueOf(positionAssigned.getSledContractId()));
        statPositionItem.setSubAccountId(positionAssigned.getSubAccountId());
        statPositionItem.setTargetType(HostingXQTargetType.CONTRACT_TARGET);
        statPositionItem.setPrice(positionAssigned.getPrice());
        statPositionItem.setQuantity(positionAssigned.getVolume());
        if (positionAssigned.getPositionDirection() == PositionDirection.POSITION_BUY) {
            statPositionItem.setDirection(StatDirection.STAT_BUY);
        } else {
            statPositionItem.setDirection(StatDirection.STAT_SELL);
        }
        StatDataSource statDataSource = new StatDataSource();
        statDataSource.setSourceDataTimestampMs(positionAssigned.getPositionTimestampMs());
        statDataSource.setSourceDataChannel(DataSourceChannel.FROM_CONTRACT_ASSIGNATION);
        statPositionItem.setSource(statDataSource);

        return statPositionItem;
    }

    /**
     * 将分配持仓信息转为持仓小单元列表
     */
    public List<StatPositionUnit> transferToStatPositionUnitList(StatPositionItem statPositionItem, PositionAssigned positionAssigned) throws ErrorInfo {
        List<StatPositionUnit> statPositionUnitList = new ArrayList<StatPositionUnit>();

        StatPositionUnit statPositionUnit = new StatPositionUnit();

        statPositionUnit.setUnitId(AppConfig.getStatPositionUnitId());
        statPositionUnit.setPositionItemId(statPositionItem.getPositionItemId());
        statPositionUnit.setSledContractId(positionAssigned.getSledContractId());
        statPositionUnit.setUnitPrice(positionAssigned.getPrice());
        statPositionUnit.setUnitQuantity(positionAssigned.getVolume());
        /*
         * 标的为合约的持仓小单元只是做为一个补齐结构，与组合统一起来。
         * ATTENTION: 小单元的买卖方向就是最终的买卖方向。
         * 因为分配的是合约，所以，小单元里的买卖方向与item的买卖方向一致
         * */
        statPositionUnit.setDirection(statPositionItem.getDirection());
        statPositionUnit.setSourceDataTimestampMs(positionAssigned.getPositionTimestampMs());

        /*
         * more fields
         * */
        ExternalDataSource dataSource = new ExternalDataSource();
        statPositionUnit.setSubAccountId(statPositionItem.getSubAccountId());
        statPositionUnit.setTargetKey(statPositionItem.getTargetKey());
        statPositionUnit.setTargetType(statPositionItem.getTargetType());
        dataSource.setSourceType(SourceType.ST_ASSIGN);
        dataSource.setSourceId(positionAssigned.getAssignId());
        statPositionUnit.setSource(dataSource);

        statPositionUnitList.add(statPositionUnit);
        return statPositionUnitList;
    }

    /**
     * 导入分配持仓数据到持仓明细
     */
    public void importAssignPositionData(PositionAssigned positionAssigned, StatPositionItem statPositionItem, List<StatPositionUnit> statPositionUnitList) throws ErrorInfo {
        new DBTransactionHelper(PositionStatisDB.Global()) {

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                // 1 写入成交数据备份数据（保留一个月的数据）
                new SourceAssignPositionTable(getConnection()).addSourceAssignPositionItem(positionAssigned);

                // 2 写入持仓数据
                new StatPositionHandler().addPositionData(getConnection(), statPositionItem, statPositionUnitList);
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
    public void deleteAssignPositionOneMonthBefore() throws ErrorInfo {
        new DBStepHelper<Void>(PositionStatisDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                long millisOneMonthBefore = System.currentTimeMillis() - Constant.MOLLIS_30_DAY;
                new SourceAssignPositionTable(getConnection()).delete(millisOneMonthBefore);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
