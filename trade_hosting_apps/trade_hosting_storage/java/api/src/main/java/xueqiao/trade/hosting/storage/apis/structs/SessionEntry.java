package xueqiao.trade.hosting.storage.apis.structs;

public class SessionEntry {
	private int subUserId = -1;
	private String token;
	private String loginIP;
	private int lastUpdateTimestamp;
	
	public int getSubUserId() {
		return subUserId;
	}
	public void setSubUserId(int subUserId) {
		this.subUserId = subUserId;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getLoginIP() {
		return loginIP;
	}
	public void setLoginIP(String loginIP) {
		this.loginIP = loginIP;
	}
	
	public int getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}
	public void setLastUpdateTimestamp(int lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
	
	
}
