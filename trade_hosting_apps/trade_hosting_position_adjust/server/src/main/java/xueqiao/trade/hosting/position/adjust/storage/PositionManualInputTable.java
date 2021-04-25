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
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;
import xueqiao.trade.hosting.position.adjust.config.Config;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInput;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionManualInputPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionManualInputOption;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PositionManualInputTable extends TableHelper<PositionManualInput> implements IDBTable {

    private final static String TABLE_NAME = "t_position_manual_input";
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


    public PositionManualInputTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionManualInput fromResultSet(ResultSet resultSet) throws Exception {
        PositionManualInput input = new PositionManualInput();
        input.setInputId(resultSet.getLong(FINPUT_ID));
        input.setSubUserId(resultSet.getLong(FSUB_USER_ID));
        input.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        input.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        input.setSledCommodityId(resultSet.getLong(FSLED_COMMODITY_ID));
        input.setPrice(resultSet.getDouble(FPRICE));
        input.setVolume(resultSet.getInt(FVOLUME));
        input.setPositionDirection(PositionDirection.findByValue(resultSet.getInt(FPOSITION_DIRECTION)));
        input.setPositionTimestampMs(resultSet.getLong(FPOSITION_TIMESTAMP));
        input.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        input.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return input;
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
                    .append("Fvolume INT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fposition_direction SMALLINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fposition_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Finput_id),")
                    .append("INDEX (Ftrade_account_id,Fposition_timestamp)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public long add(PositionManualInput positionManualInput) throws SQLException, ErrorInfo {
        Preconditions.checkNotNull(positionManualInput);

        long inputId;
        try {
            inputId = Config.getInstance().getManualInputIdMaker().createId();
        } catch (IdException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
        }

        PreparedFields fields = new PreparedFields();
        fields.addLong(FINPUT_ID, inputId);
        if (positionManualInput.isSetSubUserId()) {
            fields.addLong(FSUB_USER_ID, positionManualInput.getSubUserId());
        }
        if (positionManualInput.isSetTradeAccountId()) {
            fields.addLong(FTRADE_ACCOUNT_ID, positionManualInput.getTradeAccountId());
        }
        if (positionManualInput.isSetSledContractId()) {
            fields.addLong(FSLED_CONTRACT_ID, positionManualInput.getSledContractId());
        }
        if (positionManualInput.isSetSledCommodityId()) {
            fields.addLong(FSLED_COMMODITY_ID, positionManualInput.getSledCommodityId());
        }
        if (positionManualInput.isSetPrice()) {
            fields.addDouble(FPRICE, positionManualInput.getPrice());
        }
        if (positionManualInput.isSetVolume()) {
            fields.addInt(FVOLUME, positionManualInput.getVolume());
        }
        if (positionManualInput.isSetPositionDirection()) {
            fields.addInt(FPOSITION_DIRECTION, positionManualInput.getPositionDirection().getValue());
        }
        if (positionManualInput.isSetPositionTimestampMs()) {
            fields.addLong(FPOSITION_TIMESTAMP, positionManualInput.getPositionTimestampMs());
        }
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);

        super.insert(fields);
        return inputId;
    }

    public PositionManualInputPage query(ReqPositionManualInputOption option, IndexedPageOption pageOption) throws SQLException {
        SqlQueryBuilder sqlBuilder = getSqlQueryBuilder(option);

        PositionManualInputPage page = new PositionManualInputPage();
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

    private SqlQueryBuilder getSqlQueryBuilder(ReqPositionManualInputOption option) throws SQLException {
        Preconditions.checkNotNull(option);

        SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();

        if (option.isSetTradeAccountId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetSubUserId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSUB_USER_ID + "=?", option.getSubUserId());
        }
        if (option.isSetSledContractId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", option.getSledContractId());
        }
        if (option.isSetStartTradeTimestampMs()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FPOSITION_TIMESTAMP + ">=?", option.getStartTradeTimestampMs());
        }
        if (option.isSetEndTradeTimestampMs()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FPOSITION_TIMESTAMP + "<=?", option.getEndTradeTimestampMs());
        }
        if (option.isSetAssignSubUserId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSUB_USER_ID + "=?", option.getAssignSubUserId());
        }
        return sqlBuilder;
    }

    public List<PositionManualInput> query(ReqPositionManualInputOption option) throws SQLException {
        SqlQueryBuilder sqlBuilder = getSqlQueryBuilder(option);
        return super.getItemList(sqlBuilder);
    }
}
