package xueqiao.trade.hosting.asset.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.asset.thriftapi.PositionAssignHistory;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *  持仓分配的明细历史，记录持仓调整模块，调用分配接口时，传入的持仓明细
 **/

public class PositionAssignHistoryTable extends TableHelper<PositionAssignHistory> implements IDBTable {

    private static final String TABLE_NAME = "t_assign_history";
    public static final String COLUMN_FASSIGN_ID = "Fassign_id";
    public static final String COLUMN_FCONTENT = "Fcontent";
    public static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    public static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    public PositionAssignHistoryTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionAssignHistory fromResultSet(ResultSet resultSet) throws Exception {
        PositionAssignHistory history = new PositionAssignHistory();
        history.setAssignId(resultSet.getLong(COLUMN_FASSIGN_ID));
        history.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        history.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));
        history.setContent(resultSet.getString(COLUMN_FCONTENT));
        return history;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (14 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fassign_id BIGINT UNSIGNED NOT NULL,")
                    .append("Fcontent TEXT,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("PRIMARY KEY(Fassign_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(PositionAssignHistory assignHistory) throws SQLException {
        Preconditions.checkNotNull(assignHistory);
        Preconditions.checkArgument(assignHistory.isSetAssignId());
        Preconditions.checkArgument(assignHistory.getAssignId() > 0);
        PreparedFields fields = new PreparedFields();

        if (assignHistory.isSetContent()) {
            fields.addString(COLUMN_FCONTENT, assignHistory.getContent());
        }
        fields.addLong(COLUMN_FASSIGN_ID, assignHistory.getAssignId());
        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, now);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);

        super.insert(fields);
    }

    public PositionAssignHistory query(long assignId) throws SQLException {
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FASSIGN_ID + "=?", assignId);
        return super.getItem(builder);
    }
}
