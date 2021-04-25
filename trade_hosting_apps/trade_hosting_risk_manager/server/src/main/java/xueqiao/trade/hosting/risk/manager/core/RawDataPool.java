package xueqiao.trade.hosting.risk.manager.core;

import com.google.common.base.Preconditions;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *   原始数据池，用于存放风控数据的原始材料
 */
public class RawDataPool {
    private RiskContext mContext;

    private Set<IRawDataSampleListener> mListeners = new HashSet<>();

    private HostingFund mBaseCurrencyFund;  // 基币总资金

    // 合约持仓信息, 第一层Key为商品ID，第二层Key为合约ID
    private Map<Long, Map<Long, HostingSledContractPosition>> mContractPositions = new HashMap<>();

    // 商品的持仓方向
    private Map<Long, Long> mCommodityPositionCounts = new HashMap<>();

    public RawDataPool(RiskContext context) {
        this.mContext = context;
    }

    public RiskContext getContext() {
        return mContext;
    }

    public void addListener(IRawDataSampleListener listener) {
        if (listener == null) {
            return ;
        }

        mListeners.add(listener);
    }

    public void rmListener(IRawDataSampleListener listener) {
        if (listener == null) {
            return ;
        }

        mListeners.remove(listener);
    }

    public Set<IRawDataSampleListener> getListeners() {
        return mListeners;
    }

    public HostingFund getBaseCurrencyFund() {
        return mBaseCurrencyFund;
    }

    public Map<Long, Map<Long, HostingSledContractPosition>> getContractPositions() {
        return mContractPositions;
    }

    public Map<Long, Long> getCommodityPositionCounts() {
        return mCommodityPositionCounts;
    }

    public void update(HostingFund baseCurrencyFund
            , Map<Long, Map<Long, HostingSledContractPosition>> contractPositions) {
        Preconditions.checkNotNull(baseCurrencyFund);
        Preconditions.checkNotNull(contractPositions);

        this.mBaseCurrencyFund = baseCurrencyFund;
        this.mContractPositions = contractPositions;

        this.mCommodityPositionCounts = new HashMap<>(); // 为了减少后续该项的复制，采用写时重置
        for (Map.Entry<Long, Map<Long, HostingSledContractPosition>> e : contractPositions.entrySet()) {
            long positionCount = 0;
            for (HostingSledContractPosition contractPosition : e.getValue().values()) {
                if (contractPosition.isSetPositionVolume()) {
                    positionCount += contractPosition.getPositionVolume().getNetPosition();
                }
            }
            mCommodityPositionCounts.put(e.getKey(), positionCount);
        }
    }

}
