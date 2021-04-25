package xueqiao.trade.hosting.dealing.core.verify;

import com.google.common.base.Preconditions;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderMode;

public class VerifyProcessFactory {
    public static IVerifyProcessor getProcessor(HostingExecOrder inputOrder) {
        Preconditions.checkNotNull(inputOrder != null);
        if (inputOrder.getOrderDetail().getOrderMode() == HostingExecOrderMode.ORDER_GFD) {
            return new GFDOrderVerifyProcessor();
        }
        return null;
    }
}
