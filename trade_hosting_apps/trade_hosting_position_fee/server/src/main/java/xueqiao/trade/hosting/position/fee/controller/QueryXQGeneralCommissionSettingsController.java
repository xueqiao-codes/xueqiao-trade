package xueqiao.trade.hosting.position.fee.controller;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;

import java.sql.Connection;

public class QueryXQGeneralCommissionSettingsController implements IController {

    private long subAccountId;

    public QueryXQGeneralCommissionSettingsController(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        Precondition.check(subAccountId > 0, "invalid subAccountId");
    }

    public XQGeneralCommissionSettings doQuery() throws ErrorInfo {
        return new DBQueryHelper<XQGeneralCommissionSettings>(PositionFeeDB.Global()) {
            @Override
            protected XQGeneralCommissionSettings onQuery(Connection connection) throws Exception {
                return new XQGeneralCommissionSettingsTable(connection).query(subAccountId);
            }
        }.query();
    }
}
