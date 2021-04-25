package xueqiao.trade.hosting.storage.apis;

import java.util.List;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;

import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;

public interface IHostingTradeAccountApi {
	
	public long createTradeAccountId() throws ErrorInfo;
	
	/**
	 * 对接券商接入系统
	 */
	public BrokerAccessEntry getBrokerAccessEntry(long tradeAccountId) throws ErrorInfo;
	
	public void updateBrokerAccessEntry(long tradeAccountId
			, BrokerAccessEntry updateAccessEntryInfo) throws ErrorInfo;
	
	/**
	 *  托管机主机账号系统对接
	 */
	public HostingTradeAccount getTradeAccount(long tradeAccountId) throws ErrorInfo;
	
	public List<HostingTradeAccount> getAllTradeAccounts() throws ErrorInfo;
	
	public PageResult<HostingTradeAccount> queryTradeAccountPage(
			QueryTradeAccountOption queryOption
			, PageOption pageOption) throws ErrorInfo;
	
	public void addTradeAccount(HostingTradeAccount newAccount
			, BrokerAccessEntry initedAccessEntry) throws ErrorInfo;
	
	public void updateTradeAccountInfo(HostingTradeAccount updateAccount, BrokerAccessEntry updateBrokerAccessEntry) throws ErrorInfo;
	
	public void changeTradeAccountStatus(long tradeAccountId, TradeAccountState newState) throws ErrorInfo;
	
	public void deleteTradeAccount(long tradeAccountId) throws ErrorInfo;
	
	/**
	 *  账号接入失败描述
	 */
	public static class InvalidDescription {
		public int invalidErrorCode;
		public int apiRetCode;
		public String invalidReason;
	}
	
	public void setTradeAccountInvalid(long tradeAccountId, InvalidDescription desc) throws ErrorInfo;
	
	/**
	 *  账号接入成功
	 */
	public void setTradeAccountActive(long tradeAccountId) throws ErrorInfo;
	
	
	public void clearAll() throws ErrorInfo;
}
