package xueqiao.trade.hosting.arbitrage.core.composelimit.quot;

import com.google.common.base.Preconditions;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.apache.commons.lang.StringUtils;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.soldier.base.logger.AppLog;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *  套利行情计算器，根据已有成交和目标套数，计算所需价差和腿目标价
 * @author wangli
 *
 */
public class ComposeQuotCalculator {
    private static final String RESULT_VARIABLE_NAME = "Z";
    
    private Map<String, ComposeQuotData> mContractSymbol2Datas = new HashMap<String, ComposeQuotData>();
    private Map<Long, ComposeQuotData> mContractId2Datas = new HashMap<Long, ComposeQuotData>();
    private Expression mExpr;

    private int mTargetVolume;

    private static class StatTrade {
        private double turnover = 0.0;  //成交总额
        private int volume = 0;  // 成交总量

        public double getTurnover() {
            return turnover;
        }

        public StatTrade addTurnover(double addedTurnover) {
            this.turnover += addedTurnover;
            return this;
        }

        public int getVolume() {
            return volume;
        }

        public StatTrade addVolume(int addedVolume) {
            this.volume += addedVolume;
            return this;
        }
    }

    private Map<Long, StatTrade> mStatTrades = new HashMap<>();

    public ComposeQuotCalculator(HostingComposeGraph composeGraph, int targetVolume) {
        Preconditions.checkNotNull(composeGraph);
        Preconditions.checkArgument(targetVolume >= 1 );
        this.mTargetVolume = targetVolume;

        for (HostingComposeLeg composeLeg : composeGraph.getLegs().values()) {
            ComposeQuotData quotData = new ComposeQuotData();
            quotData.setComposeLeg(composeLeg);
            
            mContractId2Datas.put(composeLeg.getSledContractId(), quotData);
            StringBuilder contractSymbolBuilder = new StringBuilder(64);
            contractSymbolBuilder.append(composeLeg.getSledExchangeMic())
                                 .append("|").append(composeLeg.getSledCommodityType())
                                 .append("|").append(composeLeg.getSledCommodityCode())
                                 .append("|").append(composeLeg.getSledContractCode());
            try {
                mContractSymbol2Datas.put(URLEncoder.encode(contractSymbolBuilder.toString(), "UTF-8")
                        , quotData);
            } catch (UnsupportedEncodingException e) {
                AppLog.e(e.getMessage(), e);
            }
            
            // 构建反向计算公式
            ExprEvaluator expr = new ExprEvaluator();
            IExpr result = expr.eval("Roots(" + RESULT_VARIABLE_NAME 
                    + "==(" + composeGraph.getFormular() +")," + composeLeg.getVariableName() + ")");
            if (result.isFalse() || result.toString().contains("||")) {
                AppLog.w("[WARNING]No expression with leg=" + composeLeg.getVariableName() 
                    + " for graph=" + composeGraph.getComposeGraphId());
                continue;
            }
            String[] splits = StringUtils.split(result.toScript(), "==");
            if (splits == null || splits.length != 2) {
                AppLog.w("[WARNING]No expression with leg=" + composeLeg.getVariableName() 
                    + " for graph=" + composeGraph.getComposeGraphId());
                continue;
            }
            
            Set<String> variablesSet = new HashSet<>(composeGraph.getLegs().keySet());
            variablesSet.remove(composeLeg.getVariableName());
            variablesSet.add(RESULT_VARIABLE_NAME);
            quotData.setExpr(splits[1], new ExpressionBuilder(splits[1]).variables(variablesSet).build());
        }

        mExpr = new ExpressionBuilder(composeGraph.getFormular()).variables(composeGraph.getLegs().keySet()).build();
    }

    public int getTargetVolume() {
        return mTargetVolume;
    }

    public List<QuotationItem> getLastestQuotationItems() {
        List<QuotationItem> quotationItems = new ArrayList<>(mContractId2Datas.size());
        for (ComposeQuotData quotData : mContractId2Datas.values()) {
            if (quotData.getLastQuotationItem() != null) {
                quotationItems.add(quotData.getLastQuotationItem());
            }
        }
        return quotationItems;
    }
    
    /**
     * 计算价差
     *  1. 买时，计算组合行情的卖价
     *  2. 卖时，计算组合行情的买价
     */
    public Double getGapPrice(HostingXQTradeDirection direction) {
        for (ComposeQuotData quotData : mContractId2Datas.values()) {
            Double price = getAveragePrice(quotData, direction);
            if (price == null) {
                return null;
            }
            mExpr.setVariable(quotData.getComposeLeg().getVariableName(), price);
        }
        
        try {
            return mExpr.evaluate();
        } catch (Throwable e) {
            AppLog.e(e.getMessage());
        }
        return null;
    }
    
    private Double getQuotPrice(ComposeQuotData quotData, HostingXQTradeDirection direction) {
        // 买方向
        if (direction == HostingXQTradeDirection.XQ_BUY) {
            if (quotData.getComposeLeg().getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
                return quotData.getAskPrice1();
            } 
            return quotData.getBidPrice1();
        } 
        
        // 卖方向
        if (quotData.getComposeLeg().getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
            return quotData.getBidPrice1();
        } else {
            return quotData.getAskPrice1();
        }
    }
    
    public ComposeQuotData getQuotData(long sledContractId) {
        return mContractId2Datas.get(sledContractId);
    }
    public ComposeQuotData getQuotData(String contractSymbols) {
        return mContractSymbol2Datas.get(contractSymbols);
    }

    public boolean canCalTargetPrice(long sledContractId) {
        ComposeQuotData legQuotData = mContractId2Datas.get(sledContractId);
        if (legQuotData == null) {
            return false;
        }

        if (legQuotData.getExpr() == null) {
            return false;
        }

        return true;
    }

    /**
     *  计算腿的目标价
     */
    public Double getTargetPrice(long sledContractId
            , HostingXQTradeDirection direction
            , double limitPrice) {
        ComposeQuotData legQuotData = mContractId2Datas.get(sledContractId);
        if (legQuotData == null) {
            return null;
        }
        if (legQuotData.getExpr() == null) {
            return null;
        }

        for (ComposeQuotData otherLegQuotData : mContractId2Datas.values()) {
            if (otherLegQuotData.getComposeLeg().getSledContractId() == sledContractId) {
                continue;
            }
            
            Double price = getAveragePrice(otherLegQuotData, direction);
            if (price == null) {
                return null;
            }
            legQuotData.getExpr().setVariable(otherLegQuotData.getComposeLeg().getVariableName(), price);
        }

        legQuotData.getExpr().setVariable(RESULT_VARIABLE_NAME, limitPrice);

        double targetLegAveragePrice;
        try {
            targetLegAveragePrice = legQuotData.getExpr().evaluate();
        } catch (Throwable e) {
            AppLog.e(e.getMessage());
            return null;
        }

        StatTrade st = mStatTrades.get(sledContractId);
        if (st == null) {
            return targetLegAveragePrice;
        }

        int totalVolume =  mTargetVolume * legQuotData.getComposeLeg().getQuantity();
        int quotVolume = totalVolume - st.getVolume();
        if (quotVolume <= 0) {
            return null;
        }

        return (totalVolume * targetLegAveragePrice - st.getTurnover()) / quotVolume;
    }
    
    /**
     * @return 返回是否更新的标志
     */
    public boolean updateQuotationItem(QuotationItem quotationItem) {
        ComposeQuotData quotData = mContractSymbol2Datas.get(quotationItem.getContractSymbol());
        if (quotData == null) {
            return false;
        }
        
        quotData.setLastQuotationItem(quotationItem);
        return true;
    }

    public void updateTradeInfo(Collection<XQTradeWithRelatedItem> trades
            , Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfos) {
        mStatTrades.clear();

        if (trades != null) {
            for (XQTradeWithRelatedItem trade : trades) {
                for (XQTradeRelatedItem relatedItem : trade.getRelatedItems().values()) {
                    StatTrade st = mStatTrades.get(relatedItem.getSledContractId());
                    if (st == null) {
                        st = new StatTrade();
                        mStatTrades.put(relatedItem.getSledContractId(), st);
                    }
                    st.addVolume(relatedItem.getRelatedTradeVolume())
                            .addTurnover(relatedItem.getExecTradeLegPrice() * relatedItem.getRelatedTradeVolume());
                }
            }
        }

        if (unRelatedTradeLegInfos == null) {
            return ;
        }

        for (Map.Entry<Long, TreeSet<UnRelatedExecTradeLegInfo>> tradeLegInfoSetEntry : unRelatedTradeLegInfos.entrySet()) {
            for (UnRelatedExecTradeLegInfo tradeLegInfo : tradeLegInfoSetEntry.getValue()) {
                StatTrade st = mStatTrades.get(tradeLegInfoSetEntry.getKey());
                if (st == null) {
                    st = new StatTrade();
                    mStatTrades.put(tradeLegInfoSetEntry.getKey(), st);
                }

                st.addVolume(tradeLegInfo.getLeftVolume())
                  .addTurnover(tradeLegInfo.getTradeLeg().getTradeLegInfo().getLegTradePrice()
                          * tradeLegInfo.getLeftVolume());
            }
        }
    }

    private Double getAveragePrice(ComposeQuotData quotData, HostingXQTradeDirection direction) {
        Double quotPrice = this.getQuotPrice(quotData, direction);
        if (quotPrice == null) {
            return quotPrice;
        }

        StatTrade st = mStatTrades.get(quotData.getComposeLeg().getSledContractId());
        if (st == null) {
            return quotPrice;
        }

        int totalVolume =  mTargetVolume * quotData.getComposeLeg().getQuantity();
        int quotVolume = totalVolume - st.getVolume();

        return (st.getTurnover() + quotVolume * quotPrice)/ totalVolume;
    }

}
