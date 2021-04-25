package xueqiao.trade.hosting.dealing.storage;

import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderCleanIndexTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderInDealingTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecOrderTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecTradeLegTableV2;
import xueqiao.trade.hosting.dealing.storage.table.HostingExecTradeTableV2;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;

import java.sql.SQLException;

/**
 * 存储订单执行的相关数据
 * @author wangli
 *
 */
public class DealingDBV2 extends DBBase {
	private static DealingDBV2 sInstance;
	public static DealingDBV2 Global() {
		if (sInstance == null) {
			synchronized (DealingDBV2.class) {
				if (sInstance == null) {
					sInstance = new DealingDBV2();
				}
			}
		}
		return sInstance;
	}


	@Override
	public int getVersion() {
		return 9;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
    	new HostingExecOrderTableV2(null).onUpgradeOneStep(operator, targetVersion);
    	new HostingExecTradeTableV2(null).onUpgradeOneStep(operator, targetVersion);
    	new HostingExecTradeLegTableV2(null).onUpgradeOneStep(operator, targetVersion);
    	new HostingExecOrderInDealingTableV2(null).onUpgradeOneStep(operator, targetVersion);
    	new HostingExecOrderCleanIndexTableV2(null).onUpgradeOneStep(operator, targetVersion);
	}

	@Override
	protected String getDBName() {
		return "dealing_v2";
	}

}
