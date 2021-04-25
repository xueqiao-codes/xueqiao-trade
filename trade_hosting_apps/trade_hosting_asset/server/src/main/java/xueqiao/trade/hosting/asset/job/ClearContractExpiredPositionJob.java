package xueqiao.trade.hosting.asset.job;

import com.longsheng.xueqiao.contract.standard.thriftapi.ContractStatus;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.trade.TradeAccountNetPositionTable;
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionTable;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.sql.Connection;
import java.util.*;

public class ClearContractExpiredPositionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AppLog.w("Start Job ClearContractExpiredPositionJob");

        try {
            List<TradeAccountPositionTable> list = new DBQueryHelper<List<TradeAccountPositionTable>>(AssetDB.Global()) {
                @Override
                protected List<TradeAccountPositionTable> onQuery(Connection connection) throws Exception {
                    TradeAccountNetPositionTable netPositionTable = new TradeAccountNetPositionTable(connection);

                    return netPositionTable.queryAll();
                }
            }.query();
            Set<Long> expiredContractIds = new HashSet<>();
            if (list != null && list.size() > 0) {

                for (TradeAccountPositionTable table : list) {
                    IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
                    SledContractDetails details = api.getContractDetailForSure(table.getSledContractId());
                    boolean isExpired = ContractStatus.EXPIRED.equals(details.getSledContract().getContractStatus());
                    if (isExpired) {
                        AppLog.w("contract expired: "+ table.getSledContractId());
                        expiredContractIds.add(table.getSledContractId());
                    }
                }
            }

            if (expiredContractIds.size() == 0) {
                return;
            }

            new DBTransactionHelper<Void>(AssetDB.Global()) {
                TradeAccountNetPositionTable netPositionTable;

                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                    netPositionTable = new TradeAccountNetPositionTable(getConnection());
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    for (long sledContractId : expiredContractIds) {
                        AppLog.w("delete expired contract : "+ sledContractId);
                        netPositionTable.delete(sledContractId);
                    }
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();
        } catch (ErrorInfo errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        }
    }
}
