package xueqiao.trade.hosting.position.adjust.job;

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
import xueqiao.trade.hosting.asset.thriftapi.TradeAccountPositionTable;
import xueqiao.trade.hosting.position.adjust.storage.NetPositionManualInputTable;
import xueqiao.trade.hosting.position.adjust.storage.PositionAdjustDB;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClearContractExpiredPositionJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        AppLog.d("Start Job ClearContractExpiredPositionJob");

        try {
            List<TradeAccountPositionTable> list = new DBQueryHelper<List<TradeAccountPositionTable>>(PositionAdjustDB.Global()) {
                @Override
                protected List<TradeAccountPositionTable> onQuery(Connection connection) throws Exception {
                    NetPositionManualInputTable netPositionTable = new NetPositionManualInputTable(connection);
                    return netPositionTable.queryGroupByContractId();
                }
            }.query();
            Set<Long> expiredContractIds = new HashSet<>();
            if (list != null && list.size() > 0) {

                for (TradeAccountPositionTable table : list) {
                    IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
                    SledContractDetails details = api.getContractDetailForSure(table.getSledContractId());
                    boolean isExpired = ContractStatus.EXPIRED.equals(details.getSledContract().getContractStatus());
                    if (isExpired) {
                        expiredContractIds.add(table.getSledContractId());
                    }
                }
            }

            if (expiredContractIds.size() == 0) {
                return;
            }

            new DBTransactionHelper<Void>(PositionAdjustDB.Global()) {
                NetPositionManualInputTable netPositionTable;

                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                    netPositionTable = new NetPositionManualInputTable(getConnection());
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    for (long sledContractId : expiredContractIds) {
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
