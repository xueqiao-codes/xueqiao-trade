package xueqiao.trade.hosting.testing;

import xueqiao.trade.hosting.quot.comb.sdk.QuotCombClient;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

public class QuotCombClientTest implements QuotCombClient.IQuotCombClientCallback {

    public static void runTest() {
        QuotCombClient client = new QuotCombClient("trade_hosting_test", new QuotCombClientTest());
        client.startWork();

        client.addComposeGraphId(4001);
        client.addComposeGraphId(4002);

        System.out.println("addComposeGraphId 4001,4002");

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.removeComposeGraphId(4002);
        System.out.println("removeComposeGraphId 4002");


        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        client.stopWork();
    }

    @Override
    public void onReceiveQuotationComb(HostingQuotationComb quotationComb) {
        System.out.println("onReceiveQuotationComb " + quotationComb.getComposeGraphId());
    }
}
