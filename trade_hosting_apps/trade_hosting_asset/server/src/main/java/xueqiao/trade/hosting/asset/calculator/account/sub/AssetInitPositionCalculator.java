package xueqiao.trade.hosting.asset.calculator.account.sub;

import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.SettleTimeCalculator;
import xueqiao.trade.hosting.asset.quotation.AssetQuotationHelper;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.SettlementPositionDetailTable;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.asset.thriftapi.SettlementPositionDetail;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

import java.util.Calendar;
import java.util.Map;

public class AssetInitPositionCalculator extends AssetBaseCalculator<HostingSledContractPosition> {
    public AssetInitPositionCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_INIT_POSITION_KEY;
    }

    @Override
    public void onCalculate(HostingSledContractPosition position) throws Exception {
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(position.getSubAccountId());
        Map<Long, HostingSledContractPosition> positionMap = globalData.getHostingPositionMap();
        HostingSledContractPosition cachePosition = positionMap.get(position.getSledContractId());
        if (cachePosition != null) {
            position.setPositionFund(cachePosition.getPositionFund());
        }
        globalData.getHostingPositionMap().put(position.getSledContractId(), position);

        completeSettlement(position, globalData);
        globalData.setSettlementTime(position.getSledContractId());
        AssetQuotationHelper.registerQuotation(position.getSubAccountId(), position.getSledContractId());
    }

    private void completeSettlement(HostingSledContractPosition position, AssetSubAccountGlobalData globalData) throws TException {
        long now = System.currentTimeMillis();
        long sledCommodityId = AssetGlobalDataFactory.getInstance().getContractGlobalData(mAccountId).getCommodityIdByContractId(position.getSledContractId());
        IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        TradingTimeAnalysisor analysisor = new TradingTimeAnalysisor(contractApi);
        analysisor.setCommodityId(sledCommodityId);
        analysisor.setProcessTimestampMs(now);
        AppLog.d("begin analysisor trade time: ");
        try {
            if (analysisor.analysis()) {
                AppLog.d("analysisor trade time success ");
                SettleTimeCalculator cal = new SettleTimeCalculator(analysisor);
                if (cal.isMarketClosed()) {
                    new DBTransactionHelper<Void>(AssetDB.Global()) {
                        @Override
                        public void onPrepareData() throws ErrorInfo, Exception {
                            try {
                                SettlementPositionDetailTable settlementPositionDetailTable = new SettlementPositionDetailTable(getConnection());
                                SettlementPositionDetail detail = settlementPositionDetailTable.queryLatest(mAccountId, position.getSledContractId());
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.HOUR, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                if (detail == null || detail.getCreateTimestampMs() < calendar.getTimeInMillis()) {
                                    globalData.setSettlementTime(position.getSledContractId(), 10000);
                                }
                            } catch (Exception e) {
                                AppLog.e(e.getMessage(), e);
                                throw e;
                            }
                        }

                        @Override
                        public void onUpdate() throws ErrorInfo, Exception {

                        }

                        @Override
                        public Void getResult() {
                            return null;
                        }
                    }.execute();
                }
            }
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
        }
    }
}
