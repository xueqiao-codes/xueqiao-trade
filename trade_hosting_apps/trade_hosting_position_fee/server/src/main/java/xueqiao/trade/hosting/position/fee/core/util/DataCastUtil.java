package xueqiao.trade.hosting.position.fee.core.util;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.position.fee.core.bean.*;
import xueqiao.trade.hosting.upside.entry.*;

import java.util.ArrayList;
import java.util.List;

public class DataCastUtil {

    public static List<UpsidePositionRate> castToUpsidePositionRateList(TPositionRateDetails details) throws ErrorInfo {
        if (details == null || details.getCommodityRatesSize() < 1) {
            return null;
        }
        List<UpsidePositionRate> upsidePositionRateList = new ArrayList<>();
        for (TPositionCommodityRate positionCommodityRate : details.getCommodityRates()) {
            UpsidePositionRate upsidePositionRate = getCommodityRate(details.getTradeAccountId(), details.getTechPlatform(), positionCommodityRate);
            if (upsidePositionRate != null) {
                upsidePositionRateList.add(upsidePositionRate);
            }

            if (positionCommodityRate.getContractRates() != null && !positionCommodityRate.getContractRates().isEmpty()) {
                for (TPositionContractRate contractRate : positionCommodityRate.getContractRates().values()) {
                    upsidePositionRate = getContractRate(details.getTradeAccountId(), details.getTechPlatform(), positionCommodityRate, contractRate);
                    if (upsidePositionRate != null) {
                        upsidePositionRateList.add(upsidePositionRate);
                    }
                }
            }
        }
        return upsidePositionRateList;
    }

    private static UpsidePositionRate getCommodityRate(long tradeAccountId, BrokerTechPlatform techPlatform, TPositionCommodityRate positionCommodityRate) throws ErrorInfo {
        UpsidePositionRateDetail detail = getPositionRateDetail(positionCommodityRate.getMarginRate(), positionCommodityRate.getCommissionRate());
        if (detail == null) {
            return null;
        }
        UpsidePositionRate upsidePositionRate = new UpsidePositionRate();
        upsidePositionRate.setTradeAccountId(tradeAccountId);
        upsidePositionRate.setTechPlatform(techPlatform);
        upsidePositionRate.setSledCommodityId(positionCommodityRate.getSledCommodityId());
        upsidePositionRate.setSledCommodityType(positionCommodityRate.getSledCommodityType());
        upsidePositionRate.setSledCommodityCode(positionCommodityRate.getSledCommodityCode());
        upsidePositionRate.setSledExchangeMic(positionCommodityRate.getSledExchangeMic());
        upsidePositionRate.setDetail(detail);
        return upsidePositionRate;

    }

    public static UpsidePositionRate getContractRate(long tradeAccountId, BrokerTechPlatform techPlatform, TPositionCommodityRate positionCommodityRate, TPositionContractRate contractRate) throws ErrorInfo {
        UpsidePositionRateDetail detail = getPositionRateDetail(contractRate.getMarginRate(), contractRate.getCommissionRate());
        if (detail == null) {
            return null;
        }
        UpsidePositionRate upsidePositionRate = new UpsidePositionRate();
        upsidePositionRate.setTradeAccountId(tradeAccountId);
        upsidePositionRate.setTechPlatform(techPlatform);
        upsidePositionRate.setSledCommodityId(positionCommodityRate.getSledCommodityId());
        upsidePositionRate.setSledContractCode(contractRate.getSledContractCode());
        upsidePositionRate.setSledCommodityType(positionCommodityRate.getSledCommodityType());
        upsidePositionRate.setSledCommodityCode(positionCommodityRate.getSledCommodityCode());
        upsidePositionRate.setSledExchangeMic(positionCommodityRate.getSledExchangeMic());
        upsidePositionRate.setDetail(detail);
        return upsidePositionRate;
    }

    private static UpsidePositionRateDetail getPositionRateDetail(TPositionMarginRate marginRate, TPositionCommissionRate commissionRate) throws ErrorInfo {
        UpsidePositionRateDetail detail = null;
        if (marginRate != null) {
            detail = new UpsidePositionRateDetail();
            if (marginRate.getCtpMarginRate() != null) {
                detail.setCtpMarginRate(castToUpsideCTPMarginRate(marginRate.getCtpMarginRate()));
            } else if (marginRate.getEs9MarginRate() != null) {
                detail.setEs9MarginRate(castToUpsideES9MarginRate(marginRate.getEs9MarginRate()));
            }
        }
        if (commissionRate != null) {
            if (detail == null) {
                detail = new UpsidePositionRateDetail();
            }
            if (commissionRate.getCtpCommissionRate() != null) {
                detail.setCtpCommissionRate(castToUpsideCTPCommissionRate(commissionRate.getCtpCommissionRate()));
            } else if (commissionRate.getEs9CommissionRate() != null) {
                detail.setEs9CommissionRate(castToUpsideES9CommissionRate(commissionRate.getEs9CommissionRate()));
            }
        }
        return detail;
    }

    public static UpsideCTPMarginRate castToUpsideCTPMarginRate(TPositionCTPMarginRate ctpMarginRate) {
        UpsideCTPMarginRate upsideCTPMarginRate = new UpsideCTPMarginRate();
        if (ctpMarginRate.getExchangeMarginRate() != null) {
            UpsideCTPExchangeMarginRate upsideCTPExchangeMarginRate = new UpsideCTPExchangeMarginRate();
            upsideCTPExchangeMarginRate.setLongMarginRatioByMoney(ctpMarginRate.getExchangeMarginRate().getLongMarginRatioByMoney());
            upsideCTPExchangeMarginRate.setLongMarginRatioByVolume(ctpMarginRate.getExchangeMarginRate().getLongMarginRatioByVolume());
            upsideCTPExchangeMarginRate.setShortMarginRatioByMoney(ctpMarginRate.getExchangeMarginRate().getShortMarginRatioByMoney());
            upsideCTPExchangeMarginRate.setShortMarginRatioByVolume(ctpMarginRate.getExchangeMarginRate().getShortMarginRatioByVolume());
            upsideCTPMarginRate.setExchangeMarginRate(upsideCTPExchangeMarginRate);
        }
        if (ctpMarginRate.getInstrumentMarginRate() != null) {
            UpsideCTPInstrumentMarginRate upsideCTPInstrumentMarginRate = new UpsideCTPInstrumentMarginRate();
            upsideCTPInstrumentMarginRate.setLongMarginRatioByMoney(ctpMarginRate.getInstrumentMarginRate().getLongMarginRatioByMoney());
            upsideCTPInstrumentMarginRate.setLongMarginRatioByVolume(ctpMarginRate.getInstrumentMarginRate().getLongMarginRatioByVolume());
            upsideCTPInstrumentMarginRate.setShortMarginRatioByMoney(ctpMarginRate.getInstrumentMarginRate().getShortMarginRatioByMoney());
            upsideCTPInstrumentMarginRate.setShortMarginRatioByVolume(ctpMarginRate.getInstrumentMarginRate().getShortMarginRatioByVolume());
            upsideCTPInstrumentMarginRate.setRelative(ctpMarginRate.getInstrumentMarginRate().isIsRelative());
            upsideCTPMarginRate.setInstrumentMarginRate(upsideCTPInstrumentMarginRate);
        }
        return upsideCTPMarginRate;
    }

    public static UpsideES9MarginRate castToUpsideES9MarginRate(TPositionES9MarginRate es9MarginRate) throws ErrorInfo {
        UpsideES9MarginRate upsideES9MarginRate = new UpsideES9MarginRate();
        upsideES9MarginRate.setCalculateMode(ESAPICalculateMode.findByValue(es9MarginRate.getCalculateMode()));
        upsideES9MarginRate.setCurrencyGroupNo(es9MarginRate.getCurrencyGroupNo());
        upsideES9MarginRate.setCurrencyNo(es9MarginRate.getCurrencyNo());
        upsideES9MarginRate.setInitialMargin(es9MarginRate.getInitialMargin());
        upsideES9MarginRate.setMaintenanceMargin(es9MarginRate.getMaintenanceMargin());
        upsideES9MarginRate.setSellInitialMargin(es9MarginRate.getSellInitialMargin());
        upsideES9MarginRate.setSellMaintenanceMargin(es9MarginRate.getSellMaintenanceMargin());
        upsideES9MarginRate.setLockMargin(es9MarginRate.getLockMargin());
        return upsideES9MarginRate;
    }

    public static UpsideCTPCommissionRate castToUpsideCTPCommissionRate(TPositionCTPCommissionRate ctpCommissionRate) {
        UpsideCTPCommissionRate upsideCTPCommissionRate = new UpsideCTPCommissionRate();
        upsideCTPCommissionRate.setOpenRatioByMoney(ctpCommissionRate.getOpenRatioByMoney());
        upsideCTPCommissionRate.setOpenRatioByVolume(ctpCommissionRate.getOpenRatioByVolume());
        upsideCTPCommissionRate.setCloseRatioByMoney(ctpCommissionRate.getCloseRatioByMoney());
        upsideCTPCommissionRate.setCloseRatioByVolume(ctpCommissionRate.getCloseRatioByVolume());
        upsideCTPCommissionRate.setCloseTodayRatioByMoney(ctpCommissionRate.getCloseTodayRatioByMoney());
        upsideCTPCommissionRate.setCloseTodayRatioByVolume(ctpCommissionRate.getCloseTodayRatioByVolume());
        return upsideCTPCommissionRate;
    }

    public static UpsideES9CommissionRate castToUpsideES9CommissionRate(TPositionEs9CommissionRate es9CommissionRate) throws ErrorInfo {
        UpsideES9CommissionRate upsideES9CommissionRate = new UpsideES9CommissionRate();
        upsideES9CommissionRate.setCalculateMode(ESAPICalculateMode.findByValue(es9CommissionRate.getCalculateMode()));
        upsideES9CommissionRate.setCurrencyGroupNo(es9CommissionRate.getCurrencyGroupNo());
        upsideES9CommissionRate.setCurrencyNo(es9CommissionRate.getCurrencyNo());
        upsideES9CommissionRate.setOpenCloseFee(es9CommissionRate.getOpenCloseFee());
        upsideES9CommissionRate.setCloseTodayFee(es9CommissionRate.getCloseTodayFee());
        return upsideES9CommissionRate;
    }
}
