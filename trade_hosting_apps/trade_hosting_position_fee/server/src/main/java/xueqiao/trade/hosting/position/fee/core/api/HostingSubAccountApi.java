package xueqiao.trade.hosting.position.fee.core.api;

import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;

import java.util.List;

public class HostingSubAccountApi {

    private static IHostingSubAccountApi subAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class);

    public static List<HostingSubAccount> querySubAccounts() throws ErrorInfo {
        QuerySubAccountOption qryOption = new QuerySubAccountOption();
        PageOption pageOption = new PageOption();
        PageResult<HostingSubAccount> pageResult = subAccountApi.getSubAccounts(qryOption, pageOption);
        if (pageResult != null && !pageResult.getPageList().isEmpty()) {
            return pageResult.getPageList();
        }
        return null;
    }
}
