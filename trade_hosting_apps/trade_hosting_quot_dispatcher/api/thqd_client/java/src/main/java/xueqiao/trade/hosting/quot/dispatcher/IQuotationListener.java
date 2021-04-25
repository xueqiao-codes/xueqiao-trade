package xueqiao.trade.hosting.quot.dispatcher;

import xueqiao.quotation.QuotationItem;

public interface IQuotationListener {
    public void onReceivedQuotationItem(QuotationItem quotationItem) throws Exception;
}
