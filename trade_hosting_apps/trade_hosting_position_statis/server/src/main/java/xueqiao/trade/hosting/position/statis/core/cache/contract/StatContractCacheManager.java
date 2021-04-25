package xueqiao.trade.hosting.position.statis.core.cache.contract;

import com.google.gson.Gson;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityConfig;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.statis.core.cache.ICacheManager;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.statis.thriftapi.StatPositionErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.lang.ref.SoftReference;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatContractCacheManager implements ICacheManager {
    /*
     * 合约信息缓存有效时段 10分钟
     * */
    private static final long CONTRACT_CACHE_EFFECTIVE_PERIOD = 10 * 60 * 1000;
    private static StatContractCacheManager ourInstance = new StatContractCacheManager();

    private Map<String, SoftReference<StatContractCache>> softReferenceMap = new HashMap<>();

    public static StatContractCacheManager getInstance() {
        return ourInstance;
    }

    private StatContractCacheManager() {
    }

    /**
     * 从缓存中获取合约信息
     * 有效期为10分钟，过期重新调用接口获取
     */
    public StatContractCache getContract(long sledContractId, String contractSymbol) throws ErrorInfo {
        SoftReference<StatContractCache> contractCacheSoftReference = softReferenceMap.get(contractSymbol);
        StatContractCache statContractCache = null;
        if (contractCacheSoftReference != null) {
            statContractCache = contractCacheSoftReference.get();
            if (statContractCache != null) {
                if (System.currentTimeMillis() - statContractCache.getModifyTimestampMs() < CONTRACT_CACHE_EFFECTIVE_PERIOD) {
                    /*
                     * 深拷贝，避免合约信息使用的过程，该合约信息因软引用被释放掉
                     * */
                    return deepCopy(statContractCache);
                } else {
                    AppLog.i("getContract ---- statContractCache is timeout, sledContractId : " + sledContractId + ", statContractCache : " + new Gson().toJson(statContractCache));
                }
            } else {
                AppLog.i("getContract ---- statContractCache is null, sledContractId : " + sledContractId);
            }
        } else {
            AppLog.i("getContract ---- contractCacheSoftReference is null, sledContractId : " + sledContractId);
        }

        statContractCache = getComposeGraphFromApi(sledContractId);
        if (statContractCache != null) {
            contractCacheSoftReference = new SoftReference(statContractCache);
            softReferenceMap.put(contractSymbol, contractCacheSoftReference);
        } else {
            AppLog.e("getContract ---- getComposeGraphFromApi is null, sledContractId : " + sledContractId);
        }
        return deepCopy(statContractCache);
    }

    /**
     * 直接从接口获取合约信息
     * 用于低频的调用
     * */
    public static SledContractDetails getSledContractDetails(long sledContractId) {
        IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        try {
            return contractApi.getContractDetailForSure(sledContractId);
        } catch (ErrorInfo errorInfo) {
            AppReport.reportErr("getSledContractDetails ---- getContractDetailForSure fail, sledContractId : " + sledContractId, errorInfo);
        }
        return null;
    }

    private StatContractCache deepCopy(StatContractCache contractCache) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(contractCache), StatContractCache.class);
    }

    private StatContractCache getComposeGraphFromApi(long sledContractId) throws ErrorInfo {
//        AppLog.i("getComposeGraphFromApi ---- sledContractId : " + sledContractId);
        IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        SledContractDetails details = contractApi.getContractDetailForSure(sledContractId);

        SledCommodityConfig SledCommodityConfig = getSledCommodityConfig(details.getSledCommodity());
        StatContractCache contractCache = new StatContractCache();
        contractCache.setSledContractId(details.getSledContract().getSledContractId());
        contractCache.setTradeCurrency(details.getSledCommodity().getTradeCurrency());
        contractCache.setContractSize(details.getSledCommodity().getContractSize());
        contractCache.setChargeUnit(SledCommodityConfig.getChargeUnit());

        /*
         * 计算合约系数
         * */
        BigDecimal contractCoefficient = new BigDecimal(new Double(contractCache.getContractSize()).toString());
        contractCoefficient = contractCoefficient.multiply(new BigDecimal(new Double(contractCache.getChargeUnit()).toString()));
        Double baseCurrencyExchangeRate = CNYRateHelper.getInstance().getBaseCurrencyExchangeRate(contractCache.getTradeCurrency());
        BigDecimal contractBaseCurrencyCoefficient = contractCoefficient.multiply(new BigDecimal(baseCurrencyExchangeRate));
        contractCache.setContractCoefficient(contractCoefficient.doubleValue());
        contractCache.setContractBaseCurrencyCoefficient(contractBaseCurrencyCoefficient.doubleValue());
        contractCache.setModifyTimestampMs(System.currentTimeMillis());
        return contractCache;
    }

    /**
     * 获取有效的 SledCommodityConfig
     */
    private static SledCommodityConfig getSledCommodityConfig(SledCommodity sledCommodity) throws ErrorInfo {
        List<SledCommodityConfig> configList = sledCommodity.getSledCommodityConfig();
        if (configList.size() == 0) {
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_COMMODITY_CONFIG_NOT_FOUND.getValue(), "commodity config not found.");
        }

        long now = System.currentTimeMillis() / 1000;
        SledCommodityConfig sledCommodityConfig = null;

        for (SledCommodityConfig config : configList) {
            if (now >= config.getActiveStartTimestamp() && now <= config.getActiveEndTimestamp()) {
                sledCommodityConfig = config;
                break;
            }
        }
        if (sledCommodityConfig == null) {
            throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_COMMODITY_CONFIG_NOT_FOUND.getValue(), "commodity config not found.");
        }
        return sledCommodityConfig;
    }

    @Override
    public void clear() {
        softReferenceMap.clear();
    }
}
