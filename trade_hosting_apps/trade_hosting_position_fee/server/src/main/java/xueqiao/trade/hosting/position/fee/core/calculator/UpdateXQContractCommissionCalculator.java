package xueqiao.trade.hosting.position.fee.core.calculator;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.events.PositionFeeChangedEvent;
import xueqiao.trade.hosting.events.PositionFeeChangedGuardEvent;
import xueqiao.trade.hosting.events.PositionFeeEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.position.fee.core.bean.DeltaCommissionInfo;
import xueqiao.trade.hosting.position.fee.core.bean.XQDefaultPositionRate;
import xueqiao.trade.hosting.position.fee.core.cache.XQDefaultPositionRateCacheManager;
import xueqiao.trade.hosting.position.fee.core.common.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.fee.core.util.PositionRateUtil;
import xueqiao.trade.hosting.position.fee.core.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.handler.UpsideContractCommissionHandler;
import xueqiao.trade.hosting.position.fee.storage.handler.XQCommissionSettingsHandler;
import xueqiao.trade.hosting.position.fee.storage.handler.XQContractCommissionHandler;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractCommissionTable;
import xueqiao.trade.hosting.position.fee.storage.table.XQContractCommissionTable;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.List;

public class UpdateXQContractCommissionCalculator extends AbstractCalculator<Void, Void> {

    private UpsideContractCommission upsideContractCommission = null;
    private XQSpecCommissionSettings specCommissionSettings = null;
    private XQGeneralCommissionSettings generalCommissionSettings = null;

    public UpdateXQContractCommissionCalculator(XQGeneralCommissionSettings generalCommissionSettings) {
        super(generalCommissionSettings.getSubAccountId());
        this.generalCommissionSettings = generalCommissionSettings;
    }

    public UpdateXQContractCommissionCalculator(XQSpecCommissionSettings specCommissionSettings) {
        super(specCommissionSettings.getSubAccountId());
        this.specCommissionSettings = specCommissionSettings;
    }

    public UpdateXQContractCommissionCalculator(UpsideContractCommission upsideContractCommission) {
        super(upsideContractCommission.getSubAccountId());
        this.upsideContractCommission = upsideContractCommission;
    }

    @Override
    public Void onCalculate(Void param) throws Throwable {
        if (upsideContractCommission != null) {
            updateByUpside(upsideContractCommission);
        } else if (specCommissionSettings != null) {
            updateByXQSpecSettings(specCommissionSettings);
        } else if (generalCommissionSettings != null) {
            updateByXQGeneralSettings(generalCommissionSettings);
        }
        return null;
    }

    /**
     * ?????????
     */
    private void updateByXQGeneralSettings(XQGeneralCommissionSettings generalCommissionSettings) throws ErrorInfo, SQLException {
        long subAccountId = generalCommissionSettings.getSubAccountId();
        DeltaCommissionInfo deltaCommissionInfo = new DeltaCommissionInfo();
        deltaCommissionInfo.setType(generalCommissionSettings.getType());
        deltaCommissionInfo.setSettingsDataType(XQSettingsDataType.SDT_GENERAL);
        deltaCommissionInfo.setCommissionDelta(generalCommissionSettings.getCommissionDelta());
        /*
         * ??????????????????????????????????????????
         * ??????????????????????????????
         * ????????????????????????????????????????????????????????????
         * */
        int pageIndex = 0;
        final int pageSize = 50;
        List<XQContractCommission> xqContractCommissionList;
        do {
            xqContractCommissionList = XQContractCommissionHandler.queryByPage(subAccountId, XQSettingsDataType.SDT_GENERAL, pageIndex, pageSize);
            pageIndex++;
            if (xqContractCommissionList == null || xqContractCommissionList.isEmpty()) {
                break;
            }
            for (XQContractCommission originalXQContractCommission : xqContractCommissionList) {
                long commodityId = originalXQContractCommission.getContractInfo().getCommodityId();
                /*
                 * ??????????????????
                 * 1 ???????????????????????????
                 * 2 ??????????????????????????????????????????
                 * */
                CommissionInfo baseCommissionInfo;
                UpsideContractCommission tempUpsideContractCommission = UpsideContractCommissionHandler.query(subAccountId, commodityId, originalXQContractCommission.getContractInfo().getContractCode());
                if (tempUpsideContractCommission == null
                        || tempUpsideContractCommission.getCommission() == null
                        || tempUpsideContractCommission.getDataType() == UpsideDataType.UDT_NO_DATA
                        || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(tempUpsideContractCommission.getCommission().getCurrency())) {
                    // ?????????????????????
                    XQDefaultPositionRate xqDefaultPositionRate = getXQDefaultPositionRate(commodityId);
                    if (xqDefaultPositionRate == null) {
                        /*
                         * ????????????????????????????????????????????????
                         * */
                        continue;
                    }
                    baseCommissionInfo = xqDefaultPositionRate.getCommissionInfo();
                } else {
                    baseCommissionInfo = tempUpsideContractCommission.getCommission();
                }

                /*
                 * ?????????????????????
                 * */
                CommissionInfo commissionInfo = PositionRateUtil.getCommissionInfo(baseCommissionInfo, deltaCommissionInfo);

                XQContractCommission xqContractCommission = new XQContractCommission();
                xqContractCommission.setSubAccountId(subAccountId);
                xqContractCommission.setContractInfo(originalXQContractCommission.getContractInfo());
                xqContractCommission.setCommission(commissionInfo);
                xqContractCommission.setDataType(originalXQContractCommission.getDataType());
                xqContractCommission.setSettingsDataType(deltaCommissionInfo.getSettingsDataType());

                /*
                 * ?????????
                 * ??????SETTINGS?????????????????????
                 * */
                doUpdate(xqContractCommission);
            }
        } while (xqContractCommissionList.size() == pageSize);
    }

    /**
     * ?????????
     */
    private void updateByXQSpecSettings(XQSpecCommissionSettings specCommissionSettings) throws ErrorInfo, SQLException {
        long subAccountId = specCommissionSettings.getSubAccountId();
        DeltaCommissionInfo deltaCommissionInfo = new DeltaCommissionInfo();
        deltaCommissionInfo.setType(specCommissionSettings.getType());
        deltaCommissionInfo.setSettingsDataType(XQSettingsDataType.SDT_COMMODITY);
        deltaCommissionInfo.setCommissionDelta(specCommissionSettings.getCommissionDelta());
        /*
         * ??????????????????????????????????????????
         * ??????????????????????????????
         * ????????????????????????????????????????????????????????????
         * */
        int pageIndex = 0;
        final int pageSize = 50;
        List<XQContractCommission> xqContractCommissionList;
        do {
            xqContractCommissionList = XQContractCommissionHandler.queryByPage(subAccountId, XQSettingsDataType.SDT_COMMODITY, pageIndex, pageSize);
            pageIndex++;
            if (xqContractCommissionList == null || xqContractCommissionList.isEmpty()) {
                break;
            }
            for (XQContractCommission originalXQContractCommission : xqContractCommissionList) {
                long commodityId = originalXQContractCommission.getContractInfo().getCommodityId();
                /*
                 * ??????????????????
                 * 1 ???????????????????????????
                 * 2 ??????????????????????????????????????????
                 * */
                CommissionInfo baseCommissionInfo;
                UpsideContractCommission tempUpsideContractCommission = UpsideContractCommissionHandler.query(subAccountId, commodityId, originalXQContractCommission.getContractInfo().getContractCode());
                if (tempUpsideContractCommission == null
                        || tempUpsideContractCommission.getCommission() == null
                        || tempUpsideContractCommission.getDataType() == UpsideDataType.UDT_NO_DATA
                        || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(tempUpsideContractCommission.getCommission().getCurrency())) {
                    // ?????????????????????
                    XQDefaultPositionRate xqDefaultPositionRate = getXQDefaultPositionRate(commodityId);
                    if (xqDefaultPositionRate == null) {
                        /*
                         * ????????????????????????????????????????????????
                         * */
                        continue;
                    }
                    baseCommissionInfo = xqDefaultPositionRate.getCommissionInfo();
                } else {
                    baseCommissionInfo = tempUpsideContractCommission.getCommission();
                }

                /*
                 * ?????????????????????
                 * */
                CommissionInfo commissionInfo = PositionRateUtil.getCommissionInfo(baseCommissionInfo, deltaCommissionInfo);

                XQContractCommission xqContractCommission = new XQContractCommission();
                xqContractCommission.setSubAccountId(subAccountId);
                xqContractCommission.setContractInfo(originalXQContractCommission.getContractInfo());
                xqContractCommission.setCommission(commissionInfo);
                xqContractCommission.setDataType(originalXQContractCommission.getDataType());
                xqContractCommission.setSettingsDataType(deltaCommissionInfo.getSettingsDataType());

                /*
                 * ?????????
                 * ??????SETTINGS?????????????????????
                 * */
                doUpdate(xqContractCommission);
            }
        } while (xqContractCommissionList.size() == pageSize);
    }

    /**
     * ???????????????
     */
    private void updateByUpside(UpsideContractCommission upsideContractCommission) throws ErrorInfo {
        long subAccountId = upsideContractCommission.getSubAccountId();
        long commodityId = upsideContractCommission.getContractInfo().getCommodityId();
        /*
         * ??????????????????
         * 1 ???????????????????????????
         * 2 ??????????????????????????????????????????
         * */
        CommissionInfo baseCommissionInfo;
        if (upsideContractCommission.getDataType() == UpsideDataType.UDT_NO_DATA
                || upsideContractCommission.getCommission() == null
                || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(upsideContractCommission.getCommission().getCurrency())) {
            XQDefaultPositionRate xqDefaultPositionRate = getXQDefaultPositionRate(commodityId);
            if (xqDefaultPositionRate == null) {
                /*
                 * ????????????????????????????????????????????????
                 * */
                return;
            }
            baseCommissionInfo = xqDefaultPositionRate.getCommissionInfo();
        } else {
            baseCommissionInfo = upsideContractCommission.getCommission();
        }

        /*
         * ??????????????????
         * 1 ?????????????????????
         * 2 ????????????????????????????????????????????????
         * */
        DeltaCommissionInfo deltaCommissionInfo = getCommissionDeltaInfo(subAccountId, commodityId);

        /*
         * ?????????????????????
         * */
        CommissionInfo commissionInfo = PositionRateUtil.getCommissionInfo(baseCommissionInfo, deltaCommissionInfo);

        XQContractCommission xqContractCommission = new XQContractCommission();
        xqContractCommission.setSubAccountId(subAccountId);
        xqContractCommission.setContractInfo(upsideContractCommission.getContractInfo());
        xqContractCommission.setCommission(commissionInfo);
        xqContractCommission.setDataType(upsideContractCommission.getDataType());
        xqContractCommission.setSettingsDataType(deltaCommissionInfo.getSettingsDataType());

        /*
         * ?????????
         * ?????????????????????????????????
         * */
        doUpdate(xqContractCommission);
    }

    private XQDefaultPositionRate getXQDefaultPositionRate(long commodityId) throws ErrorInfo {
        return XQDefaultPositionRateCacheManager.getInstance().get(commodityId);
    }

    private DeltaCommissionInfo getCommissionDeltaInfo(long subAccountId, long commodityId) throws ErrorInfo {
        return XQCommissionSettingsHandler.getDeltaCommissionInfo(subAccountId, commodityId);
    }

    /**
     * ???????????????????????????
     * ??????SETTINGS?????????????????????
     */
    private void doUpdate(XQContractCommission xqContractCommission) throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(PositionFeeDB.Global()) {

            XQContractCommissionTable xqContractCommissionTable;
            XQContractCommission originalXQContractCommission;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                xqContractCommissionTable = new XQContractCommissionTable(getConnection());
                originalXQContractCommission = xqContractCommissionTable.query(xqContractCommission.getSubAccountId(), xqContractCommission.getContractInfo().getCommodityId(), xqContractCommission.getContractInfo().getContractCode());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (originalXQContractCommission == null) {
                    xqContractCommissionTable.insert(xqContractCommission);
                } else {
                    xqContractCommissionTable.update(xqContractCommission);
                }

                /*
                 * ???????????????????????????
                 * */
                if (upsideContractCommission != null) {
                    new UpsideContractCommissionTable(getConnection()).setSyncStatus(
                            upsideContractCommission.getSubAccountId(),
                            upsideContractCommission.getContractInfo().getCommodityId(),
                            upsideContractCommission.getContractInfo().getContractCode());
                } else if (specCommissionSettings != null) {
                    new XQSpecCommissionSettingsTable(getConnection()).setSyncStatus(specCommissionSettings.getSubAccountId(), specCommissionSettings.getCommodityId());
                } else if (generalCommissionSettings != null) {
                    new XQGeneralCommissionSettingsTable(getConnection()).setSyncStatus(generalCommissionSettings.getSubAccountId());
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                PositionFeeChangedGuardEvent positionFeeChangedGuardEvent = new PositionFeeChangedGuardEvent();
                positionFeeChangedGuardEvent.setSubAccountId(xqContractCommission.getSubAccountId());
                positionFeeChangedGuardEvent.setContractId(xqContractCommission.getContractInfo().getContractId());
                positionFeeChangedGuardEvent.setEventType(PositionFeeEventType.PF_COMMISSION_CHANGED);
                return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(positionFeeChangedGuardEvent, new TimeoutGuardPolicy().setTimeoutSeconds(3));
            }

            @Override
            protected TBase prepareNotifyMessage() {
                PositionFeeChangedEvent positionFeeChangedEvent = new PositionFeeChangedEvent();
                positionFeeChangedEvent.setSubAccountId(xqContractCommission.getSubAccountId());
                positionFeeChangedEvent.setContractId(xqContractCommission.getContractInfo().getContractId());
                positionFeeChangedEvent.setCommission(xqContractCommission.getCommission());
                positionFeeChangedEvent.setEventCreateTimestampMs(System.currentTimeMillis());
                positionFeeChangedEvent.setEventType(PositionFeeEventType.PF_COMMISSION_CHANGED);
                return positionFeeChangedEvent;
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }
}
