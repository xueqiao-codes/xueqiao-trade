package xueqiao.trade.hosting.arbitrage.core.composelimit.cost;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.logger.AppLog;
import xueqiao.trade.hosting.arbitrage.core.XQOrderHelper;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ImpactCostCalculatorBase implements IQuotationListener  {

    private AtomicLong mRefCount = new AtomicLong(0);

    private SledContractDetails mContractDetails;
    private volatile Double mImpactCost;

    protected ImpactCostCalculatorBase(SledContractDetails contractDetails) {
        this.mContractDetails = contractDetails;

        try {
            QuotationDispatcher.Global().registerListener(
                    XQOrderHelper.getQuotationPlatform(contractDetails)
                    , XQOrderHelper.getQuotationContractSymbol(contractDetails)
                    , this);
        } catch (UnsupportedEncodingException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    public long getRefCount() {
        return mRefCount.get();
    }

    public long incRefCount() {
        return mRefCount.incrementAndGet();
    }

    public long decRefCount() {
        return mRefCount.decrementAndGet();
    }

    public void destroy() {
        try {
            QuotationDispatcher.Global().unRegisterListener(
                    XQOrderHelper.getQuotationPlatform(getContractDetails())
                    , XQOrderHelper.getQuotationContractSymbol(getContractDetails())
                    , this
            );
        } catch (UnsupportedEncodingException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    public SledContractDetails getContractDetails() {
        return mContractDetails;
    }

    public Double getImpactCost() {
        return mImpactCost;
    }

    protected void updateImpactCost(double impactCost) {
//        if (AppLog.debugEnabled()) {
//            StringBuilder logBuilder = new StringBuilder(128);
//            logBuilder.append("impactCost for sledContractId=")
//                    .append(mContractDetails.getSledContract().getSledContractId())
//                    .append(", exchangeMic=").append(mContractDetails.getSledCommodity().getExchangeMic())
//                    .append(", commodityCode=").append(mContractDetails.getSledCommodity().getSledCommodityCode())
//                    .append(", commodityType=").append(mContractDetails.getSledCommodity().getSledCommodityType())
//                    .append(", contractCode=").append(mContractDetails.getSledContract().getSledContractCode())
//                    .append(", impactCost=").append(impactCost);
//            AppLog.d(logBuilder.toString());
//        }

        mImpactCost = new Double(impactCost);
    }
}
