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
                 * ???????????? ??????????????? ????????????
                 * ??????????????????????????????????????????????????????
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
                // ???????????? ??????????????????
                Iterator<HostingOrderRouteCommodityTypeNode> commodityTypeNodeIterator = exchangeNode.getSubCommodityTypeNodes().values().iterator();
                while (commodityTypeNodeIterator.hasNext()) {
                    HostingOrderRouteCommodityTypeNode commodityTypeNode = commodityTypeNodeIterator.next();
                    if (commodityTypeNode.getRelatedInfo() != null) {
                        // ?????????????????????????????????
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
                        // ????????????????????????
                        Iterator<HostingOrderRouteCommodityCodeNode> commodityCodeNodeIterator = commodityTypeNode.getSubCommodityCodeNodes().values().iterator();
                        while (commodityCodeNodeIterator.hasNext()) {
                            HostingOrderRouteCommodityCodeNode commodityCodeNode = commodityCodeNodeIterator.next();
                            // ????????????????????????
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
                 * ???????????????????????????
                 * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                 * */
                if (contract.getContractStatus() == ContractStatus.EXPIRED) {
                    continue;
                }
                if (contract.getRelateContractIdsSize() > 0) {
                    /*
                     * ????????????????????????????????????
                     * ?????????????????????????????????????????????????????????????????????????????????
                     * ??????????????????????????????????????????????????????????????????????????????????????????
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
                             * ???????????????????????????
                             * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                             * */
                            if (relateContract.getContractStatus() == ContractStatus.EXPIRED) {
                                continue;
                            }
                            updateContractPositionRate(subAccountId, routeCommodityNode, relateContract);
                        }
                    } while (relateContractList.size() == pageSize);
                } else {
                    /*
                     * ????????????????????????????????????
                     * */
                    updateContractPositionRate(subAccountId, routeCommodityNode, contract);
                }
            }
        } while (true);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ???????????????????????????????????????????????????
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
                     * ???????????????????????????????????????
                     * */
                    upsideContractCommissionTable.update(upsideContractCommission);
                }

                UpsideContractMarginTable upsideContractMarginTable = new UpsideContractMarginTable(getConnection());
                UpsideContractMargin originalPpsideContractMargin = upsideContractMarginTable.query(subAccountId, contract.getSledCommodityId(), contract.getSledContractCode());
                if (originalPpsideContractMargin == null) {
                    upsideContractMarginTable.insert(upsideContractMargin);
                } else if (needUpdate && upsideContractMargin.getMargin() != null) {
                    /*
                     * ???????????????????????????????????????
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
