package xueqiao.trade.hosting.position.statis.msgbus;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.consume;
import org.soldier.platform.page.IndexedPageOption;
import xueqiao.trade.hosting.arbitrage.thriftapi.*;
import xueqiao.trade.hosting.arbitrage.thriftapi.helper.ArbitrageStubFactory;
import xueqiao.trade.hosting.events.XQOrderGuardEvent;
import xueqiao.trade.hosting.events.XQOrderGuardEventType;
import xueqiao.trade.hosting.events.XQTradeListChangedEvent;
import xueqiao.trade.hosting.position.statis.service.ImportDataService;
import xueqiao.trade.hosting.position.statis.service.report.AppReport;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 订阅两个事件：
 * (1) XQTradeListChangedEvent
 * (2) XQOrderGuardEvent
 */
public class PositionStatisMessageConsumer implements IMessageConsumer {

	ImportDataService importDataService = new ImportDataService();

	@Override
	public StartUpMode onStartUp() {
		return StartUpMode.RESERVE_QUEUE;
	}

	@Override
	public void onInit() throws Exception {
		int pageSize = 500;
		int pageIndex = 0;

		while (true) {
			QueryEffectXQOrderIndexPage indexPage = ArbitrageStubFactory.getStub().getEffectXQOrderIndexPage(new QueryEffectXQOrderIndexOption()
				, new IndexedPageOption().setPageIndex(pageIndex).setPageSize(pageSize));
			if (indexPage.getResultIndexItemsSize() <= 0) {
				break;
			}

			Set<String> batchOrderIds = new HashSet<String>(pageSize);
			for (HostingEffectXQOrderIndexItem indexItem : indexPage.getResultIndexItems()) {
				batchOrderIds.add(indexItem.getOrderId());
			}

			Map<String, HostingXQOrderWithTradeList> orderTradeListMap = ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(batchOrderIds);
			for (HostingXQOrderWithTradeList orderTradeList : orderTradeListMap.values()) {
				handleTradeList(orderTradeList.getTradeList());
			}

			pageIndex += 1;
		}
	}

	private void handleTradeList(List<HostingXQTrade> tradeList) throws TException, UnsupportedEncodingException {

		if (AppReport.TRACE) {
			AppReport.trace(this.getClass(), "handleTradeList -- tradeList : " + new Gson().toJson(tradeList));
		}

		if (tradeList == null || tradeList.isEmpty()) {
			return;
		}

		for (HostingXQTrade trade : tradeList) {
			if (StringUtils.isEmpty(trade.getOrderId())) {
				AppReport.warning("handleTradeList called, but have trade's orderId is empty! trade=" + trade);
			}

			if (!trade.isSetTradeId() || trade.getTradeId() <= 0) {
				AppReport.warning("handleTradeList called, but have trade not set tradeId or tradeId <= 0! trade=" + trade);
				continue;
			}
			if (!trade.isSetCreateTimestampMs() || trade.getCreateTimestampMs() <= 0) {
				AppReport.warning("handleTradeList called, but have trade not set createTimestampMs or createTimestampMs <= 0! trade=" + trade);
				continue;
			}

			importDataService.importHostingXQTradeData(trade);
		}
	}

	@consume(XQTradeListChangedEvent.class)
	ConsumeResult onHandleXQTradeListChangedEvent(XQTradeListChangedEvent event) throws TException, UnsupportedEncodingException {
		if (!event.isSetOrder()) {
			AppReport.warning("onHandleXQTradeListChangedEvent called, but event's order not set");
			return ConsumeResult.CONSUME_FAILED_DROP;
		}
		handleTradeList(event.getTradeList());
		return ConsumeResult.CONSUME_OK;
	}

	@consume(XQOrderGuardEvent.class)
	ConsumeResult onHandleXQOrderGuardEvent(XQOrderGuardEvent event) throws TException, UnsupportedEncodingException {
		if (!event.isSetType()) {
			AppReport.warning("onHandleXQOrderGuardEvent called, but event's type not set");
			return ConsumeResult.CONSUME_FAILED_DROP;
		}
		if (!event.isSetOrderId() || StringUtils.isEmpty(event.getOrderId())) {
			AppReport.warning("onHandleXQOrderGuardEvent called, but event's orderId not set or empty");
			return ConsumeResult.CONSUME_FAILED_DROP;
		}

		if (event.getType() == XQOrderGuardEventType.XQ_ORDER_TRADELIST_CHANGED_GUARD) {
			Map<String, HostingXQOrderWithTradeList> guardOrderTradeListMap = ArbitrageStubFactory.getStub().getXQOrderWithTradeLists(new HashSet<String>(Arrays.asList(event.getOrderId())));
			if (guardOrderTradeListMap.containsKey(event.getOrderId())) {
				HostingXQOrderWithTradeList orderTradeList = guardOrderTradeListMap.get(event.getOrderId());
				handleTradeList(orderTradeList.getTradeList());
			}
		}
		return ConsumeResult.CONSUME_OK;
	}
}