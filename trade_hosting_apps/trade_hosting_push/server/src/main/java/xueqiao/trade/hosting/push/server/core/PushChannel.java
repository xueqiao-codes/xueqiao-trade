package xueqiao.trade.hosting.push.server.core;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.logger.AppLog;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import net.qihoo.qconf.Qconf;
import xueqiao.trade.hosting.push.server.data.ConnectionInfo;

public class PushChannel implements ChannelFutureListener {
	private int mSubUserId;
	private Channel mChannel;
	private TProtocolFactory mClientProtocolFactory;
	private AtomicInteger mWritingCount;
	private int mMaxPendingCount = 50;
	private volatile long mLastHeartBeatTimestamp = 0;
	
	public PushChannel(
			int subUserId
			, Channel channel
			, TProtocolFactory clientProtocolFactory) {
		if (channel == null) {
			throw new IllegalArgumentException("channel is null");
		}
		if (clientProtocolFactory == null) {
			throw new IllegalArgumentException("clientProtocolFactory is null");
		}
		
		try {
			mMaxPendingCount = Integer.parseInt(
					Qconf.getConf("xueqiao/trade/hosting/push_server/max_pending_packets").trim());
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
		
		this.mSubUserId = subUserId;
		this.mChannel = channel;
		this.mClientProtocolFactory = clientProtocolFactory;
		this.mWritingCount = new AtomicInteger(0);
		updateLastHeartBeatTimestamp();
		
		if (AppLog.infoEnabled()) {
			AppLog.i("PushChannel Created for subUserId=" + mSubUserId + ", mMaxPendingCount=" + mMaxPendingCount
					+ ", channelId=" + mChannel.id().asLongText());
		}
	}
	
	public static PushChannel newForContainerFound(int subUserId) {
		return new PushChannel(subUserId);
	}
	
	// only for remove use
	private PushChannel(int subUserId) {
		this.mSubUserId = subUserId;
	}
	
	public long getLastHeartBeatTimestamp() {
		return mLastHeartBeatTimestamp;
	}
	
	public void updateLastHeartBeatTimestamp() {
		this.mLastHeartBeatTimestamp = System.currentTimeMillis() / 1000;
	}
	
	public boolean writeMaxPendingDroped(Object msg) {
		boolean writeSuccess = false;
		try {
			if (mWritingCount.get() >= mMaxPendingCount) {
				if (AppLog.debugEnabled()) {
					AppLog.d("Drop msg for subUserId=" + mSubUserId + ", msgType=" + msg.getClass().getName());
				}
				return false;
			}
			try {
				mChannel.writeAndFlush(msg).addListener(this);
				writeSuccess = true;
				mWritingCount.incrementAndGet();
				return true;
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
			return false;
		} finally {
			if (!writeSuccess) {
				ReferenceCountUtil.release(msg);
			}
		}
	}
	
	
	public TProtocolFactory getClientProtocolFactory() {
		return mClientProtocolFactory;
	}
	
	public Channel getChannel() {
		return mChannel;
	}
	
	public int getMaxPendingCount() {
		return mMaxPendingCount;
	}
	
	public void setMaxPendingCount(int maxPendingCount) {
		this.mMaxPendingCount = maxPendingCount;
	}
	
	public int getSubUserId() {
		return mSubUserId;
	}
	
	public void close() {
		try {
			ConnectionInfo connectionInfo = mChannel.attr(ConnectionInfo.KEY).get();
			if (connectionInfo == null) {
				mChannel.close();
				return ;
			}
			connectionInfo.getHandleShaker().close(mChannel, new CloseWebSocketFrame());
		} catch (Throwable e) {
			AppLog.e(e.getMessage(), e);
		}
	}
	
	@Override
	public int hashCode() {
		return mSubUserId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PushChannel)) {
			return false;
		}
		
		if (this.mSubUserId == (((PushChannel)obj).getSubUserId())) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		mWritingCount.decrementAndGet();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(32);
		builder.append("PushChannel[subUserId=");
		builder.append(mSubUserId);
		builder.append("][channelId=");
		if (mChannel != null) {
			builder.append(mChannel.id().asLongText());
		} else {
			builder.append("null");
		}
		builder.append("]");
		return builder.toString();
	}

}
