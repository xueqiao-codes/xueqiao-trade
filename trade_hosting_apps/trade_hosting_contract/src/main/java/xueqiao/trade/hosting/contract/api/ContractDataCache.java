package xueqiao.trade.hosting.contract.api;

import com.longsheng.xueqiao.contract.standard.thriftapi.CommodityMappingPage;
import com.longsheng.xueqiao.contract.standard.thriftapi.ReqCommodityMappingOption;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import com.longsheng.xueqiao.contract.thriftapi.SledTradeTime;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.container.LRULinkedHashMap;
import org.soldier.base.logger.AppLog;

import java.util.Collections;
import java.util.Map;

public class ContractDataCache {
    private static ContractDataCache sInstance;
    public static ContractDataCache Global() {
        if (sInstance == null) {
            synchronized (ContractDataCache.class) {
                if (sInstance == null) {
                    sInstance = new ContractDataCache();
                }
            }
        }
        return sInstance;
    }

    private Map<Integer, SledContract> mContractData
            = Collections.synchronizedMap(new LRULinkedHashMap<>(4000)); // 缓存合约信息

    private Map<Integer, SledCommodity> mCommodityData
            = Collections.synchronizedMap(new LRULinkedHashMap<>(1000)); // 缓存商品信息

    private Map<String, CommodityMappingPage> mCommodityMappingData
            = Collections.synchronizedMap(new LRULinkedHashMap<>(1000)); // 缓存商品映射信息

    private Map<Integer, SledTradeTime> mCommodityTradeTimeData
            = Collections.synchronizedMap(new LRULinkedHashMap<>(1000)); // 缓存交易时间信息


    public synchronized void clearAll() {
        AppLog.i("clearAll ...");

        mContractData.clear();
        mContractData.clear();
        mCommodityMappingData.clear();
        mCommodityTradeTimeData.clear();
    }

    public SledContract getContract(Integer contractId) {
        return mContractData.get(contractId);
    }

    public void putContract(SledContract contract) {
        if (contract == null) {
            return ;
        }

        mContractData.put(contract.getSledContractId(), contract);
    }

    public SledCommodity getCommodity(Integer commodityId) {
        return mCommodityData.get(commodityId);
    }

    public void putCommodity(SledCommodity commodity) {
        if (commodity == null) {
            return ;
        }

        mCommodityData.put(commodity.getSledCommodityId(), commodity);
    }

    public CommodityMappingPage getCommodityMapping(ReqCommodityMappingOption mappingOption) {
        if (mappingOption == null) {
            return null;
        }

        return mCommodityMappingData.get(mappingOption.toString());
    }

    public void putCommodityMapping(ReqCommodityMappingOption mappingOption, CommodityMappingPage mappingPage) {
        if (mappingOption == null || mappingPage == null) {
            return ;
        }
        String key = mappingOption.toString();
        if (StringUtils.isEmpty(key)) {
            return ;
        }

        mCommodityMappingData.put(key, mappingPage);
    }

    public SledTradeTime getTradeTime(Integer commodityId) {
        return mCommodityTradeTimeData.get(commodityId);
    }

    public void putTradeTime(SledTradeTime tradeTime) {
        if (tradeTime == null) {
            return ;
        }

        mCommodityTradeTimeData.put(tradeTime.getSledCommodityId(), tradeTime);
    }

}
