package xueqiao.trade.hosting.position.adjust.storage;

import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionAdjustDB extends DBBase {
    @Override
    protected String getDBName() {
        return "position_adjust";
    }

    @Override
    public int getVersion() {
        return 5;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        List<TableHelper> tables = getAllTables(null);
        for (TableHelper table : tables) {
            ((IDBTable) table).onUpgradeOneStep(idbOperator, targetVersion);
        }
    }

    private static PositionAdjustDB sInstance;

    public static PositionAdjustDB Global() {
        if (sInstance == null) {
            synchronized (PositionAdjustDB.class) {
                if (sInstance == null) {
                    sInstance = new PositionAdjustDB();
                }
            }
        }
        return sInstance;
    }

    public List<TableHelper> getAllTables(Connection connection) {
        List<TableHelper> tables = new ArrayList<>();
        tables.add(new PositionManualInputTable(connection));
        tables.add(new PositionAssignedTable(connection));
        tables.add(new PositionUnassignedTable(connection));
        tables.add(new PositionVerifyTable(connection));
        tables.add(new PositionDifferenceTable(connection));
        tables.add(new NetPositionManualInputTable(connection));
        tables.add(new PositionAssignTaskTable(connection));
        tables.add(new PositionEditLockTable(connection));
        tables.add(new PositionDailyDifferenceTable(connection));
        tables.add(new PrePositionDifferenceTable(connection));
        return tables;
    }

}
