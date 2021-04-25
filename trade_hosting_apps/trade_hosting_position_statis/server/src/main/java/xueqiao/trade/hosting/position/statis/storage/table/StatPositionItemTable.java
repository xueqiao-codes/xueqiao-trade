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
import java.util.Set;

public class StatPositionItemTable extends TableHelper<StatPositionItem> implements IDBTable {

	private final static String TABLE_NAME = "stat_position_item";
	private final static String F_POSITION_ITEM_ID = "Fposition_item_id";
	private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
	private final static String F_TARGET_KEY = "Ftarget_key";
	private final static String F_TARGET_TYPE = "Ftarget_type";
	private final static String F_PRICE = "Fprice";
	private final static String F_QUANTITY = "Fquantity";
	private final static String F_DIRECTION = "Fdirection";
	private final static String F_SOURCE_DATA_TIMESTAMP_MS = "Fsource_data_timestamp_ms";
	private final static String F_SOURCE_CHANNEL = "Fsource_channel";
	private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";
	private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";

	public StatPositionItemTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 */
	public int insert(StatPositionItem statPositionItem) throws SQLException {
		PreparedFields pf = new PreparedFields();
		long currentTimeMillis = System.currentTimeMillis();

		pf.addLong(F_POSITION_ITEM_ID, statPositionItem.getPositionItemId());
		pf.addLong(F_SUB_ACCOUNT_ID, statPositionItem.getSubAccountId());
		pf.addString(F_TARGET_KEY, statPositionItem.getTargetKey());
		pf.addInt(F_TARGET_TYPE, statPositionItem.getTargetType().getValue());
		pf.addDouble(F_PRICE, statPositionItem.getPrice());
		pf.addInt(F_QUANTITY, statPositionItem.getQuantity());
		pf.addInt(F_DIRECTION, statPositionItem.getDirection().getValue());
		pf.addLong(F_SOURCE_DATA_TIMESTAMP_MS, statPositionItem.getSource().getSourceDataTimestampMs());
		pf.addLong(F_SOURCE_CHANNEL, statPositionItem.getSource().getSourceDataChannel().getValue());
		if (statPositionItem.isSetCreateTimestampMs() && statPositionItem.getCreateTimestampMs() > 0) {
			pf.addLong(F_CREATE_TIMESTAMP_MS, statPositionItem.getCreateTimestampMs());
		} else {
			pf.addLong(F_CREATE_TIMESTAMP_MS, currentTimeMillis);
		}
		pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currentTimeMillis);
		return super.insert(pf);
	}

	/**
	 * 更新持仓明细的持仓手数
	 */
	public int updateStatPositionItemQuantity(long positionItemId, int updateQuantity) throws SQLException {
		PreparedFields pf = new PreparedFields();
		long currentTimeMillis = System.currentTimeMillis();

		pf.addInt(F_QUANTITY, updateQuantity);
		pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currentTimeMillis);
		return super.update(pf, F_POSITION_ITEM_ID + "=?", positionItemId);
	}

	/**
	 * 更新持仓明细
	 */
	public int updateStatPositionItem(long positionItemId, int updateQuantity, double price) throws SQLException {
		PreparedFields pf = new PreparedFields();
		long currentTimeMillis = System.currentTimeMillis();
		pf.addInt(F_QUANTITY, updateQuantity);
		pf.addDouble(F_PRICE, price);
		pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currentTimeMillis);
		return super.update(pf, F_POSITION_ITEM_ID + "=?", positionItemId);
	}

	/**
	 * 删除数据
	 */
	public void deleteStatPositionItem(long positionItemId) throws SQLException {
		super.delete(F_POSITION_ITEM_ID + "=?", positionItemId);
	}

	/**
	 * 根据subAccountId, targetKey 和 targetType来删除
	 * */
	public void deleteStatPositionItem(long subAccountId, String targetKey, HostingXQTargetType targetType) throws SQLException {
		super.delete(F_SUB_ACCOUNT_ID + "=? AND " + F_TARGET_KEY + "=? AND " + F_TARGET_TYPE + "=?", subAccountId, targetKey, targetType.getValue());
	}

	/**
	 * 根据ID查询持仓明细项
	 */
	public StatPositionItem queryStatPositionItem(long positionItemId) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_POSITION_ITEM_ID + "=?", positionItemId);
		return super.getItem(qryBuilder, false);
	}

	/**
	 * 根据ID 集合 查询 多个持仓明细项
	 */
	public List<StatPositionItem> queryStatPositionItems(Set<Long> positionItemIdSet, boolean forUpdate) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, F_POSITION_ITEM_ID, positionItemIdSet);
		return super.getItemList(qryBuilder, forUpdate);
	}

	/**
	 * 查找 特定子账号 下 特定雪橇标的 的所有持仓明细项
	 */
	public List<StatPositionItem> queryStatPositionItemList(long subAccountId, String targetKey, HostingXQTargetType targetType) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_KEY + "=?", targetKey);
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_TYPE + "=?", targetType.getValue());
		return super.getItemList(qryBuilder, false);
	}

	/**
	 * 查询
	 */
	public PageResult<StatPositionItem> getStatPositionItemPage(QueryStatPositionItemOption qryOption, IndexedPageOption pageOption) throws SQLException {
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
			if (qryOption.isSetPositionItemId()) {
				qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_POSITION_ITEM_ID + "=?", qryOption.getPositionItemId());
			}
		}
		qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, F_CREATE_TIMESTAMP_MS);
		qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

		PageResult<StatPositionItem> resultPage = new PageResult<StatPositionItem>();
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
	public StatPositionItem fromResultSet(ResultSet resultSet) throws Exception {
		StatPositionItem statPositionItem = new StatPositionItem();
		StatDataSource statDataSource = new StatDataSource();

		statPositionItem.setPositionItemId(resultSet.getLong(F_POSITION_ITEM_ID));
		statPositionItem.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
		statPositionItem.setTargetKey(resultSet.getString(F_TARGET_KEY));
		statPositionItem.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
		statPositionItem.setPrice(resultSet.getDouble(F_PRICE));
		statPositionItem.setQuantity(resultSet.getInt(F_QUANTITY));
		statPositionItem.setDirection(StatDirection.findByValue(resultSet.getInt(F_DIRECTION)));

		statDataSource.setSourceDataChannel(DataSourceChannel.findByValue(resultSet.getInt(F_SOURCE_CHANNEL)));
		statDataSource.setSourceDataTimestampMs(resultSet.getLong(F_SOURCE_DATA_TIMESTAMP_MS));
		statPositionItem.setSource(statDataSource);

		statPositionItem.setCreateTimestampMs(resultSet.getLong(F_CREATE_TIMESTAMP_MS));
		statPositionItem.setLastmodifyTimestampMs(resultSet.getLong(F_LASTMODITY_TIMESTAMP_MS));

		return statPositionItem;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_POSITION_ITEM_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
				.append(F_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_PRICE).append(" DOUBLE DEFAULT 0.00,")
				.append(F_QUANTITY).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_SOURCE_DATA_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SOURCE_CHANNEL).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_LASTMODITY_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append("PRIMARY KEY(").append(F_POSITION_ITEM_ID).append("),")
				.append("INDEX(").append(F_TARGET_KEY).append(",").append(F_SUB_ACCOUNT_ID).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
