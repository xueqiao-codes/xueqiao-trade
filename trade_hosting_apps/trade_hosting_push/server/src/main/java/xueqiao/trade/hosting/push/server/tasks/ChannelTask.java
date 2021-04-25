package xueqiao.trade.hosting.push.server.tasks;

import org.soldier.base.logger.AppLog;

import io.netty.channel.Channel;

public abstract class ChannelTask implements Runnable {
	private Channel mTaskChannel;
	
	public Channel getTaskChannel() {
		return mTaskChannel;
	}

	public void setTaskChannel(Channel mTaskChannel) {
		this.mTaskChannel = mTaskChannel;
	}
	
	protected abstract void onChannelTask();
	
	@Override
	public void run() {
		if (mTaskChannel == null) {
			throw new IllegalStateException("task channel should not be null");
		}
		
		try {
			onChannelTask();
		} catch (Throwable e) {
			AppLog.e(e.getMessage());
		}
	}
}
