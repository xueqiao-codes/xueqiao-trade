package xueqiao.trade.hosting.asset.calculator;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

public abstract class AssetCalculatorRunnable implements Runnable {

    public AssetCalculatorRunnable(AssetBaseCalculator executor) {
        this.mExecutor = executor;
    }

    private AssetBaseCalculator mExecutor;

    @Override
    public void run() {
        boolean run = true;
        while (run) {
            try {
                onRun();
                break;
            } catch (ErrorInfo e) {
                AppLog.e(e.getMessage(), e);
                run = false;
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
                run = false;
            }
        }
    }

    public abstract void onRun() throws Exception;

    public AssetBaseCalculator getmExecutor() {
        return mExecutor;
    }
}
