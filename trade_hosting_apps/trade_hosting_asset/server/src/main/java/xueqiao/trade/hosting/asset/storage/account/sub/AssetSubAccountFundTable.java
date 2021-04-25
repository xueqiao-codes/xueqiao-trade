package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountFund;
import xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 雪橇子账户资金配置信息
 *
 * @author walter
 */
public class AssetSubAccountFundTable extends TableHelper<HostingSubAccountFund> implements IDBTable {

    private static final String TABLE_NAME = "t_sub_account_fund";

    private static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    private static final String COLUMN_FCURRENCY = "Fcurrency";
    private static final String COLUMN_FBALANCE = "Fbalance";
    private static final String COLUMN_FDEPOSIT_AMOUNT = "Fdeposit_amount";
    private static final String COLUMN_FWITHDRAW_AMOUNT = "Fwithdraw_amount";
    private static final String COLUMN_FCREDIT_AMOUNT = "Fcredit_amount";
    private static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    public AssetSubAccountFundTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    public void add(HostingSubAccountFund subAccountFund) throws SQLException {
        Preconditions.checkNotNull(subAccountFund);
        Preconditions.checkArgument(subAccountFund.getSubAccountId() > 0);
        Preconditions.checkArgument(subAccountFund.isSetCurrency());

        PreparedFields fields = getPreparedFields(subAccountFund);
        fields.addLong(COLUMN_FSUB_ACCOUNT_ID, subAccountFund.getSubAccountId());
        fields.addString(COLUMN_FCURRENCY, subAccountFund.getCurrency());
        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, now);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);
        super.insert(fields);
    }

    private PreparedFields getPreparedFields(HostingSubAccountFund subAccountFund) {
        PreparedFields fields = new PreparedFields();
        if (subAccountFund.isSetBalance()) {
            fields.addDouble(COLUMN_FBALANCE, subAccountFund.getBalance());
        }
        if (subAccountFund.isSetDepositAmount()) {
            fields.addDouble(COLUMN_FDEPOSIT_AMOUNT, subAccountFund.getDepositAmount());
        }
        if (subAccountFund.isSetWithdrawAmount()) {
            fields.addDouble(COLUMN_FWITHDRAW_AMOUNT, subAccountFund.getWithdrawAmount());
        }
        if (subAccountFund.isSetCreditAmount()) {
            fields.addDouble(COLUMN_FCREDIT_AMOUNT, subAccountFund.getCreditAmount());
        }
        return fields;
    }

    public void update(HostingSubAccountFund subAccountFund) throws SQLException {
        Preconditions.checkNotNull(subAccountFund);
        Preconditions.checkArgument(subAccountFund.getSubAccountId() > 0);
        Preconditions.checkArgument(subAccountFund.isSetCurrency());
        PreparedFields fields = getPreparedFields(subAccountFund);
        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);
        super.update(fields, COLUMN_FSUB_ACCOUNT_ID + "=? AND " + COLUMN_FCURRENCY + " =?", subAccountFund.getSubAccountId(), subAccountFund.getCurrency());

    }

    public PageResult<HostingSubAccountFund> query(ReqSubAccountFundOption subAccountFundOption, PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        Preconditions.checkNotNull(subAccountFundOption);
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        if (subAccountFundOption.isSetSubAccountId()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountFundOption.getSubAccountId());
        }
        if (subAccountFundOption.isSetCurrency()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", subAccountFundOption.getCurrency());
        }
        sqlQueryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        sqlQueryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, COLUMN_FSUB_ACCOUNT_ID);

        PageResult<HostingSubAccountFund> pageResult = new PageResult<>();
        if (pageOption.isNeedTotalCount()) {
            pageResult.setTotalCount(super.getTotalNum(sqlQueryBuilder));
        }
        pageResult.setPageList(super.getItemList(sqlQueryBuilder));

        return pageResult;
    }

    public HostingSubAccountFund query(long subAccountId, String currency) throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountId);
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", currency);
        return super.getItem(sqlQueryBuilder);
    }

    @Override
    public HostingSubAccountFund fromResultSet(ResultSet resultSet) throws Exception {
        HostingSubAccountFund subAccountFund = new HostingSubAccountFund();
        subAccountFund.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        subAccountFund.setCurrency(resultSet.getString(COLUMN_FCURRENCY));
        subAccountFund.setBalance(resultSet.getDouble(COLUMN_FBALANCE));
        subAccountFund.setDepositAmount(resultSet.getDouble(COLUMN_FDEPOSIT_AMOUNT));
        subAccountFund.setWithdrawAmount(resultSet.getDouble(COLUMN_FWITHDRAW_AMOUNT));
        subAccountFund.setCreditAmount(resultSet.getDouble(COLUMN_FCREDIT_AMOUNT));
        subAccountFund.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        subAccountFund.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));
        return subAccountFund;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fcurrency VARCHAR(32) NOT NULL DEFAULT '',")
                    .append("Fbalance DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fdeposit_amount DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fwithdraw_amount DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fcredit_amount DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fsub_account_id, Fcurrency)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb;");
            idbOperator.execute(createSqlBuilder.toString());
        }
    }
}
