package xueqiao.trade.hosting.position.fee.controller;

import com.longsheng.xueqiao.contract.standard.thriftapi.SledCommodity;
import com.longsheng.xueqiao.contract.standard.thriftapi.SledExchange;
import org.apache.thrift.TException;
import org.soldier.platform.db_helper.DBQueryHelper;
import org.soldier.platform.page.IndexedPageOption;
import org.soldier.platform.svr_platform.comm.ErrorInfo;
import xueqiao.trade.hosting.position.fee.controller.base.IController;
import xueqiao.trade.hosting.position.fee.core.cache.SledExchangeCacheManager;
import xueqiao.trade.hosting.position.fee.core.api.HostingContractApi;
import xueqiao.trade.hosting.position.fee.storage.PositionFeeDB;
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecMarginSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettingPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecMarginSettings;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryXQSpecMarginSettingPageController implements IController {

    private QueryXQSpecSettingOptions queryOptions;
    private IndexedPageOption pageOption;

    public QueryXQSpecMarginSettingPageController(QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) {
        this.queryOptions = queryOptions;
        this.pageOption = pageOption;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        // check nothing
    }

    public XQSpecMarginSettingPage doQuery() throws TException {
        XQSpecMarginSettingPage page = new DBQueryHelper<XQSpecMarginSettingPage>(PositionFeeDB.Global()) {
            @Override
            protected XQSpecMarginSettingPage onQuery(Connection connection) throws Exception {
                return new XQSpecMarginSettingsTable(connection).query(queryOptions, pageOption);
            }
        }.query();

        if (page != null && page.getXQSpecMarginSettingListSize() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            for (XQSpecMarginSettings xqSpecMarginSettings : page.getXQSpecMarginSettingList()) {
                SledExchange sledExchange = SledExchangeCacheManager.getInstance().get(xqSpecMarginSettings.getCommodityInfo().getExchangeMic());
                xqSpecMarginSettings.getCommodityInfo().setExchangeAcronym(sledExchange.getAcronym());
                xqSpecMarginSettings.getCommodityInfo().setExchangeCnAcronym(sledExchange.getCnAcronym());
                commodityIds.add((int) xqSpecMarginSettings.getCommodityId());
            }
            List<SledCommodity> commodityList = HostingContractApi.queryCommodity(commodityIds);
            Map<Integer, SledCommodity> commodityMap = new HashMap<>();
            if (commodityList != null && commodityList.size() > 0) {
                for (SledCommodity commodity : commodityList) {
                    commodityMap.put(commodity.getSledCommodityId(), commodity);
                }
                for (XQSpecMarginSettings xqSpecMarginSettings : page.getXQSpecMarginSettingList()) {
                    SledCommodity commodity = commodityMap.get((int)xqSpecMarginSettings.getCommodityId());
                    if (commodity != null) {
                        xqSpecMarginSettings.getCommodityInfo().setCommodityEngAcronym(commodity.getEngName());
                        xqSpecMarginSettings.getCommodityInfo().setCommodityCnAcronym(commodity.getCnName());
                    }
                }
            }
        }

        return page;
    }
}
