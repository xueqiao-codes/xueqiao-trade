package xueqiao.trade.hosting.position.statis.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.QueryStatClosedPositionDateSummaryOption;
import xueqiao.trade.hosting.position.statis.StatClosedPositionDateSummary;
import xueqiao.trade.hosting.position.statis.app.Constant;
import xueqiao.trade.hosting.position.statis.service.util.TimeUtil;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 平仓归档天汇总数据表，与 StatClosedPositionDateSummary 对应
 * ATTENTION: 平仓收益 closedProfits 部分在表 StatDateSummaryClosedProfitTable 中另外存储
 * */
public class StatArchiveDateSummaryTable extends TableHelper<StatClosedPositionDateSummary> implements IDBTable {

	private final static String TABLE_NAME = "stat_archive_date_summary";
	private final static String F_DATE_SUMMARY_ID = "Fdate_summary_id";
	private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
	private final static String F_TARGET_KEY = "Ftarget_key";
	private final static String F_ARCHIVED_DATE_TIMESTAMP_MS = "Farchived_date_timestamp_ms";
	private final static String F_TARGET_TYPE = "Ftarget_type";
	private final static String F_CLOSED_POSITION = "Fclosed_position";
	private final static String F_SPREAD_PROFIT = "Fspread_profit";

	public StatArchiveDateSummaryTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 */
	public int insert(StatClosedPositionDateSummary statClosedPositionDateSummary) throws SQLException {
		PreparedFields pf = new PreparedFields();
		/*
		* 因为是每天0点后做当档，所以，归档时间定为前一天的0点
		* */
		long archivedDateZeroTimestamps = TimeUtil.getCurrentDateZeroMillis(System.currentTimeMillis()) - Constant.MILLIS_PER_DAY;

		pf.addLong(F_DATE_SUMMARY_ID, statClosedPositionDateSummary.getDateSummaryId());
		pf.addLong(F_SUB_ACCOUNT_ID, statClosedPositionDateSummary.getSubAccountId());
		pf.addString(F_TARGET_KEY, statClosedPositionDateSummary.getTargetKey());
		pf.addLong(F_ARCHIVED_DATE_TIMESTAMP_MS, archivedDateZeroTimestamps);
		pf.addInt(F_TARGET_TYPE, statClosedPositionDateSummary.getTargetType().getValue());
		pf.addLong(F_CLOSED_POSITION, statClosedPositionDateSummary.getClosedPosition());
		pf.addDouble(F_SPREAD_PROFIT, statClosedPositionDateSummary.getSpreadProfit());

		return super.insert(pf);
	}

	/**
	 * 查询
	 * */
	public PageResult<StatClosedPositionDateSummary> getArchiveDateSummaryPage(QueryStatClosedPositionDateSummaryOption qryOption, IndexedPageOption pageOption) throws SQLException {
		Preconditions.checkNotNull(pageOption);
		Preconditions.checkArgument(pageOption.getPageIndex() >= 0 && pageOption.getPageSize() > 0);

		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		if (qryOption != null) {
			if (qryOption.isSetSubAccountId()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", qryOption.getSubAccountId());
			}
			if (qryOption.isSetTargetKey()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_KEY + "=?", qryOption.getTargetKey());
			}
			if (qryOption.isSetTargetType()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_TYPE + "=?", qryOption.getTargetType().getValue());
			}

			if (qryOption.isSetArchivedDateTimestampMs()) {
				/*
				* 精确的时间查询
				* 优先精确查询
				* */
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_ARCHIVED_DATE_TIMESTAMP_MS + "=?", qryOption.getArchivedDateTimestampMs());
			} else if (qryOption.isSetArchiveStartDateTimestampMs() && qryOption.isSetArchiveEndDateTimestampMs()) {
				/*
				* 范围时间查询
				* */
				long startDateZeroTimestamps = TimeUtil.getCurrentDateZeroMillis(qryOption.getArchiveStartDateTimestampMs());
				long endDateZeroTimestamps = TimeUtil.getCurrentDateZeroMillis(qryOption.getArchiveEndDateTimestampMs());

				if (startDateZeroTimestamps == endDateZeroTimestamps) {
					qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_ARCHIVED_DATE_TIMESTAMP_MS + "=?", startDateZeroTimestamps);
				} else {
					qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_ARCHIVED_DATE_TIMESTAMP_MS + ">=?", startDateZeroTimestamps);
					qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_ARCHIVED_DATE_TIMESTAMP_MS + "<=?", endDateZeroTimestamps);
				}
			}
		}

		qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, F_ARCHIVED_DATE_TIMESTAMP_MS);
		qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

		PageResult<StatClosedPositionDateSummary> resultPage = new PageResult<StatClosedPositionDateSummary>();
		if (pageOption.isNeedTotalCount()) {
			resultPage.setTotalCount(super.getTotalNum(qryBuilder));
		}
		resultPage.setPageList(super.getItemList(qryBuilder));
		return resultPage;
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

	@Override
	public StatClosedPositionDateSummary fromResultSet(ResultSet resultSet) throws Exception {
		StatClosedPositionDateSummary statClosedPositionDateSummary = new StatClosedPositionDateSummary();

		statClosedPositionDateSummary.setDateSummaryId(resultSet.getLong(F_DATE_SUMMARY_ID));
		statClosedPositionDateSummary.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
		statClosedPositionDateSummary.setTargetKey(resultSet.getString(F_TARGET_KEY));
		statClosedPositionDateSummary.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
		statClosedPositionDateSummary.setClosedPosition(resultSet.getLong(F_CLOSED_POSITION));
		statClosedPositionDateSummary.setArchivedDateTimestampMs(resultSet.getLong(F_ARCHIVED_DATE_TIMESTAMP_MS));
		statClosedPositionDateSummary.setSpreadProfit(resultSet.getDouble(F_SPREAD_PROFIT));

		return statClosedPositionDateSummary;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_DATE_SUMMARY_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")   // 这里 F_DATE_SUMMARY_ID 不做为主键，只用作唯一标识，因为不做查询条件
				.append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
				.append(F_ARCHIVED_DATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_CLOSED_POSITION).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SPREAD_PROFIT).append(" DOUBLE DEFAULT 0.00,")
				.append("PRIMARY KEY(").append(F_SUB_ACCOUNT_ID).append(",").append(F_TARGET_KEY)     // 四个字段做为联合主键
				.append(",").append(F_ARCHIVED_DATE_TIMESTAMP_MS).append(",").append(F_TARGET_TYPE).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
