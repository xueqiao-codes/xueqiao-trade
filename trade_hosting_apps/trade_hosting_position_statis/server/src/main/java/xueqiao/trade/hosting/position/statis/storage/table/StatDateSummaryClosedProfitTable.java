package xueqiao.trade.hosting.position.statis.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.ClosedProfit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 平仓天汇总 平仓收益 数据表
 * */
public class StatDateSummaryClosedProfitTable extends TableHelper<ClosedProfit> implements IDBTable {

	private final static String TABLE_NAME = "closed_profit_date_summary";
	private final static String F_DATE_SUMMARY_ID = "Fdate_summary_id";
	private final static String F_TRADE_CURRENCY = "Ftrade_currency";
	private final static String F_CLOSED_PROFIT = "Fclosed_profit";

	public StatDateSummaryClosedProfitTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 */
	public int insert(long dateSummaryId, ClosedProfit closedProfit) throws SQLException {
		PreparedFields pf = new PreparedFields();

		pf.addLong(F_DATE_SUMMARY_ID, dateSummaryId);
		pf.addString(F_TRADE_CURRENCY, closedProfit.getTradeCurrency());
		pf.addDouble(F_CLOSED_PROFIT, closedProfit.getClosedProfitValue());

		return super.insert(pf);
	}

	/**
	 * 查询
	 * */
	public List<ClosedProfit> query(long dateSummaryId) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_DATE_SUMMARY_ID + "=?", dateSummaryId);
		return super.getItemList(qryBuilder, false);
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

	@Override
	public ClosedProfit fromResultSet(ResultSet resultSet) throws Exception {
		ClosedProfit closedProfit = new ClosedProfit();

		closedProfit.setTradeCurrency(resultSet.getString(F_TRADE_CURRENCY));
		closedProfit.setClosedProfitValue(resultSet.getDouble(F_CLOSED_PROFIT));

		return closedProfit;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_DATE_SUMMARY_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TRADE_CURRENCY).append(" VARCHAR(8) NOT NULL DEFAULT '',")
				.append(F_CLOSED_PROFIT).append(" DOUBLE DEFAULT 0.00,")
				.append("PRIMARY KEY(").append(F_DATE_SUMMARY_ID).append(",").append(F_TRADE_CURRENCY).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
