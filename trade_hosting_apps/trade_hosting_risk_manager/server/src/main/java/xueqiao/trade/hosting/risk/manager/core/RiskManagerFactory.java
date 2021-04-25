package xueqiao.trade.hosting.risk.manager.core;

import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingSubAccount;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.framework.thread.TaskThreadPool;
import xueqiao.trade.hosting.storage.apis.IHostingSubAccountApi;

import java.util.HashMap;

public class RiskManagerFactory {
    private static RiskManagerFactory sInstance;

    public static RiskManagerFactory Global() {
        if (sInstance == null) {
            synchronized (RiskManagerFactory.class) {
                if (sInstance == null) {
                    sInstance = new RiskManagerFactory();
                }
            }
        }
        return sInstance;
    }

    private TaskThreadPool mThreadPool = new TaskThreadPool("risk-manager", 2);
    private HashMap<Long, RiskManager> mRiskManagers = new HashMap<>();

    private RiskManagerFactory() {
    }

    public synchronized RiskManager getRiskManager(long subAccountId) {
        return mRiskManagers.get(subAccountId);
    }

    public synchronized void addRiskManager(long subAccountId) {
        if (mRiskManagers.containsKey(subAccountId)) {
            return ;
        }

        initRiskManager(subAccountId);
    }

    private RiskManager initRiskManager(long subAccountId) {
        return new RiskManager(new RiskContext(subAccountId, mThreadPool.allocTaskThread()));
    }

    public synchronized RiskManager getRiskManagerForSure(long subAccountId) throws ErrorInfo {
        RiskManager riskManager = mRiskManagers.get(subAccountId);
        if (riskManager != null) {
            return riskManager;
        }

        IHostingSubAccountApi subAccountApi = Globals.getInstance().queryInterfaceForSure(
                IHostingSubAccountApi.class);
        HostingSubAccount subAccount = subAccountApi.getSubAccount(subAccountId);
        if (subAccount == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "SubAccountId= " + subAccountId + " is not existed");
        }

        return initRiskManager(subAccountId);
    }

}
