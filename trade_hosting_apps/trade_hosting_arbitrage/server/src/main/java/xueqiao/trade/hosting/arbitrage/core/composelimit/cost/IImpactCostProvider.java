package xueqiao.trade.hosting.arbitrage.core.composelimit.cost;

public interface IImpactCostProvider {
     Double getImpactCost();

     void release();
}
