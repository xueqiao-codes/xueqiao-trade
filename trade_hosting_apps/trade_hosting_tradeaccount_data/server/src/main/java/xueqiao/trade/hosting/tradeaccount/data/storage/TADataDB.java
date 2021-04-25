package xueqiao.trade.hosting.tradeaccount.data.storage;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundHisTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundNowTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.NetPositionSummaryTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.SettlementInfoTable;

import java.sql.SQLException;

public class TADataDB extends DBBase {

    private static TADataDB sInstance;
    public static TADataDB Global() {
        if (sInstance == null) {
            synchronized(TADataDB.class) {
                if (sInstance == null) {
                    sInstance = new TADataDB();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getDBName() {
        return "ta_data";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new FundNowTable(null).onUpgradeOneStep(operator, targetVersion);
        new FundHisTable(null).onUpgradeOneStep(operator, targetVersion);
        new NetPositionSummaryTable(null).onUpgradeOneStep(operator, targetVersion);
        new SettlementInfoTable(null).onUpgradeOneStep(operator, targetVersion);
    }
}
