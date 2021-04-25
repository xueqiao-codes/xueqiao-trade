package xueqiao.trade.hosting.position.statis.service.bean;

import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CheckNetPositionReqData {
    private long subAccountId;
    private List<PositionTargetData> positionTargetDataList;
    /*
    * 引入map是为了解决重复数据的问题
    * */
    private Map<String, PositionTargetData> positionTargetDataMap;

    public CheckNetPositionReqData(long subAccountId) {
        positionTargetDataList = new ArrayList<>();
        positionTargetDataMap = new HashMap<>();
        this.subAccountId = subAccountId;
    }

    public void addToPositionTargetData(PositionTargetData data) {

        String key = data.getTargetKey() + data.getTargetType().getValue();
        PositionTargetData positionTargetData = positionTargetDataMap.get(key);
        if (positionTargetData == null) {
            positionTargetDataList.add(data);
            positionTargetDataMap.put(key, data);
        }
    }

    public void addToPositionTargetData(String targetKey, HostingXQTargetType targetType) {
        String key = targetKey + targetType.getValue();
        PositionTargetData positionTargetData = positionTargetDataMap.get(key);
        if (positionTargetData == null) {
            positionTargetData = new PositionTargetData();
            positionTargetData.setTargetKey(targetKey);
            positionTargetData.setTargetType(targetType);
            positionTargetDataList.add(positionTargetData);
            positionTargetDataMap.put(key, positionTargetData);
        }
    }

    public List<PositionTargetData> getPositionTargetDataList() {
        return positionTargetDataList;
    }

    public long getSubAccountId() {
        return subAccountId;
    }
}
