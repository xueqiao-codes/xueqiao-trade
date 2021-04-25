package xueqiao.trade.hosting.risk.helper;

import xueqiao.trade.hosting.risk.dealing.thriftapi.TradeHostingRiskDealingVariable;
import xueqiao.trade.hosting.risk.dealing.thriftapi.client.TradeHostingRiskDealingStub;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManagerVariable;
import xueqiao.trade.hosting.risk.manager.thriftapi.client.TradeHostingRiskManagerStub;

import java.io.File;

public class RiskStubFactory {
    private static File MANAGER_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingRiskManagerVariable.serviceKey + ".sock");
    private static File DEALING_STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingRiskDealingVariable.serviceKey + ".sock");

    public static TradeHostingRiskManagerStub getManagerStub() {
        TradeHostingRiskManagerStub stub = new TradeHostingRiskManagerStub();
        stub.setSocketFile(MANAGER_STUB_SOCKET_FILE);
        return stub;
    }

    public static TradeHostingRiskDealingStub getDealingStub() {
        TradeHostingRiskDealingStub stub = new TradeHostingRiskDealingStub();
        stub.setSocketFile(DEALING_STUB_SOCKET_FILE);
        return stub;
    }
}
