package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingOrderRouteTreeApi;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

public class HostingOrderRouteTreeHandler extends HandlerBase {

    private IHostingOrderRouteTreeApi mOrderRouteTreeApi;
    private IHostingSubAccountApi mSubAccountApi;
    
    public HostingOrderRouteTreeHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        
        mOrderRouteTreeApi = Globals.getInstance().queryInterfaceForSure(IHostingOrderRouteTreeApi.class);
        mSubAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class);
        permit(EHostingUserRole.AdminGroup);
    }
    
    public HostingOrderRouteTree getHostingOrderRouteTree(long subAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        HostingOrderRouteTree routeTree = mOrderRouteTreeApi.getTree(subAccountId);
        if (routeTree == null) {
            routeTree = new HostingOrderRouteTree();
            routeTree.setConfigVersion(0);
        }
        return routeTree;
    }

    public void updateHostingOrderRouteTree(long subAccountId, HostingOrderRouteTree routeTree) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        ParameterChecker.check(routeTree != null, "routeTree should not be null");
        ParameterChecker.check(routeTree.getConfigVersion() > 0, "routeTree's configVersion should > 0");
        
        HostingSubAccount subAccount = mSubAccountApi.getSubAccount(subAccountId);
        if (subAccount == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_NOT_EXISTED.getValue()
                    , "Sub account not existed!");
        }
        mOrderRouteTreeApi.updateTree(subAccountId, routeTree);
    }

    public int getHostingOrderRouteTreeVersion(long subAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subUserId should > 0");
        return mOrderRouteTreeApi.getTreeVersion(subAccountId);
    }
    

}
