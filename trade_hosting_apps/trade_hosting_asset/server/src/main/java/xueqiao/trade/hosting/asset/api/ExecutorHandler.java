package xueqiao.trade.hosting.asset.api;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.*;

import java.util.concurrent.TimeUnit;

public class ExecutorHandler {

    public static boolean needFrozen(HostingExecOrderStateValue stateValue) {
        switch (stateValue) {
            case ORDER_VERIFY_FAILED:
            case ORDER_UPSIDE_DELETED:
            case ORDER_EXPIRED:
            case ORDER_UPSIDE_FINISHED:
            case ORDER_UPSIDE_REJECTED:
            case ORDER_SLED_SEND_FAILED:
                return false;
        }
        return true;
    }

    public static boolean calculatePosition(AssetTradeDetail detail) throws ErrorInfo {
        return calculate(detail, AssetCalculatorFactory.SUB_ACCOUNT_POSITION_KEY, detail.getSubAccountId());
    }

    public static boolean calculateFrozenPosition(HostingExecOrder execOrder) throws ErrorInfo {
        return calculate(execOrder, AssetCalculatorFactory.SUB_ACCOUNT_FROZEN_POSITION_KEY, execOrder.getSubAccountId());
    }

    public static boolean calculateFund(FundCalculateData fundCalculateData) throws ErrorInfo {
        return calculate(fundCalculateData, AssetCalculatorFactory.SUB_ACCOUNT_FUND_KEY, fundCalculateData.getSubAccountId());
    }

    public static boolean calculateFundDelay(FundCalculateData fundCalculateData, long delay, TimeUnit timeUnit) throws ErrorInfo {
        return calculateDelay(fundCalculateData, AssetCalculatorFactory.SUB_ACCOUNT_FUND_KEY, fundCalculateData.getSubAccountId(), delay, timeUnit);
    }

    public static boolean calculatePositionFund(PositionFundCalculateData positionFundCalculateData, long subAccountId) throws ErrorInfo {
        return calculate(positionFundCalculateData, AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY, subAccountId);
    }

    public static boolean calculateFrozenFund(PositionFundCalculateData data, long subAccountId) throws ErrorInfo {
        return calculate(data, AssetCalculatorFactory.SUB_ACCOUNT_FROZEN_FUND_KEY, subAccountId);
    }

    public static boolean calculateSettlement(long sledContractId, long subAccountId, long delay, TimeUnit timeUnit) throws ErrorInfo {

        return calculateDelay(sledContractId, AssetCalculatorFactory.SUB_ACCOUNT_SETTLEMENT_KEY, subAccountId, delay, timeUnit);
    }

    private static boolean calculateDelay(Object data, String executorKey, long subAccountId, long delay, TimeUnit timeUnit) throws ErrorInfo {
        AssetBaseCalculator calculator = AssetCalculatorFactory.getInstance().getCalculator(executorKey, subAccountId);
        if (calculator == null) {
            AppLog.i("not handle calculator");
            return false;
        }
        calculator.addTask(data, delay, timeUnit);
        return true;
    }

    private static boolean calculate(Object data, String executorKey, long subAccountId) throws ErrorInfo {
        return calculateDelay(data, executorKey, subAccountId, 0, TimeUnit.NANOSECONDS);
    }

    public static void printAsset(Long subAccountId) {
        AppLog.i(AssetGlobalDataFactory.getInstance().getAssetGlobalData(subAccountId).getHostingPositionMap());
        AppLog.i(AssetGlobalDataFactory.getInstance().getAssetGlobalData(subAccountId).getHostingFundMap());
    }
}
