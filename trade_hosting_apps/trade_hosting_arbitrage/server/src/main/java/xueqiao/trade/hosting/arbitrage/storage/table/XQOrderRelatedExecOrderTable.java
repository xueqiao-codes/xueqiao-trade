package xueqiao.trade.hosting.arbitrage.storage.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRelatedExecOrderItem;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;

public class XQOrderRelatedExecOrderTable extends TableHelper<XQOrderRelatedExecOrderItem> implements IDBTable {

    public XQOrderRelatedExecOrderTable(Connection conn) {
        super(conn);
    }
    
    public List<XQOrderRelatedExecOrderItem> getItems(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        return super.getItemList(queryBuilder);
    }
    
    public List<XQOrderRelatedExecOrderItem> getItemsByContractIdOrderByExecOrderId(String orderId, long sledContractId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        Preconditions.checkArgument(sledContractId > 0);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsled_contract_id=?", sledContractId);
        queryBuilder.setOrder(SqlQueryBuilder.OrderType.ASC, "Fexec_order_id");

        return super.getItemList(queryBuilder);
    }
    
    public XQOrderRelatedExecOrderItem getItemByExecOrderId(String orderId, long execOrderId) throws SQLException {
        return getItemByExecOrderId(orderId, execOrderId, false);
    }
    
    public XQOrderRelatedExecOrderItem getItemByExecOrderId(String orderId, long execOrderId, boolean forUpdate) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_order_id=?", execOrderId);
        return super.getItem(queryBuilder, forUpdate);
    }
    
    public int addItem(XQOrderRelatedExecOrderItem newItem) throws SQLException {
        Preconditions.checkNotNull(newItem);
        Preconditions.checkArgument(StringUtils.isNotEmpty(newItem.getOrderId()));
        Preconditions.checkArgument(newItem.getExecOrderId() > 0);
        
        PreparedFields pf = new PreparedFields();
        pf.addString("Forder_id", newItem.getOrderId());
        pf.addLong("Fexec_order_id", newItem.getExecOrderId());
        item2PreparedFields(newItem, pf);
        
        if (newItem.getCreateTimestampMs() > 0) {
            pf.addLong("Fcreate_timestampms", newItem.getCreateTimestampMs());
            pf.addLong("Flastmodify_timestampms", newItem.getCreateTimestampMs());
        } else {
            pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }
        
        return super.insert(pf);
    }
    
    public int updateItem(XQOrderRelatedExecOrderItem updateItem) throws SQLException {
        Preconditions.checkNotNull(updateItem);
        Preconditions.checkArgument(StringUtils.isNotEmpty(updateItem.getOrderId()));
        Preconditions.checkArgument(updateItem.getExecOrderId() > 0);
        
        PreparedFields pf = new PreparedFields();
        item2PreparedFields(updateItem, pf);
        pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        
        return super.update(pf, "Forder_id=? AND Fexec_order_id=?"
                , updateItem.getOrderId(), updateItem.getExecOrderId());
    }

    public int deleteItem(String orderId, long execOrderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        Preconditions.checkArgument(execOrderId > 0);

        return super.delete("Forder_id=? AND Fexec_order_id=?", orderId, execOrderId);

    }
    
    private void item2PreparedFields(XQOrderRelatedExecOrderItem item, PreparedFields pf) throws SQLException {
        if (item.getSledContractId() > 0) {
            pf.addLong("Fsled_contract_id", item.getSledContractId());
        }
        try {
            if (item.getRelatedExecOrder() != null) {
                pf.addString("Fexec_order", ThriftExtJsonUtils.toJsonTBase(item.getRelatedExecOrder()));
            }
            if (item.getExtraParams() != null) {
                pf.addString("Fextra_params", ThriftExtJsonUtils.mapToJson(item.getExtraParams()));
            }

        } catch (Throwable e) {
            throw new SQLException(e.getMessage(), e);
        }
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_order VARCHAR(2048) NOT NULL DEFAULT '',")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id, Fexec_order_id)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
        if (9 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Fextra_params")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Fextra_params VARCHAR(256) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
            }
        }
    }

    @Override
    public XQOrderRelatedExecOrderItem fromResultSet(ResultSet rs) throws Exception {
        XQOrderRelatedExecOrderItem relatedItem = new XQOrderRelatedExecOrderItem();
        relatedItem.setOrderId(rs.getString("Forder_id"));
        relatedItem.setExecOrderId(rs.getLong("Fexec_order_id"));
        relatedItem.setSledContractId(rs.getLong("Fsled_contract_id"));
        relatedItem.setRelatedExecOrder(
                ThriftExtJsonUtils.fromJsonTBase(rs.getString("Fexec_order"), HostingExecOrder.class));
        relatedItem.setExtraParams(ThriftExtJsonUtils.mapFromJson(rs.getString("Fextra_params")));
        relatedItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        relatedItem.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        return relatedItem;
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_order_exec_order";
    }
    
}
