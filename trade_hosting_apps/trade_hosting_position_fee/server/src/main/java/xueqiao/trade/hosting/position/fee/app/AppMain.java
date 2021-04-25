package xueqiao.trade.hosting.position.fee.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.position.fee.msgbus.PositionFeeMessageConsumer;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.thriftapi.server.TradeHostingPositionFeeAdaptor;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

import static xueqiao.trade.hosting.position.fee.app.AppConfig.APP_NAME;
import static xueqiao.trade.hosting.position.fee.app.AppConfig.MESSAGE_CONSUMER_KEY;
import static xueqiao.trade.hosting.position.fee.app.AppConfig.MODULE_NAME;

public class AppMain {
    private static final String APP_KEY = APP_NAME;
    private static String[] INIT_DATABASES = new String[]{
            APP_NAME
    };

    public static void main(String[] args) {
        AppLog.init("apps/" + MODULE_NAME);
        AppLog.i(MODULE_NAME + " ###### starting...");

        try {
            initDatabases();
            StorageInitialzer.initApis();
            PositionFeeDB.Global().init();
            HostingMessageContext.Global().createConsumerAgent(MESSAGE_CONSUMER_KEY, new PositionFeeMessageConsumer());

            // app init
            new AppInitializer().init();

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", MODULE_NAME)), 1);
            serve();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            System.exit(-1);
        }
        System.exit(1);
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

    private static void serve() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", TradeHostingPositionFeeAdaptor.class.getName());
        UnixSocketServiceContainer storageContainer = new UnixSocketServiceContainer() {
            @Override
            protected boolean willServe() {
                SvrConfiguration.setLogItemMaxLength(4096);
                return true;
            }
        };
        storageContainer.start(props);
    }
}
