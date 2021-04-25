package xueqiao.trade.hosting.contract.api;

import java.sql.SQLException;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;

public class ContractInfoDB extends DBBase {
    private static ContractInfoDB gInstance;
    public static ContractInfoDB Global() {
        if (gInstance == null) {
            synchronized (ContractInfoDB.class) {
                if (gInstance == null) {
                    gInstance = new ContractInfoDB();
                }
            }
        }
        return gInstance;
    }
    
    private ContractInfoDB() {
    }
    
    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new ContractVersionTable(null).onUpgradeOneStep(operator, targetVersion);
    }

    @Override
    protected String getDBName() {
        return "cinfo";
    }

}
