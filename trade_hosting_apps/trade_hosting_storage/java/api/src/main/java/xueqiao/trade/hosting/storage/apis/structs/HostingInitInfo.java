package xueqiao.trade.hosting.storage.apis.structs;

import xueqiao.trade.hosting.HostingRunningMode;

public class HostingInitInfo {
	private long machineId;
	private String adminLoginName;
	private String adminLoginPasswd;
	private String hostingAES16Key;
	private HostingRunningMode runningMode;
	
	public long getMachineId() {
		return machineId;
	}
	public HostingInitInfo setMachineId(long machineId) {
		this.machineId = machineId;
		return this;
	}
	
	public String getAdminLoginName() {
		return adminLoginName;
	}
	public HostingInitInfo setAdminLoginName(String adminLoginName) {
		this.adminLoginName = adminLoginName;
		return this;
	}
	
	public String getAdminLoginPasswd() {
		return adminLoginPasswd;
	}
	
	public HostingInitInfo setAdminLoginPasswd(String adminLoginPasswd) {
		this.adminLoginPasswd = adminLoginPasswd;
		return this;
	}
	
	public String getHostingAES16Key() {
		return hostingAES16Key;
	}
	
	public HostingInitInfo setHostingAES16Key(String hostingAES16Key) {
		this.hostingAES16Key = hostingAES16Key;
		return this;
	}
	
	public HostingRunningMode getRunningMode() {
		return runningMode;
	}
	public void setRunningMode(HostingRunningMode runningMode) {
		this.runningMode = runningMode;
	}
	
}
