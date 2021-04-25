package xueqiao.trade.hosting.dealing.thriftapi.helper;

import xueqiao.trade.hosting.dealing.thriftapi.TradeHostingDealingVariable;
import xueqiao.trade.hosting.dealing.thriftapi.client.TradeHostingDealingStub;

import java.io.File;

public class DealingStubFactory {
    private static File DEALING_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingDealingVariable.serviceKey + ".sock");

    public static TradeHostingDealingStub getStub() {
        TradeHostingDealingStub stub = new TradeHostingDealingStub();
        stub.setSocketFile(DEALING_STUB_SOCKET_FILE);
        return stub;
    }
}
