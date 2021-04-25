package xueqiao.trade.hosting.push.server.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.push.protocol.QuotationTopic;
import xueqiao.trade.hosting.push.protocol.QuotationUnSubscribeReq;
import xueqiao.trade.hosting.push.server.core.CallHandler;
import xueqiao.trade.hosting.push.server.core.Dispatcher;
import xueqiao.trade.hosting.push.server.core.FrameProtocolHandler.FrameResponse;
import xueqiao.trade.hosting.push.server.core.InvokeContext;

@SuppressWarnings("rawtypes")
public class UnSubscribeHandler extends CallHandler<QuotationUnSubscribeReq, TBase> {

	public UnSubscribeHandler() {
		super(QuotationUnSubscribeReq.class, null);
	}

	@Override
	public void invoke(InvokeContext context
			, QuotationUnSubscribeReq req
			, FrameResponse frameResp) throws Exception {
		if (AppLog.debugEnabled()) {
			AppLog.d("onQuotationUnSubscribeReq subUserId=" + context.getSubUserId() + ", req=" + req);
		}
		
		if (req.getTopics().isEmpty()) {
			frameResp.onFinished(null);
			return ;
		}
		
		Set<QuotationTopic> unSubscribeTopics = new HashSet<QuotationTopic>(req.getTopicsSize());
		for (QuotationTopic quotationTopic : req.getTopics()) {
			if (StringUtils.isBlank(quotationTopic.getPlatform())
					|| StringUtils.isBlank(quotationTopic.getContractSymbol())) {
				frameResp.getRespContent().setErrMsg("no error, but some quotation topic empty");
				continue;
			}
			
			QuotationTopic unSubscribeTopic = new QuotationTopic();
			unSubscribeTopic.setPlatform(StringUtils.trim(quotationTopic.getPlatform()));
			unSubscribeTopic.setContractSymbol(StringUtils.trim(quotationTopic.getContractSymbol()));
			
			unSubscribeTopics.add(unSubscribeTopic);
		}
		if (!unSubscribeTopics.isEmpty()) {
			Dispatcher.INSTANCE.onRemoveQuotationTopics(context.getSubUserId(), unSubscribeTopics);
		}
		frameResp.onFinished(null);
	}


}
