package xueqiao.trade.hosting.push.server.core;

import org.soldier.base.beanfactory.Globals;

import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.ext.HostingSubAccountRelatedCache;

public class SubAccountRelatedCacheHolder {
    
    private static HostingSubAccountRelatedCache sSubAccountRelatedCache;
    
    public static HostingSubAccountRelatedCache get() {
        if (sSubAccountRelatedCache == null) {
            synchronized(SubAccountRelatedCacheHolder.class) {
                if (sSubAccountRelatedCache == null) {
                    sSubAccountRelatedCache = new HostingSubAccountRelatedCache(
                            Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class));
                }
            }
        }
        return sSubAccountRelatedCache;
    }
}
