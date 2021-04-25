package xueqiao.trade.hosting.position.fee.core.util;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.position.fee.core.bean.RouteCommodityNode;
import xueqiao.trade.hosting.position.fee.core.bean.*;
import xueqiao.trade.hosting.position.fee.core.util.currency.CNYRateHelper;
import xueqiao.trade.hosting.position.fee.thriftapi.*;

import java.math.BigDecimal;

public class PositionRateUtil {

    private static final String CTP_CURRENCY_CNY = "CNY";

    public static CommissionInfo getCommissionInfo(CommissionInfo baseCommissionInfo, DeltaCommissionInfo commissionDeltaInfo) throws ErrorInfo {
        CommissionInfo commissionInfo = new CommissionInfo();
        CommissionInfo commissionDelta = commissionDeltaInfo.getCommissionDelta();
        if (commissionDeltaInfo.getSettingsDataType() == XQSettingsDataType.SDT_NO_DATA) {
            /*
             * 无设置数据，则没有偏移值
             * */
            commissionInfo.setOpenRatioByMoney(baseCommissionInfo.getOpenRatioByMoney());
            commissionInfo.setOpenRatioByVolume(baseCommissionInfo.getOpenRatioByVolume());
            commissionInfo.setCloseRatioByMoney(baseCommissionInfo.getCloseRatioByMoney());
            commissionInfo.setCloseRatioByVolume(baseCommissionInfo.getCloseRatioByVolume());
            commissionInfo.setCloseTodayRatioByMoney(baseCommissionInfo.getCloseTodayRatioByMoney());
            commissionInfo.setCloseTodayRatioByVolume(baseCommissionInfo.getCloseTodayRatioByVolume());
            commissionInfo.setCurrency(baseCommissionInfo.getCurrency());
            commissionInfo.setCurrencyGroup(baseCommissionInfo.getCurrencyGroup());
        } else if (commissionDeltaInfo.getType() == FeeCalculateType.FR_DELTA_ADD) {
            /*
             * 在基数据上增加
             * */
            commissionInfo.setOpenRatioByMoney(MathUtil.add(baseCommissionInfo.getOpenRatioByMoney(), commissionDelta.getOpenRatioByMoney()));
            commissionInfo.setOpenRatioByVolume(PositionRateUtil.addPositionRate(baseCommissionInfo.getOpenRatioByVolume(), baseCommissionInfo.getCurrency(), commissionDelta.getOpenRatioByVolume(), commissionDelta.getCurrency()));
            commissionInfo.setCloseRatioByMoney(MathUtil.add(baseCommissionInfo.getCloseRatioByMoney(), commissionDelta.getCloseRatioByMoney()));
            commissionInfo.setCloseRatioByVolume(PositionRateUtil.addPositionRate(baseCommissionInfo.getCloseRatioByVolume(), baseCommissionInfo.getCurrency(), commissionDelta.getCloseRatioByVolume(), commissionDelta.getCurrency()));
            commissionInfo.setCloseTodayRatioByMoney(MathUtil.add(baseCommissionInfo.getCloseTodayRatioByMoney(), commissionDelta.getCloseTodayRatioByMoney()));
            commissionInfo.setCloseTodayRatioByVolume(PositionRateUtil.addPositionRate(baseCommissionInfo.getCloseTodayRatioByVolume(), baseCommissionInfo.getCurrency(), commissionDelta.getCloseTodayRatioByVolume(), commissionDelta.getCurrency()));
            commissionInfo.setCurrency(baseCommissionInfo.getCurrency());
            commissionInfo.setCurrencyGroup(baseCommissionInfo.getCurrencyGroup());
        } else {
            /*
             * 在基数据上减少
             * */
            commissionInfo.setOpenRatioByMoney(MathUtil.subtract(baseCommissionInfo.getOpenRatioByMoney(), commissionDeltaInfo.getCommissionDelta().getOpenRatioByMoney()));
            commissionInfo.setOpenRatioByVolume(PositionRateUtil.subtractPositionRate(baseCommissionInfo.getOpenRatioByVolume(), baseCommissionInfo.getCurrency(), commissionDelta.getOpenRatioByVolume(), commissionDelta.getCurrency()));
            commissionInfo.setCloseRatioByMoney(MathUtil.subtract(baseCommissionInfo.getCloseRatioByMoney(), commissionDeltaInfo.getCommissionDelta().getCloseRatioByMoney()));
            commissionInfo.setCloseRatioByVolume(PositionRateUtil.subtractPositionRate(baseCommissionInfo.getCloseRatioByVolume(), baseCommissionInfo.getCurrency(), commissionDelta.getCloseRatioByVolume(), commissionDelta.getCurrency()));
            commissionInfo.setCloseTodayRatioByMoney(MathUtil.subtract(baseCommissionInfo.getCloseTodayRatioByMoney(), commissionDeltaInfo.getCommissionDelta().getCloseTodayRatioByMoney()));
            commissionInfo.setCloseTodayRatioByVolume(PositionRateUtil.subtractPositionRate(baseCommissionInfo.getCloseTodayRatioByVolume(), baseCommissionInfo.getCurrency(), commissionDelta.getCloseTodayRatioByVolume(), commissionDelta.getCurrency()));
            commissionInfo.setCurrency(baseCommissionInfo.getCurrency());
            commissionInfo.setCurrencyGroup(baseCommissionInfo.getCurrencyGroup());
        }
        return commissionInfo;
    }

    public static MarginInfo getMarginInfo(MarginInfo baseMarginInfo, DeltaMarginInfo deltaMarginInfo) throws ErrorInfo {
        MarginInfo marginInfo = new MarginInfo();
        MarginInfo marginDelta = deltaMarginInfo.getMarginDelta();
        if (deltaMarginInfo.getSettingsDataType() == XQSettingsDataType.SDT_NO_DATA) {
            /*
             * 无设置数据，则没有偏移值
             * */
            marginInfo.setLongMarginRatioByMoney(baseMarginInfo.getLongMarginRatioByMoney());
            marginInfo.setLongMarginRatioByVolume(baseMarginInfo.getLongMarginRatioByVolume());
            marginInfo.setShortMarginRatioByMoney(baseMarginInfo.getShortMarginRatioByMoney());
            marginInfo.setShortMarginRatioByVolume(baseMarginInfo.getShortMarginRatioByVolume());
            marginInfo.setCurrency(baseMarginInfo.getCurrency());
            marginInfo.setCurrencyGroup(baseMarginInfo.getCurrencyGroup());
        } else if (deltaMarginInfo.getType() == FeeCalculateType.FR_DELTA_ADD) {
            /*
             * 在基数据上增加
             * */
            marginInfo.setLongMarginRatioByMoney(MathUtil.add(baseMarginInfo.getLongMarginRatioByMoney(), deltaMarginInfo.getMarginDelta().getLongMarginRatioByMoney()));
            marginInfo.setLongMarginRatioByVolume(PositionRateUtil.addPositionRate(baseMarginInfo.getLongMarginRatioByVolume(), baseMarginInfo.getCurrency(), marginDelta.getLongMarginRatioByVolume(), marginDelta.getCurrency()));
            marginInfo.setShortMarginRatioByMoney(MathUtil.add(baseMarginInfo.getShortMarginRatioByMoney(), deltaMarginInfo.getMarginDelta().getShortMarginRatioByMoney()));
            marginInfo.setShortMarginRatioByVolume(PositionRateUtil.addPositionRate(baseMarginInfo.getShortMarginRatioByVolume(), baseMarginInfo.getCurrency(), marginDelta.getShortMarginRatioByVolume(), marginDelta.getCurrency()));
            marginInfo.setCurrency(baseMarginInfo.getCurrency());
            marginInfo.setCurrencyGroup(baseMarginInfo.getCurrencyGroup());
        } else {
            /*
             * 在基数据上减少
             * */
            marginInfo.setLongMarginRatioByMoney(MathUtil.subtract(baseMarginInfo.getLongMarginRatioByMoney(), deltaMarginInfo.getMarginDelta().getLongMarginRatioByMoney()));
            marginInfo.setLongMarginRatioByVolume(PositionRateUtil.subtractPositionRate(baseMarginInfo.getLongMarginRatioByVolume(), baseMarginInfo.getCurrency(), marginDelta.getLongMarginRatioByVolume(), marginDelta.getCurrency()));
            marginInfo.setShortMarginRatioByMoney(MathUtil.subtract(baseMarginInfo.getShortMarginRatioByMoney(), deltaMarginInfo.getMarginDelta().getShortMarginRatioByMoney()));
            marginInfo.setShortMarginRatioByVolume(PositionRateUtil.subtractPositionRate(baseMarginInfo.getShortMarginRatioByVolume(), baseMarginInfo.getCurrency(), marginDelta.getShortMarginRatioByVolume(), marginDelta.getCurrency()));
            marginInfo.setCurrency(baseMarginInfo.getCurrency());
            marginInfo.setCurrencyGroup(baseMarginInfo.getCurrencyGroup());
        }
        return marginInfo;
    }

    public static UpsideContractCommission getUpsideContractCommission(long subAccountId, UpsidePositionRate upsidePositionRate, SledContract contract) {
        UpsideContractCommission upsideContractCommission = new UpsideContractCommission();
        ContractInfo contractInfo = PositionRateUtil.getContractInfo(upsidePositionRate, contract);
        CommissionInfo commissionInfo;
        if (upsidePositionRate.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            commissionInfo = PositionRateUtil.getCTPCommissionInfo(upsidePositionRate);
        } else {
            commissionInfo = PositionRateUtil.getESCommissionInfo(upsidePositionRate);
        }
        upsideContractCommission.setSubAccountId(subAccountId);
        upsideContractCommission.setContractInfo(contractInfo);
        upsideContractCommission.setCommission(commissionInfo);
        return upsideContractCommission;
    }

    public static UpsideContractMargin getUpsideContractMargin(long subAccountId, UpsidePositionRate upsidePositionRate, SledContract contract) {
        UpsideContractMargin upsideContractMargin = new UpsideContractMargin();
        ContractInfo contractInfo = PositionRateUtil.getContractInfo(upsidePositionRate, contract);
        MarginInfo marginInfo;
        if (upsidePositionRate.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            marginInfo = PositionRateUtil.getCTPMarginInfo(upsidePositionRate);
        } else {
            marginInfo = PositionRateUtil.getESMarginInfo(upsidePositionRate);
        }
        upsideContractMargin.setSubAccountId(subAccountId);
        upsideContractMargin.setContractInfo(contractInfo);
        upsideContractMargin.setMargin(marginInfo);
        return upsideContractMargin;
    }

    public static UpsideContractCommission getEmptyUpsideContractCommission(long subAccountId, RouteCommodityNode routeCommodityNode, SledContract contract) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setTradeAccountId(routeCommodityNode.getTradeAccountId());
        contractInfo.setCommodityId(contract.getSledCommodityId());
        contractInfo.setContractCode(contract.getSledContractCode());
        contractInfo.setContractId(contract.getSledContractId());

//        CommissionInfo commission = new CommissionInfo();
        UpsideContractCommission upsideContractCommission = new UpsideContractCommission();
        upsideContractCommission.setSubAccountId(subAccountId);
        upsideContractCommission.setContractInfo(contractInfo);
        upsideContractCommission.setCommission(null);
        return upsideContractCommission;
    }

    public static UpsideContractMargin getEmptyUpsideContractMargin(long subAccountId, RouteCommodityNode routeCommodityNode, SledContract contract) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setTradeAccountId(routeCommodityNode.getTradeAccountId());
        contractInfo.setCommodityId(contract.getSledCommodityId());
        contractInfo.setContractCode(contract.getSledContractCode());
        contractInfo.setContractId(contract.getSledContractId());

        UpsideContractMargin upsideContractMargin = new UpsideContractMargin();
        upsideContractMargin.setSubAccountId(subAccountId);
        upsideContractMargin.setContractInfo(contractInfo);
        upsideContractMargin.setMargin(null);
        return upsideContractMargin;
    }

//    public static ContractInfo getContractInfo(UpsidePositionRate upsidePositionRate, String contractCode) {
//        ContractInfo contractInfo = new ContractInfo();
//        contractInfo.setTradeAccountId(upsidePositionRate.getTradeAccountId());
//        contractInfo.setCommodityId(upsidePositionRate.getSledCommodityId());
//        contractInfo.setContractCode(contractCode);
//        contractInfo.setExchangeMic(upsidePositionRate.getSledExchangeMic());
//        return contractInfo;
//    }

    public static ContractInfo getContractInfo(UpsidePositionRate upsidePositionRate, SledContract contract) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setTradeAccountId(upsidePositionRate.getTradeAccountId());
        contractInfo.setCommodityId(upsidePositionRate.getSledCommodityId());
        contractInfo.setContractCode(contract.getSledContractCode());
        contractInfo.setContractId(contract.getSledContractId());
        contractInfo.setExchangeMic(upsidePositionRate.getSledExchangeMic());
        return contractInfo;
    }

    public static ContractInfo getContractInfo(UpsidePositionRate upsidePositionRate, ContractInfo originalContractInfo) {
        ContractInfo contractInfo = new ContractInfo();
        contractInfo.setTradeAccountId(upsidePositionRate.getTradeAccountId());
        contractInfo.setCommodityId(upsidePositionRate.getSledCommodityId());
        contractInfo.setContractCode(originalContractInfo.getContractCode());
        contractInfo.setContractId(originalContractInfo.getContractId());
        contractInfo.setExchangeMic(upsidePositionRate.getSledExchangeMic());
        return contractInfo;
    }

    public static CommissionInfo getCTPCommissionInfo(UpsidePositionRate upsidePositionRate) {
        CommissionInfo commissionInfo = null;
        if (upsidePositionRate.getDetail() != null && upsidePositionRate.getDetail().getCtpCommissionRate() != null) {
            commissionInfo = new CommissionInfo();
            UpsideCTPCommissionRate ctpCommissionRate = upsidePositionRate.getDetail().getCtpCommissionRate();
            commissionInfo.setOpenRatioByMoney(ctpCommissionRate.getOpenRatioByMoney());
            commissionInfo.setOpenRatioByVolume(ctpCommissionRate.getOpenRatioByVolume());
            commissionInfo.setCloseRatioByMoney(ctpCommissionRate.getCloseRatioByMoney());
            commissionInfo.setCloseRatioByVolume(ctpCommissionRate.getCloseRatioByVolume());
            commissionInfo.setCloseTodayRatioByMoney(ctpCommissionRate.getCloseTodayRatioByMoney());
            commissionInfo.setCloseTodayRatioByVolume(ctpCommissionRate.getCloseTodayRatioByVolume());
            commissionInfo.setCurrency(CTP_CURRENCY_CNY);
            commissionInfo.setCurrencyGroup("");
        }
        return commissionInfo;
    }

    public static CommissionInfo getESCommissionInfo(UpsidePositionRate upsidePositionRate) {
        CommissionInfo commissionInfo = null;
        if (upsidePositionRate.getDetail() != null && upsidePositionRate.getDetail().getEs9CommissionRate() != null) {
            UpsideES9CommissionRate es9CommissionRate = upsidePositionRate.getDetail().getEs9CommissionRate();
            commissionInfo = new CommissionInfo();
            if (es9CommissionRate.getCalculateMode() == ESAPICalculateMode.API_CALCULATE_MODE_COMBINE) {
                EsAPICombineModeCalculator openCloseFeeCalculator = new EsAPICombineModeCalculator(es9CommissionRate.getOpenCloseFee());
                EsAPICombineModeCalculator closeTodayFeeCalculator = new EsAPICombineModeCalculator(es9CommissionRate.getCloseTodayFee());
                commissionInfo.setOpenRatioByMoney(openCloseFeeCalculator.getPercentage());
                commissionInfo.setOpenRatioByVolume(openCloseFeeCalculator.getQuota());
                commissionInfo.setCloseRatioByMoney(openCloseFeeCalculator.getPercentage());
                commissionInfo.setCloseRatioByVolume(openCloseFeeCalculator.getQuota());
                commissionInfo.setCloseTodayRatioByMoney(closeTodayFeeCalculator.getPercentage());
                commissionInfo.setCloseTodayRatioByVolume(closeTodayFeeCalculator.getQuota());
            } else if (es9CommissionRate.getCalculateMode() == ESAPICalculateMode.API_CALCULATE_MODE_PERCENTAGE) {
                commissionInfo.setOpenRatioByMoney(es9CommissionRate.getOpenCloseFee());
                commissionInfo.setOpenRatioByVolume(0);
                commissionInfo.setCloseRatioByMoney(es9CommissionRate.getOpenCloseFee());
                commissionInfo.setCloseRatioByVolume(0);
                commissionInfo.setCloseTodayRatioByMoney(es9CommissionRate.getCloseTodayFee());
                commissionInfo.setCloseTodayRatioByVolume(0);
            } else if (es9CommissionRate.getCalculateMode() == ESAPICalculateMode.API_CALCULATE_MODE_QUOTA) {
                commissionInfo.setOpenRatioByMoney(0);
                commissionInfo.setOpenRatioByVolume(es9CommissionRate.getOpenCloseFee());
                commissionInfo.setCloseRatioByMoney(0);
                commissionInfo.setCloseRatioByVolume(es9CommissionRate.getOpenCloseFee());
                commissionInfo.setCloseTodayRatioByMoney(0);
                commissionInfo.setCloseTodayRatioByVolume(es9CommissionRate.getCloseTodayFee());
            }
            commissionInfo.setCurrency(es9CommissionRate.getCurrencyNo());
            commissionInfo.setCurrencyGroup(es9CommissionRate.getCurrencyGroupNo());
        }
        return commissionInfo;
    }

    public static MarginInfo getCTPMarginInfo(UpsidePositionRate upsidePositionRate) {
        MarginInfo marginInfo = null;
        if (upsidePositionRate.getDetail() != null && upsidePositionRate.getDetail().getCtpMarginRate() != null) {
            UpsideCTPInstrumentMarginRate upsideCTPInstrumentMarginRate = upsidePositionRate.getDetail().getCtpMarginRate().getInstrumentMarginRate();
            if (upsideCTPInstrumentMarginRate != null) {
                if (upsideCTPInstrumentMarginRate.isRelative()) {
                    /*
                     * 因为正常来说，目前一般都是绝对值，而不是相对交易所的值
                     * 所以，有这种情况的话，需要警报一下
                     * */
                    AppLog.e("getCTPMarginInfo ---- isRelative : true");
                    UpsideCTPExchangeMarginRate upsideCTPExchangeMarginRate = upsidePositionRate.getDetail().getCtpMarginRate().getExchangeMarginRate();
                    if (upsideCTPExchangeMarginRate != null) {
                        marginInfo = new MarginInfo();
                        marginInfo.setLongMarginRatioByMoney(upsideCTPExchangeMarginRate.getLongMarginRatioByMoney() + upsideCTPInstrumentMarginRate.getLongMarginRatioByMoney());
                        marginInfo.setLongMarginRatioByVolume(upsideCTPExchangeMarginRate.getLongMarginRatioByVolume() + upsideCTPInstrumentMarginRate.getLongMarginRatioByVolume());
                        marginInfo.setShortMarginRatioByMoney(upsideCTPExchangeMarginRate.getShortMarginRatioByMoney() + upsideCTPInstrumentMarginRate.getShortMarginRatioByMoney());
                        marginInfo.setShortMarginRatioByVolume(upsideCTPExchangeMarginRate.getShortMarginRatioByVolume() + upsideCTPInstrumentMarginRate.getShortMarginRatioByVolume());
                        marginInfo.setCurrency(CTP_CURRENCY_CNY);
                        marginInfo.setCurrencyGroup("");
                    }
                } else {
                    marginInfo = new MarginInfo();
                    marginInfo.setLongMarginRatioByMoney(upsideCTPInstrumentMarginRate.getLongMarginRatioByMoney());
                    marginInfo.setLongMarginRatioByVolume(upsideCTPInstrumentMarginRate.getLongMarginRatioByVolume());
                    marginInfo.setShortMarginRatioByMoney(upsideCTPInstrumentMarginRate.getShortMarginRatioByMoney());
                    marginInfo.setShortMarginRatioByVolume(upsideCTPInstrumentMarginRate.getShortMarginRatioByVolume());
                    marginInfo.setCurrency(CTP_CURRENCY_CNY);
                    marginInfo.setCurrencyGroup("");
                }
            }
        }
        return marginInfo;
    }

    public static MarginInfo getESMarginInfo(UpsidePositionRate upsidePositionRate) {
        MarginInfo marginInfo = null;
        if (upsidePositionRate.getDetail() != null && upsidePositionRate.getDetail().getEs9MarginRate() != null) {
            UpsideES9MarginRate upsideES9MarginRate = upsidePositionRate.getDetail().getEs9MarginRate();
            if (upsideES9MarginRate.getCalculateMode() == ESAPICalculateMode.API_CALCULATE_MODE_PERCENTAGE) {
                marginInfo = new MarginInfo();
                marginInfo.setLongMarginRatioByMoney(upsideES9MarginRate.getInitialMargin());
                marginInfo.setLongMarginRatioByVolume(0);
                marginInfo.setShortMarginRatioByMoney(upsideES9MarginRate.getSellInitialMargin());
                marginInfo.setShortMarginRatioByVolume(0);
                marginInfo.setCurrency(upsideES9MarginRate.getCurrencyNo());
                marginInfo.setCurrencyGroup(upsideES9MarginRate.getCurrencyGroupNo());
            } else if (upsideES9MarginRate.getCalculateMode() == ESAPICalculateMode.API_CALCULATE_MODE_QUOTA) {
                marginInfo = new MarginInfo();
                marginInfo.setLongMarginRatioByMoney(0);
                marginInfo.setLongMarginRatioByVolume(upsideES9MarginRate.getInitialMargin());
                marginInfo.setShortMarginRatioByMoney(0);
                marginInfo.setShortMarginRatioByVolume(upsideES9MarginRate.getSellInitialMargin());
                marginInfo.setCurrency(upsideES9MarginRate.getCurrencyNo());
                marginInfo.setCurrencyGroup(upsideES9MarginRate.getCurrencyGroupNo());
            } else {
                /*
                 * 易盛的接口说明上，只会有比例 和 定额 模式，如果出现其他模式，则警报
                 * */
                AppLog.e("getESMarginInfo ---- calculateMode : " + upsideES9MarginRate.getCalculateMode());
            }
        }
        return marginInfo;
    }

    public static double addPositionRate(double baseRate, String baseRateCurrency, double delta, String deltaCurrency) throws ErrorInfo {
        BigDecimal deltaBigDecimal = CNYRateHelper.getInstance().exchangeRate(deltaCurrency, baseRateCurrency, new BigDecimal(String.valueOf(delta)));
        BigDecimal ret = new BigDecimal(String.valueOf(baseRate)).add(deltaBigDecimal);
        return ret.doubleValue();
    }

    public static double subtractPositionRate(double baseRate, String baseRateCurrency, double delta, String deltaCurrency) throws ErrorInfo {
        BigDecimal deltaBigDecimal = CNYRateHelper.getInstance().exchangeRate(deltaCurrency, baseRateCurrency, new BigDecimal(String.valueOf(delta)));
        BigDecimal ret = new BigDecimal(String.valueOf(baseRate)).subtract(deltaBigDecimal);
        return ret.doubleValue();
    }
}
