package xueqiao.trade.hosting.storage.comm;

import java.sql.SQLException;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.storage.session.HostingSessionTable;

public class SessionDB extends DBBase {
	
	private static SessionDB sInstance;
	public static SessionDB Global() {
		if (sInstance == null) {
			synchronized(SessionDB.class) {
				if (sInstance == null) {
					sInstance = new SessionDB();
				}
			}
		}
		return sInstance;
	}
	
	private SessionDB() {
		super();
		
	}
	@Override
	public int getVersion() {
		return 1;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		new HostingSessionTable(null).onUpgradeOneStep(operator, targetVersion);
	}

	@Override
	protected String getDBName() {
		return "session";
	}
}
