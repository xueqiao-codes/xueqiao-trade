package xueqiao.trade.hosting.asset.calculator.account.sub;

import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;

public class AssetClearCacheCalculator extends AssetBaseCalculator<Void> {
    public AssetClearCacheCalculator(long accountId) {
        super(accountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_CLEAR_CACHE_KEY;
    }

    @Override
    public void onCalculate(Void aVoid) throws Exception {
        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(mAccountId);
        contractGlobal.clearContractExpiredCache();

    }

}
