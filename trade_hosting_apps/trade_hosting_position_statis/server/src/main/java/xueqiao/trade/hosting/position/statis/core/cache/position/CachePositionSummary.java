package xueqiao.trade.hosting.position.statis.core.cache.position;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

import java.util.Map;

public class CachePositionSummary {
    private long subAccountId;
    private String targetKey;
    private HostingXQTargetType targetType;

    /*
     * （标的层的）净仓
     * */
    private long netPosition;

    /*
     * （标的层的）平仓盈亏
     * */
    private double closedProfit;

    /*
     * 各腿的持仓均价： (买价 * 买量 - 卖价 * 卖量) / 净仓
     * 可用于计算 持仓盈亏
     * (废弃)当净仓为0时，该计算方法不正确
     * */
//    private Map<Long, Double> positionAvgPriceMap;
    /*
     * map<contractId, 各腿持仓总额>
     * 各腿的持仓各额： sum(卖出价 * 卖出量) - sum(买入价 * 买入量)
     * 可用于计算持他盈亏： 各腿持仓盈亏 = (最新价 * 净仓 + 卖出总额 - 买入总额) * 合约乘数 * 计价单位
     * */
    private Map<Long, Double> positionTotalPriceMap;

    public long getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(long subAccountId) {
        this.subAccountId = subAccountId;
    }

    public String getTargetKey() {
        return targetKey;
    }

    public void setTargetKey(String targetKey) {
        this.targetKey = targetKey;
    }

    public HostingXQTargetType getTargetType() {
        return targetType;
    }

    public void setTargetType(HostingXQTargetType targetType) {
        this.targetType = targetType;
    }

    public long getNetPosition() {
        return netPosition;
    }

    public void setNetPosition(long netPosition) {
        this.netPosition = netPosition;
    }

    public double getClosedProfit() {
        return closedProfit;
    }

    public void setClosedProfit(double closedProfit) {
        this.closedProfit = closedProfit;
    }

    public Map<Long, Double> getPositionTotalPriceMap() {
        return positionTotalPriceMap;
    }

    public void setPositionTotalPriceMap(Map<Long, Double> positionTotalPriceMap) {
        this.positionTotalPriceMap = positionTotalPriceMap;
    }
}
