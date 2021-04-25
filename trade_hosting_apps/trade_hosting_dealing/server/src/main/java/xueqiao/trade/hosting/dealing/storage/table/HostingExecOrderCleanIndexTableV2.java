package xueqiao.trade.hosting.dealing.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.OrderCleanIndexEntry;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HostingExecOrderCleanIndexTableV2 extends TableHelper<OrderCleanIndexEntry> implements IDBTable {
    public HostingExecOrderCleanIndexTableV2(Connection conn) {
        super(conn);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (6 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fclean_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fclean_timestampms, Fexec_order_id)")
                    .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }

    public PageResult<OrderCleanIndexEntry> getBeforeCleanTimestampMs(
            long cleanTimestampMs, PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        Preconditions.checkArgument(cleanTimestampMs > 0);

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fclean_timestampms<=?", cleanTimestampMs);
        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        PageResult<OrderCleanIndexEntry> resultPage = new PageResult<>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(qryBuilder));
        }
        resultPage.setPageList(super.getItemList(qryBuilder));

        return resultPage;
    }


    public int addIndexEntry(OrderCleanIndexEntry indexEntry) throws SQLException {
        Preconditions.checkNotNull(indexEntry);
        Preconditions.checkArgument(indexEntry.getCleanTimestampMs() > 0);
        Preconditions.checkArgument(indexEntry.getExecOrderId() > 0);

        PreparedFields pf = new PreparedFields();
        pf.addLong("Fclean_timestampms", indexEntry.getCleanTimestampMs());
        pf.addLong("Fexec_order_id", indexEntry.getExecOrderId());
        pf.addLong("Fcreate_timestampms", System.currentTimeMillis());

        return super.insert(pf);
    }

    public int delIndexEntry(long cleanTimestampMs, long execOrderId) throws SQLException {
        Preconditions.checkArgument(cleanTimestampMs > 0);
        Preconditions.checkArgument(execOrderId > 0);

        return super.delete("Fclean_timestampms=? AND Fexec_order_id=?"
                , cleanTimestampMs, execOrderId);
    }

    @Override
    protected String getTableName() throws SQLException {
        return "torder_clean";
    }

    @Override
    public OrderCleanIndexEntry fromResultSet(ResultSet rs) throws Exception {
        OrderCleanIndexEntry indexEntry = new OrderCleanIndexEntry();
        indexEntry.setCleanTimestampMs(rs.getLong("Fclean_timestampms"));
        indexEntry.setExecOrderId(rs.getLong("Fexec_order_id"));
        indexEntry.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        return indexEntry;
    }
}
