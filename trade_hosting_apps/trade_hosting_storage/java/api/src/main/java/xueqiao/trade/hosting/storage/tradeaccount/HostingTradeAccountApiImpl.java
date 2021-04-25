package xueqiao.trade.hosting.storage.tradeaccount;

import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;

import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountAccessState;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.TradeAccountEvent;
import xueqiao.trade.hosting.events.TradeAccountEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;
import xueqiao.trade.hosting.storage.comm.HostingDB;
import xueqiao.trade.hosting.storage.comm.StorageApiStub;

public class HostingTradeAccountApiImpl implements IHostingTradeAccountApi{
	
	@Override
	public HostingTradeAccount getTradeAccount(long tradeAccountId) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(tradeAccountId > 0);
		
		return new DBQueryHelper<HostingTradeAccount>(HostingDB.Global()) {
			@Override
			protected HostingTradeAccount onQuery(Connection conn) throws Exception {
				return new HostingTradeAccountTable(conn).getTradeAccount(tradeAccountId, false);
			}
		}.query();
	}
	
	@Override
	public PageResult<HostingTradeAccount> queryTradeAccountPage(
			QueryTradeAccountOption queryOption
			, PageOption pageOption) throws ErrorInfo {
	    ParameterChecker.checkInnerNotNull(pageOption);
		pageOption.checkValid();
		
		return new DBQueryHelper<PageResult<HostingTradeAccount>>(HostingDB.Global()) {
			@Override
			protected PageResult<HostingTradeAccount> onQuery(Connection conn) throws Exception {
				return new HostingTradeAccountTable(conn).queryTradeAccountPage(queryOption, pageOption);
			}
		}.query();
	}

	private BrokerTechPlatform mappingBrokerPlatform(BrokerPlatform platform) throws ErrorInfo {
		if (platform == BrokerPlatform.CTP) {
			return BrokerTechPlatform.TECH_CTP;
		} else if (platform == BrokerPlatform.ESUNNY) {
			return BrokerTechPlatform.TECH_ESUNNY_9;
		} else if (platform == BrokerPlatform.ESUNNY_3) {
			return BrokerTechPlatform.TECH_ESUNNY_3;
		} else {
			throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_BROKER_TECH_PLATFORM_NOT_SUPPORTED.getValue()
					, "broker technical platform is not supported now");
		}
	}

	@Override
	public void addTradeAccount(HostingTradeAccount newAccount
			, BrokerAccessEntry initedAccessEntry) throws ErrorInfo {
	    ParameterChecker.checkInnerNotNull(newAccount);
	    ParameterChecker.checkInnerArgument(newAccount.getTradeAccountId() > 0);
	    ParameterChecker.checkInnerArgument(newAccount.getTradeBrokerAccessId() > 0);
	    ParameterChecker.checkInnerArgument(newAccount.getTradeBrokerId() > 0);
	    ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(newAccount.getLoginUserName()));
	    ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(newAccount.getLoginPassword()));
	    ParameterChecker.checkInnerNotNull(initedAccessEntry);
	    ParameterChecker.checkInnerNotNull(initedAccessEntry.getPlatform());
		
		new DBStepHelperWithMsg<Void>(HostingDB.Global()) {


			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingTradeAccountTable tradeAccountTable = new HostingTradeAccountTable(getConnection());
				
				QueryTradeAccountOption queryOption = new QueryTradeAccountOption();
				queryOption.setTradeBrokerId(newAccount.getTradeBrokerId());
				queryOption.setTradeBrokerTechPlatform(mappingBrokerPlatform(initedAccessEntry.getPlatform()));
				queryOption.setLoginUserNameWhole(newAccount.getLoginUserName().trim());
				
				PageResult<HostingTradeAccount> accountsPage
					= tradeAccountTable.queryTradeAccountPage(queryOption, new PageOption().setPageSize(1));
				if (accountsPage.getPageList() != null && !accountsPage.getPageList().isEmpty()) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_DUPLICATE_TRADE_ACCOUNT.getValue()
								, "trade account is existed");
				}
			}

			@Override
			public void onUpdate() throws Exception {
				HostingTradeAccount operateTradeAccount = new HostingTradeAccount();
				operateTradeAccount.setTradeAccountId(newAccount.getTradeAccountId());
				operateTradeAccount.setTradeBrokerAccessId(newAccount.getTradeBrokerAccessId());
				operateTradeAccount.setTradeBrokerId(newAccount.getTradeBrokerId());
				operateTradeAccount.setBrokerTechPlatform(mappingBrokerPlatform(initedAccessEntry.getPlatform()));
				operateTradeAccount.setLoginUserName(newAccount.getLoginUserName().trim());
				operateTradeAccount.setLoginPassword(newAccount.getLoginPassword().trim());
				operateTradeAccount.setAccountState(TradeAccountState.ACCOUNT_NORMAL);
				operateTradeAccount.setAccountProperties(newAccount.getAccountProperties());
				operateTradeAccount.setAccountAccessState(TradeAccountAccessState.ACCOUNT_INVALID);
				operateTradeAccount.setInvalidErrorCode(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_INVALID_OTHER.getValue());
				operateTradeAccount.setInvalidReason("账号刚刚初始化，还未接入");
				operateTradeAccount.setTradeAccountRemark(newAccount.getTradeAccountRemark());

				new HostingTradeAccountTable(getConnection()).addTradeAccount(operateTradeAccount, initedAccessEntry);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				TradeAccountEvent event = new TradeAccountEvent();
				event.setType(TradeAccountEventType.TRADE_ACCOUNT_ADDED);
				event.setTradeAccountId(newAccount.getTradeAccountId());
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
			
		}.execute();
	}

	@Override
	public void updateTradeAccountInfo(HostingTradeAccount updateAccount, BrokerAccessEntry updateAccessEntry) throws ErrorInfo {
	    ParameterChecker.checkInnerNotNull(updateAccount);
	    ParameterChecker.checkInnerArgument(updateAccount.getTradeAccountId() > 0);
	    if (updateAccount.isSetTradeBrokerAccessId()) {
			ParameterChecker.checkInnerNotNull(updateAccessEntry);
		}

		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {
		    private boolean needNotify = false;
		    private HostingTradeAccount dbUpdateAccount;
		    
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			    dbUpdateAccount	= new HostingTradeAccountTable(getConnection()).getTradeAccount(updateAccount.getTradeAccountId(), true);
				if (dbUpdateAccount == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
				if (updateAccount.isSetLoginUserName()) {
					if (!dbUpdateAccount.getLoginUserName().equals(updateAccount.getLoginUserName().trim())) {
						if (dbUpdateAccount.hadBeenActived) {
							throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_CANNOT_MODIFY_USERNAME.getValue()
								, "hadBeenActived Account should not modify username");
						}
					}
				}
				if (updateAccount.isSetTradeBrokerAccessId()) {
					if (updateAccessEntry.getBrokerId() != dbUpdateAccount.getTradeBrokerId()
							|| dbUpdateAccount.getBrokerTechPlatform() != mappingBrokerPlatform(updateAccessEntry.getPlatform())) {
						throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
								, "updateAccount's tradeBrokerAccessId is not match for broker and platform");
					}
				}
			}

			@Override
			public void onUpdate() throws Exception {
				HostingTradeAccount operationAccount = new HostingTradeAccount();
				
				operationAccount.setTradeAccountId(updateAccount.getTradeAccountId());
				if (updateAccount.isSetLoginUserName() 
				        && !updateAccount.getLoginUserName().equals(dbUpdateAccount.getLoginUserName())) {
					operationAccount.setLoginUserName(updateAccount.getLoginUserName().trim());
					needNotify = true;
				}
				if (updateAccount.isSetLoginPassword()
				        && !updateAccount.getLoginPassword().equals(dbUpdateAccount.getLoginPassword())) {
					operationAccount.setLoginPassword(updateAccount.getLoginPassword().trim());
					needNotify = true;
				}
				if (updateAccount.isSetAccountProperties()
				        && !updateAccount.getAccountProperties().equals(dbUpdateAccount.getAccountProperties())) {
					operationAccount.setAccountProperties(updateAccount.getAccountProperties());
					needNotify = true;
				}
				if (updateAccount.isSetTradeAccountRemark()) {
				    operationAccount.setTradeAccountRemark(updateAccount.getTradeAccountRemark());
				}

				if (updateAccount.isSetTradeBrokerAccessId()) {
					operationAccount.setTradeBrokerAccessId(updateAccount.getTradeBrokerAccessId());
					needNotify = true;
				}

				int rs = new HostingTradeAccountTable(getConnection()).updateTradeAccount(operationAccount, updateAccessEntry);
				if (rs <= 0) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
			    if (!needNotify) {
			        return null;
			    }
				TradeAccountEvent event = new TradeAccountEvent();
				event.setType(TradeAccountEventType.TRADE_ACCOUNT_INFO_UPDATED);
				event.setTradeAccountId(updateAccount.getTradeAccountId());
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
		}.execute();
	}
	
	@Override
	public void setTradeAccountInvalid(long tradeAccountId, InvalidDescription desc) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(tradeAccountId > 0);
	    ParameterChecker.checkInnerNotNull(desc);
	    ParameterChecker.checkInnerArgument(desc.invalidErrorCode != 0);
		
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {

			private boolean statusChanged = false;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingTradeAccount tradeAccount 
					= new HostingTradeAccountTable(getConnection()).getTradeAccount(tradeAccountId, true);
				if (tradeAccount == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
				if (tradeAccount.getAccountAccessState() == TradeAccountAccessState.ACCOUNT_INVALID) {
				    statusChanged = false;
					return ;
				}
				statusChanged = true;
			}

			@Override
			public void onUpdate() throws Exception {
				
				HostingTradeAccount operationAccount = new HostingTradeAccount();
				operationAccount.setTradeAccountId(tradeAccountId);
				operationAccount.setAccountAccessState(TradeAccountAccessState.ACCOUNT_INVALID);
				operationAccount.setApiRetCode(desc.apiRetCode);
				operationAccount.setInvalidErrorCode(desc.invalidErrorCode);
				if (StringUtils.isNotBlank(desc.invalidReason)) {
					operationAccount.setInvalidReason(desc.invalidReason);
				}
				
				new HostingTradeAccountTable(getConnection()).updateTradeAccount(operationAccount, null);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
			    TradeAccountEvent event = new TradeAccountEvent();
				if (statusChanged) {
				    event.setType(TradeAccountEventType.TRADE_ACCOUNT_ACCESS_STATE_CHANGED);
				} else {
				    event.setType(TradeAccountEventType.TRADE_ACCOUNT_ACCESS_DESCRIPTION_CHANGED);
				}
				event.setTradeAccountId(tradeAccountId);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
		}.execute();
	}

	@Override
	public void changeTradeAccountStatus(long tradeAccountId, TradeAccountState newState) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(tradeAccountId > 0);
	    ParameterChecker.checkInnerNotNull(newState);
		
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {
			private boolean needUpdate = false;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingTradeAccount tradeAccount 
					= new HostingTradeAccountTable(getConnection()).getTradeAccount(tradeAccountId, true);
				if (tradeAccount == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
				if (tradeAccount.getAccountState() == newState) {
					needUpdate = false;
				} else {
					needUpdate = true;
				}
			}

			@Override
			public void onUpdate() throws Exception {
				if (!needUpdate) {
					return ;
				}
				
				HostingTradeAccount operationAccount = new HostingTradeAccount();
				operationAccount.setTradeAccountId(tradeAccountId);
				operationAccount.setAccountState(newState);
				
				new HostingTradeAccountTable(getConnection()).updateTradeAccount(operationAccount, null);
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (!needUpdate) {
					return null;
				}
				
				TradeAccountEvent event = new TradeAccountEvent();
				event.setType(TradeAccountEventType.TRADE_ACCOUNT_STATE_CHANGED);
				event.setTradeAccountId(tradeAccountId);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
		}.execute();
	}

	@Override
	public BrokerAccessEntry getBrokerAccessEntry(long tradeAccountId) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(tradeAccountId > 0);
		return new DBQueryHelper<BrokerAccessEntry>(HostingDB.Global()){
			@Override
			protected BrokerAccessEntry onQuery(Connection conn) throws Exception {
				return new HostingTradeAccountTable(conn).getBrokerAccessEntry(tradeAccountId);
			}
		}.query();
	}

	@Override
	public void updateBrokerAccessEntry(long tradeAccountId, BrokerAccessEntry entry) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(tradeAccountId > 0);
	    ParameterChecker.checkInnerNotNull(entry);
		
		new DBStepHelperWithMsg<Void>(HostingDB.Global()){
			
			private boolean needUpdate = false;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingTradeAccountTable accountTable = new HostingTradeAccountTable(getConnection());
				HostingTradeAccount account = accountTable.getTradeAccount(tradeAccountId, false);
				if (account == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "account is not existed!");
				}
				
				if (entry.getBrokerId() != account.getTradeBrokerId()
						|| entry.getEntryId() != account.getTradeBrokerAccessId()) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_BROKER_ACCESS_CHECK_FAILED.getValue()
							, "account is not fit, check failed!");
				}
				
				BrokerAccessEntry dbAccessEntry = accountTable.getBrokerAccessEntry(tradeAccountId);
				if (dbAccessEntry != null && dbAccessEntry.equals(entry)) {
					needUpdate = false;
				} else {
					needUpdate = true;
				}
			}

			@Override
			public void onUpdate() throws Exception {
				if (!needUpdate) {
					return ;
				}
				
				int rs = new HostingTradeAccountTable(getConnection()).updateBrokerAccessEntry(tradeAccountId, entry);
				if (rs <= 0) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "account is not existed!");
				}
			}

			@SuppressWarnings("rawtypes")
			@Override
			public SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (!needUpdate) {
					return null;
				}
				
				TradeAccountEvent event = new TradeAccountEvent();
				event.setType(TradeAccountEventType.TRADE_ACCOUNT_ACCESS_ENTRY_UPDATE);
				event.setTradeAccountId(tradeAccountId);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			public MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			public Void getResult() {
				return null;
			}
			
		}.execute();
		
	}

	@Override
	public void setTradeAccountActive(long tradeAccountId) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(tradeAccountId > 0);
		
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()) {
			private boolean needUpdate = false;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingTradeAccount tradeAccount 
					= new HostingTradeAccountTable(getConnection()).getTradeAccount(tradeAccountId, true);
				if (tradeAccount == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
				if (tradeAccount.getAccountAccessState() == TradeAccountAccessState.ACCOUNT_ACTIVE) {
					needUpdate = false;
				} else {
					needUpdate = true;
				}
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				if (!needUpdate) {
					return ;
				}
				
				HostingTradeAccount operationAccount = new HostingTradeAccount();
				operationAccount.setTradeAccountId(tradeAccountId);
				operationAccount.setAccountAccessState(TradeAccountAccessState.ACCOUNT_ACTIVE);
				operationAccount.setInvalidErrorCode(0);
				operationAccount.setInvalidReason("");
				operationAccount.setApiRetCode(0);
				operationAccount.setHadBeenActived(true);
				
				new HostingTradeAccountTable(getConnection()).updateTradeAccount(operationAccount, null);
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (!needUpdate) {
					return null;
				}
				
				TradeAccountEvent event = new TradeAccountEvent();
				event.setType(TradeAccountEventType.TRADE_ACCOUNT_ACCESS_STATE_CHANGED);
				event.setTradeAccountId(tradeAccountId);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
			
		}.execute();
	}

	@Override
	public void deleteTradeAccount(long tradeAccountId) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(tradeAccountId > 0);
		
		new DBTransactionHelperWithMsg<Void>(HostingDB.Global()){
			private HostingTradeAccount deletedTradeAccount;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingTradeAccountTable table = new HostingTradeAccountTable(getConnection());
				deletedTradeAccount = table.getTradeAccount(tradeAccountId, true);
				if (deletedTradeAccount == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				HostingTradeAccountTable table = new HostingTradeAccountTable(getConnection());
				int rs = table.deleteTradeAccount(tradeAccountId);
				if (rs <= 0) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
							, "trade account is not existed!");
				}
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				TradeAccountEvent event = new TradeAccountEvent();
				event.setType(TradeAccountEventType.TRADE_ACCOUNT_DELETED);
				event.setTradeAccountId(tradeAccountId);
				event.setDeletedTradeAccount(deletedTradeAccount);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(1));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
		}.execute();
	}

	@Override
	public List<HostingTradeAccount> getAllTradeAccounts() throws ErrorInfo {
		return new DBQueryHelper<List<HostingTradeAccount>>(HostingDB.Global()) {
			@Override
			protected List<HostingTradeAccount> onQuery(Connection conn) throws Exception {
				return new HostingTradeAccountTable(conn).getAllTradeAccounts();
			}
			
		}.query();
	}

    @Override
    public long createTradeAccountId() throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return new StorageApiStub().createTradeAccountId();
            }
        });
    }

    @Override
    public void clearAll() throws ErrorInfo {
        new DBStepHelperWithMsg<Void>(HostingDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new HostingTradeAccountTable(getConnection()).deleteAll();
            }

            @Override
            public Void getResult() {
                return null;
            }

            @SuppressWarnings("rawtypes")
            @Override
            protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                TradeAccountEvent event = new TradeAccountEvent();
                event.setType(TradeAccountEventType.TRADE_ACCOUNT_ALL_CLEARD);
                return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(3));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }
        }.execute();
    }
	
}
