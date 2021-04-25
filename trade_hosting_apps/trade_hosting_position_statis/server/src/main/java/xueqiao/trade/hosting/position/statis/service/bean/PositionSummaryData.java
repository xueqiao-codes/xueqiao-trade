package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatClosedPositionSummary;
import xueqiao.trade.hosting.position.statis.StatPositionItem;
import xueqiao.trade.hosting.position.statis.StatPositionUnit;

import java.util.List;

/**
 * 持仓汇总信息数据
 */
public class PositionSummaryData {
    List<StatPositionItem> positionItemList;
    List<StatClosedPositionSummary> closedPositionSummaryList;
    List<StatPositionUnit> positionUnitList;

    public List<StatPositionItem> getPositionItemList() {
        return positionItemList;
    }

    public void setPositionItemList(List<StatPositionItem> positionItemList) {
        this.positionItemList = positionItemList;
    }

    public List<StatClosedPositionSummary> getClosedPositionSummaryList() {
        return closedPositionSummaryList;
    }

    public void setClosedPositionSummaryList(List<StatClosedPositionSummary> closedPositionSummaryList) {
        this.closedPositionSummaryList = closedPositionSummaryList;
    }

    public List<StatPositionUnit> getPositionUnitList() {
        return positionUnitList;
    }

    public void setPositionUnitList(List<StatPositionUnit> positionUnitList) {
        this.positionUnitList = positionUnitList;
    }
}
