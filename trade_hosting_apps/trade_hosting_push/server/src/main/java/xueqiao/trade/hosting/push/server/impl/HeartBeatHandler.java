package xueqiao.trade.hosting.push.server.impl;

import org.apache.thrift.TBase;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.push.protocol.HeartBeatReq;
import xueqiao.trade.hosting.push.protocol.ProtocolCallErrors;
import xueqiao.trade.hosting.push.server.core.CallHandler;
import xueqiao.trade.hosting.push.server.core.Dispatcher;
import xueqiao.trade.hosting.push.server.core.FrameProtocolHandler.FrameResponse;
import xueqiao.trade.hosting.push.server.core.InvokeContext;
import xueqiao.trade.hosting.push.server.core.PushChannel;
import xueqiao.trade.hosting.push.user.WsUserChecker;

@SuppressWarnings("rawtypes")
public class HeartBeatHandler extends CallHandler<HeartBeatReq, TBase> {
	public HeartBeatHandler() {
		super(HeartBeatReq.class, null);
	}

	@Override
	public void invoke(InvokeContext context, HeartBeatReq req, FrameResponse frameResp) {
		if (req.getToken() == null || req.getToken().isEmpty()) {
			frameResp.getRespContent().setErrCode(ProtocolCallErrors.ParamError.getValue());
			frameResp.getRespContent().setErrMsg("token should not be empty");
			frameResp.onFinished(null);
			return ;
		}
		
		if (context.getSubUserId() != req.getSubUserId()) {
			frameResp.getRespContent().setErrCode(ProtocolCallErrors.ParamError.getValue());
			frameResp.getRespContent().setErrMsg("connection user is not equals to parameter user");
			frameResp.onFinished(null);
			return ;
		}
		
		if (AppLog.infoEnabled()) {
			AppLog.i("HeartBeatReq machineId=" + req.getMachineId() 
					+ ", subUserId=" + context.getSubUserId() 
					+ ", token=" + req.getToken());
		}
		
		WsUserChecker checker = new WsUserChecker(req.getMachineId(), context.getSubUserId(), req.getToken());
		try {
			if (checker.isValid()) {
				PushChannel pushChannel = Dispatcher.INSTANCE.getPushChannel(context.getSubUserId());
				if (pushChannel != null) {
					pushChannel.updateLastHeartBeatTimestamp();
				}
				frameResp.onFinished(null);
				return ;
			}
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			frameResp.getRespContent().setErrCode(ProtocolCallErrors.ServiceUnavailable.getValue());
			frameResp.getRespContent().setErrMsg(e.getMessage());
			return ;
		}
		
		// 心跳鉴权错误，用户重新登录
		PushChannel pushChannel = Dispatcher.INSTANCE.getPushChannel(context.getSubUserId());
		if (pushChannel != null) {
			pushChannel.close();
		}
	}
	

}
