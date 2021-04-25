package xueqiao.trade.hosting.push.server.tasks;

import static io.netty.handler.codec.http.HttpHeaderNames.HOST;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import xueqiao.trade.hosting.push.server.core.Dispatcher;
import xueqiao.trade.hosting.push.server.core.URLConstants;
import xueqiao.trade.hosting.push.server.data.ConnectionInfo;
import xueqiao.trade.hosting.push.user.WsUserChecker;

public class ConnectTask extends HttpTask {
	private static final String MACHINEID_PARAMETER = "machineId";
	private static final String SUBUSERID_PARAMETER = "subUserId";
	private static final String TOKEN_PARAMETER = "token";
	private static final String PROTOCOL_PARAMETER = "protocol";
	
	private static List<String> SUPPORT_PROTOCOLS = new ArrayList<String>(){
		private static final long serialVersionUID = 1L;
		{
			this.add("compact");
			this.add("json");
		}
	};
	
	public ConnectTask() {
	}
	
	private class HandShakerListener implements ChannelFutureListener {
		private int mSubUserId;
		private WebSocketServerHandshaker mHandshaker;
		private TProtocolFactory mClientProtocolFactory;
		
		public HandShakerListener(int subUserId
				, WebSocketServerHandshaker handshaker
				, TProtocolFactory clientProtocolFactory) {
			this.mSubUserId = subUserId;
			this.mHandshaker = handshaker;
			this.mClientProtocolFactory = clientProtocolFactory;
		}
		
		@Override
		public void operationComplete(ChannelFuture future) throws Exception {
			if (future.isSuccess()) {
				future.channel().attr(ConnectionInfo.KEY)
					.set(new ConnectionInfo(mSubUserId, mClientProtocolFactory, mHandshaker));
				Dispatcher.INSTANCE.onUserLogin(mSubUserId, getTaskChannel(), mClientProtocolFactory);
			} 
		}
	}
	
	@Override
	protected void onHttpExecute(HttpTaskResponse response) throws Exception {
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpReq().uri());
    	Map<String, List<String>> parameters = queryStringDecoder.parameters();
    	if (parameters.size() == 0  
    			|| !parameters.containsKey(MACHINEID_PARAMETER)
    			|| !parameters.containsKey(SUBUSERID_PARAMETER)
    			|| !parameters.containsKey(TOKEN_PARAMETER)) {
    		response.status = HttpResponseStatus.FORBIDDEN;
    		return ;
    	}
    	final String machineIdStr = parameters.get(MACHINEID_PARAMETER).get(0).trim();
    	final String subUserIdStr = parameters.get(SUBUSERID_PARAMETER).get(0).trim();
    	final String token = parameters.get(TOKEN_PARAMETER).get(0).trim();
    	if (machineIdStr.isEmpty() || subUserIdStr.isEmpty() || token.isEmpty()) {
    		response.status = HttpResponseStatus.UNAUTHORIZED;
    		return ;
    	}
    	String protocol = "compact";
    	if (parameters.containsKey(PROTOCOL_PARAMETER)) {
    		protocol = parameters.get(PROTOCOL_PARAMETER).get(0).trim();
    	}
    	if (!SUPPORT_PROTOCOLS.contains(protocol)) {
    		response.status = HttpResponseStatus.NOT_ACCEPTABLE;
    		return ;
    	}
    	TProtocolFactory clientProtocolFactory = null;
    	if ("json".equalsIgnoreCase(protocol)) {
    		clientProtocolFactory = new TJSONProtocol.Factory();
    	} else {
    		clientProtocolFactory = new TCompactProtocol.Factory();
    	}
    	
    	long machineId = 0;
    	int subUserId = 0;
    	
    	try {
    		machineId = Long.valueOf(machineIdStr);
    		subUserId = Integer.valueOf(subUserIdStr);
    	} catch (NumberFormatException e) {
    		response.status = HttpResponseStatus.UNAUTHORIZED;
    		return ;
    	}
    	
    	WsUserChecker checker = new WsUserChecker(machineId, subUserId, token);
    	if (!checker.isValid()) {
    		response.status = HttpResponseStatus.FORBIDDEN;
    		return ;
    	}
    	
    	WebSocketServerHandshakerFactory wsFactory
    		= new WebSocketServerHandshakerFactory(getWebSocketLocation(httpReq()), null, true);
    	WebSocketServerHandshaker handShaker = wsFactory.newHandshaker(httpReq());
    	response.setAsync(true); 
    	if (handShaker == null) {
    		WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(getTaskChannel());
    	} else {
            handShaker.handshake(getTaskChannel(), httpReq())
            		  .addListener(new HandShakerListener(subUserId, handShaker, clientProtocolFactory));
        }
	}
	
	private static String getWebSocketLocation(FullHttpRequest req) {
        String location = req.headers().get(HOST) + URLConstants.CONNECT_START;
        return "ws://" + location;
    }

}
