package xueqiao.trade.hosting.testing;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

public class QuotationDispatcherTest implements IQuotationListener {
    
    
    @Override
    public void onReceivedQuotationItem(QuotationItem quotationItem) throws Exception {
        System.out.println("onReceivedQuotationItem " + quotationItem);
    }
    
    
    public static void startTest() throws InterruptedException {
        QuotationDispatcher.init("trade_hosting_testing");
        
        IQuotationListener listener = new QuotationDispatcherTest();
        
        QuotationDispatcher.Global().registerListener("SXQ", "XCEC%7C1%7CHG%7C1806", listener);
        Thread.sleep(10000);
        QuotationDispatcher.Global().unRegisterListener("SXQ", "XCEC%7C1%7CHG%7C1806", listener);
        Thread.sleep(10000);
        
    }

}
