package xueqiao.trade.hosting.position.fee.core.daemon;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.util.CollectionUtil;
import xueqiao.trade.hosting.position.fee.core.common.daemon.AbstractDaemon;
import xueqiao.trade.hosting.position.fee.core.calculator.UpdateXQContractMarginCalculator;
import xueqiao.trade.hosting.position.fee.storage.handler.UpsideContractMarginHandler;
import xueqiao.trade.hosting.position.fee.storage.handler.XQMarginSettingsHandler;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMargin;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralMarginSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings;

import java.util.List;

public class UpdateXQContractMarginDaemon extends AbstractDaemon {

    /*
     * 30秒钟
     * */
    public static final long UPDATE_XQ_CONTRACT_MARGIN_PERIOD = 1000 * 30;

    public UpdateXQContractMarginDaemon() {
        super();
        setName("UpdateXQContractMarginDaemon");
    }

    @Override
    public boolean isTaskTime() {
        return true;
    }

    @Override
    public void doTask() throws ErrorInfo, Throwable {
        syncUpsideContractMargin();
        syncGeneralMarginSettings();
        syncSpecMarginSettings();
    }

    @Override
    public long getSleepPeriod() {
        return UPDATE_XQ_CONTRACT_MARGIN_PERIOD;
    }

    private void syncUpsideContractMargin() throws Throwable {
        List<UpsideContractMargin> upsideContractMarginList;
        int pageIndex = 0;
        final int pageSize = 50;
        do {
            upsideContractMarginList = UpsideContractMarginHandler.queryUnsyncList(pageIndex, pageSize);
            if (AppLog.infoEnabled()) {
                AppLog.e("syncUpsideContractMargin ---- pageIndex : " + pageIndex + ", upsideContractMarginList size : " + CollectionUtil.getListSize(upsideContractMarginList));
            }
            pageIndex++;
            if (upsideContractMarginList == null || upsideContractMarginList.isEmpty()) {
                break;
            }
            for (UpsideContractMargin contractMargin : upsideContractMarginList) {
                new UpdateXQContractMarginCalculator(contractMargin).onCalculate(null);
            }
        } while (upsideContractMarginList.size() == pageSize);
    }

    private void syncGeneralMarginSettings() throws Throwable {
        List<XQGeneralMarginSettings> generalMarginSettingsList;
        int pageIndex = 0;
        final int pageSize = 50;
        do {
            generalMarginSettingsList = XQMarginSettingsHandler.queryGeneralUnsyncList(pageIndex, pageSize);
            if (AppLog.infoEnabled()) {
                AppLog.e("syncGeneralMarginSettings ---- pageIndex : " + pageIndex + ", generalMarginSettingsList size : " + CollectionUtil.getListSize(generalMarginSettingsList));
            }
            pageIndex++;
            if (generalMarginSettingsList == null || generalMarginSettingsList.isEmpty()) {
                break;
            }
            for (XQGeneralMarginSettings generalMarginSettings : generalMarginSettingsList) {
                new UpdateXQContractMarginCalculator(generalMarginSettings).onCalculate(null);
            }
        } while (generalMarginSettingsList.size() == pageSize);
    }

    private void syncSpecMarginSettings() throws Throwable {
        List<XQSpecMarginSettings> specMarginSettingsList;
        int pageIndex = 0;
        final int pageSize = 50;
        do {
            specMarginSettingsList = XQMarginSettingsHandler.querySpecUnsyncList(pageIndex, pageSize);
            if (AppLog.infoEnabled()) {
                AppLog.e("syncSpecMarginSettings ---- pageIndex : " + pageIndex + ", specMarginSettingsList size : " + CollectionUtil.getListSize(specMarginSettingsList));
            }
            pageIndex++;
            if (specMarginSettingsList == null || specMarginSettingsList.isEmpty()) {
                break;
            }
            for (XQSpecMarginSettings specMarginSettings : specMarginSettingsList) {
                new UpdateXQContractMarginCalculator(specMarginSettings).onCalculate(null);
            }
        } while (specMarginSettingsList.size() == pageSize);
    }
}
