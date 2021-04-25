package xueqiao.trade.hosting.dealing.core.dfa.events;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingUpsideNotifyStateInfo;
import xueqiao.trade.hosting.HostingUpsideNotifyStateSource;
import xueqiao.trade.hosting.dealing.core.dfa.DFAEvent;
import xueqiao.trade.hosting.dealing.core.dfa.InputAction;
import xueqiao.trade.hosting.events.UpsideNotifyForwardStateEvent;

public class NotifyForwardStateInputEvent extends DFAEvent {
    
    private UpsideNotifyForwardStateEvent forwardStateEvent;
    
    public NotifyForwardStateInputEvent(UpsideNotifyForwardStateEvent forwardStateEvent) {
        this.forwardStateEvent = forwardStateEvent;
    }
    
    @Override
    public String getType() {
        return getClass().getSimpleName();
    }
    
    @Override
    public InputAction getInputAction(HostingExecOrder inputOrder) {
    	HostingUpsideNotifyStateInfo forwardStateInfo = forwardStateEvent.getForwardStateInfo();
        if (forwardStateInfo == null) {
            AppLog.e("Received no stateInfo forwardStateEvent=" + forwardStateEvent);
            return null;
        }
    	
        return new NotifyStateInfoInputAction(inputOrder
        		, forwardStateEvent.getForwardStateInfo()
        		, HostingUpsideNotifyStateSource.UPSIDE_FORWARD
        		, forwardStateEvent.getEventCreateTimestampMs());
    }

}
