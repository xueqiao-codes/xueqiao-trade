package xueqiao.trade.hosting.position.statis.storage.bean;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;

/**
 * 对接从消息总线过来的成交信息（HostingXQTrade）
 */
public class SourceHostingXQTrade {
	private long tradeId;                               // 雪橇成交ID
	private String orderId;                             // 雪橇成交所属雪橇订单
	private long subAccountId;                          // 子账户id
	private HostingXQTargetType tradeTargetType;        //成交标的类型
	private String tradeTargetKey;                      // 成交标的Key
	private int tradeVolume;                            // 成交量
	private double tradePrice;                          // 成交价
	private HostingXQTradeDirection tradeDiretion;      // 成交方向
	private HostingXQTargetType sourceOrderTargetType;  //原订单标的类型
	private String sourceOrderTargetKey;                // 原订单标的Key
	private long createTimestampMs;                     // 成交时间

	public long getTradeId() {
		return tradeId;
	}

	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public long getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(long subAccountId) {
		this.subAccountId = subAccountId;
	}

	public HostingXQTargetType getTradeTargetType() {
		return tradeTargetType;
	}

	public void setTradeTargetType(HostingXQTargetType tradeTargetType) {
		this.tradeTargetType = tradeTargetType;
	}

	public String getTradeTargetKey() {
		return tradeTargetKey;
	}

	public void setTradeTargetKey(String tradeTargetKey) {
		this.tradeTargetKey = tradeTargetKey;
	}

	public int getTradeVolume() {
		return tradeVolume;
	}

	public void setTradeVolume(int tradeVolume) {
		this.tradeVolume = tradeVolume;
	}

	public double getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(double tradePrice) {
		this.tradePrice = tradePrice;
	}

	public HostingXQTradeDirection getTradeDiretion() {
		return tradeDiretion;
	}

	public void setTradeDiretion(HostingXQTradeDirection tradeDiretion) {
		this.tradeDiretion = tradeDiretion;
	}

	public HostingXQTargetType getSourceOrderTargetType() {
		return sourceOrderTargetType;
	}

	public void setSourceOrderTargetType(HostingXQTargetType sourceOrderTargetType) {
		this.sourceOrderTargetType = sourceOrderTargetType;
	}

	public String getSourceOrderTargetKey() {
		return sourceOrderTargetKey;
	}

	public void setSourceOrderTargetKey(String sourceOrderTargetKey) {
		this.sourceOrderTargetKey = sourceOrderTargetKey;
	}

	public long getCreateTimestampMs() {
		return createTimestampMs;
	}

	public void setCreateTimestampMs(long createTimestampMs) {
		this.createTimestampMs = createTimestampMs;
	}
}
