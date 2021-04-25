package xueqiao.trade.hosting.storage.apis.structs;

import java.util.Set;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingUserOrderType;

public class QueryUserOption {
	
	private int subUserId;
	private String loginNameWhole;
	private String loginNamePartical;
	private String nickNameWhole;
	private String nickNamePartical;
	
	private HostingUserOrderType order;
	private Set<Integer> batchSubUserIds;
	
	public int getSubUserId() {
		return subUserId;
	}
	public QueryUserOption setSubUserId(int subUserId) {
		this.subUserId = subUserId;
		return this;
	}
	
	public String getLoginNameWhole() {
		return loginNameWhole;
	}
	public QueryUserOption setLoginNameWhole(String loginNameWhole) {
		Preconditions.checkNotNull(loginNameWhole);
		this.loginNameWhole = loginNameWhole.trim();
		return this;
	}
	
	public String getLoginNamePartical() {
		return loginNamePartical;
	}
	public QueryUserOption setLoginNamePartical(String loginNamePartical) {
		Preconditions.checkNotNull(loginNamePartical);
		this.loginNamePartical = loginNamePartical.trim();
		return this;
	}
	
	public String getNickNameWhole() {
		return nickNameWhole;
	}
	public QueryUserOption setNickNameWhole(String nickNameWhole) {
		Preconditions.checkNotNull(nickNameWhole);
		this.nickNameWhole = nickNameWhole;
		return this;
	}
	
	public String getNickNamePartical() {
		return nickNamePartical;
	}
	public QueryUserOption setNickNamePartical(String nickNamePartical) {
		Preconditions.checkNotNull(nickNamePartical);
		this.nickNamePartical = nickNamePartical;
		return this;
	}
	
	public HostingUserOrderType getOrder() {
		return order;
	}
	public void setOrder(HostingUserOrderType order) {
		this.order = order;
	}
	
    public Set<Integer> getBatchSubUserIds() {
        return batchSubUserIds;
    }
    public void setBatchSubUserIds(Set<Integer> batchSubUserIds) {
        this.batchSubUserIds = batchSubUserIds;
    }
	
}
