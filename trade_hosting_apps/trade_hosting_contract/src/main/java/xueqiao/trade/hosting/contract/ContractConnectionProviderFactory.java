package xueqiao.trade.hosting.contract;

import org.soldier.base.logger.AppLog;

import com.longsheng.xueqiao.contract.dao.provider.IConnectionProvider;
import com.longsheng.xueqiao.contract.online.dao.provider.IConnectionProviderFactory;

public class ContractConnectionProviderFactory implements IConnectionProviderFactory {
    private static ContractConnectionProviderFactory INSTANCE;
    
    public static ContractConnectionProviderFactory Global() {
        if (INSTANCE == null) {
            synchronized(ContractConnectionProviderFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ContractConnectionProviderFactory();
                }
            }
        }
        return INSTANCE;
    }
    
    private volatile ContractDB currentDB;
    
    private ContractConnectionProviderFactory() {
    }
    
    @Override
    public IConnectionProvider getConnectionProvider(ConnectionContext ctx) {
        return currentDB.getProviderImpl();
    }
    
    public synchronized ContractDB setCurrentVersion(int version) {
        if (currentDB != null && currentDB.getContractVersion() == version) {
            AppLog.i("contractVersion equals to version, version=" + version);
            return null;
        }
       
        ContractDB needReleaseDB = null;
        if (currentDB != null) {
            needReleaseDB = currentDB;
        } 
        currentDB = new ContractDB("contract_" + version, version);
        
        AppLog.w("[NOTICE]using " + version + " for contract");
        return needReleaseDB;
    }

}
