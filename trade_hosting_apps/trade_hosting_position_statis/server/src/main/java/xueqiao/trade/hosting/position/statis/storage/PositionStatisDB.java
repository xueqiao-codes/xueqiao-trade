package xueqiao.trade.hosting.position.statis.storage;

import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.position.statis.storage.table.*;

import java.sql.SQLException;

public class PositionStatisDB extends DBBase {
	private static PositionStatisDB sInstance;

	public static PositionStatisDB Global() {
		if (sInstance == null) {
			synchronized (PositionStatisDB.class) {
				if (sInstance == null) {
					sInstance = new PositionStatisDB();
				}
			}
		}
		return sInstance;
	}

	@Override
	protected String getDBName() {
		return "position_statis";
	}

	@Override
	public int getVersion() {
		return 4;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		new SourceAssignPositionTable(null).onUpgradeOneStep(operator, targetVersion);
		new SourceHostingXQTradeTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatPositionSummaryTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatPositionItemTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatPositionUnitTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatClosedPositionSummaryTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatClosedPositionItemTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatArchiveSummaryTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatArchiveItemTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatArchiveDateSummaryTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatSummaryClosedProfitTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatDateSummaryClosedProfitTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatClosedUnitTable(null).onUpgradeOneStep(operator, targetVersion);
		new StatArchiveUnitTable(null).onUpgradeOneStep(operator, targetVersion);
		// todo: 添加一张完备归档小单元表
		new SourceHostingXQTradeRelatedItemTable(null).onUpgradeOneStep(operator, targetVersion);
	}
}
