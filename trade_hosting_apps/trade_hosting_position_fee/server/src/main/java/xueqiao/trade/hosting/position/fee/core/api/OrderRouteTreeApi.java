package xueqiao.trade.hosting.position.fee.core.api;

import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.storage.apis.IHostingOrderRouteTreeApi;

public class OrderRouteTreeApi {

    private static IHostingOrderRouteTreeApi orderRouteTreeApi = Globals.getInstance().queryInterfaceForSure(IHostingOrderRouteTreeApi.class);

    public static HostingOrderRouteTree queryOrderRouteTree(long subAccountId) throws ErrorInfo {
        return orderRouteTreeApi.getTree(subAccountId);
    }
}
