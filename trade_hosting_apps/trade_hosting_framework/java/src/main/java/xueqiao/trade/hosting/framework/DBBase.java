package xueqiao.trade.hosting.framework;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.framework.db.DBBuilder;
import xueqiao.trade.hosting.framework.db.IDBUpdator;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class DBBase implements IDBUpdator, DataSource {
	
    private org.apache.tomcat.jdbc.pool.DataSource mTomcatJDBCDataSource;
    
	protected abstract String getDBName();

	protected DBBase() {
		initDataSource(getDBName());
	}

	protected DBBase(String initDbName) {
		initDataSource(initDbName);
	}

	private void initDataSource(String initDbName) {
		PoolProperties p = new PoolProperties();
		p.setDefaultAutoCommit(true);
		String envHostAddr = System.getenv("HOST_ADDR");
		if (StringUtils.isNotEmpty(envHostAddr)) {
			p.setUrl("jdbc:mysql://" + envHostAddr + "/" + initDbName);
		} else {
			p.setUrl("jdbc:mysql://127.0.0.1:3306/" + initDbName);
		}
		p.setConnectionProperties("zeroDateTimeBehavior=round;characterEncoding=utf8");
		p.setDriverClassName("com.mysql.jdbc.Driver");
		p.setUsername("root");
		p.setPassword("root");
		p.setJmxEnabled(false);
		p.setTestWhileIdle(false);
		p.setTestOnBorrow(true);
		p.setValidationQuery("SELECT 1");
		p.setTestOnReturn(false);
		p.setValidationInterval(30000);
		p.setTimeBetweenEvictionRunsMillis(60000);
		p.setMaxActive(20);
		p.setMinIdle(2);
		p.setMaxIdle(2);
		p.setInitialSize(2);
		p.setMaxWait(1000);
		p.setRemoveAbandonedTimeout(60);
		p.setMinEvictableIdleTimeMillis(300000);
		p.setLogAbandoned(true);
		p.setRemoveAbandoned(true);
		p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
		mTomcatJDBCDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
		mTomcatJDBCDataSource.setPoolProperties(p);
	}
	
	@Override
	public Connection getConnection() throws SQLException {
	    Connection conn = mTomcatJDBCDataSource.getConnection();
	    if (!conn.getAutoCommit()) {
	        AppLog.w("[WARNING]Found mTomcatJDBCDataSource'getConnection() auto commit false, that should not be happend");
	        conn.setAutoCommit(true);
	    }
	    return conn;
	}
	
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLException("should not used this function");
	}

	@Override
	public PrintWriter getLogWriter() {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) {
	}

	@Override
	public void setLoginTimeout(int seconds) {
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		throw new SQLException("Unsupported getLoginTimeout");
	}

	@Override
	public Logger getParentLogger() {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) {
		return false;
	}
	
	public void destory() {
		if (mTomcatJDBCDataSource != null) {
			mTomcatJDBCDataSource.close();

			mTomcatJDBCDataSource = null;
		}
	}
	
	public void init() throws SQLException {
		Connection conn = DBSingleConnection.getDbConnection(getDBName());
		try {
			new DBBuilder(getDBName(), conn, this).buildIfNeeded();
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}
}
