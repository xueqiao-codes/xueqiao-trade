package xueqiao.trade.hosting.testing;

import xueqiao.trade.hosting.quot.comb.sdk.IQuotCombListener;
import xueqiao.trade.hosting.quot.comb.sdk.QuotCombDispatcher;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

public class QuotCombDispatcherTest implements IQuotCombListener {

    public static void runTest() {
        QuotCombDispatcher.init("trade_hosting_testing");

        IQuotCombListener listener = new QuotCombDispatcherTest();

        QuotCombDispatcher.Global().registerListener(4001, listener);
        QuotCombDispatcher.Global().registerListener(4002, listener);

        System.out.println("registerListener 4001,4002 listener=" + listener);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QuotCombDispatcher.Global().unRegisterListener(4002, listener);
        System.out.println("removeComposeGraphId 4002, listener=" + listener);

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReceivedQuotationComb(HostingQuotationComb quotationComb) throws Exception {
        System.out.println("onReceivedQuotationComb " + quotationComb.getComposeGraphId());
    }
}
