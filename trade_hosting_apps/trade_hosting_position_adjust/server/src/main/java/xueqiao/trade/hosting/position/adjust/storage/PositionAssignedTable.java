package xueqiao.trade.hosting.position.adjust.storage;

import com.antiy.error_code.ErrorCodeOuter;
import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.id_maker.IdException;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionAssigned;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.config.Config;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionAssignedTable extends TableHelper<PositionAssigned> implements IDBTable {

    private final static String TABLE_NAME = "t_position_assigned";
    private final static String FASSIGN_ID = "Fassign_id";

    private final static String FSUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String FINPUT_SUB_USER_ID = "Finput_sub_user_id";
    private final static String FASSIGN_SUB_USER_ID = "Fassign_sub_user_id";

    private final static String FINPUT_ID = "Finput_id";

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


    public PositionAssignedTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionAssigned fromResultSet(ResultSet resultSet) throws Exception {
        PositionAssigned positionAssigned = new PositionAssigned();
        positionAssigned.setAssignId(resultSet.getLong(FASSIGN_ID));

        positionAssigned.setInputSubUserId(resultSet.getLong(FINPUT_SUB_USER_ID));
        positionAssigned.setAssignSubUserId(resultSet.getLong(FASSIGN_SUB_USER_ID));
        positionAssigned.setSubAccountId(resultSet.getLong(FSUB_ACCOUNT_ID));

        positionAssigned.setInputId(resultSet.getLong(FINPUT_ID));

        positionAssigned.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        positionAssigned.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        positionAssigned.setSledCommodityId(resultSet.getLong(FSLED_COMMODITY_ID));
        positionAssigned.setPrice(resultSet.getDouble(FPRICE));
        positionAssigned.setVolume(resultSet.getInt(FVOLUME));
        positionAssigned.setPositionDirection(PositionDirection.findByValue(resultSet.getInt(FPOSITION_DIRECTION)));
        positionAssigned.setPositionTimestampMs(resultSet.getLong(FPOSITION_TIMESTAMP));
        positionAssigned.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        positionAssigned.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return positionAssigned;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {

        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fassign_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Fsub_account_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Finput_sub_user_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Fassign_sub_user_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Finput_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_commodity_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fprice DOUBLE  NOT NULL DEFAULT 0.0 ,")
                    .append("Fvolume INT NOT NULL DEFAULT 0 ,")
                    .append("Fposition_direction SMALLINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fposition_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fassign_id),")
                    .append("INDEX(Ftrade_account_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }

    }

    public long add(PositionAssigned positionAssigned) throws SQLException, ErrorInfo {
        Preconditions.checkNotNull(positionAssigned);

        PreparedFields fields = new PreparedFields();
        if (positionAssigned.isSetSubAccountId()) {
            fields.addLong(FSUB_ACCOUNT_ID, positionAssigned.getSubAccountId());
        }
        if (positionAssigned.isSetInputSubUserId()) {
            fields.addLong(FINPUT_SUB_USER_ID, positionAssigned.getInputSubUserId());
        }
        if (positionAssigned.isSetAssignSubUserId()) {
            fields.addLong(FASSIGN_SUB_USER_ID, positionAssigned.getAssignSubUserId());
        }
        if (positionAssigned.isSetInputId()) {
            fields.addLong(FINPUT_ID, positionAssigned.getInputId());
        }
        if (positionAssigned.isSetTradeAccountId()) {
            fields.addLong(FTRADE_ACCOUNT_ID, positionAssigned.getTradeAccountId());
        }
        if (positionAssigned.isSetSledContractId()) {
            fields.addLong(FSLED_CONTRACT_ID, positionAssigned.getSledContractId());
        }
        if (positionAssigned.isSetSledCommodityId()) {
            fields.addLong(FSLED_COMMODITY_ID, positionAssigned.getSledCommodityId());
        }
        if (positionAssigned.isSetPrice()) {
            fields.addDouble(FPRICE, positionAssigned.getPrice());
        }
        if (positionAssigned.isSetVolume()) {
            fields.addInt(FVOLUME, positionAssigned.getVolume());
        }
        if (positionAssigned.isSetPositionDirection()) {
            fields.addInt(FPOSITION_DIRECTION, positionAssigned.getPositionDirection().getValue());
        }
        if (positionAssigned.isSetPositionTimestampMs()) {
            fields.addLong(FPOSITION_TIMESTAMP, positionAssigned.getPositionTimestampMs());
        }
        long assignedId;
        try {
            assignedId = Config.getInstance().getPositionAssignIdMaker().createId();
        } catch (IdException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
        }

        fields.addLong(FASSIGN_ID, assignedId);

        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);

        super.insert(fields);
        return assignedId;
    }

    public PositionAssignedPage query(ReqPositionAssignedOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);

        SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();

        if (option.isSetTradeAccountId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetSubUserId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FASSIGN_SUB_USER_ID + "=?", option.getSubUserId());
        }
        if (option.isSetSledContractId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", option.getSledContractId());
        }
        if (option.isSetSubAccountId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSUB_ACCOUNT_ID + "=?", option.getSubAccountId());
        }
        if (option.isSetInputId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FINPUT_ID + "=?", option.getInputId());
        }
        if (option.isSetAssignStartTimestamp()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FCREATE_TIMESTAMP + ">=?", option.getAssignStartTimestamp()*1000);
        }
        if (option.isSetAssignEndTimestamp()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FCREATE_TIMESTAMP + "<=?", option.getAssignEndTimestamp()*1000);
        }

        PositionAssignedPage page = new PositionAssignedPage();
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
}
