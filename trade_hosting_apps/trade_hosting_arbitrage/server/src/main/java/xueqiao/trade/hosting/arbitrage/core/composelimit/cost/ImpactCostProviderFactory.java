package xueqiao.trade.hosting.arbitrage.core.composelimit.cost;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ImpactCostProviderFactory {
    private static final int MAX_CACHED_CALCULATORS_SIZE = 20;

    private static ImpactCostProviderFactory sInstance;
    public static ImpactCostProviderFactory Global() {
        if (sInstance == null) {
            synchronized (ImpactCostProviderFactory.class) {
                if (sInstance == null) {
                    sInstance = new ImpactCostProviderFactory();
                }
            }
        }
        return sInstance;
    }

    private LinkedHashMap<Long, ImpactCostCalculatorBase> mNonRefCalculatorsIndex = new LinkedHashMap<>();
    private Map<Long, ImpactCostCalculatorBase> mAllCalculators= new HashMap<>();

    public IImpactCostProvider getProvider(SledContractDetails sledContractDetails) {
        Preconditions.checkNotNull(sledContractDetails);
        Preconditions.checkNotNull(sledContractDetails.getSledContract());

        long sledContractId= sledContractDetails.getSledContract().getSledContractId();
        synchronized (this) {
            ImpactCostCalculatorBase calculator = mAllCalculators.get(sledContractId);
            if (calculator == null) {
                calculator = createImpactCalculator(sledContractDetails);
                mAllCalculators.put(sledContractId, calculator);
            }
            calculator.incRefCount();

            // 从待销毁缓存中移除
            mNonRefCalculatorsIndex.remove(sledContractId);

            return new ImpactCostProviderImpl(calculator, this);
        }
    }

    void releaseProviderImpl(ImpactCostProviderImpl provider) {
        synchronized (this) {
            if (0 == provider.getCalculator().decRefCount()) {
                while(mNonRefCalculatorsIndex.size() >= MAX_CACHED_CALCULATORS_SIZE) {
                    // 待销毁缓存满了，释放头部元素
                    Map.Entry<Long, ImpactCostCalculatorBase> headNoneRefEntry
                            = mNonRefCalculatorsIndex.entrySet().iterator().next();

                    // 释放资源
                    headNoneRefEntry.getValue().destroy();
                    mAllCalculators.remove(headNoneRefEntry.getKey());
                    mNonRefCalculatorsIndex.remove(headNoneRefEntry.getKey());
                }

                // 加入待销毁缓存
                long sledContractId = provider.getCalculator()
                        .getContractDetails().getSledContract().getSledContractId();
                mNonRefCalculatorsIndex.put(sledContractId, provider.getCalculator());
            }
        }
    }

    private ImpactCostCalculatorBase createImpactCalculator(SledContractDetails contractDetails) {
        return new ImpactCostCalculatorSimple(contractDetails);
    }
}
