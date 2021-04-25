package xueqiao.trade.hosting.position.fee.core.api;

import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;

public class HostingManageApi {

    private static IHostingManageApi manageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);

    /**
     * ALLDAY_HOSTING: 全天模式，是运行在模拟盘模式
     */
    public static HostingRunningMode queryHostingInfo() throws ErrorInfo {
        return manageApi.getRunningMode();
    }
}

