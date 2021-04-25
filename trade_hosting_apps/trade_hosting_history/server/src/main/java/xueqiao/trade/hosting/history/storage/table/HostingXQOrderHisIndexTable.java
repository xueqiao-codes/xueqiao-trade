package xueqiao.trade.hosting.history.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.history.storage.util.HostingXQTargetUtil;
import xueqiao.trade.hosting.history.thriftapi.HostingXQOrderHisIndexItem;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOption;
import xueqiao.trade.hosting.history.thriftapi.QueryXQOrderHisIndexItemOrderType;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class HostingXQOrderHisIndexTable extends TableHelper<HostingXQOrderHisIndexItem> implements IDBTable {

    public HostingXQOrderHisIndexTable(Connection conn) {
        super(conn);
    }

    public HostingXQOrderHisIndexItem getOrderHisIndexItem(String orderId) throws SQLException {
        return getOrderHisIndexItem(orderId, false);
    }

    public HostingXQOrderHisIndexItem getOrderHisIndexItem(String orderId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND, "Forder_id=?", orderId);
        return super.getItem(qryBuilder, forUpdate);
    }

    public PageResult<HostingXQOrderHisIndexItem> getOrderHisIndexPage(
            QueryXQOrderHisIndexItemOption qryOption, PageOption pageOption)
            throws SQLException {
        Preconditions.checkNotNull(pageOption);
        Preconditions.checkArgument(pageOption.getPageIndex() >= 0 && pageOption.getPageSize() > 0);

        SqlQueryBuilder qryBuilder = super.prepareSqlQueryBuilder();
        if (qryOption != null) {
            if (qryOption.isSetOrderCreateTimePeriod()) {
                if (qryOption.getOrderCreateTimePeriod().isSetStartTimestampMs()) {
                    qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                            ,"Forder_createtimestampms >= ?"
                            , qryOption.getOrderCreateTimePeriod().getStartTimestampMs());
                }
                if (qryOption.getOrderCreateTimePeriod().isSetEndTimestampMs()) {
                    qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                            , "Forder_createtimestampms <= ?"
                            , qryOption.getOrderCreateTimePeriod().getEndTimestampMs());
                }
            }
            if (qryOption.isSetOrderEndTimePeriod()) {
                if (qryOption.getOrderEndTimePeriod().isSetStartTimestampMs()) {
                    qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                            , "Forder_endtimestampms >= ?"
                            , qryOption.getOrderEndTimePeriod().getStartTimestampMs());
                }
                if (qryOption.getOrderEndTimePeriod().isSetEndTimestampMs()) {
                    qryBuilder.addFieldCondition(SqlQueryBuilder.ConditionType.AND
                            , "Forder_endtimestampms <= ?"
                            , qryOption.getOrderEndTimePeriod().getEndTimestampMs());
                }
            }
            if (qryOption.isSetSubAccountIds() && !qryOption.getSubAccountIds().isEmpty()) {
                qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND
                            , "Fsub_account_id"
                            , qryOption.getSubAccountIds());
            }
            if (qryOption.isSetOrderTargets()) {
                Set<String> targetSymbols = new HashSet<String>();
                for (HostingXQTarget orderTarget : qryOption.getOrderTargets()) {
                    String targetSymbol = HostingXQTargetUtil.generateSymbol(orderTarget);
                    if (StringUtils.isNotEmpty(targetSymbol)) {
                        targetSymbols.add(targetSymbol);
                    }
                }
                if (!targetSymbols.isEmpty()) {
                    qryBuilder.addInFieldCondition(SqlQueryBuilder.ConditionType.AND, "Forder_target", targetSymbols);
                }
            }

            if (qryOption.isSetItemOrderType()) {
                if (qryOption.getItemOrderType() == QueryXQOrderHisIndexItemOrderType.XQ_ORDER_CREATE_TIMESTAMP_ASC) {
                    qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Forder_createtimestampms");
                } else if (qryOption.getItemOrderType() == QueryXQOrderHisIndexItemOrderType.XQ_ORDER_CREATE_TIMESTAMP_DESC) {
                    qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, "Forder_createtimestampms");
                } else if (qryOption.getItemOrderType() == QueryXQOrderHisIndexItemOrderType.XQ_ORDER_END_TIMESTAMP_ASC) {
                    qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Forder_endtimestampms");
                } else {
                    qryBuilder.setOrder(SqlQueryBuilder.OrderType.DESC, "Forder_endtimestampms");
                }
            } else {
                qryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Forder_createtimestampms");
            }
        }

        qryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());

        PageResult<HostingXQOrderHisIndexItem> resultPage = new PageResult<HostingXQOrderHisIndexItem>();
        if (pageOption.isNeedTotalCount()) {
            resultPage.setTotalCount(super.getTotalNum(qryBuilder));
        }
        resultPage.setPageList(super.getItemList(qryBuilder));

        return resultPage;
    }

    public int addOrderHisIndexItem(HostingXQOrderHisIndexItem newItem) throws SQLException {
        Preconditions.checkNotNull(newItem);
        Preconditions.checkArgument(newItem.isSetSubAccountId());
        Preconditions.checkArgument(newItem.isSetSubUserId());
        Preconditions.checkArgument(newItem.isSetOrderTarget()
                && newItem.getOrderTarget().isSetTargetType()
                && StringUtils.isNotEmpty(newItem.getOrderTarget().getTargetKey()));
        Preconditions.checkArgument(StringUtils.isNotEmpty(newItem.getOrderId()));
        Preconditions.checkArgument(newItem.isSetOrderCreateTimestampMs());

        PreparedFields pf = new PreparedFields();
        pf.addLong("Fsub_account_id", newItem.getSubAccountId());
        pf.addInt("Fsub_user_id", newItem.getSubUserId());
        pf.addShort("Forder_type", (short)newItem.getOrderType().getValue());
        pf.addString("Forder_target", HostingXQTargetUtil.generateSymbol(newItem.getOrderTarget()));
        pf.addLong("Forder_createtimestampms", newItem.getOrderCreateTimestampMs());
        if (newItem.isSetOrderEndTimestampMs()) {
            pf.addLong("Forder_endtimestampms", newItem.getOrderEndTimestampMs());
        }
        pf.addString("Forder_id", newItem.getOrderId());

        if (newItem.isSetCreateTimestampMs()) {
            pf.addLong("Fcreate_timestampms", newItem.getCreateTimestampMs());
            pf.addLong("Flastmodify_timestampms", newItem.getLastmodifyTimestampMs());
        } else {
            pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }

        return super.insert(pf);
    }

    public int updateOrderHisIndexItem(HostingXQOrderHisIndexItem updateItem) throws SQLException {
        Preconditions.checkNotNull(updateItem);
        Preconditions.checkArgument(updateItem.isSetOrderId() && StringUtils.isNotEmpty(updateItem.getOrderId()));

        PreparedFields pf = new PreparedFields();
        if (updateItem.isSetOrderEndTimestampMs()) {
            pf.addLong("Forder_endtimestampms", updateItem.getOrderEndTimestampMs());
        }
        if (updateItem.isSetLastmodifyTimestampMs()) {
            pf.addLong("Flastmodify_timestampms", updateItem.getLastmodifyTimestampMs());
        } else {
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }

        return super.update(pf, "Forder_id=?", updateItem.getOrderId());
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Forder_type SMALLINT NOT NULL DEFAULT 0,")
                            .append("Forder_target VARCHAR(40) NOT NULL DEFAULT '',")
                            .append("Forder_createtimestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Forder_endtimestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id),")
                            .append("INDEX(Forder_createtimestampms, Fsub_account_id, Forder_target),")
                            .append("INDEX(Forder_endtimestampms, Fsub_account_id, Forder_target)")
                            .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_order_history";
    }

    @Override
    public HostingXQOrderHisIndexItem fromResultSet(ResultSet rs) throws Exception {
        HostingXQOrderHisIndexItem indexItem = new HostingXQOrderHisIndexItem();
        indexItem.setSubAccountId(rs.getLong("Fsub_account_id"));
        indexItem.setSubUserId(rs.getInt("Fsub_user_id"));
        indexItem.setOrderType(HostingXQOrderType.findByValue(rs.getShort("Forder_type")));
        indexItem.setOrderTarget(HostingXQTargetUtil.generateTarget(rs.getString("Forder_target")));
        indexItem.setOrderCreateTimestampMs(rs.getLong("Forder_createtimestampms"));
        indexItem.setOrderEndTimestampMs(rs.getLong("Forder_endtimestampms"));
        indexItem.setOrderId(rs.getString("Forder_id"));
        indexItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        indexItem.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        return indexItem;
    }
}
