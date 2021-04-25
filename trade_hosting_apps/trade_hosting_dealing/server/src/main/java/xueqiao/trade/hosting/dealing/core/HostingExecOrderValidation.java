package xueqiao.trade.hosting.dealing.core;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderMode;
import xueqiao.trade.hosting.HostingExecOrderType;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

public class HostingExecOrderValidation {
    private static final SimpleDateFormat EFFECTIVE_DATETIME_FORMATER 
                = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static void checkEffectiveDatetimeFormat(String effectiveDatetime) throws ErrorInfo {
        try {
            EFFECTIVE_DATETIME_FORMATER.parse(effectiveDatetime);
        } catch (Throwable e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "effectiveDatetime format error! should be yyyy-MM-dd HH:mm:ss");
        }
    }
    
    public static void checkContractSummary(HostingExecOrderContractSummary contractSummary) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(contractSummary.isSetSledExchangeMic() 
                && StringUtils.isNotEmpty(contractSummary.getSledExchangeMic()));
        ParameterChecker.checkInnerArgument(contractSummary.isSetSledCommodityId()
                && contractSummary.getSledCommodityId() > 0);
        ParameterChecker.checkInnerArgument(contractSummary.isSetSledCommodityType());
        ParameterChecker.checkInnerArgument(contractSummary.isSetSledCommodityCode()
                && StringUtils.isNotEmpty(contractSummary.getSledCommodityCode()));
        ParameterChecker.checkInnerArgument(contractSummary.isSetSledContractId()
                && contractSummary.getSledContractId() > 0);
        ParameterChecker.checkInnerArgument(contractSummary.isSetSledContractCode()
                && StringUtils.isNotEmpty(contractSummary.getSledContractCode()));
    }
    
    public static void checkOrderDetail(HostingExecOrderDetail orderDetail) throws ErrorInfo {
        ParameterChecker.checkInnerArgument(orderDetail.isSetOrderType());
        
        ParameterChecker.checkInnerArgument(orderDetail.isSetQuantity() && orderDetail.getQuantity() > 0);
        ParameterChecker.checkInnerArgument(orderDetail.isSetTradeDirection());
        if (orderDetail.getOrderType() == HostingExecOrderType.ORDER_LIMIT_PRICE) {
            ParameterChecker.checkInnerArgument(orderDetail.isSetLimitPrice()
                    && PriceUtils.isAppropriatePrice(orderDetail.getLimitPrice()));
        } else if (orderDetail.getOrderType() == HostingExecOrderType.ORDER_WITH_CONDITION) {
            ParameterChecker.checkInnerArgument(orderDetail.isSetLimitPrice()
                    && PriceUtils.isAppropriatePrice(orderDetail.getLimitPrice()));
            ParameterChecker.checkInnerArgument(orderDetail.isSetCondition());
            ParameterChecker.checkInnerArgument(orderDetail.isSetConditionPrice()
                    && PriceUtils.isAppropriatePrice(orderDetail.getConditionPrice()));
        } else {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "Unsupported order type");
        }
        
        ParameterChecker.checkInnerArgument(orderDetail.isSetOrderCreatorType());
        ParameterChecker.checkInnerArgument(orderDetail.isSetOrderMode());
        if (orderDetail.getOrderMode() == HostingExecOrderMode.ORDER_GTD) {
            ParameterChecker.checkInnerArgument(orderDetail.isSetEffectiveDateTime() 
                    && StringUtils.isNotEmpty(orderDetail.getEffectiveDateTime())
                    && orderDetail.getEffectiveDateTime().length() == 19);
            checkEffectiveDatetimeFormat(orderDetail.getEffectiveDateTime());
        }
    }
}
