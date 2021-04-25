package xueqiao.trade.hosting.position.fee.core.calculator;

import com.google.gson.Gson;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.common.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.fee.core.api.UpsideEntryApi;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;
import xueqiao.trade.hosting.position.fee.core.util.DataCastUtil;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideOriginalPositionRateTable;
import xueqiao.trade.hosting.upside.entry.TPositionRateDetails;

import java.util.List;

import static xueqiao.trade.hosting.position.fee.core.common.thread.TaskThreadManager.GENERAL_THREAD_KEY;

public abstract class AbstractTradeAccountPositionRateCalculator<Q> extends AbstractCalculator<Void, Q> {
    protected AbstractTradeAccountPositionRateCalculator() {
        super(GENERAL_THREAD_KEY);
    }

    protected List<UpsidePositionRate> getUpsidePositionRate(long tradeAccountId) throws TException {
        TPositionRateDetails details = UpsideEntryApi.fromTradeAccountId(tradeAccountId);
        return DataCastUtil.castToUpsidePositionRateList(details);
    }

    /**
     * 错误重试
     */
    protected List<UpsidePositionRate> getUpsidePositionRateForSure(long tradeAccountId) {
        while (true) {
            try {
                return getUpsidePositionRate(tradeAccountId);
            } catch (ErrorInfo errorInfo) {
                if (errorInfo.getErrorCode() == 2002) {
                    AppLog.e("getUpsidePositionRate fail, tradeAccountId : " + tradeAccountId + ", errorMsg : " + errorInfo.getErrorMsg());
                } else {
                    AppLog.e("getUpsidePositionRate fail with errorInfo, tradeAccountId : " + tradeAccountId, errorInfo);
                }
                return null;
            } catch (Throwable throwable) {
                AppLog.e("getUpsidePositionRate fail with throwable, tradeAccountId : " + tradeAccountId, throwable);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    protected void updateUpsidePositionRateList(long tradeAccountId, List<UpsidePositionRate> upsidePositionRateList) throws ErrorInfo {
        if (upsidePositionRateList == null || upsidePositionRateList.isEmpty()) {
            return;
        }
        setUpsidePositionRateInvalid(tradeAccountId);
        for (UpsidePositionRate upsidePositionRate : upsidePositionRateList) {
            try {
                updateUpsidePositionRate(upsidePositionRate);
            } catch (Throwable throwable) {
                AppLog.e("updateUpsidePositionRateList ---- updateUpsidePositionRate fail, tradeAccountId : " + upsidePositionRate.getTradeAccountId()
                        + ", commodityId : " + upsidePositionRate.getSledCommodityId() + ", contractCode : " + upsidePositionRate.getSledContractCode());
            }
        }
    }

    private void setUpsidePositionRateInvalid(long tradeAccountId) throws ErrorInfo {
        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new UpsideOriginalPositionRateTable(getConnection()).setInvalidByTradeAccountId(tradeAccountId);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }

    protected void updateUpsidePositionRate(UpsidePositionRate upsidePositionRate) throws ErrorInfo {
        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            UpsideOriginalPositionRateTable table;
            UpsidePositionRate originalUpsidePositionRate;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                table = new UpsideOriginalPositionRateTable(getConnection());
                originalUpsidePositionRate = table.query(upsidePositionRate.getTradeAccountId(), upsidePositionRate.getSledCommodityId(), upsidePositionRate.getSledContractCode());
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                if (originalUpsidePositionRate == null) {
                    table.insert(upsidePositionRate);
                } else {
                    // 判断数据是否一致，不一致才需要更新（更新时会设置脏数据状态，表示该数据没有同步到上手合约数据表）
                    Gson gson = new Gson();
                    String originalPositionRateDetail = gson.toJson(originalUpsidePositionRate.getDetail());
                    String updatePositionRateDetail = gson.toJson(upsidePositionRate.getDetail());
                    if (!originalPositionRateDetail.equals(updatePositionRateDetail)) {
                        table.update(upsidePositionRate);
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
