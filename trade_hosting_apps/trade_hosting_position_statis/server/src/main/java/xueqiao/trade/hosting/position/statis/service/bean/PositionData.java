package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatPositionItem;
import xueqiao.trade.hosting.position.statis.StatPositionSummary;
import xueqiao.trade.hosting.position.statis.StatPositionUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * 持仓信息
 * 包括：
 * 1 持仓明细
 * 2 持仓小单元
 * 3 持仓汇总
 */
public class PositionData {

    StatPositionItem statPositionItem;
    List<StatPositionUnit> statPositionUnitList;

    public StatPositionItem getStatPositionItem() {
        return statPositionItem;
    }

    public void setStatPositionItem(StatPositionItem statPositionItem) {
        this.statPositionItem = statPositionItem;
    }

    public List<StatPositionUnit> getStatPositionUnitList() {
        return statPositionUnitList;
    }

    public void setStatPositionUnitList(List<StatPositionUnit> statPositionUnitList) {
        this.statPositionUnitList = statPositionUnitList;
    }

    public void addToStatPositionUnitList(StatPositionUnit positionUnit) {
        if (statPositionUnitList == null) {
            if (statPositionUnitList == null) {
                statPositionUnitList = new ArrayList<>();
            }
        }
        statPositionUnitList.add(positionUnit);
    }
}
