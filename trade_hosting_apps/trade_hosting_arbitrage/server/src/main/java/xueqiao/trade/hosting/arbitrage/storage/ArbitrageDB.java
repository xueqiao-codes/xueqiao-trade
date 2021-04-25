package xueqiao.trade.hosting.arbitrage.storage;

import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderEffectIndexTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRecordTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecOrderTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecTradeTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQTradeRelatedTable;
import xueqiao.trade.hosting.arbitrage.storage.table.XQTradeTable;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;

import java.sql.SQLException;

public class ArbitrageDB extends DBBase {
    private static ArbitrageDB sInstance;
    public static ArbitrageDB Global() {
        if (sInstance == null) {
            synchronized(ArbitrageDB.class) {
                if (sInstance == null) {
                    sInstance = new ArbitrageDB();
                }
            }
        }
        return sInstance;
    }
    
    @Override
    public int getVersion() {
        return 10;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new XQOrderTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQTradeTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQOrderRelatedExecOrderTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQOrderRelatedExecTradeTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQTradeRelatedTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQOrderEffectIndexTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQOrderRecordTable(null).onUpgradeOneStep(operator, targetVersion);
    }

    @Override
    protected String getDBName() {
        return "arbitrage";
    }

}
