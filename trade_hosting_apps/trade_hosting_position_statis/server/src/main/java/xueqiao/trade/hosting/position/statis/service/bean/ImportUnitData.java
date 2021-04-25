package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatPositionUnit;
import xueqiao.trade.hosting.position.statis.storage.bean.SourceHostingXQTradeRelatedItem;

import java.util.ArrayList;
import java.util.List;

public class ImportUnitData {
    List<StatPositionUnit> positionUnitList;
    List<SourceHostingXQTradeRelatedItem> relatedItemList;

    public List<StatPositionUnit> getPositionUnitList() {
        return positionUnitList;
    }

    public void setPositionUnitList(List<StatPositionUnit> positionUnitList) {
        this.positionUnitList = positionUnitList;
    }

    public List<SourceHostingXQTradeRelatedItem> getRelatedItemList() {
        return relatedItemList;
    }

    public void setRelatedItemList(List<SourceHostingXQTradeRelatedItem> relatedItemList) {
        this.relatedItemList = relatedItemList;
    }

    public void addToPositionUnitList(StatPositionUnit positionUnit) {
        if (positionUnitList == null) {
            positionUnitList = new ArrayList<>();
        }
        positionUnitList.add(positionUnit);
    }

    public void addToRelatedItemList(SourceHostingXQTradeRelatedItem relatedItem) {
        if (relatedItemList == null) {
            relatedItemList = new ArrayList<>();
        }
        relatedItemList.add(relatedItem);
    }
}
