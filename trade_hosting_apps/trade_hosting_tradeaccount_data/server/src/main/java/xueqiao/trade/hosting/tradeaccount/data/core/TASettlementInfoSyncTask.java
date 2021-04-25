package xueqiao.trade.hosting.tradeaccount.data.core;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.tradeaccount.data.TradeAccountSettlementInfo;
import xueqiao.trade.hosting.tradeaccount.data.storage.TADataDB;
import xueqiao.trade.hosting.tradeaccount.data.storage.table.SettlementInfoTable;
import xueqiao.trade.hosting.upside.entry.TSettlementInfo;

import java.sql.Connection;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public abstract class TASettlementInfoSyncTask extends TATaskBase {

    private Date mSettlementDate;

    public TASettlementInfoSyncTask(long tradeAccountId, Date settlementDate) {
        super(tradeAccountId);
        this.mSettlementDate = settlementDate;
    }

    public Date getSettlementDate() {
        return mSettlementDate;
    }

    @Override
    protected TATaskResult doTask() throws Exception {
        if (mSettlementDate.after(new Date(System.currentTimeMillis()))) {
            return TATaskResult.TASK_SETTLEMENT_CONTENT_INVALID;
        }

        boolean existed = new DBQueryHelper<Boolean>(TADataDB.Global()) {
            @Override
            protected Boolean onQuery(Connection conn) throws Exception {
                return new SettlementInfoTable(conn).isSettlementInfoExist(getTradeAccountId(), mSettlementDate);
            }
        }.query();

        if (existed) {
            return TATaskResult.TASK_SETTLEMENT_CONTENT_EXISTED;
        }

        TSettlementInfo upsideSettlementInfo
                = getUpsideEntryStub().getSettlementInfo(mSettlementDate.toString());
        do {
            if (StringUtils.isBlank(upsideSettlementInfo.getSettlementContent())) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, -10);
                Date tenDaysBefore = new Date(calendar.getTimeInMillis());

                if (mSettlementDate.before(tenDaysBefore)
                        || mSettlementDate.toString().equals(tenDaysBefore.toString())) {
                    break;
                }

                Date nearestSettlementDate = new DBQueryHelper<Date>(TADataDB.Global()) {
                    @Override
                    protected Date onQuery(Connection conn) throws Exception {
                        return new SettlementInfoTable(conn).getNearsetSettlementDate(getTradeAccountId());
                    }
                }.query();

                if (nearestSettlementDate.before(mSettlementDate)) {
                    return TATaskResult.TASK_SETTLEMENT_CONTENT_NOT_SURE;
                }

            }
        } while(false);

        new DBStepHelper<Void>(TADataDB.Global()){
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {

                TradeAccountSettlementInfo settlementInfo = new TradeAccountSettlementInfo();
                settlementInfo.setTradeAccountId(getTradeAccountId());
                settlementInfo.setSettlementDate(mSettlementDate.toString());
                settlementInfo.setSettlementContent(upsideSettlementInfo.getSettlementContent());
                new SettlementInfoTable(getConnection()).insertSettlementInfo(settlementInfo);
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        return TATaskResult.TASK_SUCCESS;
    }

    protected Map<String, String> getParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("settlementDate", getSettlementDate().toString());
        return parameters;
    }

}
