package xueqiao.trade.hosting.dealing.core.dfa;

import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.HostingExecOrder;

public abstract class InputAction {
   
    protected HostingExecOrder inputOrder;
    protected TransferCode transferCode;
    protected HostingExecOrder outputOrder;  // 承载订单中需要变化的内容部分，如果没有内容变化，保留为空
    
    public InputAction(HostingExecOrder inputOrder) {
        this.inputOrder = inputOrder;
    }
    
    public void action() {
        long nanoStart = System.nanoTime();
        try {
            run();
        } finally {
            if (AppLog.infoEnabled()) {
                AppLog.i("InputAction type=" + this.getClass().getSimpleName() + " action timeEscaped="
                       + ((System.nanoTime() - nanoStart)/1000) + "us");
            }
        }
    }
    
    protected abstract void run();
    
    public TransferCode getTransferCode() {
        return this.transferCode;
    }
    
    public HostingExecOrder getOutputOrder() {
        return this.outputOrder;
    }
    
}
