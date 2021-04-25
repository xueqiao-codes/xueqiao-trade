package xueqiao.trade.hosting.framework.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.soldier.base.logger.AppLog;

import com.google.common.base.Preconditions;

public class DBBuilder {
	
	private String mDbName;
	private Connection mConn;
	private IDBUpdator mUpdator;
	private DBGlobalProperties mGlobalProperties;
	
	public DBBuilder(
			String dbName
			, Connection conn
			, IDBUpdator updator) {
		Preconditions.checkArgument(!StringUtils.isEmpty(dbName));
		Preconditions.checkArgument(conn != null);
		Preconditions.checkArgument(updator != null);
		this.mDbName = dbName;
		this.mConn = conn;
		this.mUpdator = updator;
		this.mGlobalProperties = new DBGlobalProperties(this.mConn);
	}
	
	public void buildIfNeeded() throws SQLException {
		boolean success = false;
		mConn.setAutoCommit(false);
		try {
			mGlobalProperties.buildPreconditions();
			int currentVersion = getCurrentDBVersion();
			int updatorVersion = mUpdator.getVersion();
			Preconditions.checkState(updatorVersion > 0);
			
			if (currentVersion == updatorVersion) {
				success = true;
				return ;
			}
			
			// 如果涉及降级操作，会有一个代码版本不对等的，但是当我们版本回退的时候，老版本的软件包里面实际上是没有
			// 降级代码。降级后升级重新发布的时候出现升级出错，重复添加字段之类的问题
			// 因此，DB降级是危险的。因此这里体系上必须保证DB升级后的代码不影响低版本的软件的运行
			// 通常的添加字段，字段增长等操作都是可以保证这些
			IDBOperator dbOperator = new DBOperatorImpl(mConn);
			if (currentVersion < updatorVersion) {
				AppLog.i("UpgradeDB " + mDbName + " start...");
				for (int upgradeVersion = currentVersion + 1; upgradeVersion <= updatorVersion; upgradeVersion++) {
					AppLog.i("Upgrade from version=" + (upgradeVersion-1) + " to " + upgradeVersion);
					mUpdator.onUpgradeOneStep(dbOperator, upgradeVersion);
				}
				updateDBVersion(updatorVersion);
				AppLog.i("UpgradeDB " + mDbName + " end...");
			} else {
				AppLog.w("Running " + mDbName + " Using Higher DB Version=" 
							+ currentVersion + " for CodeVersion=" + updatorVersion);
			}
			success = true;
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			mConn.rollback();
			throw new SQLException(e.getMessage(), e);
		} finally {
			if (success) {
				mConn.commit();
			}
		}
	}
	
	private int getCurrentDBVersion() throws SQLException {
		String dbVersion = mGlobalProperties.getProperty(DBGlobalPropetiesInnerKey.KEY_DB_VERSION);
		if (StringUtils.isEmpty(dbVersion)) {
			return 0;
		}
		return NumberUtils.createNumber(dbVersion).intValue();
	}
	
	private void updateDBVersion(int toVersion) throws SQLException {
		mGlobalProperties.setProperty(
				DBGlobalPropetiesInnerKey.KEY_DB_VERSION
				, String.valueOf(toVersion));
	}
	
	
	
}
