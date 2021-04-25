package xueqiao.trade.hosting.dealing.core.dfa;

import xueqiao.trade.hosting.HostingExecOrder;

public abstract class DFAEvent {
    public abstract String getType();
    public abstract InputAction getInputAction(HostingExecOrder inputOrder);
    
    @Override
    public String toString() {
        return getType();
    }
}
