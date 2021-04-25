package xueqiao.trade.hosting.dealing.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.dealing.storage.data.ExecOrderIndexEntryV2;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HostingExecOrderInDealingTableV2 extends TableHelper<ExecOrderIndexEntryV2> implements IDBTable {
    public HostingExecOrderInDealingTableV2(Connection conn) {
        super(conn);
    }

    public PageResult<ExecOrderIndexEntryV2> getIndexEntries(PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);

        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Fexec_order_id");
        queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        PageResult<ExecOrderIndexEntryV2> resultPage = new PageResult<>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(queryBuilder));
        }
        resultPage.setPageList(super.getItemList(queryBuilder));

        return resultPage;
    }

    public int insertIndexEntry(ExecOrderIndexEntryV2 indexEntry) throws SQLException {
        Preconditions.checkNotNull(indexEntry);
        Preconditions.checkArgument(indexEntry.getExecOrderId() > 0);

        PreparedFields pf = new PreparedFields();
        pf.addLong("Fexec_order_id", indexEntry.getExecOrderId());
        pf.addLong("Fcreate_timestampms", System.currentTimeMillis());

        return super.insert(pf);
    }

    public int deleteIndexEntry(long execOrderId) throws SQLException {
        return super.delete("Fexec_order_id=?", execOrderId);
    }

    @Override
    protected String getTableName() throws SQLException {
        return "torder_indealing";
    }

    @Override
    public ExecOrderIndexEntryV2 fromResultSet(ResultSet rs) throws Exception {
        ExecOrderIndexEntryV2 indexEntryV2 = new ExecOrderIndexEntryV2();
        indexEntryV2.setExecOrderId(rs.getLong("Fexec_order_id"));
        return indexEntryV2;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (6 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Fexec_order_id)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }
}
