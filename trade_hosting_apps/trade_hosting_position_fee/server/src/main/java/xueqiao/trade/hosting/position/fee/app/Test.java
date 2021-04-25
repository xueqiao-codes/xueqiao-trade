package xueqiao.trade.hosting.position.fee.app;

import com.google.gson.Gson;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.position.fee.core.api.HostingSubAccountApi;
import xueqiao.trade.hosting.position.fee.core.api.OrderRouteTreeApi;

import java.util.List;

/**
 * 测试
 */
public class Test {
    public static void testOrderRouteTree() throws ErrorInfo {
        List<HostingSubAccount> hostingSubAccountList = HostingSubAccountApi.querySubAccounts();
        AppLog.i("testOrderRouteTree ---- hostingSubAccountList : " + new Gson().toJson(hostingSubAccountList));

        if (hostingSubAccountList != null && hostingSubAccountList.size() > 0) {
            HostingOrderRouteTree hostingOrderRouteTree = OrderRouteTreeApi.queryOrderRouteTree(hostingSubAccountList.get(hostingSubAccountList.size() - 1).getSubAccountId());
            AppLog.i("testOrderRouteTree ---- hostingOrderRouteTree : " + new Gson().toJson(hostingOrderRouteTree));
        }
    }
}
