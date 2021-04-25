package xueqiao.trade.hosting.push.sdk.impl;

import xueqiao.trade.hosting.push.protocol.UserMsgContent;

public interface IPushChannel {
	public void sendMsgBySubUserId(final int subUserId, final UserMsgContent msg);
	public void sendMsgBySubAccountId(final long subAccountId, final UserMsgContent msg);
}
