package xueqiao.trade.hosting.position.fee.core.calculator;

import com.longsheng.xueqiao.contract.standard.thriftapi.ContractStatus;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityType;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContract;
import org.apache.thrift.TException;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingOrderRouteCommodityCodeNode;
import xueqiao.trade.hosting.HostingOrderRouteCommodityTypeNode;
import xueqiao.trade.hosting.HostingOrderRouteExchangeNode;
import xueqiao.trade.hosting.HostingOrderRouteTree;
import xueqiao.trade.hosting.position.fee.core.bean.RouteCommodityNode;
import xueqiao.trade.hosting.position.fee.core.common.calculator.AbstractCalculator;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.core.api.OrderRouteTreeApi;
import xueqiao.trade.hosting.position.fee.core.bean.UpsidePositionRate;
import xueqiao.trade.hosting.position.fee.core.util.PositionRateUtil;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractCommissionTable;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideContractMarginTable;
import xueqiao.trade.hosting.position.fee.storage.table.UpsideOriginalPositionRateTable;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractCommission;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideContractMargin;
import xueqiao.trade.hosting.position.fee.thriftapi.UpsideDataType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class AbstractContractPositionRateCalculator<Q> extends AbstractCalculator<Void, Q> {
    protected AbstractContractPositionRateCalculator(Long threadKey) {
        super(threadKey);
    }

    protected void updateOneSubAccountContractPositionRate(long subAccountId) throws TException {
        HostingOrderRouteTree hostingOrderRouteTree = OrderRouteTreeApi.queryOrderRouteTree(subAccountId);
        if (hostingOrderRouteTree == null || hostingOrderRouteTree.getSubExchangeNodesSize() < 1) {
            return;
        }

        Iterator<HostingOrderRouteExchangeNode> exchangeNodeIterator = hostingOrderRouteTree.getSubExchangeNodes().values().iterator();
        while (exchangeNodeIterator.hasNext()) {

            Map<Integer, RouteCommodityNode> routeCommodityNodeMap = new HashMap<>();

            HostingOrderRouteExchangeNode exchangeNode = exchangeNodeIterator.next();
            if (exchangeNode.getRelatedInfo() != null) {
                /*
                 * 该交易所 所有类型的 所有商品
                 * 目前产品的设计上，应该不会进入该分支
                 * */
                AppLog.e("***** Attention ***** updateOneSubAccountContractPositionRate ---- exchangeNode.getRelatedInfo() should be null for current design, and should not run here");
                List<SledCommodity> commodityList = HostingContractApi.queryCommodities(exchangeNode.sledExchangeCode, null);
                if (commodityList != null) {
                    for (SledCommodity commodity : commodityList) {
                        RouteCommodityNode routeCommodityNode = new RouteCommodityNode();
                        routeCommodityNode.setCommodityId(commodity.getSledCommodityId());
                        routeCommodityNode.setCommodityType(commodity.getSledCommodityType());
                        routeCommodityNode.setForbidden(exchangeNode.getRelatedInfo().isForbidden());
                        routeCommodityNode.setTradeAccountId(exchangeNode.getRelatedInfo().getMainTradeAccountId());
                        routeCommodityNodeMap.put(commodity.getSledCommodityId(), routeCommodityNode);
                    }
                }
            }
            if (exchangeNode.getSubCommodityTypeNodesSize() > 0) {
                // 该交易所 具体商品类型
                Iterator<HostingOrderRouteCommodityTypeNode> commodityTypeNodeIterator = exchangeNode.getSubCommodityTypeNodes().values().iterator();
                while (commodityTypeNodeIterator.hasNext()) {
                    HostingOrderRouteCommodityTypeNode commodityTypeNode = commodityTypeNodeIterator.next();
                    if (commodityTypeNode.getRelatedInfo() != null) {
                        // 所有该交易该类型的商品
                        List<SledCommodity> commodityList = HostingContractApi.queryCommodities(exchangeNode.sledExchangeCode, SledCommodityType.findByValue(commodityTypeNode.getSledCommodityType()));
                        if (commodityList != null) {
                            for (SledCommodity commodity : commodityList) {
                                RouteCommodityNode routeCommodityNode = new RouteCommodityNode();
                                routeCommodityNode.setCommodityId(commodity.getSledCommodityId());
                                routeCommodityNode.setCommodityType(commodity.getSledCommodityType());
                                routeCommodityNode.setForbidden(commodityTypeNode.getRelatedInfo().isForbidden());
                                routeCommodityNode.setTradeAccountId(commodityTypeNode.getRelatedInfo().getMainTradeAccountId());
                                routeCommodityNodeMap.put(commodity.getSledCommodityId(), routeCommodityNode);
                            }
                        }
                    }
                    if (commodityTypeNode.getSubCommodityCodeNodesSize() > 0) {
                        // 有具体商品的设置
                        Iterator<HostingOrderRouteCommodityCodeNode> commodityCodeNodeIterator = commodityTypeNode.getSubCommodityCodeNodes().values().iterator();
                        while (commodityCodeNodeIterator.hasNext()) {
                            HostingOrderRouteCommodityCodeNode commodityCodeNode = commodityCodeNodeIterator.next();
                            // 具体的商品的设置
                            SledCommodity commodity = HostingContractApi.queryCommodity(exchangeNode.getSledExchangeCode(), SledCommodityType.findByValue(commodityTypeNode.getSledCommodityType()), commodityCodeNode.getSledCommodityCode());
                            if (commodity != null) {
                                RouteCommodityNode routeCommodityNode = new RouteCommodityNode();
                                routeCommodityNode.setCommodityId(commodity.getSledCommodityId());
                                routeCommodityNode.setCommodityType(commodity.getSledCommodityType());
                                routeCommodityNode.setForbidden(commodityCodeNode.getRelatedInfo().isForbidden());
                                routeCommodityNode.setTradeAccountId(commodityCodeNode.getRelatedInfo().getMainTradeAccountId());
                                routeCommodityNodeMap.put(commodity.getSledCommodityId(), routeCommodityNode);
                            }
                        }
                    }
                }
            }
            Iterator<RouteCommodityNode> routeCommodityNodeIterator = routeCommodityNodeMap.values().iterator();
            while (routeCommodityNodeIterator.hasNext()) {
                RouteCommodityNode routeCommodityNode = routeCommodityNodeIterator.next();
                updateContractPositionRateRepeatedly(subAccountId, routeCommodityNode);
            }
        }
    }

    private void updateContractPositionRateRepeatedly(long subAccountId, RouteCommodityNode routeCommodityNode) throws TException {
        int pageIndex = 0;
        int pageSize = 10;
        List<SledContract> contractList;
        do {
            contractList = HostingContractApi.queryOnlineContracts((int) routeCommodityNode.getCommodityId(), pageIndex, pageSize);
            pageIndex++;
            if (contractList == null || contractList.isEmpty()) {
                break;
            }
            for (SledContract contract : contractList) {
                /*
                 * 只过滤过过期的合约
                 * 保留禁用的合约，因为如果后面修改为启动，本模块没有监听事件，而不会通知到本模块
                 * */
                if (contract.getContractStatus() == ContractStatus.EXPIRED) {
                    continue;
                }
                if (contract.getRelateContractIdsSize() > 0) {
                    /*
                     * 有关联合约，则取关联合约
                     * 当前，跨期和跨品种，都是由多个合约组成，所以有关联合约
                     * 我们需要的是组成跨期和跨品种合约的原本合约，也就是关联的合约
                     * */
                    int relateContractPageIndex = 0;
                    int relateContractPageSize = 50;
                    List<SledContract> relateContractList;
                    do {
                        relateContractList = HostingContractApi.queryRelateContracts(contract, relateContractPageIndex, relateContractPageSize);
                        relateContractPageIndex++;
                        if (relateContractList == null || relateContractList.isEmpty()) {
                            break;
                        }
                        for (SledContract relateContract : relateContractList) {
                            /*
                             * 只过滤过过期的合约
                             * 保留禁用的合约，因为如果后面修改为启动，本模块没有监听事件，而不会通知到本模块
                             * */
                            if (relateContract.getContractStatus() == ContractStatus.EXPIRED) {
                                continue;
                            }
                            updateContractPositionRate(subAccountId, routeCommodityNode, relateContract);
                        }
                    } while (relateContractList.size() == pageSize);
                } else {
                    /*
                     * 没有关联合约，则取本合约
                     * */
                    updateContractPositionRate(subAccountId, routeCommodityNode, contract);
                }
            }
        } while (true);
    }

    /**
     * 原则上从上手源数据同步到上手合约数据后，需要重置脏数据状态
     * 但是这里不能重置，是因为这里并没有从全局的角度去同步数据，而是从操作账号的角度处理
     * 如果这里重置，其他地方可能会漏处理
     */
    private void updateContractPositionRate(long subAccountId, RouteCommodityNode routeCommodityNode, SledContract contract) throws ErrorInfo {
        new DBTransactionHelper<Void>(PositionFeeDB.Global()) {

            UpsideContractCommission upsideContractCommission;
            UpsideContractMargin upsideContractMargin;
            boolean needUpdate = false;
            UpsideDataType dataType;

            @Override
            public void onPrepareData() throws ErrorInfo, Exception {
                UpsideOriginalPositionRateTable upsideOriginalPositionRateTable = new UpsideOriginalPositionRateTable(getConnection());
                UpsidePositionRate upsidePositionRate = upsideOriginalPositionRateTable.query(routeCommodityNode.getTradeAccountId(), contract.getSledCommodityId(), contract.getSledContractCode());
                dataType = UpsideDataType.UDT_CONTRACT;
                if (upsidePositionRate == null) {
                    upsidePositionRate = upsideOriginalPositionRateTable.query(routeCommodityNode.getTradeAccountId(), contract.getSledCommodityId(), null);
                    dataType = UpsideDataType.UDT_COMMODITY;
                }
                if (upsidePositionRate != null) {
                    needUpdate = true;
                    upsideContractCommission = PositionRateUtil.getUpsideContractCommission(subAccountId, upsidePositionRate, contract);
                    upsideContractMargin = PositionRateUtil.getUpsideContractMargin(subAccountId, upsidePositionRate, contract);
                } else {
                    needUpdate = false;
                    upsideContractCommission = PositionRateUtil.getEmptyUpsideContractCommission(subAccountId, routeCommodityNode, contract);
                    upsideContractMargin = PositionRateUtil.getEmptyUpsideContractMargin(subAccountId, routeCommodityNode, contract);
                }
                if (upsideContractCommission.getCommission() != null) {
                    upsideContractCommission.setDataType(dataType);
                } else {
                    upsideContractCommission.setDataType(UpsideDataType.UDT_NO_DATA);
                }
                if (upsideContractMargin.getMargin() != null) {
                    upsideContractMargin.setDataType(dataType);
                } else {
                    upsideContractMargin.setDataType(UpsideDataType.UDT_NO_DATA);
                }
            }

            @Override
            public void onUpdate() throws ErrorInfo, Exception {
                UpsideContractCommissionTable upsideContractCommissionTable = new UpsideContractCommissionTable(getConnection());
                UpsideContractCommission originalUpsideContractCommission = upsideContractCommissionTable.query(subAccountId, contract.getSledCommodityId(), contract.getSledContractCode());
                if (originalUpsideContractCommission == null) {
                    upsideContractCommissionTable.insert(upsideContractCommission);
                } else if (needUpdate && upsideContractCommission.getCommission() != null) {
                    /*
                     * 从上手获取到有效值时才更新
                     * */
                    upsideContractCommissionTable.update(upsideContractCommission);
                }

                UpsideContractMarginTable upsideContractMarginTable = new UpsideContractMarginTable(getConnection());
                UpsideContractMargin originalPpsideContractMargin = upsideContractMarginTable.query(subAccountId, contract.getSledCommodityId(), contract.getSledContractCode());
                if (originalPpsideContractMargin == null) {
                    upsideContractMarginTable.insert(upsideContractMargin);
                } else if (needUpdate && upsideContractMargin.getMargin() != null) {
                    /*
                     * 从上手获取到有效值时才更新
                     * */
                    upsideContractMarginTable.update(upsideContractMargin);
                }
            }

            @Override
            public Void getResult() {
                return null;
            }
        }.execute();
    }
}
