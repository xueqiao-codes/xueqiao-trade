package xueqiao.trade.hosting.arbitrage.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.HashAlgorithms;
import org.soldier.base.logger.AppLog;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class XQTradeTable extends TableHelper<HostingXQTrade> implements IDBTable {

    public XQTradeTable(Connection conn) {
        super(conn);
    }
    
    public Map<String, List<HostingXQTrade>> batchGetOrderTrades(Set<String> orderIds) throws SQLException {
        Map<String, List<HostingXQTrade>> resultMap = new HashMap<String, List<HostingXQTrade>>();
        if (orderIds == null || orderIds.isEmpty()) {
            return resultMap;
        }
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addInFieldCondition(ConditionType.AND, "Forder_id", orderIds);
        List<HostingXQTrade> tradeLists = super.getItemList(queryBuilder);
        for (HostingXQTrade trade : tradeLists) {
            List<HostingXQTrade> orderTradeList = resultMap.get(trade.getOrderId());
            if (orderTradeList == null) {
                orderTradeList = new ArrayList<HostingXQTrade>();
                resultMap.put(trade.getOrderId(), orderTradeList);
            }
            orderTradeList.add(trade);
        }
        return resultMap;
    }

    public Map<Long, HostingXQTrade> batchFilterTrades(Set<String> orderIds, Set<Long> tradeIds) throws SQLException {
        Map<Long, HostingXQTrade> resultMap = new HashMap<Long, HostingXQTrade>();
        if (orderIds == null || orderIds.isEmpty() || tradeIds == null || tradeIds.isEmpty()) {
            return resultMap;
        }

        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addInFieldCondition(ConditionType.AND, "Forder_id", orderIds);
        queryBuilder.addInFieldCondition(ConditionType.AND, "Ftrade_id", tradeIds);
        List<HostingXQTrade> tradeLists = super.getItemList(queryBuilder);
        for (HostingXQTrade trade : tradeLists) {
            resultMap.put(trade.getTradeId(), trade);
        }

        return resultMap;
    }
    
    public List<HostingXQTrade> getOrderTrades(String orderId) throws SQLException {
        Preconditions.checkArgument(StringUtils.isNotEmpty(orderId));
        
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Forder_id=?", orderId);
        return super.getItemList(queryBuilder);
    }

    public int addTrade(HostingXQTrade trade) throws SQLException {
        Preconditions.checkNotNull(trade);
        Preconditions.checkArgument(StringUtils.isNotEmpty(trade.getOrderId()));
        Preconditions.checkArgument(trade.getTradeId() > 0);
        
        PreparedFields pf = new PreparedFields();
        pf.addLong("Ftrade_id", trade.getTradeId());
        pf.addString("Forder_id", trade.getOrderId());
        
        if (trade.isSetTradeTarget()) {
            if (trade.getTradeTarget().isSetTargetType()) {
                pf.addShort("Ftarget_type", (short)trade.getTradeTarget().getTargetType().getValue());
            }
            if (trade.getTradeTarget().isSetTargetKey()) {
                pf.addString("Ftarget_key", trade.getTradeTarget().getTargetKey());
            }
        }
        if (trade.isSetTradeVolume()) {
            pf.addInt("Ftrade_volume", trade.getTradeVolume());
        }
        if (trade.isSetTradePrice() && PriceUtils.isAppropriatePrice(trade.getTradePrice())) {
            pf.addByte("Ftrade_price_isset", (byte)1);
            pf.addDouble("Ftrade_price", trade.getTradePrice());
        }
        
        if (trade.isSetSubUserId()) {
            pf.addInt("Fsub_user_id", trade.getSubUserId());
        }
        if (trade.isSetSubAccountId()) {
            pf.addLong("Fsub_account_id", trade.getSubAccountId());
        }
        
        if (trade.isSetTradeDiretion()) {
            pf.addByte("Ftrade_direction", (byte)trade.getTradeDiretion().getValue());
        }
        
        if (trade.isSetCreateTimestampMs()) {
            pf.addLong("Fcreate_timestampms", trade.getCreateTimestampMs());
            pf.addLong("Flastmodify_timestampms", trade.getLastmodifyTimestampMs());
        } else {
            pf.addLong("Fcreate_timestampms", System.currentTimeMillis());
            pf.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }
        
        if (trade.isSetSourceOrderTarget()) {
            if (trade.getSourceOrderTarget().isSetTargetType()) {
                pf.addShort("Forder_target_type"
                        , (short)trade.getSourceOrderTarget().getTargetType().getValue());
            }
            if (trade.getSourceOrderTarget().isSetTargetKey()) {
                pf.addString("Forder_target_key", trade.getSourceOrderTarget().getTargetKey());
            }
        }
        
        return super.insert(pf);
    }

    @Override
    public HostingXQTrade fromResultSet(ResultSet rs) throws Exception {
        HostingXQTrade trade = new HostingXQTrade();
        trade.setOrderId(rs.getString("Forder_id"));
        trade.setTradeId(rs.getLong("Ftrade_id"));
        
        HostingXQTarget target = new HostingXQTarget();
        target.setTargetType(HostingXQTargetType.findByValue(rs.getShort("Ftarget_type")));
        target.setTargetKey(rs.getString("Ftarget_key"));
        trade.setTradeTarget(target);
        
        trade.setTradeVolume(rs.getInt("Ftrade_volume"));
        if (rs.getByte("Ftrade_price_isset") != 0) {
            trade.setTradePrice(rs.getDouble("Ftrade_price"));
        }
        
        HostingXQTarget sourceOrderTarget = new HostingXQTarget();
        sourceOrderTarget.setTargetType(HostingXQTargetType.findByValue(rs.getShort("Forder_target_type")));
        sourceOrderTarget.setTargetKey(rs.getString("Forder_target_key"));
        trade.setSourceOrderTarget(sourceOrderTarget);
        
        
        trade.setSubUserId(rs.getInt("Fsub_user_id"));
        trade.setSubAccountId(rs.getLong("Fsub_account_id"));
        trade.setTradeDiretion(HostingXQTradeDirection.findByValue(rs.getByte("Ftrade_direction")));
        trade.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        trade.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        
        return trade;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder createSqlBuilder = new StringBuilder(128);
            createSqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                            .append("Ftrade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Forder_id VARCHAR(64) NOT NULL DEFAULT '',")
                            .append("Ftarget_type SMALLINT NOT NULL DEFAULT 0,")
                            .append("Ftarget_key VARCHAR(32) NOT NULL DEFAULT '',")
                            .append("Ftrade_volume INT NOT NULL DEFAULT 0,")
                            .append("Ftrade_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Ftrade_price DOUBLE DEFAULT 0.00,")
                            .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Ftrade_direction TINYINT NOT NULL DEFAULT 0,")
                            .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                            .append("PRIMARY KEY(Ftrade_id),")
                            .append("INDEX(Forder_id)")
                            .append(")ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(createSqlBuilder.toString());
            return ;
        }
        if (4 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Forder_target_type")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("alter table ").append(getTableName())
                               .append(" ADD COLUMN Forder_target_type SMALLINT NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
            }
            if (!operator.colunmExist(getTableName(), "Forder_target_key")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("alter table ").append(getTableName())
                               .append(" ADD COLUMN Forder_target_key VARCHAR(32) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (6 == targetVersion) {
            if (operator.indexExist(getTableName(), "Forder_id")) {
                operator.execute("DROP INDEX Forder_id ON " + getTableName() + ";");
            }
            operator.execute("ALTER TABLE " + getTableName() + " DROP PRIMARY KEY;");
            operator.execute("ALTER TABLE " + getTableName() + " ADD PRIMARY KEY(Forder_id, Ftrade_id);");
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "txq_trade";
    }
    
}
