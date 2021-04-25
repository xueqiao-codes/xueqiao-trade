package xueqiao.trade.hosting.push.server.core.seq;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TBase;
import org.apache.thrift.protocol.TCompactProtocol;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.util.ProtocolUtil;

import com.google.common.base.Preconditions;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.ReferenceCountUtil;
import xueqiao.trade.hosting.push.protocol.ProtocolFrame;
import xueqiao.trade.hosting.push.protocol.ProtocolType;
import xueqiao.trade.hosting.push.protocol.SeqMsgContent;
import xueqiao.trade.hosting.push.protocol.trade_hosting_push_protocolConstants;
import xueqiao.trade.hosting.push.server.core.PushChannel;
import xueqiao.trade.hosting.push.utils.TByteBufOutputTransport;

@SuppressWarnings("rawtypes")
public class SeqPushQueue implements Runnable {
    private static final int MAX_PENDING_MESSAGES = 500;
    
    private SeqPushScheduler mScheduler;
    private List<TBase> mQueue;
    private volatile int mCount = 0;
    private int mSubUserId;
    private PushChannel mPushChannel;
    private long mLastestSeqNo;
    private ScheduledFuture<?> mTimerFuture;
    
    public SeqPushQueue(SeqPushScheduler scheduler
    		, int subUserId
    		, PushChannel pushChannel
    		, ScheduledExecutorService timerService)  {
        mScheduler = scheduler;
        mQueue = new LinkedList<TBase>();
        mCount = 0;
        mSubUserId = subUserId;
        mPushChannel = pushChannel;
        mLastestSeqNo = 0;
        mTimerFuture = timerService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					SeqPushQueue.this.addMessage(trade_hosting_push_protocolConstants.SEQ_ALIGN_TYPE, null);
				} catch (Throwable e) {
					AppLog.e(e.getMessage(), e);
				}
			}
        }, 10, 10, TimeUnit.SECONDS);
    }
    
    public void addMessage(TBase msgContent) {
    	Preconditions.checkArgument(msgContent != null);
    	addMessage(msgContent.getClass().getSimpleName()
    			, ProtocolUtil.serialize2Bytes(mPushChannel.getClientProtocolFactory(), msgContent));
    }
    
    public void addMessage(String msgType, byte[] msgBytes) {
    	Preconditions.checkArgument(StringUtils.isNotEmpty(msgType));
    	
    	SeqMsgContent seqMsg = new SeqMsgContent();
    	seqMsg.setMsgType(msgType);
    	if (msgBytes != null) {
    		seqMsg.setMsgBytes(msgBytes);
    	}
    	
    	ProtocolFrame sendFrame = new ProtocolFrame();
    	sendFrame.setProtocol(ProtocolType.SEQMSG);
    	
    	long seqNo = 0;
    	synchronized(this) {
    		seqNo = ++mLastestSeqNo;
    		seqMsg.setSeqNo(seqNo);
    		sendFrame.setSeqMsg(seqMsg);
    		mQueue.add(sendFrame);
    		mCount += 1;
    	}
    	
    	if (AppLog.infoEnabled()) {
    		StringBuilder logBuilder = new StringBuilder(128);
    		logBuilder.append("addMessage ")
    				  .append("subUserId=").append(mSubUserId)
    				  .append(", seqNo=").append(seqNo)
    			      .append(", msgType=").append(msgType)
    		          .append(", msgBytes length=")
    		          .append(msgBytes == null ? 0 : msgBytes.length);
    		AppLog.i(logBuilder.toString());
    	}
    	
    	mScheduler.markRunnable(this, 0);
    }
    
    public void destroy() {
    	mTimerFuture.cancel(false);
    	
        synchronized(this) {
            mQueue.clear();
            mCount = 0;
        }
    }
    
    private boolean sendFirstMessage() {
        TBase msg = null;
        synchronized(this) {
            if (mCount <= 0) {
                return false;
            }
            
            // 队列堆积过满， 强制断线
            if (mCount >= MAX_PENDING_MESSAGES) {
                AppLog.w("[NOTICE]" + mSubUserId + " message pending count > " + MAX_PENDING_MESSAGES
                        + ", Disconnectd it!");
                mPushChannel.close();
                return false;
            }
            
            if (!mPushChannel.getChannel().isWritable()) {
                // 队列堆积，延时发送
                mScheduler.markRunnable(this, 10);
                return false;
            }
            
            msg = mQueue.get(0);
        }
        
        WebSocketFrame sendFrame = null;
        ByteBuf outBuf = PooledByteBufAllocator.DEFAULT.ioBuffer(512);
        ProtocolUtil.serialize(mPushChannel.getClientProtocolFactory()
                , new TByteBufOutputTransport(outBuf)
                , msg);
        if (mPushChannel.getClientProtocolFactory().getClass() == TCompactProtocol.Factory.class) {
            sendFrame = new BinaryWebSocketFrame(outBuf);
        } else {
            sendFrame = new TextWebSocketFrame(outBuf);
        }
        
        boolean sendSuccess = false;
        try {
            mPushChannel.getChannel().writeAndFlush(sendFrame);
            sendSuccess = true;
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        } finally {
            if (!sendSuccess) {
                ReferenceCountUtil.release(sendFrame);
            }
        }
        
        if (sendSuccess) {
            synchronized(this) {
                mQueue.remove(0);
                mCount -= 1;
            }
        } else {
            mScheduler.markRunnable(this, 10);
        }
        
        if (AppLog.infoEnabled()) {
            StringBuilder logBuilder = new StringBuilder(128);
            logBuilder.append("write msg ").append(msg)
                      .append(" to client result=")
                      .append(sendSuccess);
            AppLog.i(logBuilder.toString());
        }
        
        return sendSuccess;
    }

    @Override
    public void run() {
        try {
            if (AppLog.infoEnabled()) {
                AppLog.i("AgentPushQueue for subUserId=" + mSubUserId + " run...");
            }
            
            while(sendFirstMessage()) {}
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
            // Unexpected Exception
            mPushChannel.close();
        }
    }
    
    @Override
    public String toString() {
    	return "AgentPushQueue[" + mSubUserId + "]";
    }
}
