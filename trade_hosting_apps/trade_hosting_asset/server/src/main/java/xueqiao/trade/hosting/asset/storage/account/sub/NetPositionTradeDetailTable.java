package xueqiao.trade.hosting.asset.storage.account.sub;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.asset.storage.account.TradeDetailTableHandler;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 雪橇净持仓成交明细
 * @author walter
 */
public class NetPositionTradeDetailTable extends TableHelper<AssetTradeDetail> implements IDBTable {

    private static final String TABLE_NAME = "t_net_position_trade_detail";

    public NetPositionTradeDetailTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public AssetTradeDetail fromResultSet(ResultSet resultSet) throws Exception {
        AssetTradeDetail detail = TradeDetailTableHandler.getAssetTradeDetailFromResultSet(resultSet);
        return detail;
    }

    public AssetTradeDetailPage queryPositionTradeDetailPage(long subAccountId, long sledContractId, IndexedPageOption pageOption) throws SQLException {
        AssetTradeDetailPage page = new AssetTradeDetailPage();
        SqlQueryBuilder queryBuilder = getSqlQueryBuilder(subAccountId, sledContractId);
        if (pageOption != null) {
            queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(queryBuilder));
            }
        }
        page.setPage(super.getItemList(queryBuilder));
        return page;
    }

    public List<AssetTradeDetail> queryPositionTradeDetail(long subAccountId, long sledContractId) throws SQLException {
        SqlQueryBuilder queryBuilder = getSqlQueryBuilder(subAccountId, sledContractId);
        return super.getItemList(queryBuilder);
    }

    private SqlQueryBuilder getSqlQueryBuilder(long subAccountId, long sledContractId) throws SQLException {
        Preconditions.checkArgument(subAccountId > 0);
        Preconditions.checkArgument(sledContractId > 0);
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FSUB_ACCOUNT_ID + " =?", subAccountId);
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FSLED_CONTRACT_ID + " =?", sledContractId);
        queryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, TradeDetailTableHandler.COLUMN_FEXEC_TRADE_ID);
        return queryBuilder;
    }

    public void batAdd(List<AssetTradeDetail> assetTradeDetails) throws SQLException {
        Preconditions.checkNotNull(assetTradeDetails);
        for (AssetTradeDetail assetTradeDetail : assetTradeDetails) {
            add(assetTradeDetail);
        }
    }

    public void batDelete(long subAccountId, long sledContractId) throws SQLException {
        Preconditions.checkArgument(subAccountId > 0);
        Preconditions.checkArgument(sledContractId > 0);
        super.delete(TradeDetailTableHandler.COLUMN_FSUB_ACCOUNT_ID + " =? and " + TradeDetailTableHandler.COLUMN_FSLED_CONTRACT_ID + " =? ",
                subAccountId, sledContractId);
    }

    public void add(AssetTradeDetail assetTradeDetail) throws SQLException {
        Preconditions.checkNotNull(assetTradeDetail);
        PreparedFields fields = TradeDetailTableHandler. getPreparedFields(assetTradeDetail);
        fields.addLong(TradeDetailTableHandler.COLUMN_FCREATE_TIMESTAMP,  System.currentTimeMillis());
        super.insert(fields);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (4 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fexec_trade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Ftrade_price DOUBLE DEFAULT 0.0,")
                    .append("Ftrade_volume INT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Ftrade_direction TINYINT UNSIGNED NOT NULL DEFAULT 127,")
                    .append("Fsled_commodity_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fexec_trade_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            idbOperator.execute(sqlBuilder.toString());
        }
        if (7 == targetVersion){
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("ALTER TABLE ").append(getTableName())
                    .append(" ADD COLUMN Fsource VARCHAR(128) NOT NULL DEFAULT ''");
            idbOperator.execute(sqlBuilder.toString());
        }
        if (10 == targetVersion) {
            String sql = " ADD COLUMN Ftrade_detail_id BIGINT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator, sql);
            sql=" ADD COLUMN Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator,sql);
            sql =" ADD COLUMN FtradeTimestampMs BIGINT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator,sql);
            sql=" DROP PRIMARY KEY";
            executeAlterSql(idbOperator,sql);
            sql=" ADD PRIMARY KEY(Ftrade_detail_id,Fexec_trade_id)";
            executeAlterSql(idbOperator,sql);
        }
        if (18 == targetVersion){
            String sql = " ADD COLUMN Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator, sql);
        }
        if (19 == targetVersion) {
            String sql = " ADD COLUMN Fsled_order_id VARCHAR(128) NOT NULL DEFAULT ''";
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
