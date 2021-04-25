package xueqiao.trade.hosting.push.sdk.test;

import org.soldier.base.logger.AppLog;

import xueqiao.quotation.push.sdk.PushApi;
import xueqiao.quotation.push.sdk.UserMsg;

public class Main {
	
	public static void main(String[] argv) {
		AppLog.init("trade_hosting_push_sdk_test");
		
//		int key = AttrReporterFactory.getDefault().requireKey("quotation.push.test.sendmsg.count", null);
		
		String userId = argv[0];
		String content = argv[1];
		
		while(true) {
			UserMsg msg = new UserMsg();
			msg.setMsgType("test");
			msg.setMsgContent(content.getBytes());
			
			PushApi.sendMsgBySubUserId(Integer.valueOf(userId), msg);
			
			System.out.println("send msg to " + userId + ", msg=" + msg);
			
//			AttrReporterFactory.getDefault().inc(key, 1);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
