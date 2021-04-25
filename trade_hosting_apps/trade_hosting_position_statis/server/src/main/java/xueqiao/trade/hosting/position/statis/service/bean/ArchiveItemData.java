package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatClosedPositionSummary;

import java.util.List;

public class ArchiveItemData {
    StatClosedPositionSummary statClosedPositionSummary;
    List<ClosedPositionData> closedPositionDataList;

    public StatClosedPositionSummary getStatClosedPositionSummary() {
        return statClosedPositionSummary;
    }

    public void setStatClosedPositionSummary(StatClosedPositionSummary statClosedPositionSummary) {
        this.statClosedPositionSummary = statClosedPositionSummary;
    }

    public List<ClosedPositionData> getClosedPositionDataList() {
        return closedPositionDataList;
    }

    public void setClosedPositionDataList(List<ClosedPositionData> closedPositionDataList) {
        this.closedPositionDataList = closedPositionDataList;
    }
}
