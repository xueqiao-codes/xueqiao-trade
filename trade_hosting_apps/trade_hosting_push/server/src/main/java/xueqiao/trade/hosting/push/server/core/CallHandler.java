package xueqiao.trade.hosting.push.server.core;

import org.apache.thrift.TBase;

@SuppressWarnings("rawtypes")
public abstract class CallHandler<Req extends TBase, Resp extends TBase> {
	private Class<Req> mRequestStructClazz;
	private Class<Resp> mResponseStructClazz;
	
	public CallHandler(final Class<Req> requestStructClazz
			, final Class<Resp> responseStructClazz) {
		if (requestStructClazz == null) {
			throw new IllegalArgumentException("requestStructClazz should not be null");
		}
		
		this.mRequestStructClazz = requestStructClazz;
		this.mResponseStructClazz = responseStructClazz;
	}
	
	public String getRequestStructType() {
		return mRequestStructClazz.getSimpleName();
	}
	
	public Class<Req> getRequestStructClazz() {
		return mRequestStructClazz;
	}
	
	public String getResponseStructType() {
		if (mResponseStructClazz == null) {
			return "";
		}
		return mRequestStructClazz.getSimpleName();
	}
	
	public abstract void invoke(InvokeContext context
			, Req req
			, FrameProtocolHandler.FrameResponse frameResp) throws Exception;
}
