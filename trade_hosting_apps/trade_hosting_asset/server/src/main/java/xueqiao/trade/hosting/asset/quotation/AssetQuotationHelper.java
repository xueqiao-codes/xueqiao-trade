package xueqiao.trade.hosting.asset.quotation;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityType;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import com.longsheng.xueqiao.contract.standard.thriftapi.TechPlatformEnv;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.asset.actor.AssetActorFactory;
import xueqiao.trade.hosting.asset.actor.IAssetActor;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.core.ContractGlobal;
import xueqiao.trade.hosting.asset.thriftapi.HostingSledContractPosition;
import xueqiao.trade.hosting.quot.dispatcher.IQuotationListener;
import xueqiao.trade.hosting.quot.dispatcher.QuotationDispatcher;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AssetQuotationHelper {

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

    public static void registerQuotation(long subAccountId, long sledContractId) throws ErrorInfo {
        IAssetActor listener = AssetActorFactory.getInstance().getAssetActor(sledContractId, AssetCalculatorFactory.SUB_ACCOUNT_POSITION_FUND_KEY, subAccountId);
        try {
            register(sledContractId,
                    (IQuotationListener) listener, subAccountId);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void register(long sledContractId, IQuotationListener listener, long subAccountId) throws ErrorInfo, UnsupportedEncodingException {

        ContractSymbol contractSymbol = new ContractSymbol(sledContractId, subAccountId).invoke();
        String platform = contractSymbol.getPlatform();
        String symbol = contractSymbol.getSymbol();

        QuotationDispatcher.Global().registerListener(platform, symbol, listener);
    }

    private static class ContractSymbol {
        private long subAccountId;
        private long sledContractId;
        private String platform;
        private String symbol;

        public ContractSymbol(long sledContractId, long subAccountId) {
            this.sledContractId = sledContractId;
            this.subAccountId = subAccountId;
        }

        public String getPlatform() {
            return platform;
        }

        public String getSymbol() {
            return symbol;
        }

        public ContractSymbol invoke() throws ErrorInfo, UnsupportedEncodingException {
            ContractGlobal contractGlobal = AssetGlobalDataFactory.getInstance().getContractGlobalData(subAccountId);

            IHostingContractApi api = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            SledContractDetails details = api.getContractDetailForSure(sledContractId);
            platform = AssetQuotationHelper.getQuotationPlatform(details.getSledContract().getPlatformEnv());
            symbol = AssetQuotationHelper.getQuotationContractSymbol(details.getSledCommodity().getExchangeMic(),
                    details.getSledCommodity().getSledCommodityType(),
                    details.getSledCommodity().getSledCommodityCode(),
                    details.getSledContract().getSledContractCode());

            contractGlobal.putQuotationSymbol(symbol, platform, sledContractId);
            return this;
        }
    }
}
