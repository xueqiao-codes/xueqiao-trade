package xueqiao.trade.hosting.quot.comb.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.soldier.base.logger.AppLog;

import xueqiao.quotation.push.sdk.PushApi;
import xueqiao.quotation.push.sdk.UserMsg;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;
import xueqiao.trade.hosting.quot.comb.utils.SafeRunnable;

/**
 *  用于订阅用户的行情分发
 */
public class QuotCombUserDispatcher {

    private Map<Long, Set<Integer>> mSubscribeMap = new HashMap<Long, Set<Integer>>();
    private ThreadPoolExecutor mDispatcherThread;

    public QuotCombUserDispatcher() {
        mDispatcherThread = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
                , new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "QuotCombUserDispatcher");
                    }
        });
    }
    
    public void clear() {
        mDispatcherThread.execute(new SafeRunnable() {
            @Override
            public void onRun() {
                mSubscribeMap.clear();
            }
        });
    }
    
    public void addSubscribeUserGraph(int subUserId, long composeGraphId) {
        mDispatcherThread.execute(new SafeRunnable() {
            @Override
            public void onRun() {
                Set<Integer> subscribeUserSet = mSubscribeMap.get(composeGraphId);
                if (subscribeUserSet == null) {
                    subscribeUserSet = new HashSet<Integer>();
                    mSubscribeMap.put(composeGraphId, subscribeUserSet);
                }
                subscribeUserSet.add(subUserId);
            }
        });
    }
    
    public void removeSubscribeUserGraph(int subUserId, long composeGraphId) {
        mDispatcherThread.execute(new SafeRunnable() {
            @Override
            protected void onRun() {
                Set<Integer> subscribeUserSet = mSubscribeMap.get(composeGraphId);
                if (subscribeUserSet == null) {
                    return ;
                }
                subscribeUserSet.remove(subUserId);
            }
        });
    }
    
    public void sendCombQuotation(long composeGraphId
            , HostingQuotationComb combQuotation
            , byte[] combQuotationBytes) {
        mDispatcherThread.execute(new SafeRunnable() {
            @Override
            public void onRun() {
                Set<Integer> subscribeUserSet = mSubscribeMap.get(composeGraphId);
                if (subscribeUserSet == null || subscribeUserSet.isEmpty()) {
                    return ;
                }

                UserMsg userCombQuotationMsg = new UserMsg();
                userCombQuotationMsg.setMsgType("CombQuotationItem");
                userCombQuotationMsg.setMsgContent(combQuotationBytes);
                
                for (Integer subUserId : subscribeUserSet) {
                    if (AppLog.traceEnabled()) {
                        StringBuilder logBuilder = new StringBuilder(128);
                        logBuilder.append("send combQuotation=")
                                  .append(combQuotation)
                                  .append(" to subUserId=")
                                  .append(subUserId);
                        AppLog.t(logBuilder.toString());
                    }
                    
                    PushApi.sendMsgBySubUserId(subUserId, userCombQuotationMsg);
                }


            }
        });
    }
    
}
