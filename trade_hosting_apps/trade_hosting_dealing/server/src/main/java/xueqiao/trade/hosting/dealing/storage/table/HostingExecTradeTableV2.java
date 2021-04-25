package xueqiao.trade.hosting.dealing.storage.table;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.base.sql.SqlQueryBuilder.OrderType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.dealing.storage.HostingDealingDBUtils;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryExecTradeOption;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 成交历史列表(和雪橇合约一致), 组合即为组合的成交均价
 * 组合商品成交会具有多条关联的成交腿
 * 普通商品成交则只具有一条成交腿
 * @author wangli
 */
public class HostingExecTradeTableV2 extends TableHelper<HostingExecTrade> implements IDBTable {
    public HostingExecTradeTableV2(Connection conn) {
        super(conn);
    }
    
    private SqlQueryBuilder tradeOption2QueryBuilder(QueryExecTradeOption option) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        if (option == null) {
            return queryBuilder;
        }
        
        if (option.getSubUserId() > 0) {
            queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_user_id=?", option.getSubUserId());
        }
        if (option.getSubAccountId() > 0) {
            queryBuilder.addFieldCondition(ConditionType.AND, "Fsub_account_id=?", option.getSubAccountId());
        }
        if (option.getBatchExecOrderIds() != null && !option.getBatchExecOrderIds().isEmpty()) {
            queryBuilder.addInFieldCondition(ConditionType.AND, "Fexec_order_id", option.getBatchExecOrderIds());
        }
        if (option.getBatchExecTradeIds() != null && !option.getBatchExecTradeIds().isEmpty()) {
            queryBuilder.addInFieldCondition(ConditionType.AND, "Fexec_trade_id", option.getBatchExecTradeIds());
        }
        if (option.getBatchSledContractIds() != null && !option.getBatchSledContractIds().isEmpty()) {
            queryBuilder.addInFieldCondition(ConditionType.AND, "Fsled_contract_id", option.getBatchSledContractIds());
        }
        if (option.getBatchSubAccountIds() != null && !option.getBatchSubAccountIds().isEmpty()) {
            queryBuilder.addInFieldCondition(ConditionType.AND, "Fsub_account_id", option.getBatchSubAccountIds());
        }
        
        return queryBuilder;
    }
    
    public PageResult<HostingExecTrade> getTradePage(QueryExecTradeOption queryOption, PageOption pageOption) throws SQLException {
        SqlQueryBuilder queryBuilder = tradeOption2QueryBuilder(queryOption);
        queryBuilder.setOrder(OrderType.ASC, "Fexec_trade_id");
        queryBuilder.setPage(pageOption.getPageIndex(), pageOption.getPageSize());
        
        PageResult<HostingExecTrade> pageResult = new PageResult<HostingExecTrade>();
        if (pageOption.isNeedTotalCount()) {
            pageResult.setTotalCount(super.getTotalNum(queryBuilder));
        }
        pageResult.setPageList(super.getItemList(queryBuilder));
        
        return pageResult;
    }
    
    public List<HostingExecTrade> getTradeList(long execOrderId) throws SQLException {
        return getTradeList(execOrderId, false);
    }
    
    public List<HostingExecTrade> getTradeList(long execOrderId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_order_id=?", execOrderId);
        return super.getItemList(queryBuilder, forUpdate);
    }
    
    public HostingExecTrade getTrade(long execTradeId) throws SQLException {
        return getTrade(execTradeId, false);
    }
    
    public HostingExecTrade getTrade(long execTradeId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_trade_id=?", execTradeId);
        return super.getItem(queryBuilder, forUpdate);
    }
    
    public int addTrade(HostingExecTrade trade) throws SQLException {
        Preconditions.checkNotNull(trade);
        Preconditions.checkArgument(trade.isSetExecTradeId() && trade.getExecTradeId() > 0);
        
        PreparedFields fields = new PreparedFields();
        fields.addLong("Fexec_trade_id", trade.getExecTradeId());
        
        try {
            trade2PreparedFields(trade, fields);
        } catch (Throwable e) {
            throw new SQLException(e.getMessage(), e);
        }
        
        if (trade.isSetCreateTimestampMs()) {
            fields.addLong("Fcreate_timestampms", trade.getCreateTimestampMs());
            fields.addLong("Flastmodify_timestampms", trade.getCreateTimestampMs());
        } else {
            fields.addLong("Fcreate_timestampms", System.currentTimeMillis());
            fields.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }
        
        return super.insert(fields);
    }
    
    public int updateTrade(HostingExecTrade trade) throws SQLException {
        Preconditions.checkNotNull(trade);
        Preconditions.checkArgument(trade.isSetExecTradeId() && trade.getExecTradeId() > 0);
        
        PreparedFields fields = new PreparedFields();
        try {
            trade2PreparedFields(trade, fields);
        } catch (TException e) {
            throw new SQLException(e.getMessage(), e);
        }
        
        fields.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        
        return super.update(fields, "Fexec_trade_id=?", trade.getExecTradeId());
    }
    
    public int deleteTradeByTradeId(long execTradeId) throws SQLException {
        Preconditions.checkArgument(execTradeId > 0);
        return super.delete("Fexec_trade_id=?", execTradeId);
    }
    
    public int deleteTradesByOrderId(long execOrderId) throws SQLException {
        Preconditions.checkArgument(execOrderId > 0);
        return super.delete("Fexec_order_id=?", execOrderId);
    }
    
    private void trade2PreparedFields(HostingExecTrade trade, PreparedFields fields) throws TException {
        if (trade.isSetExecOrderId()) {
            fields.addLong("Fexec_order_id", trade.getExecOrderId());
        }
        
        if (trade.isSetSubUserId()) {
            fields.addInt("Fsub_user_id", trade.getSubUserId());
        }
        
        if (trade.isSetSubAccountId()) {
            fields.addLong("Fsub_account_id", trade.getSubAccountId());
        }
        
        if (trade.isSetRelatedTradeLegIds() && trade.getRelatedTradeLegIds().size() == 1) {
            fields.addString("Frelated_trade_leg_ids", relatedTradeLegIds2Str(trade.getRelatedTradeLegIds()));
        }
        
        if (trade.isSetContractSummary()) {
            HostingDealingDBUtils.contractSummary2PrepraredFields(trade.getContractSummary(), fields, false);
        }
        
        if (trade.isSetAccountSummary()) {
            if (trade.getAccountSummary().isSetTradeAccountId()) {
                fields.addLong("Ftrade_account_id", trade.getAccountSummary().getTradeAccountId());
            }
            if (trade.getAccountSummary().isSetBrokerId()) {
                fields.addLong("Fbroker_id", trade.getAccountSummary().getBrokerId());
            }
            if (trade.getAccountSummary().isSetTechPlatform()) {
                fields.addByte("Ftech_platform", (byte)trade.getAccountSummary().getTechPlatform().getValue());
            }
        }
        
        if (trade.isSetTradePrice()) {
            if (PriceUtils.isAppropriatePrice(trade.getTradePrice())) {
                fields.addByte("Ftrade_price_isset", (byte)1);
                fields.addDouble("Ftrade_price", trade.getTradePrice());
            } else {
                fields.addByte("Ftrade_price_isset", (byte)0);
            }
        }
        if (trade.isSetTradeVolume()) {
            fields.addInt("Ftrade_volume", trade.getTradeVolume());
        }
//        if (trade.isSetRelatedTradeLegPrices()) {
//            fields.addString("Frelated_trade_leg_prices"
//                    , relatedTradeLegPrices2String(trade.getRelatedTradeLegPrices()));
//        }
        if (trade.isSetOrderTradeDirection()) {
            fields.addByte("Ftrade_direction", (byte)trade.getOrderTradeDirection().getValue());
        }
//        if (trade.isSetRelatedTradeLegTradeDirections()) {
//        	fields.addString("Frelated_trade_leg_tds", relatedTradeLegTradeDirections2String(trade.getRelatedTradeLegTradeDirections()));
//        }
//        if (trade.isSetRelatedTradeLegContractSummaries()) {
//            fields.addString("Frelated_trade_leg_contracts"
//                    , ThriftExtJsonUtils.listToJsonTBase(trade.getRelatedTradeLegContractSummaries()));
//        }
//        if (trade.isSetRelatedTradeLegVolumes()) {
//            fields.addString("Frelated_trade_leg_volumes"
//                    , relatedTradeLegVolumes2String(trade.getRelatedTradeLegVolumes()));
//        }
        if (trade.isSetRelatedTradeLegCount() && trade.getRelatedTradeLegCount() > 0) {
            fields.addInt("Frelated_trade_leg_count", trade.getRelatedTradeLegCount());
        }
    }

    @Override
    public HostingExecTrade fromResultSet(ResultSet rs) throws Exception {
        HostingExecTrade execTrade = new HostingExecTrade();
        execTrade.setExecTradeId(rs.getLong("Fexec_trade_id"));
        execTrade.setExecOrderId(rs.getLong("Fexec_order_id"));
        execTrade.setSubUserId(rs.getInt("Fsub_user_id"));
        execTrade.setSubAccountId(rs.getLong("Fsub_account_id"));
        execTrade.setRelatedTradeLegIds(str2RelatedTradeLegs(rs.getString("Frelated_trade_leg_ids")));
        execTrade.setOrderTradeDirection(HostingExecOrderTradeDirection.findByValue(rs.getByte("Ftrade_direction")));
        
        execTrade.setContractSummary(HostingDealingDBUtils.contractSummaryFromRs(rs, false));
        execTrade.setAccountSummary(HostingDealingDBUtils.accountSummaryFromRs(rs));
        
        execTrade.setTradeVolume(rs.getInt("Ftrade_volume"));
        if (rs.getByte("Ftrade_price_isset") != 0) {
            execTrade.setTradePrice(rs.getDouble("Ftrade_price"));
        }
        execTrade.setRelatedTradeLegPrices(
                str2RelatedTradeLegPrices(rs.getString("Frelated_trade_leg_prices")));
        
        execTrade.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        execTrade.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        
        execTrade.setRelatedTradeLegTradeDirections(
        		str2RelatedTradeLegTradeDirections(rs.getString("Frelated_trade_leg_tds")));
        execTrade.setRelatedTradeLegContractSummaries(
                ThriftExtJsonUtils.listFromJsonTBase(rs.getString("Frelated_trade_leg_contracts")
                        , HostingExecOrderLegContractSummary.class));
        execTrade.setRelatedTradeLegVolumes(str2RelatedTradeLegVolumes(rs.getString("Frelated_trade_leg_volumes")));
        execTrade.setRelatedTradeLegCount(rs.getInt("Frelated_trade_leg_count"));

        return execTrade;
    }
    
    private String relatedTradeLegIds2Str(List<Long> tradeLegIds) {
        return StringUtils.join(tradeLegIds, ',');
    }
    
    private List<Long> str2RelatedTradeLegs(String content) throws SQLException {
        List<Long> resultList = new ArrayList<>();
        String[] relatedIdStrs = StringUtils.split(content, ',');
        if (relatedIdStrs != null) {
            for (String relatedIdStr : relatedIdStrs) {
                try {
                    resultList.add(Long.parseLong(relatedIdStr));
                } catch (NumberFormatException e) {
                    throw new SQLException(e.getMessage(), e);
                }
            }
        }
        return resultList;
    }
    
    private String relatedTradeLegPrices2String(List<Double> tradeLegPrices) {
        return StringUtils.join(tradeLegPrices, ",");
    }
    
    private List<Double> str2RelatedTradeLegPrices(String content) throws SQLException {
        List<Double> resultList = new ArrayList<>();
        String[] relatedPricesStrs = StringUtils.split(content, ',');
        if (relatedPricesStrs != null) {
            for (String relatedPriceStr : relatedPricesStrs) {
                try {
                    resultList.add(Double.parseDouble(relatedPriceStr));
                } catch (NumberFormatException e) {
                    throw new SQLException(e.getMessage(), e);
                }
            }
        }
        return resultList;
    }
    
    private String relatedTradeLegTradeDirections2String(List<HostingExecTradeDirection> tradeLegTradeDirections) {
    	if (tradeLegTradeDirections == null || tradeLegTradeDirections.isEmpty()) {
    		return "";
    	}
    	
    	StringBuilder resultBuilder = new StringBuilder(64);
    	for (int index = 0; index < tradeLegTradeDirections.size(); ++index) {
    		if (index > 0) {
    			resultBuilder.append(",");
    		}
    		resultBuilder.append(tradeLegTradeDirections.get(index).getValue());
    	}
    	return resultBuilder.toString();
    }
    
    private List<HostingExecTradeDirection> str2RelatedTradeLegTradeDirections(String content) throws SQLException {
    	List<HostingExecTradeDirection> resultList = new ArrayList<>();
    	String[] relatedTradeDirectionsStrs = StringUtils.split(content, ',');
        if (relatedTradeDirectionsStrs != null) {
            for (String relatedTradeDirectionStr : relatedTradeDirectionsStrs) {
                try {
                    resultList.add(HostingExecTradeDirection.findByValue(Integer.parseInt(relatedTradeDirectionStr)));
                } catch (NumberFormatException e) {
                    throw new SQLException(e.getMessage(), e);
                }
            }
        }
        return resultList;
    }

    private String relatedTradeLegVolumes2String(List<Integer> tradeLegVolumes) {
        return StringUtils.join(tradeLegVolumes, ',');
    }

    private List<Integer> str2RelatedTradeLegVolumes(String content) throws SQLException {
        List<Integer> resultList = new ArrayList<>();
        String[] relatedVolumesStrs = StringUtils.split(content, ',');
        if (relatedVolumesStrs != null) {
            for (String relatedVolumeStr : relatedVolumesStrs) {
                try {
                    resultList.add(Integer.parseInt(relatedVolumeStr));
                } catch (NumberFormatException e) {
                    throw new SQLException(e.getMessage(), e);
                }
            }
        }
        return resultList;
    }

    @Override
    protected String getTableName() throws SQLException {
        return "ttrade";
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                      .append("Fexec_trade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fsub_user_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Frelated_trade_leg_ids VARCHAR(256) NOT NULL DEFAULT '',")
                      // deal orderTradeDirection
                      .append("Ftrade_direction TINYINT UNSIGNED NOT NULL DEFAULT 127,")
                      // contractSummary
                      .append("Fsled_contract_id BIGINT NOT NULL DEFAULT 0,")
                      .append("Fsled_contract_code VARCHAR(32) NOT NULL DEFAULT '',")
                      .append("Fsled_commodity_id BIGINT NOT NULL DEFAULT 0,")
                      .append("Fsled_commodity_type SMALLINT NOT NULL DEFAULT 0,")
                      .append("Fsled_commodity_code VARCHAR(32) NOT NULL DEFAULT '',")
                      .append("Fsled_exchange_mic VARCHAR(14) NOT NULL DEFAULT '',")
                      // accountSummary
                      .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fbroker_id INT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Ftech_platform TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                      // tradeInfo
                      .append("Ftrade_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Ftrade_price DOUBLE DEFAULT 0.00,")
                      .append("Ftrade_volume INT NOT NULL DEFAULT 0,")
                      .append("Frelated_trade_leg_prices VARCHAR(256) NOT NULL DEFAULT '',")
                      // --
                      .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("INDEX order_trades_index(Fexec_order_id),")
                      .append("PRIMARY KEY(Fexec_trade_id)) ENGINE=InnoDB CHARACTER SET UTF8");
            operator.execute(sqlBuilder.toString());
            return ;
        }
        if (2 == targetVersion) {
        	if (!operator.colunmExist(getTableName(), "Frelated_trade_leg_tds")) {
        		StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                       .append(" ADD COLUMN Frelated_trade_leg_tds VARCHAR(64) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
        	}
        	return ;
        }
        if (5 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Fsub_account_id")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (7 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Frelated_trade_leg_contracts")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Frelated_trade_leg_contracts VARCHAR(1024) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (8 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Frelated_trade_leg_volumes")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Frelated_trade_leg_volumes VARCHAR(256) NOT NULL DEFAULT '';");
                operator.execute(alterSqlBuilder.toString());
            }
            return ;
        }
        if (9 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Frelated_trade_leg_count")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Frelated_trade_leg_count INT NOT NULL DEFAULT '-1';");
                operator.execute(alterSqlBuilder.toString());
            }
        }
    }
    

}
