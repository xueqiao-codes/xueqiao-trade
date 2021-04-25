package xueqiao.trade.hosting.tradeaccount.data.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.thrift.TException;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NetPositionSummaryTable extends TableHelper<List<TradeAccountNetPositionSummary>> implements IDBTable {

    public NetPositionSummaryTable(Connection conn) {
        super(conn);
    }

    public List<TradeAccountNetPositionSummary> getNetPositionSummaries(long tradeAccountId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, "Ftrade_account_id=?", tradeAccountId);
        List<TradeAccountNetPositionSummary> resultList =  super.getItem(qryBuilder);
        if (resultList == null) {
            return new ArrayList<>();
        }
        return resultList;
    }

    public int updateNetPositionSummary(long tradeAccountId
            , List<TradeAccountNetPositionSummary> netPositionSummaries) throws SQLException {
        Preconditions.checkArgument(tradeAccountId > 0);
        Preconditions.checkNotNull(netPositionSummaries);

        String summaryContent = null;
        try {
            summaryContent = ThriftExtJsonUtils.listToJsonTBase(netPositionSummaries);
        } catch (TException e) {
            throw new SQLException(e);
        }

        StringBuilder sqlBuilder = new StringBuilder(128);
        sqlBuilder.append("REPLACE INTO ").append(getTableName()).append(" VALUES(?,?,?)");

        return new QueryRunner().update(getConnection(), sqlBuilder.toString()
                    , tradeAccountId, summaryContent, System.currentTimeMillis());
    }

    @Override
    protected String getTableName() throws SQLException {
        return "tnet_position";
    }

    @Override
    public List<TradeAccountNetPositionSummary> fromResultSet(ResultSet rs) throws Exception {
        return ThriftExtJsonUtils.listFromJsonTBase(rs.getString("Fsummary"), TradeAccountNetPositionSummary.class);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsummary MEDIUMTEXT,")
                    .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Ftrade_account_id)")
                    .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }
}
