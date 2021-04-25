package xueqiao.trade.hosting.terminal.ao.server.impl.handler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.container.TServiceCntl;

import com.google.common.base.Preconditions;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.ValidationResult;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeGraphEnv;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeView;
import xueqiao.trade.hosting.HostingComposeViewSource;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;
import xueqiao.trade.hosting.HostingRunningMode;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.trade_hostingConstants;
import xueqiao.trade.hosting.entry.core.PageOptionHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryComposeViewOption;
import xueqiao.trade.hosting.terminal.ao.HostingComposeViewDetail;
import xueqiao.trade.hosting.terminal.ao.LandingInfo;
import xueqiao.trade.hosting.terminal.ao.QueryHostingComposeViewDetailOption;
import xueqiao.trade.hosting.terminal.ao.QueryHostingComposeViewDetailPage;
import xueqiao.trade.hosting.terminal.ao.QuerySameComposeGraphsPage;

public class HostingComposeHandler extends HandlerBase {
    private static final int MAX_PRECISION_NUMBER = 16;

    private IHostingComposeApi mComposeApi;
    private IHostingContractApi mContractApi;
    
    public HostingComposeHandler(TServiceCntl serviceCntl, LandingInfo landingInfo) throws ErrorInfo {
        super(serviceCntl, landingInfo);
        mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
        mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
    }
    
    public void checkAliasName(String aliasName) throws ErrorInfo {
        ParameterChecker.check(StringUtils.isNotBlank(aliasName), "compose's name should not be blank");
        ParameterChecker.check(aliasName.trim().length() <= 50
                , "compose's name is too long"); 
    }

    public void checkPrecisionNumber(short precisionNumber) throws ErrorInfo {
        if (precisionNumber > MAX_PRECISION_NUMBER) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                    , "precision number should not > " + MAX_PRECISION_NUMBER);
        }
    }
    
    public void checkAndFillLegs(Map<String, HostingComposeLeg> legs) throws ErrorInfo {
        ParameterChecker.check(legs != null, "compose's legs should not be null");
        ParameterChecker.check(legs.size() <= 8 && legs.size() > 1
                , "compose's legs count should <= 8 and > 1");
        
        List<Integer> quantities = new ArrayList<Integer>();
        
        Set<Long> legSledContractIds = new HashSet<Long>();
        for(Entry<String, HostingComposeLeg> leg : legs.entrySet()) {
            if(!leg.getKey().equals(leg.getValue().getVariableName())) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                        , leg.getKey() + " is not equals value's variableName");
            }
            if(leg.getKey().length() != 1) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                        , leg.getKey() + " should be one character");
            }
            if (leg.getKey().charAt(0) < 'A' || leg.getKey().charAt(0) > 'Y') {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_PARAMETER.getValue()
                        , leg.getKey() + " should be A-Y");
            }
            
            ParameterChecker.check(
                    leg.getValue().getSledContractId() > 0, leg.getKey() + "'s contractId should not <= 0");
            ParameterChecker.check(leg.getValue().getQuantity() >= 0, leg.getKey() + "'s quantity must >= 0");
            ParameterChecker.check(leg.getValue().isSetLegTradeDirection(), leg.getKey() + "'s tradeDirection must set");
            
            long legSledContractId = leg.getValue().getSledContractId();
            if (legSledContractIds.contains(legSledContractId)) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_INVALID.getValue()
                        , "different leg but contains same contract for sledContractId=" + legSledContractId);
            }
            
            SledContractDetails contractDetails = mContractApi.getContractDetailForSure(legSledContractId);
            if (contractDetails.getSledCommodity().getRelateCommodityIdsSize() > 1) {
                throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_CONTAINS_EXCHANGE_COMB_CONTRACT.getValue()
                        , legSledContractId + " is combination contract, not supported");
            }
            super.checkContractDetailRunningMode(contractDetails);
            
            leg.getValue().setSledExchangeMic(contractDetails.getSledCommodity().getExchangeMic());
            leg.getValue().setSledCommodityId(contractDetails.getSledCommodity().getSledCommodityId());
            leg.getValue().setSledCommodityType((short)contractDetails.getSledCommodity().getSledCommodityType().getValue());
            leg.getValue().setSledCommodityCode(contractDetails.getSledCommodity().getSledCommodityCode());
            leg.getValue().setSledContractCode(contractDetails.getSledContract().getSledContractCode());
            
            legSledContractIds.add(legSledContractId);
            
            if (leg.getValue().getQuantity() > 0) {
                quantities.add(leg.getValue().getQuantity());
            }
        }
        
        if (quantities.size() <= 1) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_LEGS_SHOULD_GE_TWO.getValue()
                    , "trade quantity leg's count should >= 2");
        }
        
        BigInteger quantityGcd = null;
        for (Integer q : quantities) {
            if (quantityGcd == null) {
                quantityGcd = BigInteger.valueOf(q);
                continue;
            }
            quantityGcd = quantityGcd.gcd(BigInteger.valueOf(q));
            if (quantityGcd.longValue() <= 1) {
                return ;
            }
        }
        if (quantityGcd.longValue() > 1) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_TRADE_QUANTITY_GCD_NOT_ONE.getValue()
                    , "trade trade quantity gcd is not one");
        }
    }
    
    public void checkFormular(String formular, Set<String> legsKey) throws ErrorInfo {
        Preconditions.checkNotNull(legsKey);
        ParameterChecker.check(StringUtils.isNotBlank(formular), "compose's formular should not be blank");
        
        String expressionFormular = StringUtils.deleteWhitespace(formular);
        Expression exp = null;
        try {
            exp = new ExpressionBuilder(expressionFormular).implicitMultiplication(false).variables(legsKey).build();
        } catch (Throwable e) {
            AppLog.i("parse " + expressionFormular + " failed, " + e.getMessage(), e);
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_INVALID.getValue()
                    , "formular is invalid, can not parse");
        }
        
        if (exp.getVariableNames().size() != legsKey.size()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_INVALID.getValue()
                    , "formular variables is not equals legs size");
        }
        
        ValidationResult vr = exp.validate(false);
        if (!vr.isValid()) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_INVALID.getValue()
                    , "formular is invalid");
        }
    }
    
    public long createComposeGraph(HostingComposeGraph newComposeGraph
            , String aliasName
            , short precisionNumber) throws ErrorInfo {
        if (StringUtils.isNotEmpty(aliasName)) {
    		checkAliasName(aliasName);
    	}
        checkPrecisionNumber(precisionNumber);

        ParameterChecker.check(newComposeGraph != null, "newComposeGraph should not be null");
        checkAndFillLegs(newComposeGraph.getLegs());
        checkFormular(newComposeGraph.getFormular(), newComposeGraph.getLegs().keySet());
        
        HostingComposeGraph operationGraph = new HostingComposeGraph();
        operationGraph.setComposeGraphId(mComposeApi.createComposeGraphId());
        operationGraph.setCreateSubUserId(getSubUserId());
        
        Map<String, HostingComposeLeg> operationLegs = new HashMap<String, HostingComposeLeg>();
        for (Entry<String, HostingComposeLeg> legEntry : newComposeGraph.getLegs().entrySet()) {
            HostingComposeLeg operationLeg = new HostingComposeLeg(legEntry.getValue());
            operationLegs.put(legEntry.getKey(), operationLeg);
        }
        operationGraph.setLegs(operationLegs);
        operationGraph.setFormular(StringUtils.deleteWhitespace(newComposeGraph.getFormular()));
        if (HostingRunningMode.REAL_HOSTING == getRunningMode()) {
            operationGraph.setComposeGraphEnv(HostingComposeGraphEnv.COMPOSE_GRAPH_REAL);
        } else {
            operationGraph.setComposeGraphEnv(HostingComposeGraphEnv.COMPOSE_GRAPH_SIM);
        }

        if (precisionNumber <= 0) {
            mComposeApi.createComposeGraph(getSubUserId(), operationGraph, aliasName, null);
        } else {
            mComposeApi.createComposeGraph(getSubUserId(), operationGraph, aliasName, precisionNumber);
        }
        return operationGraph.getComposeGraphId();
    }
    
    public void delComposeView(long composeGraphId) throws ErrorInfo {
    	ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
    	mComposeApi.delComposeView(getSubUserId(), composeGraphId);
    }
    
    public QuerySameComposeGraphsPage getSameComposeGraphsPage(
    		HostingComposeGraph graph, IndexedPageOption pageOption) throws ErrorInfo {
    	PageOptionHelper.checkIndexedPageOption(pageOption, 20);
    	checkAndFillLegs(graph.getLegs());
    	checkFormular(graph.getFormular(), graph.getLegs().keySet());
    	
    	PageOption apiPageOption = new PageOption();
    	apiPageOption.setPageIndex(pageOption.getPageIndex());
    	apiPageOption.setPageSize(pageOption.getPageSize());
    	apiPageOption.setNeedTotalCount(pageOption.isNeedTotalCount());
    	
    	PageResult<HostingComposeGraph> graphPage = mComposeApi.getSameComposeGraphsPage(graph, apiPageOption);
    	QuerySameComposeGraphsPage resultPage = new QuerySameComposeGraphsPage();
    	resultPage.setTotalCount(graphPage.getTotalCount());
    	resultPage.setGraphs(graphPage.getPageList());
    	return resultPage;
    }
    
    public void addComposeViewBySearch(long composeGraphId
            , String composeGraphKey
            , String aliasName
            , short precisionNumber) throws ErrorInfo {
    	ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
    	ParameterChecker.check(StringUtils.isNotEmpty(composeGraphKey), "composeGraphKey should not be empty");
    	checkPrecisionNumber(precisionNumber);

    	HostingComposeGraph composeGraph = mComposeApi.getComposeGraph(composeGraphId);
    	if (composeGraph == null) {
    		throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
    				, "compose not existed!");
    	}
    	
    	if (!composeGraphKey.equals(composeGraph.getComposeGraphKey())) {
    		throw new ErrorInfo(TradeHostingBasicErrorCode.USER_FORBIDDEN_ERROR.getValue()
    				, "composeGraphKey is not right");
    	}

    	Short opPrecisionNumber = null;
    	if (precisionNumber > 0) {
    	    opPrecisionNumber = precisionNumber;
        }
    	mComposeApi.addComposeViews(composeGraphId
    			, new HashSet<Integer>(Arrays.asList(getSubUserId()))
    			, aliasName
    			, HostingComposeViewSource.USER_SEARCHED
                , opPrecisionNumber);
    }

    public Map<Long, HostingComposeViewDetail> getComposeViewDetails(Set<Long> composeGraphIds) throws ErrorInfo {
        ParameterChecker.checkNotNull(composeGraphIds, "composeGraphIds should not be null");
        ParameterChecker.check(composeGraphIds.size() > 0 && composeGraphIds.size() <= 50
            , "composeGraphIds'size should > 0 and < 50");

        Map<Long, HostingComposeView> composeViewMap
                = mComposeApi.getComposeViewsBySubUserId(getSubUserId(), composeGraphIds);
        Map<Long, HostingComposeViewDetail> resultMap
                = new HashMap<>(composeViewMap.size() + 1);

        if (!composeViewMap.isEmpty()) {
            Map<Long, HostingComposeGraph> composeGraphMap
                    = mComposeApi.getComposeGraphs(composeViewMap.keySet());

            for (HostingComposeView composeView : composeViewMap.values()) {
                HostingComposeViewDetail viewDetail = new HostingComposeViewDetail();
                viewDetail.setViewDetail(composeView);
                viewDetail.setGraphDetail(composeGraphMap.get(composeView.getComposeGraphId()));
                resultMap.put(composeView.getComposeGraphId(), viewDetail);
            }
        }

        return resultMap;
    }
    
    public QueryHostingComposeViewDetailPage getComposeViewDetailPage(
    		QueryHostingComposeViewDetailOption queryOption
    		, IndexedPageOption pageOption) throws ErrorInfo {
    	PageOptionHelper.checkIndexedPageOption(pageOption, 50);
    	
    	QueryComposeViewOption apiQryOption = new QueryComposeViewOption();
    	apiQryOption.setSubUserId(getSubUserId());
    	if (queryOption != null) {
    		if (queryOption.isSetComposeGraphId()) {
    			apiQryOption.setGraphId(queryOption.getComposeGraphId());
    		}
    		if (queryOption.isSetAliasNamePartical()) {
    			apiQryOption.setNamePartical(queryOption.getAliasNamePartical());
    		}
    	}
    	
    	PageOption apiPageOption = new PageOption();
    	apiPageOption.setPageIndex(pageOption.getPageIndex());
    	apiPageOption.setPageSize(pageOption.getPageSize());
    	apiPageOption.setNeedTotalCount(pageOption.isNeedTotalCount());
    	
    	PageResult<HostingComposeView> composeViewPage 
    		= mComposeApi.getComposeViewPage(apiQryOption, apiPageOption);
    	
    	Set<Long> composeGraphIds = new HashSet<Long>(composeViewPage.getPageList().size() + 1);
    	for (HostingComposeView composeView : composeViewPage.getPageList()) {
    		composeGraphIds.add(composeView.getComposeGraphId());
    	}
    	Map<Long, HostingComposeGraph> composeGraphsMap = mComposeApi.getComposeGraphs(composeGraphIds);
    	
    	QueryHostingComposeViewDetailPage resultPage = new QueryHostingComposeViewDetailPage();
    	resultPage.setTotalCount(composeViewPage.getTotalCount());
    	
    	List<HostingComposeViewDetail> composeViewDetailList = new ArrayList<HostingComposeViewDetail>(
    			composeViewPage.getPageList().size() + 1);
    	for (HostingComposeView composeView : composeViewPage.getPageList()) {
    		HostingComposeViewDetail detail = new HostingComposeViewDetail();
    		detail.setViewDetail(composeView);
    		detail.setGraphDetail(composeGraphsMap.get(composeView.getComposeGraphId()));
    		if (detail.getGraphDetail() != null) {
    			detail.getGraphDetail().unsetComposeGraphKey();
    		}
    		composeViewDetailList.add(detail);
    	}
    	resultPage.setResultList(composeViewDetailList);
    	
    	return resultPage;
    }
    
    public void subscribeComposeViewQuotation(long composeGraphId) throws ErrorInfo{
    	ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
    	
    	int subscribedCount = mComposeApi.getSubscribedViewCount(getSubUserId());
    	if (subscribedCount > trade_hostingConstants.MAX_SUBSCRIBE_COMPOSE_NUMBER) {
    		throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_VIEW_SUBSCRIBE_TOO_MANY.getValue()
    				, "subscribed compose view too many!");
    	}
//    	AppLog.d("subscribeComposeViewQuotation subUserId=" + getSubUserId() 
//    		+ ", composeGraphId=" + composeGraphId + ", subscribedCount=" + subscribedCount);
    	
    	mComposeApi.changeComposeViewSubscribeStatus(getSubUserId()
    			, composeGraphId, HostingComposeViewSubscribeStatus.SUBSCRIBED);
    }
    
    public void unSubscribeComposeViewQuotation(long composeGraphId) throws ErrorInfo {
    	ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
    	mComposeApi.changeComposeViewSubscribeStatus(getSubUserId()
    			, composeGraphId, HostingComposeViewSubscribeStatus.UNSUBSCRIBED);
    }
    
    public void changeComposeViewAliasName(long composeGraphId
            , String aliasName) throws ErrorInfo {
    	ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
    	ParameterChecker.check(StringUtils.isNotBlank(aliasName), "aliasName should not be blank");
    	mComposeApi.changelComposeViewAdditionalInfo(getSubUserId(), composeGraphId, aliasName, null);
    }

    public void changeComposeViewPrecisionNumber(long composeGraphId
            , short precisionNumber) throws ErrorInfo {
        ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
        ParameterChecker.check(precisionNumber > 0, "precisionNumber should > 0");
        checkPrecisionNumber(precisionNumber);
        mComposeApi.changelComposeViewAdditionalInfo(getSubUserId(), composeGraphId, null, precisionNumber);
    }
    
    public Map<Long, HostingComposeGraph> getComposeGraphs(Set<Long> composeGraphIds) throws ErrorInfo {
        ParameterChecker.checkNotNull(composeGraphIds, "composeGraphIds should not be null");
        ParameterChecker.check(composeGraphIds.size() > 0 && composeGraphIds.size() <= 50
                , "composeGraphIds'size shoud > 0 and <= 50");
        
        return mComposeApi.getComposeGraphs(composeGraphIds);
    }

    public void addComposeViewByShare(long composeGraphId
            , String aliasName
            , short precisionNumber) throws ErrorInfo {
        ParameterChecker.check(composeGraphId > 0, "composeGraphId should > 0");
        checkPrecisionNumber(precisionNumber);

        HostingComposeGraph composeGraph = mComposeApi.getComposeGraph(composeGraphId);
        if (composeGraph == null) {
            throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
                    , "compose not existed!");
        }

        Short opPrecisionNumber = null;
        if (precisionNumber > 0) {
            opPrecisionNumber = precisionNumber;
        }
        mComposeApi.addComposeViews(composeGraphId
                , new HashSet<>(Arrays.asList(getSubUserId()))
                , aliasName
                , HostingComposeViewSource.GRAPH_SHARED
                , opPrecisionNumber);
    }
}
