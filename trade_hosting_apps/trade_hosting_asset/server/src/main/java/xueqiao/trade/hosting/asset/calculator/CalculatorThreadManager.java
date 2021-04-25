package xueqiao.trade.hosting.asset.calculator;

import xueqiao.trade.hosting.framework.thread.TaskThreadPool;

import java.util.HashMap;
import java.util.Map;

public class CalculatorThreadManager {
    private static CalculatorThreadManager ourInstance;

    private Map<Long, AssetContext> assetContextMap = new HashMap<>();

    private static byte[] lock = new byte[0];

    public static CalculatorThreadManager getInstance() {
        if (ourInstance == null) {
            synchronized (lock) {
                if (ourInstance == null) {
                    ourInstance = new CalculatorThreadManager();
                }
            }
        }
        return ourInstance;
    }

    private CalculatorThreadManager() {

    }

    private TaskThreadPool mTaskThreadPool = new TaskThreadPool("Asset", 4);

    public AssetContext getTaskThread(long accountId) {
        AssetContext assetContext = assetContextMap.get(accountId);
        if (assetContext == null) {
            synchronized (lock) {
                assetContext = assetContextMap.get(accountId);
                if (assetContext == null) {
                    assetContext = new AssetContext(accountId, mTaskThreadPool.allocTaskThread());
                    assetContextMap.put(accountId, assetContext);
                }
            }
        }
        return assetContext;
    }
}
