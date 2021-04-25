package xueqiao.trade.hosting.asset.storage.account.trade;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.asset.storage.account.TradeDetailTableHandler;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;
import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetailPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionTradeDetailOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 雪橇成交的交易明细历史
 *
 * @author walter
 */
public class TradeDetailHistoryTable extends TableHelper<AssetTradeDetail> implements IDBTable {

    private static final String TABLE_NAME = "t_trade_detail_history";

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    public TradeDetailHistoryTable(Connection conn) {
        super(conn);
    }

    @Override
    public AssetTradeDetail fromResultSet(ResultSet resultSet) throws Exception {
        return TradeDetailTableHandler.getAssetTradeDetailFromResultSet(resultSet);
    }

    public void add(AssetTradeDetail assetTradeDetail) throws SQLException {
        Preconditions.checkNotNull(assetTradeDetail);
        PreparedFields fields = TradeDetailTableHandler.getPreparedFields(assetTradeDetail);
        fields.addLong(TradeDetailTableHandler.COLUMN_FCREATE_TIMESTAMP, System.currentTimeMillis());
        super.insert(fields);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator idbOperator, int targetVersion) throws SQLException {
        if (11 == targetVersion) {
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
                    .append("Fsource VARCHAR(128) NOT NULL DEFAULT '',")
                    .append("Ftrade_detail_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("FtradeTimestampMs BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Ftrade_detail_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            idbOperator.execute(sqlBuilder.toString());
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

    public AssetTradeDetailPage query(ReqTradeAccountPositionTradeDetailOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);
        Preconditions.checkArgument(option.isSetTradeAccountId());
        Preconditions.checkArgument(option.getTradeAccountId() > 0);
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        if (option.isSetTradeAccountId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetStartTradeTimestampMs()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FTRADE_TIMESTAMPMS + ">=?", option.getStartTradeTimestampMs());
        }
        if (option.isSetEndTradeTimestampMs()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FTRADE_TIMESTAMPMS + "<=?", option.getEndTradeTimestampMs());
        }
        if (option.isSetSledContractId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FSLED_CONTRACT_ID + "=?", option.getSledContractId());
        }

        AssetTradeDetailPage page = new AssetTradeDetailPage();

        if (pageOption != null) {
            if (pageOption.isSetPageIndex()) {
                builder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            }
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(builder));
            }
        }

        page.setPage(super.getItemList(builder));
        return page;
    }

    public AssetTradeDetail queryForUpdate(long execTradeId, boolean isForUpdate) throws SQLException {
        Preconditions.checkArgument(execTradeId > 0);
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, TradeDetailTableHandler.COLUMN_FEXEC_TRADE_ID + "=?", execTradeId);
        return super.getItem(builder, isForUpdate);
    }
}
