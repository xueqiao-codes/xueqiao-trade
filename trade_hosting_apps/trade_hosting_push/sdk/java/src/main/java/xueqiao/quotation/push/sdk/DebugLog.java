package xueqiao.quotation.push.sdk;

public class DebugLog {
	public static volatile boolean DEBUG_ENABLED = false;
	
	static {
		if (System.getProperty("quotation.push.debug", "").equals("enabled")) {
			DEBUG_ENABLED = true;
		}
	}
}
