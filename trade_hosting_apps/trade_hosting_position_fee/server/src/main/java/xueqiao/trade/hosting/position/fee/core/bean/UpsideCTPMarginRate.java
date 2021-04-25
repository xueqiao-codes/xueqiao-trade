package xueqiao.trade.hosting.position.fee.core.bean;

public class UpsideCTPMarginRate {
    private UpsideCTPExchangeMarginRate exchangeMarginRate;
    private UpsideCTPInstrumentMarginRate instrumentMarginRate;

    public UpsideCTPExchangeMarginRate getExchangeMarginRate() {
        return exchangeMarginRate;
    }

    public void setExchangeMarginRate(UpsideCTPExchangeMarginRate exchangeMarginRate) {
        this.exchangeMarginRate = exchangeMarginRate;
    }

    public UpsideCTPInstrumentMarginRate getInstrumentMarginRate() {
        return instrumentMarginRate;
    }

    public void setInstrumentMarginRate(UpsideCTPInstrumentMarginRate instrumentMarginRate) {
        this.instrumentMarginRate = instrumentMarginRate;
    }
}
