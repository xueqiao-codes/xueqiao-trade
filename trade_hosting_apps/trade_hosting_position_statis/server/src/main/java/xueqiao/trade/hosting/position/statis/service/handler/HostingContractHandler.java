package xueqiao.trade.hosting.position.statis.service.handler;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodityConfig;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledContractDetails;
import org.soldier.base.beanfactory.Globals;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.service.bean.SledContractData;
import xueqiao.trade.hosting.position.statis.thriftapi.StatPositionErrorCode;
import xueqiao.trade.hosting.storage.apis.IHostingContractApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HostingContractHandler {

	private IHostingContractApi mContractApi;

	/**
	 * SledContractData 临时缓存
	 */
	private Map<Long, SledContractData> sledContractDataMap = null;

	public HostingContractHandler() {
		mContractApi = Globals.getInstance().queryInterfaceForSure(IHostingContractApi.class);
		sledContractDataMap = new HashMap<>();
	}

	/**
	 * 获取合约计算相关的参数
	 * 用map做一个临时缓存
	 */
	public SledContractData getSledContractData(long sledContractId) throws ErrorInfo {
		if (sledContractDataMap.containsKey(sledContractId)) {
			return sledContractDataMap.get(sledContractId);
		}
		synchronized (sledContractDataMap) {
			if (sledContractDataMap.containsKey(sledContractId)) {
				return sledContractDataMap.get(sledContractId);
			}
			SledContractData contractData = getSledContractDataInner(sledContractId);
			sledContractDataMap.put(sledContractId, contractData);
			return contractData;
		}
	}

	/**
	 * 内部获取SledContractData方法
	 */
	private SledContractData getSledContractDataInner(long sledContractId) throws ErrorInfo {
		ParameterChecker.check(sledContractId > 0, "sledContractId should be more than zero");
		SledContractDetails details = mContractApi.getContractDetailForSure(sledContractId);
		ParameterChecker.check(details != null, "sledContractDetails is null");
		SledCommodity sledCommodity = details.getSledCommodity();
		ParameterChecker.check(sledCommodity != null, "sledCommodity is null");

		SledCommodityConfig SledCommodityConfig = getSledCommodityConfig(sledCommodity);

		SledContractData sledContractData = new SledContractData();
		sledContractData.setSledCommodityId(sledContractId);
		sledContractData.setTradeCurrency(sledCommodity.getTradeCurrency());
		sledContractData.setContractSize(sledCommodity.getContractSize());
		sledContractData.setChargeUnit(SledCommodityConfig.getChargeUnit());

		return sledContractData;
	}

	/**
	 * 获取有效的 SledCommodityConfig
	 */
	private static SledCommodityConfig getSledCommodityConfig(SledCommodity sledCommodity) throws ErrorInfo {
		List<SledCommodityConfig> configList = sledCommodity.getSledCommodityConfig();
		if (configList.size() == 0) {
			throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_COMMODITY_CONFIG_NOT_FOUND.getValue(), "commodity config not found.");
		}

		long now = System.currentTimeMillis() / 1000;
		SledCommodityConfig sledCommodityConfig = null;

		for (SledCommodityConfig config : configList) {
			if (now >= config.getActiveStartTimestamp() && now <= config.getActiveEndTimestamp()) {
				sledCommodityConfig = config;
				break;
			}
		}
		if (sledCommodityConfig == null) {
			throw new ErrorInfo(StatPositionErrorCode.ERROR_STAT_POSITION_COMMODITY_CONFIG_NOT_FOUND.getValue(), "commodity config not found.");
		}
		return sledCommodityConfig;
	}
}
