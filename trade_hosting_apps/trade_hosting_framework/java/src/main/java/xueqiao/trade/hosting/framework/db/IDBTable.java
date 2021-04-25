package xueqiao.trade.hosting.framework.db;

import java.sql.SQLException;

public interface IDBTable {
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException;
}
