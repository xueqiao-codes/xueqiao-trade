package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecord;
import xueqiao.trade.hosting.asset.thriftapi.HostingSubAccountMoneyRecordDirection;
import xueqiao.trade.hosting.asset.thriftapi.ReqMoneyRecordOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 雪橇成子账户的出入金记录
 *
 * @author walter
 */
public class AssetSubAccountMoneyRecordTable extends TableHelper<HostingSubAccountMoneyRecord> implements IDBTable {

    private static final String TABLE_NAME = "t_sub_account_mr";

    private static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    private static final String COLUMN_FCURRENCY = "Fcurrency";
    private static final String COLUMN_FRECORD_TIMESTAMP = "Frecord_timestamp";
    private static final String COLUMN_FDIRECTION = "Fdirection";
    private static final String COLUMN_FTOTAL = "Ftotal";
    private static final String COLUMN_FDEPOSIT_AMOUNT_BEFORE = "Fdeposit_amount_before";
    private static final String COLUMN_FDEPOSIT_AMOUNT_AFTER = "Fdeposit_amount_after";
    private static final String COLUMN_FWITHDRAW_AMOUNT_BEFORE = "Fwithdraw_amount_before";
    private static final String COLUMN_FWITHDRAW_AMOUNT_AFTER = "Fwithdraw_amount_after";
    private static final String COLUMN_FTICKET = "Fticket";
    private static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    public AssetSubAccountMoneyRecordTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    public void add(HostingSubAccountMoneyRecord record) throws SQLException {
        Preconditions.checkNotNull(record);
        Preconditions.checkArgument(record.isSetCurrency());
        Preconditions.checkArgument(record.isSetSubAccountId());
        Preconditions.checkArgument(record.isSetDirection());
        PreparedFields fields = new PreparedFields();
        fields.addLong(COLUMN_FSUB_ACCOUNT_ID, record.getSubAccountId());
        fields.addString(COLUMN_FCURRENCY, record.getCurrency());
        fields.addInt(COLUMN_FDIRECTION, record.getDirection().getValue());
        if (record.isSetTotalAmount()) {
            fields.addDouble(COLUMN_FTOTAL, record.getTotalAmount());
        }
        if (record.isSetDepositAmountAfter()) {
            fields.addDouble(COLUMN_FDEPOSIT_AMOUNT_AFTER, record.getDepositAmountAfter());
        }
        if (record.isSetDepositAmountBefore()) {
            fields.addDouble(COLUMN_FDEPOSIT_AMOUNT_BEFORE, record.getDepositAmountBefore());
        }
        if (record.isSetWithdrawAmountAfter()) {
            fields.addDouble(COLUMN_FWITHDRAW_AMOUNT_AFTER, record.getWithdrawAmountAfter());
        }
        if (record.isSetWithdrawAmountBefore()) {
            fields.addDouble(COLUMN_FWITHDRAW_AMOUNT_BEFORE, record.getWithdrawAmountBefore());
        }
        if (record.isSetTicket()) {
            fields.addString(COLUMN_FTICKET, record.getTicket());
        }

        long now = System.currentTimeMillis();

        if (record.isSetRecordTimestampMs()) {
            fields.addDouble(COLUMN_FRECORD_TIMESTAMP, record.getRecordTimestampMs());
        } else {
            fields.addDouble(COLUMN_FRECORD_TIMESTAMP, now);
        }
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, now);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);
        super.insert(fields);
    }

    public PageResult<HostingSubAccountMoneyRecord> query(ReqMoneyRecordOption option, PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);
        Preconditions.checkNotNull(pageOption);
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        if (option.isSetSubAccountId()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", option.getSubAccountId());
        }
        if (option.isSetStartTimestampMs()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FRECORD_TIMESTAMP + " >=? ", option.getStartTimestampMs());
        }
        if (option.isSetEndTimestampMs()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FRECORD_TIMESTAMP + " <=? ", option.getEndTimestampMs());
        }
        if (option.isSetCurrency()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", option.getCurrency());
        }

        sqlQueryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FRECORD_TIMESTAMP);
        sqlQueryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        PageResult<HostingSubAccountMoneyRecord> pageResult = new PageResult<>();
        if (pageOption.isNeedTotalCount()) {
            pageResult.setTotalCount(super.getTotalNum(sqlQueryBuilder));
        }
        pageResult.setPageList(super.getItemList(sqlQueryBuilder));
        return pageResult;
    }

    public HostingSubAccountMoneyRecord getTicketMR(long subAccountId, String ticket) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(ticket));
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountId);
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FTICKET + "=?", ticket);
        return super.getItem(queryBuilder);
    }

    public HostingSubAccountMoneyRecord getLatestMR(long subAccountId, String currency) throws SQLException {
        Preconditions.checkArgument(subAccountId > 0);
        Preconditions.checkNotNull(currency);
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountId);
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", currency);
        queryBuilder.setPage(0, 1);
        queryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FCREATE_TIMESTAMP);
        return super.getItem(queryBuilder);
    }

    @Override
    public HostingSubAccountMoneyRecord fromResultSet(ResultSet resultSet) throws Exception {
        HostingSubAccountMoneyRecord record = new HostingSubAccountMoneyRecord();
        record.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        record.setDirection(HostingSubAccountMoneyRecordDirection.findByValue(resultSet.getInt(COLUMN_FDIRECTION)));
        record.setTotalAmount(resultSet.getDouble(COLUMN_FTOTAL));
        record.setDepositAmountBefore(resultSet.getDouble(COLUMN_FDEPOSIT_AMOUNT_BEFORE));
        record.setDepositAmountAfter(resultSet.getDouble(COLUMN_FDEPOSIT_AMOUNT_AFTER));
        record.setWithdrawAmountBefore(resultSet.getDouble(COLUMN_FWITHDRAW_AMOUNT_BEFORE));
        record.setWithdrawAmountAfter(resultSet.getDouble(COLUMN_FWITHDRAW_AMOUNT_AFTER));
        record.setRecordTimestampMs(resultSet.getLong(COLUMN_FRECORD_TIMESTAMP));
        record.setTicket(resultSet.getString(COLUMN_FTICKET));
        record.setCurrency(resultSet.getString(COLUMN_FCURRENCY));
        record.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        record.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));
        return record;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fcurrency VARCHAR(32) NOT NULL DEFAULT '',")
                    .append("Frecord_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fdirection TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Ftotal DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fdeposit_amount_before DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fdeposit_amount_after DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fwithdraw_amount_before DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fwithdraw_amount_after DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fticket VARCHAR(64) NOT NULL DEFAULT '',")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fsub_account_id, Frecord_timestamp),")
                    .append("UNIQUE KEY(Fsub_account_id, Fticket)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb;");
            idbOperator.execute(createSqlBuilder.toString());
        }
    }
}
