package xueqiao.trade.hosting.asset.calculator.account.sub;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.*;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import xueqiao.trade.hosting.*;
import xueqiao.trade.hosting.asset.api.ExecutorHandler;
import xueqiao.trade.hosting.asset.calculator.AssetBaseCalculator;
import xueqiao.trade.hosting.asset.calculator.AssetCalculatorFactory;
import xueqiao.trade.hosting.asset.core.AssetSubAccountGlobalData;
import xueqiao.trade.hosting.asset.core.AssetGlobalDataFactory;
import xueqiao.trade.hosting.asset.struct.ExecOrderOutSide;
import xueqiao.trade.hosting.asset.struct.PositionFundCalculateData;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssetFrozenPositionCalculator extends AssetBaseCalculator<HostingExecOrder> {

    @Override
    public String getKey() {
        return AssetCalculatorFactory.SUB_ACCOUNT_FROZEN_POSITION_KEY;
    }

    public AssetFrozenPositionCalculator(long subAccountId) {
        super(subAccountId);
    }

    @Override
    public void onCalculate(HostingExecOrder execOrder) throws Exception {
        Preconditions.checkNotNull(execOrder);
        Preconditions.checkArgument(execOrder.getSubAccountId() > 0);
        Preconditions.checkArgument(execOrder.getExecOrderId() > 0);

        HostingExecOrderStateValue stateValue = execOrder.getStateInfo().getCurrentState().getValue();

        if (ExecutorHandler.needFrozen(stateValue)) {
            HostingExecOrderDetail orderDetail = execOrder.getOrderDetail();
            HostingExecOrderTradeSummary tradeSummary = execOrder.getTradeSummary();
            HostingExecOrderContractSummary contractSummary = execOrder.getContractSummary();
            long orderId = execOrder.getExecOrderId();
            long subAccountId = execOrder.getSubAccountId();
            refreshFrozenPosition(orderDetail, tradeSummary, contractSummary, orderId, subAccountId);
        } else {
            AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(execOrder.getSubAccountId());
            globalData.removeOutSideContract(execOrder.getExecOrderId());
        }

        PositionFundCalculateData data = new PositionFundCalculateData();
        data.setSledContractId(execOrder.getContractSummary().getSledContractId());
        data.setSledCommodityId(execOrder.getContractSummary().getSledCommodityId());
        data.setCalculatePrice(doubleToBigDecimal(execOrder.getOrderDetail().getLimitPrice()));
        ExecutorHandler.calculateFrozenFund(data, execOrder.getSubAccountId());
    }

    private void refreshFrozenPosition(HostingExecOrderDetail orderDetail, HostingExecOrderTradeSummary tradeSummary, HostingExecOrderContractSummary contractSummary, long orderId, long subAccountId) throws TException {
        int orderTotalVolume = orderDetail.getQuantity();
        int tradeTotalVolume = tradeSummary.getTradeListTotalVolume();
        AssetSubAccountGlobalData globalData = AssetGlobalDataFactory.getInstance().getAssetGlobalData(subAccountId);
        if (orderTotalVolume > tradeTotalVolume) {
            orderDetail.setQuantity(orderTotalVolume - tradeTotalVolume);
            addOutSideSledContract(globalData, orderId, orderDetail, contractSummary);
        } else {
            globalData.removeOutSideContract(orderId);
        }
    }

    private void addOutSideSledContract(AssetSubAccountGlobalData globalData, long orderId, HostingExecOrderDetail orderDetail, HostingExecOrderContractSummary contractSummary) throws TException {
        if (SledCommodityType.FUTURES.getValue() == contractSummary.getSledCommodityType()) {
            ExecOrderOutSide outside = new ExecOrderOutSide();
            outside.setSubAccountId(this.mAccountId);
            outside.setExecOrderId(orderId);
            outside.setSledCommodityId(contractSummary.getSledCommodityId());
            outside.setSledContractId(contractSummary.getSledContractId());
            outside.setPrice(orderDetail.getLimitPrice());
            outside.setVolume(orderDetail.getQuantity());
            if (HostingExecOrderTradeDirection.ORDER_BUY.equals(orderDetail.getTradeDirection())) {
                outside.setTradeDirection(HostingExecTradeDirection.TRADE_BUY);
            } else {
                outside.setTradeDirection(HostingExecTradeDirection.TRADE_SELL);
            }
            Map<Long, ExecOrderOutSide> sledContractMap = globalData.getExecOrderOutSideMap(orderId);
            if (sledContractMap == null) {
                sledContractMap = new HashMap<>();
            }
            sledContractMap.put(contractSummary.getSledContractId(), outside);
            globalData.addExecOrderOutSideMap(orderId, sledContractMap);
            PositionFundCalculateData data = new PositionFundCalculateData();
            data.setSledContractId(contractSummary.getSledContractId());
            data.setSledCommodityId(contractSummary.getSledCommodityId());
            ExecutorHandler.calculateFrozenFund(data, this.mAccountId);
        } else {
            List<HostingExecOrderLegContractSummary> contractList = contractSummary.getRelatedLegs();

            IHostingContractApi contractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
            ReqSledCommodityOption option = new ReqSledCommodityOption();
            option.addToSledCommodityIdList((int) contractSummary.getSledCommodityId());
            SledCommodityPage sledCommodityPage = contractApi.getContractOnlineStub().reqSledCommodity(option, 0, 1);
            SledCommodity sledCommodity = sledCommodityPage.getPage().get(0);
            HostingExecTradeDirection firstLegTradeDirection;
            HostingExecTradeDirection secondLegTradeDirection;
            if (CmbDirect.SECOND.equals(sledCommodity.getCmbDirect())) {
                if (HostingExecOrderTradeDirection.ORDER_BUY.equals(orderDetail.getTradeDirection())) {
                    firstLegTradeDirection = HostingExecTradeDirection.TRADE_SELL;
                    secondLegTradeDirection = HostingExecTradeDirection.TRADE_BUY;
                } else {
                    firstLegTradeDirection = HostingExecTradeDirection.TRADE_BUY;
                    secondLegTradeDirection = HostingExecTradeDirection.TRADE_SELL;
                }
            } else {
                if (HostingExecOrderTradeDirection.ORDER_BUY.equals(orderDetail.getTradeDirection())) {
                    firstLegTradeDirection = HostingExecTradeDirection.TRADE_BUY;
                    secondLegTradeDirection = HostingExecTradeDirection.TRADE_SELL;
                } else {
                    firstLegTradeDirection = HostingExecTradeDirection.TRADE_SELL;
                    secondLegTradeDirection = HostingExecTradeDirection.TRADE_BUY;
                }
            }

            ExecOrderOutSide firstLegOutside = new ExecOrderOutSide();
            firstLegOutside.setSubAccountId(this.mAccountId);
            firstLegOutside.setExecOrderId(orderId);
            firstLegOutside.setSledCommodityId(contractList.get(0).getLegSledCommodityId());
            firstLegOutside.setSledContractId(contractList.get(0).getLegSledContractId());

            /** 交易所组合时，腿的价格设置为0，在计算冻结持仓时，使用最新行情的昨日结算价计算 **/
            firstLegOutside.setPrice(0.0);
            firstLegOutside.setVolume(orderDetail.getQuantity());
            firstLegOutside.setTradeDirection(firstLegTradeDirection);

            ExecOrderOutSide secondLegOutside = new ExecOrderOutSide();
            secondLegOutside.setSubAccountId(this.mAccountId);
            secondLegOutside.setExecOrderId(orderId);
            secondLegOutside.setSledCommodityId(contractList.get(1).getLegSledCommodityId());
            secondLegOutside.setSledContractId(contractList.get(1).getLegSledContractId());
            /** 交易所组合时，腿的价格设置为0，在计算冻结持仓时，使用最新行情的昨日结算价计算 **/
            secondLegOutside.setPrice(0.0);
            secondLegOutside.setVolume(orderDetail.getQuantity());
            secondLegOutside.setTradeDirection(secondLegTradeDirection);

            Map<Long, ExecOrderOutSide> sledContractMap = globalData.getExecOrderOutSideMap(orderId);
            if (sledContractMap == null) {
                sledContractMap = new HashMap<>();
            }
            sledContractMap.put(firstLegOutside.getSledContractId(), firstLegOutside);
            sledContractMap.put(secondLegOutside.getSledContractId(), secondLegOutside);
            PositionFundCalculateData firstLegOutsideData = new PositionFundCalculateData();
            firstLegOutsideData.setSledContractId(firstLegOutside.getSledContractId());
            firstLegOutsideData.setSledCommodityId(firstLegOutside.getSledCommodityId());
            ExecutorHandler.calculateFrozenFund(firstLegOutsideData, this.mAccountId);

            PositionFundCalculateData secondLegOutsideData = new PositionFundCalculateData();
            secondLegOutsideData.setSledContractId(secondLegOutside.getSledContractId());
            secondLegOutsideData.setSledCommodityId(secondLegOutside.getSledCommodityId());
            ExecutorHandler.calculateFrozenFund(secondLegOutsideData, this.mAccountId);
            globalData.addExecOrderOutSideMap(orderId, sledContractMap);
        }
    }

}
