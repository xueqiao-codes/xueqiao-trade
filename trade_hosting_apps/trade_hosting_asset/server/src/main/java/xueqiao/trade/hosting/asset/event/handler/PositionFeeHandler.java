package xueqiao.trade.hosting.asset.event.handler;

import com.antiy.error_code.ErrorCodeOuter;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityConfig;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import net.qihoo.qconf.QconfException;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.calculator.account.sub.AssetCalculateConfigCalculator;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.AssetTradeHandler;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.thriftapi.AssetCalculateConfig;
import xueqiao.trade.hosting.asset.thriftapi.CommissionFee;
import xueqiao.trade.hosting.asset.thriftapi.Margin;
import xueqiao.trade.hosting.position.fee.helper.PositionFeeStubFactory;
import xueqiao.trade.hosting.position.fee.thriftapi.CommissionInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.MarginInfo;
import xueqiao.trade.hosting.position.fee.thriftapi.PositionFee;
import xueqiao.trade.hosting.position.fee.thriftapi.client.TradeHostingPositionFeeStub;

import java.util.Map;

public class PositionFeeHandler {

    public static IMessageConsumer.ConsumeResult onChange(long subAccountId, long sledContractId) {
        AssetCalculateConfigCalculator calculator = null;
        try {
            calculator = (AssetCalculateConfigCalculator) AssetCalculatorFactory.getInstance()
                    .getCalculator(AssetCalculatorFactory.SUB_ACCOUNT_CALCULATE_CONFIG_KEY, subAccountId);
        } catch (ErrorInfo errorInfo) {
            AppLog.e(errorInfo.getMessage(), errorInfo);
        }
        if (calculator == null) {
            AppLog.e("calculator is not effective for subAccountId=" + subAccountId);
            return IMessageConsumer.ConsumeResult.CONSUME_RETRY;
        }
        calculator.addTask(sledContractId);
        return IMessageConsumer.ConsumeResult.CONSUME_OK;
    }

    public static AssetCalculateConfig queryAssetCalculateConfig(long subAccountId, long sledContractId) throws TException {

        PositionFee fee = reqPositionFee(subAccountId, sledContractId);
        ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(subAccountId);
        long sledCommodityId = contractGlobal.getCommodityIdByContractId(sledContractId);

        SledCommodity sledCommodity = AssetTradeHandler.getSledCommodity(sledCommodityId);
        SledCommodityConfig sledCommodityConfig = AssetTradeHandler.getSledCommodityConfig(sledCommodity);
        AssetCalculateConfig assetCalculateConfig = new AssetCalculateConfig();
        assetCalculateConfig.setSledCommodityId(sledCommodityId);
        assetCalculateConfig.setCurrency(sledCommodity.getTradeCurrency());
        assetCalculateConfig.setContractSize(sledCommodity.getContractSize());
        assetCalculateConfig.setChargeUnit(sledCommodityConfig.getChargeUnit());
        String sledCurrency = sledCommodity.getTradeCurrency();

        Margin margin = new Margin();
        if (fee.isSetMargin()) {
            MarginInfo info = fee.getMargin();
            double currencyRate = getCurrencyRate(sledCurrency, info.getCurrency());
            margin.setCurrency(info.getCurrency());
            margin.setCurrencyGroup(info.getCurrencyGroup());
            margin.setCurrencyRate(currencyRate);

            if (info.isSetLongMarginRatioByMoney()) {
                margin.setLongMarginRatioByMoney(info.getLongMarginRatioByMoney());
            }
            if (info.isSetLongMarginRatioByVolume()) {
                margin.setLongMarginRatioByVolume(info.getLongMarginRatioByVolume());
            }
            if (info.isSetShortMarginRatioByMoney()) {
                margin.setShortMarginRatioByMoney(info.getShortMarginRatioByMoney());
            }
            if (info.isSetShortMarginRatioByVolume()) {
                margin.setShortMarginRatioByVolume(info.getShortMarginRatioByVolume());
            }
        }
        assetCalculateConfig.setMargin(margin);

        CommissionFee commissionFee = new CommissionFee();
        if (fee.isSetCommission()) {
            CommissionInfo info = fee.getCommission();
            commissionFee.setCurrency(info.getCurrency());
            commissionFee.setCurrencyGroup(info.getCurrencyGroup());
            double currencyRate = getCurrencyRate(sledCurrency, info.getCurrency());
            commissionFee.setCurrencyRate(currencyRate);

            if (info.isSetOpenRatioByMoney()) {
                commissionFee.setOpenRatioByMoney(info.getOpenRatioByMoney());
            }
            if (info.isSetOpenRatioByVolume()) {
                commissionFee.setOpenRatioByVolume(info.getOpenRatioByVolume());
            }
            if (info.isSetCloseRatioByMoney()) {
                commissionFee.setCloseRatioByMoney(info.getCloseRatioByMoney());
            }
            if (info.isSetCloseRatioByVolume()) {
                commissionFee.setCloseRatioByVolume(info.getCloseRatioByVolume());
            }
            if (info.isSetCloseTodayRatioByMoney()) {
                commissionFee.setCloseTodayRatioByMoney(info.getCloseTodayRatioByMoney());
            }
            if (info.isSetCloseTodayRatioByVolume()) {
                commissionFee.setCloseTodayRatioByVolume(info.getCloseTodayRatioByVolume());
            }
        }

        assetCalculateConfig.setCommissionFee(commissionFee);

        return assetCalculateConfig;
    }

    private static double getCurrencyRate(String sledCurrency, String currency) {
        ExchangeRateHistory history = getExchangeRateHistory();
        if (!sledCurrency.equals(currency)) {

            Map<String, Double> rateMap = history.getExchangeRate();
            if (rateMap == null) {
                return 1;
            }

            Double rate = rateMap.get(currency);
            if (rate == null) {
                return 1;
            }
            return rate;
        }
        return 1;
    }

    private static ExchangeRateHistory getExchangeRateHistory() {
        SledCurrency sledCurrency = new SledCurrency();
        ExchangeRateHistoryPage page = null;
        try {
            page = sledCurrency.getSledExchangeRate();
        } catch (QconfException e) {
            AppLog.e(e.getMessage(), e);
        }
        if (page == null || page.getPageSize() == 0) {
            return null;
        }
        return page.getPage().get(0);
    }

    private static PositionFee reqPositionFee(long subAccountId, long sledContractId) throws ErrorInfo {
        TradeHostingPositionFeeStub stub = PositionFeeStubFactory.getStub();
        try {
            return stub.queryPositionFee(subAccountId, sledContractId);
        } catch (TException e) {
            AppLog.e(e.getMessage(), e);
            throw new ErrorInfo(ErrorCodeOuter.SERVER_BUSY.getErrorCode(), "Request PositionFee fail.");
        }
    }
}
