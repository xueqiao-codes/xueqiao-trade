package xueqiao.trade.hosting.push.sdk.impl;

import org.zeromq.ZContext;

public class PushChannelManager {
	private static PushChannelManager sInstance;
	public static PushChannelManager Global() {
		if (sInstance == null) {
			synchronized(PushChannelManager.class) {
				if (sInstance == null) {
					sInstance = new PushChannelManager();
				}
			}
		}
		return sInstance;
	}
	
	private IPushChannel mLocalPushChannel;
	private WorkThread mSenderThread;
	private ZContext mCtx;
	
	private PushChannelManager() {
		mCtx = new ZContext(1);
		mSenderThread = new WorkThread("user_msg_sender");
		mLocalPushChannel = new ZMQChannel(mSenderThread, mCtx, "127.0.0.1", 1600);
	}
	
	public IPushChannel getChannel() {
		return mLocalPushChannel;
	}
}
