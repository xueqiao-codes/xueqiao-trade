package xueqiao.trade.hosting.entry;

import java.util.Properties;

import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.soldier.base.Assert;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.container.ServiceContainer;

import org.w3c.dom.Attr;
import xueqiao.trade.hosting.cloud.ao.server.TradeHostingCloudAoAdaptor;
import xueqiao.trade.hosting.entry.core.CNYRateManager;
import xueqiao.trade.hosting.entry.core.EntryMessageHandler;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.ProcessUtils;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;
import xueqiao.trade.hosting.terminal.ao.server.TradeHostingTerminalAoAdaptor;

public class App {
	
	private static class AppServiceContainer extends ServiceContainer {
		@Override
		protected void willStartService() {
		}
	}
	
	private Thread cloudAoThread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				AppLog.i("start cloud ao thread...");
				runTradeHostingCloudAo();
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	});
	
	private Thread terminalAoThread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				AppLog.i("start terminal ao thread...");
				runTradeHostingTerminalAo();
			} catch (Throwable e) {
				AppLog.e(e.getMessage());
			}
		}
	});
	
	private void runTradeHostingCloudAo() throws Exception {
		Properties props = new Properties();
		props.put("Adaptor-Class", TradeHostingCloudAoAdaptor.class.getName());
		
		ServiceContainer container = new AppServiceContainer();
		container.start(props);
	}
	
	private void runTradeHostingTerminalAo() throws Exception {
		Properties props = new Properties();
		props.put("Adaptor-Class", TradeHostingTerminalAoAdaptor.class.getName());
		
		ServiceContainer container = new AppServiceContainer();
		container.start(props);;
	}
	
	public void runLoop() {
		cloudAoThread.setName("cloud_ao_main");
		terminalAoThread.setName("terminal_ao_main");
		cloudAoThread.setDaemon(true);
		terminalAoThread.setDaemon(true);
		
		cloudAoThread.start();
		terminalAoThread.start();
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			
			if (!cloudAoThread.isAlive()) {
				AppLog.f("cloud_ao_main exited!");
				break;
			}
			if (!terminalAoThread.isAlive()) {
				AppLog.f("terminal_ao_main exited!");
				break;
			}
			
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
		}
	}
	
	public static void main(String[] args) {
	    ProcessUtils.checkSingleInstance("trade_hosting_entry");
	    
		AppLog.init("apps/trade_hosting_entry");
		AppLogStream.redirectSystemPrint();
		Assert.SetLogger(Logger.getLogger(Assert.class));
		
		try {
			StorageInitialzer.initApis();
			MessageAgent.initNativeLogName("trade_hosting_entry");
			HostingMessageContext.Global()
				.createConsumerAgent("hosting_entry", new EntryMessageHandler());

			CNYRateManager.Global();  // 提前加载

			AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
					"xueqiao.trade.hosting.process.keepalive"
					, ImmutableMap.of("processname", "trade_hosting_entry")), 1);

		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			Runtime.getRuntime().halt(-1);
		}
		
		new App().runLoop();
		Runtime.getRuntime().halt(-1);
	}
}
