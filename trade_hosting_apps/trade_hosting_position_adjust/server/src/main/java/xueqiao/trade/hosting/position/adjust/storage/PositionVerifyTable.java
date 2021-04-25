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
import xueqiao.trade.hosting.position.adjust.config.Config;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerify;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerifyPage;
import xueqiao.trade.hosting.position.adjust.thriftapi.ReqPositionVerifyOption;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionVerifyTable extends TableHelper<PositionVerify> implements IDBTable {

    private final static String TABLE_NAME = "t_position_verify";
    private final static String FVERIFY_ID = "Fverify_id";
    private final static String FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String FVERIFY_TIMESTAMP = "Fverify_timestamp";
    private final static String FIS_DIFFERENT = "Fis_different";
    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    public PositionVerifyTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public PositionVerify fromResultSet(ResultSet resultSet) throws Exception {
        PositionVerify verify = new PositionVerify();
        verify.setVerifyId(resultSet.getLong(FVERIFY_ID));
        verify.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        verify.setVerifyTimestampMs(resultSet.getLong(FVERIFY_TIMESTAMP));
        verify.setDifferent(1 == resultSet.getInt(FIS_DIFFERENT) ? true : false);
        verify.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        verify.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        return verify;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fverify_id BIGINT UNSIGNED NOT NULL ,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fverify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fis_different SMALLINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fverify_id),")
                    .append("INDEX(Ftrade_account_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public long add(PositionVerify positionVerify) throws SQLException, ErrorInfo {
        Preconditions.checkNotNull(positionVerify);
        PreparedFields fields = new PreparedFields();
        if (positionVerify.isSetTradeAccountId()) {
            fields.addLong(FTRADE_ACCOUNT_ID, positionVerify.getTradeAccountId());
        }
        if (positionVerify.isSetVerifyTimestampMs()) {
            fields.addLong(FVERIFY_TIMESTAMP, positionVerify.getVerifyTimestampMs());
        }
        if (positionVerify.isSetDifferent()) {
            fields.addInt(FIS_DIFFERENT, positionVerify.isDifferent() ? 1 : 0);
        }
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);

        long verifyId;
        try {
            verifyId = Config.getInstance().getPositionVerifyIdMaker().createId();
        } catch (IdException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), ErrorCodeOuter.SERVER_BUSY.getErrorMsg());
        }
        fields.addLong(FVERIFY_ID, verifyId);

        super.insert(fields);
        return verifyId;
    }

    public PositionVerifyPage query(ReqPositionVerifyOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);
        SqlQueryBuilder sqlBuilder = super.prepareSqlQueryBuilder();

        if (option.isSetTradeAccountId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetVerifyId()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FVERIFY_ID + "=?", option.getVerifyId());
        }
        if (option.isSetStartVerifyTimestampMs()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FVERIFY_TIMESTAMP + ">=?", option.getStartVerifyTimestampMs());
        }
        if (option.isSetEndVerifyTimestampMs()) {
            sqlBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FVERIFY_TIMESTAMP + "<=?", option.getEndVerifyTimestampMs());
        }
        if (option.isLatest()) {
            pageOption = new IndexedPageOption();
            pageOption.setPageIndex(0);
            pageOption.setPageSize(1);
        }
        sqlBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, FVERIFY_TIMESTAMP);
        PositionVerifyPage page = new PositionVerifyPage();
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
