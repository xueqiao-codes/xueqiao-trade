package xueqiao.trade.hosting.position.fee.storage.table;

import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class XQGeneralMarginSettingsTable extends TableHelper<XQGeneralMarginSettings> implements IDBTable {

    private final static String TABLE_NAME = "txq_general_margin_settings";
    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_CALCULATE_TYPE = "Fcalculate_type";
    private final static String F_LONG_MARGIN_RATIO_BY_MONEY = "Flong_margin_ratio_by_money";
    private final static String F_LONG_MARGIN_RATIO_BY_VOLUME = "Flong_margin_ratio_by_volume";
    private final static String F_SHORT_MARGIN_RATIO_BY_MONEY = "Fshort_margin_ratio_by_money";
    private final static String F_SHORT_MARGIN_RATIO_BY_VOLUME = "Fshort_margin_ratio_by_volume";
    private final static String F_CURRENCY = "Fcurrency";
    private final static String F_IS_SYNC = "Fis_sync";
    private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";
    private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";

    public XQGeneralMarginSettingsTable(Connection conn) {
        super(conn);
    }

    public void insert(XQGeneralMarginSettings settings) throws SQLException {
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(settings);
        pf.addLong(F_SUB_ACCOUNT_ID, settings.getSubAccountId());
        pf.addLong(F_CREATE_TIMESTAMP_MS, currenMillis);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.insert(pf);
    }

    public void update(XQGeneralMarginSettings settings) throws SQLException {
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

    public XQGeneralMarginSettings query(long subAccountId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", subAccountId);
        return super.getItem(qryBuilder);
    }

    public List<XQGeneralMarginSettings> queryUnsyncList(int pageIndex, int pageSize) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_SYNC + "=?", 0);
        qryBuilder.setPage(pageIndex, pageSize);
        return super.getItemList(qryBuilder);
    }

    private PreparedFields getPreparedFields(XQGeneralMarginSettings settings) {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_SYNC, 0);
        if (settings.isSetType()) {
            pf.addInt(F_CALCULATE_TYPE, settings.getType().getValue());
        }
        if (settings.getMarginDelta() != null) {
            MarginInfo marginDelta = settings.getMarginDelta();
            if (marginDelta.isSetLongMarginRatioByMoney()) {
                pf.addDouble(F_LONG_MARGIN_RATIO_BY_MONEY, marginDelta.getLongMarginRatioByMoney());
            }
            if (marginDelta.isSetLongMarginRatioByVolume()) {
                pf.addDouble(F_LONG_MARGIN_RATIO_BY_VOLUME, marginDelta.getLongMarginRatioByVolume());
            }
            if (marginDelta.isSetShortMarginRatioByMoney()) {
                pf.addDouble(F_SHORT_MARGIN_RATIO_BY_MONEY, marginDelta.getShortMarginRatioByMoney());
            }
            if (marginDelta.isSetShortMarginRatioByVolume()) {
                pf.addDouble(F_SHORT_MARGIN_RATIO_BY_VOLUME, marginDelta.getShortMarginRatioByVolume());
            }
            if (marginDelta.isSetCurrency()) {
                pf.addString(F_CURRENCY, marginDelta.getCurrency());
            }
        }
        return pf;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public XQGeneralMarginSettings fromResultSet(ResultSet rs) throws Exception {
        XQGeneralMarginSettings xqGeneralMarginSettings = new XQGeneralMarginSettings();
        xqGeneralMarginSettings.setSubAccountId(rs.getLong(F_SUB_ACCOUNT_ID));
        xqGeneralMarginSettings.setType(FeeCalculateType.findByValue(rs.getInt(F_CALCULATE_TYPE)));
        xqGeneralMarginSettings.setCreateTimestampMs(rs.getLong(F_CREATE_TIMESTAMP_MS));
        xqGeneralMarginSettings.setLastmodifyTimestampMs(rs.getLong(F_LASTMODITY_TIMESTAMP_MS));
        xqGeneralMarginSettings.setIsSync(rs.getBoolean(F_IS_SYNC));

        MarginInfo marginDelta = new MarginInfo();
        marginDelta.setLongMarginRatioByMoney(rs.getDouble(F_LONG_MARGIN_RATIO_BY_MONEY));
        marginDelta.setLongMarginRatioByVolume(rs.getDouble(F_LONG_MARGIN_RATIO_BY_VOLUME));
        marginDelta.setShortMarginRatioByMoney(rs.getDouble(F_SHORT_MARGIN_RATIO_BY_MONEY));
        marginDelta.setShortMarginRatioByVolume(rs.getDouble(F_SHORT_MARGIN_RATIO_BY_VOLUME));
        marginDelta.setCurrency(rs.getString(F_CURRENCY));

        xqGeneralMarginSettings.setMarginDelta(marginDelta);
        return xqGeneralMarginSettings;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_CALCULATE_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_LONG_MARGIN_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_LONG_MARGIN_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_SHORT_MARGIN_RATIO_BY_MONEY).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_SHORT_MARGIN_RATIO_BY_VOLUME).append(" DOUBLE DEFAULT 0.00,")
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
