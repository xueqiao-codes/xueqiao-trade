package xueqiao.trade.hosting.position.statis.service.handler;

import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.HostingComposeLeg;
import xueqiao.trade.hosting.HostingComposeLegTradeDirection;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.service.bean.ComposeLegData;
import xueqiao.trade.hosting.position.statis.service.bean.PositionData;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructComposeHandler {

	/**
	 * 参数验证
	 */
//	public void verify(StatContructComposeReq contructComposeReq) throws ErrorInfo {
//		ParameterChecker.checkNotNull(contructComposeReq, "contructComposeReq");
//		ParameterChecker.check(contructComposeReq.getSubAccountId() > 0, "subAccountId should be larger than zero");
//		ParameterChecker.check(contructComposeReq.getComposeGraphId() > 0, "composeGraphId should be larger than zero");
//		ParameterChecker.check(contructComposeReq.getVolume() > 0, "volume should be larger than zero");
//		ParameterChecker.check(contructComposeReq.isSetDiretion(), "direction should be set");
//		ParameterChecker.check(contructComposeReq.isSetComposePrice(), "composePrice should be set");
//		ParameterChecker.check(contructComposeReq.getComposeLegsSize() > 0, "composeLegs should not be null or empty");
//		for (StatComposeLeg statComposeLeg : contructComposeReq.getComposeLegs()) {
//			ParameterChecker.check(statComposeLeg.getSledContractId() > 0, "sledContractId should be larger than zero");
//			ParameterChecker.check(statComposeLeg.getLegTradePrice() > 0, "legTradePrice should be larger than zero");
//		}
//	}

	/**
	 * 验证组合腿数据的合法性，以及提取组合腿信息
	 */
//	public List<ComposeLegData> checkComposeInfo(StatContructComposeReq contructComposeReq) throws ErrorInfo {
//		// 查询对应的组合标的
//		HostingComposeGraph hostingComposeGraph = new HostingComposeHandler().getComposeGraph(contructComposeReq.getComposeGraphId());
//		ParameterChecker.check(hostingComposeGraph != null, "composeGraphId is invalid, fail to find hostingComposeGraph by composeGraphId");
//		ParameterChecker.check(hostingComposeGraph.getLegs() != null && !hostingComposeGraph.getLegs().isEmpty(), "composeGraphId is invalid, legs of hostingComposeGraph are null or empty");
//
//		Map<Long, StatComposeLeg> statComposeLegMap = new HashMap<>();
//		for (StatComposeLeg statComposeLeg : contructComposeReq.getComposeLegs()) {
//			statComposeLegMap.put(statComposeLeg.getSledContractId(), statComposeLeg);
//		}
//
//		StatComposeLeg statComposeLeg = null;
//		ComposeLegData composeLegData = null;
//		List<ComposeLegData> composeLegDataList = new ArrayList<>();
//		for (HostingComposeLeg hostingComposeLeg : hostingComposeGraph.getLegs().values()) {
//			statComposeLeg = statComposeLegMap.get(hostingComposeLeg.getSledContractId());
//			ParameterChecker.check(statComposeLeg != null, "sledContractId not in hostingComposeGraph");
//
//			composeLegData = new ComposeLegData();
//			composeLegData.setSledContractId(hostingComposeLeg.getSledContractId());
//			composeLegData.setQuantity(hostingComposeLeg.getQuantity());
//			if (hostingComposeLeg.getLegTradeDirection() == HostingComposeLegTradeDirection.COMPOSE_LEG_BUY) {
//				composeLegData.setDirection(StatDirection.STAT_BUY);
//			} else {
//				composeLegData.setDirection(StatDirection.STAT_SELL);
//			}
//			composeLegData.setPrice(statComposeLeg.getLegTradePrice());
//
//			composeLegDataList.add(composeLegData);
//		}
//
//		/*
//		* 组合里至少两条腿
//		* 对组合里腿的数量做较验
//		* */
//		ParameterChecker.check(!composeLegDataList.isEmpty(), "contructComposeReq is invalid, composeLegDataList is empty");
//		return composeLegDataList;
//	}

	/**
	 * 将录入组合信息转为持仓明细信息
	 */
//	public StatPositionItem transferToStatPositionItem(StatContructComposeReq statContructComposeReq) throws ErrorInfo {
//		StatPositionItem statPositionItem = new StatPositionItem();
//
//		statPositionItem.setPositionItemId(AppConfig.getStatPositionItemId());
//		statPositionItem.setSubAccountId(statContructComposeReq.getSubAccountId());
//		statPositionItem.setTargetKey(String.valueOf(statContructComposeReq.composeGraphId));
//		statPositionItem.setTargetType(HostingXQTargetType.COMPOSE_TARGET);
//		statPositionItem.setQuantity(statContructComposeReq.getVolume());
//		statPositionItem.setDirection(statContructComposeReq.getDiretion());
//
//		/*
//		 * 组合的价差（组合的单价）是由公式计算出来的，而不是各腿的总价累加。
//		 * 目前该值由客户端传过来
//		 * */
//		statPositionItem.setPrice(statContructComposeReq.getComposePrice());
//
//		StatDataSource statDataSource = new StatDataSource();
//		statDataSource.setSourceDataChannel(DataSourceChannel.FROM_COMPOSE_CONSTRUCTION);
//		statDataSource.setSourceDataTimestampMs(System.currentTimeMillis());
//		statPositionItem.setSource(statDataSource);
//
//		return statPositionItem;
//	}

	/**
	 * 将录入组合信息转为持仓小单元列表
	 */
//	public List<StatPositionUnit> transferToStatPositionUnitList(StatPositionItem statPositionItem, List<ComposeLegData> composeLegDataList) throws ErrorInfo {
//		List<StatPositionUnit> statPositionUnitList = new ArrayList<StatPositionUnit>();
//
//		long currentMillis = System.currentTimeMillis();
//		for (ComposeLegData composeLegData : composeLegDataList) {
//			StatPositionUnit statPositionUnit = new StatPositionUnit();
//
//			statPositionUnit.setUnitId(AppConfig.getStatPositionUnitId());
//			statPositionUnit.setPositionItemId(statPositionItem.getPositionItemId());
//			statPositionUnit.setSledContractId(composeLegData.getSledContractId());
//			statPositionUnit.setUnitPrice(composeLegData.getPrice());
//			/*
//			* 小单元的 unitQuantity 是实际上该合约的数量，而非组合中合约的数量
//			* 这里的计算是：该合约（腿）的实际数量 = 组合中该腿的数量 * 该组合的手数
//			* */
//			statPositionUnit.setUnitQuantity(composeLegData.getQuantity() * statPositionItem.getQuantity());
//			/*
//			* 小单元里的买卖方向，就是最终的买卖方向。
//			* 所以，这里需要将组合里腿的方向，转换为小单元里的最终买卖方向
//			* */
//			if (statPositionItem.getDirection() == composeLegData.getDirection()) {
//				statPositionUnit.setDirection(StatDirection.STAT_BUY);
//			} else {
//				statPositionUnit.setDirection(StatDirection.STAT_SELL);
//			}
//			statPositionUnit.setSourceDataTimestampMs(currentMillis);
//
//			/*
//			* new added fields
//			* */
//			statPositionUnit.setSubAccountId(statPositionItem.getSubAccountId());
//			statPositionUnit.setTargetKey(statPositionItem.getTargetKey());
//			statPositionUnit.setTargetType(statPositionItem.getTargetType());
//			ExternalDataSource dataSource = new ExternalDataSource();
//			dataSource.setSourceId()
//
//			statPositionUnitList.add(statPositionUnit);
//		}
//
//		/*
//		 * 较验小单元数据
//		 * */
//		ParameterChecker.check(!statPositionUnitList.isEmpty(), "fail to get statPositionUnitList");
//		return statPositionUnitList;
//	}

	/**
	 * 构造反向组合持仓数据 (构造的是合约信息)
	 * ( 其实直接拿构造组合的小单元数据来构造反向数据 会更简捷，历史原因，暂不做改动 )
	 */
//	public List<PositionData> constructReversePositionDataList(StatContructComposeReq contructComposeReq, List<ComposeLegData> composeLegDataList) throws ErrorInfo {
//		List<PositionData> positionDataList = new ArrayList<>();
//
//		long currentMillis = System.currentTimeMillis();
//
//		for (ComposeLegData composeLegData : composeLegDataList) {
//			PositionData positionData = new PositionData();
//			StatPositionItem statPositionItem = new StatPositionItem();
//			List<StatPositionUnit> statPositionUnitList = new ArrayList<>();
//
//			// 持仓明细
//			statPositionItem.setPositionItemId(AppConfig.getStatPositionItemId());
//			statPositionItem.setSubAccountId(contructComposeReq.getSubAccountId());
//			statPositionItem.setTargetKey(String.valueOf(composeLegData.getSledContractId()));
//			statPositionItem.setTargetType(HostingXQTargetType.CONTRACT_TARGET);
//			statPositionItem.setPrice(composeLegData.getPrice());
//			statPositionItem.setQuantity(contructComposeReq.getVolume() * composeLegData.getQuantity());  /* 这里的数量是 组合的手数 * 该腿的数量 */
//
//			/* 这里要注意是反向，原组合里是买，这里则卖，反之亦然
//			 *  买 + 买 -》 卖
//			 *  买 + 卖 -》 买
//			 *  卖 + 买 -》 买
//			 *  卖 + 卖 -》 卖
//			 * */
//			if (contructComposeReq.getDiretion() == composeLegData.getDirection()) {
//				statPositionItem.setDirection(StatDirection.STAT_SELL);
//			} else {
//				statPositionItem.setDirection(StatDirection.STAT_BUY);
//			}
//
//			StatDataSource statDataSource = new StatDataSource();
//			statDataSource.setSourceDataChannel(DataSourceChannel.FROM_COMPOSE_REVERSE_CONSTRUCTION);
//			statDataSource.setSourceDataTimestampMs(currentMillis);
//			statPositionItem.setSource(statDataSource);
//
//			// 持仓小单元
//			StatPositionUnit statPositionUnit = new StatPositionUnit();
//			statPositionUnit.setUnitId(AppConfig.getStatPositionUnitId());
//			statPositionUnit.setPositionItemId(statPositionItem.getPositionItemId());
//			statPositionUnit.setSledContractId(composeLegData.getSledContractId());
//			statPositionUnit.setUnitPrice(composeLegData.getPrice());
//			/*
//			* 小单元中的数量，就是实际上成交的数量。
//			* 所以，合约持仓的小单的数量 等于 合约持仓的数量
//			* */
//			statPositionUnit.setUnitQuantity(statPositionItem.getQuantity());
//			/*
//			* 标的为合约时，item的方向与小单元的方向一致
//			* 因为小单元里的方向，就是标示最终的买卖方向
//			* */
//			statPositionUnit.setDirection(statPositionItem.getDirection());
//			statPositionUnit.setSourceDataTimestampMs(currentMillis);
//			statPositionUnitList.add(statPositionUnit);                /* 对于标的为合约，只有一个对应的小单元 */
//
//			positionData.setStatPositionItem(statPositionItem);
//			positionData.setStatPositionUnitList(statPositionUnitList);
//			positionDataList.add(positionData);
//		}
//
//		/*
//		 * 较验反向构造数据
//		 * */
//		ParameterChecker.check(!positionDataList.isEmpty(), "contructComposeReq is invalid, positionDataList is empty");
//		return positionDataList;
//	}

	/**
	 * 构造组合持仓
	 */
//	public void constructComposePosition(StatPositionItem statPositionItem, List<StatPositionUnit> statPositionUnitList, List<PositionData> reverseConstructionPositionDataList) throws ErrorInfo {
//		// 数据较验
//		Map<Long, StatPositionUnit> statPositionUnitMap = new HashMap<>();
//		for (StatPositionUnit unit : statPositionUnitList) {
//			statPositionUnitMap.put(unit.getSledContractId(), unit);
//		}
//		StatPositionUnit statPositionUnit;
//		for (PositionData reverseConstructionPositionData : reverseConstructionPositionDataList) {
//			statPositionUnit = statPositionUnitMap.get(Long.valueOf(reverseConstructionPositionData.getStatPositionItem().getTargetKey()));
//			ParameterChecker.check(statPositionUnit != null, "constructComposePosition fail, reverse data direction error");
//			/*
//			* 录入数据方向与反向数据方向相反
//			* */
//			ParameterChecker.check(reverseConstructionPositionData.getStatPositionItem().getDirection() != statPositionUnit.getDirection(), "constructComposePosition fail, reverse data direction error");
//			/*
//			* 录入数据数量成反向数据数量一致
//			* */
//			int unitQuantity = statPositionUnit.getUnitQuantity()/* * statPositionItem.getQuantity()*/;
//			ParameterChecker.check(reverseConstructionPositionData.getStatPositionItem().getQuantity() == unitQuantity, "constructComposePosition fail, reverse data quantity not match");
//		}
//
//
//		new DBTransactionHelper<Void>(PositionStatisDB.Global()) {
//			@Override
//			public void onPrepareData() throws ErrorInfo, Exception {
//			}
//
//			@Override
//			public void onUpdate() throws ErrorInfo, Exception {
//				StatPositionHandler statPositionHandler = new StatPositionHandler();
//				// 1 组合构造
//				statPositionHandler.addPositionData(getConnection(), statPositionItem, statPositionUnitList);
//
//				// 2 组合反向构造
//				for (PositionData positionData : reverseConstructionPositionDataList) {
//					statPositionHandler.addPositionData(getConnection(), positionData.getStatPositionItem(), positionData.getStatPositionUnitList());
//				}
//			}
//
//			@Override
//			public Void getResult() {
//				return null;
//			}
//		}.execute();
//	}
}
