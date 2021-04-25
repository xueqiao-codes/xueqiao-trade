package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatClosedPositionItem;
import xueqiao.trade.hosting.position.statis.StatClosedUnit;

import java.util.List;

public class ClosedPositionData {
    StatClosedPositionItem closedPositionItem;
    List<StatClosedUnit> closedUnitList;

    public StatClosedPositionItem getClosedPositionItem() {
        return closedPositionItem;
    }

    public void setClosedPositionItem(StatClosedPositionItem closedPositionItem) {
        this.closedPositionItem = closedPositionItem;
    }

    public List<StatClosedUnit> getClosedUnitList() {
        return closedUnitList;
    }

    public void setClosedUnitList(List<StatClosedUnit> closedUnitList) {
        this.closedUnitList = closedUnitList;
    }
}
