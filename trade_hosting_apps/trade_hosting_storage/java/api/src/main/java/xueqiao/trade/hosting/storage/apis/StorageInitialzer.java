package xueqiao.trade.hosting.storage.apis;

import org.soldier.base.beanfactory.Globals;

import xueqiao.trade.hosting.storage.compose.HostingComposeApiImpl;
import xueqiao.trade.hosting.storage.config.HostingConfigApiImpl;
import xueqiao.trade.hosting.storage.contract.HostingContractApiImpl;
import xueqiao.trade.hosting.storage.manage.HostingManageApiImpl;
import xueqiao.trade.hosting.storage.order.route.HostingOrderRouteTreeApiImpl;
import xueqiao.trade.hosting.storage.session.HostingSessionApiImpl;
import xueqiao.trade.hosting.storage.subaccount.HostingSubAccountApiImpl;
import xueqiao.trade.hosting.storage.tradeaccount.BrokerAccessEntryProviderImpl;
import xueqiao.trade.hosting.storage.tradeaccount.HostingTradeAccountApiImpl;
import xueqiao.trade.hosting.storage.user.HostingUserApiImpl;

public class StorageInitialzer {
	
	private static HostingUserApiImpl hostingUserApiImpl;
	private static HostingSessionApiImpl hostingSessionApiImpl;
	private static HostingManageApiImpl hostingManageApiImpl;
	private static HostingComposeApiImpl hostingComposeApiImpl;
	private static HostingTradeAccountApiImpl hostingTradeAccountApiImpl;
	private static BrokerAccessEntryProviderImpl brokerAccessEntryProviderImpl;
	private static HostingConfigApiImpl hostingConfigApiImpl;
	private static HostingOrderRouteTreeApiImpl hostingOrderRouteTreeApiImpl;
	private static HostingContractApiImpl hostingContractApiImpl;
	private static HostingSubAccountApiImpl hostingSubAccountApiImpl;
	
	public static void initApis() throws Exception {
		if (hostingUserApiImpl != null) {
			return ;
		}
		
		hostingManageApiImpl = new HostingManageApiImpl();
		hostingSessionApiImpl = new HostingSessionApiImpl();
		hostingUserApiImpl = new HostingUserApiImpl();
		hostingComposeApiImpl = new HostingComposeApiImpl();
		hostingTradeAccountApiImpl = new HostingTradeAccountApiImpl();
		brokerAccessEntryProviderImpl = new BrokerAccessEntryProviderImpl();
		hostingConfigApiImpl = new HostingConfigApiImpl();
		hostingOrderRouteTreeApiImpl = new HostingOrderRouteTreeApiImpl(hostingConfigApiImpl);
		hostingContractApiImpl = new HostingContractApiImpl();
		hostingSubAccountApiImpl = new HostingSubAccountApiImpl();
		
		Globals.getInstance().addImplements(hostingUserApiImpl
				, hostingManageApiImpl
				, hostingSessionApiImpl
				, hostingComposeApiImpl
				, hostingTradeAccountApiImpl
				, brokerAccessEntryProviderImpl
				, hostingConfigApiImpl
				, hostingOrderRouteTreeApiImpl
				, hostingContractApiImpl
				, hostingSubAccountApiImpl);
	}
	
	
}
