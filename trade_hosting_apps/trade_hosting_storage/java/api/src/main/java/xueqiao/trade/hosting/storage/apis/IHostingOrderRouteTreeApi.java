package xueqiao.trade.hosting.storage.apis;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingOrderRouteTree;

public interface IHostingOrderRouteTreeApi {
    
    public int getTreeVersion(long subAccountId) throws ErrorInfo;
    
    public HostingOrderRouteTree getTree(long subAccountId) throws ErrorInfo;
    
    public void updateTree(long subAccountId, HostingOrderRouteTree tree) throws ErrorInfo;
    
    public void clearAll() throws ErrorInfo;
    
}
