package xueqiao.trade.hosting.push.server.tasks;

import org.soldier.base.logger.AppLog;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;


public abstract class WebSocketFrameTask<WebSocketFrameType extends WebSocketFrame> extends ChannelTask {
	
	private WebSocketFrameType mFrame;
	
	public WebSocketFrameType frame() {
		return mFrame;
	}
	
	@SuppressWarnings("unchecked")
	public void setFrame(WebSocketFrameType frame) {
		this.mFrame = (WebSocketFrameType)frame.retain();
	}
	
	public ChannelFuture sendFrame(WebSocketFrame frame) {
		return getTaskChannel().writeAndFlush(frame);
	}
	
	protected abstract void onProcessFrame();
	
	@Override
	public void onChannelTask() {
		if (mFrame == null) {
			throw new IllegalStateException("Frame should not be null");
		}
		
		try {
			onProcessFrame();
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		} finally {
			mFrame.release();
			if (mFrame.refCnt() != 0) {
				AppLog.e("Memory Leak may occurs, WebSocketFrame refCnt=" + mFrame.refCnt());
			}
		}
	}
}
