package xueqiao.trade.hosting.tradeaccount.data.core;

import com.google.common.base.Preconditions;
import xueqiao.trade.hosting.framework.thread.TaskThread;

import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TADataSynchonizer {

    private TaskThread mTaskThread;
    private long mTradeAccountId;
    private boolean mIsDestroied;
    private long mTradeAccountCreateTimestamp;

    public TADataSynchonizer(TaskThread taskThread
            , long tradeAccountId
            , long tradeAccountCreateTimestamp) {
        this.mTaskThread = taskThread;
        this.mTradeAccountId = tradeAccountId;
        this.mTradeAccountCreateTimestamp = tradeAccountCreateTimestamp;
    }

    public TaskThread getTaskThread() {
        return mTaskThread;
    }

    public long getTradeAccountId() {
        return mTradeAccountId;
    }

    private class TAFundSyncTaskImpl extends TAFundSyncTask {
        public TAFundSyncTaskImpl(long tradeAccountId) {
            super(tradeAccountId);
        }

        @Override
        protected boolean shouldDoTask() {
            return !mIsDestroied;
        }

        @Override
        protected void onTaskFinished(TATaskResult result) {
            if (mIsDestroied) {
                return ;
            }

            mTaskThread.postTaskDelay(new TAFundSyncTaskImpl(getTradeAccountId()), 15, TimeUnit.SECONDS);
        }
    }

    private class TANetPositionSyncTaskImpl extends TANetPositionSyncTask {

        public TANetPositionSyncTaskImpl(long tradeAccountId) {
            super(tradeAccountId);
        }

        @Override
        protected boolean shouldDoTask() {
            return !mIsDestroied;
        }

        @Override
        protected void onTaskFinished(TATaskResult result) {
            if (mIsDestroied) {
                return ;
            }

            if (result == TATaskResult.TASK_SUCCESS) {
                mTaskThread.postTaskDelay(new TANetPositionSyncTaskImpl(getTradeAccountId()), 5, TimeUnit.MINUTES);
            } else {
                mTaskThread.postTaskDelay(new TANetPositionSyncTaskImpl(getTradeAccountId()), 60, TimeUnit.SECONDS);
            }
        }
    }

    // ????????????????????????
    private class ForwardSettlementInfoSyncTaskImpl extends TASettlementInfoSyncTask {

        public ForwardSettlementInfoSyncTaskImpl(long tradeAccountId, Date settlementDate) {
            super(tradeAccountId, settlementDate);
        }

        @Override
        protected boolean shouldDoTask() {
            return !mIsDestroied;
        }

        @Override
        protected void onTaskFinished(TATaskResult result) {
            if (result == TATaskResult.TASK_SUCCESS
                    || result == TATaskResult.TASK_SETTLEMENT_CONTENT_EXISTED) {
                // ????????????????????????????????????
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(getSettlementDate());
                calendar.add(Calendar.DATE, 1);

                mTaskThread.postTaskDelay(new ForwardSettlementInfoSyncTaskImpl(
                        getTradeAccountId(), new Date(calendar.getTimeInMillis()))
                    , 3, TimeUnit.SECONDS);
                return ;
            }

            if (result == TATaskResult.TASK_EXCEPTION_OCCURS) {
                mTaskThread.postTaskDelay(new ForwardSettlementInfoSyncTaskImpl(
                        getTradeAccountId(), getSettlementDate())
                    , 60, TimeUnit.SECONDS);
                return ;
            }

            mTaskThread.postTaskDelay(new ForwardSettlementInfoSyncTaskImpl(
                    getTradeAccountId(), getSettlementDate())
                    , 10, TimeUnit.MINUTES);
        }
    }

    // ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
    private class BackwordSettlementInfoSyncTaskImpl extends TASettlementInfoSyncTask {
        public BackwordSettlementInfoSyncTaskImpl(long tradeAccountId, Date settlementDate) {
            super(tradeAccountId, settlementDate);
        }

        @Override
        protected boolean shouldDoTask() {
            return !mIsDestroied;
        }

        @Override
        protected void onTaskFinished(TATaskResult result) {
            Calendar periodLeftCalendar = Calendar.getInstance();
            periodLeftCalendar.setTime(new Date(System.currentTimeMillis()));
            periodLeftCalendar.add(Calendar.DATE, -11);

            if (getSettlementDate().before(periodLeftCalendar.getTime())) {
                mTaskThread.postTaskDelay(new BackwordSettlementInfoSyncTaskImpl(
                        getTradeAccountId(), new Date(System.currentTimeMillis()))
                    , 10, TimeUnit.MINUTES);
                return ;
            }

            if (result == TATaskResult.TASK_SETTLEMENT_CONTENT_EXISTED) {
                mTaskThread.postTaskDelay(new BackwordSettlementInfoSyncTaskImpl(
                        getTradeAccountId(), new Date(System.currentTimeMillis()))
                    , 10, TimeUnit.MINUTES);
                return ;
            }

            if (result == TATaskResult.TASK_EXCEPTION_OCCURS) {
                mTaskThread.postTaskDelay(new BackwordSettlementInfoSyncTaskImpl(
                        getTradeAccountId(), getSettlementDate())
                    , 60,TimeUnit.SECONDS);
                return ;
            }

            Calendar fowardDayCalendar = Calendar.getInstance();
            fowardDayCalendar.setTime(getSettlementDate());
            fowardDayCalendar.add(Calendar.DATE, -1);

            mTaskThread.postTaskDelay(new BackwordSettlementInfoSyncTaskImpl(
                    getTradeAccountId(), new Date(fowardDayCalendar.getTimeInMillis()))
                , 3, TimeUnit.SECONDS);
        }
    }


    public void onCreate() {
        Preconditions.checkArgument(mTaskThread.isInCurrentThread());
        mIsDestroied = false;

        // ???????????????????????????
        mTaskThread.postTask(new TAFundSyncTaskImpl(getTradeAccountId()));

        // ???????????????????????????
        mTaskThread.postTask(new TANetPositionSyncTaskImpl(getTradeAccountId()));

        // ????????????????????????????????????
        Date tradeAccountCreateDate = new Date(mTradeAccountCreateTimestamp*1000);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(tradeAccountCreateDate);
        calendar.add(Calendar.DATE, -10);
        mTaskThread.postTask(new ForwardSettlementInfoSyncTaskImpl(getTradeAccountId(), new Date(calendar.getTimeInMillis())));

        // ??????????????????
        mTaskThread.postTask(new BackwordSettlementInfoSyncTaskImpl(getTradeAccountId(), new Date(System.currentTimeMillis())));
    }

    public void onDestroy() {
        Preconditions.checkArgument(mTaskThread.isInCurrentThread());
        mIsDestroied = true;
    }

}
