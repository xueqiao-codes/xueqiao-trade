package xueqiao.trade.hosting.position.statis.storage.bean;

import xueqiao.trade.hosting.position.adjust.assign.thriftapi.PositionDirection;

/**
 * 录入分配的持仓数据
 * */
public class SourceAssignedPosition {
	private long assignId;                       // 分配id
	private long subAccountId;                   // 子账户id
	private long sledContractId;                 // 雪橇合约id
	private double price;                        // 价格
	private int volume;                          // 分配数量
	private PositionDirection positionDirection; // 持仓方向
	private long positionTimestampMs;            // 持仓生效时间
	private long createTimestampMs;

	private boolean isCompleted;                 // 是否为已核对过的全部归档数据

	public long getAssignId() {
		return assignId;
	}

	public void setAssignId(long assignId) {
		this.assignId = assignId;
	}

	public long getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(long subAccountId) {
		this.subAccountId = subAccountId;
	}

	public long getSledContractId() {
		return sledContractId;
	}

	public void setSledContractId(long sledContractId) {
		this.sledContractId = sledContractId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public PositionDirection getPositionDirection() {
		return positionDirection;
	}

	public void setPositionDirection(PositionDirection positionDirection) {
		this.positionDirection = positionDirection;
	}

	public long getPositionTimestampMs() {
		return positionTimestampMs;
	}

	public void setPositionTimestampMs(long positionTimestampMs) {
		this.positionTimestampMs = positionTimestampMs;
	}

	public long getCreateTimestampMs() {
		return createTimestampMs;
	}

	public void setCreateTimestampMs(long createTimestampMs) {
		this.createTimestampMs = createTimestampMs;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}
}
