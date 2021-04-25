package xueqiao.trade.hosting.history.storage;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.history.storage.table.HostingXQOrderHisIndexTable;
import xueqiao.trade.hosting.history.storage.table.HostingXQTradeHisIndexTable;

import java.sql.SQLException;

public class HistoryDB extends DBBase {
    private static HistoryDB sInstance;
    public static HistoryDB Global() {
        if (sInstance == null) {
            synchronized (HistoryDB.class) {
                if (sInstance == null) {
                    sInstance = new HistoryDB();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getDBName() {
        return "history";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new HostingXQOrderHisIndexTable(null).onUpgradeOneStep(operator, targetVersion);
        new HostingXQTradeHisIndexTable(null).onUpgradeOneStep(operator, targetVersion);
    }
}
