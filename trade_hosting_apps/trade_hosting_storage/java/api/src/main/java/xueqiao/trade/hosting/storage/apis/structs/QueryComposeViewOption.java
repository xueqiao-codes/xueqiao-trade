package xueqiao.trade.hosting.storage.apis.structs;

import xueqiao.trade.hosting.HostingComposeViewStatus;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;

public class QueryComposeViewOption {
	private int subUserId;
	
	private long graphId;
	private String namePartical;
	private HostingComposeViewSubscribeStatus subscribeStatus;
	private HostingComposeViewStatus viewStatus = HostingComposeViewStatus.VIEW_NORMAL;
	
	public int getSubUserId() {
		return subUserId;
	}
	public void setSubUserId(int subUserId) {
		this.subUserId = subUserId;
	}
	
	public long getGraphId() {
		return graphId;
	}
	public void setGraphId(long graphId) {
		this.graphId = graphId;
	}
	
	public String getNamePartical() {
		return namePartical;
	}
	public void setNamePartical(String namePartical) {
		this.namePartical = namePartical;
	}

	public HostingComposeViewSubscribeStatus getSubscribeStatus() {
		return subscribeStatus;
	}
	
	public void setSubscribeStatus(HostingComposeViewSubscribeStatus subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public HostingComposeViewStatus getViewStatus() {
		return viewStatus;
	}

	public void setViewStatus(HostingComposeViewStatus viewStatus) {
		this.viewStatus = viewStatus;
	}
}
