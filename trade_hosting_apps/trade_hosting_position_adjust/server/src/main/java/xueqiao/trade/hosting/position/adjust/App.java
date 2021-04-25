package xueqiao.trade.hosting.position.adjust;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.utils.ProcessUtils;
import xueqiao.trade.hosting.position.adjust.config.Config;
import xueqiao.trade.hosting.position.adjust.job.AssignPositionJobScheduler;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.position.adjust.thriftapi.server.TradeHostingPositionAdjustAdaptor;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

public class App {

    private static final String APP_KEY = "hosting_position_adjust";
    private static final String[] INIT_DATABASES = new String[]{"position_adjust"};

    private static void serve() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", TradeHostingPositionAdjustAdaptor.class.getName());
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

        AppLog.init("apps/trade_hosting_position_adjust");
        AppLogStream.redirectSystemPrint();
        AppLog.i("APP_KEY: " + APP_KEY);
        AppLog.w("service start: " + APP_KEY);
        try {
            Config.getInstance();
            initDatabases();
            PositionAdjustDB.Global().init();
            StorageInitialzer.initApis();
            MessageAgent.initNativeLogName(APP_KEY);
            AssignPositionJobScheduler.Global();
            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_position_adjust")), 1);
            AppLog.i("====server start====");
            serve();
            System.exit(1);
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            System.exit(-1);
        }
    }
}
