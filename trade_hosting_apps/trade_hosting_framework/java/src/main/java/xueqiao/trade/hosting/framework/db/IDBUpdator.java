package xueqiao.trade.hosting.framework.db;

import java.sql.SQLException;

/**
 *  负责构建DB结构的构建
 * @author wileywang
 */
public interface IDBUpdator {
	/**
	 *  当前使用DB的版本号
	 */
	public int getVersion();
	
	/**
	 *  升级操作, 从targetVersion-1升级到targetVersion
	 */
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException;
	
}
