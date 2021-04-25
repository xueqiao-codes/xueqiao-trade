package xueqiao.trade.hosting.position.statis.core.quotation;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;

import java.util.Collection;

public class StatQuotationHelper {

    /**
     * 注册监听行情
     */
    public static void register(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        if (targetType == HostingXQTargetType.COMPOSE_TARGET) {
            StatComposeQuotationHelper.register(subAccountId, targetKey, targetType);
        } else if (targetType == HostingXQTargetType.CONTRACT_TARGET) {
            StatContractQuotationHelper.register(subAccountId, targetKey, targetType);
        } else {
            throw StatErrorCode.errorInvalidTargetType;
        }
    }

    /**
     * 注销监听行情
     */
    public static void unRegister(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        if (targetType == HostingXQTargetType.COMPOSE_TARGET) {
            StatComposeQuotationHelper.unRegister(subAccountId, targetKey, targetType);
        } else if (targetType == HostingXQTargetType.CONTRACT_TARGET) {
            StatContractQuotationHelper.unRegister(subAccountId, targetKey, targetType);
        } else {
            throw StatErrorCode.errorInvalidTargetType;
        }
    }

    /**
     * 注销监听所有行情
     * 重置托管机时才调用
     */
    public static void unRegisterAllQuotations() throws ErrorInfo {
        /*
         * 注销监听所有组合行情
         * */
        Collection<StatQuoCombListener> composeQuotationListenerList = StatQuotationListenerFactory.getInstance().getComposeQuotationListenerList();
        if (composeQuotationListenerList != null && composeQuotationListenerList.size() > 0) {
            for (StatQuoCombListener listener : composeQuotationListenerList) {
                unRegister(listener.getSubAccountId(), listener.getTargetKey(), listener.getTargetType());
            }
        }

        /*
         * 注销监听所有合约行情
         * */
        Collection<StatQuotationListener> quotationListenerList = StatQuotationListenerFactory.getInstance().getContractQuotationListenerList();
        if (quotationListenerList != null && quotationListenerList.size() > 0) {
            for (StatQuotationListener listener : quotationListenerList) {
                unRegister(listener.getSubAccountId(), listener.getTargetKey(), listener.getTargetType());
            }
        }
    }
}
