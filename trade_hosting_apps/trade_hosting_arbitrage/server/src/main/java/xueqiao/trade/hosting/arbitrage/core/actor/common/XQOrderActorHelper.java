package xueqiao.trade.hosting.arbitrage.core.actor.common;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.arbitrage.core.IDGenerator;
import xueqiao.trade.hosting.arbitrage.core.XQOrderBaseExecutor;
import xueqiao.trade.hosting.arbitrage.core.data.UnRelatedExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.core.data.XQTradeWithRelatedItem;
import xueqiao.trade.hosting.arbitrage.storage.data.XQTradeRelatedItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTrade;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTradeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class XQOrderActorHelper {

    public static void constructUnRelatedTrades(XQOrderBaseExecutor executor) throws ErrorInfo {
        Map<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfos = executor.generateUnRelatedExecTradeLegInfos();
        if (AppLog.debugEnabled()) {
            AppLog.d("XQOrderComposeLimitCancelledActor constructTrades, unRelatedTradeLegInfos's size="
                    + unRelatedTradeLegInfos.size());
        }
        if (unRelatedTradeLegInfos.isEmpty() ) {
            return ;
        }

        List<XQTradeWithRelatedItem> tradeWithRelatedItemList = new ArrayList<XQTradeWithRelatedItem>();
        for (Map.Entry<Long, TreeSet<UnRelatedExecTradeLegInfo>> unRelatedTradeLegInfoEntry : unRelatedTradeLegInfos.entrySet()) {
            for (UnRelatedExecTradeLegInfo tradeLegInfo : unRelatedTradeLegInfoEntry.getValue()) {
                XQTradeWithRelatedItem tradeWithRelatedItem = new XQTradeWithRelatedItem();

                HostingXQTrade newTrade = new HostingXQTrade();
                newTrade.setTradeId(IDGenerator.Global().createTradeId());
                newTrade.setOrderId(executor.getOrder().getOrderId());
                newTrade.setSubAccountId(executor.getOrder().getSubAccountId());
                newTrade.setSubUserId(executor.getOrder().getSubUserId());
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

                tradeWithRelatedItemList.add(tradeWithRelatedItem);
            }
        }

        executor.updateTradeInfo(null, tradeWithRelatedItemList);
    }
    
}
