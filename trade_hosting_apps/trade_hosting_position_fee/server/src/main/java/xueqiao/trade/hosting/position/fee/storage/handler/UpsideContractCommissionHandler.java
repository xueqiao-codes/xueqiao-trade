package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractCommissionTable;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommission;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UpsideContractCommissionHandler {

    public static UpsideContractCommission query(long subAccountId, long commodityId, String contractCode) throws SQLException, ErrorInfo {
        return new DBQueryHelper<UpsideContractCommission>(PositionFeeDB.Global()) {
            @Override
            protected UpsideContractCommission onQuery(Connection connection) throws Exception {
                return new UpsideContractCommissionTable(connection).query(subAccountId, commodityId, contractCode);
            }
        }.query();
    }

    public static List<UpsideContractCommission> queryUnsyncList(int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<UpsideContractCommission>>(PositionFeeDB.Global()) {
            @Override
            protected List<UpsideContractCommission> onQuery(Connection connection) throws Exception {
                return new UpsideContractCommissionTable(connection).queryUnsyncList(pageIndex, pageSize);
            }
        }.query();
    }
}
