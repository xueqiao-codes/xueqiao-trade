package xueqiao.trade.hosting.position.statis.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.StatClosedPositionSummary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * （一次）平仓汇总数据表，与 StatClosedPositionSummary 对应
 * ATTENTION: 平仓收益 closedProfits 部分在表 StatSummaryClosedProfitTable 中另外存储
 */
public class StatClosedPositionSummaryTable extends TableHelper<StatClosedPositionSummary> implements IDBTable {

	private final static String TABLE_NAME = "stat_closed_position_summary";
	private final static String F_CLOSED_ID = "Fclosed_id";
	private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
	private final static String F_TARGET_KEY = "Ftarget_key";
	private final static String F_TARGET_TYPE = "Ftarget_type";
	private final static String F_CLOSED_POSITION = "Fclosed_position";
	private final static String F_SPREAD_PROFIT = "Fspread_profit";
	private final static String F_CLOSED_TIMESTAMP_MS = "Fclosed_timestamp_ms";
	private final static String F_ARCHIVE_DATE_TIMESTAMP_MS = "Farchived_date_timestamp_ms";

	public StatClosedPositionSummaryTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 */
	public int insert(StatClosedPositionSummary statClosedPositionSummary) throws SQLException {
		PreparedFields pf = new PreparedFields();
		long currentTimeMillis = System.currentTimeMillis();

		pf.addLong(F_CLOSED_ID, statClosedPositionSummary.getClosedId());
		pf.addLong(F_SUB_ACCOUNT_ID, statClosedPositionSummary.getSubAccountId());
		pf.addString(F_TARGET_KEY, statClosedPositionSummary.getTargetKey());
		pf.addInt(F_TARGET_TYPE, statClosedPositionSummary.getTargetType().getValue());
		pf.addLong(F_CLOSED_POSITION, statClosedPositionSummary.getClosedPosition());
		pf.addDouble(F_SPREAD_PROFIT, statClosedPositionSummary.getSpreadProfit());
		pf.addLong(F_CLOSED_TIMESTAMP_MS, currentTimeMillis);
		pf.addLong(F_ARCHIVE_DATE_TIMESTAMP_MS, 0);  // 归档时才设置归档时间
		return super.insert(pf);
	}

	/**
	 * 查询表中第一条数据
	 */
	public StatClosedPositionSummary queryFirstItem() throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.setPage(0, 1);
		return super.getItem(qryBuilder);
	}

	/**
	 * 查询
	 */
	public List<StatClosedPositionSummary> query(long subAccountId, String targetKey, HostingXQTargetType targetType) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_KEY + "=?", targetKey);
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_TYPE + "=?", targetType.getValue());
		return super.getItemList(qryBuilder);
	}

	/**
	 * 删除数据
	 */
	public void delete(long closedId) throws SQLException {
		super.delete(F_CLOSED_ID + "=?", closedId);
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

	@Override
	public StatClosedPositionSummary fromResultSet(ResultSet resultSet) throws Exception {
		StatClosedPositionSummary statClosedPositionSummary = new StatClosedPositionSummary();

		statClosedPositionSummary.setClosedId(resultSet.getLong(F_CLOSED_ID));
		statClosedPositionSummary.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
		statClosedPositionSummary.setTargetKey(resultSet.getString(F_TARGET_KEY));
		statClosedPositionSummary.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
		statClosedPositionSummary.setClosedPosition(resultSet.getLong(F_CLOSED_POSITION));
		statClosedPositionSummary.setSpreadProfit(resultSet.getDouble(F_SPREAD_PROFIT));
		statClosedPositionSummary.setClosedTimestampMs(resultSet.getLong(F_CLOSED_TIMESTAMP_MS));
		statClosedPositionSummary.setArchivedDateTimestampMs(resultSet.getLong(F_ARCHIVE_DATE_TIMESTAMP_MS));

		return statClosedPositionSummary;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_CLOSED_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
				.append(F_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_CLOSED_POSITION).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SPREAD_PROFIT).append(" DOUBLE DEFAULT 0.00,")
				.append(F_CLOSED_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_ARCHIVE_DATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append("PRIMARY KEY(").append(F_CLOSED_ID).append("),")
				.append("INDEX(").append(F_TARGET_KEY).append(",").append(F_SUB_ACCOUNT_ID).append(",").append(F_TARGET_TYPE).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
