package xueqiao.trade.hosting.asset.storage.account;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 资源结算层面的成交明细（从成交列表中的成交明细转换成适合计算用的成交明细）
 * 落地从雪橇成交和持仓分配过来的持仓明细
 * 程序中断或重启后，会从表中读取当前成交明细继续计算实时持仓和资金数据
 *
 * @author walter
 */
public class AssetTradeDetailTable extends TableHelper<AssetTradeDetail> implements IDBTable {

    private static final String TABLE_NAME = "t_asset_trade_detail";


    public AssetTradeDetailTable(Connection conn) {
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

    public List<AssetTradeDetail> queryAll() throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, TradeDetailTableHandler.COLUMN_FEXEC_TRADE_ID);
        return super.getItemList(queryBuilder);
    }

    public List<AssetTradeDetail> queryAssetTradeDetail(long subAccountId) throws SQLException {
        Preconditions.checkArgument(subAccountId > 0);
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FSUB_ACCOUNT_ID + " =?", subAccountId);
        queryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, TradeDetailTableHandler.COLUMN_FEXEC_TRADE_ID);
        return super.getItemList(queryBuilder);
    }

    public void add(AssetTradeDetail assetTradeDetail) throws SQLException {
        Preconditions.checkNotNull(assetTradeDetail);
        PreparedFields fields = TradeDetailTableHandler.getPreparedFields(assetTradeDetail);
        fields.addLong(TradeDetailTableHandler.COLUMN_FCREATE_TIMESTAMP, System.currentTimeMillis());
        super.insert(fields);
    }

    public void delete(long execTradeId) throws SQLException {
        Preconditions.checkArgument(execTradeId > 0);
        delete(0, execTradeId);
    }

    public void delete(long tradeDetailId, long execTradeId) throws SQLException {
        super.delete(TradeDetailTableHandler.COLUMN_FEXEC_TRADE_ID + "=? AND " + TradeDetailTableHandler.COLUMN_FTRADE_DETAIL_ID + " =?", execTradeId, tradeDetailId);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (2 == targetVersion) {
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
        if (7 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("ALTER TABLE ").append(getTableName())
                    .append(" ADD COLUMN Fsource VARCHAR(128) NOT NULL DEFAULT ''");
            idbOperator.execute(sqlBuilder.toString());
        }
        if (10 == targetVersion) {
            String sql = " ADD COLUMN Ftrade_detail_id BIGINT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator, sql);
            sql = " ADD COLUMN Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator, sql);
            sql = " ADD COLUMN FtradeTimestampMs BIGINT UNSIGNED NOT NULL DEFAULT 0";
            executeAlterSql(idbOperator, sql);
            sql = " DROP PRIMARY KEY";
            executeAlterSql(idbOperator, sql);
            sql = " ADD PRIMARY KEY(Ftrade_detail_id,Fexec_trade_id)";
            executeAlterSql(idbOperator, sql);
        }
        if (18 == targetVersion) {
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
