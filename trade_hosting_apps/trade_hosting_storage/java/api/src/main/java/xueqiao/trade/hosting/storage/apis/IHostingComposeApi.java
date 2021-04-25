package xueqiao.trade.hosting.storage.apis;

import java.util.Map;
import java.util.Set;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeView;
import xueqiao.trade.hosting.HostingComposeViewSource;
import xueqiao.trade.hosting.HostingComposeViewSubscribeStatus;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;
import xueqiao.trade.hosting.storage.apis.structs.QueryComposeViewOption;

public interface IHostingComposeApi {
    
    public long createComposeGraphId() throws ErrorInfo;
    
	public HostingComposeGraph getComposeGraph(long graphId) throws ErrorInfo;
	public Map<Long, HostingComposeGraph> getComposeGraphs(Set<Long> graphIds) throws ErrorInfo;
	public PageResult<HostingComposeGraph> getSameComposeGraphsPage(
			HostingComposeGraph graph, PageOption pageOption) throws ErrorInfo;
	
	public HostingComposeView getComposeView(int subUserId, long graphId) throws ErrorInfo;

	public Map<Long, HostingComposeView> getComposeViewsBySubUserId(int subUserId, Set<Long> graphIds)
			throws ErrorInfo;

	/**
	 *  查询可见视图页
	 * @param qryOption
	 * @param pageOption
	 * @return
	 * @throws ErrorInfo
	 */
	public PageResult<HostingComposeView> getComposeViewPage(
			QueryComposeViewOption qryOption, PageOption pageOption) throws ErrorInfo;


	/**
	 *  获取用户订阅视图的总数
	 */
	public int getSubscribedViewCount(int subUserId) throws ErrorInfo;
		
	/**
	 *  用户创建
	 */
	public void createComposeGraph(int subUserId
			, HostingComposeGraph graph
			, String aliasName
			, Short precisionNumber) throws ErrorInfo;
	
	/**
	 *  批量添加组合视图
	 */
	public void addComposeViews(long graphId
			, Set<Integer> subUserIds
			, String aliasName
			, HostingComposeViewSource viewSource
			, Short precisionNumber) throws ErrorInfo;
	
	/**
	 * 更新组合订阅状态
	 */
	public void changeComposeViewSubscribeStatus(
			int subUserId
			, long graphId
			, HostingComposeViewSubscribeStatus subscribeStatus) throws ErrorInfo;
	
	/**
	 * 变更组合视图附加信息
	 */
	public void changelComposeViewAdditionalInfo(
			int subUserId
			, long graphId
			, String aliasName
			, Short precisionNumber) throws ErrorInfo;
	
	/**
	 * 删除组合视图
	 */
	public void delComposeView(int subUserId, long graphId) throws ErrorInfo;
	
	public void clearAll() throws ErrorInfo;
}
