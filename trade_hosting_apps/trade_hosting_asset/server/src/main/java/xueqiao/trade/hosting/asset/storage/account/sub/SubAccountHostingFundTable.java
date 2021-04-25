package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingFundPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqSubAccountFundHistoryOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * 雪橇子账户每日资金历史
 *
 * @author walter
 */
public class SubAccountHostingFundTable extends TableHelper<HostingFund> implements IDBTable {

    private static final String TABLE_NAME = "t_hosting_fund";

    private static final String COLUMN_FHOSTING_FUND_ID = "Fhosting_fund_id";
    private static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    private static final String COLUMN_FPRE_FUND = "Fpre_fund";
    private static final String COLUMN_FCURRENCY = "Fcurrency";
    private static final String COLUMN_FDEPOSIT_AMOUNT = "Fdeposit_amount";
    private static final String COLUMN_FWITHDRAW_AMOUNT = "Fwithdraw_amount";
    private static final String COLUMN_FCLOSE_PROFIT = "Fclose_profit";
    private static final String COLUMN_FUSE_MARGIN = "Fuse_margin";
    private static final String COLUMN_FUSE_COMMISSION = "Fuse_commission";

    private static final String COLUMN_FPOSITION_PROFIT = "Fposition_profit";
    private static final String COLUMN_FFROZEN_MARGIN = "Ffrozen_margin";
    private static final String COLUMN_FFROZEN_COMMISSION = "FfrozenCommission";
    private static final String COLUMN_FAVAILABLE_FUND = "Favailable_fund";
    private static final String COLUMN_FDYNAMIC_BENEFIT = "Fdynamic_benefit";
    private static final String COLUMN_FRISK_RATE = "Frisk_rate";
    private static final String COLUMN_FCREDIT_AMOUNT = "Fcredit_amount";
    private static final String COLUMN_FIS_BASE_CURRENCY = "Fis_base_currency";

    private static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    private static final String COLUMN_FGOODS_VALUE = "Fgoods_value";
    private static final String COLUMN_FLEVERAGE = "Fleverage";


    public SubAccountHostingFundTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public HostingFund fromResultSet(ResultSet resultSet) throws Exception {
        HostingFund hostingFund = new HostingFund();
        hostingFund.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        hostingFund.setPreFund(resultSet.getDouble(COLUMN_FPRE_FUND));
        hostingFund.setDepositAmount(resultSet.getDouble(COLUMN_FDEPOSIT_AMOUNT));
        hostingFund.setWithdrawAmount(resultSet.getDouble(COLUMN_FWITHDRAW_AMOUNT));
        hostingFund.setCloseProfit(resultSet.getDouble(COLUMN_FCLOSE_PROFIT));
        hostingFund.setPositionProfit(resultSet.getDouble(COLUMN_FPOSITION_PROFIT));
        hostingFund.setUseMargin(resultSet.getDouble(COLUMN_FUSE_MARGIN));
        hostingFund.setFrozenMargin(resultSet.getDouble(COLUMN_FFROZEN_MARGIN));
        hostingFund.setUseCommission(resultSet.getDouble(COLUMN_FUSE_COMMISSION));
        hostingFund.setFrozenCommission(resultSet.getDouble(COLUMN_FFROZEN_COMMISSION));
        hostingFund.setAvailableFund(resultSet.getDouble(COLUMN_FAVAILABLE_FUND));
        hostingFund.setDynamicBenefit(resultSet.getDouble(COLUMN_FDYNAMIC_BENEFIT));
        hostingFund.setRiskRate(resultSet.getDouble(COLUMN_FRISK_RATE));
        hostingFund.setCurrency(resultSet.getString(COLUMN_FCURRENCY));
        hostingFund.setCreditAmount(resultSet.getDouble(COLUMN_FCREDIT_AMOUNT));
        hostingFund.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        hostingFund.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));

        hostingFund.setGoodsValue(resultSet.getDouble(COLUMN_FGOODS_VALUE));
        hostingFund.setLeverage(resultSet.getDouble(COLUMN_FLEVERAGE));

        return hostingFund;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (16 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fhosting_fund_id BIGINT UNSIGNED NOT NULL,")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fpre_fund DOUBLE DEFAULT 0.0,")
                    .append("Fcurrency VARCHAR(32) NOT NULL DEFAULT '',")
                    .append("Fdeposit_amount DOUBLE DEFAULT 0.0,")
                    .append("Fwithdraw_amount DOUBLE DEFAULT 0.0,")
                    .append("Fclose_profit DOUBLE DEFAULT 0.0,")
                    .append("Fuse_margin DOUBLE DEFAULT 0.0,")
                    .append("Fuse_commission DOUBLE DEFAULT 0.0,")
                    .append("Fposition_profit DOUBLE DEFAULT 0.0,")
                    .append("Ffrozen_margin DOUBLE DEFAULT 0.0,")
                    .append("FfrozenCommission DOUBLE DEFAULT 0.0,")
                    .append("Favailable_fund DOUBLE DEFAULT 0.0,")
                    .append("Fdynamic_benefit DOUBLE DEFAULT 0.0,")
                    .append("Frisk_rate DOUBLE DEFAULT 0.0,")
                    .append("Fcredit_amount DOUBLE DEFAULT 0.0,")
                    .append("Fis_base_currency SMALLINT UNSIGNED NOT NULL DEFAULT 0,")

                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fhosting_fund_id,Fsub_account_id,Fcurrency,Fis_base_currency)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }

        if (17 == targetVersion) {
            String sql = " ADD COLUMN Fgoods_value DOUBLE DEFAULT 0.0";
            executeAlterSql(operator, sql);
            sql = " ADD COLUMN Fleverage  DOUBLE DEFAULT 0.0";
            executeAlterSql(operator, sql);
        }

    }

    private void executeAlterSql(IDBOperator idbOperator, String sql) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder(128);
        sqlBuilder.append("ALTER TABLE ").append(getTableName()).append(" ")
                .append(sql);
        idbOperator.execute(sqlBuilder.toString());
    }

    public HostingFundPage query(ReqSubAccountFundHistoryOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);
        Preconditions.checkArgument(option.getSubAccountId() > 0);

        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FIS_BASE_CURRENCY + "=?", option.isBaseCurrency() ? 1 : 0);
        if (option.isSetStartTimestampMs()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FHOSTING_FUND_ID + ">=?", option.getStartTimestampMs() / 1000);
        }
        if (option.isSetEndTimestampMs()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FHOSTING_FUND_ID + "<=?", option.getEndTimestampMs() / 1000);
        }
        if (option.isSetSubAccountId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", option.getSubAccountId());
        }

        HostingFundPage page = new HostingFundPage();
        if (pageOption != null) {
            if (pageOption.isSetPageIndex()) {
                builder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            }
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(builder));
            }
        }
        page.setPage(super.getItemList(builder));

        return page;
    }

    public HostingFund queryLatest(long subAccountId, String currency, boolean isBase) throws SQLException {
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FIS_BASE_CURRENCY + "=?", isBase ? 1 : 0);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountId);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", currency);
        builder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FHOSTING_FUND_ID);
        builder.setPage(0, 1);
        return super.getItem(builder);
    }

    public void add(HostingFund hostingFund, boolean isBaseCurrency) throws SQLException {
        Preconditions.checkNotNull(hostingFund);
        Preconditions.checkArgument(hostingFund.getSubAccountId() > 0);
        Preconditions.checkArgument(hostingFund.isSetCurrency());
        PreparedFields fields = getPreparedFields(hostingFund, isBaseCurrency);
        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, now);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);

        fields.addLong(COLUMN_FHOSTING_FUND_ID, now / 1000);

        super.insert(fields);
    }

    private PreparedFields getPreparedFields(HostingFund hostingFund, boolean isBaseCurrency) {
        PreparedFields fields = new PreparedFields();
        if (hostingFund.isSetSubAccountId()) {
            fields.addLong(COLUMN_FSUB_ACCOUNT_ID, hostingFund.getSubAccountId());
        }
        if (hostingFund.isSetPreFund()) {
            fields.addDouble(COLUMN_FPRE_FUND, hostingFund.getPreFund());
        }
        if (hostingFund.isSetDepositAmount()) {
            fields.addDouble(COLUMN_FDEPOSIT_AMOUNT, hostingFund.getDepositAmount());
        }
        if (hostingFund.isSetWithdrawAmount()) {
            fields.addDouble(COLUMN_FWITHDRAW_AMOUNT, hostingFund.getWithdrawAmount());
        }
        if (hostingFund.isSetCloseProfit()) {
            fields.addDouble(COLUMN_FCLOSE_PROFIT, hostingFund.getCloseProfit());
        }
        if (hostingFund.isSetPositionProfit()) {
            fields.addDouble(COLUMN_FPOSITION_PROFIT, hostingFund.getPositionProfit());
        }
        if (hostingFund.isSetUseMargin()) {
            fields.addDouble(COLUMN_FUSE_MARGIN, hostingFund.getUseMargin());
        }
        if (hostingFund.isSetFrozenMargin()) {
            fields.addDouble(COLUMN_FFROZEN_MARGIN, hostingFund.getFrozenMargin());
        }
        if (hostingFund.isSetUseCommission()) {
            fields.addDouble(COLUMN_FUSE_COMMISSION, hostingFund.getUseCommission());
        }
        if (hostingFund.isSetFrozenCommission()) {
            fields.addDouble(COLUMN_FFROZEN_COMMISSION, hostingFund.getFrozenCommission());
        }
        if (hostingFund.isSetAvailableFund()) {
            fields.addDouble(COLUMN_FAVAILABLE_FUND, hostingFund.getAvailableFund());
        }
        if (hostingFund.isSetDynamicBenefit()) {
            fields.addDouble(COLUMN_FDYNAMIC_BENEFIT, hostingFund.getDynamicBenefit());
        }
        if (hostingFund.isSetRiskRate()) {
            fields.addDouble(COLUMN_FRISK_RATE, hostingFund.getRiskRate());
        }
        if (hostingFund.isSetCurrency()) {
            fields.addString(COLUMN_FCURRENCY, hostingFund.getCurrency());
        }
        if (hostingFund.isSetCreditAmount()) {
            fields.addDouble(COLUMN_FCREDIT_AMOUNT, hostingFund.getCreditAmount());
        }
        if (isBaseCurrency) {
            fields.addInt(COLUMN_FIS_BASE_CURRENCY, 1);
        }
        if (hostingFund.isSetGoodsValue()) {
            fields.addDouble(COLUMN_FGOODS_VALUE, hostingFund.getGoodsValue());
        }
        if (hostingFund.isSetLeverage()) {
            fields.addDouble(COLUMN_FLEVERAGE, hostingFund.getLeverage());
        }
        return fields;
    }
}
