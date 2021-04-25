package xueqiao.trade.hosting.push.server.tasks;

import java.io.UnsupportedEncodingException;

import org.soldier.base.logger.AppLog;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

public abstract class HttpTask extends ChannelTask {
	
	private FullHttpRequest mHttpReq;
	
	private boolean isKeepAlive = false;
	private HttpVersion httpVersion;
	
	public FullHttpRequest httpReq() {
		return mHttpReq;
	}
	
	public void setHttpReq(FullHttpRequest httpReq) {
		this.mHttpReq = httpReq.retain();
	}
	
	public void sendHttpResponse(HttpResponseStatus errStatus) {
		setHttpResponse(errStatus, errStatus.toString());
	}
	
	public void setHttpResponse(HttpResponseStatus status, String content) {
		try {
			sendHttpResponse(status, "text/palin;charset=utf-8", content.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			AppLog.f("Unexepcted not support UTF-8");
		}
	}
	
	public void sendHttpResponse(HttpResponseStatus status, String contentType, byte[] body) {
		sendHttpResponse(status, contentType, Unpooled.wrappedBuffer(body));
	}
	
	protected class HttpTaskResponse {
		HttpResponseStatus status = HttpResponseStatus.OK;
		String contentType;
		byte[] body;
		
		private boolean isAsync = false;
		
		public void setAsync(boolean isAsync) {
			this.isAsync = isAsync;
		}
		
		public boolean isAsync() {
			return isAsync;
		}
		
		public void finished() {
			if (status.code() != 200) {
				sendHttpResponse(status);
			} else {
				sendHttpResponse(status, contentType, body);
			}
		}
	}
	
	protected abstract void onHttpExecute(HttpTaskResponse response) throws Exception;
	
	@Override
	public void onChannelTask() {
		if (mHttpReq == null ) {
			throw new IllegalStateException("task http request is null");
		}
		
		isKeepAlive = HttpUtil.isKeepAlive(mHttpReq);
		httpVersion = mHttpReq.protocolVersion();
		try {
			HttpTaskResponse response = new HttpTaskResponse();
			onHttpExecute(response);
			if (!response.isAsync()) {
				response.finished();
			}
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
			sendHttpResponse(HttpResponseStatus.SERVICE_UNAVAILABLE);
		} finally {
			mHttpReq.release();
			if (mHttpReq.refCnt() != 0) {
				AppLog.e("Memory Leak may occurs, httpReq refCnt=" + mHttpReq.refCnt());
			}
		}
	}
	
	public void sendHttpResponse(HttpResponseStatus status, String contentType, ByteBuf body) {
		FullHttpResponse response = null;
		if (body != null) {
			response = new DefaultFullHttpResponse(httpVersion, status, body);
		} else {
			response = new DefaultFullHttpResponse(httpVersion, status);
		}
		response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
		if (contentType != null && !contentType.isEmpty()) {
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
		} else {
			response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/octet-stream");
		}
		if (isKeepAlive) {
        	response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        	response.headers().set(HttpHeaderNames.TRANSFER_ENCODING, HttpHeaderValues.CHUNKED);
        }
		
		ChannelFuture f = getTaskChannel().writeAndFlush(response);
		if (!isKeepAlive || response.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
	}
}
