package xueqiao.trade.hosting.push.sdk.impl;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.logger.AppLog;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import xueqiao.trade.hosting.push.protocol.UserMsgContent;

public class ZMQChannel implements IPushChannel {
	private static TProtocolFactory PUSH_PROTOCOL_FACTORY = new TCompactProtocol.Factory();
	
	private WorkThread mWorkThread;
	private Socket mSocket;
	
	private String mServerAddr;
	private int mServerPort;
	private String mSocketDescription;
	
	public ZMQChannel(WorkThread workThread
			, ZContext ctx
			, String serverAddr
			, int serverPort) {
		this.mWorkThread = workThread;
		this.mServerAddr = serverAddr;
		this.mServerPort = serverPort;
		
		StringBuilder socketDescription = new StringBuilder(64);
		socketDescription.append("tcp://");
		socketDescription.append(this.mServerAddr);
		socketDescription.append(":");
		socketDescription.append(this.mServerPort);
		mSocketDescription = socketDescription.toString();
		
		mSocket = ctx.createSocket(ZMQ.PUB);
		mSocket.setSndHWM(1000);
		mSocket.connect(mSocketDescription);
		AppLog.i("connect to " + mSocketDescription);
	}
	
	public String getSocketDescription() {
		return mSocketDescription;
	}
	
	public String getServerAddr() {
		return mServerAddr;
	}
	
	public int getServerPort() {
		return mServerPort;
	}
	
	public void destroy() {
		mWorkThread.postTask(new Runnable() {
			@Override
			public void run() {
				mSocket.close();
				mSocket = null;
			}
		});
	}
	
	@Override
	public void sendMsgBySubUserId(final int subUserId, final UserMsgContent msg) {
		mWorkThread.postTask(new Runnable() {
			@Override
			public void run() {
				if (mSocket == null) {
					return ;
				}
				try {
					mSocket.sendMore("/user/" + subUserId + "/");
					mSocket.sendByteBuffer(ProtocolUtil.serialize(PUSH_PROTOCOL_FACTORY, msg), 0);
				} catch (Throwable e) {
					AppLog.e(e.getMessage(), e);
				}
			}
		});
	}

    @Override
    public void sendMsgBySubAccountId(long subAccountId, UserMsgContent msg) {
        mWorkThread.postTask(new Runnable() {
            @Override
            public void run() {
                if (mSocket == null) {
                    return ;
                }
                try {
                    mSocket.sendMore("/subaccount/" + subAccountId + "/");
                    mSocket.sendByteBuffer(ProtocolUtil.serialize(PUSH_PROTOCOL_FACTORY, msg), 0);
                } catch (Throwable e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        });
    }
	
	
}
