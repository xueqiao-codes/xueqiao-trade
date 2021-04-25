package xueqiao.trade.hosting.position.statis.core.cache.contract;

public class StatContractCache {
    private long sledContractId;         // 雪橇合约ID
    private String tradeCurrency;        // 交易币种
    private double contractSize;         // 合约每手乘数
    private double chargeUnit;           // 计价单位

    private double contractCoefficient;   // 合约每手乘数 * 计价单位
    private double contractBaseCurrencyCoefficient;  // 合约每手乘数 * 计价单位 * （当天）基币汇率
    private long modifyTimestampMs;  // 更新时间

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
    }

    public String getTradeCurrency() {
        return tradeCurrency;
    }

    public void setTradeCurrency(String tradeCurrency) {
        this.tradeCurrency = tradeCurrency;
    }

    public double getContractSize() {
        return contractSize;
    }

    public void setContractSize(double contractSize) {
        this.contractSize = contractSize;
    }

    public double getChargeUnit() {
        return chargeUnit;
    }

    public void setChargeUnit(double chargeUnit) {
        this.chargeUnit = chargeUnit;
    }

    public double getContractCoefficient() {
        return contractCoefficient;
    }

    public void setContractCoefficient(double contractCoefficient) {
        this.contractCoefficient = contractCoefficient;
    }

    public double getContractBaseCurrencyCoefficient() {
        return contractBaseCurrencyCoefficient;
    }

    public void setContractBaseCurrencyCoefficient(double contractBaseCurrencyCoefficient) {
        this.contractBaseCurrencyCoefficient = contractBaseCurrencyCoefficient;
    }

    public long getModifyTimestampMs() {
        return modifyTimestampMs;
    }

    public void setModifyTimestampMs(long modifyTimestampMs) {
        this.modifyTimestampMs = modifyTimestampMs;
    }
}
