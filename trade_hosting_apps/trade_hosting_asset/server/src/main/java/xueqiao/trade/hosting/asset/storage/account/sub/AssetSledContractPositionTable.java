package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 雪橇子账户当前持仓
 *
 * @author walter
 */
public class AssetSledContractPositionTable extends TableHelper<HostingSledContractPosition> implements IDBTable {

    private static final String TABLE_NAME = "t_sled_contract_position";
    private static final String COLUMN_FSUB_ACCOUNT_ID = "Fsub_account_id";
    private static final String COLUMN_FSLED_CONTRACT_ID = "Fsled_contract_id";
    private static final String COLUMN_FLONG_POSITION = "Flong_position";
    private static final String COLUMN_FSHORT_POSITION = "Fshort_position";
    private static final String COLUMN_FNET_POSITION = "Fnet_position";
    private static final String COLUMN_FPRE_POSITION = "Fpre_position";
    private static final String COLUMN_FCLOSE_PROFIT = "Fclose_profit";
    private static final String COLUMN_FPOSITION_PROFIT = "Fposition_profit";
    private static final String COLUMN_FPOSITION_AVG_PRICE = "Fposition_avg_price";
    private static final String COLUMN_FSLED_COMMODITY_ID = "Fsled_commodity_id";
    private static final String COLUMN_FCALCULATE_PRICE = "Fcalculate_price";
    private static final String COLUMN_FUSE_COMMISSION = "Fuse_commission";
    private static final String COLUMN_FUSE_MARGIN = "Fuse_margin";
    private static final String COLUMN_FCURRENCY = "Fcurrency";

    private static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    private static final String COLUMN_FGOODS_VALUE = "Fgoods_value";
    private static final String COLUMN_FLEVERAGE = "Fleverage";


    public AssetSledContractPositionTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public HostingSledContractPosition fromResultSet(ResultSet resultSet) throws Exception {
        HostingSledContractPosition hostingSledContractPosition = new HostingSledContractPosition();
        hostingSledContractPosition.setSledContractId(resultSet.getLong(COLUMN_FSLED_CONTRACT_ID));
        hostingSledContractPosition.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        hostingSledContractPosition.setSledCommodityId(resultSet.getLong(COLUMN_FSLED_COMMODITY_ID));
        hostingSledContractPosition.setCurrency(resultSet.getString(COLUMN_FCURRENCY));

        HostingPositionVolume positionVolume = new HostingPositionVolume();
        positionVolume.setSledContractId(resultSet.getLong(COLUMN_FSLED_CONTRACT_ID));
        positionVolume.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        positionVolume.setPrevPosition(resultSet.getLong(COLUMN_FPRE_POSITION));
        positionVolume.setLongPosition(resultSet.getLong(COLUMN_FLONG_POSITION));
        positionVolume.setShortPosition(resultSet.getLong(COLUMN_FSHORT_POSITION));
        positionVolume.setNetPosition(resultSet.getLong(COLUMN_FNET_POSITION));
        positionVolume.setUseCommission(resultSet.getDouble(COLUMN_FUSE_COMMISSION));
        positionVolume.setCloseProfit(resultSet.getDouble(COLUMN_FCLOSE_PROFIT));
        positionVolume.setPositionAvgPrice(resultSet.getDouble(COLUMN_FPOSITION_AVG_PRICE));
        positionVolume.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        positionVolume.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));
        hostingSledContractPosition.setPositionVolume(positionVolume);

        HostingPositionFund positionFund = new HostingPositionFund();
        positionFund.setSubAccountId(resultSet.getLong(COLUMN_FSUB_ACCOUNT_ID));
        positionFund.setSledContractId(resultSet.getLong(COLUMN_FSLED_CONTRACT_ID));
        positionFund.setCurrency(resultSet.getString(COLUMN_FCURRENCY));
        positionFund.setPositionProfit(resultSet.getDouble(COLUMN_FPOSITION_PROFIT));
        positionFund.setCalculatePrice(resultSet.getDouble(COLUMN_FCALCULATE_PRICE));
        positionFund.setUseMargin(resultSet.getDouble(COLUMN_FUSE_MARGIN));
        positionFund.setGoodsValue(resultSet.getDouble(COLUMN_FGOODS_VALUE));
        positionFund.setLeverage(resultSet.getDouble(COLUMN_FLEVERAGE));
        hostingSledContractPosition.setPositionFund(positionFund);

        return hostingSledContractPosition;
    }

    public List<HostingSledContractPosition> query(ReqHostingSledContractPositionOption option) throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", option.getSubAccountId());
        if (option.isSetSledContractIds() && option.getSledContractIdsSize() > 0) {
            sqlQueryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSLED_CONTRACT_ID, option.getSledContractIds());
        }
        return super.getItemList(sqlQueryBuilder);
    }

    public HostingSledContractPositionPage query(ReqHostingSledContractPositionOption option, IndexedPageOption pageOption) throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", option.getSubAccountId());
        if (option.isSetSledContractIds() && option.getSledContractIdsSize() > 0) {
            sqlQueryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSLED_CONTRACT_ID, option.getSledContractIds());
        }

        HostingSledContractPositionPage page = new HostingSledContractPositionPage();
        if (pageOption != null) {
            sqlQueryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(sqlQueryBuilder));
            }
        }

        page.setPage(super.getItemList(sqlQueryBuilder));
        return page;
    }

    public void add(HostingSledContractPosition hostingPositionVolume) throws SQLException {
        Preconditions.checkNotNull(hostingPositionVolume);
        Preconditions.checkArgument(hostingPositionVolume.getSubAccountId() > 0);
        Preconditions.checkArgument(hostingPositionVolume.getSledContractId() > 0);
        PreparedFields fields = getPreparedFields(hostingPositionVolume);
        long timestamp = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, timestamp);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, timestamp);
        super.insert(fields);

    }

    private PreparedFields getPreparedFields(HostingSledContractPosition sledContractPosition) {
        PreparedFields fields = new PreparedFields();
        if (sledContractPosition.isSetSubAccountId()) {
            fields.addLong(COLUMN_FSUB_ACCOUNT_ID, sledContractPosition.getSubAccountId());
        }
        if (sledContractPosition.isSetSledContractId()) {
            fields.addLong(COLUMN_FSLED_CONTRACT_ID, sledContractPosition.getSledContractId());
        }
        if (sledContractPosition.isSetSledCommodityId()) {
            fields.addLong(COLUMN_FSLED_COMMODITY_ID, sledContractPosition.getSledCommodityId());
        }
        if (sledContractPosition.isSetCurrency()) {
            fields.addString(COLUMN_FCURRENCY, sledContractPosition.getCurrency());
        }

        if (sledContractPosition.isSetPositionVolume()) {
            HostingPositionVolume positionVolume = sledContractPosition.getPositionVolume();
            if (positionVolume.isSetPrevPosition()) {
                fields.addLong(COLUMN_FPRE_POSITION, positionVolume.getPrevPosition());
            }
            if (positionVolume.isSetPositionAvgPrice()) {
                fields.addDouble(COLUMN_FPOSITION_AVG_PRICE, positionVolume.getPositionAvgPrice());
            }
            if (positionVolume.isSetLongPosition()) {
                fields.addLong(COLUMN_FLONG_POSITION, positionVolume.getLongPosition());
            }
            if (positionVolume.isSetShortPosition()) {
                fields.addLong(COLUMN_FSHORT_POSITION, positionVolume.getShortPosition());
            }
            if (positionVolume.isSetNetPosition()) {
                fields.addLong(COLUMN_FNET_POSITION, positionVolume.getNetPosition());
            }
            if (positionVolume.isSetUseCommission()) {
                fields.addDouble(COLUMN_FUSE_COMMISSION, positionVolume.getUseCommission());
            }
            if (positionVolume.isSetCloseProfit()) {
                fields.addDouble(COLUMN_FCLOSE_PROFIT, positionVolume.getCloseProfit());
            }

        }
        if (sledContractPosition.isSetPositionFund()) {
            HostingPositionFund positionFund = sledContractPosition.getPositionFund();
            if (positionFund.isSetPositionProfit()) {
                fields.addDouble(COLUMN_FPOSITION_PROFIT, positionFund.getPositionProfit());
            }
            if (positionFund.isSetCalculatePrice()) {
                fields.addDouble(COLUMN_FCALCULATE_PRICE, positionFund.getCalculatePrice());
            }
            if (positionFund.isSetUseMargin()) {
                fields.addDouble(COLUMN_FUSE_MARGIN, positionFund.getUseMargin());
            }
            if (positionFund.isSetGoodsValue()) {
                fields.addDouble(COLUMN_FGOODS_VALUE, positionFund.getGoodsValue());
            }
            if (positionFund.isSetLeverage()) {
                fields.addDouble(COLUMN_FLEVERAGE, positionFund.getLeverage());
            }
        }

        return fields;
    }

    public void update(HostingSledContractPosition hostingPositionVolume) throws SQLException {
        Preconditions.checkNotNull(hostingPositionVolume);
        Preconditions.checkArgument(hostingPositionVolume.getSubAccountId() > 0);
        Preconditions.checkArgument(hostingPositionVolume.getSledContractId() > 0);
        PreparedFields fields = getPreparedFields(hostingPositionVolume);
        long timestamp = System.currentTimeMillis();
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, timestamp);
        super.update(fields,
                COLUMN_FSUB_ACCOUNT_ID + " =? and " + COLUMN_FSLED_CONTRACT_ID + " =? ",
                hostingPositionVolume.getSubAccountId(), hostingPositionVolume.getSledContractId());
    }

    public HostingSledContractPosition query(long subAccountId, long sledContractId, boolean forUpdate) throws SQLException {
        Preconditions.checkArgument(subAccountId > 0);
        Preconditions.checkArgument(sledContractId > 0);
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSUB_ACCOUNT_ID + "=?", subAccountId);
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSLED_CONTRACT_ID + "=?", sledContractId);
        return super.getItem(sqlQueryBuilder);
    }

    public HostingSledContractPosition query(long subAccountId, long sledContractId) throws SQLException {
        return query(subAccountId, sledContractId, false);
    }

    public void delete(long subAccountId, long sledContractId) throws SQLException {
        Preconditions.checkArgument(subAccountId > 0);
        Preconditions.checkArgument(sledContractId > 0);
        super.delete(COLUMN_FSUB_ACCOUNT_ID + " =? and " + COLUMN_FSLED_CONTRACT_ID + " =? ",
                subAccountId, sledContractId);
    }

    public List<HostingSledContractPosition> queryAllActivePosition() throws SQLException {
        SqlQueryBuilder sqlQueryBuilder = super.prepareSqlQueryBuilder();
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.OR, COLUMN_FPRE_POSITION + "!=?", 0);
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.OR, COLUMN_FLONG_POSITION + "!=?", 0);
        sqlQueryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.OR, COLUMN_FSHORT_POSITION + "!=?", 0);
        return super.getItemList(sqlQueryBuilder);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {

        if (4 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL,")
                    .append("Flong_position BIGINT NOT NULL DEFAULT 0,")
                    .append("Fshort_position BIGINT NOT NULL DEFAULT 0,")
                    .append("Fnet_position BIGINT NOT NULL DEFAULT 0,")
                    .append("Fpre_position BIGINT NOT NULL DEFAULT 0,")
                    .append("Fclose_profit DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fposition_profit DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fposition_avg_price DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fsled_commodity_id BIGINT NOT NULL DEFAULT 0,")
                    .append("Fcalculate_price DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fuse_commission DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fuse_margin DOUBLE NOT NULL DEFAULT 0.0,")
                    .append("Fcurrency VARCHAR(32) NOT NULL DEFAULT '',")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fsub_account_id,Fsled_contract_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb;");
            idbOperator.execute(createSqlBuilder.toString());
        }

        if (17 == targetVersion) {
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
