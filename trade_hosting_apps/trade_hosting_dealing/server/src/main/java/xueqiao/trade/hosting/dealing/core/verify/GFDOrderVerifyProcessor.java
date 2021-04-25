package xueqiao.trade.hosting.dealing.core.verify;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.dealing.core.verify.IVerifyProcessor.VerifyResult.VerifyResultType;
import xueqiao.trade.hosting.BrokerTechPlatform;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;
import xueqiao.trade.hosting.HostingOrderRouteCommodityCodeNode;
import xueqiao.trade.hosting.HostingOrderRouteCommodityTypeNode;
import xueqiao.trade.hosting.HostingOrderRouteExchangeNode;
import xueqiao.trade.hosting.HostingOrderRouteRelatedInfo;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.HostingTradeAccount;
import xueqiao.trade.hosting.TradeAccountState;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.dealing.core.verify.orderext.CTPExecOrderInputExtFiller;
import xueqiao.trade.hosting.dealing.core.verify.orderext.ESunny9OrderInputExtFiller;
import xueqiao.trade.hosting.dealing.core.verify.orderext.ExecOrderInputExtFiller;
import xueqiao.trade.hosting.order.route.OrderRouteTreeCache;
import xueqiao.trade.hosting.risk.dealing.thriftapi.EHostingExecOrderRiskActionType;
import xueqiao.trade.hosting.risk.dealing.thriftapi.HostingExecOrderRiskAction;
import xueqiao.trade.hosting.risk.helper.RiskStubFactory;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.IHostingTradeAccountApi;
import xueqiao.trade.hosting.storage.apis.ext.GFDOrderCalculator;
import xueqiao.trade.hosting.storage.apis.ext.TradingTimeAnalysisor;

public class GFDOrderVerifyProcessor implements IVerifyProcessor {
    
    private IHostingTradeAccountApi mTradeAccountApi;
    private IHostingContractApi mContractApi;
    
    public GFDOrderVerifyProcessor() {
        mTradeAccountApi = Globals.getInstance().queryInterfaceForSure(IHostingTradeAccountApi.class);
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
    }
    
    @Override
    public VerifyResult verify(HostingExecOrder inputOrder) throws Exception {
        VerifyResult result = new VerifyResult();
        
        HostingOrderRouteRelatedInfo routeRelatedInfo = chooseTradeAccount(inputOrder);
        if (routeRelatedInfo == null) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_NOT_FOUND_FORSUBACCOUNT.getValue());
            return result;
        }
        if (routeRelatedInfo.isForbidden()) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_COMMODITY_TRADE_FORBIDDEN.getValue());
            return result;
        }
        
        HostingExecOrderTradeAccountSummary accountSummary = new HostingExecOrderTradeAccountSummary();
        accountSummary.setTradeAccountId(routeRelatedInfo.getMainTradeAccountId());
        HostingTradeAccount tradeAccount = mTradeAccountApi.getTradeAccount(routeRelatedInfo.getMainTradeAccountId());
        if (tradeAccount == null) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_TRADE_ACCOUNT_NOT_EXISTED.getValue());
            return result;
        }
        if (tradeAccount.getAccountState() != TradeAccountState.ACCOUNT_NORMAL) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_DISABLED.getValue());
            return result;
        }
        
        accountSummary.setBrokerId(tradeAccount.getTradeBrokerId());
        accountSummary.setTechPlatform(tradeAccount.getBrokerTechPlatform());
        result.setTradeAccountSummary(accountSummary);
        
        HostingExecOrderInputExt orderInputExt = new HostingExecOrderInputExt();
        List<HostingExecOrderLegContractSummary> outLegContractSummary 
            = new ArrayList<>();
        
        int failedErrorCode = fillOrderInputExt(inputOrder, accountSummary, orderInputExt, outLegContractSummary);
        if (failedErrorCode != 0) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(failedErrorCode);
            return result;
        }
        
        TradingTimeAnalysisor analysisor = new TradingTimeAnalysisor(mContractApi);
        analysisor.setCommodityId(inputOrder.getContractSummary().getSledCommodityId());
        analysisor.setProcessTimestampMs(System.currentTimeMillis());
        if (!analysisor.analysis()) {
            AppLog.e(analysisor);
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED.getValue());
            return result;
        }
        
        GFDOrderCalculator cal = new GFDOrderCalculator(analysisor);
        if (!cal.calculate()) {
            AppLog.e(cal);
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_CALCULTE_TRADETIME_FAILED.getValue());
            return result;
        }
        
        if (AppLog.debugEnabled()) {
            AppLog.d(cal);
        }
        if (cal.isMarketClosed()) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_REJECT_CONTRACT_NOTIN_TRADE_TIME.getValue());
            return result;
        }

        // 风控检测
        HostingExecOrderRiskAction riskAction = RiskStubFactory.getDealingStub().riskCheck(inputOrder.getSubAccountId()
            , inputOrder.getContractSummary()
            , inputOrder.getOrderDetail());
        if (riskAction.getActionType() == EHostingExecOrderRiskActionType.RISK_FORBIDDEN) {
            result.setResultType(VerifyResultType.VERIFY_FAILED);
            result.setFailedVerifyErrorCode(TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_RISK_FORBIDDEN.getValue());
            if (StringUtils.isNotEmpty(riskAction.getActionMessage())) {
                result.setStatusAppendMsg(riskAction.getActionMessage());
            }
            return result;
        }
        
        result.setResultType(VerifyResultType.VERIFY_SUCCESS);
        result.setOrderInputExt(orderInputExt);
        result.setLegContractSummarys(outLegContractSummary);
        result.setTtlTimestampMs(cal.getTTLTimestampMs());
        
        return result;
    }
    
//    private static class ChooseTradeAccountResult {
//        private boolean found;
//        private long tradeAccountId;
//        
//        private ChooseTradeAccountResult(long tradeAccountId) {
//            if (tradeAccountId <= 0) {
//                found = false;
//            } else {
//                found = true;
//                this.tradeAccountId = tradeAccountId;
//            }
//        }
//        
//        public boolean isFound() {
//            return found;
//        }
//        
//        public long getTradeAccountId() {
//            return tradeAccountId;
//        }
//        
//        public static final ChooseTradeAccountResult NOT_FOUND = new ChooseTradeAccountResult(0);
//    };
    
//    private ChooseTradeAccountResult OrderRelatedInfo2Result(HostingOrderRouteRelatedInfo relatedInfo) {
//        if (relatedInfo == null || relatedInfo.isForbidden()) {
//            return ChooseTradeAccountResult.NOT_FOUND;
//        } 
//        return new ChooseTradeAccountResult(relatedInfo.getMainTradeAccountId());
//    }
    
    private HostingOrderRouteRelatedInfo chooseTradeAccount(HostingExecOrder inputOrder) {
        HostingOrderRouteTree routeTree = OrderRouteTreeCache.Global().get(inputOrder.getSubAccountId());
        if (routeTree == null || routeTree.getSubExchangeNodes() == null) {
            if (AppLog.infoEnabled()) {
                AppLog.i("SubAccount has no route configs for subAccountId=" + inputOrder.getSubAccountId());
            }
            return null;
        }
        
        HostingOrderRouteRelatedInfo choosedRelatedInfo = null;
        HostingOrderRouteExchangeNode exchangeNode = null;
        HostingOrderRouteCommodityTypeNode commodityTypeNode = null;
        HostingOrderRouteCommodityCodeNode commodityCodeNode = null;
        do {
            exchangeNode = routeTree.getSubExchangeNodes().get(inputOrder.getContractSummary().getSledExchangeMic());
            if(exchangeNode == null) {
                break;
            }
            
            if (exchangeNode.getSubCommodityTypeNodes() != null) {
                commodityTypeNode = exchangeNode.getSubCommodityTypeNodes().get(inputOrder.getContractSummary().getSledCommodityType());
            }
            if (commodityTypeNode == null) {
                choosedRelatedInfo = exchangeNode.getRelatedInfo();
                break;
            }
        
            if (commodityTypeNode.getSubCommodityCodeNodes() != null) {
                commodityCodeNode = commodityTypeNode.getSubCommodityCodeNodes().get(inputOrder.getContractSummary().getSledCommodityCode());
            }
            if (commodityCodeNode == null) {
                choosedRelatedInfo = commodityTypeNode.getRelatedInfo();
                break;
            }
        
            choosedRelatedInfo = commodityCodeNode.getRelatedInfo();
        } while(false);
        
        if (AppLog.infoEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("SubUserId=").append(inputOrder.getSubUserId())
                      .append(" exchangeCode=").append(inputOrder.getContractSummary().getSledExchangeMic())
                      .append(" commodityType=").append(inputOrder.getContractSummary().getSledCommodityType())
                      .append(" commodityCode=").append(inputOrder.getContractSummary().getSledCommodityCode())
                      .append(" -> exchangeNode=").append(exchangeNode)
                      .append(" -> commodityTypeNode=").append(commodityTypeNode)
                      .append(" -> commodityCodeNode=").append(commodityCodeNode)
                      .append(" -> choosedRelatedInfo=").append(choosedRelatedInfo);
            AppLog.i(logBuilder.toString());
        }
        
        return choosedRelatedInfo;
    }
    
    private int fillOrderInputExt(HostingExecOrder inputOrder
            , HostingExecOrderTradeAccountSummary inputAccountSummary
            , HostingExecOrderInputExt outputOrderInputExt
            , List<HostingExecOrderLegContractSummary> outputLegContractSummary) throws Exception {
        ExecOrderInputExtFiller filler = null;
        if (inputAccountSummary.getTechPlatform() == BrokerTechPlatform.TECH_CTP) {
            filler = new CTPExecOrderInputExtFiller();
        } else if (inputAccountSummary.getTechPlatform() == BrokerTechPlatform.TECH_ESUNNY_9) {
        	filler = new ESunny9OrderInputExtFiller();
        }
        
        if (filler == null) {
            return TradeHostingBasicErrorCode.ERROR_EXEC_ORDER_VERIFY_FAILED_ACCOUNT_ORDER_NOT_SUPPORTED.getValue();
        }
        
        filler.setInputOrder(inputOrder);
        filler.setOutputOrderInputExt(outputOrderInputExt);
        filler.setContractOnlineStub(mContractApi.getContractOnlineStub());
        filler.setOutputLegContractSummary(outputLegContractSummary);
        return filler.process();
    }

}
