package xueqiao.trade.hosting.position.statis.service.bean;

import java.util.List;

public class DisassembledData {
    private PositionData originalComposeData;                 // 被拆解的组合持仓
    private PositionData disassembleComposeData;              // 拆解后的组合持仓（如果没有全部拆解）
    private List<PositionData> disassembleContractDataList;   // 拆解后的合约持仓

    public PositionData getOriginalComposeData() {
        return originalComposeData;
    }

    public void setOriginalComposeData(PositionData originalComposeData) {
        this.originalComposeData = originalComposeData;
    }

    public PositionData getDisassembleComposeData() {
        return disassembleComposeData;
    }

    public void setDisassembleComposeData(PositionData disassembleComposeData) {
        this.disassembleComposeData = disassembleComposeData;
    }

    public List<PositionData> getDisassembleContractDataList() {
        return disassembleContractDataList;
    }

    public void setDisassembleContractDataList(List<PositionData> disassembleContractDataList) {
        this.disassembleContractDataList = disassembleContractDataList;
    }
}
