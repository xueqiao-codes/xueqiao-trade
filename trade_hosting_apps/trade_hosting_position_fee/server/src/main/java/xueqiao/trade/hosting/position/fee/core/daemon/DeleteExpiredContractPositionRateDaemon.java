package xueqiao.trade.hosting.position.fee.core.daemon;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import org.apache.commons.lang.time.DateUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.core.common.daemon.AbstractDaemon;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.*;

import java.util.List;

public class DeleteExpiredContractPositionRateDaemon extends AbstractDaemon {

    /*
     * 1天
     * */
    public static final long DELETE_EXPIRED_CONTRACT_POSITION_RATE_PERIOD = DateUtils.MILLIS_PER_DAY;

    public DeleteExpiredContractPositionRateDaemon() {
        super();
        setName("DeleteExpiredContractPositionRateDaemon");
    }

    @Override
    public boolean isTaskTime() {
        return true;
    }

    @Override
    public void doTask() throws ErrorInfo, Throwable {
        int pageIndex = 0;
        int pageSize = 50;
        List<SledContract> expiredContractList;
        do {
            expiredContractList = HostingContractApi.queryExpiredContracts(pageIndex, pageSize);
            if (expiredContractList == null || expiredContractList.isEmpty()) {
                break;
            }
            for (SledContract expiredContract : expiredContractList) {
                if (AppLog.infoEnabled()) {
                    AppLog.i("delete expired contract positon rate, contractId : " + expiredContract.getSledContractId());
                }
                deleteExpiredContractPositionRate(expiredContract);
            }
            /*
             * 间隔1秒
             * */
            sleep(DateUtils.MILLIS_PER_SECOND);
        } while (expiredContractList.size() == pageSize);

    }

    @Override
    public long getSleepPeriod() {
        return DELETE_EXPIRED_CONTRACT_POSITION_RATE_PERIOD;
    }

    public void deleteExpiredContractPositionRate(SledContract expiredContract) throws ErrorInfo {
        new DBStepHelper<Void>(PositionFeeDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {

            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                new UpsideContractCommissionTable(getConnection()).delete(expiredContract.getSledContractId());
                new UpsideContractMarginTable(getConnection()).delete(expiredContract.getSledContractId());
                new XQContractCommissionTable(getConnection()).delete(expiredContract.getSledContractId());
                new XQContractMarginTable(getConnection()).delete(expiredContract.getSledContractId());

                new UpsideOriginalPositionRateTable(getConnection()).delete(expiredContract.getSledCommodityId(), expiredContract.getSledContractCode());
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
