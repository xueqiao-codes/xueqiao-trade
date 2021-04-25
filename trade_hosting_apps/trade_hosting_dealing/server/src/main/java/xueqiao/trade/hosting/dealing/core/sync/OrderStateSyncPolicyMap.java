package xueqiao.trade.hosting.dealing.core.sync;

import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_SLED_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RESTING;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_UPSIDE_REVOKE_RECEIVED;
import static xueqiao.trade.hosting.HostingExecOrderStateValue.ORDER_CONDITION_NOT_TRIGGER_REVOKE_SEND_UNKOWN;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.HostingExecOrderStateValue;

public class OrderStateSyncPolicyMap {
	private static OrderStateSyncPolicyMap INSTANCE;
	
	public static OrderStateSyncPolicyMap Global() {
		if (INSTANCE == null) {
			synchronized(OrderStateSyncPolicyMap.class) {
				if (INSTANCE == null) {
					INSTANCE = new OrderStateSyncPolicyMap();
				}
			}
		}
		return INSTANCE;
	}
	
	private Map<HostingExecOrderStateValue, ISyncPolicy> policyMapping 
			= new HashMap<HostingExecOrderStateValue, ISyncPolicy>();
	
	private static abstract class SyncStatePolicyBase implements ISyncPolicy {
		public ISyncTask getSyncTask(long execOrderId) {
			return new SyncOrderStateTask(execOrderId);
		}
		
		public BaseTaskManager getTaskManager() {
			return SyncOrderTaskManager.Global();
		}
	}
	
	private static class SyncOrderRevokeTimeoutSyncPolicy implements ISyncPolicy {
		@Override
		public long getDelayMs() {
			return 30000;
		}

		@Override
		public long getPeriodMs() {
			return 3000;
		}

		@Override
		public ISyncTask getSyncTask(long execOrderId) {
			return new RevokeTimeoutTask(execOrderId);
		}

		@Override
		public BaseTaskManager getTaskManager() {
			return RevokeTimeoutTaskManager.Global();
		}
	}

	private OrderStateSyncPolicyMap() {
		addPolicy(ORDER_SLED_SEND_UNKOWN, new SyncStatePolicyBase() {
			@Override
			public long getDelayMs() {
				// 采用一个相对安全的延时，避免SEND_FAILED的误判
				return 5000;
			}
			@Override
			public long getPeriodMs() {
				return 3000;
			}
		});
		
		addPolicy(ORDER_UPSIDE_RECEIVED, new SyncStatePolicyBase() {
			@Override
			public long getDelayMs() {
				return 30000;
			}

			@Override
			public long getPeriodMs() {
				return 60000;
			}
		});
		
		addPolicy(ORDER_UPSIDE_RESTING, new SyncStatePolicyBase() {
			@Override
			public long getDelayMs() {
				return 60000;
			}

			@Override
			public long getPeriodMs() {
				return 120000;
			}
		});
		
		addPolicy(ORDER_UPSIDE_PARTFINISHED, new SyncStatePolicyBase() {
			@Override
			public long getDelayMs() {
				return 15000;
			}

			@Override
			public long getPeriodMs() {
				return 15000;
			}
		});
		
		addPolicies(Arrays.asList(
				ORDER_CONDITION_NOT_TRIGGER_REVOKE_SEND_UNKOWN
				, ORDER_UPSIDE_RECEIVED_REVOKE_SEND_UNKOWN
				, ORDER_UPSIDE_RESTING_REVOKE_SEND_UNKOWN
				, ORDER_UPSIDE_PARTFINISHED_REVOKE_SEND_UNKOWN)
				, new SyncOrderRevokeTimeoutSyncPolicy()
		);
		
		addPolicy(ORDER_UPSIDE_REVOKE_RECEIVED, new SyncStatePolicyBase() {
			@Override
			public long getDelayMs() {
				return 2000;
			}

			@Override
			public long getPeriodMs() {
				return 2000;
			}
		});
		
		addPolicy(ORDER_CONDITION_NOT_TRIGGER, new SyncStatePolicyBase() {
			@Override
			public long getDelayMs() {
				return 60000;
			}

			@Override
			public long getPeriodMs() {
				return 120000;
			}
		});
	}
	
	private void addPolicy(HostingExecOrderStateValue stateValue, ISyncPolicy policy) {
		if (policyMapping.containsKey(stateValue)) {
			AppLog.f("duplicate " + stateValue + " policy");
			return ;
		}
		
		policyMapping.put(stateValue, policy);
	}
	
	private void addPolicies(List<HostingExecOrderStateValue> stateValues, ISyncPolicy policy) {
		for (HostingExecOrderStateValue stateValue : stateValues) {
			addPolicy(stateValue, policy);
		}
	}
	
	public ISyncPolicy getSyncPolicy(HostingExecOrderStateValue orderState) {
		return policyMapping.get(orderState);
	}
	
	public Set<HostingExecOrderStateValue> getPoliciesStateSet() {
		return policyMapping.keySet();
	}
	
}
