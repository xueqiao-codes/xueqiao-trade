package xueqiao.trade.hosting.dealing.core.dfa;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingExecOrderStateValue;

public class Arc {
    private HostingExecOrderStateValue fromState;
    private TransferCode transferCode;
    private HostingExecOrderStateValue toState;
    
    public Arc(HostingExecOrderStateValue fromState
            , TransferCode transferCode
            , HostingExecOrderStateValue toState) {
        Preconditions.checkNotNull(fromState);
        Preconditions.checkNotNull(transferCode);
        Preconditions.checkNotNull(toState);
        
        this.fromState = fromState;
        this.transferCode = transferCode;
        this.toState = toState;
    }
    
    public HostingExecOrderStateValue getFromState() {
        return this.fromState;
    }
    
    public TransferCode getTransferCode() {
        return this.transferCode;
    }
    
    public HostingExecOrderStateValue getToState() {
        return this.toState;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        builder.append(fromState)
               .append("--").append(transferCode)
               .append("-->").append(toState);
        return builder.toString();
    }
}
