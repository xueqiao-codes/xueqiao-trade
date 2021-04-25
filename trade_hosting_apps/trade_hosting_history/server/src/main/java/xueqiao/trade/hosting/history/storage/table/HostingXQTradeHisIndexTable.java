package xueqiao.trade.hosting.history.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.history.storage.util.HostingXQTargetUtil;
import xueqiao.trade.hosting.history.thriftapi.HostingXQTradeHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQTradeHisIndexItemOrderType;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class HostingXQTradeHisIndexTable extends TableHelper<HostingXQTradeHisIndexItem> implements IDBTable {
    public HostingXQTradeHisIndexTable(Connection conn) {
        super(conn);
    }

    public int addTradeHisIndexItem(HostingXQTradeHisIndexItem item) throws SQLException {
        Preconditions.checkNotNull(item);
        Preconditions.checkArgument(item.isSetSubAccountId());
        Preconditions.checkArgument(item.isSetSubUserId());
        Preconditions.checkArgument(item.isSetTradeTarget()
                && item.getTradeTarget().isSetTargetType()
                && StringUtils.isNotEmpty(item.getTradeTarget().getTargetKey()));
        Preconditions.checkArgument(StringUtils.isNotEmpty(item.getOrderId()));
        Preconditions.checkArgument(item.isSetTradeCreateTimestampMs());
        Preconditions.checkArgument(item.isSetTradeId() && item.getTradeId() > 0);

        PreparedFields pf = new PreparedFields();

        pf.addLong("Fsub_account_id", item.getSubAccountId());
        pf.addInt("Fsub_user_id", item.getSubUserId());
        pf.addString("Ftrade_target", HostingXQTargetUtil.generateSymbol(item.getTradeTarget()));
        pf.addLong("Ftrade_createtimestampms", item.getTradeCreateTimestampMs());
        pf.addString("Forder_id", item.getOrderId());
        pf.addLong("Ftrade_id", item.getTradeId());

        if (item.isSetCreateTimestampMs()) {
            pf.addLong("Fcreate_timestampms", item.getCreateTimestampMs());
            pf.addLong("Flastmodify_timestampms", item.getCreateTimestampMs());
        } else {
            pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }

        return super.insert(pf);
    }

    public HostingXQTradeHisIndexItem getTradeHisIndexItem(String orderId, long tradeId) throws SQLException {
        return getTradeHisIndexItem(orderId, tradeId, false);
    }

    public HostingXQTradeHisIndexItem getTradeHisIndexItem(String orderId, long tradeId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,"Forder_id=?", orderId);
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND,"Ftrade_id=?", tradeId);
        return super.getItem(qryBuilder, forUpdate);
    }

    public PageResult<HostingXQTradeHisIndexItem> getTradeHisIndexItemPage(
            QueryXQTradeHisIndexItemOption qryOption, PageOption pageOption) throws SQLException {
        Preconditions.checkNotNull(pageOption);
        Preconditions.checkArgument(pageOption.getPageIndex() >= 0);
        Preconditions.checkArgument(pageOption.getPageSize() > 0);

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.isSetTradeCreateTimePeriod()) {
                if (qryOption.getTradeCreateTimePeriod().isSetStartTimestampMs()) {
                    qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                            , "Ftrade_createtimestampms >= ?"
                            , qryOption.getTradeCreateTimePeriod().getStartTimestampMs());
                }
                if (qryOption.getTradeCreateTimePeriod().isSetEndTimestampMs()) {
                    qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                            , "Ftrade_createtimestampms <= ?"
                            , qryOption.getTradeCreateTimePeriod().getEndTimestampMs());
                }
            }

            if (qryOption.isSetSubAccountIds() && !qryOption.getSubAccountIds().isEmpty()) {
                qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND
                            ,"Fsub_account_id", qryOption.getSubAccountIds());
            }

            if (qryOption.isSetTradeTargets() && !qryOption.getTradeTargets().isEmpty()) {
                HashSet<String> targetSymbols = new HashSet<String>();
                for (HostingXQTarget xqTarget : qryOption.getTradeTargets()) {
                    String targetSymbol = HostingXQTargetUtil.generateSymbol(xqTarget);
                    if (StringUtils.isNotEmpty(targetSymbol)) {
                        targetSymbols.add(targetSymbol);
                    }
                }
                if (!targetSymbols.isEmpty()) {
                    qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, "Ftrade_target", targetSymbols);
                }
            }

            if (qryOption.isSetItemOrderType()) {
                if (qryOption.getItemOrderType() == QueryXQTradeHisIndexItemOrderType.XQ_TRADE_CREATE_TIMESTAMP_ASC) {
                    qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Ftrade_createtimestampms");
                } else {
                    qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, "Ftrade_createtimestampms");
                }
            } else {
                qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Ftrade_createtimestampms");
            }
        }

        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        PageResult<HostingXQTradeHisIndexItem> resultPage = new PageResult<HostingXQTradeHisIndexItem>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(qryBuilder));
        }
        resultPage.setPageList(super.getItemList(qryBuilder));
        return resultPage;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Ftrade_target VARCHAR(40) NOT NULL DEFAULT '',")
                            .append("Ftrade_createtimestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Ftrade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id, Ftrade_id),")
                            .append("INDEX(Ftrade_createtimestampms, Fsub_account_id, Ftrade_target)")
                            .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_trade_history";
    }

    @Override
    public HostingXQTradeHisIndexItem fromResultSet(ResultSet rs) throws Exception {
        HostingXQTradeHisIndexItem indexItem = new HostingXQTradeHisIndexItem();
        indexItem.setSubAccountId(rs.getLong("Fsub_account_id"));
        indexItem.setSubUserId(rs.getInt("Fsub_user_id"));
        indexItem.setTradeTarget(HostingXQTargetUtil.generateTarget(rs.getString("Ftrade_target")));
        indexItem.setTradeCreateTimestampMs(rs.getLong("Ftrade_createtimestampms"));
        indexItem.setOrderId(rs.getString("Forder_id"));
        indexItem.setTradeId(rs.getLong("Ftrade_id"));
        indexItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        indexItem.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        return indexItem;
    }
}
