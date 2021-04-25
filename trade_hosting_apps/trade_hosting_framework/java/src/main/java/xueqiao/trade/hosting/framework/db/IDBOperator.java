package xueqiao.trade.hosting.framework.db;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public interface IDBOperator {
	public int execute(String sql, Object... params) throws SQLException; 
	
	public DatabaseMetaData getMetaData() throws SQLException;
	
	public boolean colunmExist(String tableName, String columnName) throws SQLException;
	
	public boolean indexExist(String tableName, String indexName) throws SQLException;
}
