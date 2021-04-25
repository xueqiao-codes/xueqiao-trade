package xueqiao.trade.hosting.position.fee.core.bean;

import xueqiao.trade.hosting.position.fee.thriftapi.FeeCalculateType;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSettingsDataType;

public class DeltaMarginInfo {

    private FeeCalculateType type;
    private XQSettingsDataType settingsDataType;
    private MarginInfo marginDelta;

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

    public MarginInfo getMarginDelta() {
        return marginDelta;
    }

    public void setMarginDelta(MarginInfo marginDelta) {
        this.marginDelta = marginDelta;
    }
}
