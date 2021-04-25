package org.soldier.platform.id_maker_dao.storage;

import org.soldier.platform.id_maker_dao.storage.table.AllocIdBySectTable;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;

import java.sql.SQLException;

public class IdMakerDB extends DBBase {
    private static IdMakerDB sInstance;
    public static IdMakerDB Global() {
        if (sInstance == null) {
            synchronized (IdMakerDB.class) {
                if (sInstance == null) {
                    sInstance = new IdMakerDB();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getDBName() {
        return "id_maker";
    }

    @Override
    public int getVersion() {
        return 6;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new AllocIdBySectTable().onUpgradeOneStep(operator, targetVersion);
    }
}
