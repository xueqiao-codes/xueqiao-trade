package xueqiao.trade.hosting.storage.apis.structs;

import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.framework.utils.ParameterChecker;

public class PageOption {
	private int pageIndex = 0;
	private int pageSize = 10;
	private boolean needTotalCount = false;
	
	public int getPageIndex() {
		return pageIndex;
	}
	public PageOption setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
		return this;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public PageOption setPageSize(int pageSize) {
		this.pageSize = pageSize;
		return this;
	}
	
	public boolean isNeedTotalCount() {
		return needTotalCount;
	}
	public PageOption setNeedTotalCount(boolean needTotalCount) {
		this.needTotalCount = needTotalCount;
		return this;
	}
	
	public void checkValid() throws ErrorInfo {
		checkValid(Integer.MAX_VALUE);
	}
	
	public void checkValid(int maxPageSize) throws ErrorInfo {
	    ParameterChecker.checkInnerArgument(maxPageSize > 0);
		ParameterChecker.checkInnerArgument(pageIndex >= 0);
		ParameterChecker.checkInnerArgument(pageSize > 0 && pageSize <= maxPageSize);
	}
	
}
