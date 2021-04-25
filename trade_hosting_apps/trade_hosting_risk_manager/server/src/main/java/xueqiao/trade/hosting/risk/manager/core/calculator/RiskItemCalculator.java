package xueqiao.trade.hosting.risk.manager.core.calculator;

import com.google.common.base.Preconditions;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.risk.manager.core.RawDataPool;
import xueqiao.trade.hosting.risk.manager.core.RiskItemKeyBuilder;
import xueqiao.trade.hosting.risk.manager.core.RiskItemValue;
import xueqiao.trade.hosting.risk.manager.thriftapi.EHostingRiskItemValueType;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskSupportedItem;

import java.util.Map;
import java.util.TreeMap;

public abstract class RiskItemCalculator {

    private HostingRiskSupportedItem mItem;
    private TreeMap<String, String>   mParams;

    private RiskItemValue mValue;

    public RiskItemCalculator init(HostingRiskSupportedItem item
            , TreeMap<String, String> params) {
        this.mItem = item;
        this.mParams = params;

        if (mItem.getItemValueType() == EHostingRiskItemValueType.LONG_VALUE) {
            mValue = new RiskItemValue(RiskItemValue.EValueType.VALUE_LONG);
        } else {
            mValue = new RiskItemValue(RiskItemValue.EValueType.VALUE_DOUBLE);
        }

        onInit();

        return this;
    }

    public String getParamForSure(String key) {
        Preconditions.checkNotNull(mParams);
        String param = mParams.get(key);
        Preconditions.checkNotNull(param);
        return param;
    }

    /**
     *  指定预测的项目
     */
    public HostingRiskSupportedItem getItem() {
        return mItem;
    }

    public String getKey() {
        return RiskItemKeyBuilder.buildKey(mItem.getItemId(), mParams);
    }

    /**
     *  获取值
     */
    public RiskItemValue getValue() {
        return mValue;
    }

    /**
     *  获取监测项的参数
     */
    public TreeMap<String, String> getParams() {
        return mParams;
    }

    /**
     *  值未变化返回假，否则返回真
     * @param rawDataPool
     * @return
     */
    public boolean calculate(RawDataPool rawDataPool) {
        RiskItemValue newValue = new RiskItemValue(getValue().getValueType());
        onCalculate(rawDataPool, newValue);

        if (AppLog.traceEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("refreshValue ")
                    .append(getKey());

            if (mValue.equals(newValue)) {
                logBuilder.append(", oldValue=newValue=" + mValue);
            } else {
                logBuilder.append(", oldValue=").append(mValue)
                        .append(", newValue=").append(newValue);
            }
            AppLog.t(logBuilder.toString());
        }

        if (mValue.equals(newValue)) {
            return false;
        }

        mValue = newValue;
        return true;
    }

    protected abstract void onInit();

    /**
     *  驱动一次计算
     */
    protected abstract void onCalculate(RawDataPool rawDataPool
            , RiskItemValue newValue);

}
