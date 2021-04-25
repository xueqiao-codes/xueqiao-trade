package xueqiao.trade.hosting.tradeaccount.data.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFundHisItem;
import xueqiao.trade.hosting.tradeaccount.data.storage.TADataDB;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundHisTable;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundNowTable;

import java.sql.Date;
import java.util.List;

public class TAFundSampleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        if (dataMap == null) {
            AppLog.e("TAFundSampleJob error, dataMap is null");
            return ;
        }

        long tradeAccountId = dataMap.getLong(JobConstants.TRADEACCOUNT_ID_KEY);
        if (tradeAccountId <= 0) {
            AppLog.e("TAFundSampleJob, tradeAccountId <= 0");
            return ;
        }

        AppLog.i("TAFundSampleJob started for tradeAccountId=" + tradeAccountId);

        int retryCount = 100;
        while((retryCount--) > 0) {
            try {
                doSample(tradeAccountId);
                break;
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
            }
        }
    }

    private void doSample(long tradeAccountId) throws ErrorInfo {
        new DBStepHelper<Void>(TADataDB.Global()) {
            private Date today = new Date(System.currentTimeMillis());
            private List<TradeAccountFund> funds;
            private boolean needInsert = false;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                funds = new FundNowTable(getConnection()).getFunds(tradeAccountId);
                if (funds == null || funds.isEmpty()) {
                    AppLog.e("TAFundSampleJob for tradeAccountId=" + tradeAccountId + ", funds is empty");
                    return;
                }

                // 更新时间不应该小于采样时间10分钟
                if (funds.get(0).getUpdateTimestampMs() < (System.currentTimeMillis() - 10 * 60 * 1000)) {
                    AppLog.e("TAFundSampleJob for tradeAccountId=" + tradeAccountId
                            + ", funds update timestamp period too long");
                    return;
                }

                // 判断记录是否存在
                TradeAccountFundHisItem dbHisItem
                        = new FundHisTable(getConnection()).getFundHisItem(tradeAccountId, today);
                if (dbHisItem != null) {
                    return ;
                }

                needInsert = true;
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (!needInsert) {
                    return ;
                }

                TradeAccountFundHisItem newHisItem = new TradeAccountFundHisItem();
                newHisItem.setTradeAccountId(tradeAccountId);
                newHisItem.setDate(today.toString());
                newHisItem.setFunds(funds);
                new FundHisTable(getConnection()).insertFundHisItem(newHisItem);
            }

            @Override
            public Void getResult() {
                return null;
            }

        }.execute();
    }
}
