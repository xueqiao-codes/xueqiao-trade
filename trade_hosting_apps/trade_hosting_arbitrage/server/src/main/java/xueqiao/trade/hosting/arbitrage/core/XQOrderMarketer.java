package xueqiao.trade.hosting.arbitrage.core;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBStepHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderCreatorType;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecOrderMode;
import xueqiao.trade.hosting.HostingExecOrderState;
import xueqiao.trade.hosting.HostingExecOrderStateInfo;
import xueqiao.trade.hosting.HostingExecOrderStateValue;
import xueqiao.trade.hosting.HostingExecOrderTradeDirection;
import xueqiao.trade.hosting.HostingExecOrderTradeSummary;
import xueqiao.trade.hosting.HostingExecOrderType;
import xueqiao.trade.hosting.arbitrage.storage.ArbitrageDB;
import xueqiao.trade.hosting.arbitrage.storage.data.XQOrderRelatedExecOrderItem;
import xueqiao.trade.hosting.arbitrage.storage.table.XQOrderRelatedExecOrderTable;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.IHostingDealingApi;

import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class XQOrderMarketer {
    private static final int MAX_FINAL_NO_TRADE_ORDER_NUM = 10;

    private XQOrderContext mOrderContext;
    private long mSledContractId;
    
    private IHostingDealingApi mDealingApi;
    private Map<Long, HostingExecOrder> mMarketOrders = new LinkedHashMap<>();

    // 无成交，同时没有成交的订单链
    private LinkedHashMap<Long, HostingExecOrder> mFinalNoTradeOrder = new LinkedHashMap<>();
    private Map<Long, Map<String, String>> mMarketOrderExtraParams = new HashMap<>();

    private long mLastestExecOrderId = -1;
    private boolean mIsShouldCancel = false;
    private SledContractDetails mContractDetails;
    private int mUpsideTotalVolume = 0;
    
    public XQOrderMarketer(XQOrderContext orderContext, long sledContractId) throws ErrorInfo {
        this.mOrderContext = orderContext;
        this.mSledContractId = sledContractId;

        this.mDealingApi = Globals.getInstance().queryInterfaceForSure(IHostingDealingApi.class);
        this.mContractDetails = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class)
                .getContractDetailForSure(sledContractId);
        
        restoreMarketOrders();
        
        HostingExecOrder lastestOrder = getLastestOrder();
        if (lastestOrder != null) {
            if (lastestOrder.getStateInfo() == null || !lastestOrder.getStateInfo().isSetCurrentState()) {
                ExecOrderConnector.Global().sendNewOrder(lastestOrder);
            }
        }
    }
    
    public SledContractDetails getContractDetails() {
        return mContractDetails;
    }
    
    public int getUpsideTotalVolume() {
        return mUpsideTotalVolume;
    }
    
    private String generateSource() {
        StringBuilder sourceBuilder = new StringBuilder(64);
        sourceBuilder.append("XQ|").append(mOrderContext.getOrderId()).toString();
        return sourceBuilder.toString();
    }
    
    private void restoreMarketOrders() throws ErrorInfo {
        new DBQueryHelper<Void>(ArbitrageDB.Global()) {
            @Override
            protected Void onQuery(Connection conn) throws Exception {
                List<XQOrderRelatedExecOrderItem> relatedExecOrderItem 
                    = new XQOrderRelatedExecOrderTable(conn).getItemsByContractIdOrderByExecOrderId(mOrderContext.getOrderId()
                        , mSledContractId);
                for (XQOrderRelatedExecOrderItem execOrderItem : relatedExecOrderItem) {
                    mMarketOrders.put(execOrderItem.getExecOrderId(), execOrderItem.getRelatedExecOrder());
                    if (execOrderItem.getExecOrderId() > mLastestExecOrderId) {
                        mLastestExecOrderId = execOrderItem.getExecOrderId();
                    }
                    if (execOrderItem.getRelatedExecOrder().isSetTradeSummary()) {
                        mUpsideTotalVolume += execOrderItem.getRelatedExecOrder().getTradeSummary().getUpsideTradeTotalVolume();
                    }
                    if (execOrderItem.getExtraParams() != null) {
                        mMarketOrderExtraParams.put(execOrderItem.getExecOrderId(), execOrderItem.getExtraParams());
                    }

                    if (isFinalNoTradeOrder(execOrderItem.getRelatedExecOrder())) {
                        updateFinalNoTradeOrder(execOrderItem.getRelatedExecOrder());
                    }

                }
                return null;
            }
        }.query();
    }

    private boolean isFinalNoTradeOrder(HostingExecOrder execOrder) {
        if (isFinalState(execOrder.getStateInfo().getCurrentState())) {
            HostingExecOrderTradeSummary tradeSummary = execOrder.getTradeSummary();
            if (tradeSummary == null
                    || (tradeSummary.getUpsideTradeTotalVolume() <= 0 && tradeSummary.getTradeListTotalVolume() <= 0)) {
                return true;
            }
        }
        return false;
    }

    private void updateFinalNoTradeOrder(HostingExecOrder execOrder) throws ErrorInfo {
        mFinalNoTradeOrder.put(execOrder.getExecOrderId(), execOrder);
        while (mFinalNoTradeOrder.size() > MAX_FINAL_NO_TRADE_ORDER_NUM) {
            HostingExecOrder headOrder = mFinalNoTradeOrder.entrySet().iterator().next().getValue();
            new DBStepHelper<Void>(ArbitrageDB.Global()) {
                @Override
                public void onPrepareData() throws ErrorInfo, Exception {
                }

                @Override
                public void onUpdate() throws ErrorInfo, Exception {
                    new XQOrderRelatedExecOrderTable(getConnection()).deleteItem(
                            mOrderContext.getOrderId(), headOrder.getExecOrderId());
                }

                @Override
                public Void getResult() {
                    return null;
                }
            }.execute();

            mFinalNoTradeOrder.remove(headOrder.getExecOrderId());
            mMarketOrderExtraParams.remove(headOrder.getExecOrderId());
            mMarketOrders.remove(headOrder.getExecOrderId());
        }
    }
    
    /**
     *  是否有订单在执行，未到达终结态
     * @return
     */
    public boolean isRunning() {
        HostingExecOrder lastestOrder = getLastestOrder();
        if (lastestOrder == null) {
            return false;
        }
        
        return !isFinalState(lastestOrder.getStateInfo().getCurrentState());
    }

    private boolean isFinalState(HostingExecOrderState state) {
        if (state == null) {
            return false;
        }
        if (state.getValue() == HostingExecOrderStateValue.ORDER_VERIFY_FAILED
                || state.getValue() == HostingExecOrderStateValue.ORDER_SLED_SEND_FAILED
                || state.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_REJECTED
                || state.getValue() == HostingExecOrderStateValue.ORDER_EXPIRED
                || state.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_FINISHED
                || state.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_DELETED) {
            return true;
        }
        return false;
    }
    
    public HostingExecOrder getLastestOrder() {
        if (mLastestExecOrderId <= 0) {
            return null;
        }
        return mMarketOrders.get(mLastestExecOrderId);
    }

    public Map<String, String> getLastestOrderExtraParams() {
        if (mLastestExecOrderId <= 0) {
            return null;
        }
        return mMarketOrderExtraParams.get(mLastestExecOrderId);
    }
    
    public Map<Long, HostingExecOrder> getMarketOrders() {
        return mMarketOrders;
    }

    public void lanuchOrder(HostingExecOrderTradeDirection tradeDirection
            , double limitPrice
            , int quantity) throws ErrorInfo {
        lanuchOrder(tradeDirection, limitPrice, quantity, null);
    }
    
    /**
     *  发起一个订单, 运行态不准发起订单
     * @throws ErrorInfo 
     */
    public void lanuchOrder(
            HostingExecOrderTradeDirection tradeDirection
            , double limitPrice
            , int quantity
            , Map<String, String> extraParams) throws ErrorInfo  {
        Preconditions.checkState(!isRunning());
        
        HostingExecOrder sendLastestOrder = new HostingExecOrder();
        sendLastestOrder.setExecOrderId(mDealingApi.createOrderId());
        sendLastestOrder.setSubAccountId(mOrderContext.getSubAccountId());
        sendLastestOrder.setSubUserId(mOrderContext.getSubUserId());
       
        HostingExecOrderContractSummary contractSummary = new HostingExecOrderContractSummary();
        contractSummary.setSledExchangeMic(mContractDetails.getSledCommodity().getExchangeMic());
        contractSummary.setSledCommodityId(mContractDetails.getSledCommodity().getSledCommodityId());
        contractSummary.setSledCommodityCode(mContractDetails.getSledCommodity().getSledCommodityCode());
        contractSummary.setSledCommodityType((short)mContractDetails.getSledCommodity().getSledCommodityType().getValue());
        contractSummary.setSledContractId(mContractDetails.getSledContract().getSledContractId());
        contractSummary.setSledContractCode(mContractDetails.getSledContract().getSledContractCode());
        
        sendLastestOrder.setContractSummary(contractSummary);
        sendLastestOrder.setStateInfo(new HostingExecOrderStateInfo());
        
        HostingExecOrderDetail orderDetail = new HostingExecOrderDetail();
        orderDetail.setOrderType(HostingExecOrderType.ORDER_LIMIT_PRICE);
        orderDetail.setOrderCreatorType(HostingExecOrderCreatorType.ORDER_MACHINE);
        orderDetail.setLimitPrice(limitPrice);
        orderDetail.setQuantity(quantity);
        orderDetail.setTradeDirection(tradeDirection);
        orderDetail.setOrderMode(HostingExecOrderMode.ORDER_GFD);
        sendLastestOrder.setOrderDetail(orderDetail);
        sendLastestOrder.setSource(generateSource());
        
        sendLastestOrder.setVersion(0);
        sendLastestOrder.setStateInfo(new HostingExecOrderStateInfo());
        sendLastestOrder.setCreateTimestampMs(System.currentTimeMillis());
        
        appendMarketOrder(sendLastestOrder, extraParams);
    }
    
    private void appendMarketOrder(HostingExecOrder sendLastestOrder, Map<String, String> extraParams) throws ErrorInfo {
        if (AppLog.infoEnabled()) {
            AppLog.i("appendMarketOrder " + sendLastestOrder);
        }
        
        // 先写入DB保证下单成功，后续的下单动作才能保证一定成功
        new DBStepHelper<Void>(ArbitrageDB.Global()){
            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                XQOrderRelatedExecOrderItem newItem = new XQOrderRelatedExecOrderItem();
                newItem.setOrderId(mOrderContext.getOrderId());
                newItem.setSledContractId(mSledContractId);
                newItem.setExecOrderId(sendLastestOrder.getExecOrderId());
                newItem.setRelatedExecOrder(sendLastestOrder);
                newItem.setCreateTimestampMs(System.currentTimeMillis());
                if (extraParams != null) {
                    newItem.setExtraParams(extraParams);
                }
                new XQOrderRelatedExecOrderTable(getConnection()).addItem(newItem);
            }

            @Override
            public Void getResult() {
                return null;
            }
            
        }.execute();

        if (AppLog.infoEnabled()) {
            AppLog.i("appendMarketOrder xqOrderId=" + mOrderContext.getOrderId()
                    + ", execOrderId=" + sendLastestOrder.getExecOrderId()
                    + " write local finished!");
        }

        mMarketOrders.put(sendLastestOrder.getExecOrderId(), sendLastestOrder);
        mLastestExecOrderId = sendLastestOrder.getExecOrderId();
        if (extraParams != null) {
            mMarketOrderExtraParams.put(sendLastestOrder.getExecOrderId(), extraParams);
        }
        
        ExecOrderConnector.Global().sendNewOrder(sendLastestOrder);
    }
    
    private void updateMarketOrder(HostingExecOrder updateOrder) throws ErrorInfo {
        mMarketOrders.put(updateOrder.getExecOrderId(), updateOrder);
        if (isFinalNoTradeOrder(updateOrder)) {
            updateFinalNoTradeOrder(updateOrder);
        }
    }
    
    /**
     *  应该取消运行
     */
    public void shouldCancel() {
        HostingExecOrder lastestOrder = getLastestOrder();
        if (lastestOrder == null) {
            return ;
        }
        if (isFinalState(lastestOrder.getStateInfo().getCurrentState())) {
            return ;
        }
        
        mIsShouldCancel = true;

        if (AppLog.infoEnabled()) {
            AppLog.i("revokeOrder for xqOrderId=" + mOrderContext.getOrderId()
                    + ", sledContractId=" + mContractDetails.getSledContract().getSledContractId()
                    + ", execOrderId=" + lastestOrder.getExecOrderId());
        }

        ExecOrderConnector.Global().revokeOrder(lastestOrder.getExecOrderId()
                , mContractDetails.getSledCommodity().getSledCommodityId());
    }

    public boolean isCancelling() {
        return mIsShouldCancel;
    }

    /**
     * @return 返回最新订单是否更新的标志
     */
    public boolean execOrderUpdated(HostingExecOrder updateOrder) throws ErrorInfo {
        HostingExecOrder compareOrder = mMarketOrders.get(updateOrder.getExecOrderId());
        if (compareOrder == null) {
            // 可能是恢复过程中的遗漏订单
            return false;
        }
        if (compareOrder.getVersion() >= updateOrder.getVersion()) {
            return false;
        }
        
        if (updateOrder.isSetTradeSummary()) {
            if (compareOrder.isSetTradeSummary()) {
                mUpsideTotalVolume += (updateOrder.getTradeSummary().getUpsideTradeTotalVolume() 
                            - compareOrder.getTradeSummary().getUpsideTradeTotalVolume());
            } else {
                mUpsideTotalVolume += updateOrder.getTradeSummary().getUpsideTradeTotalVolume();
            }
        } 
            
        updateMarketOrder(updateOrder);

        if (updateOrder.getExecOrderId() != mLastestExecOrderId) {
            return false;
        }
        if (!mIsShouldCancel) {
            return true;
        }
        
        HostingExecOrderState updateOrderState = updateOrder.getStateInfo().getCurrentState();
        if ( updateOrderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED
             || updateOrderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_RESTING
             || updateOrderState.getValue() == HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED) {
            // 任然处在这几个状态，则尝试继续撤单, 但是频率降低
            mOrderContext.getWorkThread().postTaskDelay(new Runnable() {
                @Override
                public void run() {
                    shouldCancel();
                }
            }, 1, TimeUnit.SECONDS);
            return true; 
        }
        if (isFinalState(updateOrderState)) {
            mIsShouldCancel = false;
            ExecOrderConnector.Global().removeRevokeExecOrderJob(updateOrder.getExecOrderId());
        }
        return true;
    }

}
