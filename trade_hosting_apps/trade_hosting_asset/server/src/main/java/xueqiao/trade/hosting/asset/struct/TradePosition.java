package xueqiao.trade.hosting.asset.struct;

import xueqiao.trade.hosting.asset.thriftapi.AssetTradeDetail;

import java.math.BigDecimal;
import java.util.List;

public class TradePosition {

    private long sledContractId;
    private BigDecimal averagePrice = BigDecimal.ZERO;
    private long netPosition;
    private long longPosition;
    private long shortPosition;
    private BigDecimal closeProfit = BigDecimal.ZERO;
    private long sledCommodityId;
    private List<AssetTradeDetail> netPositionTradeDetails;
    private BigDecimal useCommission = BigDecimal.ZERO;

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public long getNetPosition() {
        return netPosition;
    }

    public void setNetPosition(long netPosition) {
        this.netPosition = netPosition;
    }

    public long getLongPosition() {
        return longPosition;
    }

    public void setLongPosition(long longPosition) {
        this.longPosition = longPosition;
    }

    public long getShortPosition() {
        return shortPosition;
    }

    public void setShortPosition(long shortPosition) {
        this.shortPosition = shortPosition;
    }

    public long getSledCommodityId() {
        return sledCommodityId;
    }

    public void setSledCommodityId(long sledCommodityId) {
        this.sledCommodityId = sledCommodityId;
    }

    public List<AssetTradeDetail> getNetPositionTradeDetails() {
        return netPositionTradeDetails;
    }

    public void setNetPositionTradeDetails(List<AssetTradeDetail> netPositionTradeDetails) {
        this.netPositionTradeDetails = netPositionTradeDetails;
    }

    public BigDecimal getCloseProfit() {
        return closeProfit;
    }

    public void setCloseProfit(BigDecimal closeProfit) {
        this.closeProfit = closeProfit;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("sledContractId: ")
                .append(sledContractId)
                .append(" averagePrice: ")
                .append(averagePrice)
                .append(" netPosition: ")
                .append(netPosition)
                .append(" longPosition: ")
                .append(longPosition)
                .append(" shortPosition: ")
                .append(shortPosition)
                .append(" closeProfit: ")
                .append(closeProfit)
                .append(" netPositionTradeDetails: ")
                .append(netPositionTradeDetails)
                .append(" sledCommodityId: ")
                .append(sledCommodityId).toString();
    }

    public BigDecimal getUseCommission() {
        return useCommission;
    }

    public void setUseCommission(BigDecimal useCommission) {
        this.useCommission = useCommission;
    }
}
