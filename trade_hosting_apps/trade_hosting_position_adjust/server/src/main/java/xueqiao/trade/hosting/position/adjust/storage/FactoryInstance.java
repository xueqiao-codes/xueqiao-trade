package xueqiao.trade.hosting.position.adjust.storage;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;

public class FactoryInstance {
    private static FactoryInstance ourInstance = new FactoryInstance();

    public static FactoryInstance getInstance() {
        return ourInstance;
    }

    private TJSONProtocol.Factory factory;

    private TCompactProtocol.Factory compactFactory;

    private FactoryInstance() {
        factory = new TJSONProtocol.Factory();
        compactFactory = new TCompactProtocol.Factory();
    }

    public TJSONProtocol.Factory getJsonFactory() {
        return factory;
    }

    public TCompactProtocol.Factory getCompactFactory() {
        return compactFactory;
    }
}
