package xueqiao.trade.hosting.dealing.core.verify;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import xueqiao.trade.hosting.HostingExecOrder;
import xueqiao.trade.hosting.HostingExecOrderInputExt;
import xueqiao.trade.hosting.HostingExecOrderLegContractSummary;
import xueqiao.trade.hosting.HostingExecOrderTradeAccountSummary;

public interface IVerifyProcessor {
    public static class VerifyResult {
        public static enum VerifyResultType {
            VERIFY_SUCCESS,
            VERIFY_FAILED,
        }
        
        private VerifyResultType mResultType;
        private HostingExecOrderTradeAccountSummary mTradeAccountSummary;
        private HostingExecOrderInputExt mOrderInputExt;
        private List<HostingExecOrderLegContractSummary> mLegContractSummarys;

        private int mFailedVerifyErrorCode = 0;

        private String mStatusAppendMsg;
        
        private long mTtlTimestampMs = 0;
        
        public VerifyResult() {
        }
        
        public VerifyResult(VerifyResultType type) {
            this.mResultType = type;
        }
        
        public VerifyResultType getResultType() {
            return mResultType;
        }
        public VerifyResult setResultType(VerifyResultType resultType) {
            this.mResultType = resultType;
            return this;
        }
        
        public HostingExecOrderTradeAccountSummary getTradeAccountSummary() {
            return mTradeAccountSummary;
        }
        public VerifyResult setTradeAccountSummary(HostingExecOrderTradeAccountSummary tradeAccountSummary) {
            this.mTradeAccountSummary = tradeAccountSummary;
            return this;
        }
        
        public List<HostingExecOrderLegContractSummary> getLegContractSummarys() {
            return mLegContractSummarys;
        }

        public void setLegContractSummarys(List<HostingExecOrderLegContractSummary> mLegContractSummarys) {
            this.mLegContractSummarys = mLegContractSummarys;
        }

        public HostingExecOrderInputExt getOrderInputExt() {
            return mOrderInputExt;
        }
        public VerifyResult setOrderInputExt(HostingExecOrderInputExt orderInputExt) {
            this.mOrderInputExt = orderInputExt;
            return this;
        }
        
        public int getFailedVerifyErrorCode() {
            return mFailedVerifyErrorCode;
        }
        public VerifyResult setFailedVerifyErrorCode(int failedVerifyErrorCode) {
            this.mFailedVerifyErrorCode = failedVerifyErrorCode;
            return this;
        }

        public String getStatusAppendMsg() {
            if (StringUtils.isNotEmpty(mStatusAppendMsg)) {
                return mStatusAppendMsg;
            }
            return "";
        }

        public void setStatusAppendMsg(String statusAppendMsg) {
            this.mStatusAppendMsg = statusAppendMsg;
        }
        
        @Override
        public String toString() {
        	StringBuilder builder = new StringBuilder(256);
        	builder.append("VerifyResult(resultType=").append(mResultType)
        		   .append(", failedVerifyErrorCode=").append(mFailedVerifyErrorCode);
        	if (mTradeAccountSummary != null) {
        		builder.append(", tradeAccountSummary=").append(mTradeAccountSummary);
        	}
        	if (mOrderInputExt != null) {
        		builder.append(", orderInputExt=").append(mOrderInputExt);
        	}
        	if (mLegContractSummarys != null) {
        	    builder.append(", mLegContractSummarys=List[");
        	    boolean isFirst = true;
        	    for (HostingExecOrderLegContractSummary legContractSummary : mLegContractSummarys) {
        	        if (isFirst) {
        	            isFirst = false;
        	        } else {
        	            builder.append(",");
        	        }
        	        builder.append(legContractSummary);
        	    }
        	    builder.append("]");
        	}
        	builder.append(", mStatusAppendMsg=").append(mStatusAppendMsg);
        	builder.append(")");
        	return builder.toString();
        }

        public long getTtlTimestampMs() {
            return mTtlTimestampMs;
        }

        public void setTtlTimestampMs(long mTtlTimestampMs) {
            this.mTtlTimestampMs = mTtlTimestampMs;
        }
    }
    
    public VerifyResult verify(HostingExecOrder inputOrder) throws Exception;
}
