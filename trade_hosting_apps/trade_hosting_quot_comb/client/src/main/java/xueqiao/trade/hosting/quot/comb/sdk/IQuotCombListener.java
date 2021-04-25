package xueqiao.trade.hosting.quot.comb.sdk;

import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

public interface IQuotCombListener {
    void onReceivedQuotationComb(HostingQuotationComb quotationComb) throws Exception;
}
