package xueqiao.trade.hosting.position.fee.core.cache;

import com.antiy.error_code.ErrorCodeInner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.longsheng.xueqiao.contract.standard.thriftapi.CalculateMode;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.bean.XQDefaultPositionRate;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.core.util.EsAPICombineModeCalculator;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class XQDefaultPositionRateCacheManager {

    private static XQDefaultPositionRateCacheManager instance = new XQDefaultPositionRateCacheManager();

    public static XQDefaultPositionRateCacheManager getInstance() {
        return instance;
    }

    private LoadingCache<Long, XQDefaultPositionRate> loadingCache;

    public XQDefaultPositionRateCacheManager() {
        initLoadingCache();
    }

    private void initLoadingCache() {
        /*
         * 指定如何数据不存在时的获取方法
         * */
        CacheLoader<Long, XQDefaultPositionRate> cacheLoader = new CacheLoader<Long, XQDefaultPositionRate>() {
            @Override
            public XQDefaultPositionRate load(Long key) throws Exception {
                return loadXQDefaultPositionRate(key);
            }
        };
        loadingCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS) // 1天后过期
                .maximumSize(10) // 缓存数量为10
                .build(cacheLoader);
    }

    /**
     * 获取数据，如果数据不在缓存中，则通过loader去获取，缓存并返回
     */
    public XQDefaultPositionRate get(long commodityId) throws ErrorInfo {
        try {
            return loadingCache.get(commodityId);
        } catch (ExecutionException e) {
            AppLog.e("get fail ---- commodityId : " + commodityId, e);
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "get commodity config from xq fail: " + e.getMessage());
        }
    }

    private XQDefaultPositionRate loadXQDefaultPositionRate(long commodityId) throws TException {
        SledCommodity commodity = HostingContractApi.queryCommodity((int) commodityId);
        if (commodity == null) {
            AppLog.e("HostingContractApi.queryCommodity fail, commodity is null, commodityId: " + commodityId);
            return null;
//            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "HostingContractApi.queryCommodity fail, commodityId: " + commodityId);
        }
//        if (commodity.getCommodityState() != CommodityState.TRADEABLE) {
//            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "commodity(id:" + commodityId + ") is not tradeable");
//        }
        if (StringUtils.isBlank(commodity.getTradeCurrency())) {
            AppLog.e("******************** no commodity tradeCurrency with commodity(id:" + commodityId + ")");
            return null;
        }
        if (commodity.getSledCommodityConfigSize() < 1) {
            AppLog.e("no commodity config with commodity(id:" + commodityId + ")");
            return null;
//            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "no commodity config with commodity(id:" + commodityId + ")");
        }
        long now = System.currentTimeMillis() / 1000;
        SledCommodityConfig sledCommodityConfig = null;

        for (SledCommodityConfig config : commodity.getSledCommodityConfig()) {
            if (now >= config.getActiveStartTimestamp() && now <= config.getActiveEndTimestamp()) {
                sledCommodityConfig = config;
                break;
            }
        }
        if (sledCommodityConfig == null) {
            AppLog.e("no valid commodity config found with commodity(id:" + commodityId + ")");
            return null;
//            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "no valid commodity config found with commodity(id:" + commodityId + ")");
        }

        CommissionInfo commissionInfo = getCommissionInfoFromCommodityConfig(sledCommodityConfig, commodity.getTradeCurrency());
        MarginInfo marginInfo = getMarginInfoFromCommodityConfig(sledCommodityConfig, commodity.getTradeCurrency());

        XQDefaultPositionRate xqDefaultPositionRate = new XQDefaultPositionRate();
        xqDefaultPositionRate.setCommodityId(commodityId);
        xqDefaultPositionRate.setCommissionInfo(commissionInfo);
        xqDefaultPositionRate.setMarginInfo(marginInfo);

        return xqDefaultPositionRate;
    }

    private CommissionInfo getCommissionInfoFromCommodityConfig(SledCommodityConfig config, String currency) throws ErrorInfo {
        CommissionInfo commissionInfo = new CommissionInfo();
        if (config.getCommissionCalculateMode() == CalculateMode.COMBINE) {
            EsAPICombineModeCalculator openCloseFeeCalculator = new EsAPICombineModeCalculator(config.getOpenCloseFee());
            commissionInfo.setOpenRatioByMoney(openCloseFeeCalculator.getPercentage());
            commissionInfo.setOpenRatioByVolume(openCloseFeeCalculator.getQuota());
            commissionInfo.setCloseRatioByMoney(openCloseFeeCalculator.getPercentage());
            commissionInfo.setCloseRatioByVolume(openCloseFeeCalculator.getQuota());
            commissionInfo.setCloseTodayRatioByMoney(openCloseFeeCalculator.getPercentage());
            commissionInfo.setCloseTodayRatioByVolume(openCloseFeeCalculator.getQuota());
        } else if (config.getCommissionCalculateMode() == CalculateMode.PERCENTAGE) {
            commissionInfo.setOpenRatioByMoney(config.getOpenCloseFee());
            commissionInfo.setOpenRatioByVolume(0);
            commissionInfo.setCloseRatioByMoney(config.getOpenCloseFee());
            commissionInfo.setCloseRatioByVolume(0);
            commissionInfo.setCloseTodayRatioByMoney(config.getOpenCloseFee());
            commissionInfo.setCloseTodayRatioByVolume(0);
        } else if (config.getCommissionCalculateMode() == CalculateMode.QUOTA) {
            commissionInfo.setOpenRatioByMoney(0);
            commissionInfo.setOpenRatioByVolume(config.getOpenCloseFee());
            commissionInfo.setCloseRatioByMoney(0);
            commissionInfo.setCloseRatioByVolume(config.getOpenCloseFee());
            commissionInfo.setCloseTodayRatioByMoney(0);
            commissionInfo.setCloseTodayRatioByVolume(config.getOpenCloseFee());
        } else {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "invalid calculate mode for commission : " + config.getCommissionCalculateMode());
        }
        commissionInfo.setCurrency(currency);
        commissionInfo.setCurrencyGroup("");
        return commissionInfo;
    }

    private MarginInfo getMarginInfoFromCommodityConfig(SledCommodityConfig config, String currency) throws ErrorInfo {
        MarginInfo marginInfo = new MarginInfo();
        if (config.getMarginCalculateMode() == CalculateMode.PERCENTAGE) {
            marginInfo.setLongMarginRatioByMoney(config.getInitialMargin());
            marginInfo.setLongMarginRatioByVolume(0);
            marginInfo.setShortMarginRatioByMoney(config.getSellInitialMargin());
            marginInfo.setShortMarginRatioByVolume(0);
            marginInfo.setCurrency(currency);
            marginInfo.setCurrencyGroup("");
        } else if (config.getMarginCalculateMode() == CalculateMode.QUOTA) {
            marginInfo = new MarginInfo();
            marginInfo.setLongMarginRatioByMoney(0);
            marginInfo.setLongMarginRatioByVolume(config.getInitialMargin());
            marginInfo.setShortMarginRatioByMoney(0);
            marginInfo.setShortMarginRatioByVolume(config.getSellInitialMargin());
            marginInfo.setCurrency(currency);
            marginInfo.setCurrencyGroup("");
        } else {
            /*
             * 易盛的接口说明上，只会有比例 和 定额 模式，如果出现其他模式，则警报
             * */
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "invalid calculate mode for margin : " + config.getCommissionCalculateMode());
        }
        return marginInfo;
    }
}
