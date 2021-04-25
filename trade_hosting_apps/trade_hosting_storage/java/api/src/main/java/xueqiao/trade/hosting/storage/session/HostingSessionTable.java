package xueqiao.trade.hosting.storage.session;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;

public class HostingSessionTable extends TableHelper<SessionEntry> implements IDBTable {
	
	public HostingSessionTable(Connection conn) {
		super(conn);
	}

	private static final String TABLE_NAME = "thosting_session";
	
	
	public int getTotalCount() throws SQLException {
		return super.getTotalNum(super.prepareSqlQueryBuilder());
	}
	
	public SessionEntry getSessionEntry(int subUserId) 
			throws SQLException {
		Preconditions.checkArgument(subUserId > 0);
		
		SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();
		sqlBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", subUserId);
		return super.getItem(sqlBuilder);
	}
	
	public List<SessionEntry> getAll() throws SQLException {
		return super.getItemList(super.prepareSqlQueryBuilder());
	}
	
	public int insertSessionEntry(SessionEntry entry) throws SQLException{
		Preconditions.checkNotNull(entry);
		Preconditions.checkArgument(entry.getSubUserId() > 0);
		Preconditions.checkArgument(StringUtils.isNotBlank(entry.getToken()));
		
		PreparedFields fields = new PreparedFields();
		fields.addInt("Fsub_user_id", entry.getSubUserId());
		fields.addString("Ftoken", entry.getToken().trim());
		if (StringUtils.isNotBlank(entry.getLoginIP())) {
			fields.addString("Flogin_ip", entry.getLoginIP().trim());
		}
		fields.addInt("Flast_update_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.insert(fields);
	}
	
	public int updateSessionEntry(SessionEntry entry) throws SQLException {
		Preconditions.checkNotNull(entry);
		Preconditions.checkArgument(entry.getSubUserId() > 0);
		
		PreparedFields fields = new PreparedFields();
		if (StringUtils.isNotBlank(entry.getToken())) {
			fields.addString("Ftoken", entry.getToken().trim());
		}
		if (StringUtils.isNotBlank(entry.getLoginIP())) {
			fields.addString("Flogin_ip", entry.getLoginIP().trim());
		}
		fields.addInt("Flast_update_timestamp", (int)(System.currentTimeMillis()/1000));
		
		return super.update(fields, "Fsub_user_id=?", entry.getSubUserId());
	}
	
	public int deleteSessionEntry(int subUserId) throws SQLException{
		Preconditions.checkArgument(subUserId > 0);
		return super.delete("Fsub_user_id=?", subUserId);
	}
	
	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder sqlBuilder = new StringBuilder(128);
			sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(TABLE_NAME).append("(")
					  .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("Ftoken VARCHAR(128) NOT NULL DEFAULT '',")
					  .append("Flogin_ip VARCHAR(64) NOT NULL DEFAULT '',")
					  .append("Flast_update_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
					  .append("PRIMARY KEY(Fsub_user_id)")
					  .append(") ENGINE=MEMORY CHARACTER SET UTF8;");
			operator.execute(sqlBuilder.toString());
		}
	}

	@Override
	public SessionEntry fromResultSet(ResultSet rs) throws Exception {
		SessionEntry entry = new SessionEntry();
		entry.setSubUserId(rs.getInt("Fsub_user_id"));
		entry.setToken(rs.getString("Ftoken"));
		entry.setLoginIP(rs.getString("Flogin_ip"));
		entry.setLastUpdateTimestamp(rs.getInt("Flast_update_timestamp"));
		return entry;
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

}
