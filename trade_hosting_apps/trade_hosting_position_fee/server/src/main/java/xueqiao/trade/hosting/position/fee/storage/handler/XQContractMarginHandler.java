package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.bean.DeltaMarginInfo;
import xueqiao.trade.hosting.position.fee.core.bean.XQDefaultPositionRate;
import xueqiao.trade.hosting.position.fee.core.cache.XQDefaultPositionRateCacheManager;
import xueqiao.trade.hosting.position.fee.core.util.PositionRateUtil;
import xueqiao.trade.hosting.position.fee.core.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQContractMarginTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class XQContractMarginHandler {

    public static List<XQContractMargin> queryByPage(long subAccountId, XQSettingsDataType settingsDataType, int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<XQContractMargin>>(PositionFeeDB.Global()) {
            @Override
            protected List<XQContractMargin> onQuery(Connection connection) throws Exception {
                return new XQContractMarginTable(connection).query(subAccountId, settingsDataType, pageIndex, pageSize);
            }
        }.query();
    }

    public static void updateXQContractMarginToGeneralSettingDataType(Connection connection, long subAccountId, long commodityId) throws SQLException, ErrorInfo {
        XQContractMarginTable table = new XQContractMarginTable(connection);
        List<XQContractMargin> xqContractMarginList = table.queryByCommodityId(subAccountId, commodityId);
        for (XQContractMargin xqContractMargin : xqContractMarginList) {
            /*
             * 获取基本数据
             * 1 看上手数据是否存在
             * 2 如果不存在则获取雪橇后台数据
             * */
            UpsideContractMargin tempUpsideContractMargin = UpsideContractMarginHandler.query(subAccountId, commodityId, xqContractMargin.getContractInfo().getContractCode());
            MarginInfo baseMarginInfo = null;
            if (tempUpsideContractMargin == null
                    || tempUpsideContractMargin.getMargin() == null
                    || tempUpsideContractMargin.getDataType() == UpsideDataType.UDT_NO_DATA
                    || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(tempUpsideContractMargin.getMargin().getCurrency())) {
                XQDefaultPositionRate xqDefaultPositionRate = XQDefaultPositionRateCacheManager.getInstance().get(commodityId);
                if (xqDefaultPositionRate != null) {
                    baseMarginInfo = xqDefaultPositionRate.getMarginInfo();
                }
            } else {
                baseMarginInfo = tempUpsideContractMargin.getMargin();
            }
            if (baseMarginInfo != null) {
                DeltaMarginInfo deltaMarginInfo = XQMarginSettingsHandler.getDeltaMarginInfo(subAccountId, commodityId);
                MarginInfo marginInfo = PositionRateUtil.getMarginInfo(baseMarginInfo, deltaMarginInfo);
                xqContractMargin.setMargin(marginInfo);
            }

            xqContractMargin.setSettingsDataType(XQSettingsDataType.SDT_GENERAL);
            table.update(xqContractMargin);
        }
    }

    public static void updateXQContractMarginToCommoditySettingDataType(Connection connection, long subAccountId, long commodityId) throws SQLException, ErrorInfo {
        XQContractMarginTable table = new XQContractMarginTable(connection);
        table.update(subAccountId, commodityId, XQSettingsDataType.SDT_COMMODITY);
    }
}
