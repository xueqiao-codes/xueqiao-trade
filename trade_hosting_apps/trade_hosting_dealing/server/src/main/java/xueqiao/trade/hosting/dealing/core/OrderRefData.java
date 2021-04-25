package xueqiao.trade.hosting.dealing.core;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.CTPOrderRef;
import xueqiao.trade.hosting.ESunny3OrderRef;
import xueqiao.trade.hosting.ESunny9OrderRef;
import xueqiao.trade.hosting.HostingExecOrderRef;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class OrderRefData {
    private static OrderRefData sInstance;
    public static OrderRefData Global() {
        if (sInstance == null) {
            synchronized (OrderRefData.class) {
                if (sInstance == null) {
                    sInstance = new OrderRefData();
                }
            }
        }
        return sInstance;
    }

    private HashMap<String, Set<Long>> orderRefMapping = new HashMap<>();

    private String buildKeyString(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderRef orderRef) {
        if (accountSummary == null || orderRef == null) {
            return null;
        }

        if (!accountSummary.isSetTradeAccountId() || accountSummary.getTradeAccountId() <= 0
                || !accountSummary.isSetTechPlatform()) {
            return null;
        }

        StringBuilder keyBuilder = new StringBuilder(64);
        keyBuilder.append(accountSummary.getTradeAccountId());
        if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            CTPOrderRef ctpRef = orderRef.getCtpRef();
            if (ctpRef == null
                    || !ctpRef.isSetFrontID()
                    || !ctpRef.isSetSessionID()
                    || StringUtils.isEmpty(ctpRef.getOrderRef())) {
                return null;
            }

            keyBuilder.append("_").append(ctpRef.getFrontID())
                      .append("_").append(ctpRef.getSessionID())
                      .append("_").append(ctpRef.getOrderRef());
        } else if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_9) {
            ESunny9OrderRef esunny9Ref = orderRef.getEsunny9Ref();
            if (esunny9Ref == null || StringUtils.isEmpty(esunny9Ref.getRefString())) {
                return null;
            }

            keyBuilder.append("_").append(esunny9Ref.getRefString());
        } else if (accountSummary.getTechPlatform() == BrokerTechPlatform.TECH_CTP.TECH_ESUNNY_3) {
            ESunny3OrderRef esunny3Ref = orderRef.getEsunny3Ref();
            if (esunny3Ref == null || StringUtils.isEmpty(esunny3Ref.getSaveString())) {
                return null;
            }

            keyBuilder.append("_").append(esunny3Ref.getSaveString());
        } else {
            return null;
        }
        return keyBuilder.toString();
    }

    public void put(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderRef orderRef
            , long execOrderId) {
        String keyString = buildKeyString(accountSummary, orderRef);
        if (keyString == null) {
            return ;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("OrderRefData put " + keyString + "->" + execOrderId);
        }
        synchronized (this) {
            Set<Long> execOrderIds = orderRefMapping.get(keyString);
            if (execOrderIds == null) {
                execOrderIds = new HashSet<>();
                orderRefMapping.put(keyString, execOrderIds);
            }

            execOrderIds.add(execOrderId);
        }

    }

    public void clear(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderRef orderRef
            , long execOrderId) {
        String keyString = buildKeyString(accountSummary, orderRef);
        if (keyString == null) {
            return ;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("OrderRefData clear " + keyString + "->" + execOrderId);
        }
        synchronized (this) {
            Set<Long> execOrderIds = orderRefMapping.get(keyString);
            if (execOrderIds == null) {
                return ;
            }
            execOrderIds.remove(execOrderId);

            if (execOrderIds.isEmpty()) {
                orderRefMapping.remove(keyString);
            }
        }
    }

    public Set<Long> get(HostingExecOrderTradeAccountSummary accountSummary
            , HostingExecOrderRef orderRef) {
        String keyString = buildKeyString(accountSummary, orderRef);
        if (keyString == null) {
            return null;
        }

        Set<Long> resultSet = null;
        synchronized (this) {
            Set<Long> execOrderIds = orderRefMapping.get(keyString);
            if (execOrderIds != null) {
                resultSet = new HashSet<>(execOrderIds);
            }
        }
        return resultSet;
    }

}
