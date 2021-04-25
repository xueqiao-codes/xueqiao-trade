package xueqiao.trade.hosting.position.fee.core.daemon;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.util.CollectionUtil;
import xueqiao.trade.hosting.position.fee.core.common.daemon.AbstractDaemon;
import xueqiao.trade.hosting.position.fee.core.calculator.UpdateXQContractCommissionCalculator;
import xueqiao.trade.hosting.position.fee.storage.handler.UpsideContractCommissionHandler;
import xueqiao.trade.hosting.position.fee.storage.handler.XQCommissionSettingsHandler;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommission;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;

import java.util.List;

public class UpdateXQContractCommissionDaemon extends AbstractDaemon {

    /*
     * 30秒
     * */
//    public static final long UPDATE_XQ_CONTRACT_COMMISSION_PERIOD = 1000 * 60;
    public static final long UPDATE_XQ_CONTRACT_COMMISSION_PERIOD = 1000 * 30;

    public UpdateXQContractCommissionDaemon() {
        super();
        setName("UpdateXQContractCommissionDaemon");
    }

    @Override
    public boolean isTaskTime() {
        return true;
    }

    @Override
    public void doTask() throws ErrorInfo, Throwable {
        if (AppLog.infoEnabled()) {
            AppLog.i("doTask ...........");
        }

        syncUpsideContractCommossion();
        syncGeneralCommissionSettings();
        syncSpecCommissionSettings();
    }

    @Override
    public long getSleepPeriod() {
        return UPDATE_XQ_CONTRACT_COMMISSION_PERIOD;
    }

    private void syncUpsideContractCommossion() throws Throwable {
        List<UpsideContractCommission> upsideContractCommissionList;
        int pageIndex = 0;
        final int pageSize = 50;
        do {
            upsideContractCommissionList = UpsideContractCommissionHandler.queryUnsyncList(pageIndex, pageSize);
            if (AppLog.infoEnabled()) {
                AppLog.i("syncUpsideContractCommossion ---- pageIndex : " + pageIndex + ", upsideContractCommissionList size : " + CollectionUtil.getListSize(upsideContractCommissionList));
            }
            pageIndex++;
            if (upsideContractCommissionList == null || upsideContractCommissionList.isEmpty()) {
                break;
            }
            for (UpsideContractCommission contractCommission : upsideContractCommissionList) {
                new UpdateXQContractCommissionCalculator(contractCommission).onCalculate(null);
            }
        } while (upsideContractCommissionList.size() == pageSize);
    }

    private void syncGeneralCommissionSettings() throws Throwable {
        List<XQGeneralCommissionSettings> generalCommissionSettingsList;
        int pageIndex = 0;
        final int pageSize = 50;
        do {
            generalCommissionSettingsList = XQCommissionSettingsHandler.queryGeneralUnsyncList(pageIndex, pageSize);
            if (AppLog.infoEnabled()) {
                AppLog.i("syncGeneralCommissionSettings ---- pageIndex : " + pageIndex + ", generalCommissionSettingsList size : " + CollectionUtil.getListSize(generalCommissionSettingsList));
            }
            pageIndex++;
            if (generalCommissionSettingsList == null || generalCommissionSettingsList.isEmpty()) {
                break;
            }
            for (XQGeneralCommissionSettings generalCommissionSettings : generalCommissionSettingsList) {
                new UpdateXQContractCommissionCalculator(generalCommissionSettings).onCalculate(null);
            }
        } while (generalCommissionSettingsList.size() == pageSize);
    }

    private void syncSpecCommissionSettings() throws Throwable {
        List<XQSpecCommissionSettings> specCommissionSettingsList;
        int pageIndex = 0;
        final int pageSize = 50;
        do {
            specCommissionSettingsList = XQCommissionSettingsHandler.querySpecUnsyncList(pageIndex, pageSize);
            if (AppLog.infoEnabled()) {
                AppLog.i("syncSpecCommissionSettings ---- pageIndex : " + pageIndex + ", specCommissionSettingsList size : " + CollectionUtil.getListSize(specCommissionSettingsList));
            }
            pageIndex++;
            if (specCommissionSettingsList == null || specCommissionSettingsList.isEmpty()) {
                break;
            }
            for (XQSpecCommissionSettings specCommissionSettings : specCommissionSettingsList) {
                new UpdateXQContractCommissionCalculator(specCommissionSettings).onCalculate(null);
            }
        } while (specCommissionSettingsList.size() == pageSize);

    }
}
