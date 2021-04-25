package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import net.qihoo.qconf.QconfException;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.event.handler.SledCurrency;
import xueqiao.trade.hosting.asset.storage.*;
import xueqiao.trade.hosting.asset.storage.account.sub.*;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.events.HostingAssetGuardEventType;
import xueqiao.trade.hosting.events.HostingPositionGuardEvent;
import xueqiao.trade.hosting.events.HostingPositionVolumeEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.math.BigDecimal;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class AssetSettlementCalculator extends AssetBaseCalculator<Long> {
    public AssetSettlementCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_SETTLEMENT_KEY;
    }

    @Override
    public void onCalculate(Long sledContractId) throws Exception {
        Preconditions.checkNotNull(sledContractId);
        Preconditions.checkArgument(sledContractId > 0);
        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(this.mAccountId);
        if (contractGlobal.isContractExpired(sledContractId)) {
            return;
        }
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(this.mAccountId);
        settlementPosition(sledContractId, globalData);
        globalData.setSettlementTime(sledContractId);
    }

    public void settlementPosition(long sledContractId, AssetSubAccountGlobalData globalData) throws ErrorInfo, QconfException {

        /* 计算同一商品下雪橇合约的持仓结算 商品：合约：实时持仓：结算持仓
           计算该商品的币种对应的资金结算 币种：上次结算的资金：出入金：每个雪橇合约的持仓平仓盈亏和手续费：结算资金 */

        new DBTransactionHelperWithMsg<Void>(AssetDB.Global()) {

            SettlementFundDetailTable settlementFundDetailTable;
            SettlementPositionDetailTable settlementPositionDetailTable;
            SettlementTradeDetailTable settlementTradeDetailTable;
            SettlementFundDetail settlementFundDetail;
            List<AssetTradeDetail> assetTradeDetails;
            AssetSledContractPositionTable contractPositionTable;
            NetPositionTradeDetailTable netPositionTradeDetailTable;
            SettlementPositionDetail positionDetail;
            HostingSledContractPosition hostingPosition;
            PreSettlementFundDetailTable preSettlementFundDetailTable;
            boolean isSettlement;
            boolean isNewPre = false;
            SubAccountHostingFundTable hostingFundTable;

            @Override
            public void onPrepareData() throws Exception {

                settlementFundDetailTable = new SettlementFundDetailTable(getConnection());
                settlementPositionDetailTable = new SettlementPositionDetailTable(getConnection());
                settlementTradeDetailTable = new SettlementTradeDetailTable(getConnection());
                contractPositionTable = new AssetSledContractPositionTable(getConnection());
                netPositionTradeDetailTable = new NetPositionTradeDetailTable(getConnection());
                settlementFundDetail = new SettlementFundDetail();
                preSettlementFundDetailTable = new PreSettlementFundDetailTable(getConnection());
                assetTradeDetails = new ArrayList<>();
                hostingFundTable = new SubAccountHostingFundTable(getConnection());

                long settlementTimestampMs = System.currentTimeMillis();
                hostingPosition = contractPositionTable.query(mAccountId, sledContractId);
                if (hostingPosition == null) {
                    return;
                }

                // 上日持仓和本日持仓都是0的
                if (hostingPosition.getPositionVolume().getPrevPosition() == 0
                        && hostingPosition.getPositionVolume().getLongPosition() == 0
                        && hostingPosition.getPositionVolume().getShortPosition() == 0
                        && hostingPosition.getPositionVolume().getNetPosition() == 0) {
                    contractPositionTable.delete(mAccountId, sledContractId);
                    globalData.getHostingPositionMap().remove(sledContractId);
                    return;
                }

                BigDecimal totalCloseProfit = doubleToBigDecimal(hostingPosition.getPositionVolume().getCloseProfit());
                BigDecimal totalUseCommission = doubleToBigDecimal(hostingPosition.getPositionVolume().getUseCommission());
                BigDecimal totalUseMargin = doubleToBigDecimal(hostingPosition.getPositionFund().getUseMargin());

                positionDetail = new SettlementPositionDetail();
                positionDetail.setSubAccountId(mAccountId);
                positionDetail.setSledContractId(hostingPosition.getSledContractId());
                positionDetail.setPosition(hostingPosition.getPositionVolume().getNetPosition());
                positionDetail.setPositionAvgPrice(hostingPosition.getPositionVolume().getPositionAvgPrice());

                HostingSledContractPosition cacheHostingPosition = globalData.getHostingPositionMap().get(sledContractId);
                positionDetail.setPositionProfit(cacheHostingPosition.getPositionFund().getPositionProfit());
                positionDetail.setCalculatePrice(cacheHostingPosition.getPositionFund().getCalculatePrice());

                positionDetail.setCurrency(hostingPosition.getCurrency());
                positionDetail.setSledCommodityId(hostingPosition.getSledCommodityId());

                positionDetail.setPrevPosition(hostingPosition.getPositionVolume().getPrevPosition());
                positionDetail.setLongPosition(hostingPosition.getPositionVolume().getLongPosition());
                positionDetail.setShortPosition(hostingPosition.getPositionVolume().getShortPosition());
                positionDetail.setCloseProfit(hostingPosition.getPositionVolume().getCloseProfit());

                positionDetail.setGoodsValue(cacheHostingPosition.getPositionFund().getGoodsValue());
                positionDetail.setLeverage(cacheHostingPosition.getPositionFund().getLeverage());
                positionDetail.setUseMargin(cacheHostingPosition.getPositionFund().getUseMargin());
                positionDetail.setUseCommission(cacheHostingPosition.getPositionVolume().getUseCommission());

                List<AssetTradeDetail> netTradeDetails = netPositionTradeDetailTable.queryPositionTradeDetail(mAccountId, sledContractId);
                if (netTradeDetails != null && netTradeDetails.size() > 0) {
                    assetTradeDetails.addAll(netTradeDetails);
                }

                // reset Position
                hostingPosition.getPositionVolume().setPrevPosition(hostingPosition.getPositionVolume().getNetPosition());
                hostingPosition.getPositionVolume().setCloseProfit(0.0);
                hostingPosition.getPositionVolume().setUseCommission(0.0);
                hostingPosition.getPositionVolume().setLongPosition(0);
                hostingPosition.getPositionVolume().setShortPosition(0);

                String currency = hostingPosition.getCurrency();
                AssetSubAccountMoneyRecordTable assetSubAccountMoneyRecordTable = new AssetSubAccountMoneyRecordTable(getConnection());

                SettlementFundDetail lastSettlementFund = preSettlementFundDetailTable.query(currency, mAccountId);

                if (lastSettlementFund == null) {
                    lastSettlementFund = new SettlementFundDetail();
                    isNewPre = true;
                }
                settlementFundDetail.setSubAccountId(mAccountId);
                settlementFundDetail.setCurrency(currency);
                settlementFundDetail.setSettlementTimestamp(settlementTimestampMs);
                long historyId = getExchangeRateHistoryId();
                settlementFundDetail.setExchangeRateHistoryId(historyId);

                // 查询子账号在上一次结算之后的的出入金情况
                BigDecimal depositAmount = BigDecimal.ZERO;
                BigDecimal withdrawAmount = BigDecimal.ZERO;

                HostingFund lastSaveFund = hostingFundTable.queryLatest(mAccountId, currency, false);
                if (lastSaveFund == null) {
                    lastSaveFund = new HostingFund();
                }

                ReqMoneyRecordOption reqOption = new ReqMoneyRecordOption();

                reqOption.setStartTimestampMs(lastSaveFund.getCreateTimestampMs());
                reqOption.setSubAccountId(mAccountId);
                reqOption.setCurrency(currency);
                PageOption pageOption = new PageOption();
                pageOption.setPageIndex(0);
                pageOption.setPageSize(Integer.MAX_VALUE);
                PageResult<HostingSubAccountMoneyRecord> recordPage = assetSubAccountMoneyRecordTable.query(reqOption, pageOption);
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
                settlementFundDetail.setCloseProfit(totalCloseProfit.doubleValue());
                settlementFundDetail.setUseCommission(totalUseCommission.doubleValue());
                settlementFundDetail.setUseMargin(cacheHostingPosition.getPositionFund().getUseMargin());
                settlementFundDetail.setDepositAmount(depositAmount.doubleValue());
                settlementFundDetail.setWithdrawAmount(withdrawAmount.doubleValue());

                BigDecimal balance = doubleToBigDecimal(lastSettlementFund.getBalance()).add(totalCloseProfit).subtract(totalUseCommission);
                settlementFundDetail.setBalance(balance.doubleValue());

                settlementFundDetail.setGoodsValue(cacheHostingPosition.getPositionFund().getGoodsValue());
                settlementFundDetail.setLeverage(cacheHostingPosition.getPositionFund().getLeverage());

                recycle(depositAmount);
                recycle(withdrawAmount);
                recycle(totalCloseProfit);
                recycle(totalUseCommission);
                recycle(totalUseMargin);
                recycle(balance);

                isSettlement = true;
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
            public void onUpdate() throws Exception {
                if (isSettlement) {
                    long settlementId = settlementPositionDetailTable.add(positionDetail);
                    settlementFundDetail.setSettlementId(settlementId);
                    if (isNewPre) {
                        preSettlementFundDetailTable.add(settlementFundDetail);
                    } else {
                        preSettlementFundDetailTable.update(settlementFundDetail);
                    }

                    settlementTradeDetailTable.batAdd(assetTradeDetails, settlementId);
                    settlementFundDetailTable.add(settlementFundDetail);
                    // 结算后总持仓是0
                    if (hostingPosition.getPositionVolume().getPrevPosition() == 0
                            && hostingPosition.getPositionVolume().getNetPosition() == 0) {
                        contractPositionTable.delete(mAccountId, sledContractId);
                        globalData.getHostingPositionMap().remove(sledContractId);
                    } else {
                        contractPositionTable.update(hostingPosition);
                        globalData.getHostingPositionMap().put(sledContractId, hostingPosition);
                    }
                    AppLog.w("settlement contract: " + sledContractId + " sub account: " + mAccountId);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                HostingPositionGuardEvent event = new HostingPositionGuardEvent();
                event.setSledContractId(sledContractId);
                event.setType(HostingAssetGuardEventType.HOSTING_POSITION_CHANGED_GUARD);
                event.setSubAccountId(mAccountId);
                return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(event
                        , new TimeoutGuardPolicy().setTimeoutSeconds(2));
            }

            @Override
            protected MessageAgent getMessageAgent() {
                return HostingMessageContext.Global().getSenderAgent();
            }

            @Override
            protected TBase prepareNotifyMessage() {
                HostingPositionVolumeEvent event = new HostingPositionVolumeEvent();
                event.setEventCreateTimestampMs(System.currentTimeMillis());
                HostingPositionVolume positionVolume;
                if (isSettlement) {
                    positionVolume = hostingPosition.getPositionVolume();
                } else {
                    positionVolume = new HostingPositionVolume().setSubAccountId(mAccountId).setSledContractId(sledContractId);
                }

                event.setPositionVolume(positionVolume);
                return event;
            }
        }.execute();
    }
}
