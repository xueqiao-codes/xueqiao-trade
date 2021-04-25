package xueqiao.trade.hosting.position.fee.core.cache;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import org.apache.thrift.TException;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;

public class SledContractCacheManager extends AbstractCache<Integer, SledContract> {

    private static SledContractCacheManager instance = new SledContractCacheManager();

    public static SledContractCacheManager getInstance() {
        return instance;
    }

    @Override
    protected SledContract loadWhenAbsent(Integer contractId) throws TException {
        return HostingContractApi.queryContract(contractId);
    }
}
