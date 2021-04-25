package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.antiy.error_code.ErrorCodeOuter;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.quotation.AssetQuotationHelper;
import xueqiao.trade.hosting.asset.storage.*;
import xueqiao.trade.hosting.asset.storage.account.sub.AssetSledContractPositionTable;
import xueqiao.trade.hosting.asset.storage.account.AssetTradeDetailTable;
import xueqiao.trade.hosting.asset.storage.account.sub.NetPositionTradeDetailTable;
import xueqiao.trade.hosting.asset.struct.FundCalculateData;
import xueqiao.trade.hosting.asset.struct.OpenCloseTag;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.asset.struct.TradePosition;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.events.*;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AssetPositionCalculator extends AssetBaseCalculator<AssetTradeDetail> {

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_POSITION_KEY;
    }

    public AssetPositionCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public void onCalculate(AssetTradeDetail tradeDetail) throws Exception {

        new DBTransactionHelperWithMsg<Void>(AssetDB.Global()) {

            private AssetSledContractPositionTable table;
            private HostingSledContractPosition oldPosition;
            private boolean isNew;
            private HostingSledContractPosition newPosition = new HostingSledContractPosition();
            private AssetSubAccountGlobalData data;


            @Override
            public void onPrepareData() throws Exception {
                // 持仓计算流程
                try {
                    data = AssetGlobalDataFactory.getInstance().getAssetGlobalData(mAccountId);
                    table = new AssetSledContractPositionTable(getConnection());
                    oldPosition = table.query(tradeDetail.getSubAccountId(), tradeDetail.getSledContractId());
                    if (oldPosition == null) {
                        oldPosition = data.getHostingSledContractPosition(tradeDetail.getSledContractId());
                        isNew = true;
                    }

                    AssetCalculateConfig calculateConfig;
                    if (tradeDetail.isSetConfig()) {
                        calculateConfig = tradeDetail.getConfig();
                    } else {
                        calculateConfig = data.getAssetCalculateConfig(tradeDetail.getSledContractId());
                    }

//                AppLog.i("old position: " + oldPosition);
                    TradePosition tradePosition = calculateSledContractPosition(tradeDetail, oldPosition, getConnection(), calculateConfig);
//                AppLog.i("tradePosition: " + tradePosition);
                    newPosition.setSubAccountId(tradeDetail.getSubAccountId());
                    newPosition.setSledContractId(tradePosition.getSledContractId());
                    newPosition.setSledCommodityId(tradeDetail.getSledCommodityId());

                    HostingPositionVolume positionVolume = new HostingPositionVolume();
                    positionVolume.setLongPosition(tradePosition.getLongPosition());
                    positionVolume.setShortPosition(tradePosition.getShortPosition());
                    positionVolume.setNetPosition(tradePosition.getNetPosition());
                    positionVolume.setPositionAvgPrice(tradePosition.getAveragePrice().doubleValue());
                    positionVolume.setCloseProfit(tradePosition.getCloseProfit().doubleValue());
                    positionVolume.setPrevPosition(oldPosition.getPositionVolume().getPrevPosition());
                    positionVolume.setUseCommission(tradePosition.getUseCommission().doubleValue());
                    positionVolume.setSubAccountId(mAccountId);
                    positionVolume.setCurrency(oldPosition.getCurrency());
                    positionVolume.setSledContractId(tradeDetail.getSledContractId());

                    newPosition.setPositionVolume(positionVolume);
                    newPosition.setCurrency(oldPosition.getCurrency());
                    newPosition.setPositionFund(oldPosition.getPositionFund());
                    setMargin(newPosition, calculateConfig);
//                AppLog.i("HostingSledContractPosition after : " + newPosition);

                    double contractSize = calculateConfig.getContractSize();
                    double chargeUnit = calculateConfig.getChargeUnit();
                    BigDecimal contractSizePrice = doubleToBigDecimal(contractSize).multiply(doubleToBigDecimal(chargeUnit));
                    BigDecimal goodsValue = tradePosition.getAveragePrice().multiply(contractSizePrice);

                    ExchangeRateHistory history = getExchangeRateHistory();
                    BigDecimal currencyRate = BigDecimal.ONE;
                    if (history != null) {
                        currencyRate = getCurrencyRate(history, history.getCurrencyCode(), oldPosition.getCurrency());
                    }

                    newPosition.getPositionFund().setGoodsValue(goodsValue.doubleValue());

                    HostingFund hostingFund = data.getBasicCurrencyFundMap().get(history.getCurrencyCode());
                    BigDecimal dynamicBenefit = BigDecimal.ZERO;
                    if (hostingFund != null) {
                        dynamicBenefit = doubleToBigDecimal(hostingFund.getDynamicBenefit());
                    }

                    BigDecimal leverage = BigDecimal.ZERO;
                    if (goodsValue.doubleValue() > 0.0 && dynamicBenefit.compareTo(BigDecimal.ZERO) != 0) {
                        leverage = goodsValue.multiply(currencyRate).divide(dynamicBenefit, 4, BigDecimal.ROUND_DOWN);
                    }
                    newPosition.getPositionFund().setLeverage(leverage.doubleValue());
                } catch (Exception e) {
                    AppLog.e(e.getMessage(), e);
                    throw e;
                }
            }

            private void setTestData() throws ErrorInfo {
                // 为了创建重入点时，具备遗留数据做测试
                throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "set this fail to create test data");
            }

            @Override
            public void onUpdate() throws Exception {
                try {
                    if (isNew) {
                        table.add(newPosition);
                    } else {
                        table.update(newPosition);
                    }

                    AssetTradeDetailTable tradeDetailTable = new AssetTradeDetailTable(getConnection());
                    tradeDetailTable.delete(tradeDetail.getAssetTradeDetailId(), tradeDetail.getExecTradeId());
                    data.getHostingPositionMap().put(tradeDetail.getSledContractId(), newPosition);
                    data.setSettlementTime(tradeDetail.getSledContractId());
                } catch (Exception e) {
                    AppLog.e(e.getMessage(), e);
                    throw e;
                }
            }

            @Override
            public Void getResult() {
                return null;
            }

            @Override
            protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
                HostingPositionGuardEvent event = new HostingPositionGuardEvent();
                event.setSledContractId(tradeDetail.getSledContractId());
                event.setType(HostingAssetGuardEventType.HOSTING_POSITION_CHANGED_GUARD);
                event.setSubAccountId(tradeDetail.getSubAccountId());
//                AppLog.d("prepareGuardMessage: " + event);
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
                HostingPositionVolume positionVolume = newPosition.getPositionVolume();
                event.setPositionVolume(positionVolume);
//                AppLog.d("prepareNotifyMessage: " + event);
                return event;
            }
        }.execute();
        AppLog.d("calculate position done ");
        onPositionChanged(tradeDetail);
    }

    private void onPositionChanged(AssetTradeDetail tradeDetail) {
        try {
            AssetQuotationHelper.registerQuotation(tradeDetail.getSubAccountId(), tradeDetail.getSledContractId());
            PositionFundCalculateData data = new PositionFundCalculateData();
            data.setSledContractId(tradeDetail.getSledContractId());
            data.setSledCommodityId(tradeDetail.getSledCommodityId());
            BigDecimal price = new BigDecimal(String.valueOf(tradeDetail.getTradePrice()));
            data.setCalculatePrice(price);
            ExecutorHandler.calculateFrozenFund(data, mAccountId);

            FundCalculateData calculateData = new FundCalculateData();
            calculateData.setSubAccountId(mAccountId);
            SledCommodity sledCommodity = AssetGlobalDataFactory.getInstance()
                    .getContractGlobalData(mAccountId)
                    .getSledCommodity(tradeDetail.getSledCommodityId());
            calculateData.setCurrency(sledCommodity.getTradeCurrency());
            ExecutorHandler.calculateFund(calculateData);

        } catch (ErrorInfo errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    private TradePosition calculateSledContractPosition(AssetTradeDetail detail,
                                                        HostingSledContractPosition oldPosition,
                                                        Connection connection,
                                                        AssetCalculateConfig calculateConfig) throws TException, SQLException {

        TradePosition newPosition = new TradePosition();
        newPosition.setSledContractId(detail.getSledContractId());
        double contractSize = calculateConfig.getContractSize();
        double chargeUnit = calculateConfig.getChargeUnit();
        BigDecimal contractSizePrice = doubleToBigDecimal(contractSize).multiply(doubleToBigDecimal(chargeUnit));

        boolean isTradeBuy = HostingExecTradeDirection.TRADE_BUY.equals(detail.getExecTradeDirection());

        if (isTradeBuy) {
            newPosition.setLongPosition(oldPosition.getPositionVolume().getLongPosition() + detail.getTradeVolume());
            newPosition.setShortPosition(oldPosition.getPositionVolume().getShortPosition());
        } else {
            newPosition.setShortPosition(oldPosition.getPositionVolume().getShortPosition() + detail.getTradeVolume());
            newPosition.setLongPosition(oldPosition.getPositionVolume().getLongPosition());
        }

        NetPositionTradeDetailTable netPositionTradeDetailTable = new NetPositionTradeDetailTable(connection);
        List<AssetTradeDetail> netPositionTradeDetails = netPositionTradeDetailTable.queryPositionTradeDetail(mAccountId, oldPosition.getSledContractId());


        if (netPositionTradeDetails == null) {
            netPositionTradeDetails = new ArrayList<>();
        }

        newPosition.setCloseProfit(doubleToBigDecimal(oldPosition.getPositionVolume().getCloseProfit()));

        OpenCloseTag openCloseTag;

        if (netPositionTradeDetails.size() == 0) {
            netPositionTradeDetails.add(detail);
            newPosition.setNetPositionTradeDetails(netPositionTradeDetails);
            netPositionTradeDetailTable.add(detail);
            openCloseTag = OpenCloseTag.OPEN;
        } else {
            boolean netTradeBuy = HostingExecTradeDirection.TRADE_BUY.equals(netPositionTradeDetails.get(0).getExecTradeDirection());

            if (isTradeBuy == netTradeBuy) {
                // 加仓
                netPositionTradeDetails.add(detail);
                newPosition.setNetPositionTradeDetails(netPositionTradeDetails);
                AppLog.d("加仓: ");
                netPositionTradeDetailTable.add(detail);
                openCloseTag = OpenCloseTag.OPEN;
            } else {
                // 平仓
                newPosition = matchPosition(detail, isTradeBuy, netPositionTradeDetails, newPosition, contractSizePrice);
                AppLog.d("平仓: ");
                netPositionTradeDetailTable.batDelete(mAccountId, oldPosition.getSledContractId());
                netPositionTradeDetailTable.batAdd(newPosition.getNetPositionTradeDetails());
                openCloseTag = OpenCloseTag.CLOSE;
            }
        }

        long prePosition = oldPosition.getPositionVolume().getPrevPosition();
        newPosition.setNetPosition(prePosition + newPosition.getLongPosition() - newPosition.getShortPosition());

        calculatePositionAvgPrice(newPosition);
        int commissionPosition = (int) (newPosition.getLongPosition() + newPosition.getShortPosition());

        // 需要处理部分平仓，部分开仓的情况
        BigDecimal thisTradeCommission;
        if (OpenCloseTag.CLOSE.equals(openCloseTag) && commissionPosition > Math.abs(prePosition)) {
            int closeVolume = (int) Math.abs(prePosition);
            int openVolume = commissionPosition - closeVolume;
            BigDecimal closeTradeCommission = calculateCommission(calculateConfig, doubleToBigDecimal(detail.getTradePrice()), closeVolume, OpenCloseTag.CLOSE);
            BigDecimal openTradeCommission = calculateCommission(calculateConfig, doubleToBigDecimal(detail.getTradePrice()), openVolume, OpenCloseTag.OPEN);
            thisTradeCommission = closeTradeCommission.add(openTradeCommission);
        } else {
            thisTradeCommission = calculateCommission(calculateConfig, doubleToBigDecimal(detail.getTradePrice()), commissionPosition, openCloseTag);
        }

        newPosition.setUseCommission(thisTradeCommission);

        recycle(contractSizePrice);
        return newPosition;
    }

    /* 计算 持仓均价 = Sum（成交价*成交量）/abs（净仓） */
    private void calculatePositionAvgPrice(TradePosition newPosition) {
        BigDecimal sum = BigDecimal.ZERO;
        for (AssetTradeDetail assetTradeDetail : newPosition.getNetPositionTradeDetails()) {
            sum = sum.add(doubleToBigDecimal(assetTradeDetail.getTradePrice()).multiply(intToBigDecimal(assetTradeDetail.getTradeVolume())));
        }
        if (newPosition.getNetPosition() != 0) {
            newPosition.setAveragePrice(sum.divide(longToBigDecimal(Math.abs(newPosition.getNetPosition())), 4, BigDecimal.ROUND_HALF_UP));
        } else {
            newPosition.setAveragePrice(BigDecimal.ZERO);
        }
    }

    private TradePosition matchPosition(AssetTradeDetail detail, boolean isTradeBuy,
                                        List<AssetTradeDetail> netPositionTradeDetails, TradePosition newPosition,
                                        BigDecimal contractSizePrice) {
        LinkedList<AssetTradeDetail> linkedList = new LinkedList();
        linkedList.addAll(netPositionTradeDetails);
        BigDecimal closeProfit = BigDecimal.ZERO;

        int volumeLeft = detail.getTradeVolume();
        do {
            if (linkedList.size() == 0) {
                detail.setTradeVolume(volumeLeft);
                linkedList.addFirst(detail);
                break;
            }
            AssetTradeDetail first = linkedList.pop();
            int delta = first.getTradeVolume() - volumeLeft;
            BigDecimal priceDelta;
            if (isTradeBuy) {
                priceDelta = doubleToBigDecimal(first.getTradePrice()).subtract(doubleToBigDecimal(detail.getTradePrice()));
            } else {
                priceDelta = doubleToBigDecimal(detail.getTradePrice()).subtract(doubleToBigDecimal(first.getTradePrice()));
            }

            if (delta == 0) {
                closeProfit = closeProfit.add(priceDelta.multiply(doubleToBigDecimal(volumeLeft)));
                break;
            } else if (delta > 0) {
                first.setTradeVolume(delta);
                linkedList.addFirst(first);
                closeProfit = closeProfit.add(priceDelta.multiply(doubleToBigDecimal(volumeLeft)));
                break;
            } else {
                closeProfit = closeProfit.add(priceDelta.multiply(doubleToBigDecimal(first.getTradeVolume())));
                volumeLeft = 0 - delta;
            }
            recycle(priceDelta);
        } while (true);

        netPositionTradeDetails.clear();
        netPositionTradeDetails.addAll(linkedList);
        newPosition.setCloseProfit(newPosition.getCloseProfit().add(closeProfit.multiply(contractSizePrice)));

        recycle(closeProfit);
        newPosition.setNetPositionTradeDetails(netPositionTradeDetails);
        return newPosition;
    }
}
