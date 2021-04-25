package xueqiao.trade.hosting.position.statis.service.handler;

import com.antiy.error_code.ErrorCodeOuter;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.StatDirection;
import xueqiao.trade.hosting.position.statis.service.bean.ComposeGraphLegData;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.report.StatErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingComposeApi;

import java.util.HashMap;
import java.util.Map;

public class HostingComposeHandler {

	private IHostingComposeApi mComposeApi;

	public HostingComposeHandler() {
		mComposeApi = Globals.getInstance().queryInterfaceForSure(IHostingComposeApi.class);
	}

	public HostingComposeGraph getComposeGraph(long composeGraphId) throws ErrorInfo {
		ParameterChecker.check(composeGraphId > 0,"composeGraphId should be more than zero");
		return mComposeApi.getComposeGraph(composeGraphId);
	}

	/**
	 *  获取组合的配比（各腿的数量）
	 *  @return map<sledContractId, quantity> 返回各腿（合约）的数量
	 * */
	public Map<Long, Integer> getComposeProportioning(String targetKey) throws ErrorInfo {
		long composeGraphId = 0;
		try {
			composeGraphId = Long.valueOf(targetKey);
		} catch (Throwable throwable) {
			throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid targetKey, fail to get composeGraph");
		}
		HostingComposeGraph hostingComposeGraph = new HostingComposeHandler().getComposeGraph(composeGraphId);
		if (hostingComposeGraph == null) {
			AppReport.reportErr("fail to find compose graph(targetKey: " + targetKey + ")");
			throw new ErrorInfo(StatErrorCode.innerError.errorCode, "fail to find compose graph(targetKey: " + targetKey + ")");
		}
		Map<Long, Integer> statComposeLegQuantityMap = new HashMap<>();
		for (HostingComposeLeg hostingComposeLeg : hostingComposeGraph.getLegs().values()) {
			statComposeLegQuantityMap.put(hostingComposeLeg.getSledContractId(), hostingComposeLeg.getQuantity());
		}
		return statComposeLegQuantityMap;
	}

	/**
	 *  获取组合的各腿信息
	 *  @return map<sledContractId, ComposeGraphLegData> 返回各腿（合约）的信息
	 * */
	public Map<Long, ComposeGraphLegData> getComposeGraphMap(String targetKey) throws ErrorInfo {
		long composeGraphId = 0;
		try {
			composeGraphId = Long.valueOf(targetKey);
		} catch (Throwable throwable) {
			throw new ErrorInfo(ErrorCodeOuter.PARAM_ERROR.getErrorCode(), "invalid targetKey, fail to get composeGraph");
		}
		HostingComposeGraph hostingComposeGraph = new HostingComposeHandler().getComposeGraph(composeGraphId);
		if (hostingComposeGraph == null) {
			AppReport.reportErr("fail to find compose graph(targetKey: " + targetKey + ")");
			throw new ErrorInfo(StatErrorCode.innerError.errorCode, "fail to find compose graph(targetKey: " + targetKey + ")");
		}
		Map<Long, ComposeGraphLegData> statComposeLegQuantityMap = new HashMap<>();
		ComposeGraphLegData tempComposeGraphLegData;
		for (HostingComposeLeg hostingComposeLeg : hostingComposeGraph.getLegs().values()) {
			tempComposeGraphLegData = new ComposeGraphLegData();
			tempComposeGraphLegData.setSledContractId(hostingComposeLeg.getSledContractId());
			tempComposeGraphLegData.setQuantity(hostingComposeLeg.getQuantity());
			if (hostingComposeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
				tempComposeGraphLegData.setDirection(StatDirection.STAT_BUY);
			} else {
				tempComposeGraphLegData.setDirection(StatDirection.STAT_SELL);
			}
			statComposeLegQuantityMap.put(hostingComposeLeg.getSledContractId(), tempComposeGraphLegData);
		}
		return statComposeLegQuantityMap;
	}
}
