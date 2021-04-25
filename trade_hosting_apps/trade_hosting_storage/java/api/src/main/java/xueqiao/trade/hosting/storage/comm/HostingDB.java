package xueqiao.trade.hosting.storage.comm;

import java.sql.SQLException;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.storage.config.HostingConfigFileTable;
import xueqiao.trade.hosting.storage.subaccount.HostingSubAccountMRTable;
import xueqiao.trade.hosting.storage.subaccount.HostingSubAccountRelatedTable;
import xueqiao.trade.hosting.storage.subaccount.HostingSubAccountTable;
import xueqiao.trade.hosting.storage.tradeaccount.HostingTradeAccountTable;
import xueqiao.trade.hosting.storage.user.HostingUserTable;

public class HostingDB extends DBBase {
	private static HostingDB gInstance;

	public static HostingDB Global() {
		if (gInstance == null) {
			synchronized (HostingDB.class) {
				if (gInstance == null) {
					gInstance = new HostingDB();
				}
			}
		}
		return gInstance;
	}
	
	protected HostingDB() {
		super();
	}

	@Override
	public int getVersion() {
		return 11;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		new HostingUserTable(null).onUpgradeOneStep(operator, targetVersion);
		new HostingTradeAccountTable(null).onUpgradeOneStep(operator, targetVersion);
		new HostingConfigFileTable(null).onUpgradeOneStep(operator, targetVersion);
		
		new HostingSubAccountTable(null).onUpgradeOneStep(operator, targetVersion);
		new HostingSubAccountMRTable(null).onUpgradeOneStep(operator, targetVersion);
		new HostingSubAccountRelatedTable(null).onUpgradeOneStep(operator, targetVersion);;
	}

	@Override
	protected String getDBName() {
		return "hosting";
	}

}
