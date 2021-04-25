package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.bean.DeltaMarginInfo;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.sql.Connection;
import java.util.List;

public class XQMarginSettingsHandler {

    public static DeltaMarginInfo getDeltaMarginInfo(long subAccountId, long commodityId) throws ErrorInfo {
        return new DBQueryHelper<DeltaMarginInfo>(PositionFeeDB.Global()) {
            @Override
            protected DeltaMarginInfo onQuery(Connection connection) throws Exception {
                DeltaMarginInfo deltaMarginInfo = new DeltaMarginInfo();
                XQGeneralMarginSettings xqGeneralMarginSettings = new XQGeneralMarginSettingsTable(connection).query(subAccountId);
                if (xqGeneralMarginSettings != null) {
                    deltaMarginInfo.setSettingsDataType(XQSettingsDataType.SDT_GENERAL);
                    deltaMarginInfo.setType(xqGeneralMarginSettings.getType());
                    deltaMarginInfo.setMarginDelta(xqGeneralMarginSettings.getMarginDelta());
                    return deltaMarginInfo;
                }
                XQSpecMarginSettings specMarginSettings = new XQSpecMarginSettingsTable(connection).query(subAccountId, commodityId);
                if (specMarginSettings != null) {
                    deltaMarginInfo.setSettingsDataType(XQSettingsDataType.SDT_COMMODITY);
                    deltaMarginInfo.setType(specMarginSettings.getType());
                    deltaMarginInfo.setMarginDelta(specMarginSettings.getMarginDelta());
                    return deltaMarginInfo;
                }
                deltaMarginInfo.setSettingsDataType(XQSettingsDataType.SDT_NO_DATA);
                deltaMarginInfo.setType(FeeCalculateType.FR_DELTA_ADD);
                deltaMarginInfo.setMarginDelta(null);
                return deltaMarginInfo;
            }
        }.query();
    }

    public static List<XQGeneralMarginSettings> queryGeneralUnsyncList(int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<XQGeneralMarginSettings>>(PositionFeeDB.Global()) {
            @Override
            protected List<XQGeneralMarginSettings> onQuery(Connection connection) throws Exception {
                return new XQGeneralMarginSettingsTable(connection).queryUnsyncList(pageIndex, pageSize);
            }
        }.query();
    }

    public static List<XQSpecMarginSettings> querySpecUnsyncList(int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<XQSpecMarginSettings>>(PositionFeeDB.Global()) {
            @Override
            protected List<XQSpecMarginSettings> onQuery(Connection connection) throws Exception {
                return new XQSpecMarginSettingsTable(connection).queryUnsyncList(pageIndex, pageSize);
            }
        }.query();
    }
}
