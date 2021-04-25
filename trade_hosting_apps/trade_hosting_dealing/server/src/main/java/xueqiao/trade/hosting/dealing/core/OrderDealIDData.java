package xueqiao.trade.hosting.dealing.core;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.CTPDealID;
import xueqiao.trade.hosting.ESunny3DealID;
import xueqiao.trade.hosting.ESunny9DealID;
import xueqiao.trade.hosting.HostingExecOrderDealID;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OrderDealIDData {
    private static OrderDealIDData sInstance;
    public static OrderDealIDData Global() {
        if (sInstance == null) {
            synchronized (OrderDealIDData.class) {
                if (sInstance == null) {
                    sInstance = new OrderDealIDData();
                }
            }
        }
        return sInstance;
    }


    private HashMap<String, Set<Long>> dealIDMapping = new HashMap<>();

    private String buildKeyString(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderDealID dealID) {
        if (accountSummary == null || dealID == null) {
            return null;
        }

        if (!accountSummary.isSetTradeAccountId() || accountSummary.getTradeAccountId() <= 0
                || !accountSummary.isSetTechPlatform()) {
            return null;
        }

        StringBuilder keyBuilder = new StringBuilder(64);
        keyBuilder.append(accountSummary.getTradeAccountId());
        if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            CTPDealID ctpDealID = dealID.getCtpDealId();
            if (ctpDealID == null || StringUtils.isEmpty(ctpDealID.getExchangeId())
                    || StringUtils.isEmpty(ctpDealID.getOrderSysId())) {
                return null;
            }

            keyBuilder.append("_").append(ctpDealID.getExchangeId())
                      .append("_").append(ctpDealID.getOrderSysId());
        } else if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_9) {
            ESunny9DealID esunny9DealID = dealID.getEsunny9DealId();
            if (esunny9DealID == null || StringUtils.isEmpty(esunny9DealID.getOrderNo())) {
                return null;
            }

            keyBuilder.append("_").append(esunny9DealID.getOrderNo());
        } else if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_3) {
            ESunny3DealID esunny3DealID = dealID.getEsunny3DealId();
            if (esunny3DealID == null || esunny3DealID.getOrderId() == 0) {
                return null;
            }
            keyBuilder.append("_").append(esunny3DealID.getOrderId());
        } else {
            return null;
        }

        return keyBuilder.toString();
    }

    public void put(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderDealID dealID
            , long execOrderId) {
        String keyString = buildKeyString(accountSummary, dealID);
        if (keyString == null) {
            return ;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("OrderDealIDData put " + keyString + "->" + execOrderId);
        }
        synchronized (this) {
            Set<Long> execOrderIds = dealIDMapping.get(keyString);
            if (execOrderIds == null) {
                execOrderIds = new HashSet<>();
                dealIDMapping.put(keyString, execOrderIds);
            }

            execOrderIds.add(execOrderId);
        }
    }

    public void clear(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderDealID dealID
            , long execOrderId) {
        String keyString = buildKeyString(accountSummary, dealID);
        if (keyString == null) {
            return ;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("OrderDealIDData clear " + keyString + "->" + execOrderId);
        }
        synchronized (this) {
            Set<Long> execOrderIds = dealIDMapping.get(keyString);
            if (execOrderIds == null) {
                return ;
            }
            execOrderIds.remove(execOrderId);

            if (execOrderIds.isEmpty()) {
                dealIDMapping.remove(keyString);
            }
        }
    }

    public Set<Long> get(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderDealID dealID) {
        String keyString = buildKeyString(accountSummary, dealID);
        if (keyString == null) {
            return null;
        }

        Set<Long> resultSet = null;
        synchronized (this) {
            Set<Long> execOrderIds = dealIDMapping.get(keyString);
            if (execOrderIds != null) {
                resultSet = new HashSet<>(execOrderIds);
            }
        }

        return resultSet;
    }
}
