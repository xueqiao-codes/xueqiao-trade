package xueqiao.trade.hosting.position.fee.controller;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.base.Precondition;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings;

import java.sql.Connection;

public class QueryXQGeneralMarginSettingsController implements IController {

    private long subAccountId;

    public QueryXQGeneralMarginSettingsController(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        Precondition.check(subAccountId > 0, "invalid subAccountId");
    }

    public XQGeneralMarginSettings doQuery() throws ErrorInfo {
        return new DBQueryHelper<XQGeneralMarginSettings>(PositionFeeDB.Global()) {
            @Override
            protected XQGeneralMarginSettings onQuery(Connection connection) throws Exception {
                return new XQGeneralMarginSettingsTable(connection).query(subAccountId);
            }
        }.query();
    }
}
