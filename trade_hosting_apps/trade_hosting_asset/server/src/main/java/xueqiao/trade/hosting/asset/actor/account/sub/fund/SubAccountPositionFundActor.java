package xueqiao.trade.hosting.asset.actor.account.sub.fund;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.asset.actor.AssetActorFactory;
import xueqiao.trade.hosting.asset.actor.IAssetActor;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.quotation.AssetABQuotationListener;
import xueqiao.trade.hosting.asset.quotation.ReduceQuotation;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.asset.utils.CalculatorUtils;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;

import java.math.BigDecimal;

public class SubAccountPositionFundActor extends AssetABQuotationListener implements IAssetActor {

    private TaskThread mTaskThread;
    private AssetBaseCalculator mExecutor;
    private long mSledContractId;
    private String symbol;
    private String platform;

    @Override
    public void onCreate(AssetBaseCalculator calculator) throws ErrorInfo {
        mExecutor = calculator;
        mTaskThread = calculator.getmContext().getmTaskThread();
    }

    @Override
    public TaskThread getTaskThread() {
        return mTaskThread;
    }

    @Override
    public void onHandleQuotationItem(QuotationItem quotationItem) throws Exception {
        if (!quotationItem.isSetLastPrice()) {
            AppLog.d("quotation last price zero: " + quotationItem);
            return;
        }
        symbol = quotationItem.getContractSymbol();
        platform = quotationItem.getPlatform();
        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance()
                .getContractGlobalData(this.mExecutor.getAccountId());
        mSledContractId = contractGlobal.getContractIdBySymbol(symbol, platform);
        long sledCommodityId = contractGlobal.getCommodityIdByContractId(mSledContractId);
        contractGlobal.setLatestQuotationMap(mSledContractId, quotationItem);
        //  降频
        if (ReduceQuotation.getInstance().isReduce(quotationItem)) {
            return;
        }

        PositionFundCalculateData calculateData = new PositionFundCalculateData();
        calculateData.setSledCommodityId(sledCommodityId);
        calculateData.setSledContractId(mSledContractId);
        calculateData.setCalculatePrice(doubleToBigDecimal(quotationItem.getLastPrice()));
        mExecutor.onCalculate(calculateData);

        AssetSubAccountGlobalData data = AssetGlobalDataFactory.getInstance().getAssetGlobalData(mExecutor.getAccountId());
        HostingSledContractPosition position = data.getHostingSledContractPosition(mSledContractId);
        if (0 == position.getPositionVolume().getNetPosition()) {
            onDestroy();
            return;
        }
    }

    @Override
    public void onDestroy() throws ErrorInfo {
        AppLog.i("unRegister: " + this.mSledContractId);
        QuotationDispatcher.Global().unRegisterListener(platform, symbol, this);
    }
}
