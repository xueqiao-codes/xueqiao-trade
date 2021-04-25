package xueqiao.trade.hosting.dealing.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.dealing.core.ExecIDGenerator;
import xueqiao.trade.hosting.dealing.core.cleaner.ExecOrderDbCleaner;
import xueqiao.trade.hosting.dealing.storage.DealingDBV2;
import xueqiao.trade.hosting.dealing.thriftapi.server.TradeHostingDealingAdaptor;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

public class AppMain {

    private static String[] INIT_DATABASES = new String[]{
            "dealing_v2"
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
        props.put("Adaptor-Class", TradeHostingDealingAdaptor.class.getName());

        int workNum = 20;
        if (Runtime.getRuntime().availableProcessors() > 0) {
            workNum = Runtime.getRuntime().availableProcessors() * 6;
        }

        props.put("workNum", String.valueOf(workNum));
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
        AppLog.init("apps/trade_hosting_dealing");

        try {
            StorageInitialzer.initApis();
            com.longsheng.xueqiao.contractconvertor.LibraryLoader.init();

            initDatabases();
            ExecIDGenerator.Initialize();
            DealingDBV2.Global().init();

            HostingMessageContext.Global().createConsumerAgent("hosting_dealing_data"
                , new DealingDataMessageConsumer());
            HostingMessageContext.Global().createConsumerAgent("hosting_dealing_notify"
                , new DealingNotifyMessageConsumer());

            ExecOrderDbCleaner dbCleaner = new ExecOrderDbCleaner();
            dbCleaner.startWorking();

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_dealing")), 1);

            serve();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }

        Runtime.getRuntime().halt(-1);
    }
}
