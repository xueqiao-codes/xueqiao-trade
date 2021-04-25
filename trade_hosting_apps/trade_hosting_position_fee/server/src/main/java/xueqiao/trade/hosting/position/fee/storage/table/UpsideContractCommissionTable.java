package xueqiao.trade.hosting.position.fee.storage.table;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UpsideContractCommissionTable extends TableHelper<UpsideContractCommission> implements IDBTable {

    private final static String TABLE_NAME = "tupside_contract_commission";

    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_TRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String F_COMMODITY_ID = "Fcommodity_id";
    private final static String F_CONTRACT_CODE = "Fcontract_code";
    private final static String F_CONTRACT_ID = "Fcontract_id";
    private final static String F_EXCHANGE_MIC = "Fexchange_mic";
    private final static String F_UPSIDE_DATA_TYPE = "Fupside_data_type";
    private final static String F_OPEN_RATIO_BY_MONEY = "Fopen_ratio_by_money";
    private final static String F_OPEN_RATIO_BY_VOLUME = "Fopen_ratio_by_volume";
    private final static String F_CLOSE_RATIO_BY_MONEY = "Fclose_ratio_by_money";
    private final static String F_CLOSE_RATIO_BY_VOLUME = "Fclose_ratio_by_volume";
    private final static String F_CLOSE_TODAY_RATIO_BY_MONEY = "Fclose_today_ratio_by_money";
    private final static String F_CLOSE_TODAY_RATIO_BY_VOLUME = "Fclose_today_ratio_by_volume";
    private final static String F_CURRENCY = "Fcurrency";
    private final static String F_CURRENCY_GROUP = "Fcurrency_group";
    private final static String F_IS_SYNC = "Fis_sync";
    private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";

    public UpsideContractCommissionTable(Connection conn) {
        super(conn);
    }

    public void insert(UpsideContractCommission commission) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(commission);
        pf.addLong(F_SUB_ACCOUNT_ID, commission.getSubAccountId());
        pf.addLong(F_COMMODITY_ID, commission.getContractInfo().getCommodityId());
        pf.addString(F_CONTRACT_CODE, commission.getContractInfo().getContractCode());
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.insert(pf);
    }

    public void update(UpsideContractCommission commission) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(commission);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.update(pf, F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + " =?",
                commission.getSubAccountId(),
                commission.getContractInfo().getCommodityId(),
                commission.getContractInfo().getContractCode());
    }

    public void setSyncStatus(long subAccountId, long commodityId, String contractCode) throws SQLException {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 1);
        super.update(pf, F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + " =?",
                subAccountId,
                commodityId,
                contractCode);
    }

    public void delete(long subAccountId, long commodityId, String contractCode) throws SQLException {
        super.delete(F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + " =?",
                subAccountId, commodityId, contractCode);
    }

    public void delete(long contractId) throws SQLException {
        super.delete(F_CONTRACT_ID + " =?", contractId);
    }

    public UpsideContractCommission query(long subAccountId, long commodityId, String contractCode) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CONTRACT_CODE + "=?", contractCode);
        return super.getItem(qryBuilder);
    }

//    public List<UpsideContractCommission> query(long subAccountId, long commodityId) throws SQLException {
//        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
//        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
//        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
//        return super.getItemList(qryBuilder);
//    }

    public List<UpsideContractCommission> queryByTradeAccountId(long tradeAccountId, long commodityId, String contractCode, UpsideDataType dataType) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TRADE_ACCOUNT_ID + "=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        if (StringUtils.isNotBlank(contractCode)) {
            qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CONTRACT_CODE + "=?", contractCode);
        }
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_UPSIDE_DATA_TYPE + "=?", dataType.getValue());
        return super.getItemList(qryBuilder);
    }

    public List<UpsideContractCommission> queryUnsyncList(int pageIndex, int pageSize) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_SYNC + "=?", 0);
        qryBuilder.setPage(pageIndex, pageSize);
        return super.getItemList(qryBuilder);
    }

    public UpsideContractCommissionPage query(QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (queryOptions != null) {
            if (queryOptions.isSetSubAccountId()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", queryOptions.getSubAccountId());
            }
            if (queryOptions.isSetExchangeMic()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_EXCHANGE_MIC + "=?", queryOptions.getExchangeMic());
            }
            if (queryOptions.isSetCommodityId()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", queryOptions.getCommodityId());
            }
            if (queryOptions.isSetContractCode()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CONTRACT_CODE + "=?", queryOptions.getContractCode());
            }
        }
        qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, F_LASTMODITY_TIMESTAMP_MS);
        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        UpsideContractCommissionPage resultPage = new UpsideContractCommissionPage();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalNum(super.getTotalNum(qryBuilder));
        }
        resultPage.setUpsideContractCommissionList(super.getItemList(qryBuilder));
        return resultPage;
    }

    private PreparedFields getPreparedFields(UpsideContractCommission commission) {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 0);
        if (commission.isSetDataType()) {
            pf.addInt(F_UPSIDE_DATA_TYPE, commission.getDataType().getValue());
        }
        if (commission.getContractInfo() != null) {
            if (commission.getContractInfo().isSetExchangeMic()) {
                pf.addString(F_EXCHANGE_MIC, commission.getContractInfo().getExchangeMic());
            }
            if (commission.getContractInfo().isSetContractId()) {
                pf.addLong(F_CONTRACT_ID, commission.getContractInfo().getContractId());
            }
            if (commission.getContractInfo().isSetTradeAccountId()) {
                pf.addLong(F_TRADE_ACCOUNT_ID, commission.getContractInfo().getTradeAccountId());
            }
        }
        if (commission.getCommission() != null) {
            CommissionInfo commissionInfo = commission.getCommission();
            if (commissionInfo.isSetOpenRatioByMoney()) {
                pf.addDouble(F_OPEN_RATIO_BY_MONEY, commissionInfo.getOpenRatioByMoney());
            }
            if (commissionInfo.isSetOpenRatioByVolume()) {
                pf.addDouble(F_OPEN_RATIO_BY_VOLUME, commissionInfo.getOpenRatioByVolume());
            }
            if (commissionInfo.isSetCloseRatioByMoney()) {
                pf.addDouble(F_CLOSE_RATIO_BY_MONEY, commissionInfo.getCloseRatioByMoney());
            }
            if (commissionInfo.isSetCloseRatioByVolume()) {
                pf.addDouble(F_CLOSE_RATIO_BY_VOLUME, commissionInfo.getCloseRatioByVolume());
            }
            if (commissionInfo.isSetCloseTodayRatioByMoney()) {
                pf.addDouble(F_CLOSE_TODAY_RATIO_BY_MONEY, commissionInfo.getCloseTodayRatioByMoney());
            }
            if (commissionInfo.isSetCloseTodayRatioByVolume()) {
                pf.addDouble(F_CLOSE_TODAY_RATIO_BY_VOLUME, commissionInfo.getCloseTodayRatioByVolume());
            }
            if (commissionInfo.isSetCurrency()) {
                pf.addString(F_CURRENCY, commissionInfo.getCurrency());
            }
            if (commissionInfo.isSetCurrencyGroup()) {
                pf.addString(F_CURRENCY_GROUP, commissionInfo.getCurrencyGroup());
            }
        }
        return pf;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public UpsideContractCommission fromResultSet(ResultSet rs) throws Exception {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setTradeAccountId(rs.getLong(F_TRADE_ACCOUNT_ID));
        contractInfo.setCommodityId(rs.getLong(F_COMMODITY_ID));
        contractInfo.setContractCode(rs.getString(F_CONTRACT_CODE));
        contractInfo.setContractId(rs.getLong(F_CONTRACT_ID));
        contractInfo.setExchangeMic(rs.getString(F_EXCHANGE_MIC));

        CommissionInfo commissionInfo = new CommissionInfo();
        commissionInfo.setOpenRatioByMoney(rs.getDouble(F_OPEN_RATIO_BY_MONEY));
        commissionInfo.setOpenRatioByVolume(rs.getDouble(F_OPEN_RATIO_BY_VOLUME));
        commissionInfo.setCloseRatioByMoney(rs.getDouble(F_CLOSE_RATIO_BY_MONEY));
        commissionInfo.setCloseRatioByVolume(rs.getDouble(F_CLOSE_RATIO_BY_VOLUME));
        commissionInfo.setCloseTodayRatioByMoney(rs.getDouble(F_CLOSE_TODAY_RATIO_BY_MONEY));
        commissionInfo.setCloseTodayRatioByVolume(rs.getDouble(F_CLOSE_TODAY_RATIO_BY_VOLUME));
        commissionInfo.setCurrency(rs.getString(F_CURRENCY));
        commissionInfo.setCurrencyGroup(rs.getString(F_CURRENCY_GROUP));

        UpsideContractCommission upsideContractCommission = new UpsideContractCommission();
        upsideContractCommission.setSubAccountId(rs.getLong(F_SUB_ACCOUNT_ID));
        upsideContractCommission.setDataType(UpsideDataType.findByValue(rs.getInt(F_UPSIDE_DATA_TYPE)));
        upsideContractCommission.setIsSync(rs.getBoolean(F_IS_SYNC));
        upsideContractCommission.setLastmodifyTimestampMs(rs.getLong(F_LASTMODITY_TIMESTAMP_MS));
        upsideContractCommission.setContractInfo(contractInfo);
        upsideContractCommission.setCommission(commissionInfo);

        return upsideContractCommission;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_COMMODITY_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_CONTRACT_CODE).append(" VARCHAR(64) NOT NULL DEFAULT '',")
                    .append(F_TRADE_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_CONTRACT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXCHANGE_MIC).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_UPSIDE_DATA_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_OPEN_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_OPEN_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_TODAY_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_TODAY_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CURRENCY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_CURRENCY_GROUP).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_IS_SYNC).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_LASTMODITY_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_SUB_ACCOUNT_ID).append(",").append(F_COMMODITY_ID).append(",").append(F_CONTRACT_CODE).append("),")
                    .append("INDEX(").append(F_TRADE_ACCOUNT_ID).append(",").append(F_COMMODITY_ID).append(",").append(F_CONTRACT_CODE).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
}
