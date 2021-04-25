package xueqiao.trade.hosting.push.server.data;

import org.apache.thrift.protocol.TProtocolFactory;

import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.AttributeKey;

public class ConnectionInfo {
	public static AttributeKey<ConnectionInfo> KEY = AttributeKey.valueOf("connInfo");
	
	private int mSubUserId;
	private TProtocolFactory mProtocolFactory;
	private WebSocketServerHandshaker mHandleShaker;
	
	public ConnectionInfo(int subUserId, TProtocolFactory protocolFactory, WebSocketServerHandshaker handleShaker) {
		if (protocolFactory == null) {
			throw new IllegalArgumentException("protocolFactory should not be null");
		}
		
		this.mSubUserId = subUserId;
		this.mProtocolFactory = protocolFactory;
		this.mHandleShaker = handleShaker;
	}
	
	public final int getSubUserId() {
		return mSubUserId;
	}
	
	public TProtocolFactory getProtocolFactory() {
		return mProtocolFactory;
	}
	
	public WebSocketServerHandshaker getHandleShaker() {
		return mHandleShaker;
	}
}
