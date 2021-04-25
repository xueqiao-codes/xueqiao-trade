package xueqiao.trade.hosting.position.fee.storage.handler;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.bean.DeltaCommissionInfo;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQGeneralCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.FeeCalculateType;
import xueqiao.trade.hosting.position.fee.thriftapi.XQGeneralCommissionSettings;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSettingsDataType;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;

import java.sql.Connection;
import java.util.List;

public class XQCommissionSettingsHandler {

    public static DeltaCommissionInfo getDeltaCommissionInfo(long subAccountId, long commodityId) throws ErrorInfo {
        return new DBQueryHelper<DeltaCommissionInfo>(PositionFeeDB.Global()) {
            @Override
            protected DeltaCommissionInfo onQuery(Connection connection) throws Exception {
                DeltaCommissionInfo deltaCommissionInfo = new DeltaCommissionInfo();
                XQGeneralCommissionSettings generalCommissionSettings = new XQGeneralCommissionSettingsTable(connection).query(subAccountId);
                if (generalCommissionSettings != null) {
                    deltaCommissionInfo.setSettingsDataType(XQSettingsDataType.SDT_GENERAL);
                    deltaCommissionInfo.setType(generalCommissionSettings.getType());
                    deltaCommissionInfo.setCommissionDelta(generalCommissionSettings.getCommissionDelta());
                    return deltaCommissionInfo;
                }
                XQSpecCommissionSettings specCommissionSettings = new XQSpecCommissionSettingsTable(connection).query(subAccountId, commodityId);
                if (specCommissionSettings != null) {
                    deltaCommissionInfo.setSettingsDataType(XQSettingsDataType.SDT_COMMODITY);
                    deltaCommissionInfo.setType(specCommissionSettings.getType());
                    deltaCommissionInfo.setCommissionDelta(specCommissionSettings.getCommissionDelta());
                    return deltaCommissionInfo;
                }
                deltaCommissionInfo.setSettingsDataType(XQSettingsDataType.SDT_NO_DATA);
                deltaCommissionInfo.setType(FeeCalculateType.FR_DELTA_ADD);
                deltaCommissionInfo.setCommissionDelta(null);
                return deltaCommissionInfo;
            }
        }.query();
    }

    public static List<XQGeneralCommissionSettings> queryGeneralUnsyncList(int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<XQGeneralCommissionSettings>>(PositionFeeDB.Global()) {
            @Override
            protected List<XQGeneralCommissionSettings> onQuery(Connection connection) throws Exception {
                return new XQGeneralCommissionSettingsTable(connection).queryUnsyncList(pageIndex, pageSize);
            }
        }.query();
    }

    public static List<XQSpecCommissionSettings> querySpecUnsyncList(int pageIndex, int pageSize) throws ErrorInfo {
        return new DBQueryHelper<List<XQSpecCommissionSettings>>(PositionFeeDB.Global()) {
            @Override
            protected List<XQSpecCommissionSettings> onQuery(Connection connection) throws Exception {
                return new XQSpecCommissionSettingsTable(connection).queryUnsyncList(pageIndex, pageSize);
            }
        }.query();
    }
}
