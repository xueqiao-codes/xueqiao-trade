package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementFundDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.SettlementFundDetail;
import xueqiao.trade.hosting.asset.thriftapi.SettlementFundDetailPage;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 雪橇子账户资金结算历史明细
 * @author walter
 */
public class SettlementFundDetailTable extends TableHelper<SettlementFundDetail> implements IDBTable {

    private static final String TABLE_NAME = "t_settlement_fund_detail";

    private static final String COLUMN_FSETTLEMENT_ID = "Fsettlement_id";
    private static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    private static final String COLUMN_FPRE_FUND = "Fpre_fund";
    private static final String COLUMN_FCURRENCY = "Fcurrency";
    private static final String COLUMN_FSETTLEMENT_TIMESTAMP = "Fsettlement_timestamp";
    private static final String COLUMN_FDEPOSIT_AMOUNT = "Fdeposit_amount";
    private static final String COLUMN_FWITHDRAW_AMOUNT = "Fwithdraw_amount";
    private static final String COLUMN_FCLOSE_PROFIT = "Fclose_profit";
    private static final String COLUMN_FUSE_MARGIN = "Fuse_margin";
    private static final String COLUMN_FUSE_COMMISSION = "Fuse_commission";
    private static final String COLUMN_FBALANCE = "Fbalance";
    private static final String COLUMN_FEXCHANGE_RATE_HISTORY_ID = "Fexchange_rate_history_id";
    private static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    private static final String COLUMN_FGOODS_VALUE ="Fgoods_value";
    private static final String COLUMN_FLEVERAGE ="Fleverage";


    public SettlementFundDetailTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    public List<SettlementFundDetail> query(String currency, long subAccountId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();

        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", currency);
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountId);
        sqlQueryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FCREATE_TIMESTAMP);
        return super.getItemList(sqlQueryBuilder, forUpdate);
    }

    public SettlementFundDetailPage query(ReqSettlementFundDetailOption option, PageOption pageOption) throws SQLException, ErrorInfo {
        Preconditions.checkNotNull(option);
        Preconditions.checkNotNull(pageOption);
        pageOption.checkValid();
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        if (option.isSetCurrency()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCURRENCY + "=?", option.getCurrency());
        }
        if (option.getSubAccountIdsSize() > 0) {
            sqlQueryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID, option.getSubAccountIds());
        }

        sqlQueryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FCREATE_TIMESTAMP);
        sqlQueryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        SettlementFundDetailPage pageResult = new SettlementFundDetailPage();
        if (pageOption.isNeedTotalCount()) {
            pageResult.setTotal(super.getTotalNum(sqlQueryBuilder));
        }
        pageResult.setPage(super.getItemList(sqlQueryBuilder));

        return pageResult;
    }

    public List<SettlementFundDetail> queryAll() throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FCREATE_TIMESTAMP);
        return super.getItemList(sqlQueryBuilder, false);
    }


    public void add(SettlementFundDetail fundDetail) throws SQLException {
        Preconditions.checkNotNull(fundDetail);
        Preconditions.checkArgument(fundDetail.isSetCurrency());
        Preconditions.checkArgument(fundDetail.isSetSubAccountId());
        Preconditions.checkArgument(fundDetail.isSetSettlementId());

        PreparedFields fields = new PreparedFields();
        fields.addLong(COLUMN_FSETTLEMENT_ID, fundDetail.getSettlementId());
        fields.addLong(COLUMN_FSUB_ACCOUNT_ID, fundDetail.getSubAccountId());
        fields.addString(COLUMN_FCURRENCY, fundDetail.getCurrency());
        if (fundDetail.isSetPreFund()) {
            fields.addDouble(COLUMN_FPRE_FUND, fundDetail.getPreFund());
        }
        if (fundDetail.isSetSettlementTimestamp()) {
            fields.addLong(COLUMN_FSETTLEMENT_TIMESTAMP, fundDetail.getSettlementTimestamp());
        }
        if (fundDetail.isSetDepositAmount()) {
            fields.addDouble(COLUMN_FDEPOSIT_AMOUNT, fundDetail.getDepositAmount());
        }
        if (fundDetail.isSetWithdrawAmount()) {
            fields.addDouble(COLUMN_FWITHDRAW_AMOUNT, fundDetail.getWithdrawAmount());
        }
        if (fundDetail.isSetCloseProfit()) {
            fields.addDouble(COLUMN_FCLOSE_PROFIT, fundDetail.getCloseProfit());
        }
        if (fundDetail.isSetUseMargin()) {
            fields.addDouble(COLUMN_FUSE_MARGIN, fundDetail.getUseMargin());
        }
        if (fundDetail.isSetUseCommission()) {
            fields.addDouble(COLUMN_FUSE_COMMISSION, fundDetail.getUseCommission());
        }
        if (fundDetail.isSetBalance()) {
            fields.addDouble(COLUMN_FBALANCE, fundDetail.getBalance());
        }
        if (fundDetail.isSetExchangeRateHistoryId()) {
            fields.addLong(COLUMN_FEXCHANGE_RATE_HISTORY_ID, fundDetail.getExchangeRateHistoryId());
        }
        if (fundDetail.isSetGoodsValue()){
            fields.addDouble(COLUMN_FGOODS_VALUE, fundDetail.getGoodsValue());
        }
        if (fundDetail.isSetLeverage()){
            fields.addDouble(COLUMN_FLEVERAGE, fundDetail.getLeverage());
        }
        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, now);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);
        super.insert(fields);
    }

    @Override
    public SettlementFundDetail fromResultSet(ResultSet resultSet) throws Exception {
        SettlementFundDetail settlementFundDetail = new SettlementFundDetail();
        settlementFundDetail.setSettlementId(resultSet.getLong(COLUMN_FSETTLEMENT_ID));
        settlementFundDetail.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        settlementFundDetail.setPreFund(resultSet.getDouble(COLUMN_FPRE_FUND));
        settlementFundDetail.setCurrency(resultSet.getString(COLUMN_FCURRENCY));
        settlementFundDetail.setSettlementTimestamp(resultSet.getLong(COLUMN_FSETTLEMENT_TIMESTAMP));
        settlementFundDetail.setDepositAmount(resultSet.getDouble(COLUMN_FDEPOSIT_AMOUNT));
        settlementFundDetail.setWithdrawAmount(resultSet.getDouble(COLUMN_FWITHDRAW_AMOUNT));
        settlementFundDetail.setCloseProfit(resultSet.getDouble(COLUMN_FCLOSE_PROFIT));
        settlementFundDetail.setUseMargin(resultSet.getDouble(COLUMN_FUSE_MARGIN));
        settlementFundDetail.setUseCommission(resultSet.getDouble(COLUMN_FUSE_COMMISSION));
        settlementFundDetail.setBalance(resultSet.getDouble(COLUMN_FBALANCE));
        settlementFundDetail.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        settlementFundDetail.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));
        settlementFundDetail.setExchangeRateHistoryId(resultSet.getLong(COLUMN_FEXCHANGE_RATE_HISTORY_ID));

        settlementFundDetail.setGoodsValue(resultSet.getDouble(COLUMN_FGOODS_VALUE));
        settlementFundDetail.setLeverage(resultSet.getDouble(COLUMN_FLEVERAGE));
        return settlementFundDetail;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (4 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fsettlement_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fpre_fund DOUBLE DEFAULT 0.0,")
                    .append("Fcurrency VARCHAR(32) NOT NULL DEFAULT '',")
                    .append("Fsettlement_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fdeposit_amount DOUBLE DEFAULT 0.0,")
                    .append("Fwithdraw_amount DOUBLE DEFAULT 0.0,")
                    .append("Fclose_profit DOUBLE DEFAULT 0.0,")
                    .append("Fuse_margin DOUBLE DEFAULT 0.0,")
                    .append("Fuse_commission DOUBLE DEFAULT 0.0,")
                    .append("Fbalance DOUBLE DEFAULT 0.0,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fsettlement_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            idbOperator.execute(sqlBuilder.toString());
        }

        if (9 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("ALTER TABLE ").append(getTableName())
                    .append(" ADD COLUMN Fexchange_rate_history_id BIGINT UNSIGNED NOT NULL DEFAULT 0");
            idbOperator.execute(sqlBuilder.toString());
        }

        if (17 == targetVersion){
            String sql = " ADD COLUMN Fgoods_value DOUBLE DEFAULT 0.0";
            executeAlterSql(idbOperator, sql);
            sql = " ADD COLUMN Fleverage  DOUBLE DEFAULT 0.0";
            executeAlterSql(idbOperator, sql);
        }
    }

    private void executeAlterSql(IDBOperator idbOperator, String sql) throws SQLException {
        StringBuilder sqlBuilder = new StringBuilder(128);
        sqlBuilder.append("ALTER TABLE ").append(getTableName()).append(" ")
                .append(sql);
        idbOperator.execute(sqlBuilder.toString());
    }
}
