package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.id_maker.IdException;
import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.config.Config;
import xueqiao.trade.hosting.asset.thriftapi.ReqSettlementPositionDetailOption;
import xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetail;
import xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetailPage;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 雪橇子账户持仓结算明细历史
 *
 * @author walter
 */
public class SettlementPositionDetailTable extends TableHelper<SettlementPositionDetail> implements IDBTable {

    private static final String TABLE_NAME = "t_settlement_position_detail";
    private static final String COLUMN_FSETTLEMENT_ID = "Fsettlement_id";
    private static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    private static final String COLUMN_FSLED_CONTRACT_ID = "Fsled_contract_id";
    private static final String COLUMN_FPOSITION = "Fposition";
    private static final String COLUMN_FPOSITION_AVE_PRICE = "Fposition_ave_price";
    private static final String COLUMN_FPOSITION_PROFIT = "Fposition_profit";
    private static final String COLUMN_FCALCULATE_PRICE = "Fcalculate_price";

    private static final String COLUMN_FCURRENCY = "Fcurrency";
    private static final String COLUMN_FSLED_COMMODITY_ID = "Fsled_commodity_id";

    private static final String COLUMN_FPRE_POSITION = "Fpre_position";
    private static final String COLUMN_FLONG_POSITION = "Flong_position";
    private static final String COLUMN_FSHORT_POSITION = "Fshort_position";
    private static final String COLUMN_FCLOSE_PROFIT = "Fclose_profit";

    private static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    private static final String COLUMN_FGOODS_VALUE = "Fgoods_value";
    private static final String COLUMN_FLEVERAGE = "Fleverage";

    private static final String COLUMN_FUSE_MARGIN = "Fuse_margin";
    private static final String COLUMN_FUSE_COMMISSION = "Fuse_commission";


    public SettlementPositionDetailTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public SettlementPositionDetail fromResultSet(ResultSet resultSet) throws Exception {
        SettlementPositionDetail positionDetail = new SettlementPositionDetail();
        positionDetail.setSettlementId(resultSet.getLong(COLUMN_FSETTLEMENT_ID));
        positionDetail.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        positionDetail.setSledContractId(resultSet.getLong(COLUMN_FSLED_CONTRACT_ID));
        positionDetail.setPosition(resultSet.getLong(COLUMN_FPOSITION));
        positionDetail.setPositionAvgPrice(resultSet.getDouble(COLUMN_FPOSITION_AVE_PRICE));
        positionDetail.setPositionProfit(resultSet.getDouble(COLUMN_FPOSITION_PROFIT));
        positionDetail.setCalculatePrice(resultSet.getDouble(COLUMN_FCALCULATE_PRICE));

        positionDetail.setCurrency(resultSet.getString(COLUMN_FCURRENCY));
        positionDetail.setSledCommodityId(resultSet.getLong(COLUMN_FSLED_COMMODITY_ID));

        positionDetail.setPrevPosition(resultSet.getLong(COLUMN_FPRE_POSITION));
        positionDetail.setLongPosition(resultSet.getLong(COLUMN_FLONG_POSITION));
        positionDetail.setShortPosition(resultSet.getLong(COLUMN_FSHORT_POSITION));
        positionDetail.setCloseProfit(resultSet.getDouble(COLUMN_FCLOSE_PROFIT));

        positionDetail.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        positionDetail.setLastmodifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));

        positionDetail.setGoodsValue(resultSet.getDouble(COLUMN_FGOODS_VALUE));
        positionDetail.setLeverage(resultSet.getDouble(COLUMN_FLEVERAGE));

        positionDetail.setUseMargin(resultSet.getDouble(COLUMN_FUSE_MARGIN));
        positionDetail.setUseCommission(resultSet.getDouble(COLUMN_FUSE_COMMISSION));
        return positionDetail;
    }


    private void add(SettlementPositionDetail settlementPositionDetail, long settlementId) throws IdException, SQLException {
        Preconditions.checkNotNull(settlementPositionDetail);
        Preconditions.checkArgument(settlementPositionDetail.getSubAccountId() > 0);
        Preconditions.checkArgument(settlementPositionDetail.getSledContractId() > 0);
        PreparedFields fields = getPreparedFields(settlementPositionDetail, settlementId);
        super.insert(fields);
    }

    public SettlementPositionDetail queryLatest(long subAccountId, long sledContractId) throws ErrorInfo, SQLException {

        ReqSettlementPositionDetailOption option = new ReqSettlementPositionDetailOption();
        option.setSubAccountId(subAccountId);
        option.setSledContractId(sledContractId);
        PageOption pageOption = new PageOption();
        pageOption.setPageIndex(0);
        pageOption.setPageSize(1);
        SqlQueryBuilder sqlQueryBuilder = getSqlQueryBuilder(option, pageOption);
        return super.getItem(sqlQueryBuilder);
    }

    public static long getSettlementId() throws IdException {
        IdMaker settlementIdMaker = Config.getInstance().getSettlementIdMaker();
        return settlementIdMaker.createId();
    }

    public long batAdd(List<SettlementPositionDetail> settlementPositionDetails) throws IdException, SQLException {
        long settlementId = getSettlementId();
        for (SettlementPositionDetail settlementPositionDetail : settlementPositionDetails) {
            add(settlementPositionDetail, settlementId);
        }
        return settlementId;
    }

    public long add(SettlementPositionDetail settlementPositionDetail) throws IdException, SQLException {
        long settlementId = getSettlementId();
        add(settlementPositionDetail, settlementId);
        return settlementId;
    }

    private PreparedFields getPreparedFields(SettlementPositionDetail settlementPositionDetail, long settlementId) {
        PreparedFields fields = new PreparedFields();
        fields.addLong(COLUMN_FSETTLEMENT_ID, settlementId);
        if (settlementPositionDetail.isSetSubAccountId()) {
            fields.addLong(COLUMN_FSUB_ACCOUNT_ID, settlementPositionDetail.getSubAccountId());
        }
        if (settlementPositionDetail.isSetSledContractId()) {
            fields.addLong(COLUMN_FSLED_CONTRACT_ID, settlementPositionDetail.getSledContractId());
        }
        if (settlementPositionDetail.isSetPosition()) {
            fields.addLong(COLUMN_FPOSITION, settlementPositionDetail.getPosition());
        }
        if (settlementPositionDetail.isSetPositionAvgPrice()) {
            fields.addDouble(COLUMN_FPOSITION_AVE_PRICE, settlementPositionDetail.getPositionAvgPrice());
        }
        if (settlementPositionDetail.isSetPositionProfit()) {
            fields.addDouble(COLUMN_FPOSITION_PROFIT, settlementPositionDetail.getPositionProfit());
        }
        if (settlementPositionDetail.isSetCalculatePrice()) {
            fields.addDouble(COLUMN_FCALCULATE_PRICE, settlementPositionDetail.getCalculatePrice());
        }

        if (settlementPositionDetail.isSetCurrency()) {
            fields.addString(COLUMN_FCURRENCY, settlementPositionDetail.getCurrency());
        }

        if (settlementPositionDetail.isSetSledCommodityId()) {
            fields.addLong(COLUMN_FSLED_COMMODITY_ID, settlementPositionDetail.getSledCommodityId());
        }

        if (settlementPositionDetail.isSetPrevPosition()) {
            fields.addLong(COLUMN_FPRE_POSITION, settlementPositionDetail.getPrevPosition());
        }
        if (settlementPositionDetail.isSetLongPosition()) {
            fields.addLong(COLUMN_FLONG_POSITION, settlementPositionDetail.getLongPosition());
        }
        if (settlementPositionDetail.isSetShortPosition()) {
            fields.addLong(COLUMN_FSHORT_POSITION, settlementPositionDetail.getShortPosition());
        }
        if (settlementPositionDetail.isSetCloseProfit()) {
            fields.addDouble(COLUMN_FCLOSE_PROFIT, settlementPositionDetail.getCloseProfit());
        }
        if (settlementPositionDetail.isSetGoodsValue()) {
            fields.addDouble(COLUMN_FGOODS_VALUE, settlementPositionDetail.getGoodsValue());
        }
        if (settlementPositionDetail.isSetLeverage()) {
            fields.addDouble(COLUMN_FLEVERAGE, settlementPositionDetail.getLeverage());
        }
        if (settlementPositionDetail.isSetUseMargin()) {
            fields.addDouble(COLUMN_FUSE_MARGIN, settlementPositionDetail.getUseMargin());
        }
        if (settlementPositionDetail.isSetUseCommission()) {
            fields.addDouble(COLUMN_FUSE_COMMISSION, settlementPositionDetail.getUseCommission());
        }

        long timestamp = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, timestamp);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, timestamp);
        return fields;
    }

    public SettlementPositionDetailPage query(ReqSettlementPositionDetailOption option, PageOption pageOption) throws SQLException, ErrorInfo {
        pageOption.checkValid();
        SqlQueryBuilder sqlQueryBuilder = getSqlQueryBuilder(option, pageOption);
        SettlementPositionDetailPage result = new SettlementPositionDetailPage();
        if (pageOption.isNeedTotalCount()) {
            result.setTotal(super.getTotalNum(sqlQueryBuilder));
        }

        result.setPage(super.getItemList(sqlQueryBuilder));
        return result;
    }

    private SqlQueryBuilder getSqlQueryBuilder(ReqSettlementPositionDetailOption option, PageOption pageOption) throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        if (option.isSetStartTimestampMs()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCREATE_TIMESTAMP + ">?", option.getStartTimestampMs());
        }
        if (option.isSetEndTimestampMs()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FCREATE_TIMESTAMP + "<?", option.getEndTimestampMs());
        }
        if (option.isSetSledContractId()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSLED_CONTRACT_ID + "=?", option.getSledContractId());
        }
        if (option.isSetSubAccountId()) {
            sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", option.getSubAccountId());
        }
        sqlQueryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        sqlQueryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, COLUMN_FCREATE_TIMESTAMP);
        return sqlQueryBuilder;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (15 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fsettlement_id BIGINT UNSIGNED NOT NULL,")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")

                    .append("Fposition BIGINT NOT NULL DEFAULT 0,")
                    .append("Fposition_ave_price DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fposition_profit DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fcalculate_price DOUBLE NOT NULL DEFAULT 0.0,")

                    .append("Fcurrency VARCHAR(32) NOT NULL DEFAULT '',")
                    .append("Fsled_commodity_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")

                    .append("Fpre_position BIGINT NOT NULL DEFAULT 0,")
                    .append("Flong_position BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fshort_position BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fclose_profit DOUBLE NOT NULL DEFAULT 0.0,")

                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fsettlement_id,Fsub_account_id,Fsled_contract_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            idbOperator.execute(sqlBuilder.toString());
        }

        if (17 == targetVersion) {
            String sql = " ADD COLUMN Fgoods_value DOUBLE DEFAULT 0.0";
            executeAlterSql(idbOperator, sql);
            sql = " ADD COLUMN Fleverage  DOUBLE DEFAULT 0.0";
            executeAlterSql(idbOperator, sql);
        }

        if (20 == targetVersion) {
            String sql = " ADD COLUMN Fuse_margin DOUBLE DEFAULT 0.0";
            executeAlterSql(idbOperator, sql);
            sql = " ADD COLUMN Fuse_commission  DOUBLE DEFAULT 0.0";
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
