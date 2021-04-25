package xueqiao.trade.hosting.position.statis.storage.table;

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

public class StatPositionUnitTable extends TableHelper<StatPositionUnit> implements IDBTable {

    private final static String TABLE_NAME = "stat_position_unit";

    private final static String F_UNIT_ID = "Funit_id";
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

    public StatPositionUnitTable(Connection conn) {
        super(conn);
    }

    /**
     * 插入数据
     */
    public int insert(StatPositionUnit statPositionUnit) throws SQLException {
        PreparedFields pf = new PreparedFields();

        pf.addLong(F_UNIT_ID, statPositionUnit.getUnitId());
        pf.addLong(F_POSITION_ITEM_ID, statPositionUnit.getPositionItemId());
        pf.addLong(F_SLED_CONTRACT_ID, statPositionUnit.getSledContractId());
        pf.addDouble(F_UNIT_PRICE, statPositionUnit.getUnitPrice());
        pf.addInt(F_UNIT_QUANTITY, statPositionUnit.getUnitQuantity());
        pf.addInt(F_DIRECTION, statPositionUnit.getDirection().getValue());
        pf.addLong(F_SOURCE_DATA_TIMESTAMP_MS, statPositionUnit.getSourceDataTimestampMs());

        pf.addLong(F_SUB_ACCOUNT_ID, statPositionUnit.getSubAccountId());
        pf.addString(F_TARGET_KEY, statPositionUnit.getTargetKey());
        pf.addInt(F_TARGET_TYPE, statPositionUnit.getTargetType().getValue());
        pf.addInt(F_SOURCE_TYPE, statPositionUnit.getSource().getSourceType().getValue());
        pf.addLong(F_SOURCE_ID, statPositionUnit.getSource().getSourceId());

        return super.insert(pf);
    }

    /**
     * 更新
     */
    public int updateStatPositionUnitQuantity(long unitId, int updateQuantity) throws SQLException {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_UNIT_QUANTITY, updateQuantity);
        return super.update(pf, F_UNIT_ID + "=?", unitId);
    }

    /**
     * 查询
     */
    public List<StatPositionUnit> queryStatPositionUnitList(long positionItemId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_POSITION_ITEM_ID + "=?", positionItemId);
        return super.getItemList(qryBuilder, false);
    }

    /**
     * 根据 positionItemId 集合 查询 合约小单元
     */
    public List<StatPositionUnit> queryStatPositionUnitList(Set<Long> positionItemIdSet, boolean forUpdate) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, F_POSITION_ITEM_ID, positionItemIdSet);
        return super.getItemList(qryBuilder, forUpdate);
    }

    /**
     * 查询
     */
    public List<StatPositionUnit> queryStatPositionUnitList(final long subAccountId, final String targetKey, final HostingXQTargetType targetType) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_KEY + "=?", targetKey);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_TYPE + "=?", targetType.getValue());
        return super.getItemList(qryBuilder, false);
    }

    /**
     * 查询
     */
    public PageResult<StatPositionUnit> getStatPositionUnitPage(QueryStatPositionUnitOption qryOption, IndexedPageOption pageOption)
            throws SQLException {

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.isSetPositionItemId()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_POSITION_ITEM_ID + "=?", qryOption.getPositionItemId());
            }
        }
        PageResult<StatPositionUnit> resultPage = new PageResult<StatPositionUnit>();
        if (pageOption != null) {
            qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            if (pageOption.isNeedTotalCount()) {
                resultPage.setTotalCount(super.getTotalNum(qryBuilder));
            }
        }
        resultPage.setPageList(super.getItemList(qryBuilder));
        return resultPage;
    }

    /**
     * 删除
     */
    public void deleteStatPositionUnit(long positionItemId) throws SQLException {
        super.delete(F_POSITION_ITEM_ID + "=?", positionItemId);
    }

    /**
     * 根据subAccountId, targetKey 和 targetType来删除
     * */
    public void deleteStatPositionUnit(long subAccountId, String targetKey, HostingXQTargetType targetType) throws SQLException {
        super.delete(F_SUB_ACCOUNT_ID + "=? AND " + F_TARGET_KEY + "=? AND " + F_TARGET_TYPE + "=?", subAccountId, targetKey, targetType.getValue());
    }

    /**
     * 删除一条小单元
     */
    public void deleteOneUnit(long unitId) throws SQLException {
        super.delete(F_UNIT_ID + "=?", unitId);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public StatPositionUnit fromResultSet(ResultSet resultSet) throws Exception {
        StatPositionUnit statPositionUnit = new StatPositionUnit();
        ExternalDataSource dataSource = new ExternalDataSource();

        statPositionUnit.setUnitId(resultSet.getLong(F_UNIT_ID));
        statPositionUnit.setPositionItemId(resultSet.getLong(F_POSITION_ITEM_ID));
        statPositionUnit.setSledContractId(resultSet.getLong(F_SLED_CONTRACT_ID));
        statPositionUnit.setUnitPrice(resultSet.getDouble(F_UNIT_PRICE));
        statPositionUnit.setUnitQuantity(resultSet.getInt(F_UNIT_QUANTITY));
        statPositionUnit.setDirection(StatDirection.findByValue(resultSet.getInt(F_DIRECTION)));
        statPositionUnit.setSourceDataTimestampMs(resultSet.getLong(F_SOURCE_DATA_TIMESTAMP_MS));

        statPositionUnit.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
        statPositionUnit.setTargetKey(resultSet.getString(F_TARGET_KEY));
        statPositionUnit.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
        dataSource.setSourceId(resultSet.getLong(F_SOURCE_ID));
        dataSource.setSourceType(SourceType.findByValue(resultSet.getInt(F_SOURCE_TYPE)));
        statPositionUnit.setSource(dataSource);

        return statPositionUnit;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_UNIT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_POSITION_ITEM_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_SLED_CONTRACT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_UNIT_PRICE).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_UNIT_QUANTITY).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_DIRECTION).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_SOURCE_DATA_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_UNIT_ID).append("),")
                    .append("INDEX(").append(F_POSITION_ITEM_ID).append(")")
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
