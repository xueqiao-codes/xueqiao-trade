package xueqiao.trade.hosting.asset.actor;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;

public interface IAssetActor {

    void onCreate(AssetBaseCalculator executor) throws ErrorInfo;
    default void onDestroy() throws ErrorInfo {
    }
}
