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

import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

public class XQTradeRelatedTable extends TableHelper<XQTradeRelatedItem> implements IDBTable {

    public XQTradeRelatedTable(Connection conn) {
        super(conn);
    }
    
    public List<XQTradeRelatedItem> getItemsByOrderId(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        return super.getItemList(queryBuilder);
    }

    public List<XQTradeRelatedItem> getItemsByTradeId(String orderId, long tradeId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        Preconditions.checkArgument(tradeId > 0);

        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        queryBuilder.addFieldCondition(ConditionType.AND, "Ftrade_id=?", tradeId);
        return super.getItemList(queryBuilder);
    }
    
    public int addRelatedItem(XQTradeRelatedItem newRelatedItem) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(newRelatedItem.getOrderId()));
        Preconditions.checkArgument(newRelatedItem.getTradeId() > 0);
        Preconditions.checkArgument(newRelatedItem.getExecTradeLegId() > 0);
        Preconditions.checkArgument(newRelatedItem.getSledContractId() > 0);
        Preconditions.checkArgument(newRelatedItem.getExecOrderId() > 0);
        Preconditions.checkArgument(newRelatedItem.getExecTradeId() > 0);
        Preconditions.checkArgument(newRelatedItem.getExecTradeLegId() > 0);
        Preconditions.checkNotNull(newRelatedItem.getExecTradeLegDirection());
        Preconditions.checkArgument(newRelatedItem.getExecTradeLegVolume() >= 0);
        Preconditions.checkArgument(
                PriceUtils.isAppropriatePrice(newRelatedItem.getExecTradeLegPrice()));
        Preconditions.checkArgument(
                newRelatedItem.getRelatedTradeVolume() >= 0
                && newRelatedItem.getRelatedTradeVolume() <= newRelatedItem.getExecTradeLegVolume());
        
        PreparedFields pf = new PreparedFields();
        pf.addString("Forder_id", newRelatedItem.getOrderId());
        pf.addLong("Ftrade_id", newRelatedItem.getTradeId());
        pf.addLong("Fsled_contract_id", newRelatedItem.getSledContractId());
        pf.addLong("Fexec_order_id", newRelatedItem.getExecOrderId());
        pf.addLong("Fexec_trade_id", newRelatedItem.getExecTradeId());
        pf.addLong("Fexec_trade_leg_id", newRelatedItem.getExecTradeLegId());
        pf.addByte("Fexec_trade_leg_direction", (byte)newRelatedItem.getExecTradeLegDirection().getValue());
        pf.addInt("Fexec_trade_leg_volume", newRelatedItem.getExecTradeLegVolume());
        pf.addDouble("Fexec_trade_leg_price", newRelatedItem.getExecTradeLegPrice());
        pf.addInt("Frelated_trade_volume", newRelatedItem.getRelatedTradeVolume());
        
        if (newRelatedItem.getCreateTimestampMs() > 0) {
            pf.addLong("Fcreate_timestampms", newRelatedItem.getCreateTimestampMs());
        }
        
        return super.insert(pf);
    }

    @Override
    public XQTradeRelatedItem fromResultSet(ResultSet rs) throws Exception {
        XQTradeRelatedItem relatedItem = new XQTradeRelatedItem();
        relatedItem.setOrderId(rs.getString("Forder_id"));
        relatedItem.setTradeId(rs.getLong("Ftrade_id"));
        relatedItem.setSledContractId(rs.getLong("Fsled_contract_id"));
        relatedItem.setExecOrderId(rs.getLong("Fexec_order_id"));
        relatedItem.setExecTradeId(rs.getLong("Fexec_trade_id"));
        relatedItem.setExecTradeLegId(rs.getLong("Fexec_trade_leg_id"));
        relatedItem.setExecTradeLegDirection(
                HostingExecTradeDirection.findByValue(rs.getByte("Fexec_trade_leg_direction")));
        relatedItem.setExecTradeLegVolume(rs.getInt("Fexec_trade_leg_volume"));
        relatedItem.setExecTradeLegPrice(rs.getDouble("Fexec_trade_leg_price"));
        relatedItem.setRelatedTradeVolume(rs.getInt("Frelated_trade_volume"));
        relatedItem.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        return relatedItem;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Ftrade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_trade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_trade_leg_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fexec_trade_leg_direction TINYINT NOT NULL DEFAULT 0,")
                            .append("Fexec_trade_leg_volume INT NOT NULL DEFAULT 0,")
                            .append("Fexec_trade_leg_price DOUBLE DEFAULT 0.00,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Forder_id, Ftrade_id, Fexec_trade_leg_id)")
                            .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        } 
        if (2 == targetVersion) {
            if (operator.colunmExist(getTableName(), "Frelated_trade_volume")) {
                return ;
            }
            
            StringBuilder alterSqlBuilder = new StringBuilder(128);
            alterSqlBuilder.append("alter table ").append(getTableName())
                           .append(" ADD COLUMN Frelated_trade_volume INT NOT NULL DEFAULT 0;");
            operator.execute(alterSqlBuilder.toString());
            return ;
        }
        
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_trade_related";
    }
    
}
