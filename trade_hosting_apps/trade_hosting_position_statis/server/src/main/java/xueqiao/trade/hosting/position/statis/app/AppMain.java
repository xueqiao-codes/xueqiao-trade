package xueqiao.trade.hosting.position.statis.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.position.statis.msgbus.PositionStatisMessageConsumer;
import xueqiao.trade.hosting.position.statis.server.TradeHostingPositionStatisAdaptor;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

public class AppMain {

    private static final String APP_KEY = AppConfig.APP_NAME;
    private static String[] INIT_DATABASES = new String[]{
            "position_statis"
    };

    public static void main(String[] args) {
        AppLog.init("apps/trade_hosting_position_statis");
        AppLog.i("trade_hosting_position_statis ###### starting...");

        try {
            initDatabases();
            StorageInitialzer.initApis();
            PositionStatisDB.Global().init();
            HostingMessageContext.Global().createConsumerAgent("hosting_position_statis"
                    , new PositionStatisMessageConsumer());

            // app init
            new AppInitializer().init();

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_position_statis")), 1);

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
        props.put("Adaptor-Class", TradeHostingPositionStatisAdaptor.class.getName());
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