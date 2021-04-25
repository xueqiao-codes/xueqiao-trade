package xueqiao.trade.hosting.position.fee.app;

import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.position.fee.core.api.HostingManageApi;
import xueqiao.trade.hosting.position.fee.core.calculator.AllTradeAccountPositionRateCalculator;
import xueqiao.trade.hosting.position.fee.core.daemon.DeleteExpiredContractPositionRateDaemon;
import xueqiao.trade.hosting.position.fee.core.daemon.UpdateUpsideContractPositionRateDaemon;
import xueqiao.trade.hosting.position.fee.core.daemon.UpdateXQContractCommissionDaemon;
import xueqiao.trade.hosting.position.fee.core.daemon.UpdateXQContractMarginDaemon;

import static xueqiao.trade.hosting.position.fee.core.daemon.DeleteExpiredContractPositionRateDaemon.DELETE_EXPIRED_CONTRACT_POSITION_RATE_PERIOD;
import static xueqiao.trade.hosting.position.fee.core.daemon.UpdateUpsideContractPositionRateDaemon.UPDATE_UPSIDE_CONTRACT_POSITION_RATE_PERIOD;
import static xueqiao.trade.hosting.position.fee.core.daemon.UpdateXQContractCommissionDaemon.UPDATE_XQ_CONTRACT_COMMISSION_PERIOD;
import static xueqiao.trade.hosting.position.fee.core.daemon.UpdateXQContractMarginDaemon.UPDATE_XQ_CONTRACT_MARGIN_PERIOD;

public class AppInitializer extends Thread {
    public void init() throws ErrorInfo {
        AppLog.i("init ......");

        // get techPlatformEnv
        getHostingTechPlatformEnv();

        start();
    }

    private void getHostingTechPlatformEnv() throws ErrorInfo {
        HostingRunningMode hostingRunningMode = HostingManageApi.queryHostingInfo();
        if (hostingRunningMode == HostingRunningMode.REAL_HOSTING) {
            AppData.setTechPlatformEnv(TechPlatformEnv.REAL);
        }
        AppData.setTechPlatformEnv(TechPlatformEnv.SIM);
    }

    @Override
    public void run() {
        // get all position rate
        AllTradeAccountPositionRateCalculator calculator = new AllTradeAccountPositionRateCalculator();
        calculator.addTask(null);

        // start daemon
        new UpdateXQContractCommissionDaemon().startDelayed(UPDATE_XQ_CONTRACT_COMMISSION_PERIOD);
        new UpdateXQContractMarginDaemon().startDelayed(UPDATE_XQ_CONTRACT_MARGIN_PERIOD);
        new UpdateUpsideContractPositionRateDaemon().startDelayed(UPDATE_UPSIDE_CONTRACT_POSITION_RATE_PERIOD);
        new DeleteExpiredContractPositionRateDaemon().startDelayed(DELETE_EXPIRED_CONTRACT_POSITION_RATE_PERIOD);
    }
}
