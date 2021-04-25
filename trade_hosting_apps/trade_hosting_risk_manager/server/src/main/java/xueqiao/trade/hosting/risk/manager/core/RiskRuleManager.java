package xueqiao.trade.hosting.risk.manager.core;

import com.google.common.base.Preconditions;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.risk.manager.core.rule.RiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleItem;
import xueqiao.trade.hosting.risk.manager.thriftapi.HostingRiskRuleJoint;
import xueqiao.trade.hosting.risk.manager.thriftapi.TradeHostingRiskManagerErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingConfigApi;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *  风控规则管理器
 */
public class RiskRuleManager {
    private static final String CONFIG_AREA = "HSARRJ";

    public interface IListener {
        void onRulesChanged(RiskRuleManager ruleManager);
    }

    private IHostingConfigApi mConfigApi;
    private RiskContext mContext;

    private volatile RiskRuleJoint mCurrentJoint;

    private Set<IListener> mListeners = new HashSet<>();

    public RiskRuleManager(RiskContext context) {
        this.mContext = context;
        this.mConfigApi = Globals.getInstance().queryInterfaceForSure(IHostingConfigApi.class);

        restore();
        if (mCurrentJoint == null) {
            mCurrentJoint = new RiskRuleJoint();
            mCurrentJoint.setSubAccountId(mContext.getSubAccountId());
        }
    }

    private void restore() {
        while(true) {
            try {
                mCurrentJoint = (RiskRuleJoint) mConfigApi.getConfig(CONFIG_AREA
                        , String.valueOf(mContext.getSubAccountId())
                        , RiskRuleJoint.class);
                return ;
            } catch (ErrorInfo e) {
                AppLog.e(e.getMessage(), e);
                if (e.getErrorCode() == TradeHostingBasicErrorCode.ERROR_CONFIG_LOST.getValue()) {
                    return ;
                }
            }
            // 等待系统恢复
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public RiskContext getContext() {
        return mContext;
    }

    private void checkInWorkThread() {
        Preconditions.checkState(getContext().getWorkThread().isInCurrentThread());
    }

    public void addListener(IListener listener) {
        checkInWorkThread();
        if (listener == null) {
            return ;
        }

        mListeners.add(listener);
    }

    public void rmListener(IListener listener) {
        checkInWorkThread();
        if (listener == null) {
            return ;
        }

        mListeners.remove(listener);
    }

    private void notifyListeners() {
        for (IListener listener : mListeners) {
            try {
                listener.onRulesChanged(this);
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }
    }

    public RiskRuleJoint getJoint() {
        return mCurrentJoint;
    }

    private void checkOpVersion(int version) throws ErrorInfo {
        if (version != mCurrentJoint.getVersion() + 1) {
            throw new ErrorInfo(TradeHostingRiskManagerErrorCode.ERROR_RISKRULE_JOINT_OPERATION_ERROR.getValue()
                , "operate version error!");
        }
    }

    private RiskRuleJoint updateJoint(RiskRuleJoint newJoint) throws ErrorInfo {
        mConfigApi.setConfig(CONFIG_AREA
                , String.valueOf(mContext.getSubAccountId())
                , newJoint.getVersion()
                , newJoint
                , null);
        mCurrentJoint = newJoint;

        getContext().getWorkThread().postTask(new Runnable() {
            @Override
            public void run() {
                notifyListeners();
            }
        });

        return newJoint;
    }

    public synchronized HostingRiskRuleJoint setRiskEnabled(int version, boolean riskEnabled) throws ErrorInfo {
        checkOpVersion(version);

        RiskRuleJoint newJoint = new RiskRuleJoint(mCurrentJoint);
        newJoint.setRiskEnabled(riskEnabled);
        newJoint.setVersion(newJoint.getVersion() + 1);

        return updateJoint(newJoint);
    }

    public synchronized HostingRiskRuleJoint batchSetSupportedItems(int version
            , Set<String> openedItemIds
            , Set<String> closedItemIds) throws ErrorInfo {
        checkOpVersion(version);

        RiskRuleJoint newJoint = new RiskRuleJoint(mCurrentJoint);
        newJoint.setVersion(newJoint.getVersion() + 1);
        newJoint.batchOpenSupportedItems(openedItemIds);
        newJoint.batchCloseSupportedItems(closedItemIds);

        return updateJoint(newJoint);
    }

    public synchronized HostingRiskRuleJoint batchSetTradedCommodityItems(int version
            , Set<Long> enabledCommodityIds
            , Set<Long> disabledCommodityIds) throws ErrorInfo {
        checkOpVersion(version);

        RiskRuleJoint newJoint = new RiskRuleJoint(mCurrentJoint);
        newJoint.setVersion(newJoint.getVersion() + 1);
        newJoint.batchEnabledTradeCommodityItems(enabledCommodityIds);
        newJoint.batchDisableTradedCommodityItems(disabledCommodityIds);

        return updateJoint(newJoint);
    }

    public synchronized HostingRiskRuleJoint batchSetGlobalRules(int version
            , Map<String, HostingRiskRuleItem> ruleItems) throws ErrorInfo {
        checkOpVersion(version);

        RiskRuleJoint newJoint = new RiskRuleJoint(mCurrentJoint);
        newJoint.setVersion(newJoint.getVersion() + 1);
        newJoint.batchSetGlobalRules(ruleItems);

        return updateJoint(newJoint);
    }

    public synchronized HostingRiskRuleJoint batchSetCommodityRules(int version
            , Map<Long,Map<String,HostingRiskRuleItem>> ruleItems) throws ErrorInfo {
        checkOpVersion(version);

        RiskRuleJoint newJoint = new RiskRuleJoint(mCurrentJoint);
        newJoint.setVersion(newJoint.getVersion() + 1);
        newJoint.batchSetCommodityRules(ruleItems);

        return updateJoint(newJoint);
    }

}
