package xueqiao.trade.hosting.arbitrage.core.composelimit.cost;

import xueqiao.trade.hosting.framework.utils.PriceUtils;

class ImpactCostProviderImpl implements IImpactCostProvider {

    private ImpactCostCalculatorBase mCalculator;
    private ImpactCostProviderFactory mFactory;

    public ImpactCostProviderImpl(ImpactCostCalculatorBase calculator
            , ImpactCostProviderFactory factory) {
        this.mCalculator = calculator;
        this.mFactory = factory;
    }

    public ImpactCostCalculatorBase getCalculator() {
        return mCalculator;
    }

    @Override
    public Double getImpactCost() {
        return mCalculator.getImpactCost();
    }

    @Override
    public void release() {
        mFactory.releaseProviderImpl(this);
    }

}
