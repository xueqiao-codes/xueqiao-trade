package xueqiao.trade.hosting.asset.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.job.AssetSubAccountJobScheduler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AssetGlobalDataFactory {
    private static AssetGlobalDataFactory ourInstance = new AssetGlobalDataFactory();

    public static AssetGlobalDataFactory getInstance() {
        return ourInstance;
    }

    private AssetGlobalDataFactory() {
    }

    private Map<Long, AssetSubAccountGlobalData> subAccountGlobalData = new ConcurrentHashMap<>();

    private Map<Long, ContractGlobal> subAccountContractGlobalData = new ConcurrentHashMap<>();


    public AssetSubAccountGlobalData getAssetGlobalData(long subAccountId) {

        AssetSubAccountGlobalData data = ourInstance.subAccountGlobalData.get(subAccountId);
        if (data == null) {
            synchronized (AssetGlobalDataFactory.class) {
                data = ourInstance.subAccountGlobalData.get(subAccountId);
                if (data == null) {
                    synchronized (AssetGlobalDataFactory.class) {
                        data = new AssetSubAccountGlobalData(subAccountId);
                        subAccountGlobalData.put(subAccountId, data);
                        createSubAccountJobs(subAccountId);
                    }
                }
            }
        }
        return data;
    }

    public ContractGlobal getContractGlobalData(long subAccountId) {
        ContractGlobal data = subAccountContractGlobalData.get(subAccountId);
        if (data == null) {
            synchronized (AssetGlobalDataFactory.class) {
                data = ourInstance.subAccountContractGlobalData.get(subAccountId);
                if (data == null) {
                    synchronized (AssetGlobalDataFactory.class) {
                        data = new ContractGlobal(subAccountId);
                        subAccountContractGlobalData.put(subAccountId, data);
                    }
                }
            }
        }
        return data;
    }

    public void setContractGlobal(long subAccount, ContractGlobal contractGlobal) {
        subAccountContractGlobalData.put(subAccount, contractGlobal);
    }

    private void createSubAccountJobs(long subAccountId) {
        try {
            AssetSubAccountJobScheduler.Global().addPositionFundJob(subAccountId);
            AssetSubAccountJobScheduler.Global().addSaveHostingFundJob(subAccountId);
            AssetSubAccountJobScheduler.Global().addFileJob(subAccountId);
            AssetSubAccountJobScheduler.Global().addClearContractExpiredCacheJob(subAccountId);
        } catch (ErrorInfo errorInfo) {
            errorInfo.printStackTrace();
        }
    }

    public void removeAll() {
        subAccountGlobalData = new ConcurrentHashMap<>();
        AssetSubAccountJobScheduler.Global().clear();
        AppLog.w("subAccountGlobalData size: " + subAccountGlobalData.size());
    }
}
