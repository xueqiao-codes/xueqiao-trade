package xueqiao.trade.hosting.risk.manager.app;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.comm.SvrConfiguration;
import org.soldier.platform.svr_platform.container.ServiceContainer;
import org.soldier.platform.svr_platform.container.UnixSocketServiceContainer;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealing;
import xueqiao.trade.hosting.risk.dealing.thriftapi.server.TradeHostingRiskDealingAdaptor;
import xueqiao.trade.hosting.risk.manager.core.RiskManagerFactory;
import xueqiao.trade.hosting.risk.manager.thriftapi.server.TradeHostingRiskManagerAdaptor;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QuerySubAccountOption;

import java.util.Properties;

public class AppMain {

    private static Thread managerThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                AppLog.i("start manager thread...");
                runManager();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    });

    private static Thread dealingThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                AppLog.i("start dealing thread...");
                runDealing();
            } catch (Throwable e) {
                AppLog.e(e.getMessage());
            }
        }
    });

    private static void runManager() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", TradeHostingRiskManagerAdaptor.class.getName());
        UnixSocketServiceContainer storageContainer = new UnixSocketServiceContainer() {
            @Override
            protected boolean willServe() {
                SvrConfiguration.setLogItemMaxLength(4096);
                return true;
            }
        };
        storageContainer.start(props);
    }

    private static void runDealing() throws Exception {
        Properties props = new Properties();
        props.put("Adaptor-Class", TradeHostingRiskDealingAdaptor.class.getName());
        UnixSocketServiceContainer storageContainer = new UnixSocketServiceContainer() {
            @Override
            protected boolean willServe() {
                SvrConfiguration.setLogItemMaxLength(4096);
                return true;
            }
        };
        storageContainer.start(props);
    }

    private static void runLoop() {
        managerThread.setName("manager_main");
        dealingThread.setName("dealing_main");
        managerThread.setDaemon(true);
        dealingThread.setDaemon(true);

        managerThread.start();
        dealingThread.start();

        while(true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            if (!managerThread.isAlive()) {
                AppLog.f("manager_main exited!");
                break;
            }
            if (!dealingThread.isAlive()) {
                AppLog.f("dealing_main exited!");
                break;
            }

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
            }
        }
    }

    private static void initializeRiskManagers() throws ErrorInfo {
        IHostingSubAccountApi subAccountApi
                = Globals.getInstance().queryInterfaceForSure(IHostingSubAccountApi.class);

        PageResult<HostingSubAccount> allHostingSubAccountPage =
                subAccountApi.getSubAccounts(new QuerySubAccountOption()
                        , new PageOption().setPageIndex(0).setPageSize(Integer.MAX_VALUE));
        for (HostingSubAccount subAccount : allHostingSubAccountPage.getPageList()) {
            RiskManagerFactory.Global().addRiskManager(subAccount.getSubAccountId());
        }
    }

    public static void main(String[] args) {
        AppLog.init("apps/trade_hosting_risk_manager");

        try {
            StorageInitialzer.initApis();
            initializeRiskManagers();

            runLoop();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }

        Runtime.getRuntime().halt(-1);
    }
}
