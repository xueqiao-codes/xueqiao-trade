package xueqiao.trade.hosting.position.adjust.adjust;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.position.adjust.storage.PositionAssignedTable;
import xueqiao.trade.hosting.position.adjust.storage.PositionManualInputTable;
import xueqiao.trade.hosting.position.adjust.storage.PositionUnassignedTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.*;

import java.sql.Connection;

public class PositionQuery {

    public PositionManualInputPage queryPositionManualInput(ReqPositionManualInputOption option, IndexedPageOption pageOption) throws ErrorInfo {

        return new DBQueryHelper<PositionManualInputPage>(PositionAdjustDB.Global()) {
            @Override
            protected PositionManualInputPage onQuery(Connection connection) throws Exception {
                PositionManualInputTable manualInputTable = new PositionManualInputTable(connection);
                return manualInputTable.query(option, pageOption);
            }
        }.query();
    }

    public PositionUnassignedPage queryPositionUnassigned(ReqPositionUnassignedOption option, IndexedPageOption pageOption) throws ErrorInfo {

        return new DBQueryHelper<PositionUnassignedPage>(PositionAdjustDB.Global()) {
            @Override
            protected PositionUnassignedPage onQuery(Connection connection) throws Exception {
                PositionUnassignedTable unssignedTable = new PositionUnassignedTable(connection);
                return unssignedTable.query(option, pageOption);
            }
        }.query();
    }

    public PositionAssignedPage queryPositionAssigned(ReqPositionAssignedOption option, IndexedPageOption pageOption) throws ErrorInfo {
        return new DBQueryHelper<PositionAssignedPage>(PositionAdjustDB.Global()) {
            @Override
            protected PositionAssignedPage onQuery(Connection connection) throws Exception {
                PositionAssignedTable assignedTable = new PositionAssignedTable(connection);
                return assignedTable.query(option, pageOption);
            }
        }.query();
    }
}
