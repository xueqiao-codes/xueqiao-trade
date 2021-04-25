package xueqiao.trade.hosting.quot.dispatcher.client;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.transport.TMemoryInputTransport;
import org.soldier.base.logger.AppLog;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.quot.dispatcher.client.swig.ITHQDCallback;
import xueqiao.trade.hosting.quot.dispatcher.client.swig.THQDSwig;

public class THQDClient extends ITHQDCallback {
	private static THQDClient sInstance;
	public static THQDClient Global() {
		return sInstance;
	}
	
	public static void init(final String consumerKey) {
		if (StringUtils.isBlank(consumerKey)) {
			throw new IllegalArgumentException("consumerKey should not be blank");
		}
		
		if (sInstance == null) {
			synchronized(THQDClient.class) {
				if (sInstance == null) {
					THQDClientLibraryLoader.init();
					sInstance = new THQDClient(consumerKey);
				}
			}
		}
	}
	
	public static void destroy() {
		if (sInstance == null) {
			return ;
		}
		
		THQDSwig.destroy();
		sInstance = null;
	}
	
	private IQuotationCallback mCallback;
	
	private THQDClient(String consumerKey) {
		THQDSwig.init(consumerKey, this);
		Runtime.getRuntime().addShutdownHook(new Thread() {  
            public void run() {  
            	THQDSwig.destroy();
            }  
        });  
	}
	
	public void setQuotationCallback(IQuotationCallback callback) {
		this.mCallback = callback;
	}
	
	public IQuotationCallback getCallback() {
		return mCallback;
	}
	
	public void addTopic(final String platform, final String contractSymbol) {
		if (StringUtils.isEmpty(platform)) {
			throw new IllegalArgumentException("platform should not be empty");
		}
		if (StringUtils.isEmpty(contractSymbol)) {
			throw new IllegalArgumentException("contractSymbol should not be empty");
		}
		
		THQDSwig.addTopic(platform, contractSymbol);
	}
	
	public void removeTopic(final String platform, final String contractSymbol) {
		if (StringUtils.isEmpty(platform)) {
			throw new IllegalArgumentException("platform should not be empty");
		}
		if (StringUtils.isEmpty(contractSymbol)) {
			throw new IllegalArgumentException("contractSymbol should not be empty");
		}
		
		THQDSwig.removeTopic(platform, contractSymbol);
	}
	
	public void onReceivedItemData(String platform, String contractSymbol, byte[] data) {
		try {
			TMemoryInputTransport transport = new TMemoryInputTransport(data);
			TCompactProtocol protocol = new TCompactProtocol(transport);
			QuotationItem item = new QuotationItem();
			try {
				item.read(protocol);
			} catch (TException e) {
				e.printStackTrace();
			}
			if (mCallback != null) {
				mCallback.onReceivedQuotationItem(platform, contractSymbol, item);
			}
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
}
