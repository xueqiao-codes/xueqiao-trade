package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideOriginalPositionRateTable;

import java.sql.Connection;
import java.util.List;

public class UpsideOriginalPositionRateHandler {

    public static List<UpsidePositionRate> queryDirtyDatas() throws ErrorInfo {
        return new DBQueryHelper<List<UpsidePositionRate>>(PositionFeeDB.Global()) {
            @Override
            protected List<UpsidePositionRate> onQuery(Connection connection) throws Exception {
                UpsideOriginalPositionRateTable table = new UpsideOriginalPositionRateTable(connection);
                return table.queryDirtyDatas();
            }
        }.query();
    }

    public static List<UpsidePositionRate> query(long tradeAccountId, long commodityId) throws ErrorInfo {
        return new DBQueryHelper<List<UpsidePositionRate>>(PositionFeeDB.Global()) {
            @Override
            protected List<UpsidePositionRate> onQuery(Connection connection) throws Exception {
                UpsideOriginalPositionRateTable table = new UpsideOriginalPositionRateTable(connection);
                return table.query(tradeAccountId, commodityId);
            }
        }.query();
    }
}
