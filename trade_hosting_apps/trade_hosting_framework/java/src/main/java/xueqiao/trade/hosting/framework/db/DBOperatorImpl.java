package xueqiao.trade.hosting.framework.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;

public class DBOperatorImpl implements IDBOperator {

	private Connection mConn;
	
	public DBOperatorImpl(Connection conn) {
		this.mConn = conn;
	}

	@Override
	public int execute(String sql, Object... params) throws SQLException {
		if (AppLog.infoEnabled()) {
			StringBuilder sqlLogBuilder = new StringBuilder(128);
			sqlLogBuilder.append("execute sql{").append(sql).append("}");
			if (params != null && params.length > 0) {
				sqlLogBuilder.append(", parameters(");
				for (int index = 0; index < params.length; ++index) {
					if (index > 0) {
						sqlLogBuilder.append(",");
					}
					sqlLogBuilder.append(params[index]);
				}
			}
			AppLog.i(sqlLogBuilder.toString());
		}
		return new QueryRunner().update(mConn, sql, params);
	}		
	
	public DatabaseMetaData getMetaData() throws SQLException {
	    return mConn.getMetaData();
	}

    @Override
    public boolean colunmExist(String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = getMetaData();
        ResultSet rs = null;
        try {
            rs = metaData.getColumns(null, "%", tableName, columnName);
            if (!rs.next()) {
                return false;
            }
            return true;
        } finally {
            DbUtils.closeQuietly(rs);
        }
    }


	@Override
	public boolean indexExist(String tableName, String indexName) throws SQLException {
		DatabaseMetaData metaData = getMetaData();
        ResultSet rs = null;
        try {
        	rs = metaData.getIndexInfo(null, null, tableName, false, false);
        	while(rs.next()) {
        		String rsIndexName = rs.getString("INDEX_NAME");
				AppLog.i("Table " + tableName + ", Index: " + rsIndexName);
        		if (indexName.equalsIgnoreCase(rsIndexName)) {
        			return true;
        		}
        	}
        	return false;
        } finally {
        	DbUtils.closeQuietly(rs);
        }
	}

}
