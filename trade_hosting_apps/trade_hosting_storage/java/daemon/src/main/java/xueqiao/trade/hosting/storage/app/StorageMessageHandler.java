package xueqiao.trade.hosting.storage.app;

import org.apache.commons.io.FileUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingInfo;
import xueqiao.trade.hosting.HostingStatus;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;
import xueqiao.trade.hosting.events.HostingEvent;
import xueqiao.trade.hosting.events.TradeAccountEvent;
import xueqiao.trade.hosting.events.TradeAccountEventType;
import xueqiao.trade.hosting.history.thriftapi.helper.HistoryStubFactory;
import xueqiao.trade.hosting.position.adjust.thriftapi.helper.PositionAdjustStubFactory;
import xueqiao.trade.hosting.position.statis.helper.PositionStatisStubFactory;
import xueqiao.trade.hosting.storage.account.BrokerAccessEntryWatcher;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingOrderRouteTreeApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.IHostingUserApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;
import xueqiao.trade.hosting.tradeaccount.data.helper.TradeAccountDataStubFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class StorageMessageHandler implements IMessageConsumer {
	private IHostingManageApi mHostingManageApi;
	private IHostingTradeAccountApi mTradeAccountApi;
	private BrokerAccessEntryWatcher mBrokerWatcher;
	
	private void syncWatchAccounts() throws ErrorInfo {
		AppLog.i("syncWatchAccounts...");
		PageResult<HostingTradeAccount>  totalAccountsPage 
			= mTradeAccountApi.queryTradeAccountPage(new QueryTradeAccountOption()
				, new PageOption().setPageIndex(0).setPageSize(Integer.MAX_VALUE));

		Set<BrokerAccessEntryWatcher.WatchEntry> watchEntries = new HashSet<>();
		for (HostingTradeAccount tradeAccount : totalAccountsPage.getPageList()) {
			BrokerAccessEntryWatcher.WatchEntry w = new BrokerAccessEntryWatcher.WatchEntry();
			w.setBrokerId(tradeAccount.getTradeBrokerId());
			w.setBrokerAccessId(tradeAccount.getTradeBrokerAccessId());
			watchEntries.add(w);
		}
		mBrokerWatcher.syncWatchEntries(watchEntries);
	}
	
	public StorageMessageHandler() {
		mTradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
		mHostingManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
		mBrokerWatcher = new BrokerAccessEntryWatcher();
	}
	
	@Override
	public StartUpMode onStartUp() {
		return StartUpMode.CLEAR_QUEUE_INIT;
	}
	
	@Override
	public void onInit() throws Exception {
	    ensureHostingEnvironment();
		syncWatchAccounts();
	}
	
	@consume(TradeAccountEvent.class)
	public ConsumeResult onHandleTradeAccountEvent(TradeAccountEvent event) throws Exception {
	    if (AppLog.infoEnabled()) {
            AppLog.i("onHandleTradeAccountEvent received " + event);
        }
	    
		if (event.getType() == null) {
			AppLog.w("received null type trade account event! drop it!");
			return ConsumeResult.CONSUME_FAILED_DROP;
		}
		
		if (event.getType() != TradeAccountEventType.TRADE_ACCOUNT_ALL_CLEARD && event.getTradeAccountId() <= 0) {
		    AppLog.w("received invalid trade account event! drop it ! event = " + event);
		    return ConsumeResult.CONSUME_FAILED_DROP;
		}
		
		if (event.getType() == TradeAccountEventType.TRADE_ACCOUNT_ADDED
				|| event.getType() == TradeAccountEventType.TRADE_ACCOUNT_DELETED
				|| event.getType() == TradeAccountEventType.TRADE_ACCOUNT_ALL_CLEARD
				|| event.getType() == TradeAccountEventType.TRADE_ACCOUNT_INFO_UPDATED) {
		    syncWatchAccounts();
		}
		
		return ConsumeResult.CONSUME_OK;
	}
	
	private void ensureHostingEnvironment() throws Exception {
	    AppLog.i("ensureHostingEnvironment...");
	    HostingInfo hostingInfo = mHostingManageApi.getHostingInfo(false);
        if (hostingInfo.getStatus() == HostingStatus.NORMAL) {
            // 保证数据目录被创建
            File hostingDataDir = mHostingManageApi.getHostingDataDir();
            if (hostingDataDir != null && !hostingDataDir.exists()) {
                if (!hostingDataDir.mkdirs()) {
                    throw new Exception("mkdirs failed for " + hostingDataDir.getAbsolutePath());
                }
            }
        } else if (hostingInfo.getStatus() == HostingStatus.CLEARING){
            // 清理所有的数据
            Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class).clearAll();
            Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class).clearAll();
            Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class).clearAll();
            Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class).clearAll();
            Globals.getInstance().queryInterfaceForSure(IHostingOrderRouteTreeApi.class).clearAll();
            Globals.getInstance().queryInterfaceForSure(IHostingUserApi.class).clearAll();
            Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class).clearAll();

            // 清空业务模块数据
			ArbitrageStubFactory.getStub().clearAll();
			AssetStubFactory.getStub().removeAllAssetData();
			HistoryStubFactory.getStub().clearAll();
			TradeAccountDataStubFactory.getStub().clearAll();
			PositionAdjustStubFactory.getStub().clearAll();
			PositionStatisStubFactory.getStub().clearAll();
            
            File hostingDataDir = mHostingManageApi.getHostingDataDir();
            if (hostingDataDir != null && hostingDataDir.exists() && hostingDataDir.isDirectory()) {
                FileUtils.deleteDirectory(hostingDataDir);
            }

            // 先让业务进行清理，之后需要运维操作确认， 清理完成标志由运维操作确认
			// mHostingManageApi.clearFinished();
        }
	}
	
	@consume(HostingEvent.class)
	public ConsumeResult onHandleHostingEvent(HostingEvent event) throws Exception {
	    if (AppLog.infoEnabled()) {
	        AppLog.i("onHandleHostingEvent received " + event);
	    }
	    ensureHostingEnvironment();
        return ConsumeResult.CONSUME_OK;
	}
}
