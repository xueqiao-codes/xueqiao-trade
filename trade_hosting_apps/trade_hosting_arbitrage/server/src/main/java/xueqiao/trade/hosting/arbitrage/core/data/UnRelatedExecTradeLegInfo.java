package xueqiao.trade.hosting.arbitrage.core.data;

import com.google.common.base.Preconditions;

import xueqiao.trade.hosting.HostingExecTradeLeg;

public class UnRelatedExecTradeLegInfo implements Comparable<UnRelatedExecTradeLegInfo> {
    private HostingExecTradeLeg mTradeLeg;
    private int mLeftVolume;
    
    public HostingExecTradeLeg getTradeLeg() {
        return mTradeLeg;
    }
    public void setTradeLeg(HostingExecTradeLeg tradeLeg) {
        this.mTradeLeg = tradeLeg;
    }
    
    public int getLeftVolume() {
        return mLeftVolume;
    }
    public void setLeftVolume(int leftVolume) {
        this.mLeftVolume = leftVolume;
    }
    
    @Override
    public int compareTo(UnRelatedExecTradeLegInfo legInfo) {
        Preconditions.checkNotNull(mTradeLeg);
        Preconditions.checkNotNull(legInfo.getTradeLeg());
        
        if (mTradeLeg.getExecTradeLegId() > legInfo.getTradeLeg().getExecTradeLegId()) {
            return 1;
        } else if (mTradeLeg.getExecTradeLegId() == legInfo.getTradeLeg().getExecTradeLegId()) {
            return 0;
        }
        return -1;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("UnRelatedExecTradeLegInfo(")
               .append("ExecOrderId=").append(mTradeLeg.getExecOrderId())
               .append(", ExecTradeId=").append(mTradeLeg.getRelatedExecTradeId())
               .append(", ExecTradeLegId=").append(mTradeLeg.getExecTradeLegId())
               .append(", sledContractId=").append(mTradeLeg.getLegContractSummary().getLegSledContractId())
               .append(", direction=").append(mTradeLeg.getTradeLegInfo().getLegUpsideTradeDirection())
               .append(", tradePrice=").append(mTradeLeg.getTradeLegInfo().getLegTradePrice())
               .append(", tradeVolume=").append(mTradeLeg.getTradeLegInfo().getLegTradeVolume())
               .append(", leftVolume=").append(mLeftVolume)
               .append(")");
        return builder.toString();
    }
    
}
