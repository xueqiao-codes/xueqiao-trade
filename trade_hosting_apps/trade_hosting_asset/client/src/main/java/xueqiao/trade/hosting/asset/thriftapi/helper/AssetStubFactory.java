package xueqiao.trade.hosting.asset.thriftapi.helper;

import xueqiao.trade.hosting.asset.thriftapi.TradeHostingAssetVariable;
import xueqiao.trade.hosting.asset.thriftapi.client.TradeHostingAssetStub;

import java.io.File;

public class AssetStubFactory {

    private static File STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingAssetVariable.serviceKey + ".sock");

    public static TradeHostingAssetStub getStub() {
        TradeHostingAssetStub stub = new TradeHostingAssetStub();
        stub.setSocketFile(STUB_SOCKET_FILE);
        return stub;
    }
}
