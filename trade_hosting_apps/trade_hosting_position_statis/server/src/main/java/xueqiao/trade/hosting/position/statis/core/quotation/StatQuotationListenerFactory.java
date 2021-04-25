package xueqiao.trade.hosting.position.statis.core.quotation;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class StatQuotationListenerFactory {

    private static StatQuotationListenerFactory ourInstance = new StatQuotationListenerFactory();

    private Map<String, StatQuotationListener> contractQuotationMap = new HashMap<>();// new ConcurrentHashMap<>();
    private Map<String, StatQuoCombListener> composeQuotationMap = new HashMap<>();// new ConcurrentHashMap<>();

    public static StatQuotationListenerFactory getInstance() {
        return ourInstance;
    }

    private StatQuotationListenerFactory() {
    }

    /**
     * 获取合约行情监听器
     */
    public StatQuotationListener getContractQuotationListener(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        String key = getKey(subAccountId, targetKey, targetType);
        StatQuotationListener listener = contractQuotationMap.get(key);
        if (listener == null) {
            synchronized (contractQuotationMap) {
                listener = contractQuotationMap.get(key);
                if (listener == null) {
                    listener = createStatQuotationListener(subAccountId, targetKey, targetType);
                    contractQuotationMap.put(key, listener);
                }
            }
        }
        return listener;
    }

    /**
     * 获取组合行情监听器
     */
    public StatQuoCombListener getComposeQuotationListener(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        String key = getKey(subAccountId, targetKey, targetType);
        StatQuoCombListener listener = composeQuotationMap.get(key);
        if (listener == null) {
            synchronized (composeQuotationMap) {
                listener = composeQuotationMap.get(key);
                if (listener == null) {
                    listener = createStatQuoCombListener(subAccountId, targetKey, targetType);
                    composeQuotationMap.put(key, listener);
                }
            }
        }
        return listener;
    }

    /**
     * 获取监听 合约行情 列表
     */
    public Collection<StatQuotationListener> getContractQuotationListenerList() {
        return contractQuotationMap.values();
    }

    /**
     * 获取监听 组合行情 列表
     */
    public Collection<StatQuoCombListener> getComposeQuotationListenerList() {
        return composeQuotationMap.values();
    }

    private StatContractQuotationListener createStatQuotationListener(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        return new StatContractQuotationListener(subAccountId, targetKey, targetType);
    }

    private StatComposeQuotationListener createStatQuoCombListener(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        return new StatComposeQuotationListener(subAccountId, targetKey, targetType);
    }

    private String getKey(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        StringBuilder builder = new StringBuilder();
        builder.append(subAccountId);
        builder.append("|");
        builder.append(targetKey);
        builder.append("|");
        builder.append(targetType.getValue());
        return builder.toString();
    }
}
