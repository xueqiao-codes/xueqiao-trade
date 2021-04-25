package xueqiao.trade.hosting.asset.storage;

import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;

public class PageOptionHelper {

    public static void checkIndexedPageOption(IndexedPageOption pageOption
            , int maxPageSize) throws ErrorInfo {
        ParameterChecker.check(pageOption != null, "pageOption should not be null");
        ParameterChecker.check(pageOption.getPageIndex() >= 0, "pageIndex should not < 0");
        ParameterChecker.check(pageOption.getPageSize() > 0
                        && pageOption.getPageSize() <= maxPageSize
                , "pageSize shoud between 1-" + maxPageSize);
    }
}
