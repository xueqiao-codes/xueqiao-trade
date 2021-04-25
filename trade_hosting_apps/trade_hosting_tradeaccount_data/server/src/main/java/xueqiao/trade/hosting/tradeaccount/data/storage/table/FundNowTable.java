package xueqiao.trade.hosting.tradeaccount.data.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.thrift.TException;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FundNowTable extends TableHelper<List<TradeAccountFund>> implements IDBTable {

    public FundNowTable(Connection conn) {
        super(conn);
    }

    public List<TradeAccountFund> getFunds(long tradeAccountId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Ftrade_account_id=?", tradeAccountId);
        return super.getItem(qryBuilder);
    }

    public int updateFunds(long tradeAccountId, List<TradeAccountFund> funds) throws SQLException {
        Preconditions.checkArgument(tradeAccountId > 0);
        Preconditions.checkNotNull(funds);

        String fundsContent = null;
        try {
            fundsContent = ThriftExtJsonUtils.listToJsonTBase(funds);
        } catch (TException e) {
            throw new SQLException(e);
        }

        StringBuilder sqlBuilder = new StringBuilder(128);
        sqlBuilder.append("REPLACE INTO ").append(getTableName()) .append(" VALUES(?,?,?)");

        return new QueryRunner().update(getConnection(), sqlBuilder.toString()
                , tradeAccountId, fundsContent, System.currentTimeMillis());
    }

    @Override
    protected String getTableName() throws SQLException {
        return "tfund_now";
    }

    @Override
    public List<TradeAccountFund> fromResultSet(ResultSet rs) throws Exception {
        return ThriftExtJsonUtils.listFromJsonTBase(rs.getString("Ffunds"), TradeAccountFund.class);
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Ffunds TEXT,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Ftrade_account_id)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }
}
