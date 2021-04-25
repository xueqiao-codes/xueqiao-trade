package xueqiao.trade.hosting.push.server.core;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TTransport;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import xueqiao.trade.hosting.push.protocol.ProtocolCallErrors;
import xueqiao.trade.hosting.push.protocol.ProtocolFrame;
import xueqiao.trade.hosting.push.protocol.ProtocolType;
import xueqiao.trade.hosting.push.protocol.ReqContent;
import xueqiao.trade.hosting.push.protocol.RespContent;

public class FrameProtocolHandler {
	private static AtomicLong RESPONSE_ID = new AtomicLong(1);
	
	@SuppressWarnings("rawtypes")
	public static interface IResponseCallback {
		public void onComplete(TProtocolFactory protocolFactory, TBase responseData);
	}
	
	private IResponseCallback mCallback;
	private TProtocolFactory mProtocolFactory;
	
	public FrameProtocolHandler(IResponseCallback callback, TProtocolFactory protocolFactory) {
		this.mCallback = callback;
		this.mProtocolFactory = protocolFactory;
	}
	
	public class FrameResponse {
		private RespContent respContent = new RespContent();
		
		public FrameResponse() {
		}
		
		public RespContent getRespContent() {
			return respContent;
		}
		
		@SuppressWarnings("rawtypes") 
		public void onFinished(TBase respStruct) {
			if (respStruct != null) {
				respContent.setResponseStructType(respStruct.getClass().getSimpleName());
				respContent.setResponseStructBytes(ProtocolUtil.serialize(mProtocolFactory, respStruct));
			} else {
				respContent.setResponseStructType("");
			}
			ProtocolFrame respFrame = new ProtocolFrame();
			respFrame.setProtocol(ProtocolType.RESP);
			respFrame.setRespContent(respContent);
			
			mCallback.onComplete(mProtocolFactory, respFrame);
		}
	}
	
	public void onHandleFrameData(InvokeContext ctx, TTransport inputTransport) {
		ProtocolFrame topFrame = ProtocolUtil.unSerialize(mProtocolFactory
				, inputTransport, ProtocolFrame.class);
		if (topFrame == null) {
			return ;
		}
		
		if (topFrame.getProtocol() == ProtocolType.REQ) {
			if (topFrame.getReqContent() == null) {
				return ;
			}
			
			FrameResponse frameResp = new FrameResponse();
			frameResp.getRespContent().setRequestId(topFrame.getReqContent().getRequestId());
			frameResp.getRespContent().setResponseId(RESPONSE_ID.getAndIncrement());
			onProcessCall(ctx, topFrame.getReqContent(), frameResp);
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void onProcessCall(InvokeContext ctx, ReqContent reqContent, FrameResponse frameResp)  {
		AppLog.i("onProcessCall requestId=" +  reqContent.requestId 
				+ ", requestStructType=" + reqContent.getRequestStructType());
		CallHandler callHandler
			= GlobalCallRegister.Global().getCallHandler(reqContent.getRequestStructType());
		if (callHandler == null) {
			frameResp.getRespContent().setErrCode(ProtocolCallErrors.ParamError.getValue());
			frameResp.getRespContent().setErrMsg(reqContent.getRequestStructType() + " is not found for call");
			frameResp.onFinished(null);
			return ;
		}
		TBase reqStruct = ProtocolUtil.unSerialize(
				mProtocolFactory, reqContent.getRequestStructBytes(), callHandler.getRequestStructClazz());
		if (reqStruct == null) {
			frameResp.getRespContent().setErrCode(ProtocolCallErrors.ParamError.getValue());
			frameResp.getRespContent().setErrMsg("request struct bytes can not unserialize to "
					+ reqContent.getRequestStructType());
			frameResp.onFinished(null);
			return ;
		}
	
		try {
			callHandler.invoke(ctx, reqStruct, frameResp);
		} catch (Throwable err) {
			AppLog.e(err.getMessage(), err);
			frameResp.getRespContent().setErrCode(ProtocolCallErrors.ServiceUnavailable.getValue());
			frameResp.getRespContent().setErrMsg("Service Unavailable");
			frameResp.onFinished(null);
		}
	}
}
