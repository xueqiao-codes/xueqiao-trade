package xueqiao.trade.hosting.quot.comb.core;

import org.soldier.base.logger.AppLog;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import xueqiao.trade.hosting.quot.comb.thriftapi.HostingQuotationComb;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class QuotCombPublisher extends Thread {

    private ZContext mCtx;
    private int mPort;

    private volatile boolean mStopped = false;
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<Runnable>();
    private ZMQ.Socket mSocket;

    public QuotCombPublisher(ZContext ctx, int port) {
        this.mCtx = ctx;
        this.mPort = port;
    }

    public void startWork() {
        this.setDaemon(true);
        this.setName("QuotCombPublisher");
        this.start();
    }

    public void stopWork() {
        mStopped = true;
        this.interrupt();
    }

    public void sendQuotationComb(long composeGraphId, HostingQuotationComb quotationComb, byte[] combQuotationBytes) {
        try {
            mQueue.put(new Runnable() {

                @Override
                public void run() {
                    if (AppLog.traceEnabled()) {
                        AppLog.t("publish combQuotation=" + quotationComb);
                    }

                    mSocket.sendMore("/quotation/comb/" + String.valueOf(composeGraphId));
                    mSocket.send(combQuotationBytes);
                }
            });
        } catch (InterruptedException e) {
            AppLog.e(e.getMessage(), e);
        }

    }

    @Override
    public void run() {
        mSocket = mCtx.createSocket(ZMQ.PUB);
        mSocket.setSndHWM(100);
        mSocket.bind("tcp://127.0.0.1:" + mPort);

        while(!mStopped) {
            try {
                Runnable runnable = mQueue.poll(100, TimeUnit.MILLISECONDS);
                if (runnable != null) {
                    runnable.run();
                }
            } catch (Throwable e) {
                AppLog.e(e.getMessage(), e);
            }

        }

    }


}
