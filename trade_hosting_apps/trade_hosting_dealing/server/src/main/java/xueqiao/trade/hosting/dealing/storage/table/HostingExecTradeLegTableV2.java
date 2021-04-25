package xueqiao.trade.hosting.dealing.storage.table;

import com.google.common.base.Preconditions;
import org.soldier.base.sql.PreparedFields;
import org.soldier.base.sql.SqlQueryBuilder;
import org.soldier.base.sql.SqlQueryBuilder.ConditionType;
import org.soldier.platform.db_helper.TableHelper;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.CTPTradeID;
import xueqiao.trade.hosting.ESunny3TradeID;
import xueqiao.trade.hosting.ESunny9TradeID;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.HostingExecTradeLegInfo;
import xueqiao.trade.hosting.HostingExecUpsideTradeID;
import xueqiao.trade.hosting.dealing.storage.HostingDealingDBUtils;
import xueqiao.trade.hosting.framework.db.IDBOperator;
import xueqiao.trade.hosting.framework.db.IDBTable;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 成交腿
 * @author wangli
 */
public class HostingExecTradeLegTableV2 extends TableHelper<HostingExecTradeLeg> implements IDBTable{
    public HostingExecTradeLegTableV2(Connection conn) {
        super(conn);
    }
    
    public List<HostingExecTradeLeg> getOrderTradeLegs(long execOrderId) throws SQLException {
        return getOrderTradeLegs(execOrderId, false);
    }
    
    public List<HostingExecTradeLeg> getOrderTradeLegs(long execOrderId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_order_id=?", execOrderId);
        return super.getItemList(queryBuilder, forUpdate);
    }
    
    public List<HostingExecTradeLeg> getTradeRelatedLegs(long relatedExecTradeId) throws SQLException {
        return getTradeRelatedLegs(relatedExecTradeId,false);
    }
    
    public List<HostingExecTradeLeg> getTradeRelatedLegs(long relatedExecTradeId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Frelated_exec_trade_id =?", relatedExecTradeId);
        return super.getItemList(queryBuilder, forUpdate);
    }
    
    public HostingExecTradeLeg getTradeLeg(long execTradeLegId) throws SQLException {
        return getTradeLeg(execTradeLegId, false);
    }
    
    public HostingExecTradeLeg getTradeLeg(long execTradeLegId, boolean forUpdate) throws SQLException {
        SqlQueryBuilder queryBuilder = super.prepareSqlQueryBuilder();
        queryBuilder.addFieldCondition(ConditionType.AND, "Fexec_trade_leg_id=?", execTradeLegId);
        return super.getItem(queryBuilder, forUpdate);
    }
    
    public int addTradeLeg(HostingExecTradeLeg tradeLeg) throws SQLException {
        Preconditions.checkNotNull(tradeLeg);
        Preconditions.checkArgument(tradeLeg.isSetExecTradeLegId() && tradeLeg.getExecTradeLegId() > 0);
        
        PreparedFields fields = new PreparedFields();
        fields.addLong("Fexec_trade_leg_id", tradeLeg.getExecTradeLegId());
        tradeLeg2PreparedFields(tradeLeg, fields);
        if (tradeLeg.isSetCreateTimestampMs()) {
            fields.addLong("Fcreate_timestampms", tradeLeg.getCreateTimestampMs());
            fields.addLong("Flastmodify_timestampms", tradeLeg.getCreateTimestampMs());
        } else {
            fields.addLong("Fcreate_timestampms", System.currentTimeMillis());
            fields.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        }
        
        return super.insert(fields);
    }
    
    public int updateTradeLeg(HostingExecTradeLeg tradeLeg) throws SQLException {
        Preconditions.checkNotNull(tradeLeg);
        Preconditions.checkArgument(tradeLeg.isSetExecTradeLegId() && tradeLeg.getExecTradeLegId() > 0);
        
        PreparedFields fields = new PreparedFields();
        tradeLeg2PreparedFields(tradeLeg, fields);
        fields.addLong("Flastmodify_timestampms", System.currentTimeMillis());
        
        return super.update(fields, "Fexec_trade_leg_id=?", tradeLeg.getExecTradeLegId());
    }
    
    public int deleteTradeLeg(long execTradeLegId) throws SQLException {
        Preconditions.checkArgument(execTradeLegId > 0);
        return super.delete( "Fexec_trade_leg_id=?", execTradeLegId);
    }
    
    private void tradeLeg2PreparedFields(HostingExecTradeLeg tradeLeg, PreparedFields fields) {
        if (tradeLeg.isSetExecOrderId()) {
            fields.addLong("Fexec_order_id", tradeLeg.getExecOrderId());
        }
        if (tradeLeg.isSetSubUserId()) {
            fields.addInt("Fsub_user_id", tradeLeg.getSubUserId());
        }
        if (tradeLeg.isSetSubAccountId()) {
            fields.addLong("Fsub_account_id", tradeLeg.getSubAccountId());
        }
        
        if (tradeLeg.isSetRelatedExecTradeId()) {
            fields.addLong("Frelated_exec_trade_id", tradeLeg.getRelatedExecTradeId());
        }
        if (tradeLeg.isSetLegIndex()) {
            fields.addShort("Fleg_index", tradeLeg.getLegIndex());
        }
        if (tradeLeg.isSetAccountSummary()) {
            HostingDealingDBUtils.accountSummary2PreparedFields(tradeLeg.getAccountSummary(), fields);
        }
        if (tradeLeg.isSetLegContractSummary()) {
            if (tradeLeg.getLegContractSummary().isSetLegSledContractId()) {
                fields.addLong("Fleg_sled_contract_id", tradeLeg.getLegContractSummary().getLegSledContractId());
            }
            if (tradeLeg.getLegContractSummary().isSetLegSledContractCode()) {
                fields.addString("Fleg_sled_contract_code", tradeLeg.getLegContractSummary().getLegSledContractCode());
            }
            if (tradeLeg.getLegContractSummary().isSetLegSledCommodityId()) {
                fields.addLong("Fleg_sled_commodity_id", tradeLeg.getLegContractSummary().getLegSledCommodityId());
            }
            if (tradeLeg.getLegContractSummary().isSetLegSledCommodityType()) {
                fields.addShort("Fleg_sled_commodity_type", tradeLeg.getLegContractSummary().getLegSledCommodityType());
            }
            if (tradeLeg.getLegContractSummary().isSetLegSledCommodityCode()) {
                fields.addString("Fleg_sled_commodity_code", tradeLeg.getLegContractSummary().getLegSledCommodityCode());
            }
            if (tradeLeg.getLegContractSummary().isSetLegSledExchangeMic()) {
                fields.addString("Fleg_sled_exchange_mic", tradeLeg.getLegContractSummary().getLegSledExchangeMic());
            }
        }
        if (tradeLeg.isSetTradeLegInfo()) {
            HostingExecTradeLegInfo tradeLegInfo = tradeLeg.getTradeLegInfo();
            
            if (tradeLegInfo.isSetLegTradePrice()) {
                if (PriceUtils.isAppropriatePrice(tradeLegInfo.getLegTradePrice())) {
                    fields.addByte("Fleg_trade_price_isset", (byte)1);
                    fields.addDouble("Fleg_trade_price", tradeLegInfo.getLegTradePrice());
                } else {
                    fields.addByte("Fleg_trade_price_isset", (byte)0);
                }
            }
            if (tradeLegInfo.isSetLegTradeVolume()) {
                fields.addInt("Fleg_trade_volume", tradeLegInfo.getLegTradeVolume());
            }
            if (tradeLegInfo.isSetLegTradeDateTime()) {
                fields.addString("Fleg_trade_datetime", tradeLegInfo.getLegTradeDateTime());
            }
            if (tradeLegInfo.isSetLegUpsideTradeDirection()) {
            	fields.addByte("Ftrade_direction", (byte)tradeLegInfo.getLegUpsideTradeDirection().getValue());
            }
            if (tradeLegInfo.isSetLegUpsideTradeId()) {
                HostingExecUpsideTradeID legUpsideTradeId = tradeLegInfo.getLegUpsideTradeId();
                if (legUpsideTradeId.isSetCtpTradeId()) {
                    if (legUpsideTradeId.getCtpTradeId().isSetTradeId()) {
                        fields.addString("Fctp_trade_id", legUpsideTradeId.getCtpTradeId().getTradeId());
                    }
                }
                if (legUpsideTradeId.isSetEsunny3TradeId()) {
                    if (legUpsideTradeId.getEsunny3TradeId().isSetMatchNo()) {
                        fields.addString("Fesunny3_match_no", legUpsideTradeId.getEsunny3TradeId().getMatchNo());
                    }
                }
                if (legUpsideTradeId.isSetEsunny9TradeId()) {
                	if (legUpsideTradeId.getEsunny9TradeId().isSetMatchNo()) {
                		fields.addString("Fesunny9_match_no", legUpsideTradeId.getEsunny9TradeId().getMatchNo());
                	}
                }
            }
        }
    }
    
    @Override
    public HostingExecTradeLeg fromResultSet(ResultSet rs) throws Exception {
        HostingExecTradeLeg tradeLeg = new HostingExecTradeLeg();
        tradeLeg.setExecTradeLegId(rs.getLong("Fexec_trade_leg_id"));
        tradeLeg.setExecOrderId(rs.getLong("Fexec_order_id"));
        tradeLeg.setSubUserId(rs.getInt("Fsub_user_id"));
        tradeLeg.setSubAccountId(rs.getLong("Fsub_account_id"));
        tradeLeg.setRelatedExecTradeId(rs.getLong("Frelated_exec_trade_id"));
        tradeLeg.setLegIndex(rs.getShort("Fleg_index"));
        
        tradeLeg.setAccountSummary(HostingDealingDBUtils.accountSummaryFromRs(rs));
        
        HostingExecOrderLegContractSummary legContractSummary 
            = new HostingExecOrderLegContractSummary();
        legContractSummary.setLegSledContractId(rs.getLong("Fleg_sled_contract_id"));
        legContractSummary.setLegSledContractCode(rs.getString("Fleg_sled_contract_code"));
        legContractSummary.setLegSledCommodityId(rs.getLong("Fleg_sled_commodity_id"));
        legContractSummary.setLegSledCommodityType(rs.getShort("Fleg_sled_commodity_type"));
        legContractSummary.setLegSledCommodityCode(rs.getString("Fleg_sled_commodity_code"));
        legContractSummary.setLegSledExchangeMic(rs.getString("Fleg_sled_exchange_mic"));
        tradeLeg.setLegContractSummary(legContractSummary);
        
        HostingExecTradeLegInfo tradeLegInfo = new HostingExecTradeLegInfo();
        if (rs.getByte("Fleg_trade_price_isset") != 0) {
            tradeLegInfo.setLegTradePrice(rs.getDouble("Fleg_trade_price"));
        }
        tradeLegInfo.setLegTradeVolume(rs.getInt("Fleg_trade_volume"));
        tradeLegInfo.setLegTradeDateTime(rs.getString("Fleg_trade_datetime"));
        HostingExecUpsideTradeID legUpsideTradeId = new HostingExecUpsideTradeID();
        if (tradeLeg.getAccountSummary().getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            CTPTradeID ctpTradeId = new CTPTradeID();
            ctpTradeId.setTradeId(rs.getString("Fctp_trade_id"));
            legUpsideTradeId.setCtpTradeId(ctpTradeId);
        } else if (tradeLeg.getAccountSummary().getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_3) {
            ESunny3TradeID esunny3TradeId = new ESunny3TradeID();
            esunny3TradeId.setMatchNo(rs.getString("Fesunny3_match_no"));
            legUpsideTradeId.setEsunny3TradeId(esunny3TradeId);
        } else if (tradeLeg.getAccountSummary().getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_9) {
        	ESunny9TradeID esunny9TradeId = new ESunny9TradeID();
        	esunny9TradeId.setMatchNo(rs.getString("Fesunny9_match_no"));
        	legUpsideTradeId.setEsunny9TradeId(esunny9TradeId);
        }
        tradeLegInfo.setLegUpsideTradeId(legUpsideTradeId);
        tradeLegInfo.setLegUpsideTradeDirection(HostingExecTradeDirection.findByValue(rs.getByte("Ftrade_direction")));
        
        tradeLeg.setTradeLegInfo(tradeLegInfo);
        tradeLeg.setCreateTimestampMs(rs.getLong("Fcreate_timestampms"));
        tradeLeg.setLastmodifyTimestampMs(rs.getLong("Flastmodify_timestampms"));
        
        return tradeLeg;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {
        if (1 == targetVersion) {
            StringBuilder sqlBuilder = new StringBuilder(128);
            sqlBuilder.append("CREATE TABLE IF NOT EXISTS ").append(getTableName()).append("(")
                      .append("Fexec_trade_leg_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fexec_order_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fsub_user_id INT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Frelated_exec_trade_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fleg_index SMALLINT NOT NULL DEFAULT -1,")
                      // accountSummary
                      .append("Ftrade_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fbroker_id INT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Ftech_platform TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                      // HostingExecOrderLegContractSummary
                      .append("Fleg_sled_contract_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fleg_sled_contract_code VARCHAR(32) NOT NULL DEFAULT '',")
                      .append("Fleg_sled_commodity_id BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fleg_sled_commodity_type SMALLINT NOT NULL DEFAULT 0,")
                      .append("Fleg_sled_commodity_code VARCHAR(32) NOT NULL DEFAULT '',")
                      .append("Fleg_sled_exchange_mic VARCHAR(14) NOT NULL DEFAULT '',")
                      // HostingExecTradeLegInfo
                      .append("Fleg_trade_price_isset TINYINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Fleg_trade_price DOUBLE DEFAULT 0.00,")
                      .append("Fleg_trade_volume INT NOT NULL DEFAULT 0,")
                      .append("Fleg_trade_datetime VARCHAR(24) NOT NULL DEFAULT '',")
                      .append("Fctp_trade_id VARCHAR(24) NOT NULL DEFAULT '',")
                      .append("Fesunny3_match_no VARCHAR(72) NOT NULL DEFAULT '',")
                      .append("Fcreate_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("Flastmodify_timestampms BIGINT UNSIGNED NOT NULL DEFAULT 0,")
                      .append("INDEX order_trade_legs_index(Fexec_order_id),")
                      .append("PRIMARY KEY(Fexec_trade_leg_id)) ENGINE=InnoDB CHARACTER SET UTF8;");
            operator.execute(sqlBuilder.toString());
        }
        if (2 == targetVersion) {
        	if (!operator.colunmExist(getTableName(), "Ftrade_direction")) {
        		StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                       .append(" ADD COLUMN Ftrade_direction TINYINT UNSIGNED NOT NULL DEFAULT 127;");
                operator.execute(alterSqlBuilder.toString());
        	}
        }
        if (3 == targetVersion) {
        	if (!operator.colunmExist(getTableName(), "Fesunny9_match_no")) {
        		StringBuilder alterSqlBuilder = new StringBuilder(64);
        		alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
        			  .append(" ADD COLUMN Fesunny9_match_no VARCHAR(72) NOT NULL DEFAULT '';");
        		operator.execute(alterSqlBuilder.toString());
        	}
        }
        if (5 == targetVersion) {
            if (!operator.colunmExist(getTableName(), "Fsub_account_id")) {
                StringBuilder alterSqlBuilder = new StringBuilder(64);
                alterSqlBuilder.append("ALTER TABLE ").append(getTableName())
                        .append(" ADD COLUMN Fsub_account_id BIGINT UNSIGNED NOT NULL DEFAULT 0;");
                operator.execute(alterSqlBuilder.toString());
            }
            
        }
    }

    @Override
    protected String getTableName() throws SQLException {
        return "ttrade_leg";
    }

}
