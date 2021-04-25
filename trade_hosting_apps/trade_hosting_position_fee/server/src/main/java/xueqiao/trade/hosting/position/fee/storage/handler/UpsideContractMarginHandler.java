package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractMarginTable;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMargin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UpsideContractMarginHandler {

    public static UpsideContractMargin query(long subAccountId, long commodityId, String contractCode) throws SQLException, ErrorInfo {
        return new DBQueryHelper<UpsideContractMargin>(PositionFeeDB.Global()) {
            @Override
            protected UpsideContractMargin onQuery(Connection connection) throws Exception {
                return new UpsideContractMarginTable(connection).query(subAccountId, commodityId, contractCode);
            }
        }.query();
    }

    public static List<UpsideContractMargin> queryUnsyncList(int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<UpsideContractMargin>>(PositionFeeDB.Global()) {
            @Override
            protected List<UpsideContractMargin> onQuery(Connection connection) throws Exception {
                return new UpsideContractMarginTable(connection).queryUnsyncList(pageIndex, pageSize);
            }
        }.query();
    }
}
