package xueqiao.trade.hosting.asset.storage;

import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.asset.storage.account.sub.*;
import xueqiao.trade.hosting.asset.storage.account.AssetTradeDetailTable;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeAccountNetPositionTable;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeDetailHistoryTable;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssetDB extends DBBase {
    @Override
    protected String getDBName() {
        return "asset";
    }

    @Override
    public int getVersion() {
        return 20;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        List<TableHelper> tables = getAllTables(null);
        for (TableHelper table : tables) {
            ((IDBTable) table).onUpgradeOneStep(idbOperator, targetVersion);
        }
    }

    public List<TableHelper> getAllTables(Connection connection) {
        List<TableHelper> tables = new ArrayList<>();
        tables.add(new AssetTradeDetailTable(connection));
        tables.add(new SettlementPositionDetailTable(connection));
        tables.add(new SettlementTradeDetailTable(connection));
        tables.add(new AssetSubAccountFundTable(connection));
        tables.add(new AssetSubAccountMoneyRecordTable(connection));
        tables.add(new SettlementFundDetailTable(connection));
        tables.add(new AssetSledContractPositionTable(connection));
        tables.add(new NetPositionTradeDetailTable(connection));
        tables.add(new TradeDetailHistoryTable(connection));
        tables.add(new TradeAccountNetPositionTable(connection));
        tables.add(new SubAccountHostingFundTable(connection));
        tables.add(new PreSettlementFundDetailTable(connection));
        tables.add(new PositionAssignHistoryTable(connection));
        return tables;
    }

    private static AssetDB sInstance;

    public static AssetDB Global() {
        if (sInstance == null) {
            synchronized (AssetDB.class) {
                if (sInstance == null) {
                    sInstance = new AssetDB();
                }
            }
        }
        return sInstance;
    }
}
