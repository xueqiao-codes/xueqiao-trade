package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsidePositionRateDetail {
    private UpsideCTPMarginRate ctpMarginRate;
    private UpsideES9MarginRate es9MarginRate;
    private UpsideCTPCommissionRate ctpCommissionRate;
    private UpsideES9CommissionRate es9CommissionRate;

    public UpsideCTPMarginRate getCtpMarginRate() {
        return ctpMarginRate;
    }

    public void setCtpMarginRate(UpsideCTPMarginRate ctpMarginRate) {
        this.ctpMarginRate = ctpMarginRate;
    }

    public UpsideES9MarginRate getEs9MarginRate() {
        return es9MarginRate;
    }

    public void setEs9MarginRate(UpsideES9MarginRate es9MarginRate) {
        this.es9MarginRate = es9MarginRate;
    }

    public UpsideCTPCommissionRate getCtpCommissionRate() {
        return ctpCommissionRate;
    }

    public void setCtpCommissionRate(UpsideCTPCommissionRate ctpCommissionRate) {
        this.ctpCommissionRate = ctpCommissionRate;
    }

    public UpsideES9CommissionRate getEs9CommissionRate() {
        return es9CommissionRate;
    }

    public void setEs9CommissionRate(UpsideES9CommissionRate es9CommissionRate) {
        this.es9CommissionRate = es9CommissionRate;
    }
}
