package xueqiao.trade.hosting.storage.apis;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;

public interface IBrokerAccessEntryProvider {
	public BrokerAccessEntry getBrokerAccessEntry(int tradeBrokerId
			, int tradeBrokerAccessId) throws ErrorInfo;
}
