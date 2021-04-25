package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.longsheng.xueqiao.broker.thriftapi.BrokerAccessEntry;
import com.longsheng.xueqiao.broker.thriftapi.BrokerPlatform;
import com.longsheng.xueqiao.broker.thriftapi.TechPlatformEnv;

import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.trade_hostingConstants;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IBrokerAccessEntryProvider;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryTradeAccountOption;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingTradeAccountPage;
import xueqiao.trade.hosting.terminal.ao.trade_hosting_terminal_aoConstants;

public class HostingTradeAccountHandler extends HandlerBase {

    private IHostingManageApi mManageApi;
    private IHostingTradeAccountApi mTradeAccountApi;
    
    public HostingTradeAccountHandler(TServiceCntl serviceCntl
            , LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        mManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
        mTradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        
        permit(EHostingUserRole.AdminGroup);
    }
    
    public void checkLoginUserName(String loginUserName) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotBlank(loginUserName), "loginUserName should not be blank");
        ParameterChecker.check(loginUserName.trim().length() 
                <= trade_hostingConstants.MAX_TRADE_ACCOUNT_LOGIN_USER_NAME_LENGTH
                ,  "loginUserName is too long, should <= " + trade_hostingConstants.MAX_TRADE_ACCOUNT_LOGIN_USER_NAME_LENGTH);
    }
    
    public void checkLoginPassword(String loginPassword) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotBlank(loginPassword), "loginPassword should not be blank");
        ParameterChecker.check(loginPassword.trim().length()
                <= trade_hostingConstants.MAX_TRADE_ACCOUNT_LOGIN_PASSWD_LENGTH
                , "loginPassword is too long, should <=" + trade_hostingConstants.MAX_TRADE_ACCOUNT_LOGIN_PASSWD_LENGTH);
    }
    
    public void checkEsunny3AccountProperties(HostingTradeAccount checkAccount) throws ErrorInfo {
    	ParameterChecker.check(checkAccount.isSetAccountProperties(), "accountProperties should be set");
    	ParameterChecker.check(checkAccount.getAccountProperties().containsKey(trade_hostingConstants.ESUNNY3_APPID_PROPKEY)
    			, "accountProperties should contains property key " + trade_hostingConstants.ESUNNY3_APPID_PROPKEY);
    	ParameterChecker.check(checkAccount.getAccountProperties().containsKey(trade_hostingConstants.ESUNNY3_CERTINFO_PROPKEY)
    			, "accountProperties should contains property key " + trade_hostingConstants.ESUNNY3_CERTINFO_PROPKEY);
    }
    
    public void checkEsunny9AccountProperties(HostingTradeAccount checkAccount) throws ErrorInfo {
    	ParameterChecker.check(checkAccount.isSetAccountProperties(), "accountProperties should be set");
    	ParameterChecker.check(checkAccount.getAccountProperties().containsKey(trade_hostingConstants.ESUNNY9_AUTHCODE_PROPKEY)
    			, "accountProperties should contains property key " + trade_hostingConstants.ESUNNY9_AUTHCODE_PROPKEY);
    }
    
    public BrokerAccessEntry checkBrokerAccess(int tradeBrokerId, int tradeBrokerAccessId) throws ErrorInfo {
        ParameterChecker.check(tradeBrokerId > 0, "tradeBrokerId should > 0");
        ParameterChecker.check(tradeBrokerAccessId > 0, "tradeBrokerAccessId should > 0");
        
        try {
            BrokerAccessEntry entry = Globals.getInstance().queryInterfaceForSure(IBrokerAccessEntryProvider.class)
                    .getBrokerAccessEntry(tradeBrokerId, tradeBrokerAccessId);
            if (entry == null 
                    || entry.getBrokerId() != tradeBrokerId
                    || entry.getEntryId() != tradeBrokerAccessId) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_BROKER_ACCESS_CHECK_FAILED.getValue()
                        , "broker access entry ids check not equals");
            }
            return entry;
        } catch (ErrorInfo ei) {
            throw ei;
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SERVER_INNER.getValue()
                    , "server inner error!");
        }
    }

    private void checkAccessEntryEnvironment(BrokerAccessEntry accessEntry) throws ErrorInfo {
        HostingRunningMode runningMode = mManageApi.getRunningMode();
        if (runningMode == HostingRunningMode.REAL_HOSTING) {
            if (accessEntry.getTechPlatformEnv() != TechPlatformEnv.REAL) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_BROKER_TECH_ENV_NOT_MATCH.getValue()
                        , runningMode + " hosting should use REAL broker access");
            }
        } else {
            if (accessEntry.getTechPlatformEnv() != TechPlatformEnv.SIM) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_BROKER_TECH_ENV_NOT_MATCH.getValue()
                        , runningMode + " hosting should use SIM broker access");
            }
        }
    }

    public long addTradeAccount(HostingTradeAccount newAccount) throws ErrorInfo, TException {
        ParameterChecker.check(newAccount != null, "newAccount should not be null");
        checkLoginUserName(newAccount.getLoginUserName());
        checkLoginPassword(newAccount.getLoginPassword());
        
        BrokerAccessEntry initedAccessEntry = checkBrokerAccess(
                newAccount.getTradeBrokerId() , newAccount.getTradeBrokerAccessId());
        checkAccessEntryEnvironment(initedAccessEntry);
        
        HostingTradeAccount operationAccount = new HostingTradeAccount();
        operationAccount.setTradeAccountId(mTradeAccountApi.createTradeAccountId());
        operationAccount.setLoginUserName(newAccount.getLoginUserName().trim());
        operationAccount.setLoginPassword(newAccount.getLoginPassword().trim());
        operationAccount.setTradeBrokerId(initedAccessEntry.getBrokerId());
        operationAccount.setTradeBrokerAccessId(initedAccessEntry.getEntryId());
        if (newAccount.isSetTradeAccountRemark()) {
            operationAccount.setTradeAccountRemark(newAccount.getTradeAccountRemark());
        }
        
        if (initedAccessEntry.getPlatform() == BrokerPlatform.ESUNNY_3) {
        	checkEsunny3AccountProperties(newAccount);
        } else if (initedAccessEntry.getPlatform() == BrokerPlatform.ESUNNY) {
        	checkEsunny9AccountProperties(newAccount);
        }
        operationAccount.setAccountProperties(newAccount.getAccountProperties());
        
        mTradeAccountApi.addTradeAccount(operationAccount, initedAccessEntry);
        return operationAccount.getTradeAccountId();
    }

    public void disableTradeAccount(long tradeAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");
        mTradeAccountApi.changeTradeAccountStatus(tradeAccountId, TradeAccountState.ACCOUNT_DISABLED);
    }

    public QueryHostingTradeAccountPage getTradeAccountPage(
            QueryHostingTradeAccountOption queryOption
            , IndexedPageOption pageOption) throws ErrorInfo, TException {
        PageOptionHelper.checkIndexedPageOption(pageOption
                , trade_hosting_terminal_aoConstants.MAX_QUERY_TRADEACCOUNT_PAGESIZE);
        
        QueryTradeAccountOption apiQueryOption = new QueryTradeAccountOption();
        if (queryOption != null) {
            if (queryOption.isSetTradeAccountId()) {
                apiQueryOption.setTradeAccountId(queryOption.getTradeAccountId());
            }
            if (queryOption.isSetBrokerId()) {
                apiQueryOption.setTradeBrokerId(queryOption.getBrokerId());
            }
            if (queryOption.isSetInAccountStates()) {
                apiQueryOption.setInStates(queryOption.getInAccountStates());
            }
            if (queryOption.isSetNotInAccountStates()) {
                apiQueryOption.setNotInStates(queryOption.getNotInAccountStates());
            }
            if (queryOption.isSetLoginUserNamePartical()) {
                apiQueryOption.setLoginUserNamePartical(queryOption.getLoginUserNamePartical().trim());
            }
            if (queryOption.isSetLoginUserNameWhole()) {
                apiQueryOption.setLoginUserNameWhole(queryOption.getLoginUserNameWhole().trim());
            }
        }
        
        PageResult<HostingTradeAccount> tradeAccountPage 
             = mTradeAccountApi.queryTradeAccountPage(apiQueryOption, PageOptionHelper.toApiPageOption(pageOption));
        QueryHostingTradeAccountPage resultPage = new QueryHostingTradeAccountPage();
        resultPage.setTotalCount(tradeAccountPage.getTotalCount());
        List<HostingTradeAccount> resultTradeAccounts = new ArrayList<HostingTradeAccount>(1+resultPage.getResultListSize());
        for (HostingTradeAccount tradeAccount : tradeAccountPage.getPageList()) {
            tradeAccount.unsetLoginPassword();
            resultTradeAccounts.add(tradeAccount);
        }
        resultPage.setResultList(resultTradeAccounts);
        return resultPage;
    }

    public void enableTradeAccount(long tradeAccountId)
            throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId shoud > 0");
        
        mTradeAccountApi.changeTradeAccountStatus(tradeAccountId, TradeAccountState.ACCOUNT_NORMAL);
    }

    public void updateTradeAccountInfo(HostingTradeAccount updateAccount) throws ErrorInfo, TException {
        ParameterChecker.check(updateAccount != null, "updateAccount should not be null");
        ParameterChecker.check(updateAccount.getTradeAccountId() > 0, "updateAccount shoud has tradeAccountId");
        
        if (updateAccount.isSetLoginUserName()) {
            checkLoginUserName(updateAccount.getLoginUserName());
        }
        if (updateAccount.isSetLoginPassword()) {
            checkLoginPassword(updateAccount.getLoginPassword());
        }
        if (updateAccount.isSetAccountProperties()) {
        	HostingTradeAccount originTradeAccount = mTradeAccountApi.getTradeAccount(updateAccount.getTradeAccountId());
        	if (originTradeAccount == null) {
        		throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue()
						, "trade account is not existed!");
        	}
        	if (originTradeAccount.getBrokerTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_3) {
        		checkEsunny3AccountProperties(updateAccount);
        	} else if (originTradeAccount.getBrokerTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_9) {
        		checkEsunny9AccountProperties(updateAccount);
        	}
        }

        BrokerAccessEntry updateAccessEntry = null;
        if (updateAccount.isSetTradeBrokerAccessId()) {
            ParameterChecker.check(updateAccount.getTradeBrokerAccessId() > 0, "tradeBrokerAccessId should > 0");
            ParameterChecker.check(updateAccount.isSetTradeBrokerId() && updateAccount.getTradeBrokerId() > 0
                    , "tradeBrokerId should be set tradeBrokerId > 0");

            updateAccessEntry = checkBrokerAccess(
                    updateAccount.getTradeBrokerId() , updateAccount.getTradeBrokerAccessId());
            checkAccessEntryEnvironment(updateAccessEntry);
        }
        
        mTradeAccountApi.updateTradeAccountInfo(updateAccount, updateAccessEntry);
    }
    
    public void rmTradeAccount(long tradeAccountId) throws ErrorInfo, TException {
        ParameterChecker.check(tradeAccountId > 0, "tradeAccountId should > 0");

        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_NOT_SUPPORTED.getValue(), "not supported operation");

//        mTradeAccountApi.changeTradeAccountStatus(tradeAccountId, TradeAccountState.ACCOUNT_REMOVED);
    }


}
