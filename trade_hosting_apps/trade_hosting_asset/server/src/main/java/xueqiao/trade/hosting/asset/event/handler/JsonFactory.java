package xueqiao.trade.hosting.asset.event.handler;

import org.apache.thrift.protocol.TJSONProtocol;

public class JsonFactory {
    private static JsonFactory ourInstance = new JsonFactory();

    public static JsonFactory getInstance() {
        return ourInstance;
    }

    private TJSONProtocol.Factory factory;

    private JsonFactory() {
        factory = new TJSONProtocol.Factory();
    }

    public TJSONProtocol.Factory getFactory() {
        return factory;
    }
}
