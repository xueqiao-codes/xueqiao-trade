package xueqiao.trade.hosting.push.server.core;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class GlobalCallRegister {
	private static GlobalCallRegister sInstance;
	
	public static GlobalCallRegister Global() {
		if (sInstance == null) {
			synchronized(GlobalCallRegister.class) {
				if (sInstance == null) {
					sInstance = new GlobalCallRegister();
				}
			}
		}
		return sInstance;
	}
	
	private Map<String, CallHandler> mRegisterMap = new HashMap<String, CallHandler>();
	
	private GlobalCallRegister() {
	}
	
	public GlobalCallRegister addHandler(CallHandler handler) {
		mRegisterMap.put(handler.getRequestStructType(), handler);
		return this;
	}
	public CallHandler getCallHandler(String requestStructType) {
		return mRegisterMap.get(requestStructType);
	}
}
