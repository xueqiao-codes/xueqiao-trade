package xueqiao.trade.hosting.push.server.tasks;

import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import xueqiao.trade.hosting.push.server.core.FrameProtocolHandler;
import xueqiao.trade.hosting.push.server.core.InvokeContext;
import xueqiao.trade.hosting.push.server.core.FrameProtocolHandler.IResponseCallback;
import xueqiao.trade.hosting.push.server.data.ConnectionInfo;
import xueqiao.trade.hosting.push.utils.TByteBufInputTransport;
import xueqiao.trade.hosting.push.utils.TByteBufOutputTransport;

@SuppressWarnings("rawtypes")
public class InvokeFrameTask extends WebSocketFrameTask<WebSocketFrame> 
		implements IResponseCallback {
	public static final int MODE_BINARY = 1;
	public static final int MODE_TEXT = 2;
	
	private int mMode;
	
	public InvokeFrameTask(int mode) {
		this.mMode = mode;
	}
	
	@Override
	public void onComplete(TProtocolFactory protocolFactory, TBase responseData) {
		ByteBuf outByteBuf = PooledByteBufAllocator.DEFAULT.ioBuffer(256);
		ProtocolUtil.serialize(protocolFactory, new TByteBufOutputTransport(outByteBuf), responseData);
		if (mMode == MODE_BINARY) {
			super.sendFrame(new BinaryWebSocketFrame(outByteBuf));
		} else {
			super.sendFrame(new TextWebSocketFrame(outByteBuf));
		}
	}

	@Override
	protected void onProcessFrame() {
		ConnectionInfo connectionInfo = this.getTaskChannel().attr(ConnectionInfo.KEY).get();
		if (connectionInfo == null) {
			AppLog.e("Can not getConnectionInfo for channel", new Exception());
			return ;
		}
		
		FrameProtocolHandler handler = new FrameProtocolHandler(this, connectionInfo.getProtocolFactory());
		InvokeContext context = new InvokeContext();
		context.setSubUserId(connectionInfo.getSubUserId());
		
		handler.onHandleFrameData(context, new TByteBufInputTransport(frame().content()));
	}

}
