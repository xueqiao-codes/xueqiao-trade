package xueqiao.trade.hosting.position.statis.service.bean;

public class SledContractData {

	private long sledCommodityId;        // 雪橇合约ID
	private String tradeCurrency;        // 交易币种
	private double contractSize;         // 合约每手乘数
	private double chargeUnit;           // 计价单位

	public long getSledCommodityId() {
		return sledCommodityId;
	}

	public void setSledCommodityId(long sledCommodityId) {
		this.sledCommodityId = sledCommodityId;
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
}
