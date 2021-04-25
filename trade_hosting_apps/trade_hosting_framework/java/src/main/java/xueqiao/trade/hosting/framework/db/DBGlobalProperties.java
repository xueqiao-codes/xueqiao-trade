package xueqiao.trade.hosting.framework.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;

import com.google.common.base.Preconditions;

public class DBGlobalProperties {
	private static final String TABLE_NAME = "tglobal_properties";
	private static final String CLOUMN_KEY = "Fkey";
	private static final String CLOUMN_VALUE = "Fvalue";
	
	private Connection mConn;
	
	public DBGlobalProperties(Connection conn) {
		Preconditions.checkArgument(conn != null);
		this.mConn = conn;
	}
	
	public void buildPreconditions() throws SQLException {
		StringBuilder sqlBuilder = new StringBuilder(64);
		sqlBuilder.append("show tables like '").append(TABLE_NAME).append("'");
		
		List<String> tableList = new QueryRunner().query(mConn, sqlBuilder.toString()
				, new AbstractListHandler<String>() {
					@Override
					protected String handleRow(ResultSet rs) throws SQLException {
						return rs.getString(1);
					} 
		});
		
		if (!tableList.contains(TABLE_NAME)) {
			createTable();
		}
	}
	
	private void createTable() throws SQLException {
		QueryRunner runner = new QueryRunner();
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("CREATE TABLE ").append(TABLE_NAME).append("(\n")
				  .append(CLOUMN_KEY).append(" VARCHAR(16) NOT NULL DEFAULT \"\",")
				  .append(CLOUMN_VALUE).append(" VARCHAR(256) NOT NULL DEFAULT \"\",")
				  .append("PRIMARY KEY(").append(CLOUMN_KEY).append(")) ENGINE=InnoDB CHARACTER SET UTF8;");
		runner.update(mConn, sqlBuilder.toString());
	}
	
	public String getProperty(String key) throws SQLException {
		return getProperty(key, false);
	}
	
	public String getProperty(String key, boolean forUpdate) throws SQLException {
		Preconditions.checkArgument(!StringUtils.isEmpty(key));
		
		SqlQueryBuilder sqlBuilder = new SqlQueryBuilder();
		sqlBuilder.addTables(TABLE_NAME);
		sqlBuilder.addFields(CLOUMN_KEY, CLOUMN_VALUE);
		sqlBuilder.addFieldCondition(ConditionType.AND, CLOUMN_KEY + "=?", key);
		
		StringBuilder queryString = new StringBuilder();
		queryString.append(sqlBuilder.getItemsSql());
		if (forUpdate) {
			queryString.append(" FOR UPDATE");
		}
		
		QueryRunner runner = new QueryRunner();
		return runner.query(mConn, queryString.toString(), new ResultSetHandler<String>() {
			@Override
			public String handle(ResultSet rs) throws SQLException {
				if (rs.next()) {
					return rs.getString(CLOUMN_VALUE);
				}
				return "";
			}
			
		}, sqlBuilder.getParameterList());
		
	}
	
	public void setProperty(String key, String value) throws SQLException {
		Preconditions.checkArgument(!StringUtils.isEmpty(key));
		Preconditions.checkNotNull(value);
		
		StringBuilder sqlBuilder = new StringBuilder();
		sqlBuilder.append("INSERT INTO ").append(TABLE_NAME)
			      .append(" values(?,?) ON DUPLICATE KEY UPDATE ")
			      .append(CLOUMN_VALUE).append("=?");
		
		new QueryRunner().update(mConn, sqlBuilder.toString(), key, value, value);
	}
}
