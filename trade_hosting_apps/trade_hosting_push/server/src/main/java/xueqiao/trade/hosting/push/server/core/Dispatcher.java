package xueqiao.trade.hosting.push.server.core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.push.protocol.ProtocolFrame;
import xueqiao.trade.hosting.push.protocol.ProtocolType;
import xueqiao.trade.hosting.push.protocol.QuotationTopic;
import xueqiao.trade.hosting.push.protocol.UserMsgContent;
import xueqiao.trade.hosting.push.server.UserMsgReceivor.IUserMsgCallback;
import xueqiao.trade.hosting.push.server.data.ConnectionInfo;
import xueqiao.trade.hosting.push.server.data.CopySet;
import xueqiao.trade.hosting.push.utils.RefHolder;
import xueqiao.trade.hosting.push.utils.TByteBufOutputTransport;
import xueqiao.trade.hosting.quot.dispatcher.client.IQuotationCallback;
import xueqiao.trade.hosting.quot.dispatcher.client.THQDClient;

public class Dispatcher implements IQuotationCallback
        , ChannelFutureListener
        , IUserMsgCallback {
	public static Dispatcher INSTANCE = new Dispatcher();
	public static AttributeKey<Boolean> SELF_CLOSED_KEY = AttributeKey.valueOf("selfClosed");
	
	private static int ATTR_KEY_USER_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.user.count", null);
	
	private static int ATTR_KEY_SUBSCRIBE_TOPIC_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.subscribe.topic.count", null);
	private static int ATTR_KEY_QUOTATION_RECEIVED_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.received.quotation.count", null);
	private static int ATTR_KEY_QUOTATION_DISPATCH_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.dispatched.quotation.count", null);
	private static int ATTR_KEY_QUOTATION_DROP_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.drop.quotation.count", null);
	private static int ATTR_KEY_USERMSG_DISPATCH_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.dispatched.usermsg.count", null);
	private static int ATTR_KEY_USERMSG_DROP_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.drop.usermsg.count", null);
	private static int ATTR_KEY_USERMSG_SIZE = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.received.usermsg.size", null);
	
	private Timer connectionsClearTimer = new Timer(true);
	private final long connectionsClearPeriod = 15000;
	
	private class ConnectionsClearTask extends TimerTask {
		@Override
		public void run() {
			try {
				long startTimestampMs = System.currentTimeMillis();
				if (AppLog.infoEnabled()) {
					AppLog.i("start clear connections...");
				}
			
				long startTimestamp = startTimestampMs / 1000;
				Iterator<PushChannel> it = userConnectionsMap.values().iterator();
				while(it.hasNext()) {
					PushChannel channel = it.next();
					if (channel == null) {
						continue;
					}
					if (AppLog.infoEnabled()) {
					    AppLog.i("check channel for subUserId=" + channel.getSubUserId()
					            + ", nowTimestamp=" + startTimestamp
					            + ", heartBeatTimestamp=" + channel.getLastHeartBeatTimestamp());
					}
					if (startTimestamp - channel.getLastHeartBeatTimestamp() > 60) {
					    if (AppLog.infoEnabled()) {
					        AppLog.i("close channel for subUserId=" + channel.getSubUserId());
					    }
						channel.close();
					}
				}
			
				if (AppLog.infoEnabled()) {
					AppLog.i("clear connections finished, using " + (System.currentTimeMillis() - startTimestampMs) + "ms");
				}
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
			connectionsClearTimer.schedule(new ConnectionsClearTask(), connectionsClearPeriod);
		}
	};
	
	private ConcurrentHashMap<Integer, PushChannel> userConnectionsMap 
		= new ConcurrentHashMap<Integer, PushChannel>(30,  1.5f);
	private ConcurrentHashMap<String, CopySet<PushChannel>> topicChannelsMap
	    = new ConcurrentHashMap<String, CopySet<PushChannel>>(100, 2.0f);
	private ConcurrentHashMap<Integer, CopySet<String>> userTopicsMap
		= new ConcurrentHashMap<Integer, CopySet<String>>(30, 1.5f);
	
	// 登錄，登出，訂閱的處理線程
	private ThreadPoolExecutor mWorkThread;
	private List<IDispatcherListener> mListeners = new ArrayList<IDispatcherListener>();
	
	protected Dispatcher() {
		mWorkThread = new ThreadPoolExecutor(1, 1,
	            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
	            , new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, "DispatcherWorkThread-" + System.currentTimeMillis());
					}
		});
		
		THQDClient.init("trade_hosting_push_server");
		THQDClient.Global().setQuotationCallback(this);
		
		connectionsClearTimer.schedule(new ConnectionsClearTask(), connectionsClearPeriod);
	}
	
	private static abstract class SafeRunnable implements Runnable {
		protected abstract void onRun();
		@Override
		public void run() {
			try {
				onRun();
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
		
	}
	
	public void registerListener(IDispatcherListener listener) {
	    mWorkThread.execute(new SafeRunnable() {
            @Override
            protected void onRun() {
                mListeners.add(listener);
            }
	    });
	}
	
	public void unRegisterListener(IDispatcherListener listener) {
	    mWorkThread.execute(new SafeRunnable() {
            @Override
            protected void onRun() {
                mListeners.remove(listener);
            }
	    });
	}
	
	private void notifyListenersConnected(int subUserId, PushChannel pushChannel) {
	    for (IDispatcherListener listener : mListeners) {
	        listener.onConnected(subUserId, pushChannel);
	    }
	}
	
	private void notifyListenersDisconnected(int subUserId) {
	    for (IDispatcherListener listener : mListeners) {
	        listener.onDisconnected(subUserId);
	    }
	}
	
	public PushChannel getPushChannel(final int subUserId) {
		return userConnectionsMap.get(subUserId);
	}
	
	public void onUserLogin(final int subUserId
			, final Channel userChannel
			, final TProtocolFactory clientProtocolFactory) {
		mWorkThread.execute(new SafeRunnable() {
			@Override
			public void onRun() {
				userLogin(subUserId, userChannel, clientProtocolFactory);
			}
		});
	}
	
	public void onAddQuotationTopics(final int subUserId, final Set<QuotationTopic> quotationTopics) {
		mWorkThread.execute(new SafeRunnable() {
			@Override
			public void onRun() {
				List<String> topics = new ArrayList<String>(quotationTopics.size());
				for (QuotationTopic quotationTopic : quotationTopics) {
					topics.add(buildQuotationTopic(quotationTopic));
				}
				addTopics(subUserId, topics);
			}
		});
	}
	
	public void onRemoveQuotationTopics(final int subUserId, final Set<QuotationTopic> quotationTopics) {
		mWorkThread.execute(new SafeRunnable() {
			@Override
			public void onRun() {
				List<String> topics = new ArrayList<String>(quotationTopics.size());
				for (QuotationTopic quotationTopic : quotationTopics) {
					topics.add(buildQuotationTopic(quotationTopic));
				}
				removeTopics(subUserId, topics);
			}
		});
	}
	
	@Override
	public void operationComplete(ChannelFuture future) throws Exception {
		Boolean selfClosed = future.channel().attr(SELF_CLOSED_KEY).get();
		if (selfClosed != null && selfClosed) {
			return ;
		}
		final ConnectionInfo connectionInfo = future.channel().attr(ConnectionInfo.KEY).get();
		if (connectionInfo == null) {
			return ;
		}
		mWorkThread.execute(new SafeRunnable() {
			@Override
			public void onRun() {
				connectionClosed(connectionInfo.getSubUserId());
			}
		});
	}
	
	// works in working thread
	private void removeTopics(int subUserId, List<String> topics) {
		if (AppLog.infoEnabled()) {
			AppLog.i("removeTopics for subUserId=" + subUserId + ", topics=" + StringUtils.join(topics, ","));
		}
		
		PushChannel pushChannel = userConnectionsMap.get(subUserId);
		if (pushChannel == null) {
			return ;
		}
		
		CopySet<String> userTopicsSet = userTopicsMap.get(subUserId);
		for (String topic : topics) {
			removePushChannelTopic(topic, pushChannel);
			if (userTopicsSet != null) {
				userTopicsSet.remove(topic);
			}
		}
	}
	
	private void addTopics(int subUserId, List<String> topics) {
		if (AppLog.infoEnabled()) {
			AppLog.i("addTopics for subUserId=" + subUserId + ", topics=" + StringUtils.join(topics, ","));
		}
		
		PushChannel pushChannel = userConnectionsMap.get(subUserId);
		if (pushChannel == null) {
			return ;
		}
		
		CopySet<String> userTopicsSet = userTopicsMap.get(subUserId);
		if (userTopicsSet == null) {
			userTopicsSet = new CopySet<String>();
		}
		userTopicsMap.put(subUserId, userTopicsSet);
		
		for (String topic : topics) {
			addPushChannelTopic(topic, pushChannel);
			userTopicsSet.add(topic);
		}
	}
	
	private void userLogin(int subUserId
			, Channel userChannel
			, TProtocolFactory clientProtocolFactory) {
		if (AppLog.infoEnabled()) {
			AppLog.i("login for subUserId=" + subUserId 
					+ ", protocol=" + clientProtocolFactory.getClass().getName()
					+ ", channelId=" + userChannel.id().asLongText());
		}
		PushChannel originPushChannel = userConnectionsMap.get(subUserId);
		if (originPushChannel != null) {
			if (originPushChannel.getChannel().id().asLongText().equals(
					userChannel.id().asLongText())) {
				// same channel for user
				return ; 
			}
			
			closePushChannel(originPushChannel);
		}
		
		PushChannel userPushChannel = new PushChannel(subUserId, userChannel, clientProtocolFactory);
		userConnectionsMap.put(subUserId, userPushChannel);
		notifyListenersConnected(subUserId, userPushChannel);
		userChannel.closeFuture().addListener(this);
		AttrReporterFactory.getDefault().keep(ATTR_KEY_USER_COUNT, userConnectionsMap.size());
	}
	
	private void addPushChannelTopic(String topic, PushChannel pushChannel) {
		if (AppLog.infoEnabled()) {
			AppLog.i("addPushChannelTopic topic=" + topic + ", pushChannel=" + pushChannel);
		}
		
		CopySet<PushChannel> topicChannelSet = topicChannelsMap.get(topic);
		if (topicChannelSet == null) {
			topicChannelSet = new CopySet<PushChannel>();
			topicChannelsMap.put(topic, topicChannelSet);
		}
		if (topicChannelSet.count() <= 0) {
			QuotationTopic quotationTopic = fromTopicStr(topic);
			if (quotationTopic != null) {
				if (AppLog.infoEnabled()) {
					AppLog.i("subscribe /" + quotationTopic.getPlatform() + "/" + quotationTopic.getContractSymbol() + " from SubscribeSystem");
				}
				THQDClient.Global().addTopic(quotationTopic.getPlatform(), quotationTopic.getContractSymbol());
			}
		}
		topicChannelSet.add(pushChannel);
		AttrReporterFactory.getDefault().keep(ATTR_KEY_SUBSCRIBE_TOPIC_COUNT, topicChannelsMap.size());
	}
	
	private void removePushChannelTopic(String topic, PushChannel pushChannel) {
		if (AppLog.infoEnabled()) {
			AppLog.i("removePushChannelTopic topic=" + topic + ", pushChannel=" + pushChannel);
		}
		
		CopySet<PushChannel> topicChannelSet = topicChannelsMap.get(topic);
		if (topicChannelSet == null) {
			return ;
		}
		topicChannelSet.remove(pushChannel); 
		if (topicChannelSet.count() <= 0) {
			QuotationTopic quotationTopic = fromTopicStr(topic);
			if (quotationTopic != null) {
				if (AppLog.infoEnabled()) {
					AppLog.i("Unsubscribe /" + quotationTopic.getPlatform() + "/" + quotationTopic.getContractSymbol() + " from SubscribeSystem");
				}
				THQDClient.Global().removeTopic(quotationTopic.getPlatform(), quotationTopic.getContractSymbol());
			}
			
			topicChannelsMap.remove(topic);
		}
		AttrReporterFactory.getDefault().keep(ATTR_KEY_SUBSCRIBE_TOPIC_COUNT, topicChannelsMap.size());
	}
	
	private void clearUserTopics(int subUserId) {
		if (AppLog.infoEnabled()) {
			AppLog.i("clear topics for subUserId=" + subUserId);
		}
		
		CopySet<String> topicsSet = userTopicsMap.get(subUserId);
		if (topicsSet == null) {
			return ;
		}
		
		Set<String> topicsSetAll = topicsSet.unsafedAll();
		for (String topic : topicsSetAll) {
			removePushChannelTopic(topic, PushChannel.newForContainerFound(subUserId));
		}
		userTopicsMap.remove(subUserId);
	}
	
	private void connectionClosed(int subUserId) {
		if (AppLog.infoEnabled()) {
			AppLog.i("Connection Closed for subUserId=" + subUserId);
		}
		clearUserTopics(subUserId);
		userConnectionsMap.remove(subUserId);
		notifyListenersDisconnected(subUserId);
		AttrReporterFactory.getDefault().keep(ATTR_KEY_USER_COUNT, userConnectionsMap.size());
	}
	
	/**
	 *  主動關閉通道，取消對應的通知
	 * @param pushChannel
	 */
	private void closePushChannel(PushChannel pushChannel) {
		pushChannel.getChannel().attr(ConnectionInfo.KEY).set(null);
		pushChannel.close();
		connectionClosed(pushChannel.getSubUserId());
	}
	
	private static String buildQuotationTopic(QuotationTopic quotationTopic) {
		return buildQuotationTopic(quotationTopic.getPlatform(), quotationTopic.getContractSymbol());
	}
	
	private static String buildQuotationTopic(String platform, String contractSymbol) {
		StringBuilder builder = new StringBuilder(64);
		builder.append("/quotation/");
		builder.append(platform);
		builder.append("/");
		builder.append(contractSymbol);
		builder.append("/");
		return builder.toString();
	}
	
	private static QuotationTopic fromTopicStr(String topic) {
		if (topic != null && topic.startsWith("/quotation/")) {
			String[] quotationTopicSplits = StringUtils.split(topic, '/');
			if (quotationTopicSplits.length >= 3) {
				QuotationTopic quotationTopic = new QuotationTopic();
				quotationTopic.setPlatform(quotationTopicSplits[1]);
				quotationTopic.setContractSymbol(quotationTopicSplits[2]);
				return quotationTopic;
			}
		}
		return null;
	}
	
	private boolean sendProtocolFrameMaxPendingDroped(
            ProtocolFrame message
            , PushChannel pushChannel
            , RefHolder<BinaryWebSocketFrame> outCompactFrame
            , RefHolder<TextWebSocketFrame> outJsonFrame
            , int initialBufferSize) {
	    if (pushChannel.getClientProtocolFactory().getClass() == TCompactProtocol.Factory.class) {
            if (null == outCompactFrame.get()) {
                ByteBuf outCompactByteBuf = PooledByteBufAllocator.DEFAULT.ioBuffer(initialBufferSize);
                ProtocolUtil.serialize(pushChannel.getClientProtocolFactory()
                            , new TByteBufOutputTransport(outCompactByteBuf)
                            , message);
                outCompactFrame.set(new BinaryWebSocketFrame(outCompactByteBuf));
            }
            
            return pushChannel.writeMaxPendingDroped(outCompactFrame.get().retainedDuplicate());
        } else {
            if (null == outJsonFrame.get()) {
                ByteBuf outJsonByteBuf =  PooledByteBufAllocator.DEFAULT.ioBuffer(initialBufferSize);
                ProtocolUtil.serialize(pushChannel.getClientProtocolFactory()
                            , new TByteBufOutputTransport(outJsonByteBuf)
                            , message);
                outJsonFrame.set(new TextWebSocketFrame(outJsonByteBuf));
            }
            
            return pushChannel.writeMaxPendingDroped(outJsonFrame.get().retainedDuplicate());
        }
    }
	
	@Override
	public void onReceivedQuotationItem(String platform, String contractSymbol, QuotationItem item) {
		CopySet<PushChannel> subscribeChannels 
			= topicChannelsMap.get(buildQuotationTopic(platform, contractSymbol));
		if (subscribeChannels == null) {
			return ;
		}
		
		if (AppLog.debugEnabled()) {
			AppLog.d("received Quotation " + item);
		}
		
		AttrReporterFactory.getDefault().inc(ATTR_KEY_QUOTATION_RECEIVED_COUNT, 1);
		
		item.unsetReceivedHostName();
		item.unsetReceivedProcessId();
		item.unsetReceivedTimestampMs();
		
		ProtocolFrame message = new ProtocolFrame();
		message.setProtocol(ProtocolType.QUOTATION);
		message.setQuotationItem(item);
		
		RefHolder<BinaryWebSocketFrame> outCompactFrame = new RefHolder<BinaryWebSocketFrame>();
		RefHolder<TextWebSocketFrame> outJsonFrame = new RefHolder<TextWebSocketFrame>();
		Set<PushChannel> allChannels = subscribeChannels.copyAll();
		for (PushChannel pushChannel : allChannels) {
			if (AppLog.debugEnabled()) {
				AppLog.d("dispatch quotation to " + pushChannel.getSubUserId() + ", platform=" 
						+ item.getPlatform() + ", contractSymbols=" + item.getContractSymbol());
			}
			if (sendProtocolFrameMaxPendingDroped(message, pushChannel, outCompactFrame, outJsonFrame, 512)) {
				AttrReporterFactory.getDefault().inc(ATTR_KEY_QUOTATION_DISPATCH_COUNT, 1);
			} else {
				AttrReporterFactory.getDefault().inc(ATTR_KEY_QUOTATION_DROP_COUNT, 1);
			}
		}
		if (null != outCompactFrame.get()) {
			ReferenceCountUtil.release(outCompactFrame.get());
		}
		if (null != outJsonFrame.get()) {
			ReferenceCountUtil.release(outJsonFrame.get());
		}
	}
	
	private void sendUserMsgContent(PushChannel pushChannel, UserMsgContent msgContent) {
	    ProtocolFrame message = new ProtocolFrame();
        message.setProtocol(ProtocolType.USERMSG);
        message.setUserMsg(msgContent);
        
        AttrReporterFactory.getDefault().average(ATTR_KEY_USERMSG_SIZE, msgContent.bufferForMsgBytes().remaining());
        
        RefHolder<BinaryWebSocketFrame> outCompactFrame = new RefHolder<BinaryWebSocketFrame>();
        RefHolder<TextWebSocketFrame> outJsonFrame = new RefHolder<TextWebSocketFrame>();
        if (sendProtocolFrameMaxPendingDroped(message, pushChannel, outCompactFrame, outJsonFrame, 512)) {
            AttrReporterFactory.getDefault().inc(ATTR_KEY_USERMSG_DISPATCH_COUNT, 1);
        } else {
            AttrReporterFactory.getDefault().inc(ATTR_KEY_USERMSG_DROP_COUNT, 1);
        }
        if (null != outCompactFrame.get()) {
            ReferenceCountUtil.release(outCompactFrame.get());
        }
        if (null != outJsonFrame.get()) {
            ReferenceCountUtil.release(outJsonFrame.get());
        }
	}
	
	@Override
	public void onReceivedUserMsgBySubUserId(int subUserId, UserMsgContent msgContent) {
		PushChannel channel = userConnectionsMap.get(subUserId);
		if (channel == null) {
			return ;
		}
		sendUserMsgContent(channel, msgContent);
	}

    @Override
    public void onReceivedUserMsgBySubAccountId(long subAccountId, UserMsgContent msgContent) {
        Set<Integer> subUserIds = SubAccountRelatedCacheHolder.get().getRelatedSubUserIds(subAccountId);
        if (subUserIds == null) {
            return ;
        }
        for (Integer subUserId : subUserIds) {
            PushChannel channel = userConnectionsMap.get(subUserId);
            if (channel == null) {
                continue ;
            }
            sendUserMsgContent(channel, msgContent);
        }
    }
	
}
