package xueqiao.trade.hosting.dealing.core.revoke;

import xueqiao.trade.hosting.HostingExecOrderStateValue;

public class RevokeAction {
    private HostingExecOrderStateValue nextState;
    private int failedErrorCode;

    private boolean reject = false;

    public RevokeAction(HostingExecOrderStateValue nextState) {
        this.setNextState(nextState);
    }

    public RevokeAction(int failedErrorCode) {
        this.setReject(true);
        this.setFailedErrorCode(failedErrorCode);
    }

    public RevokeAction(HostingExecOrderStateValue nextState
            , int failedErrorCode) {
        this.setNextState(nextState);
        this.setFailedErrorCode(failedErrorCode);   // 流转到订单状态信息中去
    }

    public HostingExecOrderStateValue getNextState() {
        return nextState;
    }

    public void setNextState(HostingExecOrderStateValue nextState) {
        this.nextState = nextState;
    }

    public int getFailedErrorCode() {
        return failedErrorCode;
    }

    public void setFailedErrorCode(int failedErrorCode) {
        this.failedErrorCode = failedErrorCode;
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

}
