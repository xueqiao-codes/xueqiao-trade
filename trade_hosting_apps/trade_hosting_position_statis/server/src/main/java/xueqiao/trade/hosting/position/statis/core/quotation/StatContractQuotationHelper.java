package xueqiao.trade.hosting.position.statis.core.quotation;

import com.antiy.error_code.ErrorCodeInner;
import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityType;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class StatContractQuotationHelper {
    public static String getQuotationPlatform(TechPlatformEnv env) {
        if (TechPlatformEnv.SIM.equals(env)) {
            return "SXQ";
        }
        return "XQ";
    }

    public static String getQuotationContractSymbol(String exchangeMic, SledCommodityType sledCommodityType, String sledCommodityCode, String sledContractCode) throws UnsupportedEncodingException {
        Preconditions.checkNotNull(sledCommodityType);
        StringBuilder contractSymbolBuilder = new StringBuilder(64);
        contractSymbolBuilder.append(exchangeMic)
                .append("|").append(sledCommodityType.getValue())
                .append("|").append(sledCommodityCode)
                .append("|").append(sledContractCode);
        return URLEncoder.encode(contractSymbolBuilder.toString(), "UTF-8");
    }

    /**
     * 注册监听 合约 行情
     */
    public static void register(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        long sledContractId = Long.valueOf(targetKey);
        StatQuotationListener listener = StatQuotationListenerFactory.getInstance().getContractQuotationListener(subAccountId, targetKey, targetType);
        register(sledContractId, listener);
    }

    /**
     * 注销监 合约 行情
     */
    public static void unRegister(long subAccountId, String targetKey, HostingXQTargetType targetType) throws ErrorInfo {
        long sledContractId = Long.valueOf(targetKey);
        StatQuotationListener listener = StatQuotationListenerFactory.getInstance().getContractQuotationListener(subAccountId, targetKey, targetType);
        unRegister(sledContractId, listener);
    }

    /**
     * 注册监听一个行情
     * 只适用于单个合约的行情监听
     */
    public static void register(long sledContractId, IQuotationListener listener) throws ErrorInfo {
        try {
            ContractSymbol contractSymbol = new ContractSymbol(sledContractId).invoke();
            String platform = contractSymbol.getPlatform();
            String symbol = contractSymbol.getSymbol();
            if (QuotationDispatcher.Global() == null) {
                AppLog.e(" ################## QuotationDispatcher.Global() is null");
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "QuotationDispatcher.Global() is null, registerListener fail, sledContractId : " + sledContractId);
            }
            QuotationDispatcher.Global().registerListener(platform, symbol, listener);
        } catch (UnsupportedEncodingException e) {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), e.toString());
        }
    }

    public static void unRegister(long sledContractId, IQuotationListener listener) throws ErrorInfo {
        try {
            ContractSymbol contractSymbol = new ContractSymbol(sledContractId).invoke();
            String platform = contractSymbol.getPlatform();
            String symbol = contractSymbol.getSymbol();
            if (QuotationDispatcher.Global() == null) {
                AppLog.e(" ################## QuotationDispatcher.Global() is null");
                throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), "QuotationDispatcher.Global() is null, unRegisterListener fail, sledContractId : " + sledContractId);
            }
            QuotationDispatcher.Global().unRegisterListener(platform, symbol, listener);
        } catch (UnsupportedEncodingException e) {
            throw new ErrorInfo(ErrorCodeInner.SERVER_INNER_ERROR.getErrorCode(), e.toString());
        }
    }

    private static class ContractSymbol {
        private long sledContractId;
        private String platform;
        private String symbol;

        public ContractSymbol(long sledContractId) {
            this.sledContractId = sledContractId;
        }

        public String getPlatform() {
            return platform;
        }

        public String getSymbol() {
            return symbol;
        }

        public ContractSymbol invoke() throws ErrorInfo, UnsupportedEncodingException {
            IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            SledContractDetails details = api.getContractDetailForSure(sledContractId);
            platform = getQuotationPlatform(details.getSledContract().getPlatformEnv());
            symbol = getQuotationContractSymbol(details.getSledCommodity().getExchangeMic(),
                    details.getSledCommodity().getSledCommodityType(),
                    details.getSledCommodity().getSledCommodityCode(),
                    details.getSledContract().getSledContractCode());
//            AppLog.i("register platform: " + platform + " symbol: " + symbol);
            return this;
        }
    }
}
