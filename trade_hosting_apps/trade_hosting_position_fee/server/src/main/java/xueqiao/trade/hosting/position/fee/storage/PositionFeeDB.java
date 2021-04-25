package xueqiao.trade.hosting.position.fee.storage;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.position.fee.storage.table.*;

import java.sql.SQLException;

public class PositionFeeDB extends DBBase {

    private static PositionFeeDB sInstance;

    public static PositionFeeDB Global() {
        if (sInstance == null) {
            synchronized (PositionFeeDB.class) {
                if (sInstance == null) {
                    sInstance = new PositionFeeDB();
                }
            }
        }
        return sInstance;
    }

    @Override
    protected String getDBName() {
        return "position_fee";
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        new UpsideOriginalPositionRateTable(null).onUpgradeOneStep(operator, targetVersion);

        new UpsideContractCommissionTable(null).onUpgradeOneStep(operator, targetVersion);
        new UpsideContractMarginTable(null).onUpgradeOneStep(operator, targetVersion);

        new XQContractCommissionTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQContractMarginTable(null).onUpgradeOneStep(operator, targetVersion);

        new XQGeneralCommissionSettingsTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQGeneralMarginSettingsTable(null).onUpgradeOneStep(operator, targetVersion);

        new XQSpecCommissionSettingsTable(null).onUpgradeOneStep(operator, targetVersion);
        new XQSpecMarginSettingsTable(null).onUpgradeOneStep(operator, targetVersion);
    }
}
