package xueqiao.trade.hosting.push.server;

import java.util.concurrent.atomic.AtomicLong;

import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import xueqiao.trade.hosting.push.server.core.TaskPool;
import xueqiao.trade.hosting.push.server.core.URLConstants;
import xueqiao.trade.hosting.push.server.data.ConnectionInfo;
import xueqiao.trade.hosting.push.server.tasks.ConnectTask;
import xueqiao.trade.hosting.push.server.tasks.HttpTask;
import xueqiao.trade.hosting.push.server.tasks.InvokeFrameTask;
import xueqiao.trade.hosting.push.server.tasks.ResourceLoaderTask;

public class PushHandler extends SimpleChannelInboundHandler<Object> {
	
	private static TaskPool TASK_EXECUTOR = new TaskPool(10, 100, 600);
	
	private static AtomicLong CONNECT_COUNT = new AtomicLong(0);
	private static int ATTR_KEY_CONNECT_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.connection.count", null);
	
    public PushHandler() {
    }
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof FullHttpRequest) {
			handleHttpRequest(ctx, (FullHttpRequest)msg);
		} else if (msg instanceof WebSocketFrame) {
			handleWebSocketFrame(ctx, (WebSocketFrame) msg);
		}
	}
	
	private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
		if (frame instanceof BinaryWebSocketFrame) {
			InvokeFrameTask invokeTask = new InvokeFrameTask(InvokeFrameTask.MODE_BINARY);
			invokeTask.setTaskChannel(ctx.channel());
			invokeTask.setFrame((BinaryWebSocketFrame)frame);
			TASK_EXECUTOR.addTask(invokeTask);
			return ;
		}
		if (frame instanceof TextWebSocketFrame) {
			InvokeFrameTask invokeTask = new InvokeFrameTask(InvokeFrameTask.MODE_TEXT);
			invokeTask.setTaskChannel(ctx.channel());
			invokeTask.setFrame((TextWebSocketFrame)frame);
			TASK_EXECUTOR.addTask(invokeTask);
			return ;
		}
		
        if (frame instanceof CloseWebSocketFrame) {
        	ConnectionInfo connectionInfo = ctx.channel().attr(ConnectionInfo.KEY).get();
        	if (connectionInfo != null){
        		connectionInfo.getHandleShaker().close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        	} else {
        		ctx.channel().close();
        	}
            return;
        }
        
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        
        throw new UnsupportedOperationException(
        		String.format("%s frame types not supported", frame.getClass().getName()));
    }
	
	private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.decoderResult().isSuccess()) {
        	sendHttpError(ctx, req, HttpResponseStatus.BAD_REQUEST);
            return;
        }

        HttpTask task = null;
        if (req.uri().startsWith(URLConstants.RESOURCE_START)) {
			task = new ResourceLoaderTask();
        } else if (req.uri().startsWith(URLConstants.CONNECT_START)) {
        	task = new ConnectTask();
      	} 
        
        if (task == null) {
        	sendHttpError(ctx, req, HttpResponseStatus.NOT_FOUND);
        	return ;
        }
        
        task.setHttpReq(req);
        task.setTaskChannel(ctx.channel());
        if (!TASK_EXECUTOR.addTask(task)) {
        	sendHttpError(ctx, req, HttpResponseStatus.SERVICE_UNAVAILABLE);
        }
    }
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		try {
			if (AppLog.warnEnabled()) {
				AppLog.w("exceptionCaught connection=" + ctx.channel().remoteAddress()
						+ ", cause=" + cause);
			}
			ctx.close();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
    	if (AppLog.infoEnabled()) {
    		AppLog.i("Connection added " + ctx.channel().remoteAddress());
    	}
    	AttrReporterFactory.getDefault().keep(ATTR_KEY_CONNECT_COUNT, CONNECT_COUNT.incrementAndGet());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
    	if (AppLog.infoEnabled()) {
    		AppLog.i("Connection removed " + ctx.channel().remoteAddress());
    	}
    	AttrReporterFactory.getDefault().keep(ATTR_KEY_CONNECT_COUNT, CONNECT_COUNT.decrementAndGet());
    }
    
    private void sendHttpError(ChannelHandlerContext ctx, FullHttpRequest req, HttpResponseStatus errStatus) {
    	ctx.writeAndFlush(new DefaultFullHttpResponse(req.protocolVersion(), errStatus))
			.addListener(ChannelFutureListener.CLOSE);
    }
	
}
