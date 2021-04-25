package xueqiao.trade.hosting.arbitrage.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.arbitrage.core.IDGenerator;
import xueqiao.trade.hosting.arbitrage.core.XQEffectOrderCleaner;
import xueqiao.trade.hosting.arbitrage.core.composelimit.ComposeSettingsProvider;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.thriftapi.server.TradeHostingArbitrageAdaptor;
import xueqiao.trade.hosting.arbitrage.thriftapi.trade_hosting_arbitrageConstants;
import xueqiao.trade.hosting.dealing.thriftapi.impl.HostingDealApiClient;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

public class AppMain {
    private static String[] INIT_DATABASES = new String[]{
            "arbitrage"
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
        props.put("Adaptor-Class", TradeHostingArbitrageAdaptor.class.getName());
        UnixSocketServiceContainer storageContainer = new UnixSocketServiceContainer() {
            @Override
            protected boolean willServe() {
                SvrConfiguration.setLogItemMaxLength(4096);
                return true;
            }
        };
        storageContainer.start(props);
    }
    
    public static void main(String[] args) {
        AppLog.init("apps/trade_hosting_arbitrage");
        try {
            StorageInitialzer.initApis();
            Globals.getInstance().addImplements(new HostingDealApiClient(
                    Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class)
            ));

            initDatabases();
            ArbitrageDB.Global().init();
            IDGenerator.Initialize();
            QuotationDispatcher.init("trade_hosting_arbitrage");

            // PRELOAD
            ComposeSettingsProvider.get(trade_hosting_arbitrageConstants.SYSTEM_SETTING_KEY);

            HostingMessageContext.Global().createConsumerAgent("hosting_arbitrage"
                    , new ArbitrageMessageConsumer());
            XQEffectOrderCleaner orderCleaner = new XQEffectOrderCleaner();
            orderCleaner.startWorking();

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_arbitrage")), 1);
            
            serve();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }
        Runtime.getRuntime().halt(-1);
    }
}
