package xueqiao.trade.hosting.quot.comb.core;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeView;
import xueqiao.trade.hosting.HostingComposeViewStatus;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;
import xueqiao.trade.hosting.events.ComposeViewEvent;
import xueqiao.trade.hosting.events.ComposeViewEventType;
import xueqiao.trade.hosting.events.LandingStatusChangedEvent;
import xueqiao.trade.hosting.quot.dispatcher.client.THQDClient;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryComposeViewOption;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;

public class QuotCombMessageConsumer implements IMessageConsumer {
    private IHostingComposeApi mComposeApi;
    private IHostingSessionApi mSessionApi;
    
    public QuotCombMessageConsumer() throws ErrorInfo {
        mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
        mSessionApi = Globals.getInstance().queryInterface(IHostingSessionApi.class);
    }
    
    @Override
    public StartUpMode onStartUp() {
        return StartUpMode.CLEAR_QUEUE_INIT;
    }

    @Override
    public void onInit() throws Exception {
        new QuotCombInitializer(QuotCombProcessor.Global()).doIntialize();
    }
    
    @consume(LandingStatusChangedEvent.class) 
    ConsumeResult onHandleLandingStatusChangedEvent(LandingStatusChangedEvent event) throws Exception{
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleLandingStatusChangedEvent received event=" + event);
        }
        
        if (event.getSubUserId() <= 0) {
            AppLog.e("onHandleLandingStatusChangedEvent called, but event's subUserId<= 0");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        SessionEntry sessionEntry = mSessionApi.getSession(event.getSubUserId());
        QueryComposeViewOption qryOption = new QueryComposeViewOption();
        qryOption.setSubUserId(event.getSubUserId());
        qryOption.setSubscribeStatus(HostingComposeViewSubscribeStatus.SUBSCRIBED);
        qryOption.setViewStatus(HostingComposeViewStatus.VIEW_NORMAL);
        
        PageOption pageOption = new PageOption();
        pageOption.setNeedTotalCount(false);
        pageOption.setPageIndex(0);
        pageOption.setPageSize(Integer.MAX_VALUE);
        
        PageResult<HostingComposeView> subscribedComposeViewPage = mComposeApi.getComposeViewPage(qryOption, pageOption);
        for (HostingComposeView subscribedComposeView : subscribedComposeViewPage.getPageList()) {
            if (sessionEntry != null) {
                HostingComposeGraph composeGraph = HostingComposeGraphHolder.Global()
                        .getComposeGraph(subscribedComposeView.getComposeGraphId());
                if (composeGraph == null) {
                    continue;
                }
                QuotCombProcessor.Global().addSubscribeUserGraph(event.getSubUserId(), composeGraph);
            } else {
                QuotCombProcessor.Global().removeSubscribeUserGraph(event.getSubUserId(), subscribedComposeView.getComposeGraphId());
            }
        }
        
        return ConsumeResult.CONSUME_OK;
    }
    
    @consume(ComposeViewEvent.class)
    ConsumeResult onHandleComposeViewEvent(ComposeViewEvent event) throws Exception{
        if (AppLog.infoEnabled()) {
            AppLog.i("onHandleComposeViewEvent received event=" + event);
        }
        
        if (!event.isSetType()) {
            AppLog.e("onHandleComposeViewEvent called, but event's type not set");
            return ConsumeResult.CONSUME_FAILED_DROP;
        }
        
        if (event.getType() == ComposeViewEventType.COMPOSE_VIEW_ALL_CLEARED) {
            new QuotCombInitializer(QuotCombProcessor.Global()).doIntialize();
            return ConsumeResult.CONSUME_OK;
        }
        
        if (event.getType() == ComposeViewEventType.COMPOSE_VIEW_SUBSCRIBED
            || event.getType() == ComposeViewEventType.COMPOSE_VIEW_UNSUBSCRIBD
            || event.getType() == ComposeViewEventType.COMPOSE_VIEW_DELETED) {
            if (event.getComposeGraphId() <= 0) {
                AppLog.e("onHandleComposeViewEvent called, event type=" + event.getType() + ", but composeGraphId < = 0");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }
            if (!event.isSetSubUserIds() || event.getSubUserIds().isEmpty()) {
                AppLog.e("onHandleComposeViewEvent called, eventType=" + event.getType() + ", but subUserIds not set or empty!");
                return ConsumeResult.CONSUME_FAILED_DROP;
            }
            
            HostingComposeGraph composeGraph = mComposeApi.getComposeGraph(event.getComposeGraphId());
            if (composeGraph == null) {
                AppLog.w("[WARNING]Compose graph not found, composeGraphId=" + event.getComposeGraphId());
                return ConsumeResult.CONSUME_FAILED_DROP;
            }

            for (Integer subUserId : event.getSubUserIds()) {
                HostingComposeView composeView = mComposeApi.getComposeView(subUserId, event.getComposeGraphId());
                if (composeView == null
                        || composeView.getSubscribeStatus() == HostingComposeViewSubscribeStatus.UNSUBSCRIBED
                        || composeView.getViewStatus() == HostingComposeViewStatus.VIEW_DELETED) {
                    QuotCombProcessor.Global().removeSubscribeUserGraph(subUserId, event.getComposeGraphId());
                } else {
                    QuotCombProcessor.Global().addSubscribeUserGraph(subUserId, composeGraph);
                }
            }
        }
        
        return ConsumeResult.CONSUME_OK;
    }

}
