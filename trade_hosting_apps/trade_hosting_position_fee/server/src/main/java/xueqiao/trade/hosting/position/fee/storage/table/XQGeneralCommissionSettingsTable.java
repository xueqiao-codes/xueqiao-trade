package xueqiao.trade.hosting.position.fee.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.FeeCalculateType;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class XQGeneralCommissionSettingsTable extends TableHelper<XQGeneralCommissionSettings> implements IDBTable {

    private final static String TABLE_NAME = "txq_general_commission_settings";
    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
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

    public XQGeneralCommissionSettingsTable(Connection conn) {
        super(conn);
    }

    public void insert(XQGeneralCommissionSettings settings) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(settings);
        pf.addLong(F_SUB_ACCOUNT_ID, settings.getSubAccountId());
        pf.addLong(F_CREATE_TIMESTAMP_MS, currenMillis);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.insert(pf);
    }

    public void update(XQGeneralCommissionSettings settings) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(settings);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.update(pf, F_SUB_ACCOUNT_ID + "=?", settings.getSubAccountId());
    }

    public void setSyncStatus(long subAccountId) throws SQLException {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 1);
        super.update(pf, F_SUB_ACCOUNT_ID + "=?", subAccountId);
    }

    public XQGeneralCommissionSettings query(long subAccountId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
        return super.getItem(qryBuilder);
    }

    public List<XQGeneralCommissionSettings> queryUnsyncList(int pageIndex, int pageSize) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_SYNC + "=?", 0);
        qryBuilder.setPage(pageIndex, pageSize);
        return super.getItemList(qryBuilder);
    }

    private PreparedFields getPreparedFields(XQGeneralCommissionSettings settings) {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 0);
        if (settings.isSetType()) {
            pf.addInt(F_CALCULATE_TYPE, settings.getType().getValue());
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
    public XQGeneralCommissionSettings fromResultSet(ResultSet rs) throws Exception {
        XQGeneralCommissionSettings xqGeneralCommissionSettings = new XQGeneralCommissionSettings();
        xqGeneralCommissionSettings.setSubAccountId(rs.getLong(F_SUB_ACCOUNT_ID));
        xqGeneralCommissionSettings.setType(FeeCalculateType.findByValue(rs.getInt(F_CALCULATE_TYPE)));
        xqGeneralCommissionSettings.setCreateTimestampMs(rs.getLong(F_CREATE_TIMESTAMP_MS));
        xqGeneralCommissionSettings.setLastmodifyTimestampMs(rs.getLong(F_LASTMODITY_TIMESTAMP_MS));
        xqGeneralCommissionSettings.setIsSync(rs.getBoolean(F_IS_SYNC));

        CommissionInfo commissionDelta = new CommissionInfo();
        commissionDelta.setOpenRatioByMoney(rs.getDouble(F_OPEN_RATIO_BY_MONEY));
        commissionDelta.setOpenRatioByVolume(rs.getDouble(F_OPEN_RATIO_BY_VOLUME));
        commissionDelta.setCloseRatioByMoney(rs.getDouble(F_CLOSE_RATIO_BY_MONEY));
        commissionDelta.setCloseRatioByVolume(rs.getDouble(F_CLOSE_RATIO_BY_VOLUME));
        commissionDelta.setCloseTodayRatioByMoney(rs.getDouble(F_CLOSE_TODAY_RATIO_BY_MONEY));
        commissionDelta.setCloseTodayRatioByVolume(rs.getDouble(F_CLOSE_TODAY_RATIO_BY_VOLUME));
        commissionDelta.setCurrency(rs.getString(F_CURRENCY));

        xqGeneralCommissionSettings.setCommissionDelta(commissionDelta);
        return xqGeneralCommissionSettings;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
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
                    .append("PRIMARY KEY(").append(F_SUB_ACCOUNT_ID).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
}
