package xueqiao.trade.hosting.storage.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import org.soldier.watcher.file.FileWatcherModule;
import xueqiao.trade.hosting.dealing.thriftapi.impl.HostingDealApiClient;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.ProcessUtils;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;
import xueqiao.trade.hosting.storage.comm.ComposeDB;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.comm.SessionDB;
import xueqiao.trade.hosting.storage.thriftapi.server.TradeHostingStorageAdaptor;

import java.sql.Connection;
import java.util.Properties;

public class AppMain {
	private static String[] INIT_DATABASES = new String[]{
			"hosting", "session",  "compose"
	};
	
	private static void initDatabases() throws Exception {
		Connection conn = null;
		try {
			conn = DBSingleConnection.getNoneDbConnection();
			for (String database : INIT_DATABASES) {
				StringBuilder sqlBuilder = new StringBuilder(128);
				sqlBuilder.append("CREATE DATABASE ")
						  .append(" IF NOT EXISTS ")
						  .append(database)
						  .append(";");
				new QueryRunner().update(conn, sqlBuilder.toString());
			}
		} finally {
			DbUtils.closeQuietly(conn);
		}
	}

	private static void serve() throws Exception {
		Properties props = new Properties();
		props.put("Adaptor-Class", TradeHostingStorageAdaptor.class.getName());
		if (Runtime.getRuntime().availableProcessors() > 0) {
			props.put("workNum", String.valueOf(4 * Runtime.getRuntime().availableProcessors()));
		} else {
			props.put("workNum", "10");
		}
		UnixSocketServiceContainer storageContainer = new UnixSocketServiceContainer();
		storageContainer.start(props);
	}


	
	public static void main(String[] args) {
	    ProcessUtils.checkSingleInstance("trade_hosting_storage_daemon");
	    FileWatcherModule.Instance().initNativeLog("trade_hosting_storage_daemon");
		AppLog.init("apps/trade_hosting_storage_daemon");
		try {
			MessageAgent.initNativeLogName("trade_hosting_storage_daemon");
			initDatabases();
			HostingDB.Global().init();
			SessionDB.Global().init();
			ComposeDB.Global().init();
			
			StorageInitialzer.initApis();
			Globals.getInstance().addImplements(new HostingDealApiClient(
					Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class)));

			HostingMessageContext.Global().createConsumerAgent(
					"hosting_session_checker", new SessionMessageHandler());
			HostingMessageContext.Global().createConsumerAgent(
					"hosting_storage", new StorageMessageHandler());
			
			StorageTimerScheduler.start();

			AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
					"xueqiao.trade.hosting.process.keepalive"
					, ImmutableMap.of("processname", "trade_hosting_storage_daemon")), 1);

			serve();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}

		Runtime.getRuntime().halt(-1);
	}
}
