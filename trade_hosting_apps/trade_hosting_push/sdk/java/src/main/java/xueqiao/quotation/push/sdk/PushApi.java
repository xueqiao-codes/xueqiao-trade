package xueqiao.quotation.push.sdk;

import xueqiao.trade.hosting.push.protocol.UserMsgContent;
import xueqiao.trade.hosting.push.sdk.impl.IPushChannel;
import xueqiao.trade.hosting.push.sdk.impl.PushChannelManager;

public class PushApi {
	public static void sendMsgBySubUserId(final int subUserId, final UserMsg msg) {
		if (null == msg) {
			throw new IllegalArgumentException("msg should not be null");
		}
		if (null == msg.getMsgType() || msg.getMsgType().isEmpty()) {
			throw new IllegalArgumentException("msg should not be null or empty");
		}
		
		IPushChannel pushChannel = PushChannelManager.Global().getChannel();
		if (pushChannel == null) {
			return ;
		}
		
		final UserMsgContent msgContent = new UserMsgContent();
		msgContent.setMsgType(msg.getMsgType());
		msgContent.setMsgBytes(msg.getMsgContent());
		pushChannel.sendMsgBySubUserId(subUserId, msgContent);
	}
	
	public static void sendMsgBySubAccountId(final long subAccountId, final UserMsg msg) {
	    if (null == msg) {
            throw new IllegalArgumentException("msg should not be null");
        }
        if (null == msg.getMsgType() || msg.getMsgType().isEmpty()) {
            throw new IllegalArgumentException("msg should not be null or empty");
        }
        
        IPushChannel pushChannel = PushChannelManager.Global().getChannel();
        if (pushChannel == null) {
            return ;
        }
        
        final UserMsgContent msgContent = new UserMsgContent();
        msgContent.setMsgType(msg.getMsgType());
        msgContent.setMsgBytes(msg.getMsgContent());
        pushChannel.sendMsgBySubAccountId(subAccountId, msgContent);
	}
	
}
