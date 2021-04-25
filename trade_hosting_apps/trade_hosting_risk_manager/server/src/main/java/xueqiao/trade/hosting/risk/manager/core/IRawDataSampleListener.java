package xueqiao.trade.hosting.risk.manager.core;

public interface IRawDataSampleListener {

    void onSampleProcess(RawDataPool rawDataPool);

    void onSampleFinished(RawDataPool rawDataPool);
}
