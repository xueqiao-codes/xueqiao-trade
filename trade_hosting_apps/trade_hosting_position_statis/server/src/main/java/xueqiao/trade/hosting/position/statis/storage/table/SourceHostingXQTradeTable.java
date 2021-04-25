package xueqiao.trade.hosting.position.statis.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceHostingXQTrade;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 成交数据记录表
 * 用于判断数据是否重复
 * （保存一个月的数据）
 */
public class SourceHostingXQTradeTable extends TableHelper<SourceHostingXQTrade> implements IDBTable {

	private final static String TABLE_NAME = "source_xq_trade";
	private final static String F_TRADE_ID = "Ftrade_id";
	private final static String F_ORDER_ID = "Forder_id";
	private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
	private final static String F_TRADE_TARGET_TYPE = "Ftrade_target_type";
	private final static String F_TRADE_TARGET_KEY = "Ftrade_Target_key";
	private final static String F_TRADE_VOLUME = "Ftrade_volume";
	private final static String F_TRADE_PRICE = "Ftrade_price";
	private final static String F_TRADE_DIRECTION = "Ftrade_diretion";
	private final static String F_SOURCE_ORDER_TARGET_TYPE = "Fsource_order_target_type";
	private final static String F_SOURCE_ORDER_TARGET_KEY = "Fsource_order_target_key";
	private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";

	public SourceHostingXQTradeTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 */
	public int addSourceXQTradeItem(HostingXQTrade xqTrade) throws SQLException {
		PreparedFields pf = new PreparedFields();

		pf.addLong(F_TRADE_ID, xqTrade.getTradeId());
		pf.addString(F_ORDER_ID, xqTrade.getOrderId());
		pf.addLong(F_SUB_ACCOUNT_ID, xqTrade.getSubAccountId());
		pf.addInt(F_TRADE_TARGET_TYPE, xqTrade.getTradeTarget().getTargetType().getValue());
		pf.addString(F_TRADE_TARGET_KEY, xqTrade.getTradeTarget().getTargetKey());
		pf.addInt(F_TRADE_VOLUME, xqTrade.getTradeVolume());
		pf.addDouble(F_TRADE_PRICE, xqTrade.getTradePrice());
		pf.addInt(F_TRADE_DIRECTION, xqTrade.getTradeDiretion().getValue());
		pf.addInt(F_SOURCE_ORDER_TARGET_TYPE, xqTrade.getSourceOrderTarget().getTargetType().getValue());
		pf.addString(F_SOURCE_ORDER_TARGET_KEY, xqTrade.getSourceOrderTarget().getTargetKey());
		pf.addLong(F_CREATE_TIMESTAMP_MS, System.currentTimeMillis());

		return super.insert(pf);
	}

	/**
	 * 查询
	 */
	public SourceHostingXQTrade getSourceHostingXQTrade(String orderId, long tradeId) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TRADE_ID + "=?", tradeId);
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_ORDER_ID + "=?", orderId);
		return super.getItem(qryBuilder, false);
	}

	/**
	 * 根据时间删除
	 * 删除millis（时间缀）之前的数据
	 */
	public void delete(long millis) throws SQLException {
		super.delete(F_CREATE_TIMESTAMP_MS + "<?", millis);
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

	@Override
	public SourceHostingXQTrade fromResultSet(ResultSet resultSet) throws Exception {
		SourceHostingXQTrade sourceHostingXQTrade = new SourceHostingXQTrade();

		sourceHostingXQTrade.setTradeId(resultSet.getLong(F_TRADE_ID));
		sourceHostingXQTrade.setOrderId(resultSet.getString(F_ORDER_ID));
		sourceHostingXQTrade.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
		sourceHostingXQTrade.setTradeTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TRADE_TARGET_TYPE)));
		sourceHostingXQTrade.setTradeTargetKey(resultSet.getString(F_TRADE_TARGET_KEY));
		sourceHostingXQTrade.setTradeVolume(resultSet.getInt(F_TRADE_VOLUME));
		sourceHostingXQTrade.setTradePrice(resultSet.getDouble(F_TRADE_PRICE));
		sourceHostingXQTrade.setTradeDiretion(HostingXQTradeDirection.findByValue(resultSet.getInt(F_TRADE_DIRECTION)));
		sourceHostingXQTrade.setSourceOrderTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_SOURCE_ORDER_TARGET_TYPE)));
		sourceHostingXQTrade.setSourceOrderTargetKey(resultSet.getString(F_SOURCE_ORDER_TARGET_KEY));
		sourceHostingXQTrade.setCreateTimestampMs(resultSet.getLong(F_CREATE_TIMESTAMP_MS));

		return sourceHostingXQTrade;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_TRADE_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_ORDER_ID).append(" VARCHAR(64) NOT NULL DEFAULT '',")
				.append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TRADE_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_TRADE_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
				.append(F_TRADE_VOLUME).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TRADE_PRICE).append(" DOUBLE DEFAULT 0.00,")
				.append(F_TRADE_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_SOURCE_ORDER_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_SOURCE_ORDER_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
				.append(F_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append("PRIMARY KEY(").append(F_ORDER_ID).append(",").append(F_TRADE_ID).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
