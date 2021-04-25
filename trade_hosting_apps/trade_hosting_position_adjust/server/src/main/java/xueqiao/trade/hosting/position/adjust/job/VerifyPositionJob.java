package xueqiao.trade.hosting.position.adjust.job;

import org.apache.thrift.TException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.adjust.adjust.PositionVerifyQuery;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.position.adjust.storage.PositionDifferenceTable;
import xueqiao.trade.hosting.position.adjust.storage.PositionVerifyTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifference;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionVerify;

import java.util.List;

public class VerifyPositionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        if (dataMap == null) {
            AppLog.e("StartJob execute, but dataMap is null");
            return;
        }
        long tradeAccountId;
        try {
            tradeAccountId = dataMap.getLong("tradeAccountId");
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
            return;
        }

        AppLog.w("Start VerifyPositionJob executing for tradeAccountId= " + tradeAccountId);

        try {
            List<PositionDifference> differences = new PositionVerifyQuery().verifyTotalPositionDifference(tradeAccountId);
            saveDifference(differences, tradeAccountId);
            AssignPositionJobScheduler.Global().removeVerifyPositionJob(tradeAccountId);
        } catch (ErrorInfo errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
        }

    }

    private void saveDifference(List<PositionDifference> differences, long tradeAccountId) throws ErrorInfo {

        new DBTransactionHelper<Void>(PositionAdjustDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                PositionVerify positionVerify = new PositionVerify();
                boolean isDifferent = differences.size() > 0;
                positionVerify.setDifferent(isDifferent);
                positionVerify.setTradeAccountId(tradeAccountId);
                positionVerify.setVerifyTimestampMs(System.currentTimeMillis());
                PositionVerifyTable verifyTable = new PositionVerifyTable(getConnection());
                long verifyId = verifyTable.add(positionVerify);
                if (isDifferent) {
                    for (PositionDifference difference : differences) {
                        difference.setVerifyId(verifyId);
                        PositionDifferenceTable differenceTable = new PositionDifferenceTable(getConnection());
                        differenceTable.add(difference);
                    }
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }


}