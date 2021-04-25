package xueqiao.trade.hosting.position.adjust.job;

import org.apache.thrift.TException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.adjust.adjust.PositionVerifyQuery;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.position.adjust.storage.PositionDailyDifferenceTable;
import xueqiao.trade.hosting.position.adjust.storage.PrePositionDifferenceTable;
import xueqiao.trade.hosting.position.adjust.thriftapi.DailyPositionDifference;
import xueqiao.trade.hosting.position.adjust.thriftapi.PositionDifference;
import xueqiao.trade.hosting.position.adjust.thriftapi.PrePositionDifference;
import xueqiao.trade.hosting.position.adjust.utils.DateTimeUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerifyDailyPositionJob implements Job {
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

        AppLog.w("Start VerifyDailyPositionJob executing for tradeAccountId= " + tradeAccountId);

        try {
            long now = System.currentTimeMillis();
            long dateSec = DateTimeUtils.getDateTimeSecDatePart(DateTimeUtils.getDateStringDatePart(now));
            Map<Long, DailyPositionDifference> dailyDifferences = new PositionVerifyQuery().verifyDailyPositionDifference(tradeAccountId, now);
            List<PositionDifference> positionDifferences = new PositionVerifyQuery().verifyTotalPositionDifference(tradeAccountId);

            Map<Long, PrePositionDifference> newPreDifferences = new HashMap<>();
            for (PositionDifference positionDifference : positionDifferences) {
                PrePositionDifference preDifference = new PrePositionDifference();
                preDifference.setDateSec(dateSec);
                preDifference.setTradeAccountId(positionDifference.getTradeAccountId());
                preDifference.setSledContractId(positionDifference.getSledContractId());
                preDifference.setSledNetPosition(positionDifference.getSledNetPosition());
                preDifference.setUpsideNetPosition(positionDifference.getUpsideNetPosition());
                preDifference.setDotTimestampMs(now);
                newPreDifferences.put(positionDifference.getSledContractId(), preDifference);
            }

            saveDifference(dailyDifferences, newPreDifferences, tradeAccountId);
            AssignPositionJobScheduler.Global().removeVerifyDailyPositionJob(tradeAccountId);
        } catch (ErrorInfo errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
        }

    }

    private void saveDifference(Map<Long, DailyPositionDifference> differences, Map<Long, PrePositionDifference> newPreDifferences, long tradeAccountId) throws ErrorInfo {

        new DBTransactionHelper<Void>(PositionAdjustDB.Global()) {
            PositionDailyDifferenceTable dailyDifferenceTable;
            PrePositionDifferenceTable prePositionDifferenceTable;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                dailyDifferenceTable = new PositionDailyDifferenceTable(getConnection());
                prePositionDifferenceTable = new PrePositionDifferenceTable(getConnection());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                for (long sledContractId : differences.keySet()) {
                    DailyPositionDifference difference = differences.get(sledContractId);
                    dailyDifferenceTable.add(difference);
                }
                for (long sledContractId : newPreDifferences.keySet()) {
                    PrePositionDifference difference = prePositionDifferenceTable.queryForUpdate(sledContractId, tradeAccountId, true);
                    PrePositionDifference newDifference = newPreDifferences.get(sledContractId);
                    if (difference == null) {
                        prePositionDifferenceTable.add(newDifference);
                    } else {
                        prePositionDifferenceTable.update(newDifference);
                    }
                }

            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();


    }

    private List<PrePositionDifference> getPrePositionDifference(long tradeAccountId) throws ErrorInfo {
        return new DBQueryHelper<List<PrePositionDifference>>(PositionAdjustDB.Global()) {
            @Override
            protected List<PrePositionDifference> onQuery(Connection connection) throws Exception {
                return new PrePositionDifferenceTable(connection).query(tradeAccountId);
            }
        }.query();
    }
}
