package xueqiao.trade.hosting.storage.apis.structs;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;

public class PageResult<T> {
	private int totalCount;
	private List<T> pageList;
	
	public PageResult() {
		this.pageList = new ArrayList<T>();
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public PageResult<T> setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		return this;
	}
	
	public List<T> getPageList() {
		return pageList;
	}
	
	public PageResult<T> setPageList(List<T> pageList) {
		Preconditions.checkArgument(pageList != null);
		this.pageList = pageList;
		return this;
	}
}
