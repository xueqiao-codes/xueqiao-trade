package xueqiao.trade.hosting.position.fee.core.cache;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledExchange;
import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;

public class SledExchangeCacheManager extends AbstractCache<String, SledExchange> {

    private static SledExchangeCacheManager instance = new SledExchangeCacheManager();

    public static SledExchangeCacheManager getInstance() {
        return instance;
    }

    @Override
    protected SledExchange loadWhenAbsent(String exchangeMic) throws TException {
        return HostingContractApi.queryExchange(exchangeMic);
    }
}
