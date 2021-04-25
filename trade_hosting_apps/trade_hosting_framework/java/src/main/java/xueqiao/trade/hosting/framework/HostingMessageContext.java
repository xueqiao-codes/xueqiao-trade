package xueqiao.trade.hosting.framework;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.soldier.framework.message_bus.api.IMessageConsumer;
import org.soldier.framework.message_bus.api.MessageAgent;

import com.google.common.base.Preconditions;

public class HostingMessageContext {
	
	private static File HOSTING_MESSAGE_GRAPH_FILE 
		= new File("/data/configs/qconf/xueqiao/trade/hosting/message_graph.json");
	
	private static HostingMessageContext sContext;
	
	public static HostingMessageContext Global() {
		if (sContext == null) {
			synchronized(HostingMessageContext.class) {
				if (sContext == null) {
					sContext = new HostingMessageContext();
				}
			}
		}
		return sContext;
	}
	
	private MessageAgent mSenderAgent;
	private Map<String, MessageAgent> mConsumerAgents = new HashMap<String, MessageAgent>();
	
	private HostingMessageContext() {
		mSenderAgent = new MessageAgent(new IMessageConsumer() {
			@Override
			public StartUpMode onStartUp() {
				return StartUpMode.CLEAR_QUEUE_INIT;
			}

			@Override
			public void onInit() throws Exception {
			}
		}, "empty", HOSTING_MESSAGE_GRAPH_FILE);
	}
	
	public MessageAgent getSenderAgent() {
		return mSenderAgent;
	}
	
	public synchronized void createConsumerAgent(String agentName, IMessageConsumer consumer) {
		Preconditions.checkArgument(StringUtils.isNotBlank(agentName));
		
		if (mConsumerAgents.containsKey(agentName)) {
			return ;
		}
		
		mConsumerAgents.put(agentName, new MessageAgent(consumer,agentName, HOSTING_MESSAGE_GRAPH_FILE));
	}
}
