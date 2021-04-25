package xueqiao.trade.hosting.position.adjust.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionEditLock;
import xueqiao.trade.hosting.position.adjust.thriftapi.trade_hosting_position_adjustConstants;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionEditLockTable extends TableHelper<PositionEditLock> implements IDBTable {

    private final static String TABLE_NAME = "t_position_lock";
    private final static String FLOCK_AREA_KEY = "Flock_area_key";
    private final static String FSUB_USER_ID = "Fsub_user_id";
    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    public PositionEditLockTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionEditLock fromResultSet(ResultSet resultSet) throws Exception {
        PositionEditLock positionEditLock = new PositionEditLock();
        positionEditLock.setLockArea(resultSet.getString(FLOCK_AREA_KEY));
        positionEditLock.setSubUserId(resultSet.getLong(FSUB_USER_ID));
        positionEditLock.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        positionEditLock.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return positionEditLock;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Flock_area_key VARCHAR(64) NOT NULL ,")
                    .append("Fsub_user_id BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Flock_area_key)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
        if (2 == targetVersion) {
            // init lock key
            operator.execute("insert into t_position_lock set Flock_area_key  = ?, Fcreate_timestamp =?",
                    trade_hosting_position_adjustConstants.POSITION_EDIT_AREA_ASSIGN, System.currentTimeMillis());
            operator.execute("insert into t_position_lock set Flock_area_key  = ?, Fcreate_timestamp =?",
                    trade_hosting_position_adjustConstants.POSITION_EDIT_AREA_MANUAL_INPUT, System.currentTimeMillis());
        }
    }

    // 暂时不允许外部设置key
/*
    public void initKey(PositionEditLock lock) throws SQLException {
        Preconditions.checkNotNull(lock);
        Preconditions.checkArgument(lock.isSetLockArea());
        PreparedFields fields = getPreparedFields(lock);
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.insert(fields);
    }
*/
    public void update(PositionEditLock lock) throws SQLException {
        Preconditions.checkNotNull(lock);
        Preconditions.checkArgument(lock.isSetLockArea());
        PreparedFields fields = getPreparedFields(lock);
        long now = System.currentTimeMillis();
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.update(fields, FLOCK_AREA_KEY + "=?", lock.getLockArea());
    }

    private PreparedFields getPreparedFields(PositionEditLock lock) {
        PreparedFields fields = new PreparedFields();
        if (lock.isSetLockArea()) {
            fields.addString(FLOCK_AREA_KEY, lock.getLockArea());
        }
        if (lock.isSetSubUserId()) {
            fields.addLong(FSUB_USER_ID, lock.getSubUserId());
        }
        return fields;
    }

    public PositionEditLock query(String lockKey, boolean isForUpdate) throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FLOCK_AREA_KEY + "=?", lockKey);
        return super.getItem(sqlQueryBuilder, isForUpdate);
    }

    @Override
    public int deleteAll() throws SQLException {
        super.deleteAll();
        initKey(trade_hosting_position_adjustConstants.POSITION_EDIT_AREA_ASSIGN);
        initKey(trade_hosting_position_adjustConstants.POSITION_EDIT_AREA_MANUAL_INPUT);
        return 0;
    }

    private void clearLock(String lockKey) throws SQLException {
        PositionEditLock oldLock= query(lockKey,false);
        if (oldLock == null){
            initKey(lockKey);
        }else {
            PositionEditLock lock = new PositionEditLock();
            lock.setLockArea(lockKey);
            lock.setSubUserId(0);
            update(lock);
        }
    }

    private void initKey(String lockKey) throws SQLException {
        PreparedFields fields = new PreparedFields();
        fields.addString(FLOCK_AREA_KEY,lockKey);
        fields.addLong(FCREATE_TIMESTAMP, System.currentTimeMillis());
        fields.addLong(FLAST_MODIFY_TIMESTAMP, System.currentTimeMillis());
        super.insert(fields);
    }
}
