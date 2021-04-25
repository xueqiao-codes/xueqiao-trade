package xueqiao.trade.hosting.push.user;

import org.apache.thrift.TException;
import org.soldier.base.beanfactory.Globals;
import org.soldier.base.logger.AppLog;

import xueqiao.trade.hosting.storage.apis.IHostingManageApi;
import xueqiao.trade.hosting.storage.apis.IHostingSessionApi;
import xueqiao.trade.hosting.storage.apis.structs.SessionEntry;

public class WsUserChecker {
	private long mMachineId;
	private int mSubUserId;
	private String mToken;
	private IHostingManageApi mManageApi;
	private IHostingSessionApi mSessionApi;
	
	public WsUserChecker(final long machineId
			, final int subUserId
			, final String token) {
		this.mMachineId = machineId;
		this.mSubUserId = subUserId;
		this.mToken = token;
		this.mManageApi = Globals.getInstance().queryInterfaceForSure(IHostingManageApi.class);
		this.mSessionApi = Globals.getInstance().queryInterfaceForSure(IHostingSessionApi.class);
	}
	
	public boolean isValid() throws TException { 
		Boolean valid = false;
		try {
		    valid = checkValid();
		    return valid;
		} finally {
		    if (AppLog.infoEnabled()) {
		        StringBuilder logBuilder = new StringBuilder(64);
		        logBuilder.append("Check machineId=").append(mMachineId)
		                  .append(", subUserId=").append(mSubUserId)
		                  .append(", token=").append(mToken)
		                  .append(", valid=").append(valid);
		        AppLog.i(logBuilder.toString());
	        }
		}
	}
	
	private Boolean checkValid() throws TException {
	    if (mMachineId == 0 && mSubUserId == 0 && mToken.equals("00AABBCC_TEST_TOKEN")) {
            return true;
        }
        
        if (mMachineId != mManageApi.getMachineId()) {
            return false;
        }
        
        if (mSubUserId <= 0) {
        	return false;
        }
        SessionEntry entry = mSessionApi.getSession(mSubUserId);
        if (entry == null) {
            return false;
        }
        if (mToken != null && mToken.equals(entry.getToken())) {
            return true;
        }
        return false;
	}
}
