package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import net.qihoo.qconf.QconfException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.event.handler.SledCurrency;
import xueqiao.trade.hosting.asset.storage.AssetDB;
import xueqiao.trade.hosting.asset.storage.account.sub.*;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.math.BigDecimal;
import java.util.Map;

public class AssetSaveHostingFundCalculator extends AssetBaseCalculator<Void> {
    public AssetSaveHostingFundCalculator(long accountId) {
        super(accountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_SAVE_HOSTING_FUND_KEY;
    }

    @Override
    public void onCalculate(Void aVoid) throws Exception {

        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(mAccountId);
        Map<String, HostingFund> map = globalData.getHostingFundMap();
        Map<String, HostingFund> basicMap = globalData.getBasicCurrencyFundMap();

        long settlementTimestampMs = System.currentTimeMillis();

        try {
            new DBTransactionHelper<Void>(AssetDB.Global()) {

                SettlementFundDetailTable settlementFundDetailTable;
                SubAccountHostingFundTable hostingFundTable;
                PreSettlementFundDetailTable preSettlementFundDetailTable;


                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                    hostingFundTable = new SubAccountHostingFundTable(getConnection());
                    settlementFundDetailTable = new SettlementFundDetailTable(getConnection());
                    preSettlementFundDetailTable = new PreSettlementFundDetailTable(getConnection());
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    for (HostingFund hostingFund : map.values()) {
                        boolean isNewPre = false;
                        String currency = hostingFund.getCurrency();
                        AppLog.w("currency: " + currency);
                        HostingFund lastSaveFund = hostingFundTable.queryLatest(mAccountId, currency, false);
                        AppLog.w("lastSaveFund: " + lastSaveFund);
                        if (lastSaveFund == null) {
                            lastSaveFund = new HostingFund();
                        }

                        hostingFundTable.add(hostingFund, false);

                        SettlementFundDetail lastSettlementFund = preSettlementFundDetailTable.query(currency, mAccountId);
                        if (lastSettlementFund == null) {
                            lastSettlementFund = new SettlementFundDetail();
                            isNewPre = true;
                        }
                        AppLog.w(lastSettlementFund);
                        SettlementFundDetail settlementFundDetail = new SettlementFundDetail();
                        settlementFundDetail.setSubAccountId(mAccountId);
                        settlementFundDetail.setCurrency(hostingFund.getCurrency());

                        settlementFundDetail.setSettlementTimestamp(settlementTimestampMs);
                        long historyId = getExchangeRateHistoryId();
                        settlementFundDetail.setExchangeRateHistoryId(historyId);

                        // 查询子账号在上一次结算之后的的出入金情况
                        BigDecimal depositAmount = BigDecimal.ZERO;
                        BigDecimal withdrawAmount = BigDecimal.ZERO;
                        ReqMoneyRecordOption reqOption = new ReqMoneyRecordOption();
                        reqOption.setStartTimestampMs(lastSaveFund.getCreateTimestampMs());
                        reqOption.setSubAccountId(mAccountId);
                        reqOption.setCurrency(currency);
                        PageOption pageOption = new PageOption();
                        pageOption.setPageIndex(0);
                        pageOption.setPageSize(Integer.MAX_VALUE);
                        AppLog.w("reqOption : " + reqOption);
                        AssetSubAccountMoneyRecordTable assetSubAccountMoneyRecordTable = new AssetSubAccountMoneyRecordTable(getConnection());
                        PageResult<HostingSubAccountMoneyRecord> recordPage = assetSubAccountMoneyRecordTable.query(reqOption, pageOption);
                        AppLog.w("records : " + recordPage.getPageList());
                        for (HostingSubAccountMoneyRecord record : recordPage.getPageList()) {
                            if (HostingSubAccountMoneyRecordDirection.DEPOSIT.equals(record.getDirection())) {
                                depositAmount = depositAmount.add(doubleToBigDecimal(record.getTotalAmount()));
                            } else {
                                withdrawAmount = withdrawAmount.add(doubleToBigDecimal(record.getTotalAmount()));
                            }
                        }

                        settlementFundDetail.setSubAccountId(mAccountId);
                        settlementFundDetail.setPreFund(lastSettlementFund.getBalance());
                        settlementFundDetail.setCurrency(currency);
                        settlementFundDetail.setCloseProfit(lastSettlementFund.getCloseProfit());
                        settlementFundDetail.setUseCommission(hostingFund.getUseCommission());
                        settlementFundDetail.setUseMargin(hostingFund.getUseMargin());
                        settlementFundDetail.setDepositAmount(depositAmount.doubleValue());
                        settlementFundDetail.setWithdrawAmount(withdrawAmount.doubleValue());

                        BigDecimal balance = doubleToBigDecimal(lastSettlementFund.getBalance()).add(depositAmount).subtract(withdrawAmount);

                        settlementFundDetail.setBalance(balance.doubleValue());

                        long settlementId = SettlementPositionDetailTable.getSettlementId();
                        settlementFundDetail.setSettlementId(settlementId);
                        settlementFundDetail.setGoodsValue(hostingFund.getGoodsValue());
                        settlementFundDetail.setLeverage(hostingFund.getLeverage());

                        AppLog.w("settlementFundDetail :" + settlementFundDetail);

                        if (isNewPre) {
                            preSettlementFundDetailTable.add(settlementFundDetail);
                        } else {
                            preSettlementFundDetailTable.update(settlementFundDetail);
                        }

                        settlementFundDetailTable.add(settlementFundDetail);
                    }

                    for (HostingFund hostingFund : basicMap.values()) {
                        hostingFundTable.add(hostingFund, true);
                    }
                }

                private BigDecimal doubleToBigDecimal(double x) {
                    if (Double.isNaN(x)) {
                        x = 0.0;
                    }
                    return new BigDecimal(Double.toString(x));
                }

                private long getExchangeRateHistoryId() throws QconfException {
                    SledCurrency sledCurrency = new SledCurrency();
                    ExchangeRateHistoryPage page = sledCurrency.getSledExchangeRate();
                    long historyId = 0;
                    if (page != null) {
                        historyId = page.getPage().get(0).getHistoryId();
                    }
                    return historyId;
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();

            for (String currency : map.keySet()) {
                FundCalculateData fundCalculateData = new FundCalculateData();
                fundCalculateData.setSubAccountId(mAccountId);
                fundCalculateData.setCurrency(currency);
                ExecutorHandler.calculateFund(fundCalculateData);
            }
        } catch (Exception e) {
            AppLog.e(e.getMessage(), e);
        }

    }
}
