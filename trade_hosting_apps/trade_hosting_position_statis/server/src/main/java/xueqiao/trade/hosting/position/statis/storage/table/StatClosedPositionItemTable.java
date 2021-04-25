package xueqiao.trade.hosting.position.statis.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatClosedPositionItemTable extends TableHelper<StatClosedPositionItem> implements IDBTable {

	private final static String TABLE_NAME = "stat_closed_position_item";
	private final static String F_CLOSED_ITEM_ID = "Fclosed_item_id";
	private final static String F_CLOSED_ID = "Fclosed_id";
	private final static String F_POSITION_ITEM_ID = "Fposition_item_id";
	private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
	private final static String F_TARGET_KEY = "Ftarget_key";
	private final static String F_TARGET_TYPE = "Ftarget_type";
	private final static String F_PRICE = "Fprice";
	private final static String F_QUANTITY = "Fclosed_quantity";
	private final static String F_DIRECTION = "Fdirection";
	private final static String F_SOURCE_DATA_TIMESTAMP_MS = "Fsource_data_timestamp_ms";
	private final static String F_SOURCE_CHANNEL = "Fsource_channel";
	private final static String F_POSITION_CREATE_TIMESTAMP_MS = "Fposition_create_timestamp_ms";
	private final static String F_CLOSED_TIMESTAMP_MS = "Fclosed_timestamp_ms";
	private final static String F_ARCHIVED_DATE_TIMESTAMP_MS = "Farchived_date_timestamp_ms";

	public StatClosedPositionItemTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 * */
	public int insert(StatClosedPositionItem item) throws SQLException {
		PreparedFields pf = new PreparedFields();
		long currentTimeMillis = System.currentTimeMillis();

		pf.addLong(F_CLOSED_ITEM_ID, item.getClosedItemId());
		pf.addLong(F_CLOSED_ID, item.getClosedId());
		pf.addLong(F_POSITION_ITEM_ID, item.getPositionItemId());
		pf.addLong(F_SUB_ACCOUNT_ID, item.getSubAccountId());
		pf.addString(F_TARGET_KEY, item.getTargetKey());
		pf.addInt(F_TARGET_TYPE, item.getTargetType().getValue());
		pf.addDouble(F_PRICE, item.getPrice());
		pf.addInt(F_QUANTITY, item.getClosedQuantity());
		pf.addInt(F_DIRECTION, item.getDirection().getValue());
		pf.addLong(F_SOURCE_DATA_TIMESTAMP_MS, item.getSource().getSourceDataTimestampMs());
		pf.addLong(F_SOURCE_CHANNEL, item.getSource().getSourceDataChannel().getValue());
		pf.addLong(F_POSITION_CREATE_TIMESTAMP_MS, item.getPositionCreateTimestampMs());
		pf.addLong(F_CLOSED_TIMESTAMP_MS, currentTimeMillis);
		pf.addLong(F_ARCHIVED_DATE_TIMESTAMP_MS, 0);  // 这里是平仓，未归档，故归档时间为0
		return super.insert(pf);
	}

	/**
	 * 查询
	 * */
	public List<StatClosedPositionItem> queryStatClosedPositionItemList(long closedId) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CLOSED_ID + "=?", closedId);
		return super.getItemList(qryBuilder, false);
	}

	/**
	 * 查询
	 */
	public PageResult<StatClosedPositionItem> getStatClosedPositionItemPage(QueryStatClosedPositionItemOption queryOption, IndexedPageOption pageOption) throws SQLException {
		Preconditions.checkNotNull(pageOption);
		Preconditions.checkArgument(pageOption.getPageIndex() >= 0 && pageOption.getPageSize() > 0);

		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		if (queryOption != null) {
			if (queryOption.isSetClosedId()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,F_CLOSED_ID + "=?", queryOption.getClosedId());
			}
			if (queryOption.isSetSubAccountId()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,F_SUB_ACCOUNT_ID + "=?", queryOption.getSubAccountId());
			}
			if (queryOption.isSetTargetKey()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,F_TARGET_KEY + "=?", queryOption.getTargetKey());
			}
			if (queryOption.isSetTargetType()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,F_TARGET_TYPE + "=?", queryOption.getTargetType().getValue());
			}
		}
		qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, F_CLOSED_TIMESTAMP_MS);

		qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

		PageResult<StatClosedPositionItem> resultPage = new PageResult<StatClosedPositionItem>();
		if (pageOption.isNeedTotalCount()) {
			resultPage.setTotalCount(super.getTotalNum(qryBuilder));
		}
		resultPage.setPageList(super.getItemList(qryBuilder));

		return resultPage;
	}

	/**
	 * 删除
	 * */
	public void delete(long closedId) throws SQLException {
		super.delete(F_CLOSED_ID + "=?", closedId);
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

	@Override
	public StatClosedPositionItem fromResultSet(ResultSet resultSet) throws Exception {
		StatClosedPositionItem statClosedPositionItem = new StatClosedPositionItem();
		StatDataSource statDataSource = new StatDataSource();

		statClosedPositionItem.setClosedItemId(resultSet.getLong(F_CLOSED_ITEM_ID));
		statClosedPositionItem.setClosedId(resultSet.getLong(F_CLOSED_ID));
		statClosedPositionItem.setPositionItemId(resultSet.getLong(F_POSITION_ITEM_ID));
		statClosedPositionItem.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
		statClosedPositionItem.setTargetKey(resultSet.getString(F_TARGET_KEY));
		statClosedPositionItem.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
		statClosedPositionItem.setPrice(resultSet.getDouble(F_PRICE));
		statClosedPositionItem.setClosedQuantity(resultSet.getInt(F_QUANTITY));
		statClosedPositionItem.setDirection(StatDirection.findByValue(resultSet.getInt(F_DIRECTION)));

		statDataSource.setSourceDataChannel(DataSourceChannel.findByValue(resultSet.getInt(F_SOURCE_CHANNEL)));
		statDataSource.setSourceDataTimestampMs(resultSet.getLong(F_SOURCE_DATA_TIMESTAMP_MS));
		statClosedPositionItem.setSource(statDataSource);

		statClosedPositionItem.setPositionCreateTimestampMs(resultSet.getLong(F_POSITION_CREATE_TIMESTAMP_MS));
		statClosedPositionItem.setClosedTimestampMs(resultSet.getLong(F_CLOSED_TIMESTAMP_MS));
		statClosedPositionItem.setArchivedDateTimestampMs(resultSet.getLong(F_ARCHIVED_DATE_TIMESTAMP_MS));

		return statClosedPositionItem;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_CLOSED_ITEM_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_CLOSED_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_POSITION_ITEM_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
				.append(F_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_PRICE).append(" DOUBLE DEFAULT 0.00,")
				.append(F_QUANTITY).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_SOURCE_DATA_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SOURCE_CHANNEL).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_POSITION_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_CLOSED_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_ARCHIVED_DATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append("PRIMARY KEY(").append(F_CLOSED_ITEM_ID).append("),")
				.append("INDEX(").append(F_CLOSED_ID).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
