package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatDirection;

public class ComposeLegData {

	private long sledContractId;                   // 雪橇合约id
	private double price;                          // 价格
	private int quantity;                          // 数量（如果对应的标的对象为合约，则unitQuantity固定为1）
	private StatDirection direction;               // 方向

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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public StatDirection getDirection() {
		return direction;
	}

	public void setDirection(StatDirection direction) {
		this.direction = direction;
	}
}
