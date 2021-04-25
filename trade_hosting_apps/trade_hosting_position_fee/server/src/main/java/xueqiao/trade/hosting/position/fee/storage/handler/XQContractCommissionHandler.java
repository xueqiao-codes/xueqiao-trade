package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.bean.DeltaCommissionInfo;
import xueqiao.trade.hosting.position.fee.core.bean.XQDefaultPositionRate;
import xueqiao.trade.hosting.position.fee.core.cache.XQDefaultPositionRateCacheManager;
import xueqiao.trade.hosting.position.fee.core.util.PositionRateUtil;
import xueqiao.trade.hosting.position.fee.core.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQContractCommissionTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class XQContractCommissionHandler {

    public static List<XQContractCommission> queryByPage(long subAccountId, XQSettingsDataType settingsDataType, int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<XQContractCommission>>(PositionFeeDB.Global()) {
            @Override
            protected List<XQContractCommission> onQuery(Connection connection) throws Exception {
                return new XQContractCommissionTable(connection).query(subAccountId, settingsDataType, pageIndex, pageSize);
            }
        }.query();
    }

    public static void updateXQContractCommissionToGeneralSettingDataType(Connection connection, long subAccountId, long commodityId) throws SQLException, ErrorInfo {
        XQContractCommissionTable table = new XQContractCommissionTable(connection);
        List<XQContractCommission> xqContractCommissionList = table.queryByCommodityId(subAccountId, commodityId);
        for (XQContractCommission xqContractCommission : xqContractCommissionList) {
            /*
             * 获取基本数据
             * 1 看上手数据是否存在
             * 2 如果不存在则获取雪橇后台数据
             * */
            UpsideContractCommission tempUpsideContractCommission = UpsideContractCommissionHandler.query(subAccountId, commodityId, xqContractCommission.getContractInfo().getContractCode());
            CommissionInfo baseCommissionInfo = null;
            if (tempUpsideContractCommission == null
                    || tempUpsideContractCommission.getCommission() == null
                    || tempUpsideContractCommission.getDataType() == UpsideDataType.UDT_NO_DATA
                    || !CNYRateHelper.getInstance().isCurrencyExistInXueqiao(tempUpsideContractCommission.getCommission().getCurrency())) {
                XQDefaultPositionRate xqDefaultPositionRate = XQDefaultPositionRateCacheManager.getInstance().get(commodityId);
                if (xqDefaultPositionRate != null) {
                    baseCommissionInfo = xqDefaultPositionRate.getCommissionInfo();
                }
            } else {
                baseCommissionInfo = tempUpsideContractCommission.getCommission();
            }
            if (baseCommissionInfo != null) {
                DeltaCommissionInfo deltaCommissionInfo = XQCommissionSettingsHandler.getDeltaCommissionInfo(subAccountId, commodityId);
                CommissionInfo commissionInfo = PositionRateUtil.getCommissionInfo(baseCommissionInfo, deltaCommissionInfo);
                xqContractCommission.setCommission(commissionInfo);
            }

            xqContractCommission.setSettingsDataType(XQSettingsDataType.SDT_GENERAL);
            table.update(xqContractCommission);
        }
    }

    public static void updateXQContractCommissionToCommoditySettingDataType(Connection connection, long subAccountId, long commodityId) throws SQLException, ErrorInfo {
        XQContractCommissionTable table = new XQContractCommissionTable(connection);
        table.update(subAccountId, commodityId, XQSettingsDataType.SDT_COMMODITY);
    }


}
