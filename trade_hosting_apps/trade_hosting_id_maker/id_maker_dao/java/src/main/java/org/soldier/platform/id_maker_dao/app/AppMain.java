package org.soldier.platform.id_maker_dao.app;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.id_maker_dao.server.IdMakerDaoAdaptor;
import org.soldier.platform.id_maker_dao.storage.IdMakerDB;
import org.soldier.platform.svr_platform.container.ServiceContainer;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

import java.sql.Connection;
import java.util.Properties;

public class AppMain {
    private static String[] INIT_DATABASES = new String[]{
            "id_maker"
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

    public static void main(String[] args) {
        AppLog.init("apps/trade_hosting_id_maker");
        try {
            initDatabases();
            IdMakerDB.Global().init();
            StorageInitialzer.initApis();

            Properties props = new Properties();
            props.put("Adaptor-Class", IdMakerDaoAdaptor.class.getName());
            props.put("workerNum", "2");
            props.put("bindAddress", "127.0.0.1");
            ServiceContainer container = new ServiceContainer() {
                @Override
                protected void willStartService() {
                }
            };

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_id_maker")), 1);

            container.start(props);
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }

        Runtime.getRuntime().halt(-1);
    }
}
