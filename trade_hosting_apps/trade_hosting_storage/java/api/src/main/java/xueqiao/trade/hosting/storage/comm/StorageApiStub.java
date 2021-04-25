package xueqiao.trade.hosting.storage.comm;

import java.io.File;

import xueqiao.trade.hosting.storage.thriftapi.TradeHostingStorageVariable;
import xueqiao.trade.hosting.storage.thriftapi.client.TradeHostingStorageStub;

public class StorageApiStub extends TradeHostingStorageStub {
    private static File STUB_SOCKET_FILE = new File(
            "/data/run/service_" + TradeHostingStorageVariable.serviceKey + ".sock");
    
    public StorageApiStub() {
        this.setSocketFile(STUB_SOCKET_FILE);
    }
}
