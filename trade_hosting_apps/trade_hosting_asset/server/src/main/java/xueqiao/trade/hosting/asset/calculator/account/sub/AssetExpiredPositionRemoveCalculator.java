package xueqiao.trade.hosting.asset.calculator.account.sub;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.actor.AssetActorFactory;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.quotation.AssetQuotationHelper;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSledContractPositionTable;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;

import java.util.Map;

public class AssetExpiredPositionRemoveCalculator extends AssetBaseCalculator<Long> {
    public AssetExpiredPositionRemoveCalculator(long accountId) {
        super(accountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_EXPIRED_POSITION_REMOVE_KEY;
    }

    @Override
    public void onCalculate(Long sledContractId) throws Exception {
        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(this.mAccountId);
        if (contractGlobal == null) {
            return;
        }
        if (contractGlobal.isContractExpired(sledContractId)) {
            new DBTransactionHelper<Void>(AssetDB.Global()) {
                @Override
                public void onPrepareData() throws ErrorInfo, Exception {

                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    AssetSledContractPositionTable table = new AssetSledContractPositionTable(getConnection());
                    table.delete(mAccountId, sledContractId);
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();

            AssetActorFactory.getInstance().removeAssetActor(sledContractId, AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY, mAccountId);
            AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(this.mAccountId);
            Map<Long, HostingSledContractPosition> hostingPositions = globalData.getHostingPositionMap();
            if (hostingPositions != null) {
                hostingPositions.remove(sledContractId);
            }

            super.sendPositionDeleteMsg(sledContractId);
        }
    }
}
