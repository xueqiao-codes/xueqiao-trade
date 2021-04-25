package xueqiao.trade.hosting.arbitrage.core.composelimit.pod;

import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.impl.ComposeLegByPod;
import xueqiao.trade.hosting.arbitrage.core.composelimit.pod.impl.ComposeParallelPod;
import xueqiao.trade.hosting.arbitrage.core.executor.XQOrderComposeLimitExecutor;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecType;


public class ComposePodFactory {

    public static ComposePod createPod(
            HostingXQComposeLimitOrderExecParams execParams
            , int podIndex
            , XQOrderComposeLimitExecutor executor
            , int targetVolume) {
        if (execParams.getExecType() == HostingXQComposeLimitOrderExecType.PARALLEL_LEG) {
            return new ComposeParallelPod(podIndex, executor, targetVolume);
        } else if (execParams.getExecType() == HostingXQComposeLimitOrderExecType.LEG_BY_LEG) {
            return new ComposeLegByPod(podIndex, executor, targetVolume);
        }

        return null;
    }
}
