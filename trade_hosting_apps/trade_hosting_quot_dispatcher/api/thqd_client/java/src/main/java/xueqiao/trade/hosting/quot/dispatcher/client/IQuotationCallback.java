package xueqiao.trade.hosting.quot.dispatcher.client;

import xueqiao.quotation.QuotationItem;

public interface IQuotationCallback {
	public void onReceivedQuotationItem(String platform, String contractSymbol, QuotationItem item);
}
