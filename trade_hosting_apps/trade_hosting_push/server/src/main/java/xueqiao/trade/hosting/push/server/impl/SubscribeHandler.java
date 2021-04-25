package xueqiao.trade.hosting.push.server.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.push.protocol.QuotationSubscribeReq;
import xueqiao.trade.hosting.push.protocol.QuotationTopic;
import xueqiao.trade.hosting.push.server.core.CallHandler;
import xueqiao.trade.hosting.push.server.core.Dispatcher;
import xueqiao.trade.hosting.push.server.core.FrameProtocolHandler.FrameResponse;
import xueqiao.trade.hosting.push.server.core.InvokeContext;

@SuppressWarnings("rawtypes")
public class SubscribeHandler extends CallHandler<QuotationSubscribeReq, TBase>{
	public SubscribeHandler() {
		super(QuotationSubscribeReq.class, null);
	}

	@Override
	public void invoke(InvokeContext context
			, QuotationSubscribeReq req
			, FrameResponse frameResp) throws Exception {
		if (AppLog.debugEnabled()) {
			AppLog.d("onReceivedSubscribeReq userId=" + context.getSubUserId() + ", req=" + req);
		}
		
		if (req.getTopics().isEmpty()) {
			frameResp.onFinished(null);
			return ;
		}
		
		Set<QuotationTopic> subscribeTopics = new HashSet<QuotationTopic>(req.getTopicsSize());
		for (QuotationTopic quotationTopic : req.getTopics()) {
			if (StringUtils.isBlank(quotationTopic.getPlatform())
				|| StringUtils.isBlank(quotationTopic.getContractSymbol())) {
				frameResp.getRespContent().setErrMsg("no error, but some quotation topic empty");
				continue;
			}
			
			QuotationTopic subscribeTopic = new QuotationTopic();
			subscribeTopic.setPlatform(StringUtils.trim(quotationTopic.getPlatform()));
			subscribeTopic.setContractSymbol(StringUtils.trim(quotationTopic.getContractSymbol()));
			
			subscribeTopics.add(subscribeTopic);
		}
		if (!subscribeTopics.isEmpty()) {
			Dispatcher.INSTANCE.onAddQuotationTopics(context.getSubUserId(), subscribeTopics);
		}
		frameResp.onFinished(null);
	}

}
