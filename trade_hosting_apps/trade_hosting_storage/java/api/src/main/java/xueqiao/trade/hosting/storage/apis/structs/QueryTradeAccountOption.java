package xueqiao.trade.hosting.storage.apis.structs;

import java.util.Set;

import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.TradeAccountState;

public class QueryTradeAccountOption {
	private long tradeAccountId;
	private int tradeBrokerId;
	private int tradeBrokerAccessId;
	private BrokerTechPlatform tradeBrokerTechPlatform;
	
	private String loginUserNamePartical;
	private String loginUserNameWhole;
	private Set<TradeAccountState> inStates;
	private Set<TradeAccountState> notInStates;
	
	public long getTradeAccountId() {
		return tradeAccountId;
	}
	public void setTradeAccountId(long tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}
	
	public int getTradeBrokerId() {
		return tradeBrokerId;
	}
	public void setTradeBrokerId(int brokerId) {
		this.tradeBrokerId = brokerId;
	}
	
	public int getTradeBrokerAccessId() {
		return tradeBrokerAccessId;
	}
	public void setTradeBrokerAccessId(int tradeBrokerAccessId) {
		this.tradeBrokerAccessId = tradeBrokerAccessId;
	}
	
	public String getLoginUserNamePartical() {
		return loginUserNamePartical;
	}
	public void setLoginUserNamePartical(String loginUserNamePartical) {
		this.loginUserNamePartical = loginUserNamePartical;
	}
	
	public String getLoginUserNameWhole() {
		return loginUserNameWhole;
	}
	public void setLoginUserNameWhole(String loginUserNameWhole) {
		this.loginUserNameWhole = loginUserNameWhole;
	}
	
	public Set<TradeAccountState> getInStates() {
		return inStates;
	}
	public void setInStates(Set<TradeAccountState> inStates) {
		this.inStates = inStates;
	}
	
	public Set<TradeAccountState> getNotInStates() {
		return notInStates;
	}
	public void setNotInStates(Set<TradeAccountState> notInStates) {
		this.notInStates = notInStates;
	}

	public BrokerTechPlatform getTradeBrokerTechPlatform() {
		return tradeBrokerTechPlatform;
	}

	public void setTradeBrokerTechPlatform(BrokerTechPlatform tradeBrokerTechPlatform) {
		this.tradeBrokerTechPlatform = tradeBrokerTechPlatform;
	}
}
