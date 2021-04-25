package xueqiao.trade.hosting.contract;

import com.antiy.error_code.ErrorCodeInner;
import com.longsheng.xueqiao.contract.dao.provider.IConnectionProvider;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.DBBase;
import xueqiao.trade.hosting.framework.db.IDBOperator;

import java.sql.Connection;
import java.sql.SQLException;

public class ContractDB extends DBBase {

    private String dbName;
    private int contractVersion;

    private IConnectionProvider providerImpl = new IConnectionProvider() {
        @Override
        public Connection getConnection() throws ErrorInfo {
            try {
                return ContractDB.this.getConnection();
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode()
                        , "connect to " + dbName + " failed!");
            }
        }
    };
    
    public ContractDB(String dbName, int contractVersion) {
        super(dbName);
        this.dbName = dbName;
        this.contractVersion = contractVersion;
    }

    public IConnectionProvider getProviderImpl() {
        return providerImpl;
    }

    @Override
    protected String getDBName() {
        return dbName;
    }

    public int getContractVersion() {
        return this.contractVersion;
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public void onUpgradeOneStep(IDBOperator operator, int targetVersion) throws SQLException {

    }
}
