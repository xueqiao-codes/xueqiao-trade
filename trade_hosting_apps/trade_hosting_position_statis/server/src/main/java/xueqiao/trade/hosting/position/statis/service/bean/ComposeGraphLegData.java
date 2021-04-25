package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatDirection;

public class ComposeGraphLegData {
    public long sledContractId;
    public int quantity;
    public StatDirection direction;

    public long getSledContractId() {
        return sledContractId;
    }

    public void setSledContractId(long sledContractId) {
        this.sledContractId = sledContractId;
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
