package xueqiao.trade.hosting.asset.actor;

import com.antiy.error_code.ErrorCodeOuter;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.actor.account.sub.fund.SubAccountPositionFundActor;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetActorFactory {
    private static AssetActorFactory ourInstance = new AssetActorFactory();

    public static AssetActorFactory getInstance() {
        return ourInstance;
    }

    private AssetActorFactory() {
    }

    private Map<String, SubAccountPositionFundActor> positionFundActorMap = new ConcurrentHashMap<>();

    public IAssetActor getAssetActor(long sledContractId, String assetKey, long subAccountId) throws ErrorInfo {

        AssetBaseCalculator calculator = AssetCalculatorFactory.getInstance().getCalculator(assetKey, subAccountId);
        IAssetActor actor;
        if (AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY.equals(assetKey)) {
            String key = getSubAccountContractKey(sledContractId, subAccountId);
            actor = positionFundActorMap.get(key);
            if (actor == null) {
                synchronized (AssetActorFactory.class) {
                    actor = new SubAccountPositionFundActor();
                    actor.onCreate(calculator);
                    positionFundActorMap.put(key, (SubAccountPositionFundActor) actor);
                }
            }
        } else {
            throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "Asset actor not found: " + assetKey);
        }

        return actor;
    }

    private String getSubAccountContractKey(long sledContractId, long subAccountId) {
        return sledContractId + "|" + subAccountId;
    }

    public void removeAssetActor(long sledContractId, String assetKey, long subAccountId) {
        if (AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY.equals(assetKey)) {
            String key = getSubAccountContractKey(sledContractId, subAccountId);
            SubAccountPositionFundActor actor = positionFundActorMap.get(key);
            if (actor != null) {
                try {
                    actor.onDestroy();
                } catch (ErrorInfo errorInfo) {
                    AppLog.e(errorInfo.getMessage(), errorInfo);
                }
                synchronized (AssetActorFactory.class) {
                    positionFundActorMap.remove(key);
                }
            }
        }
    }
}
