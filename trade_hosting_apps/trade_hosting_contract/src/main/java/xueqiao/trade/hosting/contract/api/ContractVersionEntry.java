package xueqiao.trade.hosting.contract.api;

public class ContractVersionEntry {
	
	private int lastestVersion;
	private int fromVersion;
	private int createTimestamp;
	private int switchTimestamp;
	
	public int getLastestVersion() {
		return lastestVersion;
	}
	public void setLastestVersion(int lastestVersion) {
		this.lastestVersion = lastestVersion;
	}
	
	public int getFromVersion() {
		return fromVersion;
	}
	public void setFromVersion(int fromVersion) {
		this.fromVersion = fromVersion;
	}
	
	public int getCreateTimestamp() {
		return createTimestamp;
	}
	public void setCreateTimestamp(int createTimestamp) {
		this.createTimestamp = createTimestamp;
	}
	
	public int getSwitchTimestamp() {
		return switchTimestamp;
	}
	public void setSwitchTimestamp(int switchTimestamp) {
		this.switchTimestamp = switchTimestamp;
	}
	
}
