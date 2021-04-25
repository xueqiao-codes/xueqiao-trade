package xueqiao.trade.hosting.asset.calculator;

import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistory;
import com.longsheng.xueqiao.currency.thriftapi.ExchangeRateHistoryPage;
import net.qihoo.qconf.QconfException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.quotation.push.sdk.PushApi;
import xueqiao.quotation.push.sdk.UserMsg;
import xueqiao.trade.hosting.asset.event.handler.SledCurrency;
import xueqiao.trade.hosting.asset.storage.FactoryInstance;
import xueqiao.trade.hosting.asset.struct.OpenCloseTag;
import xueqiao.trade.hosting.asset.thriftapi.*;
import xueqiao.trade.hosting.asset.utils.CalculatorUtils;
import xueqiao.trade.hosting.events.ExpiredContractDeleteEvent;
import xueqiao.trade.hosting.events.HostingFundEvent;
import xueqiao.trade.hosting.events.HostingPositionFundEvent;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.push.sdk.impl.ProtocolUtil;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AssetBaseCalculator<T> {

    protected AssetContext mContext;

    public abstract String getKey();

    public long mAccountId;

    public long getAccountId() {
        return mAccountId;
    }

    public AssetBaseCalculator(long accountId) {
        this.mAccountId = accountId;
        this.mContext = CalculatorThreadManager.getInstance().getTaskThread(accountId);
    }

    public abstract void onCalculate(T t) throws Exception;

    public AssetContext getmContext() {
        return mContext;
    }

    public void addTask(T data) {
        addTask(data, 0, TimeUnit.MILLISECONDS);
    }

    public void addTask(T data, long delay, TimeUnit timeUnit) {
        TaskThread taskThread = this.getmContext().getmTaskThread();
        taskThread.postTaskDelay(new AssetCalculatorRunnable(this) {
            @Override
            public void onRun() throws Exception {
                getmExecutor().onCalculate(data);
            }
        }, delay, timeUnit);
    }

    protected BigDecimal calculateCommission(AssetCalculateConfig calculateConfig, BigDecimal price, int volume, OpenCloseTag openCloseTag) throws ErrorInfo {
        double contractSize = calculateConfig.getContractSize();
        double chargeUnit = calculateConfig.getChargeUnit();
        BigDecimal contractSizePrice = doubleToBigDecimal(contractSize).multiply(doubleToBigDecimal(chargeUnit));
        CommissionFee commissionFee = calculateConfig.getCommissionFee();

        double volumeRatio;
        double moneyRatio;
        if (OpenCloseTag.OPEN.equals(openCloseTag)) {
            volumeRatio = commissionFee.getOpenRatioByVolume();
            moneyRatio = commissionFee.getOpenRatioByMoney();
        } else if (OpenCloseTag.CLOSE_TODAY.equals(openCloseTag)) {
            volumeRatio = commissionFee.getCloseTodayRatioByVolume();
            moneyRatio = commissionFee.getCloseTodayRatioByMoney();
        } else {
            volumeRatio = commissionFee.getCloseRatioByVolume();
            moneyRatio = commissionFee.getCloseRatioByMoney();
        }

        BigDecimal volumeFactor = doubleToBigDecimal(volumeRatio);
        BigDecimal moneyFactor = doubleToBigDecimal(moneyRatio);

        BigDecimal commission = intToBigDecimal(volume).multiply(volumeFactor)
                .add(price.multiply(intToBigDecimal(volume)).multiply(moneyFactor).multiply(contractSizePrice));

        if (Double.compare(commissionFee.getCurrencyRate(), 1.0) != 0) {
            BigDecimal currencyRate = doubleToBigDecimal(commissionFee.getCurrencyRate());
            commission = commission.multiply(currencyRate);
        }

        recycle(contractSizePrice);
        recycle(volumeFactor);
        recycle(moneyFactor);
        return commission;
    }

    protected void setMargin(HostingSledContractPosition position, AssetCalculateConfig calculateConfig) throws ErrorInfo {
        long netPosition = position.getPositionVolume().getNetPosition();
        double price = position.getPositionFund().getCalculatePrice();

        BigDecimal useMargin = calculateMargin(calculateConfig, netPosition, doubleToBigDecimal(price));

        position.getPositionFund().setUseMargin(useMargin.doubleValue());
    }

    protected BigDecimal calculateMargin(AssetCalculateConfig calculateConfig, long netPosition, BigDecimal price) {
        double contractSize = calculateConfig.getContractSize();
        double chargeUnit = calculateConfig.getChargeUnit();
        BigDecimal contractSizePrice = doubleToBigDecimal(contractSize).multiply(doubleToBigDecimal(chargeUnit));
        Margin margin = calculateConfig.getMargin();

        double volumeRatio;
        double moneyRatio;
        if (netPosition >= 0) {
            volumeRatio = margin.getLongMarginRatioByVolume();
            moneyRatio = margin.getLongMarginRatioByMoney();
        } else {
            volumeRatio = margin.getShortMarginRatioByVolume();
            moneyRatio = margin.getShortMarginRatioByMoney();
//            AppLog.d("netPosition =" + netPosition + ", calculateConfig: " + calculateConfig);
        }

        BigDecimal volumeFactor = doubleToBigDecimal(volumeRatio);
        BigDecimal moneyFactor = doubleToBigDecimal(moneyRatio);


        return doubleToBigDecimal(Math.abs(netPosition)).multiply(volumeFactor)
                .add(doubleToBigDecimal(Math.abs(netPosition))
                        .multiply(moneyFactor).multiply(price).multiply(contractSizePrice));
    }

    protected void sendPositionMsg(HostingSledContractPosition hsp) {
        HostingPositionFundEvent event = new HostingPositionFundEvent();
        event.setEventCreateTimestampMs(System.currentTimeMillis());
        HostingPositionFund positionFund = hsp.getPositionFund();

        // 计算价为0，不设置
        if (CalculatorUtils.isDoubleZero(positionFund.getCalculatePrice())) {
            positionFund.unsetCalculatePrice();
            positionFund.unsetPositionProfit();
        }
        event.setPositionFund(positionFund);

        UserMsg msg = new UserMsg();
        msg.setMsgType(event.getClass().getSimpleName());
        msg.setMsgContent(ProtocolUtil.serialize(FactoryInstance.getInstance().getCompactFactory(), event).array());
        PushApi.sendMsgBySubAccountId(hsp.getSubAccountId(), msg);
    }

    protected void sendPositionDeleteMsg(long sledContractId ){
        ExpiredContractDeleteEvent event = new ExpiredContractDeleteEvent();
        event.setSledContractId(sledContractId);
        event.setSubAccountId(mAccountId);
        event.setEventCreateTimestampMs(System.currentTimeMillis());

        UserMsg msg = new UserMsg();
        msg.setMsgType(event.getClass().getSimpleName());
        msg.setMsgContent(ProtocolUtil.serialize(FactoryInstance.getInstance().getCompactFactory(), event).array());
        PushApi.sendMsgBySubAccountId(mAccountId, msg);
    }

    protected void sendFundMsg(HostingFund hostingFund, boolean isBaseCurrency) {
        if (CalculatorUtils.isDoubleZero(hostingFund.getDynamicBenefit())) {
            hostingFund.unsetRiskRate();
        }

        HostingFundEvent event = new HostingFundEvent();
        event.setCurrency(hostingFund.getCurrency());
        event.setEventCreateTimestampMs(System.currentTimeMillis());
        event.setSubAccountId(hostingFund.getSubAccountId());
        event.setHostingFund(hostingFund);
        event.setBaseCurrency(isBaseCurrency);
        UserMsg msg = new UserMsg();
        msg.setMsgType(event.getClass().getSimpleName());
        msg.setMsgContent(ProtocolUtil.serialize(FactoryInstance.getInstance().getCompactFactory(), event).array());
        PushApi.sendMsgBySubAccountId(hostingFund.getSubAccountId(), msg);
    }

    protected void sendFundMsg(HostingFund hostingFund) {
        sendFundMsg(hostingFund, false);
    }

    protected BigDecimal getCurrencyRate(ExchangeRateHistory history, String basicCurrency, String currency) {
        BigDecimal currencyRate = BigDecimal.ONE;
        if (!basicCurrency.equals(currency)) {

            Map<String, Double> rateMap = history.getExchangeRate();
            if (rateMap == null) {
                return null;
            }

            Double rate = rateMap.get(currency);
            if (rate == null) {
                return null;
            }
            currencyRate = doubleToBigDecimal(rate);
        }
        return currencyRate;
    }

    protected BigDecimal getCurrencyRate(String currency) {
        ExchangeRateHistory history = getExchangeRateHistory();
        BigDecimal currencyRate = BigDecimal.ONE;
        if (history != null) {
            currencyRate = getCurrencyRate(history, history.getCurrencyCode(), currency);
        }
        if (!history.getCurrencyCode().equals(currency)) {

            Map<String, Double> rateMap = history.getExchangeRate();
            if (rateMap == null) {
                return null;
            }

            Double rate = rateMap.get(currency);
            if (rate == null) {
                return null;
            }
            currencyRate = doubleToBigDecimal(rate);
        }
        return currencyRate;
    }

    protected ExchangeRateHistory getExchangeRateHistory() {
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

    protected BigDecimal doubleToBigDecimal(double x) {
        if (Double.isNaN(x)) {
            x = 0.0;
        }
        return new BigDecimal(Double.toString(x));
    }

    protected BigDecimal intToBigDecimal(int x) {
        return new BigDecimal(Integer.toString(x));
    }

    protected BigDecimal longToBigDecimal(long x) {
        return new BigDecimal(Long.toString(x));
    }

    protected void recycle(Object obj) {

        obj = null;
    }
}
