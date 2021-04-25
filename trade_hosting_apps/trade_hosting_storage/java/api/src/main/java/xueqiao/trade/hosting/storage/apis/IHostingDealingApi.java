package xueqiao.trade.hosting.storage.apis;

import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderContractSummary;
import xueqiao.trade.hosting.HostingExecOrderDetail;
import xueqiao.trade.hosting.HostingExecTrade;
import xueqiao.trade.hosting.HostingExecTradeLeg;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.util.List;

public interface IHostingDealingApi {
    public long createOrderId() throws ErrorInfo;
    public long createTradeId() throws ErrorInfo;
    public long createTradeLegId() throws ErrorInfo;
    
    /**
     *  雪橇内部下单
     */
    public void createUserDeal(int subUserId
            , long subAccountId
            , long execOrderId
            , HostingExecOrderContractSummary contractSummary
            , HostingExecOrderDetail orderDetail
            , String source) throws ErrorInfo;
    
    /**
     *  直接利用雪橇合约ID下单
     */
    public void createUserDeal(int subUserId
            , long subAccountId
            , long execOrderId
            , long sledContractId
            , HostingExecOrderDetail orderDetail
            , String source) throws ErrorInfo;
    
    /**
     *  撤销订单
     */
    public void revokeDeal(long execOrderId) throws ErrorInfo;
    
    /**
     *  获取订单
     */
    public HostingExecOrder getOrder(long execOrderId) throws ErrorInfo;
    
    /**
     *  获取订单的成交列表
     */
    public List<HostingExecTrade> getOrderTrades(long execOrderId) throws ErrorInfo;
    
    /**
     *  获取成交的订单腿
     */
    public List<HostingExecTradeLeg> getTradeRelatedLegs(long execTradeId) throws ErrorInfo;

    /**
     *  获取所有的有效期订单
     */
    public List<HostingExecOrder> getAllInDealingOrders() throws ErrorInfo;

    /**
     *  分页获取有效期内订单
     */
    public PageResult<HostingExecOrder> getInDealingOrders(PageOption pageOption) throws ErrorInfo;


    /**
     *  清理所有数据
     */
    public void clearAll() throws ErrorInfo ;
}
