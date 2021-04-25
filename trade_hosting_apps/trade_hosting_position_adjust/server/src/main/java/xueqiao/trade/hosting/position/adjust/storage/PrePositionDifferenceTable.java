package xueqiao.trade.hosting.position.adjust.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.PrePositionDifference;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PrePositionDifferenceTable extends TableHelper<PrePositionDifference> implements IDBTable {

    private final static String TABLE_NAME = "t_pre_position_difference";
    private final static String FDATE_SEC = "Fdate_sec";
    private final static String FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String FSLED_CONTRACT_ID = "Fsled_contract_id";
    private final static String FSLED_NET_POSITION = "Fsled_net_position";
    private final static String FUPSIDE_NET_POSITION = "Fupside_net_position";
    private final static String FSTART_TIMESTAMP = "Fstart_timestamp";
    private final static String FDOT_TIMESTAMP = "Fdot_timestamp";
    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    public PrePositionDifferenceTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PrePositionDifference fromResultSet(ResultSet resultSet) throws Exception {
        PrePositionDifference difference = new PrePositionDifference();
        difference.setDateSec(resultSet.getLong(FDATE_SEC));
        difference.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        difference.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        difference.setSledNetPosition(resultSet.getInt(FSLED_NET_POSITION));
        difference.setUpsideNetPosition(resultSet.getInt(FUPSIDE_NET_POSITION));
        difference.setDotTimestampMs(resultSet.getLong(FDOT_TIMESTAMP));
        difference.setStartTimestampMs(resultSet.getLong(FSTART_TIMESTAMP));
        difference.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        difference.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return difference;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (4 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fdate_sec BIGINT UNSIGNED NOT NULL ,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fsled_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fupside_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fstart_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fdot_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Ftrade_account_id,Fsled_contract_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(PrePositionDifference prePositionDifference) throws SQLException {
        checkInput(prePositionDifference);
        PreparedFields fields = getPreparedFields(prePositionDifference);
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.insert(fields);
    }

    private PreparedFields getPreparedFields(PrePositionDifference prePositionDifference) {
        PreparedFields fields = new PreparedFields();
        if (prePositionDifference.isSetDateSec()) {
            fields.addLong(FDATE_SEC, prePositionDifference.getDateSec());
        }
        if (prePositionDifference.isSetTradeAccountId()) {
            fields.addLong(FTRADE_ACCOUNT_ID, prePositionDifference.getTradeAccountId());
        }
        if (prePositionDifference.isSetSledContractId()) {
            fields.addLong(FSLED_CONTRACT_ID, prePositionDifference.getSledContractId());
        }
        if (prePositionDifference.isSetSledNetPosition()) {
            fields.addInt(FSLED_NET_POSITION, prePositionDifference.getSledNetPosition());
        }
        if (prePositionDifference.isSetUpsideNetPosition()) {
            fields.addInt(FUPSIDE_NET_POSITION, prePositionDifference.getUpsideNetPosition());
        }
        if (prePositionDifference.isSetDotTimestampMs()) {
            fields.addLong(FDOT_TIMESTAMP, prePositionDifference.getDotTimestampMs());
        }
        if (prePositionDifference.isSetStartTimestampMs()) {
            fields.addLong(FSTART_TIMESTAMP, prePositionDifference.getStartTimestampMs());
        }
        return fields;
    }

    private void checkInput(PrePositionDifference prePositionDifference) {
        Preconditions.checkNotNull(prePositionDifference);
        Preconditions.checkArgument(prePositionDifference.isSetDateSec());
        Preconditions.checkArgument(prePositionDifference.isSetTradeAccountId());
        Preconditions.checkArgument(prePositionDifference.isSetSledContractId());
        Preconditions.checkArgument(prePositionDifference.getDateSec() > 0);
        Preconditions.checkArgument(prePositionDifference.getTradeAccountId() > 0);
        Preconditions.checkArgument(prePositionDifference.getSledContractId() > 0);
    }

    public void update(PrePositionDifference prePositionDifference) throws SQLException {
        checkInput(prePositionDifference);
        PreparedFields fields = getPreparedFields(prePositionDifference);

        super.update(fields, FTRADE_ACCOUNT_ID + "=? and " + FSLED_CONTRACT_ID + "=?",
                prePositionDifference.getTradeAccountId(), prePositionDifference.getSledContractId());
    }

    public PrePositionDifference queryForUpdate(long sledContractId, long tradeAccountId, boolean isForUpdate) throws SQLException {
        Preconditions.checkArgument(sledContractId > 0);
        Preconditions.checkArgument(tradeAccountId > 0);
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", tradeAccountId);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", sledContractId);
        return super.getItem(builder);
    }

    public List<PrePositionDifference> query(long tradeAccountId) throws SQLException {
        Preconditions.checkArgument(tradeAccountId > 0);
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", tradeAccountId);
        return super.getItemList(builder);
    }
}
