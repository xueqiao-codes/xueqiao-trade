package xueqiao.trade.hosting.position.fee.core.bean;

import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.FeeCalculateType;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSettingsDataType;

public class DeltaCommissionInfo {

    private FeeCalculateType type;
    private XQSettingsDataType settingsDataType;
    private CommissionInfo commissionDelta;

    public FeeCalculateType getType() {
        return type;
    }

    public void setType(FeeCalculateType type) {
        this.type = type;
    }

    public XQSettingsDataType getSettingsDataType() {
        return settingsDataType;
    }

    public void setSettingsDataType(XQSettingsDataType settingsDataType) {
        this.settingsDataType = settingsDataType;
    }

    public CommissionInfo getCommissionDelta() {
        return commissionDelta;
    }

    public void setCommissionDelta(CommissionInfo commissionDelta) {
        this.commissionDelta = commissionDelta;
    }
}
