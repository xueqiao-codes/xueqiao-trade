package xueqiao.trade.hosting.position.adjust.job;

import com.antiy.error_code.ErrorCodeOuter;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.adjust.api.HostingAssetHandler;
import xueqiao.trade.hosting.position.adjust.api.PositionStatisHandler;
import xueqiao.trade.hosting.position.adjust.assign.thriftapi.AssignPositionResp;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.position.adjust.storage.PositionAssignTaskTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.AoType;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionAssignTask;
import xueqiao.trade.hosting.position.adjust.thriftapi.TradeHostingPositionAdjustErrorCode;

public class AssignPositionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        if (dataMap == null) {
            AppLog.e("StartJob execute, but dataMap is null");
            return;
        }
        AoType aoType;
        try {
            aoType = AoType.findByValue(dataMap.getInt("aoType"));
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
            return;
        }

        AppLog.d("StartJob start executing for aoType= " + aoType);
        try {
            new DBTransactionHelper<Void>(PositionAdjustDB.Global()) {
                @Override
                public void onPrepareData() throws ErrorInfo, Exception {

                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    PositionAssignTaskTable taskTable = new PositionAssignTaskTable(getConnection());
                    PositionAssignTask task = taskTable.query(aoType);
                    if (task != null) {
                        if (AoType.ASSET.equals(aoType)) {
                            HostingAssetHandler hostingAssetHandler = new HostingAssetHandler();
                            AssignPositionResp resp = hostingAssetHandler.assignPosition(task.getPositionAssigned());
                            if (!resp.isSuccess()) {
                                throw new ErrorInfo(TradeHostingPositionAdjustErrorCode.ERROR_POSITION_ASSIGN_ERROR_IN_ASSET.getValue(),
                                        "Assign position call asset not success.");
                            }
                        } else if (AoType.STATIS.equals(aoType)) {
                            PositionStatisHandler positionStatisHandler = new PositionStatisHandler();
                            positionStatisHandler.assignPosition(task.getPositionAssigned());
                        } else {
                            throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "task type not support.");
                        }

                        taskTable.delete(task.getTaskId());
                    } else {
                        AssignPositionJobScheduler.Global().removeAssignAoJob(aoType);
                    }
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();
        } catch (Exception errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        }
    }
}
