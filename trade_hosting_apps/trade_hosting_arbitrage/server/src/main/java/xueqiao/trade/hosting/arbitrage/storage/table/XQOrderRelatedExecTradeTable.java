package xueqiao.trade.hosting.arbitrage.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRelatedExecTradeItem;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class XQOrderRelatedExecTradeTable extends TableHelper<XQOrderRelatedExecTradeItem> implements IDBTable {
    public XQOrderRelatedExecTradeTable(Connection conn) {
        super(conn);
    }
    
    public XQOrderRelatedExecTradeItem getItem(String orderId, long execTradeId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        Preconditions.checkArgument(execTradeId > 0);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_trade_id=?", execTradeId);
        return super.getItem(queryBuilder);
    }
    
    public List<XQOrderRelatedExecTradeItem> getItems(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        return super.getItemList(queryBuilder);
    }
    
    public List<XQOrderRelatedExecTradeItem> getItems(String orderId, long sledContractId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        Preconditions.checkArgument(sledContractId > 0);
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Fsled_contract_id=?", sledContractId);
        return super.getItemList(queryBuilder);
    }
    
    public int addItem(XQOrderRelatedExecTradeItem newItem) throws SQLException {
        Preconditions.checkNotNull(newItem);
        Preconditions.checkArgument(StringUtils.isNotEmpty(newItem.getOrderId()));
        Preconditions.checkArgument(newItem.getExecTradeId() > 0);
        Preconditions.checkArgument(newItem.getRelatedExecTrade() != null);
        
        PreparedFields pf = new PreparedFields();
        pf.addString("Forder_id", newItem.getOrderId());
        pf.addLong("Fexec_trade_id", newItem.getExecTradeId());
        pf.addLong("Fexec_order_id", newItem.getExecOrderId());
        pf.addLong("Fsled_contract_id", newItem.getSledContractId());
        try {
            pf.addString("Fexec_trade", ThriftExtJsonUtils.toJsonTBase(newItem.getRelatedExecTrade()));
        } catch (Throwable e) {
            throw new SQLException(e.getMessage(), e);
        }
        if (newItem.getCreateTimestampMs() > 0) {
            pf.addLong("Fcreate_timestampms", newItem.getCreateTimestampMs());
        }
        
        return super.insert(pf);
    }
    
    public int delItem(String orderId, long execTradeId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        Preconditions.checkArgument(execTradeId > 0);
        return super.delete("Forder_id=? AND Fexec_trade_id=?", orderId, execTradeId);
    }
    
    public int delItems(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        return super.delete("Forder_id=?", orderId);
    }

    @Override
    public XQOrderRelatedExecTradeItem fromResultSet(ResultSet rs) throws Exception {
        XQOrderRelatedExecTradeItem relatedItem = new XQOrderRelatedExecTradeItem();
        relatedItem.setOrderId(rs.getString("Forder_id"));
        relatedItem.setExecTradeId(rs.getLong("Fexec_trade_id"));
        relatedItem.setExecOrderId(rs.getLong("Fexec_order_id"));
        relatedItem.setSledContractId(rs.getLong("Fsled_contract_id"));
        relatedItem.setRelatedExecTrade(ThriftExtJsonUtils.fromJsonTBase(
                rs.getString("Fexec_trade"), HostingExecTrade.class));
        relatedItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        return relatedItem;
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_order_exec_trade";
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Fexec_trade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_trade VARCHAR(1024) NOT NULL DEFAULT '',")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id, Fexec_trade_id)")
                            .append(") ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
        }
    }
    
}
