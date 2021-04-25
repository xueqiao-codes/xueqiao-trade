package xueqiao.trade.hosting.position.statis.service.bean;

import java.util.ArrayList;
import java.util.List;

public class RecoveryClosedData {
    private long closedId;
    private List<PositionData> originalPositionDataList;             // 原持仓数据
    private List<ClosedPositionData> closedPositionDataList;         // 需要恢复的平仓数据
    private List<RecoveryPositionData> positionDataList;             // 恢复后的持仓数据（与已有的持仓合并）

    public RecoveryClosedData() {
        originalPositionDataList = new ArrayList<>();
        closedPositionDataList = new ArrayList<>();
        positionDataList = new ArrayList<>();
    }

    public long getClosedId() {
        return closedId;
    }

    public void setClosedId(long closedId) {
        this.closedId = closedId;
    }

    public List<PositionData> getOriginalPositionDataList() {
        return originalPositionDataList;
    }

    public void addtoOriginalPositionDataList(PositionData originalPositionData) {
        this.originalPositionDataList.add(originalPositionData);
    }

    public List<ClosedPositionData> getClosedPositionDataList() {
        return closedPositionDataList;
    }

    public void addtoClosedPositionDataList(ClosedPositionData closedPositionData) {
        this.closedPositionDataList.add(closedPositionData);
    }

    public List<RecoveryPositionData> getPositionDataList() {
        return positionDataList;
    }

    public void addtoPositionDataList(RecoveryPositionData positionData) {
        this.positionDataList.add(positionData);
    }
}
