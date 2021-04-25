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
import xueqiao.trade.hosting.position.fee.core.bean.DeltaMarginInfo;
import xueqiao.trade.hosting.position.fee.core.bean.XQDefaultPositionRate;
import xueqiao.trade.hosting.position.fee.core.cache.XQDefaultPositionRateCacheManager;
import xueqiao.trade.hosting.position.fee.core.common.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.fee.core.util.PositionRateUtil;
import xueqiao.trade.hosting.position.fee.core.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.handler.UpsideContractMarginHandler;
import xueqiao.trade.hosting.position.fee.storage.handler.XQContractMarginHandler;
import xueqiao.trade.hosting.position.fee.storage.handler.XQMarginSettingsHandler;
import xueqiao.trade.hosting.position.fee.storage.table.*;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.List;

public class UpdateXQContractMarginCalculator extends AbstractCalculator<Void, Void> {

    private XQGeneralMarginSettings xqGeneralMarginSettings;
    private XQSpecMarginSettings xqSpecMarginSettings;
    private UpsideContractMargin upsideContractMargin;

    public UpdateXQContractMarginCalculator(XQGeneralMarginSettings xqGeneralMarginSettings) {
        super(xqGeneralMarginSettings.getSubAccountId());
        this.xqGeneralMarginSettings = xqGeneralMarginSettings;
    }

    public UpdateXQContractMarginCalculator(XQSpecMarginSettings xqSpecMarginSettings) {
        super(xqSpecMarginSettings.getSubAccountId());
        this.xqSpecMarginSettings = xqSpecMarginSettings;
    }

    public UpdateXQContractMarginCalculator(UpsideContractMargin upsideContractMargin) {
        super(upsideContractMargin.getSubAccountId());
        this.upsideContractMargin = upsideContractMargin;
    }

    @Override
    public Void onCalculate(Void param) throws Throwable {
        if (xqGeneralMarginSettings != null) {
            updateByXQGeneralSettings(xqGeneralMarginSettings);
        } else if (xqSpecMarginSettings != null) {
            updateByXQSpecSettings(xqSpecMarginSettings);
        } else if (upsideContractMargin != null) {
            updateByUpside(upsideContractMargin);
        }
        return null;
    }

    private void updateByXQGeneralSettings(XQGeneralMarginSettings xqGeneralMarginSettings) throws ErrorInfo, SQLException {
        long subAccountId = xqGeneralMarginSettings.getSubAccountId();
        DeltaMarginInfo deltaMarginInfo = new DeltaMarginInfo();
        deltaMarginInfo.setType(xqGeneralMarginSettings.getType());
        deltaMarginInfo.setSettingsDataType(XQSettingsDataType.SDT_GENERAL);
        deltaMarginInfo.setMarginDelta(xqGeneralMarginSettings.getMarginDelta());
        /*
         * 查询对应的雪橇手续费数据列表
         * 根据列表逐条数据处理
         * 这里数据量可能会比较大，需要分批查询处理
         * */
        int pageIndex = 0;
        final int pageSize = 50;
        List<XQContractMargin> xqContractMarginList;
        do {
            xqContractMarginList = XQContractMarginHandler.queryByPage(subAccountId, XQSettingsDataType.SDT_GENERAL, pageIndex, pageSize);
            pageIndex++;
            if (xqContractMarginList == null || xqContractMarginList.isEmpty()) {
                break;
            }
            for (XQContractMargin originalXQContractMargin : xqContractMarginList) {
                long commodityId = originalXQContractMargin.getContractInfo().getCommodityId();
                /*
                 * 获取基本数据
                 * 1 看上手数据是否存在
                 * 2 如果不存在则获取雪橇后台数据
                 * */
                MarginInfo baseMarginInfo;
                UpsideContractMargin tempUpsideContractMargin = UpsideContractMarginHandler.query(subAccountId, commodityId, originalXQContractMargin.getContractInfo().getContractCode());
                if (tempUpsideContractMargin == null
                        || tempUpsideContractMargin.getMargin() == null
                        || tempUpsideContractMargin.getDataType() == UpsideDataType.UDT_NO_DATA
                        || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(tempUpsideContractMargin.getMargin().getCurrency())) {
                    // 从雪橇后台获取
                    XQDefaultPositionRate xqDefaultPositionRate = getXQDefaultPositionRate(commodityId);
                    if (xqDefaultPositionRate == null) {
                        /*
                         * 如果雪橇后台获取不到数据，则跳过
                         * */
                        continue;
                    }
                    baseMarginInfo = xqDefaultPositionRate.getMarginInfo();
                } else {
                    baseMarginInfo = tempUpsideContractMargin.getMargin();
                }

                /*
                 * 计算手续费数据
                 * */
                MarginInfo marginInfo = PositionRateUtil.getMarginInfo(baseMarginInfo, deltaMarginInfo);

                XQContractMargin xqContractMargin = new XQContractMargin();
                xqContractMargin.setSubAccountId(subAccountId);
                xqContractMargin.setContractInfo(originalXQContractMargin.getContractInfo());
                xqContractMargin.setMargin(marginInfo);
                xqContractMargin.setDataType(originalXQContractMargin.getDataType());
                xqContractMargin.setSettingsDataType(deltaMarginInfo.getSettingsDataType());

                /*
                 * 写入库
                 * 设置SETTINGS数据已同步状态
                 * */
                doUpdate(xqContractMargin);
            }
        } while (xqContractMarginList.size() == pageSize);
    }

    private void updateByXQSpecSettings(XQSpecMarginSettings xqSpecMarginSettings) throws ErrorInfo, SQLException {
        long subAccountId = xqSpecMarginSettings.getSubAccountId();
        DeltaMarginInfo deltaMarginInfo = new DeltaMarginInfo();
        deltaMarginInfo.setType(xqSpecMarginSettings.getType());
        deltaMarginInfo.setSettingsDataType(XQSettingsDataType.SDT_COMMODITY);
        deltaMarginInfo.setMarginDelta(xqSpecMarginSettings.getMarginDelta());
        /*
         * 查询对应的雪橇手续费数据列表
         * 根据列表逐条数据处理
         * 这里数据量可能会比较大，需要分批查询处理
         * */
        int pageIndex = 0;
        final int pageSize = 50;
        List<XQContractMargin> xqContractMarginList;
        do {
            xqContractMarginList = XQContractMarginHandler.queryByPage(subAccountId, XQSettingsDataType.SDT_COMMODITY, pageIndex, pageSize);
            pageIndex++;
            if (xqContractMarginList == null || xqContractMarginList.isEmpty()) {
                break;
            }
            for (XQContractMargin originalXQContractMargin : xqContractMarginList) {
                long commodityId = originalXQContractMargin.getContractInfo().getCommodityId();
                /*
                 * 获取基本数据
                 * 1 看上手数据是否存在
                 * 2 如果不存在则获取雪橇后台数据
                 * */
                MarginInfo baseMarginInfo;
                UpsideContractMargin tempUpsideContractMargin = UpsideContractMarginHandler.query(subAccountId, commodityId, originalXQContractMargin.getContractInfo().getContractCode());
                if (tempUpsideContractMargin == null
                        || tempUpsideContractMargin.getMargin() == null
                        || tempUpsideContractMargin.getDataType() == UpsideDataType.UDT_NO_DATA
                        || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(tempUpsideContractMargin.getMargin().getCurrency())) {
                    // 从雪橇后台获取
                    XQDefaultPositionRate xqDefaultPositionRate = getXQDefaultPositionRate(commodityId);
                    if (xqDefaultPositionRate == null) {
                        /*
                         * 如果雪橇后台获取不到数据，则跳过
                         * */
                        continue;
                    }
                    baseMarginInfo = xqDefaultPositionRate.getMarginInfo();
                } else {
                    baseMarginInfo = tempUpsideContractMargin.getMargin();
                }
                /*
                 * 计算手续费数据
                 * */
                MarginInfo marginInfo = PositionRateUtil.getMarginInfo(baseMarginInfo, deltaMarginInfo);

                XQContractMargin xqContractMargin = new XQContractMargin();
                xqContractMargin.setSubAccountId(subAccountId);
                xqContractMargin.setContractInfo(originalXQContractMargin.getContractInfo());
                xqContractMargin.setMargin(marginInfo);
                xqContractMargin.setDataType(originalXQContractMargin.getDataType());
                xqContractMargin.setSettingsDataType(deltaMarginInfo.getSettingsDataType());

                /*
                 * 写入库
                 * 设置SETTINGS数据已同步状态
                 * */
                doUpdate(xqContractMargin);
            }
        } while (xqContractMarginList.size() == pageSize);
    }

    private void updateByUpside(UpsideContractMargin upsideContractMargin) throws ErrorInfo {
        long subAccountId = upsideContractMargin.getSubAccountId();
        long commodityId = upsideContractMargin.getContractInfo().getCommodityId();
        /*
         * 获取基本数据
         * 1 看上手数据是否存在
         * 2 如果不存在则获取雪橇后台数据
         * */
        MarginInfo baseMarginInfo;
        if (upsideContractMargin.getDataType() == UpsideDataType.UDT_NO_DATA
                || upsideContractMargin.getMargin() == null
                || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(upsideContractMargin.getMargin().getCurrency())) {
            XQDefaultPositionRate xqDefaultPositionRate = getXQDefaultPositionRate(commodityId);
            if (xqDefaultPositionRate == null) {
                /*
                 * 如果雪橇后台获取不到数据，则跳过
                 * */
                return;
            }
            baseMarginInfo = xqDefaultPositionRate.getMarginInfo();
        } else {
            baseMarginInfo = upsideContractMargin.getMargin();
        }

        /*
         * 获取偏差数据
         * 1 先查询商品数据
         * 2 若商品数据不存在，则查询通用数据
         * */
        DeltaMarginInfo deltaMarginInfo = getDeltaMarginInfo(subAccountId, commodityId);

        /*
         * 计算手续费数据
         * */
        MarginInfo marginInfo = PositionRateUtil.getMarginInfo(baseMarginInfo, deltaMarginInfo);

        XQContractMargin xqContractMargin = new XQContractMargin();
        xqContractMargin.setSubAccountId(subAccountId);
        xqContractMargin.setContractInfo(upsideContractMargin.getContractInfo());
        xqContractMargin.setMargin(marginInfo);
        xqContractMargin.setDataType(upsideContractMargin.getDataType());
        xqContractMargin.setSettingsDataType(deltaMarginInfo.getSettingsDataType());

        /*
         * 写入库
         * 设置上手数据已同步状态
         * */
        doUpdate(xqContractMargin);
    }

    private XQDefaultPositionRate getXQDefaultPositionRate(long commodityId) throws ErrorInfo {
        return XQDefaultPositionRateCacheManager.getInstance().get(commodityId);
    }

    private DeltaMarginInfo getDeltaMarginInfo(long subAccountId, long commodityId) throws ErrorInfo {
        return XQMarginSettingsHandler.getDeltaMarginInfo(subAccountId, commodityId);
    }

    /**
     * 更新的同时，发消息
     * 设置SETTINGS数据已同步状态
     */
    private void doUpdate(XQContractMargin xqContractMargin) throws ErrorInfo {
        new DBTransactionHelperWithMsg<Void>(PositionFeeDB.Global()) {

            XQContractMarginTable xqContractMarginTable;
            XQContractMargin originalXQContractMargin;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                xqContractMarginTable = new XQContractMarginTable(getConnection());
                originalXQContractMargin = xqContractMarginTable.query(xqContractMargin.getSubAccountId(), xqContractMargin.getContractInfo().getCommodityId(), xqContractMargin.getContractInfo().getContractCode());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (originalXQContractMargin == null) {
                    xqContractMarginTable.insert(xqContractMargin);
                } else {
                    xqContractMarginTable.update(xqContractMargin);
                }

                /*
                 * 重置数据已同步状态
                 * */
                if (upsideContractMargin != null) {
                    new UpsideContractMarginTable(getConnection()).setSyncStatus(
                            upsideContractMargin.getSubAccountId(),
                            upsideContractMargin.getContractInfo().getCommodityId(),
                            upsideContractMargin.getContractInfo().getContractCode());
                } else if (xqSpecMarginSettings != null) {
                    new XQSpecMarginSettingsTable(getConnection()).setSyncStatus(xqSpecMarginSettings.getSubAccountId(), xqSpecMarginSettings.getCommodityId());
                } else if (xqGeneralMarginSettings != null) {
                    new XQGeneralMarginSettingsTable(getConnection()).setSyncStatus(xqGeneralMarginSettings.getSubAccountId());
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                PositionFeeChangedGuardEvent positionFeeChangedGuardEvent = new PositionFeeChangedGuardEvent();
                positionFeeChangedGuardEvent.setSubAccountId(xqContractMargin.getSubAccountId());
                positionFeeChangedGuardEvent.setContractId(xqContractMargin.getContractInfo().getContractId());
                positionFeeChangedGuardEvent.setEventType(PositionFeeEventType.PF_MARGIN_CHANGED);
                return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(positionFeeChangedGuardEvent, new TimeoutGuardPolicy().setTimeoutSeconds(3));
            }

            @Override
            protected TBase prepareNotifyMessage() {
                PositionFeeChangedEvent positionFeeChangedEvent = new PositionFeeChangedEvent();
                positionFeeChangedEvent.setSubAccountId(xqContractMargin.getSubAccountId());
                positionFeeChangedEvent.setContractId(xqContractMargin.getContractInfo().getContractId());
                positionFeeChangedEvent.setMargin(xqContractMargin.getMargin());
                positionFeeChangedEvent.setEventCreateTimestampMs(System.currentTimeMillis());
                positionFeeChangedEvent.setEventType(PositionFeeEventType.PF_MARGIN_CHANGED);
                return positionFeeChangedEvent;
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }
}
