package xueqiao.trade.hosting.position.fee.storage.table;

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

public class XQSpecCommissionSettingsTable extends TableHelper<XQSpecCommissionSettings> implements IDBTable {

    private final static String TABLE_NAME = "txq_spec_commission_settings";
    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_EXCHANGE_MIC = "Fexchange_mic";
    private final static String F_COMMODITY_ID = "Fcommodity_id";
    private final static String F_CALCULATE_TYPE = "Fcalculate_type";
    private final static String F_OPEN_RATIO_BY_MONEY = "Fopen_ratio_by_money";
    private final static String F_OPEN_RATIO_BY_VOLUME = "Fopen_ratio_by_volume";
    private final static String F_CLOSE_RATIO_BY_MONEY = "Fclose_ratio_by_money";
    private final static String F_CLOSE_RATIO_BY_VOLUME = "Fclose_ratio_by_volume";
    private final static String F_CLOSE_TODAY_RATIO_BY_MONEY = "Fclose_today_ratio_by_money";
    private final static String F_CLOSE_TODAY_RATIO_BY_VOLUME = "Fclose_today_ratio_by_volume";
    private final static String F_CURRENCY = "Fcurrency";
    private final static String F_IS_SYNC = "Fis_sync";
    private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";
    private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";

    public XQSpecCommissionSettingsTable(Connection conn) {
        super(conn);
    }

    public void insert(XQSpecCommissionSettings settings) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(settings);
        pf.addLong(F_SUB_ACCOUNT_ID, settings.getSubAccountId());
        pf.addLong(F_COMMODITY_ID, settings.getCommodityId());
        pf.addLong(F_CREATE_TIMESTAMP_MS, currenMillis);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.insert(pf);
    }

    public void update(XQSpecCommissionSettings settings) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(settings);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.update(pf, F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + "=?", settings.getSubAccountId(), settings.getCommodityId());
    }

    public void setSyncStatus(long subAccountId, long commodityId) throws SQLException {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 1);
        super.update(pf, F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =?", subAccountId, commodityId);
    }

    public void delete(long subAccountId, long commodityId) throws SQLException {
        super.delete(F_SUB_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + "=?", subAccountId, commodityId);
    }

    public XQSpecCommissionSettings query(long subAccountId, long commodityId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        return super.getItem(qryBuilder);
    }

    public List<XQSpecCommissionSettings> queryUnsyncList(int pageIndex, int pageSize) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_SYNC + "=?", 0);
        qryBuilder.setPage(pageIndex, pageSize);
        return super.getItemList(qryBuilder);
    }

    public XQSpecCommissionSettingPage query(QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (queryOptions != null) {
            if (queryOptions.isSetSubAccountId()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", queryOptions.getSubAccountId());
            }
            if (queryOptions.isSetExchangeMic()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_EXCHANGE_MIC + "=?", queryOptions.getExchangeMic());
            }
            if (queryOptions.isSetCommodityIds()) {
                qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID, queryOptions.getCommodityIds());
            }
            if (queryOptions.isSetType()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CALCULATE_TYPE + "=?", queryOptions.getType().getValue());
            }
        }
        qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, F_LASTMODITY_TIMESTAMP_MS);
        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        XQSpecCommissionSettingPage resultPage = new XQSpecCommissionSettingPage();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalNum(super.getTotalNum(qryBuilder));
        }
        resultPage.setXQSpecCommissionSettingList(super.getItemList(qryBuilder));
        return resultPage;
    }

    private PreparedFields getPreparedFields(XQSpecCommissionSettings settings) {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 0);
        if (settings.isSetType()) {
            pf.addInt(F_CALCULATE_TYPE, settings.getType().getValue());
        }
        if (settings.isSetCommodityInfo()) {
            if (settings.getCommodityInfo().isSetExchangeMic()) {
                pf.addString(F_EXCHANGE_MIC, settings.getCommodityInfo().getExchangeMic());
            }
        }
        if (settings.getCommissionDelta() != null) {
            CommissionInfo commissionDelta = settings.getCommissionDelta();
            if (commissionDelta.isSetOpenRatioByMoney()) {
                pf.addDouble(F_OPEN_RATIO_BY_MONEY, commissionDelta.getOpenRatioByMoney());
            }
            if (commissionDelta.isSetOpenRatioByVolume()) {
                pf.addDouble(F_OPEN_RATIO_BY_VOLUME, commissionDelta.getOpenRatioByVolume());
            }
            if (commissionDelta.isSetCloseRatioByMoney()) {
                pf.addDouble(F_CLOSE_RATIO_BY_MONEY, commissionDelta.getCloseRatioByMoney());
            }
            if (commissionDelta.isSetCloseRatioByVolume()) {
                pf.addDouble(F_CLOSE_RATIO_BY_VOLUME, commissionDelta.getCloseRatioByVolume());
            }
            if (commissionDelta.isSetCloseTodayRatioByMoney()) {
                pf.addDouble(F_CLOSE_TODAY_RATIO_BY_MONEY, commissionDelta.getCloseTodayRatioByMoney());
            }
            if (commissionDelta.isSetCloseTodayRatioByVolume()) {
                pf.addDouble(F_CLOSE_TODAY_RATIO_BY_VOLUME, commissionDelta.getCloseTodayRatioByVolume());
            }
            if (commissionDelta.isSetCurrency()) {
                pf.addString(F_CURRENCY, commissionDelta.getCurrency());
            }
        }
        return pf;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public XQSpecCommissionSettings fromResultSet(ResultSet rs) throws Exception {
        XQSpecCommissionSettings xqSpecCommissionSettings = new XQSpecCommissionSettings();
        xqSpecCommissionSettings.setSubAccountId(rs.getLong(F_SUB_ACCOUNT_ID));
//        xqSpecCommissionSettings.setExchangeMic(rs.getString(F_EXCHANGE_MIC));
        xqSpecCommissionSettings.setCommodityId(rs.getLong(F_COMMODITY_ID));
        xqSpecCommissionSettings.setType(FeeCalculateType.findByValue(rs.getInt(F_CALCULATE_TYPE)));
        xqSpecCommissionSettings.setCreateTimestampMs(rs.getLong(F_CREATE_TIMESTAMP_MS));
        xqSpecCommissionSettings.setLastmodifyTimestampMs(rs.getLong(F_LASTMODITY_TIMESTAMP_MS));
        xqSpecCommissionSettings.setIsSync(rs.getBoolean(F_IS_SYNC));

        CommissionInfo commissionDelta = new CommissionInfo();
        commissionDelta.setOpenRatioByMoney(rs.getDouble(F_OPEN_RATIO_BY_MONEY));
        commissionDelta.setOpenRatioByVolume(rs.getDouble(F_OPEN_RATIO_BY_VOLUME));
        commissionDelta.setCloseRatioByMoney(rs.getDouble(F_CLOSE_RATIO_BY_MONEY));
        commissionDelta.setCloseRatioByVolume(rs.getDouble(F_CLOSE_RATIO_BY_VOLUME));
        commissionDelta.setCloseTodayRatioByMoney(rs.getDouble(F_CLOSE_TODAY_RATIO_BY_MONEY));
        commissionDelta.setCloseTodayRatioByVolume(rs.getDouble(F_CLOSE_TODAY_RATIO_BY_VOLUME));
        commissionDelta.setCurrency(rs.getString(F_CURRENCY));

        CommodityInfo commodityInfo = new CommodityInfo();
        commodityInfo.setExchangeMic(rs.getString(F_EXCHANGE_MIC));

        xqSpecCommissionSettings.setCommissionDelta(commissionDelta);
        xqSpecCommissionSettings.setCommodityInfo(commodityInfo);
        return xqSpecCommissionSettings;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_COMMODITY_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_EXCHANGE_MIC).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_CALCULATE_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_OPEN_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_OPEN_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_TODAY_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CLOSE_TODAY_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CURRENCY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_IS_SYNC).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_LASTMODITY_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_SUB_ACCOUNT_ID).append(",").append(F_COMMODITY_ID).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
}
