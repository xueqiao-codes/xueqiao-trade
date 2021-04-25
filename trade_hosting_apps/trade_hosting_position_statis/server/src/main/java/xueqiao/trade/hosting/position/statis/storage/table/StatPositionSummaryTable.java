package xueqiao.trade.hosting.position.statis.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.position.statis.QueryStatPositionSummaryOption;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StatPositionSummaryTable extends TableHelper<StatPositionSummary> implements IDBTable {

    private final static String TABLE_NAME = "stat_position_summary";
    private final static String F_TARGET_KEY = "Ftarget_key";
    private final static String F_SUB_ACCOUNT_ID = "Fsub_account_id";
    private final static String F_TARGET_TYPE = "Ftarget_type";
    private final static String F_LONG_POSITION = "Flong_position";
    private final static String F_SHORT_POSITION = "Fshort_position";
    private final static String F_NET_POSITION = "Fnet_position";
    private final static String F_POSITION_AVG_PRICE = "Fposition_avg_price";
    private final static String F_CREATE_TIMESTAMP_MS = "Fcreate_timestamp_ms";
    private final static String F_LASTMODITY_TIMESTAMP_MS = "Flastmodify_timestamp_ms";

    // where condition
    private final static String WHERE_CONDITION_TARGET_KEY = F_TARGET_KEY + "=?";
    private final static String WHERE_CONDITION_TARGET_TYPE = F_TARGET_TYPE + "=?";
    private final static String WHERE_CONDITION_SUB_ACCOUNT_ID = F_SUB_ACCOUNT_ID + "=?";
    private final static String WHERE_CONDITION_TARGET_KEY_AND_TARGET_TYPE_AND_SUB_ACCOUNT_ID = F_TARGET_KEY + "=? AND "
            + F_TARGET_TYPE + "=? AND "
            + F_SUB_ACCOUNT_ID + "=?";

    public StatPositionSummaryTable(Connection conn) {
        super(conn);
    }

    /**
     * 添加
     */
    public int insert(StatPositionSummary statPositionSummary) throws SQLException {
        PreparedFields pf = new PreparedFields();
        long currentTimeMillis = System.currentTimeMillis();

        pf.addString(F_TARGET_KEY, statPositionSummary.getTargetKey());
        pf.addLong(F_SUB_ACCOUNT_ID, statPositionSummary.getSubAccountId());
        pf.addInt(F_TARGET_TYPE, statPositionSummary.getTargetType().getValue());
        pf.addLong(F_LONG_POSITION, statPositionSummary.getLongPosition());
        pf.addLong(F_SHORT_POSITION, statPositionSummary.getShortPosition());
        pf.addLong(F_NET_POSITION, statPositionSummary.getNetPosition());
        pf.addDouble(F_POSITION_AVG_PRICE, statPositionSummary.getPositionAvgPrice());
        if (statPositionSummary.isSetCreateTimestampMs()) {
            pf.addLong(F_CREATE_TIMESTAMP_MS, statPositionSummary.getCreateTimestampMs());
        } else {
            pf.addLong(F_CREATE_TIMESTAMP_MS, currentTimeMillis);
        }
        if (statPositionSummary.isSetLastmodifyTimestampMs()) {
            pf.addLong(F_LASTMODITY_TIMESTAMP_MS, statPositionSummary.getLastmodifyTimestampMs());
        } else {
            pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currentTimeMillis);
        }
        return super.insert(pf);
    }

    /**
     * 根据F_TARGET_KEY, F_TARGET_TYPE 和 F_SUB_ACCOUNT_ID 更新
     */
    public int update(StatPositionSummary statPositionSummary) throws SQLException {
        PreparedFields pf = new PreparedFields();
        long currentTimeMillis = System.currentTimeMillis();

        if (statPositionSummary.isSetLongPosition()) {
            pf.addLong(F_LONG_POSITION, statPositionSummary.getLongPosition());
        }
        if (statPositionSummary.isSetShortPosition()) {
            pf.addLong(F_SHORT_POSITION, statPositionSummary.getShortPosition());
        }
        if (statPositionSummary.isSetNetPosition()) {
            pf.addLong(F_NET_POSITION, statPositionSummary.getNetPosition());
        }
        if (statPositionSummary.isSetPositionAvgPrice()) {
            pf.addDouble(F_POSITION_AVG_PRICE, statPositionSummary.getPositionAvgPrice());
        }
        if (statPositionSummary.isSetLastmodifyTimestampMs()) {
            pf.addLong(F_LASTMODITY_TIMESTAMP_MS, statPositionSummary.getLastmodifyTimestampMs());
        } else {
            pf.addLong(F_LASTMODITY_TIMESTAMP_MS, currentTimeMillis);
        }

        return super.update(pf, WHERE_CONDITION_TARGET_KEY_AND_TARGET_TYPE_AND_SUB_ACCOUNT_ID, statPositionSummary.getTargetKey(), statPositionSummary.getTargetType().getValue(), statPositionSummary.getSubAccountId());
    }

    /**
     * 根据F_TARGET_KEY, F_TARGET_TYPE 和 F_SUB_ACCOUNT_ID 删除
     */
    public void delete(String targetKey, HostingXQTargetType targetType, long subAccountId) throws SQLException {
        super.delete(WHERE_CONDITION_TARGET_KEY_AND_TARGET_TYPE_AND_SUB_ACCOUNT_ID, targetKey, targetType.getValue(), subAccountId);
    }

    /**
     * 删除所有长仓和短仓都为0的汇总
     */
    public void clearEmptyPositionSumary() throws SQLException {
        super.delete(F_LONG_POSITION + "=? AND " + F_SHORT_POSITION + "=?", 0, 0);
    }

    /**
     * 根据F_TARGET_KEY, F_TARGET_TYPE 和 F_SUB_ACCOUNT_ID 查询
     */
    public StatPositionSummary queryStatPositionSummary(String targetKey, HostingXQTargetType targetType, long subAccountId) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, WHERE_CONDITION_TARGET_KEY, targetKey);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, WHERE_CONDITION_TARGET_TYPE, targetType.getValue());
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, WHERE_CONDITION_SUB_ACCOUNT_ID, subAccountId);
        return super.getItem(qryBuilder, false);
    }

    /**
     * 查所有
     */
    public List<StatPositionSummary> queryAllStatPositionSummary() throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        return super.getItemList(qryBuilder, false);
    }

    /**
     * 查询
     */
    public PageResult<StatPositionSummary> getStatPositionSummaryPage(QueryStatPositionSummaryOption qryOption, IndexedPageOption pageOption)
            throws SQLException {
        Preconditions.checkNotNull(pageOption);
        Preconditions.checkArgument(pageOption.getPageIndex() >= 0 && pageOption.getPageSize() > 0);

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.isSetSubAccountId()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_SUB_ACCOUNT_ID + "=?", qryOption.getSubAccountId());
            }
            if (qryOption.isSetTargetKey()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_KEY + "=?", qryOption.getTargetKey());
            }
            if (qryOption.isSetTargetType()) {
                qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, F_TARGET_TYPE + "=?", qryOption.getTargetType().getValue());
            }
        }
        qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, F_CREATE_TIMESTAMP_MS);
        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        PageResult<StatPositionSummary> resultPage = new PageResult<StatPositionSummary>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(qryBuilder));
        }
        resultPage.setPageList(super.getItemList(qryBuilder));

        return resultPage;
    }

    @Override
    protected String getTableName() throws SQLException {
        return TABLE_NAME;
    }

    @Override
    public StatPositionSummary fromResultSet(ResultSet resultSet) throws Exception {
        StatPositionSummary statPositionSummary = new StatPositionSummary();

        statPositionSummary.setTargetKey(resultSet.getString(F_TARGET_KEY));
        statPositionSummary.setSubAccountId(resultSet.getLong(F_SUB_ACCOUNT_ID));
        statPositionSummary.setTargetType(HostingXQTargetType.findByValue(resultSet.getInt(F_TARGET_TYPE)));
        statPositionSummary.setLongPosition(resultSet.getLong(F_LONG_POSITION));
        statPositionSummary.setShortPosition(resultSet.getLong(F_SHORT_POSITION));
        statPositionSummary.setNetPosition(resultSet.getLong(F_NET_POSITION));
        /*
         * 持仓均价的计算公式是：（买入总额 - 卖出总额）/净仓
         * 当净仓为0时，持仓均价无效，不设置值（此时客户端显示该字段为无效状态）
         * */
        if (statPositionSummary.getNetPosition() != 0) {
            statPositionSummary.setPositionAvgPrice(resultSet.getDouble(F_POSITION_AVG_PRICE));
        }
        statPositionSummary.setCreateTimestampMs(resultSet.getLong(F_CREATE_TIMESTAMP_MS));
        statPositionSummary.setLastmodifyTimestampMs(resultSet.getLong(F_LASTMODITY_TIMESTAMP_MS));

        return statPositionSummary;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                    .append(F_TARGET_KEY).append(" VARCHAR(32) NOT NULL DEFAULT '',")
                    .append(F_SUB_ACCOUNT_ID).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_TARGET_TYPE).append(" TINYINT NOT NULL DEFAULT 0,")
                    .append(F_LONG_POSITION).append(" BIGINT NOT NULL DEFAULT 0,")
                    .append(F_SHORT_POSITION).append(" BIGINT NOT NULL DEFAULT 0,")
                    .append(F_NET_POSITION).append(" BIGINT NOT NULL DEFAULT 0,")
                    .append(F_POSITION_AVG_PRICE).append(" DOUBLE DEFAULT 0.00,")
                    .append(F_CREATE_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append(F_LASTMODITY_TIMESTAMP_MS).append(" BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                    .append("PRIMARY KEY(").append(F_TARGET_KEY).append(",").append(F_SUB_ACCOUNT_ID).append(",").append(F_TARGET_TYPE).append(")")
                    .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
}
