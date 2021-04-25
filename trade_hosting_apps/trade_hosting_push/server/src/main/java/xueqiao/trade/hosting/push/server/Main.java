package xueqiao.trade.hosting.push.server;

import org.soldier.base.logger.AppLog;
import org.soldier.framework.message_bus.api.MessageAgent;
import org.zeromq.ZContext;

import xueqiao.trade.hosting.framework.HostingMessageContext;
import xueqiao.trade.hosting.framework.utils.ProcessUtils;
import xueqiao.trade.hosting.push.server.core.Dispatcher;
import xueqiao.trade.hosting.push.server.core.GlobalCallRegister;
import xueqiao.trade.hosting.push.server.core.agent.AgentPushConsumer;
import xueqiao.trade.hosting.push.server.core.seq.SeqPushManager;
import xueqiao.trade.hosting.push.server.impl.HeartBeatHandler;
import xueqiao.trade.hosting.push.server.impl.SubscribeHandler;
import xueqiao.trade.hosting.push.server.impl.UnSubscribeHandler;
import xueqiao.trade.hosting.storage.apis.StorageInitialzer;

public class Main {
	public static void main(String[] args) throws Exception {
	    ProcessUtils.checkSingleInstance("trade_hosting_push_server");
		AppLog.init("apps/trade_hosting_push_server");
		
		StorageInitialzer.initApis();
		
		GlobalCallRegister.Global()
				.addHandler(new SubscribeHandler())
				.addHandler(new UnSubscribeHandler())
				.addHandler(new HeartBeatHandler());
		
		int ioThreads = 1;
		if (Runtime.getRuntime().availableProcessors() > 1) {
			ioThreads = Runtime.getRuntime().availableProcessors() / 2;
		}
		
		ZContext ctx = new ZContext(ioThreads);
		UserMsgReceivor receivor = new UserMsgReceivor(ctx, Variables.USER_MSG_RECEIVOR_PORT, Dispatcher.INSTANCE);
		receivor.start();
		
		Dispatcher.INSTANCE.registerListener(SeqPushManager.Global());
		AgentPushConsumer pushConsumer = new AgentPushConsumer();
		MessageAgent.initNativeLogName("trade_hosting_push_server");
		HostingMessageContext.Global().createConsumerAgent("hosting_push", pushConsumer);
		
		new PushServer(Variables.PUSH_SERVER_PORT).run();
		receivor.stop();
	}
}
