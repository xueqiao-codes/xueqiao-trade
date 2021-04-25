package xueqiao.trade.hosting.position.fee.storage.table;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRateDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UpsideOriginalPositionRateTable extends TableHelper<UpsidePositionRate> implements IDBTable {

    private final static String EMPTY_CONTRACT_CODE = "##";

    private final static String TABLE_NAME = "tupside_original_position_rate";

    private final static String F_TRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String F_COMMODITY_ID = "Fcommodity_id";
    private final static String F_CONTRACT_CODE = "Fcontract_code";
    private final static String F_COMMODITY_TYPE = "Fcommodity_type";
    private final static String F_COMMODITY_CODE = "Fcommodity_code";
    private final static String F_EXCHANGE_MIC = "Fexchane_mic";
    private final static String F_TECH_PLATFORM = "Ftech_platform";
    private final static String F_DETAIL = "Fdetail";
    private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";
    private final static String F_IS_DIRTY = "Fis_dirty";
    private final static String F_IS_VALID = "Fis_valid";

    public UpsideOriginalPositionRateTable(Connection conn) {
        super(conn);
    }

    private UpsidePositionRate replaceEmptyContractCode(UpsidePositionRate positionRate) {
        if (StringUtils.isBlank(positionRate.getSledContractCode())) {
            positionRate.setSledContractCode(EMPTY_CONTRACT_CODE);
        }
        return positionRate;
    }

    public void insert(UpsidePositionRate positionRate) throws SQLException {
        positionRate = replaceEmptyContractCode(positionRate);

        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(positionRate);
        pf.addLong(F_TRADE_ACCOUNT_ID, positionRate.getTradeAccountId());
        pf.addLong(F_COMMODITY_ID, positionRate.getSledCommodityId());
        pf.addString(F_CONTRACT_CODE, positionRate.getSledContractCode());
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.insert(pf);
    }

    public void update(UpsidePositionRate positionRate) throws SQLException {
        positionRate = replaceEmptyContractCode(positionRate);

        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = getPreparedFields(positionRate);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.update(pf, F_TRADE_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + "=?", positionRate.getTradeAccountId(), positionRate.getSledCommodityId(), positionRate.getSledContractCode());
    }

    public void setInvalidByTradeAccountId(long tradeAccountId) throws SQLException {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_VALID, 0);
        super.update(pf, F_TRADE_ACCOUNT_ID + "=?", tradeAccountId);
    }

    public void resetDirtyStatus(long tradeAccountId, long commodityId, String contractCode) throws SQLException {
        if (StringUtils.isBlank(contractCode)) {
            contractCode = EMPTY_CONTRACT_CODE;
        }
        long currenMillis = System.currentTimeMillis();
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_IS_DIRTY, 0);
        pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currenMillis);
        super.update(pf, F_TRADE_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + "=?", tradeAccountId, commodityId, contractCode);
    }

    public UpsidePositionRate query(long tradeAccountId, long commodityId, String contractCode) throws SQLException {
        if (StringUtils.isBlank(contractCode)) {
            contractCode = EMPTY_CONTRACT_CODE;
        }
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TRADE_ACCOUNT_ID + "=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_CONTRACT_CODE + "=?", contractCode);
        return super.getItem(qryBuilder);
    }

    public List<UpsidePositionRate> query(long tradeAccountId, long commodityId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TRADE_ACCOUNT_ID + "=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_COMMODITY_ID + "=?", commodityId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_VALID + "=?", 1);
        return super.getItemList(qryBuilder);
    }

    public List<UpsidePositionRate> queryDirtyDatas() throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_DIRTY + "=?", 1);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_IS_VALID + "=?", 1);
        return super.getItemList(qryBuilder);
    }

    public void delete(long tradeAccountId, long commodityId, String contractCode) throws SQLException {
        if (StringUtils.isBlank(contractCode)) {
            contractCode = EMPTY_CONTRACT_CODE;
        }
        super.delete(F_TRADE_ACCOUNT_ID + "=? AND " + F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + "=?", tradeAccountId, commodityId, contractCode);
    }

    public void delete(long commodityId, String contractCode) throws SQLException {
        super.delete(F_COMMODITY_ID + " =? AND " + F_CONTRACT_CODE + "=?", commodityId, contractCode);
    }

    private PreparedFields getPreparedFields(UpsidePositionRate positionRate) {
        PreparedFields pf = new PreparedFields();
        pf.addInt(F_COMMODITY_TYPE, positionRate.getSledCommodityType());
        if (StringUtils.isNotBlank(positionRate.getSledCommodityCode())) {
            pf.addString(F_COMMODITY_CODE, positionRate.getSledCommodityCode());
        }
        if (StringUtils.isNotBlank(positionRate.getSledExchangeMic())) {
            pf.addString(F_EXCHANGE_MIC, positionRate.getSledExchangeMic());
        }
        if (positionRate.getTechPlatform() != null) {
            pf.addInt(F_TECH_PLATFORM, positionRate.getTechPlatform().getValue());
        }
        if (positionRate.getDetail() != null) {
            pf.addString(F_DETAIL, new Gson().toJson(positionRate.getDetail()));
        }
        pf.addInt(F_IS_DIRTY, 1);
        pf.addInt(F_IS_VALID, 1);
        return pf;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public UpsidePositionRate fromResultSet(ResultSet rs) throws Exception {
        UpsidePositionRate upsidePositionRate = new UpsidePositionRate();
        upsidePositionRate.setTradeAccountId(rs.getLong(F_TRADE_ACCOUNT_ID));
        upsidePositionRate.setSledCommodityId(rs.getLong(F_COMMODITY_ID));
        String contractCode = rs.getString(F_CONTRACT_CODE);
        if (EMPTY_CONTRACT_CODE.equals(contractCode)) {
            upsidePositionRate.setSledContractCode("");
        } else {
            upsidePositionRate.setSledContractCode(contractCode);
        }
        upsidePositionRate.setSledCommodityType(rs.getInt(F_COMMODITY_TYPE));
        upsidePositionRate.setSledCommodityCode(rs.getString(F_COMMODITY_CODE));
        upsidePositionRate.setSledExchangeMic(rs.getString(F_EXCHANGE_MIC));
        upsidePositionRate.setTechPlatform(BrokerTechPlatform.findByValue(rs.getInt(F_TECH_PLATFORM)));
        upsidePositionRate.setLastmodifyTimestampMs(rs.getLong(F_LASTMODITY_TIMESTAMP_MS));
        upsidePositionRate.setDirty(rs.getBoolean(F_IS_DIRTY));
        upsidePositionRate.setValid(rs.getBoolean(F_IS_VALID));
        String detail = rs.getString(F_DETAIL);
        if (StringUtils.isNotBlank(detail)) {
            upsidePositionRate.setDetail(new Gson().fromJson(detail, UpsidePositionRateDetail.class));
        }
        return upsidePositionRate;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(512);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_TRADE_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_COMMODITY_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_CONTRACT_CODE).append(" VARCHAR(64) NOT NULL DEFAULT '',")
                    .append(F_COMMODITY_TYPE).append(" INT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_COMMODITY_CODE).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_EXCHANGE_MIC).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_TECH_PLATFORM).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_DETAIL).append(" VARCHAR(512) NOT NULL DEFAULT '',")
                    .append(F_LASTMODITY_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_IS_DIRTY).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_IS_VALID).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_TRADE_ACCOUNT_ID).append(",").append(F_COMMODITY_ID).append(",").append(F_CONTRACT_CODE).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
}
