package xueqiao.trade.hosting.position.statis.service;

import org.apache.thrift.TBase;
import org.soldier.framework.message_bus.api.IGuardPolicy;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.soldier.framework.message_bus.api.TimeoutGuardPolicy;
import org.soldier.platform.db_helper.DBTransactionHelper;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.events.StatPositionEventType;
import xueqiao.trade.hosting.events.StatPositionSummaryChangedEvent;
import xueqiao.trade.hosting.events.StatPositionSummaryChangedGuardEvent;
import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.DBTransactionHelperWithMsg;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.app.AppConfig;
import xueqiao.trade.hosting.position.statis.service.bean.ArchiveItemData;
import xueqiao.trade.hosting.position.statis.service.bean.ClosedPositionData;
import xueqiao.trade.hosting.position.statis.service.handler.StatClosedPositionHandler;
import xueqiao.trade.hosting.position.statis.service.handler.StatPositionSummaryHandler;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;
import xueqiao.trade.hosting.position.statis.service.thread.StatSingleWorkerThread;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.*;

import java.util.*;

public class ArchivePositionService {

	StatClosedPositionHandler statClosedPositionHandler = new StatClosedPositionHandler();
	StatClosedPositionSummary statClosedPositionSummary;
	List<StatClosedPositionSummary> statClosedPositionSummaryList = null;

	/**
	 * 归档
	 * 将平仓表中的数据移到归档表中（统计表和明细表）
	 */
	public void archive() throws ErrorInfo {

		if (AppReport.TRACE) {
			AppReport.trace(this.getClass(), "archive");
		}

		// 清除空持仓汇总（长仓和短仓都为0）
		StatPositionSummaryHandler.clearPositionSummary();

		// 查找平仓表，如不为空，则返回一条平仓数据
		while (findStatClosedPositionSummary()) {
			doArchive();
		}
	}

	private boolean findStatClosedPositionSummary() throws ErrorInfo {
		statClosedPositionSummary = statClosedPositionHandler.findFirstStatClosedPositionSummary();
		if (statClosedPositionSummary != null) {
			statClosedPositionSummaryList = statClosedPositionHandler.queryStatClosedPositionSummary(statClosedPositionSummary.getSubAccountId(), statClosedPositionSummary.getTargetKey(), statClosedPositionSummary.getTargetType());
			return true;
		}
		return false;
	}

	/**
	 * 归档
	 * 把同一子账号同一标的的平仓记录归档到一个天归档记录中
	 * 同时更新持仓小单元（平仓时未处理，留在此处处理）
	 */
	private void doArchive() throws ErrorInfo {
		new StatSingleWorkerThread<Void>() {

			@Override
			protected Void onCall() throws Exception {
				_doArchive();
				return null;
			}
		}.get();
	}

	private void _doArchive() throws ErrorInfo {

		if (AppReport.TRACE) {
			AppReport.trace(this.getClass(), "doArchive -- statClosedPositionSummary : " + statClosedPositionSummary.toString());
		}

		List<ArchiveItemData> archiveItemDataList = new ArrayList<>();
		StatClosedPositionDateSummary statClosedPositionDateSummary = new StatClosedPositionDateSummary();
		statClosedPositionDateSummary.setDateSummaryId(AppConfig.getStatClosedPositionDateSummaryId());
		statClosedPositionDateSummary.setSubAccountId(statClosedPositionSummary.getSubAccountId());
		statClosedPositionDateSummary.setTargetKey(statClosedPositionSummary.getTargetKey());
		statClosedPositionDateSummary.setTargetType(statClosedPositionSummary.getTargetType());

		long closedPosition = 0;
		double tempCloseProfit = 0.0;
		double spreadProfit = 0.0;
		Map<String, Double> closedProfitMap = new HashMap<>();

		for (StatClosedPositionSummary closedPositionSummary : statClosedPositionSummaryList) {

			closedPosition += closedPositionSummary.getClosedPosition();
			spreadProfit += closedPositionSummary.getSpreadProfit();

			for (ClosedProfit closedProfit : closedPositionSummary.getClosedProfits()) {
				if (closedProfitMap.containsKey(closedProfit.getTradeCurrency())) {
					tempCloseProfit = closedProfitMap.get(closedProfit.getTradeCurrency());
				} else {
					tempCloseProfit = 0.0;
				}
				tempCloseProfit += closedProfit.getClosedProfitValue();
				closedProfitMap.put(closedProfit.getTradeCurrency(), tempCloseProfit);
			}

			ArchiveItemData archiveItemData = new ArchiveItemData();
			archiveItemData.setStatClosedPositionSummary(closedPositionSummary);
			// 查找对应的平仓明细
			List<StatClosedPositionItem> statClosedPositionItemList = statClosedPositionHandler.queryStatClosedPositionItemList(closedPositionSummary.getClosedId());
			if (statClosedPositionItemList == null || statClosedPositionItemList.size() < 1) {
				AppReport.reportErr(new StringBuilder(this.getClass().getSimpleName()).append(" ### doArchive ---- ").append("closed position items not exist(closedId:").append(statClosedPositionSummary.getClosedId()).append(")").toString());
				/*
				 * 平仓是根据持仓明细来计算平仓汇总的,
				 * 所以，正常情况下不会出现该错误。
				 * 如果出现，则说明操作平仓时逻辑有错，或者其他不可预知的错误
				 * 如果出现，则默认该项为0吧
				 * */
				statClosedPositionItemList = new ArrayList<>();
			}
			List<ClosedPositionData> closedPositionDataList = new ArrayList<>();
			ClosedPositionData closedPositionData;
			for (StatClosedPositionItem closedPositionItem : statClosedPositionItemList) {
				List<StatClosedUnit> closedUnitList = statClosedPositionHandler.queryClosedUnitList(closedPositionItem.getClosedItemId());
				closedPositionData = new ClosedPositionData();
				closedPositionData.setClosedPositionItem(closedPositionItem);
				closedPositionData.setClosedUnitList(closedUnitList);
				closedPositionDataList.add(closedPositionData);
			}
			archiveItemData.setClosedPositionDataList(closedPositionDataList);
			archiveItemDataList.add(archiveItemData);
		}
		statClosedPositionDateSummary.setClosedPosition(closedPosition);
		statClosedPositionDateSummary.setSpreadProfit(spreadProfit);

		List<ClosedProfit> closedProfitList = new ArrayList<ClosedProfit>();
		ClosedProfit closedProfit = null;
		for (String currency : closedProfitMap.keySet()) {
			closedProfit = new ClosedProfit();
			closedProfit.setTradeCurrency(currency);
			closedProfit.setClosedProfitValue(closedProfitMap.get(currency).doubleValue());
			closedProfitList.add(closedProfit);
		}

		statClosedPositionDateSummary.setClosedProfits(closedProfitList);

//		if (AppReport.TRACE) {
//			AppReport.trace(this.getClass(), "doArchive -- before transaction ----  archiveItemDataList : " + new Gson().toJson(archiveItemDataList));
//		}

		/* 事务开始 */
		new DBTransactionHelper<Void>(PositionStatisDB.Global()) {
			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {

				// 写入天汇总归档表
				new StatArchiveDateSummaryTable(getConnection()).insert(statClosedPositionDateSummary);

				// 写入天汇总平仓收益
				StatDateSummaryClosedProfitTable statDateSummaryClosedProfitTable = new StatDateSummaryClosedProfitTable(getConnection());
				for (ClosedProfit closedProfitItem : statClosedPositionDateSummary.getClosedProfits()) {
					statDateSummaryClosedProfitTable.insert(statClosedPositionDateSummary.getDateSummaryId(), closedProfitItem);
				}

				StatArchiveSummaryTable statArchiveSummaryTable = new StatArchiveSummaryTable(getConnection());
				StatArchiveItemTable statArchiveItemTable = new StatArchiveItemTable(getConnection());
				StatArchiveUnitTable statArchiveUnitTable = new StatArchiveUnitTable(getConnection());
//				StatPositionItemTable statPositionItemTable = new StatPositionItemTable(getConnection());
//				StatPositionUnitTable statPositionUnitTable = new StatPositionUnitTable(getConnection());

				for (ArchiveItemData archiveItemData : archiveItemDataList) {
					// 写入归档表
					statArchiveSummaryTable.insert(archiveItemData.getStatClosedPositionSummary());


					for (ClosedPositionData closedPositionData : archiveItemData.getClosedPositionDataList()) {
						// 写入归档明细表
						statArchiveItemTable.insert(closedPositionData.getClosedPositionItem());

						// 写入归档小单元表
						for (StatClosedUnit closedUnit : closedPositionData.getClosedUnitList()) {
							statArchiveUnitTable.insert(closedUnit);
						}

//						// 更新持仓小单元记录
//						StatPositionItem statPositionItem = statPositionItemTable.queryStatPositionItem(closedPositionData.getClosedPositionItem().getPositionItemId());
//						if (statPositionItem == null) {
//							// 清理小单元
//							statPositionUnitTable.deleteStatPositionUnit(closedPositionData.getClosedPositionItem().getPositionItemId());
//						}
					}
					// 删除 平仓表, 平仓明细表 以及 平仓小单元表 中对应的记录
					statClosedPositionHandler.removeClosedPosition(getConnection(), archiveItemData);
				}
			}

			@Override
			public Void getResult() {
				return null;
			}
		}.execute();
		/* 事务结束 */

		clearInvalidPositionSummary();
	}

	/**
	 * 删除持仓量为0的持仓汇总
	 */
	private void clearInvalidPositionSummary() throws ErrorInfo {

		new DBTransactionHelperWithMsg<Void>(PositionStatisDB.Global()) {

			StatPositionSummaryTable statPositionSummaryTable = null;
			StatPositionSummary statPositionSummary = null;
			boolean clear = false;

			@Override
			public void onPrepareData() throws ErrorInfo, Exception {
				statPositionSummaryTable = new StatPositionSummaryTable(getConnection());
				statPositionSummary = statPositionSummaryTable.queryStatPositionSummary(statClosedPositionSummary.getTargetKey(), statClosedPositionSummary.getTargetType(), statClosedPositionSummary.getSubAccountId());
			}

			@Override
			public void onUpdate() throws ErrorInfo, Exception {
				if (statPositionSummary != null) {
					if (statPositionSummary.getLongPosition() == 0 && statPositionSummary.getShortPosition() == 0) {
						statPositionSummaryTable.delete(statPositionSummary.getTargetKey(), statPositionSummary.getTargetType(), statPositionSummary.getSubAccountId());
						clear = true;
					}
				}
			}

			@Override
			public Void getResult() {
				return null;
			}

			@Override
			protected AbstractMap.SimpleEntry<TBase, IGuardPolicy> prepareGuardMessage() {
				if (clear) {
					StatPositionSummaryChangedGuardEvent event = new StatPositionSummaryChangedGuardEvent();
					event.setSubAccountId(statPositionSummary.getSubAccountId());
					event.setTargetKey(statPositionSummary.getTargetKey());
					event.setTargetType(statPositionSummary.getTargetType());
					event.setEventType(StatPositionEventType.STAT_ITEM_REMOVED);
					return new AbstractMap.SimpleEntry<TBase, IGuardPolicy>(event, new TimeoutGuardPolicy().setTimeoutSeconds(3));
				}
				return null;
			}

			@Override
			protected MessageAgent getMessageAgent() {
				return HostingMessageContext.Global().getSenderAgent();
			}

			@Override
			protected TBase prepareNotifyMessage() {
				if (clear) {
					StatPositionSummaryChangedEvent event = new StatPositionSummaryChangedEvent();
					event.setSubAccountId(statPositionSummary.getSubAccountId());
					if (statPositionSummary.getNetPosition() == 0) {
						statPositionSummary.unsetPositionAvgPrice();
					}
					event.setStatPositionSummary(statPositionSummary);
					event.setEventCreateTimestampMs(System.currentTimeMillis());
					event.setEventType(StatPositionEventType.STAT_ITEM_REMOVED);
					return event;
				}
				return null;
			}
		}.execute();
	}
}
