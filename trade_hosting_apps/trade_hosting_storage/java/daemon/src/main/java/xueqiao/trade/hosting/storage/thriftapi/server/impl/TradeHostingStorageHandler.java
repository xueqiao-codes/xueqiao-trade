package xueqiao.trade.hosting.storage.thriftapi.server.impl;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import net.qihoo.qconf.Qconf;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.id_maker.IdMaker;
import org.soldier.platform.id_maker.IdMakerFactory;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import org.soldier.platform.zookeeper.ZooKeeperManagerFactory;
import xueqiao.trade.hosting.HostingExecOrderDealID;
import xueqiao.trade.hosting.HostingExecOrderRef;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingSession;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.framework.utils.ThriftExtJsonUtils;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi.InvalidDescription;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;
import xueqiao.trade.hosting.storage.thriftapi.TradeAccountInvalidDescription;
import xueqiao.trade.hosting.storage.thriftapi.UpdateConfigDescription;
import xueqiao.trade.hosting.storage.thriftapi.server.TradeHostingStorageAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TradeHostingStorageHandler extends TradeHostingStorageAdaptor {
	
    private HostingConfigUpdator mConfigUpdator;
	private IHostingTradeAccountApi mTradeAccountApi;
	private IHostingDealingApi mDealingApi;
	private IHostingManageApi mManageApi;
	private IHostingSessionApi mSessionApi;
	
	private IdMaker mComposeGraphIdMaker;
    private IdMaker mTradeAccountIdMaker;
	private IdMaker mSubAccountIdMaker;
	
	
	@Override
	public int InitApp(Properties props) {
	    mConfigUpdator = new HostingConfigUpdator();
	    
		mTradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
		mDealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
		mManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
		mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
		
		mComposeGraphIdMaker = IdMakerFactory.getInstance().getIdMaker(200);
        if (mComposeGraphIdMaker == null) {
            return -1;
        }
        mTradeAccountIdMaker = IdMakerFactory.getInstance().getIdMaker(201);
        if (mTradeAccountIdMaker == null) {
            return -1;
        }
		
		mSubAccountIdMaker = IdMakerFactory.getInstance().getIdMaker(205);
		if (mSubAccountIdMaker == null) {
		    return -1;
		}
		return 0;
	}
	
	@Override
	public void destroy() {
	}

	@Override
	protected List<HostingTradeAccount> getTraddeAccount(TServiceCntl oCntl
			, long tradeAccountId) throws ErrorInfo, TException {
		ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
		
		List<HostingTradeAccount> results = new ArrayList<HostingTradeAccount>();
		HostingTradeAccount tradeAccount = mTradeAccountApi.getTradeAccount(tradeAccountId);
		if (tradeAccount != null) {
			results.add(tradeAccount);
		}
		return results;
	}

	@Override
	protected List<BrokerAccessEntry> getBrokerAccessEntry(TServiceCntl oCntl, long tradeAccountId) 
			throws ErrorInfo, TException {
		ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
		
		List<BrokerAccessEntry> results = new ArrayList<BrokerAccessEntry>();
		BrokerAccessEntry entry = mTradeAccountApi.getBrokerAccessEntry(tradeAccountId);
		if (entry != null) {
			results.add(entry);
		}
		return results;
	}

	@Override
	protected void setTradeAccountInvalid(TServiceCntl oCntl
			, long tradeAccountId
			, TradeAccountInvalidDescription invalidDescription) throws ErrorInfo, TException {
		ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
		ParameterChecker.check(invalidDescription != null, "invalidDescription should not be null");
		ParameterChecker.check(invalidDescription.getInvalidErrorCode() != 0
				, "invalidDescription invalidErrorCode should not be 0");
		
		InvalidDescription desc = new InvalidDescription();
		desc.invalidErrorCode = invalidDescription.getInvalidErrorCode();
		if (invalidDescription.isSetApiRetCode()) {
			desc.apiRetCode = invalidDescription.getApiRetCode();
		}
		if (invalidDescription.isSetInvalidReason()) {
			desc.invalidReason = invalidDescription.getInvalidReason();
		}
		mTradeAccountApi.setTradeAccountInvalid(tradeAccountId, desc);
	}

	@Override
	protected void setTradeAccountActive(TServiceCntl oCntl, long tradeAccountId) throws ErrorInfo, TException {
		ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
		mTradeAccountApi.setTradeAccountActive(tradeAccountId);
	}
	
	@Override
	protected List<HostingTradeAccount> getAllTradeAccounts(TServiceCntl oCntl) throws ErrorInfo, TException {
		return mTradeAccountApi.getAllTradeAccounts();
	}


    @Override
    protected long createComposeGraphId(TServiceCntl oCntl) throws ErrorInfo, TException {
        try {
            return mComposeGraphIdMaker.createId();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!" + e.getMessage());
        }
    }

    @Override
    protected long createTradeAccountId(TServiceCntl oCntl) throws ErrorInfo, TException {
        try {
            return mTradeAccountIdMaker.createId();
        } catch (Throwable e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!" + e.getMessage());
        }
    }

    @Override
    protected void updateConfig(TServiceCntl oCntl, UpdateConfigDescription configDescription)
            throws ErrorInfo, TException {
        mConfigUpdator.updateConfig(configDescription);
    }

    @Override
    protected long getMachineId(TServiceCntl oCntl) throws ErrorInfo, TException {
        return mManageApi.getMachineId();
    }

    @Override
    protected List<HostingSession> getHostingSession(TServiceCntl oCntl, int subUserId) throws ErrorInfo, TException {
        SessionEntry se = mSessionApi.getSession(subUserId);
        List<HostingSession> resultList = new ArrayList<>();
        if (se == null) {
            return resultList;
        }
        HostingSession hostingSession = new HostingSession();
        hostingSession.setMachineId(mManageApi.getMachineId())
                      .setSubUserId(se.getSubUserId())
                      .setToken(se.getToken())
                      .setLoginIP(se.getLoginIP());
        resultList.add(hostingSession);
        return resultList;
    }


    @Override
    protected List<BrokerAccessEntry> getBrokerAccessEntryFromCloud(TServiceCntl oCntl
            , long tradeBrokerId
            , long tradeBrokerAccessId) throws ErrorInfo, TException {
        try {
            List<BrokerAccessEntry> resultEntries = new ArrayList<BrokerAccessEntry>();
            
            String qconfName = ZooKeeperManagerFactory.Global().getQconfIDCName("xueqiao_broker");
            
            StringBuilder pathBuilder = new StringBuilder(64);
            pathBuilder.append("xueqiao/broker/access");
      
            List<String> brokerIDKeys = Qconf.getBatchKeys(pathBuilder.toString(), qconfName);
            if (brokerIDKeys == null || !brokerIDKeys.contains(String.valueOf(tradeBrokerId))) {
                return resultEntries;
            }
      
            pathBuilder.append("/").append(tradeBrokerId);
            List<String> brokerAccessIDKeys = Qconf.getBatchKeys(pathBuilder.toString(), qconfName);
            if (brokerAccessIDKeys == null || !brokerAccessIDKeys.contains(String.valueOf(tradeBrokerAccessId))) {
                return resultEntries    ;
            }
      
            pathBuilder.append("/").append(tradeBrokerAccessId);
            resultEntries.add(ThriftExtJsonUtils.fromJsonTBase(
                  Qconf.getConf(pathBuilder.toString(), qconfName), BrokerAccessEntry.class));
            return resultEntries;
      } catch(Throwable e) {
          AppLog.e(e.getMessage(), e);
          throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue(), "server inner error!" + e.getMessage());
      }
    }

    @Override
    protected long createSubAccountId(TServiceCntl oCntl) throws ErrorInfo, TException {
        try {
            return mSubAccountIdMaker.createId();
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!");
        }
    }


}
