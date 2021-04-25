package xueqiao.trade.hosting.position.statis.core.calculator.position.dynamic;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.thriftapi.HostingFund;
import xueqiao.trade.hosting.asset.thriftapi.HostingFundPage;
import xueqiao.trade.hosting.asset.thriftapi.ReqHostingFundOption;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;
import xueqiao.trade.hosting.asset.thriftapi.helper.AssetStubFactory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class DynamicBenefitManager {

    private static final long DYNAMIC_BENEFIT_TIMEOUT = 5000;

    private TradeHostingAssetStub tradeHostingAssetStub;

    private static DynamicBenefitManager ourInstance = new DynamicBenefitManager();
    private Map<Long, SoftReference<DynamicBenefit>> softReferenceMap = new HashMap<>();

    public static DynamicBenefitManager getInstance() {
        return ourInstance;
    }

    private DynamicBenefitManager() {
        tradeHostingAssetStub = new TradeHostingAssetStub();
    }

    /**
     * 获取基币(CNY)动态总权益
     */
    public DynamicBenefit fetchBaseCurrencyDynamicBenefit(long subAccountId) throws ErrorInfo {
        long currentTimeStamps = System.currentTimeMillis();
        SoftReference<DynamicBenefit> softReference = softReferenceMap.get(subAccountId);
        DynamicBenefit dynamicBenefit;
        if (softReference != null) {
            dynamicBenefit = softReference.get();
            if (dynamicBenefit != null) {
                if (currentTimeStamps - dynamicBenefit.getUpdateTimeStamps() < DYNAMIC_BENEFIT_TIMEOUT) {
                    DynamicBenefit dynamicBenefitCopy = new DynamicBenefit();
                    dynamicBenefitCopy.setBaseCurrency(dynamicBenefit.getBaseCurrency());
                    dynamicBenefitCopy.setBaseCurrencyDynamicBenefit(dynamicBenefit.getBaseCurrencyDynamicBenefit());
                    dynamicBenefitCopy.setUpdateTimeStamps(dynamicBenefit.getUpdateTimeStamps());
                    return dynamicBenefitCopy;
                }
            }
        }
        dynamicBenefit = fetchBaseCurrencyDynamicBenefitFromAsset(subAccountId);
        if (dynamicBenefit != null && StringUtils.isNotBlank(dynamicBenefit.getBaseCurrency())) {
            softReference = new SoftReference(dynamicBenefit);
            softReferenceMap.put(subAccountId, softReference);
            return dynamicBenefit;
        } else {
            AppLog.e("DynamicBenefitManager # fetchBaseCurrencyDynamicBenefit ---- get from asset fail, subAccountId : " + subAccountId);
        }
        return null;
    }

    /**
     * 获取基币(CNY)动态总权益
     */
    private DynamicBenefit fetchBaseCurrencyDynamicBenefitFromAsset(long subAccountId) throws ErrorInfo {
        ReqHostingFundOption option = new ReqHostingFundOption();
        option.addToSubAccountIds(subAccountId);
        option.setBaseCurrency(true);

        HostingFundPage hostingFundPage = null;
        DynamicBenefit dynamicBenefit = new DynamicBenefit();
        try {
            hostingFundPage = AssetStubFactory.getStub().getHostingSubAccountFund(option);
        } catch (TException exception) {
            AppLog.e("DynamicBenefitManager # fetchBaseCurrencyDynamicBenefitFromAsset ---- empty result for getHostingSubAccountFund, fail to get dynamicBenefit, subAccountId : " + subAccountId, exception);
        }

        if (hostingFundPage != null && hostingFundPage.getPageSize() > 0) {
            HostingFund hostingFund = hostingFundPage.getPage().get(0);
            dynamicBenefit.setBaseCurrency(hostingFund.getCurrency());
            dynamicBenefit.setBaseCurrencyDynamicBenefit(hostingFund.getDynamicBenefit());
            dynamicBenefit.setUpdateTimeStamps(System.currentTimeMillis());
        } else {
            AppLog.e("DynamicBenefitManager # fetchBaseCurrencyDynamicBenefitFromAsset ---- hostingFundPage is empty, fail to get dynamicBenefit, subAccountId : " + subAccountId);
        }
        return dynamicBenefit;
    }
}
