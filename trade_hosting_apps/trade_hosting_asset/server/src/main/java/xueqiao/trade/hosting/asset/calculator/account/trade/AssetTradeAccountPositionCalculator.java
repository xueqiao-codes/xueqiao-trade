package xueqiao.trade.hosting.asset.calculator.account.trade;

import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;

public class AssetTradeAccountPositionCalculator extends AssetBaseCalculator {
    public AssetTradeAccountPositionCalculator(long accountId) {
        super(accountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.TRADE_ACCOUNT_POSITION_KEY;
    }

    @Override
    public void onCalculate(Object o) throws Exception {

    }
}
