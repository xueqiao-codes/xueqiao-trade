package xueqiao.trade.hosting.asset;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.asset.config.Config;
import xueqiao.trade.hosting.asset.event.handler.AssetNotifyEventHandler;
import xueqiao.trade.hosting.asset.event.handler.AssetSyncEventHandler;
import xueqiao.trade.hosting.asset.calculator.CalculatorThreadManager;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.thriftapi.server.TradeHostingAssetAdaptor;
import xueqiao.trade.hosting.dealing.thriftapi.impl.HostingDealApiClient;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.ProcessUtils;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

public class App {

    private static final String APP_KEY = "hosting_asset";
    private static final String CONSUMEM_AGENT_KEY_SYNC = "hosting_asset_sync";
    private static final String CONSUMER_AGENT_KEY_NOTIFY = "hosting_asset_notify";

    private static  final String[] INIT_DATABASES =new String[]{"asset"};

    private static void serve() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", TradeHostingAssetAdaptor.class.getName());
        UnixSocketServiceContainer serviceContainer = new UnixSocketServiceContainer();
        serviceContainer.start(props);
    }


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


    public static void main(String[] args) throws ErrorInfo {
        ProcessUtils.checkSingleInstance(APP_KEY);

        AppLog.init("apps/trade_hosting_asset");
        AppLogStream.redirectSystemPrint();
        AppLog.i("APP_KEY: " + APP_KEY);
        AppLog.w("service start: " + APP_KEY);

        try {
            Config.getInstance();
            initDatabases();
            AssetDB.Global().init();
            StorageInitialzer.initApis();
            Globals.getInstance().addImplements(new HostingDealApiClient(
                    Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class)
            ));

            QuotationDispatcher.init(APP_KEY);
            MessageAgent.initNativeLogName(APP_KEY);
            CalculatorThreadManager.getInstance();
            HostingMessageContext.Global().createConsumerAgent(CONSUMEM_AGENT_KEY_SYNC, new AssetSyncEventHandler());
            HostingMessageContext.Global().createConsumerAgent(CONSUMER_AGENT_KEY_NOTIFY,new AssetNotifyEventHandler());

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_asset")), 1);


            serve();
            System.exit(1);
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            System.exit(-1);
        }
    }
}
