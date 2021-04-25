package xueqiao.trade.hosting.asset.calculator.account.sub;

import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.thriftapi.AssetCalculateConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetCalculateConfigCalculator extends AssetBaseCalculator<Long> {
    public AssetCalculateConfigCalculator(long accountId) {
        super(accountId);
    }

    @Override
    public void onCalculate(Long sledContractId) throws Exception {
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(this.mAccountId);
        globalData.getAssetCalculateConfig(sledContractId, true);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_CALCULATE_CONFIG_KEY;
    }
}
