package xueqiao.trade.hosting.entry.core;

import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;

import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.storage.apis.structs.PageOption;

public class PageOptionHelper {
	
	public static void checkIndexedPageOption(IndexedPageOption pageOption
			, int maxPageSize) throws ErrorInfo {
		ParameterChecker.check(pageOption != null, "pageOption should not be null");
		ParameterChecker.check(pageOption.getPageIndex() >= 0, "pageIndex should not < 0");
		ParameterChecker.check(pageOption.getPageSize() > 0 
				&& pageOption.getPageSize() <= maxPageSize
				, "pageSize shoud between 1-" + maxPageSize);
	}
	
	public static PageOption toApiPageOption(IndexedPageOption pageOption) {
		PageOption apiPageOption = new PageOption();
		apiPageOption.setNeedTotalCount(pageOption.isNeedTotalCount());
		apiPageOption.setPageIndex(pageOption.getPageIndex());
		apiPageOption.setPageSize(pageOption.getPageSize());
		return apiPageOption;
	}
}
