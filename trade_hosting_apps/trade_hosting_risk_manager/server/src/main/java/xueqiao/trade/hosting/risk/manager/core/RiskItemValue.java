package xueqiao.trade.hosting.risk.manager.core;

import com.google.common.base.Preconditions;

public class RiskItemValue {

    public enum EValueType {
        VALUE_LONG,   // 整数
        VALUE_DOUBLE  // 浮点
    }

    private EValueType mValueType;
    private long mLongValue;
    private double mDoubleValue;
    private boolean mIsSetValue;

    public RiskItemValue(EValueType valueType) {
        Preconditions.checkNotNull(valueType);

        this.mValueType = valueType;
        this.mIsSetValue = false;
    }

    public EValueType getValueType() {
        return mValueType;
    }

    public RiskItemValue(long value) {
        this.mValueType = EValueType.VALUE_LONG;
        this.mLongValue = value;
        this.mIsSetValue = true;
    }

    public RiskItemValue(double value) {
        this.mValueType = EValueType.VALUE_DOUBLE;
        this.mDoubleValue = value;
        this.mIsSetValue = true;
    }

    public long getLong() {
        Preconditions.checkState(mValueType == EValueType.VALUE_LONG);
        return this.mLongValue;
    }

    public double getDouble() {
        Preconditions.checkState(mValueType == EValueType.VALUE_DOUBLE);
        return mDoubleValue;
    }

    public RiskItemValue setValue(long value) {
        Preconditions.checkState(mValueType == EValueType.VALUE_LONG);
        this.mLongValue = value;
        this.mIsSetValue = true;
        return this;
    }

    public RiskItemValue setValue(double value) {
        Preconditions.checkState(mValueType == EValueType.VALUE_DOUBLE);
        this.mDoubleValue = value;
        this.mIsSetValue = true;
        return this;
    }

    public RiskItemValue unsetValue() {
        this.mIsSetValue = false;
        return this;
    }

    public boolean isSetValue() {
        return this.mIsSetValue;
    }

    @Override
    public String toString() {
        StringBuilder valueBuilder = new StringBuilder(32);
        if (mValueType == EValueType.VALUE_LONG) {
            valueBuilder.append("L:").append(mLongValue);
        } else {
            valueBuilder.append("D:").append(mDoubleValue);
        }
        return valueBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (! (o instanceof RiskItemValue)) {
            return false;
        }

        RiskItemValue co = (RiskItemValue)o;
        if (this.mValueType != co.mValueType) {
            return false;
        }
        if (this.mIsSetValue != co.mIsSetValue) {
            return false;
        }

        if (this.mValueType == EValueType.VALUE_LONG) {
            return this.mLongValue == co.mLongValue;
        }
        return 0 == Double.compare(this.mDoubleValue, co.mDoubleValue);
    }

}
