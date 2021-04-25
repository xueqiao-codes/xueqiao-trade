package xueqiao.trade.hosting.tradeaccount.data.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SettlementInfoTable extends TableHelper<TradeAccountSettlementInfo> implements IDBTable {

    public SettlementInfoTable(Connection conn) {
        super(conn);
    }

    public Date getNearsetSettlementDate(long tradeAccountId) throws SQLException {
        SqlQueryBuilder qryBuilder = new SqlQueryBuilder();
        qryBuilder.addFields("Ftrade_account_id","Fsettlement_date");
        qryBuilder.addTables(getTableName());
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Ftrade_account_id=?", tradeAccountId);

        qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, "Fsettlement_date");
        qryBuilder.setPage(0, 1);

        return new QueryRunner().query(getConnection(), qryBuilder.getItemsSql(), new ResultSetHandler<Date>() {
            @Override
            public Date handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return rs.getDate("Fsettlement_date");
                }
                return null;
            }
        }, qryBuilder.getParameterList());
    }

    public boolean isSettlementInfoExist(long tradeAccountId, Date settlementDate) throws SQLException {
        SqlQueryBuilder qryBuilder = new SqlQueryBuilder();
        qryBuilder.addFields("Ftrade_account_id","Fsettlement_date");
        qryBuilder.addTables(getTableName());
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Ftrade_account_id=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fsettlement_date=?", settlementDate);

        return new QueryRunner().query(getConnection(), qryBuilder.getItemsSql(), new ResultSetHandler<Boolean>() {
            @Override
            public Boolean handle(ResultSet rs) throws SQLException {
                if (rs.next()) {
                    return true;
                }
                return false;
            }
        }, qryBuilder.getParameterList());
    }

    public List<TradeAccountSettlementInfo> getSettlementInfos(
            long tradeAccountId, Date qryDateBegin, Date qryDateEnd) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Ftrade_account_id=?", tradeAccountId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fsettlement_date >= ?", qryDateBegin);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                , "Fsettlement_date <= ?", qryDateEnd);
        qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Fsettlement_date");

        return super.getItemList(qryBuilder);
    }

    public int insertSettlementInfo(TradeAccountSettlementInfo settlementInfo) throws SQLException {
        Preconditions.checkNotNull(settlementInfo);
        Preconditions.checkArgument(settlementInfo.getTradeAccountId() > 0);
        Preconditions.checkArgument(StringUtils.isNotEmpty(settlementInfo.getSettlementDate()));

        PreparedFields pf = new PreparedFields();
        pf.addLong("Ftrade_account_id", settlementInfo.getTradeAccountId());
        pf.addDate("Fsettlement_date", Date.valueOf(settlementInfo.getSettlementDate()));
        pf.addString("Fsettlement_content", settlementInfo.getSettlementContent());
        pf.addLong("Fcreate_timestampms", System.currentTimeMillis());

        return super.insert(pf);
    }

    @Override
    protected String getTableName() throws SQLException {
        return "tsettlement_info";
    }

    @Override
    public TradeAccountSettlementInfo fromResultSet(ResultSet rs) throws Exception {
        TradeAccountSettlementInfo settlementInfo = new TradeAccountSettlementInfo();
        settlementInfo.setTradeAccountId(rs.getLong("Ftrade_account_id"));
        settlementInfo.setSettlementDate(rs.getDate("Fsettlement_date").toString());
        settlementInfo.setSettlementContent(rs.getString("Fsettlement_content"));
        settlementInfo.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        return settlementInfo;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsettlement_date date,")
                            .append("Fsettlement_content MEDIUMTEXT,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Ftrade_account_id, Fsettlement_date)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
    }
}
