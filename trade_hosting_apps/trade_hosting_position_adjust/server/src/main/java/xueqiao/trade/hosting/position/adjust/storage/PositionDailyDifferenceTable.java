package xueqiao.trade.hosting.position.adjust.storage;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;
import xueqiao.trade.hosting.position.adjust.utils.DateTimeUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PositionDailyDifferenceTable extends TableHelper<DailyPositionDifference> implements IDBTable {

    private final static String TABLE_NAME = "t_position_daily_difference";
    private final static String FDATE_SEC = "Fdate_sec";
    private final static String FTRADE_ACCOUNT_ID = "Ftrade_account_id";
    private final static String FSLED_CONTRACT_ID = "Fsled_contract_id";
    private final static String FSLED_NET_POSITION = "Fsled_net_position";
    private final static String FUPSIDE_NET_POSITION = "Fupside_net_position";

    private final static String FINPUT_NET_POSITION = "Finput_net_position";
    private final static String FSUM_NET_POSITION = "Fsum_net_position";
    private final static String FSTART_TIMESTAMP = "Fstart_timestamp";
    private final static String FDOT_TIMESTAMP = "Fdot_timestamp";
    private final static String FMILESTONE = "Fmilestone";
    private final static String FVERIFY_STATUS = "Fverify_status";
    private final static String FNOTE = "Fnote";

    private final static String FCREATE_TIMESTAMP = "Fcreate_timestamp";
    private final static String FLAST_MODIFY_TIMESTAMP = "Flast_modify_timestamp";

    public PositionDailyDifferenceTable(Connection conn) {
        super(conn);
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public DailyPositionDifference fromResultSet(ResultSet resultSet) throws Exception {
        DailyPositionDifference difference = new DailyPositionDifference();
        difference.setDateSec(resultSet.getLong(FDATE_SEC));
        difference.setTradeAccountId(resultSet.getLong(FTRADE_ACCOUNT_ID));
        difference.setSledContractId(resultSet.getLong(FSLED_CONTRACT_ID));
        difference.setSledNetPosition(resultSet.getInt(FSLED_NET_POSITION));
        difference.setUpsideNetPosition(resultSet.getInt(FUPSIDE_NET_POSITION));
        difference.setInputNetPosition(resultSet.getInt(FINPUT_NET_POSITION));
        difference.setSumNetPosition(resultSet.getInt(FSUM_NET_POSITION));
        difference.setDotTimestampMs(resultSet.getLong(FDOT_TIMESTAMP));
        difference.setMilestone(Milestone.findByValue(resultSet.getInt(FMILESTONE)));
        difference.setVerifyStatus(VerifyStatus.findByValue(resultSet.getInt(FVERIFY_STATUS)));
        difference.setNote(resultSet.getString(FNOTE));
        difference.setCreateTimestampMs(resultSet.getLong(FCREATE_TIMESTAMP));
        difference.setLastmodifyTimestampMs(resultSet.getLong(FLAST_MODIFY_TIMESTAMP));
        difference.setStartTimestampMs(resultSet.getLong(FSTART_TIMESTAMP));
        difference.setPersisted(true);
        return difference;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (5 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append("Fdate_sec BIGINT UNSIGNED NOT NULL ,")
                    .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fsled_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fupside_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Finput_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fsum_net_position INT NOT NULL DEFAULT 0 ,")
                    .append("Fstart_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fdot_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fmilestone SMALLINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fverify_status SMALLINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Fnote VARCHAR(512) NOT NULL DEFAULT '' ,")
                    .append("Fcreate_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0 ,")
                    .append("Flast_modify_timestamp BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(Fdate_sec,Ftrade_account_id,Fsled_contract_id)")
                    .append(") CHARSET=utf8mb4, ENGINE=InnoDb");
            operator.execute(sqlBuilder.toString());
        }
    }

    public void add(DailyPositionDifference difference) throws SQLException {
        checkInput(difference);
        PreparedFields fields = getPreparedFields(difference);
        long now = System.currentTimeMillis();
        fields.addLong(FCREATE_TIMESTAMP, now);
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.insert(fields);
    }

    public void update(DailyPositionDifference difference) throws SQLException {
        checkInput(difference);
        PreparedFields fields = getPreparedFields(difference);
        long now = System.currentTimeMillis();
        fields.addLong(FLAST_MODIFY_TIMESTAMP, now);
        super.update(fields, FDATE_SEC + "=? and " + FTRADE_ACCOUNT_ID + "=? and " + FSLED_CONTRACT_ID + "=?",
                difference.getDateSec(), difference.getTradeAccountId(), difference.getSledContractId());
    }

    private void checkInput(DailyPositionDifference difference) {
        Preconditions.checkNotNull(difference);
        Preconditions.checkArgument(difference.isSetDateSec());
        Preconditions.checkArgument(difference.isSetTradeAccountId());
        Preconditions.checkArgument(difference.isSetSledContractId());
        Preconditions.checkArgument(difference.getDateSec() > 0);
        Preconditions.checkArgument(difference.getTradeAccountId() > 0);
        Preconditions.checkArgument(difference.getSledContractId() > 0);
    }


    private PreparedFields getPreparedFields(DailyPositionDifference difference) {
        PreparedFields fields = new PreparedFields();
        if (difference.isSetDateSec()) {
            fields.addLong(FDATE_SEC, difference.getDateSec());
        }
        if (difference.isSetTradeAccountId()) {
            fields.addLong(FTRADE_ACCOUNT_ID, difference.getTradeAccountId());
        }
        if (difference.isSetSledContractId()) {
            fields.addLong(FSLED_CONTRACT_ID, difference.getSledContractId());
        }
        if (difference.isSetSledNetPosition()) {
            fields.addInt(FSLED_NET_POSITION, difference.getSledNetPosition());
        }
        if (difference.isSetUpsideNetPosition()) {
            fields.addInt(FUPSIDE_NET_POSITION, difference.getUpsideNetPosition());
        }
        if (difference.isSetInputNetPosition()) {
            fields.addInt(FINPUT_NET_POSITION, difference.getInputNetPosition());
        }
        if (difference.isSetSumNetPosition()) {
            fields.addInt(FSUM_NET_POSITION, difference.getSumNetPosition());
        }
        if (difference.isSetDotTimestampMs()) {
            fields.addLong(FDOT_TIMESTAMP, difference.getDotTimestampMs());
        }
        if (difference.isSetMilestone()) {
            fields.addInt(FMILESTONE, difference.getMilestone().getValue());
        }
        if (difference.isSetVerifyStatus()) {
            fields.addInt(FVERIFY_STATUS, difference.getVerifyStatus().getValue());
        }
        if (difference.isSetNote()) {
            fields.addString(FNOTE, difference.getNote());
        }
        if (difference.isSetStartTimestampMs()) {
            fields.addLong(FSTART_TIMESTAMP, difference.getStartTimestampMs());
        }
        return fields;
    }

    public DailyPositionDifference queryForUpdate(long dateSec, long tradeAccountId, long sledContractId) throws SQLException {
        Preconditions.checkArgument(dateSec > 0);
        Preconditions.checkArgument(tradeAccountId > 0);
        Preconditions.checkArgument(sledContractId > 0);
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FDATE_SEC + "=?", dateSec);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", tradeAccountId);
        builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", sledContractId);

        return super.getItem(builder);
    }

    public DailyPositionDifferencePage query(ReqDailyPositionDifferenceOption option, IndexedPageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(option);
        SqlQueryBuilder builder = super.prepareSqlQueryBuilder();
        if (option.isSetDateSec()) {
            long dateSec = DateTimeUtils.getDateTimeSecDatePart(DateTimeUtils.getDateStringDatePart(option.getDateSec() * 1000));
            Preconditions.checkArgument(dateSec > 0);
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FDATE_SEC + "=?", dateSec);
        }
        if (option.isSetTradeAccountId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FTRADE_ACCOUNT_ID + "=?", option.getTradeAccountId());
        }
        if (option.isSetSledContractId()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSLED_CONTRACT_ID + "=?", option.getSledContractId());
        }
        if (option.isSetStartDateTimestampMs()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FSTART_TIMESTAMP + ">?" + option.getStartDateTimestampMs());
        }
        if (option.isSetEndDateTimestampMs()) {
            builder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, FDOT_TIMESTAMP + "<?", option.getEndDateTimestampMs());
        }

        DailyPositionDifferencePage page = new DailyPositionDifferencePage();
        if (pageOption != null) {
            if (pageOption.isSetPageIndex()) {
                builder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
            }
            if (pageOption.isNeedTotalCount()) {
                page.setTotal(super.getTotalNum(builder));
            }
        }
        page.setPage(super.getItemList(builder));
        return page;
    }
}
