package xueqiao.trade.hosting.tradeaccount.data.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;
import xueqiao.trade.hosting.tradeaccount.data.server.TradeHostingTradeAccountDataAdaptor;
import xueqiao.trade.hosting.tradeaccount.data.storage.TADataDB;

import java.sql.Connection;
import java.util.Properties;

public class AppMain {

    private static String[] INIT_DATABASES = new String[]{
            "ta_data"
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
        props.put("Adaptor-Class", TradeHostingTradeAccountDataAdaptor.class.getName());
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
        AppLog.init("apps/trade_hosting_tradeaccount_data");

        try {
            StorageInitialzer.initApis();
            initDatabases();
            TADataDB.Global().init();

            HostingMessageContext.Global().createConsumerAgent("hosting_tradeaccount_data"
                , new TADataConsumer());

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_tradeaccount_data")), 1);

            serve();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }
        Runtime.getRuntime().halt(-1);
    }
}
