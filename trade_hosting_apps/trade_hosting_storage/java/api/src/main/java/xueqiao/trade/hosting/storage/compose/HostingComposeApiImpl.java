package xueqiao.trade.hosting.storage.compose;

import java.sql.Connection;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeView;
import xueqiao.trade.hosting.HostingComposeViewSource;
import xueqiao.trade.hosting.HostingComposeViewStatus;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;
import xueqiao.trade.hosting.TradeHostingBasicErrorCode;
import xueqiao.trade.hosting.events.ComposeViewEvent;
import xueqiao.trade.hosting.events.ComposeViewEventType;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBStepHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.framework.utils.ErrorInfoCallHelper;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryComposeViewOption;
import xueqiao.trade.hosting.storage.comm.ComposeDB;
import xueqiao.trade.hosting.storage.comm.StorageApiStub;

public class HostingComposeApiImpl implements IHostingComposeApi {
	@Override
	public HostingComposeGraph getComposeGraph(long composeGraphId) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(composeGraphId > 0);
		return new DBQueryHelper<HostingComposeGraph>(ComposeDB.Global()) {
			@Override
			protected HostingComposeGraph onQuery(Connection conn) throws Exception {
				return new HostingComposeGraphTable(conn).getComposeGraph(composeGraphId, false);
			}
		}.query();
	}

    @Override
    public long createComposeGraphId() throws ErrorInfo {
        return ErrorInfoCallHelper.call(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return new StorageApiStub().createComposeGraphId();
            }
        });
    }

	@Override
	public Map<Long, HostingComposeGraph> getComposeGraphs(Set<Long> composeGraphIds) throws ErrorInfo {
		if (composeGraphIds == null || composeGraphIds.isEmpty()) {
			return new HashMap<Long, HostingComposeGraph>();
		}
		return new DBQueryHelper<Map<Long, HostingComposeGraph>>(ComposeDB.Global()) {
			@Override
			protected Map<Long, HostingComposeGraph> onQuery(Connection conn) throws Exception {
				return new HostingComposeGraphTable(conn).getComposeGraphs(composeGraphIds);
			}
		}.query();
	}
	
	public PageResult<HostingComposeGraph> getSameComposeGraphsPage(HostingComposeGraph graph
			, PageOption pageOption) throws ErrorInfo {
		ParameterChecker.checkInnerNotNull(graph);
		ParameterChecker.checkInnerArgument(StringUtils.isNotEmpty(graph.getFormular()));
		ParameterChecker.checkInnerArgument(graph.isSetLegs() && !graph.getLegs().isEmpty());
		ParameterChecker.checkInnerNotNull(pageOption);
		pageOption.checkValid();
		
		return new DBQueryHelper<PageResult<HostingComposeGraph>>(ComposeDB.Global()) {

			@Override
			protected PageResult<HostingComposeGraph> onQuery(Connection conn) throws Exception {
				return new HostingComposeGraphTable(conn).getSameComposeGraphPage(graph, pageOption);
			}
			
		}.query();
	}

	@Override
	public PageResult<HostingComposeView> getComposeViewPage(QueryComposeViewOption qryOption, PageOption pageOption)
			throws ErrorInfo {
		ParameterChecker.checkInnerNotNull(pageOption);
		pageOption.checkValid();

		return new DBQueryHelper<PageResult<HostingComposeView>>(ComposeDB.Global()) {
			@Override
			protected PageResult<HostingComposeView> onQuery(Connection conn) throws Exception {
				if (qryOption == null) {
					return new HostingComposeViewTable(conn).getComposeViewPage(new QueryComposeViewOption(), pageOption);
				}
				return new HostingComposeViewTable(conn).getComposeViewPage(qryOption, pageOption);
			}
		}.query();
	}


	@Override
	public int getSubscribedViewCount(int subUserId) throws ErrorInfo {
		return new DBQueryHelper<Integer>(ComposeDB.Global()) {
			@Override
			protected Integer onQuery(Connection conn) throws Exception {
				return new HostingComposeViewTable(conn).getSubscribedViewCount(subUserId);
			}
		}.query();
	}

	@Override
	public void changeComposeViewSubscribeStatus(int subUserId
			, long graphId
			, HostingComposeViewSubscribeStatus subscribeStatus) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(subUserId > 0);
		ParameterChecker.checkInnerArgument(graphId > 0);
		ParameterChecker.checkInnerNotNull(subscribeStatus);
		
		new DBTransactionHelperWithMsg<Void>(ComposeDB.Global()) {
			private boolean needUpdate = true;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingComposeView composeView 
					= new HostingComposeViewTable(getConnection()).getComposeView(subUserId, graphId, true);
				if (composeView == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_VIEW_NOT_EXISTED.getValue()
							, "compose view not existed");
				}
				
				if (composeView.getSubscribeStatus() == subscribeStatus) {
					needUpdate = false;
				}
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				if (!needUpdate) {
					return ;
				}
				
				HostingComposeView opComposeView = new HostingComposeView();
				opComposeView.setSubUserId(subUserId);
				opComposeView.setComposeGraphId(graphId);
				opComposeView.setSubscribeStatus(subscribeStatus);
				
				new HostingComposeViewTable(getConnection()).updateComposeView(opComposeView);
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (!needUpdate) {
					return null;
				}
				
				ComposeViewEvent event = new ComposeViewEvent();
				if (subscribeStatus == HostingComposeViewSubscribeStatus.SUBSCRIBED) {
					event.setType(ComposeViewEventType.COMPOSE_VIEW_SUBSCRIBED);
				} else {
					event.setType(ComposeViewEventType.COMPOSE_VIEW_UNSUBSCRIBD);
				}
				event.setComposeGraphId(graphId);
				event.setSubUserIds(new HashSet<Integer>(Arrays.asList(subUserId)));
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
		}.execute();
	}

	@Override
	public void delComposeView(int subUserId, long graphId) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(subUserId > 0);
		ParameterChecker.checkInnerArgument(graphId > 0);
		
		new DBStepHelperWithMsg<Void>(ComposeDB.Global()) {
			private HostingComposeView deleteView;
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				deleteView =  new HostingComposeViewTable(getConnection())
						.getComposeView(subUserId, graphId, true);
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				if (deleteView == null || deleteView.getViewStatus() == HostingComposeViewStatus.VIEW_DELETED) {
					return ;
				}

				HostingComposeView operateView = new HostingComposeView();
				operateView.setSubUserId(subUserId);
				operateView.setComposeGraphId(graphId);
				operateView.setSubscribeStatus(HostingComposeViewSubscribeStatus.UNSUBSCRIBED);
				operateView.setViewStatus(HostingComposeViewStatus.VIEW_DELETED);

				new HostingComposeViewTable(getConnection()).updateComposeView(operateView);
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (deleteView == null || deleteView.getViewStatus() == HostingComposeViewStatus.VIEW_DELETED) {
					return null;
				}
				
				ComposeViewEvent event = new ComposeViewEvent();
				event.setType(ComposeViewEventType.COMPOSE_VIEW_DELETED);
				event.setComposeGraphId(graphId);
				event.setSubUserIds(new HashSet<Integer>(Arrays.asList(subUserId)));
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
			
		}.execute();
		
	}
	
	@Override
	public void clearAll() throws ErrorInfo {
		new DBTransactionHelperWithMsg<Void>(ComposeDB.Global()) {
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			}
			
			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				new HostingComposeViewTable(getConnection()).deleteAll();
				new HostingComposeGraphTable(getConnection()).deleteAll();
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				ComposeViewEvent event = new ComposeViewEvent();
				event.setType(ComposeViewEventType.COMPOSE_VIEW_ALL_CLEARED);
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(5));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
	            
		}.execute();
	}
	
	private String generateComposeGraphKey() {
		StringBuilder keyBuilder = new StringBuilder(64);
		for (int index = 0; index < 62; ++index) {
			keyBuilder.append((char)((int)'a' + RandomUtils.nextInt(26)));
		}
		return keyBuilder.toString();
	}

	@Override
	public void createComposeGraph(int subUserId
			, HostingComposeGraph graph
			, String aliasName
			, Short precisionNumber) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(subUserId > 0);
		ParameterChecker.checkInnerNotNull(graph != null);
		ParameterChecker.checkInnerArgument(graph.getComposeGraphId() > 0);
	    ParameterChecker.checkInnerArgument(StringUtils.isNotBlank(graph.getFormular()));
	    ParameterChecker.checkInnerArgument(graph.isSetLegs() && !graph.getLegs().isEmpty());
	    ParameterChecker.checkInnerArgument(graph.isSetComposeGraphEnv());
	    for (Entry<String, HostingComposeLeg> legEntry : graph.getLegs().entrySet()) {
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetVariableName());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetLegTradeDirection());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetQuantity());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetSledExchangeMic());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetSledCommodityId());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetSledCommodityCode());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetSledCommodityType());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetSledContractId());
	        ParameterChecker.checkInnerArgument(legEntry.getValue().isSetSledContractCode());
	    }
		
	    new DBTransactionHelperWithMsg<Void>(ComposeDB.Global()) {
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				HostingComposeGraph newComposeGraph = new HostingComposeGraph();
				newComposeGraph.setCreateSubUserId(subUserId);
				newComposeGraph.setComposeGraphId(graph.getComposeGraphId());
				newComposeGraph.setFormular(graph.getFormular());
				newComposeGraph.setLegs(graph.getLegs());
				newComposeGraph.setComposeGraphKey(generateComposeGraphKey());
				newComposeGraph.setComposeGraphEnv(graph.getComposeGraphEnv());
				new HostingComposeGraphTable(getConnection()).addComposeGraph(newComposeGraph);

				HostingComposeView newComposeView = new HostingComposeView();
				newComposeView.setSubUserId(subUserId);
				newComposeView.setComposeGraphId(graph.getComposeGraphId());
				if (StringUtils.isNotBlank(aliasName)) {
					newComposeView.setAliasName(StringUtils.trimToEmpty(aliasName));
				}
				if (precisionNumber != null && precisionNumber > 0) {
					newComposeView.setPrecisionNumber(precisionNumber);
				}
				newComposeView.setSubscribeStatus(HostingComposeViewSubscribeStatus.UNSUBSCRIBED);
				newComposeView.setViewSource(HostingComposeViewSource.USER_CREATED);
				new HostingComposeViewTable(getConnection()).addComposeView(newComposeView);
			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				ComposeViewEvent event = new ComposeViewEvent();
				event.setType(ComposeViewEventType.COMPOSE_VIEW_ADDED);
				event.setComposeGraphId(graph.getComposeGraphId());
				event.setSubUserIds(new HashSet<Integer>(Arrays.asList(subUserId)));
				return new SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(2));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
	    	
	    }.execute();
	}

	@Override
	public void addComposeViews(long graphId
			, Set<Integer> subUserIds
			, String aliasName
			, HostingComposeViewSource viewSource
			, Short precisionNumber) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(graphId > 0);
		ParameterChecker.checkInnerNotNull(subUserIds);
		ParameterChecker.checkInnerArgument(!subUserIds.isEmpty());
		ParameterChecker.checkInnerArgument(viewSource != null);
		
		new DBTransactionHelperWithMsg<Void>(ComposeDB.Global()) {
			private Set<Integer> needAddedSubUserIds = new HashSet<>();
			private Set<Integer> needUpdateDeletedSubUserIds = new HashSet<>();
			private Set<Integer> needUpdateInfoSubUserIds = new HashSet<>();
			
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingComposeGraph composeGraph 
					= new HostingComposeGraphTable(getConnection()).getComposeGraph(graphId, false);
				if (composeGraph == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_GRAPH_NOT_EXISTED.getValue()
							, "compose graph not existed");
				}
				
				Map<Integer, HostingComposeView> resultMap 
					= new HostingComposeViewTable(getConnection()).getUsersComposeView(subUserIds, graphId, null);
				for (Integer subUserId : subUserIds) {
					HostingComposeView composeView = resultMap.get(subUserId);
//					AppLog.i("addComposeViews subUserId=" + subUserId + ", graphId=" + graphId + ", composeView=" + composeView);
					if (composeView == null) {
						needAddedSubUserIds.add(subUserId);
					} else {
						if (composeView.getViewStatus() == HostingComposeViewStatus.VIEW_DELETED) {
							needUpdateDeletedSubUserIds.add(subUserId);
						} else {
							needUpdateInfoSubUserIds.add(subUserId);
						}
					}
				}
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				if (needAddedSubUserIds.isEmpty()
						&& needUpdateDeletedSubUserIds.isEmpty()
						&& needUpdateInfoSubUserIds.isEmpty()) {
					return ;
				}
				
				HostingComposeViewTable composeViewTable = new HostingComposeViewTable(getConnection());
				HostingComposeView opComposeView = new HostingComposeView();
				opComposeView.setComposeGraphId(graphId);
				if (StringUtils.isNotBlank(aliasName)) {
					opComposeView.setAliasName(StringUtils.trimToEmpty(aliasName));
				}
				if (precisionNumber != null && precisionNumber > 0) {
					opComposeView.setPrecisionNumber(precisionNumber);
				}
				opComposeView.setViewSource(viewSource);
				opComposeView.setViewStatus(HostingComposeViewStatus.VIEW_NORMAL);

				for (Integer subUserId : needAddedSubUserIds) {
					opComposeView.setSubUserId(subUserId);
					opComposeView.setSubscribeStatus(HostingComposeViewSubscribeStatus.UNSUBSCRIBED);
					composeViewTable.addComposeView(opComposeView);
				}
				for (Integer subUserId : needUpdateDeletedSubUserIds) {
					opComposeView.setSubUserId(subUserId);
					opComposeView.setSubscribeStatus(HostingComposeViewSubscribeStatus.UNSUBSCRIBED);
					composeViewTable.updateComposeView(opComposeView);
				}
				for (Integer subUserId : needUpdateInfoSubUserIds) {
					opComposeView.setSubUserId(subUserId);
					opComposeView.unsetSubscribeStatus();
					composeViewTable.updateComposeView(opComposeView);
				}

			}

			@Override
			public Void getResult() {
				return null;
			}

			@SuppressWarnings("rawtypes")
			@Override
			protected SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (needAddedSubUserIds.isEmpty() && needUpdateDeletedSubUserIds.isEmpty()) {
					return null;
				}

				HashSet<Integer> subUserIds = new HashSet<>();
				subUserIds.addAll(needAddedSubUserIds);
				subUserIds.addAll(needUpdateDeletedSubUserIds);

				ComposeViewEvent event = new ComposeViewEvent();
				event.setType(ComposeViewEventType.COMPOSE_VIEW_ADDED);
				event.setComposeGraphId(graphId);
				event.setSubUserIds(subUserIds);
				return new SimpleEntry<TBase, IGuardPolicy>(event
						, new TimeoutGuardPolicy().setTimeoutSeconds((needAddedSubUserIds.size()/20) + 1));
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}
			
		}.execute();
	}

	@Override
	public HostingComposeView getComposeView(int subUserId, long graphId) throws ErrorInfo {
		return new DBQueryHelper<HostingComposeView>(ComposeDB.Global()) {
			@Override
			protected HostingComposeView onQuery(Connection conn) throws Exception {
				return new HostingComposeViewTable(conn).getComposeView(subUserId, graphId, false);
			}
		}.query();
	}


	@Override
	public void changelComposeViewAdditionalInfo(
			int subUserId
			, long graphId
			, String aliasName
			, Short precisionNumber) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(subUserId > 0);
		ParameterChecker.checkInnerArgument(graphId > 0);
		
		new DBTransactionHelper<Void>(ComposeDB.Global()) {
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				HostingComposeView composeView 
					= new HostingComposeViewTable(getConnection()).getComposeView(subUserId, graphId, true);
				if (composeView == null) {
					throw new ErrorInfo(TradeHostingBasicErrorCode.ERROR_COMPOSE_VIEW_NOT_EXISTED.getValue()
							, "compose view not existed");
				}
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				HostingComposeView opComposeView = new HostingComposeView();
				opComposeView.setSubUserId(subUserId);
				opComposeView.setComposeGraphId(graphId);
				if (aliasName != null) {
					if (StringUtils.isBlank(aliasName)) {
						opComposeView.setAliasName("");
					} else {
						opComposeView.setAliasName(StringUtils.trimToEmpty(aliasName));
					}
				}
				if (precisionNumber != null && precisionNumber > 0) {
					opComposeView.setPrecisionNumber(precisionNumber);
				}
				
				new HostingComposeViewTable(getConnection()).updateComposeView(opComposeView);
			}

			@Override
			public Void getResult() {
				return null;
			}
			
		}.execute();
	}

	@Override
	public Map<Long, HostingComposeView> getComposeViewsBySubUserId(int subUserId
			, Set<Long> graphIds) throws ErrorInfo {
		ParameterChecker.checkInnerArgument(subUserId > 0);
		ParameterChecker.checkInnerArgument(graphIds != null && graphIds.size() > 0);

		return new DBQueryHelper<Map<Long, HostingComposeView>>(ComposeDB.Global()) {

			@Override
			protected Map<Long, HostingComposeView> onQuery(Connection conn) throws Exception {
				return new HostingComposeViewTable(conn).getComposeViewBySubUserId(subUserId, graphIds, null);
			}
		}.query();
	}

}
