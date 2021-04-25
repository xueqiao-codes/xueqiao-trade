package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.position.statis.StatClosedPositionSummary;

import java.util.ArrayList;
import java.util.List;

public class ClosedData {
    private List<PositionData> originalPositionDataList;             // 原持仓数据
    private List<PositionData> continuedPositionDataList;            // 持续持仓数据
    private List<ClosedPositionData> closedPositionDataList;               // 平仓数据
    private StatClosedPositionSummary closedPositionSummary;         // 平仓汇总

    public ClosedData() {
        originalPositionDataList = new ArrayList<>();
        continuedPositionDataList = new ArrayList<>();
        closedPositionDataList = new ArrayList<>();
    }

    public List<PositionData> getOriginalPositionDataList() {
        return originalPositionDataList;
    }

    public void addtoOriginalPositionDataList(PositionData originalPositionData) {
        this.originalPositionDataList.add(originalPositionData);
    }

    public List<PositionData> getContinuedPositionDataList() {
        return continuedPositionDataList;
    }

    public void addtoContinuedPositionDataList(PositionData continuedPositionData) {
        this.continuedPositionDataList.add(continuedPositionData);
    }

    public List<ClosedPositionData> getClosedPositionDataList() {
        return closedPositionDataList;
    }

    public void addtoClosedPositionDataList(ClosedPositionData closedPositionData) {
        this.closedPositionDataList.add(closedPositionData);
    }

    public StatClosedPositionSummary getClosedPositionSummary() {
        return closedPositionSummary;
    }

    public void setClosedPositionSummary(StatClosedPositionSummary closedPositionSummary) {
        this.closedPositionSummary = closedPositionSummary;
    }
}
