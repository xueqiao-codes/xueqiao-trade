package xueqiao.trade.hosting.quot.comb.sdk;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.soldier.base.StringFactory;
import org.soldier.base.logger.AppLog;
import org.soldier.platform.svr_platform.client.TStubOption;
import org.soldier.platform.svr_platform.util.ProtocolUtil;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import xueqiao.trade.hosting.framework.thread.TaskThread;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;
import xueqiao.trade.hosting.quot.comb.thriftapi.SyncCombTopicsRequest;
import xueqiao.trade.hosting.quot.comb.thriftapi.helper.QuotCombStubFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QuotCombClient extends Thread {

    public interface IQuotCombClientCallback {
        void onReceiveQuotationComb(HostingQuotationComb quotationComb);
    }

    private static TProtocolFactory PROTOCOL_FACTORY = new TCompactProtocol.Factory();

    private static String ADD_TOPIC_CALL = "add_topic";
    private static String REMOVE_TOPIC_CALL = "remove_topic";
    private static String SYNC_TOPICS_CALL = "sync_topic";
    private static String END_CALL = "end";
    private static String REP_SUCCESS = "success";

    private static class MultiPartMsg {
        public List<byte[]> parts = new ArrayList<byte[]>();

        public boolean receive(ZMQ.Socket socket) {
            parts.clear();
            boolean more = true;
            while (more)
            {
                byte[] msg = socket.recv(ZMQ.DONTWAIT);
                if (msg == null) {
                    return false;
                }
                parts.add(msg);
                more = socket.hasReceiveMore();
            }
            return true;
        }
    }

    private volatile boolean mStopped = false;
    private final String mConsumerKey;
    private IQuotCombClientCallback mCallback;

    private TaskThread mTimerThread = new TaskThread();
    private ZContext mCtx;

    private ZMQ.Socket mRecvSocket;
    private ZMQ.Socket mCallSocket;
    private String mCallSocketDescription;

    private HashSet<Long> mSubscribeComposeGraphIds = new HashSet<>();
    private TaskThread mQuotationThread = new TaskThread();

    public QuotCombClient(String consumerKey, IQuotCombClientCallback callback) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(consumerKey));
        Preconditions.checkNotNull(callback);

        mConsumerKey = consumerKey;
        mCallback = callback;

        mCallSocketDescription = "inproc://quot.comb.client." + consumerKey + ".socket";
        mCtx = new ZContext();

    }

    private class TriggerSyncTopicsTask implements Runnable {

        @Override
        public void run() {
            AppLog.t("Trigger syncTopics start...");

            ZMQ.Socket reqSocket = mCtx.createSocket(ZMQ.REQ);
            reqSocket.connect(mCallSocketDescription);
            reqSocket.send(SYNC_TOPICS_CALL);
            reqSocket.recvStr();
            reqSocket.close();

            AppLog.t("Trigger syncTopics finished...");

            mTimerThread.postTaskDelay(new TriggerSyncTopicsTask(), 5, TimeUnit.SECONDS);
        }

    }

    public void startWork() {
        this.setDaemon(true);
        this.setName("QuotCombClient_" + mConsumerKey);
        this.start();

        mTimerThread.postTaskDelay(new TriggerSyncTopicsTask(), 5, TimeUnit.SECONDS);
    }

    public void stopWork() {
        mStopped = true;

        ZMQ.Socket reqSocket = mCtx.createSocket(ZMQ.REQ);
        reqSocket.connect(mCallSocketDescription);
        reqSocket.send(END_CALL);

        try {
            this.join(2000);
        } catch (InterruptedException e) {
        }
        reqSocket.close();

        mCtx.destroy();
    }

    public void addComposeGraphId(long composeGraphId) {
        ZMQ.Socket reqSocket = mCtx.createSocket(ZMQ.REQ);
        reqSocket.connect(mCallSocketDescription);
        reqSocket.sendMore(ADD_TOPIC_CALL);
        reqSocket.send(String.valueOf(composeGraphId));
        reqSocket.recvStr();
        reqSocket.close();
    }

    public void removeComposeGraphId(long composeGraphId) {
        ZMQ.Socket reqSocket = mCtx.createSocket(ZMQ.REQ);
        reqSocket.connect(mCallSocketDescription);
        reqSocket.sendMore(REMOVE_TOPIC_CALL);
        reqSocket.send(String.valueOf(composeGraphId));
        reqSocket.recvStr();
        reqSocket.close();
    }

    private void syncTopics() {
        SyncCombTopicsRequest request = new SyncCombTopicsRequest();
        request.setConsumerKey(mConsumerKey);
        request.setComposeGraphIds(mSubscribeComposeGraphIds);

        try {
            QuotCombStubFactory.getStub().syncCombTopics(request, new TStubOption().setTimeoutMs(100));
        } catch (Throwable e) {
            AppLog.e(e.getMessage(), e);
        }
    }

    private void sendCallResp() {
        mCallSocket.send(REP_SUCCESS);
    }

    private void onCall() {
        while(true) {
            MultiPartMsg msg = new MultiPartMsg();
            if (!msg.receive(mCallSocket)) {
                break ;
            }
            if (msg.parts.isEmpty()) {
                break;
            }

            String callType = StringFactory.newUtf8String(msg.parts.get(0));
            if (ADD_TOPIC_CALL.equals(callType)) {
                long composeGraphId = Long.parseLong(StringFactory.newUtf8String(msg.parts.get(1)));
                if (composeGraphId <= 0 || mSubscribeComposeGraphIds.contains(composeGraphId)) {
                    sendCallResp();
                    continue ;
                }
                mSubscribeComposeGraphIds.add(composeGraphId);

                mRecvSocket.subscribe("/quotation/comb/" + String.valueOf(composeGraphId));

                syncTopics();
                AppLog.i("subscribe composeGraphId=" + composeGraphId);
            } else if (REMOVE_TOPIC_CALL.equals(callType)) {
                long composeGraphId = Long.parseLong(StringFactory.newUtf8String(msg.parts.get(1)));
                if (composeGraphId <= 0 || !mSubscribeComposeGraphIds.contains(composeGraphId)) {
                    sendCallResp();
                    continue ;
                }
                mSubscribeComposeGraphIds.remove(composeGraphId);

                mRecvSocket.unsubscribe("/quotation/comb/" + String.valueOf(composeGraphId));

                syncTopics();
                AppLog.i("unSubscribe composeGraphId=" + composeGraphId);
            } else if (SYNC_TOPICS_CALL.equals(callType)) {
                syncTopics();
            }

            sendCallResp();

            if (END_CALL.equals(callType)) {
                AppLog.i("end...");
                break;
            }
        }
    }

    private class QuotationCombPublishTask implements Runnable {

        private byte[] mQuotationBytes;
        public QuotationCombPublishTask(byte[] quotationBytes) {
            this.mQuotationBytes = quotationBytes;
        }

        @Override
        public void run() {
            HostingQuotationComb quotationComb
                    = ProtocolUtil.unSerialize(PROTOCOL_FACTORY, mQuotationBytes, HostingQuotationComb.class);
            if (quotationComb == null) {
                return ;
            }

            mCallback.onReceiveQuotationComb(quotationComb);
        }
    }

    private void onProcess() {
        int receivedCount = 0;
        while(receivedCount < 100) {
            MultiPartMsg msg = new MultiPartMsg();
            if (!msg.receive(mRecvSocket)) {
                break;
            }

            ++receivedCount;
            if (msg.parts.size() != 2) {
                continue;
            }

            mQuotationThread.postTask(new QuotationCombPublishTask(msg.parts.get(1)));
        }
    }

    @Override
    public void run() {
        mCallSocket = mCtx.createSocket(ZMQ.REP);
        mCallSocket.bind(mCallSocketDescription);

        mRecvSocket = mCtx.createSocket(ZMQ.SUB);
        mRecvSocket.setTCPKeepAlive(1);
        mRecvSocket.setTCPKeepAliveCount(3);
        mRecvSocket.setTCPKeepAliveIdle(60);
        mRecvSocket.setTCPKeepAliveInterval(3);
        mRecvSocket.setRcvHWM(100);
        mRecvSocket.connect("tcp://127.0.0.1:1850");

        ZMQ.Poller poller = mCtx.createPoller(2);
        poller.register(new ZMQ.PollItem(mCallSocket, ZMQ.Poller.POLLIN));
        poller.register(new ZMQ.PollItem(mRecvSocket, ZMQ.Poller.POLLIN));

        while(!mStopped) {
            try {
                int ret = poller.poll();
                if (ret <= 0 ) {
                    if (ret < 0) {
                        AppLog.e("poll sockets failed, ret=" + ret);
                    }
                    continue;
                }

                if (poller.pollin(0)) {
                    onCall();
                    if (mStopped) {
                        break;
                    }
                }
                if (poller.pollin(1)) {
                    onProcess();
                }
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }
        }

        mCallSocket.close();
        mRecvSocket.close();
    }
}
