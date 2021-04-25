package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;

import xueqiao.trade.hosting.EHostingUserRole;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.entry.core.EntryCache;
import xueqiao.trade.hosting.entry.core.PermissionChecker;
import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

public class HandlerBase {
    
    private TServiceCntl mServiceCntl;
    private LandingInfo mLandingInfo;
    
    protected HandlerBase(
            TServiceCntl serviceCntl
            , LandingInfo landingInfo) throws ErrorInfo {
        this.mServiceCntl = serviceCntl;
        this.mLandingInfo = landingInfo;
    }
    
    public long getMachineId() {
        if (mLandingInfo == null) {
            return 0;
        }
        
        return mLandingInfo.getMachineId();
    }
    
    public int getSubUserId() {
        if (mLandingInfo == null) {
            return 0;
        }
        
        return mLandingInfo.getSubUserId();
    }
    
    public TServiceCntl getServiceCntl() {
        return mServiceCntl;
    }
    
    public LandingInfo getLandingInfo() {
        return mLandingInfo;
    }
    
    protected void permit(EHostingUserRole userRole) throws ErrorInfo {
        PermissionChecker.checkHasPermission(mLandingInfo, userRole);
    }
    
    protected boolean hasPermission(EHostingUserRole userRole) throws ErrorInfo {
        return PermissionChecker.hasPermission(mLandingInfo, userRole);
    }
    
    protected HostingRunningMode getRunningMode() throws ErrorInfo {
        HostingRunningMode runningMode = EntryCache.Global().getHostingRunningMode();
        if (runningMode != null) {
            if (AppLog.debugEnabled()) {
                AppLog.d("runningModel from cache is " + runningMode);
            }
            return runningMode;
        }
        
        runningMode = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class).getRunningMode();
        if (runningMode == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.HOSTING_HAS_NOT_INITED.getValue()
                    , "hosting has not init");
        }
        return runningMode;
    }
    
    protected void checkContractDetailRunningMode(SledContractDetails contractDetails) throws ErrorInfo {
        HostingRunningMode runningMode = getRunningMode();
        if (runningMode == HostingRunningMode.REAL_HOSTING) {
            if (contractDetails.getSledContract().getPlatformEnv() != TechPlatformEnv.REAL) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONTRACT_NOT_MATCH_MACHINE_RUNNING_MODE.getValue()
                        , "real hosting should use real contract");
            }
        } else {
            if (contractDetails.getSledContract().getPlatformEnv() != TechPlatformEnv.SIM) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_CONTRACT_NOT_MATCH_MACHINE_RUNNING_MODE.getValue()
                        , "sim hosting should use sim contract");
            }
        }
    }
    
    protected boolean hasRelatedAccount(long subAccountId) throws ErrorInfo {
        return EntryCache.Global().getSubAccountRelatedCache().hasRelated(subAccountId, getSubUserId());
    }
    
    protected void checkHasRelatedAccount(long subAccountId) throws ErrorInfo {
        if (!hasRelatedAccount(subAccountId)) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_SUB_ACCOUNT_RELATED_ITEM_NOT_EXIST.getValue()
                    , "do not has related sub account");
        }
    }
}
