package xueqiao.trade.hosting.tradeaccount.data.core;

import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.upside.entry.client.TradeHostingUpsideEntryStub;

import java.io.File;
import java.util.Map;

public abstract class TATaskBase implements Runnable {

    private long mTradeAccountId;

    protected TATaskBase(long tradeAccountId) {
        this.mTradeAccountId = tradeAccountId;
    }

    public long getTradeAccountId() {
        return this.mTradeAccountId;
    }

    protected TradeHostingUpsideEntryStub getUpsideEntryStub() {
        TradeHostingUpsideEntryStub stub = new TradeHostingUpsideEntryStub();
        StringBuilder localSocketFilePathBuilder = new StringBuilder(64);
        localSocketFilePathBuilder.append("/data/trade_hosting_upside_entry/run/")
                .append(mTradeAccountId)
                .append(".sock");
        stub.setSocketFile(new File(localSocketFilePathBuilder.toString()));
        return stub;
    }

    @Override
    public void run()  {
        TATaskResult result = TATaskResult.TASK_SUCCESS;
        StringBuilder logBuilder = new StringBuilder(128);
        try {
            if (!shouldDoTask()) {
                return;
            }

            logBuilder.append(this.getClass().getSimpleName())
                      .append(" runs, tradeAccountId=")
                      .append(mTradeAccountId);
            Map<String, String> parameters = getParameters();
            if (parameters != null) {
                logBuilder.append(", parameters={");
                for (Map.Entry<String, String> e : parameters.entrySet()) {
                    logBuilder.append(e.getKey()).append("=").append(e.getValue()).append(" ");
                }
                logBuilder.append("}");
            }

            result = doTask();
        } catch (ErrorInfo ei) {
            logBuilder.append(", " + ei.toString());
            result = TATaskResult.TASK_EXCEPTION_OCCURS;
        } catch (TException et) {
            logBuilder.append(", TException=").append(et.getMessage());
            result = TATaskResult.TASK_EXCEPTION_OCCURS;
        } catch (Throwable e) {
            logBuilder.append(", Throwable=" + e.getClass().getName()
                    + ", msg=" + e.getMessage());
            result = TATaskResult.TASK_EXCEPTION_OCCURS;
        }

        logBuilder.append(", result=" + result);

        AppLog.i(logBuilder.toString());

        onTaskFinished(result);
    }

    protected Map<String, String> getParameters() {
        return null;
    }

    protected abstract boolean shouldDoTask();
    protected abstract TATaskResult doTask() throws Exception;
    protected abstract void onTaskFinished(TATaskResult result);
}
