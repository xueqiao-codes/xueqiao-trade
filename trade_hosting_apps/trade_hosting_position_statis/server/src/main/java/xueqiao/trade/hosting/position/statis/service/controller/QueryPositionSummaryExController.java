package xueqiao.trade.hosting.position.statis.service.controller;

import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.arbitrage.thriftapi.HostingXQTargetType;
import xueqiao.trade.hosting.framework.utils.ParameterChecker;
import xueqiao.trade.hosting.position.statis.*;
import xueqiao.trade.hosting.position.statis.core.cache.position.CachePositionDynamic;
import xueqiao.trade.hosting.position.statis.core.cache.position.StatPositionCacheManager;
import xueqiao.trade.hosting.position.statis.storage.PositionStatisDB;
import xueqiao.trade.hosting.position.statis.storage.table.StatPositionSummaryTable;
import xueqiao.trade.hosting.storage.apis.structs.PageResult;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class QueryPositionSummaryExController {

    private QueryStatPositionSummaryOption queryOption;
    private IndexedPageOption pageOption;

    public QueryPositionSummaryExController(QueryStatPositionSummaryOption queryOption, IndexedPageOption pageOption) {
        this.queryOption = queryOption;
        this.pageOption = pageOption;
    }

    public void validateParams() throws ErrorInfo {
        ParameterChecker.checkNotNull(pageOption, "pageOption should be set");
        ParameterChecker.check(pageOption.isSetPageIndex() && pageOption.getPageIndex() >= 0
                , "pageOption's pageIndex should be set and pageOption's pageIndex should >= 0");
        ParameterChecker.check(pageOption.isSetPageSize() && pageOption.getPageSize() > 0
                , "pageOption's pageSize should be set and pageOption's pageSize should > 0");
    }

    public StatPositionSummaryExPage query() throws ErrorInfo {
        PageResult<StatPositionSummary> itemPageResult = queryStatPositionSummary();

        List<StatPositionSummary> positionSummaryList = itemPageResult.getPageList();
        List<StatPositionSummaryEx> positionSummaryExList = new ArrayList<>();
        StatPositionSummaryEx tempPositionSummaryEx;
        if (positionSummaryList != null && positionSummaryList.size() > 0) {
            for (StatPositionSummary positionSummary : positionSummaryList) {
                tempPositionSummaryEx = new StatPositionSummaryEx();
                tempPositionSummaryEx.setSubAccountId(positionSummary.getSubAccountId());
                tempPositionSummaryEx.setTargetKey(positionSummary.getTargetKey());
                tempPositionSummaryEx.setTargetType(positionSummary.getTargetType());

                tempPositionSummaryEx.setPositionSummary(positionSummary);
                tempPositionSummaryEx.setPositionDynamicInfo(convertToDynamicInfo(positionSummary.getSubAccountId(), positionSummary.getTargetKey(), positionSummary.getTargetType()));

                positionSummaryExList.add(tempPositionSummaryEx);
            }
        }

        StatPositionSummaryExPage positionSummaryExPage = new StatPositionSummaryExPage();

        positionSummaryExPage.setTotalNum(itemPageResult.getTotalCount());
        positionSummaryExPage.setStatPositionSummaryExList(positionSummaryExList);
        return positionSummaryExPage;
    }

    private PageResult<StatPositionSummary> queryStatPositionSummary() throws ErrorInfo {
        return new DBQueryHelper<PageResult<StatPositionSummary>>(PositionStatisDB.Global()) {
            @Override
            protected PageResult<StatPositionSummary> onQuery(Connection conn) throws Exception {
                return new StatPositionSummaryTable(conn).getStatPositionSummaryPage(queryOption, pageOption);
            }
        }.query();
    }

    public StatPositionDynamicInfo convertToDynamicInfo(long subAccountId, String targetKey, HostingXQTargetType targetType) {
        CachePositionDynamic positionDynamic = StatPositionCacheManager.getInstance().getCachePositionDynamic(subAccountId, targetKey, targetType);
        if (positionDynamic == null) {
            return null;
        }
        StatPositionDynamicInfo dynamicInfo = new StatPositionDynamicInfo();

        dynamicInfo.setTargetKey(positionDynamic.getTargetKey());
        dynamicInfo.setSubAccountId(positionDynamic.getSubAccountId());
        dynamicInfo.setTargetType(positionDynamic.getTargetType());
        if (positionDynamic.isSetLastPrice()) {
            dynamicInfo.setLastPrice(positionDynamic.getLastPrice());
        }
        if (positionDynamic.isSetPositionProfit()) {
            dynamicInfo.setPositionProfit(positionDynamic.getPositionProfit());
        }
        if (positionDynamic.isSetClosedProfit()) {
            dynamicInfo.setClosedProfit(positionDynamic.getClosedProfit());
        }
        if (positionDynamic.isSetTotalProfit()) {
            dynamicInfo.setTotalProfit(positionDynamic.getTotalProfit());
        }
        if (positionDynamic.isSetPositionValue()) {
            dynamicInfo.setPositionValue(positionDynamic.getPositionValue());
        }
        if (positionDynamic.isSetLeverage()) {
            dynamicInfo.setLeverage(positionDynamic.getLeverage());
        }
        if (positionDynamic.isSetCurrency()) {
            dynamicInfo.setCurrency(positionDynamic.getCurrency());
        }

        return dynamicInfo;
    }
}
