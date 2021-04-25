package xueqiao.trade.hosting.storage.comm;

import java.sql.SQLException;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.storage.compose.HostingComposeGraphTable;
import xueqiao.trade.hosting.storage.compose.HostingComposeViewTable;

public class ComposeDB extends DBBase {
	private static ComposeDB sInstance;
	public static ComposeDB Global() {
		if (sInstance == null) {
			synchronized(ComposeDB.class) {
				if (sInstance == null) {
					sInstance = new ComposeDB();
				}
			}
		}
		return sInstance;
	}
	
	@Override
	public int getVersion() {
		return 2;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		new HostingComposeGraphTable(null).onUpgradeOneStep(operator, targetVersion);
		new HostingComposeViewTable(null).onUpgradeOneStep(operator, targetVersion);
	}

	@Override
	protected String getDBName() {
		return "compose";
	}

}
