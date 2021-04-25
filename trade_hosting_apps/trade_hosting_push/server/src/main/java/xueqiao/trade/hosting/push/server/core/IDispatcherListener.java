package xueqiao.trade.hosting.push.server.core;

public interface IDispatcherListener {
    public void onConnected(int subUserId, PushChannel pushChannel);
    
    public void onDisconnected(int subUserId);
}
