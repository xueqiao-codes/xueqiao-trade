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

public class UpsideContractMarginTable extends TableHelper<UpsideContractMargin> implements IDBTable {

    private final static String TABLE_NAME = "tupside_contract_margin";

    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_TRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String F_COMMODITY_ID = "Fcommodity_id";
    private final static String F_CONTRACT_CODE = "Fcontract_code";
    private final static String F_CONTRACT_ID = "Fcontract_id";
    private final static String F_EXCHANGE_MIC = "Fexchange_mic";
    private final static String F_UPSIDE_DATA_TYPE = "Fupside_data_type";
    private final static String F_LONG_MARGIN_RATIO_BY_MONEY = "Flong_margin_ratio_by_money";
    private final static String F_LONG_MARGIN_RATIO_BY_VOLUME = "Flong_margin_ratio_by_volume";
    private final static String F_SHORT_MARGIN_RATIO_BY_MONEY = "Fshort_margin_ratio_by_money";
    private final static String F_SHORT_MARGIN_RATIO_BY_VOLUME = "Fshort_margin_ratio_by_volume";
    private final static String F_CURRENCY = "Fcurrency";
    private final static String F_CURRENCY_GROUP = "Fcurrency_group";
    private final static String F_IS_SYNC = "Fis_sync";
    private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";

    public UpsideContractMarginTable(Connection conn) {
        super(conn);
    }

    public void insert(UpsideContractMargin margin) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(margin);
        pf.addLong(F_SUB_ACCOUNT_ID, margin.getSubAccountId());
        pf.addLong(F_COMMODITY_ID, margin.getContractInfo().getCommodityId());
        pf.addString(F_CONTRACT_CODE, margin.getContractInfo().getContractCode());
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.insert(pf);
    }

    public void update(UpsideContractMargin margin) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(margin);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.update(pf, F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + " =?",
                margin.getSubAccountId(),
                margin.getContractInfo().getCommodityId(),
                margin.getContractInfo().getContractCode());
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

    public UpsideContractMargin query(long subAccountId, long commodityId, String contractCode) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CONTRACT_CODE + "=?", contractCode);
        return super.getItem(qryBuilder);
    }

    public List<UpsideContractMargin> queryByTradeAccountId(long tradeAccountId, long commodityId, String contractCode) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TRADE_ACCOUNT_ID + "=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        if (StringUtils.isNotBlank(contractCode)) {
            qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CONTRACT_CODE + "=?", contractCode);
        }
        return super.getItemList(qryBuilder);
    }

    public List<UpsideContractMargin> queryUnsyncList(int pageIndex, int pageSize) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_SYNC + "=?", 0);
        qryBuilder.setPage(pageIndex, pageSize);
        return super.getItemList(qryBuilder);
    }

    public UpsideContractMarginPage query(QueryUpsidePFeeOptions queryOptions, IndexedPageOption pageOption) throws SQLException {
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

        UpsideContractMarginPage resultPage = new UpsideContractMarginPage();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalNum(super.getTotalNum(qryBuilder));
        }
        resultPage.setUpsideContractMarginList(super.getItemList(qryBuilder));
        return resultPage;
    }

    private PreparedFields getPreparedFields(UpsideContractMargin margin) {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 0);
        if (margin.isSetDataType()) {
            pf.addInt(F_UPSIDE_DATA_TYPE, margin.getDataType().getValue());
        }
        if (margin.getContractInfo() != null) {
            if (margin.getContractInfo().isSetExchangeMic()) {
                pf.addString(F_EXCHANGE_MIC, margin.getContractInfo().getExchangeMic());
            }
            if (margin.getContractInfo().isSetContractId()) {
                pf.addLong(F_CONTRACT_ID, margin.getContractInfo().getContractId());
            }
            if (margin.getContractInfo().isSetTradeAccountId()) {
                pf.addLong(F_TRADE_ACCOUNT_ID, margin.getContractInfo().getTradeAccountId());
            }
        }
        if (margin.getMargin() != null) {
            MarginInfo marginInfo = margin.getMargin();
            if (marginInfo.isSetLongMarginRatioByMoney()) {
                pf.addDouble(F_LONG_MARGIN_RATIO_BY_MONEY, marginInfo.getLongMarginRatioByMoney());
            }
            if (marginInfo.isSetLongMarginRatioByVolume()) {
                pf.addDouble(F_LONG_MARGIN_RATIO_BY_VOLUME, marginInfo.getLongMarginRatioByVolume());
            }
            if (marginInfo.isSetShortMarginRatioByMoney()) {
                pf.addDouble(F_SHORT_MARGIN_RATIO_BY_MONEY, marginInfo.getShortMarginRatioByMoney());
            }
            if (marginInfo.isSetShortMarginRatioByVolume()) {
                pf.addDouble(F_SHORT_MARGIN_RATIO_BY_VOLUME, marginInfo.getShortMarginRatioByVolume());
            }
            if (marginInfo.isSetCurrency()) {
                pf.addString(F_CURRENCY, marginInfo.getCurrency());
            }
            if (marginInfo.isSetCurrencyGroup()) {
                pf.addString(F_CURRENCY_GROUP, marginInfo.getCurrencyGroup());
            }
        }
        return pf;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public UpsideContractMargin fromResultSet(ResultSet rs) throws Exception {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setTradeAccountId(rs.getLong(F_TRADE_ACCOUNT_ID));
        contractInfo.setCommodityId(rs.getLong(F_COMMODITY_ID));
        contractInfo.setContractCode(rs.getString(F_CONTRACT_CODE));
        contractInfo.setContractId(rs.getLong(F_CONTRACT_ID));
        contractInfo.setExchangeMic(rs.getString(F_EXCHANGE_MIC));

        MarginInfo marginInfo = new MarginInfo();
        marginInfo.setLongMarginRatioByMoney(rs.getDouble(F_LONG_MARGIN_RATIO_BY_MONEY));
        marginInfo.setLongMarginRatioByVolume(rs.getDouble(F_LONG_MARGIN_RATIO_BY_VOLUME));
        marginInfo.setShortMarginRatioByMoney(rs.getDouble(F_SHORT_MARGIN_RATIO_BY_MONEY));
        marginInfo.setShortMarginRatioByVolume(rs.getDouble(F_SHORT_MARGIN_RATIO_BY_VOLUME));
        marginInfo.setCurrency(rs.getString(F_CURRENCY));
        marginInfo.setCurrencyGroup(rs.getString(F_CURRENCY_GROUP));

        UpsideContractMargin upsideContractMargin = new UpsideContractMargin();
        upsideContractMargin.setSubAccountId(rs.getLong(F_SUB_ACCOUNT_ID));
        upsideContractMargin.setDataType(UpsideDataType.findByValue(rs.getInt(F_UPSIDE_DATA_TYPE)));
        upsideContractMargin.setIsSync(rs.getBoolean(F_IS_SYNC));
        upsideContractMargin.setLastmodifyTimestampMs(rs.getLong(F_LASTMODITY_TIMESTAMP_MS));
        upsideContractMargin.setContractInfo(contractInfo);
        upsideContractMargin.setMargin(marginInfo);

        return upsideContractMargin;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_TRADE_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_COMMODITY_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_CONTRACT_CODE).append(" VARCHAR(64) NOT NULL DEFAULT '',")
                    .append(F_CONTRACT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXCHANGE_MIC).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_UPSIDE_DATA_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_LONG_MARGIN_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_LONG_MARGIN_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_SHORT_MARGIN_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_SHORT_MARGIN_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
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
