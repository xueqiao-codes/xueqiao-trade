package xueqiao.trade.hosting.position.fee.core.daemon;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.common.util.CollectionUtil;
import xueqiao.trade.hosting.position.fee.core.common.daemon.AbstractDaemon;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;
import xueqiao.trade.hosting.position.fee.core.calculator.UpdateContractPositionRateCalculator;
import xueqiao.trade.hosting.position.fee.storage.handler.UpsideOriginalPositionRateHandler;

import java.util.Iterator;
import java.util.List;

public class UpdateUpsideContractPositionRateDaemon extends AbstractDaemon {

    /*
     * 30分钟
     * */
    public static final long UPDATE_UPSIDE_CONTRACT_POSITION_RATE_PERIOD = 1000 * 60 * 30;

    public UpdateUpsideContractPositionRateDaemon() {
        super();
        setName("UpdateUpsideContractPositionRateDaemon");
    }

    @Override
    public boolean isTaskTime() {
        return true;
    }

    @Override
    public void doTask() throws ErrorInfo, Throwable {
        List<UpsidePositionRate> upsidePositionRateList = UpsideOriginalPositionRateHandler.queryDirtyDatas();

        if (AppLog.infoEnabled()) {
            AppLog.i("doTask ---- upsidePositionRateList size : " + CollectionUtil.getListSize(upsidePositionRateList));
        }

        if (upsidePositionRateList == null || upsidePositionRateList.isEmpty()) {
            return;
        }
        Iterator<UpsidePositionRate> upsidePositionRateIterator = upsidePositionRateList.iterator();
        UpdateContractPositionRateCalculator calculator = new UpdateContractPositionRateCalculator();
        while (upsidePositionRateIterator.hasNext()) {
            UpsidePositionRate upsidePositionRate = upsidePositionRateIterator.next();

            try {
                calculator.onCalculate(upsidePositionRate);
            } catch (Throwable throwable) {
                AppLog.e("doTask ---- calculator.onCalculate", throwable);
            }

            upsidePositionRateIterator.remove();
        }
    }

    @Override
    public long getSleepPeriod() {
        return UPDATE_UPSIDE_CONTRACT_POSITION_RATE_PERIOD;
    }
}
