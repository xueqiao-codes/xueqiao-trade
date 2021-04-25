package xueqiao.trade.hosting.position.adjust.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.adjust.thriftapi.AoType;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignTask;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionAssignTaskTable extends TableHelper<PositionAssignTask> implements IDBTable {

    private final static String TABLE_NAME = "t_position_assign_task";
    private final static String FTASK_ID = "Ftask_id";
    private final static String FAO_TYPE = "Fao_type";
    private final static String FPOSITION_ASSIGN_CONTENT = "Fposition_assign_content";

    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    public PositionAssignTaskTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionAssignTask fromResultSet(ResultSet resultSet) throws Exception {
        PositionAssignTask task = new PositionAssignTask();
        task.setTaskId(resultSet.getLong(FTASK_ID));
        String content = resultSet.getString(FPOSITION_ASSIGN_CONTENT);
        PositionAssigned positionAssigned = ProtocolUtil.unSerialize(
                FactoryInstance.getInstance().getJsonFactory(), content.getBytes(), PositionAssigned.class);
        task.setPositionAssigned(positionAssigned);
        task.setAoType(AoType.findByValue(resultSet.getInt(FAO_TYPE)));
        task.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        task.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return task;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Ftask_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,")
                    .append("Fao_type SMALLINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fposition_assign_content TEXT,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Ftask_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb, AUTO_INCREMENT=1");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(PositionAssignTask task) throws SQLException {
        Preconditions.checkNotNull(task);
        Preconditions.checkArgument(task.isSetAoType());
        Preconditions.checkArgument(task.isSetPositionAssigned());
        PreparedFields fields = new PreparedFields();
        fields.addInt(FAO_TYPE, task.getAoType().getValue());
        fields.addString(FPOSITION_ASSIGN_CONTENT,
                new String(ProtocolUtil.serialize(FactoryInstance.getInstance().getJsonFactory(), task.getPositionAssigned()).array()));
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.insert(fields);
    }

    public PositionAssignTask query(AoType aoType) throws SQLException {
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FAO_TYPE + "=?", aoType.getValue());
        return super.getItem(builder);
    }

    public void delete(long taskId) throws SQLException {
        super.delete(FTASK_ID + "=?", taskId);
    }
}
