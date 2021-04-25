package xueqiao.trade.hosting.storage.subaccount;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingSubAccountMoneyRecord;
import xueqiao.trade.hosting.HostingSubAccountMoneyRecordDirection;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountMoneyRecordOption;

public class HostingSubAccountMRTable extends TableHelper <HostingSubAccountMoneyRecord> implements IDBTable {

    public HostingSubAccountMRTable(Connection conn) {
        super(conn);
    }
    
    public PageResult<HostingSubAccountMoneyRecord> getMRPage(QuerySubAccountMoneyRecordOption queryOption
            , PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        if (queryOption != null) {
            if (queryOption.getSubAccountId() > 0) {
                queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", queryOption.getSubAccountId());
            }
        }
        
        queryBuilder.setOrder(OrderType.DESC, "Frecord_timestamp");
        queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        
        PageResult<HostingSubAccountMoneyRecord> resultPage = new PageResult<HostingSubAccountMoneyRecord>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(queryBuilder));
        }
        resultPage.setPageList(super.getItemList(queryBuilder));
        
        return resultPage;
    }
    
    public HostingSubAccountMoneyRecord getTicketMR(long subAccountId, String ticket) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(ticket));
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", subAccountId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fticket=?", ticket);
        return super.getItem(queryBuilder);
    }
    
    public int appendMR(HostingSubAccountMoneyRecord mr) throws SQLException {
        Preconditions.checkNotNull(mr);
        Preconditions.checkArgument(mr.isSetDirection());
        
        PreparedFields pf = new PreparedFields();
        pf.addLong("Fsub_account_id", mr.getSubAccountId());
        pf.addInt("Frecord_timestamp", mr.getRecordTimestamp());
        pf.addByte("Fdirection", (byte)mr.getDirection().getValue());
        pf.addLong("Fhow_much", mr.getHowMuch());
        pf.addLong("Fin_amount_before", mr.getInAmountBefore());
        pf.addLong("Fin_amount_after", mr.getInAmountAfter());
        pf.addLong("Fout_amount_before", mr.getOutAmountBefore());
        pf.addLong("Fout_amount_after", mr.getOutAmountAfter());
        pf.addString("Fticket", mr.getTicket());
        
        return super.insert(pf);
    }
    
    
    @Override
    public HostingSubAccountMoneyRecord fromResultSet(ResultSet rs) throws Exception {
        HostingSubAccountMoneyRecord mr = new HostingSubAccountMoneyRecord();
        mr.setSubAccountId(rs.getLong("Fsub_account_id"));
        mr.setRecordTimestamp(rs.getInt("Frecord_timestamp"));
        mr.setDirection(HostingSubAccountMoneyRecordDirection.findByValue(rs.getByte("Fdirection")));
        mr.setHowMuch(rs.getLong("Fhow_much"));
        mr.setOpSubUserId(rs.getInt("Fop_sub_user_id"));
        mr.setInAmountBefore(rs.getLong("Fin_amount_before"));
        mr.setInAmountAfter(rs.getLong("Fin_amount_after"));
        mr.setOutAmountBefore(rs.getLong("Fout_amount_before"));
        mr.setOutAmountAfter(rs.getLong("Fout_amount_after"));
        mr.setTicket(rs.getString("Fticket"));
        return mr;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (10 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Frecord_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fdirection TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fhow_much BIGINT NOT NULL DEFAULT 0,")
                            .append("Fop_sub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fin_amount_before BIGINT NOT NULL DEFAULT 0,")
                            .append("Fin_amount_after BIGINT NOT NULL DEFAULT 0,")
                            .append("Fout_amount_before BIGINT NOT NULL DEFAULT 0,")
                            .append("Fout_amount_after BIGINT NOT NULL DEFAULT 0,")
                            .append("Fticket VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("PRIMARY KEY(Fsub_account_id, Frecord_timestamp),")
                            .append("UNIQUE KEY(Fsub_account_id, Fticket))ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "thosting_sub_account_mr";
    }

}
