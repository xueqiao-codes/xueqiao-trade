package xueqiao.trade.hosting.arbitrage.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderState;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderStateValue;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQSuspendReason;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeSummary;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class XQOrderTable extends TableHelper<HostingXQOrder> implements IDBTable {

    public XQOrderTable(Connection conn) {
        super(conn);
    }
    
    public HostingXQOrder getOrder(String orderId) throws SQLException {
        return getOrder(orderId, false);
    }
    
    public HostingXQOrder getOrder(String orderId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        return super.getItem(queryBuilder, forUpdate);
    }
    
    public List<HostingXQOrder> getOrdersList(Set<String> orderIds) throws SQLException {
        Preconditions.checkNotNull(orderIds);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addInFieldCondition(ConditionType.AND, "Forder_id", orderIds);
        
        return super.getItemList(queryBuilder);
    }
    
    public Map<String, HostingXQOrder> getOrdersMap(Set<String> orderIds) throws SQLException {
        Preconditions.checkNotNull(orderIds);
        
        Map<String, HostingXQOrder> resultMap = new HashMap<String, HostingXQOrder>(orderIds.size() + 1);
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addInFieldCondition(ConditionType.AND, "Forder_id", orderIds);
        List<HostingXQOrder> orderList = super.getItemList(queryBuilder);
        for (HostingXQOrder order : orderList) {
            resultMap.put(order.getOrderId(), order);
        }
        return resultMap;
    }
    
    public int addOrder(HostingXQOrder newOrder) throws SQLException {
        Preconditions.checkNotNull(newOrder);
        Preconditions.checkArgument(StringUtils.isNotEmpty(newOrder.getOrderId()));
        
        PreparedFields pf = new PreparedFields();
        pf.addString("Forder_id", newOrder.getOrderId());
        order2PreparedFields(newOrder, pf);
        
        if (newOrder.isSetVersion()) {
            pf.addInt("Fversion", newOrder.getVersion());
        } else {
            pf.addInt("Fversion", 1);
        }
        
        if (newOrder.isSetCreateTimestampMs()) {
            pf.addLong("Fcreate_timestampms", newOrder.getCreateTimestampMs());
            pf.addLong("Flastmodify_timestampms", newOrder.getCreateTimestampMs());
        } else {
            pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }
        
        return super.insert(pf);
    }
    
    public int updateOrder(HostingXQOrder updateOrder) throws SQLException {
        Preconditions.checkNotNull(updateOrder);
        Preconditions.checkArgument(StringUtils.isNotEmpty(updateOrder.getOrderId()));
        
        PreparedFields pf = new PreparedFields();
        order2PreparedFields(updateOrder, pf);
        if (updateOrder.isSetVersion()) {
            pf.addInt("Fversion", updateOrder.getVersion());
        } else {
            pf.addFieldExpression("Fversion=Fversion+1");
        }
        pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        
        return super.update(pf, "Forder_id=?", updateOrder.getOrderId());
    }
    
    public int delOrder(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        return super.delete("Forder_id=?", orderId);
    }
    
    private void order2PreparedFields(HostingXQOrder order, PreparedFields pf) throws SQLException {
        if (order.isSetSubUserId()) {
            pf.addInt("Fsub_user_id", order.getSubUserId());
        }
        if (order.isSetSubAccountId()) {
            pf.addLong("Fsub_account_id", order.getSubAccountId());
        }
        if (order.isSetOrderType()) {
            pf.addShort("Ftype", (short)order.getOrderType().getValue());
        }
        if (order.isSetOrderTarget()) {
            if (order.getOrderTarget().isSetTargetType()) {
                pf.addShort("Ftarget_type", (short)order.getOrderTarget().getTargetType().getValue());
            }
            if (order.getOrderTarget().isSetTargetKey()) {
                pf.addString("Ftarget_key", order.getOrderTarget().getTargetKey());
            }
        }
        try {
            if (order.isSetOrderDetail()) {
                pf.addString("Fdetail", ThriftExtJsonUtils.toJsonTBase(order.getOrderDetail()));
            }
            if (order.isSetOrderTradeSummary()) {
                pf.addString("Ftrade_summary", ThriftExtJsonUtils.toJsonTBase(order.getOrderTradeSummary()));
            }
        } catch (Throwable e) {
            throw new SQLException(e.getMessage(), e);
        }
        
        if (order.isSetOrderState()) {
            HostingXQOrderState orderState = order.getOrderState();
            if (orderState.isSetStateValue()) {
                pf.addShort("Fstate_value", (short)orderState.getStateValue().getValue());
            }
            if (orderState.isSetStateTimestampMs()) {
                pf.addLong("Fstate_timestampms", orderState.getStateTimestampMs());
            }
            if (orderState.isSetSuspendReason()) {
                pf.addShort("Fsuspend_reason", (short)orderState.getSuspendReason().getValue());
            }
            if (orderState.isSetSuspendedErrorCode()) {
                pf.addInt("Fsuspend_error_code", orderState.getSuspendedErrorCode());
            }
            if (orderState.isSetCancelledErrorCode()) {
                pf.addInt("Fcancel_error_code", orderState.getCancelledErrorCode());
            }
            if (orderState.isSetExecUsefulMsg()) {
                pf.addString("Fexec_useful_msg", orderState.getExecUsefulMsg());
            }
            if (orderState.isSetStateMsg()) {
                pf.addString("Fstate_msg", orderState.getStateMsg());
            }
            if (orderState.isSetEffectIndexCleaned()) {
                if (orderState.isEffectIndexCleaned()) {
                    pf.addByte("Feffect_index_cleaned", (byte)1);
                } else {
                    pf.addByte("Feffect_index_cleaned", (byte)0);
                }
            }
            if (orderState.isSetResumeMode()) {
                pf.addShort("Fresume_mode", (short)orderState.getResumeMode().getValue());
            }
        }
        
        if (order.isSetSourceOrderId()) {
            pf.addString("Fsource_order_id", order.getSourceOrderId());
        }
        if (order.isSetActionOrderId()) {
            pf.addString("Faction_order_id", order.getActionOrderId());
        }

        if (order.isSetGfdOrderEndTimestampMs() && order.getGfdOrderEndTimestampMs() > 0) {
            pf.addLong("Fgfd_order_end_timestampms", order.getGfdOrderEndTimestampMs());
        }
        
    }

    @Override
    public HostingXQOrder fromResultSet(ResultSet rs) throws Exception {
        HostingXQOrder order = new HostingXQOrder();
        order.setOrderId(rs.getString("Forder_id"));
        order.setSubUserId(rs.getInt("Fsub_user_id"));
        order.setSubAccountId(rs.getLong("Fsub_account_id"));
        order.setOrderType(HostingXQOrderType.findByValue(rs.getShort("Ftype")));
        
        HostingXQTarget target = new HostingXQTarget();
        target.setTargetType(HostingXQTargetType.findByValue(rs.getShort("Ftarget_type")));
        target.setTargetKey(rs.getString("Ftarget_key"));
        order.setOrderTarget(target);
        
        order.setOrderDetail(
                ThriftExtJsonUtils.fromJsonTBase(rs.getString("Fdetail"), HostingXQOrderDetail.class));
        
        HostingXQOrderState state = new HostingXQOrderState();
        state.setStateValue(HostingXQOrderStateValue.findByValue(rs.getShort("Fstate_value")));
        state.setStateTimestampMs(rs.getLong("Fstate_timestampms"));
        state.setSuspendReason(HostingXQSuspendReason.findByValue(rs.getShort("Fsuspend_reason")));
        state.setSuspendedErrorCode(rs.getInt("Fsuspend_error_code"));
        state.setCancelledErrorCode(rs.getInt("Fcancel_error_code"));
        state.setExecUsefulMsg(rs.getString("Fexec_useful_msg"));
        state.setStateMsg(rs.getString("Fstate_msg"));
        state.setEffectIndexCleaned(rs.getByte("Feffect_index_cleaned") != 0);
        state.setResumeMode(HostingXQOrderResumeMode.findByValue(rs.getShort("Fresume_mode")));
        order.setOrderState(state);
        
        order.setOrderTradeSummary(
                ThriftExtJsonUtils.fromJsonTBase(rs.getString("Ftrade_summary"), HostingXQTradeSummary.class));
        order.setVersion(rs.getInt("Fversion"));
        
        order.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        order.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        order.setSourceOrderId(rs.getString("Fsource_order_id"));
        order.setActionOrderId(rs.getString("Faction_order_id"));

        order.setGfdOrderEndTimestampMs(rs.getLong("Fgfd_order_end_timestampms"));
        return order;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Ftype SMALLINT NOT NULL DEFAULT 0,")
                            .append("Ftarget_type SMALLINT NOT NULL DEFAULT 0,")
                            .append("Ftarget_key VARCHAR(32) NOT NULL DEFAULT '',")
                            .append("Fdetail VARCHAR(2048) NOT NULL DEFAULT '',")
                            .append("Fstate_value SMALLINT NOT NULL DEFAULT 0,")
                            .append("Fstate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsuspend_reason SMALLINT NOT NULL DEFAULT 0,")
                            .append("Fsuspend_error_code INT NOT NULL DEFAULT 0,")
                            .append("Fcancel_error_code INT NOT NULL DEFAULT 0,")
                            .append("Fexec_useful_msg VARCHAR(128) NOT NULL DEFAULT '',")
                            .append("Fstate_msg VARCHAR(256) NOT NULL DEFAULT '',")
                            .append("Ftrade_summary VARCHAR(2048) NOT NULL DEFAULT '',")
                            .append("Fversion INT NOT NULL DEFAULT 0,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id)) ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
        if (3 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Fsource_order_id")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                               .append(" ADD COLUMN Fsource_order_id VARCHAR(64) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
            }
            if (!operator.colunmExist(getTableName(), "Faction_order_id")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                               .append(" ADD COLUMN Faction_order_id VARCHAR(64) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (5 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Feffect_index_cleaned")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                               .append(" ADD COLUMN Feffect_index_cleaned TINYINT NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (7 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Fresume_mode")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                               .append(" ADD COLUMN Fresume_mode SMALLINT NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (10 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Fgfd_order_end_timestampms")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Fgfd_order_end_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_order";
    }
    
}
