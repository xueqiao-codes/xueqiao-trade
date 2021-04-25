package xueqiao.trade.hosting.quot.comb.core;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.HostingComposeGraph;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.comb.thriftapi.SyncCombTopicsRequest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class QuotCombSyncTopicsHandler {
    private static class ConsumerNode {
        private String mConsumerKey;
        private Map<Long, HostingComposeGraph> mComposeGraphs = new HashMap<>();
        private long mLastSyncTimetampMs;

        private ConsumerNode(String consumerKey) {
            this.mConsumerKey = consumerKey;
        }

        public String getConsumerKey() {
            return mConsumerKey;
        }

        public Collection<HostingComposeGraph> getComposeGraphs() {
            return mComposeGraphs.values();
        }

        public void clearComposeGraphs() {
            mComposeGraphs.clear();
        }

        public void addComposeGraph(HostingComposeGraph composeGraph) {
            Preconditions.checkNotNull(composeGraph);
            mComposeGraphs.put(composeGraph.getComposeGraphId(), composeGraph);
        }

        public long getLastSyncTimetampMs() {
            return mLastSyncTimetampMs;
        }

        public void setLastSyncTimetampMs(long lastSyncTimetampMs) {
            this.mLastSyncTimetampMs = lastSyncTimetampMs;
        }
    }

    private TaskThread mAnylysisThread;

    private Map<String, ConsumerNode> mConsumers = new HashMap<>();
    private Map<Long, HostingComposeGraph> mNeedSubscribeComposeGraphs = new HashMap<>();

    private class AnalysisChecker implements Runnable {
        @Override
        public void run() {
            analysisSubscribeTopics();
            mAnylysisThread.postTaskDelay(new AnalysisChecker(), 5, TimeUnit.SECONDS);
        }
    }

    public QuotCombSyncTopicsHandler() {
        mAnylysisThread = new TaskThread();
        mAnylysisThread.postTaskDelay(new AnalysisChecker(), 5, TimeUnit.SECONDS);
    }

    public void syncCombTopics(SyncCombTopicsRequest request) {
        Preconditions.checkNotNull(request);
        Preconditions.checkArgument(StringUtils.isNotEmpty(request.getConsumerKey()));

        mAnylysisThread.postTask(new Runnable() {

            @Override
            public void run() {
                try {
                    ConsumerNode node = new ConsumerNode(request.getConsumerKey());
                    node.setLastSyncTimetampMs(System.currentTimeMillis());
                    if (request.getComposeGraphIds() != null) {
                        for (Long composeGraphId : request.getComposeGraphIds()) {
                            HostingComposeGraph composeGraph = HostingComposeGraphHolder
                                    .Global().getComposeGraph(composeGraphId);
                            if (composeGraph == null) {
                                continue;
                            }
                            node.addComposeGraph(composeGraph);
                        }
                    }

                    mConsumers.put(request.getConsumerKey(), node);

                    analysisSubscribeTopics();
                } catch (ErrorInfo e) {
                    AppLog.e(e.getMessage(), e);
                }
            }
        });
    }

    private void analysisSubscribeTopics() {
        Preconditions.checkState(mAnylysisThread.isInCurrentThread());

        Map<Long, HostingComposeGraph> newNeedSubscribeComposeGraphs = new HashMap<>();
        calculateSubscribeTopics(newNeedSubscribeComposeGraphs);

        for (HostingComposeGraph composeGraph : newNeedSubscribeComposeGraphs.values()) {
            if (!mNeedSubscribeComposeGraphs.containsKey(composeGraph.getComposeGraphId())) {
                QuotCombProcessor.Global().addNeedPublishGraph(composeGraph);
            }
        }

        for (HostingComposeGraph composeGraph : mNeedSubscribeComposeGraphs.values()) {
            if (!newNeedSubscribeComposeGraphs.containsKey(composeGraph.getComposeGraphId())) {
                QuotCombProcessor.Global().removeNeedPublishGraph(composeGraph.getComposeGraphId());
            }
        }

        mNeedSubscribeComposeGraphs = newNeedSubscribeComposeGraphs;
    }

    private void calculateSubscribeTopics(Map<Long, HostingComposeGraph> needSubscribeComposeGraphs) {
        long currentTimestampMs = System.currentTimeMillis();

        for (ConsumerNode node : mConsumers.values()) {
            if (currentTimestampMs >= node.getLastSyncTimetampMs() + 15000) {
                continue;
            }

            if (node.getComposeGraphs() != null) {
                for (HostingComposeGraph composeGraph : node.getComposeGraphs()) {
                    needSubscribeComposeGraphs.put(composeGraph.getComposeGraphId(), composeGraph);
                }
            }
        }
    }

}
