package xueqiao.trade.hosting.push.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.StringFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.attr.AttrReporterFactory;
import org.soldier.platform.svr_platform.util.ProtocolUtil;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import xueqiao.trade.hosting.push.protocol.UserMsgContent;

public class UserMsgReceivor implements Runnable {
	private static TProtocolFactory USER_MSG_PROTOCOL = new TCompactProtocol.Factory();
	
	private static int ATTR_KEY_RECEIVED_USERMSG_COUNT = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.received.usermsg.count", null);
	private static int ATTR_KEY_USER_MSG_HANDLE_TIMENS = AttrReporterFactory.getDefault().requireKey(
			"xueqiao.trade.hosting.push.server.usermsg.handle.timens", null);
	
	public static interface IUserMsgCallback {
		public void onReceivedUserMsgBySubUserId(final int subUserId, final UserMsgContent msgContent);
		public void onReceivedUserMsgBySubAccountId(final long subAccountId, final UserMsgContent msgContent);
	}
	
	private ZContext mCtx;
	private int mPort;
	private IUserMsgCallback mCallback;
	private Thread mReceiveThread;
	private volatile boolean isEnded = false;
	
	private Socket mSocket;
	
	private AtomicBoolean mHeartBeatFlag = new AtomicBoolean(false);
	private final String mHeartBeatTopic = "#heartbeat#";
	
	private Thread mHeartBeatThread = new Thread() {
		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mHeartBeatFlag.set(true);
			}
		}
	};
	
	public UserMsgReceivor(ZContext ctx, int port, IUserMsgCallback callback) {
		this.mCtx = ctx;
		this.mPort = port;
		this.mCallback = callback;
	}
	
	public void start() {
		mSocket = mCtx.createSocket(ZMQ.SUB);
		mSocket.setRcvHWM(1000);
		mSocket.bind("tcp://127.0.0.1:" + mPort);
		
		mReceiveThread = new Thread(this);
		mReceiveThread.setDaemon(true);
		mReceiveThread.setName("user_msg_recivor_" + mPort);
		mReceiveThread.start();
		
		mHeartBeatThread.setDaemon(true);
		mHeartBeatThread.setName("user_msg_heartbeat_" + mPort);
		mHeartBeatThread.start();
	}
	
	public void stop() {
		isEnded = true;
		mReceiveThread.interrupt();
	}
	
	private static class MultiPartMsg {
		public List<byte[]> parts = new ArrayList<byte[]>();
		
		public boolean receive(Socket socket) {
			parts.clear();
			boolean more = true;
	        while (more)
	        {
	        	byte[] msg = socket.recv();
	        	if (msg == null) {
	        		return false;
	        	}
	        	parts.add(msg);
				more = socket.hasReceiveMore();
			}
			return true;
		}
	}

	@Override
	public void run() {
		mSocket.subscribe("/user/");
		mSocket.subscribe("/subaccount/");
		
		boolean subscribe_flag = true;
		MultiPartMsg msg = new MultiPartMsg();
		while(!isEnded) {
			try {
				// main for clean dead socket
				if (mHeartBeatFlag.get()) {
					long start = System.nanoTime();
					if (subscribe_flag) {
						mSocket.subscribe(mHeartBeatTopic);
						subscribe_flag = false;
					} else {
						mSocket.unsubscribe(mHeartBeatTopic);
						subscribe_flag = true;
					}
					mHeartBeatFlag.set(false);
					AppLog.i("usermsg heartbeat time=" + (System.nanoTime() - start) + "ns, next_subscribe=" + subscribe_flag);
				}
				
				if (!msg.receive(mSocket)) {
					AppLog.d("received error msg");
					continue;
				}
				
				long start = System.nanoTime();
				handleMsg(msg);
				AttrReporterFactory.getDefault().average(ATTR_KEY_USER_MSG_HANDLE_TIMENS
						, System.nanoTime() - start);
			} catch (Throwable e) {
				AppLog.e(e.getMessage(), e);
			}
		}
	}
	
	private void handleMsg(MultiPartMsg msg) {
		if (msg.parts.size() != 2) {
			AppLog.d("recived parts length is not 2 msg, parts size=" + msg.parts.size());
			return ;
		}
		
		String topic = StringFactory.newUtf8String(msg.parts.get(0));
		String[] splits = StringUtils.split(topic, '/');
		if (splits.length < 2) {
			if (AppLog.debugEnabled()) {
				AppLog.d("received topic splits " + splits.length + " < 2");
			}
			return ;
		}
		
		
		String subAccountIdStr = null;
		String subUserIdStr = null;
		if ("user".equals(splits[0])) {
		    subUserIdStr = splits[1];
		} else if ("subaccount".equals(splits[0])) {
		    subAccountIdStr = splits[1];
		} else {
		    if (AppLog.debugEnabled()) {
		        AppLog.d("received topic splits[0]=" + splits[0]);
		    }
		    return ;
		}

		UserMsgContent msgContent = ProtocolUtil.unSerialize(USER_MSG_PROTOCOL, msg.parts.get(1), UserMsgContent.class);
		if (AppLog.debugEnabled()) {
			AppLog.d("onRecivedUserMsg subUserId=" + subUserIdStr
					+ ", subAccountId=" + subAccountIdStr
					+ ", contentBytesLen=" + msg.parts.get(1).length
					+ ", msgType=" + ((msgContent != null) ? msgContent.getMsgType() : "null"));
		}
		if (msgContent == null) {
			if (AppLog.debugEnabled()) {
				AppLog.d("onRecivedUserMsg decode content null for userId=" + subUserIdStr);
			}
			return ;
		}
		
		AttrReporterFactory.getDefault().inc(ATTR_KEY_RECEIVED_USERMSG_COUNT, 1);
		if (mCallback == null) {
            return ;
        }
		
		try {
		    if (StringUtils.isNotEmpty(subUserIdStr)) {
		        mCallback.onReceivedUserMsgBySubUserId(Integer.parseInt(subUserIdStr), msgContent);
		    } else if (StringUtils.isNotEmpty(subAccountIdStr)){
		        mCallback.onReceivedUserMsgBySubAccountId(Long.parseLong(subAccountIdStr), msgContent);
		    }
		} catch (Throwable e) {
		    AppLog.e(e.getMessage(), e);
		}
	}
}
