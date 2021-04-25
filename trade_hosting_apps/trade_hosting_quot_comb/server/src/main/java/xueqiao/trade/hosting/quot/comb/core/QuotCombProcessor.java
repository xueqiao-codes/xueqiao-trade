package xueqiao.trade.hosting.quot.comb.core;

import com.google.common.base.Preconditions;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import org.soldier.platform.svr_platform.util.ProtocolUtil;
import org.zeromq.ZContext;
import xueqiao.quotation.QuotationItem;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.quot.comb.utils.SafeRunnable;
import xueqiao.trade.hosting.quot.dispatcher.client.IQuotationCallback;
import xueqiao.trade.hosting.quot.dispatcher.client.THQDClient;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class QuotCombProcessor implements IQuotationCallback, QuotCombCalculatorManager.ITopicCallback {
    private static final TProtocolFactory PROTOCOLFACTORY = new TCompactProtocol.Factory();
    private static QuotCombProcessor sInstance;

    private ThreadPoolExecutor mProcessorThread;

    // 计算器资源管理
    private QuotCombCalculatorManager mCalculatorManager = new QuotCombCalculatorManager(this);

    // 用户订阅的信息
    private Map<Long, Set<Integer>> mSubscribeUsers = new HashMap<>();
    private QuotCombUserDispatcher mUserDispatcher;

    // 内部程序订阅的信息
    private HashSet<Long> mNeedPublishGraphs = new HashSet<>();
    private ZContext mCtx;
    private QuotCombPublisher mPublisher;


    public static QuotCombProcessor Global() {
        if (sInstance == null) {
            synchronized (QuotCombProcessor.class) {
                if (sInstance == null) {
                    sInstance = new QuotCombProcessor();
                }
            }
        }
        return sInstance;
    }
    
    private QuotCombProcessor() {
        mProcessorThread = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()
                , new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "QuotCombProcessor");
                    }
        });
        mUserDispatcher = new QuotCombUserDispatcher();
        mCtx = new ZContext();
        mPublisher = new QuotCombPublisher(mCtx, 1850);
        mPublisher.startWork();
    }

    // 计算保留的计算资源
    private void reserveCalculators() {
        HashSet<Long> reserveCalculators = new HashSet<>(mNeedPublishGraphs);
        reserveCalculators.addAll(mSubscribeUsers.keySet());

        HashSet<Long> allComposeGraphIds = new HashSet<>(mCalculatorManager.getAllComposeGraphIds());
        for (Long composeGraphId : allComposeGraphIds) {
            if (!reserveCalculators.contains(composeGraphId)) {
                mCalculatorManager.rmCalculator(composeGraphId);
            }
        }
    }
    
    public void clearSubscribeUsers() {
        if (AppLog.infoEnabled()) {
            AppLog.i("clearSubscribeUsers...");
        }
        
        mUserDispatcher.clear();
        mProcessorThread.execute(new SafeRunnable() {
            @Override
            public void onRun() {
                mSubscribeUsers.clear();
                reserveCalculators();
            }
        });
    }

    public void addNeedPublishGraph(HostingComposeGraph composeGraph) {
        Preconditions.checkNotNull(composeGraph);

        if (AppLog.infoEnabled()) {
            AppLog.i("addNeedPublishGraph composeGraph=" + composeGraph);
        }

        mProcessorThread.execute(new SafeRunnable() {
            @Override
            protected void onRun() throws Exception {
                mCalculatorManager.addCalculator(composeGraph);
                mNeedPublishGraphs.add(composeGraph.getComposeGraphId());
            }
        });

    }

    public void addSubscribeUserGraph(int subUserId, HostingComposeGraph composeGraph) {
        Preconditions.checkNotNull(composeGraph);
        if (AppLog.infoEnabled()) {
            AppLog.i("addSubscribeUserGraph subUserId=" + subUserId + ", composeGraph=" + composeGraph);
        }
        
        mUserDispatcher.addSubscribeUserGraph(subUserId, composeGraph.getComposeGraphId());
        mProcessorThread.execute(new SafeRunnable() {
            @Override
            public void onRun() throws UnsupportedEncodingException, ErrorInfo {
                mCalculatorManager.addCalculator(composeGraph);
                
                Set<Integer> subscribedUserSet = mSubscribeUsers.get(composeGraph.getComposeGraphId());
                if (subscribedUserSet == null) {
                    subscribedUserSet = new HashSet<Integer>();
                    mSubscribeUsers.put(composeGraph.getComposeGraphId(), subscribedUserSet);
                }
                subscribedUserSet.add(subUserId);
                

            }
        });
    }

    public void removeNeedPublishGraph(long composeGraphId) {
        mProcessorThread.execute(new SafeRunnable() {
            @Override
            protected void onRun() throws Exception {
                mNeedPublishGraphs.remove(composeGraphId);

                Set<Integer> subscribedUserSet = mSubscribeUsers.get(composeGraphId);
                if (subscribedUserSet != null && !subscribedUserSet.isEmpty()) {
                    // 用户任然关心这个组合
                    return ;
                }

                mCalculatorManager.rmCalculator(composeGraphId);
            }
        });

    }
    
    public void removeSubscribeUserGraph(int subUserId, long composeGraphId) {
        if (AppLog.infoEnabled()) {
            AppLog.i("removeSubscribeUserGraph subUserId=" + subUserId + ", composeGraphId=" + composeGraphId);
        }
        
        mUserDispatcher.removeSubscribeUserGraph(subUserId, composeGraphId);
        mProcessorThread.execute(new SafeRunnable() {
            @Override
            public void onRun() {
                Set<Integer> subscribedUserSet = mSubscribeUsers.get(composeGraphId);
                if (subscribedUserSet != null) {
                    subscribedUserSet.remove(subUserId);
                    if (!subscribedUserSet.isEmpty()) {
                        // 还有用户关注这个组合
                        return ;
                    }
                }

                if (mNeedPublishGraphs.contains(composeGraphId)) {
                    // 内部程序关系这个组合
                    return ;
                }

                mCalculatorManager.rmCalculator(composeGraphId);
            }
        });
    }



    @Override
    public void onReceivedQuotationItem(String platform, String contractSymbol, QuotationItem item) {
        mProcessorThread.execute(new SafeRunnable() {
            @Override
            public void onRun() {
                if (AppLog.traceEnabled()) {
                    AppLog.t("onReceivedQuotationItem item=" + item);
                }
                
                Set<Long> relatedCalculatorSet = mCalculatorManager.getRelatedCalculators(
                        item.getPlatform(), item.getContractSymbol());
                if (relatedCalculatorSet == null || relatedCalculatorSet.isEmpty()) {
                    return ;
                }
                
                for (Long composeGraphId : relatedCalculatorSet) {
                    QuotCombCalculator calculator = mCalculatorManager.getCalculator(composeGraphId);
                    if (calculator == null) {
                        AppLog.w("[WARNING]can not found calculator for composeGraphId=" + composeGraphId);
                        continue;
                    }
                    
                    if (!calculator.onReceivedQuotationItem(item)) {
                        continue;
                    }

                    byte[] combQuotationBytes = ProtocolUtil.serialize2Bytes(PROTOCOLFACTORY
                            , calculator.getCombQuotationItem());
                    mUserDispatcher.sendCombQuotation(composeGraphId, calculator.getCombQuotationItem(), combQuotationBytes);
                    if (mNeedPublishGraphs.contains(composeGraphId)) {
                        mPublisher.sendQuotationComb(composeGraphId, calculator.getCombQuotationItem(), combQuotationBytes);
                    }
                }
            }
        });
    }

    @Override
    public void addTopic(String platform, String contractSymbol) {
        THQDClient.Global().addTopic(platform, contractSymbol);
    }

    @Override
    public void removeTopic(String platform, String contractSymbol) {
        THQDClient.Global().removeTopic(platform, contractSymbol);
    }
}
