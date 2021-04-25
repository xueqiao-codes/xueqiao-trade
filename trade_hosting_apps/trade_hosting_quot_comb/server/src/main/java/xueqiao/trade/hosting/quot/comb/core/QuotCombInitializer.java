package xueqiao.trade.hosting.quot.comb.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeView;
import xueqiao.trade.hosting.HostingComposeViewStatus;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryComposeViewOption;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;

public class QuotCombInitializer {
    private IHostingSessionApi mSessionApi;
    private IHostingComposeApi mComposeApi;
    private Map<Long, HostingComposeGraph> mGraphCache = new HashMap<Long, HostingComposeGraph>();
    private QuotCombProcessor mProcessor;
    
    public QuotCombInitializer(QuotCombProcessor processor) {
        mProcessor = processor;
        mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
        mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
    }
    
    private HostingComposeGraph getComposeGraph(long composeGraphId) throws ErrorInfo {
        HostingComposeGraph composeGraph = mGraphCache.get(composeGraphId);
        if(composeGraph != null) {
            return composeGraph;
        }
        composeGraph = mComposeApi.getComposeGraph(composeGraphId);
        if (composeGraph == null) {
            return null;
        }
        mGraphCache.put(composeGraphId, composeGraph);
        return composeGraph;
    }
    
    public void doIntialize() throws ErrorInfo {
        mProcessor.clearSubscribeUsers();
        
        List<SessionEntry> allOnlineSessions = mSessionApi.getAll();
        for (SessionEntry e : allOnlineSessions) {
            QueryComposeViewOption qryOption = new QueryComposeViewOption();
            qryOption.setSubUserId(e.getSubUserId());
            qryOption.setSubscribeStatus(HostingComposeViewSubscribeStatus.SUBSCRIBED);
            qryOption.setViewStatus(HostingComposeViewStatus.VIEW_NORMAL);
            
            PageOption pageOption = new PageOption();
            pageOption.setNeedTotalCount(false);
            pageOption.setPageIndex(0);
            pageOption.setPageSize(Integer.MAX_VALUE);
            
            PageResult<HostingComposeView> subscribedComposeViewPage = mComposeApi.getComposeViewPage(qryOption, pageOption);
            for (HostingComposeView subscribedComposeView : subscribedComposeViewPage.getPageList()) {
                HostingComposeGraph composeGraph = getComposeGraph(subscribedComposeView.getComposeGraphId());
                if (composeGraph == null) {
                    AppLog.w("[WARNING]Compose Graph Not Found for composeGraphId=" + subscribedComposeView.getComposeGraphId());
                    continue;
                }
                
                mProcessor.addSubscribeUserGraph(e.getSubUserId(), composeGraph);
            }
        }
    }
    
}
