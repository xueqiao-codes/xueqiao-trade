package xueqiao.trade.hosting.arbitrage.core;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeDirection;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.HostingExecTradeLegInfo;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRelatedExecTradeItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecTradeTable;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 子腿执行器
 */
public class XQOrderSubExecutor {
    private long mSubSledContractId;
    private XQOrderContext mOrderContext;
    private IHostingDealingApi mDealingApi;

    /**
     *  市场下单
     */
    private XQOrderMarketer mOrderMarketer;


    // 以成交ID为Key
    private Map<Long, HostingExecTrade> mRelatedExecTrades = new HashMap<Long, HostingExecTrade>();
    private int mTradeListTotalVolume;
    
    // 以子合约ID为key，记录每条合约腿的信息
    private Map<Long, List<HostingExecTradeLeg>> mRelatedExecTradeLegs 
            = new HashMap<Long, List<HostingExecTradeLeg>>(); 
    
    public XQOrderSubExecutor(XQOrderContext orderContext, long subSledContractId) throws ErrorInfo {
        mOrderContext = orderContext;
        mSubSledContractId = subSledContractId;
        mDealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        mOrderMarketer = new XQOrderMarketer(mOrderContext, mSubSledContractId);
        
        restoreFromDB();
    }
    
    public long getSubSledContractId() {
        return mSubSledContractId;
    }
    
    public String getQuotationPlatform() {
        return XQOrderHelper.getQuotationPlatform(getContractDetails());
    }
    
    public String getQuotationContractSymbol() throws UnsupportedEncodingException {
        return XQOrderHelper.getQuotationContractSymbol(getContractDetails());
    }
    
    public int getUpsideTotalVolume() {
        return mOrderMarketer.getUpsideTotalVolume();
    }
    
    public boolean isTradeListComplete() {
        // 计算所有市场挂单上手的给出的成交总量
        return (!mOrderMarketer.isRunning()) && (mTradeListTotalVolume >= getUpsideTotalVolume());
    }

    public int getTradeListTotalVolume() {
        return mTradeListTotalVolume;
    }
    
    private void restoreFromDB() throws ErrorInfo {
        new DBQueryHelper<Void>(ArbitrageDB.Global()) {
            @Override
            protected Void onQuery(Connection conn) throws Exception {
                List<XQOrderRelatedExecTradeItem> relatedExecTradeItems 
                    = new XQOrderRelatedExecTradeTable(conn).getItems(mOrderContext.getOrderId(), mSubSledContractId);
                for (XQOrderRelatedExecTradeItem relatedExecTradeItem : relatedExecTradeItems) {
                    onTradeAdded(relatedExecTradeItem.getRelatedExecTrade());
                }
                return null;
            }
        }.query();
    }
    
    public SledContractDetails getContractDetails() {
        return mOrderMarketer.getContractDetails();
    }
    
    public XQOrderMarketer getOrderMarketer() {
        return mOrderMarketer;
    }
    
    public boolean hasOrderRunning() {
        return mOrderMarketer.isRunning();
    }

    public void cancelOrderRunning() {
        mOrderMarketer.shouldCancel();
    }

    public boolean orderUpdated(HostingExecOrder execOrder) throws ErrorInfo {
        boolean result =  mOrderMarketer.execOrderUpdated(execOrder);
        if (AppLog.infoEnabled()) {
            HostingExecOrderState orderState = null;
            if (mOrderMarketer.getLastestOrder() != null
                    && mOrderMarketer.getLastestOrder().getStateInfo() != null) {
                orderState = mOrderMarketer.getLastestOrder().getStateInfo().getCurrentState();
            }

            AppLog.i("orderUpdated xqOrderId=" + mOrderContext.getOrderId()
                    + ", sledContractId=" + getSubSledContractId()
                    + ", tradeListTotalVolume=" + mTradeListTotalVolume
                    + ", upsideTotalVolume=" + getUpsideTotalVolume()
                    + ", result = " + result
                    + ", latestOrderState=" + orderState);
        }
        return result;
    }
    
    /**
     * @return 返回成交列表是否变更的标志
     */
    public boolean orderTradeListChanged(List<HostingExecTrade> execTradeList) throws ErrorInfo {
        boolean tradeListChanged = false;
        for (HostingExecTrade execTrade : execTradeList) {
            if (mRelatedExecTrades.containsKey(execTrade.getExecTradeId())) {
                continue;
            }
            onTradeAdded(execTrade);
            tradeListChanged = true;
        }

        if (AppLog.infoEnabled()) {
            AppLog.i("orderTradeListChanged xqOrderId=" + mOrderContext.getOrderId()
                    + ", sledContractId=" + getSubSledContractId()
                    + ", tradeListTotalVolume=" + mTradeListTotalVolume
                    + ", upsideTotalVolume=" + getUpsideTotalVolume()
                    + ", tradeListChanged=" + tradeListChanged);
        }

        return tradeListChanged;
    }
    
    // 按照执行成交ID为Key
    public Map<Long, HostingExecTrade> getRelatedTrades() {
        return mRelatedExecTrades;
    }
    
    // 按照腿的合约ID为Key
    public Map<Long, List<HostingExecTradeLeg>> getRelatedExecTradeLegs() {
        return mRelatedExecTradeLegs;
    }
    
    private void onTradeAdded(HostingExecTrade newTrade) throws ErrorInfo {
        List<HostingExecTradeLeg> tradeLegs;
        if (newTrade.getRelatedTradeLegIdsSize() == 1) {
            // 单腿成交，构造订单腿信息即可
            tradeLegs = new ArrayList<>();
            HostingExecTradeLeg newTradeLeg = new HostingExecTradeLeg();
            newTradeLeg.setExecOrderId(newTrade.getExecOrderId());
            newTradeLeg.setExecTradeLegId(newTrade.getRelatedTradeLegIds().get(0));
            newTradeLeg.setRelatedExecTradeId(newTrade.getExecTradeId());
            newTradeLeg.setLegIndex((short)0);
            newTradeLeg.setAccountSummary(newTrade.getAccountSummary());
            
            HostingExecOrderLegContractSummary legContractSummary = new HostingExecOrderLegContractSummary();
            legContractSummary.setLegSledExchangeMic(newTrade.getContractSummary().getSledExchangeMic());
            legContractSummary.setLegSledCommodityCode(newTrade.getContractSummary().getSledCommodityCode());
            legContractSummary.setLegSledCommodityId(newTrade.getContractSummary().getSledCommodityId());
            legContractSummary.setLegSledCommodityType(newTrade.getContractSummary().getSledCommodityType());
            legContractSummary.setLegSledContractCode(newTrade.getContractSummary().getSledContractCode());
            legContractSummary.setLegSledContractId(newTrade.getContractSummary().getSledContractId());
            newTradeLeg.setLegContractSummary(legContractSummary);
            
            HostingExecTradeLegInfo tradeLegInfo = new HostingExecTradeLegInfo();
            tradeLegInfo.setLegTradeVolume(newTrade.getTradeVolume());
            tradeLegInfo.setLegTradePrice(newTrade.getTradePrice());
            if (newTrade.getOrderTradeDirection() == HostingExecOrderTradeDirection.ORDER_BUY) {
                tradeLegInfo.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_BUY);
            } else {
                tradeLegInfo.setLegUpsideTradeDirection(HostingExecTradeDirection.TRADE_SELL);
            }
            newTradeLeg.setTradeLegInfo(tradeLegInfo);
            newTradeLeg.setSubAccountId(mOrderContext.getSubAccountId());
            newTradeLeg.setSubUserId(mOrderContext.getSubUserId());
            tradeLegs.add(newTradeLeg);
        } else {
            tradeLegs = mDealingApi.getTradeRelatedLegs(newTrade.getExecTradeId());
        }
        
        for (HostingExecTradeLeg tradeLeg : tradeLegs) {
            List<HostingExecTradeLeg> execTradeLegList 
                = mRelatedExecTradeLegs.get(tradeLeg.getLegContractSummary().getLegSledContractId());
            if (execTradeLegList == null) {
                execTradeLegList = new ArrayList<>();
                mRelatedExecTradeLegs.put(tradeLeg.getLegContractSummary().getLegSledContractId(), execTradeLegList);
            }
            execTradeLegList.add(tradeLeg);
        }
        
        mRelatedExecTrades.put(newTrade.getExecTradeId(), newTrade);
        mTradeListTotalVolume += newTrade.getTradeVolume();
    }
    
}
