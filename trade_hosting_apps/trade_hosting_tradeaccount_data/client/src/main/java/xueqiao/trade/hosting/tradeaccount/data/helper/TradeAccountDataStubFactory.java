package xueqiao.trade.hosting.tradeaccount.data.helper;

import xueqiao.trade.hosting.tradeaccount.data.TradeHostingTradeAccountDataVariable;
import xueqiao.trade.hosting.tradeaccount.data.client.TradeHostingTradeAccountDataStub;

import java.io.File;

public class TradeAccountDataStubFactory {

    private static File TRADEACCOUNTDATA_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingTradeAccountDataVariable.serviceKey + ".sock");

    public static TradeHostingTradeAccountDataStub getStub() {
        TradeHostingTradeAccountDataStub stub = new TradeHostingTradeAccountDataStub();
        stub.setSocketFile(TRADEACCOUNTDATA_STUB_SOCKET_FILE);
        return stub;
    }
}
