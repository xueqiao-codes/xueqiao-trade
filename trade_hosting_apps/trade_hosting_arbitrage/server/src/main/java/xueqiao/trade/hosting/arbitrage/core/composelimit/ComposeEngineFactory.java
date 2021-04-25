package xueqiao.trade.hosting.arbitrage.core.composelimit;

import com.google.common.base.Preconditions;
import xueqiao.trade.hosting.arbitrage.core.composelimit.engine.ComposePodEngine;
import xueqiao.trade.hosting.arbitrage.core.composelimit.engine.SimpleComposeEngine;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;

public class ComposeEngineFactory {
    public static IComposeEngine createEngine(HostingXQOrder composeOrder) {
        HostingXQComposeLimitOrderDetail orderDetail = composeOrder.getOrderDetail().getComposeLimitOrderDetail();
        Preconditions.checkNotNull(orderDetail);

        if (!orderDetail.isSetExecParams()) {
            return new SimpleComposeEngine();
        }

        HostingXQComposeLimitOrderExecParams execParams = orderDetail.getExecParams();
        if (!execParams.isSetExecType()) {
            return new SimpleComposeEngine();
        }

        return new ComposePodEngine();
    }
}
