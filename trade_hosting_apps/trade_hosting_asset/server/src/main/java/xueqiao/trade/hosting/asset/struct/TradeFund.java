package xueqiao.trade.hosting.asset.struct;

import java.math.BigDecimal;

public class TradeFund {
    private long sledContractId;
    private long sledCommodityId;
    private BigDecimal calculatePrice = BigDecimal.ZERO;
    private BigDecimal positionProfit = BigDecimal.ZERO;
    private BigDecimal useMargin = BigDecimal.ZERO;
    private BigDecimal useCommission = BigDecimal.ZERO;
    private BigDecimal frozenMargin = BigDecimal.ZERO;
    private BigDecimal frozenCommission = BigDecimal.ZERO;
    private BigDecimal availableFund = BigDecimal.ZERO;
    private BigDecimal dynamicBenefit = BigDecimal.ZERO;
    private BigDecimal riskRate = BigDecimal.ZERO;
    private String currency;

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

    public long getSledCommodityId() {
        return sledCommodityId;
    }

    public void setSledCommodityId(long sledCommodityId) {
        this.sledCommodityId = sledCommodityId;
    }

    public BigDecimal getCalculatePrice() {
        return calculatePrice;
    }

    public void setCalculatePrice(BigDecimal calculatePrice) {
        this.calculatePrice = calculatePrice;
    }

    public BigDecimal getPositionProfit() {
        return positionProfit;
    }

    public void setPositionProfit(BigDecimal positionProfit) {
        this.positionProfit = positionProfit;
    }

    public BigDecimal getUseMargin() {
        return useMargin;
    }

    public void setUseMargin(BigDecimal useMargin) {
        this.useMargin = useMargin;
    }

    public BigDecimal getUseCommission() {
        return useCommission;
    }

    public void setUseCommission(BigDecimal useCommission) {
        this.useCommission = useCommission;
    }

    public BigDecimal getFrozenMargin() {
        return frozenMargin;
    }

    public void setFrozenMargin(BigDecimal frozenMargin) {
        this.frozenMargin = frozenMargin;
    }

    public BigDecimal getFrozenCommission() {
        return frozenCommission;
    }

    public void setFrozenCommission(BigDecimal frozenCommission) {
        this.frozenCommission = frozenCommission;
    }

    public BigDecimal getAvailableFund() {
        return availableFund;
    }

    public void setAvailableFund(BigDecimal availableFund) {
        this.availableFund = availableFund;
    }

    public BigDecimal getDynamicBenefit() {
        return dynamicBenefit;
    }

    public void setDynamicBenefit(BigDecimal dynamicBenefit) {
        this.dynamicBenefit = dynamicBenefit;
    }

    public BigDecimal getRiskRate() {
        return riskRate;
    }

    public void setRiskRate(BigDecimal riskRate) {
        this.riskRate = riskRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("sledContractId: ")
                .append(sledContractId)
                .append(" sledCommodityId: ")
                .append(sledCommodityId)
                .append(" calculatePrice: ")
                .append(calculatePrice)
                .append(" positionProfit: ")
                .append(positionProfit)
                .append(" useMargin: ")
                .append(useMargin)
                .append(" useCommission: ")
                .append(useCommission)
                .append(" frozenMargin: ")
                .append(frozenMargin)
                .append(" frozenCommission: ")
                .append(frozenCommission)
                .append(" availableFund: ")
                .append(availableFund)
                .append(" dynamicBenefit: ")
                .append(dynamicBenefit)
                .append(" riskRate: ")
                .append(riskRate)
                .append(" currency: ")
                .append(currency)
                .toString();
    }
}
