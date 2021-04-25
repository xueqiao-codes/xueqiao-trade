package xueqiao.trade.hosting.storage.tradeaccount;

import java.util.List;
import java.util.concurrent.Callable;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;

import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.storage.apis.IBrokerAccessEntryProvider;
import xueqiao.trade.hosting.storage.comm.StorageApiStub;

public class BrokerAccessEntryProviderImpl implements IBrokerAccessEntryProvider {

	@Override
	public BrokerAccessEntry getBrokerAccessEntry(int tradeBrokerId, int tradeBrokerAccessId) throws ErrorInfo {
	    return ErrorInfoCallHelper.call(new Callable<BrokerAccessEntry>() {
            @Override
            public BrokerAccessEntry call() throws Exception {
	            List<BrokerAccessEntry> entries 
	                = new StorageApiStub().getBrokerAccessEntryFromCloud(tradeBrokerId, tradeBrokerAccessId);
	            if (entries == null || entries.isEmpty()) {
	                return null;
	            }
	            return entries.get(0);
            }
        });
	}
}
