package xueqiao.trade.hosting.arbitrage.thriftapi.helper;

import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageVariable;
import xueqiao.trade.hosting.arbitrage.thriftapi.client.TradeHostingArbitrageStub;

import java.io.File;

public class ArbitrageStubFactory {

    private static File ARBITRAGE_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingArbitrageVariable.serviceKey + ".sock");

    public static TradeHostingArbitrageStub getStub() {
        TradeHostingArbitrageStub stub = new TradeHostingArbitrageStub();
        stub.setSocketFile(ARBITRAGE_STUB_SOCKET_FILE);
        return stub;
    }
}
