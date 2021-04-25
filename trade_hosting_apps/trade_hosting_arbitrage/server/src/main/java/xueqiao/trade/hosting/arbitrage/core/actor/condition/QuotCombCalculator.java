package xueqiao.trade.hosting.arbitrage.core.actor.condition;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.soldier.base.logger.AppLog;

import com.google.common.base.Preconditions;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Set;

import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeGraphEnv;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.framework.utils.PriceUtils;

/**
 *  组合行情计算器
 */
public class QuotCombCalculator {
    
    private Expression mFormularExpression;
    private HostingComposeGraph mComposeGraph;
    private Map<String, HostingComposeLeg> mContractSymbol2Legs = new HashMap<String, HostingComposeLeg>();
    private Map<String, QuotationItem> mContractQuotationItemLastest = new HashMap<String, QuotationItem>();
    
    private QuotationItem mOutputCombQuotationItem = new QuotationItem();
    
    public QuotCombCalculator(HostingComposeGraph composeGraph) throws UnsupportedEncodingException {
        Preconditions.checkNotNull(composeGraph);
        Preconditions.checkArgument(composeGraph.isSetComposeGraphEnv());
        mComposeGraph = composeGraph;
        construtContractSymbo2Legs();
        mFormularExpression 
            = new ExpressionBuilder(mComposeGraph.getFormular()).variables(mComposeGraph.getLegs().keySet()).build();
        
        mOutputCombQuotationItem.setPlatform("COMB");
        mOutputCombQuotationItem.setContractSymbol(String.valueOf(getComposeGraphId()));
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getComposeGraphId());
        return builder.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (!(o instanceof QuotCombCalculator)) {
            return false;
        }
        
        return this.getComposeGraphId() == ((QuotCombCalculator)o).getComposeGraphId();
    }
    
    private void construtContractSymbo2Legs() throws UnsupportedEncodingException {
        for (Entry<String, HostingComposeLeg> composeLeg : mComposeGraph.getLegs().entrySet()) {
            Preconditions.checkArgument(composeLeg.getValue().isSetSledExchangeMic());
            Preconditions.checkArgument(composeLeg.getValue().isSetSledCommodityType());
            Preconditions.checkArgument(composeLeg.getValue().isSetSledCommodityCode());
            Preconditions.checkArgument(composeLeg.getValue().isSetSledContractCode());
            
            StringBuilder contractSymbolBuilder = new StringBuilder(64);
            contractSymbolBuilder.append(composeLeg.getValue().getSledExchangeMic())
                                 .append("|").append(composeLeg.getValue().getSledCommodityType())
                                 .append("|").append(composeLeg.getValue().getSledCommodityCode())
                                 .append("|").append(composeLeg.getValue().getSledContractCode());
            String contractSymbol = URLEncoder.encode(contractSymbolBuilder.toString(), "UTF-8");
            mContractSymbol2Legs.put(contractSymbol, composeLeg.getValue());
        }
    }
    
    private void calculate() {
        Double bidPrice = calculateBidPrice();
        if (bidPrice != null) {
            mOutputCombQuotationItem.setBidPrice(Arrays.asList(bidPrice));
        } else {
            mOutputCombQuotationItem.unsetBidPrice();
        }
        
        Double askPrice = calculateAskPrice();
        if (askPrice != null) {
            mOutputCombQuotationItem.setAskPrice(Arrays.asList(askPrice));
        } else {
            mOutputCombQuotationItem.unsetAskPrice();
        }
        
//        Long bidQty = calculateBidQty();
//        if (bidQty != null) {
//            mOutputCombQuotationItem.setBidQty(Arrays.asList(bidQty));
//        } else {
//            mOutputCombQuotationItem.unsetBidQty();
//        }
//        
//        Long askQty = calculateAskQty();
//        if (askQty != null) {
//            mOutputCombQuotationItem.setAskQty(Arrays.asList(askQty));
//        } else {
//            mOutputCombQuotationItem.unsetAskQty();
//        }
        
        Double lastPrice = calculateLastPrice();
        if (lastPrice != null) {
            mOutputCombQuotationItem.setLastPrice(lastPrice);
        } else {
            mOutputCombQuotationItem.unsetLastPrice();
        }
        
        mOutputCombQuotationItem.setUpdateTimestampMs(System.currentTimeMillis());
        mOutputCombQuotationItem.setRaceTimestampMs(mOutputCombQuotationItem.getUpdateTimestampMs());
    }
    
    private Double calculateBidPrice() {
        for (Entry<String, QuotationItem> qEntry : mContractQuotationItemLastest.entrySet()) {
            HostingComposeLeg composeLeg = mContractSymbol2Legs.get(qEntry.getKey());
            if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
                if (qEntry.getValue().getBidPriceSize() <= 0) {
                    return null;
                }
                mFormularExpression.setVariable(composeLeg.getVariableName(), qEntry.getValue().getBidPrice().get(0));
            } else {
                if (qEntry.getValue().getAskPriceSize() <= 0) {
                    return null;
                }
                mFormularExpression.setVariable(composeLeg.getVariableName(), qEntry.getValue().getAskPrice().get(0));
            }
        }
        return evaluateExpression();
    }
    
//    private Long calculateBidQty() {
//        long minBidQty = Long.MAX_VALUE;
//        for (Entry<String, QuotationItem> qEntry : mContractQuotationItemLastest.entrySet()) {
//            HostingComposeLeg composeLeg = mContractSymbol2Legs.get(qEntry.getKey());
//            if (composeLeg.getQuantity() <= 0) {
//                continue;
//            }
//            
//            long legBidQty = 0;
//            if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
//                if (qEntry.getValue().getBidQtySize() <= 0) {
//                    return null;
//                }
//                
//                legBidQty = qEntry.getValue().getBidQty().get(0)/composeLeg.getQuantity();
//            } else {
//                if (qEntry.getValue().getAskQtySize() <= 0) {
//                    return null;
//                }
//                legBidQty = qEntry.getValue().getAskQty().get(0)/composeLeg.getQuantity();
//            }
//            
//            if (legBidQty < minBidQty) {
//                minBidQty = legBidQty;
//            }
//        }
//        
//        if (minBidQty == Long.MAX_VALUE) {
//            return null;
//        }
//        
//        return minBidQty;
//    }
    
    private Double calculateAskPrice() {
        for (Entry<String, QuotationItem> qEntry : mContractQuotationItemLastest.entrySet()) {
            HostingComposeLeg composeLeg = mContractSymbol2Legs.get(qEntry.getKey());
            if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
                if (qEntry.getValue().getAskPriceSize() <= 0) {
                    return null;
                }
                mFormularExpression.setVariable(composeLeg.getVariableName(), qEntry.getValue().getAskPrice().get(0));
            } else {
                if (qEntry.getValue().getBidPriceSize() <= 0) {
                    return null;
                }
                mFormularExpression.setVariable(composeLeg.getVariableName(), qEntry.getValue().getBidPrice().get(0));
            }
        }
        return evaluateExpression();
    }
    
//    private Long calculateAskQty() {
//        long minAskQty = Long.MAX_VALUE;
//        for (Entry<String, QuotationItem> qEntry : mContractQuotationItemLastest.entrySet()) {
//            HostingComposeLeg composeLeg = mContractSymbol2Legs.get(qEntry.getKey());
//            if (composeLeg.getQuantity() <= 0) {
//                continue;
//            }
//            
//            long legAskQty = 0;
//            if (composeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
//                if (qEntry.getValue().getAskQtySize() <= 0) {
//                    return null;
//                }
//                legAskQty = qEntry.getValue().getAskQty().get(0)/composeLeg.getQuantity();
//            } else {
//                if (qEntry.getValue().getBidQtySize() <= 0) {
//                    return null;
//                }
//                legAskQty = qEntry.getValue().getBidQty().get(0)/composeLeg.getQuantity();
//            }
//            
//            if (legAskQty < minAskQty) {
//                minAskQty = legAskQty;
//            }
//        }
//        
//        if (minAskQty == Long.MAX_VALUE) {
//            return null;
//        }
//        return minAskQty;
//    }
    
    private Double calculateLastPrice() {
        for (Entry<String, QuotationItem> qEntry : mContractQuotationItemLastest.entrySet()) {
            HostingComposeLeg composeLeg = mContractSymbol2Legs.get(qEntry.getKey());
            if (qEntry.getValue().isSetLastPrice()
                    && PriceUtils.isAppropriatePrice(qEntry.getValue().getLastPrice())) {
                mFormularExpression.setVariable(composeLeg.getVariableName(), qEntry.getValue().getLastPrice());
                continue;
            }
            
            return null;
        }
        return evaluateExpression();
    }
    
    public long getComposeGraphId() {
        return mComposeGraph.getComposeGraphId();
    }
    
    private Double evaluateExpression() {
        try {
            double result =  mFormularExpression.evaluate();
            if (Double.isNaN(result)) {
                return null;
            }
            return result;
        } catch (Throwable e) {
            AppLog.d(e.getMessage(), e);
        }
        return null;
    }
    
    /**
     *  触发重新计算时返回true
     * @param quotation
     * @return
     */
    public boolean onReceivedQuotationItem(final QuotationItem newQuotation) {
        if (newQuotation == null) {
            return false;
        }
        
        if (!mContractSymbol2Legs.containsKey(newQuotation.getContractSymbol())) {
            return false;
        }
        
        QuotationItem originQuotation = mContractQuotationItemLastest.put(newQuotation.getContractSymbol(), newQuotation);
        if (originQuotation != null && isQuotationSame(originQuotation, newQuotation)) {
            return false;
        }
        
        // 每条腿的行情必须都具备才能计算
        if (mContractQuotationItemLastest.size() < mContractSymbol2Legs.size()) {
            return false;
        }
        
        calculate();
        return true;
    }
    
    private boolean isQuotationSame(QuotationItem originQuotation, QuotationItem newQuotation) {
        if (originQuotation.getAskPriceSize() <= 0
                || originQuotation.getAskQtySize() <= 0
                || originQuotation.getBidPriceSize() <= 0
                || originQuotation.getBidQtySize() <= 0) {
            return false;
        }
        if (newQuotation.getAskPriceSize() <= 0
                || newQuotation.getAskQtySize() <= 0
                || newQuotation.getBidPriceSize() <= 0
                || newQuotation.getBidQtySize() <= 0) {
            return false;
        }
        
        if (originQuotation.getBidPrice().get(0).equals(newQuotation.getBidPrice().get(0))
                && originQuotation.getBidQty().get(0).equals(newQuotation.getBidQty().get(0))
                && originQuotation.getAskPrice().get(0).equals(newQuotation.getAskPrice().get(0))
                && originQuotation.getAskQty().get(0).equals(newQuotation.getAskQty().get(0))
                && (originQuotation.isSetLastPrice() && newQuotation.isSetLastPrice() 
                    && 0 == Double.compare(originQuotation.getLastPrice(), newQuotation.getLastPrice()))) {
            return true;
        }
        return false;
    }
    
    public String getPlatform() {
        if (mComposeGraph.getComposeGraphEnv() == HostingComposeGraphEnv.COMPOSE_GRAPH_SIM) {
            return "SXQ";
        }
        return "XQ";
    }
    
    public Set<String> getRelatedContractSymbols() {
        return mContractSymbol2Legs.keySet();
    }
    
    public QuotationItem getCombQuotationItem() {
        return new QuotationItem(mOutputCombQuotationItem);
    }
}
