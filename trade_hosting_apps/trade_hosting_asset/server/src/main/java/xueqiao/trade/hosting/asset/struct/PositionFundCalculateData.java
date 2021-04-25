package xueqiao.trade.hosting.asset.struct;

import java.math.BigDecimal;

public class PositionFundCalculateData {

    private long sledContractId;
    private long sledCommodityId;
    private BigDecimal calculatePrice = BigDecimal.ZERO;
    public BigDecimal getCalculatePrice() {
        return calculatePrice;
    }
    public void setCalculatePrice(BigDecimal calculatePrice) {
        this.calculatePrice = calculatePrice;
    }

    public long getSledCommodityId() {
        return sledCommodityId;
    }

    public void setSledCommodityId(long sledCommodityId) {
        this.sledCommodityId = sledCommodityId;
    }

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

}
