package xueqiao.trade.hosting.arbitrage.core;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRecordItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRecordTable;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

import java.sql.Connection;

/**
 *  订单额外信息记录器
 */
public class XQOrderSubRecorder {
    private XQOrderContext mOrderContext;
    private long mSledContractId;
    private boolean mDBRecordExist;
    private Double mLatestUsedPrice;

    public XQOrderSubRecorder(XQOrderContext orderContext, long sledContractId) throws ErrorInfo {
        this.mOrderContext = orderContext;
        this.mSledContractId = sledContractId;
        restoreFromDB();
    }

    private void restoreFromDB() throws ErrorInfo {
        XQOrderRecordItem recordItem = new DBQueryHelper<XQOrderRecordItem>(ArbitrageDB.Global()) {
            @Override
            protected XQOrderRecordItem onQuery(Connection conn) throws Exception {
                return new XQOrderRecordTable(conn).getRecord(mOrderContext.getOrderId(), mSledContractId);
            }
        }.query();

        if (recordItem != null) {
            mDBRecordExist = true;
            mLatestUsedPrice = recordItem.getLastestUsedPrice();
        } else {
            mDBRecordExist = false;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("XQOrderSubRecorder xqOrderId=" + mOrderContext.getOrderId()
                    + ", sledContractId=" + mSledContractId
                    + ", mDBRecordExist=" + mDBRecordExist
                    + ", mLatestUsedPrice=" + mLatestUsedPrice);
        }
    }

    public void updateLatestRecordPrice(double latestUsedPrice) throws ErrorInfo {
        if (!PriceUtils.isAppropriatePrice(latestUsedPrice)) {
            return ;
        }

        if (mLatestUsedPrice != null
                && Double.compare(mLatestUsedPrice, latestUsedPrice) == 0) {
            return ;
        }

        new DBStepHelper<Void>(ArbitrageDB.Global()) {
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                XQOrderRecordItem operationItem = new XQOrderRecordItem();
                operationItem.setOrderId(mOrderContext.getOrderId());
                operationItem.setSledContractId(mSledContractId);
                operationItem.setLastestUsedPrice(latestUsedPrice);
                if (!mDBRecordExist) {
                    new XQOrderRecordTable(getConnection()).insertRecord(operationItem);
                } else {
                    new XQOrderRecordTable(getConnection()).updateRecord(operationItem);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();

        mDBRecordExist = true;
        mLatestUsedPrice = new Double(latestUsedPrice);

        if (AppLog.infoEnabled()) {
            AppLog.i("updateLatestRecordPrice xqOrderId=" + mOrderContext.getOrderId()
                    + ", sledContractId=" + mSledContractId
                    + ", mLatestUsedPrice=" + mLatestUsedPrice);
        }
    }

    public Double getLatestUsedPrice() {
        return mLatestUsedPrice;
    }
}
