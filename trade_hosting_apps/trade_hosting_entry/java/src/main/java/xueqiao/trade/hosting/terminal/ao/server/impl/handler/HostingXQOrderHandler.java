package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingEffectXQOrderIndexItem;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderExecType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderFirstLegChooseMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderFirstLegChooseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByFirstLegExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegByTriggerType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderLegChaseParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderParallelParams;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeLimitOrderSettings;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQComposeOrderLimitLegSendOrderExtraParam;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQCondition;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrder;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderExecDetail;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderResumeMode;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderType;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQOrderWithTradeList;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTarget;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.QueryEffectXQOrderIndexPage;
import xueqiao.trade.hosting.arbitrage.thriftapi.TradeHostingArbitrageErrorCode;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.entry.core.XQOrderID;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.terminal.ao.HostingXQOrderWithTradeListPage;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class HostingXQOrderHandler extends HandlerBase {

    private IHostingContractApi mContractApi;
    private IHostingComposeApi mComposeApi;
    
    public HostingXQOrderHandler(TServiceCntl serviceCntl
            , LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
        mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
    }

    private XQOrderID checkOrderId(String orderId) throws ErrorInfo {
        XQOrderID xqOrderID = XQOrderID.fromString(orderId);
        if (xqOrderID == null 
            || xqOrderID.getMachineId() != super.getMachineId()) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_ID_FORMAT.getValue()
                    , "orderId format error");
        }
        return xqOrderID;
    }

    private void fillComposeLimitOrderExecParamsDefault(HostingXQComposeLimitOrderExecParams execParams
            , HostingComposeGraph composeGraph) throws TException {
        if (!execParams.isSetExecType()) {
            return ;
        }

        if (!execParams.isSetExecEveryQty()) {
            execParams.setExecEveryQty(1);
        }

        HostingXQComposeLimitOrderSettings settings
                = ArbitrageStubFactory.getStub().getSystemXQComposeLimitOrderSettings();
        if (execParams.getExecType() == HostingXQComposeLimitOrderExecType.PARALLEL_LEG) {
            if (!execParams.isSetExecParallelParams()) {
                execParams.setExecParallelParams(new HostingXQComposeLimitOrderParallelParams());
            }

            if (!execParams.getExecParallelParams().isSetLegChaseParams()) {
                execParams.getExecParallelParams().setLegChaseParams(new HashMap<>());
            }
            fillComposeLimitOrderLegChaseParams(execParams.getExecParallelParams().getLegChaseParams()
                , composeGraph, settings);

            if (!execParams.getExecParallelParams().isSetLegSendOrderExtraParam()) {
                execParams.getExecParallelParams().setLegSendOrderExtraParam(new HashMap<>());
            }
            fillComposeLimitOrderLegSendOrderExtraParams(execParams.getExecParallelParams().getLegSendOrderExtraParam()
                , composeGraph, settings);

        } else if (execParams.getExecType() == HostingXQComposeLimitOrderExecType.LEG_BY_LEG) {
            if (!execParams.isSetExecLegByParams()) {
                execParams.setExecLegByParams(new HostingXQComposeLimitOrderLegByParams());
            }

            if (!execParams.getExecLegByParams().isSetLegByTriggerType()) {
                execParams.getExecLegByParams().setLegByTriggerType(
                        HostingXQComposeLimitOrderLegByTriggerType.PRICE_BEST);
            }

            if (!execParams.getExecLegByParams().isSetFirstLegChooseParam()) {
                execParams.getExecLegByParams().setFirstLegChooseParam(
                        new HostingXQComposeLimitOrderFirstLegChooseParam().setMode(
                                HostingXQComposeLimitOrderFirstLegChooseMode.FIRST_LEG_CHOOSE_MODE_DEFAULT)
                );
            }

            if (!execParams.getExecLegByParams().isSetLegChaseParams()) {
                execParams.getExecLegByParams().setLegChaseParams(new HashMap<>());
            }
            fillComposeLimitOrderLegChaseParams(execParams.getExecLegByParams().getLegChaseParams()
                , composeGraph, settings);

            if (!execParams.getExecLegByParams().isSetLegSendOrderExtraParam()) {
                execParams.getExecLegByParams().setLegSendOrderExtraParam(new HashMap<>());
            }
            fillComposeLimitOrderLegSendOrderExtraParams(execParams.getExecLegByParams().getLegSendOrderExtraParam()
                , composeGraph, settings);

            if (execParams.getExecLegByParams().getLegByTriggerType()
                    == HostingXQComposeLimitOrderLegByTriggerType.PRICE_BEST) {

                if (!execParams.getExecLegByParams().isSetFirstLegExtraParams()) {
                    // 逐腿到价未设置撤单偏离参数，默认填充为1
                    execParams.getExecLegByParams().setFirstLegExtraParams(new HashMap<>());

                    Map<Long,HostingXQComposeLimitOrderLegByFirstLegExtraParam> firstLegExtraParamMap
                            = execParams.getExecLegByParams().getFirstLegExtraParams();
                    for (HostingComposeLeg composeLeg : composeGraph.getLegs().values()) {
                        firstLegExtraParamMap.put(composeLeg.getSledContractId()
                                , new HostingXQComposeLimitOrderLegByFirstLegExtraParam().setRevokeDeviatePriceTicks(1));
                    }

                }

            }

        }

    }

    private void fillComposeLimitOrderLegChaseParams(Map<Long,HostingXQComposeLimitOrderLegChaseParam> legChaseParamMap
            , HostingComposeGraph composeGraph
            , HostingXQComposeLimitOrderSettings settings) {
        for (HostingComposeLeg composeLeg : composeGraph.getLegs().values()) {
            HostingXQComposeLimitOrderLegChaseParam legChaseParam
                    = legChaseParamMap.get(composeLeg.getSledContractId());
            if (legChaseParam == null) {
                legChaseParam = new HostingXQComposeLimitOrderLegChaseParam();
                legChaseParamMap.put(composeLeg.getSledContractId(), legChaseParam);
            }

            if (!legChaseParam.isSetTicks()) {
                legChaseParam.setTicks(settings.getDefaultChaseTicks());
            }
            if (!legChaseParam.isSetTimes() || legChaseParam.getTimes() <= 0) {
                Preconditions.checkArgument(StringUtils.isNotEmpty(composeLeg.getSledExchangeMic()));
                if (mContractApi.isInvolExchange(composeLeg.getSledExchangeMic())) {
                    legChaseParam.setTimes(settings.getDefaultInvolRevokeLimitNum());
                }
            }
            if (!legChaseParam.isSetProtectPriceRatio()) {
                legChaseParam.setProtectPriceRatio(settings.getDefaultPriceProtectRatio());
            }
        }
    }

    private void fillComposeLimitOrderLegSendOrderExtraParams(
            Map<Long,HostingXQComposeOrderLimitLegSendOrderExtraParam> legSendOrderExtraParamMap
            , HostingComposeGraph composeGraph
            , HostingXQComposeLimitOrderSettings settings) {
        for (HostingComposeLeg composeLeg : composeGraph.getLegs().values()) {
            HostingXQComposeOrderLimitLegSendOrderExtraParam legSendOrderExtraParam
                    = legSendOrderExtraParamMap.get(composeLeg.getSledContractId());
            if (legSendOrderExtraParam == null) {
                legSendOrderExtraParam = new HostingXQComposeOrderLimitLegSendOrderExtraParam();
                legSendOrderExtraParamMap.put(composeLeg.getSledContractId(), legSendOrderExtraParam);
            }

            if (!legSendOrderExtraParam.isSetQuantityRatio()) {
                legSendOrderExtraParam.setQuantityRatio(settings.getDefaultQuantityRatio());
            }
        }
    }
    
    public void create(long subAccountId
                , String orderId
                , HostingXQOrderType orderType
                , HostingXQTarget orderTarget
                , HostingXQOrderDetail orderDetail) throws ErrorInfo, TException {
        ParameterChecker.check(subAccountId > 0, "subAccountId should > 0");
        XQOrderID xqOrderID = checkOrderId(orderId);
        if (xqOrderID.getSubAccountId() != subAccountId) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_ID_FORMAT.getValue()
                    , "orderId format error");
        }
        if (xqOrderID.getSubUserId() != super.getSubUserId()) {
            throw new ErrorInfo(TradeHostingArbitrageErrorCode.ERROR_XQ_ORDER_ID_FORMAT.getValue()
                    , "orderId format error");
        }
        checkHasRelatedAccount(subAccountId);
        ParameterChecker.checkNotNull(orderType, "orderType should not be set");
        ParameterChecker.checkNotNull(orderTarget, "orderTarget should not be set");
        ParameterChecker.check(orderTarget.isSetTargetType(), "orderTarget's targetType should be set");
        ParameterChecker.check(orderTarget.isSetTargetKey() && StringUtils.isNotEmpty(orderTarget.getTargetKey())
                , "orderTarget's targetKey should be set and not be empty");

        HostingXQOrderDetail createOrderDetail = new HostingXQOrderDetail(orderDetail);
        try {
            if (orderTarget.getTargetType() == HostingXQTargetType.CONTRACT_TARGET) {
                checkContractDetailRunningMode(
                        mContractApi.getContractDetailForSure(
                                NumberUtils.createLong(orderTarget.getTargetKey())));
            } else {
                long composeGraphId = NumberUtils.createLong(orderTarget.getTargetKey());
                HostingComposeGraph composeGraph = mComposeApi.getComposeGraph(composeGraphId);
                if (null == composeGraph) {
                    throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
                            , "compose graph is not existed for " + composeGraphId);
                }

                if (orderType == HostingXQOrderType.XQ_ORDER_COMPOSE_LIMIT) {
                    if (!createOrderDetail.isSetComposeLimitOrderDetail()) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                                , "composeLimitOrderDetail should be set");
                    }
                    if (!createOrderDetail.getComposeLimitOrderDetail().isSetExecParams()) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                                , "composeLimitOrderDetail's execParams should be set");
                    }
                    fillComposeLimitOrderExecParamsDefault(
                            createOrderDetail.getComposeLimitOrderDetail().getExecParams(), composeGraph);

                } else if (orderType == HostingXQOrderType.XQ_ORDER_CONDITION) {
                    if (!createOrderDetail.isSetConditionOrderDetail()) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                                , "conditionOrderDetail should be set");
                    }
                    if (!createOrderDetail.getConditionOrderDetail().isSetConditions()
                         || createOrderDetail.getConditionOrderDetail().getConditionsSize() <= 0) {
                        throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                                , "conditionOrderDetail's conditions should be set and not be empty");
                    }

                    for (HostingXQCondition orderCondition
                            : createOrderDetail.getConditionOrderDetail().getConditions()) {
                        if (orderCondition.isSetConditionAction()
                                && orderCondition.getConditionAction().isSetExtra()
                                && orderCondition.getConditionAction().getExtra().isSetComposeLimitExecParams()) {
                            fillComposeLimitOrderExecParamsDefault(
                                    orderCondition.getConditionAction().getExtra().getComposeLimitExecParams()
                                    , composeGraph);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "orderTarget's targetKey should be number");
        }

        HostingXQOrder newXQOrder = new HostingXQOrder();
        newXQOrder.setOrderId(orderId);
        newXQOrder.setSubUserId(getSubUserId());
        newXQOrder.setSubAccountId(subAccountId);
        newXQOrder.setOrderType(orderType);
        newXQOrder.setOrderTarget(orderTarget);
        newXQOrder.setOrderDetail(createOrderDetail);
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                ArbitrageStubFactory.getStub().createXQOrder(newXQOrder);
                return null;
            }
        });
    }
    
    private static interface IOrderOperator {
        public void apply(String orderId) throws Exception;
    }
    
    private void operateOrder(String orderId, IOrderOperator operator) throws ErrorInfo {
        XQOrderID xqOrderID = checkOrderId(orderId);
        checkHasRelatedAccount(xqOrderID.getSubAccountId());
        ErrorInfoCallHelper.call(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                operator.apply(orderId);
                return null;
            }
        });
    }
    
    private Map<String, ErrorInfo> batchOperateOrders(Set<String> orderIds, IOrderOperator operator) throws ErrorInfo {
        ParameterChecker.checkNotNull(orderIds, "orderIds should not be null");
        Map<String, ErrorInfo> resultMap = new HashMap<String, ErrorInfo>();
        for (String orderId : orderIds) {
            try {
                operateOrder(orderId, operator);
            } catch (ErrorInfo e) {
                resultMap.put(orderId, e);
            }
        }
        return resultMap;
    }
    
    public Map<String, ErrorInfo> batchResumeXQOrders(Set<String> orderIds
        , Map<String, HostingXQOrderResumeMode> resumeModes) throws ErrorInfo {
        return batchOperateOrders(orderIds, new IOrderOperator() {
            @Override
            public void apply(String orderId) throws Exception {
                HostingXQOrderResumeMode resumeMode = HostingXQOrderResumeMode.RESUME_MODE_NONE;
                if (resumeModes != null) {
                    resumeMode = resumeModes.get(orderId);
                    if (resumeMode == null) {
                        resumeMode = HostingXQOrderResumeMode.RESUME_MODE_NONE;
                    }
                }

                ArbitrageStubFactory.getStub().resumeXQOrder(orderId, resumeMode);
            }
        });
    }
    
    public Map<String, ErrorInfo> batchSuspendXQOrders(Set<String> orderIds) throws ErrorInfo {
        return batchOperateOrders(orderIds, new IOrderOperator() {
            @Override
            public void apply(String orderId) throws Exception {
                ArbitrageStubFactory.getStub().suspendXQOrder(orderId);
            }
        });
    }
    
    public Map<String, ErrorInfo> batchCancelXQOrders(Set<String> orderIds) throws ErrorInfo {
        return batchOperateOrders(orderIds, new IOrderOperator() {
            @Override
            public void apply(String orderId) throws Exception {
                ArbitrageStubFactory.getStub().cancelXQOrder(orderId);
            }
        });
    }
    
    public HostingXQOrderWithTradeListPage getEffectXQOrderWithTradeListPage(
            QueryEffectXQOrderIndexOption qryOption
            , IndexedPageOption pageOption) throws ErrorInfo {
        ParameterChecker.checkNotNull(qryOption, "qryOption should not be null");
        ParameterChecker.check(qryOption.isSetSubAccountIds() && !qryOption.getSubAccountIds().isEmpty()
                , "qryOption's subAccountIds should be set and not be empty");
        
        PageOptionHelper.checkIndexedPageOption(pageOption, 200);
        QueryEffectXQOrderIndexOption apiQryOption = new QueryEffectXQOrderIndexOption();
        apiQryOption.setOrderIds(qryOption.getOrderIds());
        apiQryOption.setSubUserIds(qryOption.getSubUserIds());
        apiQryOption.setSubAccountIds(new HashSet<Long>());
        for (Long subAccountId : qryOption.getSubAccountIds()) {
            if (hasRelatedAccount(subAccountId)) {
                apiQryOption.getSubAccountIds().add(subAccountId);
            }
        }
        
        if(apiQryOption.getSubAccountIds().isEmpty()) {
            HostingXQOrderWithTradeListPage resultPage = new HostingXQOrderWithTradeListPage();
            resultPage.setTotalCount(0);
            resultPage.setResultList(new ArrayList<HostingXQOrderWithTradeList>());
            return resultPage;
        }
        
        return ErrorInfoCallHelper.call(new Callable<HostingXQOrderWithTradeListPage>() {
            @Override
            public HostingXQOrderWithTradeListPage call() throws Exception {
                HostingXQOrderWithTradeListPage resultPage = new HostingXQOrderWithTradeListPage();
                QueryEffectXQOrderIndexPage indexPage 
                    = ArbitrageStubFactory.getStub().getEffectXQOrderIndexPage(apiQryOption, pageOption);
                
                if (indexPage.getResultIndexItemsSize() <= 0) {
                    resultPage.setTotalCount(0);
                    resultPage.setResultList(new ArrayList<HostingXQOrderWithTradeList>());
                    return resultPage;
                }
                
                Map<String, HostingXQOrderWithTradeList>
                    orderItems = ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(
                            indexPage.getResultIndexItems().stream().map(item->item.getOrderId()).collect(Collectors.toSet()));
                
                resultPage.setResultList(new ArrayList<HostingXQOrderWithTradeList>(orderItems.size() + 1));
                for (HostingEffectXQOrderIndexItem indexItem : indexPage.getResultIndexItems()) {
                    HostingXQOrderWithTradeList orderWithTradeList = orderItems.get(indexItem.getOrderId());
                    if (orderWithTradeList == null) {
                        AppLog.w("[WARNING]get index items but no order with trade list item, orderId=" + indexItem.getOrderId());
                        continue;
                    }
                    resultPage.getResultList().add(orderWithTradeList);
                }
                resultPage.setTotalCount(indexPage.getTotalNum());
                return resultPage;
            }
        });
    }
    
    public Map<String, HostingXQOrderWithTradeList> getXQOrderWithTradeLists(Set<String> orderIds) throws ErrorInfo {
        ParameterChecker.checkNotNull(orderIds, "orderIds should not be null");
        
        Set<String> queryOrderIds = new HashSet<String>();
        for (String orderId : orderIds) {
            XQOrderID xqOrderID = XQOrderID.fromString(orderId);
            if (xqOrderID == null || xqOrderID.getMachineId() != super.getMachineId()) {
                continue;
            }
            if (!hasRelatedAccount(xqOrderID.getSubAccountId())) {
                continue;
            }
            queryOrderIds.add(orderId);
        }
        if (queryOrderIds.isEmpty()) {
            return new HashMap<String, HostingXQOrderWithTradeList>();
        }
        
        return ErrorInfoCallHelper.call(new Callable<Map<String, HostingXQOrderWithTradeList>>() {
            @Override
            public Map<String, HostingXQOrderWithTradeList> call() throws Exception {
                return ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(queryOrderIds);
            }
        });
    }
    
    public HostingXQOrderExecDetail getXQOrderExecDetail(String orderId) throws ErrorInfo {
        XQOrderID xqOrderID = checkOrderId(orderId);
        checkHasRelatedAccount(xqOrderID.getSubAccountId());
        return ErrorInfoCallHelper.call(new Callable<HostingXQOrderExecDetail>() {
            @Override
            public HostingXQOrderExecDetail call() throws Exception {
                return ArbitrageStubFactory.getStub().getXQOrderExecDetail(orderId);
            }
        });
    }
}
