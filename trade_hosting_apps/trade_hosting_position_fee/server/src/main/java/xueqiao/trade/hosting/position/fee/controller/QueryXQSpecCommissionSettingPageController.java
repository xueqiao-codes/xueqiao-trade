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
import xueqiao.trade.hosting.position.fee.storage.table.XQSpecCommissionSettingsTable;
import xueqiao.trade.hosting.position.fee.thriftapi.QueryXQSpecSettingOptions;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettingPage;
import xueqiao.trade.hosting.position.fee.thriftapi.XQSpecCommissionSettings;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryXQSpecCommissionSettingPageController implements IController {

    private QueryXQSpecSettingOptions queryOptions;
    private IndexedPageOption pageOption;

    public QueryXQSpecCommissionSettingPageController(QueryXQSpecSettingOptions queryOptions, IndexedPageOption pageOption) {
        this.queryOptions = queryOptions;
        this.pageOption = pageOption;
    }

    @Override
    public void checkParams() throws ErrorInfo {
        // check nothing
    }

    public XQSpecCommissionSettingPage doQuery() throws TException {
        XQSpecCommissionSettingPage page = new DBQueryHelper<XQSpecCommissionSettingPage>(PositionFeeDB.Global()) {
            @Override
            protected XQSpecCommissionSettingPage onQuery(Connection connection) throws Exception {
                return new XQSpecCommissionSettingsTable(connection).query(queryOptions, pageOption);
            }
        }.query();

        if (page != null && page.getXQSpecCommissionSettingListSize() > 0) {
            List<Integer> commodityIds = new ArrayList<>();
            for (XQSpecCommissionSettings xqSpecCommissionSettings : page.getXQSpecCommissionSettingList()) {
                SledExchange sledExchange = SledExchangeCacheManager.getInstance().get(xqSpecCommissionSettings.getCommodityInfo().getExchangeMic());
                xqSpecCommissionSettings.getCommodityInfo().setExchangeAcronym(sledExchange.getAcronym());
                xqSpecCommissionSettings.getCommodityInfo().setExchangeCnAcronym(sledExchange.getCnAcronym());
                commodityIds.add((int) xqSpecCommissionSettings.getCommodityId());
            }
            List<SledCommodity> commodityList = HostingContractApi.queryCommodity(commodityIds);
            Map<Integer, SledCommodity> commodityMap = new HashMap<>();
            if (commodityList != null && commodityList.size() > 0) {
                for (SledCommodity commodity : commodityList) {
                    commodityMap.put(commodity.getSledCommodityId(), commodity);
                }
                for (XQSpecCommissionSettings xqSpecCommissionSettings : page.getXQSpecCommissionSettingList()) {
                    SledCommodity commodity = commodityMap.get((int) xqSpecCommissionSettings.getCommodityId());
                    if (commodity != null) {
                        xqSpecCommissionSettings.getCommodityInfo().setCommodityEngAcronym(commodity.getEngName());
                        xqSpecCommissionSettings.getCommodityInfo().setCommodityCnAcronym(commodity.getCnName());
                    }
                }
            }
        }

        return page;
    }
}
