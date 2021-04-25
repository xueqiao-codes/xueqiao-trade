package xueqiao.trade.hosting.dealing.core.sync;

public class RevokeTimeoutTaskManager extends BaseTaskManager {
	private static RevokeTimeoutTaskManager INSTANCE;
	public static RevokeTimeoutTaskManager Global() {
		if (INSTANCE == null) {
			synchronized(SyncOrderTaskManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new RevokeTimeoutTaskManager();
				}
			}
		}
		return INSTANCE;
	}
	
	@Override
	protected int freequencySleepMs() {
		return 100;
	}

}
