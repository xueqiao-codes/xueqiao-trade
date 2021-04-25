package xueqiao.trade.hosting.tradeaccount.data.core;

import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountNetPositionSummary;
import xueqiao.trade.hosting.tradeaccount.data.storage.TADataDB;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.NetPositionSummaryTable;
import xueqiao.trade.hosting.upside.entry.TNetPositionSummary;

import java.util.List;
import java.util.stream.Collectors;

public abstract class TANetPositionSyncTask extends TATaskBase {

    public TANetPositionSyncTask(long tradeAccountId) {
        super(tradeAccountId);
    }

    @Override
    protected TATaskResult doTask() throws Exception {
        List<TNetPositionSummary> upsidePositions =  getUpsideEntryStub().getNetPositionSummaries();

        long updateTimestampMs = System.currentTimeMillis();
        List<TradeAccountNetPositionSummary> positionSummaries
                = upsidePositions.stream().map(p->{
            TradeAccountNetPositionSummary positionSummary = new TradeAccountNetPositionSummary()
                    .setTradeAccountId(getTradeAccountId())
                    .setSledExchangeCode(p.getSledExchangeCode())
                    .setSledCommodityId(p.getSledCommodityId())
                    .setSledCommodityCode(p.getSledCommodityCode())
                    .setSledCommodityType(p.getSledCommodityType())
                    .setSledContractCode(p.getSledContractCode())
                    .setNetVolume(p.getNetVolume())
                    .setUpdateTimestampMs(updateTimestampMs);
            if (p.isSetAveragePrice()) {
                positionSummary.setAveragePrice(p.getAveragePrice());
            }
            return positionSummary;
        }).collect(Collectors.toList());

        new DBStepHelper<Void>(TADataDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new NetPositionSummaryTable(getConnection()).updateNetPositionSummary(getTradeAccountId(), positionSummaries);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();


        return TATaskResult.TASK_SUCCESS;
    }

}
