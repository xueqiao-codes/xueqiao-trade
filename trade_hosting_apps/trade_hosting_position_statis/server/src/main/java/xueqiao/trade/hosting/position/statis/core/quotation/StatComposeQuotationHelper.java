package xueqiao.trade.hosting.position.statis.core.quotation;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.quot.comb.sdk.IQuotCombListener;
import xueqiao.trade.hosting.quot.comb.sdk.QuotCombDispatcher;

public class StatComposeQuotationHelper {

    public static void register(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        long composeGraphId = Long.valueOf(targetKey);
        StatQuoCombListener listener = StatQuotationListenerFactory.getInstance().getComposeQuotationListener(subAccountId, targetKey, targetType);
        register(composeGraphId, listener);
    }

    /**
     * 注销监 合约 行情
     */
    public static void unRegister(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        long composeGraphId = Long.valueOf(targetKey);
        StatQuoCombListener listener = StatQuotationListenerFactory.getInstance().getComposeQuotationListener(subAccountId, targetKey, targetType);
        unRegister(composeGraphId, listener);
    }

    /**
     * 注册监听 组合 行情
     */
    private static void register(long composeGraphId, IQuotCombListener listener) throws ErrorInfo {
        QuotCombDispatcher.Global().registerListener(composeGraphId, listener);
    }

    /**
     * 注销监听 组合 行情
     */
    private static void unRegister(long composeGraphId, IQuotCombListener listener) throws ErrorInfo {
        QuotCombDispatcher.Global().unRegisterListener(composeGraphId, listener);
    }

}
