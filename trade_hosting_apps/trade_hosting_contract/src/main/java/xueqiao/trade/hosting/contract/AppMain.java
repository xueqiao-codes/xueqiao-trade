package xueqiao.trade.hosting.contract;

import java.sql.Connection;
import java.util.Properties;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.base.logger.AppLogStream;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.container.ServiceContainer;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;

import com.longsheng.xueqiao.contract.online.dao.thriftapi.server.ContractOnlineDaoServiceAdaptor;

import xueqiao.trade.hosting.contract.ContractSyncProcessor.SyncResult;
import xueqiao.trade.hosting.contract.api.ContractApiImpl;
import xueqiao.trade.hosting.contract.api.ContractDataCache;
import xueqiao.trade.hosting.contract.api.ContractInfoDB;
import xueqiao.trade.hosting.contract.api.ContractOnlineDaoProxy;
import xueqiao.trade.hosting.contract.api.ContractVersionEntry;
import xueqiao.trade.hosting.framework.DBSingleConnection;
import xueqiao.trade.hosting.framework.utils.ProcessUtils;

public class AppMain {
    
    private static String[] INIT_DATABASES = new String[]{
            "cinfo"
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
    
    private static Thread contractForHostingThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                AppLog.i("start contract for hosting thread...");
                runContractForHostingService();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    });
    
    private static Thread contractForTerminalThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                AppLog.i("start contract for terminal thread...");
                runContractForTerminalService();
            } catch (Throwable e) {
                AppLog.e(e.getMessage());
            }
        }
    });
    
    private static void runContractForHostingService() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", ContractOnlineDaoServiceAdaptor.class.getName());
        props.put("Adaptor-Class-Impl", ContractOnlineDaoProxy.class.getName());
        UnixSocketServiceContainer container = new UnixSocketServiceContainer();
        container.start(props);
    }
    
    private static void runContractForTerminalService() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", ContractOnlineDaoServiceAdaptor.class.getName());
        props.put("workNum", 4);
        
        ServiceContainer container = new ServiceContainer() {
            @Override
            protected void willStartService() {
            }
        };
        container.start(props);
    }
    
    public static void runLoop() {
        contractForHostingThread.setName("contract_for_hosting");
        contractForTerminalThread.setName("contract_for_terminal");
        contractForHostingThread.setDaemon(true);
        contractForTerminalThread.setDaemon(true);
        
        contractForHostingThread.start();
        contractForTerminalThread.start();
        
        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            
            if (!contractForHostingThread.isAlive()) {
                AppLog.f("contract_for_hosting exited!");
                break;
            }
            if (!contractForTerminalThread.isAlive()) {
                AppLog.f("terminal_ao_main exited!");
                break;
            }
            
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
        }
    }
    
    private static void startContractSync() {
        // 60S检测一次合约的更新
        Thread contractSyncThread = new Thread() {
            @Override
            public void run() {
                while(true) {
                    ContractSyncProcessor processor = new ContractSyncProcessor();
                    while(true) {
                        try {
                            AppLog.i("sync contract run...");
                            SyncResult syncResult = processor.syncOnce();
                            if (!syncResult.isUpdated()) {
                                AppLog.i("clean contract old dbs...");
                                processor.cleanOldDBs();
                                Thread.sleep(60000);
                            } else {
                                ContractDB needReleaseDB 
                                    = ContractConnectionProviderFactory.Global().setCurrentVersion(syncResult.getNewVersion());
                                ContractDataCache.Global().clearAll();
                                try {
                                    Thread.sleep(30000);
                                } catch (InterruptedException e1) {
                                }
                                if (needReleaseDB != null) {
                                    AppLog.i("release ContractDB for version=" + needReleaseDB.getContractVersion());
                                    needReleaseDB.destory();
                                }
                                Thread.sleep(30000);
                                processor.cleanOldDBs();
                            }
                        } catch (Throwable e) {
                            AppLog.e(e.getMessage(), e);
                            try {
                                Thread.sleep(15000);
                            } catch (InterruptedException e1) {
                            }
                        }
                    }
                }
            }
        };
        contractSyncThread.setName("contract_sync");
        contractSyncThread.setDaemon(true);
        contractSyncThread.start();
    }
    
    public static void main(String[] args) {
        ProcessUtils.checkSingleInstance("trade_hosting_cotract");
        AppLog.init("apps/trade_hosting_contract");
        AppLogStream.redirectSystemPrint();
        try {
            initDatabases();
            ContractInfoDB.Global().init();
            
            ContractApiImpl apiImpl = new ContractApiImpl();
            Globals.getInstance().addImplements(apiImpl);
            
            ContractVersionEntry entry = apiImpl.getLastestVersion();
            if (entry != null && entry.getLastestVersion() > 0) {
                ContractConnectionProviderFactory.Global().setCurrentVersion(entry.getLastestVersion());
            }
            
            Globals.getInstance().addImplements(ContractConnectionProviderFactory.Global());
            startContractSync();

            AttrReporterFactory.getDefault().keep(AttrReporterFactory.getDefault().requireKey(
                    "xueqiao.trade.hosting.process.keepalive"
                    , ImmutableMap.of("processname", "trade_hosting_contract")), 1);

        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            Runtime.getRuntime().halt(-1);
        }
        
        runLoop();
        Runtime.getRuntime().halt(-1);
    }
}
