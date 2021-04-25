package xueqiao.trade.hosting.tradeaccount.data.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountFund;
import xueqiao.trade.hosting.tradeaccount.data.storage.TADataDB;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.FundNowTable;
import xueqiao.trade.hosting.upside.entry.TFund;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TAFundSyncTask extends TATaskBase {
    public TAFundSyncTask(long tradeAccountId) {
        super(tradeAccountId);
    }

    @Override
    protected TATaskResult doTask() throws Exception {
        List<TFund> upsideFunds = super.getUpsideEntryStub().getFunds();
        if (upsideFunds == null || upsideFunds.isEmpty()) {
            AppLog.i("funds is empty for tradeAccountId=" + getTradeAccountId());
            return TATaskResult.TASK_FUNDS_EMPTY;
        }

        long updateTimestampMs = System.currentTimeMillis();
        List<TradeAccountFund> funds = upsideFunds.stream().filter(item->{
                Double zero = new Double(0.0);
                return !zero.equals(item.getCredit())
                        || !zero.equals(item.getPreBalance())
                        || !zero.equals(item.getDeposit())
                        || !zero.equals(item.getWithdraw())
                        || !zero.equals(item.getFrozenMargin())
                        || !zero.equals(item.getFrozenCash())
                        || !zero.equals(item.getCurrMargin())
                        || !zero.equals(item.getCommission())
                        || !zero.equals(item.getCloseProfit())
                        || !zero.equals(item.getPositionProfit())
                        || !zero.equals(item.getAvailable())
                        || !zero.equals(item.getDynamicBenefit());
            }).map(item -> {
            return new TradeAccountFund().setTradeAccountId(getTradeAccountId())
                    .setCurrencyChannel(item.getCurrencyChannel())
                    .setCurrencyNo(item.getCurrencyNo())
                    .setCredit(item.getCredit())
                    .setPreBalance(item.getPreBalance())
                    .setDeposit(item.getDeposit())
                    .setWithdraw(item.getWithdraw())
                    .setFrozenMargin(item.getFrozenMargin())
                    .setFrozenCash(item.getFrozenCash())
                    .setCurrMargin(item.getCurrMargin())
                    .setCommission(item.getCommission())
                    .setCloseProfit(item.getCloseProfit())
                    .setPositionProfit(item.getPositionProfit())
                    .setAvailable(item.getAvailable())
                    .setDynamicBenefit(item.getDynamicBenefit())
                    .setRiskRate(item.getRiskRate())
                    .setUpdateTimestampMs(updateTimestampMs);
        }).collect(Collectors.toList());

        new DBStepHelper<Void>(TADataDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new FundNowTable(getConnection()).updateFunds(getTradeAccountId(), funds);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        return TATaskResult.TASK_SUCCESS;
    }
}
