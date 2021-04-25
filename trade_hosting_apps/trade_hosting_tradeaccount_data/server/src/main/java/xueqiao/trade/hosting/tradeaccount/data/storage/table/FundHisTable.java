package xueqiao.trade.hosting.tradeaccount.data.storage.table;

import com.google.common.base.Preconditions;
import org.apache.thrift.TException;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FundHisTable extends TableHelper<TradeAccountFundHisItem> implements IDBTable {

    public FundHisTable(Connection conn) {
        super(conn);
    }

    public TradeAccountFundHisItem getFundHisItem(long tradeAccountId, Date qryDate) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Ftrade_account_id=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,"Fdate=?", qryDate);

        return super.getItem(qryBuilder);
    }

    public List<TradeAccountFundHisItem> getFundHisItems(long tradeAccountId, Date qryDateBegin, Date qryDateEnd)
            throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Ftrade_account_id=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fdate >= ?", qryDateBegin);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                ,"Fdate <= ?", qryDateEnd);
        qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Fdate");

        return super.getItemList(qryBuilder);
    }

    public int insertFundHisItem(TradeAccountFundHisItem fundHisItem) throws SQLException {
        Preconditions.checkNotNull(fundHisItem);
        Preconditions.checkArgument(fundHisItem.getTradeAccountId() > 0);
        Preconditions.checkArgument(fundHisItem.getFundsSize() > 0);

        PreparedFields pf = new PreparedFields();
        pf.addLong("Ftrade_account_id", fundHisItem.getTradeAccountId());
        pf.addDate("Fdate", Date.valueOf(fundHisItem.getDate()));
        try {
            pf.addString("Ffunds", ThriftExtJsonUtils.listToJsonTBase(fundHisItem.getFunds()));
        } catch (TException e) {
            throw new SQLException(e);
        }
        pf.addLong("Fcreate_timestampms", System.currentTimeMillis());

        return super.insert(pf);
    }

    @Override
    protected String getTableName() throws SQLException {
        return "tfund_his";
    }

    @Override
    public TradeAccountFundHisItem fromResultSet(ResultSet rs) throws Exception {
        TradeAccountFundHisItem resultItem = new TradeAccountFundHisItem();
        resultItem.setTradeAccountId(rs.getLong("Ftrade_account_id"));
        resultItem.setDate(rs.getDate("Fdate").toString());
        resultItem.setFunds(ThriftExtJsonUtils.listFromJsonTBase(
                rs.getString("Ffunds"), TradeAccountFund.class));
        resultItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        return resultItem;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fdate DATE,")
                            .append("Ffunds TEXT,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Ftrade_account_id, Fdate)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }
}
