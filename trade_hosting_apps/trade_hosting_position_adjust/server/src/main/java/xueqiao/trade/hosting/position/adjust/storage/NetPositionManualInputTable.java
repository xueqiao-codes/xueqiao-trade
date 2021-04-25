package xueqiao.trade.hosting.position.adjust.storage;

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

public class NetPositionManualInputTable extends TableHelper<TradeAccountPositionTable> implements IDBTable {
    public NetPositionManualInputTable(Connection conn) {
        super(conn);
    }

    private static final String TABLE_NAME = "t_trade_account_manual_input_position";
    public static final String FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    public static final String FSLED_CONTRACT_ID = "Fsled_contract_id";
    public static final String FNET_POSITION = "Fnet_position";
    public static final String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    public static final String FLAST_MODITY_TIMESTAMP = "Flast_modify_timestamp";

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public TradeAccountPositionTable fromResultSet(ResultSet resultSet) throws Exception {
        TradeAccountPositionTable table = new TradeAccountPositionTable();
        table.setTradeAccount(resultSet.getLong(FTRADE_ACCOUNT_ID));
        table.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        table.setNetPosition(resultSet.getInt(FNET_POSITION));
        table.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        table.setLastModifyTimestampMs(resultSet.getLong(FLAST_MODITY_TIMESTAMP));
        return table;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
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
            fields.addLong(FTRADE_ACCOUNT_ID, positionTable.getTradeAccount());
        }
        if (positionTable.isSetSledContractId()) {
            fields.addLong(FSLED_CONTRACT_ID, positionTable.getSledContractId());
        }
        if (positionTable.isSetNetPosition()) {
            fields.addInt(FNET_POSITION, positionTable.getNetPosition());
        }

        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODITY_TIMESTAMP, now);
        super.insert(fields);
    }

    public TradeAccountPositionTable queryForUpdate(long tradeAccountId, long sledContractId) throws SQLException {
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", tradeAccountId);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", sledContractId);
        return super.getItem(builder);
    }

    public void update(TradeAccountPositionTable positionTable) throws SQLException {
        Preconditions.checkNotNull(positionTable);
        Preconditions.checkArgument(positionTable.isSetTradeAccount());
        Preconditions.checkArgument(positionTable.isSetSledContractId());
        PreparedFields fields = new PreparedFields();
        if (positionTable.isSetNetPosition()) {
            fields.addInt(FNET_POSITION, positionTable.getNetPosition());
        }
        long now = System.currentTimeMillis();
        fields.addLong(FLAST_MODITY_TIMESTAMP, now);
        super.update(fields, FTRADE_ACCOUNT_ID + "=? AND " + FSLED_CONTRACT_ID + " =?", positionTable.getTradeAccount(), positionTable.getSledContractId());
    }

    public List<TradeAccountPositionTable> query(ReqTradeAccountPositionOption option) throws SQLException {
        Preconditions.checkNotNull(option);
        Preconditions.checkArgument(option.isSetTradeAccountId());

        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        if (option.isSetTradeAccountId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }

        return super.getItemList(builder);
    }

    public void delete(long sledContractId) throws SQLException {
        super.delete(FSLED_CONTRACT_ID + "=?", sledContractId);
    }

    public List<TradeAccountPositionTable> queryGroupByContractId() throws SQLException {

        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.setGroup(FSLED_CONTRACT_ID);
        return super.getItemList(builder);
    }
}
