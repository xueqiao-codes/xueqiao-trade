package xueqiao.trade.hosting.dealing.core.sync;

public class SyncOrderTaskManager extends BaseTaskManager {
	private static SyncOrderTaskManager INSTANCE;
	public static SyncOrderTaskManager Global() {
		if (INSTANCE == null) {
			synchronized(SyncOrderTaskManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new SyncOrderTaskManager();
				}
			}
		}
		return INSTANCE;
	}
	
	
	@Override
	protected int freequencySleepMs() {
		return 1000;
	}

}
