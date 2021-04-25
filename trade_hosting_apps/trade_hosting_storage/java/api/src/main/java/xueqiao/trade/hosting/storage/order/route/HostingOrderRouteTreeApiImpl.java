package xueqiao.trade.hosting.storage.order.route;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.events.OrderRouteTreeEvent;
import xueqiao.trade.hosting.events.OrderRouteTreeEventType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingConfigApi;
import xueqiao.trade.hosting.storage.apis.IHostingOrderRouteTreeApi;
import xueqiao.trade.hosting.storage.apis.structs.ConfigFileEntry;

public class HostingOrderRouteTreeApiImpl implements IHostingOrderRouteTreeApi {
    private static final String CONFIG_AREA = "HSAORT";
    
    private IHostingConfigApi mConfigApi;
    
    public HostingOrderRouteTreeApiImpl(IHostingConfigApi configApi) {
        this.mConfigApi = configApi;
    }
    
    @Override
    public int getTreeVersion(long subAccountId) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        
        ConfigFileEntry configEntry 
            = mConfigApi.getConfigEntry(CONFIG_AREA, String.valueOf(subAccountId));
        if (configEntry == null) {
            return 0;
        }
        return configEntry.getConfigVersion();
    }

    @Override
    public HostingOrderRouteTree getTree(long subAccountId) throws ErrorInfo {
        return HostingOrderRouteTree.class.cast(
                mConfigApi.getConfig(CONFIG_AREA, String.valueOf(subAccountId), HostingOrderRouteTree.class));
    }

    @Override
    public void updateTree(long subAccountId, HostingOrderRouteTree tree) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(subAccountId > 0);
        ParameterChecker.checkInnerNotNull(tree);
        ParameterChecker.checkInnerArgument(tree.isSetConfigVersion() && tree.getConfigVersion() > 0);
        
        OrderRouteTreeEvent event = new OrderRouteTreeEvent();
        event.setType(OrderRouteTreeEventType.ORDER_ROUTE_TREE_CHANGED);
        event.setSubAccountId(subAccountId);
        
        mConfigApi.setConfig(CONFIG_AREA, String.valueOf(subAccountId), tree.getConfigVersion(), tree, event);
    }

    @Override
    public void clearAll() throws ErrorInfo {
        OrderRouteTreeEvent event = new OrderRouteTreeEvent();
        event.setType(OrderRouteTreeEventType.ORDER_ROUTE_TREE_ALL_CLEARD);
        mConfigApi.clearConfigs(CONFIG_AREA, event);
    }
}
