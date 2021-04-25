package xueqiao.trade.hosting.order.route;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingOrderRouteTreeApi;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;

import java.util.HashMap;
import java.util.Map;

public class OrderRouteTreeCache {
    private static OrderRouteTreeCache INSTANCE;
    public static OrderRouteTreeCache Global() {
        if (INSTANCE == null) {
            synchronized(OrderRouteTreeCache.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OrderRouteTreeCache();
                }
            }
        }
        return INSTANCE;
    }

    private IHostingOrderRouteTreeApi mOrderRouteTreeApi;
    private IHostingSubAccountApi mSubAccountApi;
    private Map<Long, HostingOrderRouteTree> mOrderRouteTreeCacheMap
        = new HashMap<Long, HostingOrderRouteTree>();
    
    private OrderRouteTreeCache() {
        mSubAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class);
        mOrderRouteTreeApi = Globals.getInstance().queryInterfaceForSure(IHostingOrderRouteTreeApi.class);
    }

    public synchronized boolean contains(long subAccountId) {
        return mOrderRouteTreeCacheMap.containsKey(subAccountId);
    }
    
    public synchronized HostingOrderRouteTree get(long subAccountId) {
        return mOrderRouteTreeCacheMap.get(subAccountId);
    }
    
    public void add(long subAccountId, HostingOrderRouteTree tree) {
        AppLog.i("add route tree for subAccountId=" + subAccountId + ", version="
                   + ((tree != null) ? tree.getConfigVersion() : 0));
        if (tree == null) {
            mOrderRouteTreeCacheMap.put(subAccountId, new HostingOrderRouteTree().setConfigVersion(0));
            return ;
        }
        mOrderRouteTreeCacheMap.put(subAccountId, tree);
    }
    
    public synchronized void update(long subAccountId, HostingOrderRouteTree updateTree) {
        if (updateTree == null) {
            return ;
        }
        
        AppLog.i("update route tree subUserId=" + subAccountId
                + ", updateTree version=" + updateTree.getConfigVersion());
        
        HostingOrderRouteTree originTree = mOrderRouteTreeCacheMap.get(subAccountId);
        if (originTree == null) {
            return ;
        }
        if (originTree.getConfigVersion() >= updateTree.getConfigVersion()) {
            return ;
        }
        mOrderRouteTreeCacheMap.put(subAccountId, updateTree);
    }
    
    public synchronized void rm(long subAccountId) {
        AppLog.i("route tree removed for subUserId=" + subAccountId);
        mOrderRouteTreeCacheMap.remove(subAccountId);
    }

    public HostingOrderRouteTree getRouteTreeNullIfLostFromDB(long subAccountId) throws ErrorInfo {
        try {
            return mOrderRouteTreeApi.getTree(subAccountId);
        } catch (ErrorInfo e) {
            if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_CONFIG_LOST.getValue()) {
                AppLog.e("order route cache config lost for subUserId=" + subAccountId + ", " + e.getErrorMsg());
                return null;
            }
            throw e;
        }
    }

    public synchronized void syncFromDB() throws ErrorInfo {
        AppLog.i("syncFromDB...");
        mOrderRouteTreeCacheMap.clear();
        PageResult<HostingSubAccount> allHostingSubAccountPage =
                mSubAccountApi.getSubAccounts(new QuerySubAccountOption()
                        , new PageOption().setPageIndex(0).setPageSize(Integer.MAX_VALUE));
        for (HostingSubAccount subAccount : allHostingSubAccountPage.getPageList()) {
            OrderRouteTreeCache.Global().add(subAccount.getSubAccountId()
                    , getRouteTreeNullIfLostFromDB(subAccount.getSubAccountId()));
        }
    }
    
}
