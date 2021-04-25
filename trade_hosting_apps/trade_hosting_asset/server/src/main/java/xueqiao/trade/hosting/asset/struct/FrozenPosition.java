package xueqiao.trade.hosting.asset.struct;

import java.math.BigDecimal;

public class FrozenPosition {

    private long sledContractId;
    private long sledCommodityId;
    private BigDecimal calculatePrice = BigDecimal.ZERO;
    private long longFrozenPosition;
    private long shortFrozenPosition;
    private BigDecimal frozenCommission = BigDecimal.ZERO;
    private BigDecimal longFrozenMargin = BigDecimal.ZERO;
    private BigDecimal shortFrozenMargin = BigDecimal.ZERO;

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

    public long getLongFrozenPosition() {
        return longFrozenPosition;
    }

    public void setLongFrozenPosition(long longFrozenPosition) {
        this.longFrozenPosition = longFrozenPosition;
    }

    public long getShortFrozenPosition() {
        return shortFrozenPosition;
    }

    public void setShortFrozenPosition(long shortFrozenPosition) {
        this.shortFrozenPosition = shortFrozenPosition;
    }

    public BigDecimal getFrozenCommission() {
        return frozenCommission;
    }

    public void setFrozenCommission(BigDecimal frozenCommission) {
        this.frozenCommission = frozenCommission;
    }

    public BigDecimal getLongFrozenMargin() {
        return longFrozenMargin;
    }

    public void setLongFrozenMargin(BigDecimal longFrozenMargin) {
        this.longFrozenMargin = longFrozenMargin;
    }

    public BigDecimal getShortFrozenMargin() {
        return shortFrozenMargin;
    }

    public void setShortFrozenMargin(BigDecimal shortFrozenMargin) {
        this.shortFrozenMargin = shortFrozenMargin;
    }
}
