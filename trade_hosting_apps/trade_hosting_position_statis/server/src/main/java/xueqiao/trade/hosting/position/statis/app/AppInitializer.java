package xueqiao.trade.hosting.position.statis.app;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.core.cache.BackupAndRecoveryManager;
import xueqiao.trade.hosting.position.statis.core.scheduler.StatJobScheduler;
import xueqiao.trade.hosting.position.statis.daemon.ArchiveDaemon;
import xueqiao.trade.hosting.position.statis.service.handler.InitQuotationHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.quot.comb.sdk.QuotCombDispatcher;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

import java.io.UnsupportedEncodingException;

public class AppInitializer extends Thread {
    public AppInitializer() {
    }

    public void init() {
        this.setName("position_statis_app_initializer");
        this.setPriority(MIN_PRIORITY);
        this.setDaemon(true);
        this.start();
    }

    @Override
    public void run() {
        ensureInit();
    }

    private void ensureInit() {
        /*
         * start daemons
         * */
        initDaemon();

        /*
         * recovery cache data
         * attention: 确保先调用 recoveryData()， 再调用initJobs()
         * */
        while (true) {
            try {
                recoveryData();
                break;
            } catch (ErrorInfo errorInfo) {
                AppReport.reportErr("AppInitializer # recoveryData fail", errorInfo);
                doSleep(1000);
            }
        }

        /*
         * init quotation listener
         * */
        while (true) {
            try {
                initQuotation();
                break;
            } catch (Exception errorInfo) {
                AppReport.reportErr("AppInitializer # initQuotation fail", errorInfo);
                doSleep(1000);
            }
        }

        /*
         * start jobs
         * */
        while (true) {
            try {
                initJobs();
                break;
            } catch (ErrorInfo errorInfo) {
                AppReport.reportErr("AppInitializer # initJobs fail", errorInfo);
                doSleep(1000);
            }
        }

    }

    private void doSleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void initDaemon() {
        new ArchiveDaemon().start();
        /*
         * 数据长期保存，不做删除
         * */
//		new DeleteImportDataDaemon().start();
    }

    private static void initJobs() throws ErrorInfo {
        StatJobScheduler.Global().addQuotationBackupJob();
        StatJobScheduler.Global().addPositionDynamicInfoSenderJob();
        StatJobScheduler.Global().addCleanCacheJob();
    }

    /**
     * 行情数据恢复
     * attention: 确保先调用 recoveryData()， 再调用initJobs()
     */
    private static void recoveryData() throws ErrorInfo {
        /*
         * 恢复行情缓存数据
         * */
        BackupAndRecoveryManager.getInstance().recoveryQuotationCache();

        /*
         * 触发计算持仓动态信息
         * */
        BackupAndRecoveryManager.getInstance().initPositionDynamicInfo();
    }

    /**
     * 初始化行情监听
     */
    private static void initQuotation() throws ErrorInfo, UnsupportedEncodingException {
        QuotationDispatcher.init(AppConfig.APP_NAME);
        QuotCombDispatcher.init(AppConfig.APP_NAME);
        InitQuotationHandler.initQuotationListener();
    }
}
