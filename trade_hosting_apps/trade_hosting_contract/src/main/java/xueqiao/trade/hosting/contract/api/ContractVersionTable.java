package xueqiao.trade.hosting.contract.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

public class ContractVersionTable extends TableHelper<ContractVersionEntry> implements IDBTable {
	
	public ContractVersionTable(Connection conn) {
		super(conn);
	}

	private static final String TABLE_NAME = "tcontract_version";
	
	public ContractVersionEntry getVersion(boolean forUpdate) throws SQLException {
		
		SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();
		sqlBuilder.addFieldCondition(ConditionType.AND, "Fid=?", 1);
		
		return super.getItem(sqlBuilder, forUpdate);
	}
	
	public int updateVersion(int newLastestVersion
			, int fromVersion) throws SQLException {
		Preconditions.checkArgument(newLastestVersion > 0);
		Preconditions.checkArgument(fromVersion >= 0);
		
		PreparedFields fields = new PreparedFields();
		fields.addInt("Flastest_version", newLastestVersion);
		fields.addInt("Ffrom_version", fromVersion);
		
		return super.update(fields, "Fid=?", 1);
	}
	
	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createTableSql = new StringBuilder(128);
			createTableSql.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(")
					  .append("Fid INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Flastest_version INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Ffrom_version INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Fswitch_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("PRIMARY KEY(Fid)")
					  .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createTableSql.toString());
			
			StringBuilder insertOneRowSql = new StringBuilder(128);
			int currentTimestamp = (int)(System.currentTimeMillis()/1000);
			insertOneRowSql.append("INSERT INTO ").append(TABLE_NAME)
						   .append(" SET Fid=1, Flastest_version=0, Ffrom_version=0")
						   .append(",Fcreate_timestamp=").append(currentTimestamp)
						   .append(",Fswitch_timestamp=").append(currentTimestamp)
						   .append(" ON DUPLICATE KEY UPDATE Flastest_version=0, Ffrom_version=0");
			operator.execute(insertOneRowSql.toString());
		}
	}

	@Override
	public ContractVersionEntry fromResultSet(ResultSet rs) throws Exception {
		ContractVersionEntry entry = new ContractVersionEntry();
		entry.setLastestVersion(rs.getInt("Flastest_version"));
		entry.setFromVersion(rs.getInt("Ffrom_version"));
		entry.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
		entry.setSwitchTimestamp(rs.getInt("Fswitch_timestamp"));
		return entry;
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

}
