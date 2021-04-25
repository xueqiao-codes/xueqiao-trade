package xueqiao.trade.hosting.arbitrage.core.executor;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.core.IDGenerator;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.XQOrderSubExecutor;
import xueqiao.trade.hosting.arbitrage.core.data.CalculateTradesResult;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeSummary;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

/**
 *  单合约交易执行器的基础, 计算成交的方式一致
 */
public abstract class XQOrderContractBaseExecutor extends XQOrderBaseExecutor {
    
    protected long mSledContractId;
    
    public XQOrderContractBaseExecutor(HostingXQOrder order) throws ErrorInfo {
        super(order);
    }
    
    public long getSledContractId() {
        return mSledContractId;
    }
    
    @Override
    protected void checkOrderTarget(HostingXQTarget target) throws ErrorInfo {
        ParameterChecker.check(target.getTargetType() == HostingXQTargetType.CONTRACT_TARGET
                , "target's type should be set to contract target");
        ParameterChecker.check(target.isSetTargetKey() && StringUtils.isNotEmpty(target.getTargetKey())
                , "targetKey should be set and not empty");
        
        try {
            NumberUtils.createLong(target.getTargetKey());
        } catch (NumberFormatException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "target's key should be number");
        }
    }
    
    @Override
    protected void prepare() throws ErrorInfo {
        mSledContractId = NumberUtils.createLong(getOrder().getOrderTarget().getTargetKey());
        mSubExecutors.put(mSledContractId, new XQOrderSubExecutor(getContext(), mSledContractId));
    }
    
    @Override
    protected void calculateTrades(CalculateTradesResult result) throws ErrorInfo {
        Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfos = super.generateUnRelatedExecTradeLegInfos();
        if (unRelatedTradeLegInfos.isEmpty() ) {
            return ;
        }
        
        HostingXQTradeSummary tradeSummary = new HostingXQTradeSummary();
        XQOrderSubExecutor subExecutor = mSubExecutors.get(mSledContractId);
        Map<Long, HostingExecTrade> relatedTrades = subExecutor.getRelatedTrades();
        int relatedTradesTotalVolume = 0;
        BigDecimal relatedTradesTotalFee = new BigDecimal(0);
        for (HostingExecTrade execTrade : relatedTrades.values()) {
            relatedTradesTotalVolume += execTrade.getTradeVolume();
            relatedTradesTotalFee = relatedTradesTotalFee.add(
                    new BigDecimal(execTrade.getTradeVolume()).multiply(
                            new BigDecimal(String.valueOf(execTrade.getTradePrice()))));
        }
        tradeSummary.setTotalVolume(relatedTradesTotalVolume);
        if (relatedTradesTotalVolume > 0) {
            tradeSummary.setAveragePrice(relatedTradesTotalFee.divide(
                    new BigDecimal(relatedTradesTotalVolume), 16, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        tradeSummary.setSubTradeSummaries(super.generateSubTradeSummaries());
        result.setOrderTradeSummary(tradeSummary);
        
        for (Entry<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfoEntry : unRelatedTradeLegInfos.entrySet()) {
            for (UnRelatedExecTradeLegInfo tradeLegInfo : unRelatedTradeLegInfoEntry.getValue()) {
                XQTradeWithRelatedItem tradeWithRelatedItem = new XQTradeWithRelatedItem();
                
                HostingXQTrade newTrade = new HostingXQTrade();
                newTrade.setTradeId(IDGenerator.Global().createTradeId());
                newTrade.setOrderId(getOrder().getOrderId());
                newTrade.setSubAccountId(getOrder().getSubAccountId());
                newTrade.setSubUserId(getOrder().getSubUserId());
                if (tradeLegInfo.getTradeLeg().getTradeLegInfo().getLegUpsideTradeDirection()
                        == HostingExecTradeDirection.TRADE_BUY) {
                    newTrade.setTradeDiretion(HostingXQTradeDirection.XQ_BUY);
                } else {
                    newTrade.setTradeDiretion(HostingXQTradeDirection.XQ_SELL);
                }
                HostingXQTarget tradeTarget = new HostingXQTarget();
                tradeTarget.setTargetType(HostingXQTargetType.CONTRACT_TARGET);
                tradeTarget.setTargetKey(String.valueOf(unRelatedTradeLegInfoEntry.getKey()));
                newTrade.setTradeTarget(tradeTarget);
                newTrade.setTradePrice(tradeLegInfo.getTradeLeg().getTradeLegInfo().getLegTradePrice());
                newTrade.setTradeVolume(tradeLegInfo.getLeftVolume());
                tradeWithRelatedItem.setTrade(newTrade);
                
                tradeWithRelatedItem.addRelatedItem(XQTradeRelatedItem.from(newTrade
                        , tradeLegInfo.getTradeLeg(), tradeLegInfo.getLeftVolume()));
                
                result.getTradeWithRelatedItemsList().add(tradeWithRelatedItem);
            }
        }
       
    }
}
