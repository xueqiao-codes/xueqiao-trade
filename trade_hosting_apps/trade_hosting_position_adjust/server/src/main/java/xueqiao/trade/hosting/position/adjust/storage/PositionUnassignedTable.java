package xueqiao.trade.hosting.position.adjust.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class
PositionUnassignedTable extends TableHelper<PositionUnassigned> implements IDBTable {

    private final static String TABLE_NAME = "t_position_unassigned";
    private final static String FINPUT_ID = "Finput_id";
    private final static String FSUB_USER_ID = "Fsub_user_id";
    private final static String FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String FSLED_CONTRACT_ID = "Fsled_contract_id";
    private final static String FSLED_COMMODITY_ID = "Fsled_commodity_id";
    private final static String FPRICE = "Fprice";
    private final static String FVOLUME = "Fvolume";
    private final static String FPOSITION_DIRECTION = "Fposition_direction";
    private final static String FPOSITION_TIMESTAMP = "Fposition_timestamp";
    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    // TODO REQ OPTION UPDATE


    public PositionUnassignedTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionUnassigned fromResultSet(ResultSet resultSet) throws Exception {
        PositionUnassigned positionUnassigned = new PositionUnassigned();
        positionUnassigned.setInputId(resultSet.getLong(FINPUT_ID));
        positionUnassigned.setInputSubUserId(resultSet.getLong(FSUB_USER_ID));
        positionUnassigned.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        positionUnassigned.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        positionUnassigned.setSledCommodityId(resultSet.getLong(FSLED_COMMODITY_ID));
        positionUnassigned.setPrice(resultSet.getDouble(FPRICE));
        positionUnassigned.setVolume(resultSet.getInt(FVOLUME));
        positionUnassigned.setPositionDirection(PositionDirection.findByValue(resultSet.getInt(FPOSITION_DIRECTION)));
        positionUnassigned.setPositionTimestampMs(resultSet.getLong(FPOSITION_TIMESTAMP));
        positionUnassigned.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        positionUnassigned.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return positionUnassigned;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {

        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Finput_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Fsub_user_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_commodity_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fprice DOUBLE NOT NULL DEFAULT 0.0 ,")
                    .append("Fvolume INT NOT NULL DEFAULT 0 ,")
                    .append("Fposition_direction SMALLINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fposition_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Finput_id),")
                    .append("INDEX(Ftrade_account_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(PositionUnassigned unassigned) throws SQLException {
        Preconditions.checkNotNull(unassigned);
        PreparedFields fields = getPreparedFields(unassigned);
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);

        if (unassigned.isSetInputId()) {
            fields.addLong(FINPUT_ID, unassigned.getInputId());
        }

        super.insert(fields);
    }

    public void update(PositionUnassigned unassigned) throws SQLException {
        Preconditions.checkNotNull(unassigned);
        Preconditions.checkArgument(unassigned.getInputId() > 0);
        PreparedFields fields = getPreparedFields(unassigned);
        long now = System.currentTimeMillis();
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);

        super.update(fields, FINPUT_ID + "=?", unassigned.getInputId());
    }

    public void delete(long inputId) throws SQLException {
        super.delete(FINPUT_ID + "=?", inputId);
    }

    private PreparedFields getPreparedFields(PositionUnassigned unassigned) {
        PreparedFields fields = new PreparedFields();

        if (unassigned.isSetInputSubUserId()) {
            fields.addLong(FSUB_USER_ID, unassigned.getInputSubUserId());
        }
        if (unassigned.isSetTradeAccountId()) {
            fields.addLong(FTRADE_ACCOUNT_ID, unassigned.getTradeAccountId());
        }
        if (unassigned.isSetSledContractId()) {
            fields.addLong(FSLED_CONTRACT_ID, unassigned.getSledContractId());
        }
        if (unassigned.isSetSledCommodityId()) {
            fields.addLong(FSLED_COMMODITY_ID, unassigned.getSledCommodityId());
        }
        if (unassigned.isSetPrice()) {
            fields.addDouble(FPRICE, unassigned.getPrice());
        }
        if (unassigned.isSetVolume()) {
            fields.addInt(FVOLUME, unassigned.getVolume());
        }
        if (unassigned.isSetPositionDirection()) {
            fields.addInt(FPOSITION_DIRECTION, unassigned.getPositionDirection().getValue());
        }
        if (unassigned.isSetPositionTimestampMs()) {
            fields.addLong(FPOSITION_TIMESTAMP, unassigned.getPositionTimestampMs());
        }
        return fields;
    }

    public PositionUnassignedPage query(ReqPositionUnassignedOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);

        SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();

        if (option.isSetTradeAccountId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetManualInputUserId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSUB_USER_ID + "=?", option.getManualInputUserId());
        }
        if (option.isSetSledContractId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", option.getSledContractId());
        }
        if (option.isSetInputId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FINPUT_ID + "=?", option.getInputId());
        }

        PositionUnassignedPage page = new PositionUnassignedPage();
        if (pageOption != null) {
            if (pageOption.isSetPageIndex()) {
                sqlBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            }
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(sqlBuilder));
            }
        }
        page.setPage(super.getItemList(sqlBuilder));
        return page;
    }

    public PositionUnassigned queryForUpdate(long inputId, boolean isForUpdate) throws SQLException {
        SqlQueryBuilder buider = super.prepareSqlQueryBuilder();
        buider.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FINPUT_ID + "=?", inputId);
        return super.getItem(buider, isForUpdate);
    }
}
