package xueqiao.trade.hosting.risk.manager.core.rule;

import org.apache.commons.lang.StringUtils;
import xueqiao.trade.hosting.risk.manager.core.RiskItemKeyBuilder;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;

import java.util.TreeMap;

/**
 *  风控规则触发的结构
 */
public class RiskRuleResult {

    private HostingRiskSupportedItem mItem;
    private TreeMap<String, String> mParams;

    private long mSledCommodityId;
    private long mSledContractId;

    private boolean mAlarmTriggered; // 是否触发预警
    private String mAlarmDescription; // 报警描述

    private boolean mForbiddenOpenPositionTriggered; // 是否触发禁止开仓
    private String mForbiddenOpenPositionDescription; // 禁止开仓描述

    public RiskRuleResult(HostingRiskSupportedItem item
            , TreeMap<String, String> params) {
        this.mItem = item;
        this.mParams = params;
    }

    public HostingRiskSupportedItem getItem() {
        return mItem;
    }

    public TreeMap<String, String> getParams() {
        return mParams;
    }

    public String getKey() {
        return RiskItemKeyBuilder.buildKey(mItem.getItemId(), mParams);
    }

    public RiskRuleResult setCommodityId(long sledCommodityId) {
        this.mSledCommodityId = sledCommodityId;
        return this;
    }

    public long getCommodityId() {
        return this.mSledCommodityId;
    }

    public RiskRuleResult setContractId(long sledContractId) {
        this.mSledContractId = sledContractId;
        return this;
    }

    public long getContractId() {
        return this.mSledContractId;
    }

    public RiskRuleResult setAlarmTriggered(boolean triggered, String description) {
        this.mAlarmTriggered = triggered;
        this.mAlarmDescription = description;
        return this;
    }

    public boolean isAlarmTriggered() {
        return mAlarmTriggered;
    }

    public boolean isForbiddenOpenPositionTriggered() {
        return mForbiddenOpenPositionTriggered;
    }

    public RiskRuleResult setForbiddenOpenPositionTriggered(boolean triggered, String description) {
        this.mForbiddenOpenPositionTriggered = triggered;
        this.mForbiddenOpenPositionDescription = description;
        return this;
    }

    public String getForbiddenOpenPositionDescription() {
        return this.mForbiddenOpenPositionDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof RiskRuleResult)) {
            return false;
        }

        RiskRuleResult rrro = (RiskRuleResult)o;
        if (!getKey().equals(rrro.getKey())
                || mSledCommodityId != rrro.mSledCommodityId
                || mSledContractId != rrro.mSledContractId
                || mAlarmTriggered != rrro.mAlarmTriggered
                || !StringUtils.equals(mAlarmDescription, rrro.mAlarmDescription)
                || mForbiddenOpenPositionTriggered != rrro.mForbiddenOpenPositionTriggered
                || !StringUtils.equals(mForbiddenOpenPositionDescription, rrro.mForbiddenOpenPositionDescription)) {
            return false;
        }

        return true;
    }

}
