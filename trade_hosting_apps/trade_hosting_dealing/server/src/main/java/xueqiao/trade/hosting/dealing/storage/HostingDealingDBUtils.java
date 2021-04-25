package xueqiao.trade.hosting.dealing.storage;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.thrift.TException;
import org.soldier.base.sql.PreparedFields;

import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.HostingExecOrderCondition;
import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderCreatorType;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderMode;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.HostingExecOrderType;
import xueqiao.trade.hosting.framework.utils.PriceUtils;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;

public class HostingDealingDBUtils {
	
	public static HostingExecOrderContractSummary contractSummaryFromRs(ResultSet rs
	        , boolean needRelatedLegs) throws SQLException {
		HostingExecOrderContractSummary contractSummary = new HostingExecOrderContractSummary();
		contractSummary.setSledContractId(rs.getLong("Fsled_contract_id"));
		contractSummary.setSledContractCode(rs.getString("Fsled_contract_code"));
		contractSummary.setSledCommodityId(rs.getLong("Fsled_commodity_id"));
		contractSummary.setSledCommodityType(rs.getShort("Fsled_commodity_type"));
		contractSummary.setSledCommodityCode(rs.getString("Fsled_commodity_code"));
		contractSummary.setSledExchangeMic(rs.getString("Fsled_exchange_mic"));
		if (needRelatedLegs) {
		    try {
		        contractSummary.setRelatedLegs(ThriftExtJsonUtils.listFromJsonTBase(
		                rs.getString("Fsled_contract_related_legs"), HostingExecOrderLegContractSummary.class));
		    } catch (Throwable e) {
		        throw new SQLException(e.getMessage(), e);
		    } 
		}
		return contractSummary;
	}
	
	public static void contractSummary2PrepraredFields(HostingExecOrderContractSummary contractSummary
			, PreparedFields fields, boolean needRelatedLegs) throws TException {
		if (contractSummary.isSetSledContractId()) {
            fields.addLong("Fsled_contract_id", contractSummary.getSledContractId());
        }
        if (contractSummary.isSetSledContractCode()) {
            fields.addString("Fsled_contract_code", contractSummary.getSledContractCode());
        }
        if (contractSummary.isSetSledCommodityId()) {
            fields.addLong("Fsled_commodity_id", contractSummary.getSledCommodityId());
        }
        if (contractSummary.isSetSledCommodityType()) {
            fields.addShort("Fsled_commodity_type", contractSummary.getSledCommodityType());
        }
        if (contractSummary.isSetSledCommodityCode()) {
            fields.addString("Fsled_commodity_code", contractSummary.getSledCommodityCode());
        }
        if (contractSummary.isSetSledExchangeMic()) {
            fields.addString("Fsled_exchange_mic", contractSummary.getSledExchangeMic());
        }
        if (needRelatedLegs && contractSummary.isSetRelatedLegs()) {
            fields.addString("Fsled_contract_related_legs"
                    , ThriftExtJsonUtils.listToJsonTBase(contractSummary.getRelatedLegs()));
        }
	}
	
	public static HostingExecOrderDetail orderDetailFromRs(ResultSet rs) throws SQLException {
		HostingExecOrderDetail orderDetail = new HostingExecOrderDetail();
	    orderDetail.setOrderType(HostingExecOrderType.findByValue(rs.getByte("Forder_type")));
	    if (rs.getByte("Flimit_price_isset") != 0) {
	        orderDetail.setLimitPrice(rs.getDouble("Flimit_price"));
	    }
	    orderDetail.setQuantity(rs.getInt("Fquantity"));
	    orderDetail.setTradeDirection(HostingExecOrderTradeDirection.findByValue(rs.getByte("Ftrade_direction")));
	    orderDetail.setOrderCreatorType(HostingExecOrderCreatorType.findByValue(rs.getByte("Forder_creator_type")));
	    orderDetail.setOrderMode(HostingExecOrderMode.findByValue(rs.getByte("Forder_mode")));
	    orderDetail.setEffectiveDateTime(rs.getString("Feffective_datetime"));
	    orderDetail.setCondition(HostingExecOrderCondition.findByValue(rs.getByte("Forder_condition")));
	    if (rs.getByte("Forder_condition_price_isset") != 0) {
	        orderDetail.setConditionPrice(rs.getDouble("Forder_condition_price"));
	    }
		
	    return orderDetail;
	}
	
	public static void orderDetail2PreparedFields(HostingExecOrderDetail orderDetail
			, PreparedFields fields) {
		if (orderDetail.isSetOrderType()) {
            fields.addByte("Forder_type", (byte)orderDetail.getOrderType().getValue());
        }
        if (orderDetail.isSetLimitPrice()) {
            if (PriceUtils.isAppropriatePrice(orderDetail.getLimitPrice())) {
                fields.addByte("Flimit_price_isset", (byte)1);
                fields.addDouble("Flimit_price", orderDetail.getLimitPrice());
            } else {
                fields.addByte("Flimit_price_isset", (byte)0);
            }
        }
        if (orderDetail.isSetQuantity()) {
            fields.addInt("Fquantity", orderDetail.getQuantity());
        }
        if (orderDetail.isSetTradeDirection()) {
            fields.addByte("Ftrade_direction", (byte)orderDetail.getTradeDirection().getValue());
        }
        if (orderDetail.isSetOrderCreatorType()) {
            fields.addByte("Forder_creator_type", (byte)orderDetail.getOrderCreatorType().getValue());
        }
        if (orderDetail.isSetOrderMode()) {
            fields.addByte("Forder_mode", (byte)orderDetail.getOrderMode().getValue());
        }
        if (orderDetail.isSetEffectiveDateTime()) {
            fields.addString("Feffective_datetime", orderDetail.getEffectiveDateTime());
        }
        if (orderDetail.isSetCondition()) {
            fields.addByte("Forder_condition", (byte)orderDetail.getCondition().getValue());
        }
        if (orderDetail.isSetConditionPrice()) {
            if (PriceUtils.isAppropriatePrice(orderDetail.getConditionPrice())) {
                fields.addByte("Forder_condition_price_isset", (byte)1);
                fields.addDouble("Forder_condition_price", orderDetail.getConditionPrice());
            } else {
                fields.addByte("Forder_condition_price_isset", (byte)0);
            }
        }
	}
	
	public static HostingExecOrderTradeAccountSummary accountSummaryFromRs(ResultSet rs) throws SQLException {
		HostingExecOrderTradeAccountSummary accountSummary = new HostingExecOrderTradeAccountSummary();
	    accountSummary.setTradeAccountId(rs.getLong("Ftrade_account_id"));
	    accountSummary.setBrokerId(rs.getInt("Fbroker_id"));
	    accountSummary.setTechPlatform(BrokerTechPlatform.findByValue(rs.getByte("Ftech_platform")));
	    return accountSummary;
	}
	
	public static void accountSummary2PreparedFields(HostingExecOrderTradeAccountSummary accountSummary
			, PreparedFields fields) {
		if (accountSummary.isSetTradeAccountId()) {
            fields.addLong("Ftrade_account_id", accountSummary.getTradeAccountId());
        }
        if (accountSummary.isSetBrokerId()) {
            fields.addLong("Fbroker_id", accountSummary.getBrokerId());
        }
        if (accountSummary.isSetTechPlatform()) {
            fields.addByte("Ftech_platform", (byte)accountSummary.getTechPlatform().getValue());
        }
	}
	
	
	public static HostingExecOrderTradeSummary tradeSummaryFromRs(ResultSet rs) throws SQLException {
		HostingExecOrderTradeSummary tradeSummary = new HostingExecOrderTradeSummary();
	    tradeSummary.setUpsideTradeTotalVolume(rs.getInt("Fupside_trade_total_volume"));
	    tradeSummary.setUpsideTradeRestingVolume(rs.getInt("Fupside_trade_resting_volume"));
	    if (rs.getByte("Fupside_trade_average_price_isset") != 0) {
	        tradeSummary.setUpsideTradeAveragePrice(rs.getDouble("Fupside_trade_average_price"));
	    }
	    tradeSummary.setTradeListTotalVolume(rs.getInt("Ftrade_list_total_volume"));
	    if (rs.getByte("Ftrade_list_average_price_isset") != 0) {
	        tradeSummary.setTradeListAveragePrice(rs.getDouble("Ftrade_list_average_price"));
	    }
		return tradeSummary;
	}
	
	public static void tradeSummary2PreparedFields(HostingExecOrderTradeSummary tradeSummary
			, PreparedFields fields) {
		if (tradeSummary.isSetUpsideTradeTotalVolume()) {
            fields.addInt("Fupside_trade_total_volume", tradeSummary.getUpsideTradeTotalVolume());
        }
        if (tradeSummary.isSetUpsideTradeRestingVolume()) {
            fields.addInt("Fupside_trade_resting_volume", tradeSummary.getUpsideTradeRestingVolume());
        }
        if (tradeSummary.isSetUpsideTradeAveragePrice()) {
            if (PriceUtils.isAppropriatePrice(tradeSummary.getUpsideTradeAveragePrice())) {
                fields.addByte("Fupside_trade_average_price_isset", (byte)1);
                fields.addDouble("Fupside_trade_average_price", tradeSummary.getUpsideTradeAveragePrice());
            } else {
                fields.addByte("Fupside_trade_average_price_isset", (byte)0);
            }
        }
        if (tradeSummary.isSetTradeListTotalVolume()) {
            fields.addInt("Ftrade_list_total_volume", tradeSummary.getTradeListTotalVolume());
        }
        if (tradeSummary.isSetTradeListAveragePrice()) {
            if (PriceUtils.isAppropriatePrice(tradeSummary.getTradeListAveragePrice())) {
                fields.addByte("Ftrade_list_average_price_isset", (byte)1);
                fields.addDouble("Ftrade_list_average_price", tradeSummary.getTradeListAveragePrice());
            } else {
                fields.addByte("Ftrade_list_average_price_isset", (byte)0);
            }
        }
	}
}
