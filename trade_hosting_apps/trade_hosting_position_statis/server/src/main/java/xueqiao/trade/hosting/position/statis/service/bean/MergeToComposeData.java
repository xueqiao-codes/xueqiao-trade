package xueqiao.trade.hosting.position.statis.service.bean;

import java.util.ArrayList;
import java.util.List;

public class MergeToComposeData {
    private List<PositionData> originalContractPositionDataList;
    private List<PositionData> postMergeContractPositionDataList;
    private PositionData postMergeComposePositionData;

    public List<PositionData> getOriginalContractPositionDataList() {
        return originalContractPositionDataList;
    }

    public void setOriginalContractPositionDataList(List<PositionData> originalContractPositionDataList) {
        this.originalContractPositionDataList = originalContractPositionDataList;
    }

    public List<PositionData> getPostMergeContractPositionDataList() {
        return postMergeContractPositionDataList;
    }

    public void setPostMergeContractPositionDataList(List<PositionData> postMergeContractPositionDataList) {
        this.postMergeContractPositionDataList = postMergeContractPositionDataList;
    }

    public PositionData getPostMergeComposePositionData() {
        return postMergeComposePositionData;
    }

    public void setPostMergeComposePositionData(PositionData postMergeComposePositionData) {
        this.postMergeComposePositionData = postMergeComposePositionData;
    }

    public void addToOriginalContractPositionDataList(PositionData positionData) {
        if (originalContractPositionDataList == null) {
            originalContractPositionDataList = new ArrayList<>();
        }
        originalContractPositionDataList.add(positionData);
    }

    public void addToPostMergeContractPositionDataList(PositionData positionData) {
        if (postMergeContractPositionDataList == null) {
            postMergeContractPositionDataList = new ArrayList<>();
        }
        postMergeContractPositionDataList.add(positionData);
    }
}
