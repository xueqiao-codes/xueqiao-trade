package xueqiao.trade.hosting.position.statis.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.ExternalDataSource;
import xueqiao.trade.hosting.position.statis.SourceType;
import xueqiao.trade.hosting.position.statis.StatClosedUnit;
import xueqiao.trade.hosting.position.statis.StatDirection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatArchiveUnitTable extends TableHelper<StatClosedUnit> implements IDBTable {

    private final static String TABLE_NAME = "stat_archive_unit";

    private final static String F_ARCHIVE_UNIT_ID = "Farchive_unit_id";
    private final static String F_CLOSED_ITEM_ID = "Fclosed_item_id";
    private final static String F_POSITION_UNIT_ID = "Fposition_unit_id";
    private final static String F_POSITION_ITEM_ID = "Fposition_item_id";
    private final static String F_SLED_CONTRACT_ID = "Fsled_contract_id";
    private final static String F_UNIT_PRICE = "Funit_price";
    private final static String F_UNIT_QUANTITY = "Funit_quantity";
    private final static String F_DIRECTION = "Fdirection";
    private final static String F_SOURCE_DATA_TIMESTAMP_MS = "Fsource_data_timestamp_ms";

    /*
     * more columns
     * */
    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_TARGET_KEY = "Ftarget_key";
    private final static String F_TARGET_TYPE = "Ftarget_type";
    private final static String F_SOURCE_TYPE = "Fsource_type";
    private final static String F_SOURCE_ID = "Fsource_id";

    public StatArchiveUnitTable(Connection conn) {
        super(conn);
    }

    /**
     * 插入数据
     */
    public int insert(StatClosedUnit statClosedUnit) throws SQLException {
        PreparedFields pf = new PreparedFields();

        pf.addLong(F_ARCHIVE_UNIT_ID, statClosedUnit.getClosedUnitId());
        pf.addLong(F_CLOSED_ITEM_ID, statClosedUnit.getClosedItemId());
        pf.addLong(F_POSITION_UNIT_ID, statClosedUnit.getPositionUnitId());
        pf.addLong(F_POSITION_ITEM_ID, statClosedUnit.getPositionItemId());
        pf.addLong(F_SLED_CONTRACT_ID, statClosedUnit.getSledContractId());
        pf.addDouble(F_UNIT_PRICE, statClosedUnit.getUnitPrice());
        pf.addInt(F_UNIT_QUANTITY, statClosedUnit.getUnitQuantity());
        pf.addInt(F_DIRECTION, statClosedUnit.getDirection().getValue());
        pf.addLong(F_SOURCE_DATA_TIMESTAMP_MS, statClosedUnit.getSourceDataTimestampMs());

        pf.addLong(F_SUB_ACCOUNT_ID, statClosedUnit.getSubAccountId());
        pf.addString(F_TARGET_KEY, statClosedUnit.getTargetKey());
        /*
        * 数据兼容处理，确保对老数据的操作不报错
        * */
        if (statClosedUnit.getTargetType() == null) {
            pf.addInt(F_TARGET_TYPE, 0);
        } else {
            pf.addInt(F_TARGET_TYPE, statClosedUnit.getTargetType().getValue());
        }
        pf.addInt(F_SOURCE_TYPE, statClosedUnit.getSource().getSourceType().getValue());
        pf.addLong(F_SOURCE_ID, statClosedUnit.getSource().getSourceId());

        return super.insert(pf);
    }

    /**
     * 查询
     */
    public List<StatClosedUnit> query(long closedItemId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CLOSED_ITEM_ID + "=?", closedItemId);
        return super.getItemList(qryBuilder, false);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public StatClosedUnit fromResultSet(ResultSet resultSet) throws Exception {
        StatClosedUnit closedUnit = new StatClosedUnit();
        ExternalDataSource dataSource = new ExternalDataSource();

        closedUnit.setClosedUnitId(resultSet.getLong(F_ARCHIVE_UNIT_ID));
        closedUnit.setClosedItemId(resultSet.getLong(F_CLOSED_ITEM_ID));
        closedUnit.setPositionUnitId(resultSet.getLong(F_POSITION_UNIT_ID));
        closedUnit.setPositionItemId(resultSet.getLong(F_POSITION_ITEM_ID));
        closedUnit.setSledContractId(resultSet.getLong(F_SLED_CONTRACT_ID));
        closedUnit.setUnitPrice(resultSet.getDouble(F_UNIT_PRICE));
        closedUnit.setUnitQuantity(resultSet.getInt(F_UNIT_QUANTITY));
        closedUnit.setDirection(StatDirection.findByValue(resultSet.getInt(F_DIRECTION)));
        closedUnit.setSourceDataTimestampMs(resultSet.getLong(F_SOURCE_DATA_TIMESTAMP_MS));

        closedUnit.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
        closedUnit.setTargetKey(resultSet.getString(F_TARGET_KEY));
        closedUnit.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
        dataSource.setSourceId(resultSet.getLong(F_SOURCE_ID));
        dataSource.setSourceType(SourceType.findByValue(resultSet.getInt(F_SOURCE_TYPE)));
        closedUnit.setSource(dataSource);

        return closedUnit;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (2 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_ARCHIVE_UNIT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_CLOSED_ITEM_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_POSITION_UNIT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_POSITION_ITEM_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_SLED_CONTRACT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_UNIT_PRICE).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_UNIT_QUANTITY).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_SOURCE_DATA_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_ARCHIVE_UNIT_ID).append("),")
                    .append("INDEX(").append(F_CLOSED_ITEM_ID).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        } else if (3 == targetVersion) {
            // add columns
            StringBuilder addSubAccountIdSqlBuilder = new StringBuilder(128);
            addSubAccountIdSqlBuilder.append("ALTER TABLE ").append(TABLE_NAME).append(" ADD(").append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0);");
            operator.execute(addSubAccountIdSqlBuilder.toString());

            StringBuilder addTargetKeySqlBuilder = new StringBuilder(128);
            addTargetKeySqlBuilder.append("ALTER TABLE ").append(TABLE_NAME).append(" ADD(").append(F_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '');");
            operator.execute(addTargetKeySqlBuilder.toString());

            StringBuilder addTargetTypeSqlBuilder = new StringBuilder(128);
            addTargetTypeSqlBuilder.append("ALTER TABLE ").append(TABLE_NAME).append(" ADD(").append(F_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0);");
            operator.execute(addTargetTypeSqlBuilder.toString());

            StringBuilder addSourceTypeSqlBuilder = new StringBuilder(128);
            addSourceTypeSqlBuilder.append("ALTER TABLE ").append(TABLE_NAME).append(" ADD(").append(F_SOURCE_TYPE).append(" TINYINT NOT NULL DEFAULT 0);");
            operator.execute(addSourceTypeSqlBuilder.toString());

            StringBuilder addSourceIdSqlBuilder = new StringBuilder(128);
            addSourceIdSqlBuilder.append("ALTER TABLE ").append(TABLE_NAME).append(" ADD(").append(F_SOURCE_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0);");
            operator.execute(addSourceIdSqlBuilder.toString());

            // add index
            StringBuilder addIndexSqlBuilder = new StringBuilder(128);
            addIndexSqlBuilder.append("ALTER TABLE ").append(TABLE_NAME).append(" ADD INDEX(").append(F_SUB_ACCOUNT_ID).append(",").append(F_TARGET_KEY).append(",").append(F_TARGET_TYPE).append(");");
            operator.execute(addIndexSqlBuilder.toString());
        }
    }
}
