package xueqiao.trade.hosting.position.statis.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceAssignedPosition;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 分配过来的数据记录表
 * 用于判断数据是否重复
 * （保存一个月的数据）
 * ATTENTION:目前分配过来的都是合约
 */
public class SourceAssignPositionTable extends TableHelper<SourceAssignedPosition> implements IDBTable {

	private final static String TABLE_NAME = "source_assign_position";
	private final static String F_ASSIGN_ID = "Fassign_id";                        // 唯一标识
	private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
	private final static String F_SLED_CONTRACT_ID = "Fsled_contract_id";
	private final static String F_PRICE = "Fprice";
	private final static String F_VOLUME = "Fvolume";
	private final static String F_POSITION_DIRECTION = "Fposition_diretion";
	private final static String F_POSITION_TIMESTAMP_MS = "Fposition_timestamp_ms";
	private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";

	public SourceAssignPositionTable(Connection conn) {
		super(conn);
	}

	/**
	 * 插入数据
	 */
	public int addSourceAssignPositionItem(PositionAssigned positionAssigned) throws SQLException {
		PreparedFields pf = new PreparedFields();

		pf.addLong(F_ASSIGN_ID, positionAssigned.getAssignId());
		pf.addLong(F_SUB_ACCOUNT_ID, positionAssigned.getSubAccountId());
		pf.addLong(F_SLED_CONTRACT_ID, positionAssigned.getSledContractId());
		pf.addDouble(F_PRICE, positionAssigned.getPrice());
		pf.addInt(F_VOLUME, positionAssigned.getVolume());
		pf.addInt(F_POSITION_DIRECTION, positionAssigned.getPositionDirection().getValue());
		pf.addLong(F_POSITION_TIMESTAMP_MS, positionAssigned.getPositionTimestampMs());
		pf.addLong(F_CREATE_TIMESTAMP_MS, System.currentTimeMillis());

		return super.insert(pf);
	}

	/**
	 * 查询
	 */
	public SourceAssignedPosition getSourceAssignPositionItem(long assignId) throws SQLException {
		SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
		qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_ASSIGN_ID + "=?", assignId);
		return super.getItem(qryBuilder, false);
	}

	/**
	 * 根据时间删除
	 * 删除millis（时间缀）之前的数据
	 */
	public void delete(long millis) throws SQLException {
		String whereCondition = F_CREATE_TIMESTAMP_MS + "<?";
		super.delete(whereCondition, millis);
	}

	@Override
	protected String getTableName() throws SQLException {
		return TABLE_NAME;
	}

	@Override
	public SourceAssignedPosition fromResultSet(ResultSet resultSet) throws Exception {
		SourceAssignedPosition sourceAssignedPosition = new SourceAssignedPosition();

		sourceAssignedPosition.setAssignId(resultSet.getLong(F_ASSIGN_ID));
		sourceAssignedPosition.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
		sourceAssignedPosition.setSledContractId(resultSet.getLong(F_SLED_CONTRACT_ID));
		sourceAssignedPosition.setPrice(resultSet.getDouble(F_PRICE));
		sourceAssignedPosition.setVolume(resultSet.getInt(F_VOLUME));
		sourceAssignedPosition.setPositionDirection(PositionDirection.findByValue(resultSet.getInt(F_POSITION_DIRECTION)));
		sourceAssignedPosition.setPositionTimestampMs(resultSet.getLong(F_POSITION_TIMESTAMP_MS));
		sourceAssignedPosition.setCreateTimestampMs(resultSet.getLong(F_CREATE_TIMESTAMP_MS));

		return sourceAssignedPosition;
	}

	@Override
	public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
		if (1 == targetVersion) {
			StringBuilder createSqlBuilder = new StringBuilder(128);
			createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
				.append(F_ASSIGN_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_SLED_CONTRACT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_PRICE).append(" DOUBLE DEFAULT 0.00,")
				.append(F_VOLUME).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_POSITION_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
				.append(F_POSITION_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append(F_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
				.append("PRIMARY KEY(").append(F_ASSIGN_ID).append(")")
				.append(")ENGINE=InnoDB CHARACTER SET UTF8;");
			operator.execute(createSqlBuilder.toString());
		}
	}
}
