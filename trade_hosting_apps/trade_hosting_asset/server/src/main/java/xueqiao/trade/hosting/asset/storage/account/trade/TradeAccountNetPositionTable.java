package xueqiao.trade.hosting.asset.storage.account.trade;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.asset.thriftapi.ReqTradeAccountPositionOption;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionTable;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 资金账户的净持仓
 *
 * @author walter
 */
public class TradeAccountNetPositionTable extends TableHelper<TradeAccountPositionTable> implements IDBTable {

    private static final String TABLE_NAME = "t_trade_account_position";
    public static final String COLUMN_FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    public static final String COLUMN_FSLED_CONTRACT_ID = "Fsled_contract_id";
    public static final String COLUMN_FNET_POSITION = "Fnet_position";
    public static final String COLUMN_FCREATE_TIMESTAMP = "Fcreate_timestamp";
    public static final String COLUMN_FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    public TradeAccountNetPositionTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public TradeAccountPositionTable fromResultSet(ResultSet resultSet) throws Exception {
        TradeAccountPositionTable table = new TradeAccountPositionTable();
        table.setTradeAccount(resultSet.getLong(COLUMN_FTRADE_ACCOUNT_ID));
        table.setSledContractId(resultSet.getLong(COLUMN_FSLED_CONTRACT_ID));
        table.setNetPosition(resultSet.getInt(COLUMN_FNET_POSITION));
        table.setCreateTimestampMs(resultSet.getLong(COLUMN_FCREATE_TIMESTAMP));
        table.setLastModifyTimestampMs(resultSet.getLong(COLUMN_FLAST_MODITY_TIMESTAMP));
        return table;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (12 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Ftrade_account_id VARCHAR(64) NOT NULL ,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fnet_position INT NOT NULL DEFAULT 0,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("PRIMARY KEY(Ftrade_account_id,Fsled_contract_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(TradeAccountPositionTable positionTable) throws SQLException {
        Preconditions.checkNotNull(positionTable);
        Preconditions.checkArgument(positionTable.isSetTradeAccount());
        Preconditions.checkArgument(positionTable.isSetSledContractId());
        PreparedFields fields = new PreparedFields();
        if (positionTable.isSetTradeAccount()) {
            fields.addLong(COLUMN_FTRADE_ACCOUNT_ID, positionTable.getTradeAccount());
        }
        if (positionTable.isSetSledContractId()) {
            fields.addLong(COLUMN_FSLED_CONTRACT_ID, positionTable.getSledContractId());
        }
        if (positionTable.isSetNetPosition()) {
            fields.addInt(COLUMN_FNET_POSITION, positionTable.getNetPosition());
        }

        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FCREATE_TIMESTAMP, now);
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);
        super.insert(fields);
    }

    public TradeAccountPositionTable queryForUpdate(long tradeAccountId, long sledContractId) throws SQLException {
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FTRADE_ACCOUNT_ID + "=?", tradeAccountId);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FSLED_CONTRACT_ID + "=?", sledContractId);
        return super.getItem(builder);
    }

    public void update(TradeAccountPositionTable positionTable) throws SQLException {
        Preconditions.checkNotNull(positionTable);
        Preconditions.checkArgument(positionTable.isSetTradeAccount());
        Preconditions.checkArgument(positionTable.isSetSledContractId());
        PreparedFields fields = new PreparedFields();
        if (positionTable.isSetNetPosition()) {
            fields.addInt(COLUMN_FNET_POSITION, positionTable.getNetPosition());
        }
        long now = System.currentTimeMillis();
        fields.addLong(COLUMN_FLAST_MODITY_TIMESTAMP, now);
        super.update(fields, COLUMN_FTRADE_ACCOUNT_ID + "=? AND " + COLUMN_FSLED_CONTRACT_ID + " =?", positionTable.getTradeAccount(), positionTable.getSledContractId());
    }

    public List<TradeAccountPositionTable> query(ReqTradeAccountPositionOption option) throws SQLException {
        Preconditions.checkNotNull(option);
        Preconditions.checkArgument(option.isSetTradeAccountId());

        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        if (option.isSetTradeAccountId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, COLUMN_FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }

        return super.getItemList(builder);
    }

    public void delete(long tradeAccountId, long sledContractId) throws SQLException {
        super.delete(COLUMN_FTRADE_ACCOUNT_ID + "=? and " + COLUMN_FSLED_CONTRACT_ID + "=?", tradeAccountId, sledContractId);
    }

    public void delete(long sledContractId) throws SQLException {
        super.delete(COLUMN_FSLED_CONTRACT_ID + "=?", sledContractId);
    }

    public List<TradeAccountPositionTable> queryAll() throws SQLException {

        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        return super.getItemList(builder);
    }
}
