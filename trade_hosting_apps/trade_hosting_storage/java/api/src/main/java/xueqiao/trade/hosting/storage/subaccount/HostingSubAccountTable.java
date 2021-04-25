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

import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;

public class HostingSubAccountTable extends TableHelper <HostingSubAccount> implements IDBTable{

    public HostingSubAccountTable(Connection conn) {
        super(conn);
    }
    
    public HostingSubAccount getSubAccount(long subAccountId) throws SQLException {
        return getSubAccount(subAccountId, false);
    }
    
    public PageResult<HostingSubAccount> getSubAccountPage(QuerySubAccountOption qryOption
            , PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.getSubAccountId() > 0) {
                queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", qryOption.getSubAccountId());
            }
            if (StringUtils.isNotEmpty(qryOption.getSubAccountNameWhole())) {
                queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_name=?", qryOption.getSubAccountNameWhole());
            }
            if (StringUtils.isNotEmpty(qryOption.getSubAccountNamePartical())) {
                queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_name like ?"
                        , "%" + qryOption.getSubAccountNamePartical() + "%");
            }
        }
        
        queryBuilder.setOrder(OrderType.ASC, "Fsub_account_id");
        queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        
        PageResult<HostingSubAccount> resultPage = new PageResult<HostingSubAccount>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(queryBuilder));
        }
        resultPage.setPageList(super.getItemList(queryBuilder));
        
        return resultPage;
    }
    
    public HostingSubAccount getSubAccount(long subAccountId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", subAccountId);
        return super.getItem(queryBuilder, forUpdate);
    }
    
    public int updateSubAccount(HostingSubAccount subAccount) throws SQLException {
        Preconditions.checkNotNull(subAccount);
        Preconditions.checkArgument(subAccount.getSubAccountId() > 0);
        
        PreparedFields pf = new PreparedFields();
        subAccount2PreparedFields(subAccount, pf);
        pf.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        return super.update(pf, "Fsub_account_id=?", subAccount.getSubAccountId());
    }
    
    public int createSubAccount(HostingSubAccount subAccount) throws SQLException {
        Preconditions.checkNotNull(subAccount);
        Preconditions.checkArgument(subAccount.getSubAccountId() > 0);
        
        PreparedFields pf = new PreparedFields();
        pf.addLong("Fsub_account_id", subAccount.getSubAccountId());
        subAccount2PreparedFields(subAccount, pf);
        
        pf.addInt("Fcreate_timestamp", (int)(System.currentTimeMillis()/1000));
        pf.addInt("Flastmodify_timestamp", (int)(System.currentTimeMillis()/1000));
        
        return super.insert(pf);
    }
    
    private void subAccount2PreparedFields(HostingSubAccount subAccount, PreparedFields pf) {
        if (subAccount.isSetSubAccountName()) {
            pf.addString("Fsub_account_name", subAccount.getSubAccountName());
        }
        if (subAccount.isSetInAmount()) {
            pf.addLong("Fin_amount", subAccount.getInAmount());
        }
        if (subAccount.isSetOutAmount()) {
            pf.addLong("Fout_amount", subAccount.getOutAmount());
        }
    }

    @Override
    public HostingSubAccount fromResultSet(ResultSet rs) throws Exception {
        HostingSubAccount subAccount = new HostingSubAccount();
        subAccount.setSubAccountId(rs.getLong("Fsub_account_id"));
        subAccount.setSubAccountName(rs.getString("Fsub_account_name"));
        subAccount.setInAmount(rs.getLong("Fin_amount"));
        subAccount.setOutAmount(rs.getLong("Fout_amount"));
        subAccount.setCreateTimestamp(rs.getInt("Fcreate_timestamp"));
        subAccount.setLastmodifyTimestamp(rs.getInt("Flastmodify_timestamp"));
        return subAccount;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (10 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_account_name VARCHAR(64) NOT NULL DEFAULT 0 UNIQUE,")
                            .append("Fin_amount BIGINT NOT NULL DEFAULT 0,")
                            .append("Fout_amount BIGINT NOT NULL DEFAULT 0,")
                            .append("Fcreate_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestamp INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Fsub_account_id))ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "thosting_sub_account";
    }

}
